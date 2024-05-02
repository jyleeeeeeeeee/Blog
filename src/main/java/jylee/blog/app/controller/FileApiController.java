package jylee.blog.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/tui-editor")
public class FileApiController {
    // 파일 업로드 디렉터리 경로
    @Value("${uploadDir}")
    private String uploadDir;
//    private final String uploadDir = Paths.get("D:", "blog", "upload").toString();

    /**
     * @param image 파일 객체
     * @return 업로드 파일명
     */
    @PostMapping("/image-upload")
    public String uploadEditorImage(@RequestParam MultipartFile image) {
        if (image.isEmpty()) {
            return "";
        }

        String originalFilename = image.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String savedFilename = uuid + "." + extension;
        String fileFullPath = Paths.get(uploadDir, savedFilename).toString();

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile);
            return savedFilename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/image-print", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] printEditorImage(@RequestParam String filename) {
        String fileFullPath = Paths.get(uploadDir, filename).toString();

        File uploadedFile = new File(fileFullPath);

        if (!uploadedFile.exists()) {
            throw new RuntimeException();
        }

        try {
            return Files.readAllBytes(uploadedFile.toPath());
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
