package an.com.lopez.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Response {
	private OutputStream outputStream;
	public static final String STATE_200="http/1.1 200 OK\n";
	public static final String STATE_404="http/1.1 404\n";
	
	public Response(OutputStream outputStream){
		this.outputStream=outputStream;
	}
	
	public String write(String str) throws IOException {
		String message=STATE_200+"\n"+str;
		send(message);
		return message;
	}
	public String write(File file) throws IOException {
		InputStream inputStream=new FileInputStream(file);
		byte[] bytes=new byte[inputStream.available()];
		inputStream.read(bytes);
		outputStream.write(bytes);
		return null;
	}
	
	public String error404() throws IOException {
		String message=STATE_404+"\n";
		send(message);
		return message;
	}
	
	public String write(String str,Map<String, Object> map) throws IOException{
		String html=str;
		String htmlTempString=html;
		String regex="<(\\w|\\s|\"|=){0,}slz:(if|text)=\"\\$\\{([A-Z]|[a-z]|[0-9]){1,9}\\}\"(\\w|\\s|\"|=){0,}>";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(html);
		StringBuilder sb=new StringBuilder();
		while(matcher.find()){
			int start=matcher.start();
			int end=matcher.end();
			String table=html.substring(start, end);
			int l=0;
			int r=0;
			if(table.contains("slz:text=\"${")){
				String oldString=html.substring(start, html.indexOf("<", start+table.length()));
//				System.out.println(table);
				l=table.indexOf("slz:text=\"${");
				r=table.indexOf("}\"", l);
				String attributeName=table.substring(l+"slz:text=\"${".length(),r);
				String regex1=table.substring(l, r+"}\"".length());
				htmlTempString=htmlTempString.replace(oldString, table.replace(regex1, "")+(map.get(attributeName)==null?"":map.get(attributeName)));
			}
		}
		return write(htmlTempString);
	} 
	
	private void send(String message) throws IOException {
		byte[] bytes=message.getBytes("UTF-8");
		outputStream.write(bytes);
		outputStream.flush();
	}
}
