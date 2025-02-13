package org.ItBridge.domain.Lecture.Business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.db.LectureDetail.LectureDetailEntity;
import org.ItBridge.db.video.VideoEntity;
import org.ItBridge.domain.Lecture.Controller.model.LectureRequest;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.ItBridge.domain.Lecture.Controller.model.LectureSaveReqeust;
import org.ItBridge.domain.Lecture.Converter.LectureConverter;
import org.ItBridge.domain.Lecture.Service.LectureService;
import org.ItBridge.domain.LectureDetail.Service.FIleStorageService;
import org.ItBridge.domain.LectureDetail.Service.LectureDetailService;
import org.ItBridge.domain.video.Service.VideoService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
@Slf4j
public class LectureBusiness {
    private final LectureConverter lectureConverter;
    private final LectureService lectureService;
    private final FIleStorageService fIleStorageService;
    private final LectureDetailService lectureDetailService;
    private final VideoService videoService;


    public List<LectureResponse> getBestLectures(String category) {
        // 상위 5개 강의 데이터를 가져와 변환
        var listEntity = lectureService.getTop5Lecture(category);
        return listEntity.stream()
                .map(lectureConverter::toResponse)
                .collect(Collectors.toList());
    }

    public List<LectureResponse> searchCourses(String keyword) {
        var lectureEntities = lectureService.searchCourses(keyword);
        return lectureEntities.stream()
                .map(entity -> lectureConverter.toResponse(entity)) //람다식 풀어서 쓴 것
                .collect(Collectors.toList());

    }

    public List<LectureResponse> getNewLectures(String category) {
        var listEntity = lectureService.getNewLecture(category);
        return listEntity.stream()
                .map(entity -> lectureConverter.toResponse(entity))
                .collect(Collectors.toList());
    }

    public List<LectureResponse> getHeaderLecture(String type) {
        var listEntity = lectureService.getLectureHeader(type);
        return listEntity.stream()
                .map(entity -> lectureConverter.toResponse(entity))
                .collect(Collectors.toList());
    }

    public LectureResponse getLecture(Long lectureId) {
        var entity = lectureService.getLecture(lectureId);
        var response = lectureConverter.toResponse(entity);
        return response;
    }

    @Transactional
    public LectureResponse saveLecture(LectureSaveReqeust lectureSaveReqeust, MultipartFile thumbnail, List<MultipartFile> detailImages, List<MultipartFile> lectureVideos) throws IOException {
        String thumbnailUrl = fIleStorageService.saveFile(thumbnail, "thumbnails");

        var lecture = lectureConverter.tosvaeEntity(thumbnailUrl, lectureSaveReqeust);
        var lectureEntitysave = lectureService.saveLEcture(lecture);

        int sortIndex = 1;
        for (MultipartFile image : detailImages) {
            String imageUrl = fIleStorageService.saveFile(image, "details");

            LectureDetailEntity lectureDetail = LectureDetailEntity.builder()
                    .lecture(lecture)
                    .url(imageUrl)
                    .sortImg(sortIndex++)
                    .created_at(LocalDateTime.now())
                    .updated_at(LocalDateTime.now())
                    .build();
            lectureDetailService.save(lectureDetail);
        }
        int videoSortIndex = 1;
        for (MultipartFile video : lectureVideos) {
            String videoUrl = fIleStorageService.saveFile(video, "videos");

            VideoEntity videoEntity = VideoEntity.builder()
                    .lecture(lecture)
                    .url(videoUrl)
                    .sortId(videoSortIndex++)
                    .title("강의 영상 " + videoSortIndex)  // 기본 제목 설정
                    .duration("00:00") // 추후 업데이트 필요
                    .build();

            videoService.save(videoEntity);


        }
        return lectureConverter.toResponse(lecture);
    }
}
