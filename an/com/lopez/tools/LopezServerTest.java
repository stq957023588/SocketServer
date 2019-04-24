package an.com.lopez.tools;

import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class LopezServerTest {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 * @throws AlreadyBoundException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LopezServerTest server=new LopezServerTest();
		server.server1();
	}
	
	public void server1 () throws Exception{
		Remote service=new MyRemoteImp();
		LocateRegistry.createRegistry(1099);
		Naming.rebind("rmi://localhost:1099/RemoteHello", service);
	}
}
