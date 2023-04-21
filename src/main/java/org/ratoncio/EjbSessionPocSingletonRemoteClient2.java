package org.ratoncio;

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

import org.ratoncio.singleton.EjbSessionPocSingletonRemote;

@WebServlet(urlPatterns = "/singleton2")
public class EjbSessionPocSingletonRemoteClient2 extends HttpServlet{
   private static final long serialVersionUID = 1L;

   private EjbSessionPocSingletonRemote bean;
   private Context ctx;

   public EjbSessionPocSingletonRemoteClient2(){
    super();
    try {
        Properties p = new Properties();
        //p.put(Context.PROVIDER_URL, "corbaloc::127.0.0.1:2809");
        //p.put(Context.PROVIDER_URL, "corbaloc::128.0.0.1:2809/NameService");
        
        p.put(Context.PROVIDER_URL, "corbaloc:iiop:192.168.0.190:2809/NameService");
        /*
        if (context exist){
            ctx = busco
        }
        else {
            ctx = creo
        } */

        //crear contexto
        ctx = new InitialContext(p);
        String jndi;
        jndi = "org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        jndi = "ejb/ejb-app/ejb-app.war/EjbSingletonBean#org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        jndi = "java:global/ejb-app/EjbSingletonBean!org.ratoncio.singleton.EjbSessionPocSingletonRemote";
        /*
        jndi = "corbaname:rir:#ejb/global/ejb-app/EjbBean!org\\.ratoncio\\.EjbSessionPocRemote";
        jndi = "corbaname::openliberty01:2809/NameService#ejb/global/ejb-app/EjbBean!org\\.ratoncio\\.EjbSessionPocRemote";
        jndi = "corbaname::openliberty01:2809/NameService#ejb/global/ejb-app/EjbBean!org%5c.ratoncio%5c.EjbSessionPocRemote";
        */
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
            /*
            Properties p = new Properties();
            p.put(Context.PROVIDER_URL, "corbaloc::127.0.0.1:2809");
            InitialContext ctx = new InitialContext(p);
            Context remoteContext = (Context) ctx.lookup("corbaname::127.0.0.1:2809/NameService");
            */
            //lookup remote bean
            //Object remoteObj = remoteContext.lookup("EjbBean");
            //EjbSessionPocRemote bean = (EjbSessionPocRemote) ctx.lookup("corbaname::127.0.0.1:2809#EjbBean");
            //EjbSessionPocRemote bean = (EjbSessionPocRemote) remoteContext.lookup("ejb/EjbBean");
            //EjbSessionPocRemote bean = (EjbSessionPocRemote) ctx.lookup("corbaname::127.0.0.1:2809/NameService#EjbSessionRemotePoc!org.ratoncio.EjbSessionPocRemoteServlet");

            //Context envCtx = (Context) ctx.lookup("corbaname::127.0.0.1:2809/NameService#EjbSessionRemotePoc!org.ratoncio.EjbSessionPocRemote");
            //Context envCtx = (Context) ctx.lookup("corbaname::127.0.0.1:2809/NameService#ejb/global/ejb-app/EjbSessionRemotePoc!org.ratoncio.EjbSessionPocRemote");

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

            //bean = (EjbSessionPocRemote) ctx.lookup("org.ratoncio.EjbSessionPocRemote");
            response.getWriter().write("\nbean: " + bean.toString());
            //response.getWriter().write("\nremote object: " + remoteObj.toString());
            response.getWriter().write("\ncontext environment: " + ctx.getEnvironment().toString());
            //response.getWriter().write("\ncontext lookup: " + envCtx.toString());
            bean.increment(3);
            response.getWriter().write("\nbean value : " + bean.result());

            /*
            response.getWriter().write("\nremote context: " + remoteContext.toString());
            response.getWriter().write("\nEnumeration:\n");
            NamingEnumeration listRemote = remoteContext.list("");
            while (listRemote.hasMore()){
                response.getWriter().write(listRemote.next().toString()+"\n");
            }
            */
            response.getWriter().write("\n");
            response.getWriter().flush();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   

}
