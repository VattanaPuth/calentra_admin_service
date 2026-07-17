package valio.admin_service.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.file-upload")
public record FileUploadProperties(List<String> allowedExtensions, long maxSizeMb){}