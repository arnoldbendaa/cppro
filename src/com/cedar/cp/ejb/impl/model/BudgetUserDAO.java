package com.cedar.cp.ejb.impl.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.sql.DataSource;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForCycleELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForLocationELO;
import com.cedar.cp.dto.budgetlocation.UserModelElementAssignmentImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllBudgetUsersELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.BudgetDetailsForUserELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel2ELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel3ELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel4ELO;
import com.cedar.cp.dto.model.BudgetUserCK;
import com.cedar.cp.dto.model.BudgetUserDetailsELO;
import com.cedar.cp.dto.model.BudgetUserPK;
import com.cedar.cp.dto.model.BudgetUserRefImpl;
import com.cedar.cp.dto.model.BudgetUsersForNodeELO;
import com.cedar.cp.dto.model.CheckUserAccessELO;
import com.cedar.cp.dto.model.CheckUserAccessToModelELO;
import com.cedar.cp.dto.model.CheckUserELO;
import com.cedar.cp.dto.model.LinkedBudgetUserDetailsELO;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.NodesForUserAndCycleELO;
import com.cedar.cp.dto.model.NodesForUserAndModelELO;
import com.cedar.cp.dto.model.StartupDetailsForPickerELO;
import com.cedar.cp.dto.model.StartupDetailsForPickerLevel2ELO;
import com.cedar.cp.dto.model.StartupDetailsForPickerLevel3ELO;
import com.cedar.cp.dto.model.UsersForModelAndElementELO;
import com.cedar.cp.dto.user.AllUsersAssignmentsELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.SqlBuilder;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.awt.FitTableColumnsAction;

public class BudgetUserDAO extends AbstractDAO {

    Log _log = new Log(getClass());
    private static final String SQL_SELECT_COLUMNS = "select BUDGET_USER.MODEL_ID,BUDGET_USER.STRUCTURE_ELEMENT_ID,BUDGET_USER.USER_ID,BUDGET_USER.READ_ONLY,BUDGET_USER.CURRENCY_ID,BUDGET_USER.VERSION_NUM,BUDGET_USER.UPDATED_BY_USER_ID,BUDGET_USER.UPDATED_TIME,BUDGET_USER.CREATED_TIME";
    protected static final String SQL_LOAD = " from BUDGET_USER where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? ";
    protected static final String SQL_CREATE = "insert into BUDGET_USER ( MODEL_ID,STRUCTURE_ELEMENT_ID,USER_ID,READ_ONLY,CURRENCY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)";
    protected static final String SQL_STORE = "update BUDGET_USER set READ_ONLY = ?,CURRENCY_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? AND VERSION_NUM = ?";
    protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from BUDGET_USER where MODEL_ID = ?,STRUCTURE_ELEMENT_ID = ?,USER_ID = ?";
    protected static String SQL_ALL_BUDGET_USERS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_USER.MODEL_ID      ,BUDGET_USER.STRUCTURE_ELEMENT_ID      ,BUDGET_USER.USER_ID      ,USR.NAME      ,USR.USER_ID      ,USR.NAME from BUDGET_USER    ,MODEL    ,USR where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID  and  USR.USER_ID = BUDGET_USER.USER_ID order by USR.USER_ID";

    protected static String SQL_CHECK_USER_ACCESS_TO_MODEL = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_USER.MODEL_ID      ,BUDGET_USER.STRUCTURE_ELEMENT_ID      ,BUDGET_USER.USER_ID      ,BUDGET_USER.READ_ONLY from BUDGET_USER    ,MODEL where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_USER.USER_ID = ? and BUDGET_USER.MODEL_ID = ?";

    protected static String SQL_CHECK_USER_ACCESS = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_USER.MODEL_ID      ,BUDGET_USER.STRUCTURE_ELEMENT_ID      ,BUDGET_USER.USER_ID      ,BUDGET_USER.READ_ONLY from BUDGET_USER    ,MODEL where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_USER.USER_ID = ? and BUDGET_USER.STRUCTURE_ELEMENT_ID = ?";

    protected static String SQL_CHECK_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_USER.MODEL_ID      ,BUDGET_USER.STRUCTURE_ELEMENT_ID      ,BUDGET_USER.USER_ID      ,BUDGET_USER.USER_ID from BUDGET_USER    ,MODEL where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_USER.USER_ID = ?";

    protected static String SQL_BUDGET_USERS_FOR_NODE = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_USER.MODEL_ID      ,BUDGET_USER.STRUCTURE_ELEMENT_ID      ,BUDGET_USER.USER_ID      ,USR.USER_ID      ,USR.NAME      ,USR.USER_ID      ,USR.NAME      ,USR.FULL_NAME      ,USR.E_MAIL_ADDRESS from BUDGET_USER    ,MODEL    ,USR where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_USER.MODEL_ID = ? and BUDGET_USER.STRUCTURE_ELEMENT_ID = ? and BUDGET_USER.USER_ID = USR.USER_ID";

    protected static String SQL_NODES_FOR_USER_AND_CYCLE = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF from BUDGET_USER    ,MODEL    ,STRUCTURE_ELEMENT    ,BUDGET_CYCLE where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_CYCLE.BUDGET_CYCLE_ID = ? AND BUDGET_USER.USER_ID = ? AND MODEL.MODEL_ID = BUDGET_CYCLE.MODEL_ID AND MODEL.MODEL_ID = BUDGET_USER.MODEL_ID AND BUDGET_USER.STRUCTURE_ELEMENT_ID = STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID AND MODEL.BUDGET_HIERARCHY_ID = STRUCTURE_ELEMENT.STRUCTURE_ID";

    protected static String SQL_NODES_FOR_USER_AND_MODEL = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,BUDGET_USER.MODEL_ID      ,BUDGET_USER.STRUCTURE_ELEMENT_ID      ,BUDGET_USER.USER_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,BUDGET_USER.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION from BUDGET_USER    ,MODEL    ,STRUCTURE_ELEMENT where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_USER.MODEL_ID = ? AND BUDGET_USER.USER_ID = ? and BUDGET_USER.STRUCTURE_ELEMENT_ID = STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID order by STRUCTURE_ELEMENT.DEPTH, STRUCTURE_ELEMENT.POSITION";

    protected static String SQL_USERS_FOR_MODEL_AND_ELEMENT = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.FULL_NAME      ,USR.E_MAIL_ADDRESS from BUDGET_USER    ,MODEL    ,USR where 1=1   and BUDGET_USER.MODEL_ID = MODEL.MODEL_ID  and  BUDGET_USER.MODEL_ID = ? and BUDGET_USER.STRUCTURE_ELEMENT_ID = ? and BUDGET_USER.USER_ID = USR.USER_ID";
    protected static final String SQL_DELETE_BATCH = "delete from BUDGET_USER where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? ";
    public static final String SQL_BULK_GET_ALL = " from BUDGET_USER where 1=1 and BUDGET_USER.MODEL_ID = ? order by  BUDGET_USER.MODEL_ID ,BUDGET_USER.STRUCTURE_ELEMENT_ID ,BUDGET_USER.USER_ID";
    protected static final String SQL_GET_ALL = " from BUDGET_USER where    MODEL_ID = ? ";
    private static String SQL_BUDGET_DETAILS_FOR_USER_SELECT = "select\n a.MODEL_ID\n,a.MODEL_VIS_ID\n,a.BUDGET_HIERARCHY_ID\n,a.BUDGET_CYCLE_ID\n,a.CYCLE_DESC\n,a.STRUCTURE_ELEMENT_ID\n,NVL(a.DISPLAY_NAME, a.VIS_ID)\n,a.STRUC_DESC\n,a.DEPTH\n,a.levelend\n,a.leaf\n,a.lowestleveldate\n,a.leafoverride\n,b.STATE\n,b.SUBMITABLE\n,b.REJECTABLE\n,b.USER_ID\n,cursor\n(\n select\n  innerstate.STATE\n ,innerstrucelem.STRUCTURE_ELEMENT_ID\n ,NVL(innerstrucelem.DISPLAY_NAME, innerstrucelem.VIS_ID)\n ,innerstrucelem.DESCRIPTION\n ,\n (\n     select\n      count(*)\n     from\n      BUDGET_USER innerinnerusr, MODEL iim\n     where\n         a.MODEL_ID                              = innerinnerusr.MODEL_ID\n         and iim.MODEL_ID                        = innerinnerusr.MODEL_ID\n         and innerstrucelem.STRUCTURE_ID         = iim.BUDGET_HIERARCHY_ID\n         and innerstrucelem.STRUCTURE_ELEMENT_ID = innerinnerusr.STRUCTURE_ELEMENT_ID\n         and innerinnerusr.USER_ID               = ?  ) usercount\n ,\n (\n     select\n      count(*)\n     from\n      BUDGET_USER innerinnerusr, MODEL iim\n     where\n         a.MODEL_ID                              = innerinnerusr.MODEL_ID\n         and iim.MODEL_ID                        = innerinnerusr.MODEL_ID\n         and innerstrucelem.STRUCTURE_ID         = iim.BUDGET_HIERARCHY_ID\n         and innerstrucelem.STRUCTURE_ELEMENT_ID = innerinnerusr.STRUCTURE_ELEMENT_ID\n         and innerinnerusr.USER_ID              != ?\n ) otherusercount\n ,innerstate.SUBMITABLE\n ,innerstate.REJECTABLE\n ,innerstate.USER_ID\n ,innerstrucelem.leaf\n ,(     select         innerdate.planned_end_date\n     from\n         budget_cycle innerbc,\n         level_date innerdate,\n         structure_element se2\n     where\n         a.BUDGET_CYCLE_ID = innerbc.BUDGET_CYCLE_ID\n         and innerbc.budget_cycle_id = innerdate.budget_cycle_id \n         and innerstrucelem.STRUCTURE_ID = se2.structure_id \n         and innerstrucelem.STRUCTURE_element_id = se2.STRUCTURE_element_id\n         and innerdate.depth = se2.DEPTH\n ) childLevelDate from\n  STRUCTURE_ELEMENT  innerstrucelem\n ,BUDGET_STATE       innerstate\n where\n     a.BUDGET_HIERARCHY_ID               = innerstrucelem.STRUCTURE_ID\n and a.STRUCTURE_ELEMENT_ID              = innerstrucelem.PARENT_ID\n and a.BUDGET_CYCLE_ID                   = innerstate.BUDGET_CYCLE_ID(+)\n and innerstrucelem.STRUCTURE_ELEMENT_ID = innerstate.STRUCTURE_ELEMENT_ID(+)\n order by innerstrucelem.CHILD_INDEX\n) children\n,cursor (\nselect distinct\n         bi.BUDGET_INSTRUCTION_ID ,bi.VIS_ID\nfrom\n         BUDGET_INSTRUCTION bi\n         ,BDGT_INSTR_ASSGNMNT bia\nwhere\n             bia.BUDGET_CYCLE_ID  = a.BUDGET_CYCLE_ID\n         and bia.BUDGET_INSTRUCTION_ID = bi.BUDGET_INSTRUCTION_ID\n ) cycle_bi\n,cursor (\nselect distinct\n    bi.BUDGET_INSTRUCTION_ID ,bi.VIS_ID\nfrom\n BUDGET_INSTRUCTION     bi\n,BDGT_INSTR_ASSGNMNT    bia\n,STRUCTURE_ELEMENT      se1\nwhere\n    bia.BUDGET_INSTRUCTION_ID = bi.BUDGET_INSTRUCTION_ID\nand bia.BUDGET_LOCATION_HIER_ID = a.BUDGET_HIERARCHY_ID\nand se1.STRUCTURE_ID = bia.BUDGET_LOCATION_HIER_ID\nand se1.STRUCTURE_ELEMENT_ID = bia.BUDGET_LOCATION_ELEMENT_ID\nand (\n        (\n            bia.SELECT_CHILDREN <> 'Y'\n        and bia.BUDGET_LOCATION_ELEMENT_ID  = a.STRUCTURE_ELEMENT_ID\n        )\n    or\n        (\n            bia.SELECT_CHILDREN = 'Y'\n        and a.STRUCTURE_ELEMENT_ID in\n            (\n                select se2.STRUCTURE_ELEMENT_ID from STRUCTURE_ELEMENT se2\n                where\n                    se2.STRUCTURE_ID = se1.STRUCTURE_ID\n                and se2.POSITION between se1.POSITION and se1.END_POSITION\n            )\n        )\n    )\n) location_bi\n,a.locationusercount\n,a.\"CATEGORY\"\nfrom\n(\n select\n  model.MODEL_ID, model.VIS_ID model_vis_id, budget_hierarchy_id\n ,cycle.BUDGET_CYCLE_ID, cycle.DESCRIPTION cycle_desc\n ,strucelem.STRUCTURE_ELEMENT_ID\n ,strucelem.DISPLAY_NAME,strucelem.VIS_ID, strucelem.DESCRIPTION struc_desc, strucelem.CHILD_INDEX\n ,strucelem.DEPTH depth\n ,strucelem.POSITION\n ,strucelem.leaf leaf\n ,levelDate.planned_end_date levelend\n ,cycle.leaf_override leafoverride\n ,cycle.\"CATEGORY\"\n ,(  select \n         planned_end_date \n     from \n         level_date \n     where \n         depth = (select max(depth) from level_date where budget_cycle_id = cycle.BUDGET_CYCLE_ID) \n         and budget_cycle_id = cycle.BUDGET_CYCLE_ID) lowestleveldate\n ,(\n     select\n         count(*)\n     from\n         BUDGET_USER usr\n     where\n         model.MODEL_ID = usr.MODEL_ID\n         and strucelem.STRUCTURE_ID = BUDGET_HIERARCHY_ID\n         and strucelem.STRUCTURE_ELEMENT_ID = usr.STRUCTURE_ELEMENT_ID\n         and usr.USER_ID = ?\n ) locationusercount\n from\n  MODEL              model\n ,BUDGET_CYCLE       cycle\n ,Level_Date         levelDate\n ,STRUCTURE_ELEMENT  strucelem\n";

