package cppro.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import cppro.beans.Product;
import cppro.utils.MyUtils;

/**
 * Servlet implementation class getDataTypes
 */
@WebServlet(urlPatterns = { "/getDataTypes"})
public class getDataTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getDataTypes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        Connection conn = MyUtils.getStoredConnection(request);
        String sql = "select * from DATA_TYPE order by created_time";
        
        PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
	        ResultSet rs = pstm.executeQuery();
	        JSONArray jArray  = new JSONArray();
	        while(rs.next()){
		        JSONArray temp = new JSONArray();
	        	temp.put(rs.getString("DATA_TYPE_ID"));//0
	        	temp.put(rs.getString("VIS_ID"));
	        	temp.put(rs.getString("DESCRIPTION"));
	        	temp.put(rs.getString("READ_ONLY_FLAG"));//3
	        	temp.put(rs.getString("AVAILABLE_FOR_IMPORT"));
	        	temp.put(rs.getString("AVAILABLE_FOR_EXPORT"));
	        	temp.put(rs.getInt("SUB_TYPE"));
	        	jArray.put(temp);
	        }
	        response.getWriter().append(jArray.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
