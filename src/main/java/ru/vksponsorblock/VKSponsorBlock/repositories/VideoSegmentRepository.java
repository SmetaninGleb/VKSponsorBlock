package ru.vksponsorblock.VKSponsorBlock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vksponsorblock.VKSponsorBlock.models.VideoSegment;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoSegmentRepository extends JpaRepository<VideoSegment, UUID> {
    VideoSegment findVideoSegmentById(UUID id);
    List<VideoSegment> findAllByVideoId(String videoId);
}
