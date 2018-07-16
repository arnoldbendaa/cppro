package cppro.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.utc.struts.homepage.BLChildDTO;
import com.cedar.cp.utc.struts.homepage.BudgetCycleDTO;
import com.cedar.cp.utc.struts.homepage.BudgetLocationDTO;
import com.cedar.cp.utc.struts.homepage.ModelDTO;

import cppro.beans.UserAccount;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;
import java.util.List;
import com.cedar.cp.utc.struts.approver.CrumbDTO;

/**
 * Servlet implementation class Servlet2
 */
@WebServlet(urlPatterns = { "/Servlet2"})
public class Servlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
//		Map params;
//		params = request.getParameterMap();
		int structureElementId = Integer.parseInt(request.getParameter("structureElementId"));
		int budgetCycleId = Integer.parseInt(request.getParameter("budgetCycleId"));
		Connection cnx = MyUtils.getStoredConnection(request);
        ArrayList<ModelDTO> modelList = new ArrayList<>();
		HttpSession session = request.getSession(false);
		UserAccount user = (UserAccount)MyUtils.getLoginedUser(session);
        Connection conn = MyUtils.getStoredConnection(request);
		int userId = user.getUserId();
		String locationIdentifier = "";
		String description = "";
		
        EntityList cycles = DBUtils.getBudgetDetailsForUser(conn,userId, true, structureElementId, budgetCycleId);
        int rows = cycles.getNumRows();

        for (int i = 0; i < rows; ++i) {
            ModelDTO modelDTO = new ModelDTO();
            Object modelref = cycles.getValueAt(i, "Model");
            modelDTO.setName(modelref);
            modelDTO.setModelId(((Integer) cycles.getValueAt(i, "ModelId")).intValue());

            EntityList budgetCycleEntityList = (EntityList) cycles.getValueAt(i, "BudgetCycles");
            int noCycles = budgetCycleEntityList.getNumRows();
            ArrayList budgetCycleList = new ArrayList();

            for (int j = 0; j < noCycles; ++j) {
                BudgetCycleDTO bcDTO = new BudgetCycleDTO();
                bcDTO.setBudgetCycle(budgetCycleEntityList.getValueAt(j, "BudgetCycle"));
                bcDTO.setModelId(((Integer) budgetCycleEntityList.getValueAt(j, "ModelId")).intValue());
                bcDTO.setBudgetCycleId(((Integer) budgetCycleEntityList.getValueAt(j, "BudgetCycleId")).intValue());
                bcDTO.setHierachyId(((Integer) budgetCycleEntityList.getValueAt(j, "HierarchyId")).intValue());
                bcDTO.setCategory((String) budgetCycleEntityList.getValueAt(j, "Category"));
                EntityList budgetLocationEntityList = (EntityList) budgetCycleEntityList.getValueAt(j, "BudgetLocations");
                int noLocations = budgetLocationEntityList.getNumRows();
                ArrayList budgetLocationList = new ArrayList();

                for (int k = 0; k < noLocations; ++k) {
                    BudgetLocationDTO blDTO = new BudgetLocationDTO();
                    boolean massSubmit = false;
                    boolean massReject = false;
                    boolean allChildrenLeafs = true;
                    Object blState = budgetLocationEntityList.getValueAt(k, "State");
                    int blStateValue = 0;
                    if (blState != null && blState instanceof Integer) {
                        blStateValue = ((Integer) blState).intValue();
                    }

                    blDTO.setState(blStateValue);
                    blDTO.setStructureElementId(((Integer) budgetLocationEntityList.getValueAt(k, "StructureElementId")).intValue());
                    locationIdentifier = (String) budgetLocationEntityList.getValueAt(k, "VisId");
                    blDTO.setIdentifier(locationIdentifier);
                    description = (String) budgetLocationEntityList.getValueAt(k, "Description");
                    blDTO.setDescription(description);
                    blDTO.setDepth(((Integer) budgetLocationEntityList.getValueAt(k, "Depth")).intValue() + 1);
                    blDTO.setEndDate((Timestamp) budgetLocationEntityList.getValueAt(k, "EndDate"));

                    blDTO.setFullRights(((Boolean) budgetLocationEntityList.getValueAt(k, "FullRights")).booleanValue());

//                    blDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                    EntityList children = (EntityList) budgetLocationEntityList.getValueAt(k, "ChildLocations");
                    int noChildren = children.getNumRows();
                    ArrayList budgetLocationChildList = new ArrayList();

                    for (int l = 0; l < noChildren; ++l) {
                        BLChildDTO childDTO = new BLChildDTO();
                        Object childState = children.getValueAt(l, "State");
                        int childStateValue = 0;
                        if (childState != null && childState instanceof Integer) {
                            childStateValue = ((Integer) childState).intValue();
                        }

                        if (1==1) {
                            childDTO.setState(childStateValue);
                            childDTO.setStructureElementId(((Integer) children.getValueAt(l, "StructureElementId")).intValue());
                            childDTO.setIdentifier((String) children.getValueAt(l, "ElementVisId"));
                            childDTO.setDescription((String) children.getValueAt(l, "Description"));
                            childDTO.setUserCount(((Integer) children.getValueAt(l, "UserCount")).intValue());
                            childDTO.setOtherUserCount(((Integer) children.getValueAt(l, "OtherUserCount")).intValue());
                            childDTO.setSubmitable(((Boolean) children.getValueAt(l, "Submitable")).booleanValue());
                            childDTO.setRejectable(((Boolean) children.getValueAt(l, "Rejectable")).booleanValue());
                            childDTO.setLastUpdateById(((Integer) children.getValueAt(i, "LastUpdateById")).intValue());
                            childDTO.setEndDate((Timestamp) children.getValueAt(l, "EndDate"));
                            childDTO.setFullRights(((Boolean) children.getValueAt(l, "FullRights")).booleanValue());
//                            childDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                            childDTO.setParent(blDTO);
                            boolean leaf = ((Boolean) children.getValueAt(l, "Leaf")).booleanValue();
                            if (!leaf) {
                                allChildrenLeafs = false;
                            }

                            if (!massSubmit && leaf) {
                                massSubmit = true;
                            }

                            if (!massReject && childDTO.isRejectable()) {
                                massReject = true;
                            }

                            budgetLocationChildList.add(childDTO);
                        }
                    }

                    blDTO.setChildren(budgetLocationChildList);
                    blDTO.setUserCount(((Integer) budgetLocationEntityList.getValueAt(k, "UserCount")).intValue());
//                    blDTO.setController(myForm.isController());
                    blDTO.setSubmitable(((Boolean) budgetLocationEntityList.getValueAt(k, "Submitable")).booleanValue());
                    blDTO.setRejectable(((Boolean) budgetLocationEntityList.getValueAt(k, "Rejectable")).booleanValue());
                    blDTO.setLastUpdatedById(((Integer) budgetLocationEntityList.getValueAt(k, "LastUpdatedById")).intValue());
                    if (blDTO.getState() != 2) {
                        blDTO.setMassSubmitable(false);
                    } else {
                        blDTO.setMassSubmitable(allChildrenLeafs && massSubmit);
                    }

                    blDTO.setMassRejectable(allChildrenLeafs && massReject);
//                    if (myForm.getOldUserCount() == 0 && blDTO.getUserCount() > 0) {
//                        myForm.setOldUserCount(blDTO.getUserCount());
//                    } else if (myForm.getOldUserCount() > 0) {
//                        blDTO.setUserCount(1);
//                        blDTO.setAgreeable(myForm.getOldDepth() > 0);
//                    }

                    budgetLocationList.add(blDTO);
                }

                bcDTO.setBudgetLocations(budgetLocationList);
                budgetCycleList.add(bcDTO);
            }

            modelDTO.setBudgetCycle(budgetCycleList);
            modelList.add(modelDTO);
        }
