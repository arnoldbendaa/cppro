package cppro.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cedar.cp.utc.common.CPContext;
import com.google.gson.Gson;
import com.softproideas.app.admin.datatypes.service.DataTypesService;
import com.softproideas.app.admin.datatypes.service.DataTypesServiceImpl;
import com.softproideas.app.reviewbudget.budget.model.ReviewBudgetDTO;
import com.softproideas.app.reviewbudget.budget.service.BudgetService;
import com.softproideas.app.reviewbudget.budget.service.BudgetServiceImpl;
import com.softproideas.app.reviewbudget.xcellform.model.XCellFormDTO;
import com.softproideas.common.exceptions.ServiceException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import cppro.beans.UserAccount;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;
import java.util.List;
import com.softproideas.app.core.profile.model.ProfileDTO;
import com.softproideas.app.core.profile.service.ProfileService;
import com.softproideas.app.core.profile.service.ProfileServiceImpl;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softproideas.app.reviewbudget.export.service.ExportService;
import com.softproideas.app.reviewbudget.export.service.ExportServiceImpl;

/**
 * Servlet implementation class reviewBudget
 */
@WebServlet("/reviewBudgets")
public class reviewBudget extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public reviewBudget() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(false);
		UserAccount user = (UserAccount) MyUtils.getLoginedUser(session);
		int userId = user.getUserId();
		DBUtils dbutils = new DBUtils();
		dbutils.userId = userId;
		String json = "";
		response.setHeader("Content-Type", "application/json");
		CPContext t = (CPContext) session.getAttribute("cpContext");
		ObjectMapper mapper = new ObjectMapper();

		if (request.getParameterMap().containsKey("method1")) {
			String method1 = request.getParameter("method1");
			if(method1.equals("fetchReviewBudget")){
				BudgetService budgetService = new BudgetServiceImpl();budgetService.Init(userId,t);
				int topNodeId = Integer.parseInt(request.getParameter("topNodeId"));
				int modelId =  Integer.parseInt(request.getParameter("modelId"));
				int budgetCycleId = Integer.parseInt(request.getParameter("budgetCycleId"));
				int dataEntryProfileId=Integer.parseInt(request.getParameter("dataEntryProfileId"));
				int dim0 = Integer.parseInt(request.getParameter("dim0"));
				int dim1 = Integer.parseInt(request.getParameter("dim1"));
				int dim2 = Integer.parseInt(request.getParameter("dim2"));
				String dataType = request.getParameter("dataType");
				
				 HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
			        if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
			            selectionsMap.put(new Integer(0), new Integer(dim0));
			            selectionsMap.put(new Integer(1), new Integer(dim1));
			            selectionsMap.put(new Integer(2), new Integer(dim2));
			        }
			    try {
					ReviewBudgetDTO result = budgetService.fetchReviewBudget(topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
//					json = new Gson().toJson(result).toString();
					json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
					
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(method1.equals("profiles")){
				ProfileService profileService;
		    	profileService = new ProfileServiceImpl();
		    	profileService.Init(t);

	            int modelId = Integer.parseInt(request.getParameter("modelId"));
				int budgetCycleId = Integer.parseInt(request.getParameter("budgetCycleId"));
				int topNodeId = Integer.parseInt(request.getParameter("topNode"));
				try {
					List<ProfileDTO> result = profileService.browseProfiles(modelId, budgetCycleId, topNodeId);
					json = new Gson().toJson(result).toString();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(method1.equals("exportToXls")){
				BudgetService budgetService = new BudgetServiceImpl();budgetService.Init(userId,t);
				ExportService exportService = new ExportServiceImpl();exportService.Init(userId,t);
				int topNodeId = Integer.parseInt(request.getParameter("topNodeId"));
				int modelId = Integer.parseInt(request.getParameter("modelId"));
				int budgetCycleId = Integer.parseInt(request.getParameter("budgetCycleId"));
				int dataEntryProfileId = Integer.parseInt(request.getParameter("dataEntryProfileId"));
				int dim0=0;
				int dim1=0;int dim2=0;String dataType="";String proflieName="";
				if (request.getParameterMap().containsKey("dim0")) 
					dim0 = Integer.parseInt(request.getParameter("dim0"));
				if (request.getParameterMap().containsKey("dim1")) 
					dim1 = Integer.parseInt(request.getParameter("dim1"));
				if (request.getParameterMap().containsKey("dim2")) 
					dim2 = Integer.parseInt(request.getParameter("dim2"));
				if (request.getParameterMap().containsKey("dataType")) 
					dataType = request.getParameter("dataType");
				if (request.getParameterMap().containsKey("dataType")) 
					proflieName = request.getParameter("proflieName");
				String profileName= request.getParameter("profileName");
				
				  HashMap<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
				    if ((dim0 != 0) && (dim1 != 0) && (dim2 != 0)) {
				        selectionsMap.put(new Integer(0), new Integer(dim0));
				        selectionsMap.put(new Integer(1), new Integer(dim1));
				        selectionsMap.put(new Integer(2), new Integer(dim2));
				    }
				    XCellFormDTO reviewBudgetDetails = null;
					try {
						reviewBudgetDetails = (XCellFormDTO) budgetService.fetchReviewBudgetDetails(topNodeId, modelId, budgetCycleId, dataEntryProfileId, selectionsMap, dataType);
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    Workbook workbook = null;
					try {
						workbook = exportService.exportToXls(reviewBudgetDetails.getExcelFile(), reviewBudgetDetails.getWorkbook());
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    response.setContentType("application/force-download");
				profileName = profileName.replace(" ", "_");
				if (workbook instanceof XSSFWorkbook) {
				    response.setHeader("Content-Disposition", "attachment;filename=" + profileName + ".xlsx");
				} else {
				    response.setHeader("Content-Disposition", "attachment;filename=" + profileName + ".xls");
				}
				response.setHeader("Set-Cookie", "fileDownload=true; path=/");
				workbook.write(response.getOutputStream());
				// IOUtils.copy(inputStream, );
				// inputStream.close();
				response.flushBuffer();
			}
			response.getWriter().append(json).flush();
			
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
