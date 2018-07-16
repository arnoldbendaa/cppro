package cppro.utils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.DeflaterOutputStream;
import com.softproideas.app.admin.budgetcycles.mapper.FormMapper;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.admin.budgetcycles.util.BudgetCyclesValidator;
import com.softproideas.app.admin.dimensions.calendar.mapper.CalendarMapper;
import com.softproideas.app.admin.dimensions.mapper.DimensionMapper;

import java.util.Calendar;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForCycleELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForLocationELO;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.AllHierarchysELO;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.dto.message.MessageRefImpl;
import com.cedar.cp.dto.message.MessageUserPK;
import com.cedar.cp.dto.message.MessageUserRefImpl;
import com.cedar.cp.dto.message.UnreadInBoxForUserELO;
import com.cedar.cp.dto.model.AllBudgetCyclesELO;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.AllModelsWithActiveCycleForUserELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionCSO;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionSSO;
import com.cedar.cp.dto.model.BudgetCycleImpl;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.BudgetDetailsForUserELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel2ELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel3ELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel4ELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelDimensionRelPK;
import com.cedar.cp.dto.model.ModelDimensionRelRefImpl;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.recalculate.AllRecalculateBatchTasksELO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskRefImpl;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.xmlform.AllXcellXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
import com.cedar.cp.dto.xmlform.GenerateFlatFormsTaskRequest;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
import com.cedar.cp.ejb.impl.model.BudgetCycleEditorSessionSEJB;
import com.cedar.cp.ejb.impl.model.ModelEditorSessionSEJB;
import com.cedar.cp.ejb.impl.xmlform.XmlFormAccessor;
import com.cedar.cp.utc.struts.homepage.BLChildDTO;
import com.cedar.cp.utc.struts.homepage.BudgetCycleDTO;
import com.cedar.cp.utc.struts.homepage.BudgetInstructionDTO;
import com.cedar.cp.utc.struts.homepage.BudgetLocationDTO;
import com.cedar.cp.utc.struts.homepage.ModelDTO;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.TemplateCellDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.cedar.cp.util.flatform.model.workbook.editor.WorkbookMapper;
import com.softproideas.app.reviewbudget.budget.model.ReviewBudgetDTO;
import com.softproideas.app.reviewbudget.dao.BudgetCycleDAO;
import com.softproideas.app.reviewbudget.dao.BudgetCycleDAOImpl;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.xcellform.model.XCellFormDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.util.MapperUtil;
import com.softproideas.util.validation.GlobalMappedMappingValidator;
import com.softproideas.util.validation.MappingValidator;
import com.softproideas.api.cpfunctionsevaluator.CPFunctionsEvaluator;
import com.softproideas.api.cpfunctionsevaluator.CPFunctionsEvaluatorFactory;
import com.softproideas.api.cpfunctionsevaluator.FlatFormExtractorUtil;
import com.softproideas.api.cpfunctionsevaluator.FlatFormExtractorPrototype.MappingType;
import com.softproideas.app.admin.budgetcycles.mapper.BudgetCyclesMapper;
import com.softproideas.app.admin.models.mapper.ModelsMapper;
import com.softproideas.app.admin.models.model.FinanceCubeModelDTO;
import com.softproideas.app.admin.models.model.ModelDetailsDTO;
import com.softproideas.app.admin.recalculatebatches.mapper.RecalculateBatchesMapper;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDTO;
import com.softproideas.app.core.datatype.mapper.DataTypeCoreMapper;
import com.softproideas.app.core.datatype.model.DataTypeCoreDTO;
import com.softproideas.app.core.financecube.mapper.FinanceCubeCoreMapper;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.app.core.flatform.mapper.FlatFormsCoreMapper;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreWithGlobalDTO;
import com.softproideas.app.core.profile.dao.UserFlatFormDataToRecordProfile;
import com.softproideas.app.core.profile.dao.UserFlatFormLink;
import com.softproideas.app.core.profile.mapper.ProfileMapper;
import com.softproideas.app.core.users.mapper.UserCoreMapper;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.app.core.workbook.model.FlatFormExtractorDao;
import com.softproideas.app.core.workbook.model.FlatFormExtractorMapper;
import com.softproideas.app.flatformtemplate.configuration.mapper.ConfigurationMapper;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDetailsDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationTreeDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConnectionUUID;
import com.softproideas.app.flatformtemplate.configuration.model.DimensionForFlatFormTemplateDTO;
import com.softproideas.app.flatformtemplate.configuration.model.TotalDTO;
import com.softproideas.app.flatformtemplate.generate.model.CompleteFlatFormIgredients;
import com.softproideas.app.flatformtemplate.generate.model.CompleteWorkbook;
import com.softproideas.app.flatformtemplate.generate.model.GenerateDTO;
import com.softproideas.app.flatformtemplate.generate.util.GenerateUtil;
import com.softproideas.app.flatformtemplate.template.mapper.TemplateMapper;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import cppro.beans.Product;
import cppro.beans.UserAccount;
import cppro.conn.ConnectionUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import com.softproideas.app.reviewbudget.dimension.model.DimensionDTO;
import com.cedar.cp.utc.common.CPContext;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;

import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionLocalHome;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionServer;
import com.cedar.cp.ejb.api.model.ModelEditorSessionServer;
import com.cedar.cp.ejb.base.common.util.HttpUtils;
import java.net.URI;
import com.softproideas.app.admin.datatypes.model.DataTypeDTO;
import com.softproideas.app.admin.datatypes.mapper.DataTypesMapper;
import com.cedar.cp.util.Cryptography;
public class DBUtils {
	public static HttpServletRequest request;
	@Autowired
	CPContextHolder cpContextHolder;
	public static CPContext mContext;
	private JdbcTemplate jdbcTemplate;
	public static int userId = 0;
    public static class Indexes {
        private int worksheetIndex;
        private int cellIndex;
        private String mapping;
        private int formulaIndex;
        @Autowired
        public Indexes(int worksheetIndex, int cellIndex, int formulaIndex, String mapping) {
            super();
            this.worksheetIndex = worksheetIndex;
            this.cellIndex = cellIndex;
            this.mapping = mapping;
            this.formulaIndex = formulaIndex;
        }

        public Indexes(int worksheetIndex, int cellIndex, String mapping) {
            super();
            this.worksheetIndex = worksheetIndex;
            this.cellIndex = cellIndex;
            this.mapping = mapping;
            this.formulaIndex = -1;
        }

        public int getWorksheetIndex() {
            return worksheetIndex;
        }

        public int getCellIndex() {
            return cellIndex;
        }

        public String getMapping() {
            return mapping;
        }

        public void setMapping(String mapping) {
            this.mapping = mapping;
        }

        public int getFormulaIndex() {
            return formulaIndex;
        }

        public String toString() {
            return worksheetIndex + ":" + cellIndex + ":" + formulaIndex;
        }
    }
    private static Set<String> numericDataTypes;

