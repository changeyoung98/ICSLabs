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

import Sample.Entity.Airbus;
import Sample.Entity.Boeing;
import Sample.Entity.Plane;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/PlaneServlet")
public class PlaneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PlaneServlet() {
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
            out.println("<title>Servlet PlaneServlet</title>");
            out.println("</head>");
            out.println("<body>");

            String manufacturer = (String) request.getParameter("manufacturer");            
            System.out.println(manufacturer);
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
			List planes = session.createQuery("from Plane as p where p.manufacturer = :manu").
					setParameter("manu", manufacturer).list();
			
			Airbus airbus = new Airbus();
			airbus.setManufacturer("Airbus");
			airbus.setType("320");
			airbus.setCapacity("320");
			session.save(airbus);
			
			Boeing boeing = new Boeing();
			boeing.setManufacturer("Boeing");
			boeing.setType("777");
			boeing.setComfort("Great");
			session.save(boeing);
			
			Plane plane = new Plane();
			plane.setManufacturer("Il");
			plane.setType("78");
			session.save(plane);
						
            session.getTransaction().commit();
            
			for (int i = 0; i < planes.size(); i++) {
				Plane thePlane = (Plane) planes.get(i);
				out.println("id: " + thePlane.getId() + "<br>" + "type: "
						+ thePlane.getManufacturer() + thePlane.getType() + "<br>"
						+ "manufacturer: " + thePlane.getManufacturer() + "<br><br>");
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
