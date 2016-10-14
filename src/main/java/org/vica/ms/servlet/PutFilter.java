package org.vica.ms.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter for PutMethod Test
 * 经测试，Put/Delete等高级请求方法不能很好的命中，需要额外提供过滤器或拦截器予以特殊处理
 * 这也使得原计划RESTful风格设计破产
 * Created by Vica-tony on 9/11/2016.
 */
@WebFilter(filterName="ServletFilter",urlPatterns="/*")
public class PutFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[Filter] Init");
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response = (HttpServletResponse)arg1;
        System.out.println("[Filter] DoFilter -<"+request.getMethod()+">-"+request.getRequestURI());
        if(request.getMethod().toLowerCase().equals("put")){
            response.setStatus(200);
            Home home = new Home();
            home.doPut(request,response);
            return;
        }
        chain.doFilter(request,response);
    }

    public void destroy() {
        System.out.println("[Filter] Destroy");
    }
}
