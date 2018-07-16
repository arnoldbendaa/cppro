package cppro.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cppro.beans.UserAccount;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;

/**
 * Servlet implementation class DataType
 */
@WebServlet(urlPatterns = { "/DataType"})
public class DataType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataType() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        request.setAttribute("base_url", MyUtils.base_url);
        request.setAttribute("sidebar", "dataType");

		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/dataType.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
        String operation = (String) request.getParameter("operation");
        Connection conn = MyUtils.getStoredConnection(request);

		if(operation.equals("add")){//creat data types
			String identifier = (String)request.getParameter("identifier");
			String desc = (String)request.getParameter("desc");
			int subType = Integer.parseInt(request.getParameter("subType"));
			String importData = (String)(request.getParameter("importData"));
			String exportData = (String)(request.getParameter("exportData"));
			String readOnly = (String)(request.getParameter("readOnly"));
			HttpSession session = req.getSession(false);
			UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
			int userId = user.getUserId();
			try {
				DBUtils.insertDataType(conn, userId, identifier, desc, subType, importData, exportData, readOnly);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(operation.equals("edit")){
			int type_id = Integer.parseInt(request.getParameter("type_id"));
			String identifier = (String)request.getParameter("identifier");
			String desc = (String)request.getParameter("desc");
			int subType = Integer.parseInt(request.getParameter("subType"));
			String importData = (String)(request.getParameter("importData"));
			String exportData = (String)(request.getParameter("exportData"));
			String readOnly = (String)(request.getParameter("readOnly"));
			HttpSession session = req.getSession(false);
			UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
			//int userId = user.getUserId();
			
		      String sql = "Update DATA_TYPE set VIS_ID =?, DESCRIPTION=?, READ_ONLY_FLAG=?, "+
		      "AVAILABLE_FOR_IMPORT=?,AVAILABLE_FOR_EXPORT=?,SUB_TYPE=? where DATA_TYPE_ID=? ";
		      try {
			      PreparedStatement pstm = conn.prepareStatement(sql);
			      pstm.setString(1, identifier);
			      pstm.setString(2, desc);
			      pstm.setString(3, readOnly);
			      pstm.setString(4, importData);
			      pstm.setString(5, exportData);
			      pstm.setInt(6, subType);
			      pstm.setInt(7,type_id);			      
			      pstm.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(operation.equals("delete")){
			int type_id = Integer.parseInt(request.getParameter("type_id"));
	        String sql = "Delete DATA_TYPE where DATA_TYPE_ID= ?";
	        try {
		        PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setInt(1, type_id);
		        pstm.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
		}
	}

}
