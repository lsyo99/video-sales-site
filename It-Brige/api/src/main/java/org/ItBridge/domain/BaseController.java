package org.ItBridge.domain;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class BaseController {

    @GetMapping(value = "/savevideo/{filename}", produces = "video/mp4")
    public ResponseEntity<Resource> getVideo(@PathVariable String filename) throws IOException {
        // ✅ 확장자가 없는 경우 .mp4 추가
        if (!filename.endsWith(".mp4")) {
            filename += ".mp4";
        }

        // ✅ 실제 저장된 경로 반영
        Path videoPath = Paths.get("/Users/iseoyeong/Desktop/It-bridge/It-Brige/api/src/main/resources/static/savevideo/", filename);
        System.out.println("🔍 요청된 비디오 파일 경로: " + videoPath.toString());

        Resource resource = new UrlResource(videoPath.toUri());

        // 📌 파일 존재 여부 확인
        if (!resource.exists() || !resource.isReadable()) {
            System.out.println("❌ 파일을 찾을 수 없거나 읽을 수 없습니다.");
            return ResponseEntity.notFound().build();
        }

        System.out.println("✅ 파일이 존재합니다. 올바르게 반환됩니다.");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }


}
