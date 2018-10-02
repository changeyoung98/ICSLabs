package sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserManagerServlet
 */
@WebServlet("/UserManagerServlet")
public class UserManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            // Begin unit of work
            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            // Write HTML header
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>User Manager</title></head><body>");

           // Print page
           //printUserForm(out);
           listUser(out);

           // Write HTML footer
           out.println("</body></html>");
           out.flush();
           out.close();
           HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        }
        catch (Exception ex) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            if ( ServletException.class.isInstance( ex ) ) {
                throw ( ServletException ) ex;
            }
            else {
                throw new ServletException( ex );
            }
        }
	}

	private void printUserForm(PrintWriter out) {
        out.println("<h2>Add new user:</h2>");
        out.println("<form>");
        out.println("Title: <input name='eventTitle' length='50'/><br/>");
        out.println("Date (e.g. 24.12.2009): <input name='eventDate' length='10'/><br/>");
        out.println("<input type='submit' name='action' value='store'/>");
        out.println("</form>");
    }
	
	@SuppressWarnings({ "unchecked" })
	private void listUser(PrintWriter out) {

        List<User> result = HibernateUtil.getSessionFactory()
                .getCurrentSession().createQuery("from User").list();
        if (result.size() > 0) {
            out.println("<h2>Users in database:</h2>");
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>Firstname</th>");
            out.println("<th>Lastname</th>");
            out.println("<th>Password</th>");
            out.println("<th>email</th>");
            out.println("</tr>");
            Iterator it = result.iterator();
            while (it.hasNext()) {
                User user = (User) it.next();
                out.println("<tr>");
                out.println("<td>" + user.getId() + "</td>");
                out.println("<td>" + user.getFirstname() + "</td>");
                out.println("<td>" + user.getLastname() + "</td>");
                out.println("<td>" + user.getPhone() + "</td>");
                out.println("<td>" + user.getEmail() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
    }

}
