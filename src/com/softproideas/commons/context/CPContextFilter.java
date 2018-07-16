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
package com.softproideas.commons.context;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import com.cedar.cp.utc.common.CPContext;

/**
 * Filter used to get CPContext from HttpSession and set to CPContextHolder
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class CPContextFilter extends GenericFilterBean {

    @Autowired
    CPContextHolder cpContextHolder;
    
    private Set<String> ignorePaths;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	String contextPath = ((HttpServletRequest) request).getContextPath();
    	String path = ((HttpServletRequest) request).getRequestURI();
    	
    	boolean ignore = false;
    	if (ignorePaths!=null) {
    		for(String ignorePath : ignorePaths){
    			if(path.startsWith(contextPath+ignorePath)){
    				ignore = true;
    				break;
    			}
    		}
    	} 
    	
    	if(!ignore && request instanceof HttpServletRequest){
    		HttpSession session = ((HttpServletRequest) request).getSession();
    		CPContext cpContext = (CPContext) session.getAttribute("cpContext");
    		if(cpContext!=null)
    			cpContextHolder.init(cpContext);
    	}
        chain.doFilter(request, response);
    }

	public Set<String> getIgnorePaths() {
		return ignorePaths;
	}

	public void setIgnorePaths(Set<String> ignorePaths) {
		this.ignorePaths = ignorePaths;
	}

}
