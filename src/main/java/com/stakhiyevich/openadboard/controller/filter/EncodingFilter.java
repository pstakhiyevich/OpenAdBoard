package com.stakhiyevich.openadboard.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

@WebFilter(filterName = "characterEncodingFilter", urlPatterns = "/*",
        initParams = @WebInitParam(name = "encoding", value = "UTF-8"))
public class EncodingFilter implements Filter {
    private static final String ENCODING = "encoding";
    private String correctEncoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        correctEncoding = filterConfig.getInitParameter(ENCODING);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestEncoding = servletRequest.getCharacterEncoding();
        if (correctEncoding != null && !correctEncoding.equalsIgnoreCase(requestEncoding)) {
            servletRequest.setCharacterEncoding(correctEncoding);
            servletResponse.setCharacterEncoding(correctEncoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        correctEncoding = null;
    }
}
