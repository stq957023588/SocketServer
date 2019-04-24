package an.com.lopez.blog;

import java.lang.reflect.Proxy;

public class BlogTest {
	public static void main(String[] args) {
		BlogTest test=new BlogTest();
		test.Agent();
	}
	public void Agent(){
		Class[] cls=new Class[]{Interface_.class};
		AgentHandler handler=new AgentHandler();
		ClassLoader loder=this.getClass().getClassLoader();
		Interface_ i=(Interface_)Proxy.newProxyInstance(loder, cls, handler);
		i.HelloWorld();
	}
}
