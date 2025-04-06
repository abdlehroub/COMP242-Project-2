package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Post implements Comparable<Post> {
	private static int idCount;
	private int id;
	private static LinkedList<Integer> idList = new LinkedList<Integer>();
	private LinkedList<User> sharedWithList = new LinkedList<User>();
	private User creator;
	private String content;
	private Calendar creationDate;

	public Post(User creator, String content, GregorianCalendar creationDate, LinkedList<User> sharedWithList) {
		setCreator(creator);
		setContent(content);
		setCreationDate(creationDate);
		setId();
		setSharedWithList(sharedWithList);
		idList.addFirst(this.id);
		addToCreatorList();

	}

	public Post(int id, User creator, String content, GregorianCalendar creationDate) {
		setCreator(creator);
		setContent(content);
		setCreationDate(creationDate);
		setId(id);
		idList.addFirst(this.id);
		addToCreatorList();
	}

	public void setId() {
		while (idList.contains(idCount)) {
			idCount++;
		}
		this.id = idCount;
	}

	public void setId(int id) {
		while (idList.contains(id)) {
			id++;
		}
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public void addToCreatorList() {
		creator.getPostsCreatedList().addFirst(this);
	}

	public User getCreator() {
		return this.creator;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setCreationDate(Calendar creationDate) {
		Calendar birthYearCalendar = new GregorianCalendar(
				new GregorianCalendar().get(Calendar.YEAR) - creator.getAge(), Calendar.JANUARY, 1);
		Calendar currentDate = new GregorianCalendar();
		if (creationDate.before(birthYearCalendar) || creationDate.after(currentDate)) {
			throw new IllegalArgumentException("Error: Invalid creation date!");
		}
		this.creationDate = creationDate;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public String getCreationDateFormat() {
		if (this.creationDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = formatter.format(creationDate.getTime());
			return formattedDate;
		}
		return null;
	}

	public void addSharedWith(User user) {
		sharedWithList.addFirst(user);
		user.getSharedWithThemPostsList().addFirst(this);
	}

	public void setSharedWithList(LinkedList<User> sharedWithList) {
		for (int i = 0; i < sharedWithList.size(); i++) {
			sharedWithList.get(i).getSharedWithThemPostsList().addFirst(this);
		}
		this.sharedWithList = sharedWithList;
	}

	public void removeSharedWith(User user) {
		sharedWithList.remove(user);
		user.getSharedWithThemPostsList().remove(this);
	}

	public void removePost() {
		for (int i = 0; i < sharedWithList.size(); i++) {
			sharedWithList.get(i).getSharedWithThemPostsList().remove(this);
		}
	}

	public String getSharedWithListPrinted() {
		StringBuilder users = new StringBuilder();
		for (int i = 0; i < sharedWithList.size(); i++) {
			users.append("[" + sharedWithList.get(i) + "] ");
		}
		return users.toString();
	}

	public LinkedList<User> getSharedWithList() {
		return sharedWithList;
	}

	@Override
	public int compareTo(Post o) {
		return this.creationDate.compareTo(o.getCreationDate());
	}

}
