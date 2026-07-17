package valio.admin_service.enums;

import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FileCategory {
    IMAGE(Set.of("jpg", "jpeg", "png", "gif", "webp"), "admin-images"),
    DOCUMENT(Set.of("pdf", "doc", "docx", "xls", "xlsx"), "admin-documents"),
    ARCHIVE(Set.of("zip", "rar", "7z"), "admin-archives");
	
	private final Set<String> extensions;
	private final String bucketName;
	
    public static FileCategory fromExtension(String ext) {
        String normalized = ext.toLowerCase().replace(".", "");
        for (FileCategory category : values()) {
            if (category.extensions.contains(normalized)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unsupported file extension: " + ext);
    }
	
}
