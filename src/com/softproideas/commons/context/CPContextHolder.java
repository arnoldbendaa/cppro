/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.commons.context;

import com.cedar.cp.api.admin.tidytask.TidyTasksProcess;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.api.datatype.DataTypesProcess;
import com.cedar.cp.api.dimension.DimensionsProcess;
import com.cedar.cp.api.dimension.HierarchysProcess;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentsProcess;
import com.cedar.cp.api.importtask.ImportTasksProcess;
import com.cedar.cp.api.model.BudgetCyclesProcess;
import com.cedar.cp.api.model.ModelsProcess;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskProcess;
import com.cedar.cp.api.systemproperty.SystemPropertysProcess;
import com.cedar.cp.api.user.DataEntryProfilesProcess;
import com.cedar.cp.api.user.UsersProcess;
import com.cedar.cp.api.user.loggedinusers.LoggedInUsersProcess;
import com.cedar.cp.api.xmlform.XmlFormsProcess;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionServer;
import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionServer;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.cedar.cp.ejb.api.budgetinstruction.BudgetInstructionEditorSessionServer;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionServer;
import com.cedar.cp.ejb.api.budgetlocation.UserModelSecurityEditorSessionServer;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionServer;
import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionServer;
import com.cedar.cp.ejb.api.dataeditor.DataEditorEditorSessionServer;
import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionServer;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionServer;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionServer;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionServer;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionServer;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionServer;
import com.cedar.cp.ejb.api.model.BudgetCycleHelperServer;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionServer;
import com.cedar.cp.ejb.api.model.ModelEditorSessionServer;
import com.cedar.cp.ejb.api.model.globalmapping2.GlobalMappedModel2EditorSessionServer;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionServer;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionServer;
import com.cedar.cp.ejb.api.report.destination.external.ExternalDestinationEditorSessionServer;
import com.cedar.cp.ejb.api.report.destination.internal.InternalDestinationEditorSessionServer;
import com.cedar.cp.ejb.api.role.RoleEditorSessionServer;
import com.cedar.cp.ejb.api.systemproperty.SystemPropertyEditorSessionServer;
import com.cedar.cp.ejb.api.task.TaskProcessServer;
import com.cedar.cp.ejb.api.user.DataEntryProfileEditorSessionServer;
import com.cedar.cp.ejb.api.user.UserEditorSessionServer;
import com.cedar.cp.ejb.api.user.loggedinusers.LoggedInUsersEditorSessionServer;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPContextCache;

/**
 * This class holding CPContext, CPContext identifier and provides access to processes
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 *        2014 All rights reserved to Softpro Ideas Group
 */
public class CPContextHolder {

    private String mCPContextId;
    private CPContext mCPContext;

    CPContextHolder(String init) {
    	System.out.println(init);
    };
    public CPContextHolder(){
    	
    }

    public void init(CPContext mCPContext) {
        this.mCPContext = mCPContext;
        this.mCPContextId = CPContextCache.getCPContextId(mCPContext);
    }

    public int getUserId() {
        return this.getUserContext().getUserId();
    }

    public String getUserName() {
        return this.getUserContext().getLogonString();
    }

    public CPConnection getCPConnection() {
        return this.mCPContext.getCPConnection();
    }

    public UserContext getUserContext() {
        return this.mCPContext.getUserContext();
    }
    
    public Boolean isAdmin() {
        return this.getUserContext().isAdmin();
    }
    
    public Boolean isSystemAdministrator(){
        return this.getUserContext().getUserRoles().contains("SystemAdministrator");
    }

    public UsersProcess getUsersProcess() {
        return this.mCPContext.getCPConnection().getUsersProcess();
    }

    public ModelsProcess getModelsProcess() {
        return this.mCPContext.getCPConnection().getModelsProcess();
    }

    public ListSessionServer getListSessionServer() {
        CPConnection connection = getCPConnection();
        ListSessionServer server = new ListSessionServer(connection);
        return server;
    }

    public ExternalSystemEditorSessionServer getExternalSystemEditorSessionServer() {
        CPConnection connection = getCPConnection();
        ExternalSystemEditorSessionServer server = new ExternalSystemEditorSessionServer(connection);
        return server;
    }

    public ModelEditorSessionServer getModelEditorSessionServer() {
        CPConnection connection = getCPConnection();
        ModelEditorSessionServer server = new ModelEditorSessionServer(connection);
        return server;
    }

