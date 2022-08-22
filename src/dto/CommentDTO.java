package dto;


import beans.Comment;
import beans.Post;
import beans.User;
import utils.DateHelper;

public class CommentDTO {
	private int id;
	private String text;
	private String commentDate;
	private String updateDate;
	private String userName;
	private String userSurname;
	
	public CommentDTO() {}

	public CommentDTO(int id, User user, String text, String commentDate, String updateDate, Post post) {
		super();
		this.id = id;
		this.text = text;
		this.commentDate = commentDate;
		this.updateDate = updateDate;
	}

	public CommentDTO(Comment comment) {
		super();
		this.id = comment.getId();
		this.text = comment.getText();
		this.commentDate = DateHelper.dateToString(comment.getCommentDate());
		this.updateDate = DateHelper.dateToString(comment.getUpdateDate());
		this.userName = comment.getUser().getName();
		this.userSurname = comment.getUser().getSurname();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}
	

}
