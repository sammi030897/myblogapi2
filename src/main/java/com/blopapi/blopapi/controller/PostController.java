package com.blopapi.blopapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blopapi.blopapi.payload.PostDto;
import com.blopapi.blopapi.service.PostService;



@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	private PostService postservice;
	
	public PostController(PostService postservice) {
		this.postservice=postservice;
	}
//	http://localhost:8080/api/posts
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		PostDto savedDto = postservice.createPost(postDto);
		return new ResponseEntity<>(savedDto,HttpStatus.CREATED);
	}
//	http://localhost:8080/api/posts/1
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
		PostDto dto=postservice.getPostById(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
//	http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title&sortDir=desc
	@GetMapping
	public List<PostDto> getAllPosts(
		@RequestParam(value="pageNo",defaultValue="0",required = false) int pageNo,
		@RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
		@RequestParam(value="sortBy",defaultValue="id",required=false) String sortBy,
		@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir){
		List<PostDto> postdtos=postservice.getAllPosts(pageNo, pageSize, sortBy,sortDir);
		return postdtos;
	}
//	http://localhost:8080/api/posts/1
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") long id){
		postservice.deletePost(id);
		return new ResponseEntity<>("post is deleted!!!",HttpStatus.OK);
	}
//	http://localhost:8080/api/posts/1
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id,@RequestBody PostDto postdto){
		PostDto dto = postservice.updatePost(id,postdto);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
}
