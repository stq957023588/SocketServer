package an.com.lopez.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class HtmlModel {
	private String html;
	private String htmlName;
	public HtmlModel(String name){
		this.htmlName=name;
		try {
			setHtml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getHtml() {
		return html;
	}
	private void setHtml() throws IOException{
		File file=new File(Server.context.resourcesPath+"\\html\\"+htmlName+".html");
		InputStream inputStream=new FileInputStream(file);
		this.html=IOUtils.toString(inputStream);
	}
	
	
}
