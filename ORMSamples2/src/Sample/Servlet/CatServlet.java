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
import Sample.Entity.Cat;
import Sample.Entity.DomesticCat;
import Sample.Entity.Plane;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/CatServlet")
public class CatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CatServlet() {
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
            out.println("<title>Servlet CatServlet</title>");
            out.println("</head>");
            out.println("<body>");

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
			List cats = session.createQuery("from Cat").list();	
			DomesticCat domesticCat = new DomesticCat();
			Date birthday = new Date();
			domesticCat.setUid(new Long(3));
			domesticCat.setBirthday(birthday);
			domesticCat.setColor("blue");
			domesticCat.setName("Doraemon");
			domesticCat.setSex("male");
			domesticCat.setWeight(20);
			session.save(domesticCat);
			session.getTransaction().commit();
            
			for (int i = 0; i < cats.size(); i++) {
				Cat theCat = (Cat)cats.get(i);
				out.println("id: " + theCat.getUid() + "<br>" + 
				            "birthday: " + theCat.getBirthday() + "<br>" +
						    "color: " + theCat.getColor() + "<br>" +
						    "weight: " + theCat.getWeight() + "<br>");
				if (theCat instanceof DomesticCat) {
					DomesticCat aCat = (DomesticCat)theCat;
					out.println("name: " + aCat.getName() + "<br>");
				}
				out.println("<br>");
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
