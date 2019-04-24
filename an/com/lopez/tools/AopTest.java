package an.com.lopez.tools;

import an.com.lopez.server.AopClass;
import an.com.lopez.server.AopFun;

@AopClass
public class AopTest {
	
	@AopFun("log")
	public void log(){
		System.out.println("Aop Test");
	}
	
	@AopFun("aop")
	public void aop(){
		System.out.println("aaaaaaaaaaaaaaaaaaaaa");
	}
	
}


