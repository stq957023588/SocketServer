package an.com.lopez.tools;

import java.util.NoSuchElementException;

public class MyQueue<T> {
	private int theCapacity;
	private int theSize;
	private int front;
	private int back;
	
	private T[] theItems;
	
	@SuppressWarnings("unchecked")
	public MyQueue(int capacity){
		theCapacity=capacity;
		theItems=(T[])new Object[theCapacity];
		doClear();
	}
	public void clear(){
		doClear();
	}
	public int size(){
		return theSize;
	}
	private void doClear(){
		theSize=0;
		front=0;
		back=0;
	}
	public boolean enqueue(T t){
		if(theSize==theCapacity)
			throw new ArrayIndexOutOfBoundsException();
		theItems[back]=t;
		theSize++;
		back++;
		if(back==theCapacity)
			back=0;
		return true;
	}
	
	public T dequeue(){
		if(theSize<=0)
			throw new NoSuchElementException();
		T returnVal=theItems[front++];
		theSize--;
		if(front==theCapacity)
			front=0;
		return returnVal;
	}
	
}
