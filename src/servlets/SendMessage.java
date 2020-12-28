package servlets;

import clases.MessageDAO;
import clases.User;
import javassist.CtBehavior;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendMessage extends HttpServlet {


    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        MessageDAO messageDAO =new MessageDAO();
        User user = (User) req.getSession().getAttribute("user");
        messageDAO.createConnect();
        String message = "";
        if(req.getParameter("message")!= null){
            message = req.getParameter("message");
        }

        int idwith = 0;
        if(  req.getSession().getAttribute("messagingWith") != null ) {
             idwith = Integer.parseInt((String) req.getSession().getAttribute("messagingWith"));
        }

        messageDAO.sendMessage(user.getId(),idwith,message);
       /* req.getRequestDispatcher("index").sendRedirect(req, res);*/
        res.sendRedirect("index");
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
