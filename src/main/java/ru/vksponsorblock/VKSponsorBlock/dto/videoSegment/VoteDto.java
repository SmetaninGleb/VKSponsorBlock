package ru.vksponsorblock.VKSponsorBlock.dto.videoSegment;

import lombok.Data;
import ru.vksponsorblock.VKSponsorBlock.utils.VoteType;

import java.util.UUID;

@Data
public class VoteDto {
    private UUID videoSegmentId;
    private UUID userId;
    private VoteType voteType;
}
