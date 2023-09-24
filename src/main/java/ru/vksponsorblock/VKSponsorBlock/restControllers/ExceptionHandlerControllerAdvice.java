package ru.vksponsorblock.VKSponsorBlock.restControllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vksponsorblock.VKSponsorBlock.dto.RestErrorResponseDto;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserAlreadyExistsException;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserNotFoundException;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.VideoSegmentNotFoundException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(value = {
            UserNotFoundException.class,
            UserAlreadyExistsException.class,
            VideoSegmentNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponseDto> handleBadRequest(RuntimeException ex) {
        return new ResponseEntity<>(new RestErrorResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
