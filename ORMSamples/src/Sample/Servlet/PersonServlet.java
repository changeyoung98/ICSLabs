package Sample.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Sample.Entity.Event;
import Sample.Entity.Person;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PersonServlet() {
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
            out.println("<title>Servlet PersonServlet</title>");
            out.println("</head>");
            out.println("<body>");

            String event = (String) request.getParameter("event");  
            String person = (String) request.getParameter("person"); 
            long eventId = new Long(event);
            long personId = new Long(person);
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
            		
            Person aPerson = (Person) session.createQuery(
                     "select p from Person p left join fetch p.events where p.id = :pid").
                     setParameter("pid", personId).uniqueResult(); 
            Event anEvent = (Event) session.load(Event.class, eventId);
            session.getTransaction().commit(); 

            aPerson.getEvents().add(anEvent); // aPerson (and its collection) is detached 
            
            session = HibernateUtil.getSessionFactory().getCurrentSession(); 
            session.beginTransaction(); 
            session.update(aPerson); // Reattachment of aPerson 
            session.getTransaction().commit();

            session = HibernateUtil.getSessionFactory().getCurrentSession(); 
            session.beginTransaction();
            
//            session.refresh(aPerson);
//            aPerson.getEmailAddresses().add("new@new.com");
//           
            session.refresh(anEvent);
//            Set participants = anEvent.getParticipants();
//            Iterator iter = participants.iterator();
//            while(iter.hasNext()){
//                Person thePerson = (Person)iter.next();
//                out.println("Participant: " + thePerson.getFirstname() + " " + thePerson.getLastname() + "<br><br>");
//            }
//            session.getTransaction().commit();
            
            String ss = "<h2> The Person " + aPerson.getLastname() + " " + aPerson.getFirstname() 
            		     + " has joined " + anEvent.getTitle() + " at " + anEvent.getDate();
                        
            out.println(ss);
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
