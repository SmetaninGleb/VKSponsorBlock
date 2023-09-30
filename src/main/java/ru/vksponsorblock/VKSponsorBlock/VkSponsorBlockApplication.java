package ru.vksponsorblock.VKSponsorBlock;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import ru.vksponsorblock.VKSponsorBlock.dto.videoSegment.VideoSegmentResponseDto;
import ru.vksponsorblock.VKSponsorBlock.models.VideoSegment;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "VKSponsorBlock Server",
				version = "1.0.0",
				description = "Server for VKSponsorBlock browser extension",
				contact = @Contact(
						name = "Gleb Smetanin",
						email = "g.o.smetanin@gmail.com"
				)
		)
)
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
