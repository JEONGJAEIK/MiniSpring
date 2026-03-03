package com.createspring.board.repository;


import com.createspring.board.entity.Post;
import com.createspring.spring.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BoardRepository {
    Map<Long, Post> postMemory = new HashMap<>();

    public void save(Post p) {
        postMemory.put(p.getId(), p);
    }

    public Post findById(Long id) {
        return postMemory.get(id);
    }
}
