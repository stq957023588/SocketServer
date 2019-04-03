package an.com.lopez.tools;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;


public class Invoker<T> {
	
	
	public Object getInstance(Class<?> cls,InvocationHandler handler) {
		return (Object)Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, handler);
	}
	
	public Object getInstance(Class<?> cls) {
		MethodProxy invocationHandler=new MethodProxy();
		Object newProxyInstance=Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, invocationHandler);
		return (Object)newProxyInstance;
	}
	
	public Object getInstance(Class<?> cls,T t,String methodName,Map<Class, Object> params) {
		MethodProxy invocationHandler=new MethodProxy(t,methodName,params);
		Object newProxyInstance=Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, invocationHandler);
		return (Object)newProxyInstance;
	}
}
