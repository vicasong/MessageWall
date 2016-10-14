package org.vica.ms.servlet;

import org.vica.ms.po.User;
import org.vica.ms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Servlet Of UserAccount
 * Created by Vica-tony on 8/29/2016.
 */
@WebServlet(name = "Account", urlPatterns = {"/account/*"})
public class Account extends HttpServlet {

    /**
     * Post Request
     *
     * @param request  req
     * @param response resp
     * @throws ServletException ex
     * @throws IOException      ex
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //get the action
        String where = request.getPathInfo();
        if (where == null) {
            //default to login
            forLogin(request, response);
        } else {
            if (where.startsWith("/register")) {
                forRegister(request, response);
            } else {
                response.setStatus(404);
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        /**
         * Get the action to handle
         */
        String where = request.getPathInfo();
        if (where == null) {
            // default to home (login)
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            if (where.startsWith("/exit")) {
                request.getSession().removeAttribute("user");
                response.sendRedirect("/account");
            } else {
                response.setStatus(404);
            }
        }

    }

    /**
     * Handle The Register Action (Json)
     * @param request req
     * @param response res
     * @throws ServletException
     * @throws IOException
     */
    private void forRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String name = request.getParameter("userName");
        String nick = request.getParameter("nickName");
        String pass = request.getParameter("password");
        if (new UserService().register(name, nick, pass)) {
            response.getWriter().write("{\"success\":\"ok\"}");
        } else {
            response.getWriter().write("{\"success\":\"bad\"}");
        }
    }

    /**
     * Handle The Login Action (Json)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("userName");
        String pass = request.getParameter("password");
        User user = new UserService().login(name, pass);
        if (user == null) {
            request.setAttribute("error", "UserName Or Password Error!");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/home");
        }
    }

}
