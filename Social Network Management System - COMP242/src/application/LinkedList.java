package application;

import java.util.ArrayList;

public class LinkedList<T extends Comparable<T>> {
	Node<T> head;

	public LinkedList() { // To Create dummy head
		head = new Node<T>(null);
		head.setNext(head);
		head.setPrev(head);
	}

	public boolean isEmpty() { // By check if any node linked with the head
		return head.getNext() == head;
	}

	public boolean contains(T data) {
		if (!isEmpty()) {
			Node<T> curr = head.getNext();
			while (curr != head) {
				if (curr.getData() == data) {
					return true;
				}
				curr = curr.getNext();
			}
		}
		return false;
	}

	public int size() { // Return the number of Nodes in the list
		Node<T> curr = head.getNext();
		int size = 0;
		while (curr != head) {
			size++;
			curr = curr.getNext();
		}
		return size;
	}

	public void addFirst(T data) { // For Unsorted Linked-List
		Node<T> node = new Node<T>(data);
		if (isEmpty()) {
			node.setNext(head);
			head.setPrev(node);
		} else {
			node.setNext(head.getNext());
			head.getNext().setPrev(node);

		}
		node.setPrev(head);
		head.setNext(node);
	}

	public void insertSorted(T data) { // For Sorted Linked-List
		if (isEmpty()) { // Case 1 -> the list is empty then add the node in the first.
			addFirst(data);
		} else {
			Node<T> node = new Node<T>(data);
			Node<T> curr = head.getNext();
			while (curr.getNext() != head) {
				if (curr.getData().compareTo(data) >= 0)
					break;
				else
					curr = curr.getNext();
			}
			if (curr == head.getNext()) { // Case 2 -> the new node is less than all nodes in the list then add it first
											// too.
				addFirst(data);
			} else if (curr.getNext() == head && curr.getData().compareTo(data) < 0) { // Case 3 -> the current pointer
																						// stopped on the last node
				node.setNext(head);
				node.setPrev(curr);
				curr.setNext(node);
				head.setPrev(node);
			} else { // Case 4 -> if the pointer stopped on node in the middle of the list
				node.setNext(curr);
				node.setPrev(curr.getPrev());
				curr.getPrev().setNext(node);
				curr.setPrev(node);
			}
		}
	}

	public void addLast(T data) { // Add Node as the last node in the list
		Node<T> node = new Node<T>(data);
		node.setNext(head);
		node.setPrev(head.getNext());
		head.getPrev().setNext(node);
		head.setPrev(node);
	}

	public void insert(int index, T data) { // Insert node in the list with specific index
		if (index <= size() && index >= 0) {
			Node<T> curr = head.getNext();
			Node<T> node = new Node<T>(data);
			while (index-- > 0) {
				curr = curr.getNext();
			}
			node.setNext(curr);
			node.setPrev(curr.getPrev());
			curr.getPrev().setNext(node);
			curr.setPrev(node);
		} else {
			System.out.println("Invalid index!");
		}

	}

	public T removeFirst() { // Remove the first node in the list
		T data = head.getNext().getData();
		head.setNext(head.getNext().getNext());
		return data;
	}

	public T remove(int index) { // Remove Node by index
		if (isEmpty()) {
			System.out.println("The list is empty!");
		} else if (index < size() && index > 0) {
			Node<T> curr = head.getNext();
			while (index-- > 0) {
				curr = curr.getNext();
			}
			T data = curr.getData();
			curr.getPrev().setNext(curr.getNext());
			curr.getNext().setPrev(curr.getPrev());
			return data;
		} else {
			System.out.println("Index Out Of Bound!!");
		}
		return null;
	}

	public boolean remove(T data) { // Remove the first Node have the data in arguments
		if (isEmpty()) {
			System.out.println("The list is empty!");
		} else {
			Node<T> curr = head.getNext();
			while (curr != head) {
				if (curr.getData().compareTo(data) == 0) {
					curr.getPrev().setNext(curr.getNext());
					curr.getNext().setPrev(curr.getPrev());
					return true;
				}
				curr = curr.getNext();
			}
		}
		return false;
	}

	public T removeLast() { // Remove the last node in the list
		if (isEmpty()) {
			System.out.println("The list is empty!!");
		} else {
			T data = head.getPrev().getData();
			head.getPrev().getPrev().setNext(head);
			head.setPrev(head.getPrev().getPrev());
			return data;
		}
		return null;
	}

	public void removeDuplicate() { // Remove the duplicated nodes recursively
		if (head.getNext() != head)
			removeDuplicate(head.getNext());
	}

	private void removeDuplicate(Node<T> curr) { // Helper method to remove duplicated nodes recursively
		if (curr.getNext() == head) // Base Case when the current on the last node in the list
			return;
		else {
			if (curr.getNext().getData().compareTo(curr.getData()) == 0) {
				curr.setNext(curr.getNext().getNext());
				curr.getNext().setPrev(curr);
				removeDuplicate(curr);
			} else {
				removeDuplicate(curr.getNext());
			}
		}
	}

	public void clear() { // Clear all elements in the list
		head.setNext(head);
		head.setPrev(head);
	}

	public T get(int index) {
		if (head.getNext() != head) {
			Node<T> curr = head.getNext();
			while (index-- > 0) {
				curr = curr.getNext();
			}
			return curr.getData();
		}
		return null;
	}

	public ArrayList<T> toArrayList() {
		ArrayList<T> list = new ArrayList<T>();
		Node<T> curr = head.getNext();
		while (curr != head) {
			list.add(curr.getData());
			curr = curr.getNext();
		}
		return list;
	}

	public String toString() {
		String s = "Head->";
		Node<T> curr = head.getNext();
		while (curr != head) {
			s += curr;
			curr = curr.getNext();
		}
		return s + "Head";
	}

}
