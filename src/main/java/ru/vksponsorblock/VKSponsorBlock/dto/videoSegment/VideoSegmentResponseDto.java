package ru.vksponsorblock.VKSponsorBlock.dto.videoSegment;

import lombok.Data;
import ru.vksponsorblock.VKSponsorBlock.utils.SegmentType;

import java.util.UUID;

@Data
public class VideoSegmentResponseDto {
    private UUID id;
    private Float startTimeSeconds;
    private Float endTimeSeconds;
    private SegmentType segmentType;
    private Integer vote;
}
