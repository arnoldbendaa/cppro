/*      */package com.cedar.cp.ejb.impl.xmlform;

/*      */
/*      */import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
/*      */
import com.cedar.cp.api.base.ValidationException;
/*      */
import com.cedar.cp.api.base.VersionValidationException;
/*      */
import com.cedar.cp.api.xmlform.XmlFormRef;
/*      */
import com.cedar.cp.dto.datatype.DataTypePK;
/*      */
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
/*      */
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
/*      */
import com.cedar.cp.dto.model.FinanceCubeDataTypePK;
/*      */
import com.cedar.cp.dto.model.FinanceCubeDataTypeRefImpl;
/*      */
import com.cedar.cp.dto.model.FinanceCubePK;
/*      */
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
/*      */
import com.cedar.cp.dto.model.ModelPK;
/*      */
import com.cedar.cp.dto.model.ModelRefImpl;
/*      */
import com.cedar.cp.dto.user.AllUsersELO;
/*      */
import com.cedar.cp.dto.user.UserPK;
/*      */
import com.cedar.cp.dto.user.UserRefImpl;
/*      */
import com.cedar.cp.dto.xmlform.AllDynamicXMLFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFFXmlFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFinXmlFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFinanceAndFlatFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFinanceAndFlatFormsForModelELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsAndDataTypesForModelELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsForModelELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFixedXMLFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.AllFlatXMLFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.AllMassVirementXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllXcellXmlFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.AllXmlFormsAndProfilesELO;
/*      */
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
/*      */
import com.cedar.cp.dto.xmlform.XMLFormCellPickerInfoELO;
/*      */
import com.cedar.cp.dto.xmlform.XMLFormDefinitionELO;
/*      */
import com.cedar.cp.dto.xmlform.XmlFormCK;
/*      */
import com.cedar.cp.dto.xmlform.XmlFormPK;
/*      */
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
/*      */
import com.cedar.cp.dto.xmlform.XmlFormUserLinkCK;
/*      */
import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
/*      */
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEVO;
/*      */
import com.cedar.cp.util.DefaultValueMapping;
/*      */
import com.cedar.cp.util.Log;
/*      */
import com.cedar.cp.util.Timer;
/*      */
import com.cedar.cp.util.ValueMapping;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.common.models.StructureElementCoreDTO;



/*      */
import java.io.PrintStream;
/*      */
import java.sql.Connection;
/*      */
import java.sql.PreparedStatement;
/*      */
import java.sql.ResultSet;
/*      */
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
/*      */
import java.sql.Timestamp;
/*      */
import java.util.ArrayList;
import java.util.Calendar;
/*      */
import java.util.Collection;
/*      */
import java.util.Collections;
/*      */
import java.util.Date;
import java.util.HashMap;
/*      */
import java.util.Iterator;
/*      */
import java.util.List;
/*      */
import java.util.Map;
/*      */
import java.util.Set;





/*      */
import javax.sql.DataSource;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

