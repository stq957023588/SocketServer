package an.com.lopez.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class HttpRequest extends Request{
	private String requestLine="";
	private String requestHeader="";
	private String requestBody="";
	private String gotoURL="";
	
	public HttpRequest(InputStream in) {
		// TODO Auto-generated constructor stub
		this.in=in;
		try {
			init();
			setParam();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws IOException {
		// TODO Auto-generated method stub
		byte[] bytes=new byte[1024];
	    int len=0;
	    StringBuffer sBuffer=new StringBuffer();
	    while(true){
	    	len=super.in.read(bytes);
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

	@Override
	public Object getParam(String key) {
		// TODO Auto-generated method stub
		return params.get(key);
	}

	@Override
	public void setParam() {
		// TODO Auto-generated method stub
		if(requestLine.length()<=11	){
			System.out.println("Error RequestLine:"+requestLine);
			return;
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
	public void printMsg() {
		System.out.println(requestLine);
		System.out.println(requestHeader);
		System.out.println(requestBody);
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

	

}
