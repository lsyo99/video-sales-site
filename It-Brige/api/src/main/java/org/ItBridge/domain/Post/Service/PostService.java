package org.ItBridge.domain.Post.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.Post.PostEntity;
import org.ItBridge.db.Post.PostRepository;
import org.ItBridge.db.files.FileEntity;
import org.ItBridge.db.files.FileRepository;
import org.ItBridge.domain.Post.Controller.Model.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final FileRepository fileRepository;
    public Page<PostEntity> getAllPostsByBoardById(Long boardId, Pageable pageable) {
        return postRepository.findByBoard_Id(boardId, pageable);
    }

    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity); // 저장 후 반환
    }
    public PostEntity getPostWithUser(Long postId) {
        return postRepository.findPostWithUser(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }
    public PostEntity getPost(Long postId){
        return postRepository.findById(postId).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"계시글을 불러올 수 없음"));
    }

    public void saveFile(List<FileEntity> filesEntity) {
        fileRepository.saveAll(filesEntity);
    }
}
