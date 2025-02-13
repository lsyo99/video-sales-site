package org.ItBridge.domain.LectureDetail.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FIleStorageService {
    private static final String LECTURE_DETAIL_PATH = "/Users/iseoyeong/Desktop/It-bridge/It-Brige/api/src/main/resources/static/image/back1";
    private static final String LECTURE_THUMBNAIL_PATH = "/Users/iseoyeong/Desktop/It-bridge/It-Brige/api/src/main/resources/static/image/lectureThumbnail";
    private static final String LECTURE_VIDEO_PATH = "/Users/iseoyeong/Desktop/It-bridge/It-Brige/api/src/main/resources/static/savevideo";

    // 파일 저장 메서드
    public String saveFile(MultipartFile file, String type) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path targetLocation;

        // 요청한 폴더에 맞게 저장 경로 선택
        switch (type) {
            case "details":
                targetLocation = Paths.get(LECTURE_DETAIL_PATH).resolve(fileName);
                break;
            case "thumbnails":
                targetLocation = Paths.get(LECTURE_THUMBNAIL_PATH).resolve(fileName);
                break;
            case "videos":
                targetLocation = Paths.get(LECTURE_VIDEO_PATH).resolve(fileName);
                break;
            default:
                throw new IllegalArgumentException("잘못된 파일 유형: " + type);
        }

        // 디렉토리가 없으면 생성
        Files.createDirectories(targetLocation.getParent());

        // 파일 저장
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // 저장된 파일의 URL 반환 (프론트에서 접근 가능하도록 설정)
        return "/static/" + type + "/" + fileName;
    }
}
