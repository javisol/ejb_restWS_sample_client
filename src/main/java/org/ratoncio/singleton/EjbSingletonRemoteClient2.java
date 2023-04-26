package org.ratoncio.singleton;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ratoncio.interfaces.singleton.EjbSingletonRemote;

@WebServlet(urlPatterns = "/singleton2")
public class EjbSingletonRemoteClient2 extends HttpServlet{
    private static final long serialVersionUID = 1L;

    private EjbSingletonRemote bean;
    private Object remoteObject;

    public EjbSingletonRemoteClient2(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            System.out.println("[doGet]:");
            getBean();
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

    private void getBean(){
        try {
            InitialContext initialContext = new InitialContext();
            System.out.println("[initialContext]: " + initialContext.toString());
            Context remoteContext = (Context)initialContext.lookup("corbaname::192.168.0.190:2809/NameService");
            System.out.println("[remoteContext]: " + initialContext.toString());

            String jndi;
            jndi = "ejb/global/ejb-app/EjbSingletonBean!org.ratoncio.singleton.EjbSingletonRemote";

            System.out.println("[JNDI]: " + jndi);
            remoteObject = remoteContext.lookup(jndi);
            System.out.println("[remote object]: " + remoteObject.toString());
            bean = (EjbSingletonRemote) PortableRemoteObject.narrow(remoteObject, org.ratoncio.interfaces.singleton.EjbSingletonRemote.class);
            System.out.println("[bean]: " + bean.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
