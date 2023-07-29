package com.blopapi.blopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blopapi.blopapi.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
