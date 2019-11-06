package com.ascending.training.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "zlog", urlPatterns = "/*")
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        System.out.println("This is from Log filter before");

        //test listener
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpSession session = req.getSession(); //sessionCreated is executed
//        session.setAttribute("url", "mkyong.com"); //attributeAdded() is executed
//        session.setAttribute("url", "mkyong2.com"); //attributeReplaced() is executed
//        session.removeAttribute("url"); //attributeRemoved() is executed

        filterChain.doFilter(request,response);
        System.out.println("This is from Log filter after");
    }
}
