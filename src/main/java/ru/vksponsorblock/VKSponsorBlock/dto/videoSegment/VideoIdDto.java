package ru.vksponsorblock.VKSponsorBlock.dto.videoSegment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VideoIdDto {
    @NotNull
    private String videoId;
}
