package com.createspring.board.service;


import com.createspring.board.dto.PostDTO;
import com.createspring.board.entity.Post;
import com.createspring.board.event.BoardEventPublisher;
import com.createspring.board.event.BoardEventPublisher2;
import com.createspring.board.repository.BoardRepository;
import com.createspring.spring.annotation.Service;

@Service
public class PostService {
    // TODO 현재는 옵저버 패턴만을 적용하여 발행자의 중복이 있다.
    //  스프링 이벤트리스너를 직접구현하고 발행자 중복을 없애보자
    private final BoardRepository boardRepository;
    private final BoardEventPublisher boardEventPublisher;
    private final BoardEventPublisher2 boardEventPublisher2;

    public PostService(BoardRepository boardRepository, BoardEventPublisher boardEventPublisher, BoardEventPublisher2 boardEventPublisher2) {
        this.boardRepository = boardRepository;
        this.boardEventPublisher = boardEventPublisher;
        this.boardEventPublisher2 = boardEventPublisher2;
    }

    /**
     * 게시글작성
     */
    public void createPost(PostDTO dto) {
        Post post = new Post(dto.getId(), dto.getTitle(), dto.getContent(), dto.getAuthor());
        boardRepository.save(post);
        boardEventPublisher.publishEvent();
    }

    /**
     * 글 조회
     */
    public Post getPost(Long id) {
        Post post = boardRepository.findById(id);
        boardEventPublisher2.publishEvent();
        return post;
    }
}
