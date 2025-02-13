package org.ItBridge.domain.video.Converter;

import lombok.AllArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.db.video.VideoEntity;
import org.ItBridge.domain.video.Controller.model.VideoResponse;

import java.util.List;
import java.util.stream.Collectors;

@Converter
@AllArgsConstructor
public class VideoConverter {

    public List<VideoResponse> toResponseList(List<VideoEntity> entityList){
        return entityList.stream()
                .map(entity -> VideoResponse.builder()
                        .id(entity.getId())
                        .url(entity.getUrl())
                        .sort_id(entity.getSortId())
                        .lecture_id(entity.getLecture().getId())
                        .title(entity.getTitle())
                        .duration(entity.getDuration())
                                .build())
                .collect(Collectors.toList());
    }
}
