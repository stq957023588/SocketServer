package an.com.lopez.tools;

import java.util.HashMap;
import java.util.Map;

import an.com.lopez.server.HtmlModel;



@CtrlClass
public class TestCtrl {
//	@CtrlField
//	Request request;
	
	@CtrlMethod("/test")
	public String test(String age,Map<String,Object> paramMap) {
		System.out.println("test:");
		System.out.println(paramMap);
		return "Spring";
	}
	@CtrlMethod("/test1")
	public Map<String, String> test1(String age,Map<String,Object> paramMap) {
		System.out.println(age);
		System.out.println(paramMap);
		Map<String, String> map=new HashMap<String, String>();
		map.put("age", "19");
		return map;
	}
	
	@CtrlMethod("/test2")
	public ReturnObject test2(){
//		System.out.println(request.getParam("name"));
		ReturnObject returnObject=new ReturnObject("test");
		returnObject.setAttribute("test", "Attribute Test");
		return returnObject;
	}
	
	@CtrlMethod("/test3")
	public ReturnObject test3(){
		return new ReturnObject("test");
	}
	
	@CtrlMethod("/test4")
	public HtmlModel test4(Map<String,Object> params){
		System.out.println("test4:");
		System.out.println(params);
		return new HtmlModel("test");
	}
	
}
