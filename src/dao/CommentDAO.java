package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Comment;
import beans.Post;
import beans.User;
import services.ProjectInit;
import utils.DateHelper;

public class CommentDAO {
	private HashMap<Integer, Comment> comments = new HashMap<Integer, Comment>();

	private static CommentDAO instance = null;
	private CommentDAO() {}
	
	public static CommentDAO getInstance() {
		if(instance == null)
			instance = new CommentDAO();
		
		return instance;
	}
	
	public Collection<Comment> findAll() {
		Collection<Comment> allComments = comments.values();
		return allComments;
	}
	
	public Comment findComment(int id) {
		return comments.containsKey(id) ? comments.get(id) : null;
	}

	
	public Comment save(Comment comment) {
		int maxId = -1;
		for (int id : comments.keySet()) {
			if (id > maxId) {
				maxId = id;
			}
		}

		maxId++;
		comment.setId(maxId);
		comments.put(maxId, comment);
		return comment;
	}

	public void loadComments(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/podaci/comments.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			int id = 0;
			int userId;
			int postId;
			User user = null;
			Post post = null;
			String text = "";
			LocalDate commentDate = null;
			LocalDate updateDate = null;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = Integer.parseInt(st.nextToken().trim());
					userId = Integer.parseInt(st.nextToken().trim());
					user = new User(userId);
					text = st.nextToken().trim();
					commentDate = DateHelper.stringToDate(st.nextToken().trim());
					String upDate = st.nextToken().trim();
					if(!upDate.equals("-1")) {
						updateDate = DateHelper.stringToDate(upDate);						
					}
					postId = Integer.parseInt(st.nextToken().trim());
					post = new Post(postId);
				}
				comments.put(id, new Comment(id, user, text, commentDate, updateDate, post));
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
	
	
	public void saveToFile() {
		BufferedWriter out = null;

		File file = new File(ProjectInit.contextPath + "/podaci/comments.txt");
		try {
			out = new BufferedWriter(new FileWriter(file));
			for(Comment comment : comments.values()) {
				out.write(comment.fileLine() + "\n");
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void bindCommentPost() {
		for (Comment comment: comments.values()) {
			int id = comment.getPost().getId();
			Post post = PostDAO.getInstance().findPost(id);
			if(post == null) {
				System.out.println("Doslo je do greske u ucitavanju commPost");
				continue;
			}
			System.out.println(id);
			comment.setPost(post);
			post.getComments().add(comment);
		}
	}
	
	public void bindCommentUser() {
		for (Comment comment: comments.values()) {
			int id = comment.getUser().getId();
			User user = UserDAO.getInstance().findUser(id);
			if(user == null) {
				System.out.println("Doslo je do greske u ucitavanju commUser");
				continue;
			}
			comment.setUser(user);
		}
	}

	public Comment change(Comment comment) {
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment delete(int id) {
		return comments.remove(id);
	}
}
