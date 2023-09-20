package ru.vksponsorblock.VKSponsorBlock.restControllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vksponsorblock.VKSponsorBlock.dto.RestErrorResponseDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoIdDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentAddRequestDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentResponseDto;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VoteDto;
import ru.vksponsorblock.VKSponsorBlock.services.videoSegment.VideoSegmentService;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.VideoSegmentNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VideoSegmentRestController {
    private final VideoSegmentService videoSegmentService;

    @Autowired
    public VideoSegmentRestController(VideoSegmentService videoSegmentService) {
        this.videoSegmentService = videoSegmentService;
    }

    @GetMapping("/skipSegments")
    public List<VideoSegmentResponseDto> getSkipSegments(@Valid VideoIdDto requestDto) {
        return videoSegmentService.getSegments(requestDto);
    }

    @PostMapping("/skipSegments")
    public ResponseEntity<HttpStatus> postSkipSegment(@RequestParam @Valid VideoSegmentAddRequestDto requestDto) {
        videoSegmentService.addSegment(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/vote")
    public ResponseEntity<HttpStatus> vote(@RequestParam @Valid VoteDto voteDto) {
        videoSegmentService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<RestErrorResponseDto> handler(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorResponseDto(ex.getMessage()));
    }
}
