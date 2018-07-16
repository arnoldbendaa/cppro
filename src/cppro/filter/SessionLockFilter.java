package cppro.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.JsonParser;
import com.softproideas.app.flatformeditor.form.model.FlatFormDetailsDTO;
import com.cedar.cp.utc.common.CPContext;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import cppro.conn.ConnectionUtils;
import cppro.utils.MyUtils;

/**
 * Servlet Filter implementation class SessionLockFilter
 */
//@WebFilter(filterName = "/SessionLockFilter",urlPatterns = { "/flatFormEditor/*" })
public class SessionLockFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SessionLockFilter() {
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
		 
		 
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String type = req.getMethod();
		String uri = req.getRequestURI();
		HttpSession session = req.getSession(false);
		CPContext context = (CPContext)session.getAttribute("cpContext");
		int lockUserId =0;
		java.sql.Timestamp lockTime = null;
		String loginURI = req.getContextPath() + "/login";
		
		
		//
		RequestWrapper wrappedRequest = new RequestWrapper(req);
		request = wrappedRequest;
		
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(context==null)
			 ((HttpServletResponse) response).sendRedirect(loginURI);
		int userId = context.getIntUserId();
		String queryForLockTime = "select value from system_property where prprty='WEB: Session Lock Time'";
		int lockLimit = 30;
		try {
			PreparedStatement pstmlock = conn.prepareStatement(queryForLockTime);
			ResultSet resultSetForLock = pstmlock.executeQuery();
			if(resultSetForLock.next()){
				lockLimit = resultSetForLock.getInt(1);
			}
			resultSetForLock.close();
			pstmlock.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//check 1 
		if(type.equalsIgnoreCase("get")&&uri.contains("/flatFormEditor/flatForm/")){
			int index = uri.indexOf("flatFormEditor/flatForm");
			String xmlFormId = uri.substring(index);
			xmlFormId = xmlFormId.replace("flatFormEditor/flatForm/","");
			xmlFormId = xmlFormId.replace("getLockFlag/", "");
			String query = "select lock_time,user_id from lock_state where xml_form_id="+xmlFormId;
			try {
				PreparedStatement pstm = conn.prepareStatement(query);
				ResultSet resultSet = pstm.executeQuery();
				if(resultSet.next()){
					lockTime = resultSet.getTimestamp(1);
					lockUserId = resultSet.getInt(2);
					java.sql.Timestamp  now = new java.sql.Timestamp(System.currentTimeMillis());
			        long firstTime = (getTimeNoMillis(now) * 1000000) + now.getNanos();
			        long secondTime = (getTimeNoMillis(lockTime) * 1000000) + lockTime.getNanos();
			        long diff = Math.abs(firstTime - secondTime)/1000000000; // diff is in nanos
			        if(diff>lockLimit*60){//30 min unlock
			        	String query2 = "update LOCK_STATE set user_id=" + userId+",LOCK_TIME=CURRENT_TIMESTAMP WHERE xml_form_id= ?";
			        	PreparedStatement pstm2 = conn.prepareStatement(query2);
			        	pstm2.setString(1, xmlFormId);
			        	pstm2.executeQuery();
			        	pstm2.close();
			        }else{
						if(userId==lockUserId){//if lock user then update lock time.
				        	String query2 = "update LOCK_STATE set LOCK_TIME=CURRENT_TIMESTAMP WHERE xml_form_id=?";
				        	PreparedStatement pstm2 = conn.prepareStatement(query2);
				        	pstm2.setString(1, xmlFormId);
				        	pstm2.executeQuery();
				        	pstm2.close();
			        	}
			        }

				}else{
					String query1 = "insert into lock_state (USER_ID,XML_FORM_ID,LOCKED,LOCK_ENABLE,LOCK_TIME) values("+userId+","+xmlFormId+",'Y','N',CURRENT_TIMESTAMP)";
					PreparedStatement pstm1 = conn.prepareStatement(query1);
					pstm1.executeQuery();
					pstm1.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			chain.doFilter(request, response);
		}else if(type.equalsIgnoreCase("put")){
//			String data = MyUtils.getInputStream(req);
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = request.getReader();
			char[] charBuffer = new char[128];
			int bytesRead = -1;
			while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
				stringBuilder.append(charBuffer, 0, bytesRead);
			}
			String data = stringBuilder.toString();
			JsonParser parser = new JsonParser();
			JsonElement mJson = parser.parse(data);
			Gson gson = new Gson();
			FlatFormDetailsDTO flatForm = gson.fromJson(mJson,
					FlatFormDetailsDTO.class);
			int xmlFormId = flatForm.getFlatFormId();
			String query = "select lock_time,user_id,lock_enable from lock_state where xml_form_id="+xmlFormId;
			try {
				PreparedStatement pstm = conn.prepareStatement(query);
				ResultSet resultSet = pstm.executeQuery();
				if(resultSet.next()){
					lockTime = resultSet.getTimestamp(1);
					lockUserId = resultSet.getInt(2);
					String lockEnable= resultSet.getString(3);
					if(lockEnable.equals("Y")){
						java.sql.Timestamp  now = new java.sql.Timestamp(System.currentTimeMillis());
				        long firstTime = (getTimeNoMillis(now) * 1000000) + now.getNanos();
				        long secondTime = (getTimeNoMillis(lockTime) * 1000000) + lockTime.getNanos();
				        long diff = Math.abs(firstTime - secondTime)/1000000000; // diff is in nanos
				        if(diff>lockLimit*60){//30 min unlock
				        	String query2 = "update LOCK_STATE set user_id=" + userId+",LOCK_TIME=CURRENT_TIMESTAMP WHERE xml_form_id=?";
				        	PreparedStatement pstm2 = conn.prepareStatement(query2);
				        	pstm2.setInt(1, xmlFormId);
				        	pstm2.executeQuery();
				        	pstm2.close();
				        	chain.doFilter(request, response);
				        }else{
				        	if(userId==lockUserId){
					        	String query2 = "update LOCK_STATE set user_id=" + userId+",LOCK_TIME=CURRENT_TIMESTAMP WHERE xml_form_id=" + xmlFormId;
					        	PreparedStatement pstm2 = conn.prepareStatement(query2);
					        	pstm2.executeQuery();
					        	pstm2.close();
					        	chain.doFilter(request, response);
				        	}else{
				        		response.getWriter().append("{\"message\":\"Session locked.Please try again later.\",\"title\":\"Session Locked\",\"success\":false,\"data\":{},\"error\":{\"message\":\"test\",\"title\":\"title\"}}").flush();
				        	}
				        }
					}else 
						chain.doFilter(request, response);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}

		}else 
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	private void lockTable(String fieldName){
		Connection conn;
		try {
			conn = ConnectionUtils.getConnection();
	        String query = "update lockstate set locked='Y' where fieldname='"+fieldName+"'";
			PreparedStatement pstm = conn.prepareStatement(query);
			ResultSet resultSet = pstm.executeQuery();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void unlockTable(String fieldName){
		Connection conn;
		try {
			conn = ConnectionUtils.getConnection();
	        String query = "update lockstate set locked='N' where fieldname='"+fieldName+"'";
			PreparedStatement pstm = conn.prepareStatement(query);
			ResultSet resultSet = pstm.executeQuery();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private long getTimeNoMillis(Timestamp t) {
        return t.getTime() - (t.getNanos()/1000000);
    }


}
