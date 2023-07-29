package com.blopapi.blopapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blopapi.blopapi.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findBypostId(long id);
}
