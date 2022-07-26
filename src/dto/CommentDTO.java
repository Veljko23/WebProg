package dto;


import beans.Comment;
import beans.Post;
import beans.User;
import utils.DateHelper;

public class CommentDTO {
	private int id;
	private User user;
	private String text;
	private String commentDate;
	private String updateDate;
	private Post post;
	
	public CommentDTO() {}

	public CommentDTO(int id, User user, String text, String commentDate, String updateDate, Post post) {
		super();
		this.id = id;
		this.user = user;
		this.text = text;
		this.commentDate = commentDate;
		this.updateDate = updateDate;
		this.post = post;
	}

	public CommentDTO(Comment comment) {
		super();
		this.id = comment.getId();
		this.user = comment.getUser();
		this.text = comment.getText();
		this.commentDate = DateHelper.dateToString(comment.getCommentDate());
		this.updateDate = DateHelper.dateToString(comment.getUpdateDate());
		this.post = comment.getPost();
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

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
	
	
}
