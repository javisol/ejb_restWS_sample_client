package org.ratoncio.stateful;

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

import org.ratoncio.interfaces.stateful.EjbStatefulRemote;

@WebServlet(urlPatterns = "/stateful")
public class EjbStatefulRemoteClient extends HttpServlet{
    private static final long serialVersionUID = 1L;

    private EjbStatefulRemote bean;
    private Object remoteObject;


    public EjbStatefulRemoteClient(){
    super();
    try {
        Properties p = new Properties();
        
        p.put(Context.PROVIDER_URL, "corbaloc:iiop:192.168.0.190:2809/NameService");
        InitialContext initialContext = new InitialContext(p);
        System.out.println("[initialContext]: " + initialContext.toString());
        Context remoteContext = (Context)initialContext.lookup("corbaname::192.168.0.190:2809/NameService");
        System.out.println("[remoteContext]: " + initialContext.toString());

        String jndi;
        jndi = "org.ratoncio.stateful.EjbSessionPocRemote";
        jndi = "ejb/ejb-app/ejb-app.war/EjbBean#org.ratoncio.stateful.EjbSessionPocRemote";
        jndi = "java:global/ejb-app/EjbBean!org.ratoncio.stateful.EjbSessionPocRemote";

        System.out.println("[JNDI]: " + jndi);
        remoteObject = remoteContext.lookup(jndi);
        bean = (EjbStatefulRemote) PortableRemoteObject.narrow(remoteObject, org.ratoncio.interfaces.stateful.EjbStatefulRemote.class);
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
