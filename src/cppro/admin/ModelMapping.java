package cppro.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cppro.utils.MyUtils;

/**
 * Servlet implementation class ModelMapping
 */
@WebServlet("/ModelMapping")
public class ModelMapping extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModelMapping() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        request.setAttribute("base_url", MyUtils.base_url);
        request.setAttribute("sidebar", "inputOutput");

		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/admin/modelMapping.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	
		HttpServletRequest req = (HttpServletRequest) request;
		//get user Id 
		//HttpSession session = req.getSession(false);
		//UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
        Connection conn = MyUtils.getStoredConnection(request);
		//int userId = user.getUserId();
        int userId = 1;
		PreparedStatement pstm;
		 String operation = (String) request.getParameter("operation");
        if(operation.equals("read")){
        	Collection<JSONObject>items = new ArrayList<JSONObject>();
        	JSONObject temp = new JSONObject();
        	String sql = "SELECT MAPPED_MODEL_ID,MODEL.VIS_ID,MODEL.DESCRIPTION,EXTERNAL_SYSTEM.VIS_ID AS EXTERNAL_SYSTEM,COMPANY_VIS_ID,MAPPED_MODEL.LEDGER_VIS_ID FROM MAPPED_MODEL LEFT JOIN MODEL ON MAPPED_MODEL.MAPPED_MODEL_ID = MODEL.MODEL_ID LEFT JOIN EXTERNAL_SYSTEM ON MAPPED_MODEL.EXTERNAL_SYSTEM_ID=EXTERNAL_SYSTEM.EXTERNAL_SYSTEM_ID ORDER BY MODEL.VIS_ID";
        	try{
        		pstm = conn.prepareStatement(sql);
        		ResultSet rs = pstm.executeQuery();
        		while(rs.next()){
        			temp = new JSONObject();
            		int id=rs.getInt("MAPPED_MODEL_ID");
            		String visId = rs.getString("VIS_ID");
            		String description = rs.getString("DESCRIPTION");
            		String externalSystem = rs.getString("EXTERNAL_SYSTEM");
            		String companyVisId = rs.getString("COMPANY_VIS_ID");
            		String ledgerVisId = rs.getString("LEDGER_VIS_ID");
            		
            		temp.put("0",id);
            		temp.put("1", visId==null?" ":visId);
            		temp.put("2", description==null?" ":description);
            		temp.put("3", externalSystem==null?" ":externalSystem);
            		temp.put("4", companyVisId==null?" ":companyVisId);
            		temp.put("5", ledgerVisId==null?" ":ledgerVisId);
            	           	
            		items.add(temp);
        		}
        		JSONObject result = new JSONObject();
        		result.put("mappedModel", items);
        		items = new ArrayList<JSONObject>();
        		sql = "SELECT * FROM EXTERNAL_SYSTEM ORDER BY VIS_ID";
        		try{
        			pstm = conn.prepareStatement(sql);
        			rs = pstm.executeQuery();
        			while(rs.next()){
        				temp = new JSONObject();
        				int id = rs.getInt("EXTERNAL_SYSTEM_ID");
        				String visId = rs.getString("VIS_ID");
        				String description = rs.getString("DESCRIPTION");
        				String location = rs.getString("LOCATION");
        				String enabled = rs.getString("ENABLED");
        				
        				temp.put("0", id);
        				temp.put("1",visId==null?" ":visId);
        				temp.put("2",description==null?" ":description);
        				temp.put("3",location==null?" ":location);
        				temp.put("4",enabled==null?" ":enabled);
        				items.add(temp);
        			}
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		result.put("externalSystem",items);
        		
        		response.getWriter().append(result.toString()).flush();
        	}catch(Exception ex){
        			
        	}
        }
	
	}

}
