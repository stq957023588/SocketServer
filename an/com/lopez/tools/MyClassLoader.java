package an.com.lopez.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class MyClassLoader extends ClassLoader{
	
	private String path="C:\\IO_TEST\\test\\Test\\bin\\";
	
	public Class<?> findClass(String name) throws ClassNotFoundException{
		Class<?> cls = findLoadedClass(name);
        if (cls != null) {
            return cls;
        }

        try {
            InputStream is = new FileInputStream(path + name.replace(".", "/") + ".class");
            byte[] bytes = IOUtils.toByteArray(is);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.loadClass(name);
	}
}
