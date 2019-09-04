package com.ascending.training.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "zlog", urlPatterns = "/*")
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        System.out.println("This is from Log filter before");
        filterChain.doFilter(request,response);
        System.out.println("This is from Log filter after");
    }
}
