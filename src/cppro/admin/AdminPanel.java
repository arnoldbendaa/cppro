package cppro.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.utc.common.CPContext;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.softproideas.app.admin.authentication.model.AdminUserDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationDetailsDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationTechniqueDTO;
import com.softproideas.app.admin.authentication.model.SecurityLogDTO;
import com.softproideas.app.admin.authentication.service.AuthenticationService;
import com.softproideas.app.admin.authentication.service.AuthenticationServiceImpl;
import com.softproideas.app.admin.authentication.util.AuthenticationUtil;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleStructureLevelEndDatesDTO;
import com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService;
import com.softproideas.app.admin.budgetcycles.service.BudgetCyclesServiceImpl;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDTO;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDetailsDTO;
import com.softproideas.app.admin.budgetinstructions.service.BudgetInstructionsService;
import com.softproideas.app.admin.budgetinstructions.service.BudgetInstructionsServiceImpl;
import com.softproideas.app.admin.changemanagement.model.ChangeMgmtDTO;
import com.softproideas.app.admin.changemanagement.service.ChangeManagementService;
import com.softproideas.app.admin.changemanagement.service.ChangeManagementServiceImpl;
import com.softproideas.app.admin.dataeditor.model.DataEditorRow;
import com.softproideas.app.admin.dataeditor.model.DataEditorSearchOption;
import com.softproideas.app.admin.dataeditor.model.DimensionDataForModelDTO;
import com.softproideas.app.admin.dataeditor.service.DataEditorService;
import com.softproideas.app.admin.dataeditor.service.DataEditorServiceImpl;
import com.softproideas.app.admin.datatypes.model.DataTypeDTO;
import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.app.admin.datatypes.model.DataTypesMeasureClassDTO;
import com.softproideas.app.admin.datatypes.model.DataTypesSubTypeDTO;
import com.softproideas.app.admin.datatypes.util.DataTypesUtil;
import com.softproideas.app.admin.dimensions.account.service.AccountService;
import com.softproideas.app.admin.dimensions.account.service.AccountServiceImpl;
import com.softproideas.app.admin.dimensions.business.service.BusinessService;
import com.softproideas.app.admin.dimensions.business.service.BusinessServiceImpl;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDTO;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDetailsDTO;
import com.softproideas.app.admin.dimensions.calendar.service.CalendarService;
import com.softproideas.app.admin.dimensions.calendar.service.CalendarServiceImpl;
import com.softproideas.app.admin.dimensions.model.DimensionDetailsDTO;
import com.softproideas.app.admin.externalsystems.model.ExternalSystemDTO;
import com.softproideas.app.admin.externalsystems.model.ExternalSystemDetailsDTO;
import com.softproideas.app.admin.externalsystems.service.ExternalSystemService;
import com.softproideas.app.admin.externalsystems.service.ExternalSystemServiceImpl;
import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDTO;
import com.softproideas.app.admin.financecubeformula.services.FinanceCubeFormulaService;
import com.softproideas.app.admin.financecubeformula.services.FinanceCubeFormulaServiceImpl;
import com.softproideas.app.admin.financecubes.model.FinanceCubeDetailsDTO;
import com.softproideas.app.admin.financecubes.services.FinanceCubesService;
import com.softproideas.app.admin.financecubes.services.FinanceCubesServiceImpl;
import com.softproideas.app.admin.formdashboard.service.FormDashboardService;
import com.softproideas.app.admin.formdashboard.service.FormDashboardServiceImpl;
import com.softproideas.app.admin.forms.flatforms.model.FormUndeploymentDataDTO;
import com.softproideas.app.admin.forms.flatforms.service.FlatFormsService;
import com.softproideas.app.admin.forms.flatforms.service.FlatFormsServiceImpl;
import com.softproideas.app.admin.hierarchies.service.HierarchiesService;
import com.softproideas.app.admin.hierarchies.service.HierarchiesServiceImpl;
import com.softproideas.app.admin.importtasks.model.ImportDTO;
import com.softproideas.app.admin.importtasks.service.ImportService;
import com.softproideas.app.admin.importtasks.service.ImportServiceImpl;
import com.softproideas.app.admin.loggedhistory.model.LoggedHistoryUserDTO;
import com.softproideas.app.admin.loggedhistory.service.LoggedHistoryService;
import com.softproideas.app.admin.loggedhistory.service.LoggedHistoryServiceImpl;
import com.softproideas.app.admin.loggedinusers.model.LoggedInUserDTO;
import com.softproideas.app.admin.loggedinusers.service.LoggedInUsersService;
import com.softproideas.app.admin.loggedinusers.service.LoggedInUsersServiceImpl;
import com.softproideas.app.admin.modelmappings.model.CompanyDTO;
import com.softproideas.app.admin.modelmappings.model.ExternalRequestDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDetailsDTO;
import com.softproideas.app.admin.modelmappings.service.ModelMappingsService;
import com.softproideas.app.admin.modelmappings.service.ModelMappingsServiceImpl;
import com.softproideas.app.admin.models.model.FinanceCubeModelDTO;
import com.softproideas.app.admin.models.model.ModelDetailsDTO;
import com.softproideas.app.admin.models.service.ModelsService;
import com.softproideas.app.admin.models.service.ModelsServiceImpl;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecurityDTO;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecurityDetailsDTO;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecuritySaveData;
import com.softproideas.app.admin.modelusersecurity.services.ModelUserSecurityService;
import com.softproideas.app.admin.modelusersecurity.services.ModelUserSecurityServiceImpl;
import com.softproideas.app.admin.monitors.model.TaskDTO;
import com.softproideas.app.admin.monitors.model.TaskDetailsDTO;
import com.softproideas.app.admin.monitors.services.TaskViewerService;
import com.softproideas.app.admin.monitors.services.TaskViewerServiceImpl;
import com.softproideas.app.admin.notes.model.NotesDTO;
import com.softproideas.app.admin.notes.server.NotesService;
import com.softproideas.app.admin.notes.server.NotesServiceImpl;
import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDetailsDTO;
import com.softproideas.app.admin.profiles.service.ProfilesService;
import com.softproideas.app.admin.profiles.service.ProfilesServiceImpl;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDetailsDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchProfileDTO;
import com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService;
import com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesServiceImpl;
import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDTO;
import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDetailsDTO;
import com.softproideas.app.admin.report.externaldestinations.service.ExternalDestinationsService;
import com.softproideas.app.admin.report.externaldestinations.service.ExternalDestinationsServiceImpl;
import com.softproideas.app.admin.report.internaldestinations.model.InternalDestinationDTO;
import com.softproideas.app.admin.report.internaldestinations.model.InternalDestinationDetailsDTO;
import com.softproideas.app.admin.report.internaldestinations.model.ReportMessageTypeDTO;
import com.softproideas.app.admin.report.internaldestinations.service.InternalDestinationsService;
import com.softproideas.app.admin.report.internaldestinations.service.InternalDestinationsServiceImpl;
import com.softproideas.app.admin.report.internaldestinations.util.InternalDestinationUtil;
import com.softproideas.app.admin.roles.model.RoleDetailsDTO;
import com.softproideas.app.admin.roles.service.RolesService;
import com.softproideas.app.admin.roles.service.RolesServiceImpl;
import com.softproideas.app.admin.structuredisplayname.service.StructureDisplayNameService;
import com.softproideas.app.admin.structuredisplayname.service.StructureDisplayNameServiceImpl;
import com.softproideas.app.admin.systemproperties.model.SystemPropertyDTO;
import com.softproideas.app.admin.systemproperties.model.SystemPropertyDetailsDTO;
import com.softproideas.app.admin.systemproperties.services.SystemPropertiesService;
import com.softproideas.app.admin.systemproperties.services.SystemPropertiesServiceImpl;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecurityDTO;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecurityDetailsDTO;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecuritySaveData;
import com.softproideas.app.admin.usermodelsecurity.service.UserModelSecurityService;
import com.softproideas.app.admin.usermodelsecurity.service.UserModelSecurityServiceImpl;
import com.softproideas.app.admin.users.model.UserDetailsDTO;
import com.softproideas.app.admin.users.service.UsersService;
import com.softproideas.app.admin.users.service.UsersServiceImpl;
import com.softproideas.app.core.datatype.model.DataTypeCoreDTO;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreWithGlobalDTO;
import com.softproideas.app.core.model.service.ModelCoreService;
import com.softproideas.app.core.model.service.ModelCoreServiceImpl;
import com.softproideas.app.core.roles.model.RoleCoreDTO;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.app.lookuptable.auction.model.FlatFormDTO;
import com.softproideas.app.reviewbudget.dimension.model.DimensionDTO;
import com.softproideas.app.reviewbudget.note.service.NoteServiceImpl;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.common.models.FormDashboardDTO;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.tree.NodeLazyDTO;
import com.softproideas.commons.model.tree.NodeStaticWithIdAndDescriptionDTO;

