package ru.vksponsorblock.VKSponsorBlock.services.videoSegment.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vksponsorblock.VKSponsorBlock.models.VideoSegment;
import ru.vksponsorblock.VKSponsorBlock.repositories.VideoSegmentRepository;
import ru.vksponsorblock.VKSponsorBlock.services.videoSegment.VideoSegmentValidateService;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.VideoSegmentNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
public class VideoSegmentValidateServiceImpl implements VideoSegmentValidateService {
    private final VideoSegmentRepository segmentRepository;

    @Autowired
    public VideoSegmentValidateServiceImpl(VideoSegmentRepository segmentRepository) {
        this.segmentRepository = segmentRepository;
    }

    @Override
    public VideoSegment validateSegmentById(UUID segmentId) {
        Optional<VideoSegment> optSegment = segmentRepository.findById(segmentId);
        return optSegment.orElseThrow(() -> getNotFoundExceptionById(segmentId));
    }

    private VideoSegmentNotFoundException getNotFoundExceptionById(UUID segmentId) {
        VideoSegmentNotFoundException exception = new VideoSegmentNotFoundException();
        exception.setVideoSegmentId(segmentId);
        return exception;
    }
}
