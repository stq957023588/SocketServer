package an.com.lopez.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import an.com.lopez.server.CtrlField;

public class CacheModel {
	String url;
	Object obj;
	Method method;
	Class<?> returnType;
	Class[] paramsType;
	
	public CacheModel(String url,Object obj,Method method,Class<?> returnType,Class[] paramsType){
		this.url=url;
		this.obj=obj;
		this.method=method;
		this.returnType=returnType;
		this.paramsType=paramsType;
		
	}
	
	public Object invoke(Object...params) {
		try {
			return method.invoke(obj, null);
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
