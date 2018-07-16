package cppro.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import cppro.beans.UserAccount;
import cppro.utils.MyUtils;

/**
 * Servlet implementation class ExternalSystem
 */
@WebServlet("/ExternalSystem")
public class ExternalSystem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExternalSystem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		request.setAttribute("base_url", MyUtils.base_url);
		request.setAttribute("sidebar", "inputOutput");

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/admin/externalSystem.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		HttpServletRequest req = (HttpServletRequest) request;
		// get user Id
		// HttpSession session = req.getSession(false);
		// UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
		Connection conn = MyUtils.getStoredConnection(request);
		// int userId = user.getUserId();
		int userId = 1;
		PreparedStatement pstm;
		String operation = (String) request.getParameter("operation");
		if (operation.equals("read")) {
			Collection<JSONObject> items = new ArrayList<JSONObject>();
			JSONObject temp = new JSONObject();
			String sql = "select * from EXTERNAL_SYSTEM ORDER BY VIS_ID";
			try {
				pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
				while (rs.next()) {
					temp = new JSONObject();
					int id = rs.getInt("EXTERNAL_SYSTEM_ID");
					int systemType = rs.getInt("SYSTEM_TYPE");
					String visId = rs.getString("VIS_ID");
					String description = rs.getString("DESCRIPTION");
					String location = rs.getString("location");
					String enabled = rs.getString("ENABLED");
					String connectorClass = rs.getString("CONNECTOR_CLASS");

					temp.put("0", id);
					temp.put("1", visId);
					temp.put("2", systemType);
					temp.put("3", description);
					temp.put("4", location);
					temp.put("5", enabled);
					temp.put("6", connectorClass);
					items.add(temp);
				}
				response.getWriter().append(items.toString()).flush();
			} catch (Exception ex) {

			}
		} else if (operation.equals("add")) {
			String visId = (String) request.getParameter("visId");
			int systemType = Integer.parseInt(request.getParameter("systemType"));
			String description = (String) request.getParameter("description");
			String connectorClass = (String) request.getParameter("connectorClass");
			String location = (String) request.getParameter("location");
			String enabled = (String) request.getParameter("enabled");
			String sql = "INSERT INTO EXTERNAL_SYSTEM (SYSTEM_TYPE,VIS_ID,DESCRIPTION,LOCATION,CONNECTOR_CLASS,ENABLED,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME)"
					+ "VALUES (?,?,?,?,?,?,1,?,?,? )";
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, systemType);
				pstm.setString(2, visId);
				pstm.setString(3, description);
				pstm.setString(4, location);
				pstm.setString(5, connectorClass);
				pstm.setString(6, enabled);
				pstm.setInt(7, userId);
				pstm.setTimestamp(8, timestamp);
				pstm.setTimestamp(9, timestamp);
				pstm.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
			response.getWriter().append("Success").flush();
		} else if (operation.equals("edit")) {
			int externalSystemId = Integer.parseInt(request.getParameter("externalSystemId"));
			String visId = (String) request.getParameter("visId");
			int systemType = Integer.parseInt(request.getParameter("systemType"));
			String description = (String) request.getParameter("description");
			String connectorClass = (String) request.getParameter("connectorClass");
			String location = (String) request.getParameter("location");
			String enabled = (String) request.getParameter("enabled");
			String sql = "UPDATE EXTERNAL_SYSTEM SET SYSTEM_TYPE=?,VIS_ID=?,DESCRIPTION=?,LOCATION=?,CONNECTOR_CLASS=?,ENABLED=?,UPDATED_BY_USER_ID=? WHERE EXTERNAL_SYSTEM_ID=?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, systemType);
				pstm.setString(2, visId);
				pstm.setString(3, description);
				pstm.setString(4, location);
				pstm.setString(5, connectorClass);
				pstm.setString(6, enabled);
				pstm.setInt(7, userId);
				pstm.setInt(8, externalSystemId);
				pstm.executeUpdate();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			response.getWriter().append("Success").flush();
		} else if (operation.equals("delete")) {
			int externalSystemId = Integer.parseInt(request.getParameter("id"));
			String sql = "DELETE FROM EXTERNAL_SYSTEM WHERE EXTERNAL_SYSTEM_ID=?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, externalSystemId);
				pstm.executeQuery();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			response.getWriter().append("Success").flush();
		}
	}

}
