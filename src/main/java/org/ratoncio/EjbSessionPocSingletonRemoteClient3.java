package org.ratoncio;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.rmi.PortableRemoteObject;

import org.ratoncio.singleton.EjbSessionPocSingletonRemote;

@WebServlet(urlPatterns = "/singleton3")
public class EjbSessionPocSingletonRemoteClient3 extends HttpServlet{
   private static final long serialVersionUID = 1L;

   private EjbSessionPocSingletonRemote bean;
   private Object remoteObject;

   public EjbSessionPocSingletonRemoteClient3(){
    super();
    try {
        Properties p = new Properties();
        
        p.put(Context.PROVIDER_URL, "corbaloc:iiop:192.168.0.190:2809/NameService");
        //context creation
        InitialContext initialContext = new InitialContext();
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
        bean = (EjbSessionPocSingletonRemote) PortableRemoteObject.narrow(remoteObject, org.ratoncio.singleton.EjbSessionPocSingletonRemote.class);
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

            response.getWriter().write("\nremote object: " + remoteObject.toString());
            response.getWriter().write(remoteObject.getClass().toString());
            bean.increment(3);
            response.getWriter().write("\nbean value : " + bean.result());

            response.getWriter().write("\n");
            response.getWriter().flush();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   

}
