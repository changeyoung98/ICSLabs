package Sample.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Sample.Entity.Event;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EventServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");

            String title = (String) request.getParameter("title");            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String datestr = (String) request.getParameter("date");
            System.out.println(datestr);
            Date date=sdf.parse(datestr); 
            System.out.println(date);

            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Event t = new Event();
            t.setDate(date);
            t.setTitle(title);
            session.save(t);
            session.getTransaction().commit();
            
			out.println("<FORM METHOD=POST ACTION=\"PersonServlet\">");
			out.println("Event ID <INPUT TYPE=TEXT NAME=event SIZE=20 ><BR>");
			out.println("Person ID <INPUT TYPE=TEXT NAME=person SIZE=20 >");
			out.println("<P><INPUT TYPE=SUBMIT value=\"Next\">");
            out.println("<h1>The event has been inserted!</h1><br>");
            
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List events = session.createQuery("from Event").list();
			session.getTransaction().commit();
			for (int i = 0; i < events.size(); i++) {
				Event theEvent = (Event) events.get(i);
				out.println("id: " + theEvent.getId() + "<br>" + "title: "
						+ theEvent.getTitle() + "<br>" + "date: " + theEvent.getDate() + "<br><br>");
			}
			
			out.println("</body>");
			out.println("</html>");
            
        } catch(Exception e){
        	e.printStackTrace();
        }
        finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
