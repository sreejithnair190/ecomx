package me.sreejithnair.ecomx_api.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    private long maxSizeInBytes;
    private List<String> allowedTypes;
    private boolean required;

    @Override
    public void initialize(ValidImage annotation) {
        this.maxSizeInBytes = annotation.maxSizeInMB() * 1024 * 1024;
        this.allowedTypes = Arrays.asList(annotation.allowedTypes());
        this.required = annotation.required();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (file == null || file.isEmpty()) {
            if (required) {
                context.buildConstraintViolationWithTemplate("Image file is required")
                        .addConstraintViolation();
                return false;
            }
            return true;
        }

        // Check content type
        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType)) {
            context.buildConstraintViolationWithTemplate(
                    "Invalid file type. Allowed: " + String.join(", ", allowedTypes))
                    .addConstraintViolation();
            return false;
        }

        // Check file size
        if (file.getSize() > maxSizeInBytes) {
            context.buildConstraintViolationWithTemplate(
                    "File size exceeds maximum limit of " + (maxSizeInBytes / (1024 * 1024)) + "MB")
                    .addConstraintViolation();
            return false;
        }

        // Check file extension matches content type
        String filename = file.getOriginalFilename();
        if (filename != null && !hasValidExtension(filename, contentType)) {
            context.buildConstraintViolationWithTemplate("File extension doesn't match content type")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean hasValidExtension(String filename, String contentType) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (contentType) {
            case "image/jpeg" -> extension.equals("jpg") || extension.equals("jpeg");
            case "image/png" -> extension.equals("png");
            case "image/gif" -> extension.equals("gif");
            case "image/webp" -> extension.equals("webp");
            default -> false;
        };
    }
}
