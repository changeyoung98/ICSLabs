/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package mood;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/report")
public class MoodServlet extends HttpServlet {
    private static final long serialVersionUID = 18925377774889413L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     *  methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Servlet MoodServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MoodServlet at "
                    + request.getContextPath() + "</h1>");
            
            //request.setAttribute("mood", "sleepy");
            
            String mood = (String) request.getAttribute("mood");
            
            //mood = (String) request.getParameter("mood");
            
            out.println("<p>Duke's mood is: " + mood + "</p>");
            if (mood.equals("sleepy")) {
                out.println("<img src=\"resources/images/duke.snooze.gif\" alt=\"Duke sleeping\"/><br/>");
            } else if (mood.equals("alert")) {
                out.println("<img src=\"resources/images/duke.waving.gif\" alt=\"Duke waving\"/><br/>");
            } else if (mood.equals("hungry")) {
                out.println("<img src=\"resources/images/duke.cookies.gif\" alt=\"Duke with cookies\"/><br/>");
            } else if (mood.equals("lethargic")) {
                out.println("<img src=\"resources/images/duke.handsOnHips.gif\" alt=\"Duke with hands on hips\"/><br/>");
            } else if (mood.equals("thoughtful")) {
                out.println("<img src=\"resources/images/duke.pensive.gif\" alt=\"Duke thinking\"/><br/>");
            } else {
                out.println("<img src=\"resources/images/duke.thumbsup.gif\" alt=\"Duke with thumbs-up gesture\"/><br/>");
            }
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

