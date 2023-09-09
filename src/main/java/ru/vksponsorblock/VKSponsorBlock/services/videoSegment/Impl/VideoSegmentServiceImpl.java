package ru.vksponsorblock.VKSponsorBlock.services.videoSegment.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public List<VideoSegmentResponseDto> getSegments(VideoIdDto videoIdDto) {
        List<VideoSegment> segments = videoSegmentRepository.findAllByVideoId(videoIdDto.getVideoId());
        List<VideoSegmentResponseDto> dtoList = segments.stream()
                .map(segment -> {
                    VideoSegmentResponseDto dto = new VideoSegmentResponseDto();
                    modelMapper.map(dto, VideoSegment.class);
                    Integer vote = segment.getLikedUsers().size() - segment.getDislikedUsers().size();
                    dto.setVote(vote);
                    return dto;
                })
                .toList();
        return dtoList;
    }

    @Override
    public void addSegment(VideoSegmentAddRequestDto addRequestDto) {
        User user = userValidateService.validateUserById(addRequestDto.getUserId());
        VideoSegment segment = modelMapper.map(addRequestDto, VideoSegment.class);
        segment.setCreator(user);
        videoSegmentRepository.save(segment);
    }

    @Override
    public void vote(VoteDto voteDto) {
        User user = userValidateService.validateUserById(voteDto.getUserId());
        VideoSegment segment = videoSegmentValidateService.validateSegmentById(voteDto.getVideoSegmentId());
        if (voteDto.getVoteType().equals(VoteType.UP)
                && !segment.getLikedUsers().contains(user)) {
            segment.getLikedUsers().add(user);
        }
        if (voteDto.getVoteType().equals(VoteType.DOWN)
                && !segment.getDislikedUsers().contains(user)) {
            segment.getDislikedUsers().add(user);
        }
        if (voteDto.getVoteType().equals(VoteType.RESET)) {
            segment.getLikedUsers().remove(user);
            segment.getDislikedUsers().remove(user);
        }
        videoSegmentRepository.save(segment);
    }
}
