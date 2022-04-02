package com.stakhiyevich.openadboard.controller.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class CommandFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
