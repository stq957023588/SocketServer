package an.com.lopez.blog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AgentHandler implements InvocationHandler{
	Object obj;
	public AgentHandler(Object obj){
		this.obj=obj;
	}
	public AgentHandler(){
		
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		System.out.println("Agent Method");
		return null;
	}
}
