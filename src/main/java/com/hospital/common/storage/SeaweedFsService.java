package com.hospital.common.storage;

import com.hospital.common.config.SeaweedFsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SeaweedFsService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
    );
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private final SeaweedFsProperties properties;
    private final RestTemplate restTemplate;

    public String uploadStaffPhoto(String staffId, MultipartFile file) {
        validatePhoto(file);

        String extension = resolveExtension(file);
        String objectKey = "staff/" + staffId + extension;
        String uploadUrl = buildObjectUrl(objectKey);

        HttpHeaders headers = new HttpHeaders();
        String contentType = file.getContentType();
        if (StringUtils.hasText(contentType)) {
            headers.setContentType(MediaType.parseMediaType(contentType));
        }

        try {
            HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);
            restTemplate.exchange(uploadUrl, HttpMethod.PUT, entity, Void.class);
        } catch (IOException exception) {
            throw new RuntimeException("staff Error");
        }

        return objectKey;
    }

    public PhotoData download(String objectKey) {
        String downloadUrl = buildObjectUrl(objectKey);
        ResponseEntity<byte[]> response = restTemplate.exchange(
                downloadUrl,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                byte[].class
        );

        byte[] body = response.getBody();
        if (body == null || body.length == 0) {
            throw new RuntimeException("staff Error");
        }

        MediaType contentType = response.getHeaders().getContentType();
        String mediaType = contentType != null ? contentType.toString() : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        return new PhotoData(body, mediaType);
    }

    public void delete(String objectKey) {
        if (!StringUtils.hasText(objectKey)) {
            return;
        }

        String deleteUrl = buildObjectUrl(objectKey);
        restTemplate.exchange(deleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    }

    private void validatePhoto(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("staff Error");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("staff Error");
        }

        String contentType = file.getContentType();
        if (!StringUtils.hasText(contentType) || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new RuntimeException("staff Error");
        }
    }

    private String resolveExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.hasText(originalFilename) && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase();
        }

        String contentType = file.getContentType();
        if ("image/png".equals(contentType)) {
            return ".png";
        }
        if ("image/gif".equals(contentType)) {
            return ".gif";
        }
        if ("image/webp".equals(contentType)) {
            return ".webp";
        }
        return ".jpg";
    }

    private String buildObjectUrl(String objectKey) {
        String endpoint = properties.getFiler().getEndpoint().replaceAll("/$", "");
        String bucket = properties.getFiler().getBucket();
        return endpoint + "/" + bucket + "/" + objectKey;
    }

    public record PhotoData(byte[] bytes, String contentType) {
    }
}
