package org.ItBridge.domain.Lecture.Controller.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureSaveReqeust {
    private String title;
    private String category;
    private BigDecimal price;
    private Long sales;
    private List<String> tags;

    public String getTagsAsJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(tags); // JSON 문자열로 변환
        } catch (JsonProcessingException e) {
            return "[]"; // 변환 실패 시 빈 배열 반환
        }
    }
}
