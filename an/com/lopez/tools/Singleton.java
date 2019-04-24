package an.com.lopez.tools;

public class Singleton {
	private static Singleton single;
	private Singleton(){
		
	}
//	public static Singleton getInstance(){
//		if(single==null){
//			single=new Singleton();
//		}
//		return single;
//	}
	public static final Singleton getInstance(){
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder{
		private static final Singleton INSTANCE=new Singleton();
	}
}
