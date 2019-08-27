package com.ascending.training.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import javax.servlet.*;

@WebFilter(filterName = "security", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig){
        logger.info("initializer");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        HttpServletRequest req = (HttpServletRequest) request;

        logger.info(String.format("Verb: %s, URL: %s", req.getMethod(), req.getRequestURI()));
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy(){}
}
