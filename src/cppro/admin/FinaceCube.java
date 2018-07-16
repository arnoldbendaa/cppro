package cppro.admin;

import java.io.IOException;
import java.sql.Connection;
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

import cppro.utils.MyUtils;

/**
 * Servlet implementation class FinaceCube
 */
@WebServlet("/FinaceCube")
public class FinaceCube extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinaceCube() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("base_url", MyUtils.base_url);
        request.setAttribute("sidebar", "financeCube");

		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/financeCube.jsp");
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
			JSONArray temp = new JSONArray();
            String sql = "select FINANCE_CUBE.*,model.VIS_ID as modelId from FINANCE_CUBE left join model on FINANCE_CUBE.model_id = model.MODEL_ID order by FINANCE_CUBE.vis_id";
			try {
				pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
		        int i = 0;
				while(rs.next()){
					temp = new JSONArray();
					i++;
		        	temp.put(i);//0
		        	temp.put(rs.getString("modelId"));
		        	temp.put(rs.getString("vis_id"));
		        	temp.put(rs.getString("DESCRIPTION"));
		        	temp.put("<button class='btn-warning' onclick='editDimension();'>Open</button>&nbsp;&nbsp;<button class='btn-danger' onclick='deleteDimension();'>delete</button>");
		        	jArray.put(temp);		
		        }
			} catch (SQLException e) {
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
		}
	}

}
