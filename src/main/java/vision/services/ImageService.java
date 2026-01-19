package vision.services;

import jakarta.servlet.http.Part;
import vision.config.AppConfig;
import vision.exceptions.FileStorageException;
import vision.exceptions.ValidationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageService {

    private final String uploadDir = AppConfig.UPLOAD_DIR;

    public ImageService() {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
    }

    public String saveImage(Part image) throws ValidationException, FileStorageException {
        if (image == null || image.getSize() == 0) {
            throw new FileStorageException("Выберите изображение");
        }
        String fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString().toLowerCase();
        String newFileName = UUID.randomUUID() + "_" + fileName;

        File file = new File(uploadDir, newFileName);

        try (InputStream is = image.getInputStream()) {
            Files.copy(is, file.toPath());
        } catch (IOException e) {
            throw new FileStorageException("Ошибка при сохранении изображения", e);
        }

        return newFileName;
    }

    public String saveImageForUpdate(Part imagePart, String imagePath) throws FileStorageException {
        if (imagePart != null && imagePart.getSize() > 0) {
            return saveImage(imagePart);
        }
        return imagePath;
    }

    public void deleteImage(String fileName) {
        if (fileName == null || fileName.isBlank()) return;
        File file = new File(uploadDir, fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