	/* 1303 */ protected static String SQL_ALL_MODELS_WITH_ACTIVE_CYCLE_FOR_USER = "select distinct 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL.DESCRIPTION      ,MODEL.BUDGET_HIERARCHY_ID from MODEL    ,BUDGET_USER    ,BUDGET_CYCLE where 1=1  and  BUDGET_USER.USER_ID = ? AND BUDGET_USER.MODEL_ID = MODEL.MODEL_ID AND MODEL.MODEL_ID = BUDGET_CYCLE.MODEL_ID AND BUDGET_CYCLE.STATUS = 1 order by MODEL.VIS_ID";
    private static String SQL_BUDGET_DETAILS_FOR_USER_SELECT = "select\n a.MODEL_ID\n,a.MODEL_VIS_ID\n,a.BUDGET_HIERARCHY_ID\n,a.BUDGET_CYCLE_ID\n,a.CYCLE_DESC\n,a.STRUCTURE_ELEMENT_ID\n,NVL(a.DISPLAY_NAME, a.VIS_ID)\n,a.STRUC_DESC\n,a.DEPTH\n,a.levelend\n,a.leaf\n,a.lowestleveldate\n,a.leafoverride\n,b.STATE\n,b.SUBMITABLE\n,b.REJECTABLE\n,b.USER_ID\n,cursor\n(\n select\n  innerstate.STATE\n ,innerstrucelem.STRUCTURE_ELEMENT_ID\n ,NVL(innerstrucelem.DISPLAY_NAME, innerstrucelem.VIS_ID)\n ,innerstrucelem.DESCRIPTION\n ,\n (\n     select\n      count(*)\n     from\n      BUDGET_USER innerinnerusr, MODEL iim\n     where\n         a.MODEL_ID                              = innerinnerusr.MODEL_ID\n         and iim.MODEL_ID                        = innerinnerusr.MODEL_ID\n         and innerstrucelem.STRUCTURE_ID         = iim.BUDGET_HIERARCHY_ID\n         and innerstrucelem.STRUCTURE_ELEMENT_ID = innerinnerusr.STRUCTURE_ELEMENT_ID\n         and innerinnerusr.USER_ID               = ?  ) usercount\n ,\n (\n     select\n      count(*)\n     from\n      BUDGET_USER innerinnerusr, MODEL iim\n     where\n         a.MODEL_ID                              = innerinnerusr.MODEL_ID\n         and iim.MODEL_ID                        = innerinnerusr.MODEL_ID\n         and innerstrucelem.STRUCTURE_ID         = iim.BUDGET_HIERARCHY_ID\n         and innerstrucelem.STRUCTURE_ELEMENT_ID = innerinnerusr.STRUCTURE_ELEMENT_ID\n         and innerinnerusr.USER_ID              != ?\n ) otherusercount\n ,innerstate.SUBMITABLE\n ,innerstate.REJECTABLE\n ,innerstate.USER_ID\n ,innerstrucelem.leaf\n ,(     select         innerdate.planned_end_date\n     from\n         budget_cycle innerbc,\n         level_date innerdate,\n         structure_element se2\n     where\n         a.BUDGET_CYCLE_ID = innerbc.BUDGET_CYCLE_ID\n         and innerbc.budget_cycle_id = innerdate.budget_cycle_id \n         and innerstrucelem.STRUCTURE_ID = se2.structure_id \n         and innerstrucelem.STRUCTURE_element_id = se2.STRUCTURE_element_id\n         and innerdate.depth = se2.DEPTH\n ) childLevelDate from\n  STRUCTURE_ELEMENT  innerstrucelem\n ,BUDGET_STATE       innerstate\n where\n     a.BUDGET_HIERARCHY_ID               = innerstrucelem.STRUCTURE_ID\n and a.STRUCTURE_ELEMENT_ID              = innerstrucelem.PARENT_ID\n and a.BUDGET_CYCLE_ID                   = innerstate.BUDGET_CYCLE_ID(+)\n and innerstrucelem.STRUCTURE_ELEMENT_ID = innerstate.STRUCTURE_ELEMENT_ID(+)\n order by innerstrucelem.CHILD_INDEX\n) children\n,cursor (\nselect distinct\n         bi.BUDGET_INSTRUCTION_ID ,bi.VIS_ID\nfrom\n         BUDGET_INSTRUCTION bi\n         ,BDGT_INSTR_ASSGNMNT bia\nwhere\n             bia.BUDGET_CYCLE_ID  = a.BUDGET_CYCLE_ID\n         and bia.BUDGET_INSTRUCTION_ID = bi.BUDGET_INSTRUCTION_ID\n ) cycle_bi\n,cursor (\nselect distinct\n    bi.BUDGET_INSTRUCTION_ID ,bi.VIS_ID\nfrom\n BUDGET_INSTRUCTION     bi\n,BDGT_INSTR_ASSGNMNT    bia\n,STRUCTURE_ELEMENT      se1\nwhere\n    bia.BUDGET_INSTRUCTION_ID = bi.BUDGET_INSTRUCTION_ID\nand bia.BUDGET_LOCATION_HIER_ID = a.BUDGET_HIERARCHY_ID\nand se1.STRUCTURE_ID = bia.BUDGET_LOCATION_HIER_ID\nand se1.STRUCTURE_ELEMENT_ID = bia.BUDGET_LOCATION_ELEMENT_ID\nand (\n        (\n            bia.SELECT_CHILDREN <> 'Y'\n        and bia.BUDGET_LOCATION_ELEMENT_ID  = a.STRUCTURE_ELEMENT_ID\n        )\n    or\n        (\n            bia.SELECT_CHILDREN = 'Y'\n        and a.STRUCTURE_ELEMENT_ID in\n            (\n                select se2.STRUCTURE_ELEMENT_ID from STRUCTURE_ELEMENT se2\n                where\n                    se2.STRUCTURE_ID = se1.STRUCTURE_ID\n                and se2.POSITION between se1.POSITION and se1.END_POSITION\n            )\n        )\n    )\n) location_bi\n,a.locationusercount\n,a.\"CATEGORY\"\nfrom\n(\n select\n  model.MODEL_ID, model.VIS_ID model_vis_id, budget_hierarchy_id\n ,cycle.BUDGET_CYCLE_ID, cycle.DESCRIPTION cycle_desc\n ,strucelem.STRUCTURE_ELEMENT_ID\n ,strucelem.DISPLAY_NAME,strucelem.VIS_ID, strucelem.DESCRIPTION struc_desc, strucelem.CHILD_INDEX\n ,strucelem.DEPTH depth\n ,strucelem.POSITION\n ,strucelem.leaf leaf\n ,levelDate.planned_end_date levelend\n ,cycle.leaf_override leafoverride\n ,cycle.\"CATEGORY\"\n ,(  select \n         planned_end_date \n     from \n         level_date \n     where \n         depth = (select max(depth) from level_date where budget_cycle_id = cycle.BUDGET_CYCLE_ID) \n         and budget_cycle_id = cycle.BUDGET_CYCLE_ID) lowestleveldate\n ,(\n     select\n         count(*)\n     from\n         BUDGET_USER usr\n     where\n         model.MODEL_ID = usr.MODEL_ID\n         and strucelem.STRUCTURE_ID = BUDGET_HIERARCHY_ID\n         and strucelem.STRUCTURE_ELEMENT_ID = usr.STRUCTURE_ELEMENT_ID\n         and usr.USER_ID = ?\n ) locationusercount\n from\n  MODEL              model\n ,BUDGET_CYCLE       cycle\n ,Level_Date         levelDate\n ,STRUCTURE_ELEMENT  strucelem\n";
    private static String SQL_BUDGET_DETAILS_FOR_USER_WHERE_1 = " where\n     model.MODEL_ID              = cycle.MODEL_ID\n and cycle.budget_cycle_id       = levelDate.budget_cycle_id\n and levelDate.depth             = strucelem.depth and strucelem.STRUCTURE_ID      = model.BUDGET_HIERARCHY_ID\n and cycle.STATUS                = 1\n and strucelem.depth                = 0\n";
    private static String SQL_BUDGET_DETAILS_FOR_USER_FINAL_2 = ") a\n, BUDGET_STATE b, DICTIONARY d\n where\n a.BUDGET_CYCLE_ID           = b.BUDGET_CYCLE_ID(+)\n and a.STRUCTURE_ELEMENT_ID  = b.STRUCTURE_ELEMENT_ID(+)\n and a.model_id = ? and a.\"CATEGORY\" = d.VALUE \norder by\n d.ROW_INDEX, a.MODEL_VIS_ID\n,a.CYCLE_DESC\n,a.VIS_ID\n";
    private static String SQL_BUDGET_DETAILS_FOR_USER_FINAL = ") a\n, BUDGET_STATE b\n where\n a.BUDGET_CYCLE_ID           = b.BUDGET_CYCLE_ID(+)\nand  a.STRUCTURE_ELEMENT_ID  = b.STRUCTURE_ELEMENT_ID(+)\norder by\n a.MODEL_VIS_ID\n,a.CYCLE_DESC\n,a.VIS_ID\n";
    private static String SQL_BUDGET_DETAILS_FOR_USER_WHERE_2 = " where\n     cycle.BUDGET_CYCLE_ID           = ?\n and cycle.budget_cycle_id           = levelDate.budget_cycle_id\n and levelDate.depth                 = strucelem.depth and cycle.MODEL_ID                  = model.MODEL_ID\n and strucelem.STRUCTURE_ID          = model.BUDGET_HIERARCHY_ID\n and strucelem.STRUCTURE_ELEMENT_ID  = ?\n and cycle.STATUS                    = 1\n";
    private static String SQL_HIERARACHY_FOR_BUDGET_CYCLE = "SELECT DISTINCT * FROM (\n\tSELECT structure_element_id, 'N' as full_rights \n\tFROM structure_element START WITH structure_element_id in \n\t\t( \n\t\t\tSELECT bu.structure_element_id \n\t\t\tFROM budget_USER bu JOIN budget_cycle bc \n\t\t\tON bu.model_id = bc.model_id \n\t\t\tWHERE bu.user_id = ? AND bc.budget_cycle_id = ? \n\t\t) \n\tCONNECT BY structure_element_id = PRIOR parent_id \n) \nWHERE structure_element_id not in \n\t(\n\t\tSELECT bu.structure_element_id \n\t\tFROM budget_USER bu JOIN budget_cycle bc \n\t\tON bu.model_id = bc.model_id \n\t\tWHERE bu.user_id = ? AND bc.budget_cycle_id = ? \n\t) \nUNION ALL \n\tSELECT bu.structure_element_id, 'Y' as full_rights \n\tFROM budget_USER bu JOIN budget_cycle bc \n\tON bu.model_id = bc.model_id \n\tWHERE bu.user_id = ? AND bc.budget_cycle_id = ?";
    private static String SQL_ALL_DATA_ENTRY_PROFILES_FOR_USER = "select 0,      USR.USER_ID,      USR.NAME,      DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID      ,DATA_ENTRY_PROFILE.VIS_ID      ,DATA_ENTRY_PROFILE.DESCRIPTION,      DATA_ENTRY_PROFILE.XMLFORM_ID,     DATA_ENTRY_PROFILE.STRUCTURE_ID0,    DATA_ENTRY_PROFILE.STRUCTURE_ID1,    DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,    DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,    DATA_ENTRY_PROFILE.ELEMENT_LABEL0,    DATA_ENTRY_PROFILE.ELEMENT_LABEL1,    DATA_ENTRY_PROFILE.DATA_TYPE, FORMS.TYPE  from    DATA_ENTRY_PROFILE, USR, (select xml_form.xml_form_id, xml_form.type from xml_form, xml_form_user_link, budget_cycle_link where xml_form.xml_form_id = xml_form_user_link.xml_form_id (+) and (xml_form_user_link.user_id is null or xml_form_user_link.user_id = ?) and budget_cycle_link.budget_cycle_id = ? and budget_cycle_link.xml_form_id=xml_form.xml_form_id) FORMS where 1=1 and DATA_ENTRY_PROFILE.MOBILE <>'Y' AND    DATA_ENTRY_PROFILE.XMLFORM_ID = FORMS.XML_FORM_ID AND    DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID  AND    DATA_ENTRY_PROFILE.USER_ID = ?  AND   (DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID = ? OR DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID=0) AND    DATA_ENTRY_PROFILE.MODEL_ID = ? order by DATA_ENTRY_PROFILE.VIS_ID";
    protected static String SQL_ALL_FINANCE_CUBES_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.DESCRIPTION      ,FINANCE_CUBE.LOCKED_BY_TASK_ID      ,FINANCE_CUBE.HAS_DATA      ,(select 'Y' from dual where exists(select 1 from AMM_FINANCE_CUBE where AMM_FINANCE_CUBE.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID))      ,(select 'Y' from dual where exists(select 1 from AMM_FINANCE_CUBE where AMM_FINANCE_CUBE.SRC_FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID)) from FINANCE_CUBE    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?)  and FINANCE_CUBE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, FINANCE_CUBE.VIS_ID";
    protected static String SQL_ALL_MODELS_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,MODEL.DESCRIPTION      ,MODEL.BUDGET_HIERARCHY_ID      ,(SELECT 'Y' FROM dual WHERE EXISTS (SELECT 1 FROM MAPPED_MODEL WHERE MAPPED_MODEL.MODEL_ID = MODEL.MODEL_ID AND MAPPED_MODEL.GLOBAL = 'Y')) as GLOBAL     from     MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?)  order by MODEL.VIS_ID";
    protected static String SQL_ALL_DATA_TYPES = "select 0       ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH  ,DATA_TYPE.MEASURE_SCALE    ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS from DATA_TYPE where 1=1  order by DATA_TYPE.VIS_ID";
    protected static String SQL_ALL_USERS = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.FULL_NAME      ,USR.USER_DISABLED from USR where 1=1  order by NAME";
    protected static String SQL_ALL_BUDGET_CYCLES_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_CYCLE.BUDGET_CYCLE_ID      ,BUDGET_CYCLE.VIS_ID      ,BUDGET_CYCLE.DESCRIPTION      ,BUDGET_CYCLE.STATUS, PERIOD_ID, PERIOD_ID_TO, PERIOD_FROM_VISID, PERIOD_TO_VISID, BUDGET_CYCLE.CATEGORY from BUDGET_CYCLE, MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?) and BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID, BUDGET_CYCLE.VIS_ID";
    protected static String SQL_ALL_XML_FORMS_FOR_MODEL = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID    , finance_cube.finance_cube_id, finance_cube.vis_id   ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.XML_FORM_ID      ,XML_FORM.UPDATED_TIME      from XML_FORM    ,MODEL    ,FINANCE_CUBE where 1=1  and  MODEL.MODEL_ID = ? AND MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID AND FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by XML_FORM.TYPE, XML_FORM.VIS_ID";
    protected static String SQL_AVAILABLE_DIMENSIONS = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION.TYPE      ,DIMENSION.DESCRIPTION from DIMENSION where 1=1  and  DIMENSION.DIMENSION_ID not in ( select distinct DIMENSION_ID from MODEL_DIMENSION_REL) order by DIMENSION.VIS_ID";
    protected static String SQL_ALL_HIERARCHYS = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,HIERARCHY.DESCRIPTION      ,DIMENSION.TYPE from HIERARCHY    ,DIMENSION    ,MODEL_DIMENSION_REL    ,MODEL where 1=1   and HIERARCHY.DIMENSION_ID = DIMENSION.DIMENSION_ID  and  HIERARCHY.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID (+) and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID (+) order by MODEL.VIS_ID, DIMENSION.VIS_ID, HIERARCHY.VIS_ID";

    static BudgetCycleDAO budgetCycleDAO;
    static FlatFormExtractorDao flatFormExtractorDao;
    BudgetCycleEditorSessionSEJB budgetCycleServer;
    public static void initInstance(){
    	if (budgetCycleDAO == null)
    		budgetCycleDAO = new BudgetCycleDAOImpl();
    }
    public DBUtils(){
    	jdbcTemplate = getJDBCTempate();
    	budgetCycleServer = new BudgetCycleEditorSessionSEJB();
    }
	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {

		// String sql = "Select a.NAME, a.PASSWORD_BYTES a.USER_ID from USR a "
		// + " where a.NAME = ? and a.PASSWORD_BYTES= ?";
		String sql = "Select a.NAME, a.PASSWORD_BYTES, a.USER_ID from USR a "
				+ " where a.NAME = ? and a.PASSWORD_BYTES= ?";
		String enpassword = Cryptography.encrypt(password, "fc30");
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, enpassword);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			// String gender = rs.getString("Gender");
			int userId = rs.getInt("USER_ID");

			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setUserId(userId);
			// user.setGender(gender);
			return user;
		}
		return null;
	}

	public static UserAccount findUser(Connection conn, String userName) throws SQLException {

		String sql = "Select a.NAME, a.PASSWORD_BYTES,a.USER_ID from USR a " + " where a.NAME = ? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String password = rs.getString("PASSWORD_BYTES");
			int userId = rs.getInt("USER_ID");
			// String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setUserId(userId);

			// user.setGender(gender);
			return user;
		}
		return null;
	}

	public static List<Product> queryProduct(Connection conn) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from Product a ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {
			String code = rs.getString("Code");
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product();
			product.setCode(code);
			product.setName(name);
			product.setPrice(price);
			list.add(product);
		}
		return list;
	}

	public static AllModelsWithActiveCycleForUserELO getAllModelsWithActiveCycleForUser(Connection conn, int param1) {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		AllModelsWithActiveCycleForUserELO results = new AllModelsWithActiveCycleForUserELO();
		try {
			if(conn==null)
				conn = ConnectionUtils.getConnection();
			stmt = conn.prepareStatement(SQL_ALL_MODELS_WITH_ACTIVE_CYCLE_FOR_USER);
			int col = 1;
			stmt.setInt(col++, param1);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				col = 2;
				results.add(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++),
						resultSet.getInt(col++));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return results;
	}

	public static ArrayList<ModelDTO> populateHomeForm(HttpServletRequest request, Connection conn,int pModelId,int userId) {
		ArrayList<ModelDTO> modelList = null;
		modelList = new ArrayList<ModelDTO>();
//		BudgetUsersProcess budgetUserProcess = cnx.getBudgetUsersProcess();
		EntityList cycles = getBudgetDetailsForUser(conn,userId, pModelId);
		int rows = cycles.getNumRows();
		for (int i = 0; i < rows; ++i) {
			ModelDTO modelDTO = new ModelDTO();
			Object modelref = cycles.getValueAt(i, "Model");
			Integer modelId = (Integer) cycles.getValueAt(i, "ModelId");
			modelDTO.setName(modelref);
			modelDTO.setModelId(modelId.intValue());
			EntityList budgetCycleEntityList = (EntityList) cycles.getValueAt(i, "BudgetCycles");
			int noCycles = budgetCycleEntityList.getNumRows();
			ArrayList<BudgetCycleDTO> budgetCycleList = new ArrayList<BudgetCycleDTO>();

			for (int j = 0; j < noCycles; ++j) {

				BudgetCycleDTO bcDTO = new BudgetCycleDTO();
				bcDTO.setBudgetCycle(budgetCycleEntityList.getValueAt(j, "BudgetCycle"));
				bcDTO.setModelId(((Integer) budgetCycleEntityList.getValueAt(j, "ModelId")).intValue());
				bcDTO.setBudgetCycleId(((Integer) budgetCycleEntityList.getValueAt(j, "BudgetCycleId")).intValue());
				bcDTO.setCategory((String) budgetCycleEntityList.getValueAt(j, "Category"));

				EntityList cycleInstructionEntityList = (EntityList) budgetCycleEntityList.getValueAt(j,
						"CycleInstructions");
				int noOfCycleInst = cycleInstructionEntityList.getNumRows();
				ArrayList<BudgetInstructionDTO> budgetCycleInstructions = new ArrayList<BudgetInstructionDTO>();
				for (int budgetLocationEntityList = 0; budgetLocationEntityList < noOfCycleInst; ++budgetLocationEntityList) {
					BudgetInstructionDTO noLocations = new BudgetInstructionDTO();
					noLocations.setId(((Integer) cycleInstructionEntityList.getValueAt(budgetLocationEntityList,
							"BudgetInstructionId")).intValue());
					noLocations.setIdentifier(
							(String) cycleInstructionEntityList.getValueAt(budgetLocationEntityList, "VisId"));
					budgetCycleInstructions.add(noLocations);
				}
				bcDTO.setBudgetCycleInstructions(budgetCycleInstructions);

				EntityList var39 = (EntityList) budgetCycleEntityList.getValueAt(j, "BudgetLocations");
				int var40 = var39.getNumRows();
				ArrayList<BudgetLocationDTO> budgetLocationList = new ArrayList<BudgetLocationDTO>();

				for (int k = 0; k < var40; ++k) {
					BudgetLocationDTO blDTO = new BudgetLocationDTO();
					Object blState = var39.getValueAt(k, "State");
					int blStateValue = 0;
					if (blState != null && blState instanceof Integer) {
						blStateValue = ((Integer) blState).intValue();
					}

					blDTO.setState(blStateValue);
					blDTO.setStructureElementId(((Integer) var39.getValueAt(k, "StructureElementId")).intValue());
					blDTO.setIdentifier((String) var39.getValueAt(k, "VisId"));
					blDTO.setDescription((String) var39.getValueAt(k, "Description"));
					blDTO.setDepth(((Integer) var39.getValueAt(k, "Depth")).intValue());
					blDTO.setEndDate((Timestamp) var39.getValueAt(k, "EndDate"));
					// blDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
					blDTO.setFullRights(((Boolean) var39.getValueAt(k, "FullRights")).booleanValue());

					EntityList children = (EntityList) var39.getValueAt(k, "ChildLocations");
					int noChildren = children.getNumRows();
					ArrayList<BLChildDTO> budgetLocationChildList = new ArrayList<BLChildDTO>();

					for (int locationInstructionEntityList = 0; locationInstructionEntityList < noChildren; ++locationInstructionEntityList) {
						BLChildDTO noLocationInstructions = new BLChildDTO();
						Object l = children.getValueAt(locationInstructionEntityList, "State");
						int biDTO = 0;
						if (l != null && l instanceof Integer) {
							biDTO = ((Integer) l).intValue();
						}

						noLocationInstructions.setState(biDTO);
						noLocationInstructions.setStructureElementId(
								((Integer) children.getValueAt(locationInstructionEntityList, "StructureElementId"))
										.intValue());
						noLocationInstructions.setIdentifier(
								(String) children.getValueAt(locationInstructionEntityList, "ElementVisId"));

						noLocationInstructions.setDescription(
								(String) children.getValueAt(locationInstructionEntityList, "Description"));
						noLocationInstructions.setFullRights(
								((Boolean) children.getValueAt(locationInstructionEntityList, "FullRights"))
										.booleanValue());
						noLocationInstructions.setLastUpdateById(
								((Integer) children.getValueAt(locationInstructionEntityList, "LastUpdateById"))
										.intValue());

						noLocationInstructions.setUserCount(
								((Integer) children.getValueAt(locationInstructionEntityList, "UserCount")).intValue());
						noLocationInstructions
								.setEndDate((Timestamp) children.getValueAt(locationInstructionEntityList, "EndDate"));
						// noLocationInstructions.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
						noLocationInstructions.setParent(blDTO);

						budgetLocationChildList.add(noLocationInstructions);
					}

					blDTO.setChildren(budgetLocationChildList);
					EntityList var42 = (EntityList) var39.getValueAt(k, "BudgetInstructions");
					int var41 = var42.getNumRows();
					ArrayList<BudgetInstructionDTO> budgetLocationInstructions = new ArrayList<BudgetInstructionDTO>();

					for (int var44 = 0; var44 < var41; ++var44) {
						BudgetInstructionDTO var43 = new BudgetInstructionDTO();
						var43.setId(((Integer) var42.getValueAt(var44, "BudgetInstructionId")).intValue());
						var43.setIdentifier((String) var42.getValueAt(var44, "VisId"));
						budgetLocationInstructions.add(var43);
					}

					blDTO.setBudgetInstruction(budgetLocationInstructions);
					budgetLocationList.add(blDTO);
				}

				bcDTO.setBudgetLocations(budgetLocationList);

//				if (bcDTO.getCategory().equalsIgnoreCase("B") && setRoles.contains("Budget Cycle - Budget")
//						|| bcDTO.getCategory().equalsIgnoreCase("F") && setRoles.contains("Budget Cycle - Forecast")
//						|| bcDTO.getCategory().equalsIgnoreCase("M")
//								&& setRoles.contains("Budget Cycle - Management Accounts")) {
				if (bcDTO.getCategory().equalsIgnoreCase("B")  
						|| bcDTO.getCategory().equalsIgnoreCase("F") 
						|| bcDTO.getCategory().equalsIgnoreCase("M")
								) {
					budgetCycleList.add(bcDTO);
				}
			}
			// Collections.sort(budgetCycleList, new
			// BudgetCycleComparatorUtil().BudgetCategoryComparator);
			modelDTO.setBudgetCycle(budgetCycleList);
			modelList.add(modelDTO);
		}
		return modelList;
	}
	
    public static BudgetDetailsForUserELO getBudgetDetailsForUser(Connection conn,int userId, int modelId) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        BudgetDetailsForUserELO results = new BudgetDetailsForUserELO();
        try {
            int col = 1;

            String sql = new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_1).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL_2).toString();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, modelId);

            resultSet = stmt.executeQuery();
            processBudgetDetails(resultSet, results);
        } catch (SQLException sqle) {

            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append("home").append(" BudgetDetailsForUser").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append("home").append(" BudgetDetailsForUser").toString(), e);
        } finally {
        }


        return results;
    }
    private static BudgetDetailsForUserELO processBudgetDetails(ResultSet resultSet, BudgetDetailsForUserELO results) throws Exception {
        ModelPK prevModelPK = null;
        BudgetCyclePK prevBudgetCyclePK = null;
        BudgetDetailsForUserLevel2ELO level2ELO = null;
        int prevStructureElementId = -1;
        BudgetDetailsForUserLevel3ELO level3ELO = null;

        while (resultSet.next()) {
            int col = 1;

            int modelId = resultSet.getInt(col++);
            String modelName = resultSet.getString(col++);

            int hierarchyId = resultSet.getInt(col++);

            int budgetCycleId = resultSet.getInt(col++);
            String budgetCycleName = resultSet.getString(col++);

            int structureElementId = resultSet.getInt(col++);

            String structureElementVisId = resultSet.getString(col++);
            String structureElementDescription = resultSet.getString(col++);

            int elemDepth = resultSet.getInt(col++);

            Timestamp endDate = resultSet.getTimestamp(col++);

            boolean topleaf = false;
            String nullCheck = resultSet.getString(col++);
            if (nullCheck != null) {
                topleaf = nullCheck.equals("Y");
            }

            Timestamp bottomLevelEndDate = resultSet.getTimestamp(col++);

            boolean overrideLeaves = false;
            nullCheck = resultSet.getString(col++);
            if (nullCheck != null) {
                overrideLeaves = nullCheck.equals("Y");
            }

            if ((topleaf) && (overrideLeaves)) {
                endDate = bottomLevelEndDate;
            }

            
            Integer budgetState = new Integer(resultSet.getInt(col++));
            if (resultSet.wasNull()) {
                budgetState = null;
            }

            boolean submitable = false;

            nullCheck = resultSet.getString(col++);
            if (nullCheck != null) {
                submitable = nullCheck.equals("Y");
            }

            boolean rejectable = false;
            nullCheck = resultSet.getString(col++);
            if (nullCheck != null) {
                rejectable = nullCheck.equals("Y");
            }

            int lastUpdatedById = resultSet.getInt(col++);
            
            ResultSet rs2 = (ResultSet) resultSet.getObject(col++);

            ResultSet rs4 = (ResultSet) resultSet.getObject(col++);

            ResultSet rs5 = (ResultSet) resultSet.getObject(col++);

            int locationUserCount = resultSet.getInt(col++);
            
            String category = resultSet.getString(col++);

            ModelPK pkModel = new ModelPK(modelId);
            ModelRef refModel = new ModelRefImpl(pkModel, modelName);

            BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(budgetCycleId);
            BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
            BudgetCycleRef refBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, budgetCycleName);

            BudgetDetailsForUserLevel4ELO children = new BudgetDetailsForUserLevel4ELO();
            while (rs2.next()) {
                int col2 = 1;

                Integer childState = new Integer(rs2.getInt(col2++));
                if (rs2.wasNull()) {
                    childState = null;
                }

                int childElementId = rs2.getInt(col2++);
                String childElementVisId = rs2.getString(col2++);
                String childElementName = rs2.getString(col2++);

                int childUserCount = rs2.getInt(col2++);

                int otherChildUserCount = rs2.getInt(col2++);

                boolean child_submitable = false;

                nullCheck = rs2.getString(col2++);
                if (nullCheck != null) {
                    child_submitable = nullCheck.equals("Y");
                }

                boolean child_rejectable = false;
                nullCheck = rs2.getString(col2++);
                if (nullCheck != null) {
                    child_rejectable = nullCheck.equals("Y");
                }
                int child_lastUpdateById = rs2.getInt(col2++);

                boolean child_leaf = false;
                nullCheck = rs2.getString(col2++);
                if (nullCheck != null) {
                    child_leaf = nullCheck.equals("Y");
                }

                Timestamp childEndDate = rs2.getTimestamp(col2++);
                if ((child_leaf) && (overrideLeaves)) {
                    childEndDate = bottomLevelEndDate;
                }

                children.add(childState, childElementId, childElementVisId, childElementName, childUserCount, otherChildUserCount, child_submitable, child_rejectable, child_lastUpdateById, child_leaf, childEndDate);
            }
            rs2.close();

            AllBudgetInstructionsForCycleELO cycleInstructions = new AllBudgetInstructionsForCycleELO();
            while (rs4.next()) {
                int col4 = 1;

                int biId = rs4.getInt(col4++);

                String biVisId = rs4.getString(col4++);

                cycleInstructions.add(biId, biVisId);
            }
            rs4.close();

            AllBudgetInstructionsForLocationELO locationInstructions = new AllBudgetInstructionsForLocationELO();
            while (rs5.next()) {
                int col5 = 1;

                int biId = rs5.getInt(col5++);

                String biVisId = rs5.getString(col5++);

                locationInstructions.add(biId, biVisId);
            }
            rs5.close();

            if ((prevModelPK == null) || (!pkModel.equals(prevModelPK))) {
                prevModelPK = pkModel;
                level2ELO = new BudgetDetailsForUserLevel2ELO();
                prevBudgetCyclePK = null;
                prevStructureElementId = -1;
                results.add(modelId, refModel, level2ELO);
            }

            if ((prevBudgetCyclePK == null) || (!pkBudgetCycle.equals(prevBudgetCyclePK))) {
                prevBudgetCyclePK = pkBudgetCycle;
                level3ELO = new BudgetDetailsForUserLevel3ELO();
                prevStructureElementId = -1;
                level2ELO.add(refBudgetCycle, new Integer(budgetCycleId), new Integer(modelId), level3ELO, new Integer(hierarchyId), cycleInstructions, category);
            }

            if (structureElementId != prevStructureElementId) {
                prevStructureElementId = structureElementId;
                level3ELO.add(budgetState, structureElementId, structureElementVisId, structureElementDescription, elemDepth, children, locationInstructions, locationUserCount, submitable, rejectable, lastUpdatedById, endDate);
            }

        }

        return results;
    }

	public static List<String> queryExcelFileName(Connection conn) throws SQLException {
		String sql = "select filename from excelfile";
		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<String> list = new ArrayList<String>();
		while (rs.next()) {
			String file_name = rs.getString("filename");
			list.add(file_name);
		}
		return list;
	}

	public static Product findProduct(Connection conn, String code) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from Product a where a.Code=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, code);

		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product(code, name, price);
			return product;
		}
		return null;
	}

	public static void updateProduct(Connection conn, Product product) throws SQLException {
		String sql = "Update Product set Name =?, Price=? where Code=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, product.getName());
		pstm.setFloat(2, product.getPrice());
		pstm.setString(3, product.getCode());
		pstm.executeUpdate();
	}

	public static void insertProduct(Connection conn, Product product) throws SQLException {
		String sql = "Insert into Product(Code, Name,Price) values (?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, product.getCode());
		pstm.setString(2, product.getName());
		pstm.setFloat(3, product.getPrice());

		pstm.executeUpdate();
	}

	public static void deleteProduct(Connection conn, String code) throws SQLException {
		String sql = "Delete Product where Code= ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, code);
		pstm.executeUpdate();
	}

	public static void insertXmlReportFolder(Connection conn, String folderName, int userId) throws SQLException {
		String sql = "insert into XML_REPORT_FOLDER ( XML_REPORT_FOLDER_ID,PARENT_FOLDER_ID,VIS_ID,USER_ID,VERSION_NUM,UPDATED_BY_USER_ID,"
				+ "UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?)";

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		int xml_report_folder_id = Math.abs((int) (new Date().getTime()));
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, xml_report_folder_id);// xml_report_folder_id;
		pstm.setInt(2, 0);// parent_folder_id
		pstm.setString(3, folderName);// vis_id
		pstm.setInt(4, userId);// user id
		pstm.setInt(5, 0);// version number
		pstm.setInt(6, userId);// UPDATED_BY_USER_ID
		pstm.setTimestamp(7, timestamp);
		pstm.setTimestamp(8, timestamp);
		pstm.executeUpdate();
	}

	public static void insertXmlReport(Connection conn, String nodeName, int userId, int parenetId)
			throws SQLException {
		String sql = "insert into XML_REPORT ( XML_REPORT_ID,XML_REPORT_FOLDER_ID,VIS_ID,USER_ID,"
				+ "DEFINITION,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,empty_clob(),?,?,?,?)";

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		int xml_report_folder_id = (int) (new Date().getTime());
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, xml_report_folder_id);// XML_REPORT_ID;
		pstm.setInt(2, parenetId);// parent folder id
		pstm.setString(3, nodeName);// vis id
		pstm.setInt(4, userId);
		pstm.setInt(6, 0);// version number
		pstm.setInt(7, userId);
		pstm.setTimestamp(8, timestamp);
		pstm.setTimestamp(9, timestamp);
		pstm.executeUpdate();
	}

	public static void insertDataType(Connection conn, int userId, String identifier, String desc, int subType,
			String importData, String exportData, String readOnly) throws SQLException {
		String sql = "insert into DATA_TYPE (VIS_ID,DESCRIPTION,READ_ONLY_FLAG,"
				+ "AVAILABLE_FOR_IMPORT,AVAILABLE_FOR_EXPORT,SUB_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,"
				+ "UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		pstm.setString(1, identifier);
		pstm.setString(2, desc);
		pstm.setString(3, readOnly);
		pstm.setString(4, importData);
		pstm.setString(5, exportData);
		pstm.setInt(6, subType);
		pstm.setInt(7, 0);// version is 0
		pstm.setInt(8, userId);
		pstm.setTimestamp(9, timestamp);
		pstm.setTimestamp(10, timestamp);
		pstm.executeUpdate();
	}
	
	/*      */   public static UnreadInBoxForUserELO getSummaryUnreadMessagesForUser(Connection conn,String userId)
	/*      */   {
//	/* 2001 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
	/* 2002 */     PreparedStatement stmt = null;
	/* 2003 */     ResultSet resultSet = null;
	/* 2004 */     UnreadInBoxForUserELO results = new UnreadInBoxForUserELO();
	/*      */     try
	/*      */     {
//	/* 2007 */       stmt = conn.prepareStatement("select  * from ( select MESSAGE.MESSAGE_ID col1 ,MESSAGE_USER.MESSAGE_ID col2 ,MESSAGE_USER.MESSAGE_USER_ID col3 ,MESSAGE.MESSAGE_ID col4 ,MESSAGE_USER.MESSAGE_USER_ID col5 ,MESSAGE.SUBJECT col6 ,MESSAGE_USER.READ col7 ,MESSAGE.CREATED_TIME col8 ,(select distinct 'Y' from message m , MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id ) col9 , rank() over ( order by MESSAGE.CREATED_TIME desc ) rk from MESSAGE , MESSAGE_USER where MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.USER_ID = ? and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.READ <> 'Y' and MESSAGE_USER.DELETED <> 'Y'      )  where rk < 6");
	/* 2007 */       stmt = conn.prepareStatement("select  * from ( select MESSAGE.MESSAGE_ID col1 ,MESSAGE_USER.MESSAGE_ID col2 ,MESSAGE_USER.MESSAGE_USER_ID col3 ,MESSAGE.MESSAGE_ID col4 ,MESSAGE_USER.MESSAGE_USER_ID col5 ,MESSAGE.SUBJECT col6 ,MESSAGE_USER.READ col7 ,MESSAGE.CREATED_TIME col8 ,(select distinct 'Y' from message m , MESSAGE_ATTATCH ma where m.message_id = ma.message_id and m.message_id = message.message_id ) col9 , rank() over ( order by MESSAGE.CREATED_TIME desc ) rk from MESSAGE , MESSAGE_USER where MESSAGE.MESSAGE_ID = MESSAGE_USER.MESSAGE_ID and MESSAGE_USER.TYPE = 0 and MESSAGE_USER.READ <> 'Y' and MESSAGE_USER.DELETED <> 'Y'      )  where rk < 6");

	/* 2008 */       int col = 1;
//	/* 2009 */       stmt.setString(col++, userId);
	/* 2010 */       resultSet = stmt.executeQuery();
	/* 2011 */       while (resultSet.next())
	/*      */       {
	/* 2013 */         col = 1;
	/*      */ 
	/* 2016 */         MessagePK pkMessage = new MessagePK(resultSet.getLong(col++));
	/*      */ 
	/* 2019 */         String textMessage = "";
	/*      */ 
	/* 2022 */         MessageUserPK pkMessageUser = new MessageUserPK(resultSet.getLong(col++), resultSet.getLong(col++));
	/*      */ 
	/* 2026 */         String textMessageUser = "";
	/*      */ 
	/* 2029 */         MessageRefImpl erMessage = new MessageRefImpl(pkMessage, textMessage);
	/*      */ 
	/* 2035 */         MessageUserRefImpl erMessageUser = new MessageUserRefImpl(pkMessageUser, textMessageUser);
	/*      */ 
	/* 2040 */         long col1 = resultSet.getLong(col++);
	/* 2041 */         long col2 = resultSet.getLong(col++);
	/* 2042 */         String col3 = resultSet.getString(col++);
	/* 2043 */         String col4 = resultSet.getString(col++);
	/* 2044 */         if (resultSet.wasNull())
	/* 2045 */           col4 = "";
	/* 2046 */         Timestamp col5 = resultSet.getTimestamp(col++);
	/* 2047 */         String col6 = resultSet.getString(col++);
	/* 2048 */         if (resultSet.wasNull()) {
	/* 2049 */           col6 = "";
	/*      */         }
	/*      */ 
	/* 2052 */         results.add(erMessage, erMessageUser, col1, col2, col3, col4.equals("Y"), col5, col6.equals("Y"));
	/*      */       }
	/*      */ 
	/*      */     }
	/*      */     catch (SQLException sqle)
	/*      */     {
	/* 2066 */       sqle.printStackTrace();
	/*      */     }
	/*      */     finally
	/*      */     {
//	/* 2070 */       closeResultSet(resultSet);
//	/* 2071 */       closeStatement(stmt);
//	/* 2072 */       closeConnection();
	/*      */     }
	/*      */ 
	/*      */ 
	/* 2081 */     return results;
	/*      */   }
    public static BudgetDetailsForUserELO getBudgetDetailsForUser(Connection conn,int userId, boolean detailedSelection, int paramBudgetLocationId, int paramCycleId) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        BudgetDetailsForUserELO results = new BudgetDetailsForUserELO();
        try {
            Map<Integer, String> hierarchy = getHierarchyForBudgetCycle(conn,userId, paramCycleId);

            int col = 1;

            if (!detailedSelection) {
                String sql = new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_1).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL).toString();
                stmt = conn.prepareStatement(sql);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
            } else {
                String sql = new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_2).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL).toString();
                stmt = conn.prepareStatement(sql);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, paramCycleId);
                stmt.setInt(col++, paramBudgetLocationId);
            }

            resultSet = stmt.executeQuery();
            processBudgetDetailsForHierarchy(resultSet, results, hierarchy);
        } catch (SQLException sqle) {

            System.err.println(sqle);
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return results;
    }
    private static Map<Integer, String> getHierarchyForBudgetCycle(Connection conn,int userId, int paramCycleId) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        try {
            int col = 1;

            String sql = new StringBuilder().append(SQL_HIERARACHY_FOR_BUDGET_CYCLE).toString();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, paramCycleId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, paramCycleId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, paramCycleId);

            resultSet = stmt.executeQuery();


            while (resultSet.next()) {
                col = 1;

                int modelId = resultSet.getInt(col++);
                String modelName = resultSet.getString(col++);
                result.put(modelId, modelName);
            }
            return result;
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }
    private static BudgetDetailsForUserELO processBudgetDetailsForHierarchy(ResultSet resultSet, BudgetDetailsForUserELO results, Map<Integer, String> hierarchy) throws Exception {
        if (hierarchy.size() <= 0)
            return results;

        ModelPK prevModelPK = null;
        BudgetCyclePK prevBudgetCyclePK = null;
        BudgetDetailsForUserLevel2ELO level2ELO = null;
        int prevStructureElementId = -1;
        BudgetDetailsForUserLevel3ELO level3ELO = null;

        boolean filterChild = true;

        while (resultSet.next()) {
            int col = 1;

            int modelId = resultSet.getInt(col++);
            String modelName = resultSet.getString(col++);

            int hierarchyId = resultSet.getInt(col++);

            int budgetCycleId = resultSet.getInt(col++);
            String budgetCycleName = resultSet.getString(col++);

            int structureElementId = resultSet.getInt(col++);
            boolean fullRights = hierarchy.get(structureElementId) == null || (hierarchy.get(structureElementId) != null && hierarchy.get(structureElementId).equals("Y"));

            String structureElementVisId = resultSet.getString(col++);
            String structureElementDescription = resultSet.getString(col++);

            int elemDepth = resultSet.getInt(col++);

            Timestamp endDate = resultSet.getTimestamp(col++);

            boolean topleaf = false;
            String nullCheck = resultSet.getString(col++);
            if (nullCheck != null) {
                topleaf = nullCheck.equals("Y");
            }

            Timestamp bottomLevelEndDate = resultSet.getTimestamp(col++);

            boolean overrideLeaves = false;
            nullCheck = resultSet.getString(col++);
            if (nullCheck != null) {
                overrideLeaves = nullCheck.equals("Y");
            }

            if ((topleaf) && (overrideLeaves)) {
                endDate = bottomLevelEndDate;
            }

            Integer budgetState = new Integer(resultSet.getInt(col++));
            if (resultSet.wasNull()) {
                budgetState = null;
            }

            boolean submitable = false;

            nullCheck = resultSet.getString(col++);
            if (nullCheck != null) {
                submitable = nullCheck.equals("Y");
            }

            boolean rejectable = false;
            nullCheck = resultSet.getString(col++);
            if (nullCheck != null) {
                rejectable = nullCheck.equals("Y");
            }

            int lastUpdatedById = resultSet.getInt(col++);

            ResultSet rs2 = (ResultSet) resultSet.getObject(col++);

            ResultSet rs4 = (ResultSet) resultSet.getObject(col++);

            ResultSet rs5 = (ResultSet) resultSet.getObject(col++);

            int locationUserCount = resultSet.getInt(col++);
            String category = resultSet.getString(col++);

            ModelPK pkModel = new ModelPK(modelId);
            ModelRef refModel = new ModelRefImpl(pkModel, modelName);

            BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(budgetCycleId);
            BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
            BudgetCycleRef refBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, budgetCycleName);

            BudgetDetailsForUserLevel4ELO children = new BudgetDetailsForUserLevel4ELO();
            while (rs2.next()) {
                int col2 = 1;

                Integer childState = new Integer(rs2.getInt(col2++));
                if (rs2.wasNull()) {
                    childState = null;
                }

                int childElementId = rs2.getInt(col2++);
                String childElementVisId = rs2.getString(col2++);
                String childElementName = rs2.getString(col2++);

                int childUserCount = rs2.getInt(col2++);

                int otherChildUserCount = rs2.getInt(col2++);

                boolean child_submitable = false;

                nullCheck = rs2.getString(col2++);
                if (nullCheck != null) {
                    child_submitable = nullCheck.equals("Y");
                }

                boolean child_rejectable = false;
                nullCheck = rs2.getString(col2++);
                if (nullCheck != null) {
                    child_rejectable = nullCheck.equals("Y");
                }
                int child_lastUpdateById = rs2.getInt(col2++);

                boolean child_leaf = false;
                nullCheck = rs2.getString(col2++);
                if (nullCheck != null) {
                    child_leaf = nullCheck.equals("Y");
                }

                Timestamp childEndDate = rs2.getTimestamp(col2++);
                if ((child_leaf) && (overrideLeaves)) {
                    childEndDate = bottomLevelEndDate;
                }

                if (fullRights) {
                    filterChild = false;
                } else {
                    filterChild = hierarchy.get(childElementId) == null;
                }
                
                if (!filterChild) {
                    filterChild = fullRights || (hierarchy.get(childElementId) != null && hierarchy.get(childElementId).equals("Y"));
                    if (!childElementVisId.equals("_notMapped")) {
                        children.add(childState, childElementId, childElementVisId, childElementName, childUserCount, otherChildUserCount, child_submitable, child_rejectable, child_lastUpdateById, child_leaf, childEndDate, filterChild);
                    }
                }
            }
            rs2.close();

            AllBudgetInstructionsForCycleELO cycleInstructions = new AllBudgetInstructionsForCycleELO();
            while (rs4.next()) {
                int col4 = 1;

                int biId = rs4.getInt(col4++);

                String biVisId = rs4.getString(col4++);

                cycleInstructions.add(biId, biVisId);
            }
            rs4.close();

            AllBudgetInstructionsForLocationELO locationInstructions = new AllBudgetInstructionsForLocationELO();
            while (rs5.next()) {
                int col5 = 1;

                int biId = rs5.getInt(col5++);

                String biVisId = rs5.getString(col5++);

                locationInstructions.add(biId, biVisId);
            }
            rs5.close();

            if ((prevModelPK == null) || (!pkModel.equals(prevModelPK))) {
                prevModelPK = pkModel;
                level2ELO = new BudgetDetailsForUserLevel2ELO();
                prevBudgetCyclePK = null;
                prevStructureElementId = -1;
                results.add(modelId, refModel, level2ELO);
            }

            if ((prevBudgetCyclePK == null) || (!pkBudgetCycle.equals(prevBudgetCyclePK))) {
                prevBudgetCyclePK = pkBudgetCycle;
                level3ELO = new BudgetDetailsForUserLevel3ELO();
                prevStructureElementId = -1;
                level2ELO.add(refBudgetCycle, new Integer(budgetCycleId), new Integer(modelId), level3ELO, new Integer(hierarchyId), cycleInstructions, category);
            }

            if (structureElementId != prevStructureElementId) {
                prevStructureElementId = structureElementId;
                level3ELO.add(budgetState, structureElementId, structureElementVisId, structureElementDescription, elemDepth, children, locationInstructions, locationUserCount, submitable, rejectable, lastUpdatedById, endDate, fullRights);
            }

        }

        return results;
    }
    
    public static JSONArray getProfileList(Connection conn,int userId,int modelId,int budgetCycleId) throws SQLException{
		PreparedStatement pstm = conn.prepareStatement(SQL_ALL_DATA_ENTRY_PROFILES_FOR_USER);
		ResultSet resultSet = null;

		int col = 1;
		pstm.setInt(col++, userId);
		pstm.setInt(col++, budgetCycleId);
		pstm.setInt(col++, userId);
		pstm.setInt(col++, budgetCycleId);
		pstm.setInt(col++, modelId);
		JSONArray results = new JSONArray();
		int i=0;
		resultSet = pstm.executeQuery();
		while (resultSet.next()) {
			col = 4;
//			UserPK pkUser = new UserPK(resultSet.getInt(col++));
//			String textUser = resultSet.getString(col++);
//			DataEntryProfilePK pkDataEntryProfile = new DataEntryProfilePK(resultSet.getInt(col++));
//			String textDataEntryProfile = resultSet.getString(col++);
//			DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(pkUser, pkDataEntryProfile);
//			UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
//			DataEntryProfileRefImpl erDataEntryProfile = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);
			int profileId = resultSet.getInt(col++);
			String visId = resultSet.getString(col++);
			String description = resultSet.getString(col++);
//			String col1 = resultSet.getString(col++);
			int xmlFormId = resultSet.getInt(col++);
			int structureId0 = resultSet.getInt(col++);
			int structureId1 = resultSet.getInt(col++);
			int structureElementId0 = resultSet.getInt(col++);
			int structureElementId1 = resultSet.getInt(col++);
			String elementLabel0 = resultSet.getString(col++);
			String elementLabel1 = resultSet.getString(col++);
//			String dataType = resultSet.getString(col++);
			Integer formType = resultSet.getInt(col++);
			JSONObject temp = new JSONObject();
			try {
				temp.put("profileId",profileId);
				if(i==0)
					temp.put("defaultProfile", true);
				else
					temp.put("defaultProfile", false);
				temp.put("name", visId);
				temp.put("description", description);
				temp.put("formId", xmlFormId);
				temp.put("structureId0", structureId0);
				temp.put("structureId1", structureId1);
				temp.put("elementLabel0", elementLabel0==null?"null":elementLabel0);
				temp.put("elementLabel1", elementLabel1==null?"null":elementLabel1);
				temp.put("structureElementId0",structureElementId0);
				temp.put("structureElementId1", structureElementId1);
				temp.put("formType",XMLFormConstants.xmlforms[formType]);
				results.put(i,temp);
				i++;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return results;
    }
    
    public static ReviewBudgetDTO fetchReviewBudget(Connection conn,int topNodeId, int modelId, int budgetCycleId, int dataEntryProfileId, Map<Integer, Integer> selectionsMap, String dataType) throws ServiceException {
    	initInstance();
    	XCellFormDTO xCellFormDTO = null;
//        try {
//            xCellFormDTO = budgetCycleDAO.getXCellFormDTO(conn,dataEntryProfileId);
//        }  catch (Exception e2) {
//            // TODO Auto-generated catch block
//            e2.printStackTrace();
//            throw new ServiceException("error while fetching review budget dto", e2);
//        }
//        fetchReviewBudget(conn,xCellFormDTO, topNodeId, modelId, selectionsMap, dataType);
        return xCellFormDTO;

    } 
    private static void fetchReviewBudget(Connection conn,XCellFormDTO xCellFormDTO, int topNodeId, int modelId, Map<Integer, Integer> selectionsMap, String dataType) throws ServiceException {
    	
    	initInstance();
    	
        if (selectionsMap == null) {
        }
        if (selectionsMap.containsKey(0) == false || selectionsMap.get(0) == 0) {
            selectionsMap.put(0, topNodeId);
        }
        if (selectionsMap.containsKey(2) == false || selectionsMap.get(2) == 0) {
            Integer dim2 = (Integer) xCellFormDTO.getContextVariables().get(2);
            selectionsMap.put(2, dim2);
        }
        xCellFormDTO.setContextVariables(null);
        Map<String, String> contextVariables = new HashMap<String, String>();
        List<ElementDTO> selectedDimensions = budgetCycleDAO.getSelectedDimensions(selectionsMap, modelId);// uzupelnic jesli nie ma
        for (int i = 0; i < selectedDimensions.size(); i++) {
            contextVariables.put(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(i)), selectedDimensions.get(i).getName());
        }
        xCellFormDTO.setSelectedDimension(selectedDimensions);
        if (dataType != null && dataType.trim().isEmpty() == false) {
            xCellFormDTO.setDataType(dataType);
        }
        contextVariables.put(WorkbookProperties.MODEL_ID.toString(), String.valueOf(modelId));
        WorkbookDTO workbook = xCellFormDTO.getWorkbook();
        workbook.getProperties().putAll(contextVariables);
        extractWorkbook(conn,workbook);
//        noteService.fetchLastNotesForFinanceCube(workbook, selectedDimensions);
    }
    private static void extractWorkbook(Connection conn,WorkbookDTO workbook) throws ServiceException {
        try {
    		HttpSession session = ((HttpServletRequest) request).getSession();
//    		CPContext cpContext = (CPContext) session.getAttribute("cpContext");
//    		
//    		CPContextHolder cpContextHolder1=null;
//			if(cpContext!=null)
//				cpContextHolder1.init(cpContext);
			

//                FlatFormExtractor flatFormExtractor = new FlatFormExtractor((CPConnection) conn, flatFormExtractorDao);
//                FlatFormExtractor flatFormExtractor = new FlatFormExtractor();

                getValuesForMappings(workbook, conn);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    public static void getValuesForMappings(WorkbookDTO workbook, Connection connection) throws Exception {
        long startTime = startLogTime();

        Map<MappingType, List<Indexes>> processingList;
        processingList = analyzeWorkbook(workbook);
        CPFunctionsEvaluator[] evaluators = null;
        // 1. extract workbook
        if (processingList.containsKey(MappingType.MAPPING)) {
            evaluators = extractMappingsByModelVisId(workbook, processingList.get(MappingType.MAPPING), connection);
        }

        HashMap<String, Object> results = new HashMap<String, Object>();
        for (CPFunctionsEvaluator e: evaluators) {
            HashMap<String, Object> temp = e.submitBatch();
            results.putAll(temp);
        }
        processResults(workbook, results, processingList.get(MappingType.MAPPING));

        results.clear();

        // 2. extract again workbook if have cell reference
        if (processingList.containsKey(MappingType.REFERENCE)) {
            fillSimpleMappingReferences(workbook, processingList.get(MappingType.REFERENCE));
            evaluators = extractMappingsByModelVisId(workbook, processingList.get(MappingType.REFERENCE), connection);
        }
        for (CPFunctionsEvaluator e: evaluators) {
            HashMap<String, Object> temp = e.submitBatch();
            results.putAll(temp);
        }
        processResults(workbook, results, processingList.get(MappingType.REFERENCE));
        endLogTime(startTime);
    }

    private static long startLogTime() {
        long startTime = System.nanoTime();
        return startTime;
    }

    private static void endLogTime(long startTime) {
        long endTime = startLogTime();
        long duration = endTime - startTime;
        double seconds = (double) duration / 1000000000.0;
        System.out.println("== Execution in " + seconds + " sec");
    }
    private static Map<MappingType, List<Indexes>> analyzeWorkbook(WorkbookDTO workbook) {
        System.out.println("Analizing workbook");
        long start = System.currentTimeMillis();
        Map<MappingType, List<Indexes>> processingList = new HashMap<MappingType, List<Indexes>>();
        for (MappingType m: MappingType.values()) {
            processingList.put(m, new ArrayList<Indexes>());
        }
        List<WorksheetDTO> worksheets = workbook.getWorksheets();
        int cellsCount = 0;
        for (int worksheetIndex = 0; worksheetIndex < worksheets.size(); worksheetIndex++) { // iterate worksheets
            WorksheetDTO worksheet = worksheets.get(worksheetIndex);
            cellsCount += worksheet.getCells().size();
            Map<MappingType, List<Indexes>> temp = analizeWorksheet(worksheet, worksheetIndex);
            for (MappingType type: temp.keySet()) {
                processingList.get(type).addAll(temp.get(type));
            }
        }
        long end = System.currentTimeMillis();
        double time = (end - start) / 1000.0;
        System.out.println("Analized " + worksheets.size() + " worksheets");
        System.out.println("Analized " + cellsCount + " cells");
        System.out.println("Workbook Anilized in " + time + " sec");
        return processingList;
    }

    private static Map<MappingType, List<Indexes>> analizeWorksheet(WorksheetDTO worksheet, int worksheetIndex) {
        List<CellDTO> cells = (List<CellDTO>) worksheet.getCells();
        Map<MappingType, List<Indexes>> processingList = new HashMap<MappingType, List<Indexes>>();
        for (MappingType m: MappingType.values()) {
            processingList.put(m, new ArrayList<Indexes>());
        }
        for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) { // iterate cells
            if (cells != null) {
                CellExtendedDTO cell = (CellExtendedDTO) cells.get(cellIndex);
                if (cell == null) {
                    processingList.get(MappingType.UNDEFINED).add(new Indexes(worksheetIndex, cellIndex, null));
                    // return MappingType.UNDEFINED;
                } else if (cell.getInputMapping() != null && cell.getInputMapping().trim() != "") {
                    String inputMapping = cell.getInputMapping();
                    if (FlatFormExtractorUtil.containsCellReference(inputMapping)) {
                        processingList.get(MappingType.REFERENCE).add(new Indexes(worksheetIndex, cellIndex, inputMapping));
                        // return MappingType.REFERENCE;
                    } else {
                        processingList.get(MappingType.MAPPING).add(new Indexes(worksheetIndex, cellIndex, inputMapping));
                        // return MappingType.MAPPING;
                    }
                } else if (FlatFormExtractorUtil.containsFormulaMapping(cell.getFormula()) || FlatFormExtractorUtil.containsFormulaMapping(cell.getText())) {
                    if (FlatFormExtractorUtil.containsFormulaMapping(cell.getFormula()) == false && FlatFormExtractorUtil.containsFormulaMapping(cell.getText())) {
                        cell.setFormula(cell.getText());
                        cell.setText(null);
                    }
                    List<String> inputMappings = FlatFormExtractorUtil.getInputMappingsFromFormulaExpression(cell.getFormula());
                    for (int i = 0; i < inputMappings.size(); i++) {
                        if (FlatFormExtractorUtil.containsCellReference(cell.getFormula())) {// czy moze byc referencja poza mappingiem?
                            processingList.get(MappingType.REFERENCE).add(new Indexes(worksheetIndex, cellIndex, i, inputMappings.get(i)));
                        } else {
                            processingList.get(MappingType.MAPPING).add(new Indexes(worksheetIndex, cellIndex, i, inputMappings.get(i)));
                        }
                    }
                } else if (cell.getText() != null && cell.getText().trim().isEmpty() == false) {
                    FlatFormExtractorUtil.setFormattedValue(cell.getText(), cell);
                }
                processingList.get(MappingType.UNDEFINED).add(new Indexes(worksheetIndex, cellIndex, null));
            }
        }
        return processingList;
    }

    private static CPFunctionsEvaluator[] extractMappingsByModelVisId(WorkbookDTO workbook, List<Indexes> processingList, Connection connection) throws Exception {
        Map<String, CPFunctionsEvaluator> evaluatorsForModels = new HashMap<String, CPFunctionsEvaluator>();
        CPConnection conn = DriverManager.getConnection("cpapi:jboss:localhost:8080", "fc","1234",ConnectionContext.INTERACTIVE_WEB);
        for (Indexes indexes: processingList) {
            WorksheetDTO worksheet = workbook.getWorksheets().get(indexes.getWorksheetIndex());
            Map<String, String> worksheetProperties = worksheet.getProperties();
            String worksheetModelVisId = worksheetProperties.get(WorkbookProperties.MODEL_VISID.toString());
            // String worksheetModelVisId = getProperties(indexes, mv, workbook);
            if (worksheetModelVisId == null) {
                throw new ValidationException("No modelVisId set fot worksheet " + worksheet.getName());
            }

            String inputMapping = indexes.getMapping();
            MappingValidator mv = null;
            if (FlatFormExtractorUtil.isGlobal(worksheetModelVisId)) {
                mv = new GlobalMappedMappingValidator(inputMapping);
            } else {
                mv = new MappingValidator(inputMapping);
            }
            String[] errors = mv.getValidationErrors();
            if (errors.length > 0) {
                CellExtendedDTO cell = (CellExtendedDTO) worksheet.getCells().get(indexes.getCellIndex());
                workbook.setValid(false);
                worksheet.setValid(false);
                String[][] errmsgs = new String[2][];
                errmsgs[0] = errors;
                errmsgs[1] = new String[0];
                cell.setValidationMessages(errmsgs);
                if (cell.getInputMapping() != null && cell.getInputMapping().trim() != "") {
                    cell.setInputMapping(null);
                    cell.setText("#MAPPING!");
                } else {
                    cell.setFormula(null);
                    cell.setText("#FORMULA!");
                }
                continue;
            }
            String modelVisId = getModelVisId(indexes, mv, workbook);
            if (evaluatorsForModels.containsKey(modelVisId) == false) {
                Map<String, String> properties = getProperties(indexes, mv, workbook);
                
                CPFunctionsEvaluator evaluator = CPFunctionsEvaluatorFactory.getCPFunctionsEvaluator((CPConnection)conn);

                setProperties(properties, workbook.getProperties(), evaluator);
                evaluator.addBatchExpression(inputMapping, indexes.toString());
                evaluatorsForModels.put(modelVisId, evaluator);
            } else {
                evaluatorsForModels.get(modelVisId).addBatchExpression(inputMapping, indexes.toString());
            }
        }
        return evaluatorsForModels.values().toArray(new CPFunctionsEvaluator[0]);
    }

    protected static String getModelVisId(Indexes indexes, MappingValidator mv, WorkbookDTO workbook) {
        return workbook.getWorksheets().get(indexes.getWorksheetIndex()).getProperties().get(WorkbookProperties.MODEL_VISID.toString());
    }

    protected static Map<String, String> getProperties(Indexes indexes, MappingValidator mv, WorkbookDTO workbook) {
        return workbook.getWorksheets().get(indexes.getWorksheetIndex()).getProperties();
    }

    private static void processResults(WorkbookDTO workbook, HashMap<String, Object> results, List<Indexes> indexes) {
        for (Indexes in: indexes) {
            int sheetIndex = in.getWorksheetIndex();
            int cellIndex = in.getCellIndex();
            int mappingIndex = in.getFormulaIndex();
            CellExtendedDTO cell = (CellExtendedDTO) workbook.getWorksheets().get(sheetIndex).getCells().get(cellIndex);
            String mapping = in.getMapping();
            if (FlatFormExtractorUtil.containsFormulaMapping(cell.getFormula())) {
                String formula = cell.getFormula();
                String value = null;
                if (results.containsKey(in.toString())) {
                    Object result = results.get(in.toString());
                    if (result != null) {
                        value = String.valueOf(result);
                        value = FlatFormExtractorUtil.invertValueIfNecessary(mapping, value, workbook, cell);
                    }
                }
                value = zeroValueIfNecessary(mapping, value, workbook);
                if (FlatFormExtractorUtil.isNumeric(value)) {
                    formula = formula.replace(mapping, value);
                } else {
                    System.out.println("Sheet : " + workbook.getWorksheets().get(sheetIndex).getName() + " ,  Cell: column " + cell.getColumn() + " , row " + cell.getRow());
                    System.out.println(formula);
                    formula = formula.replace(mapping, value);
                    System.out.println(formula);
                    // /formula = "=\"" + value + "\"";
                }
                // formula = formula.replace(mappingsSubmitted.get(s), value);
                FlatFormExtractorUtil.setFormattedValue(formula, cell);
            } else if (FlatFormExtractorUtil.containsInputMapping(cell.getInputMapping())) {
                if (results != null && results.containsKey(in.toString())) {
                    Object result = results.get(in.toString());
                    String value = null;
                    if (result != null) {
                        value = String.valueOf(result);
                        value = FlatFormExtractorUtil.invertValueIfNecessary(mapping, value, workbook, cell);
                        FlatFormExtractorUtil.setFormattedValue(value, cell);
                    } else {/* NO RESULT */
                    }
                } else {/* NO RESULTS */
                }
            }
        }
    }

    private static String zeroValueIfNecessary(String inputMapping, String value, WorkbookDTO workbook) {
        if (GeneralUtils.isEmptyOrNull(value) || value.equals("null")) {
            // String dataType = DimensionUtil.getKeyFromMapping(inputMapping, "dt").toUpperCase();
            String dataType = FlatFormExtractorUtil.getKeyFromMapping(inputMapping, "dt").toUpperCase();
            if (GeneralUtils.isEmptyOrNull(dataType)) {
                dataType = workbook.getProperties().get(WorkbookProperties.DATA_TYPE.toString());
            }
            if (dataType != null && numericDataTypes.contains(dataType)) {
                value = "0";
            } else {
                value = "";
            }
        }
        return value;
    }

    private static void fillSimpleMappingReferences(WorkbookDTO workbook, List<Indexes> indexes) {
        for (Indexes in: indexes) {
            WorksheetDTO worksheet = workbook.getWorksheets().get(in.getWorksheetIndex());
            String mapping = in.getMapping();
            mapping = FlatFormExtractorUtil.fillSimpleMappingReferences(mapping, worksheet, workbook);
            CellExtendedDTO cell = (CellExtendedDTO) worksheet.getCells().get(in.getCellIndex());
            if (cell.getInputMapping() != null && cell.getInputMapping().trim() != "") {
                String inputMapping = cell.getInputMapping();
                inputMapping = inputMapping.replace(in.getMapping(), mapping);
                cell.setInputMapping(inputMapping);
            } else {
                String formula = cell.getFormula();
                formula = formula.replace(in.getMapping(), mapping);
                cell.setFormula(formula);
            }
            in.setMapping(mapping);
        }
    }

    /**
     * Set modelVisId, financeCubeVisId nad hierarchyVisIds to Evaluator
     */
    private static void setProperties(Map<String, String> worksheetProperties, Map<String, String> workbookProperties, CPFunctionsEvaluator evaluator) throws ValidationException {
        evaluator.setParameters(workbookProperties);
        String modelVisId = worksheetProperties != null ? worksheetProperties.get(WorkbookProperties.MODEL_VISID.toString()) : null;
        String financeCubeVisId = worksheetProperties != null ? worksheetProperties.get(WorkbookProperties.FINANCE_CUBE_VISID.toString()) : null;
        if ((modelVisId != null) && (financeCubeVisId != null)) {
            int dimensionCount = evaluator.setModelAndFinanceCube(modelVisId, financeCubeVisId);
            String[] hierarchyVisIds = new String[dimensionCount];
            for (int dimIndex = 0; dimIndex < dimensionCount; dimIndex++) {
                String visId = worksheetProperties.get(FlatFormExtractorUtil.getHierarchyVisIdPropertyName(dimIndex));
                if (visId == null) {
                    throw new ValidationException("Dimension " + dimIndex + " hierarchy vis id not defined");
                }
                hierarchyVisIds[dimIndex] = visId;
            }
            evaluator.setHierarchies(hierarchyVisIds);
        }
    }
//    public ConfigurationTreeDTO browseConfigurations(Boolean disableDirectories) throws ServiceException, DaoException {
//        try {
//            String query = "select TMPL_CONF_UUID, VIS_ID, VERSION_NUM, PARENT_UUID, IS_DIRECTORY, CHILD_INDEX from XML_FORM_TMPL_CONF order by CHILD_INDEX asc";
//            // IS_DIRECTORY DESC, TMPL_CONF_ID asc
//            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
//            ConfigurationTreeDTO configurationRoot = ConfigurationMapper.mapXmlFormConfigurations(rows, disableDirectories);
//
//            return configurationRoot;
//        } catch (Exception e) {
//            String errorMsg = "Error while executing select query";
//            logger.error(errorMsg, e);
//            throw new DaoException(errorMsg, e);
//        }
//    }

    public static List<FinanceCubeModelCoreDTO> browseFinanceCubes(Connection conn, int userId) {
        AllFinanceCubesELO allFinanceCubesELO = getAllFinanceCubesForLoggedUser(conn,userId);
        return FinanceCubeCoreMapper.mapAllFinanseCubesELO(allFinanceCubesELO);
    }
    
    
	public static AllFinanceCubesELO getAllFinanceCubesForLoggedUser(Connection conn,int userId) {
/*  600 */     PreparedStatement stmt = null;
/*  601 */     ResultSet resultSet = null;
/*  602 */     AllFinanceCubesELO results = new AllFinanceCubesELO();
/*      */     try
/*      */     {
/*  605 */       stmt = conn.prepareStatement(SQL_ALL_FINANCE_CUBES_FOR_USER);
/*  606 */       int col = 1;
			 stmt.setInt(1, userId);
/*  607 */       resultSet = stmt.executeQuery();
/*  608 */       while (resultSet.next())
/*      */       {
/*  610 */         col = 2;
/*      */ 
/*  613 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  616 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  619 */         FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
/*      */ 
/*  622 */         String textFinanceCube = resultSet.getString(col++);
/*      */ 
/*  627 */         FinanceCubeCK ckFinanceCube = new FinanceCubeCK(pkModel, pkFinanceCube);
/*      */ 
/*  633 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  639 */         FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(ckFinanceCube, textFinanceCube);
/*      */ 
/*  644 */         String col1 = resultSet.getString(col++);
///*  645 */         Integer col2 = getWrappedIntegerFromJdbc(resultSet, col++);
					Integer col2 = resultSet.getInt(col++);
/*  646 */         String col3 = resultSet.getString(col++);
/*  647 */         if (resultSet.wasNull())
/*  648 */           col3 = "";
/*  649 */         Boolean col4 = resultSet.getBoolean(col++);
/*  650 */         Boolean col5 = resultSet.getBoolean(col++);
/*      */ 
/*  653 */         results.add(erFinanceCube, erModel, col1, col2, col3.equals("Y"), col4, col5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
					sqle.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/*      */ 
/*  680 */     return results;
		}
	
    public static ConfigurationTreeDTO browseConfigurations(Boolean disableDirectories) throws ServiceException, DaoException {
        try {
        	
            String query = "select TMPL_CONF_UUID, VIS_ID, VERSION_NUM, PARENT_UUID, IS_DIRECTORY, CHILD_INDEX from XML_FORM_TMPL_CONF order by CHILD_INDEX asc";
            // IS_DIRECTORY DESC, TMPL_CONF_ID asc
            Connection conn = ConnectionUtils.getConnection();
            
    		PreparedStatement pstm = conn.prepareStatement(query);
    		ResultSet resultSet = null;
    		int col = 1;
    		JSONArray results = new JSONArray();
    		int i=0;
    		List<Map<String, Object>> rows = new ArrayList();
    		resultSet = pstm.executeQuery();
    		while (resultSet.next()) {
    			col = 1;
    			HashMap<String,Object> temp = new HashMap<String,Object>();
    			temp.put("TMPL_CONF_UUID", resultSet.getString(col++));
    			temp.put("VIS_ID", resultSet.getString(col++));
    			temp.put("VERSION_NUM", resultSet.getBigDecimal(col++));
    			temp.put("PARENT_UUID", resultSet.getString(col++));
    			temp.put("IS_DIRECTORY", resultSet.getString(col++));
    			temp.put("CHILD_INDEX", resultSet.getBigDecimal(col++));
    			rows.add(temp);
    		}
//            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            ConfigurationTreeDTO configurationRoot = ConfigurationMapper.mapXmlFormConfigurations(rows, disableDirectories);
            return configurationRoot;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            throw new DaoException(errorMsg, e);
        }
    }
    public static TemplateDetailsDTO browseTemplates(Boolean disableDirectories) throws ServiceException, SQLException, DaoException {
        try {
            String query = "select TMPL_UUID, VIS_ID, DESCRIPTION, VERSION_NUM, JSON_FORM, PARENT_UUID, TYPE, DEFINITION, CHILD_INDEX from xml_form_tmpl order by TYPE asc, TMPL_ID asc";
//            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            Connection conn = ConnectionUtils.getConnection();
    		PreparedStatement pstm = conn.prepareStatement(query);
    		ResultSet resultSet = null;
    		int col = 1;
    		JSONArray results = new JSONArray();
    		int i=0;
    		List<Map<String, Object>> rows = new ArrayList();
    		resultSet = pstm.executeQuery();
    		while (resultSet.next()) {
    			col = 1;
    			HashMap<String,Object> temp = new HashMap<String,Object>();
    			temp.put("TMPL_UUID", resultSet.getString(col++));
    			temp.put("VIS_ID", resultSet.getString(col++));
    			temp.put("DESCRIPTION", resultSet.getString(col++));
    			temp.put("VERSION_NUM", resultSet.getBigDecimal(col++));
    			temp.put("JSON_FORM", resultSet.getString(col++));
    			temp.put("PARENT_UUID", resultSet.getString(col++));
    			temp.put("TYPE", resultSet.getBigDecimal(col++));
    			temp.put("DEFINITION", resultSet.getString(col++));
    			temp.put("CHILD_INDEX", resultSet.getBigDecimal(col++));
    			rows.add(temp);
    		}
            TemplateDetailsDTO templateList = TemplateMapper.mapXmlFormTemplatesTree(rows, disableDirectories);
            return templateList;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            throw new DaoException(errorMsg, e);
        }
    }
    public static List<ModelCoreWithGlobalDTO> browseModelsForLoggedUser(int userId) throws ServiceException {
        AllModelsELO allModelsForLoggedUser;
		try {
			allModelsForLoggedUser = getAllModelsForLoggedUser(userId);
	        List<ModelCoreWithGlobalDTO> modelsDTO = ModelCoreMapper.mapAllModelsELOToModelsWithGlobalDTO(allModelsForLoggedUser);
	        return modelsDTO;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
	public static AllModelsELO getAllModelsForLoggedUser(int userId) throws ClassNotFoundException {
	    PreparedStatement stmt = null;
	    ResultSet resultSet = null;
	    AllModelsELO results = new AllModelsELO();
	    try {
	        Connection conn = ConnectionUtils.getConnection();
	        stmt = conn.prepareStatement(SQL_ALL_MODELS_FOR_USER);
	        int col = 1;
	        stmt.setInt(col++, userId);
	        resultSet = stmt.executeQuery();
	        while (resultSet.next()) {
	            col = 2;
	            ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
	            String textModel = resultSet.getString(col++);
	            ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
	            String description = resultSet.getString(col++);
	            int budgetHierarchyId = resultSet.getInt(col++);
	            String strGlobal = resultSet.getString(col++);
	            Boolean global = false;
	            if(strGlobal!=null)
	            	global = strGlobal.equals("Y");
	            results.add(erModel, description, budgetHierarchyId, global);
	        }
	    } catch (SQLException sqle) {
	        sqle.printStackTrace();
	    } finally {
	    }
	    return results;
	}
	
   public List<DataTypeDTO> browseDataTypesForAdmin() throws ServiceException {
        AllDataTypesELO allDataTypes = getAllDataTypes();
        List<DataTypeDTO> dataTypesDTOList = DataTypesMapper.mapAllDataTypesELO(allDataTypes);
        return dataTypesDTOList;
    }
   
	public static List<DataTypeCoreDTO> browseDataTypes() throws ServiceException {
		AllDataTypesELO allDataTypes = getAllDataTypes();
		List<DataTypeCoreDTO> dataTypesDTOList = DataTypeCoreMapper.mapAllDataTypesELO(allDataTypes);
		return dataTypesDTOList;
	}
/*      */   public static AllDataTypesELO getAllDataTypes()
/*      */   {
/*  808 */     PreparedStatement stmt = null;
/*  809 */     ResultSet resultSet = null;
/*  810 */     AllDataTypesELO results = new AllDataTypesELO();
/*      */     try
/*      */     {
	 				Connection conn = ConnectionUtils.getConnection();
/*  813 */       stmt = conn.prepareStatement(SQL_ALL_DATA_TYPES);
/*  814 */       int col = 1;
/*  815 */       resultSet = stmt.executeQuery();
/*  816 */       while (resultSet.next())
/*      */       {
/*  818 */         col = 2;
/*      */ 
/*  821 */         DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
/*      */ 
/*  824 */         String textDataType = resultSet.getString(col++);
/*  825 */         String erDataTypeDescription = resultSet.getString(col++);
/*  826 */         int erDataTypeSubType = resultSet.getInt(col++);
/*  827 */         Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
/*  828 */         Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
/*      */ 		   Integer erDataTypeMeasureScale = Integer.valueOf(resultSet.getInt(col++));
/*  832 */         DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength, erDataTypeMeasureScale);
/*      */ 
/*  841 */         String col1 = resultSet.getString(col++);
/*  842 */         int col2 = resultSet.getInt(col++);
					Integer col3 = resultSet.getInt(col++);
/*      */ 
/*  846 */         results.add(erDataType, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException | ClassNotFoundException sqle)
/*      */     {
					sqle.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  870 */     return results;
/*      */   }

	public static AllUsersELO getAllUsers() throws ClassNotFoundException {
	    PreparedStatement stmt = null;
	    ResultSet resultSet = null;
	    AllUsersELO results = new AllUsersELO();
	    try {
	    	Connection conn = ConnectionUtils.getConnection();
	        stmt = conn.prepareStatement(SQL_ALL_USERS);
	        int col = 1;
	        resultSet = stmt.executeQuery();
	        while (resultSet.next()) {
	            col = 2;
	            UserPK pkUser = new UserPK(resultSet.getInt(col++));
	            String textUser = resultSet.getString(col++);
	            UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
	            String col1 = resultSet.getString(col++);
	            String col2 = resultSet.getString(col++);
	            if (resultSet.wasNull()) {
	                col2 = "";
	            }
	            results.add(erUser, col1, col2.equals("Y"));
	        }
	
	    } catch (SQLException sqle) {
	    	sqle.printStackTrace();
	    } finally {
	    }
	
	
	    return results;
	}
	
    public static TemplateDetailsDTO fetchTemplate(UUID templateUUID, AllUsersELO allUsersELO) throws ServiceException, SQLException, DaoException {
        try {
            String query = "select TMPL_UUID, VIS_ID, DESCRIPTION, VERSION_NUM, JSON_FORM, PARENT_UUID, TYPE, DEFINITION, CHILD_INDEX from xml_form_tmpl WHERE TMPL_UUID = ?";
            Connection conn = ConnectionUtils.getConnection();
    		PreparedStatement pstm = conn.prepareStatement(query);
    		pstm.setString(1, templateUUID.toString());
    		ResultSet resultSet = null;
    		int col = 1;
    		JSONArray results = new JSONArray();
    		Map<String, Object> row = new HashMap<String,Object>();
    		 resultSet = pstm.executeQuery();
    		while (resultSet.next()) {
    			col = 1;
    			HashMap<String,Object> temp = new HashMap<String,Object>();
    			temp.put("TMPL_UUID", resultSet.getString(col++));
    			temp.put("VIS_ID", resultSet.getString(col++));
    			temp.put("DESCRIPTION", resultSet.getString(col++));
    			temp.put("VERSION_NUM", resultSet.getBigDecimal(col++));
    			temp.put("JSON_FORM", resultSet.getString(col++));
    			temp.put("PARENT_UUID", resultSet.getString(col++));
    			temp.put("TYPE", resultSet.getBigDecimal(col++));
//    			temp.put("TYPE", resultSet.getInt(col++));
    			temp.put("DEFINITION", resultSet.getString(col++));
    			temp.put("CHILD_INDEX", resultSet.getBigDecimal(col++));
    			row=temp;
    		}
            TemplateDetailsDTO template = new TemplateDetailsDTO();
            List<Integer> userIds = new ArrayList<Integer>();
            if (allUsersELO != null) {
                userIds = fetchUserIds(templateUUID);
            }
            
            template = TemplateMapper.mapXmlFormTemplated(row, true, false, userIds, allUsersELO);
            return template;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            throw new DaoException(errorMsg, e);
        }
    }
    private static List<Integer> fetchUserIds(UUID templateUUID) throws ClassNotFoundException, SQLException {
        List<Integer> userIds = new ArrayList<Integer>();
        String query = "select USER_ID from xml_form_tmpl_user_link WHERE TMPL_UUID = ?";
        
        Connection conn = ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, templateUUID.toString());
		ResultSet resultSet = null;
		int col=0;
		List<Map<String, Object>> rows = new ArrayList();
		resultSet = pstm.executeQuery();
		while (resultSet.next()) {
			col = 1;
			HashMap<String,Object> temp = new HashMap<String,Object>();
			temp.put("USER_ID", resultSet.getInt(col++));
			rows.add(temp);
		}
        for (Map<String, Object> map: rows) {
            Integer userId = ((BigDecimal) map.get("USER_ID")).intValue();
            userIds.add(userId);
        }
        return userIds;
    }

    public static ConfigurationDetailsDTO fetchConfiguration(UUID configurationUUID) throws ServiceException, DaoException {
        try {
            String query = "select VIS_ID, VERSION_NUM, PARENT_UUID, IS_DIRECTORY from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?";
            Connection conn = ConnectionUtils.getConnection();
    		PreparedStatement pstm = conn.prepareStatement(query);
    		pstm.setString(1, configurationUUID.toString());
    		ResultSet resultSet = null;
    		resultSet = pstm.executeQuery();
    		Map<String, Object> row = new HashMap<String,Object>();
    		while(resultSet.next()){
    			int col=1;
    			row.put("VIS_ID", resultSet.getString(col++));
    			row.put("VERSION_NUM", resultSet.getBigDecimal(col++));
    			row.put("PARENT_UUID", resultSet.getString(col++));
    			row.put("IS_DIRECTORY", resultSet.getString(col++));
    		}
    		pstm.close();
//            Map<String, Object> row = getJdbcTemplate().queryForMap(query, configurationUUID.toString());
            String query2 = "select TMPL_CONF_DIM_UUID, VIS_ID, SHEET_NAME, MODEL_VIS_ID, CHILD_INDEX, IS_HIDDEN, EXCLUDED_DIMENSIONS from XML_FORM_TMPL_CONF_DIM where TMPL_CONF_UUID = ?";
    		pstm = conn.prepareStatement(query2);
    		pstm.setString(1, configurationUUID.toString());
    		resultSet = pstm.executeQuery();
    		List<Map<String, Object>> rowsDimension = new ArrayList();
    		while(resultSet.next()){
    			Map<String,Object> temp = new HashMap<String,Object>();
    			int col=1;
    			temp.put("TMPL_CONF_DIM_UUID", resultSet.getString(col++));
    			temp.put("VIS_ID", resultSet.getString(col++));
    			temp.put("SHEET_NAME", resultSet.getString(col++));
    			temp.put("MODEL_VIS_ID", resultSet.getString(col++));
    			temp.put("CHILD_INDEX", resultSet.getBigDecimal(col++));
    			temp.put("IS_HIDDEN", resultSet.getString(col++));
    			temp.put("EXCLUDED_DIMENSIONS", resultSet.getString(col++));
    			rowsDimension.add(temp);
    		}
    		
//            List<Map<String, Object>> rowsDimension = getJdbcTemplate().queryForList(query2, configurationUUID.toString());
            String query3 = "select t.TMPL_CONF_TOTAL_UUID, t.SHEET_NAME, t.CHILD_INDEX, t.IS_HIDDEN, t.IS_GRAND, listagg(dt.TMPL_CONF_DIM_UUID, ',') within group (order by dt.TMPL_CONF_DIM_UUID) as DIMS FROM XML_FORM_TMPL_CONF_TOTAL t left join XML_FORM_TMPL_CONF_DIM_TOTAL dt on dt.TMPL_CONF_TOTAL_UUID = t.TMPL_CONF_TOTAL_UUID where t.TMPL_CONF_UUID = ? group by t.TMPL_CONF_TOTAL_UUID, t.SHEET_NAME, t.CHILD_INDEX, t.IS_HIDDEN, t.IS_GRAND";
    		pstm = conn.prepareStatement(query3);
    		pstm.setString(1, configurationUUID.toString());
    		resultSet = pstm.executeQuery();
    		List<Map<String, Object>> rowsTotal = new ArrayList();
    		while(resultSet.next()){
    			Map<String,Object> temp = new HashMap<String,Object>();
    			int col=1;
    			temp.put("TMPL_CONF_TOTAL_UUID", resultSet.getString(col++));
    			temp.put("SHEET_NAME", resultSet.getString(col++));
    			BigDecimal childIndex = resultSet.getBigDecimal(col++);
    			temp.put("CHILD_INDEX", childIndex);
    			temp.put("IS_HIDDEN", resultSet.getString(col++));
    			temp.put("IS_GRAND", resultSet.getString(col++));
    			temp.put("DIMS", resultSet.getString(col++));
    			rowsTotal.add(temp);
    		}
            ConfigurationDetailsDTO configuration = ConfigurationMapper.mapXmlFormConfigurationDetails(configurationUUID, row, rowsDimension, rowsTotal);
            return configuration;
        } catch (Exception e) {
        	e.printStackTrace();
            String errorMsg = "Error while executing select query";
            throw new DaoException(errorMsg, e);
        }
    }
    public ResponseMessage updateTemplate(final TemplateDetailsDTO template, Boolean fullUpdate) throws DaoException {
        try {
            final UUID templateUUID = template.getTemplateUUID();
            ResponseMessage message = new ResponseMessage();

            validateIfRoot(templateUUID, "update");

            int currentVersionNum = template.getVersionNum();
            // validate versionNum
            if(jdbcTemplate==null)
            	jdbcTemplate = getJDBCTempate();
            Map<String, Object> currentDBRow = getJDBCTempate().queryForMap("select VERSION_NUM from xml_form_tmpl WHERE TMPL_UUID = ?", templateUUID.toString());
            ValidationError validationError = MapperUtil.validateVersionNum(currentVersionNum, currentDBRow);
            if (validationError.isError()) {
                return validationError;
            }
            String query;
            PreparedStatementSetter pss;
            if (fullUpdate) {
                query = "UPDATE XML_FORM_TMPL SET VIS_ID = ?, DESCRIPTION = ?, VERSION_NUM = ?, JSON_FORM = ?, PARENT_UUID = ?, TYPE = ?, DEFINITION = ? WHERE TMPL_UUID = ?";
                pss = prepareFullUpdateStatement(template, templateUUID, currentVersionNum + 1);
            } else {
                query = "UPDATE XML_FORM_TMPL SET VIS_ID = ?, DESCRIPTION = ?, VERSION_NUM = ? WHERE TMPL_UUID = ?";
                pss = prepareSimpleUpdateStatement(template, templateUUID, currentVersionNum + 1);
            }
            updateSelectedUsers(template);
            // update template
            int result = getJDBCTempate().update(query, pss);
            if (result >= 1) {
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing update query";
            throw new DaoException(errorMsg, e);
        }
    }

    private void validateIfRoot(UUID templateUUID, String operation) throws DaoException {
        String selectQuery = "select PARENT_UUID from xml_form_tmpl WHERE TMPL_UUID = ?";
        if(jdbcTemplate==null)
        	jdbcTemplate = getJDBCTempate();

        Map<String, Object> row = jdbcTemplate.queryForMap(selectQuery, templateUUID.toString());
        String parentUUIDString = (String) row.get("PARENT_UUID");
        if (parentUUIDString == null) {
            throw new DaoException("Can't " + operation + " root");
        }
    }
    private void updateSelectedUsers(final TemplateDetailsDTO template) {

        final UUID templateUUID = template.getTemplateUUID();
        String queryDeletingTemplates = "delete from XML_FORM_TMPL_USER_LINK  where TMPL_UUID = ?";
        String queryUpdateTemplates = "insert into XML_FORM_TMPL_USER_LINK (USER_ID, TMPL_UUID) values (?, ?)";
        BatchPreparedStatementSetter upd = updateBatchInsertUserTemplate(template, templateUUID);
        getJDBCTempate().update(queryDeletingTemplates, templateUUID.toString());
        getJDBCTempate().batchUpdate(queryUpdateTemplates, upd);
    }
    private PreparedStatementSetter prepareFullUpdateStatement(final TemplateDetailsDTO template, final UUID templateUUID, final int versionNum) {
        final Integer type = TemplateMapper.mapType(template.getType());
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, template.getVisId());
                ps.setString(2, template.getDescription());
                ps.setInt(3, versionNum);
                ps.setString(4, template.getJsonForm());
                if (template.getParentUUID() != null) {
                    ps.setString(5, template.getParentUUID().toString());
                } else {
                    ps.setNull(5, 0);
                }
                ps.setInt(6, type);
                ps.setString(7, WorkbookMapper.mapWorkbookDTOToXmlString(template.getWorkbook()));
                if (templateUUID == null) {
                    ps.setString(8, UUID.randomUUID().toString());
                } else {
                    ps.setString(8, templateUUID.toString());
                }
            }
        };
        return pss;
    };
    private BatchPreparedStatementSetter updateBatchInsertUserTemplate(final TemplateDetailsDTO template, final UUID templateUUID) {
        BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
            List<UserCoreDTO> users = template.getUsers();

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UserCoreDTO user = users.get(i);
                ps.setInt(1, user.getUserId());
                ps.setString(2, templateUUID.toString());
            }

            @Override
            public int getBatchSize() {

                return users.size();
            }

        };
        return pss;
    }
    private PreparedStatementSetter prepareSimpleUpdateStatement(final TemplateDetailsDTO template, final UUID templateUUID, final int versionNum) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, template.getVisId());
                ps.setString(2, template.getDescription());
                ps.setInt(3, versionNum);
                ps.setString(4, templateUUID.toString());
            }
        };
        return pss;
    }

    public static JdbcTemplate getJDBCTempate() {
        
    	DataSource ds;
		String lookupName = "java:jboss/jdbc/fc";
		try
		{
		 InitialContext ic = new InitialContext();
		 ds = ((DataSource)ic.lookup(lookupName));
		 JdbcTemplate jtm = new JdbcTemplate(ds);
		 return jtm;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
    }
    public ResponseMessage deleteTemplate(UUID templateUUID) throws DaoException {
        try {
            ResponseMessage message = new ResponseMessage();

            validateIfRoot(templateUUID, "delete");

            String queryDecrementIndexes = "UPDATE XML_FORM_TMPL SET CHILD_INDEX = CHILD_INDEX - 1 WHERE PARENT_UUID = (SELECT PARENT_UUID FROM XML_FORM_TMPL WHERE TMPL_UUID = ?) AND CHILD_INDEX > (SELECT CHILD_INDEX FROM XML_FORM_TMPL WHERE TMPL_UUID = ?)";
            PreparedStatementSetter pssDecrementIndexes = prepareUpdateIndexesStatement(templateUUID);
            getJDBCTempate().update(queryDecrementIndexes, pssDecrementIndexes);

            String query = "DELETE FROM XML_FORM_TMPL WHERE TMPL_UUID = ?";
            int result = getJDBCTempate().update(query, templateUUID.toString());
            if (result >= 1) {
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing delete query";
            throw new DaoException(errorMsg, e);
        }
    }
    private PreparedStatementSetter prepareUpdateIndexesStatement(final UUID templateUUID) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, templateUUID.toString());
                ps.setString(2, templateUUID.toString());
            }
        };
        return pss;
    }
    public ResponseMessage updateConfiguration(ConfigurationDetailsDTO configuration) throws DaoException {
        try {
            ResponseMessage message = new ResponseMessage();
            String query = "update XML_FORM_TMPL_CONF set VIS_ID = ?, VERSION_NUM = ?, PARENT_UUID = ?, IS_DIRECTORY = ? where TMPL_CONF_UUID = ?";
            int currentVersionNum = configuration.getVersionNum();
            // validate versionNum
            Map<String, Object> currentDBRow = getJDBCTempate().queryForMap("select VERSION_NUM from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?", configuration.getConfigurationUUID().toString());
            ValidationError validationError = MapperUtil.validateVersionNum(currentVersionNum, currentDBRow);
            if (validationError.isError()) {
                return validationError;
            }
            PreparedStatementSetter pss = prepareStatementForConfiguration(configuration, configuration.getConfigurationUUID(), currentVersionNum + 1, false);
            int result = getJDBCTempate().update(query, pss);
            if (result >= 1) {
                List<ConnectionUUID> connections = prepareListToUpdate(configuration);
                List<DimensionForFlatFormTemplateDTO> dimensionList = configuration.getDimensions();
                List<TotalDTO> totalList = configuration.getTotals();
                if (dimensionList != null) {
                    updateDimensions(dimensionList, configuration.getConfigurationUUID().toString());
                }
                if (totalList != null) {
                    updateTotals(totalList, configuration.getConfigurationUUID().toString());
                }
                updateConnectionsTotalsWithDimensions(connections, configuration.getConfigurationUUID().toString());
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing update queries";
            throw new DaoException(errorMsg, e);
        }
    }
    private PreparedStatementSetter prepareStatementForConfiguration(final ConfigurationDTO configuration, final UUID configurationUUID, final int versionNum, final boolean isChildIndex) {
        final String isDirectory = (configuration.isDirectory()) ? "Y" : "N";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, configuration.getConfigurationVisId());
                ps.setInt(2, versionNum);
                if (configuration.getParentUUID() != null) {
                    ps.setString(3, configuration.getParentUUID().toString());
                } else {
                    ps.setNull(3, 0);
                }
                ps.setString(4, isDirectory);
                ps.setString(5, configurationUUID.toString());
                if (isChildIndex) {
                    ps.setInt(6, configuration.getIndex());
                }
            }
        };
        return pss;
    };
    /**
     * Add UUID for recently created elements in Configurations. Elements can be: Dimension, Total, Connection. Connection determines attachment Dimension for Total.
     * @param configuration
     */
    private List<ConnectionUUID> prepareListToUpdate(ConfigurationDetailsDTO configuration) {
        UUID dimensionUUID;
        UUID totalUUID;
        List<DimensionForFlatFormTemplateDTO> dimensionList = configuration.getDimensions();
        List<TotalDTO> totalList = configuration.getTotals();
        List<ConnectionUUID> connectionUUIDs = new ArrayList<ConnectionUUID>();
        List<DimensionForFlatFormTemplateDTO> dimensionTotalList;
        for (TotalDTO total: totalList) {
            if (total.getTotalUUID() == null) {
                totalUUID = UUID.randomUUID();
                total.setTotalUUID(totalUUID);
            } else {
                total.setOldTotal(true);
            }
        }
        for (DimensionForFlatFormTemplateDTO dimension: dimensionList) {
            if (dimension.getDimensionUUID() == null) {
                dimensionUUID = UUID.randomUUID();
                dimension.setDimensionUUID(dimensionUUID);
            } else {
                dimension.setOldDimension(true);
            }
            for (TotalDTO total: totalList) {

                dimensionTotalList = total.getDimensionList();
                if (dimensionTotalList != null) {
                    for (DimensionForFlatFormTemplateDTO dimensionTotal: dimensionTotalList) {
                        if (dimension.getDimensionVisId().equals(dimensionTotal.getDimensionVisId()) && dimension.getModelVisId().equals(dimensionTotal.getModelVisId())) {
                            ConnectionUUID connection = new ConnectionUUID();
                            connection.setLeft(total.getTotalUUID());
                            connection.setRight(dimension.getDimensionUUID());
                            connectionUUIDs.add(connection);
                        }
                    }
                }
            }
        }
        return connectionUUIDs;
    }
    /**
     * Updating list of Dimension.
     * @param dimensionList
     * @param configurationUUID_String
     */
    private void updateDimensions(List<DimensionForFlatFormTemplateDTO> dimensionList, String configurationUUID_String) {
        List<DimensionForFlatFormTemplateDTO> oldDimensionList = new ArrayList<DimensionForFlatFormTemplateDTO>();
        List<DimensionForFlatFormTemplateDTO> newDimensionList = new ArrayList<DimensionForFlatFormTemplateDTO>();
        splitDimesnionList(dimensionList, oldDimensionList, newDimensionList);

        deleteBatchDimensions(oldDimensionList, configurationUUID_String);
        insertBatchDimensions(newDimensionList, configurationUUID_String);
        updateBatchDimensions(oldDimensionList, configurationUUID_String);
    }

    /**
     * Updating list of Totals.
     * @param totalList
     * @param configurationUUID_String
     */
    private void updateTotals(List<TotalDTO> totalList, String configurationUUID_String) {
        List<TotalDTO> oldTotalList = new ArrayList<TotalDTO>();
        List<TotalDTO> newTotalList = new ArrayList<TotalDTO>();
        splitTotalList(totalList, oldTotalList, newTotalList);

        deleteBatchTotals(oldTotalList, configurationUUID_String);
        insertBatchTotals(newTotalList, configurationUUID_String);
        updateBatchTotals(oldTotalList, configurationUUID_String);
    }

    /**
     * Division list of Totals to new Total list and old Total List.
     * @param totalList
     * @param oldTotalList
     * @param newTotalList
     */
    private void splitTotalList(List<TotalDTO> totalList, List<TotalDTO> oldTotalList, List<TotalDTO> newTotalList) {
        for (TotalDTO total: totalList) {
            if (total.isOldTotal()) {
                oldTotalList.add(total);
            } else {
                newTotalList.add(total);
            }
        }
    }
    /**
     * Update Connection determines attachment Dimension for Total.
     * @param totalList
     */
    private void updateConnectionsTotalsWithDimensions(List<ConnectionUUID> connections, String configurationUUID_String) {
        String queryDeletingDimensions = "delete from XML_FORM_TMPL_CONF_DIM_TOTAL where TMPL_CONF_UUID = ?";
        Object[] configurationUUID_ForUpdate = new Object[1];
        int[] types = new int[1];
        configurationUUID_ForUpdate[0] = configurationUUID_String;
        types[0] = Types.VARCHAR;
        getJDBCTempate().update(queryDeletingDimensions, configurationUUID_ForUpdate, types);
        insertBatchDimensionsForTotals(connections, configurationUUID_String);
    }


    /**
     * Delete all Dimensions not occur at dimensionListWithUUID.
     * @param dimensionListWithUUID
     * @param configurationUUID
     */
    private void deleteBatchDimensions(List<DimensionForFlatFormTemplateDTO> dimensionListWithUUID, String configurationUUID_String) {
        Object[] dimensionUUIDs = new Object[dimensionListWithUUID.size() + 1];
        dimensionUUIDs[0] = configurationUUID_String;
        if (dimensionListWithUUID.size() > 0) {
            String queryDeletingDimensions = "delete from XML_FORM_TMPL_CONF_DIM where TMPL_CONF_UUID = ? and TMPL_CONF_DIM_UUID not in (?";
            UUID dimensionTemporaryUUID = dimensionListWithUUID.get(0).getDimensionUUID();
            if (dimensionTemporaryUUID != null) {
                dimensionUUIDs[1] = dimensionTemporaryUUID.toString();
            } else {
                dimensionUUIDs[1] = "non";
            }
            int i = 2;
            for (DimensionForFlatFormTemplateDTO dimension: dimensionListWithUUID.subList(1, dimensionListWithUUID.size())) {
                queryDeletingDimensions += ", ?";
                dimensionTemporaryUUID = dimension.getDimensionUUID();
                if (dimensionTemporaryUUID != null) {
                    dimensionUUIDs[i] = dimensionTemporaryUUID.toString();
                } else {
                    dimensionUUIDs[i] = "non";
                }
                i++;
            }
            queryDeletingDimensions += ")";
            getJDBCTempate().update(queryDeletingDimensions, dimensionUUIDs);
        } else {
            String queryDeletingDimensions = "delete from XML_FORM_TMPL_CONF_DIM where TMPL_CONF_UUID = ?";
            getJDBCTempate().update(queryDeletingDimensions, configurationUUID_String);
        }
    }

    /**
     * Delete all Totals not occur at totalListWithUUID.
     * @param totalListWithUUID
     * @param configurationUUID
     */
    private void deleteBatchTotals(List<TotalDTO> totalListWithUUID, String configurationUUID_String) {
        Object[] totalUUIDs = new Object[totalListWithUUID.size() + 1];
        totalUUIDs[0] = configurationUUID_String.toString();
        if (totalListWithUUID.size() > 0) {
            String queryDeletingTotals = "delete from XML_FORM_TMPL_CONF_TOTAL where TMPL_CONF_UUID = ? and TMPL_CONF_TOTAL_UUID NOT IN (?";
            UUID totalTemporaryUUID = totalListWithUUID.get(0).getTotalUUID();
            if (totalTemporaryUUID != null) {
                totalUUIDs[1] = totalTemporaryUUID.toString();
            } else {
                totalUUIDs[1] = "non";
            }
            int i = 2;
            for (TotalDTO total: totalListWithUUID.subList(1, totalListWithUUID.size())) {
                queryDeletingTotals += ", ?";
                totalTemporaryUUID = total.getTotalUUID();
                if (totalTemporaryUUID != null) {
                    totalUUIDs[i] = totalTemporaryUUID.toString();
                } else {
                    totalUUIDs[i] = "non";
                }
                i++;
            }
            queryDeletingTotals += ")";
            getJDBCTempate().update(queryDeletingTotals, totalUUIDs);
        } else {
            String queryDeletingTotals = "delete from XML_FORM_TMPL_CONF_TOTAL  where TMPL_CONF_UUID = ?";
            getJDBCTempate().update(queryDeletingTotals, configurationUUID_String.toString());
        }
    }
    /**
     * Division list of Dimension to newDimension list and oldDiemsnion list.
     * @param dimensionList
     * @param oldDimensionList
     * @param newDimensionList
     */
    private void splitDimesnionList(List<DimensionForFlatFormTemplateDTO> dimensionList, List<DimensionForFlatFormTemplateDTO> oldDimensionList, List<DimensionForFlatFormTemplateDTO> newDimensionList) {
        for (DimensionForFlatFormTemplateDTO dimension: dimensionList) {
            if (dimension.isOldDimension()) {
                oldDimensionList.add(dimension);
            } else {
                newDimensionList.add(dimension);
            }
        }
    }
    public void insertBatchDimensions(final List<DimensionForFlatFormTemplateDTO> dimensions, final String configurationUUID_String) {
        if (dimensions.size() > 0) {
            String query = "insert into XML_FORM_TMPL_CONF_DIM (TMPL_CONF_UUID, TMPL_CONF_DIM_UUID, VIS_ID, SHEET_NAME, MODEL_VIS_ID, CHILD_INDEX, IS_HIDDEN, EXCLUDED_DIMENSIONS) values (?, ?, ?, ?, ?, ?, ?, ?)";

            getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    DimensionForFlatFormTemplateDTO dimension = dimensions.get(i);
                    ps.setString(1, configurationUUID_String);
                    ps.setString(2, dimension.getDimensionUUID().toString());
                    ps.setString(3, dimension.getDimensionVisId());
                    ps.setString(4, dimension.getSheetName());
                    ps.setString(5, dimension.getModelVisId());
                    ps.setInt(6, dimension.getIndex());
                    ps.setBoolean(7, dimension.isHidden());
                    if(dimension.getExcludedDimensions() == null) {
                        ps.setString(8, null);
                    } else {
                        ps.setString(8, dimension.getExcludedDimensions().toString());
                    }
                }

                @Override
                public int getBatchSize() {
                    return dimensions.size();
                }

            });
        }
    }

    public void updateBatchDimensions(final List<DimensionForFlatFormTemplateDTO> dimensions, final String configurationUUID_String) {
        if (dimensions.size() > 0) {
            String query = "update XML_FORM_TMPL_CONF_DIM set VIS_ID = ?, SHEET_NAME = ?, CHILD_INDEX = ?, IS_HIDDEN = ?, EXCLUDED_DIMENSIONS = ? where TMPL_CONF_DIM_UUID = ?";

            getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    DimensionForFlatFormTemplateDTO dimension = dimensions.get(i);
                    ps.setString(1, dimension.getDimensionVisId());
                    ps.setString(2, dimension.getSheetName());
                    ps.setInt(3, dimension.getIndex());
                    ps.setBoolean(4, dimension.isHidden());
                    if(dimension.getExcludedDimensions() != null) {
                        ps.setString(5, dimension.getExcludedDimensions().toString());
                    } else {
                        ps.setString(5, null);
                    }
                    ps.setString(6, dimension.getDimensionUUID().toString());
                }

                @Override
                public int getBatchSize() {
                    return dimensions.size();
                }

            });
        }
    }
    public void insertBatchDimensionsForTotals(final List<ConnectionUUID> connectionUUIDs, final String configurationUUID_String) {
        String query = "insert into XML_FORM_TMPL_CONF_DIM_TOTAL (TMPL_CONF_TOTAL_UUID, TMPL_CONF_DIM_UUID, TMPL_CONF_UUID) values (?, ?, ?)";

        getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ConnectionUUID connection = connectionUUIDs.get(i);
                ps.setString(1, connection.getLeft().toString());
                ps.setString(2, connection.getRight().toString());
                ps.setString(3, configurationUUID_String);
            }

            @Override
            public int getBatchSize() {
                return connectionUUIDs.size();
            }

        });
    }
    public void insertBatchTotals(final List<TotalDTO> totals, final String configurationUUID_String) {
        String query = "insert into XML_FORM_TMPL_CONF_TOTAL (TMPL_CONF_UUID, TMPL_CONF_TOTAL_UUID, SHEET_NAME, CHILD_INDEX, IS_HIDDEN, IS_GRAND) values (?, ?, ?, ?, ?, ?)";

        getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TotalDTO total = totals.get(i);
                ps.setString(1, configurationUUID_String);
                ps.setString(2, total.getTotalUUID().toString());
                ps.setString(3, total.getSheetName());
                ps.setInt(4, total.getIndex());
                ps.setBoolean(5, total.isHidden());
                ps.setBoolean(6, total.isGrandTotal());
            }

            @Override
            public int getBatchSize() {
                return totals.size();
            }

        });
    }

    public void updateBatchTotals(final List<TotalDTO> totals, final String configurationUUID_String) {
        String query = "update XML_FORM_TMPL_CONF_TOTAL set SHEET_NAME = ?, CHILD_INDEX = ?, IS_HIDDEN = ?, IS_GRAND = ? where TMPL_CONF_TOTAL_UUID = ?";

        getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TotalDTO total = totals.get(i);
                ps.setString(1, total.getSheetName());
                ps.setInt(2, total.getIndex());
                ps.setBoolean(3, total.isHidden());
                ps.setBoolean(4, total.isGrandTotal());
                ps.setString(5, total.getTotalUUID().toString());
            }

            @Override
            public int getBatchSize() {
                return totals.size();
            }

        });
    }
    public ResponseMessage updateConfigurationName(UUID configurationUUID, String configurationName, int versionNum) throws DaoException {
        try {
            ResponseMessage message = new ResponseMessage();
            String query = "update XML_FORM_TMPL_CONF set VIS_ID = ?, VERSION_NUM = ? where TMPL_CONF_UUID = ?";
            // validate versionNum
            Map<String, Object> currentDBRow = getJDBCTempate().queryForMap("select VERSION_NUM from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?", configurationUUID.toString());
            ValidationError validationError = MapperUtil.validateVersionNum(versionNum, currentDBRow);
            if (validationError.isError()) {
                return validationError;
            }
            PreparedStatementSetter pss = prepareStatementForConfigurationName(configurationUUID, configurationName, versionNum + 1);
            int result = getJDBCTempate().update(query, pss);
            if (result >= 1) {
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing update name query";
            throw new DaoException(errorMsg, e);
        }
    }
    private PreparedStatementSetter prepareStatementForConfigurationName(final UUID configurationUUID, final String configurationVisId, final int versionNum) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, configurationVisId);
                ps.setInt(2, versionNum);
                ps.setString(3, configurationUUID.toString());
            }
        };
        return pss;
    };
    public ResponseMessage deleteConfiguration(UUID configurationUUID) throws DaoException {
        try {
            String query = "delete from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?";
            ResponseMessage message = new ResponseMessage();
            int result = getJDBCTempate().update(query, configurationUUID.toString());
            if (result >= 1) {
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing delete query";
            throw new DaoException(errorMsg, e);
        }
    }
    public ResponseMessage generateForms(GenerateDTO generateDTO) throws ServiceException, CloneNotSupportedException, ClassNotFoundException, SQLException, DaoException {
        ResponseMessage ifDuplicate = checkIfDuplicate(generateDTO);
        if (ifDuplicate != null) {
            return ifDuplicate;
        }

        CompleteFlatFormIgredients completeFlatFormIgredients = generateCompleteFlatFormIgredients(generateDTO);

        // fill templates if hierarchy based
        if (completeFlatFormIgredients.getTemplateDetailsDTO().getType().equals(TemplateMapper.mapType(2))) {
            fillFlatFormTemplateHierarchyBased(generateDTO, completeFlatFormIgredients);
        } else if (completeFlatFormIgredients.getTemplateDetailsDTO().getType().equals(TemplateMapper.mapType(1))) {
            fillFlatFormTemplateStandard(completeFlatFormIgredients);
        }

        GenerateFlatFormsTaskRequest request = buildRequestTask(generateDTO, completeFlatFormIgredients);
        return IssueNewTask(request);
    }
    private void fillFlatFormTemplateHierarchyBased(GenerateDTO generateDTO, CompleteFlatFormIgredients completeFlatFormIgredients) throws ServiceException, CloneNotSupportedException {
        UUID configurationUUID = generateDTO.getConfigurationUUID();
        ConfigurationDetailsDTO configurationDetailsDTO = null;
        if (configurationUUID == null) {
            throw new ServiceException("Configuration should be chosen for " + TemplateMapper.mapType(2) + " template [" + completeFlatFormIgredients.getTemplateDetailsDTO().getVisId() + "].");
        }
        try {
			configurationDetailsDTO = fetchConfiguration(configurationUUID);
	        fillTemplateSheets(completeFlatFormIgredients.getCompleteWorkbook(), configurationDetailsDTO);
	        setWorksheetPropertiesForHierarchy(completeFlatFormIgredients.getCompleteWorkbook().getWorkbookDTO(), configurationDetailsDTO);

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * @param workbookDTO
     * @param workbook
     */
    private void fillFlatFormTemplateStandard(CompleteFlatFormIgredients completeFlatFormIgredients) {
        WorkbookDTO workbookDTO = completeFlatFormIgredients.getCompleteWorkbook().getWorkbookDTO();
        WorkbookDTO workbook = completeFlatFormIgredients.getTemplateDetailsDTO().getWorkbook();
        setWorksheetPropertiesForSimple(workbookDTO, workbook);
    }
    private ResponseMessage checkIfDuplicate(GenerateDTO generateDTO) throws ServiceException, CloneNotSupportedException {
		List<FlatFormExtendedCoreDTO> flatForms = fetchFlatForms();
        Boolean override = generateDTO.getOverride();
        List<String> duplicates = GenerateUtil.checkIfDuplicatesExists(generateDTO, flatForms);
        if (generateDTO.isLastRequest() == false) {
            if (duplicates.size() > 0) {
                String message = duplicates.toString();
                return new ResponseMessage(false, message);
            }
        } else if (duplicates.size() == generateDTO.getFinanceCubeModels().size() && override == false) {
            return new ResponseMessage(true);
        }
        return null;
    }
    private CompleteFlatFormIgredients generateCompleteFlatFormIgredients(GenerateDTO generateDTO) throws ServiceException, ClassNotFoundException, SQLException, DaoException {
        UUID templateUUID = generateDTO.getTemplateUUID();
        AllUsersELO allUsers = getAllUsers();
        TemplateDetailsDTO templateDetailsDTO = fetchTemplate(templateUUID,allUsers);
        String jsonForm = templateDetailsDTO.getJsonForm();
        byte[] excelFile = toXLSX(jsonForm);
        Workbook workbook = getWorkbook(excelFile);

        // workbook.setForceFormulaRecalculation(false);
        WorkbookDTO workbookDTO = manageXmlForm(workbook, true);
        CompleteWorkbook completeWorkbook = new CompleteWorkbook(workbook, workbookDTO);

        CompleteFlatFormIgredients completeFlatFormIgredients = new CompleteFlatFormIgredients(excelFile, completeWorkbook, templateDetailsDTO);
        return completeFlatFormIgredients;
    }

    private GenerateFlatFormsTaskRequest buildRequestTask(GenerateDTO generateDTO, CompleteFlatFormIgredients completeFlatFormIgredients) throws CloneNotSupportedException, ServiceException {
        Map<Integer, String> visIds = new HashMap<Integer, String>();
        Map<Integer, String> definitions = new HashMap<Integer, String>();
        List<FinanceCubeModelCoreDTO> financeCubeModels = generateDTO.getFinanceCubeModels();
        for (FinanceCubeModelCoreDTO financeCubeModel: financeCubeModels) {
            int financeCubeId = financeCubeModel.getFinanceCubeId();

            // VisIds
            String visId = GenerateUtil.getCompanyFromModelVisId(financeCubeModel.getModel().getModelVisId()) + " - " + generateDTO.getName();
            visIds.put(financeCubeId, visId);

            // Definitions
            WorkbookDTO newWorkbookDTO = (WorkbookDTO) completeFlatFormIgredients.getCompleteWorkbook().getWorkbookDTO().clone();
            setWorksheetPropertiesForFinanceCube(newWorkbookDTO, financeCubeModel.getModel().getModelVisId());
            String definition = WorkbookMapper.mapWorkbookDTOToXmlString(newWorkbookDTO);
            definitions.put(financeCubeId, definition);
        }

        List<UserRefImpl> userIds = setUserIdsToRequest(completeFlatFormIgredients.getTemplateDetailsDTO());

        byte[] excelFile = getExcelFile(completeFlatFormIgredients.getCompleteWorkbook().getWorkbook());

        String description = generateDTO.getDescription();
        GenerateFlatFormsTaskRequest request = new GenerateFlatFormsTaskRequest();
        request.setDescription(description);
        request.setOverride(generateDTO.getOverride());
        request.setExcelFile(excelFile);
        request.setVisIds(visIds);
        request.setUserIds(userIds);
        request.setDefinitions(definitions);
        return request;
    }
    private ResponseMessage IssueNewTask(GenerateFlatFormsTaskRequest request) throws ServiceException {
        try {
//            int issueNewTask = TaskMessageFactory.issueNewTask(new InitialContext(), true, request, userId);//arnold
        	int issueNewTask = 1;
            return new ResponseMessage(true, "Forms have been generated successfully with taskID = " + issueNewTask);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void fillTemplateSheets(CompleteWorkbook completeWorkbook, ConfigurationDetailsDTO configurationDetailsDTO) throws CloneNotSupportedException {
        List<WorksheetDTO> worksheetList = completeWorkbook.getWorkbookDTO().getWorksheets();
        int sum = configurationDetailsDTO.getDimensions().size() + configurationDetailsDTO.getTotals().size();
        int sheetTemplateNum = worksheetList.size() - 1;
        WorksheetDTO lastWorksheet = worksheetList.get(sheetTemplateNum);
        Workbook workbook = completeWorkbook.getWorkbook();
        int sheetTemplateIndex = workbook.getSheetIndex(lastWorksheet.getName());

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        for (int i = 0; i < sum - 1; i++) {
            workbook.cloneSheet(sheetTemplateIndex);
            worksheetList.add((WorksheetDTO) lastWorksheet.clone());
        }

        fillTemplateCellsWithDimensions(configurationDetailsDTO, completeWorkbook, sheetTemplateNum, style);
        fillTemplateCellsWithTotals(configurationDetailsDTO, completeWorkbook, sheetTemplateNum, style);
    }
    private void setWorksheetPropertiesForHierarchy(WorkbookDTO workbookDTO, ConfigurationDetailsDTO configurationDetailsDTO) {
//      List<FinanceCubeModelCoreDTO> financeCubeModelCoreListDTO = financeCubeCoreService.browseFinanceCubes();

      List<DimensionForFlatFormTemplateDTO> dimensions = configurationDetailsDTO.getDimensions();
      List<WorksheetDTO> worksheets = workbookDTO.getWorksheets();

      for (DimensionForFlatFormTemplateDTO dimension: dimensions) {
          String modelVisId = dimension.getModelVisId();
//          FinanceCubeModelCoreDTO financeCube = FinanceCubeModelCoreUtil.findFinanceCubeByModelVisId(modelVisId, financeCubeModelCoreListDTO);
//          List<DimensionWithHierarchiesCoreDTO> dimensionList = formService.fetchModelDimensionsWithHierarchies(financeCube.getModel().getModelId());
//          Map<String, String> properties = WorkbookMapper.manageWorksheetProperties(financeCube, dimensionList);
          Map<String, String> properties = getPropertiesForModelVisId(modelVisId);

          for (WorksheetDTO worksheet: worksheets) {
              if (worksheet.getName().equals(dimension.getSheetName())) {
                  worksheet.setProperties(properties);
                  break;
              }
          }
      }
  }
    private void setWorksheetPropertiesForSimple(WorkbookDTO workbookDTO, WorkbookDTO workbookWithProperties) {

        // workbook
        Map<String, String> propertiesWorkbook = workbookWithProperties.getProperties();
        workbookDTO.setProperties(propertiesWorkbook);

        // worksheets
        List<WorksheetDTO> worksheets = workbookDTO.getWorksheets();
        List<WorksheetDTO> worksheetsWithProperties = workbookWithProperties.getWorksheets();

        for (WorksheetDTO worksheetDTO: worksheets) {
            String worksheetName = worksheetDTO.getName();
            for (WorksheetDTO worksheetPropertiesDTO: worksheetsWithProperties) {
                String worksheetPropName = worksheetPropertiesDTO.getName();
                if (worksheetPropName.equals(worksheetName)) {
                    Map<String, String> properties = worksheetPropertiesDTO.getProperties();
                    worksheetDTO.setProperties(properties);
                }
            }
        }
    }

    private void setWorksheetPropertiesForFinanceCube(WorkbookDTO workbookDTO, String modelVisId) throws ServiceException {
//      int modelId = financeCubeModerCoreDTO.getModel().getModelId();
//      List<DimensionWithHierarchiesCoreDTO> dimensionList = formService.fetchModelDimensionsWithHierarchies(modelId);
//      Map<String, String> properties = WorkbookMapper.manageWorksheetProperties(financeCubeModerCoreDTO, dimensionList);
      Map<String, String> properties = getPropertiesForModelVisId(modelVisId);

      List<WorksheetDTO> worksheets = workbookDTO.getWorksheets();
      for (WorksheetDTO worksheetDTO: worksheets) {
          if (worksheetDTO.getProperties() == null || worksheetDTO.getProperties().size() == 0) {
              String worksheetName = worksheetDTO.getName();
              if (worksheetName.equalsIgnoreCase("menu")) {
//                  List<FinanceCubeModelCoreDTO> financeCubeModelCoreListDTO = financeCubeCoreService.browseFinanceCubes();
//                  FinanceCubeModelCoreDTO financeCubeModerCoreDTOMenu = FinanceCubeModelCoreUtil.findFinanceCubeByModelVisId("1/1", financeCubeModelCoreListDTO);
//                  List<DimensionWithHierarchiesCoreDTO> dimensionListMenu = formService.fetchModelDimensionsWithHierarchies(financeCubeModerCoreDTOMenu.getModel().getModelId());
//                  Map<String, String> propertiesMenu = WorkbookMapper.manageWorksheetProperties(financeCubeModerCoreDTOMenu, dimensionListMenu);
                  Map<String, String> propertiesMenu = getPropertiesForModelVisId("1/1");
                  worksheetDTO.setProperties(propertiesMenu);
              } else {
                  worksheetDTO.setProperties(properties);
              }
          }

      }
  }
    private Workbook getWorkbook(byte[] file) throws ServiceException {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(file);
            Workbook workbook = WorkbookFactory.create(bis);
            bis.close();
            return workbook;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    private byte[] toXLSX(String json) throws ServiceException {
        try {
//			InputStream is = cpContextHolder.getCPConnection().getExcelIOProcess().convertJsonToXlsx(json, "");
        	InputStream is = convertJsonToExcel(URI.create("http://localhost:9170"), 
		              json, "", "xlsx", new Boolean[] { Boolean.valueOf(false) });
            byte[] excelFile = IOUtils.toByteArray(is);
            is.close();
            return excelFile;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

	private void fillTemplateCellsWithDimensions(ConfigurationDetailsDTO configurationDetailsDTO, CompleteWorkbook completeWorkbook, int sheetTemplateNum, CellStyle style) {
        List<DimensionForFlatFormTemplateDTO> dimensionList = configurationDetailsDTO.getDimensions();
        for (DimensionForFlatFormTemplateDTO dimension: dimensionList) {
            fillTemplateCellsWithDimension(dimension, completeWorkbook, sheetTemplateNum, style, dimensionList);
        }
    }
    private void fillTemplateCellsWithDimension(DimensionForFlatFormTemplateDTO dimension, CompleteWorkbook completeWorkbook, int sheetTemplateNum, CellStyle style, List<DimensionForFlatFormTemplateDTO> dimensionList) {
        int index = dimension.getIndex() - 1;
        // set excel worksheet name and hidden property
        completeWorkbook.getWorkbook().setSheetName(sheetTemplateNum + index, dimension.getSheetName());
        completeWorkbook.getWorkbook().setSheetHidden(sheetTemplateNum + index, dimension.isHidden());

        Sheet sheet = completeWorkbook.getWorkbook().getSheet(dimension.getSheetName());
        // Find cell A1, set its value as sheet title
        CellReference cellReference = new CellReference("A1");
        Row rowA = sheet.getRow(cellReference.getRow());
        if (rowA != null) {
            Cell cellA1 = rowA.getCell(cellReference.getCol());
            if (cellA1 != null) {
                cellA1.setCellValue(sheet.getSheetName());
                // Change font colour of A1 to white
                cellA1.setCellStyle(style);
            }
        }
        // set definition worksheet name
        WorksheetDTO worksheetDTO = completeWorkbook.getWorkbookDTO().getWorksheets().get(sheetTemplateNum + index);
        worksheetDTO.setName(dimension.getSheetName());

        String dim0 = dimension.getDimensionVisId();
        List<? extends CellDTO> cellsList = worksheetDTO.getCells();
        List<CellDTO> cellsListToDelete = new ArrayList<CellDTO>();

        for (CellDTO cellDTO: cellsList) {
            if (cellDTO instanceof TemplateCellDTO) {
                TemplateCellDTO xCellFormCellDTO = (TemplateCellDTO) cellDTO;
                if (xCellFormCellDTO.isTotal()) {
                    Cell cell = sheet.getRow(xCellFormCellDTO.getRow()).getCell(xCellFormCellDTO.getColumn());
                    setDefaultColor(cell);
                }
            }

            String inputMapping = cellDTO.getInputMapping();
            if (inputMapping != null && !inputMapping.isEmpty() && (inputMapping.contains("dim0=,") || inputMapping.contains("costc=,"))) {
                inputMapping = inputMapping.replaceAll("dim0=,", "dim0=" + dim0 + ","); // getCell IM
                inputMapping = inputMapping.replaceAll("costc=,", "costc=" + dim0 + ","); // parameterLookup IM
                if (dimension.getExcludedDimensions() == null || dimension.getExcludedDimensions().isEmpty()) {
                    cellDTO.setInputMapping(inputMapping);
                } else if (!inputMapping.startsWith("cedar.cp.getCell")) {
                    cellDTO.setInputMapping(inputMapping);
                } else {
                    // String excludedDimensionsFormula = generateExcludedDimensionFormula(inputMapping, dimension.getExcludedDimensions(), dim0, dimensionList, cellDTO);
                    ArrayList<String> excludedDimensionFormulaList;
                    ArrayList<String> excludeDimensions = (ArrayList<String>) dimension.getExcludedDimensions().clone();
                    excludedDimensionFormulaList = generateFormulaExcludedDimensionFromCells(excludeDimensions, dimensionList, cellDTO);
                    excludedDimensionFormulaList.addAll(generateFormulaExcludedDimensionToCalculate(cellDTO.getInputMapping(), excludeDimensions));
                    String excludedDimensionsFormula = generateExcludedDimensionFormula(inputMapping, excludedDimensionFormulaList);
                    cellDTO.setText(excludedDimensionsFormula);
                    cellDTO.setInputMapping(null);
                }
            }
            String outputMapping = cellDTO.getOutputMapping();
            if (outputMapping != null && !outputMapping.isEmpty() && (outputMapping.contains("dim0=,") || outputMapping.contains("costc=,"))) {
                outputMapping = outputMapping.replaceAll("dim0=,", "dim0=" + dim0 + ",");
                outputMapping = outputMapping.replaceAll("costc=,", "costc=" + dim0 + ",");
                cellDTO.setOutputMapping(outputMapping);
            }
            String text = cellDTO.getText();
            if (text != null && !text.isEmpty() && (text.contains("dim0=,") || text.contains("costc=,"))) {
                text = text.replaceAll("dim0=,", "dim0=" + dim0 + ",");
                text = text.replaceAll("costc=,", "costc=" + dim0 + ",");
                cellDTO.setText(text);
            }
            if (inputMapping == null && outputMapping == null && text == null) {
                cellsListToDelete.add(cellDTO);
            }
        }

        cellsList.removeAll(cellsListToDelete);
    }
    private ArrayList<String> generateFormulaExcludedDimensionFromCells(ArrayList<String> excludedDimensions, List<DimensionForFlatFormTemplateDTO> dimensionList, CellDTO cellDTO) {
        ArrayList<String> results = new ArrayList<String>();
        String excludedDimensionFormula;
        for (Iterator<String> it = excludedDimensions.iterator(); it.hasNext();) {
            String excludedDimension = it.next();
            for (DimensionForFlatFormTemplateDTO dimension: dimensionList) {
                if (dimension.getDimensionVisId().equals(excludedDimension) && (dimension.getExcludedDimensions() == null || dimension.getExcludedDimensions().isEmpty())) {
                    excludedDimensionFormula = "'" + dimension.getSheetName() + "'!" + CellReference.convertNumToColString(cellDTO.getColumn()) + String.valueOf(cellDTO.getRow() + 1);
                    results.add(excludedDimensionFormula);
                    it.remove();
                }
            }
        }
        return results;
    }

    private ArrayList<String> generateFormulaExcludedDimensionToCalculate(String inputMapping, ArrayList<String> excludedDimensions) {
        ArrayList<String> results = new ArrayList<String>();
        String excludedDimensionFormula;
        for (String excludedDimension: excludedDimensions) {
            excludedDimensionFormula = inputMapping.replaceAll("dim0=,", "dim0=" + excludedDimension + ",");
            excludedDimensionFormula = excludedDimensionFormula.replaceAll("costc=,", "costc=" + excludedDimension + ",");
            results.add(excludedDimensionFormula);
        }
        return results;
    }

    private String generateExcludedDimensionFormula(String inputMapping, ArrayList<String> excludedDimensions) {
        String resultText = "=" + inputMapping;
        for (String excludedDimension: excludedDimensions) {
            resultText = resultText + " - " + excludedDimension;
        }
        return resultText;
    }
    private List<UserRefImpl> setUserIdsToRequest(TemplateDetailsDTO templateDetailsDTO) {
        List<UserRefImpl> list = UserCoreMapper.mapUserCoreDTO(templateDetailsDTO.getUsers());

        List<UserCoreDTO> users = templateDetailsDTO.getUsers();
        List<Integer> userIds = new ArrayList<Integer>();
        for (UserCoreDTO user: users) {
            int userId = user.getUserId();
            userIds.add(userId);
        }
        return list;
    }
    private void fillTemplateCellsWithTotals(ConfigurationDetailsDTO configurationDetailsDTO, CompleteWorkbook completeWorkbook, int sheetTemplateNum, CellStyle style) {
        List<TotalDTO> totalList = configurationDetailsDTO.getTotals();
        for (TotalDTO item: totalList) {
            int index = item.getIndex() - 1;
            // set excel worksheet name and hidden property
            completeWorkbook.getWorkbook().setSheetName(sheetTemplateNum + index, item.getSheetName());
            completeWorkbook.getWorkbook().setSheetHidden(sheetTemplateNum + index, item.isHidden());

            Sheet sheet = completeWorkbook.getWorkbook().getSheet(item.getSheetName());
            // Find cell A1, set its value as sheet title
            CellReference cellReference = new CellReference("A1");
            Row rowA = sheet.getRow(cellReference.getRow());
            if (rowA != null) {
                Cell cellA1 = rowA.getCell(cellReference.getCol());
                if (cellA1 != null) {
                    cellA1.setCellValue(sheet.getSheetName());
                    // Change font colour of A1 to white
                    cellA1.setCellStyle(style);
                }
            }
            // set definition worksheet name
            WorksheetDTO work = completeWorkbook.getWorkbookDTO().getWorksheets().get(sheetTemplateNum + index);
            work.setName(item.getSheetName());

            List<? extends CellDTO> cellsList = work.getCells();
            for (CellDTO cellDTO: cellsList) {

                String inputMapping = cellDTO.getInputMapping();
                if (inputMapping != null && !inputMapping.isEmpty() && (inputMapping.contains("dim0=,") || inputMapping.contains("costc=,"))) {
                    cellDTO.setInputMapping(null);
                }
                String outputMapping = cellDTO.getOutputMapping();
                if (outputMapping != null && !outputMapping.isEmpty() && (outputMapping.contains("dim0=,") || outputMapping.contains("costc=,"))) {
                    cellDTO.setOutputMapping(null);
                }
                String text = cellDTO.getText();
                if (text != null && !text.isEmpty() && (text.contains("dim0=,") || text.contains("costc=,"))) {
                    cellDTO.setText(null);
                }

                if (cellDTO instanceof TemplateCellDTO) {

                    TemplateCellDTO xCellFormCellDTO = (TemplateCellDTO) cellDTO;
                    if (xCellFormCellDTO.isTotal()) {
                        xCellFormCellDTO.setInputMapping(null);
                        xCellFormCellDTO.setOutputMapping(null);

                        String column = CellReference.convertNumToColString(xCellFormCellDTO.getColumn());
                        int row = xCellFormCellDTO.getRow() + 1;
                        String cellAddress = column + row;

                        text = "=";
                        if (item.isGrandTotal()) { // sum all totals
                            for (TotalDTO total: totalList) {
                                if (!total.isGrandTotal()) {
                                    text = text + " + &apos;" + total.getSheetName() + "&apos;!" + cellAddress;
                                }
                            }
                        } else { // sum specified dims
                            List<DimensionForFlatFormTemplateDTO> dimensionList = item.getDimensionList();
                            for (DimensionForFlatFormTemplateDTO dim: dimensionList) {
                                text = text + " + &apos;" + dim.getSheetName() + "&apos;!" + cellAddress;
                            }
                        }

                        Cell cell = sheet.getRow(xCellFormCellDTO.getRow()).getCell(xCellFormCellDTO.getColumn());
                        setDefaultColor(cell);
                        xCellFormCellDTO.setText(text);
                        cellDTO = xCellFormCellDTO;
                    }

                }

            }
        }
    }
    private byte[] getExcelFile(Workbook workbook) throws ServiceException {
        try {
            return workbookToByteArray(workbook);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    private byte[] workbookToByteArray(Workbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }
    private void setDefaultColor(Cell cell) {
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null && cellStyle instanceof XSSFCellStyle) {
            XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
            // TODO: Check after pattern connection
            // xssfCellStyle.setFillPattern(CellStyle.NO_FILL);
            xssfCellStyle.setFillForegroundColor((short) 0);
        }
    }
    public List<FlatFormExtendedCoreDTO> fetchFlatForms() throws ServiceException {
        try {
            // Retrieve all forms current user has rights to.
        	AllXcellXmlFormsELO allFormsForUser = getAllXcellXmlFormsForLoggedUser(userId);
            // Return data converted to DTO, then JSON
            return FlatFormsCoreMapper.mapAllXcellXmlFormsELO(allFormsForUser);
        } catch (Exception e) {
            throw new ServiceException("Error during fetching flat forms!", e);
        }
    }
    public AllXcellXmlFormsELO getAllXcellXmlFormsForLoggedUser(int userId) throws EJBException {
        AllXcellXmlFormsELO elo = null;

        try {
            XmlFormAccessor e = new XmlFormAccessor(new InitialContext());
            elo = e.getAllXcellXmlFormsForLoggedUser(userId);
            return elo;
        } catch (Exception var3) {
            throw new EJBException(var3.getMessage(), var3);
        }
    }
    
    public WorkbookDTO manageXmlForm(Workbook workbook, boolean isTemplateProcess) {
        WorkbookDTO workbookDTO = new WorkbookDTO();
        List<WorksheetDTO> worksheets = manageWorkbookDTO(workbook, isTemplateProcess);
        workbookDTO.setWorksheets(worksheets);
        return workbookDTO;
    }
    private List<WorksheetDTO> manageWorkbookDTO(Workbook workbook, boolean isTemplateProcess) {
        List<WorksheetDTO> worksheetsDTO = new ArrayList<WorksheetDTO>();

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            WorksheetDTO worksheetDTO = new WorksheetDTO();
            Sheet sheet = workbook.getSheetAt(i);

            worksheetDTO.setName(sheet.getSheetName());
            RichTextString emptyTextString = null;
            if (sheet.getRow(0) != null && sheet.getRow(0).getCell(0) != null && !isTemplateProcess) {
                Cell cell = sheet.getRow(0).getCell(0);
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    String modelVisId = cell.getStringCellValue();
                    if (modelVisId != null && !modelVisId.trim().isEmpty()) {
                        cell.setCellValue(emptyTextString); // clear cell
                        Map<String, String> properties = getPropertiesForModelVisId(modelVisId);
                        worksheetDTO.setProperties(properties);
                    }
                }
            } else if (isTemplateProcess && !workbook.isSheetHidden(i) && !workbook.isSheetVeryHidden(i)) {
                // Find cell A1, set its value as sheet title
                CellReference cellReference = new CellReference("A1");
                Row row = sheet.getRow(cellReference.getRow());
                if (row != null) {
                    Cell cell = row.getCell(cellReference.getCol());
                    if (cell != null) {
                        cell.setCellValue(sheet.getSheetName());
                        // Change font colour of A1 to white
                        cell.setCellStyle(style);
                    }
                }
            }
            worksheetsDTO.add(worksheetDTO);
        }

        WorkbookMapper.manageWorksheetsCells(workbook, worksheetsDTO, isTemplateProcess);
        return worksheetsDTO;
    }
    
    public Map<String, String> getPropertiesForModelVisId(String modelVisId) {
        //@formatter:off
    	String sql = ""
    	        + "SELECT 0 \"DIM_ID\", DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.BUDGET_HIERARCHY_ID = HIER.HIERARCHY_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.VIS_ID = ? "
    			+ "UNION ALL "
    			+ "SELECT 1 \"DIM_ID\", DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.ACCOUNT_ID = DIM.DIMENSION_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.VIS_ID = ? "
    			+ "UNION ALL "
    			+ "SELECT 2 \"DIM_ID\", DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.CALENDAR_ID = DIM.DIMENSION_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.VIS_ID = ?";
    	//@formatter:on
        List<Map<String, Object>> queryForList = getJDBCTempate().queryForList(sql, modelVisId, modelVisId, modelVisId);
        Map<String, String> map = FlatFormExtractorMapper.map(queryForList);
        return map;
    }
    public String convertXlsToJson(URI address, byte[] bytes, String password, Boolean... flags) throws EJBException {
        try {
            String url = address.toString() + "/toJSON";
            
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            ContentBody cb = new ByteArrayBody(bytes, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "form.xls");
            entity.addPart("file", cb);

            HttpEntity responseEntity = HttpUtils.doPost(url, entity);
            String json = EntityUtils.toString(responseEntity, "UTF-8");
            responseEntity.consumeContent();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("Error converting xls to json!", e);
        }
    }
	public InputStream convertJsonToExcel(URI serviceURI, String json, String password, String exportFileType,
			Boolean... flags) throws EJBException, ValidationException {
		try {
			String url = serviceURI.toString() + "/toExcel/" + exportFileType;
			byte[] compressJson = compress(json);

			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			ContentBody cb = new ByteArrayBody(compressJson, "application/zip", "json.zip");
			entity.addPart("file", cb);

			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(entity);

			HttpEntity responseEntity = HttpUtils.doPost(url, entity);

			return responseEntity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException("Error converting json to xls!", e);
		}
	}
	private byte[] compress(String text) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			OutputStream out = new DeflaterOutputStream(baos);
			out.write(text.getBytes("UTF-8"));
			out.close();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		return baos.toByteArray();
	}
	
    public TemplateDetailsDTO insertTemplate(final TemplateDetailsDTO template) throws DaoException {
        try {
            UUID parentUUID = template.getParentUUID();
            ResponseMessage message = new ResponseMessage();
            // String typeDirectory = TemplateMapper.mapType(0);
            // TODO:zamienic na zapytanie
            // if (!fetchTemplate(parentUUID).getType().equals(typeDirectory)) {
            // throw new DaoException("Can't insert child for Template (not Directory)");
            // }

            String queryDecrementIndexes = "UPDATE XML_FORM_TMPL SET CHILD_INDEX = CHILD_INDEX + 1 WHERE PARENT_UUID = ? AND CHILD_INDEX >= ?";
            PreparedStatementSetter pssDecrementIndexes = prepareUpdateIndexesStatement(parentUUID, template.getIndex());
            getJDBCTempate().update(queryDecrementIndexes, pssDecrementIndexes);

            String query = "INSERT INTO xml_form_tmpl (VIS_ID, DESCRIPTION, VERSION_NUM, JSON_FORM, PARENT_UUID, TYPE, DEFINITION, CHILD_INDEX, TMPL_UUID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatementSetter pss = prepareStatement(template, null, 0);
            int result = getJDBCTempate().update(query, pss);
            if (result >= 1) {
                Integer type = TemplateMapper.mapType(template.getType());
                String selectQuery = "select TMPL_UUID from xml_form_tmpl WHERE VIS_ID = ? AND PARENT_UUID = ? AND TYPE = ? AND ROWNUM = 1 ORDER BY TMPL_UUID DESC";
                Map<String, Object> row = getJDBCTempate().queryForMap(selectQuery, template.getVisId(), parentUUID.toString(), type);
                UUID tmplUUID = UUID.fromString((String) row.get("TMPL_UUID"));
                message.setSuccess(true);
                template.setTemplateUUID(tmplUUID);
            } else {
                message.setError(true);
            }
            return template;// message;
        } catch (Exception e) {
            String errorMsg = "Error while executing insert query";
            throw new DaoException(errorMsg, e);
        }
    }
    private PreparedStatementSetter prepareUpdateIndexesStatement(final UUID parentUUID, final int index) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, parentUUID.toString());
                ps.setInt(2, index);
            }
        };
        return pss;
    }
    private PreparedStatementSetter prepareStatement(final TemplateDetailsDTO template, final UUID templateUUID, final int versionNum) {
        final Integer type = TemplateMapper.mapType(template.getType());
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, template.getVisId());
                ps.setString(2, template.getDescription());
                ps.setInt(3, versionNum);
                ps.setString(4, template.getJsonForm());
                if (template.getParentUUID() != null) {
                    ps.setString(5, template.getParentUUID().toString());
                } else {
                    ps.setNull(5, 0);
                }
                ps.setInt(6, type);
                if (type != 0) {
                    ps.setString(7, WorkbookMapper.mapWorkbookDTOToXmlString(template.getWorkbook()));
                } else {
                    ps.setNull(7, 0);
                }
                ps.setInt(8, template.getIndex());
                if (templateUUID == null) {
                    ps.setString(9, UUID.randomUUID().toString());
                } else {
                    ps.setString(9, templateUUID.toString());
                }

            }
        };
        return pss;
    };
    public ConfigurationTreeDTO insertConfiguration(ConfigurationTreeDTO configuration) throws DaoException {
        try {
            if (!isDirectory(configuration.getParentUUID())) {
                throw new DaoException("Can't insert child for Configuration (not Directory)");
            }

            UUID parentUUID = configuration.getParentUUID();
            String queryDecrementIndexes = "UPDATE XML_FORM_TMPL_CONF SET CHILD_INDEX = CHILD_INDEX + 1 WHERE PARENT_UUID = ? AND CHILD_INDEX >= ?";
            PreparedStatementSetter pssDecrementIndexes = prepareUpdateIndexesStatement(parentUUID, configuration.getIndex());
            getJDBCTempate().update(queryDecrementIndexes, pssDecrementIndexes);

            UUID configurationUUID = UUID.randomUUID();
            String query = "insert into XML_FORM_TMPL_CONF (VIS_ID, VERSION_NUM, PARENT_UUID, IS_DIRECTORY, TMPL_CONF_UUID, CHILD_INDEX) values (?, ?, ?, ?, ?, ?)";
            PreparedStatementSetter pss = prepareStatementForConfiguration(configuration, configurationUUID, 0, true);
            
            int result = getJDBCTempate().update(query, pss);
            if (result >= 1) {
                configuration.setConfigurationUUID(configurationUUID);
            } else {
                throw new DaoException("Error while executing insert query");
            }
            return configuration;
        } catch (Exception e) {
            String errorMsg = "Error while executing insert query";
            throw new DaoException(errorMsg, e);
        }
    }
    private Boolean isDirectory(UUID configurationUUID) {
        String testQuery = "select IS_DIRECTORY from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?";
        Map<String, Object> row = getJDBCTempate().queryForMap(testQuery, configurationUUID.toString());
        boolean isDirectory = ((String) row.get("IS_DIRECTORY")).equalsIgnoreCase("Y");
        return isDirectory;
    }
    public List<FinanceCubeModelDTO> browseFilterModelsFinanceCubeForLoggedUser(int userId) throws ServiceException, ClassNotFoundException, SQLException {
    	Connection conn = ConnectionUtils.getConnection();
        AllFinanceCubesELO allFinanceCubesELO = getAllFinanceCubesForLoggedUser(conn,userId);
        List<FinanceCubeModelDTO> fcModelsDTO = ModelsMapper.mapAllFinanseCubesELOWithModel(allFinanceCubesELO);
        return fcModelsDTO;

    }
    
    public AllBudgetCyclesELO getAllBudgetCyclesForUser(int userId) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllBudgetCyclesELO results = new AllBudgetCyclesELO();
        try {
        	Connection conn = ConnectionUtils.getConnection();
            stmt = conn.prepareStatement(SQL_ALL_BUDGET_CYCLES_FOR_USER);
            int col = 1;
            stmt.setInt(col++, userId);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;
                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                String textModel = resultSet.getString(col++);
                BudgetCyclePK pkBudgetCycle = new BudgetCyclePK(resultSet.getInt(col++));
                String textBudgetCycle = resultSet.getString(col++);
                BudgetCycleCK ckBudgetCycle = new BudgetCycleCK(pkModel, pkBudgetCycle);
                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                BudgetCycleRefImpl erBudgetCycle = new BudgetCycleRefImpl(ckBudgetCycle, textBudgetCycle);
                String col1 = resultSet.getString(col++);
                int col2 = resultSet.getInt(col++);
                int periodFrom = resultSet.getInt(col++);
                int periodTo = resultSet.getInt(col++);
                String periodFromVisId = resultSet.getString(col++);
                String periodToVisId = resultSet.getString(col++);
                String category = resultSet.getString(col);
                results.add(erBudgetCycle, erModel, col1, col2, periodFrom, periodTo, periodFromVisId, periodToVisId, category);
            }

        } catch (SQLException sqle) {
        	sqle.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return results;
    }
    public List<com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO> browseBudgetCycles(int userId) {
    	
        AllBudgetCyclesELO budgetCycles = getAllBudgetCyclesForUser(userId);
        List<com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO> budgetCyclesDTO = BudgetCyclesMapper.mapAllBudgetCyclesELOToBudgetCycleDTO(budgetCycles);
        return budgetCyclesDTO;
    }
    
    public List<FlatFormExtendedCoreDTO> browseXmlFormsForModel(int modelId) throws ClassNotFoundException {
        AllXmlFormsELO xmlFormsELO = getAllXmlFormsForModel(modelId);
        return FormMapper.mapAllXmlFormsELOToFormDTO(xmlFormsELO);
    }

    public AllXmlFormsELO getAllXmlFormsForModel(int modelId) throws ClassNotFoundException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllXmlFormsELO results = new AllXmlFormsELO();
        try {
        	Connection conn = ConnectionUtils.getConnection();
            stmt = conn.prepareStatement(SQL_ALL_XML_FORMS_FOR_MODEL);
            int col = 1;
            stmt.setInt(col++, modelId);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                String textXmlForm = resultSet.getString(col++);
                XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                String textFinanceCube = resultSet.getString(col++);
                FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                int col1 = resultSet.getInt(col++);
                String col2 = resultSet.getString(col++);
                int col3 = resultSet.getInt(col++);
                Timestamp col4 = resultSet.getTimestamp(col++);
                results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } 
        return results;
    }
    public List<DimensionDTO> browseCalendarsForModelVisId(String modelVisId) {
        HashSet<String> modelVisIds = new HashSet<String>();
        modelVisIds.add(modelVisId);

        HashMap<String, ArrayList<HierarchyRef>> calendar = getCalendarForModels(modelVisIds);
        return CalendarMapper.mapHashMapWithHierarchyRefToDTO(calendar);
    }
	public HashMap<String, ArrayList<HierarchyRef>> getCalendarForModels(HashSet<String> models) {
//		Date date = new Date();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		HashMap<String, ArrayList<HierarchyRef>> results = new HashMap<String, ArrayList<HierarchyRef>>();
		String sql = "";
		try {
			int i = 0, length = models.toArray().length;
			for (Object model : models.toArray()) {
			    sql += "(SELECT '" + model.toString() + "' AS model, "+
			            "  s.structure_element_id, "+
			            "  s.CAL_VIS_ID_PREFIX "+
			            "  || s.vis_id as label , "+
			            "    CASE "+
			            "      WHEN h.parent_id = 0 "+
			            "      THEN 0 "+
			            "      ELSE to_number(s.vis_id) "+
			            "    END AS month, "+
			            "    CASE "+
			            "      WHEN h.parent_id = 0 "+
			            "      THEN  to_number(s.vis_id) "+
			            "      ELSE to_number(h.vis_id) "+
			            "    END AS year "+
			            "FROM hierarchy_element h "+
			            "JOIN structure_element s "+
			            "ON h.hierarchy_element_id = s.parent_id "+
			            "WHERE h.hierarchy_id      = "+
			            "  ( SELECT hierarchy_id FROM hierarchy WHERE vis_id = '" + model.toString() + "-Cal' )) ";
			    
				if (i < length - 1) {
					sql += "union all\n" + 
							"\n" + 
							"";
				}
				i++;
			}
			sql += "order by model, year, month";
			// TODO sometimes in DB s.vis_id == 'Open'???
			Connection conn = ConnectionUtils.getConnection();
			stmt = conn.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			ArrayList<HierarchyRef> periods = null;
			String model = "";
			while (resultSet.next()) {
				if (!resultSet.getString(1).equals(model)) {
				    if (periods != null) {
				        results.put(model, periods);                                                        
				    }
				    model = resultSet.getString(1);
				    periods = new ArrayList<HierarchyRef>();
				}
				String narrative = resultSet.getString(3);
				periods.add(new HierarchyRefImpl(new HierarchyPK(resultSet.getInt(2)), narrative));
			}	
			results.put(model, periods);
		} catch (SQLException sqle) {
			System.err.println(sqle);
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SSS");
//		System.out.println(time.format(new Date()) + " " + time.format(date));
		return results;
	}
    public ResponseMessage save(BudgetCycleDetailsDTO budgetCycle) throws ServiceException, DaoException, ClassNotFoundException, SQLException {
        ResponseMessage message = null;
        
        	
//        BudgetCycleEditorSessionServer server = cpContextHolder.getBudgetCycleEditorSessionServer();
//    	BudgetCycleEditorSessionSEJB server = new BudgetCycleEditorSessionSEJB();
//    	BudgetCycleEditorSessionSEJB budgetCycleServer
        BudgetCycleImpl budgetCycleImpl = getBudgetCycleFromServer(budgetCycleServer,budgetCycle.getBudgetCycleId(), budgetCycle.getModel().getModelId());
        ValidationError error = BudgetCyclesValidator.validateBudgetCycleDetails(budgetCycle);
        String method = (budgetCycle.getBudgetCycleId() != -1) ? "edit" : "create";
        
        List<Object[]> beforeSave = budgetCycleImpl.getXmlForms();

        if (error.getFieldErrors().isEmpty()) {
            budgetCycleImpl = BudgetCyclesMapper.mapBudgetCycleDetailsDTOToBudgetCycleImpl(budgetCycleImpl, budgetCycle, userId);
            message = save(budgetCycleServer, budgetCycleImpl, method,budgetCycle);
            List<Object[]> afterSave = budgetCycleImpl.getXmlForms();
            updateProfiles(beforeSave, afterSave, budgetCycle);
        } else {
            error.setMessage("Something WRONG during (" + method.toUpperCase() + ") budget cycle.");
            message = error;
        }
        return message;
    }
    private ResponseMessage save(BudgetCycleEditorSessionSEJB server,BudgetCycleImpl budgetCycleImpl, String operation,BudgetCycleDetailsDTO budgetCycle) throws ServiceException {
//    	BudgetCycleEditorSessionSEJB server = new BudgetCycleEditorSessionSEJB();
    	//steed
    	//BudgetCycleEditorSessionServer server = cpContextHolder.getBudgetCycleEditorSessionServer();
    	BudgetCycleCK saveResult = null;
        try {
            if (operation.equals("edit")) {
                server.update(new BudgetCycleEditorSessionCSO(userId,budgetCycleImpl));
            } else if (operation.equals("create")) {
            	saveResult = server.insert(new BudgetCycleEditorSessionCSO(userId,budgetCycleImpl));
                //arnold
                budgetCycle.setBudgetCycleId(saveResult.getBudgetCyclePK().getBudgetCycleId());

            }
            
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            throw new ServiceException("Error during " + operation + " budget cycle.", e);
        }
    }
    private BudgetCycleImpl getBudgetCycleFromServer(BudgetCycleEditorSessionSEJB server, int budgetCycleId, int modelId) throws ServiceException {

//    	BudgetCycleEditorSessionSEJB server = new BudgetCycleEditorSessionSEJB();
        BudgetCycleEditorSessionSSO sso = null;
        try {
            if (budgetCycleId != -1) {
                ModelPK modelPK = new ModelPK(modelId);
                BudgetCyclePK budgetCyclePK = new BudgetCyclePK(budgetCycleId);
                BudgetCycleCK budgetCycleCK = new BudgetCycleCK(modelPK, budgetCyclePK);
                sso = server.getItemData(userId,budgetCycleCK);
            } else {
                sso = server.getNewItemData(userId);
            }
            BudgetCycleImpl budgetCycleImpl = sso.getEditorData();
            return budgetCycleImpl;
        } catch (CPException e) {
            throw new ServiceException("Error during browsing budget cycle with id =" + budgetCycleId + "!");
        } catch (ValidationException e) {
            throw new ServiceException("Validation error during browsing budget cycle with id =" + budgetCycleId + "!");
        }
    }
    private void updateProfiles(List<Object[]> oldFormList, List<Object[]> newFormList, BudgetCycleDetailsDTO budgetCycle) throws ServiceException, DaoException, ClassNotFoundException, SQLException {
        
        List<Integer> formToSubstractList = new ArrayList<Integer>();
        List<Integer> formToAddList = new ArrayList<Integer>();

        // Create new profiles
        newFormList: for (Object[] newFormRefImpl: newFormList) {
            for (Object[] oldFormRefImpl: oldFormList) {
                if (((Integer) newFormRefImpl[0]).equals((Integer) oldFormRefImpl[0])) {
                    continue newFormList; // exists in old and new userlist
                }
            }
            formToAddList.add((Integer) newFormRefImpl[0]);
        }

        // Delete old profiles
        oldFormList: for (Object[] oldFormRefImpl: oldFormList) {
            for (Object[] newFormRefImpl: newFormList) {
                if (((Integer) newFormRefImpl[0]).equals((Integer) oldFormRefImpl[0])) {
                    continue oldFormList; // exists in old and new userlist
                }
            }
            formToSubstractList.add((Integer) oldFormRefImpl[0]);
        }
        
        if(formToAddList.size() > 0) {
            insertProfileForBudgetCycle(formToAddList, budgetCycle);
        }
        if (formToSubstractList.size() > 0) {
            deleteProfilesForBudgetCycle(formToSubstractList, budgetCycle.getBudgetCycleId());
        }
    }

    public void insertProfileForBudgetCycle(List<Integer> flatFormIdList, BudgetCycleDetailsDTO budgetCycle) throws DaoException, ClassNotFoundException, SQLException {
        if (flatFormIdList.size() > 0) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("flatFormIdList", flatFormIdList);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJDBCTempate().getDataSource());

            // @formatter:off
			String getDataUsersForProfiles = ""
					+ "SELECT DISTINCT BUDGET_USER.USER_ID AS userId, "
					+ "  XML_FORM.XML_FORM_ID AS xmlFormId "
					+ "FROM BUDGET_USER "
					+ "INNER JOIN FINANCE_CUBE "
					+ "ON BUDGET_USER.MODEL_ID = FINANCE_CUBE.MODEL_ID "
					+ "INNER JOIN XML_FORM "
					+ "ON FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID "
					+ "AND XML_FORM.XML_FORM_ID       IN (:flatFormIdList) "
					+ "INNER JOIN XML_FORM_USER_LINK "
					+ "ON XML_FORM_USER_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID "
					+ "AND XML_FORM_USER_LINK.USER_ID    = BUDGET_USER.USER_ID";

			String getDataFlatFormsForProfiles = ""
					+ "SELECT VIS_ID AS visId, " + "  DESCRIPTION "
					+ "FROM XML_FORM "
					+ "WHERE XML_FORM_ID IN (:flatFormIdList)";
			// @formatter:on

            Map<String, Object> idStm = getJDBCTempate().queryForMap("SELECT MAX(DATA_ENTRY_PROFILE_ID) AS maxId FROM DATA_ENTRY_PROFILE");
            List<Map<String, Object>> dataUsersStm = namedParameterJdbcTemplate.queryForList(getDataUsersForProfiles, parameters.getValues());
            List<Map<String, Object>> dataFlatFormsStm = namedParameterJdbcTemplate.queryForList(getDataFlatFormsForProfiles, parameters.getValues());

            int pk = MapperUtil.mapBigDecimal(idStm.get("maxId"));
            final int npk = pk + 1;

            int flatFormId;
            String flatFormVisId;
            String dataEntryProfileVisId;
            String dataEntryProfileDescription;
            List<UserFlatFormLink> userFlatFormLinkList = new ArrayList<UserFlatFormLink>();
            List<UserFlatFormDataToRecordProfile> userFlatFormDataToRecordProfileList = new ArrayList<UserFlatFormDataToRecordProfile>();

            for (int i = 0; i < dataUsersStm.size(); i++) {
                UserFlatFormLink userFlatFormLink = new UserFlatFormLink();
                userFlatFormLink.setUserId(MapperUtil.mapBigDecimal(dataUsersStm.get(i).get("userId")));
                userFlatFormLink.setFlatFormId(MapperUtil.mapBigDecimal(dataUsersStm.get(i).get("xmlFormId")));
                userFlatFormLinkList.add(userFlatFormLink);
            }
            for (int i = 0; i < dataFlatFormsStm.size(); i++) {
                flatFormId = MapperUtil.mapBigDecimal(dataUsersStm.get(i).get("xmlFormId"));
                flatFormVisId = (String) dataFlatFormsStm.get(i).get("visId");
                dataEntryProfileVisId = ProfileMapper.mapFormNameToProfileName(flatFormVisId);
                dataEntryProfileDescription = (String) dataFlatFormsStm.get(i).get("description");
                for (int j = 0; j < userFlatFormLinkList.size(); j++) {
                    if (userFlatFormLinkList.get(j).getFlatFormId() == flatFormId) {
                        UserFlatFormDataToRecordProfile userFlatFormDataToRecordProfile = new UserFlatFormDataToRecordProfile();
                        userFlatFormDataToRecordProfile.setUserId(userFlatFormLinkList.get(j).getUserId());
                        userFlatFormDataToRecordProfile.setFlatFormId(flatFormId);
                        userFlatFormDataToRecordProfile.setFlatFormVisId(dataEntryProfileVisId);
                        userFlatFormDataToRecordProfile.setFlatFormDescription(dataEntryProfileDescription);
                        userFlatFormDataToRecordProfileList.add(userFlatFormDataToRecordProfile);
                    }
                }
            }
            if (dataUsersStm.size() > 0 && dataFlatFormsStm.size() > 0) {
                insertBatchProfileForBudgetCycle(npk, userFlatFormDataToRecordProfileList, budgetCycle.getModel().getModelId(), budgetCycle.getBudgetCycleId());
            }
        }
    }
    public void insertBatchProfileForBudgetCycle(final int npk, final List<UserFlatFormDataToRecordProfile> userFlatFormDataToRecordProfileList, final int modelId, final int budgetCycleId) throws ClassNotFoundException, SQLException {
        String query = "insert into DATA_ENTRY_PROFILE (DATA_ENTRY_PROFILE_ID, VIS_ID, USER_ID, MODEL_ID, DESCRIPTION, XMLFORM_ID, UPDATED_TIME, CREATED_TIME, BUDGET_CYCLE_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int i=0; int len = userFlatFormDataToRecordProfileList.size();
        Connection conn = ConnectionUtils.getConnection();
       
        for(i=0; i < len;i++){
        	 PreparedStatement ps = conn.prepareStatement(query);
        	Calendar calendar = Calendar.getInstance();
			final Date now = calendar.getTime();
			java.sql.Timestamp nowTime = new java.sql.Timestamp(now.getTime());
			ps.setInt(1, npk + i);
			ps.setString(2, userFlatFormDataToRecordProfileList.get(i).getFlatFormVisId());
			ps.setInt(3, userFlatFormDataToRecordProfileList.get(i).getUserId());
			ps.setInt(4, modelId);
			ps.setString(5, userFlatFormDataToRecordProfileList.get(i).getFlatFormDescription());
			ps.setInt(6, userFlatFormDataToRecordProfileList.get(i).getFlatFormId());
			ps.setTimestamp(7, nowTime);
			ps.setTimestamp(8, nowTime);
			ps.setInt(9, budgetCycleId);
			int rs = ps.executeUpdate();
			ps.close();
        }
//        getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {
//            Calendar calendar = Calendar.getInstance();
//            final Date now = calendar.getTime();
//            java.sql.Timestamp nowTime = new java.sql.Timestamp(now.getTime());
//
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setInt(1, npk + i);
//                ps.setString(2, userFlatFormDataToRecordProfileList.get(i).getFlatFormVisId());
//                ps.setInt(3, userFlatFormDataToRecordProfileList.get(i).getUserId());
//                ps.setInt(4, modelId);
//                ps.setString(5, userFlatFormDataToRecordProfileList.get(i).getFlatFormDescription());
//                ps.setInt(6, userFlatFormDataToRecordProfileList.get(i).getFlatFormId());
//                ps.setTimestamp(7, nowTime);
//                ps.setTimestamp(8, nowTime);
//                ps.setInt(9, budgetCycleId);
//            }
//
//            @Override
//            public int getBatchSize() {
//                return userFlatFormDataToRecordProfileList.size();
//            }
//
//        });
    }
    public void deleteProfilesForBudgetCycle(final List<Integer> flatFormIdList, int budgetCycleId) throws DaoException {
        if (flatFormIdList.size() > 0) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("flatFormIdList", flatFormIdList);
            parameters.addValue("budgetCycleId", budgetCycleId);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJDBCTempate().getDataSource());

            // @formatter:off
			String getDataForProfiles = ""
					+ "DELETE FROM DATA_ENTRY_PROFILE WHERE BUDGET_CYCLE_ID = :budgetCycleId AND XMLFORM_ID IN (:flatFormIdList)";
			// @formatter:on

            namedParameterJdbcTemplate.update(getDataForProfiles, parameters.getValues());
        }
    }

    public List<RecalculateBatchDTO> browseAllRecalculateBatchTasks() throws ServiceException {
//        AllRecalculateBatchTasksELO allRecalculateBatchTasks = cpContextHolder.getListSessionServer().getAllRecalculateBatchTasks();
        AllRecalculateBatchTasksELO allRecalculateBatchTasks = getAllRecalculateBatchTasks();

        List<RecalculateBatchDTO> recalculateBatchsDTOList = RecalculateBatchesMapper.mapAllRecalculateBatchTasksELO(allRecalculateBatchTasks);
        return recalculateBatchsDTOList;
    }
	public AllRecalculateBatchTasksELO getAllRecalculateBatchTasks() {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		PreparedStatement stmt1 = null;
		AllRecalculateBatchTasksELO results = new AllRecalculateBatchTasksELO(true);
		try {
			Connection conn = ConnectionUtils.getConnection();

			stmt = conn.prepareStatement("select recalculate_batch_id, recalculate_batch.model_id, model.vis_id, model.description, recalculate_batch.identifier, recalculate_batch.description, user_id from recalculate_batch join model on recalculate_batch.model_id = model.model_id");
			int col;
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				col = 1;

				// import_task_id
				RecalculateBatchTaskPK pkRecalculateBatchTask = new RecalculateBatchTaskPK(resultSet.getInt(col++));
				RecalculateBatchTaskRefImpl erRecalculateBatchTask = new RecalculateBatchTaskRefImpl(pkRecalculateBatchTask, "RecalculateBatchTask");

				// model
				ModelPK modelPk = new ModelPK(resultSet.getInt(col++));
				ModelRefImpl model = new ModelRefImpl(modelPk, resultSet.getString(col++) + " - " + resultSet.getString(col++));

				String identifier = resultSet.getString(col++);
				String description = resultSet.getString(col++);
				int userId = resultSet.getInt(col++);
				
				results.add(erRecalculateBatchTask, model, identifier, description, userId);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return results;
	}
    public List<com.softproideas.app.admin.dimensions.model.DimensionDTO> browseAccounts(int userId) throws ServiceException {
        AllDimensionsELO allDimensions = null;
		try {
			allDimensions = getDimensionsForLoggedUser(userId);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        AvailableDimensionsELO availableDimensions = null;
		try {
			availableDimensions = getAvailableDimensions();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<com.softproideas.app.admin.dimensions.model.DimensionDTO> accountsDTOList = DimensionMapper.mapAllDimensionsELO(allDimensions, availableDimensions, 1);
        return accountsDTOList;
    }
	public AllDimensionsELO getDimensionsForLoggedUser(int userId) throws ClassNotFoundException {
		String SQL_ALL_DIMENSIONS_FOR_USER = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,MODEL_DIMENSION_REL.MODEL_ID      ,MODEL_DIMENSION_REL.DIMENSION_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION.DESCRIPTION      ,MODEL_DIMENSION_REL.DIMENSION_SEQ_NUM      ,( select count(*) from hierarchy where dimension_id = dimension.dimension_id ) HIERARCHY_COUNT from DIMENSION    ,MODEL_DIMENSION_REL    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?)  and  DIMENSION.DIMENSION_ID = MODEL_DIMENSION_REL.DIMENSION_ID (+) and MODEL_DIMENSION_REL.MODEL_ID = MODEL.MODEL_ID (+) order by MODEL.VIS_ID, DIMENSION.VIS_ID";

/*  849 */     PreparedStatement stmt = null;
/*  850 */     ResultSet resultSet = null;
/*  851 */     AllDimensionsELO results = new AllDimensionsELO();
/*      */     try
/*      */     {
				Connection conn = ConnectionUtils.getConnection();
/*  854 */       stmt = conn.prepareStatement(SQL_ALL_DIMENSIONS_FOR_USER);
/*  855 */       int col = 1;
			 stmt.setInt(col++, userId);
/*  856 */       resultSet = stmt.executeQuery();
/*  857 */       while (resultSet.next())
/*      */       {
/*  859 */         col = 2;
/*      */ 
/*  862 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
/*      */ 
/*  865 */         String textDimension = resultSet.getString(col++);
/*  866 */         int erDimensionType = resultSet.getInt(col++);
/*      */ 
/*  869 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  873 */         String textModelDimensionRel = "";
/*      */ 
/*  875 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  878 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  881 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
/*      */ 
/*  888 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
/*      */ 
/*  894 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  899 */         int col1 = resultSet.getInt(col++);
/*  900 */         String col2 = resultSet.getString(col++);
/*  901 */         int col3 = resultSet.getInt(col++);
/*  902 */         int col4 = resultSet.getInt(col++);
/*      */ 
/*  905 */         results.add(erDimension, erModelDimensionRel, erModel, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  918 */       sqle.printStackTrace();
/*      */     }
/*      */ 
/*      */ 
/*  932 */     return results;
		}
	/*      */   public AvailableDimensionsELO getAvailableDimensions() throws ClassNotFoundException
	/*      */   {
		 String SQL_AVAILABLE_DIMENSIONS = "select 0       ,DIMENSION.DIMENSION_ID      ,DIMENSION.VIS_ID      ,DIMENSION.TYPE      ,DIMENSION.TYPE      ,DIMENSION.DESCRIPTION from DIMENSION where 1=1  and  DIMENSION.DIMENSION_ID not in ( select distinct DIMENSION_ID from MODEL_DIMENSION_REL) order by DIMENSION.VIS_ID";

	/*  964 */     PreparedStatement stmt = null;
	/*  965 */     ResultSet resultSet = null;
	/*  966 */     AvailableDimensionsELO results = new AvailableDimensionsELO();
	/*      */     try
	/*      */     {
						Connection conn = ConnectionUtils.getConnection();
	/*  969 */       stmt = conn.prepareStatement(SQL_AVAILABLE_DIMENSIONS);
	/*  970 */       int col = 1;
	/*  971 */       resultSet = stmt.executeQuery();
	/*  972 */       while (resultSet.next())
	/*      */       {
	/*  974 */         col = 2;
	/*      */ 
	/*  977 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
	/*      */ 
	/*  980 */         String textDimension = resultSet.getString(col++);
	/*  981 */         int erDimensionType = resultSet.getInt(col++);
	/*      */ 
	/*  985 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
	/*      */ 
	/*  991 */         int col1 = resultSet.getInt(col++);
	/*  992 */         String col2 = resultSet.getString(col++);
	/*      */ 
	/*  995 */         results.add(erDimension, col1, col2);
	/*      */       }
	/*      */ 
	/*      */     }
	/*      */     catch (SQLException sqle)
	/*      */     {
						sqle.printStackTrace();
	/*      */     }
	/* 1018 */     return results;
	/*      */   }
	
    public BudgetCycleDetailsDTO fetchBudgetCycle(int budgetCycleId, int modelId) throws ServiceException {
//        BudgetCycleEditorSessionServer server = cpContextHolder.getBudgetCycleEditorSessionServer();

        BudgetCycleImpl budgetCycle = getBudgetCycleFromServer(budgetCycleServer, budgetCycleId, modelId);
        BudgetCycleDetailsDTO budgetCycleDetailsDTO = BudgetCyclesMapper.mapBudgetCycleImplToBudgetCycleDetailsDTO(budgetCycle);
        return budgetCycleDetailsDTO;
    }
    private BudgetCycleImpl getBudgetCycleFromServer(BudgetCycleEditorSessionServer server, int budgetCycleId, int modelId) throws ServiceException {

        BudgetCycleEditorSessionSSO sso = null;
        try {
            if (budgetCycleId != -1) {
                ModelPK modelPK = new ModelPK(modelId);
                BudgetCyclePK budgetCyclePK = new BudgetCyclePK(budgetCycleId);
                BudgetCycleCK budgetCycleCK = new BudgetCycleCK(modelPK, budgetCyclePK);

                sso = server.getItemData(budgetCycleCK);
            } else {
                sso = server.getNewItemData();
            }
            BudgetCycleImpl budgetCycleImpl = sso.getEditorData();
            return budgetCycleImpl;
        } catch (CPException e) {
            throw new ServiceException("Error during browsing budget cycle with id =" + budgetCycleId + "!");
        } catch (ValidationException e) {
            throw new ServiceException("Validation error during browsing budget cycle with id =" + budgetCycleId + "!");
        }
    }
    public ModelDetailsDTO fetchModel(int modelId) throws ServiceException, ClassNotFoundException {
        ModelDetailsDTO modelDetailsDTO;
//        ModelEditorSessionServer server = cpContextHolder.getModelEditorSessionServer();
        ModelEditorSessionSEJB server = new ModelEditorSessionSEJB();

        AvailableDimensionsELO dimensionList = getAvailableDimensions();
        HierarchyRef[] hierarchy = getAvailableBudgetHierarchyRefs();
        if (modelId != -1) {
            // if model egzist
            ModelImpl modelImpl = getItemData(modelId, server);
            modelDetailsDTO = ModelsMapper.mapModelImpl(modelImpl, dimensionList, hierarchy);
        } else {
            // create empty model with list available reference for Dimensions and Hierarchies.
            modelDetailsDTO = new ModelDetailsDTO();
            ModelsMapper.mapAvailableDimensionAndHierarchyAndSetProperties(modelDetailsDTO, dimensionList, hierarchy);
        }
        return modelDetailsDTO;
    }
    public HierarchyRef[] getAvailableBudgetHierarchyRefs() throws CPException, ClassNotFoundException {
        HierarchyRef[] ret = (HierarchyRef[])((HierarchyRef[])getAllHierarchys().getValues("Hierarchy"));

        return ret;
     }

    private ModelImpl getItemData(int modelId, ModelEditorSessionSEJB server) throws ServiceException {
        try {
            ModelPK modelPK = new ModelPK(modelId);
            ModelEditorSessionSSO modelEditorSessionSSO;
            modelEditorSessionSSO = server.getItemData(this.userId,modelPK);
            ModelImpl modelImpl = modelEditorSessionSSO.getEditorData();
            return modelImpl;
        } catch (CPException e) {
            throw new ServiceException("Error during get Model!", e);
        } catch (ValidationException e) {
            throw new ServiceException("Validation error during get Model!", e);
        }
    }
    /*      */   public AllHierarchysELO getAllHierarchys() throws ClassNotFoundException
    /*      */   {
    /*  535 */     PreparedStatement stmt = null;
    /*  536 */     ResultSet resultSet = null;
    /*  537 */     AllHierarchysELO results = new AllHierarchysELO();
    /*      */     try
    /*      */     {
    					Connection conn = ConnectionUtils.getConnection();
    /*  540 */       stmt = conn.prepareStatement(SQL_ALL_HIERARCHYS);
    /*  541 */       int col = 1;
    /*  542 */       resultSet = stmt.executeQuery();
    /*  543 */       while (resultSet.next())
    /*      */       {
    /*  545 */         col = 2;
    /*      */ 
    /*  548 */         DimensionPK pkDimension = new DimensionPK(resultSet.getInt(col++));
    /*      */ 
    /*  551 */         String textDimension = resultSet.getString(col++);
    /*  552 */         int erDimensionType = resultSet.getInt(col++);
    /*      */ 
    /*  555 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
    /*      */ 
    /*  558 */         String textHierarchy = resultSet.getString(col++);
    /*      */ 
    /*  561 */         ModelDimensionRelPK pkModelDimensionRel = new ModelDimensionRelPK(resultSet.getInt(col++), resultSet.getInt(col++));
    /*      */ 
    /*  565 */         String textModelDimensionRel = "";
    /*      */ 
    /*  567 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
    /*      */ 
    /*  570 */         String textModel = resultSet.getString(col++);
    /*      */ 
    /*  574 */         HierarchyCK ckHierarchy = new HierarchyCK(pkDimension, pkHierarchy);
    /*      */ 
    /*  580 */         DimensionRefImpl erDimension = new DimensionRefImpl(pkDimension, textDimension, erDimensionType);
    /*      */ 
    /*  587 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(ckHierarchy, textHierarchy);
    /*      */ 
    /*  593 */         ModelDimensionRelRefImpl erModelDimensionRel = new ModelDimensionRelRefImpl(pkModelDimensionRel, textModelDimensionRel);
    /*      */ 
    /*  599 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
    /*      */ 
    /*  604 */         String col1 = resultSet.getString(col++);
    /*  605 */         int col2 = resultSet.getInt(col++);
    /*      */ 
    /*  608 */         results.add(erHierarchy, erDimension, erModelDimensionRel, erModel, col1, col2);
    /*      */       }
    /*      */ 
    /*      */     }
    /*      */     catch (SQLException sqle)
    /*      */     {
    /*  620 */       sqle.printStackTrace();
    /*      */     }
    /*      */     finally
    /*      */     {
    /*      */     }
    /*      */ 
    /*      */ 
    /*  634 */     return results;
    /*      */   }


}

