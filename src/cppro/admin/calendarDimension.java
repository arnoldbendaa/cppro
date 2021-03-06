package cppro.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cppro.beans.UserAccount;
import cppro.utils.MyUtils;

/**
 * Servlet implementation class calendarDimension
 */
@WebServlet("/calendarDimension")
public class calendarDimension extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public calendarDimension() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        request.setAttribute("base_url", MyUtils.base_url);
        request.setAttribute("sidebar", "calendarDimension");
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/calendarDimension.jsp");
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
			String sql = "select * from dimension where type=3 order by VIS_ID";
			try {
				pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
				while(rs.next()){
					temp = new JSONObject();
					int id = rs.getInt("DIMENSION_ID");
					temp.put("0",id);
					temp.put("1",rs.getString("VIS_ID").split("-")[0]);
					temp.put("2",rs.getString("VIS_ID"));
					temp.put("3",rs.getString("VIS_ID"));
					temp.put("4",rs.getString("DESCRIPTION"));
					temp.put("5","<button class='btn-warning' onclick='editDimension();'>Open</button>&nbsp;&nbsp;<button class='btn-danger' onclick='deleteDimension();'>delete</button>");
					items.add(temp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().append(items.toString()).flush();
		}else if(operation.equals("add")){
			String dimensionVisId = (String)request.getParameter("dimensionVisId");
			String dimensionDesc = (String)request.getParameter("dimensionDesc");
			String strElements = (String)request.getParameter("elements");
			String model = (String)request.getParameter("model");
			model = model.equals("Unassigned")?"1/1":model;
			JSONArray jsonArray = null;
			try {
				jsonArray=new JSONArray(strElements);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			String sql = "insert into dimension (vis_id,description,type,external_system_ref,null_element_id,version_num,updated_by_user_id,updated_time,created_time)"
					+ " values (?,?,3,10,null,0,?,?,?)";
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, dimensionVisId);
				pstm.setString(2, dimensionDesc);
				pstm.setInt(3, userId);
				pstm.setTimestamp(4, timestamp);
				pstm.setTimestamp(5, timestamp);
				pstm.executeUpdate();
				sql = "select dimension_id from dimension where CREATED_TIME=? and updated_by_user_id=?";
				pstm = conn.prepareStatement(sql);
				pstm.setTimestamp(1, timestamp);
				pstm.setInt(2, userId);
				ResultSet rs = pstm.executeQuery();
				if(rs.next()){
					int dimensionId = rs.getInt("DIMENSION_ID");
					int elementLength = jsonArray.length();
					for(int i=0; i<elementLength;i++){
						JSONObject row = jsonArray.getJSONObject(i);
						int year = row.getInt("year");
						JSONArray temp = row.getJSONArray("datas");
						String yearInd = temp.getString(0);
						String monthInd = temp.getString(1);
						String openingBalanceInd = temp.getString(2);
						System.out.println(row);
						sql = "insert into CALENDAR_YEAR_SPEC "
								+ "(dimension_id,CALENDAR_YEAR,YEAR_IND,HALF_YEAR_IND,QUARTER_IND,MONTH_IND,WEEK_IND,DAY_IND,OPENING_BALANCE_IND,ADJUSTMENT_IND,PERIOD_13_IND,PERIOD_14_IND) values"
								+"(?,?,?,'N','N',?,'N','N',?,'N','N','N')";
						pstm = conn.prepareStatement(sql);
						pstm.setInt(1, dimensionId);
						pstm.setInt(2, year);
						pstm.setString(3, yearInd);
						pstm.setString(4, monthInd);
						pstm.setString(5, openingBalanceInd);
						pstm.executeQuery();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().append("Success").flush();	
		}else if(operation.equals("getInfo")){
			int dimensionId = Integer.parseInt(request.getParameter("id"));
			String visId = "",description="";
			Collection<JSONObject>items = new ArrayList<JSONObject>();
			JSONObject temp = new JSONObject();

			String sql = "select * from dimension where DIMENSION_ID=?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				ResultSet rs = pstm.executeQuery();
				if(rs.next()){
					visId = rs.getString("VIS_ID");
					description = rs.getString("description");
				}
				sql = "select * from CALENDAR_YEAR_SPEC where DIMENSION_ID=? ORDER BY CALENDAR_YEAR";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				rs = pstm.executeQuery();
				while(rs.next()){
					int id = rs.getInt("CALENDAR_YEAR_SPEC_ID");
					temp = new JSONObject();
					String year = rs.getString("CALENDAR_YEAR");
					String yearInd = rs.getString("YEAR_IND");
					String monthInd = rs.getString("MONTH_IND");
					String openBalanceInd = rs.getString("OPENING_BALANCE_IND");
					temp.put("year", year);
					JSONArray data = new JSONArray();
					data.put(yearInd);
					data.put(monthInd);
					data.put(openBalanceInd);
					temp.put("datas", data);
					items.add(temp);
				}
				JSONObject result = new JSONObject();
				result.put("vis_id", visId);
				result.put("desc", description);
				result.put("elements", items);
				response.getWriter().append(result.toString()).flush();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if(operation.equals("editDimension")){
			//get parameters
			String dimensionVisId = (String)request.getParameter("dimensionVisId");
			String dimensionDesc = (String)request.getParameter("dimensionDesc");
			String strElements = (String)request.getParameter("elements");
			int dimensionId = Integer.parseInt(request.getParameter("id"));
			String model = (String)request.getParameter("model");
			
			JSONArray jsonArray = null;
			try {
				jsonArray=new JSONArray(strElements);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			String sql = "Update Dimension set vis_id=?,description=?,updated_time=? where dimension_id=?";
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, dimensionVisId);
				pstm.setString(2, dimensionDesc);
				pstm.setTimestamp(3, timestamp);
				pstm.setInt(4, dimensionId);
				pstm.executeUpdate();
				//delete exist items;
				sql = "delete from CALENDAR_YEAR_SPEC where dimension_id=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				pstm.executeQuery();
				
					int elementLength = jsonArray.length();
					for(int i=0; i<elementLength;i++){
						JSONObject row = jsonArray.getJSONObject(i);
						int year = row.getInt("year");
						JSONArray temp = row.getJSONArray("datas");
						String yearInd = temp.getString(0);
						String monthInd = temp.getString(1);
						String openingBalanceInd = temp.getString(2);
						System.out.println(row);
						sql = "insert into CALENDAR_YEAR_SPEC "
								+ "(dimension_id,CALENDAR_YEAR,YEAR_IND,HALF_YEAR_IND,QUARTER_IND,MONTH_IND,WEEK_IND,DAY_IND,OPENING_BALANCE_IND,ADJUSTMENT_IND,PERIOD_13_IND,PERIOD_14_IND) values"
								+"(?,?,?,'N','N',?,'N','N',?,'N','N','N')";
						pstm = conn.prepareStatement(sql);
						pstm.setInt(1, dimensionId);
						pstm.setInt(2, year);
						pstm.setString(3, yearInd);
						pstm.setString(4, monthInd);
						pstm.setString(5, openingBalanceInd);
						pstm.executeQuery();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().append("Success").flush();	
		}else if(operation.equals("delete")){
			int dimensionId = Integer.parseInt(request.getParameter("id"));
			String sql = "delete from dimension where DIMENSION_ID=?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				pstm.executeQuery();
				sql = "delete from CALENDAR_YEAR_SPEC where dimension_id=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				pstm.executeQuery();
				response.getWriter().append("Success").flush();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
