package application;

import javafx.beans.property.SimpleBooleanProperty;

public class User implements Comparable<User> {
	private String id;
	private String name;
	private int age;
	private LinkedList<User> friendsList;
	private LinkedList<Post> postsCreatedList;
	private LinkedList<Post> sharedWithThemPostsList;
	private SimpleBooleanProperty selected;
	private static LinkedList<String> ids = new LinkedList<String>();

	public User(String id, String name, int age) throws IllegalArgumentException {
		setId(id);
		setName(name);
		setAge(age);
		friendsList = new LinkedList<User>();
		postsCreatedList = new LinkedList<Post>();
		sharedWithThemPostsList = new LinkedList<Post>();
		ids.addFirst(id);
	}

	public void setId(String id) throws IllegalArgumentException {
		if (!ids.contains(id))
			this.id = id;
		else
			throw new IllegalArgumentException("Error: Invalid UserId");
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setAge(int age) {
		if (age > 0) {
			this.age = age;
		} else {
			throw new IllegalArgumentException("Error: Invalid age, it must be greater than zero!");
		}
	}

	public int getAge() {
		return this.age;
	}

	public LinkedList<User> getFriendsList() {
		return friendsList;
	}

	public void addFreind(User freind) {
		this.friendsList.addFirst(freind);
	}

	public LinkedList<Post> getPostsCreatedList() {
		return postsCreatedList;
	}

	public void removePost(Post post) {
		post.removePost();
		postsCreatedList.remove(post);
	}

	public void removeOthersPost(Post post) {
		sharedWithThemPostsList.remove(post);
		post.getSharedWithList().remove(this);
	}

	public void createPost(Post post) {
		this.postsCreatedList.addFirst(post);
	}

	public LinkedList<Post> getSharedWithThemPostsList() {
		return sharedWithThemPostsList;
	}

	public boolean isSelected() {
		return selected.get();
	}

	public SimpleBooleanProperty selectedProperty() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}
	
	
	
	public static LinkedList<String> getIds() {
		return ids;
	}


	@Override
	public int compareTo(User o) {
		return this.getName().compareTo(o.getName()) ;
	}

	public String toString() {
		return id + " - " + name;
	}

	
}
