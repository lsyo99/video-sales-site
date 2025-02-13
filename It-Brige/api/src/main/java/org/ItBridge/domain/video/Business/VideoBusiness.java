package org.ItBridge.domain.video.Business;

import lombok.AllArgsConstructor;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.domain.video.Controller.model.VideoResponse;
import org.ItBridge.domain.video.Converter.VideoConverter;
import org.ItBridge.domain.video.Service.VideoService;

import java.util.List;

@Business
@AllArgsConstructor
public class VideoBusiness {
    private final VideoService videoService;
    private final VideoConverter videoConverter;

    public List<VideoResponse> getVideo(Long lectureId) {
        var entity = videoService.getVideo(lectureId);
        return videoConverter.toResponseList(entity);
    }
}
