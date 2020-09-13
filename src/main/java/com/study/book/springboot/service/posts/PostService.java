package com.study.book.springboot.service.posts;

import com.study.book.springboot.domain.posts.Posts;
import com.study.book.springboot.domain.posts.PostsRepository;
import com.study.book.springboot.web.dto.PostsListResponseDto;
import com.study.book.springboot.web.dto.PostsResponseDto;
import com.study.book.springboot.web.dto.PostsSaveRequestDto;
import com.study.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostsRepository repository;

    @Transactional
    public Long save(PostsSaveRequestDto dto){
        return repository.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto dto) {
        Posts posts = repository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        posts.update(dto.getTitle(), dto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return repository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long deleteById(Long id){
        repository.deleteById(id);
        return id;
    }
}
