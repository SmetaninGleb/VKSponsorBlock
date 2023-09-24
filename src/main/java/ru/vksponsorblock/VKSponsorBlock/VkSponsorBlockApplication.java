package ru.vksponsorblock.VKSponsorBlock;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentResponseDto;
import ru.vksponsorblock.VKSponsorBlock.models.VideoSegment;

@SpringBootApplication
public class VkSponsorBlockApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkSponsorBlockApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(VideoSegmentResponseDto.class, VideoSegment.class)
				.addMappings(mapper -> {
					mapper.map(VideoSegmentResponseDto::getId, VideoSegment::setVideoId);
				});
		return modelMapper;
	}
}
