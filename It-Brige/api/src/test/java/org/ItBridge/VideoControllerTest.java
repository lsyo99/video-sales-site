//package org.ItBridge;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.ItBridge.db.video.VideoEntity;
//import org.ItBridge.db.video.VideoRepository;
//import org.ItBridge.domain.video.Business.VideoBusiness;
//import org.ItBridge.domain.video.Controller.VideoController;
//import org.ItBridge.domain.video.Controller.model.VideoResponse;
//import org.ItBridge.domain.video.Service.VideoService;
////import org.ItBridge.db.Video.VideoEntity;
////import org.ItBridge.db.Video.VideoRepository;
//import org.ItBridge.Interceptor.AuthorizationInterceptor;
//import org.ItBridge.domain.token.Business.TokenBusiness;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(VideoController.class)
//public class VideoControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper; // JSON 변환을 위한 ObjectMapper
//
//    @MockBean
//    private VideoBusiness videoBusiness;
//
//    @MockBean
//    private VideoService videoService;
//
//    @MockBean
//    private VideoRepository videoRepository;
//
//    @MockBean
//    private TokenBusiness tokenBusiness;
//
//    @MockBean
//    private AuthorizationInterceptor authorizationInterceptor;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        // AuthorizationInterceptor가 항상 true를 반환하도록 설정 -> 에러 발생하기에 추가
//        when(authorizationInterceptor.preHandle(any(), any(), any())).thenReturn(true);
//    }
//
//
//    /**
//     * GET 요청: 강의 비디오 리스트 조회 테스트
//     */
//    @Test
//    @WithMockUser(username = "testUser", roles = {"USER"})
//    public void testGetVideos() throws Exception {
//        List<VideoResponse> mockVideos = List.of(
//                VideoResponse.builder().id(1L).url("http://test.com/video1.mp4").title("강의1").build(),
//                VideoResponse.builder().id(2L).url("http://test.com/video2.mp4").title("강의2").build()
//        );
//
//        when(videoBusiness.getVideo(anyLong())).thenReturn(mockVideos);
//
//        mockMvc.perform(get("/api/lectures/videos/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.body[0].id").value(1))
//                .andExpect(jsonPath("$.body[0].title").value("강의1"));
//    }
//
//}
//
//
////package org.ItBridge;
////
////import com.fasterxml.jackson.databind.ObjectMapper;
////import org.ItBridge.domain.video.Business.VideoBusiness;
////import org.ItBridge.domain.video.Controller.VideoController;
////import org.ItBridge.domain.video.Controller.model.VideoResponse;
////import org.ItBridge.Interceptor.AuthorizationInterceptor;
////import org.ItBridge.domain.token.Business.TokenBusiness;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
////import org.springframework.boot.test.mock.mockito.MockBean;
////import org.springframework.http.MediaType;
////import org.springframework.security.test.context.support.WithMockUser;
////import org.springframework.test.web.servlet.MockMvc;
////
////import java.util.List;
////
////import static org.mockito.ArgumentMatchers.any;
////import static org.mockito.ArgumentMatchers.anyLong;
////import static org.mockito.Mockito.when;
////import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
////import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
////
////@WebMvcTest(VideoController.class)
////public class VideoControllerTest {
////
////    @Autowired
////    private MockMvc mockMvc;
////
////    @Autowired
////    private ObjectMapper objectMapper; // JSON 변환을 위한 ObjectMapper
////
////    @MockBean
////    private VideoBusiness videoBusiness;
////
////    @MockBean
////    private TokenBusiness tokenBusiness;
////
////    @MockBean
////    private AuthorizationInterceptor authorizationInterceptor;
////
////    @BeforeEach
////    void setUp() throws Exception {
////        // ✅ AuthorizationInterceptor가 항상 true를 반환하도록 설정
////        when(authorizationInterceptor.preHandle(any(), any(), any())).thenReturn(true);
////    }
////
////    /**
////     * GET 요청: 강의 비디오 리스트 조회 테스트
////     */
////    @Test
////    @WithMockUser(username = "testUser", roles = {"ADMIN"})
////    public void testGetVideos() throws Exception {
////        List<VideoResponse> mockVideos = List.of(
////                VideoResponse.builder().id(1L).url("http://test.com/video1.mp4").title("강의1").build(),
////                VideoResponse.builder().id(2L).url("http://test.com/video2.mp4").title("강의2").build()
////        );
////
////        when(videoBusiness.getVideo(anyLong())).thenReturn(mockVideos);
////
////        mockMvc.perform(get("/api/lectures/videos/1").with(csrf())) // ✅ CSRF 포함
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.body[0].id").value(1))
////                .andExpect(jsonPath("$.body[0].title").value("강의1"));
////    }
////}
