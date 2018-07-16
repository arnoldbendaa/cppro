/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
/**
 * 
 */
package com.softproideas.commons.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.GenericFilterBean;

/**
 * <p>
 * TODO: Comment me
 * </p>
 * 
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class SessionTimeoutCookieFilter extends GenericFilterBean {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpSession session = httpReq.getSession(false);
        if (session != null) {
            long currTime = System.currentTimeMillis();
            long expiryTime = currTime + session.getMaxInactiveInterval() * 1000;
            String cookieValue = "%7B%22sessionExpiry%22%3A" + expiryTime + "%7D"; // "%7B%22sessionExpiry%22%3A" + expiryTime + "%2C%22path%22%3A%22" + "/" + "%22%7D";

            Cookie cookie = getCookie(httpReq, "sessionExpiry2");

            String domain = httpReq.getServerName().replaceAll(".*\\.(?=.*\\.)", "");
            if (cookie != null) {
                cookie.setValue(cookieValue);
                cookie.setDomain(domain);
                cookie.setPath("/");
                cookie.setMaxAge(120 * 60);
                httpResp.addCookie(cookie);
            } else {
                Cookie newCookie = new Cookie("sessionExpiry2", cookieValue);
                newCookie.setDomain(domain);
                newCookie.setPath("/");
                newCookie.setMaxAge(120 * 60);
                httpResp.addCookie(newCookie);
            }
        }

        filterChain.doFilter(req, resp);
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie: request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    public static List<Cookie> getCookies(HttpServletRequest request, String name) {
        List<Cookie> cookies = new ArrayList<Cookie>();
        if (request.getCookies() != null) {
            for (Cookie cookie: request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    cookies.add(cookie);
                }
            }
        }
        return cookies;
    }

}
