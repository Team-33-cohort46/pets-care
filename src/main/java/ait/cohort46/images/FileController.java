package ait.cohort46.images;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("folder") String folder) {
        String fileUrl = s3Service.uploadFile(file, folder);
        return ResponseEntity.ok(fileUrl);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<URL> getFileUrl(@PathVariable String fileName) {
        URL fileUrl = s3Service.getFileUrl(fileName);
        return ResponseEntity.ok(fileUrl);
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        s3Service.deleteFile(fileName);
        return ResponseEntity.ok("File deleted successfully");
    }
}