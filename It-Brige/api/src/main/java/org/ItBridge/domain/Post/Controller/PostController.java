package org.ItBridge.domain.Post.Controller;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.domain.Post.Business.PostBusiness;
import org.ItBridge.domain.Post.Controller.Model.PostCreateRequest;
import org.ItBridge.domain.Post.Controller.Model.PostDetailResponse;
import org.ItBridge.domain.Post.Controller.Model.PostRequest;
import org.ItBridge.domain.Post.Controller.Model.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/posts")
public class PostController {
    private final PostBusiness postBusiness;
    //전체 글 가져오기
    @GetMapping("/board/{boardId}")
    public Api<Page<PostResponse>> getAllPostsByBoardId(
            @PathVariable Long boardId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PostResponse> response = postBusiness.getAllPostsByBoardId(boardId, pageable);
        return Api.ok(response);
    }


    //계시글 생성
    @PostMapping("/create/body")
    public Api<PostResponse> createPost(
            @RequestBody PostCreateRequest postCreateRequest
            ) {
        var response = postBusiness.createPost(postCreateRequest);
        return Api.ok(response);
    }
    @PostMapping("/create/{postId}/files")
    public Api<String> uploadFiles(
            @PathVariable Long postId,
            @RequestParam("files") List<MultipartFile> files
    ){
            postBusiness.uploadFile(postId,files);
            return Api.ok(null);
    }

//    계시글 상세보기 feat 댓글
    @GetMapping("/{postId}")
    public Api<PostDetailResponse> detailPost(
            @PathVariable Long postId
    ){
        var response = postBusiness.getPostDetails(postId);
        return Api.ok(response);
    }

}
