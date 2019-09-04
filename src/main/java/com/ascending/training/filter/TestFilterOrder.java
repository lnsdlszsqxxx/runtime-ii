package com.ascending.training.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "test", urlPatterns = "/*")
public class TestFilterOrder implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("this is from test filter before");
        filterChain.doFilter(request,response);
        System.out.println("this is from test filter after");

    }
}
