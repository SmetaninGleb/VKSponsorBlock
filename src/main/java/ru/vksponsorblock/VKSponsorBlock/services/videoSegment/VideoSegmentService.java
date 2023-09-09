package ru.vksponsorblock.VKSponsorBlock.services.videoSegment;

import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentAddRequestDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentResponseDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VoteDto;

import java.util.List;

public interface VideoSegmentService {
    List<VideoSegmentResponseDto> getSegments(VideoIdDto videoIdDto);
    void addSegment(VideoSegmentAddRequestDto addRequestDto);
    void vote(VoteDto voteDto);
}
