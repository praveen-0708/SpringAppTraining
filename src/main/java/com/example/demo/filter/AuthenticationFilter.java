package com.example.demo.filter;

import com.example.demo.service.MyResponseWrapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter implements Filter {
    private Logger logger = Logger.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (/*session != null*/true) {
            Long startTime = System.currentTimeMillis();
            MyResponseWrapper responseWrapper = new MyResponseWrapper(response);

            filterChain.doFilter(request, response);

            Long endTime = System.currentTimeMillis();
            long executionTime = (endTime - startTime);

            responseWrapper.addHeader("response-time", executionTime + "ms");

            logger.info(executionTime);
        } else {
            logger.info("user not logged in");
            PrintWriter printWriter = response.getWriter();
            printWriter.write("User not logged in");
            printWriter.close();
        }
    }

    @Override
    public void destroy() {

    }
}

