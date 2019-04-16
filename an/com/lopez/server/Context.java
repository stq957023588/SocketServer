package an.com.lopez.server;

import java.io.File;
import java.net.URL;

public class Context {
	public final String clazzPath;
	public final String resourcesPath;
	public Context(){
		URL url=Thread.currentThread().getContextClassLoader().getResource("");
		this.clazzPath=url.getPath();
		
		String path = new File(this.clazzPath).getParent();
		this.resourcesPath=path+"/resources";
	}
	
		
}
