package application;

public class Node<T extends Comparable<T>> {
	private T data;
	private Node<T> next;
	private Node<T> prev;

	public Node(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getNext() {
		return this.next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getPrev() {
		return this.prev;
	}

	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}

	public String toString() {
		return "<-[" + this.data + "]->";
	}

}
