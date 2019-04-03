package an.com.lopez.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class Request {
	private InputStream inputStream;
	private String requestLine="";
	private String requestHeader="";
	private String requestBody="";
	private String gotoURL="";
	private Map<String,Object> params;
	public Request(InputStream inputStream){
		this.inputStream=inputStream;
		try {
			init();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if("GET".equals(ServerCheck())){
			String[] arr=requestLine.substring(4, requestLine.length()-9).split("\\?");
			gotoURL=arr[0];
			if(arr.length>1){
				if(params==null)
					params=new HashMap<String, Object>();
				for(String str:arr[1].split("&")){
					params.put(str.split("=")[0], str.split("=")[1]);
				}
			}
			
		}else if ("POST".equals(ServerCheck())) {
			gotoURL=requestLine.substring(5, requestLine.length()-9);
			if(params==null)
				params=new HashMap<String, Object>();
			if(requestBody.split("&").length>0){
				for(String str:requestBody.split("&")){
					params.put(str.split("=")[0], str.split("=")[1]);
				}
			}
		}
		System.out.println("gotoURL:\t"+gotoURL);
	}
	
	public void init() throws IOException {
	    byte[] bytes=new byte[1024];
	    int len=0;
	    StringBuffer sBuffer=new StringBuffer();
	    while(true){
	    	len=inputStream.read(bytes);
	    	sBuffer.append(new String(bytes));
	    	if(len<=0||len<1024){
	    		break;
	    	}
	    }
	    String[] bodyArray=sBuffer.toString().split("\\n");
	    requestLine=bodyArray[0].trim();
	    int i=1;
	    for(;i<bodyArray.length;i++){
	    	if("".equals(bodyArray[i].trim()) || bodyArray[i]==null || "\n".equals(bodyArray[i]))
	    		break;
	    	requestHeader+=bodyArray[i].trim();
	    	requestHeader+="\n";
	    }
		for(;i<bodyArray.length;i++){
			requestBody+=bodyArray[i].trim();
		}
	}
	
	public String ServerCheck() {
		if("GET".equals(requestLine.substring(0, 3).toUpperCase()) 
				&& "HTTP/1.1".equals(requestLine.substring(requestLine.length()-8, requestLine.length()).toUpperCase()))
			return "GET";
		if("POST".equals(requestLine.substring(0, 4).toUpperCase()) 
				&& "HTTP/1.1".equals(requestLine.substring(requestLine.length()-8, requestLine.length()).toUpperCase()))
			return "POST";
		return "";
	}
	
	public Object getParam(String paramName) {
		return params.get(paramName);
	}
	
	public void printMsg() {
		System.out.println("requestLine:\n"+requestLine);
		System.out.println("requestHeader:\n"+requestHeader);
		System.out.println("requestBody:\n"+requestBody);
	}

	public String getRequestLine() {
		return requestLine;
	}

	public void setRequestLine(String requestLine) {
		this.requestLine = requestLine;
	}

	public String getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getGotoURL() {
		return gotoURL;
	}

	public void setGotoURL(String gotoURL) {
		this.gotoURL = gotoURL;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
}
