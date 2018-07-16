package cppro.servlet;
 
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cppro.beans.UserAccount;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;

import com.cedar.cp.api.base.AdminOnlyException;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.common.SingleEntryPointMismatchException;
import com.softproideas.commons.context.CPContextHolder;

import java.security.Principal;
import edu.umich.auth.cosign.CosignPrincipal;

@WebServlet(urlPatterns = { "/doLogin" })
public class DoLoginServlet extends HttpServlet {
    @Autowired
    CPContextHolder cpContextHolder;
    private WebApplicationContext springContext;

    private static final long serialVersionUID = 1L;
	public enum AuthMethod{
		PASSWORD, PIN, LOGOUT, AUTHORIZE_ACCESS
	}
    public DoLoginServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember= "Y".equals(rememberMeStr);
 
        UserAccount user = null;
        boolean hasError = false;
        String errorString = null;
 
        if (userName == null || password == null
                 || userName.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
              
                user = DBUtils.findUser(conn, userName, password);
                 
                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        
        // If error, forward to /WEB-INF/views/login.jsp
        if (hasError) {
            user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
             
        
            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
 
       
            // Forward to /WEB-INF/views/login.jsp
            RequestDispatcher dispatcher //
            = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
 
            dispatcher.forward(request, response);
        }
     
        // If no error
        // Store user information in Session
        // And redirect to userInfo page.
        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);
             
             // If user checked "Remember me".
            if(remember)  {
                MyUtils.storeUserCookie(response,user);
            }
    
            // Else delete cookie.
            else  {
                MyUtils.deleteUserCookie(response);
            }                       
            
            
        	CPContext cpContext = (CPContext) session.getAttribute("cpContext");
            CPSystemProperties sysProps = this.getCPSystemProperties(request);
    		String userId = user.getUserName();
    		String pass = user.getPassword();
    		
            CPContext t = (CPContext)session.getAttribute("cpContext");
            if(t == null) {
               Principal principal = request.getUserPrincipal();
               CosignPrincipal cosignUser = (CosignPrincipal)principal;
//               sysProps = (CPSystemProperties)request.getSession().getServletContext().getAttribute("cpSystemProperties");
               CPContext context;
//			try {
//				context = CPContext.logon(sysProps, request, cosignUser);
//	               context.setCosignSignon(true);
//	               session.setAttribute("cpContext", context);
//	               session.setMaxInactiveInterval('\ua8c0');
//
//			} catch (UserLicenseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvalidCredentialsException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UserDisabledException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SingleEntryPointMismatchException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            }

    		try {
				cpContext = (CPContext) CPContext.logon(sysProps, request, userId, pass);
				session.setAttribute("cpContext", cpContext);
				cpContextHolder.init(cpContext);
				DBUtils.mContext = cpContext;
			} catch (UserLicenseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidCredentialsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserDisabledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AdminOnlyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SingleEntryPointMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cpContext.setMobileAPIUser(true);
            }
            
            
            // Redirect to userInfo page.
         //   response.sendRedirect(request.getContextPath() + "/userInfo"); 
            response.sendRedirect(request.getContextPath() + "/homePage.do");
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
	private CPSystemProperties getCPSystemProperties(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();

		CPSystemProperties context = (CPSystemProperties) servletContext.getAttribute("cpSystemProperties");
		
		if(context==null){
			CPSystemProperties props = new CPSystemProperties();
			servletContext.setAttribute("cpSystemProperties", props);
//			session.setAttribute("cpSystemProperties", props);
			
			context = (CPSystemProperties) servletContext.getAttribute("cpSystemProperties");
		}
		return context;
	}
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }

}