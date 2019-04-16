package an.com.lopez.server;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class ServerTools {
	public static Properties PropertiesTest(String path) throws Exception{
		Properties p=new Properties();
		InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		p.load(in);
		in.close();
		return p;
		
	}
	public static void listFile(File file){
		if(file.isDirectory()){
			for(File f:file.listFiles()){
				listFile(f);
			}
		}else{
			System.out.println(file.getAbsolutePath());
		}
	}
}
