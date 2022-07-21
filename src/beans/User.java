package beans;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Gender;
import enums.Role;
import utils.DateHelper;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	private LocalDate birdthDate;
	private Gender gender;
	private Role role;
	private String picture;
	private ArrayList<Post> posts;
	private ArrayList<String> pictures;
	private ArrayList<FriendshipRequest> requests;
	private ArrayList<User> friends;
	private boolean privateProfile;
	
	public User() {
		posts = new ArrayList<Post>();
		pictures = new ArrayList<String>();
		requests = new ArrayList<FriendshipRequest>();
		friends = new ArrayList<User>();
	}
	
	public User(int id) {
		this.id = id;
		posts = new ArrayList<Post>();
		pictures = new ArrayList<String>();
		requests = new ArrayList<FriendshipRequest>();
		friends = new ArrayList<User>();
	}

	public User(int id, String username, String password ,String email, String name, String surname, LocalDate birdthDate, Gender gender,
			Role role, String picture, ArrayList<Post> posts, ArrayList<String> pictures,
			ArrayList<FriendshipRequest> requests, ArrayList<User> friends, boolean privateProfile) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birdthDate = birdthDate;
		this.gender = gender;
		this.role = role;
		this.picture = picture;
		this.posts = posts;
		this.pictures = pictures;
		this.requests = requests;
		this.friends = friends;
		this.privateProfile = privateProfile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirdthDate() {
		return birdthDate;
	}

	public void setBirdthDate(LocalDate birdthDate) {
		this.birdthDate = birdthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	public ArrayList<FriendshipRequest> getRequests() {
		return requests;
	}

	public void setRequests(ArrayList<FriendshipRequest> requests) {
		this.requests = requests;
	}

	public ArrayList<User> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}

	public boolean isPrivateProfile() {
		return privateProfile;
	}

	public void setPrivateProfile(boolean privateProfile) {
		this.privateProfile = privateProfile;
	}
	
	public String fileLine() {
		return id + ";" + username + ";" + password + ";" + email + ";" + name + ";" + surname + ";"
				+ DateHelper.dateToString(birdthDate) + ";" + gender.ordinal() + ";" + role.ordinal() + ";" + picture
				+ ";" + privateProfile;
	}
	
}
