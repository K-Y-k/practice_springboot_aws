package com.kyk.book.springboot.service;

import com.kyk.book.springboot.domain.posts.Posts;
import com.kyk.book.springboot.domain.posts.PostsRepository;
import com.kyk.book.springboot.web.dto.PostsListResponseDto;
import com.kyk.book.springboot.web.dto.PostsResponseDto;
import com.kyk.book.springboot.web.dto.PostsSaveRequestDto;
import com.kyk.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    /**
     * 글 저장 기능
     */
    public Long save(PostsSaveRequestDto requestDto) {
        Posts posts = requestDto.toEntity();
        return postsRepository.save(posts).getId();
    }


    /**
     * 글 수정 기능
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // 업데이터 쿼리를 날리는 부분이 없는데
        // 그 이유는 JPA의 영속성 컨텍스트 때문이다.

        // 영속성 컨텍스트란 엔티티를 영구 저장하는 환경인데,
        // 트랜잭션 안에서 데이터베이스에 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태가 된다.

        // 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경된 것을 반영한다.
        // 즉, 엔티티 객체의 값만 변경하면 별도의 update 쿼리가 필요 없다.
        // 이 개념이 더티 체킹(변경 감지)
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /**
     * 글 일부 조회 기능
     */
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }


    /**
     * 글 전체 조회 기능
     */
    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도를 개선한다. 즉, 조회 기능만 있으면 이 방식이 좋다.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()// postsRepository 결과로 넘어온 Posts객체를
                .map(PostsListResponseDto::new)      // Stream으로 map을 통해 PostsListResponseDto로 변환하고
                .collect(Collectors.toList());       // List로 반환한다.
    }


    /**
     * 글 삭제 기능
     */
    @Transactional
    public void delete(Long id) {
        Posts findPost = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(findPost);
    }
}
