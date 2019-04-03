package an.com.lopez.tools;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T>{
	//默认大小
	private static final int DEFAULT_CAPACITY=10;
	private int theSize;
	private T[] theItems;
	
	public MyArrayList(){
		doClear();
	}
	public void clear(){
		doClear();
	}
	private void doClear(){
		theSize=0;
		ensureCapacity(DEFAULT_CAPACITY);
	}
	public void ensureCapacity(int newCapacity){
		if(theSize>newCapacity)
			return;
		T[] old=theItems;
		theItems=(T[])new Object[newCapacity];
		for(int i=0;i<old.length;i++)
			theItems[i]=old[i];
	}
	
	public int size(){
		return theSize;
	}
	public boolean isEmpty(){
		return size()==0;
	}
	public T get(int index){
		if(index<0 || index>size())
			throw new ArrayIndexOutOfBoundsException();
		return theItems[index];
	}
	public T set(int index,T newVal){
		if(index<0 || index>size())
			throw new ArrayIndexOutOfBoundsException();
		T oldVal=theItems[index];
		theItems[index]=newVal;
		return oldVal;
	}
	
	public boolean add(T data){
		add(size(),data);
		return true;
	}
	public void add(int index,T data){
		if(theItems.length==size())
			ensureCapacity(size()*2+1);
		for(int i=size();i>index;i--)
			theItems[i]=theItems[i-1];
		theItems[index]=data;
	}
	
	
	public T remove(int index){
		T removeData=theItems[index];
		for(int i=index;i<size()-1;i++)
			theItems[i]=theItems[i+1];
		theSize--;
		return removeData;
	}
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<T>{
		private int current=0;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current<size();
		}

		@Override
		public T next() {
			// TODO Auto-generated method stub
			if(!hasNext())
				throw new NoSuchElementException();
			return theItems[current++];
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			MyArrayList.this.remove(--current);
		}
		
	}
	
	
}
