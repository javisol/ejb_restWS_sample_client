package org.ratoncio.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet(urlPatterns = "/ejbappdb")
public class TestOpenLibertyDataSource extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //@Resource(lookup = "jdbc/openlibertydb")
    //DataSource postgreDb;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PreparedStatement query = null;
        ResultSet rset = null;

        try {
            //DataSource postgreDb = InitialContext.doLookup("jdbc/openlibertydb");
            InitialContext initialContext = new InitialContext();
            DataSource postgreDb = (DataSource) initialContext.lookup("jdbc/openlibertydb");

            Connection conn = postgreDb.getConnection();
            query = conn.prepareStatement("SELECT * FROM PETS");

            rset = query.executeQuery();

            System.out.println("[doGet]:");
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("\nCLIENT: "); 
            response.getWriter().write("\n");
            if (rset != null){
                while (rset.next()){
                    response.getWriter().write(rset.getString(2)+"\n");
                }
            }
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }



}
