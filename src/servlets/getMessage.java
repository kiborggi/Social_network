package servlets;

import clases.MessageDAO;
import clases.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "getMessage")
public class getMessage extends HttpServlet {
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        final HttpSession session = req.getSession();
        session.setAttribute("messagingWith", req.getParameter("with"));
        req.getRequestDispatcher("/").forward(req, res);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

}
