package beans;

import java.time.LocalDate;

public class Comment {
	
	private int id;
	private User user;
	private String text;
	private LocalDate commentDate;
	private LocalDate updateDate;
	private Post post;
	
	public Comment() {
		
	}
	
	public Comment(int id, User user, String text, LocalDate commentDate, LocalDate updateDate, Post post) {
		super();
		this.id = id;
		this.user = user;
		this.text = text;
		this.commentDate = commentDate;
		this.updateDate = updateDate;
		this.post = post;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(LocalDate commentDate) {
		this.commentDate = commentDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	

}
