package com.cedar.cp.utc.common;

import javax.servlet.http.HttpServletRequest;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

public class BrowserUtil {

    public static boolean isUnsupportedBrowser(HttpServletRequest servletRequest) {
        Boolean blockBrowser = (Boolean) servletRequest.getSession().getAttribute("blockBrowser");
        if (blockBrowser == null) {

            // get browser
            UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
            ReadableUserAgent agent = parser.parse(servletRequest.getHeader("User-Agent"));
            String browser = agent.getFamily().getName();

            // get browser properties
            CPSystemProperties systemProperties = (CPSystemProperties) servletRequest.getSession().getServletContext().getAttribute("cpSystemProperties");
            boolean block = false;

            // check if browser is blocked in system
            if (systemProperties != null) {

                servletRequest.getSession().setAttribute("disabledChrome", systemProperties.isDisabledChrome());
                servletRequest.getSession().setAttribute("disabledFirefox", systemProperties.isDisabledFF());
                servletRequest.getSession().setAttribute("disabledIE", systemProperties.isDisabledIE());

                Object[] browserInfo = new Object[2];
                browserInfo[0] = browser;
                browserInfo[1] = agent.getVersionNumber().getMajor();
                servletRequest.getSession().setAttribute("browserInfo", browserInfo);
                if (browser.equals("Firefox")) {
                    block = systemProperties.isDisabledFF();
                } else if (browser.equals("Chrome") || browser.equals("Chromium") || browser.equals("Chrome Mobile")) {
                    block = systemProperties.isDisabledChrome();
                } else if (browser.equals("IE")) {
                    block = systemProperties.isDisabledIE();
                    // block when IE older than 10
                    try {
                        if (Double.parseDouble((String) browserInfo[1]) < 10) {
                            block = true;
                        }
                    } catch (Exception e) {
                    }

                } else if (browser.equals("unknown")) {
                    // administration panel
                    return false;
                } else {
                    // block other browsers
                    block = true;
                }
            }
            servletRequest.getSession().setAttribute("blockBrowser", block);
            blockBrowser = block;
        }
        return blockBrowser;
    }
}
