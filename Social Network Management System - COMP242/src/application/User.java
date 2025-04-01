package application;

public class User implements Comparable<User> {
	private String id;
	private String name;
	private int age;
	private LinkedList<User> friendsList;
	private LinkedList<Post> postsCreatedList;
	private LinkedList<Post> sharedWithThemPostsList;

	public User(String id, String name, int age) {
		setId(id);
		this.name = name;
		this.age = age;
	}

	public void setId(String id) {
		if (id.matches("^[0-9]+$"))
			this.id = id;
		else
			throw new IllegalArgumentException("Error: Invalid UserId, it must be only numbers!");
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
		if (age < 0) {
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

	public void createPost(Post post) {
		this.postsCreatedList.addFirst(post);
	}

	public LinkedList<Post> getSharedWithThemPostsList() {
		return sharedWithThemPostsList;
	}

	@Override
	public int compareTo(User o) {
		return postsCreatedList.size() - o.getPostsCreatedList().size();
	}

	public String toString() {
		return id + " - " + name;
	}
}
