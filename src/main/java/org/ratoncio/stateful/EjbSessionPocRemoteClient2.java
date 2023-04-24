package org.ratoncio.stateful;

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

import org.ratoncio.interfaces.stateful.EjbSessionPocRemote;

@WebServlet(urlPatterns = "/client2")
public class EjbSessionPocRemoteClient2 extends HttpServlet{
   private static final long serialVersionUID = 1L;

   private EjbSessionPocRemote bean;
   private Context ctx;

   public EjbSessionPocRemoteClient2(){
    super();
    try {
        Properties p = new Properties();
        p.put(Context.PROVIDER_URL, "corbaloc:iiop:openliberty01:2809/NameService");

        ctx = new InitialContext(p);
        String jndi;
        jndi = "org.ratoncio.stateful.EjbSessionPocRemote";
        jndi = "ejb/ejb-app/ejb-app.war/EjbBean#org.ratoncio.stateful.EjbSessionPocRemote";
        jndi = "java:global/ejb-app/EjbBean!org.ratoncio.stateful.EjbSessionPocRemote";
        System.out.println("[JNDI]: " + jndi);
        bean = (EjbSessionPocRemote) ctx.lookup(jndi);
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
