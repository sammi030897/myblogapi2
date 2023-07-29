package com.blopapi.blopapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blopapi.blopapi.entity.Post;
import com.blopapi.blopapi.exception.ResourceNotFoundException;
import com.blopapi.blopapi.payload.PostDto;
import com.blopapi.blopapi.repository.PostRepository;
@Service
public class PostServiceimpl implements PostService {

	private PostRepository postrepo;
	
	private ModelMapper modelmapper;
	
	public PostServiceimpl(PostRepository postrepo,ModelMapper modelmapper) {
		this.postrepo=postrepo;
		this.modelmapper=modelmapper;
	}
	
	@Override
	public PostDto createPost(PostDto postDto) {
//		Post post= new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
//		Post savedPost = postrepo.save(post);
		
		Post post = mapToPost(postDto);
		
		Post savedPost = postrepo.save(post);
		
//		PostDto dto=new PostDto();
//		dto.setId(savedPost.getId());
//		dto.setTitle(savedPost.getTitle());
//		dto.setDescription(savedPost.getDescription());
//		dto.setContent(savedPost.getContent());
		
		PostDto dto = mapToPostDto(savedPost);
		
		return dto;
	}
	@Override
	public PostDto getPostById(long id) {
		Post post= postrepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id));
//		PostDto dto=new PostDto();
//		dto.setId(post.getId());
//		dto.setTitle(post.getTitle());
//		dto.setDescription(post.getDescription());
//		dto.setContent(post.getContent());
		PostDto dto = mapToPostDto(post);
		return dto;
	}

	PostDto mapToPostDto(Post post) {
		PostDto dto = modelmapper.map(post,PostDto.class);
//		PostDto dto=new PostDto();
//		dto.setId(post.getId());
//		dto.setTitle(post.getTitle());
//		dto.setDescription(post.getDescription());
//		dto.setContent(post.getContent());
		return dto;
	}
	Post mapToPost(PostDto postDto) {
		Post post = modelmapper.map(postDto, Post.class);
//		Post post=new Post();
//		post.setId(postDto.getId());
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
	}

	@Override
	public void deletePost(long id) {
		Post post = postrepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id));
		postrepo.deleteById(id);
	}

	@Override
	public PostDto updatePost(long id, PostDto postdto) {
		Post post = postrepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id));
		Post updateContent = mapToPost(postdto);
		updateContent.setId(post.getId());
		Post updatePostInfo = postrepo.save(updateContent);
		 return mapToPostDto(updatePostInfo);
	}

	@Override
	public List<PostDto> getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		Page<Post> posts = postrepo.findAll(pageable);
		List<Post> content = posts.getContent();
		List<PostDto> postsDto = content.stream().map(post->mapToPostDto(post)).collect(Collectors.toList());
		return postsDto;
	}
}
