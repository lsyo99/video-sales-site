package org.ItBridge.domain.video.Controller;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.Common.api.Result;
import org.ItBridge.domain.video.Business.VideoBusiness;
import org.ItBridge.domain.video.Controller.model.VideoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@RequestMapping("/api/lectures/videos")
@RestController
@RequiredArgsConstructor
public class VideoController {
    private final VideoBusiness videoBusiness;



    @GetMapping("/{lectureId}")
    public Api<List<VideoResponse>> getVideos(@PathVariable Long lectureId) {
        var response = videoBusiness.getVideo(lectureId);

        // 저장된 절대 경로를 상대 경로로 변환
        response.forEach(video -> {
            video.setUrl("/savevideo/" + Paths.get(video.getUrl()).getFileName().toString());
        });

        return Api.ok(response);
    }



}
