package cppro.admin;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;

import cppro.utils.MyUtils;

/**
 * Servlet implementation class SystemPropery
 */
@WebServlet("/SystemPropery")
public class SystemPropery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemPropery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("base_url", MyUtils.base_url);
        request.setAttribute("sidebar", "systemProperty");

		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/systemProperty.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);
		String operation = (String)request.getParameter("operation");
		PreparedStatement pstm;
		if(operation.equals("read")){
			JSONArray jArray  = new JSONArray();
			JSONObject temp = new JSONObject();
            String sql = "select * from SYSTEM_PROPERTY order by prprty";
			try {
				pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
		        int i = 0;
				while(rs.next()){
					temp = new JSONObject();
					String id = rs.getString("SYSTEM_PROPERTY_ID");
					String value = rs.getString("value");
					if(value==null) value="";
					i++;
		        	temp.put("0",i);//0
		        	temp.put("1",rs.getString("prprty"));
		        	temp.put("2",value);
		        	temp.put("3",rs.getString("DESCRIPTION"));
		        	temp.put("4","<button class='btn-warning' onclick='editProperty();'>Open</button>");
					temp.put("DT_RowId", "row_"+id);
		        	jArray.put(temp);		
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject result = new JSONObject();
	        try {
				result.put("data",jArray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        response.getWriter().append(result.toString());
		}else if(operation.equals("edit")){
			String value = (String)request.getParameter("value");
			int Id = Integer.parseInt(request.getParameter("id"));
            String sql = "update system_property set value=? where SYSTEM_PROPERTY_ID=?";
			
            try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, value);
				pstm.setInt(2, Id);
				pstm.executeUpdate();
		        response.getWriter().append("Success");
		        
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
