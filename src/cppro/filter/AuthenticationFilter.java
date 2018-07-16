package cppro.filter;

import java.io.File;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {

	private ServletContext context;

    /**
     * Default constructor. 
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub

    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();

		HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/login";
        String homeUri = req.getContextPath() + "/homePage.do";
        String doLoginURI = req.getContextPath()+"/doLogin";
        String imageURI = req.getContextPath()+"/images";

        boolean loggedIn = session != null && session.getAttribute("loginedUser") != null;
        boolean loginRequest = req.getRequestURI().equals(loginURI);
        boolean doLoginRequest = req.getRequestURI().equals(doLoginURI);
        boolean isImage = req.getRequestURI().equals(imageURI);
        if ( uri.indexOf(".css") > 0)
            chain.doFilter(request, response);
        else if(uri.indexOf(".js")>0)
        	chain.doFilter(request, response);
        else if(uri.indexOf("/images")>0)
        	chain.doFilter(request, response);
        else if(uri.indexOf("/bower_components")>0)
        	chain.doFilter(request, response);
        if ((loggedIn || loginRequest || doLoginRequest)) {
        	if(loggedIn&&uri.indexOf("cppro/login")>0)
        		((HttpServletResponse) response).sendRedirect(homeUri);
        	else 
        		chain.doFilter(request, response);
        } else {
        	System.out.println("by authentication filter web site redirect to login");
            ((HttpServletResponse) response).sendRedirect(loginURI);
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.context = fConfig.getServletContext();

	}

}
