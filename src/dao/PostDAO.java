package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Comment;
import beans.Post;
import beans.User;

public class PostDAO {
	private HashMap<Integer, Post> posts = new HashMap<Integer, Post>();

	private static PostDAO instance = null;
	private PostDAO() {}
	
	public static PostDAO getInstance() {
		if(instance == null)
			instance = new PostDAO();
		
		return instance;
	}
	
	public Collection<Post> findAll() {
		Collection<Post> allPosts = posts.values();
		return allPosts;
	}
	
	public Post findPost(int id) {
		return posts.containsKey(id) ? posts.get(id) : null;
	}

	
	public Post save(Post post) {
		int maxId = -1;
		for (int id : posts.keySet()) {
			if (id > maxId) {
				maxId = id;
			}
		}

		maxId++;
		post.setId(maxId);
		posts.put(maxId, post);
		return post;
	}

	public void loadPosts(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/podaci/posts.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			int id = 0;
			String picture = "";
			String text = "";
			int userId;
			User user = null;

			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = Integer.parseInt(st.nextToken().trim());
					picture = st.nextToken().trim();
					text = st.nextToken().trim();
					userId = Integer.parseInt(st.nextToken().trim());
					user = new User(userId);
					
				}
				posts.put(id, new Post(id, picture, text, new ArrayList<Comment>(), user));
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
	
	public void bindPostUser() {
		for(Post post: posts.values()) {
			int id = post.getUser().getId();
			User user = UserDAO.getInstance().findUser(id);
			
			if(user == null) {
				System.out.println("Doslo je do greske u ucitavanju.");
				continue;
			}
			post.setUser(user);
			user.getPosts().add(post);
		}
	}

	public Post change(Post post) {
		posts.put(post.getId(), post);
		return post;
	}

	public Post delete(int id) {
		return posts.remove(id);
	}
}
