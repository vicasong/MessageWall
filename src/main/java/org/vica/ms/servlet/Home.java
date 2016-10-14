package org.vica.ms.servlet;

import org.apache.log4j.Logger;
import org.vica.ms.dto.MsgDetail;
import org.vica.ms.dto.MsgHome;
import org.vica.ms.po.User;
import org.vica.ms.service.MessageService;
import org.vica.ms.service.ReplyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Servlet For Main Content
 * Created by Vica-tony on 8/29/2016.
 */
@WebServlet(name = "Home", urlPatterns = {"/home/*"})
public class Home extends HttpServlet {

    private Logger logger = Logger.getLogger(this.getClass());
    private User user;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/account");
        } else {
            user = (User) request.getSession().getAttribute("user");
            String where = request.getPathInfo();
            if (where == null) {
                forHomeList(request, response);
            } else {
                if (where.startsWith("/build")) {
                    forNewMsg(request, response);
                } else if (where.startsWith("/reply")) {
                    forReply(request, response);
                } else {
                    response.setStatus(404);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/account");
        } else {
            user = (User) request.getSession().getAttribute("user");
            String where = request.getPathInfo();
            if (where == null) {
                forHomeList(request, response);
            } else {
                if (where.startsWith("/my")) {
                    forMeSend(request, response);
                } else if (where.startsWith("/detail")) {
                    forDetail(request, response);
                } else if (where.startsWith("/fingerout")) {
                    forSearch(request, response);
                } else if(where.equals("/del")) {
                    forDelete(request, response);
                } else {
                    response.setStatus(404);
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("In Put");
        System.out.println("Args="+req.getParameter("val"));
    }

    /**
     * Handle The Home Index Action, That is Msg List Page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forHomeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        queryPagedList(false, request, response);
    }

    /**
     * Handle The Action That Query The Msg List By Sender
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forMeSend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        queryPagedList(true, request, response);
    }

    /**
     * Handle The Action To Look Detail Of Msg
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msgIdString = request.getParameter("msg");
        String pageString = request.getParameter("page");
        long msgId = 0;
        int page = 1;
        if (pageString != null) {
            try {
                page = Integer.parseInt(pageString);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        if (msgIdString != null) {
            try {
                msgId = Long.parseLong(msgIdString);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        MsgDetail detail = new ReplyService(user).queryMsg(msgId, page, 9);
        // Only Session
        request.getSession().setAttribute("detail", detail);
        request.getRequestDispatcher("/detail.jsp").forward(request, response);
    }

    /**
     * Action To Search Msg By Keyword
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("key");
        String pageString = request.getParameter("page");
        int page = 1;
        if (pageString != null) {
            try {
                page = Integer.parseInt(pageString);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        MsgHome home = new MessageService(user).queryTitle(keyword, page, 12);
        request.setAttribute("home", home);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    /**
     * Action To Put A New Msg (Json)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forNewMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String msgString = request.getParameter("msg");
        String titleString = request.getParameter("title");
        if (msgString == null || msgString.trim().length() < 1 || titleString == null || titleString.trim().length() < 1) {
            response.getWriter().write("{\"success\":\"bad\",\"data\":\"The operation is illegal.\"}");
        } else {
            if (new MessageService(user).addMsg(titleString, msgString)) {
                response.getWriter().write("{\"success\":\"ok\"}");
            } else {
                response.getWriter().write("{\"success\":\"bad\",\"data\":\"The operation is illegal. Maybe caused by a expired page.\"}");
            }
        }
    }

    /**
     * Action To Put A Reply (Json)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String msgString = request.getParameter("msg");
        String targetString = request.getParameter("target");
        Long target = 0L;
        if (targetString != null) {
            try {
                target = Long.parseLong(targetString);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        if (target < 1)
            target = null;
        if (msgString == null || msgString.trim().length() < 1) {
            response.getWriter().write("{\"success\":\"bad\",\"data\":\"The operation is illegal.\"}");
        } else {
            if (request.getSession().getAttribute("detail") != null) {
                MsgDetail detail = (MsgDetail) request.getSession().getAttribute("detail");
                if (new ReplyService(user).reply(detail.getMessage().getMsg_id(), msgString, target)) {
                    response.getWriter().write("{\"success\":\"ok\"}");
                } else {
                    response.getWriter().write("{\"success\":\"bad\",\"data\":\"The operation is illegal. Maybe caused by a expired page.\"}");
                }
            } else {
                response.getWriter().write("{\"success\":\"bad\",\"data\":\"The page was expired. Please refresh it and try again.\"}");
            }
        }
    }

    /**
     * Query The Detail Info Of Msg
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        int id = 0;
        if (idString != null) {
            try {
                id = Integer.parseInt(idString);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        if(id>0)
            new ReplyService(user).deleteReply(id);
        response.sendRedirect("/home.jsp");
    }

    /**
     * Query Paged Msg
     * @param forUser is this query for current user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void queryPagedList(boolean forUser, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageString = request.getParameter("page");
        int page = 1;
        if (pageString != null) {
            try {
                page = Integer.parseInt(pageString);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        MsgHome home;
        if (forUser)
            home = new MessageService(user).queryMy(page, 12);
        else
            home = new MessageService(user).queryPaged(page, 2);
        request.setAttribute("home", home);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