    public RecalculateBatchTaskEditorSessionServer getRecalculateBatchTaskEditorSessionServer() {
        CPConnection connection = getCPConnection();
        RecalculateBatchTaskEditorSessionServer server = new RecalculateBatchTaskEditorSessionServer(connection);
        return server;
    }

    public SystemPropertyEditorSessionServer getSystemPropertyEditorSessionServer() {
        CPConnection connection = getCPConnection();
        SystemPropertyEditorSessionServer server = new SystemPropertyEditorSessionServer(connection);
        return server;
    }

    public TidyTaskEditorSessionServer getTidyTaskEditorSessionServer() {
        CPConnection connection = getCPConnection();
        TidyTaskEditorSessionServer server = new TidyTaskEditorSessionServer(connection);
        return server;
    }

    public TaskProcessServer getTaskProcessServer() {
        CPConnection connection = getCPConnection();
        TaskProcessServer server = new TaskProcessServer(connection);
        return server;
    }

    public DataTypeEditorSessionServer getDataTypeEditorSessionServer() {
        CPConnection connection = getCPConnection();
        DataTypeEditorSessionServer server = new DataTypeEditorSessionServer(connection);
        return server;
    }
    
    public AuthenticationPolicyEditorSessionServer getAuthenticationPolicyEditorSessionServer() {
        CPConnection connection = getCPConnection();
        AuthenticationPolicyEditorSessionServer server = new AuthenticationPolicyEditorSessionServer(connection);
        return server;
    }    

    public DimensionEditorSessionServer getDimensionEditorSessionServer() {
        CPConnection connection = getCPConnection();
        DimensionEditorSessionServer server = new DimensionEditorSessionServer(connection);
        return server;
    }    
    
    public CalendarEditorSessionServer getCalendarEditorSessionServer() {
        CPConnection connection = getCPConnection();
        CalendarEditorSessionServer server = new CalendarEditorSessionServer(connection);
        return server;
    }     
    
    public RoleEditorSessionServer getRoleEditorSessionServer() {
        CPConnection connection = getCPConnection();
        RoleEditorSessionServer server = new RoleEditorSessionServer(connection);
        return server;
    }

    public UserEditorSessionServer getUserEditorSessionServer() {
        CPConnection connection = getCPConnection();
        UserEditorSessionServer server = new UserEditorSessionServer(connection);
        return server;
    }
    
    public BudgetInstructionEditorSessionServer getBudgetInstructionEditorSessionServer() {
        CPConnection connection = getCPConnection();
        BudgetInstructionEditorSessionServer server = new BudgetInstructionEditorSessionServer(connection);
        return server;
    }

    public LoggedInUsersEditorSessionServer getLoggedInUsersEditorSessionServer() {
        CPConnection connection = getCPConnection();
        LoggedInUsersEditorSessionServer server = new LoggedInUsersEditorSessionServer(connection);
        return server;
    }

    public FinanceCubeEditorSessionServer getFinanceCubeEditorSessionServer() {
        CPConnection connection = getCPConnection();
        FinanceCubeEditorSessionServer server = new FinanceCubeEditorSessionServer(connection);
        return server;
    }

    public BudgetCycleEditorSessionServer getBudgetCycleEditorSessionServer() {
        CPConnection connection = getCPConnection();
        BudgetCycleEditorSessionServer server = new BudgetCycleEditorSessionServer(connection);
        return server;
    }
    
    public XmlFormEditorSessionServer getXmlFormEditorSessionServer() {
        CPConnection connection = getCPConnection();
        XmlFormEditorSessionServer server = new XmlFormEditorSessionServer(connection);
        return server;
    }

    public BudgetCycleHelperServer getBudgetCycleHelperServer() {
        CPConnection connection = getCPConnection();
        BudgetCycleHelperServer server = new BudgetCycleHelperServer(connection);
        return server;
    }
    
    public DataEditorEditorSessionServer getDataEditorEditorSessionServer() {
        CPConnection connection = getCPConnection();
        DataEditorEditorSessionServer server = new DataEditorEditorSessionServer(connection);
        return server;
    }
    public HierarchyEditorSessionServer getHierarchyEditorSessionServer() {
        CPConnection connection = getCPConnection();
        HierarchyEditorSessionServer server = new HierarchyEditorSessionServer(connection);
        return server;
    }    
    
    
    public ChangeMgmtEditorSessionServer getChangeMgmtEditorSessionServer() {
        CPConnection connection = getCPConnection();
        ChangeMgmtEditorSessionServer server = new ChangeMgmtEditorSessionServer(connection);
        return server;
    }    
    
