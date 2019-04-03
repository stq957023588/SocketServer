package an.com.lopez.tools;

import java.util.HashMap;
import java.util.Map;


public class ReturnObject {
	String pageName;
	private Map<String, Object> map;
	
	public ReturnObject(String pageName){
		this.pageName=pageName;
	}
	
	public void setAttribute(String key,Object value) {
		if(map==null)
			map=new HashMap<String, Object>();
		map.put(key, value);
	}
	public Object getAttribute(String key){
		if(!map.containsKey(key))
			return null;
		return map.get(key);
	}
	
//	public String getJsonStr(){
//		System.out.println(JSONObject.toJSONString(map));
//		return JSONObject.toJSONString(map);
//	}
	public Map<String, Object> getAttribute(){
		return map;
	}
	
}
