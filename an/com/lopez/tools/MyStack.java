package an.com.lopez.tools;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class MyStack<T> {
	private int theCapacity;
	private int theSize;
	private int topOfStack;
	private T[] theItems;
	
	public MyStack(int capacity){
		theCapacity=capacity;
		doClear();
	}
	public void clear(){
		doClear();
	}
	@SuppressWarnings("unchecked")
	private void doClear(){
		
		theItems=(T[])new Object[theCapacity];
		theSize=0;
		topOfStack=-1;
	}
	
	public int size(){
		return theSize;
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	public int capacity(){
		return theCapacity;
	}
	
	
	public boolean push(T data){
		if(size()==capacity())
			throw new ArrayIndexOutOfBoundsException();
		theItems[++topOfStack]=data;
		theSize++;
		return true;
	}
	public T pop(){
		if(theSize<=0 || topOfStack <=-1)
			throw new NoSuchElementException();
		T returnVal=theItems[topOfStack];
		topOfStack--;
		theSize--;
		return returnVal;
	}
	public T top(){
		if(theSize<=0 || topOfStack <=-1)
			throw new NoSuchElementException();
		return theItems[topOfStack];
	}
}
