package com.blopapi.blopapi.service;

import java.util.List;

import com.blopapi.blopapi.payload.PostDto;

public interface PostService {
	public PostDto createPost(PostDto postDto);


	public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


	public void deletePost(long id);

	public PostDto updatePost(long id, PostDto postdto);


	PostDto getPostById(long id);
}
