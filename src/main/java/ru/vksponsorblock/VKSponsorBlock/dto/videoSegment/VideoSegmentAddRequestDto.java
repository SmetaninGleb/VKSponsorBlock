package ru.vksponsorblock.VKSponsorBlock.dto.videoSegment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.vksponsorblock.VKSponsorBlock.utils.SegmentType;

import java.util.UUID;

@Data
public class VideoSegmentAddRequestDto {
    @NotNull
    private String videoId;
    @NotNull
    private Float startTime;
    @NotNull
    private Float endTime;
    @NotNull
    private SegmentType type;
    @NotNull
    private UUID userId;
}
