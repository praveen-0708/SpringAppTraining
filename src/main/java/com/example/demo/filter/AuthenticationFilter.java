package com.example.demo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class AuthenticationFilter implements Filter {
    private Logger logger = Logger.getLogger(AuthenticationFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session=request.getSession(false);
        if(session!=null){
            filterChain.doFilter(request,response);
            logger.info("valid session");
        }
        else {
            logger.info("user not logged in");
            PrintWriter printWriter=response.getWriter();
            printWriter.write("User not logged in");
            printWriter.close();
        }
    }

    @Override
    public void destroy() {

    }
}
