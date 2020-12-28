package servlets;

import clases.JDBSConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    JDBSConnector jdbsConnector;

    {
        try {
            jdbsConnector = new JDBSConnector();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void doService(HttpServletRequest request, HttpServletResponse response) {
        jdbsConnector.createConnect();
        String requestName = request.getParameter("name");
        String requestPass = request.getParameter("pass");

        int id = jdbsConnector.isExist(requestPass, requestName);

        if (id >= 1) {
            HttpSession session = request.getSession();
            session.setAttribute("user", jdbsConnector.getByID(id));
            try {
                response.sendRedirect("index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect("login");
            } catch (IOException e) {
               e.printStackTrace();
            }
        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }
}
