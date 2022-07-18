package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Comment;
import beans.Post;
import beans.User;
import utils.DateHelper;

public class CommentDAO {
	private HashMap<Integer, Comment> comments = new HashMap<Integer, Comment>();

	public CommentDAO() {
		
	}
	
	public CommentDAO(String contextPath) {
		loadComments(contextPath);
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

	private void loadComments(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/comments.txt");
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
					postId = Integer.parseInt(st.nextToken().trim());
					post = new Post(postId);
					commentDate = DateHelper.stringToDate(st.nextToken().trim());
					updateDate = DateHelper.stringToDate(st.nextToken().trim());
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

	public Comment change(Comment comment) {
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment delete(int id) {
		return comments.remove(id);
	}
}
