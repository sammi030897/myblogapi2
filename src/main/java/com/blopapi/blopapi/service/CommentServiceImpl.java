package com.blopapi.blopapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.blopapi.blopapi.entity.Comment;
import com.blopapi.blopapi.entity.Post;
import com.blopapi.blopapi.exception.ResourceNotFoundException;
import com.blopapi.blopapi.payload.CommentDto;
import com.blopapi.blopapi.repository.CommentRepository;
import com.blopapi.blopapi.repository.PostRepository;
@Service
public class CommentServiceImpl implements CommentService {

	private PostRepository postrepo;
	
	private CommentRepository commentrepo;
	
	public CommentServiceImpl(PostRepository postrepo,CommentRepository commentrepo) {
		this.postrepo=postrepo;
		this.commentrepo=commentrepo;
	}
	
	@Override
	public CommentDto createcomment(long postid, CommentDto commentDto) {
		Post post = postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException(postid));
		
		Comment comment=new Comment();
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		comment.setPost(post);
		
		Comment savecomment = commentrepo.save(comment);
		
		CommentDto dto=new CommentDto();
		dto.setId(savecomment.getId());
		dto.setName(savecomment.getName());
		dto.setEmail(savecomment.getEmail());
		dto.setBody(savecomment.getBody());
		
			return dto;
	}

	@Override
	public List<CommentDto> findCommentByPostId(long postid) {
		
		Post post = postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException(postid));
		
		List<Comment> comments = commentrepo.findBypostId(postid);
		List<CommentDto> result = comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
		return result;
	}
	
	CommentDto mapToDto(Comment comment) {
	CommentDto dto=new CommentDto();
	dto.setId(comment.getId());
	dto.setName(comment.getName());
	dto.setEmail(comment.getEmail());
	dto.setBody(comment.getBody());
		return dto;
	}

	@Override
	public void deleteCommentById(long postid, long id) {
		Post post = postrepo.findById(postid).orElseThrow(()-> new ResourceNotFoundException(postid));
		
		Comment comment = commentrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
		commentrepo.deleteById(id);
	}

	@Override
	public CommentDto getCommentById(long postid, long id) {
		Post post = postrepo.findById(postid).orElseThrow(()-> new ResourceNotFoundException(postid));
		Comment comment = commentrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
		CommentDto commentdto = mapToDto(comment);
		return commentdto;
	}

	@Override
	public CommentDto updateComment(long postid, long id, CommentDto commentDto) {
		Post post=postrepo.findById(postid).orElseThrow(()-> new ResourceNotFoundException(postid));
		
		Comment comment = commentrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
		
			comment.setName(commentDto.getName());
			comment.setEmail(commentDto.getEmail());
			comment.setBody(commentDto.getBody());
			 
			Comment comments = commentrepo.save(comment);
		
			return mapToDto(comments);
	}

	

}
