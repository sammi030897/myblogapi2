package com.blopapi.blopapi.service;

import java.util.List;

import com.blopapi.blopapi.payload.CommentDto;

public interface CommentService {
	CommentDto createcomment(long postid,CommentDto commentDto);
	
	List<CommentDto> findCommentByPostId(long postid);
	
	void deleteCommentById(long postid,long id);

	CommentDto getCommentById(long postid, long id);
	
	CommentDto updateComment(long postid,long id,CommentDto commentDto);
}
