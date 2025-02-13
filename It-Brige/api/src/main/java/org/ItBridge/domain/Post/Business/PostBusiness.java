package org.ItBridge.domain.Post.Business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.db.Post.PostEntity;
import org.ItBridge.db.borad.BoardRepository;
import org.ItBridge.db.user.UserRepository;
import org.ItBridge.domain.Comment.Business.CommentBusiness;
import org.ItBridge.domain.Comment.Service.CommentService;
import org.ItBridge.domain.Post.Controller.Model.PostCreateRequest;
import org.ItBridge.domain.Post.Controller.Model.PostDetailResponse;
import org.ItBridge.domain.Post.Controller.Model.PostRequest;
import org.ItBridge.domain.Post.Controller.Model.PostResponse;
import org.ItBridge.domain.Post.Converter.PostConverter;
import org.ItBridge.domain.Post.Service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
@Slf4j
public class PostBusiness {
    private final PostService postService;
    private final PostConverter postConverter;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentService commentService;
    private final CommentBusiness commentBusiness;


    public Page<PostResponse> getAllPostsByBoardId(Long boardId, Pageable pageable) {
        Page<PostEntity> postEntities = postService.getAllPostsByBoardById(boardId, pageable);

        // Page<PostEntity> → Page<PostResponse> 변환
        List<PostResponse> postResponses = postEntities.getContent().stream()
                .map(postConverter::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, postEntities.getTotalElements());
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest postCreateRequest) {
//        var userId = userRepository.findById(request.getUser_id()).orElseThrow(()->new IllegalArgumentException("user gone"));
//        var boardId = boardRepository.findById(request.getBoard_id()).orElseThrow(()->new IllegalArgumentException("board gone"));
//        var post = postConverter.toEntity(request, userId,boardId);
//        postService.createPost(post);
        var userId = userRepository.findById(postCreateRequest.getUser_id()).orElseThrow(()-> new IllegalArgumentException("user gone"));
        log.info("생성userid:{}",userId);
        var boardId = boardRepository.findById(postCreateRequest.getBoard_id()).orElseThrow(()-> new IllegalArgumentException("user gone"));
        log.info("생성boardId:{}",boardId);
        var postEntity = postConverter.toEntity(postCreateRequest, userId, boardId);
        var getPostEntity = postService.createPost(postEntity);
        return postConverter.toResponse(getPostEntity);

    }


    public PostDetailResponse getPostDetails(Long postId) {
        var post = postService.getPostWithUser(postId);
        var comments = commentService.getCommentByPostId(postId);
        return postConverter.toDetailResponse(post, comments);

    }


    public void uploadFile(Long postId, List<MultipartFile> files) {
        var postEntity = postService.getPost(postId);
        var filesEntity = postConverter.toListFileEntity(postEntity,files);
        postService.saveFile(filesEntity);


    }
}
