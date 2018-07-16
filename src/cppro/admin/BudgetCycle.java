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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cppro.utils.MyUtils;

/**
 * Servlet implementation class BudgetCycle
 */
@WebServlet("/BudgetCycle")
public class BudgetCycle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BudgetCycle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("base_url", MyUtils.base_url);
        request.setAttribute("sidebar", "budgetCycle");
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/budgetCycle.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        Connection conn = MyUtils.getStoredConnection(request);
        String operation = (String) request.getParameter("operation");
//        int userId = 1;
		PreparedStatement pstm;

		if(operation.equals("read")){
			JSONArray jArray  = new JSONArray();
			JSONObject temp = new JSONObject();
            String sql = "select budget_cycle.*,model.VIS_ID as modelId from budget_cycle left join model on budget_cycle.model_id = model.MODEL_ID order by budget_cycle.model_id, budget_cycle.vis_id";
			try {
				pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
		        int i = 0;

while(rs.next()){
	temp = new JSONObject();
	String id = rs.getString("BUDGET_CYCLE_ID");
	i++;
	temp.put("0",i);//0
	temp.put("1",rs.getString("modelId"));
	temp.put("2",rs.getString("vis_id"));
	temp.put("3",rs.getString("DESCRIPTION"));
	temp.put("4",rs.getString("status").equals("1")?"In Progress":"Ended");//3
	temp.put("5",rs.getString("PERIOD_FROM_VISID"));
	temp.put("6",rs.getString("PERIOD_TO_VISID"));
	temp.put("7",rs.getString("category"));
	temp.put("8","<button class='btn-warning' onclick='edit();'>Open</button>&nbsp;&nbsp;<button class='btn-danger' onclick='delete();'>delete</button>");
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
	        }        
	}

}
