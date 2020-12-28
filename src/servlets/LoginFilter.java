package servlets;

import clases.JDBSConnector;
import clases.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFilter implements Filter {
    JDBSConnector jdbsConnector;

    {
        try {
            jdbsConnector = new JDBSConnector();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        jdbsConnector.createConnect();
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        final HttpSession session = req.getSession();
        String requestName = req.getParameter("name");
        String requestPass = req.getParameter("pass");
        final User user = (User) session.getAttribute("user");

        if (user != null) {

            redirect(req, res,"index");

        } else if (jdbsConnector.isExist(requestPass, requestName) >= 1) {
            session.setAttribute("user", jdbsConnector.getByID(jdbsConnector.isExist(requestPass, requestName)));

            redirect(req, res,"index");

        } else {

            req.getRequestDispatcher("login").forward(req, res);
        }


    }
    public void redirect(HttpServletRequest req, HttpServletResponse res,String status) throws ServletException, IOException {

        if(req.getParameter("fromId")!=null){
            status = "sendMessage";
            req.getSession().setAttribute("messagingWith",req.getParameter("fromId"));
        }
        if(req.getParameter("message")!=null){
            status = "sendMessage";
        }
        if(req.getParameter("with")!=null){
            status = "/getMessage";
        }

        req.getRequestDispatcher(status).forward(req, res);


    }

    @Override
    public void destroy() {

    }
}
