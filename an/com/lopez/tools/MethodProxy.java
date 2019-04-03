package an.com.lopez.tools;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class MethodProxy<T> implements InvocationHandler {
	/**
	 * 
	 */
	private T t;
	private String methodName;
	private Map<Class, Object> params;
	public MethodProxy(){
		
	}
	public MethodProxy(T t,String methodName,Map<Class, Object> params){
		this.t=t;
		this.methodName=methodName;
		this.params=params;
	}
	
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		
		run();
		return null;
	}
	
	public void simple(){
		System.out.println("Annoations");
	}
	
	public Object run() {
		try {
			Class[] cls = {};
			cls=params.keySet().toArray(cls);
			Method method1=t.getClass().getMethod(methodName,cls);
			method1.invoke(t, params.values().toArray());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//定义接口方法内容
		return "method call success";
	}

}
