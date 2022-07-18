package beans;

import java.util.ArrayList;

public class Post {
	private int id;
	private String picture;
	private String text;
	private ArrayList<Comment> comments;
	private User user;
	
	public Post() {
		comments = new ArrayList<Comment>();
	}
	
	public Post(int id) {
		this.id = id;
		comments = new ArrayList<Comment>();
	}
	public Post(int id, String picture, String text, ArrayList<Comment> comments, User user) {
		super();
		this.id = id;
		this.picture = picture;
		this.text = text;
		this.comments = comments;
		this.user = user;
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

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
