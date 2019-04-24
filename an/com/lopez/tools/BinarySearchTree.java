package an.com.lopez.tools;

public class BinarySearchTree<T extends Comparable<? super T>>{
	private BinaryNode<T> root;
	public BinarySearchTree(){
		root=null;
	}
	public void makeEmpty(){
		root=null;
	}
	public boolean isEmpty(){
		return root==null;
	}
	public boolean contains(T data){
		return contains(data,root);
	}
	public T findMin(){
		if(isEmpty())
			throw new NullPointerException();
		return findMin(root).data;
	}
	public T findMax(){
		if(isEmpty())
			throw new NullPointerException();
		return findMax(root).data;
			
	}
	public void insert(T data){
		root=insert(data,root);
	}
	
	public void remove(T data){
		remove(data, root);
	}
	
	
	private boolean contains(T data,BinaryNode<T> t){
		if(t==null)
			return false;
		int compareResult=data.compareTo(t.data);
		if(compareResult<0)
			return contains(data,t.left);
		else if(compareResult>0)
			return contains(data,t.right);
		else
			return true;
	}
	private BinaryNode<T> findMin(BinaryNode<T> t){
		if(t==null)
			return null;
		else if(t.left==null)
			return t;
		else
			return findMin(t.left);
	}
	
	private BinaryNode<T> findMax(BinaryNode<T> t){
		if(t!=null)
			while(t.right!=null)
				t=t.right;
		return t;
	}
	
	private BinaryNode<T> insert(T data,BinaryNode<T> t){
		if(t==null)
			return new BinaryNode<T>(data,null,null);
		int compareResult=data.compareTo(t.data);
		if(compareResult<0)
			t.left=insert(data, t.left);
		else if(compareResult>0)
			t.right=insert(data,t.right);
		return t;
	}
	
	private BinaryNode<T> remove(T data,BinaryNode<T> t){
		if(t==null)
			return null;
		int compareResult=data.compareTo(t.data);
		
		if(compareResult<0)
			t.left=remove(data, t.left);
		else if(compareResult>0)
			t.right=remove(data, t.right);
		else if(t.left !=null && t.right !=null){
			t.data=findMin(t.right).data;
			t.right=remove(t.data, t.right);
		}else
			t=(t.left != null) ? t.left : t.right;
		return t;
	}
	
	
	private static class BinaryNode<T>{
		T data;
		BinaryNode<T> left;
		BinaryNode<T> right;
		BinaryNode(T data){
			this(data,null,null);
		}
		BinaryNode(T data,BinaryNode<T> left,BinaryNode<T> right){
			this.data=data;
			this.left=left;
			this.right=right;
		}
	}
}
