package cppro.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import com.softproideas.commons.context.CPContextHolder;

import cppro.conn.ConnectionUtils;
import cppro.utils.MyUtils;

@WebFilter(filterName = "JDBCFilter", urlPatterns = { "/*" })
public class JDBCFilter implements Filter {
	private ServletContext context;
    @Autowired
    CPContextHolder cpContextHolder;
    protected transient DataSource mDataSource;

	public JDBCFilter() {
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
	}

	@Override
	public void destroy() {

	}

	// Check the target of the request is a servlet?
	private boolean needJDBC(HttpServletRequest request) {
		System.out.println("JDBC Filter");
		//
		// Servlet Url-pattern: /spath/*
		//
		// => /spath
		String servletPath = request.getServletPath();
		// => /abc/mnp
		String pathInfo = request.getPathInfo();

		String urlPattern = servletPath;

		if (pathInfo != null) {
			// => /spath/*
			urlPattern = servletPath + "/*";
		}

		// Key: servletName.
		// Value: ServletRegistration
		Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
				.getServletRegistrations();

		// Collection of all servlet in your webapp.
		Collection<? extends ServletRegistration> values = servletRegistrations.values();
		for (ServletRegistration sr : values) {
			Collection<String> mappings = sr.getMappings();
			if (mappings.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		//
		// Only open connections for the special requests need
		// connection. (For example, the path to the servlet, JSP, ..)
		//
		// Avoid open connection for commons request
		// (for example: image, css, javascript,... )
		//
		String uri = req.getRequestURI();
		
        if ( uri.indexOf(".css") > 0)
            chain.doFilter(request, response);
        else if(uri.indexOf(".js")>0)
        	chain.doFilter(request, response);
        else if(uri.indexOf("/images")>0)
        	chain.doFilter(request, response);
        else if(uri.indexOf("/bower_components")>0)
        	chain.doFilter(request, response);
        else {
    		if (this.needJDBC(req)) {
    			Connection conn = null;
    			try {
    				conn = MyUtils.getStoredConnection(req);
    				if(conn==null){
    					conn = ConnectionUtils.getConnection();
    					conn.setAutoCommit(false);
    					MyUtils.storeConnection(request, conn);
    				}
    				chain.doFilter(request, response);
    				conn.commit();
    			} catch (Exception e) {
    				e.printStackTrace();
    				ConnectionUtils.rollbackQuietly(conn);
    				throw new ServletException();
    			} finally {
    				ConnectionUtils.closeQuietly(conn);
    			}
    		}

    		else {

    			chain.doFilter(request, response);
    		}
        }
		
		

	}

}