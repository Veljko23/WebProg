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

import beans.DirectMessage;
import beans.User;
import services.ProjectInit;
import utils.DateHelper;

public class DirectMessageDAO {
	private HashMap<Integer, DirectMessage> messages = new HashMap<Integer, DirectMessage>();

	private static DirectMessageDAO instance = null;
	private DirectMessageDAO() {}
	
	public static DirectMessageDAO getInstance() {
		if(instance == null)
			instance = new DirectMessageDAO();
		
		return instance;
	}
	
	public Collection<DirectMessage> findAll() {
		Collection<DirectMessage> allDirectMessages = messages.values();
		return allDirectMessages;
	}
	
	public DirectMessage findDirectMessage(int id) {
		return messages.containsKey(id) ? messages.get(id) : null;
	}

	
	public DirectMessage save(DirectMessage message) {
		
		int maxId = -1;
		for (int id : messages.keySet()) {
			if (id > maxId) {
				maxId = id;
			}
		}

		maxId++;
		message.setId(maxId);
		messages.put(maxId, message);
		saveToFile();
		return message;
	}

	public void loadDirectMessages(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/podaci/directMessages.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			int id = 0;
			int senderId;
			int receiverId;
			User sender = null;
			User receiver = null;
			String messageContext = "";
			LocalDate messageDate = null;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = Integer.parseInt(st.nextToken().trim());
					senderId = Integer.parseInt(st.nextToken().trim());
					sender = new User(senderId);
					receiverId = Integer.parseInt(st.nextToken().trim());
					receiver = new User(receiverId);
					messageContext = st.nextToken().trim();
					messageDate = DateHelper.stringToDate(st.nextToken().trim());
				}
				messages.put(id, new DirectMessage(id, sender, receiver, messageContext, messageDate));
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

		File file = new File(ProjectInit.contextPath + "/podaci/directMessages.txt");
		try {
			out = new BufferedWriter(new FileWriter(file));
			for (DirectMessage message : messages.values()) {
				out.write(message.fileLine() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}

	}
	
	public void bindMessageUser() {
		for(DirectMessage message: messages.values()) {
			int senderId = message.getSender().getId();
			int receiverId = message.getReceiver().getId();
			
			User sender = UserDAO.getInstance().findUser(senderId);
			User receiver = UserDAO.getInstance().findUser(receiverId);
			
			if(sender == null || receiver == null) {
				System.out.println("Nije pronadjen user. Greska!");
				continue;
			}
			message.setSender(sender);
			message.setReceiver(receiver);
		}
	}

	public DirectMessage change(DirectMessage message) {
		messages.put(message.getId(), message);
		return message;
	}

	public DirectMessage delete(int id) {
		return messages.remove(id);
	}
}
