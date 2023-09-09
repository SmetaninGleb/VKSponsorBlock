package ru.vksponsorblock.VKSponsorBlock.services.videoSegment;

import ru.vksponsorblock.VKSponsorBlock.models.VideoSegment;

import java.util.UUID;

public interface VideoSegmentValidateService {
    VideoSegment validateSegmentById(UUID segmentId);
}
