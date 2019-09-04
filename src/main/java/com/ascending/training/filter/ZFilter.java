package com.ascending.training.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "Afilter", urlPatterns = "/*")
public class ZFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        System.out.println("ZFilter before");
        filterChain.doFilter(request,response);
        System.out.println("ZFilter after");
    }
}
