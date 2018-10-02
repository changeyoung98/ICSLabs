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
import Sample.Entity.Apple;
import Sample.Entity.Boeing;
import Sample.Entity.Cat;
import Sample.Entity.DomesticCat;
import Sample.Entity.Fruit;
import Sample.Entity.Plane;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/FruitServlet")
public class FruitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FruitServlet() {
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
            out.println("<title>Servlet FruitServlet</title>");
            out.println("</head>");
            out.println("<body>");

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
			
			Apple apple = new Apple();
			apple.setUid(new Long(5));
			apple.setShape("round");
			apple.setFlavor("so-so");
			apple.setColor("yellow");
			apple.setWeight(9);			
			session.save(apple);
			
			List fruits = session.createQuery("from Fruit").list();	


			session.getTransaction().commit();
            
			for (int i = 0; i < fruits.size(); i++) {
				Fruit theFruit = (Fruit)fruits.get(i);
				out.println("id: " + theFruit.getUid() + "<br>" + 
				            "shape: " + theFruit.getShape() + "<br>" +
						    "color: " + theFruit.getColor() + "<br>" +
						    "flavor: " + theFruit.getFlavor() + "<br>");
				if (theFruit instanceof Apple) {
					Apple aFruit = (Apple)theFruit;
					out.println("weight: " + aFruit.getWeight() + "<br>");
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
