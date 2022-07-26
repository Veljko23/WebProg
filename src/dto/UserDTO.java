package dto;

import java.util.ArrayList;

import beans.User;
import enums.Gender;
import enums.Role;
import utils.DateHelper;

public class UserDTO {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	private String birdthDate;
	private Gender gender;
	private Role role;
	private String picture;
	private ArrayList<String> pictures;
	//private ArrayList<User> friends;
	private boolean privateProfile;
	
	public UserDTO() {}

	public UserDTO(int id, String username, String password, String email, String name, String surname,
			String birdthDate, Gender gender, Role role, String picture, ArrayList<String> pictures,
			boolean privateProfile) {
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
		this.pictures = pictures;
		this.privateProfile = privateProfile;
	}
	
	public UserDTO(User user) {
		
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.birdthDate = DateHelper.dateToString(user.getBirdthDate());
		this.gender = user.getGender();
		this.role = user.getRole();
		this.picture = user.getPicture();
		this.pictures = user.getPictures();
		this.privateProfile = user.isPrivateProfile();
		
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

	public String getBirdthDate() {
		return birdthDate;
	}

	public void setBirdthDate(String birdthDate) {
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

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	public boolean isPrivateProfile() {
		return privateProfile;
	}

	public void setPrivateProfile(boolean privateProfile) {
		this.privateProfile = privateProfile;
	}
	
	
}
