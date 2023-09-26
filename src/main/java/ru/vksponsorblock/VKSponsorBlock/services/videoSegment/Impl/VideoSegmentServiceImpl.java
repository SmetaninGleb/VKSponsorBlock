package ru.vksponsorblock.VKSponsorBlock.services.videoSegment.Impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentAddRequestDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentResponseDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VoteDto;
import ru.vksponsorblock.VKSponsorBlock.models.User;
import ru.vksponsorblock.VKSponsorBlock.models.VideoSegment;
import ru.vksponsorblock.VKSponsorBlock.repositories.VideoSegmentRepository;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserValidateService;
import ru.vksponsorblock.VKSponsorBlock.services.videoSegment.VideoSegmentService;
import ru.vksponsorblock.VKSponsorBlock.services.videoSegment.VideoSegmentValidateService;
import ru.vksponsorblock.VKSponsorBlock.utils.VoteType;

import java.util.List;

@Service
@Slf4j
public class VideoSegmentServiceImpl implements VideoSegmentService {
    private final VideoSegmentRepository videoSegmentRepository;
    private final ModelMapper modelMapper;
    private final UserValidateService userValidateService;
    private final VideoSegmentValidateService videoSegmentValidateService;

    @Autowired
    public VideoSegmentServiceImpl(VideoSegmentRepository videoSegmentRepository,
                                   ModelMapper modelMapper,
                                   UserValidateService userValidateService,
                                   VideoSegmentValidateService videoSegmentValidateService) {
        this.videoSegmentRepository = videoSegmentRepository;
        this.modelMapper = modelMapper;
        this.userValidateService = userValidateService;
        this.videoSegmentValidateService = videoSegmentValidateService;
    }

    @Transactional
    @Override
    public List<VideoSegmentResponseDto> getSegments(VideoIdDto videoIdDto) {
        List<VideoSegment> segments = videoSegmentRepository.findAllByVideoId(videoIdDto.getVideoId());
        List<VideoSegmentResponseDto> dtoList = segments.stream()
                .map(segment -> {
                    VideoSegmentResponseDto dto = modelMapper.map(segment, VideoSegmentResponseDto.class);
                    Integer vote = segment.getLikedUsers().size() - segment.getDislikedUsers().size();
                    dto.setVote(vote);
                    return dto;
                })
                .toList();
        return dtoList;
    }

    @Transactional
    @Override
    public void addSegment(VideoSegmentAddRequestDto addRequestDto) {
        VideoSegment segment = getSegmentByRequest(addRequestDto);
        videoSegmentRepository.save(segment);
    }

    @Transactional
    @Override
    public void vote(VoteDto voteDto) {
        User user = userValidateService.validateUserById(voteDto.getUserId());
        VideoSegment segment = videoSegmentValidateService.validateSegmentById(voteDto.getVideoSegmentId());
        if (voteDto.getVoteType().equals(VoteType.LIKE)
                && !segment.getLikedUsers().contains(user)) {
            segment.getDislikedUsers().remove(user);
            segment.getLikedUsers().add(user);
        }
        if (voteDto.getVoteType().equals(VoteType.DISLIKE)
                && !segment.getDislikedUsers().contains(user)) {
            segment.getLikedUsers().remove(user);
            segment.getDislikedUsers().add(user);
        }
        if (voteDto.getVoteType().equals(VoteType.RESET)) {
            segment.getLikedUsers().remove(user);
            segment.getDislikedUsers().remove(user);
        }
        videoSegmentRepository.save(segment);
    }

    private VideoSegment getSegmentByRequest(VideoSegmentAddRequestDto dto) {
        VideoSegment segment = new VideoSegment();
        segment.setVideoId(dto.getVideoId());
        segment.setCreator(userValidateService.validateUserById(dto.getUserId()));
        segment.setStartTimeSeconds(dto.getStartTime());
        segment.setEndTimeSeconds(dto.getEndTime());
        segment.setSegmentType(dto.getType());
        return segment;
    }
}
