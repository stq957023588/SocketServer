package an.com.lopez.tools;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable<T>{
	private int theSize;
	private int modCount=0;
	private Node<T> beginMarker;
	private Node<T> endMarker;
	
	public MyLinkedList(){
		doClear();
	}
	public void clear(){
		doClear();
	}
	
	public int size(){
		return theSize;
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	/**
	 * 清空
	 */
	private void doClear(){
		beginMarker=new Node<T>(null,null,null);
		endMarker=new Node<T>(null,beginMarker,null);
		beginMarker.next=endMarker;
		
		theSize=0;
		modCount++;
	}
	public boolean add(T t){
		add(size(),t);
		return true;
	}
	public void add(int index,T t){
		addBefore(getNode(index,0,size()),t);
	}
	
	public T get(int index){
		return getNode(index).data;
	}
	
	public T set(int index,T data){
		Node<T> node=getNode(index);
		T oldData=node.data;
		node.data=data;
		return oldData;
	}
	public T remove(int index){
		return remove(getNode(index));
	}
	
	/**
	 * 向某个节点之前插入数据
	 * @param p 当前节点(向某个节点之前插入数据的某个节点)
	 * @param t 需要插入的数据(向某个节点之前插入数据的数据)
	 */
	private void addBefore(Node<T> p,T t){
		Node<T> newNode=new Node<T>(t, p.prev, p);
		p.prev.next=newNode;
		p.prev=newNode;
		theSize++;
		modCount++;
	}
	
	private T remove(Node<T> node){
		node.prev.next=node.next;
		node.next.prev=node.prev;
		theSize--;
		modCount++;
		return node.data;
	}
	private Node<T> getNode(int index){
		return getNode(index,0,size()-1);
	}
	private Node<T> getNode(int index,int lower,int upper){
		Node<T> node;
		if(index<lower ||index> upper)
			throw new IndexOutOfBoundsException();
		if(index < size()/2){
			node=beginMarker;
			for(int i=0;i<index;i++){
				node=node.next;
			}
		}else{
			node=endMarker;
			for(int i=size();i>index;i--){
				node=node.prev;
			}
		}
		return node;
	}
	
	
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new LinkedListIterator();
	}
	private class LinkedListIterator implements Iterator<T>{
		private Node<T> current=beginMarker.next;
		private int expectedModCount=modCount;
		private boolean okToRemove=false;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current != endMarker;
		}

		@Override
		public T next() {
			// TODO Auto-generated method stub
			if(modCount!=expectedModCount)
				throw new ConcurrentModificationException();
			if(!hasNext())
				throw new NoSuchElementException();
			T nextData=current.data;
			current=current.next;
			okToRemove=true;
			return nextData;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			if(modCount!=expectedModCount)
				throw new ConcurrentModificationException();
			if(!okToRemove)
				throw new IllegalStateException();
			MyLinkedList.this.remove(current.prev);
			expectedModCount++;
			okToRemove=false;
		}
		
	}
	
	
	private static class Node<T>{
		public T data;
		public Node<T> prev;
		public Node<T> next;
		public Node(T d,Node<T> p,Node<T> n){
			data=d;prev=p;next=n;
		}
	}
}