/*      */
/*      */public class XmlFormDAO extends AbstractDAO
/*      */{
    /* 77 */Log _log = new Log(getClass());
    /*      */protected static final String SQL_FIND_BY_PRIMARY_KEY = "select XML_FORM_ID from XML_FORM where    XML_FORM_ID = ? ";
    /*      */private static final String SQL_SELECT_LOBS = "select  DEFINITION from XML_FORM where    XML_FORM_ID = ? for update";
    /*      */private static final String SQL_SELECT_COLUMNS = "select XML_FORM.DEFINITION,XML_FORM.XML_FORM_ID,XML_FORM.VIS_ID,XML_FORM.DESCRIPTION,XML_FORM.TYPE,XML_FORM.DESIGN_MODE,XML_FORM.FINANCE_CUBE_ID,XML_FORM.SECURITY_ACCESS,XML_FORM.VERSION_NUM,XML_FORM.UPDATED_BY_USER_ID,XML_FORM.UPDATED_TIME,XML_FORM.CREATED_TIME";
    /*      */protected static final String SQL_LOAD = " from XML_FORM where    XML_FORM_ID = ? ";
    /*      */protected static final String SQL_CREATE = "insert into XML_FORM ( XML_FORM_ID,VIS_ID,DESCRIPTION,TYPE,DESIGN_MODE,FINANCE_CUBE_ID,DEFINITION,SECURITY_ACCESS,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,empty_clob(),?,?,?,?,?)";
    /*      */protected static final String SQL_UPDATE_SEQ_NUM = "update XML_FORM_SEQ set SEQ_NUM = SEQ_NUM + ?";
    /*      */protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from XML_FORM_SEQ";
    /*      */protected static final String SQL_DUPLICATE_VALUE_CHECK_XMLFORMNAME = "select count(*) from XML_FORM where    VIS_ID = ? and not(    XML_FORM_ID = ? )";
    /*      */protected static final String SQL_STORE = "update XML_FORM set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,DESIGN_MODE = ?,FINANCE_CUBE_ID = ?,SECURITY_ACCESS = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_FORM_ID = ? AND VERSION_NUM = ?";
    /*      */protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from XML_FORM where XML_FORM_ID = ?";
    /*      */protected static final String SQL_REMOVE = "delete from XML_FORM where    XML_FORM_ID = ? ";
    protected static String SQL_ALL_XML_FORMS = "select 0  ,XML_FORM.XML_FORM_ID ,XML_FORM.VIS_ID ,FINANCE_CUBE.FINANCE_CUBE_ID  ,FINANCE_CUBE.VIS_ID,XML_FORM.TYPE       ,XML_FORM.DESCRIPTION      ,XML_FORM.XML_FORM_ID   , XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where 1=1   and (XML_FORM.TYPE = 3 or XML_FORM.TYPE = 4 or XML_FORM.TYPE = 6) and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    /*      */protected static String SQL_ALL_XML_FORMS_FOR_LOGGED_USER = "select 0  ,XML_FORM.XML_FORM_ID ,XML_FORM.VIS_ID ,FINANCE_CUBE.FINANCE_CUBE_ID  ,FINANCE_CUBE.VIS_ID,XML_FORM.TYPE       ,XML_FORM.DESCRIPTION      ,XML_FORM.XML_FORM_ID   , XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where FINANCE_CUBE.model_id in (select distinct model_id from budget_user where user_id = ?)  and (XML_FORM.TYPE = 3 or XML_FORM.TYPE = 4 or XML_FORM.TYPE = 6) and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    /* 924 */protected static String SQL_ALL_FIN_XML_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where 1=1  and  XML_FORM.TYPE = 3 and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    /*      */protected static String SQL_ALL_FIN_XML_FORMS_FOR_USER = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where FINANCE_CUBE.model_id in (select distinct model_id from budget_user where user_id = ?)  and  XML_FORM.TYPE = 3 and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    /* 1032 */protected static String SQL_ALL_F_F_XML_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where 1=1  and  XML_FORM.TYPE = 4 and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    protected static String SQL_ALL_F_F_XML_FORMS_FOR_USER = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where FINANCE_CUBE.model_id in (select distinct model_id from budget_user where user_id = ?) and  XML_FORM.TYPE = 4 and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    protected static String SQL_ALL_XCELL_XML_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where 1=1  and  XML_FORM.TYPE IN (6,7) and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    protected static String SQL_ALL_XCELL_XML_FORMS_FOR_USER = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where FINANCE_CUBE.model_id in (select distinct model_id from budget_user where user_id = ?) and  XML_FORM.TYPE IN (6,7) and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    protected static String SQL_ALL_XML_FORMS_FOR_USER = " select 0  ,XML_FORM.XML_FORM_ID ,XML_FORM.VIS_ID ,FINANCE_CUBE.FINANCE_CUBE_ID  ,FINANCE_CUBE.VIS_ID, XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      , XML_FORM.XML_FORM_ID  , XML_FORM.UPDATED_TIME from XML_FORM    ,MODEL    ,FINANCE_CUBE   ,(     select xml_form_id       from (             select f.xml_form_id, l.user_id from xml_form f, xml_form_user_link l              where f.xml_form_id = l.xml_form_id (+)             )    where  user_id is null or user_id = ?    ) FORMS where 1=1  AND MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID  AND FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID AND (XML_FORM.TYPE = 3 or XML_FORM.TYPE = 4 or XML_FORM.TYPE = 6)  and  XML_FORM.XML_FORM_ID = FORMS.XML_FORM_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";

    /* 1140 */protected static String SQL_ALL_MASS_VIREMENT_XML_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where 1=1  and  XML_FORM.TYPE = 5 and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    /*      */protected static String SQL_ALL_MASS_VIREMENT_XML_FORMS_FOR_USER = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where FINANCE_CUBE.model_id in (select distinct model_id from budget_user where user_id = ?)  and  XML_FORM.TYPE = 5 and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID";
    /* 1248 */protected static String SQL_ALL_FINANCE_XML_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,XML_FORM.FINANCE_CUBE_ID from XML_FORM where 1=1  and  XML_FORM.TYPE = 3 order by XML_FORM.VIS_ID";
    /*      */
    /* 1328 */protected static String SQL_ALL_FINANCE_AND_FLAT_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,XML_FORM.FINANCE_CUBE_ID from XML_FORM where 1=1  and  XML_FORM.TYPE = 3 or XML_FORM.TYPE = 4 order by XML_FORM.VIS_ID";
    /*      */
    /* 1408 */protected static String SQL_ALL_FINANCE_XML_FORMS_FOR_MODEL = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.DESCRIPTION from XML_FORM    ,MODEL    ,FINANCE_CUBE where 1=1  and  MODEL.MODEL_ID = ? AND MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID AND FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID AND XML_FORM.TYPE = 3 order by XML_FORM.VIS_ID";

    protected static String SQL_ALL_XML_FORMS_FOR_MODEL = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID    , finance_cube.finance_cube_id, finance_cube.vis_id   ,XML_FORM.TYPE      ,XML_FORM.DESCRIPTION      ,XML_FORM.XML_FORM_ID      ,XML_FORM.UPDATED_TIME      from XML_FORM    ,MODEL    ,FINANCE_CUBE where 1=1  and  MODEL.MODEL_ID = ? AND MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID AND FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by XML_FORM.TYPE, XML_FORM.VIS_ID";

    /*      */
    /* 1524 */protected static String SQL_ALL_FINANCE_AND_FLAT_FORMS_FOR_MODEL = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.DESCRIPTION from XML_FORM    ,MODEL    ,FINANCE_CUBE where 1=1  and  MODEL.MODEL_ID = ? AND MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID AND FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID AND XML_FORM.TYPE IN (3,4) order by XML_FORM.VIS_ID";
    /*      */
    /* 1640 */protected static String SQL_ALL_FINANCE_XML_FORMS_AND_DATA_TYPES_FOR_MODEL = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID      ,FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.DATA_TYPE_ID      ,DATA_TYPE.VIS_ID      ,DATA_TYPE.DESCRIPTION      ,DATA_TYPE.SUB_TYPE      ,DATA_TYPE.MEASURE_CLASS      ,DATA_TYPE.MEASURE_LENGTH from XML_FORM    ,MODEL    ,FINANCE_CUBE    ,FINANCE_CUBE_DATA_TYPE    ,DATA_TYPE where 1=1  and  MODEL.MODEL_ID = ? AND MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID AND FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID AND XML_FORM.TYPE = 3 AND FINANCE_CUBE.FINANCE_CUBE_ID = FINANCE_CUBE_DATA_TYPE.FINANCE_CUBE_ID and FINANCE_CUBE_DATA_TYPE.DATA_TYPE_ID = DATA_TYPE.DATA_TYPE_ID order by XML_FORM.VIS_ID, DATA_TYPE.VIS_ID";
    /*      */
    /* 1798 */protected static String SQL_ALL_FIXED_X_M_L_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,XML_FORM.XML_FORM_ID      ,XML_FORM.UPDATED_TIME from XML_FORM where 1=1  and  XML_FORM.TYPE = 1 order by XML_FORM.VIS_ID";
    /*      */
    /* 1881 */protected static String SQL_ALL_DYNAMIC_X_M_L_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,XML_FORM.XML_FORM_ID      ,XML_FORM.UPDATED_TIME from XML_FORM where 1=1  and  XML_FORM.TYPE = 2 order by XML_FORM.VIS_ID";
    /*      */
    /* 1964 */protected static String SQL_ALL_FLAT_X_M_L_FORMS = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,XML_FORM.FINANCE_CUBE_ID      ,XML_FORM.XML_FORM_ID      ,XML_FORM.UPDATED_TIME from XML_FORM where 1=1  and  XML_FORM.TYPE = 4 order by XML_FORM.VIS_ID";
    /*      */
    /* 2050 */protected static String SQL_X_M_L_FORM_DEFINITION = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,XML_FORM.DESCRIPTION      ,XML_FORM.DEFINITION      ,XML_FORM.FINANCE_CUBE_ID from XML_FORM where 1=1  and  XML_FORM.XML_FORM_ID = ?";
    /*      */
    /* 2141 */protected static String SQL_X_M_L_FORM_CELL_PICKER_INFO = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,FINANCE_CUBE.MODEL_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID from XML_FORM    ,FINANCE_CUBE where 1=1  and  XML_FORM.XML_FORM_ID = ? and XML_FORM.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID";
    /*      */
    /* 2243 */private static String[][] SQL_DELETE_CHILDREN = { { "XML_FORM_USER_LINK", "delete from XML_FORM_USER_LINK where     XML_FORM_USER_LINK.XML_FORM_ID = ? " } };
    /*      */
    /* 2252 */private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
    /*      */
    /* 2256 */private static String SQL_DELETE_DEPENDANT_CRITERIA = "and XML_FORM.XML_FORM_ID = ?)";
    /*      */private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from XML_FORM where   XML_FORM_ID = ?";
    /*      */public static final String SQL_GET_XML_FORM_USER_LINK_REF = "select 0,XML_FORM.XML_FORM_ID from XML_FORM_USER_LINK,XML_FORM where 1=1 and XML_FORM_USER_LINK.XML_FORM_ID = ? and XML_FORM_USER_LINK.USER_ID = ? and XML_FORM_USER_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID";
    /* 2616 */protected static String SQL_ALL_FINANCE_XML_FORMS_FOR_MODEL_AND_USER = "select 0       ,XML_FORM.XML_FORM_ID      ,XML_FORM.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,FINANCE_CUBE.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.DESCRIPTION      ,XML_FORM.DESIGN_MODE, BUDGET_CYCLE_LINK.XML_FORM_DATA_TYPE, (SELECT count(*) FROM budget_cycle bc WHERE BUDGET_CYCLE_LINK.xml_form_id =  bc.xml_form_id AND bc.BUDGET_CYCLE_ID = BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID) as default_form from XML_FORM    ,MODEL    ,FINANCE_CUBE    ,BUDGET_CYCLE_LINK,(      select xml_form_id        from (             select f.xml_form_id, l.user_id from xml_form f, xml_form_user_link l              where f.xml_form_id = l.xml_form_id (+)             )       where user_id is null or user_id = ?     ) FORMS where 1=1  and  XML_FORM.XML_FORM_ID = BUDGET_CYCLE_LINK.XML_FORM_ID AND BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID = ? AND MODEL.MODEL_ID = ? AND MODEL.MODEL_ID = FINANCE_CUBE.MODEL_ID  AND FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID  AND (XML_FORM.TYPE = 3 or XML_FORM.TYPE = 4 or XML_FORM.TYPE = 6)  and  XML_FORM.XML_FORM_ID = FORMS.XML_FORM_ID order by XML_FORM.VIS_ID";
    /*      */private static final String SQL_ACCOUNT_INDEX = "select m.dimension_seq_num  from finance_cube fc      ,model_dimension_rel m where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 1";
    /*      */private static final String SQL_SECONDARY_INDEX = "select max(m.dimension_seq_num)  from finance_cube fc      ,model_dimension_rel m where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 2";
    /*      */private static final String SQL_ACCOUNT_HIERARCHIES = "select h.vis_id, h.description  from hierarchy h      ,finance_cube fc      ,model_dimension_rel m where h.dimension_id = m.dimension_id   and fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 1 order by 1";
    /*      */private static final String SQL_SECONDARY_HIERARCHIES = "select h.vis_id, h.description  from hierarchy h      ,model_dimension_rel m      ,finance_cube fc where h.dimension_id = m.dimension_id   and m.model_id = fc.model_id   and fc.finance_cube_id = ?   and m.dimension_seq_num = ? order by 1";
    /*      */private static final String SQL_PERIOD_LEAVES = "select s.vis_id, s.description from structure_element s     ,finance_cube fc     ,hierarchy h     ,model_dimension_rel m where s.structure_id = h.hierarchy_id   and h.dimension_id = m.dimension_id   and fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 3   and s.leaf = 'Y' order by s.position";
    /* 3107 */private static String SQL_INSERT_TEMP_DATA = "insert into fd_temp_data (structure_id, structure_element_id, selected) values (? ,? ,?)";
    /*      */
    /* 3165 */private static String SQL_FORM_DEPLOYMENT_USERS = "with params as\n(\n  select /*+ materialize */ ? as model_id from dual\n),\nfd_temp_data_t as\n(\n  select /*+ materialize */ * from fd_temp_data\n),\nbudget_users as -- Join budget users to structure element to get pos info.\n(\n  select /*+ materialize */\n         bu.user_id, u.name as user_vis_id, se.structure_id, se.vis_id,\n         se.structure_element_id, se.position, se.end_position, se.depth\n  from budget_user bu,\n       usr u,\n       structure_element se,\n       params\n  where bu.model_id = params.model_id\n    and se.structure_element_id = bu.structure_element_id\n    and u.user_id = bu.user_id\n),\nticks_and_crosses as -- Join ticks and crosses to structure element to get pos info.\n(\n  select /*+ materialize */\n         se.vis_id, se.structure_id, se.structure_element_id, se.position,\n         se.end_position, se.depth, td.selected\n  from fd_temp_data_t td,\n       structure_element se\n  where td.structure_id = se.structure_id\n    and td.structure_element_id = se.structure_element_id \n), \nbudget_elements as -- Join budget users with ticks and crosses\n(\n  select /*+ materialize */\n         bu.structure_id, bu.user_id, bu.user_vis_id, bu.position,\n         bu.end_position, tac.depth, bu.vis_id, tac.selected\n  from budget_users bu,\n       ticks_and_crosses tac\n  where bu.structure_id = tac.structure_id\n    and tac.position <= bu.position and bu.position <= tac.end_position\n)\nselect distinct user_id, user_vis_id\nfrom\n(\n  -- Use the lowest depth row as 'the' row to deteremine if the budget user is selected\n  select be.user_id, be.user_vis_id, be.position tp, be.end_position tep, \n         be.selected, be.depth, be.vis_id,  \n         rank() over ( partition by user_id, position order by be.depth desc ) as rk\n  from budget_elements be\n)\nwhere rk = 1 and selected = 'Y'";
    /*      */protected XmlFormUserLinkDAO mXmlFormUserLinkDAO;
    /*      */protected XmlFormEVO mDetails;
    /*      */private CLOB mDefinitionClob;
    private BLOB mExcelFileBlob;
    private CLOB mJsonForm;

    /*      */
    /*      */public XmlFormDAO(Connection connection)
    /*      */{
        /* 84 */super(connection);
        /*      */}

    /*      */
    /*      */public XmlFormDAO()
    /*      */{
        /*      */}

    /*      */
    /*      */public XmlFormDAO(DataSource ds)
    /*      */{
        /* 100 */super(ds);
        /*      */}

    /*      */
    /*      */protected XmlFormPK getPK()
    /*      */{
        /* 108 */return this.mDetails.getPK();
        /*      */}

    /*      */
    /*      */public void setDetails(XmlFormEVO details)
    /*      */{
        /* 117 */this.mDetails = details.deepClone();
        /*      */}

    /*      */
    /*      */public XmlFormEVO setAndGetDetails(XmlFormEVO details, String dependants)
    /*      */{
        /* 128 */setDetails(details);
        /* 129 */generateKeys();
        /* 130 */getDependants(this.mDetails, dependants);
        /* 131 */return this.mDetails.deepClone();
        /*      */}

    /*      */
    /*      */public XmlFormPK create()
    /*      */throws DuplicateNameValidationException, ValidationException
    /*      */{
        /* 140 */doCreate();
        /*      */
        /* 142 */return this.mDetails.getPK();
        /*      */}

    /*      */
    /*      */public void load(XmlFormPK pk)
    /*      */throws ValidationException
    /*      */{
        /* 152 */doLoad(pk);
        /*      */}

    /*      */
    /*      */public void store()
    /*      */throws DuplicateNameValidationException, VersionValidationException, ValidationException
    /*      */{
        /* 161 */doStore();
        /*      */}

    /*      */
    /*      */public void remove()
    /*      */{
        /* 170 */doRemove();
        /*      */}

    /*      */
    /*      */public XmlFormPK findByPrimaryKey(XmlFormPK pk_)
    /*      */throws ValidationException
    /*      */{
        /* 179 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 180 */if (exists(pk_))
        /*      */{
            /* 182 */if (timer != null) {
                /* 183 */timer.logDebug("findByPrimaryKey", pk_);
                /*      */}
            /* 185 */return pk_;
            /*      */}
        /*      */
        /* 188 */throw new ValidationException(new StringBuilder().append(pk_).append(" not found").toString());
        /*      */}

    /*      */
    /*      */protected boolean exists(XmlFormPK pk)
    /*      */{
        /* 206 */PreparedStatement stmt = null;
        /* 207 */ResultSet resultSet = null;
        /* 208 */boolean returnValue = false;
        /*      */try
        /*      */{
            /* 212 */stmt = getConnection().prepareStatement("select XML_FORM_ID from XML_FORM where    XML_FORM_ID = ? ");
            /*      */
            /* 214 */int col = 1;
            /* 215 */stmt.setInt(col++, pk.getXmlFormId());
            /*      */
            /* 217 */resultSet = stmt.executeQuery();
            /*      */
            /* 219 */if (!resultSet.next())
                /* 220 */returnValue = false;
            /*      */else
                /* 222 */returnValue = true;
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 226 */throw handleSQLException(pk, "select XML_FORM_ID from XML_FORM where    XML_FORM_ID = ? ", sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 230 */closeResultSet(resultSet);
            /* 231 */closeStatement(stmt);
            /* 232 */closeConnection();
            /*      */}
        /* 234 */return returnValue;
        /*      */}

    /*      */
    /*      */private void selectLobs(XmlFormEVO evo_)
    /*      */{
        /* 248 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /*      */
        /* 250 */PreparedStatement stmt = null;
        /* 251 */ResultSet resultSet = null;
        /*      */try
        /*      */{
            /* 255 */stmt = getConnection().prepareStatement("select  DEFINITION from XML_FORM where    XML_FORM_ID = ? for update");
            /*      */
            /* 257 */putEvoKeysToJdbc(evo_, stmt, 1);
            /*      */
            /* 259 */resultSet = stmt.executeQuery();
            /*      */
            /* 261 */int col = 1;
            /* 262 */while (resultSet.next())
            /*      */{
                /* 264 */this.mDefinitionClob = ((CLOB) resultSet.getClob(col++));
                /*      */}
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 269 */throw handleSQLException(evo_.getPK(), "select  DEFINITION from XML_FORM where    XML_FORM_ID = ? for update", sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 273 */closeResultSet(resultSet);
            /* 274 */closeStatement(stmt);
            /*      */
            /* 276 */if (timer != null)
                /* 277 */timer.logDebug("selectLobs", evo_.getPK());
            /*      */}
        /*      */}

    private void selectJsonForm(XmlFormEVO evo_)
    /*      */{
        /* 248 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /*      */
        /* 250 */PreparedStatement stmt = null;
        /* 251 */ResultSet resultSet = null;
        /*      */try
        /*      */{
            /* 255 */stmt = getConnection().prepareStatement("select JSON_FORM from XML_FORM where XML_FORM_ID = ? for update");
            /*      */
            /* 257 */putEvoKeysToJdbc(evo_, stmt, 1);
            /*      */
            /* 259 */resultSet = stmt.executeQuery();
            /*      */
            /* 261 */int col = 1;
            /* 262 */while (resultSet.next())
            /*      */{
                /* 264 */this.mJsonForm = ((CLOB) resultSet.getClob(col++));
                /*      */}
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 269 */throw handleSQLException(evo_.getPK(), "select JSON_FORM from XML_FORM where XML_FORM_ID = ? for update", sqle);
            /*      */} catch (Exception e)
        /*      */{
            this._log.error("handleException", e);
            /*      */}
        /*      */finally
        /*      */{
            /* 273 */closeResultSet(resultSet);
            /* 274 */closeStatement(stmt);
            /*      */
            /* 276 */if (timer != null)
                /* 277 */timer.logDebug("selectJsonForm", evo_.getPK());
            /*      */}
        /*      */}

    /**
     * Select lobs excel file
     * 
     * @param evo_
     */
    private void selectLobsExcelFile(XmlFormEVO evo_) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select EXCEL_FILE from XML_FORM where XML_FORM_ID = ? for update");

            putEvoKeysToJdbc(evo_, stmt, 1);

            resultSet = stmt.executeQuery();

            int col = 1;
            while (resultSet.next()) {
                this.mExcelFileBlob = ((BLOB) resultSet.getBlob(col++));
            }
        } catch (SQLException sqle) {
            throw handleSQLException(evo_.getPK(), "select EXCEL_FILE from XML_FORM where XML_FORM_ID = ? for update", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);

            if (timer != null)
                timer.logDebug("selectLobs", evo_.getPK());
        }
    }

    /*      */private void putLobs(XmlFormEVO evo_) throws SQLException
    /*      */{
        /* 283 */updateClob(this.mDefinitionClob, evo_.getDefinition());
        /*      */}

    private void putJsonForm(XmlFormEVO evo_) throws SQLException
    /*      */{
        /* 283 */updateClob(this.mJsonForm, evo_.getJsonForm());
        /*      */}

    /**
     * Put lobs excel file
     * 
     * @param evo_
     * @throws SQLException
     */
    private void putLobsExcelFile(XmlFormEVO evo_) throws SQLException {
        if (this.mExcelFileBlob != null) {
            updateBlob(this.mExcelFileBlob, evo_.getExcelFile());
        }
    }

    /**
     * Get XxmlFormEVO from JDBC
     * 
     * @param resultSet_
     * @return
     * @throws SQLException
     */
    private XmlFormEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
        int col = 1;
        this.mExcelFileBlob = ((BLOB) resultSet_.getBlob(col++));
        this.mDefinitionClob = ((CLOB) resultSet_.getClob(col++));
        this.mJsonForm = ((CLOB) resultSet_.getClob(col++));

        // Convert blob to byte array only if excel file blob is not null
        byte[] excelFile = null;
        if (this.mExcelFileBlob != null) {
            excelFile = blobToByteArray(this.mExcelFileBlob);
        }

        String jsonForm = "";
        if (this.mJsonForm != null) {
            jsonForm = clobToString(mJsonForm);
        }

        XmlFormEVO evo = new XmlFormEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), clobToString(this.mDefinitionClob), excelFile, jsonForm, resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), null);

        evo.setUpdatedByUserId(resultSet_.getInt(col++));
        evo.setUpdatedTime(resultSet_.getTimestamp(col++));
        evo.setCreatedTime(resultSet_.getTimestamp(col++));
        return evo;
    }

    /*      */private int putEvoKeysToJdbc(XmlFormEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
    /*      */{
        /* 327 */int col = startCol_;
        /* 328 */stmt_.setInt(col++, evo_.getXmlFormId());
        /* 329 */return col;
        /*      */}

    /*      */
    /*      */private int putEvoDataToJdbc(XmlFormEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
    /*      */{
        /* 334 */int col = startCol_;
        /* 335 */stmt_.setString(col++, evo_.getVisId());
        /* 336 */stmt_.setString(col++, evo_.getDescription());
        /* 337 */stmt_.setInt(col++, evo_.getType());
        /* 338 */if (evo_.getDesignMode())
            /* 339 */stmt_.setString(col++, "Y");
        /*      */else
            /* 341 */stmt_.setString(col++, " ");
        /* 342 */stmt_.setInt(col++, evo_.getFinanceCubeId());
        /*      */
        /* 344 */if (evo_.getSecurityAccess())
            /* 345 */stmt_.setString(col++, "Y");
        /*      */else
            /* 347 */stmt_.setString(col++, " ");
        /* 348 */stmt_.setInt(col++, evo_.getVersionNum());
        /* 349 */stmt_.setInt(col++, evo_.getUpdatedByUserId());
        /* 350 */stmt_.setTimestamp(col++, evo_.getUpdatedTime());
        /* 351 */stmt_.setTimestamp(col++, evo_.getCreatedTime());
        /* 352 */return col;
        /*      */}

    /*      */

    /**
     * Get XmlForm from database
     * 
     * @param pk
     * @throws ValidationException
     */
    protected void doLoad(XmlFormPK pk) throws ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select XML_FORM.EXCEL_FILE, XML_FORM.DEFINITION, XML_FORM.JSON_FORM, XML_FORM.XML_FORM_ID, XML_FORM.VIS_ID, XML_FORM.DESCRIPTION, XML_FORM.TYPE, XML_FORM.DESIGN_MODE, XML_FORM.FINANCE_CUBE_ID, XML_FORM.SECURITY_ACCESS, XML_FORM.VERSION_NUM, XML_FORM.UPDATED_BY_USER_ID, XML_FORM.UPDATED_TIME, XML_FORM.CREATED_TIME from XML_FORM where XML_FORM_ID = ?");

            int col = 1;
            stmt.setInt(col++, pk.getXmlFormId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
            }

            this.mDetails = getEvoFromJdbc(resultSet);
            if (this.mDetails.isModified())
                this._log.info("doLoad", this.mDetails);
        } catch (SQLException sqle) {
            throw handleSQLException(pk, "select XML_FORM.EXCEL_FILE, XML_FORM.DEFINITION, XML_FORM.JSON_FORM, XML_FORM.XML_FORM_ID, XML_FORM.VIS_ID, XML_FORM.DESCRIPTION, XML_FORM.TYPE, XML_FORM.DESIGN_MODE, XML_FORM.FINANCE_CUBE_ID, XML_FORM.SECURITY_ACCESS, XML_FORM.VERSION_NUM, XML_FORM.UPDATED_BY_USER_ID, XML_FORM.UPDATED_TIME, XML_FORM.CREATED_TIME from XML_FORM where XML_FORM_ID = ?", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("doLoad", pk);
        }
    }

    /**
     * Insert XmlForm to database
     * 
     * @throws DuplicateNameValidationException
     * @throws ValidationException
     */
    protected void doCreate() throws DuplicateNameValidationException, ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        generateKeys();

        this.mDetails.postCreateInit();

        PreparedStatement stmt = null;
        try {
            duplicateValueCheckXmlFormName();

            this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
            this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
            stmt = getConnection().prepareStatement("insert into XML_FORM (XML_FORM_ID, VIS_ID, DESCRIPTION, TYPE, DESIGN_MODE, FINANCE_CUBE_ID, DEFINITION, SECURITY_ACCESS, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME, EXCEL_FILE, JSON_FORM) values ( ?, ?, ?, ?, ?, ?, empty_clob(), ?, ?, ?, ?, ?, empty_blob(), empty_clob() )");

            int col = 1;
            col = putEvoKeysToJdbc(this.mDetails, stmt, col);
            col = putEvoDataToJdbc(this.mDetails, stmt, col);

            int resultCount = stmt.executeUpdate();
            if (resultCount != 1) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(this.mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
            }

            // Add DEFINITION
            selectLobs(this.mDetails);
            this._log.debug("doCreate", "calling putLobs");
            putLobs(this.mDetails);

            // Add EXCEL_FILE
            selectLobsExcelFile(this.mDetails);
            this._log.debug("doCreate", "calling putLobsExcelFile");
            putLobsExcelFile(this.mDetails);

            // Add JSON_FORM
            selectJsonForm(this.mDetails);
            this._log.debug("doCreate", "calling putJsonForm");
            putJsonForm(this.mDetails);

            this.mDetails.reset();
        } catch (SQLException sqle) {
            throw handleSQLException(this.mDetails.getPK(), "insert into XML_FORM (XML_FORM_ID, VIS_ID, DESCRIPTION, TYPE, DESIGN_MODE, FINANCE_CUBE_ID, DEFINITION, SECURITY_ACCESS, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME, EXCEL_FILE, JSON_FORM) values ( ?, ?, ?, ?, ?, ?, empty_clob(), ?, ?, ?, ?, ?, empty_blob(), empty_clob() )", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if (timer != null) {
                timer.logDebug("doCreate", this.mDetails.toString());
            }
        }

        try {
            getXmlFormUserLinkDAO().update(this.mDetails.getUserListMap());
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception", e);
        }
    }

    /*      */public int reserveIds(int insertCount)
    /*      */{
        /* 521 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /*      */
        /* 523 */PreparedStatement stmt = null;
        /* 524 */ResultSet resultSet = null;
        /* 525 */String sqlString = null;
        /*      */try
        /*      */{
            /* 530 */sqlString = "update XML_FORM_SEQ set SEQ_NUM = SEQ_NUM + ?";
            /* 531 */stmt = getConnection().prepareStatement("update XML_FORM_SEQ set SEQ_NUM = SEQ_NUM + ?");
            /* 532 */stmt.setInt(1, insertCount);
            /*      */
            /* 534 */int resultCount = stmt.executeUpdate();
            /* 535 */if (resultCount != 1) {
                /* 536 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: update failed: resultCount=").append(resultCount).toString());
                /*      */}
            /* 538 */closeStatement(stmt);
            /*      */
            /* 541 */sqlString = "select SEQ_NUM from XML_FORM_SEQ";
            /* 542 */stmt = getConnection().prepareStatement("select SEQ_NUM from XML_FORM_SEQ");
            /* 543 */resultSet = stmt.executeQuery();
            /* 544 */if (!resultSet.next())
                /* 545 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: select failed").toString());
            /* 546 */int latestKey = resultSet.getInt(1);
            /*      */
            /* 548 */int i = latestKey - insertCount;
            /*      */return i;
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 552 */throw handleSQLException(sqlString, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 556 */closeResultSet(resultSet);
            /* 557 */closeStatement(stmt);
            /* 558 */closeConnection();
            /*      */
            /* 560 */if (timer != null)
                /* 561 */timer.logDebug("reserveIds", new StringBuilder().append("keys=").append(insertCount).toString());
            /* 561 */}
        /*      */}

    /*      */
    /*      */public XmlFormPK generateKeys()
    /*      */{
        /* 571 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /*      */
        /* 573 */int insertCount = this.mDetails.getInsertCount(0);
        /*      */
        /* 576 */if (insertCount == 0) {
            /* 577 */return this.mDetails.getPK();
            /*      */}
        /* 579 */this.mDetails.assignNextKey(reserveIds(insertCount));
        /*      */
        /* 581 */return this.mDetails.getPK();
        /*      */}

    /*      */
    /*      */protected void duplicateValueCheckXmlFormName()
    /*      */throws DuplicateNameValidationException
    /*      */{
        /* 594 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 595 */PreparedStatement stmt = null;
        /* 596 */ResultSet resultSet = null;
        /*      */try
        /*      */{
            /* 600 */stmt = getConnection().prepareStatement("select count(*) from XML_FORM where    VIS_ID = ? and not(    XML_FORM_ID = ? )");
            /*      */
            /* 603 */int col = 1;
            /* 604 */stmt.setString(col++, this.mDetails.getVisId());
            /* 605 */col = putEvoKeysToJdbc(this.mDetails, stmt, col);
            /*      */
            /* 608 */resultSet = stmt.executeQuery();
            /*      */
            /* 610 */if (!resultSet.next()) {
                /* 611 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" select of ").append(getPK()).append(" not found").toString());
                /*      */}
            /*      */
            /* 615 */col = 1;
            /* 616 */int count = resultSet.getInt(col++);
            /* 617 */if (count > 0) {
                /* 618 */throw new DuplicateNameValidationException(new StringBuilder().append("Duplicate name. Please re-name and retry.").toString());
                // throw new DuplicateNameValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" XmlFormName").toString());
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 624 */throw handleSQLException(getPK(), "select count(*) from XML_FORM where    VIS_ID = ? and not(    XML_FORM_ID = ? )", sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 628 */closeResultSet(resultSet);
            /* 629 */closeStatement(stmt);
            /* 630 */closeConnection();
            /*      */
            /* 632 */if (timer != null)
                /* 633 */timer.logDebug("duplicateValueCheckXmlFormName", "");
            /*      */}
        /*      */}

    /*      */

    /**
     * Update XmlForm in database
     * 
     * @throws DuplicateNameValidationException
     * @throws VersionValidationException
     * @throws ValidationException
     */
    protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;

        generateKeys();

        PreparedStatement stmt = null;

        boolean mainChanged = this.mDetails.isModified();
        boolean dependantChanged = false;
        try {
            if (mainChanged) {
                duplicateValueCheckXmlFormName();
            }
            if (getXmlFormUserLinkDAO().update(this.mDetails.getUserListMap())) {
                dependantChanged = true;
            }
            if ((mainChanged) || (dependantChanged)) {
                this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);

                this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
                stmt = getConnection().prepareStatement("update XML_FORM set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,DESIGN_MODE = ?,FINANCE_CUBE_ID = ?,SECURITY_ACCESS = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_FORM_ID = ? AND VERSION_NUM = ?");

                // Update DEFINITION
                selectLobs(this.mDetails);
                putLobs(this.mDetails);

                // Update XML_FILE
                selectLobsExcelFile(this.mDetails);
                putLobsExcelFile(this.mDetails);

                // Update JSON_FORM
                selectJsonForm(this.mDetails);
                putJsonForm(this.mDetails);

                int col = 1;
                col = putEvoDataToJdbc(this.mDetails, stmt, col);
                col = putEvoKeysToJdbc(this.mDetails, stmt, col);

                stmt.setInt(col++, this.mDetails.getVersionNum() - 1);

                int resultCount = stmt.executeUpdate();

                if (resultCount == 0) {
                    checkVersionNum();
                }
                if (resultCount != 1) {
                    throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
                }

                this.mDetails.reset();
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "update XML_FORM set VIS_ID = ?,DESCRIPTION = ?,TYPE = ?,DESIGN_MODE = ?,FINANCE_CUBE_ID = ?,SECURITY_ACCESS = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    XML_FORM_ID = ? AND VERSION_NUM = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if ((timer != null) && ((mainChanged) || (dependantChanged)))
                timer.logDebug("store", new StringBuilder().append(this.mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
        }
    }

    /*      */private void checkVersionNum()
    /*      */throws VersionValidationException
    /*      */{
        /* 741 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 742 */PreparedStatement stmt = null;
        /* 743 */ResultSet resultSet = null;
        /*      */try
        /*      */{
            /* 747 */stmt = getConnection().prepareStatement("select VERSION_NUM from XML_FORM where XML_FORM_ID = ?");
            /*      */
            /* 750 */int col = 1;
            /* 751 */stmt.setInt(col++, this.mDetails.getXmlFormId());
            /*      */
            /* 754 */resultSet = stmt.executeQuery();
            /*      */
            /* 756 */if (!resultSet.next()) {
                /* 757 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkVersionNum: select of ").append(getPK()).append(" not found").toString());
                /*      */}
            /*      */
            /* 760 */col = 1;
            /* 761 */int dbVersionNumber = resultSet.getInt(col++);
            /* 762 */if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
                /* 763 */throw new VersionValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" expected:").append(this.mDetails.getVersionNum() - 1).append(" found:").append(dbVersionNumber).toString());
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 769 */throw handleSQLException(getPK(), "select VERSION_NUM from XML_FORM where XML_FORM_ID = ?", sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 773 */closeStatement(stmt);
            /* 774 */closeResultSet(resultSet);
            /*      */
            /* 776 */if (timer != null)
                /* 777 */timer.logDebug("checkVersionNum", this.mDetails.getPK());
            /*      */}
        /*      */}

    /*      */
    /*      */protected void doRemove()
    /*      */{
        /* 794 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 795 */deleteDependants(this.mDetails.getPK());
        /*      */
        /* 800 */PreparedStatement stmt = null;
        /*      */try
        /*      */{
            /* 805 */stmt = getConnection().prepareStatement("delete from XML_FORM where    XML_FORM_ID = ? ");
            /*      */
            /* 808 */int col = 1;
            /* 809 */stmt.setInt(col++, this.mDetails.getXmlFormId());
            /*      */
            /* 811 */int resultCount = stmt.executeUpdate();
            /*      */
            /* 813 */if (resultCount != 1) {
                /* 814 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" delete failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 820 */throw handleSQLException(getPK(), "delete from XML_FORM where    XML_FORM_ID = ? ", sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 824 */closeStatement(stmt);
            /* 825 */closeConnection();
            /*      */
            /* 827 */if (timer != null)
                /* 828 */timer.logDebug("remove", this.mDetails.getPK());
            /*      */}
        /*      */}

    /*      */
    /*      */public AllXmlFormsELO getAllXmlForms()
    /*      */{
        /* 861 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 862 */PreparedStatement stmt = null;
        /* 863 */ResultSet resultSet = null;
        /* 864 */AllXmlFormsELO results = new AllXmlFormsELO();
        /*      */try
        /*      */{
            /* 867 */stmt = getConnection().prepareStatement(SQL_ALL_XML_FORMS);
            /* 868 */int col = 1;
            /* 869 */resultSet = stmt.executeQuery();
            /* 870 */while (resultSet.next())
            /*      */{
                /* 872 */col = 2;
                /*      */
                /* 875 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /* 878 */String textXmlForm = resultSet.getString(col++);
                /* 882 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);

                FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                String textFinanceCube = resultSet.getString(col++);
                FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 887 */int col1 = resultSet.getInt(col++);
                /* 888 */String col2 = resultSet.getString(col++);
                /* 889 */int col3 = resultSet.getInt(col++);
                /* 890 */Timestamp col4 = resultSet.getTimestamp(col++);
                /*      */
                /* 893 */results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 904 */throw handleSQLException(SQL_ALL_XML_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 908 */closeResultSet(resultSet);
            /* 909 */closeStatement(stmt);
            /* 910 */closeConnection();
            /*      */}
        /*      */
        /* 913 */if (timer != null) {
            /* 914 */timer.logDebug("getAllXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 918 */return results;
        /*      */}

    /*      */
    /*      */public AllFinXmlFormsELO getAllFinXmlForms(int userId)
    /*      */{
        /* 955 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 956 */PreparedStatement stmt = null;
        /* 957 */ResultSet resultSet = null;
        /* 958 */AllFinXmlFormsELO results = new AllFinXmlFormsELO();
        /*      */try
        /*      */{
            /* 961 */stmt = getConnection().prepareStatement(SQL_ALL_FIN_XML_FORMS_FOR_USER);
            /* 962 */int col = 1;
            stmt.setInt(1, userId);
            /* 963 */resultSet = stmt.executeQuery();
            /* 964 */while (resultSet.next())
            /*      */{
                /* 966 */col = 2;
                /*      */
                /* 969 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 972 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 975 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 978 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 981 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 987 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 992 */int col1 = resultSet.getInt(col++);
                /* 993 */String col2 = resultSet.getString(col++);
                /* 994 */int col3 = resultSet.getInt(col++);
                /* 995 */String col4 = resultSet.getString(col++);
                /* 996 */Timestamp col5 = resultSet.getTimestamp(col++);
                /*      */
                /* 999 */results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4, col5);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1012 */throw handleSQLException(SQL_ALL_FIN_XML_FORMS_FOR_USER, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1016 */closeResultSet(resultSet);
            /* 1017 */closeStatement(stmt);
            /* 1018 */closeConnection();
            /*      */}
        /*      */
        /* 1021 */if (timer != null) {
            /* 1022 */timer.logDebug("getAllFinXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1026 */return results;
        /*      */}

    /*      */
    /*      */public AllFFXmlFormsELO getAllFFXmlForms()
    /*      */{
        /* 1063 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1064 */PreparedStatement stmt = null;
        /* 1065 */ResultSet resultSet = null;
        /* 1066 */AllFFXmlFormsELO results = new AllFFXmlFormsELO();
        /*      */try
        /*      */{
            /* 1069 */stmt = getConnection().prepareStatement(SQL_ALL_F_F_XML_FORMS);
            /* 1070 */int col = 1;
            /* 1071 */resultSet = stmt.executeQuery();
            /* 1072 */while (resultSet.next())
            /*      */{
                /* 1074 */col = 2;
                /*      */
                /* 1077 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1080 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1083 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 1086 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 1089 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1095 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 1100 */int col1 = resultSet.getInt(col++);
                /* 1101 */String col2 = resultSet.getString(col++);
                /* 1102 */int col3 = resultSet.getInt(col++);
                /* 1103 */String col4 = resultSet.getString(col++);
                /* 1104 */Timestamp col5 = resultSet.getTimestamp(col++);
                /*      */
                /* 1107 */results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4, col5);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1120 */throw handleSQLException(SQL_ALL_F_F_XML_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1124 */closeResultSet(resultSet);
            /* 1125 */closeStatement(stmt);
            /* 1126 */closeConnection();
            /*      */}
        /*      */
        /* 1129 */if (timer != null) {
            /* 1130 */timer.logDebug("getAllFFXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1134 */return results;
        /*      */}

    public AllXcellXmlFormsELO getAllXcellXmlForms() {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllXcellXmlFormsELO results = new AllXcellXmlFormsELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_XCELL_XML_FORMS);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;
                XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                String textXmlForm = resultSet.getString(col++);
                FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                String textFinanceCube = resultSet.getString(col++);
                XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                int col1 = resultSet.getInt(col++);
                String col2 = resultSet.getString(col++);
                int col3 = resultSet.getInt(col++);
                String col4 = resultSet.getString(col++);
                Timestamp col5 = resultSet.getTimestamp(col++);

                results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4, col5);
            }
        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_XCELL_XML_FORMS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllXcellXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public AllXmlFormsELO getXcellXmlFormsForUser(int userId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllXmlFormsELO results = new AllXmlFormsELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_XML_FORMS_FOR_USER);
            int col = 1;
            stmt.setInt(1, userId);
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
            throw handleSQLException(SQL_ALL_XCELL_XML_FORMS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getXcellXmlFormsForUser", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    /*      */public AllMassVirementXmlFormsELO getAllMassVirementXmlForms()
    /*      */{
        /* 1171 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1172 */PreparedStatement stmt = null;
        /* 1173 */ResultSet resultSet = null;
        /* 1174 */AllMassVirementXmlFormsELO results = new AllMassVirementXmlFormsELO();
        /*      */try
        /*      */{
            /* 1177 */stmt = getConnection().prepareStatement(SQL_ALL_MASS_VIREMENT_XML_FORMS);
            /* 1178 */int col = 1;
            /* 1179 */resultSet = stmt.executeQuery();
            /* 1180 */while (resultSet.next())
            /*      */{
                /* 1182 */col = 2;
                /*      */
                /* 1185 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1188 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1191 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 1194 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 1197 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1203 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 1208 */int col1 = resultSet.getInt(col++);
                /* 1209 */String col2 = resultSet.getString(col++);
                /* 1210 */int col3 = resultSet.getInt(col++);
                /* 1211 */String col4 = resultSet.getString(col++);
                /* 1212 */Timestamp col5 = resultSet.getTimestamp(col++);
                /*      */
                /* 1215 */results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4, col5);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1228 */throw handleSQLException(SQL_ALL_MASS_VIREMENT_XML_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1232 */closeResultSet(resultSet);
            /* 1233 */closeStatement(stmt);
            /* 1234 */closeConnection();
            /*      */}
        /*      */
        /* 1237 */if (timer != null) {
            /* 1238 */timer.logDebug("getAllMassVirementXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1242 */return results;
        /*      */}

    /*      */
    /*      */public AllFinanceXmlFormsELO getAllFinanceXmlForms()
    /*      */{
        /* 1271 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1272 */PreparedStatement stmt = null;
        /* 1273 */ResultSet resultSet = null;
        /* 1274 */AllFinanceXmlFormsELO results = new AllFinanceXmlFormsELO();
        /*      */try
        /*      */{
            /* 1277 */stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_XML_FORMS);
            /* 1278 */int col = 1;
            /* 1279 */resultSet = stmt.executeQuery();
            /* 1280 */while (resultSet.next())
            /*      */{
                /* 1282 */col = 2;
                /*      */
                /* 1285 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1288 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1292 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1297 */int col1 = resultSet.getInt(col++);
                /*      */
                /* 1300 */results.add(erXmlForm, col1);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1308 */throw handleSQLException(SQL_ALL_FINANCE_XML_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1312 */closeResultSet(resultSet);
            /* 1313 */closeStatement(stmt);
            /* 1314 */closeConnection();
            /*      */}
        /*      */
        /* 1317 */if (timer != null) {
            /* 1318 */timer.logDebug("getAllFinanceXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1322 */return results;
        /*      */}

    /*      */
    /*      */public AllFinanceAndFlatFormsELO getAllFinanceAndFlatForms()
    /*      */{
        /* 1351 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1352 */PreparedStatement stmt = null;
        /* 1353 */ResultSet resultSet = null;
        /* 1354 */AllFinanceAndFlatFormsELO results = new AllFinanceAndFlatFormsELO();
        /*      */try
        /*      */{
            /* 1357 */stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_AND_FLAT_FORMS);
            /* 1358 */int col = 1;
            /* 1359 */resultSet = stmt.executeQuery();
            /* 1360 */while (resultSet.next())
            /*      */{
                /* 1362 */col = 2;
                /*      */
                /* 1365 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1368 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1372 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1377 */int col1 = resultSet.getInt(col++);
                /*      */
                /* 1380 */results.add(erXmlForm, col1);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1388 */throw handleSQLException(SQL_ALL_FINANCE_AND_FLAT_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1392 */closeResultSet(resultSet);
            /* 1393 */closeStatement(stmt);
            /* 1394 */closeConnection();
            /*      */}
        /*      */
        /* 1397 */if (timer != null) {
            /* 1398 */timer.logDebug("getAllFinanceAndFlatForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1402 */return results;
        /*      */}

    /*      */
    /*      */public AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModel(int param1)
    /*      */{
        /* 1441 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1442 */PreparedStatement stmt = null;
        /* 1443 */ResultSet resultSet = null;
        /* 1444 */AllFinanceXmlFormsForModelELO results = new AllFinanceXmlFormsForModelELO();
        /*      */try
        /*      */{
            /* 1447 */stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_XML_FORMS_FOR_MODEL);
            /* 1448 */int col = 1;
            /* 1449 */stmt.setInt(col++, param1);
            /* 1450 */resultSet = stmt.executeQuery();
            /* 1451 */while (resultSet.next())
            /*      */{
                /* 1453 */col = 2;
                /*      */
                /* 1456 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1459 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1462 */ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                /*      */
                /* 1465 */String textModel = resultSet.getString(col++);
                /*      */
                /* 1467 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 1470 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 1473 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1479 */ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                /*      */
                /* 1485 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 1490 */String col1 = resultSet.getString(col++);
                /*      */
                /* 1493 */results.add(erXmlForm, erModel, erFinanceCube, col1, "");
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1503 */throw handleSQLException(SQL_ALL_FINANCE_XML_FORMS_FOR_MODEL, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1507 */closeResultSet(resultSet);
            /* 1508 */closeStatement(stmt);
            /* 1509 */closeConnection();
            /*      */}
        /*      */
        /* 1512 */if (timer != null) {
            /* 1513 */timer.logDebug("getAllFinanceXmlFormsForModel", new StringBuilder().append(" ModelId=").append(param1).append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1518 */return results;
        /*      */}

    /*      */
    /*      */public AllFinanceAndFlatFormsForModelELO getAllFinanceAndFlatFormsForModel(int param1)
    /*      */{
        /* 1557 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1558 */PreparedStatement stmt = null;
        /* 1559 */ResultSet resultSet = null;
        /* 1560 */AllFinanceAndFlatFormsForModelELO results = new AllFinanceAndFlatFormsForModelELO();
        /*      */try
        /*      */{
            /* 1563 */stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_AND_FLAT_FORMS_FOR_MODEL);
            /* 1564 */int col = 1;
            /* 1565 */stmt.setInt(col++, param1);
            /* 1566 */resultSet = stmt.executeQuery();
            /* 1567 */while (resultSet.next())
            /*      */{
                /* 1569 */col = 2;
                /*      */
                /* 1572 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1575 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1578 */ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                /*      */
                /* 1581 */String textModel = resultSet.getString(col++);
                /*      */
                /* 1583 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 1586 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 1589 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1595 */ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                /*      */
                /* 1601 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 1606 */String col1 = resultSet.getString(col++);
                /*      */
                /* 1609 */results.add(erXmlForm, erModel, erFinanceCube, col1);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1619 */throw handleSQLException(SQL_ALL_FINANCE_AND_FLAT_FORMS_FOR_MODEL, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1623 */closeResultSet(resultSet);
            /* 1624 */closeStatement(stmt);
            /* 1625 */closeConnection();
            /*      */}
        /*      */
        /* 1628 */if (timer != null) {
            /* 1629 */timer.logDebug("getAllFinanceAndFlatFormsForModel", new StringBuilder().append(" ModelId=").append(param1).append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1634 */return results;
        /*      */}

    /*      */
    /*      */public AllFinanceXmlFormsAndDataTypesForModelELO getAllFinanceXmlFormsAndDataTypesForModel(int param1)
    /*      */{
        /* 1684 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1685 */PreparedStatement stmt = null;
        /* 1686 */ResultSet resultSet = null;
        /* 1687 */AllFinanceXmlFormsAndDataTypesForModelELO results = new AllFinanceXmlFormsAndDataTypesForModelELO();
        /*      */try
        /*      */{
            /* 1690 */stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_XML_FORMS_AND_DATA_TYPES_FOR_MODEL);
            /* 1691 */int col = 1;
            /* 1692 */stmt.setInt(col++, param1);
            /* 1693 */resultSet = stmt.executeQuery();
            /* 1694 */while (resultSet.next())
            /*      */{
                /* 1696 */col = 2;
                /*      */
                /* 1699 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1702 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1705 */ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                /*      */
                /* 1708 */String textModel = resultSet.getString(col++);
                /*      */
                /* 1710 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 1713 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 1715 */FinanceCubeDataTypePK pkFinanceCubeDataType = new FinanceCubeDataTypePK(resultSet.getInt(col++), resultSet.getShort(col++));
                /*      */
                /* 1719 */String textFinanceCubeDataType = "";
                /*      */
                /* 1721 */DataTypePK pkDataType = new DataTypePK(resultSet.getShort(col++));
                /*      */
                /* 1724 */String textDataType = resultSet.getString(col++);
                /* 1725 */String erDataTypeDescription = resultSet.getString(col++);
                /* 1726 */int erDataTypeSubType = resultSet.getInt(col++);
                /* 1727 */Integer erDataTypeMeasureClass = Integer.valueOf(resultSet.getInt(col++));
                /* 1728 */Integer erDataTypeMeasureLength = Integer.valueOf(resultSet.getInt(col++));
                /*      */
                /* 1731 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1737 */ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                /*      */
                /* 1743 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 1749 */FinanceCubeDataTypeRefImpl erFinanceCubeDataType = new FinanceCubeDataTypeRefImpl(pkFinanceCubeDataType, textFinanceCubeDataType);
                /*      */
                /* 1755 */DataTypeRefImpl erDataType = new DataTypeRefImpl(pkDataType, textDataType, erDataTypeDescription, erDataTypeSubType, erDataTypeMeasureClass, erDataTypeMeasureLength);
                /*      */
                /* 1766 */results.add(erXmlForm, erModel, erFinanceCube, erFinanceCubeDataType, erDataType);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1777 */throw handleSQLException(SQL_ALL_FINANCE_XML_FORMS_AND_DATA_TYPES_FOR_MODEL, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1781 */closeResultSet(resultSet);
            /* 1782 */closeStatement(stmt);
            /* 1783 */closeConnection();
            /*      */}
        /*      */
        /* 1786 */if (timer != null) {
            /* 1787 */timer.logDebug("getAllFinanceXmlFormsAndDataTypesForModel", new StringBuilder().append(" ModelId=").append(param1).append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1792 */return results;
        /*      */}

    /*      */
    /*      */public AllFixedXMLFormsELO getAllFixedXMLForms()
    /*      */{
        /* 1822 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1823 */PreparedStatement stmt = null;
        /* 1824 */ResultSet resultSet = null;
        /* 1825 */AllFixedXMLFormsELO results = new AllFixedXMLFormsELO();
        /*      */try
        /*      */{
            /* 1828 */stmt = getConnection().prepareStatement(SQL_ALL_FIXED_X_M_L_FORMS);
            /* 1829 */int col = 1;
            /* 1830 */resultSet = stmt.executeQuery();
            /* 1831 */while (resultSet.next())
            /*      */{
                /* 1833 */col = 2;
                /*      */
                /* 1836 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1839 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1843 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1848 */int col1 = resultSet.getInt(col++);
                /* 1849 */Timestamp col2 = resultSet.getTimestamp(col++);
                /*      */
                /* 1852 */results.add(erXmlForm, col1, col2);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1861 */throw handleSQLException(SQL_ALL_FIXED_X_M_L_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1865 */closeResultSet(resultSet);
            /* 1866 */closeStatement(stmt);
            /* 1867 */closeConnection();
            /*      */}
        /*      */
        /* 1870 */if (timer != null) {
            /* 1871 */timer.logDebug("getAllFixedXMLForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1875 */return results;
        /*      */}

    /*      */
    /*      */public AllDynamicXMLFormsELO getAllDynamicXMLForms()
    /*      */{
        /* 1905 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1906 */PreparedStatement stmt = null;
        /* 1907 */ResultSet resultSet = null;
        /* 1908 */AllDynamicXMLFormsELO results = new AllDynamicXMLFormsELO();
        /*      */try
        /*      */{
            /* 1911 */stmt = getConnection().prepareStatement(SQL_ALL_DYNAMIC_X_M_L_FORMS);
            /* 1912 */int col = 1;
            /* 1913 */resultSet = stmt.executeQuery();
            /* 1914 */while (resultSet.next())
            /*      */{
                /* 1916 */col = 2;
                /*      */
                /* 1919 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1922 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1926 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1931 */int col1 = resultSet.getInt(col++);
                /* 1932 */Timestamp col2 = resultSet.getTimestamp(col++);
                /*      */
                /* 1935 */results.add(erXmlForm, col1, col2);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1944 */throw handleSQLException(SQL_ALL_DYNAMIC_X_M_L_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1948 */closeResultSet(resultSet);
            /* 1949 */closeStatement(stmt);
            /* 1950 */closeConnection();
            /*      */}
        /*      */
        /* 1953 */if (timer != null) {
            /* 1954 */timer.logDebug("getAllDynamicXMLForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1958 */return results;
        /*      */}

    /*      */
    /*      */public AllFlatXMLFormsELO getAllFlatXMLForms()
    /*      */{
        /* 1989 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1990 */PreparedStatement stmt = null;
        /* 1991 */ResultSet resultSet = null;
        /* 1992 */AllFlatXMLFormsELO results = new AllFlatXMLFormsELO();
        /*      */try
        /*      */{
            /* 1995 */stmt = getConnection().prepareStatement(SQL_ALL_FLAT_X_M_L_FORMS);
            /* 1996 */int col = 1;
            /* 1997 */resultSet = stmt.executeQuery();
            /* 1998 */while (resultSet.next())
            /*      */{
                /* 2000 */col = 2;
                /*      */
                /* 2003 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 2006 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 2010 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 2015 */int col1 = resultSet.getInt(col++);
                /* 2016 */int col2 = resultSet.getInt(col++);
                /* 2017 */Timestamp col3 = resultSet.getTimestamp(col++);
                /*      */
                /* 2020 */results.add(erXmlForm, col1, col2, col3);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2030 */throw handleSQLException(SQL_ALL_FLAT_X_M_L_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2034 */closeResultSet(resultSet);
            /* 2035 */closeStatement(stmt);
            /* 2036 */closeConnection();
            /*      */}
        /*      */
        /* 2039 */if (timer != null) {
            /* 2040 */timer.logDebug("getAllFlatXMLForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 2044 */return results;
        /*      */}

    /*      */
    /*      */public XMLFormDefinitionELO getXMLFormDefinition(int param1)
    /*      */{
        /* 2077 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2078 */PreparedStatement stmt = null;
        /* 2079 */ResultSet resultSet = null;
        /* 2080 */XMLFormDefinitionELO results = new XMLFormDefinitionELO();
        /*      */try
        /*      */{
            /* 2083 */stmt = getConnection().prepareStatement(SQL_X_M_L_FORM_DEFINITION);
            /* 2084 */int col = 1;
            /* 2085 */stmt.setInt(col++, param1);
            /* 2086 */resultSet = stmt.executeQuery();
            /* 2087 */while (resultSet.next())
            /*      */{
                /* 2089 */col = 2;
                /*      */
                /* 2092 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 2095 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 2099 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 2104 */String col1 = resultSet.getString(col++);
                /* 2105 */CLOB col2Clob = (CLOB) resultSet.getClob(col++);
                /* 2106 */String col2 = clobToString(col2Clob);
                /* 2107 */int col3 = resultSet.getInt(col++);
                /*      */
                /* 2110 */results.add(erXmlForm, col1, col2, col3);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2120 */throw handleSQLException(SQL_X_M_L_FORM_DEFINITION, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2124 */closeResultSet(resultSet);
            /* 2125 */closeStatement(stmt);
            /* 2126 */closeConnection();
            /*      */}
        /*      */
        /* 2129 */if (timer != null) {
            /* 2130 */timer.logDebug("getXMLFormDefinition", new StringBuilder().append(" XmlFormId=").append(param1).append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 2135 */return results;
        /*      */}

    /*      */
    /*      */public XMLFormCellPickerInfoELO getXMLFormCellPickerInfo(int param1)
    /*      */{
        /* 2171 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2172 */PreparedStatement stmt = null;
        /* 2173 */ResultSet resultSet = null;
        /* 2174 */XMLFormCellPickerInfoELO results = new XMLFormCellPickerInfoELO();
        /*      */try
        /*      */{
            /* 2177 */stmt = getConnection().prepareStatement(SQL_X_M_L_FORM_CELL_PICKER_INFO);
            /* 2178 */int col = 1;
            /* 2179 */stmt.setInt(col++, param1);
            /* 2180 */resultSet = stmt.executeQuery();
            /* 2181 */while (resultSet.next())
            /*      */{
                /* 2183 */col = 2;
                /*      */
                /* 2186 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 2189 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 2192 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 2195 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 2198 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 2204 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 2209 */int col1 = resultSet.getInt(col++);
                /* 2210 */int col2 = resultSet.getInt(col++);
                /*      */
                /* 2213 */results.add(erXmlForm, erFinanceCube, col1, col2);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2223 */throw handleSQLException(SQL_X_M_L_FORM_CELL_PICKER_INFO, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2227 */closeResultSet(resultSet);
            /* 2228 */closeStatement(stmt);
            /* 2229 */closeConnection();
            /*      */}
        /*      */
        /* 2232 */if (timer != null) {
            /* 2233 */timer.logDebug("getXMLFormCellPickerInfo", new StringBuilder().append(" XmlFormId=").append(param1).append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 2238 */return results;
        /*      */}

    /*      */
    /*      */private void deleteDependants(XmlFormPK pk)
    /*      */{
        /* 2265 */Set emptyStrings = Collections.emptySet();
        /* 2266 */deleteDependants(pk, emptyStrings);
        /*      */}

    /*      */
    /*      */private void deleteDependants(XmlFormPK pk, Set<String> exclusionTables)
    /*      */{
        /* 2272 */for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
        /*      */{
            /* 2274 */if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
                /*      */continue;
            /* 2276 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
            /*      */
            /* 2278 */PreparedStatement stmt = null;
            /*      */
            /* 2280 */int resultCount = 0;
            /* 2281 */String s = null;
            /*      */try
            /*      */{
                /* 2284 */s = new StringBuilder().append(SQL_DELETE_CHILDRENS_DEPENDANTS[i][1]).append(SQL_DELETE_DEPENDANT_CRITERIA).toString();
                /*      */
                /* 2286 */if (this._log.isDebugEnabled()) {
                    /* 2287 */this._log.debug("deleteDependants", s);
                    /*      */}
                /* 2289 */stmt = getConnection().prepareStatement(s);
                /*      */
                /* 2292 */int col = 1;
                /* 2293 */stmt.setInt(col++, pk.getXmlFormId());
                /*      */
                /* 2296 */resultCount = stmt.executeUpdate();
                /*      */}
            /*      */catch (SQLException sqle)
            /*      */{
                /* 2300 */throw handleSQLException(pk, s, sqle);
                /*      */}
            /*      */finally
            /*      */{
                /* 2304 */closeStatement(stmt);
                /* 2305 */closeConnection();
                /*      */
                /* 2307 */if (timer != null) {
                    /* 2308 */timer.logDebug("deleteDependants", new StringBuilder().append("A[").append(i).append("] count=").append(resultCount).toString());
                    /*      */}
                /*      */}
            /*      */}
        /* 2312 */for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
        /*      */{
            /* 2314 */if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
                /*      */continue;
            /* 2316 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
            /*      */
            /* 2318 */PreparedStatement stmt = null;
            /*      */
            /* 2320 */int resultCount = 0;
            /* 2321 */String s = null;
            /*      */try
            /*      */{
                /* 2324 */s = SQL_DELETE_CHILDREN[i][1];
                /*      */
                /* 2326 */if (this._log.isDebugEnabled()) {
                    /* 2327 */this._log.debug("deleteDependants", s);
                    /*      */}
                /* 2329 */stmt = getConnection().prepareStatement(s);
                /*      */
                /* 2332 */int col = 1;
                /* 2333 */stmt.setInt(col++, pk.getXmlFormId());
                /*      */
                /* 2336 */resultCount = stmt.executeUpdate();
                /*      */}
            /*      */catch (SQLException sqle)
            /*      */{
                /* 2340 */throw handleSQLException(pk, s, sqle);
                /*      */}
            /*      */finally
            /*      */{
                /* 2344 */closeStatement(stmt);
                /* 2345 */closeConnection();
                /*      */
                /* 2347 */if (timer != null)
                    /* 2348 */timer.logDebug("deleteDependants", new StringBuilder().append("B[").append(i).append("] count=").append(resultCount).toString());
                /*      */}
            /*      */}
        /*      */}

    public void deleteXMLForms(Object[][] forms) {
        // XML_FORM_USER_LINK
        for (Object[] form: forms) {
            /* 2316 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
            /*      */
            /* 2318 */PreparedStatement stmt = null;
            /*      */
            /* 2320 */int resultCount = 0;
            /* 2321 */String s = null;
            /*      */try
            /*      */{
                /* 2324 */s = SQL_DELETE_CHILDREN[0][1];
                /*      */
                /* 2326 */if (this._log.isDebugEnabled()) {
                    /* 2327 */this._log.debug("deleteDependants", s);
                    /*      */}
                /* 2329 */stmt = getConnection().prepareStatement(s);
                /*      */
                /* 2332 */int col = 1;
                /* 2333 */stmt.setInt(col++, (Integer) form[4]);
                /*      */
                /* 2336 */resultCount = stmt.executeUpdate();
                /*      */}
            /*      */catch (SQLException sqle)
            /*      */{
                /*      */}
            /*      */finally
            /*      */{
                /* 2344 */closeStatement(stmt);
                /* 2345 */closeConnection();
                /*      */
                /* 2347 */if (timer != null)
                    /* 2348 */timer.logDebug("deleteDependants", new StringBuilder().append("xmls count=").append(resultCount).toString());
                /*      */}
            /*      */}

        // XML_FORM
        for (Object[] form: forms) {
            /* 2316 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
            /*      */
            /* 2318 */PreparedStatement stmt = null;
            /*      */
            /* 2320 */int resultCount = 0;
            /* 2321 */String s = null;
            /*      */try
            /*      */{
                /* 2324 */s = SQL_REMOVE;
                /*      */
                /* 2326 */if (this._log.isDebugEnabled()) {
                    /* 2327 */this._log.debug("delete XML form", s);
                    /*      */}
                /* 2329 */stmt = getConnection().prepareStatement(s);
                /*      */
                /* 2332 */int col = 1;
                /* 2333 */stmt.setInt(col++, (Integer) form[4]);
                /*      */
                /* 2336 */resultCount = stmt.executeUpdate();
                /*      */}
            /*      */catch (SQLException sqle)
            /*      */{
                /*      */}
            /*      */finally
            /*      */{
                /* 2344 */closeStatement(stmt);
                /* 2345 */closeConnection();
                /*      */
                /* 2347 */if (timer != null)
                    /* 2348 */timer.logDebug("delete XML forms", new StringBuilder().append("xmls count=").append(resultCount).toString());
                /*      */}
            /*      */}
    }

    /*      */
    /*      */public XmlFormEVO getDetails(XmlFormPK pk, String dependants)
    /*      */throws ValidationException
    /*      */{
        /* 2367 */return getDetails(new XmlFormCK(pk), dependants);
        /*      */}

    /*      */
    /*      */public XmlFormEVO getDetails(XmlFormCK paramCK, String dependants)
    /*      */throws ValidationException
    /*      */{
        /* 2384 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /*      */
        /* 2387 */if (this.mDetails == null) {
            /* 2388 */doLoad(paramCK.getXmlFormPK());
            /*      */}
        /* 2390 */else if (!this.mDetails.getPK().equals(paramCK.getXmlFormPK())) {
            /* 2391 */doLoad(paramCK.getXmlFormPK());
            /*      */}
        /* 2393 */else if (!checkIfValid())
        /*      */{
            /* 2395 */this._log.info("getDetails", new StringBuilder().append("[ALERT] XmlFormEVO ").append(this.mDetails.getPK()).append(" no longer valid - reloading").toString());
            /*      */
            /* 2397 */doLoad(paramCK.getXmlFormPK());
            /*      */}
        /*      */
        // /* 2407 */ if ((dependants.indexOf("<0>") > -1) && (!this.mDetails.isUserListAllItemsLoaded()))
        // /* */ {
        /* 2412 */this.mDetails.setUserList(getXmlFormUserLinkDAO().getAll(this.mDetails.getXmlFormId(), dependants, this.mDetails.getUserList()));
        /*      */
        // /* 2419 */ this.mDetails.setUserListAllItemsLoaded(true);
        // /* */ }
        /*      */
        /* 2422 */if ((paramCK instanceof XmlFormUserLinkCK))
        /*      */{
            /* 2424 */if (this.mDetails.getUserList() == null) {
                /* 2425 */this.mDetails.loadUserListItem(getXmlFormUserLinkDAO().getDetails(paramCK, dependants));
                /*      */}
            /*      */else {
                /* 2428 */XmlFormUserLinkPK pk = ((XmlFormUserLinkCK) paramCK).getXmlFormUserLinkPK();
                /* 2429 */XmlFormUserLinkEVO evo = this.mDetails.getUserListItem(pk);
                /* 2430 */if (evo == null) {
                    /* 2431 */this.mDetails.loadUserListItem(getXmlFormUserLinkDAO().getDetails(paramCK, dependants));
                    /*      */}
                /*      */}
            /*      */}
        /*      */
        /* 2436 */XmlFormEVO details = new XmlFormEVO();
        /* 2437 */details = this.mDetails.deepClone();
        /*      */
        /* 2439 */if (timer != null) {
            /* 2440 */timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
            /*      */}
        /* 2442 */return details;
        /*      */}

    /*      */
    /*      */private boolean checkIfValid()
    /*      */{
        /* 2452 */boolean stillValid = false;
        /* 2453 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2454 */PreparedStatement stmt = null;
        /* 2455 */ResultSet resultSet = null;
        /*      */try
        /*      */{
            /* 2458 */stmt = getConnection().prepareStatement("select VERSION_NUM from XML_FORM where   XML_FORM_ID = ?");
            /* 2459 */int col = 1;
            /* 2460 */stmt.setInt(col++, this.mDetails.getXmlFormId());
            /*      */
            /* 2462 */resultSet = stmt.executeQuery();
            /*      */
            /* 2464 */if (!resultSet.next()) {
                /* 2465 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkIfValid ").append(this.mDetails.getPK()).append(" not found").toString());
                /*      */}
            /* 2467 */col = 1;
            /* 2468 */int dbVersionNum = resultSet.getInt(col++);
            /*      */
            /* 2470 */if (dbVersionNum == this.mDetails.getVersionNum())
                /* 2471 */stillValid = true;
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2475 */throw handleSQLException(this.mDetails.getPK(), "select VERSION_NUM from XML_FORM where   XML_FORM_ID = ?", sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2479 */closeResultSet(resultSet);
            /* 2480 */closeStatement(stmt);
            /* 2481 */closeConnection();
            /*      */
            /* 2483 */if (timer != null) {
                /* 2484 */timer.logDebug("checkIfValid", this.mDetails.getPK());
                /*      */}
            /*      */}
        /* 2487 */return stillValid;
        /*      */}

    /*      */
    /*      */public XmlFormEVO getDetails(String dependants)
    /*      */throws ValidationException
    /*      */{
        /* 2493 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /*      */
        /* 2495 */if (!checkIfValid())
        /*      */{
            /* 2497 */this._log.info("getDetails", new StringBuilder().append("XmlForm ").append(this.mDetails.getPK()).append(" no longer valid - reloading").toString());
            /* 2498 */doLoad(this.mDetails.getPK());
            /*      */}
        /*      */
        /* 2502 */getDependants(this.mDetails, dependants);
        /*      */
        /* 2505 */XmlFormEVO details = this.mDetails.deepClone();
        /*      */
        /* 2507 */if (timer != null) {
            /* 2508 */timer.logDebug("getDetails", new StringBuilder().append(this.mDetails.getPK()).append(" ").append(dependants).toString());
            /*      */}
        /* 2510 */return details;
        /*      */}

    /*      */
    /*      */protected XmlFormUserLinkDAO getXmlFormUserLinkDAO()
    /*      */{
        /* 2519 */if (this.mXmlFormUserLinkDAO == null)
        /*      */{
            /* 2521 */if (this.mDataSource != null)
                /* 2522 */this.mXmlFormUserLinkDAO = new XmlFormUserLinkDAO(this.mDataSource);
            /*      */else {
                /* 2524 */this.mXmlFormUserLinkDAO = new XmlFormUserLinkDAO(getConnection());
                /*      */}
            /*      */}
        /* 2527 */return this.mXmlFormUserLinkDAO;
        /*      */}

    /*      */
    /*      */public String getEntityName()
    /*      */{
        /* 2532 */return "XmlForm";
        /*      */}

    /*      */
    /*      */public XmlFormRef getRef(XmlFormPK paramXmlFormPK)
    /*      */throws ValidationException
    /*      */{
        /* 2538 */XmlFormEVO evo = getDetails(paramXmlFormPK, "");
        /* 2539 */return evo.getEntityRef();
        /*      */}

    /*      */
    /*      */public void getDependants(Collection c, String dependants)
    /*      */{
        /* 2564 */if (c == null)
            /* 2565 */return;
        /* 2566 */Iterator iter = c.iterator();
        /* 2567 */while (iter.hasNext())
        /*      */{
            /* 2569 */XmlFormEVO evo = (XmlFormEVO) iter.next();
            /* 2570 */getDependants(evo, dependants);
            /*      */}
        /*      */}

    /*      */
    /*      */public void getDependants(XmlFormEVO evo, String dependants)
    /*      */{
        /* 2584 */if (evo.getXmlFormId() < 1) {
            /* 2585 */return;
            /*      */}
        /*      */
        /* 2593 */if (dependants.indexOf("<0>") > -1)
        /*      */{
            /* 2596 */if (!evo.isUserListAllItemsLoaded())
            /*      */{
                /* 2598 */evo.setUserList(getXmlFormUserLinkDAO().getAll(evo.getXmlFormId(), dependants, evo.getUserList()));
                /*      */
                /* 2605 */evo.setUserListAllItemsLoaded(false);
                /*      */}
            /*      */}
        /*      */}

    /*      */
    /*      */public AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModelAndUser(int param1, int budgetCycleId, int userId, boolean hasDesignModeSecurity)
    /*      */{
        /* 2663 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2664 */PreparedStatement stmt = null;
        /* 2665 */ResultSet resultSet = null;
        /* 2666 */AllFinanceXmlFormsForModelELO results = new AllFinanceXmlFormsForModelELO();
        /*      */try
        /*      */{
            /* 2669 */stmt = getConnection().prepareStatement(SQL_ALL_FINANCE_XML_FORMS_FOR_MODEL_AND_USER);
            /* 2670 */int col = 1;
            /* 2671 */stmt.setInt(col++, userId);
            stmt.setInt(col++, budgetCycleId);
            /* 2672 */stmt.setInt(col++, param1);
            /* 2673 */resultSet = stmt.executeQuery();
            /* 2674 */while (resultSet.next())
            /*      */{
                /* 2676 */boolean isDesignMode = resultSet.getString(9).equals("Y");
                /* 2677 */if ((isDesignMode) && (!hasDesignModeSecurity))
                    /*      */continue;
                /* 2679 */col = 2;
                /*      */
                /* 2682 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 2685 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 2688 */ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
                /*      */
                /* 2691 */String textModel = resultSet.getString(col++);
                /*      */
                /* 2693 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 2696 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 2699 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 2705 */ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
                /*      */
                /* 2711 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 2716 */String description = resultSet.getString(col++);

                col++; // design mode

                String dataType = resultSet.getString(col++);
                boolean defaultForm = (resultSet.getInt(col++) > 0);
                /*      */
                /* 2719 */results.add(erXmlForm, erModel, erFinanceCube, description, dataType, defaultForm);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2729 */throw handleSQLException(SQL_ALL_FINANCE_XML_FORMS_FOR_MODEL_AND_USER, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2733 */closeResultSet(resultSet);
            /* 2734 */closeStatement(stmt);
            /* 2735 */closeConnection();
            /*      */}
        /*      */
        /* 2738 */if (timer != null) {
            /* 2739 */timer.logDebug("getAllFinanceXmlFormsForModelAndUser", new StringBuilder().append(" ModelId=").append(param1).append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 2744 */return results;
        /*      */}

    /*      */
    /*      */public int getAccountDimensionIndexForFinanceCube(int financeCubeId)
    /*      */{
        /* 2749 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2750 */PreparedStatement stmt = null;
        /* 2751 */ResultSet resultSet = null;
        /* 2752 */int results = 1;
        /*      */try
        /*      */{
            /* 2755 */stmt = getConnection().prepareStatement("select m.dimension_seq_num  from finance_cube fc      ,model_dimension_rel m where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 1");
            /* 2756 */int col1 = 1;
            /* 2757 */stmt.setInt(col1++, financeCubeId);
            /* 2758 */resultSet = stmt.executeQuery();
            /* 2759 */if (resultSet.next())
            /*      */{
                /* 2761 */int index = 1;
                /* 2762 */results = resultSet.getInt(index++);
                /*      */}
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2767 */System.err.println(sqle);
            /* 2768 */sqle.printStackTrace();
            /* 2769 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getAccountDimensionIndexForFinanceCube").toString(), sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2773 */closeResultSet(resultSet);
            /* 2774 */closeStatement(stmt);
            /* 2775 */closeConnection();
            /*      */}
        /*      */
        /* 2778 */if (timer != null) {
            /* 2779 */timer.logDebug("getAccountDimensionIndexForFinanceCube", new StringBuilder().append(" items=").append(results).toString());
            /*      */}
        /* 2781 */return results;
        /*      */}

    /*      */
    /*      */public int getSecondaryDimensionIndexForFinanceCube(int financeCubeId)
    /*      */{
        /* 2786 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2787 */PreparedStatement stmt = null;
        /* 2788 */ResultSet resultSet = null;
        /* 2789 */int results = -1;
        /*      */try
        /*      */{
            /* 2792 */stmt = getConnection().prepareStatement("select max(m.dimension_seq_num)  from finance_cube fc      ,model_dimension_rel m where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 2");
            /* 2793 */int col1 = 1;
            /* 2794 */stmt.setInt(col1++, financeCubeId);
            /* 2795 */resultSet = stmt.executeQuery();
            /* 2796 */if (resultSet.next())
            /*      */{
                /* 2798 */int index = 1;
                /* 2799 */results = resultSet.getInt(index++);
                /*      */}
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2804 */System.err.println(sqle);
            /* 2805 */sqle.printStackTrace();
            /* 2806 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getSecondaryDimensionIndexForFinanceCube").toString(), sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2810 */closeResultSet(resultSet);
            /* 2811 */closeStatement(stmt);
            /* 2812 */closeConnection();
            /*      */}
        /*      */
        /* 2815 */if (timer != null) {
            /* 2816 */timer.logDebug("getAccountDimensionIndexForFinanceCube", new StringBuilder().append(" items=").append(results).toString());
            /*      */}
        /* 2818 */return results;
        /*      */}

    /*      */
    /*      */public ValueMapping getPeriodLeavesForFinanceCube(int financeCubeId)
    /*      */{
        /* 2823 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2824 */PreparedStatement stmt = null;
        /* 2825 */ResultSet resultSet = null;
        /* 2826 */ValueMapping results = null;
        /*      */try
        /*      */{
            /* 2829 */List literals = new ArrayList();
            /* 2830 */List values = new ArrayList();
            /*      */
            /* 2832 */stmt = getConnection().prepareStatement("select s.vis_id, s.description from structure_element s     ,finance_cube fc     ,hierarchy h     ,model_dimension_rel m where s.structure_id = h.hierarchy_id   and h.dimension_id = m.dimension_id   and fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 3   and s.leaf = 'Y' order by s.position");
            /* 2833 */int col1 = 1;
            /* 2834 */stmt.setInt(col1++, financeCubeId);
            /* 2835 */resultSet = stmt.executeQuery();
            /* 2836 */while (resultSet.next())
            /*      */{
                /* 2838 */int index = 1;
                /*      */
                /* 2840 */String vis = resultSet.getString(index++);
                /* 2841 */String label = resultSet.getString(index++);
                /* 2842 */literals.add(new StringBuilder().append(vis).append(" - ").append(label).toString());
                /* 2843 */values.add(vis);
                /*      */}
            /*      */
            /* 2846 */ValueMapping mapping = new DefaultValueMapping((String[]) (String[]) literals.toArray(new String[0]), values.toArray());
            /*      */
            /* 2848 */results = mapping;
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2852 */System.err.println(sqle);
            /* 2853 */sqle.printStackTrace();
            /* 2854 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getPeriodLeavesForFinanceCube").toString(), sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2858 */closeResultSet(resultSet);
            /* 2859 */closeStatement(stmt);
            /* 2860 */closeConnection();
            /*      */}
        /*      */
        /* 2863 */if (timer != null) {
            /* 2864 */timer.logDebug("getPeriodLeavesForFinanceCube", new StringBuilder().append(" items=").append(results.getLiterals().size()).toString());
            /*      */}
        /* 2866 */return results;
        /*      */}

    /*      */
    /*      */public ValueMapping getAccountHierarchiesFinanceCube(int financeCubeId)
    /*      */{
        /* 2871 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2872 */PreparedStatement stmt = null;
        /* 2873 */ResultSet resultSet = null;
        /* 2874 */ValueMapping results = null;
        /*      */try
        /*      */{
            /* 2877 */List literals = new ArrayList();
            /* 2878 */List values = new ArrayList();
            /*      */
            /* 2880 */stmt = getConnection().prepareStatement("select h.vis_id, h.description  from hierarchy h      ,finance_cube fc      ,model_dimension_rel m where h.dimension_id = m.dimension_id   and fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 1 order by 1");
            /* 2881 */int col1 = 1;
            /* 2882 */stmt.setInt(col1++, financeCubeId);
            /* 2883 */resultSet = stmt.executeQuery();
            /* 2884 */while (resultSet.next())
            /*      */{
                /* 2886 */int index = 1;
                /*      */
                /* 2888 */String vis = resultSet.getString(index++);
                /* 2889 */String label = resultSet.getString(index++);
                /* 2890 */literals.add(new StringBuilder().append(vis).append(" - ").append(label).toString());
                /* 2891 */values.add(vis);
                /*      */}
            /*      */
            /* 2894 */ValueMapping mapping = new DefaultValueMapping((String[]) (String[]) literals.toArray(new String[0]), values.toArray());
            /*      */
            /* 2896 */results = mapping;
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2900 */System.err.println(sqle);
            /* 2901 */sqle.printStackTrace();
            /* 2902 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getAccountHierarchiesFinanceCube").toString(), sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2906 */closeResultSet(resultSet);
            /* 2907 */closeStatement(stmt);
            /* 2908 */closeConnection();
            /*      */}
        /*      */
        /* 2911 */if (timer != null) {
            /* 2912 */timer.logDebug("getAccountHierarchiesFinanceCube", new StringBuilder().append(" items=").append(results.getLiterals().size()).toString());
            /*      */}
        /* 2914 */return results;
        /*      */}

    /*      */
    /*      */public ValueMapping getSecondaryHierarchiesFinanceCube(int financeCubeId, int dimensionId)
    /*      */{
        /* 2919 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2920 */PreparedStatement stmt = null;
        /* 2921 */ResultSet resultSet = null;
        /* 2922 */ValueMapping results = null;
        /*      */try
        /*      */{
            /* 2925 */List literals = new ArrayList();
            /* 2926 */List values = new ArrayList();
            /*      */
            /* 2928 */stmt = getConnection().prepareStatement("select h.vis_id, h.description  from hierarchy h      ,model_dimension_rel m      ,finance_cube fc where h.dimension_id = m.dimension_id   and m.model_id = fc.model_id   and fc.finance_cube_id = ?   and m.dimension_seq_num = ? order by 1");
            /* 2929 */int col1 = 1;
            /* 2930 */stmt.setInt(col1++, financeCubeId);
            /* 2931 */stmt.setInt(col1++, dimensionId);
            /* 2932 */resultSet = stmt.executeQuery();
            /* 2933 */while (resultSet.next())
            /*      */{
                /* 2935 */int index = 1;
                /*      */
                /* 2937 */String vis = resultSet.getString(index++);
                /* 2938 */String label = resultSet.getString(index++);
                /* 2939 */literals.add(new StringBuilder().append(vis).append(" - ").append(label).toString());
                /* 2940 */values.add(vis);
                /*      */}
            /*      */
            /* 2943 */ValueMapping mapping = new DefaultValueMapping((String[]) (String[]) literals.toArray(new String[0]), values.toArray());
            /*      */
            /* 2945 */results = mapping;
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2949 */System.err.println(sqle);
            /* 2950 */sqle.printStackTrace();
            /* 2951 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getSecondaryHierarchiesFinanceCube").toString(), sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2955 */closeResultSet(resultSet);
            /* 2956 */closeStatement(stmt);
            /* 2957 */closeConnection();
            /*      */}
        /*      */
        /* 2960 */if (timer != null) {
            /* 2961 */timer.logDebug("getSecondaryHierarchiesFinanceCube", new StringBuilder().append(" items=").append(results.getLiterals().size()).toString());
            /*      */}
        /* 2963 */return results;
        /*      */}

    /*      */
    /*      */public int lookupStructureElementFromVisId(String fcVisId, int dimId, String visId)
    /*      */throws SQLException
    /*      */{
        /* 2969 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 2970 */int result = 0;
        /* 2971 */ResultSet resultSet = null;
        /* 2972 */PreparedStatement stmt = null;
        /*      */try
        /*      */{
            /* 2975 */stmt = getConnection().prepareStatement("select se.structure_element_id   from structure_element se, finance_cube fc, model_dimension_rel mdr, hierarchy h  where fc.vis_id = ?    and mdr.model_id = fc.model_id and mdr.dimension_seq_num = ?    and h.dimension_id = mdr.dimension_id    and se.structure_id = h.hierarchy_id and se.vis_id = ? ");
            /*      */
            /* 2982 */stmt.setString(1, fcVisId);
            /* 2983 */stmt.setInt(2, dimId);
            /* 2984 */stmt.setString(3, visId);
            /* 2985 */resultSet = stmt.executeQuery();
            /* 2986 */if (resultSet.next())
                /* 2987 */result = resultSet.getInt(1);
            /*      */else
                /* 2989 */this._log.warn("lookupStructureElementFromVisId", new StringBuilder().append("Structure visId '").append(visId).append("' not found").toString());
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 2993 */System.err.println(sqle);
            /* 2994 */sqle.printStackTrace();
            /* 2995 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" lookupStructureElementFromVisId").toString(), sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 2999 */closeResultSet(resultSet);
            /* 3000 */closeStatement(stmt);
            /* 3001 */closeConnection();
            /*      */}
        /*      */
        /* 3004 */if (timer != null) {
            /* 3005 */timer.logDebug("lookupStructureElementFromVisId", new StringBuilder().append(" id=").append(result).toString());
            /*      */}
        /* 3007 */return result;
        /*      */}

    /*      */
    public Map<StructureElementKeyImpl, Boolean> populateChildElement(Map<StructureElementKeyImpl, Boolean> parents) {
        String sql = "select s.structure_id, s.structure_element_id " + 
    "from structure_element s start with s.structure_id = ? and s.structure_element_id = ? " + "connect by PRIOR s.structure_element_id=s.parent_id";
        Map<StructureElementKeyImpl, Boolean> temp = new HashMap<StructureElementKeyImpl, Boolean>(parents);
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            for (StructureElementKeyImpl key: parents.keySet()) {
                ResultSet resultSet = null;

                stmt.setInt(1, key.getStructureId());
                stmt.setInt(2, key.getStructureElementId());
                resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    int structureId = resultSet.getInt(1);
                    int structureElementId = resultSet.getInt(2);
                    StructureElementKeyImpl element = new StructureElementKeyImpl(structureId, structureElementId);
                    temp.put(element, new Boolean(true));
                }
            }
        } catch (SQLException sqle) {
            throw handleSQLException(SQL_INSERT_TEMP_DATA, sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
        return temp;
    }

    /*      */public void populateTempTable(Map m)
    /*      */{
        /* 3070 */PreparedStatement stmt = null;
        /*      */try
        /*      */{
            /* 3074 */stmt = getConnection().prepareStatement(SQL_INSERT_TEMP_DATA);
            /*      */
            /* 3076 */Iterator iter = m.keySet().iterator();
            /* 3077 */while (iter.hasNext())
            /*      */{
                /* 3079 */int col = 1;
                /* 3080 */StructureElementKeyImpl ref = (StructureElementKeyImpl) iter.next();
                /*      */
                /* 3082 */stmt.setInt(col++, ref.getStructureId());
                /* 3083 */stmt.setInt(col++, ref.getStructureElementId());
                /*      */
                /* 3085 */String booleanValue = "N";
                /* 3086 */Boolean b = (Boolean) m.get(ref);
                /* 3087 */if (b.booleanValue()) {
                    /* 3088 */booleanValue = "Y";
                    /*      */}
                /* 3090 */stmt.setString(col++, booleanValue);
                /*      */
                /* 3092 */stmt.addBatch();
                /*      */}
            /* 3094 */stmt.executeBatch();
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 3098 */throw handleSQLException(SQL_INSERT_TEMP_DATA, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 3102 */closeStatement(stmt);
            /* 3103 */closeConnection();
            /*      */}
        /*      */}

    /*      */
    /*      */public AllUsersELO getFormDeploymentUserList(int modelId)
    /*      */throws SQLException
    /*      */{
        /* 3114 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 3115 */AllUsersELO result = new AllUsersELO();
        /* 3116 */ResultSet resultSet = null;
        /* 3117 */PreparedStatement stmt = null;
        /*      */try
        /*      */{
            /* 3120 */String sql = "with params as(  select /*+ materialize */ ? as model_id from dual),fd_temp_data_t1 as(  SELECT /*+ materialize */ * FROM fd_temp_data),fd_temp_data_t as(select se.STRUCTURE_ID, se.STRUCTURE_ELEMENT_ID from structure_element se start with  (se.STRUCTURE_ID, se.STRUCTURE_ELEMENT_ID) in (select STRUCTURE_ID, STRUCTURE_ELEMENT_ID from fd_temp_data_t1)connect by se.STRUCTURE_ELEMENT_ID = prior PARENT_ID)select distinct user_id, user_vis_id from(  select bu.user_id, u.name as user_vis_id  from budget_user bu  left join usr u on (bu.user_id = u.user_id)  left join model m on (bu.model_id = m.model_id)  where (m.BUDGET_HIERARCHY_ID , bu.STRUCTURE_ELEMENT_ID) in (select STRUCTURE_ID, STRUCTURE_ELEMENT_ID from fd_temp_data_t))";
            /* 3121 */stmt = getConnection().prepareStatement(sql);
            /* 3122 */int col = 1;
            /* 3123 */stmt.setInt(col, modelId);
            /* 3124 */resultSet = stmt.executeQuery();
            /*      */
            /* 3126 */while (resultSet.next())
            /*      */{
                /* 3128 */col = 1;
                /*      */
                /* 3131 */UserPK pkUser = new UserPK(resultSet.getInt(col++));
                /* 3132 */String textUser = resultSet.getString(col++);
                /*      */
                /* 3135 */UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
                /*      */
                /* 3143 */result.add(erUser, "", false);
                /*      */}
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 3148 */System.err.println(sqle);
            /* 3149 */sqle.printStackTrace();
            /* 3150 */throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" lookupStructureElementFromVisId").toString(), sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 3154 */closeResultSet(resultSet);
            /* 3155 */closeStatement(stmt);
            /* 3156 */closeConnection();
            /*      */}
        /*      */
        /* 3159 */if (timer != null) {
            /* 3160 */timer.logDebug("getFormDeploymentUserList");
            /*      */}
        /* 3162 */return result;
        /*      */}

    /*      */
    /*      */public AllXmlFormsAndProfilesELO getAllXmlFormsAndProfiles(String name, String fullname, String form)
    /*      */{
        /* 3231 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /*      */
        /* 3233 */String sqlStatement = "";
        /* 3234 */PreparedStatement stmt = null;
        /* 3235 */ResultSet resultSet = null;
        /* 3236 */AllXmlFormsAndProfilesELO results = new AllXmlFormsAndProfilesELO();
        /*      */try
        /*      */{
            /* 3240 */StringBuilder sb = new StringBuilder();
            /* 3241 */sb.append("select USR.NAME, USR.FULL_NAME, XML_FORM.VIS_ID, XML_FORM.DESCRIPTION, DATA_ENTRY_PROFILE.VIS_ID, DATA_ENTRY_PROFILE.DESCRIPTION ");
            /* 3242 */sb.append("from USR, XML_FORM, DATA_ENTRY_PROFILE ");
            /* 3243 */sb.append("where USR.NAME like ? and USR.FULL_NAME like ? and ");
            /* 3244 */if (form.equals("%"))
                /* 3245 */sb.append("( XML_FORM.VIS_ID like ? or XML_FORM.VIS_ID is null ) ");
            /*      */else {
                /* 3247 */sb.append("XML_FORM.VIS_ID like ? ");
                /*      */}
            /* 3249 */sb.append("and USR.USER_ID = DATA_ENTRY_PROFILE.USER_ID ");
            /* 3250 */sb.append("and DATA_ENTRY_PROFILE.XMLFORM_ID = XML_FORM.XML_FORM_ID ");
            /* 3251 */sb.append("order by USR.NAME, XML_FORM.VIS_ID ");
            /*      */
            /* 3253 */sqlStatement = sb.toString();
            /* 3254 */stmt = getConnection().prepareStatement(sqlStatement);
            /* 3255 */int col = 1;
            /* 3256 */stmt.setString(col++, name);
            /* 3257 */stmt.setString(col++, fullname);
            /* 3258 */stmt.setString(col++, form);
            /* 3259 */resultSet = stmt.executeQuery();
            /* 3260 */while (resultSet.next())
            /*      */{
                /* 3262 */col = 1;
                /*      */
                /* 3264 */String col1 = resultSet.getString(col++);
                /* 3265 */String col2 = resultSet.getString(col++);
                /* 3266 */String col3 = resultSet.getString(col++);
                /* 3267 */String col4 = resultSet.getString(col++);
                /* 3268 */String col5 = resultSet.getString(col++);
                /* 3269 */String col6 = resultSet.getString(col++);
                /*      */
                /* 3272 */results.add(col1, col2, col3, col4, col5, col6);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 3284 */throw handleSQLException(sqlStatement, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 3288 */closeResultSet(resultSet);
            /* 3289 */closeStatement(stmt);
            /* 3290 */closeConnection();
            /*      */}
        /*      */
        /* 3293 */if (timer != null) {
            /* 3294 */timer.logDebug("getAllXmlFormsAndProfiles", new StringBuilder().append(" name=").append(name).append(",fullname=").append(fullname).append(",form=").append(form).append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 3301 */return results;
        /*      */}

    /**
     * Gets all the forms with a specific model id. Used in Budget Cycle Editor.
     * 
     * @param modelId
     * @return
     */
    public AllXmlFormsELO getAllXmlFormsForModel(int modelId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllXmlFormsELO results = new AllXmlFormsELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_XML_FORMS_FOR_MODEL);
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
            throw handleSQLException(SQL_ALL_XML_FORMS_FOR_MODEL, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllXmlFormsForModel", new StringBuilder().append(" ModelId=").append(modelId).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public List<Object[]> getUsersAccess() {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<Object[]> results = new ArrayList<Object[]>();
        try {
            stmt = getConnection().prepareStatement("select distinct XML_FORM_ID, USER_ID from xml_form_user_link order by xml_form_id");
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Object[] data = new Object[2];
                data[0] = resultSet.getInt(1);
                data[1] = resultSet.getInt(2);
                results.add(data);
            }

        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getUsersAccess").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getUsersAccess").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        return results;
    }

    public AllXmlFormsELO getAllXmlFormsForLoggedUser(int userId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 862 */PreparedStatement stmt = null;
        /* 863 */ResultSet resultSet = null;
        /* 864 */AllXmlFormsELO results = new AllXmlFormsELO();
        /*      */try
        /*      */{
            /* 867 */stmt = getConnection().prepareStatement(SQL_ALL_XML_FORMS_FOR_LOGGED_USER);
            /* 868 */int col = 1;
            stmt.setInt(1, userId);
            /* 869 */resultSet = stmt.executeQuery();
            /* 870 */while (resultSet.next())
            /*      */{
                /* 872 */col = 2;
                /*      */
                /* 875 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /* 878 */String textXmlForm = resultSet.getString(col++);
                /* 882 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);

                FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                String textFinanceCube = resultSet.getString(col++);
                FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 887 */int col1 = resultSet.getInt(col++);
                /* 888 */String col2 = resultSet.getString(col++);
                /* 889 */int col3 = resultSet.getInt(col++);
                /* 890 */Timestamp col4 = resultSet.getTimestamp(col++);
                /*      */
                /* 893 */results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 904 */throw handleSQLException(SQL_ALL_XML_FORMS_FOR_LOGGED_USER, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 908 */closeResultSet(resultSet);
            /* 909 */closeStatement(stmt);
            /* 910 */closeConnection();
            /*      */}
        /*      */
        /* 913 */if (timer != null) {
            /* 914 */timer.logDebug("getAllXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 918 */return results;
    }

    public AllFFXmlFormsELO getAllFFXmlFormsForLoggedUser(int userId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1064 */PreparedStatement stmt = null;
        /* 1065 */ResultSet resultSet = null;
        /* 1066 */AllFFXmlFormsELO results = new AllFFXmlFormsELO();
        /*      */try
        /*      */{
            /* 1069 */stmt = getConnection().prepareStatement(SQL_ALL_F_F_XML_FORMS_FOR_USER);
            /* 1070 */int col = 1;
            stmt.setInt(1, userId);
            /* 1071 */resultSet = stmt.executeQuery();
            /* 1072 */while (resultSet.next())
            /*      */{
                /* 1074 */col = 2;
                /*      */
                /* 1077 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1080 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1083 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 1086 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 1089 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1095 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 1100 */int col1 = resultSet.getInt(col++);
                /* 1101 */String col2 = resultSet.getString(col++);
                /* 1102 */int col3 = resultSet.getInt(col++);
                /* 1103 */String col4 = resultSet.getString(col++);
                /* 1104 */Timestamp col5 = resultSet.getTimestamp(col++);
                /*      */
                /* 1107 */results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4, col5);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1120 */throw handleSQLException(SQL_ALL_F_F_XML_FORMS_FOR_USER, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1124 */closeResultSet(resultSet);
            /* 1125 */closeStatement(stmt);
            /* 1126 */closeConnection();
            /*      */}
        /*      */
        /* 1129 */if (timer != null) {
            /* 1130 */timer.logDebug("getAllFFXmlFormsForUser", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1134 */return results;
    }

    public AllXcellXmlFormsELO getAllXcellXmlFormsForLoggedUser(int userId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllXcellXmlFormsELO results = new AllXcellXmlFormsELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_XCELL_XML_FORMS_FOR_USER);
            int col = 1;
            stmt.setInt(1, userId);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;
                XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                String textXmlForm = resultSet.getString(col++);
                FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                String textFinanceCube = resultSet.getString(col++);
                XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                int col1 = resultSet.getInt(col++);
                String col2 = resultSet.getString(col++);
                int col3 = resultSet.getInt(col++);
                String col4 = resultSet.getString(col++);
                Timestamp col5 = resultSet.getTimestamp(col++);

                results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4, col5);
            }
        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_XCELL_XML_FORMS_FOR_USER, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllXcellXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public AllMassVirementXmlFormsELO getAllMassVirementXmlFormsForLoggedUser(int userId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        /* 1172 */PreparedStatement stmt = null;
        /* 1173 */ResultSet resultSet = null;
        /* 1174 */AllMassVirementXmlFormsELO results = new AllMassVirementXmlFormsELO();
        /*      */try
        /*      */{
            /* 1177 */stmt = getConnection().prepareStatement(SQL_ALL_MASS_VIREMENT_XML_FORMS);
            /* 1178 */int col = 1;
            /* 1179 */resultSet = stmt.executeQuery();
            /* 1180 */while (resultSet.next())
            /*      */{
                /* 1182 */col = 2;
                /*      */
                /* 1185 */XmlFormPK pkXmlForm = new XmlFormPK(resultSet.getInt(col++));
                /*      */
                /* 1188 */String textXmlForm = resultSet.getString(col++);
                /*      */
                /* 1191 */FinanceCubePK pkFinanceCube = new FinanceCubePK(resultSet.getInt(col++));
                /*      */
                /* 1194 */String textFinanceCube = resultSet.getString(col++);
                /*      */
                /* 1197 */XmlFormRefImpl erXmlForm = new XmlFormRefImpl(pkXmlForm, textXmlForm);
                /*      */
                /* 1203 */FinanceCubeRefImpl erFinanceCube = new FinanceCubeRefImpl(pkFinanceCube, textFinanceCube);
                /*      */
                /* 1208 */int col1 = resultSet.getInt(col++);
                /* 1209 */String col2 = resultSet.getString(col++);
                /* 1210 */int col3 = resultSet.getInt(col++);
                /* 1211 */String col4 = resultSet.getString(col++);
                /* 1212 */Timestamp col5 = resultSet.getTimestamp(col++);
                /*      */
                /* 1215 */results.add(erXmlForm, erFinanceCube, col1, col2, col3, col4, col5);
                /*      */}
            /*      */
            /*      */}
        /*      */catch (SQLException sqle)
        /*      */{
            /* 1228 */throw handleSQLException(SQL_ALL_MASS_VIREMENT_XML_FORMS, sqle);
            /*      */}
        /*      */finally
        /*      */{
            /* 1232 */closeResultSet(resultSet);
            /* 1233 */closeStatement(stmt);
            /* 1234 */closeConnection();
            /*      */}
        /*      */
        /* 1237 */if (timer != null) {
            /* 1238 */timer.logDebug("getAllMassVirementXmlForms", new StringBuilder().append(" items=").append(results.size()).toString());
            /*      */}
        /*      */
        /* 1242 */return results;
    }

    public Object[] getExcelFile(Object pk) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select EXCEL_FILE, VERSION_NUM from XML_FORM where XML_FORM_ID = ?");
            stmt.setInt(1, ((XmlFormPK) pk).getXmlFormId());
            resultSet = stmt.executeQuery();

            int col = 1;
            while (resultSet.next()) {
                Object[] result = new Object[2];
                result[0] = blobToByteArray(((BLOB) resultSet.getBlob(col++)));
                result[1] = resultSet.getInt(col++);
                return result;
            }
        } catch (SQLException sqle) {
            throw handleSQLException((XmlFormPK) pk, "select EXCEL_FILE, VERSION_NUM from XML_FORM where XML_FORM_ID = ?", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("selectLobs", (XmlFormPK) pk);
        }
        return null;
    }

    public boolean saveJsonForm(Object pk, String json, int versionNumber, int userId) {
        int resultCount = 0;
        // check if verison is same
        mDetails = new XmlFormEVO();
        mDetails.setXmlFormId(((XmlFormPK) pk).getXmlFormId());
        mDetails.setVersionNum(versionNumber);
        if (checkIfValid()) {

            // save json
            PreparedStatement stmt = null;
            try {
                stmt = getConnection().prepareStatement("update XML_FORM set JSON_FORM = ?, UPDATED_BY_USER_ID = ?, UPDATED_TIME = ? where XML_FORM_ID = ?");

                // prepare clob
                selectJsonForm(mDetails);
                updateClob(mJsonForm, json);

                stmt.setClob(1, mJsonForm);
                stmt.setInt(2, userId);
                stmt.setTimestamp(3, new Timestamp(new Date().getTime()));
                stmt.setInt(4, ((XmlFormPK) pk).getXmlFormId());
                resultCount = stmt.executeUpdate();
            } catch (SQLException sqle) {
                throw handleSQLException((XmlFormPK) pk, "update XML_FORM set JSON_FORM = ? where XML_FORM_ID = ?", sqle);
            } finally {
                closeStatement(stmt);
                closeConnection();
            }
            if (resultCount == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean deployProfileForUsers(ArrayList<DataEntryProfileEVO> profiles) {
        
        PreparedStatement pstmt = null;
        PreparedStatement pkStm = null;

        //@formatter:off
                String sql = "insert into DATA_ENTRY_PROFILE "
                        + "(DATA_ENTRY_PROFILE_ID, VIS_ID, USER_ID, MODEL_ID, DESCRIPTION, XMLFORM_ID, DATA_TYPE, UPDATED_TIME, CREATED_TIME, BUDGET_CYCLE_ID, MOBILE, STRUCTURE_ID0, STRUCTURE_ELEMENT_ID0, ELEMENT_LABEL0, STRUCTURE_ID1, STRUCTURE_ELEMENT_ID1, ELEMENT_LABEL1, STRUCTURE_ID2, STRUCTURE_ELEMENT_ID2, ELEMENT_LABEL2)"
                        + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        // @formatter:on
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        try {
            Connection conn = getConnection();

            pkStm = conn.prepareStatement("SELECT MAX(DATA_ENTRY_PROFILE_ID) FROM DATA_ENTRY_PROFILE");
            ResultSet resultSet = pkStm.executeQuery();
            int pk = 100000000;
            while (resultSet.next()) {
                pk = resultSet.getInt(1);
            }

            pstmt = conn.prepareStatement(sql);

            for (DataEntryProfileEVO profile: profiles) {
                int c = 1;
                pstmt.setInt(c++, ++pk);
                pstmt.setString(c++, profile.getVisId());
                pstmt.setInt(c++, profile.getUserId());
                pstmt.setInt(c++, profile.getModelId());
                pstmt.setString(c++, profile.getDescription());
                pstmt.setInt(c++, profile.getXmlformId());
                pstmt.setString(c++, profile.getDataType());
                pstmt.setTimestamp(c++, new java.sql.Timestamp(now.getTime()));
                pstmt.setTimestamp(c++, new java.sql.Timestamp(now.getTime()));
                pstmt.setInt(c++, profile.getBudgetCycleId());
                pstmt.setString(c++, String.valueOf(profile.getMobile()));
                //dim0
                pstmt.setInt(c++, profile.getStructureId0());
                pstmt.setInt(c++, profile.getStructureElementId0());
                pstmt.setString(c++, profile.getElementLabel0());                
                //dim1
                pstmt.setInt(c++, profile.getStructureId1());
                pstmt.setInt(c++, profile.getStructureElementId1());
                pstmt.setString(c++, profile.getElementLabel1());
                //dim2 
                pstmt.setInt(c++, profile.getStructureId2());
                pstmt.setInt(c++, profile.getStructureElementId2());
                pstmt.setString(c++, profile.getElementLabel2());

                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException sqle) {
            SQLException e = sqle;
            do {
                if(e instanceof SQLIntegrityConstraintViolationException){
                   return false; 
                }
                e = e.getNextException();
            }
            while(e!= null);
            throw handleSQLException("", sqle);
        } finally {
            closeStatement(pkStm);
            closeStatement(pstmt);
            closeConnection();
        }
        return true;
    }

    public ArrayList<Integer> getUsersId(Integer structure_id, Integer structure_element_id, Integer xmlFormId) {
        ArrayList<Integer> returned = new ArrayList<Integer>();
        //@formatter:off
        String sql = "" + "WITH " + "bu_usr AS " + "( " + "select distinct bu.user_id from BUDGET_USER bu " + "where BU.STRUCTURE_ELEMENT_ID in (SELECT " + "  s.structure_element_id " + "FROM structure_element s " + "  START WITH s.structure_id              = ? " + "AND s.structure_element_id               = ? " + "  CONNECT BY PRIOR s.structure_element_id=s.parent_id) " + "), " + "xform_usr AS " + "( " + "select distinct xful.user_id from xml_form_user_link xful, USR u " + "  where "
                + "  xful.user_id = u.user_id and " + "  XFUL.XML_FORM_ID = ?  and " + "  U.USER_DISABLED <> 'Y' " + ") " + "select * from bu_usr " + "intersect " + "select * from xform_usr";
        //@formatter:on
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, structure_id.intValue());
            stmt.setInt(2, structure_element_id.intValue());
            stmt.setInt(3, xmlFormId.intValue());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                returned.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            closeStatement(stmt);
            closeConnection();
        }
        return returned;
    }

    public ArrayList<StructureElementCoreDTO> getStructuresElementsForUser(Integer userId, int modelId, int structureId, int structureElementId) {
        ArrayList<StructureElementCoreDTO> data = new  ArrayList<StructureElementCoreDTO>();
      //@formatter:off
        String sql = ""
                + "SELECT s.vis_id, "
                + "  s.structure_id, "
                + "  s.structure_element_id "
                + "FROM structure_element s "
                + "  START WITH s.structure_id              = ? "
                + "AND s.structure_element_id               = ? "
                + "  CONNECT BY PRIOR s.structure_element_id=s.parent_id "
                + "INTERSECT "
                + "SELECT s.vis_id, "
                + "  s.structure_id, "
                + "  s.structure_element_id "
                + "FROM structure_element s "
                + "  START WITH s.structure_id = ? "
                + "AND s.structure_element_id IN "
                + "  (SELECT bu.structure_element_id "
                + "  FROM budget_user bu "
                + "  WHERE bu.model_id = ? "
                + "  AND bu.user_id    = ? "
                + "  ) "
                + "  CONNECT BY PRIOR s.structure_element_id=s.parent_id";
      //@formatter:on
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, structureId);
            stmt.setInt(2, structureElementId);
            stmt.setInt(3, structureId);          
            
            
            stmt.setInt(4, modelId);
            stmt.setInt(5, userId.intValue());
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                StructureElementCoreDTO d = new  StructureElementCoreDTO();
                d.setStructureElementVisId(resultSet.getString(1));
                d.setStructureId(resultSet.getInt(2));
                d.setStructureElementId(resultSet.getInt(3));               
                
                data.add(d);
            }
          
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            closeStatement(stmt);
            closeConnection();
        }
        return data;  
    };

}