    // private static String SQL_BUDGET_DETAILS_FOR_USER_WHERE_1 =
    // " ,BUDGET_USER        usr\nwhere\n     usr.USER_ID                 = ?\n and usr.MODEL_ID                = model.MODEL_ID\n and model.MODEL_ID              = cycle.MODEL_ID\n and cycle.budget_cycle_id       = levelDate.budget_cycle_id\n and levelDate.depth             = strucelem.depth and strucelem.STRUCTURE_ID      = model.BUDGET_HIERARCHY_ID\n and usr.STRUCTURE_ELEMENT_ID    = strucelem.STRUCTURE_ELEMENT_ID\n and cycle.STATUS                = 1\n";
    private static String SQL_BUDGET_DETAILS_FOR_USER_WHERE_1 = " where\n     model.MODEL_ID              = cycle.MODEL_ID\n and cycle.budget_cycle_id       = levelDate.budget_cycle_id\n and levelDate.depth             = strucelem.depth and strucelem.STRUCTURE_ID      = model.BUDGET_HIERARCHY_ID\n and cycle.STATUS                = 1\n and strucelem.depth                = 0\n";

    private static String SQL_BUDGET_DETAILS_FOR_USER_WHERE_2 = " where\n     cycle.BUDGET_CYCLE_ID           = ?\n and cycle.budget_cycle_id           = levelDate.budget_cycle_id\n and levelDate.depth                 = strucelem.depth and cycle.MODEL_ID                  = model.MODEL_ID\n and strucelem.STRUCTURE_ID          = model.BUDGET_HIERARCHY_ID\n and strucelem.STRUCTURE_ELEMENT_ID  = ?\n and cycle.STATUS                    = 1\n";

    private static String SQL_HIERARACHY_FOR_BUDGET_CYCLE = "SELECT DISTINCT * FROM (\n\tSELECT structure_element_id, 'N' as full_rights \n\tFROM structure_element START WITH structure_element_id in \n\t\t( \n\t\t\tSELECT bu.structure_element_id \n\t\t\tFROM budget_USER bu JOIN budget_cycle bc \n\t\t\tON bu.model_id = bc.model_id \n\t\t\tWHERE bu.user_id = ? AND bc.budget_cycle_id = ? \n\t\t) \n\tCONNECT BY structure_element_id = PRIOR parent_id \n) \nWHERE structure_element_id not in \n\t(\n\t\tSELECT bu.structure_element_id \n\t\tFROM budget_USER bu JOIN budget_cycle bc \n\t\tON bu.model_id = bc.model_id \n\t\tWHERE bu.user_id = ? AND bc.budget_cycle_id = ? \n\t) \nUNION ALL \n\tSELECT bu.structure_element_id, 'Y' as full_rights \n\tFROM budget_USER bu JOIN budget_cycle bc \n\tON bu.model_id = bc.model_id \n\tWHERE bu.user_id = ? AND bc.budget_cycle_id = ?";

    private static String SQL_BUDGET_DETAILS_FOR_USER_FINAL = ") a\n, BUDGET_STATE b\n where\n a.BUDGET_CYCLE_ID           = b.BUDGET_CYCLE_ID(+)\nand  a.STRUCTURE_ELEMENT_ID  = b.STRUCTURE_ELEMENT_ID(+)\norder by\n a.MODEL_VIS_ID\n,a.CYCLE_DESC\n,a.VIS_ID\n";

    private static String SQL_BUDGET_DETAILS_FOR_USER_FINAL_2 = ") a\n, BUDGET_STATE b, DICTIONARY d\n where\n a.BUDGET_CYCLE_ID           = b.BUDGET_CYCLE_ID(+)\n and a.STRUCTURE_ELEMENT_ID  = b.STRUCTURE_ELEMENT_ID(+)\n and a.model_id = ? and a.\"CATEGORY\" = d.VALUE \norder by\n d.ROW_INDEX, a.MODEL_VIS_ID\n,a.CYCLE_DESC\n,a.VIS_ID\n";
    protected static final String SQL_BUDGET_USERS = "select distinct c.user_id, c.name, c.full_name, c.E_MAIL_ADDRESS from budget_user a ,budget_cycle b ,usr c where b.budget_cycle_id = ? and b.model_id = a.model_id and a.user_id = c.user_id and a.structure_element_id in (";
    protected static final String SQL_BUDGET_USERS_NODE_DOWN = "select distinct c.user_id, c.name, c.full_name, c.E_MAIL_ADDRESS from budget_user a ,budget_cycle b ,usr c ,( select inner_struc.structure_element_id, inner_struc.vis_id, inner_struc.parent_id from structure_element inner_struc where structure_id = ? start with inner_struc.structure_element_id = ? connect by prior inner_struc.structure_element_id = inner_struc.parent_id ) d where b.budget_cycle_id = ? and b.model_id = a.model_id and a.structure_element_id = d.structure_element_id and a.user_id = c.user_id ";
    private static final String SQL_BUDGET_USERS_APPROVER_NODE_UP = "select distinct    c.user_id, c.name, c.full_name, c.E_MAIL_ADDRESS from     budget_user a     ,budget_cycle b    ,usr c     ,(        select             inner_struc.structure_id, inner_struc.structure_element_id, inner_struc.vis_id, inner_struc.parent_id, inner_struc.depth        from            structure_element inner_struc        where            STRUCTURE_ID = ? start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID    ) d    ,(         select             max (dd.depth) only_depth        from            budget_user aa            ,budget_cycle bb            ,usr cc            ,(                select                     inner_struc.structure_id, inner_struc.structure_element_id, inner_struc.vis_id, inner_struc.parent_id, inner_struc.depth                from                    structure_element inner_struc                where                    STRUCTURE_ID = ? start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID            ) dd            ,(                select                     depth_check.depth                 from                     structure_element depth_check                where                    STRUCTURE_ID = ? and STRUCTURE_ELEMENT_ID = ?            ) ee        where            bb.budget_cycle_id = ?            and bb.model_id = aa.model_id            and aa.structure_element_id = dd.structure_element_id            and dd.depth < ee.depth    ) e    where        b.budget_cycle_id = ?        and b.model_id = a.model_id        and a.structure_element_id = d.structure_element_id        and d.depth = e.only_depth        and a.user_id = c.user_id";
    private static final String PICKER_DIMENSION_SELECT_RA = " select mdr.DIMENSION_SEQ_NUM seq, d.DIMENSION_ID, d.vis_id, d.description, d.type, cursor ( select h.HIERARCHY_ID, h.VIS_ID, h.description, cursor ( select s.structure_id, s.structure_element_id, s.vis_id, s.description,  s.leaf,  s.cal_elem_type,  s.position from  structure_element s where  H.HIERARCHY_ID = S.STRUCTURE_ID and s.structure_element_id in (select structure_element_id from budget_user bu where bu.model_id = m.model_id and bu.user_id = ?) order by s.position  )se from HIERARCHY h where H.DIMENSION_ID = d.DIMENSION_ID and h.HIERARCHY_ID = m.BUDGET_HIERARCHY_ID ) hierarchy from dimension d, model_dimension_rel mdr, model m where m.model_id = ? and mdr.model_id = m.model_id and mdr.DIMENSION_ID = d.DIMENSION_ID and mdr.DIMENSION_SEQ_NUM = 0";
    private static final String PICKER_DIMENSION_SELECT_RA_SPECIFIC_ADMIN = "select  mdr.DIMENSION_SEQ_NUM as SEQ\n        ,d.DIMENSION_ID\n        ,d.VIS_ID\n        ,d.DESCRIPTION\n        ,d.TYPE\n        ,cursor\n        (\n        select  h.HIERARCHY_ID\n                ,h.VIS_ID\n                ,h.DESCRIPTION\n                ,cursor\n                (\n                select  s.STRUCTURE_ID\n                        ,s.STRUCTURE_ELEMENT_ID\n                        ,s.VIS_ID\n                        ,s.DESCRIPTION\n                        ,s.LEAF\n                        ,s.CAL_ELEM_TYPE\n                        ,s.POSITION\n                from    STRUCTURE_ELEMENT s\n                where   h.HIERARCHY_ID = s.STRUCTURE_ID\n                and     s.STRUCTURE_ELEMENT_ID in ( ${structureElements} )\n                )se\n        from    HIERARCHY h\n        where   h.DIMENSION_ID = d.DIMENSION_ID\n        and     h.HIERARCHY_ID = m.BUDGET_HIERARCHY_ID\n        ) hierarchy\nfrom    DIMENSION d\n        ,MODEL_DIMENSION_REL mdr\n        ,MODEL m\nwhere   m.MODEL_ID = ?\nand     mdr.MODEL_ID = m.MODEL_ID\nand     mdr.DIMENSION_ID = d.DIMENSION_ID\nand     mdr.DIMENSION_SEQ_NUM = 0";
    private static final String PICKER_DIMENSION_SELECT_RA_SPECIFIC = "select  mdr.DIMENSION_SEQ_NUM as SEQ\n        ,d.DIMENSION_ID\n        ,d.VIS_ID\n        ,d.DESCRIPTION\n        ,d.TYPE\n        ,cursor\n        (\n        select  h.HIERARCHY_ID\n                ,h.VIS_ID\n                ,h.DESCRIPTION\n                ,cursor\n                (\n                select  s.STRUCTURE_ID\n                        ,s.STRUCTURE_ELEMENT_ID\n                        ,s.VIS_ID\n                        ,s.DESCRIPTION\n                        ,s.LEAF\n                        ,s.CAL_ELEM_TYPE\n                        ,s.POSITION\n                from    STRUCTURE_ELEMENT s\n                where   h.HIERARCHY_ID = s.STRUCTURE_ID\n                and     exists\n                        (\n                        select  bu.STRUCTURE_ELEMENT_ID\n                        from    BUDGET_USER bu\n                                ,\n                                (\n                                select  se.STRUCTURE_ELEMENT_ID\n                                from    (\n                                        select  STRUCTURE_ID,STRUCTURE_ELEMENT_ID\n                                                ,POSITION\n                                        from    STRUCTURE_ELEMENT\n                                        where   STRUCTURE_ELEMENT_ID in ( ${structureElements} )\n                               ) getorig\n                        join   STRUCTURE_ELEMENT se\n                               on (   se.STRUCTURE_ID  = getorig.STRUCTURE_ID\n                                  and se.POSITION     <= getorig.POSITION\n                                  and se.END_POSITION >= getorig.POSITION)\n                                  ) sp\n                        where   bu.MODEL_ID = m.MODEL_ID\n                        and     bu.USER_ID = ?\n                        and     bu.STRUCTURE_ELEMENT_ID = sp.STRUCTURE_ELEMENT_ID\n                        )\n                and     s.STRUCTURE_ELEMENT_ID in ( ${structureElements} )\n                )se\n        from    HIERARCHY h\n        where   h.DIMENSION_ID = d.DIMENSION_ID\n        and     h.HIERARCHY_ID = m.BUDGET_HIERARCHY_ID\n        ) hierarchy\nfrom    DIMENSION d\n        ,MODEL_DIMENSION_REL mdr\n        ,MODEL m\nwhere   m.MODEL_ID = ?\nand     mdr.MODEL_ID = m.MODEL_ID\nand     mdr.DIMENSION_ID = d.DIMENSION_ID\nand     mdr.DIMENSION_SEQ_NUM = 0";
    private static final String PICKER_DIMENSION_SELECT_RA_REST = " select   mdr.DIMENSION_SEQ_NUM seq,  d.DIMENSION_ID,  d.vis_id,  d.description,  d.type,  cursor  (  select  h.HIERARCHY_ID,   h.VIS_ID,   h.description,  cursor  (  select   s.structure_id, s.structure_element_id,  s.vis_id,  s.description,  s.leaf,  s.cal_elem_type,  s.position  from   structure_element s  where   H.DIMENSION_ID = d.DIMENSION_ID  and H.HIERARCHY_ID = S.STRUCTURE_ID  and S.PARENT_ID = 0  )se  from   HIERARCHY h  where  H.DIMENSION_ID = d.DIMENSION_ID  ) hierarchy  from   dimension d,  model_dimension_rel mdr,  model m  where  m.model_id = ?   and mdr.model_id = m.model_id  and mdr.DIMENSION_ID = d.DIMENSION_ID  and mdr.DIMENSION_SEQ_NUM > 0 order by mdr.DIMENSION_SEQ_NUM";
    private static final String TIDY_BUDGET_USERS_SQL = "delete  from budget_user  where (model_id, structure_element_id) in  \t   ( select model_id, structure_element_id  \t\t from budget_user  \t\t where model_id = ? and  \t\t\t   structure_element_id not in ( select structure_element_id  \t\t \t\t\t\t\t\t\t\t\t from structure_element  \t\t\t\t\t\t\t\t\t\t\t where structure_id = ? ) )";
    private static final String IS_RESPONSIBILITY_AREA_READ_ONLY_SQL = "select read_only  from budget_user  where user_id = ? and structure_element_id in         ( select structure_element_id  \t\t    from (select structure_element_id, parent_id from structure_element where structure_id = ?)           start with structure_element_id = ? connect by prior parent_id = structure_element_id \t    )";
    protected static final String SQL_RESPAREA_NODEUP_ASSIGNMENTS = "            SELECT distinct               a.user_id,               a.name,               a.full_name,               d.vis_id,               f.description , f.vis_id ,            CASE b.READ_ONLY           WHEN NULL THEN 'W'            WHEN 'N' THEN 'W'                  WHEN ' ' THEN 'W'              ELSE 'R'            END as READ            FROM                usr a,               budget_user b,               structure_element c,               model d,              (                select                     inner_struc.structure_element_id, inner_struc.vis_id, inner_struc.parent_id                from                     structure_element inner_struc                where                     STRUCTURE_ID = ? start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID                    ) e, (                  select description, vis_id from structure_element where structure_element_id = ?) f             WHERE                a.user_id = b.user_id                AND b.structure_element_id = c.structure_element_id               and b.structure_element_id = e.structure_element_id               AND b.model_id = d.model_id              ORDER BY                 a.user_id asc";
    protected static final String SQL_BUDGET_USERS_LINKED = "select distinct c.user_id, c.name, c.full_name, c.E_MAIL_ADDRESS, a.structure_element_id from budget_user a ,budget_cycle b ,usr c where b.budget_cycle_id = ? and b.model_id = a.model_id and a.user_id = c.user_id and a.structure_element_id in (";
    protected BudgetUserEVO mDetails;

