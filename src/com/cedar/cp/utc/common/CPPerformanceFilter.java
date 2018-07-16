package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.util.Log;
import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CPPerformanceFilter implements Filter {

    private static Log sLog = new Log(CPPerformanceFilter.class);
    public static int mPerformLogLevel;

    public void init(FilterConfig config) throws ServletException {
        sLog.info("init", "start performance logger");
        CPSystemProperties systemProperties = (CPSystemProperties) config.getServletContext().getAttribute("cpSystemProperties");
        mPerformLogLevel = systemProperties.getPerformanceLogLevel();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        doLog(request, start);
    }

    private static void doLog(ServletRequest request, long start) {
        if (mPerformLogLevel == 1) {
            long finish = System.currentTimeMillis();
//            if (mLogger != null) {
//                HttpServletRequest servletRequest = (HttpServletRequest) request;
//                mLogger.log("CP", servletRequest.getServletPath(), -1, finish - start, (new Date()).getTime());
//            }
        }

    }

    public void destroy() {
        sLog.info("destroy", "destroy performance logger");
    }
}
