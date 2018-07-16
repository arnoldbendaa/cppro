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
 * Servlet implementation class accountHierarchy
 */
@WebServlet("/accountHierarchy")
public class accountHierarchy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accountHierarchy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        request.setAttribute("base_url", MyUtils.base_url);
        request.setAttribute("sidebar", "hierarchy");
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/accountHierarchy.jsp");
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
			Collection<JSONObject>dimensionItems = new ArrayList<JSONObject>();
			JSONObject temp = new JSONObject();
			String sql = "select hierarchy.*,DIMENSION.VIS_ID AS DVIS from hierarchy  LEFT JOIN  DIMENSION ON HIERARCHY.DIMENSION_ID=DIMENSION.DIMENSION_ID where DIMENSION.type=1 order by hierarchy.CREATED_TIME desc";
			try {
				pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
				while(rs.next()){
					temp = new JSONObject();
					int id = rs.getInt("HIERARCHY_ID");
					temp.put("0",id);
					temp.put("1",rs.getString("DVIS").split("-")[0]);
					temp.put("2",rs.getString("DVIS"));
					temp.put("3",rs.getString("VIS_ID"));
					temp.put("4",rs.getString("DESCRIPTION"));
					temp.put("5","<button class='btn-warning' onclick='openHierarchy();'>Open</button>&nbsp;&nbsp;<button class='btn-danger' onclick='deleteHierarchy();'>delete</button>");
					temp.put("DT_RowId", "row_"+id);
					items.add(temp);
				}
				sql = "select * from dimension where type=1 order by CREATED_TIME desc";
				pstm = conn.prepareStatement(sql);
				rs = pstm.executeQuery();
				while(rs.next()){
					temp = new JSONObject();
					int id = rs.getInt("DIMENSION_ID");
					temp.put("0",id);
					temp.put("1",rs.getString("VIS_ID"));
					dimensionItems.add(temp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			JSONObject result = new JSONObject();
			try {
				result.put("dimensions",dimensionItems);
				result.put("hierarchies",items);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().append(result.toString()).flush();
        }else if(operation=="add"){
        	String visId = (String)request.getParameter("visId");
        	String description = (String)request.getParameter("desc");
        	int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));

        	try {
        		//insert in to account hierary table
        		String json = "{\"title\":\"Public - tes\",\"expanded\":true,\"folder\":true,\"credit\":\"1\",\"augCreditDebit\":0,\"children\":[{\"title\":\"New 1487582986606 - .\",\"expanded\":true,\"credit\":\"1\",\"augCreditDebit\":\"0\",\"children\":[{\"title\":\"New 1487582988149 - .\",\"expanded\":true,\"credit\":\"1\",\"augCreditDebit\":\"0\",\"children\":[{\"title\":\"New 1487582989662 - .\",\"expanded\":true,\"credit\":\"1\",\"augCreditDebit\":\"0\"}],\"folder\":true},{\"title\":\"New 1487582988365 - .\",\"expanded\":true,\"credit\":\"1\",\"augCreditDebit\":\"0\"},{\"title\":\"New 1487582988558 - .\",\"expanded\":true,\"credit\":\"1\",\"augCreditDebit\":\"0\"}],\"folder\":true},{\"title\":\"New 1487582986789 - .\",\"expanded\":true,\"credit\":\"1\",\"augCreditDebit\":\"0\"},{\"title\":\"New 1487582986981 - .\",\"expanded\":true,\"credit\":\"1\",\"augCreditDebit\":\"0\"}]}";
				JSONObject elements = new JSONObject(request.getParameter("json"));
//				String sql = "insert into hierarchy (dimension_id,vis_id,description,version_num,updated_by_user_id,updated_time,created_time) values(?,?,?,0,?,??)";
//				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//				pstm = conn.prepareStatement(sql);
//				pstm.setInt(1, dimensionId);
//				pstm.setString(2,visId);
//				pstm.setString(3, description);
//				pstm.setInt(4, 0);//version number
//				pstm.setInt(5, userId);//updated by user id
//				pstm.setTimestamp(6, timestamp);//updated time
//				pstm.setTimestamp(7, timestamp);//created time
//				pstm.executeUpdate();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
//        	catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }else if(operation=="getInfo"){
            int id = Integer.parseInt(request.getParameter("operation"));
			String sql = "select hierarchy.*,DIMENSION.VIS_ID AS DVIS from hierarchy  LEFT JOIN  DIMENSION ON HIERARCHY.DIMENSION_ID=DIMENSION.DIMENSION_ID where DIMENSION.type=1 order by hierarchy.CREATED_TIME desc";
			try {
				pstm = conn.prepareStatement(sql);
			}catch(Exception ex){
				System.out.println(ex.toString());
			}

        }
	}
	
	protected void insertTree(HttpServletRequest request,JSONObject ele,int index,int augParentId,int parentId,int hierarchyId ){
		index++;
		augParentId = saveJson(request,ele,index,augParentId,parentId,hierarchyId);
		JSONArray children;
		try {
			children = ele.getJSONArray("children");
			if(children.length()<1) return;
			for(int i=0;i<children.length();i++){
				index++;
				JSONObject temp = children.getJSONObject(i);
				insertTree(request,temp,index,augParentId,parentId,hierarchyId);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected int saveJson(HttpServletRequest request,JSONObject ele, int index,int augParentId,int parentId,int hierarchyId){
        Connection conn = MyUtils.getStoredConnection(request);
		PreparedStatement pstm;
		
		String sql = "insert into hierarchy (dimension_id,vis_id,description,version_num,updated_by_user_id,updated_time,created_time) values(?,?,?,0,?,??)";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		pstm.setInt(1, dimensionId);
//		pstm.setString(2,visId);
//		pstm.setString(3, description);
//		pstm.setInt(4, 0);//version number
//		pstm.setInt(5, userId);//updated by user id
//		pstm.setTimestamp(6, timestamp);//updated time
//		pstm.setTimestamp(7, timestamp);//created time
		return 1;
	}
}