    public BudgetUserDAO(Connection connection) {
        super(connection);
    }

    public BudgetUserDAO() {
    }

    public BudgetUserDAO(DataSource ds) {
        super(ds);
    }

    protected BudgetUserPK getPK() {
        return mDetails.getPK();
    }

    public void setDetails(BudgetUserEVO details) {
        mDetails = details.deepClone();
    }

    private BudgetUserEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
        int col = 1;
        BudgetUserEVO evo = new BudgetUserEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), resultSet_.getInt(col++));

        evo.setUpdatedByUserId(resultSet_.getInt(col++));
        evo.setUpdatedTime(resultSet_.getTimestamp(col++));
        evo.setCreatedTime(resultSet_.getTimestamp(col++));
        return evo;
    }

    private int putEvoKeysToJdbc(BudgetUserEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
        int col = startCol_;
        stmt_.setInt(col++, evo_.getModelId());
        stmt_.setInt(col++, evo_.getStructureElementId());
        stmt_.setInt(col++, evo_.getUserId());
        return col;
    }

    private int putEvoDataToJdbc(BudgetUserEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
        int col = startCol_;
        if (evo_.getReadOnly())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, " ");
        stmt_.setInt(col++, evo_.getCurrencyId());
        stmt_.setInt(col++, evo_.getVersionNum());
        stmt_.setInt(col++, evo_.getUpdatedByUserId());
        stmt_.setTimestamp(col++, evo_.getUpdatedTime());
        stmt_.setTimestamp(col++, evo_.getCreatedTime());
        return col;
    }

    protected void doLoad(BudgetUserPK pk) throws ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select BUDGET_USER.MODEL_ID,BUDGET_USER.STRUCTURE_ELEMENT_ID,BUDGET_USER.USER_ID,BUDGET_USER.READ_ONLY,BUDGET_USER.CURRENCY_ID,BUDGET_USER.VERSION_NUM,BUDGET_USER.UPDATED_BY_USER_ID,BUDGET_USER.UPDATED_TIME,BUDGET_USER.CREATED_TIME from BUDGET_USER where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? ");

            int col = 1;
            stmt.setInt(col++, pk.getModelId());
            stmt.setInt(col++, pk.getStructureElementId());
            stmt.setInt(col++, pk.getUserId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
            }

            mDetails = getEvoFromJdbc(resultSet);
            if (mDetails.isModified())
                _log.info("doLoad", mDetails);
        } catch (SQLException sqle) {
            throw handleSQLException(pk, "select BUDGET_USER.MODEL_ID,BUDGET_USER.STRUCTURE_ELEMENT_ID,BUDGET_USER.USER_ID,BUDGET_USER.READ_ONLY,BUDGET_USER.CURRENCY_ID,BUDGET_USER.VERSION_NUM,BUDGET_USER.UPDATED_BY_USER_ID,BUDGET_USER.UPDATED_TIME,BUDGET_USER.CREATED_TIME from BUDGET_USER where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? ", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("doLoad", pk);
        }
    }

    protected void doCreate() throws DuplicateNameValidationException, ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        try {
            mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
            mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
            stmt = getConnection().prepareStatement("insert into BUDGET_USER ( MODEL_ID,STRUCTURE_ELEMENT_ID,USER_ID,READ_ONLY,CURRENCY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)");

            int col = 1;
            col = putEvoKeysToJdbc(mDetails, stmt, col);
            col = putEvoDataToJdbc(mDetails, stmt, col);

            int resultCount = stmt.executeUpdate();
            if (resultCount != 1) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
            }

            mDetails.reset();
        } catch (SQLException sqle) {
            throw handleSQLException(mDetails.getPK(), "insert into BUDGET_USER ( MODEL_ID,STRUCTURE_ELEMENT_ID,USER_ID,READ_ONLY,CURRENCY_ID,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?)", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("doCreate", mDetails.toString());
        }
    }

    protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        PreparedStatement stmt = null;

        boolean mainChanged = mDetails.isModified();
        boolean dependantChanged = false;
        try {
            if ((mainChanged) || (dependantChanged)) {
                mDetails.setVersionNum(mDetails.getVersionNum() + 1);

                mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
                stmt = getConnection().prepareStatement("update BUDGET_USER set READ_ONLY = ?,CURRENCY_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? AND VERSION_NUM = ?");

                int col = 1;
                col = putEvoDataToJdbc(mDetails, stmt, col);
                col = putEvoKeysToJdbc(mDetails, stmt, col);

                stmt.setInt(col++, mDetails.getVersionNum() - 1);

                int resultCount = stmt.executeUpdate();

                if (resultCount == 0) {
                    checkVersionNum();
                }
                if (resultCount != 1) {
                    throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
                }

                mDetails.reset();
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "update BUDGET_USER set READ_ONLY = ?,CURRENCY_ID = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? AND VERSION_NUM = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if ((timer != null) && ((mainChanged) || (dependantChanged)))
                timer.logDebug("store", new StringBuilder().append(mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
        }
    }

    private void checkVersionNum() throws VersionValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select VERSION_NUM from BUDGET_USER where MODEL_ID = ?,STRUCTURE_ELEMENT_ID = ?,USER_ID = ?");

            int col = 1;
            stmt.setInt(col++, mDetails.getModelId());
            stmt.setInt(col++, mDetails.getStructureElementId());
            stmt.setInt(col++, mDetails.getUserId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkVersionNum: select of ").append(getPK()).append(" not found").toString());
            }

            col = 1;
            int dbVersionNumber = resultSet.getInt(col++);
            if (mDetails.getVersionNum() - 1 != dbVersionNumber) {
                throw new VersionValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" expected:").append(mDetails.getVersionNum() - 1).append(" found:").append(dbVersionNumber).toString());
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "select VERSION_NUM from BUDGET_USER where MODEL_ID = ?,STRUCTURE_ELEMENT_ID = ?,USER_ID = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeResultSet(resultSet);

            if (timer != null)
                timer.logDebug("checkVersionNum", mDetails.getPK());
        }
    }

    public AllBudgetUsersELO getAllBudgetUsers() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllBudgetUsersELO results = new AllBudgetUsersELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_BUDGET_USERS);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));

                String textModel = resultSet.getString(col++);

                BudgetUserPK pkBudgetUser = new BudgetUserPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));

                String textBudgetUser = resultSet.getString(col++);

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                BudgetUserCK ckBudgetUser = new BudgetUserCK(pkModel, pkBudgetUser);

                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);

                BudgetUserRefImpl erBudgetUser = new BudgetUserRefImpl(ckBudgetUser, textBudgetUser);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                results.add(erBudgetUser, erModel, erUser);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_BUDGET_USERS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllBudgetUsers", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public CheckUserAccessToModelELO getCheckUserAccessToModel(int param1, int param2) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        CheckUserAccessToModelELO results = new CheckUserAccessToModelELO();
        try {
            stmt = getConnection().prepareStatement(SQL_CHECK_USER_ACCESS_TO_MODEL);
            int col = 1;
            stmt.setInt(col++, param1);
            stmt.setInt(col++, param2);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));

                String textModel = resultSet.getString(col++);

                BudgetUserPK pkBudgetUser = new BudgetUserPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));

                String textBudgetUser = "";

                BudgetUserCK ckBudgetUser = new BudgetUserCK(pkModel, pkBudgetUser);

                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);

                BudgetUserRefImpl erBudgetUser = new BudgetUserRefImpl(ckBudgetUser, textBudgetUser);

                String col1 = resultSet.getString(col++);
                if (resultSet.wasNull()) {
                    col1 = "";
                }

                results.add(erBudgetUser, erModel, col1.equals("Y"));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_CHECK_USER_ACCESS_TO_MODEL, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getCheckUserAccessToModel", new StringBuilder().append(" UserId=").append(param1).append(",ModelId=").append(param2).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public CheckUserAccessELO getCheckUserAccess(int param1, int param2) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        CheckUserAccessELO results = new CheckUserAccessELO();
        try {
            stmt = getConnection().prepareStatement(SQL_CHECK_USER_ACCESS);
            int col = 1;
            stmt.setInt(col++, param1);
            stmt.setInt(col++, param2);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));

                String textModel = resultSet.getString(col++);

                BudgetUserPK pkBudgetUser = new BudgetUserPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));

                String textBudgetUser = "";

                BudgetUserCK ckBudgetUser = new BudgetUserCK(pkModel, pkBudgetUser);

                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);

                BudgetUserRefImpl erBudgetUser = new BudgetUserRefImpl(ckBudgetUser, textBudgetUser);

                String col1 = resultSet.getString(col++);
                if (resultSet.wasNull()) {
                    col1 = "";
                }

                results.add(erBudgetUser, erModel, col1.equals("Y"));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_CHECK_USER_ACCESS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getCheckUserAccess", new StringBuilder().append(" UserId=").append(param1).append(",StructureElementId=").append(param2).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public CheckUserELO getCheckUser(int param1) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        CheckUserELO results = new CheckUserELO();
        try {
            stmt = getConnection().prepareStatement(SQL_CHECK_USER);
            int col = 1;
            stmt.setInt(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));

                String textModel = resultSet.getString(col++);

                BudgetUserPK pkBudgetUser = new BudgetUserPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));

                String textBudgetUser = "";

                BudgetUserCK ckBudgetUser = new BudgetUserCK(pkModel, pkBudgetUser);

                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);

                BudgetUserRefImpl erBudgetUser = new BudgetUserRefImpl(ckBudgetUser, textBudgetUser);

                int col1 = resultSet.getInt(col++);

                results.add(erBudgetUser, erModel, col1);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_CHECK_USER, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getCheckUser", new StringBuilder().append(" UserId=").append(param1).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public BudgetUsersForNodeELO getBudgetUsersForNode(int param1, int param2) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetUsersForNodeELO results = new BudgetUsersForNodeELO();
        try {
            stmt = getConnection().prepareStatement(SQL_BUDGET_USERS_FOR_NODE);
            int col = 1;
            stmt.setInt(col++, param1);
            stmt.setInt(col++, param2);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));

                String textModel = resultSet.getString(col++);

                BudgetUserPK pkBudgetUser = new BudgetUserPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));

                String textBudgetUser = "";

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                BudgetUserCK ckBudgetUser = new BudgetUserCK(pkModel, pkBudgetUser);

                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);

                BudgetUserRefImpl erBudgetUser = new BudgetUserRefImpl(ckBudgetUser, textBudgetUser);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                int col1 = resultSet.getInt(col++);
                String col2 = resultSet.getString(col++);
                String col3 = resultSet.getString(col++);
                String col4 = resultSet.getString(col++);

                results.add(erBudgetUser, erModel, erUser, col1, col2, col3, col4);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_BUDGET_USERS_FOR_NODE, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetUsersForNode", new StringBuilder().append(" ModelId=").append(param1).append(",StructureElementId=").append(param2).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public NodesForUserAndCycleELO getNodesForUserAndCycle(int param1, int param2) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        NodesForUserAndCycleELO results = new NodesForUserAndCycleELO();
        try {
            stmt = getConnection().prepareStatement(SQL_NODES_FOR_USER_AND_CYCLE);
            int col = 1;
            stmt.setInt(col++, param1);
            stmt.setInt(col++, param2);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                results.add(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getInt(col++), resultSet.getString(col++).equals("Y"));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_NODES_FOR_USER_AND_CYCLE, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getNodesForUserAndCycle", new StringBuilder().append(" BudgetCycleId=").append(param1).append(",UserId=").append(param2).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public NodesForUserAndModelELO getNodesForUserAndModel(int param1, int param2) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        NodesForUserAndModelELO results = new NodesForUserAndModelELO();
        try {
            stmt = getConnection().prepareStatement(SQL_NODES_FOR_USER_AND_MODEL);
            int col = 1;
            stmt.setInt(col++, param1);
            stmt.setInt(col++, param2);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                ModelPK pkModel = new ModelPK(resultSet.getInt(col++));

                String textModel = resultSet.getString(col++);

                BudgetUserPK pkBudgetUser = new BudgetUserPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));

                String textBudgetUser = "";

                StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));

                String textStructureElement = resultSet.getString(col++);

                BudgetUserCK ckBudgetUser = new BudgetUserCK(pkModel, pkBudgetUser);

                ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);

                BudgetUserRefImpl erBudgetUser = new BudgetUserRefImpl(ckBudgetUser, textBudgetUser);

                StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);

                int col1 = resultSet.getInt(col++);
                String col2 = resultSet.getString(col++);
                String col3 = resultSet.getString(col++);

                results.add(erBudgetUser, erModel, erStructureElement, col1, col2, col3);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_NODES_FOR_USER_AND_MODEL, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getNodesForUserAndModel", new StringBuilder().append(" ModelId=").append(param1).append(",UserId=").append(param2).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public UsersForModelAndElementELO getUsersForModelAndElement(int param1, int param2) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UsersForModelAndElementELO results = new UsersForModelAndElementELO();
        try {
            stmt = getConnection().prepareStatement(SQL_USERS_FOR_MODEL_AND_ELEMENT);
            int col = 1;
            stmt.setInt(col++, param1);
            stmt.setInt(col++, param2);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                results.add(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_USERS_FOR_MODEL_AND_ELEMENT, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getUsersForModelAndElement", new StringBuilder().append(" ModelId=").append(param1).append(",StructureElementId=").append(param2).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public boolean update(Map items) throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        if (items == null) {
            return false;
        }
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement deleteStmt = null;

        boolean somethingChanged = false;
        try {
            Iterator iter2 = new ArrayList(items.values()).iterator();
            while (iter2.hasNext()) {
                mDetails = ((BudgetUserEVO) iter2.next());

                if (mDetails.deletePending()) {
                    somethingChanged = true;

                    if (deleteStmt == null) {
                        deleteStmt = getConnection().prepareStatement("delete from BUDGET_USER where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? ");
                    }

                    int col = 1;
                    deleteStmt.setInt(col++, mDetails.getModelId());
                    deleteStmt.setInt(col++, mDetails.getStructureElementId());
                    deleteStmt.setInt(col++, mDetails.getUserId());

                    if (_log.isDebugEnabled()) {
                        _log.debug("update", new StringBuilder().append("BudgetUser deleting ModelId=").append(mDetails.getModelId()).append(",StructureElementId=").append(mDetails.getStructureElementId()).append(",UserId=").append(mDetails.getUserId()).toString());
                    }

                    deleteStmt.addBatch();

                    items.remove(mDetails.getPK());
                }

            }

            if (deleteStmt != null) {
                Timer timer2 = _log.isDebugEnabled() ? new Timer(_log) : null;

                deleteStmt.executeBatch();

                if (timer2 != null) {
                    timer2.logDebug("update", "delete batch");
                }
            }

            Iterator iter1 = items.values().iterator();
            while (iter1.hasNext()) {
                mDetails = ((BudgetUserEVO) iter1.next());

                if (mDetails.insertPending()) {
                    somethingChanged = true;
                    doCreate();
                } else if (mDetails.isModified()) {
                    somethingChanged = true;
                    doStore();
                }

            }

            return somethingChanged;
        } catch (SQLException sqle) {
            throw handleSQLException("delete from BUDGET_USER where    MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? ", sqle);
        } finally {
            if (deleteStmt != null) {
                closeStatement(deleteStmt);
                closeConnection();
            }

            mDetails = null;

            if ((somethingChanged) && (timer != null))
                timer.logDebug("update", "collection");
        }
    }

    public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        int itemCount = 0;

        Collection theseItems = new ArrayList();
        owningEVO.setBudgetUsers(theseItems);
        owningEVO.setBudgetUsersAllItemsLoaded(true);
        try {
            stmt = getConnection().prepareStatement("select BUDGET_USER.MODEL_ID,BUDGET_USER.STRUCTURE_ELEMENT_ID,BUDGET_USER.USER_ID,BUDGET_USER.READ_ONLY,BUDGET_USER.CURRENCY_ID,BUDGET_USER.VERSION_NUM,BUDGET_USER.UPDATED_BY_USER_ID,BUDGET_USER.UPDATED_TIME,BUDGET_USER.CREATED_TIME from BUDGET_USER where 1=1 and BUDGET_USER.MODEL_ID = ? order by  BUDGET_USER.MODEL_ID ,BUDGET_USER.STRUCTURE_ELEMENT_ID ,BUDGET_USER.USER_ID");

            int col = 1;
            stmt.setInt(col++, entityPK.getModelId());

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                itemCount++;
                mDetails = getEvoFromJdbc(resultSet);

                theseItems.add(mDetails);
            }

            if (timer != null) {
                timer.logDebug("bulkGetAll", new StringBuilder().append("items=").append(itemCount).toString());
            }
        } catch (SQLException sqle) {
            throw handleSQLException("select BUDGET_USER.MODEL_ID,BUDGET_USER.STRUCTURE_ELEMENT_ID,BUDGET_USER.USER_ID,BUDGET_USER.READ_ONLY,BUDGET_USER.CURRENCY_ID,BUDGET_USER.VERSION_NUM,BUDGET_USER.UPDATED_BY_USER_ID,BUDGET_USER.UPDATED_TIME,BUDGET_USER.CREATED_TIME from BUDGET_USER where 1=1 and BUDGET_USER.MODEL_ID = ? order by  BUDGET_USER.MODEL_ID ,BUDGET_USER.STRUCTURE_ELEMENT_ID ,BUDGET_USER.USER_ID", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            mDetails = null;
        }
    }

    public Collection getAll(int selectModelId, String dependants, Collection currentList) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        ArrayList items = new ArrayList();
        try {
            stmt = getConnection().prepareStatement("select BUDGET_USER.MODEL_ID,BUDGET_USER.STRUCTURE_ELEMENT_ID,BUDGET_USER.USER_ID,BUDGET_USER.READ_ONLY,BUDGET_USER.CURRENCY_ID,BUDGET_USER.VERSION_NUM,BUDGET_USER.UPDATED_BY_USER_ID,BUDGET_USER.UPDATED_TIME,BUDGET_USER.CREATED_TIME from BUDGET_USER where    MODEL_ID = ? ");

            int col = 1;
            stmt.setInt(col++, selectModelId);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                mDetails = getEvoFromJdbc(resultSet);

                items.add(mDetails);
            }

            if (currentList != null) {
                ListIterator iter = items.listIterator();
                BudgetUserEVO currentEVO = null;
                BudgetUserEVO newEVO = null;
                while (iter.hasNext()) {
                    newEVO = (BudgetUserEVO) iter.next();
                    Iterator iter2 = currentList.iterator();
                    while (iter2.hasNext()) {
                        currentEVO = (BudgetUserEVO) iter2.next();
                        if (currentEVO.getPK().equals(newEVO.getPK())) {
                            iter.set(currentEVO);
                        }
                    }

                }

                Iterator iter2 = currentList.iterator();
                while (iter2.hasNext()) {
                    currentEVO = (BudgetUserEVO) iter2.next();
                    if (currentEVO.insertPending()) {
                        items.add(currentEVO);
                    }
                }
            }
            mDetails = null;
        } catch (SQLException sqle) {
            throw handleSQLException("select BUDGET_USER.MODEL_ID,BUDGET_USER.STRUCTURE_ELEMENT_ID,BUDGET_USER.USER_ID,BUDGET_USER.READ_ONLY,BUDGET_USER.CURRENCY_ID,BUDGET_USER.VERSION_NUM,BUDGET_USER.UPDATED_BY_USER_ID,BUDGET_USER.UPDATED_TIME,BUDGET_USER.CREATED_TIME from BUDGET_USER where    MODEL_ID = ? ", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null) {
                timer.logDebug("getAll", new StringBuilder().append(" ModelId=").append(selectModelId).append(" items=").append(items.size()).toString());
            }

        }

        return items;
    }

    public BudgetUserEVO getDetails(ModelCK paramCK, String dependants) throws ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        if (mDetails == null) {
            doLoad(((BudgetUserCK) paramCK).getBudgetUserPK());
        } else if (!mDetails.getPK().equals(((BudgetUserCK) paramCK).getBudgetUserPK())) {
            doLoad(((BudgetUserCK) paramCK).getBudgetUserPK());
        }

        BudgetUserEVO details = new BudgetUserEVO();
        details = mDetails.deepClone();

        if (timer != null) {
            timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
        }
        return details;
    }

    public BudgetUserEVO getDetails(ModelCK paramCK, BudgetUserEVO paramEVO, String dependants) throws ValidationException {
        BudgetUserEVO savedEVO = mDetails;
        mDetails = paramEVO;
        BudgetUserEVO newEVO = getDetails(paramCK, dependants);
        mDetails = savedEVO;
        return newEVO;
    }

    public BudgetUserEVO getDetails(String dependants) throws ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        BudgetUserEVO details = mDetails.deepClone();

        if (timer != null) {
            timer.logDebug("getDetails", new StringBuilder().append(mDetails.getPK()).append(" ").append(dependants).toString());
        }
        return details;
    }

    public String getEntityName() {
        return "BudgetUser";
    }

    public BudgetUserRefImpl getRef(BudgetUserPK paramBudgetUserPK) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID from BUDGET_USER\njoin MODEL on (1=1\nand BUDGET_USER.MODEL_ID = MODEL.MODEL_ID\n) where 1=1 and BUDGET_USER.MODEL_ID = ? and BUDGET_USER.STRUCTURE_ELEMENT_ID = ? and BUDGET_USER.USER_ID = ?");
            int col = 1;
            stmt.setInt(col++, paramBudgetUserPK.getModelId());
            stmt.setInt(col++, paramBudgetUserPK.getStructureElementId());
            stmt.setInt(col++, paramBudgetUserPK.getUserId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getRef ").append(paramBudgetUserPK).append(" not found").toString());
            }
            col = 2;
            ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));

            String textBudgetUser = "";
            BudgetUserCK ckBudgetUser = new BudgetUserCK(newModelPK, paramBudgetUserPK);

            return new BudgetUserRefImpl(ckBudgetUser, textBudgetUser);
        } catch (SQLException sqle) {
            throw handleSQLException(paramBudgetUserPK, "select 0,MODEL.MODEL_ID from BUDGET_USER\njoin MODEL on (1=1\nand BUDGET_USER.MODEL_ID = MODEL.MODEL_ID\n) where 1=1 and BUDGET_USER.MODEL_ID = ? and BUDGET_USER.STRUCTURE_ELEMENT_ID = ? and BUDGET_USER.USER_ID = ?", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("getRef", paramBudgetUserPK);
        }
    }

    public BudgetDetailsForUserELO getBudgetDetailsForUser(int userId, int modelId) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        BudgetDetailsForUserELO results = new BudgetDetailsForUserELO();
        try {
            int col = 1;

            String sql = new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_1).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL_2).toString();
            stmt = getConnection().prepareStatement(sql);
            // stmt.setInt(col++, userId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, modelId);

            resultSet = stmt.executeQuery();
            processBudgetDetails(resultSet, results);
        } catch (SQLException sqle) {
            _log.debug(new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_1).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL_2).toString());

            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" BudgetDetailsForUser").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" BudgetDetailsForUser").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetDetailsForUser", new StringBuilder().append(" ModelId=").append(userId).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public BudgetDetailsForUserELO getBudgetDetailsForUser(int userId, boolean detailedSelection, int paramBudgetLocationId, int paramCycleId) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        BudgetDetailsForUserELO results = new BudgetDetailsForUserELO();
        try {
            Map<Integer, String> hierarchy = getHierarchyForBudgetCycle(userId, paramCycleId);

            int col = 1;

            if (!detailedSelection) {
                String sql = new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_1).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL).toString();
                stmt = getConnection().prepareStatement(sql);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
            } else {
                String sql = new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_2).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL).toString();
                stmt = getConnection().prepareStatement(sql);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
                stmt.setInt(col++, paramCycleId);
                stmt.setInt(col++, paramBudgetLocationId);
            }

            resultSet = stmt.executeQuery();
            processBudgetDetailsForHierarchy(resultSet, results, hierarchy);
        } catch (SQLException sqle) {
            if (!detailedSelection) {
                _log.debug(new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_1).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL).toString());
            } else {
                _log.debug(new StringBuilder().append(SQL_BUDGET_DETAILS_FOR_USER_SELECT).append(SQL_BUDGET_DETAILS_FOR_USER_WHERE_2).append(SQL_BUDGET_DETAILS_FOR_USER_FINAL).toString());
            }

            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" BudgetDetailsForUser").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" BudgetDetailsForUser").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetDetailsForUser", new StringBuilder().append(" ModelId=").append(userId).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    private Map<Integer, String> getHierarchyForBudgetCycle(int userId, int paramCycleId) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            int col = 1;

            String sql = new StringBuilder().append(SQL_HIERARACHY_FOR_BUDGET_CYCLE).toString();
            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, paramCycleId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, paramCycleId);
            stmt.setInt(col++, userId);
            stmt.setInt(col++, paramCycleId);

            resultSet = stmt.executeQuery();

            Map<Integer, String> result = new HashMap<Integer, String>();

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
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" BudgetDetailsForUser").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" BudgetDetailsForUser").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
    }

    private BudgetDetailsForUserELO processBudgetDetailsForHierarchy(ResultSet resultSet, BudgetDetailsForUserELO results, Map<Integer, String> hierarchy) throws Exception {
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

    private BudgetDetailsForUserELO processBudgetDetails(ResultSet resultSet, BudgetDetailsForUserELO results) throws Exception {
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

    public BudgetDetailsForUserELO getBudgetDetailsForUser(int userId) {
        return getBudgetDetailsForUser(userId, false, 0, 0);
    }

    public EntityList getBudgetUserDetails(int bcId, int[] structureElementId) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetUserDetailsELO results = new BudgetUserDetailsELO();
        if (structureElementId == null) {
            return results;
        }
        try {
            int col = 1;
            StringBuffer sql = new StringBuffer("select distinct c.user_id, c.name, c.full_name, c.E_MAIL_ADDRESS from budget_user a ,budget_cycle b ,usr c where b.budget_cycle_id = ? and b.model_id = a.model_id and a.user_id = c.user_id and a.structure_element_id in (");
            for (int i = 0; i < structureElementId.length; i++) {
                if (i != 0) {
                    sql.append(" , ");
                }
                sql.append(" ? ");
            }
            sql.append(")");

            stmt = getConnection().prepareStatement(sql.toString());
            stmt.setInt(col++, bcId);

            for (int i = 0; i < structureElementId.length; i++) {
                stmt.setInt(col++, structureElementId[i]);
            }
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 1;

                int userlId = resultSet.getInt(col++);
                String userName = resultSet.getString(col++);
                String userFullName = resultSet.getString(col++);
                String email = resultSet.getString(col++);

                results.add(userlId, userName, userFullName, email);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetUserDetails").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetUserDetails").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetUserDetails", new StringBuilder().append(" budgetCycleId=").append(bcId).toString());
        }
        return results;
    }

    public EntityList getBudgetUserDetailsNodeDown(int bcId, int structureElementId, int structureId) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetUserDetailsELO results = new BudgetUserDetailsELO();
        try {
            int col = 1;

            stmt = getConnection()
                    .prepareStatement(
                            "select distinct c.user_id, c.name, c.full_name, c.E_MAIL_ADDRESS from budget_user a ,budget_cycle b ,usr c ,( select inner_struc.structure_element_id, inner_struc.vis_id, inner_struc.parent_id from structure_element inner_struc where structure_id = ? start with inner_struc.structure_element_id = ? connect by prior inner_struc.structure_element_id = inner_struc.parent_id ) d where b.budget_cycle_id = ? and b.model_id = a.model_id and a.structure_element_id = d.structure_element_id and a.user_id = c.user_id ");
            stmt.setInt(col++, structureId);
            stmt.setInt(col++, structureElementId);
            stmt.setInt(col++, bcId);

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 1;

                int userlId = resultSet.getInt(col++);
                String userName = resultSet.getString(col++);
                String userFullName = resultSet.getString(col++);
                String email = resultSet.getString(col++);

                results.add(userlId, userName, userFullName, email);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetUserDetails").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetUserDetails").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetUserDetails", new StringBuilder().append(" budgetCycleId=").append(bcId).toString());
        }
        return results;
    }

    public EntityList getBudgetUserAuthDetailsNodeUp(int bcId, int structureElementId, int structureId) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        BudgetUserDetailsELO results = new BudgetUserDetailsELO();
        try {
            int col = 1;

            stmt = getConnection()
                    .prepareStatement(
                            "select distinct    c.user_id, c.name, c.full_name, c.E_MAIL_ADDRESS from     budget_user a     ,budget_cycle b    ,usr c     ,(        select             inner_struc.structure_id, inner_struc.structure_element_id, inner_struc.vis_id, inner_struc.parent_id, inner_struc.depth        from            structure_element inner_struc        where            STRUCTURE_ID = ? start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID    ) d    ,(         select             max (dd.depth) only_depth        from            budget_user aa            ,budget_cycle bb            ,usr cc            ,(                select                     inner_struc.structure_id, inner_struc.structure_element_id, inner_struc.vis_id, inner_struc.parent_id, inner_struc.depth                from                    structure_element inner_struc                where                    STRUCTURE_ID = ? start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID            ) dd            ,(                select                     depth_check.depth                 from                     structure_element depth_check                where                    STRUCTURE_ID = ? and STRUCTURE_ELEMENT_ID = ?            ) ee        where            bb.budget_cycle_id = ?            and bb.model_id = aa.model_id            and aa.structure_element_id = dd.structure_element_id            and dd.depth < ee.depth    ) e    where        b.budget_cycle_id = ?        and b.model_id = a.model_id        and a.structure_element_id = d.structure_element_id        and d.depth = e.only_depth        and a.user_id = c.user_id");
            stmt.setInt(col++, structureId);
            stmt.setInt(col++, structureElementId);
            stmt.setInt(col++, structureId);
            stmt.setInt(col++, structureElementId);
            stmt.setInt(col++, structureId);
            stmt.setInt(col++, structureElementId);

            stmt.setInt(col++, bcId);
            stmt.setInt(col++, bcId);

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 1;

                int userlId = resultSet.getInt(col++);
                String userName = resultSet.getString(col++);
                String userFullName = resultSet.getString(col++);
                String email = resultSet.getString(col++);

                results.add(userlId, userName, userFullName, email);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetUserAuthDetailsNodeUp").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetUserAuthDetailsNodeUp").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetUserAuthDetailsNodeUp", new StringBuilder().append(" budgetCycleId=").append(bcId).toString());
        }
        return results;
    }

    public EntityList getPickerStartUpData(int modelId, int[] structureElementIDs, int userId, boolean admin) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;

        StartupDetailsForPickerELO results = new StartupDetailsForPickerELO();
        try {
            int col = 1;
            if ((structureElementIDs == null) || (structureElementIDs.length == 0)) {
                _log.debug(
                        "getPickerStartUpData",
                        "\n select mdr.DIMENSION_SEQ_NUM seq, d.DIMENSION_ID, d.vis_id, d.description, d.type, cursor ( select h.HIERARCHY_ID, h.VIS_ID, h.description, cursor ( select s.structure_id, s.structure_element_id, s.vis_id, s.description,  s.leaf,  s.cal_elem_type,  s.position from  structure_element s where  H.HIERARCHY_ID = S.STRUCTURE_ID and s.structure_element_id in (select structure_element_id from budget_user bu where bu.model_id = m.model_id and bu.user_id = ?) order by s.position  )se from HIERARCHY h where H.DIMENSION_ID = d.DIMENSION_ID and h.HIERARCHY_ID = m.BUDGET_HIERARCHY_ID ) hierarchy from dimension d, model_dimension_rel mdr, model m where m.model_id = ? and mdr.model_id = m.model_id and mdr.DIMENSION_ID = d.DIMENSION_ID and mdr.DIMENSION_SEQ_NUM = 0");
                _log.debug("getPickerStartUpData", new StringBuilder().append("userId=").append(userId).toString());
                _log.debug("getPickerStartUpData", new StringBuilder().append("modelId=").append(modelId).toString());
                stmt = getConnection()
                        .prepareStatement(
                                " select mdr.DIMENSION_SEQ_NUM seq, d.DIMENSION_ID, d.vis_id, d.description, d.type, cursor ( select h.HIERARCHY_ID, h.VIS_ID, h.description, cursor ( select s.structure_id, s.structure_element_id, s.vis_id, s.description,  s.leaf,  s.cal_elem_type,  s.position from  structure_element s where  H.HIERARCHY_ID = S.STRUCTURE_ID and s.structure_element_id in (select structure_element_id from budget_user bu where bu.model_id = m.model_id and bu.user_id = ?) order by s.position  )se from HIERARCHY h where H.DIMENSION_ID = d.DIMENSION_ID and h.HIERARCHY_ID = m.BUDGET_HIERARCHY_ID ) hierarchy from dimension d, model_dimension_rel mdr, model m where m.model_id = ? and mdr.model_id = m.model_id and mdr.DIMENSION_ID = d.DIMENSION_ID and mdr.DIMENSION_SEQ_NUM = 0");
                stmt.setInt(col++, userId);
                stmt.setInt(col++, modelId);
            } else {
                StringBuilder structureElementBind = new StringBuilder();
                for (int id: structureElementIDs) {
                    if (structureElementBind.length() > 0) {
                        structureElementBind.append(",");
                    }
                    structureElementBind.append("?");
                }
                SqlBuilder builder;
                if (admin)
                    builder = new SqlBuilder(
                            new String[] { "select  mdr.DIMENSION_SEQ_NUM as SEQ\n        ,d.DIMENSION_ID\n        ,d.VIS_ID\n        ,d.DESCRIPTION\n        ,d.TYPE\n        ,cursor\n        (\n        select  h.HIERARCHY_ID\n                ,h.VIS_ID\n                ,h.DESCRIPTION\n                ,cursor\n                (\n                select  s.STRUCTURE_ID\n                        ,s.STRUCTURE_ELEMENT_ID\n                        ,s.VIS_ID\n                        ,s.DESCRIPTION\n                        ,s.LEAF\n                        ,s.CAL_ELEM_TYPE\n                        ,s.POSITION\n                from    STRUCTURE_ELEMENT s\n                where   h.HIERARCHY_ID = s.STRUCTURE_ID\n                and     s.STRUCTURE_ELEMENT_ID in ( ${structureElements} )\n                )se\n        from    HIERARCHY h\n        where   h.DIMENSION_ID = d.DIMENSION_ID\n        and     h.HIERARCHY_ID = m.BUDGET_HIERARCHY_ID\n        ) hierarchy\nfrom    DIMENSION d\n        ,MODEL_DIMENSION_REL mdr\n        ,MODEL m\nwhere   m.MODEL_ID = ?\nand     mdr.MODEL_ID = m.MODEL_ID\nand     mdr.DIMENSION_ID = d.DIMENSION_ID\nand     mdr.DIMENSION_SEQ_NUM = 0" });
                else {
                    builder = new SqlBuilder(
                            new String[] { "select  mdr.DIMENSION_SEQ_NUM as SEQ\n        ,d.DIMENSION_ID\n        ,d.VIS_ID\n        ,d.DESCRIPTION\n        ,d.TYPE\n        ,cursor\n        (\n        select  h.HIERARCHY_ID\n                ,h.VIS_ID\n                ,h.DESCRIPTION\n                ,cursor\n                (\n                select  s.STRUCTURE_ID\n                        ,s.STRUCTURE_ELEMENT_ID\n                        ,s.VIS_ID\n                        ,s.DESCRIPTION\n                        ,s.LEAF\n                        ,s.CAL_ELEM_TYPE\n                        ,s.POSITION\n                from    STRUCTURE_ELEMENT s\n                where   h.HIERARCHY_ID = s.STRUCTURE_ID\n                and     exists\n                        (\n                        select  bu.STRUCTURE_ELEMENT_ID\n                        from    BUDGET_USER bu\n                                ,\n                                (\n                                select  se.STRUCTURE_ELEMENT_ID\n                                from    (\n                                        select  STRUCTURE_ID,STRUCTURE_ELEMENT_ID\n                                                ,POSITION\n                                        from    STRUCTURE_ELEMENT\n                                        where   STRUCTURE_ELEMENT_ID in ( ${structureElements} )\n                               ) getorig\n                        join   STRUCTURE_ELEMENT se\n                               on (   se.STRUCTURE_ID  = getorig.STRUCTURE_ID\n                                  and se.POSITION     <= getorig.POSITION\n                                  and se.END_POSITION >= getorig.POSITION)\n                                  ) sp\n                        where   bu.MODEL_ID = m.MODEL_ID\n                        and     bu.USER_ID = ?\n                        and     bu.STRUCTURE_ELEMENT_ID = sp.STRUCTURE_ELEMENT_ID\n                        )\n                and     s.STRUCTURE_ELEMENT_ID in ( ${structureElements} )\n                )se\n        from    HIERARCHY h\n        where   h.DIMENSION_ID = d.DIMENSION_ID\n        and     h.HIERARCHY_ID = m.BUDGET_HIERARCHY_ID\n        ) hierarchy\nfrom    DIMENSION d\n        ,MODEL_DIMENSION_REL mdr\n        ,MODEL m\nwhere   m.MODEL_ID = ?\nand     mdr.MODEL_ID = m.MODEL_ID\nand     mdr.DIMENSION_ID = d.DIMENSION_ID\nand     mdr.DIMENSION_SEQ_NUM = 0" });
                }
                builder.substitute(new String[] { "${structureElements}", structureElementBind.toString() });
                _log.debug("getPickerStartUpData", new StringBuilder().append("\n").append(builder.toString()).toString());
                _log.debug("getPickerStartUpData", new StringBuilder().append("modelId=").append(modelId).toString());
                _log.debug("getPickerStartUpData", new StringBuilder().append("structureElementId=").append(Arrays.toString(structureElementIDs)).toString());
                _log.debug("getPickerStartUpData", new StringBuilder().append("userId=").append(userId).toString());
                _log.debug("getPickerStartUpData", new StringBuilder().append("admin=").append(admin).toString());
                stmt = getConnection().prepareStatement(builder.toString());

                if (!admin) {
                    for (int id: structureElementIDs)
                        stmt.setInt(col++, id);
                    stmt.setInt(col++, userId);
                }
                for (int id: structureElementIDs)
                    stmt.setInt(col++, id);
                stmt.setInt(col++, modelId);
            }

            processStartUpResults(results, stmt);
            closeStatement(stmt);
            closeConnection();

            col = 1;
            _log.debug(
                    "getPickerStartUpData",
                    "\n select   mdr.DIMENSION_SEQ_NUM seq,  d.DIMENSION_ID,  d.vis_id,  d.description,  d.type,  cursor  (  select  h.HIERARCHY_ID,   h.VIS_ID,   h.description,  cursor  (  select   s.structure_id, s.structure_element_id,  s.vis_id,  s.description,  s.leaf,  s.cal_elem_type,  s.position  from   structure_element s  where   H.DIMENSION_ID = d.DIMENSION_ID  and H.HIERARCHY_ID = S.STRUCTURE_ID  and S.PARENT_ID = 0  )se  from   HIERARCHY h  where  H.DIMENSION_ID = d.DIMENSION_ID  ) hierarchy  from   dimension d,  model_dimension_rel mdr,  model m  where  m.model_id = ?   and mdr.model_id = m.model_id  and mdr.DIMENSION_ID = d.DIMENSION_ID  and mdr.DIMENSION_SEQ_NUM > 0 order by mdr.DIMENSION_SEQ_NUM");
            _log.debug("getPickerStartUpData", new StringBuilder().append("modelId=").append(modelId).toString());
            stmt = getConnection()
                    .prepareStatement(
                            " select   mdr.DIMENSION_SEQ_NUM seq,  d.DIMENSION_ID,  d.vis_id,  d.description,  d.type,  cursor  (  select  h.HIERARCHY_ID,   h.VIS_ID,   h.description,  cursor  (  select   s.structure_id, s.structure_element_id,  s.vis_id,  s.description,  s.leaf,  s.cal_elem_type,  s.position  from   structure_element s  where   H.DIMENSION_ID = d.DIMENSION_ID  and H.HIERARCHY_ID = S.STRUCTURE_ID  and S.PARENT_ID = 0  )se  from   HIERARCHY h  where  H.DIMENSION_ID = d.DIMENSION_ID  ) hierarchy  from   dimension d,  model_dimension_rel mdr,  model m  where  m.model_id = ?   and mdr.model_id = m.model_id  and mdr.DIMENSION_ID = d.DIMENSION_ID  and mdr.DIMENSION_SEQ_NUM > 0 order by mdr.DIMENSION_SEQ_NUM");
            stmt.setInt(col++, modelId);

            processStartUpResults(results, stmt);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getPickerStartUpData").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getPickerStartUpData").toString(), e);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            if (structureElementIDs != null)
                timer.logDebug("getPickerStartUpData", new StringBuilder().append("  modelId=").append(modelId).append(" structureElementId=").append(Arrays.toString(structureElementIDs)).toString());
            else {
                timer.logDebug("getPickerStartUpData", new StringBuilder().append("  modelId=").append(modelId).toString());
            }
        }
        return results;
    }

    private EntityList processStartUpResults(StartupDetailsForPickerELO elo, PreparedStatement stmt) throws SQLException {
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        ResultSet resultSet3 = null;

        resultSet1 = stmt.executeQuery();
        while (resultSet1.next()) {
            int col = 1;

            resultSet1.getInt(col++);
            int id = resultSet1.getInt(col++);
            String visId = resultSet1.getString(col++);
            String description = resultSet1.getString(col++);
            int type = resultSet1.getInt(col++);
            resultSet2 = (ResultSet) resultSet1.getObject(col++);

            StartupDetailsForPickerLevel2ELO hier = new StartupDetailsForPickerLevel2ELO();

            while (resultSet2.next()) {
                int col2 = 1;

                int hid = resultSet2.getInt(col2++);
                String hvisid = resultSet2.getString(col2++);
                String hdesc = resultSet2.getString(col2++);
                resultSet3 = (ResultSet) resultSet2.getObject(col2++);

                StartupDetailsForPickerLevel3ELO se = new StartupDetailsForPickerLevel3ELO();
                while (resultSet3.next()) {
                    int col3 = 1;

                    int sid = resultSet3.getInt(col3++);
                    int seid = resultSet3.getInt(col3++);
                    String sevisid = resultSet3.getString(col3++);
                    String sedesc = resultSet3.getString(col3++);
                    boolean leaf = resultSet3.getString(col3++).equals("Y");
                    Integer calElemType = Integer.valueOf(resultSet3.getInt(col3++));
                    int position = resultSet3.getInt(col3++);

                    StructureElementPK sePk = new StructureElementPK(sid, seid);
                    StructureElementRefImpl seref = new StructureElementRefImpl(sePk, sevisid);

                    se.add(seref, sid, seid, sevisid, sedesc, leaf, calElemType.intValue(), position);
                }
                resultSet3.close();
                hier.add(hid, hvisid, hdesc, se);
            }
            resultSet2.close();
            elo.add(id, visId, description, hier, new DimensionRefImpl(new DimensionPK(id), visId, type));
        }
        resultSet1.close();

        return elo;
    }

    public void tidyOrphanBudgetUsers(int modelId, int structureId) {
        PreparedStatement ps = null;
        try {
            ps = getConnection().prepareStatement("delete  from budget_user  where (model_id, structure_element_id) in  \t   ( select model_id, structure_element_id  \t\t from budget_user  \t\t where model_id = ? and  \t\t\t   structure_element_id not in ( select structure_element_id  \t\t \t\t\t\t\t\t\t\t\t from structure_element  \t\t\t\t\t\t\t\t\t\t\t where structure_id = ? ) )");
            ps.setInt(1, modelId);
            ps.setInt(2, structureId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw handleSQLException("Failed to tidyOrphanedBudgetUsers", e);
        } finally {
            closeStatement(ps);
            closeConnection();
        }
    }

    public boolean isResponsibilityAreaReadonly(int userId, int structureId, int raElementId) {
        boolean result = false;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = getConnection().prepareStatement("select read_only  from budget_user  where user_id = ? and structure_element_id in         ( select structure_element_id  \t\t    from (select structure_element_id, parent_id from structure_element where structure_id = ?)           start with structure_element_id = ? connect by prior parent_id = structure_element_id \t    )");
            ps.setInt(1, userId);
            ps.setInt(2, structureId);
            ps.setInt(3, raElementId);

            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String flag = resultSet.getString(1);
                result = "Y".equals(flag);
            }
        } catch (SQLException e) {
            throw handleSQLException("Failed to isResponsibilityAreaReadonly", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(ps);
            closeConnection();
        }

        return result;
    }

    public EntityList getNodeAndUpUserAssignments(int structureElementId, int structureId) {
        getConnection();

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        AllUsersAssignmentsELO elo = new AllUsersAssignmentsELO();
        try {
            stmt = mConnection
                    .prepareStatement("            SELECT distinct               a.user_id,               a.name,               a.full_name,               d.vis_id,               f.description , f.vis_id ,            CASE b.READ_ONLY           WHEN NULL THEN 'W'            WHEN 'N' THEN 'W'                  WHEN ' ' THEN 'W'              ELSE 'R'            END as READ            FROM                usr a,               budget_user b,               structure_element c,               model d,              (                select                     inner_struc.structure_element_id, inner_struc.vis_id, inner_struc.parent_id                from                     structure_element inner_struc                where                     STRUCTURE_ID = ? start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID                    ) e, (                  select description, vis_id from structure_element where structure_element_id = ?) f             WHERE                a.user_id = b.user_id                AND b.structure_element_id = c.structure_element_id               and b.structure_element_id = e.structure_element_id               AND b.model_id = d.model_id              ORDER BY                 a.user_id asc");
            int col = 1;
            stmt.setInt(col++, structureId);
            stmt.setInt(col++, structureElementId);
            stmt.setInt(col++, structureElementId);

            resultSet = stmt.executeQuery();
            String read = "";

            while (resultSet.next()) {
                col = 1;
                String userId = resultSet.getString(col++);
                String name = resultSet.getString(col++);
                String fullname = resultSet.getString(col++);
                String modelid = resultSet.getString(col++);
                String description = resultSet.getString(col++);
                String elemid = resultSet.getString(col++);
                read = resultSet.getString(col++);
                elo.add(name, fullname, modelid, elemid, description, read);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new RuntimeException(getEntityName(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        return elo;
    }

    public EntityList getLinkedBudgetUserDetails(int bcId, int[] structureElementId) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        LinkedBudgetUserDetailsELO results = new LinkedBudgetUserDetailsELO();
        if (structureElementId == null) {
            return results;
        }
        try {
            int col = 1;
            StringBuffer sql = new StringBuffer("select distinct c.user_id, c.name, c.full_name, c.E_MAIL_ADDRESS, a.structure_element_id from budget_user a ,budget_cycle b ,usr c where b.budget_cycle_id = ? and b.model_id = a.model_id and a.user_id = c.user_id and a.structure_element_id in (");
            for (int i = 0; i < structureElementId.length; i++) {
                if (i != 0) {
                    sql.append(" , ");
                }
                sql.append(" ? ");
            }
            sql.append(") order by c.user_id asc");

            stmt = getConnection().prepareStatement(sql.toString());
            stmt.setInt(col++, bcId);

            for (int i = 0; i < structureElementId.length; i++)
                stmt.setInt(col++, structureElementId[i]);
            resultSet = stmt.executeQuery();

            int prevUser = 0;
            String prevUserName = null;
            String prevFullName = null;
            String prevEmail = null;
            List prevSes = null;
            boolean firstTime = true;
            while (resultSet.next()) {
                col = 1;

                int userlId = resultSet.getInt(col++);
                if (prevUser != userlId) {
                    if (!firstTime)
                        results.add(prevUser, prevUserName, prevFullName, prevEmail, prevSes);
                    else {
                        firstTime = false;
                    }
                    prevUser = userlId;
                    prevUserName = resultSet.getString(col++);
                    prevFullName = resultSet.getString(col++);
                    prevEmail = resultSet.getString(col++);
                    prevSes = new ArrayList();
                    int val = resultSet.getInt(col++);
                    prevSes.add(String.valueOf(val));
                } else {
                    col += 3;
                    int val = resultSet.getInt(col++);
                    prevSes.add(String.valueOf(val));
                }

            }

            results.add(prevUser, prevUserName, prevFullName, prevEmail, prevSes);
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetUserDetailsLinked").toString(), sqle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetUserDetailsLinked").toString(), e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getBudgetUserDetailsLinked", new StringBuilder().append(" budgetCycleId=").append(bcId).toString());
        }
        return results;
    }

    public EntityList getUserModelSecurity() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        String[] columnNames = { "User", "UserDisplay", "NumModels", "ModelSummary" };

        EntityListImpl results = new EntityListImpl(columnNames, new Object[0][columnNames.length]);

//        SqlBuilder sqlb = new SqlBuilder(new String[] { "with init as", "(", "select  distinct USER_ID, NAME, FULL_NAME, MODEL_ID, m.VIS_ID as MODEL_VIS_ID", "from    USR", "left", "join    BUDGET_USER using (USER_ID)", "left", "join    MODEL m using (MODEL_ID)", ")", ",init2 as", "(", "select  m.*", "        ,row_number() over (partition by USER_ID order by MODEL_VIS_ID) as SEQ", "        ,(select count(*) from init t where t.USER_ID = m.USER_ID and MODEL_VIS_ID is not null) as NUM_MODELS",
//                "from    init m", ")", "select  USER_ID, NAME, FULL_NAME", "        ,NUM_MODELS", "        ,WM_CONCAT(MODEL_VIS_ID)", "        ||", "        case when NUM_MODELS > 9 then '...' end", "        as MODEL_SUMMARY", "from    init2", "where   SEQ < 10", "group   by USER_ID, NAME, FULL_NAME, NUM_MODELS", "order   by NAME" });
        SqlBuilder sqlb = new SqlBuilder(new String[] { "with init as", "(", "select  distinct USER_ID, NAME, FULL_NAME, MODEL_ID, m.VIS_ID as MODEL_VIS_ID", "from    USR", "left", "join    BUDGET_USER using (USER_ID)", "left", "join    MODEL m using (MODEL_ID)", ")", ",init2 as", "(", "select  m.*", "        ,row_number() over (partition by USER_ID order by MODEL_VIS_ID) as SEQ", "        ,(select count(*) from init t where t.USER_ID = m.USER_ID and MODEL_VIS_ID is not null) as NUM_MODELS",
                "from    init m", ")", "select  USER_ID, NAME, FULL_NAME", "        ,NUM_MODELS", "        ,LISTAGG(MODEL_VIS_ID,',') WITHIN GROUP (ORDER BY MODEL_VIS_ID)", "        ||", "        case when NUM_MODELS > 9 then '...' end", "        as MODEL_SUMMARY", "from    init2", "where   SEQ < 10", "group   by USER_ID, NAME, FULL_NAME, NUM_MODELS", "order   by NAME" });

        SqlExecutor sqle = new SqlExecutor("getUserModelSecurity", getDataSource(), sqlb, _log);
        ResultSet resultSet = sqle.getResultSet();
        try {
            while (resultSet.next()) {
                List row = new ArrayList();
                row.add(new UserRefImpl(new UserPK(resultSet.getInt("USER_ID")), resultSet.getString("NAME")));

                row.add(new StringBuilder().append(resultSet.getString("NAME")).append(" - ").append(resultSet.getString("FULL_NAME")).toString());
                row.add(Integer.valueOf(resultSet.getInt("NUM_MODELS")));
                row.add(resultSet.getString("MODEL_SUMMARY"));
                results.add(row);
            }
        } catch (SQLException e) {
            throw handleSQLException(sqlb.toString(), e);
        } finally {
            sqle.close();
        }

        if (timer != null) {
            timer.logDebug("getUserModelSecurity", new StringBuilder().append(" items=").append(results.getNumRows()).toString());
        }

        return results;
    }

    public List<UserModelElementAssignment> getRespAreaAccess(PrimaryKey pk) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        SqlBuilder sqlb = new SqlBuilder(new String[] { "select  m.MODEL_ID", "        ,m.VIS_ID as MODEL_VIS_ID", "        ,se.STRUCTURE_ID", "        ,se.STRUCTURE_ELEMENT_ID", "        ,se.VIS_ID as ELEM_VIS_ID", "        ,se.DESCRIPTION as ELEM_DESCRIPTION", "        ,u.USER_ID", "        ,u.NAME", "        ,u.FULL_NAME", "        ,bu.READ_ONLY", "from    MODEL m", "join    BUDGET_USER bu", "        on (bu.MODEL_ID = m.MODEL_ID)", "join    STRUCTURE_ELEMENT se",
                "        on (se.STRUCTURE_ID = m.BUDGET_HIERARCHY_ID", "            and se.STRUCTURE_ELEMENT_ID = bu.STRUCTURE_ELEMENT_ID)", "join    USR u on (u.USER_ID = bu.USER_ID)", "${where}", "order   by ${order}, se.POSITION" });

        SqlExecutor sqle = new SqlExecutor("getRespAreaAccess", getDataSource(), sqlb, _log);

        if (pk == null) {
            sqlb.substitute(new String[] { "${where}", null });
            sqlb.substitute(new String[] { "${order}", "m.VIS_ID, u.NAME" });
        } else if ((pk instanceof ModelPK)) {
            sqlb.substitute(new String[] { "${where}", "where   m.MODEL_ID = <modelId>" });
            sqlb.substitute(new String[] { "${order}", "u.NAME" });
            int modelId = ((ModelPK) pk).getModelId();
            sqle.addBindVariable("<modelId>", Integer.valueOf(modelId));
        } else {
            sqlb.substitute(new String[] { "${where}", "where   bu.USER_ID = <userId>" });
            sqlb.substitute(new String[] { "${order}", "m.VIS_ID" });
            int userId = ((UserPK) pk).getUserId();
            sqle.addBindVariable("<userId>", Integer.valueOf(userId));
        }
        ResultSet resultSet = sqle.getResultSet();
        List results = new ArrayList();
        try {
            while (resultSet.next()) {
                ModelRef model = new ModelRefImpl(new ModelPK(resultSet.getInt("MODEL_ID")), resultSet.getString("MODEL_VIS_ID"));

                StructureElementRef se = new StructureElementRefImpl(new StructureElementPK(resultSet.getInt("STRUCTURE_ID"), resultSet.getInt("STRUCTURE_ELEMENT_ID")), resultSet.getString("ELEM_VIS_ID"));

                String sedesc = resultSet.getString("ELEM_DESCRIPTION");
                UserRef user = new UserRefImpl(new UserPK(resultSet.getInt("USER_ID")), resultSet.getString("NAME"));

                String fullname = resultSet.getString("FULL_NAME");
                Boolean readonly = new Boolean(resultSet.getString("READ_ONLY").equals("Y"));

                UserModelElementAssignment ea = new UserModelElementAssignmentImpl(model, user, se, sedesc, readonly);
                results.add(ea);
            }
        } catch (SQLException e) {
            throw handleSQLException(sqlb.toString(), e);
        } finally {
            sqle.close();
        }

        if (timer != null) {
            timer.logDebug("getRespAreaAccess", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public EntityList getModelsAndRAHierarchies() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        String[] columnNames = { "Model", "ModelDescription", "Dimension", "DimensionDescription", "HierarchyId", "Hierarchy", "StructureElement", "StructureElementDescription" };

        EntityListImpl results = new EntityListImpl(columnNames, new Object[0][columnNames.length]);

        SqlBuilder sqlb = new SqlBuilder(new String[] { "select  m.MODEL_ID, m.VIS_ID as MODEL_VIS_ID, m.DESCRIPTION as MODEL_DESCRIPTION", "        ,h.HIERARCHY_ID, h.VIS_ID as HIERARCHY_VIS_ID", "        ,d.DIMENSION_ID, d.VIS_ID as DIMENSION_VIS_ID, d.DESCRIPTION as DIMENSION_DESCRIPTION", "        ,se.STRUCTURE_ID, se.STRUCTURE_ELEMENT_ID, se.VIS_ID as ELEM_VIS_ID, se.DESCRIPTION as ELEM_DESCRIPTION", "from    MODEL m", "join    HIERARCHY h",
                "        on (h.HIERARCHY_ID = m.BUDGET_HIERARCHY_ID)", "join    DIMENSION d", "        on (d.DIMENSION_ID = h.DIMENSION_ID)", "join    STRUCTURE_ELEMENT se", "        on (se.STRUCTURE_ID = h.HIERARCHY_ID", "            and se.PARENT_ID = 0)", "order   by m.VIS_ID" });

        SqlExecutor sqle = new SqlExecutor("getModelsAndRAHierarchies", getDataSource(), sqlb, _log);
        ResultSet resultSet = sqle.getResultSet();
        try {
            while (resultSet.next()) {
                List row = new ArrayList();

                row.add(new ModelRefImpl(new ModelPK(resultSet.getInt("MODEL_ID")), resultSet.getString("MODEL_VIS_ID")));

                row.add(resultSet.getString("MODEL_DESCRIPTION"));

                row.add(new DimensionRefImpl(new DimensionPK(resultSet.getInt("DIMENSION_ID")), resultSet.getString("DIMENSION_VIS_ID"), 2));

                row.add(resultSet.getString("DIMENSION_DESCRIPTION"));

                row.add(Integer.valueOf(resultSet.getInt("HIERARCHY_ID")));

                row.add(new HierarchyRefImpl(new HierarchyPK(resultSet.getInt("HIERARCHY_ID")), resultSet.getString("HIERARCHY_VIS_ID")));

                row.add(new StructureElementRefImpl(new StructureElementPK(resultSet.getInt("STRUCTURE_ID"), resultSet.getInt("STRUCTURE_ELEMENT_ID")), resultSet.getString("ELEM_VIS_ID")));

                row.add(resultSet.getString("ELEM_DESCRIPTION"));
                results.add(row);
            }
        } catch (SQLException e) {
            throw handleSQLException(sqlb.toString(), e);
        } finally {
            sqle.close();
        }

        if (timer != null) {
            timer.logDebug("getModelsAndRAHierarchies", new StringBuilder().append(" items=").append(results.getNumRows()).toString());
        }

        return results;
    }

    public void delete(UserRef user, ModelRef model, StructureElementRef se) {
        SqlBuilder sqlb = new SqlBuilder(new String[] { "delete from BUDGET_USER", "where  MODEL_ID = <modelId> ", "and    STRUCTURE_ELEMENT_ID = <seId> ", "and    USER_ID = <userId> " });

        SqlExecutor sqle = new SqlExecutor("delete", getDataSource(), sqlb, _log);
        sqle.addBindVariable("<modelId>", Integer.valueOf(((ModelPK) model.getPrimaryKey()).getModelId()));
        sqle.addBindVariable("<seId>", Integer.valueOf(((StructureElementPK) se.getPrimaryKey()).getStructureElementId()));
        sqle.addBindVariable("<userId>", Integer.valueOf(((UserPK) user.getPrimaryKey()).getUserId()));
        sqle.executeUpdate();
    }

    public void delete(UserRef user) {
        SqlBuilder sqlb = new SqlBuilder(new String[] { "delete from BUDGET_USER", "where  USER_ID = <userId> " });

        SqlExecutor sqle = new SqlExecutor("delete", getDataSource(), sqlb, _log);
        sqle.addBindVariable("<userId>", Integer.valueOf(((UserPK) user.getPrimaryKey()).getUserId()));
        sqle.executeUpdate();
    }

    public void delete(ModelRef model) {
        SqlBuilder sqlb = new SqlBuilder(new String[] { "delete from BUDGET_USER", "where  MODEL_ID = <modelId> " });

        SqlExecutor sqle = new SqlExecutor("delete", getDataSource(), sqlb, _log);
        sqle.addBindVariable("<modelId>", Integer.valueOf(((ModelPK) model.getPrimaryKey()).getModelId()));
        sqle.executeUpdate();
    }

    public void insert(UserRef user, ModelRef model, StructureElementRef se, Boolean ro) {
        SqlBuilder sqlb = new SqlBuilder(new String[] { "insert into BUDGET_USER", "(MODEL_ID, STRUCTURE_ELEMENT_ID, USER_ID, READ_ONLY, CURRENCY_ID, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME)", "values", "(", " <modelId>", ",<seId>", ",<userId>", ",<readOnly>", ",0,1,0,sysdate,sysdate", ")" });

        SqlExecutor sqle = new SqlExecutor("delete", getDataSource(), sqlb, _log);
        sqle.addBindVariable("<modelId>", Integer.valueOf(((ModelPK) model.getPrimaryKey()).getModelId()));
        sqle.addBindVariable("<seId>", Integer.valueOf(((StructureElementPK) se.getPrimaryKey()).getStructureElementId()));
        sqle.addBindVariable("<userId>", Integer.valueOf(((UserPK) user.getPrimaryKey()).getUserId()));
        sqle.addBindVariable("<readOnly>", ro.booleanValue() ? "Y" : " ");
        sqle.executeUpdate();
    }

    public void insert(List<Object[]> list) {
        PreparedStatement insertStmt = null;
        try {
            insertStmt = getConnection().prepareStatement("insert into BUDGET_USER (MODEL_ID, STRUCTURE_ELEMENT_ID, USER_ID, READ_ONLY, CURRENCY_ID, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME) values ( ?,?,?,?,0,1,0,sysdate,sysdate )");

            for (Object[] data: list) {
                int col = 1;
                insertStmt.setInt(col++, (Integer) data[0]);
                insertStmt.setInt(col++, (((StructureElementPK) ((StructureElementRef) data[2]).getPrimaryKey()).getStructureElementId()));
                insertStmt.setInt(col++, (Integer) data[1]);
                insertStmt.setString(col++, " ");
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        } catch (SQLException e) {
            throw handleSQLException("insert", e);
        } finally {
            closeStatement(insertStmt);
            closeConnection();
        }
    }

    public void insertAfterNewModel(Object[] data) {
        PreparedStatement insertStmt = null;
        try {
            insertStmt = getConnection().prepareStatement("insert into BUDGET_USER (MODEL_ID, STRUCTURE_ELEMENT_ID, USER_ID, READ_ONLY, CURRENCY_ID, VERSION_NUM, UPDATED_BY_USER_ID, UPDATED_TIME, CREATED_TIME) values ( ?,(select STRUCTURE_ELEMENT_ID from STRUCTURE_ELEMENT where STRUCTURE_ID=?),?,?,0,1,0,sysdate,sysdate )");
            int col = 1;
            insertStmt.setInt(col++, (Integer) data[0]);
            insertStmt.setInt(col++, (Integer) data[2]);
            insertStmt.setInt(col++, (Integer) data[1]);
            insertStmt.setString(col++, " ");

            insertStmt.execute();
        } catch (SQLException e) {
            throw handleSQLException("insert", e);
        } finally {
            closeStatement(insertStmt);
            closeConnection();
        }
    }

    public void delete(List<Object[]> list) {
        PreparedStatement deleteStmt = null;
        try {
            deleteStmt = getConnection().prepareStatement("delete from BUDGET_USER where  MODEL_ID = ? and STRUCTURE_ELEMENT_ID = ? and USER_ID = ? ");

            for (Object[] data: list) {
                int col = 1;
                deleteStmt.setInt(col++, (Integer) data[0]);
                deleteStmt.setInt(col++, (Integer) data[2]);
                deleteStmt.setInt(col++, (Integer) data[1]);
                deleteStmt.addBatch();
            }
            deleteStmt.executeBatch();
        } catch (SQLException e) {
            throw handleSQLException("delete", e);
        } finally {
            closeStatement(deleteStmt);
            closeConnection();
        }
    }

    public void update(UserRef user, ModelRef model, StructureElementRef se, Boolean ro) {
        SqlBuilder sqlb = new SqlBuilder(new String[] { "update  BUDGET_USER", "set     READ_ONLY = <readOnly>", "       ,UPDATED_TIME = sysdate", "where   MODEL_ID = <modelId> ", "and     STRUCTURE_ELEMENT_ID = <seId> ", "and     USER_ID = <userId> " });

        SqlExecutor sqle = new SqlExecutor("delete", getDataSource(), sqlb, _log);
        sqle.addBindVariable("<modelId>", Integer.valueOf(((ModelPK) model.getPrimaryKey()).getModelId()));
        sqle.addBindVariable("<seId>", Integer.valueOf(((StructureElementPK) se.getPrimaryKey()).getStructureElementId()));
        sqle.addBindVariable("<userId>", Integer.valueOf(((UserPK) user.getPrimaryKey()).getUserId()));
        sqle.addBindVariable("<readOnly>", ro.booleanValue() ? "Y" : " ");
        sqle.executeUpdate();
    }

    public boolean getUserReadOnlyAccess(int modelId, int structureElementId, int userId) throws ValidationException {
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet resultSet = null;
        boolean result;
        try {
            stmt = getConnection().prepareStatement("select READ_ONLY from BUDGET_USER where MODEL_ID = ? AND STRUCTURE_ELEMENT_ID = ? AND USER_ID = ? ");

            int col = 1;
            stmt.setInt(col++, modelId);
            stmt.setInt(col++, structureElementId);
            stmt.setInt(col++, userId);

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                result = false;
            } else {
                result = resultSet.getString(1).equals("Y") ? true : false;
            }
            if (!result) {
                // check parents of structure element
                ResultSet resultSet2 = null;

                stmt2 = getConnection().prepareStatement("select PARENT_ID from STRUCTURE_ELEMENT where STRUCTURE_ELEMENT_ID = ?");
                stmt2.setInt(1, structureElementId);
                resultSet2 = stmt2.executeQuery();

                if (resultSet2.next()) {
                    int parentId = resultSet2.getInt(1);
                    if (parentId != 0) {
                        result = getUserReadOnlyAccess(modelId, parentId, userId);
                    }
                } else {
                    throw handleSQLException("Error in SQL to check ReadOnly Access - getting structures", null);
                }
            }
        } catch (SQLException e) {
            throw handleSQLException("Error in SQL to check ReadOnly Access", e);
        } finally {
            closeStatement(stmt);
            closeStatement(stmt2);
            closeConnection();
        }
        return result;
    }

    public void deleteUserFromResponsibilityTable(int userId) {
        PreparedStatement stmt = null;
        try {
            stmt = this.getConnection().prepareCall("delete from BUDGET_USER where user_id = ?");
            stmt.setInt(1, userId);
            stmt.execute();

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    public void deleteUserFromFinanceFormTable(int userId) {
        PreparedStatement stmt = null;
        try {
            stmt = this.getConnection().prepareCall("delete from XML_FORM_USER_LINK where user_id = ?");
            stmt.setInt(1, userId);
            stmt.execute();

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    public void deleteUserRole(int userId) {
        PreparedStatement stmt = null;
        try {
            stmt = this.getConnection().prepareCall("delete from USER_ROLE where user_id = ?");
            stmt.setInt(1, userId);
            stmt.execute();

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    public void deleteUserFromDataEntryProfileTable(int userId) {
        PreparedStatement stmt = null;
        try {
            stmt = this.getConnection().prepareCall("delete from DATA_ENTRY_PROFILE where user_id = ?");
            stmt.setInt(1, userId);
            stmt.execute();

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    public void deleteUserFromInternalDestinationTable(int userId) {
        PreparedStatement stmt = null;
        try {
            stmt = this.getConnection().prepareCall("delete from INTERNAL_DESTINATION_USERS where user_id = ?");
            stmt.setInt(1, userId);
            stmt.execute();

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }
}