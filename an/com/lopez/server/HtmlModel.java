package an.com.lopez.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
		File file=new File(Server.htmlMap.get(htmlName));
		InputStream inputStream=new FileInputStream(file);
		byte[]	bytes=new byte[1024];
		int len;
		StringBuffer buffer=new StringBuffer();
		while(true){
			len=inputStream.read(bytes);
			buffer.append(new String(bytes));
			if(len==-1 || len<1024){
				break;
			}
		}
		this.html=buffer.toString();
	}
	
	
}
