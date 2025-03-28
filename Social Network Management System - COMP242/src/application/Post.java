package application;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Post implements Comparable<Post> {
	private String id;
	private User creator;
	private String content;
	private Calendar creationDate;
	private LinkedList<User> sharedWithList;

	public Post(String id, User creator, String content, GregorianCalendar creationDate) {
		setId(id);
		setCreator(creator);
		setContent(content);
		setCreationDate(creationDate);
	}

	public void setId(String id) {
		if (id.matches("^[0-9]+$"))
			this.id = id;
		else
			throw new IllegalArgumentException("Error: Invalid PostId, it must be only numbers!");
	}

	public String getId() {
		return this.id;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getCreator() {
		return this.getCreator();
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setCreationDate(Calendar creationDate) {
		if (creationDate.compareTo(new GregorianCalendar()) > 0)
			throw new IllegalArgumentException("Error: Invalid creation date!");
		this.creationDate = creationDate;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void addSharedWith(User user) {
		sharedWithList.addFirst(user);
	}

	@Override
	public int compareTo(Post o) {
		return this.creationDate.compareTo(o.getCreationDate());
	}

}
