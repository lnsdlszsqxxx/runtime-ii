package com.ascending.training.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "Zhi", urlPatterns = "/*")
public class AFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{

        System.out.println("Afilter before");
        filterChain.doFilter(request,response);
        System.out.println("Afilter after");

    }

}
