package ru.vksponsorblock.VKSponsorBlock.utils.exceptions;

import lombok.Data;

import java.util.UUID;


public class VideoSegmentNotFoundException extends RuntimeException {
    private UUID videoSegmentId;
    private String message;

    public void setVideoSegmentId(UUID videoSegmentId) {
        this.videoSegmentId = videoSegmentId;
        this.message = "User with id " + videoSegmentId + " not found!";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
