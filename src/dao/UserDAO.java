package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;


import beans.FriendshipRequest;
import beans.User;
import beans.Post;
import enums.Gender;
import enums.Role;
import services.ProjectInit;
import utils.DateHelper;

public class UserDAO {
	private HashMap<Integer, User> users = new HashMap<Integer, User>();

	private static UserDAO instance = null;
	private UserDAO() {}
	
	public static UserDAO getInstance() {
		if(instance == null)
			instance = new UserDAO();
		
		return instance;
	}

	public Collection<User> findAll() {
		Collection<User> allUsers = users.values();
		return allUsers;
	}

	public User findUser(int id) {
		return users.containsKey(id) ? users.get(id) : null;
	}

	public User save(User user) {
		int maxId = -1;
		for (int id : users.keySet()) {
			if (id > maxId) {
				maxId = id;
			}
		}

		maxId++;
		user.setId(maxId);
		users.put(maxId, user);
		return user;
	}

	public void loadUsers(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/users.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			int id = 0;
			String username = "";
			String password = "";
			String email = "";
			String name = "";
			String surname = "";
			LocalDate birdthDate = null;

			Gender gender = null;
			Role role = null;
			String picture = "";
			boolean privateProfile = false;

			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = Integer.parseInt(st.nextToken().trim());
					username = st.nextToken().trim();
					password = st.nextToken().trim();
					email = st.nextToken().trim();
					name = st.nextToken().trim();
					surname = st.nextToken().trim();
					birdthDate = DateHelper.stringToDate(st.nextToken().trim());

					Gender[] genders = Gender.values();
					gender = genders[Integer.parseInt(st.nextToken().trim())];

					Role[] roles = Role.values();
					role = roles[Integer.parseInt(st.nextToken().trim())];

					picture = st.nextToken().trim();
					privateProfile = Boolean.parseBoolean(st.nextToken().trim());
				}
				users.put(id,
						new User(id, username, password ,email, name, surname, birdthDate, gender, role, picture,
								new ArrayList<Post>(), new ArrayList<String>(), new ArrayList<FriendshipRequest>(),
								new ArrayList<User>(), privateProfile));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

	}
	
	public void bindUserPictures() {
		BufferedReader in = null;
		try {
			File file = new File(ProjectInit.contextPath + "/podaci/pictures.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			int userId = 0;
			String picture = "";

			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");

				userId = Integer.parseInt(st.nextToken().trim());
				picture = st.nextToken().trim();

				User user = UserDAO.getInstance().findUser(userId);
				user.getPictures().add(picture);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

	}
	
	public void bindUserFriends() {
		BufferedReader in = null;
		try {
			File file = new File(ProjectInit.contextPath + "/podaci/friends.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			int user1Id = 0;
			int user2Id = 0;

			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");

				user1Id = Integer.parseInt(st.nextToken().trim());
				user2Id = Integer.parseInt(st.nextToken().trim());

				User user1 = UserDAO.getInstance().findUser(user1Id);
				User user2 = UserDAO.getInstance().findUser(user2Id);
				user1.getFriends().add(user2);
				user2.getFriends().add(user1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

	}

	public User change(User user) {
		users.put(user.getId(), user);
		return user;
	}

	public User delete(int id) {
		return users.remove(id);
	}
}