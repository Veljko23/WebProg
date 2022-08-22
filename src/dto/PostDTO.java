package dto;

import java.util.ArrayList;

import beans.Comment;
import beans.Post;
import beans.User;

public class PostDTO {
	
	private int id;
	private String picture;
	private String text;
	//private ArrayList<Comment> comments;
	
	public PostDTO() {}

	public PostDTO(int id, String picture, String text, User user) {
		super();
		this.id = id;
		this.picture = picture;
		this.text = text;
	}

	public PostDTO(Post post) {
		super();
		this.id = post.getId();
		this.picture = post.getPicture();
		this.text = post.getText();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
