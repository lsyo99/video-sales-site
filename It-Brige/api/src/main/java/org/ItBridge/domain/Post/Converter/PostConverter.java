package org.ItBridge.domain.Post.Converter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.Common.api.Api;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.Comment.CommentEntity;
import org.ItBridge.db.Post.PostEntity;
import org.ItBridge.db.borad.BoardEntity;
import org.ItBridge.db.files.FileEntity;
import org.ItBridge.db.user.UserEntity;
import org.ItBridge.domain.Comment.Controller.Model.CommentResponse;
import org.ItBridge.domain.Post.Controller.Model.PostCreateRequest;
import org.ItBridge.domain.Post.Controller.Model.PostDetailResponse;
import org.ItBridge.domain.Post.Controller.Model.PostRequest;
import org.ItBridge.domain.Post.Controller.Model.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Converter
@Slf4j
@AllArgsConstructor
public class PostConverter {
    public PostResponse toResponse(PostEntity postEntity) {
        return Optional.ofNullable(postEntity).map(
                it -> {
                    return PostResponse.builder()
                            .id(it.getId())
                            .body(it.getTitle())
                            .title(it.getTitle())
                            .Board_id(it.getBoard().getId())
                            .user_id(it.getUser().getId())
                            .user_name(it.getUser().getName())
                            .created_at(it.getCreatedAt())
                            .build();
                }
        ).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"post is null"));
    }
    public PostEntity toEntity(PostCreateRequest postCreateRequest, UserEntity userEntity, BoardEntity boardEntity){
        return PostEntity.builder()
                .title(postCreateRequest.getTitle())
                .body(postCreateRequest.getBody())
                .user(userEntity)
                .board(boardEntity)
                .createdAt(LocalDateTime.now())
                .build();
    }
    public PostDetailResponse toDetailResponse(PostEntity post, List<CommentEntity> comments) {
        return PostDetailResponse.builder()
                .post_id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .user_id(post.getUser().getId())
                .createdAt(post.getCreatedAt())
                .user_name(post.getUser().getName())
                .comments(comments.stream().map(this::toCommentResponse).collect(Collectors.toList()))
                .build();
    }
    private CommentResponse toCommentResponse(CommentEntity comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .post_id(comment.getPost().getId())
                .user_id(comment.getUser().getId())
                .user_name(comment.getUser().getName())
                .body(comment.getBody())
                .created_at(comment.getCreatedAt())
                .build();
    }
    public List<FileEntity> toListFileEntity(PostEntity post, List<MultipartFile> files) {
        if (files != null && !files.isEmpty()) {
            List<FileEntity> fileEntities = files.stream().map(file -> {
                        try {
                            // 파일 저장 메서드 호출
                            String fileUrl = saveFileToStorage(file);

                            // FileEntity 생성
                            return FileEntity.builder()
                                    .file_name(file.getOriginalFilename()) // 파일 이름
                                    .file_url(fileUrl) // 파일 URL
                                    .post(post) // 해당 게시글과 연관
                                    .created_at(LocalDateTime.now()) // 생성 시간
                                    .build();
                        } catch (IOException e) {
                            // 파일 저장 실패 시 로그 출력
                            System.err.println("파일 저장 실패: " + file.getOriginalFilename());
                            e.printStackTrace();
                            return null; // 저장 실패 시 null 반환
                        }
                    }).filter(Objects::nonNull) // null 값 필터링
                    .collect(Collectors.toList());
            return fileEntities;
        }
        return Collections.emptyList(); // 파일이 없을 경우 빈 리스트 반환
    }

    private String saveFileToStorage(MultipartFile file) throws IOException {
        // 절대 경로 설정
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/savefile";
        File directory = new File(uploadDir);

        // 디렉토리가 없으면 생성
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (!isCreated) {
                throw new IOException("파일 저장 디렉토리 생성 실패: " + uploadDir);
            }
        }

        // 고유 파일 이름 생성 (중복 방지)
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File targetFile = new File(directory, uniqueFileName);

        // 파일 저장
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new IOException("파일 저장 중 오류 발생: " + targetFile.getAbsolutePath(), e);
        }

        // 저장된 파일 경로 반환
        return targetFile.getAbsolutePath();
    }

}
