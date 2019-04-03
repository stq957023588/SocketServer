package an.com.lopez.server;

import java.io.InputStream;
import java.util.Map;

public abstract class Request {
	InputStream in;
	Map<String,Object> params;
	public abstract void init() throws Exception;
	
	public abstract Object getParam(String key);
	
	public abstract void setParam();
	public abstract void printMsg();

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	
}
