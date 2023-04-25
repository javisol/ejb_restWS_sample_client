package org.ratoncio.singleton;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ratoncio.interfaces.singleton.EjbSingletonRemote;

@WebServlet(urlPatterns = "/singleton")
public class EjbSingletonRemoteClient extends HttpServlet{
   private static final long serialVersionUID = 1L;

   private EjbSingletonRemote bean;
   private Object remoteObject;

   public EjbSingletonRemoteClient(){
    super();
    try {
        Properties p = new Properties();
        
        p.put(Context.PROVIDER_URL, "corbaloc:iiop:192.168.0.190:2809/NameService");
        //context creation
        InitialContext initialContext = new InitialContext(p);
        System.out.println("[initialContext]: " + initialContext.toString());
        Context remoteContext = (Context)initialContext.lookup("corbaname::192.168.0.190:2809/NameService");
        System.out.println("[remoteContext]: " + initialContext.toString());


        String jndi;
        jndi = "org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        jndi = "ejb/ejb-app/ejb-app.war/EjbSingletonBean#org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        jndi = "java:global/ejb-app/EjbSingletonBean!org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        jndi = "ejb/global/ejb-app/EjbSingletonBean!org.ratoncio.singleton.EjbSessionPocSingletonRemote";

        System.out.println("[JNDI]: " + jndi);
        remoteObject = remoteContext.lookup(jndi);
        bean = (EjbSingletonRemote) PortableRemoteObject.narrow(remoteObject, org.ratoncio.interfaces.singleton.EjbSingletonRemote.class);
        System.out.println("[remote object]: " + remoteObject.toString());

    }
    catch(Exception e){
        e.printStackTrace();
    }
 

   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            System.out.println("[doGet]:");
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("\nCLIENT: "); 
            response.getWriter().write("\n");

            //response.getWriter().write("\nremote object: " + remoteObject.toString());
            //response.getWriter().write(remoteObject.getClass().toString());
            bean.increment(3);
            response.getWriter().write("\nbean value : " + bean.result());

            response.getWriter().write("\n");
            response.getWriter().flush();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   

}