//        this.doCrumb(locationIdentifier,description,String.valueOf(structureElementId));
		ModelDTO modeldto = modelList.get(0);
		List budgetCycleList = modeldto.getBudgetCycle();
		BudgetCycleDTO budgetCycledto = (BudgetCycleDTO) budgetCycleList.get(0);
		int modelId = budgetCycledto.getModelId();
		String tabName = budgetCycledto.getBudgetCycle();
		String category = budgetCycledto.getCategory();
		int hierarychyId = budgetCycledto.getHierachyId();
		List<BudgetLocationDTO> elements = budgetCycledto.getBudgetLocations();
		String modelName = modeldto.getName();
		int length = elements.size();
		JSONObject result = new JSONObject();
		try{
			for(int i = 0 ; i < length; i++){
				JSONObject temp = new JSONObject();
				BudgetLocationDTO element = (BudgetLocationDTO)elements.get(0);
				temp.put("name",element.getIdentifier());
				result.put(Integer.toString(i),temp);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		request.setAttribute("budgetList", elements);
		request.setAttribute("tabName",modelName+tabName);
		request.setAttribute("category", category);
		request.setAttribute("structureElemenetId", structureElementId);
		request.setAttribute("budgetCycleId", budgetCycleId);
		request.setAttribute("modelId", modelId);
		
		request.setAttribute("modelName",modelName);
		request.setAttribute("cycleName",tabName);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/servlet2.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public void doCrumb(String locationIdentifier, String description,String structureElementId){
		List crumbs;
	       String delim = "**,**";

		crumbs = new ArrayList();
	       String visIdList = " ";
	       String structureIdList = structureElementId;
	       String descriptionList =description;
	       StringTokenizer visIdTokens = new StringTokenizer(visIdList, delim); 
	       StringTokenizer structureIdsTokens = new StringTokenizer(structureIdList, delim);
	       StringTokenizer descriptionTokens = new StringTokenizer(descriptionList, delim);
	       String finalVisId;
	       String finalStructureId;
	       String finalDescription;
	       StringBuffer bufferVisId = new StringBuffer();
	       StringBuffer bufferStrutureId = new StringBuffer();
	       StringBuffer bufferDescription = new StringBuffer();
	       int i = 0;
	       while(visIdTokens.hasMoreTokens() && i < 2) {
	           finalVisId =  visIdTokens.nextToken();
	           if(!finalVisId.equals(locationIdentifier))
	          {
	              finalStructureId = structureIdsTokens.nextToken();
	              finalDescription = descriptionTokens.nextToken();
	              bufferVisId.append(finalVisId + delim);
	              bufferStrutureId.append(finalStructureId + delim);
	              bufferDescription.append(finalDescription + delim);
	              CrumbDTO dto1 = new CrumbDTO(finalVisId, finalStructureId, "1", "0", finalDescription);
	              crumbs.add(dto1);
	              i++;
	           }else{
	               break;
	           }
	       }
	       String vlToken = locationIdentifier;
	       String seToken = String.valueOf(structureElementId);
	       String odToken = "1";
	       String oucToken = "0";
	       String dToken = description;
	       
	}
}
