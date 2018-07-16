package cppro.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import cppro.beans.UserAccount;
import cppro.utils.MyUtils;

/**
 * Servlet implementation class users
 */
@WebServlet("/users")
public class users extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public users() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        request.setAttribute("base_url", MyUtils.base_url);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/users.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		//get user Id 
		HttpSession session = req.getSession(false);
		UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
        Connection conn = MyUtils.getStoredConnection(request);
		int userId = user.getUserId();
//        int userId = 1;
		PreparedStatement pstm;
        String operation = (String) request.getParameter("operation");
        if(operation.equals("read")){
			Collection<JSONObject>items = new ArrayList<JSONObject>();
			JSONObject temp = new JSONObject();
			String sql = "select * from Model order by VIS_ID";
			try {
				pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
				int i = 0 ; 
				while(rs.next()){
					i++;
					String desc = rs.getString("DESCRIPTION");
					temp = new JSONObject();
					int id = rs.getInt("MODEL_ID");
					temp.put("0",i);
					temp.put("1",rs.getString("VIS_ID"));
					temp.put("2",desc==null?" ":desc);
					temp.put("3","<input type='checkbox' />");
					temp.put("4","<button class='btn-warning' onclick='editDimension();'>Open</button>&nbsp;&nbsp;<button class='btn-danger' onclick='deleteDimension();'>delete</button>");
					temp.put("5", id);
					items.add(temp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().append(items.toString()).flush();

        }
	}

}
