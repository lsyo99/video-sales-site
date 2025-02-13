package org.ItBridge.domain.video.Service;

import lombok.AllArgsConstructor;
import org.ItBridge.db.video.VideoEntity;
import org.ItBridge.db.video.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public List<VideoEntity> getVideo(Long lectureId) {
        return videoRepository.findAllByLectureIdOrderBySortId(lectureId);
    }

    public VideoEntity save(VideoEntity videoEntity) {
        return videoRepository.save(videoEntity);
    }
}
