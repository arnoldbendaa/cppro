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
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cppro.beans.UserAccount;
import cppro.utils.MyUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Servlet implementation class accountDimension
 */
@WebServlet("/accountDimension")
public class accountDimension extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accountDimension() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        request.setAttribute("base_url", MyUtils.base_url);
        request.setAttribute("sidebar", "accountDimension");

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/accountDimension.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		HttpServletRequest req = (HttpServletRequest) request;
		//get user Id 
		HttpSession session = req.getSession(false);
		UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
        Connection conn = MyUtils.getStoredConnection(request);
		int userId = user.getUserId();
//        int userId = 1;
		PreparedStatement pstm;
        String operation = (String) request.getParameter("operation");
		if(operation.equals("add")){//creat account dimension
			//get parameters
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
					+ " values (?,?,1,10,null,0,?,?,?)";
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
						String eleVisId = row.getString("1");
						String eleDesc = row.getString("2");
						int creditDebit = row.getInt("11");
						Boolean disabled = row.getBoolean("10");
						Boolean not_plannable = row.getBoolean("9");
						String strDisabled= disabled==true?"1":"0";
						String strNotPlannable = not_plannable==true?"1":"0";
						String nullElement = row.getBoolean("12")==true?"1":"0";
						System.out.println(row);
						sql = "insert into dimension_element (dimension_id,vis_id,description,credit_debit,disabled,not_plannable,null_element) values"
								+"(?,?,?,?,?,?,?)";
						pstm = conn.prepareStatement(sql);
						pstm.setInt(1, dimensionId);
						pstm.setString(2,eleVisId);
						pstm.setString(3, eleDesc);
						pstm.setInt(4,creditDebit);
						pstm.setString(5, strDisabled);
						pstm.setString(6, strNotPlannable);
						pstm.setString(7, nullElement);
						pstm.executeQuery();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().append("Success").flush();	
		}else if(operation.equals("read")){
			Collection<JSONObject>items = new ArrayList<JSONObject>();
			JSONObject temp = new JSONObject();
			String sql = "select * from dimension where type=1 order by VIS_ID";
			try {
				pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
				while(rs.next()){
					temp = new JSONObject();
					int id = rs.getInt("DIMENSION_ID");
					temp.put("0",id);
					temp.put("1",rs.getString("VIS_ID").split("-")[0]);
					temp.put("2",rs.getString("VIS_ID"));
					temp.put("3",rs.getString("DESCRIPTION"));
					temp.put("4",1);//sequence column
					temp.put("5",1);//hierarchies column
					temp.put("6","<button class='btn-warning' onclick='editDimension();'>Open</button>&nbsp;&nbsp;<button class='btn-danger' onclick='deleteDimension();'>delete</button>");
					items.add(temp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().append(items.toString()).flush();
		}else if(operation.equals("delete")){
			int dimensionId = Integer.parseInt(request.getParameter("id"));
			String sql = "delete from dimension where DIMENSION_ID=?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				pstm.executeQuery();
				sql = "delete from dimension_element where dimension_id=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				pstm.executeQuery();
				response.getWriter().append("Success").flush();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
				sql = "select * from dimension_element where dimension_id=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				rs = pstm.executeQuery();
				while(rs.next()){
					int id = rs.getInt("DIMENSION_ELEMENT_ID");
					temp = new JSONObject();
					String item1 = rs.getString("VIS_ID");
					if(item1==null || item1.isEmpty())
						item1 = "";
					String item3 = rs.getString("NOT_PLANNABLE").equals("1")?"<input type='checkbox' checked />":"<input type='checkbox'/>";
					String item4 = rs.getString("DISABLED").equals("1")?"<input type='checkbox' checked />":"<input type='checkbox'/>";
					String item5 = rs.getString("CREDIT_DEBIT").equals("1")?"Credit":"Debit";
					String item6 = rs.getString("NULL_ELEMENT").equals("1")?"<input type='checkbox' checked />":"<input type='checkbox'/>";
					String item7 = "No Override";
					String item8 = "<input type='button' class='btn-danger' value='delete' onclick='deleteRow("+id+");'/>";
					temp.put("0",id);
					temp.put("1",item1);
					temp.put("2",rs.getString("DESCRIPTION"));
					temp.put("3",item3);
					temp.put("4", item4);
					temp.put("5", item5);
					temp.put("6", item6);
					temp.put("7", item7);
					temp.put("8", item8);
					temp.put("9",rs.getString("NOT_PLANNABLE").equals("1")?true:false);//hierarchies column
					temp.put("10",rs.getString("DISABLED").equals("1")?true:false);//hierarchies column
					temp.put("11",rs.getString("CREDIT_DEBIT"));//hierarchies column
					temp.put("12",rs.getString("NULL_ELEMENT").equals("1")?true:false);//hierarchies column
					temp.put("DT_RowId", "row_"+id);
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
				sql = "delete from dimension_element where dimension_id=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, dimensionId);
				pstm.executeQuery();
				
					int elementLength = jsonArray.length();
					for(int i=0; i<elementLength;i++){
						JSONObject row = jsonArray.getJSONObject(i);
						String eleVisId = row.getString("1");
						String eleDesc = row.getString("2");
						int creditDebit = row.getInt("11");
						Boolean disabled = row.getBoolean("10");
						Boolean not_plannable = row.getBoolean("9");
						String strDisabled= disabled==true?"1":"0";
						String strNotPlannable = not_plannable==true?"1":"0";
						String nullElement = row.getBoolean("12")==true?"1":"0";
						System.out.println(row);
						sql = "insert into dimension_element (dimension_id,vis_id,description,credit_debit,disabled,not_plannable,null_element) values"
								+"(?,?,?,?,?,?,?)";
						pstm = conn.prepareStatement(sql);
						pstm.setInt(1, dimensionId);
						pstm.setString(2,eleVisId);
						pstm.setString(3, eleDesc);
						pstm.setInt(4,creditDebit);
						pstm.setString(5, strDisabled);
						pstm.setString(6, strNotPlannable);
						pstm.setString(7, nullElement);
						pstm.executeQuery();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().append("Success").flush();	
		}

	}

}