import cppro.beans.UserAccount;
import cppro.utils.DBUtils;
import cppro.utils.MyUtils;
import java.util.ArrayList;
import java.util.HashMap;

import com.softproideas.app.admin.datatypes.service.DataTypesService;
import com.softproideas.app.admin.datatypes.service.DataTypesServiceImpl;
import com.softproideas.app.admin.hierarchies.model.HierarchyDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyDetailsDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;

@WebServlet("/adminPanels")
public class AdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	 @Autowired
//	 HierarchiesService hierarchiesService;
//	@Autowired
//	StructureDisplayNameService structureDisplayNameService
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminPanel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		HttpSession session = request.getSession(false);
		UserAccount user = (UserAccount) MyUtils.getLoginedUser(session);
		int userId = user.getUserId();
		DBUtils dbutils = new DBUtils();
		dbutils.userId = userId;
		String json = "";
		response.setHeader("Content-Type", "text/plain");
		CPContext t = (CPContext) session.getAttribute("cpContext");
		if (request.getParameterMap().containsKey("method1")) {
			String method1 = request.getParameter("method1");
		 if(method1.equals("getExcelFileName")){
				Connection conn = MyUtils.getStoredConnection(request);
				String errorString = null;
				List<String> list = null;
				try {
					list = DBUtils.queryExcelFileName(conn);
				} catch (SQLException e) {
					e.printStackTrace();
					errorString = e.getMessage();
				}
				json = new Gson().toJson(list).toString();
			}		}
		response.getWriter().append(json).flush();
		return;
	}


}
