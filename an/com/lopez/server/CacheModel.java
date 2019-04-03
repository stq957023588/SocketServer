package an.com.lopez.server;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class CacheModel {
	String url;
	Object obj;
	Method method;
	Class<?> returnType;
	Class[] paramsType;
	Object[] params;
	
	public CacheModel(String url,Object obj,Method method,Class<?> returnType,Class[] paramsType){
		this.url=url;
		this.obj=obj;
		this.method=method;
		this.returnType=returnType;
		this.paramsType=paramsType;
	}
	
	public Object invoke(Map<String,Object> paramsMap) {
		try {
			params=new Object[paramsType.length];
			for(int i=0;i<paramsType.length;i++)
				if(paramsType[i]==Map.class)
					params[i]=paramsMap;
			
			return method.invoke(obj, params);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void setRequest(Request request) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields=obj.getClass().getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(CtrlField.class)){
				field.set(obj, request);
			}
		}
	}
}
