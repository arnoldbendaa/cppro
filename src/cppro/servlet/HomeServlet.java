package cppro.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.message.UnreadInBoxForUserELO;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.homepage.BudgetCycleDTO;
import com.cedar.cp.utc.struts.homepage.BudgetLocationDTO;
import com.cedar.cp.utc.struts.homepage.ModelDTO;

import cppro.beans.Product;
import cppro.beans.UserAccount;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;
import com.cedar.cp.utc.common.CPAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(urlPatterns = { "/home" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Connection cnx = MyUtils.getStoredConnection(request);
        ArrayList<ModelDTO> modelList = new ArrayList<>();
		HttpSession session = request.getSession(false);
		UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
        Connection conn = MyUtils.getStoredConnection(request);
		int userId = user.getUserId();
		String errorString = null;
		EntityList models = null;
		try{
			models = DBUtils.getAllModelsWithActiveCycleForUser(conn,userId);
		}catch(Exception e){
			e.printStackTrace();
			errorString = e.getMessage();
		}
        
        int rows = models.getNumRows();

        for (int i = 0; i < rows; ++i) {
            ModelDTO modelDTO = new ModelDTO();
            Object modelref = models.getValueAt(i, "VisId");
            Integer modelId = (Integer) models.getValueAt(i, "ModelId");
            String description = models.getValueAt(i, "Description").toString();
            modelDTO.setName(modelref);
            modelDTO.setModelId(modelId.intValue());
            modelDTO.setDescription(description);
            modelList.add(modelDTO);
        }
        
        UnreadInBoxForUserELO messages = DBUtils.getSummaryUnreadMessagesForUser(conn, String.valueOf(userId));
		// Store info in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("tabLists", modelList);
		
        request.setAttribute("base_url", MyUtils.base_url);
        	List item = messages.getDataAsList();
    		request.setAttribute("messageList", item);

		// Forward to /WEB-INF/views/productListView.jsp
		// RequestDispatcher dispatcher =
		// this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp");
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//doGet(request, response);
		HttpServletRequest req = (HttpServletRequest) request;
		//get user Id 
		HttpSession session = req.getSession(false);
		UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
        Connection conn = MyUtils.getStoredConnection(request);
		int userId = user.getUserId();
		PreparedStatement pstm;
        String operation = (String) request.getParameter("operation");
        int modelId = Integer.parseInt(request.getParameter("modelId"));
        ArrayList<ModelDTO> modelList = new ArrayList<>();
        ServletContext context = getServletContext();
        JSONObject result = new JSONObject();
		try {

		if(operation.equals("getTabContent")){//creat account dimension
			modelList = DBUtils.populateHomeForm(request, conn,modelId,userId);
//			Gson gson = new GsonBuilder().
//				    registerTypeAdapter(Node.class, new NodeSerializer()).create();
			List budgetCycleList = modelList.get(0).getBudgetCycle(); 
//			BudgetCycleDTO budgetCycleDTO = (BudgetCycleDTO) budgetCycleList.get(0);
			int length = budgetCycleList.size();
			for(int i=0;i<length;i++){
				JSONObject temp = new JSONObject();
				BudgetCycleDTO item = (BudgetCycleDTO) budgetCycleList.get(i);
				temp.put("budgetCycle", item.getBudgetCycle());
				temp.put("budgetCycleId",item.getBudgetCycleId());
				temp.put("structureElementId", item.getStructureId());
				temp.put("category", item.getCategory());
				ArrayList bgl = (ArrayList) item.getBudgetLocations();
				BudgetLocationDTO budgetListDTO = (BudgetLocationDTO) bgl.get(0);
				int structureElementId = budgetListDTO.getStructureElementId();
				temp.put("structureElementId", structureElementId);
				temp.put("locationState", budgetListDTO.getState());
				temp.put("childNotStarted",budgetListDTO.getChildNotStarted());
				temp.put("childPreparing",budgetListDTO.getChildPreparing());
				temp.put("childSubmited",budgetListDTO.getChildSubmited());
				temp.put("childAgreed", budgetListDTO.getChildAgreed());
				result.put(Integer.toString(i),temp);
			}
//			budgetCycleDTO.
//			String outPut = new Gson().toJson(modelList);
			response.getWriter().append(result.toString()).flush();	
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

}
