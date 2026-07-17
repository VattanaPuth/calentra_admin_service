package valio.admin_service.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import valio.admin_service.properties.MinioProperties;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {
	
	private final MinioProperties properties;

	@Bean
	MinioClient minioClient() {
		return MinioClient.builder()
				.endpoint(properties.endpoint())
				.credentials(properties.accessKey(), properties.secretKey())
				.build();
	}

}
