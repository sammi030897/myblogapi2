package com.blopapi.blopapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blopapi.blopapi.payload.CommentDto;
import com.blopapi.blopapi.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	private CommentService commentservice;

	public CommentController(CommentService commentservice) {
		this.commentservice = commentservice;
	}
//	http://localhost:8080/api/posts/1/comments
	@PostMapping("/posts/{postid}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="postid") long postid,
		@RequestBody CommentDto commentdto){
		
		CommentDto dto = commentservice.createcomment(postid,commentdto);
		return new ResponseEntity<>(dto,HttpStatus.CREATED); 
	}
	
	@GetMapping("/posts/{postid}/comments")
	public List<CommentDto> findCommentByPostId(@PathVariable(value="postid") long postid){
		List<CommentDto> dtos = commentservice.findCommentByPostId(postid);
		return dtos;
		
	}
//	http://localhost:8080/api/posts/1/comments/1
	@DeleteMapping("/posts/{postid}/comments/{id}")
	public ResponseEntity<String> deleteCommentById(@PathVariable(value="postid") long postid,@PathVariable (value="id") long id){
		commentservice.deleteCommentById(postid,id);
		return new ResponseEntity<>("data is deleted",HttpStatus.OK);
	}
//	http://localhost:8080/api/posts/1/comments/1
	@GetMapping("/posts/{postid}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value="postid") long postid,@PathVariable(value="id") long id){
		CommentDto dto=commentservice.getCommentById(postid,id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
//	http://localhost:8080/api/posts/1/comments/1	
	@PutMapping("/posts/{postid}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postid") long postid,
													@PathVariable(value="id")	long id,
													@RequestBody CommentDto commentDto){
		CommentDto updateComment = commentservice.updateComment(postid, id, commentDto);
		return new ResponseEntity<>(updateComment,HttpStatus.OK);
	}
}