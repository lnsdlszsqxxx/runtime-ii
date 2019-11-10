package com.ascending.training.filter;
import com.ascending.training.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    Logger logger;

    private static String AUTH_URI = "/auth";

    @Override
    public void init(FilterConfig filterConfig) {
        // TODO Auto-generated method stub
        logger.info("This is from SecurityFilter init");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        int statusCode = authorization(req);

        logger.info("This is from SecurityFilter: "+req.getMethod()+": "+req.getRequestURL());

        System.out.println("This is from Security filter before "+statusCode);

        if (statusCode == HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(request, response);
        else ((HttpServletResponse)response).sendError(statusCode);
        System.out.println("This is from Security filter after");

    }

    public void destroy() {
        // TODO Auto-generated method stub
        logger.info("This is from SecurityFilter destroy");

    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();
//        if (uri.equalsIgnoreCase(AUTH_URI)) return HttpServletResponse.SC_ACCEPTED;
        if (uri.matches("^"+AUTH_URI+".*")) return HttpServletResponse.SC_ACCEPTED;
        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", "");
            System.out.println(token);
            if (token == null || token.isEmpty()) return statusCode;
            Claims claims = JwtUtil.decodeJwtToken(token);
            String allowedResources = "/";
            switch(verb) {
                case "GET"    : allowedResources = (String)claims.get("allowedReadResources");   break;
                case "POST"   : allowedResources = (String)claims.get("allowedCreateResources"); break;
                case "PUT"    : allowedResources = (String)claims.get("allowedUpdateResources"); break;
                case "DELETE" : allowedResources = (String)claims.get("allowedDeleteResources"); break;
            }
            for (String s : allowedResources.split(",")) {
                if (!s.trim().matches("^/.*")) continue;
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }
            logger.info(String.format("Security Filter authorization Verb: %s, allowed resources: %s", verb, allowedResources));
        }
        catch (Exception e) {
            logger.error("Security Filter authorization Exception: "+e.getMessage());
        }
        return statusCode;
    }
}