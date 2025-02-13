package org.ItBridge;

import org.ItBridge.Interceptor.AuthorizationInterceptor;
import org.ItBridge.domain.Comment.Business.CommentBusiness;
import org.ItBridge.domain.Lecture.Business.LectureBusiness;
import org.ItBridge.domain.LectureDetail.Business.LectureDetailBusiness;
import org.ItBridge.domain.MyPage.Business.MyPageBusiness;
import org.ItBridge.domain.Post.Business.PostBusiness;
import org.ItBridge.domain.User.Business.UserBusiness;
import org.ItBridge.domain.payment.Business.PaymentBusiness;
import org.ItBridge.domain.token.Business.TokenBusiness;
import org.ItBridge.domain.token.Service.TokenService;
import org.ItBridge.domain.video.Business.VideoBusiness;
import org.ItBridge.domain.User.Controller.UserOpenApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class WebConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // TokenBusiness를 Mock으로 등록
    private TokenBusiness tokenBusiness;

    @MockBean // AuthorizationInterceptor도 Mock 처리
    private AuthorizationInterceptor authorizationInterceptor;

    @MockBean
    private CommentBusiness commentBusiness;

    @MockBean
    private LectureDetailBusiness lectureDetailBusiness;

    @MockBean
    private LectureBusiness lectureBusiness;

    @MockBean
    private MyPageBusiness myPageBusiness;

    @MockBean
    private PostBusiness postBusiness;

    @MockBean
    private VideoBusiness videoBusiness;

    @MockBean
    private UserBusiness userBusiness;

    @MockBean
    private PaymentBusiness paymentBusiness;
    @MockBean
    private TokenService tokenService;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void testInterceptorForAdminAccess() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isForbidden()); // 인증 없이 접근하면 403 응답
    }
}