    public InternalDestinationEditorSessionServer getInternalDestinationEditorSessionServer() {
        CPConnection connection = getCPConnection();
        InternalDestinationEditorSessionServer server = new InternalDestinationEditorSessionServer(connection);
        return server;
    }    
    
    public ExternalDestinationEditorSessionServer getExternalDestinationEditorSessionServer() {
        CPConnection connection = getCPConnection();
        ExternalDestinationEditorSessionServer server = new ExternalDestinationEditorSessionServer(connection);
        return server;
    }    
    
    public BudgetLocationEditorSessionServer getBudgetLocationEditorSessionServer() {
        CPConnection connection = getCPConnection();
        BudgetLocationEditorSessionServer server = new BudgetLocationEditorSessionServer(connection);
        return server;
    }

    public GlobalMappedModel2EditorSessionServer getGlobalMappedModel2EditorSessionServer() {
        CPConnection connection = getCPConnection();
        GlobalMappedModel2EditorSessionServer server = new GlobalMappedModel2EditorSessionServer(connection);
        return server;
    } 
    
    public MappedModelEditorSessionServer getMappedModelEditorSessionServer() {
        CPConnection connection = getCPConnection();
        MappedModelEditorSessionServer server = new MappedModelEditorSessionServer(connection);
        return server;
    }  

    public UserModelSecurityEditorSessionServer getUserModelSecurityEditorSessionServer() {
        CPConnection connection = getCPConnection();
        UserModelSecurityEditorSessionServer server = new UserModelSecurityEditorSessionServer(connection);
        return server;
    }
   public CubeFormulaEditorSessionServer getCubeFormulaEditorSessionServer(){
       CPConnection connection = getCPConnection();
       CubeFormulaEditorSessionServer server = new CubeFormulaEditorSessionServer(connection);
       return server;
   }

    public DimensionsProcess getDimensionsProcess() {
        return this.mCPContext.getCPConnection().getDimensionsProcess();
    }

    public XmlFormsProcess getXmlFormsProcess() {
        return this.mCPContext.getCPConnection().getXmlFormsProcess();
    }

    public ListHelper getListHelper() {
        return this.mCPContext.getCPConnection().getListHelper();
    }

    public DataEntryProcess getDataEntryProcess() {
        return this.mCPContext.getCPConnection().getDataEntryProcess();
    }

    public DataEntryProfilesProcess getDataEntryProfilesProcess() {
        return this.mCPContext.getCPConnection().getDataEntryProfilesProcess();
    }

    public BudgetCyclesProcess getBudgetCyclesProcess() {
        return this.mCPContext.getCPConnection().getBudgetCyclesProcess();
    }

    public SystemPropertysProcess getSystemPropertysProcess() {
        return this.mCPContext.getCPConnection().getSystemPropertysProcess();
    }

    public TidyTasksProcess getTidyTasksProcess() {
        return this.mCPContext.getCPConnection().getTidyTasksProcess();
    }

    public RecalculateBatchTaskProcess getRecalculateBatchTaskProcess() {
        return this.mCPContext.getCPConnection().getRecalculateBatchTaskProcess();
    }

    public LoggedInUsersProcess getLoggedInUsersProcess() {
        return this.mCPContext.getCPConnection().getLoggedInUsersProcess();
    }

    public String getCPContextId() {
        return mCPContextId;
    }

    public CPContext getCPContext() {
        return mCPContext;
    }

    public HierarchysProcess getHierarchiesProcess() {
        return this.mCPContext.getCPConnection().getHierarchysProcess();
    }

    public ExtendedAttachmentsProcess getExtAttachmentsProcess() {
        return this.mCPContext.getCPConnection().getExtendedAttachmentsProcess();
    }

    public DataTypesProcess getDataTypesProcess() {
        return this.mCPContext.getCPConnection().getDataTypesProcess();
    }

    public boolean areButtonsVisible() {
        return this.getUserContext().areButtonsVisible();
    }
    
    public ImportTasksProcess getImportTasksProcess() {
        return this.mCPContext.getCPConnection().getImportTasksProcess();
    }
    public boolean getRoadMapAvailable() {
        return this.getUserContext().getRoadMapAvailable();
    }

    public DataEntryProfileEditorSessionServer getDataEntryProfileEditorSessionServer() {
        CPConnection connection = getCPConnection();
        DataEntryProfileEditorSessionServer server = new DataEntryProfileEditorSessionServer(connection);
        return server;
    }
}
