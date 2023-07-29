package com.blopapi.blopapi.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDto {
	private long id;

	@NotEmpty
	@Size(min = 2,message = "title should be at least 2 character")
	private String title;
	
	@NotEmpty(message = "enter the character now")
	@Size(min = 4)
	private String description;
	
	@NotEmpty(message="please fill the character")
	private String content;
}
