package org.ratoncio.singleton;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ratoncio.interfaces.singleton.EjbSessionPocSingletonRemote;

@WebServlet(urlPatterns = "/singleton2")
public class EjbSessionPocSingletonRemoteClient2 extends HttpServlet{
   private static final long serialVersionUID = 1L;

   private EjbSessionPocSingletonRemote bean;
   private Context ctx;

   public EjbSessionPocSingletonRemoteClient2(){
    super();
    try {
        Properties p = new Properties();
        
        p.put(Context.PROVIDER_URL, "corbaloc:iiop:192.168.0.190:2809/NameService");
        //context creation
        ctx = new InitialContext(p);
        String jndi;
        jndi = "org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        jndi = "ejb/ejb-app/ejb-app.war/EjbSingletonBean#org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        jndi = "java:global/ejb-app/EjbSingletonBean!org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        System.out.println("[JNDI]: " + jndi);
        bean = (EjbSessionPocSingletonRemote) ctx.lookup(jndi);
    }
    catch(Exception e){
        e.printStackTrace();
    }
 

   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("\nCLIENT: "); 
            response.getWriter().write("\ncontext: " + ctx.toString());
            response.getWriter().write("\nEnumeration:\n");
            NamingEnumeration list = ctx.list("");
            while (list.hasMore()){
                response.getWriter().write(list.next().toString()+"\n");
            }
            response.getWriter().write("\n");

            response.getWriter().write("\nbean: " + bean.toString());
            response.getWriter().write("\ncontext environment: " + ctx.getEnvironment().toString());
            bean.increment(3);
            response.getWriter().write("\nbean value : " + bean.result());

            response.getWriter().write("\n");
            response.getWriter().flush();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   

}
