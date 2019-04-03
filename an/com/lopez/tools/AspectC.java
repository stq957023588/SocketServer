package an.com.lopez.tools;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AspectC implements InvocationHandler{
	protected Object main;
	public AspectC(Object object){
		this.main=object;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		System.out.println(proxy.getClass());
		System.out.println(method.getName());
		System.out.println("Aop Test Before");
		for(Object o:args){
			System.out.println(o);
		}
		Object object=method.invoke(main, args);
		System.out.println("Aop Test After");
		return object;
	}

}
