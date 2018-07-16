/*      */ package com.cedar.cp.ejb.impl.dimension;
/*      */ 
/*      */ import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.base.VersionValidationException;
/*      */ import com.cedar.cp.api.dimension.StructureElementKey;
/*      */ import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.EntityListImpl;
/*      */ import com.cedar.cp.dto.dataEntry.AvailableStructureElementELO;
/*      */ import com.cedar.cp.dto.dimension.AllDisabledLeafELO;
/*      */ import com.cedar.cp.dto.dimension.AllDisabledLeafandNotPlannableELO;
/*      */ import com.cedar.cp.dto.dimension.AllLeafStructureElementsELO;
/*      */ import com.cedar.cp.dto.dimension.AllNotPlannableELO;
/*      */ import com.cedar.cp.dto.dimension.AllSecurityStructureElementsELO;
/*      */ import com.cedar.cp.dto.dimension.AllStructureElementsELO;
/*      */ import com.cedar.cp.dto.dimension.BudgetHierarchyElementELO;
/*      */ import com.cedar.cp.dto.dimension.BudgetLocationElementForModelELO;
/*      */ import com.cedar.cp.dto.dimension.CalendarForFinanceCubeELO;
/*      */ import com.cedar.cp.dto.dimension.CalendarForModelELO;
/*      */ import com.cedar.cp.dto.dimension.CalendarForModelVisIdELO;
/*      */ import com.cedar.cp.dto.dimension.CheckLeafELO;
/*      */ import com.cedar.cp.dto.dimension.ChildrenForParentELO;
/*      */ import com.cedar.cp.dto.dimension.HierarchyPK;
/*      */ import com.cedar.cp.dto.dimension.HierarchyRefImpl;
/*      */ import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
/*      */ import com.cedar.cp.dto.dimension.LeafPlannableBudgetLocationsForModelELO;
/*      */ import com.cedar.cp.dto.dimension.LeavesForParentELO;
/*      */ import com.cedar.cp.dto.dimension.MassStateImmediateChildrenELO;
/*      */ import com.cedar.cp.dto.dimension.ReportChildrenForParentToRelativeDepthELO;
/*      */ import com.cedar.cp.dto.dimension.ReportLeavesForParentELO;
/*      */ import com.cedar.cp.dto.dimension.RespAreaImmediateChildrenELO;
/*      */ import com.cedar.cp.dto.dimension.StructureElementByVisIdELO;
/*      */ import com.cedar.cp.dto.dimension.StructureElementCK;
/*      */ import com.cedar.cp.dto.dimension.StructureElementELO;
/*      */ import com.cedar.cp.dto.dimension.StructureElementForIdsELO;
/*      */ import com.cedar.cp.dto.dimension.StructureElementPK;
/*      */ import com.cedar.cp.dto.dimension.StructureElementParentsELO;
/*      */ import com.cedar.cp.dto.dimension.StructureElementParentsFromVisIdELO;
/*      */ import com.cedar.cp.dto.dimension.StructureElementParentsReversedELO;
/*      */ import com.cedar.cp.dto.dimension.StructureElementRefImpl;
/*      */ import com.cedar.cp.dto.dimension.StructureElementValuesELO;
/*      */ import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
/*      */ import com.cedar.cp.dto.model.ModelPK;
/*      */ import com.cedar.cp.dto.model.ModelRefImpl;
/*      */ import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
/*      */ import com.cedar.cp.dto.model.ra.ResponsibilityAreaRefImpl;
/*      */ import com.cedar.cp.ejb.base.cube.flatform.DimensionLookup;
/*      */ import com.cedar.cp.ejb.base.cube.flatform.HierarchyLookup;
/*      */ import com.cedar.cp.ejb.base.cube.flatform.StructureElementLookup;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import com.cedar.cp.util.common.JdbcUtils;
/*      */ import com.cedar.cp.util.common.JdbcUtils.ColType;
/*      */ import com.cedar.cp.util.common.JdbcUtils.StructureElementRefColType;
/*      */ import com.cedar.cp.util.xmlreport.Constraint;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.MessageFormat;
import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import javax.sql.DataSource;
/*      */ import oracle.jdbc.OracleConnection;
/*      */ import oracle.sql.ARRAY;
/*      */ import oracle.sql.ArrayDescriptor;
/*      */ import oracle.sql.DatumWithConnection;
/*      */ import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
/*      */ 
/*      */ public class StructureElementDAO extends AbstractDAO
/*      */ {
/*   60 */   Log _log = new Log(getClass());
/*      */   protected static final String SQL_FIND_BY_PRIMARY_KEY = "select STRUCTURE_ID,STRUCTURE_ELEMENT_ID from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   private static final String SQL_SELECT_COLUMNS = "select STRUCTURE_ELEMENT.STRUCTURE_ID,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID,STRUCTURE_ELEMENT.VIS_ID,STRUCTURE_ELEMENT.DESCRIPTION,STRUCTURE_ELEMENT.PARENT_ID,STRUCTURE_ELEMENT.CHILD_INDEX,STRUCTURE_ELEMENT.DEPTH,STRUCTURE_ELEMENT.POSITION,STRUCTURE_ELEMENT.END_POSITION,STRUCTURE_ELEMENT.LEAF,STRUCTURE_ELEMENT.IS_CREDIT,STRUCTURE_ELEMENT.DISABLED,STRUCTURE_ELEMENT.NOT_PLANNABLE,STRUCTURE_ELEMENT.CAL_ELEM_TYPE,STRUCTURE_ELEMENT.CAL_VIS_ID_PREFIX,STRUCTURE_ELEMENT.ACTUAL_DATE";
/*      */   protected static final String SQL_LOAD = " from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_CREATE = "insert into STRUCTURE_ELEMENT ( STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VIS_ID,DESCRIPTION,PARENT_ID,CHILD_INDEX,DEPTH,POSITION,END_POSITION,LEAF,IS_CREDIT,DISABLED,NOT_PLANNABLE,CAL_ELEM_TYPE,CAL_VIS_ID_PREFIX,ACTUAL_DATE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String SQL_STORE = "update STRUCTURE_ELEMENT set VIS_ID = ?,DESCRIPTION = ?,PARENT_ID = ?,CHILD_INDEX = ?,DEPTH = ?,POSITION = ?,END_POSITION = ?,LEAF = ?,IS_CREDIT = ?,DISABLED = ?,NOT_PLANNABLE = ?,CAL_ELEM_TYPE = ?,CAL_VIS_ID_PREFIX = ?,ACTUAL_DATE = ? where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*      */   protected static final String SQL_REMOVE = "delete from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ";
/*  564 */   protected static String SQL_ALL_STRUCTURE_ELEMENTS = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.PARENT_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.END_POSITION      ,STRUCTURE_ELEMENT.CAL_ELEM_TYPE      ,STRUCTURE_ELEMENT.DISABLED      ,STRUCTURE_ELEMENT.NOT_PLANNABLE      ,STRUCTURE_ELEMENT.ACTUAL_DATE from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ID = ? order by POSITION";
/*      */ 
/*  678 */   protected static String SQL_ALL_LEAF_STRUCTURE_ELEMENTS = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.PARENT_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.END_POSITION      ,STRUCTURE_ELEMENT.CAL_ELEM_TYPE      ,STRUCTURE_ELEMENT.DISABLED      ,STRUCTURE_ELEMENT.NOT_PLANNABLE from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ID = ? and LEAF = 'Y' order by POSITION";
/*      */ 
/*  789 */   protected static String SQL_LEAF_PLANNABLE_BUDGET_LOCATIONS_FOR_MODEL = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,STRUCTURE_ELEMENT.PARENT_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID from STRUCTURE_ELEMENT    ,MODEL where 1=1  and  MODEL.MODEL_ID = ? AND STRUCTURE_ID = MODEL.BUDGET_HIERARCHY_ID and LEAF = 'Y' AND DISABLED <> 'Y' AND NOT_PLANNABLE <> 'Y' order by POSITION";
/*      */ 
/*  897 */   protected static String SQL_STRUCTURE_ELEMENT_PARENTS = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.PARENT_ID from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ID = ? start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID";
/*      */ 
/*  993 */   protected static String SQL_STRUCTURE_ELEMENT_PARENTS_REVERSED = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.DESCRIPTION from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ID = ? start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID order by LEVEL DESC";
/*      */ 
/* 1089 */   protected static String SQL_STRUCTURE_ELEMENT_PARENTS_FROM_VIS_ID = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION from STRUCTURE_ELEMENT    ,HIERARCHY where 1=1  and  HIERARCHY.VIS_ID = ? and STRUCTURE_ID = HIERARCHY_ID start with STRUCTURE_ELEMENT_ID = ? connect by prior PARENT_ID = STRUCTURE_ELEMENT_ID order by LEVEL DESC";
/*      */ 
/* 1204 */   protected static String SQL_IMMEDIATE_CHILDREN = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.IS_CREDIT      ,STRUCTURE_ELEMENT.CAL_ELEM_TYPE from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ID = ? and PARENT_ID = ? order by POSITION";
/*      */ 
/* 1322 */   protected static String SQL_MASS_STATE_IMMEDIATE_CHILDREN = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.IS_CREDIT      ,STRUCTURE_ELEMENT.CAL_ELEM_TYPE      ,STRUCTURE_ELEMENT.DISABLED      ,STRUCTURE_ELEMENT.NOT_PLANNABLE from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ID = ? and PARENT_ID = ? order by POSITION";
/*      */ 
/* 1450 */   protected static String SQL_STRUCTURE_ELEMENT_VALUES = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.IS_CREDIT from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ID = ? and STRUCTURE_ELEMENT_ID = ? order by POSITION";
/*      */ 
/* 1562 */   protected static String SQL_CHECK_LEAF = "select distinct 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.DISABLED from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT_ID = ?";
/*      */ 
/* 1659 */   protected static String SQL_STRUCTURE_ELEMENT = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.PARENT_ID, STRUCTURE_ELEMENT.LEAF from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT_ID = ?";
/*      */ 
/* 1760 */   protected static String SQL_STRUCTURE_ELEMENT_FOR_IDS = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.PARENT_ID      ,STRUCTURE_ELEMENT.LEAF from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ID = ? and STRUCTURE_ELEMENT_ID = ?";
/*      */ 
/* 1870 */   protected static String SQL_STRUCTURE_ELEMENT_BY_VIS_ID = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,HIERARCHY.HIERARCHY_ID      ,HIERARCHY.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.PARENT_ID      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.POSITION from STRUCTURE_ELEMENT    ,HIERARCHY where 1=1  and  STRUCTURE_ELEMENT.VIS_ID = ? and  STRUCTURE_ELEMENT.STRUCTURE_ID = HIERARCHY.HIERARCHY_ID and HIERARCHY.DIMENSION_ID = ?";
/*      */ 
/* 1999 */   protected static String SQL_RESP_AREA_IMMEDIATE_CHILDREN = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.IS_CREDIT      ,RESPONSIBILITY_AREA.VIREMENT_AUTH_STATUS      ,RESPONSIBILITY_AREA.RESPONSIBILITY_AREA_ID      ,RESPONSIBILITY_AREA.VERSION_NUM from STRUCTURE_ELEMENT    ,RESPONSIBILITY_AREA where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? and STRUCTURE_ELEMENT.PARENT_ID = ? and STRUCTURE_ELEMENT.STRUCTURE_ID = RESPONSIBILITY_AREA.STRUCTURE_ID (+) and STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID = RESPONSIBILITY_AREA.STRUCTURE_ELEMENT_ID (+) order by STRUCTURE_ELEMENT.POSITION";
/*      */ 
/* 2138 */   protected static String SQL_ALL_DISABLED_LEAF = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DISABLED from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? and STRUCTURE_ELEMENT.LEAF = 'Y' and STRUCTURE_ELEMENT.DISABLED = 'Y'";
/*      */ 
/* 2232 */   protected static String SQL_ALL_NOT_PLANNABLE = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.NOT_PLANNABLE from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? and STRUCTURE_ELEMENT.NOT_PLANNABLE = 'Y'";
/*      */ 
/* 2326 */   protected static String SQL_ALL_DISABLED_LEAFAND_NOT_PLANNABLE = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DISABLED from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? and STRUCTURE_ELEMENT.LEAF = 'Y' and ( STRUCTURE_ELEMENT.DISABLED = 'Y' or STRUCTURE_ELEMENT.NOT_PLANNABLE = 'Y' )";
/*      */ 
/* 2420 */   protected static String SQL_LEAVES_FOR_PARENT = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.IS_CREDIT from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? and STRUCTURE_ELEMENT.POSITION >= ( select position from structure_element where structure_id = ? and structure_element_id = ? ) and STRUCTURE_ELEMENT.POSITION <= ( select end_position from structure_element where structure_id = ? and structure_element_id = ? ) and STRUCTURE_ELEMENT.LEAF = 'Y' and STRUCTURE_ELEMENT.NOT_PLANNABLE <> 'Y' and STRUCTURE_ELEMENT.DISABLED <> 'Y' order by STRUCTURE_ELEMENT.POSITION";
/*      */ 
/* 2547 */   protected static String SQL_CHILDREN_FOR_PARENT = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.IS_CREDIT from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? and STRUCTURE_ELEMENT.POSITION >= ( select position from structure_element where structure_id = ? and structure_element_id = ? ) and STRUCTURE_ELEMENT.POSITION <= ( select end_position from structure_element where structure_id = ? and structure_element_id = ? ) and STRUCTURE_ELEMENT.NOT_PLANNABLE <> 'Y' and STRUCTURE_ELEMENT.DISABLED <> 'Y' order by STRUCTURE_ELEMENT.POSITION";
/*      */ 
/* 2674 */   protected static String SQL_REPORT_LEAVES_FOR_PARENT = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.IS_CREDIT from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? and STRUCTURE_ELEMENT.POSITION >= ( select position from structure_element where structure_id = ? and structure_element_id = ? ) and STRUCTURE_ELEMENT.POSITION <= ( select end_position from structure_element where structure_id = ? and structure_element_id = ? ) and STRUCTURE_ELEMENT.LEAF = 'Y' order by STRUCTURE_ELEMENT.POSITION";
/*      */ 
/* 2801 */   protected static String SQL_REPORT_CHILDREN_FOR_PARENT_TO_RELATIVE_DEPTH = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.IS_CREDIT from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? and STRUCTURE_ELEMENT.POSITION >= ( select position from structure_element where structure_id = ? and structure_element_id = ? ) and STRUCTURE_ELEMENT.POSITION <= ( select end_position from structure_element where structure_id = ? and structure_element_id = ? ) and STRUCTURE_ELEMENT.DEPTH <= ( select DEPTH from structure_element where structure_id = ? and structure_element_id = ? ) + ? order by STRUCTURE_ELEMENT.POSITION";
/*      */ 
/* 2940 */   protected static String SQL_BUDGET_HIERARCHY_ELEMENT = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.END_POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.DISABLED      ,STRUCTURE_ELEMENT.NOT_PLANNABLE from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT.STRUCTURE_ID = ? order by STRUCTURE_ELEMENT.DEPTH desc, STRUCTURE_ELEMENT.POSITION desc";
/*      */ 
/* 3056 */   protected static String SQL_BUDGET_LOCATION_ELEMENT_FOR_MODEL = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.POSITION      ,STRUCTURE_ELEMENT.END_POSITION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.LEAF      ,STRUCTURE_ELEMENT.DISABLED      ,STRUCTURE_ELEMENT.NOT_PLANNABLE from STRUCTURE_ELEMENT    ,MODEL where 1=1  and  MODEL.MODEL_ID = ? and STRUCTURE_ELEMENT.STRUCTURE_ID = MODEL.BUDGET_HIERARCHY_ID and STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID = ?";
/*      */   protected static final String SQL_PROTECTION_STATE = "WITH \nX AS \n( \n  SELECT \n         dimension_element_id \n        ,disabled \n        ,not_plannable \n    FROM DIMENSION_ELEMENT \n   WHERE dimension_element_id IN ({0}) \n) \n,state1 AS \n( \n  SELECT  \n    CASE WHEN num <> ? THEN 1 \n    ELSE NULL \n     END VALUE \n    FROM (SELECT COUNT(*) num FROM X) \n) \n,state AS \n( \n  SELECT STATE1.VALUE FROM STATE1 \n  UNION ALL \n  SELECT \n    CASE \n    WHEN x.disabled = ''Y''      THEN 2 \n    WHEN x.not_plannable = ''Y'' THEN 3 \n    ELSE NULL \n     END \n    FROM STATE1, X \n   WHERE state1.value IS NULL \n     AND ( \n           x.disabled = ''Y'' \n           or x.not_plannable = ''Y'' \n         ) \n) \nSELECT MIN(value) FROM state \n";
/*      */   public static final int PROTECTION_NON_LEAF = 1;
/*      */   public static final int PROTECTION_DISABLED = 2;
/*      */   public static final int PROTECTION_NON_PLANNABLE = 3;
/*      */   protected static final String SQL_LOWEST_RA = "select  distinct bu.structure_element_id from  budget_user bu,  structure_element se,  (    select position, end_position from structure_element where structure_id = ? and structure_element_id = ?  )  struc where   bu.model_id = ?   and se.structure_id = ?   and se.structure_element_id = bu.structure_element_id   and se.position >= struc.position   and se.position <= struc.end_position   and se.disabled <> 'Y'   and se.not_plannable <> 'Y' ";
/*      */   protected static final String SQL_SUBMITABLE_CHECK = "select se.* from structure_element se, (    select position, end_position from structure_element where structure_id = ? and structure_element_id = ? ) struc where se.structure_id = ? and se.position >= struc.position and se.position <= struc.end_position and se.disabled <> 'Y' and se.not_plannable <> 'Y' and se.structure_element_id not in (    select bs.structure_element_id from budget_state bs where bs.budget_cycle_id = ? and bs.state > 3 and bs.submitable = 'Y' )";
/*      */   protected static final String SQL_STRUCTURE_ELEMENT_FROM_MODEL = " SELECT STRUCTURE_ELEMENT.structure_id, STRUCTURE_ELEMENT.structure_element_id, STRUCTURE_ELEMENT.vis_id, STRUCTURE_ELEMENT.description, STRUCTURE_ELEMENT.position, STRUCTURE_ELEMENT.leaf, STRUCTURE_ELEMENT.is_credit, MODEL_DIMENSION_REL.dimension_seq_num  FROM HIERARCHY, MODEL_DIMENSION_REL, STRUCTURE_ELEMENT  WHERE   HIERARCHY.dimension_id = MODEL_DIMENSION_REL.dimension_id AND STRUCTURE_ELEMENT.structure_id = HIERARCHY.hierarchy_id AND STRUCTURE_ELEMENT.parent_id = 0 AND MODEL_DIMENSION_REL.model_id = ?  ORDER BY  MODEL_DIMENSION_REL.dimension_seq_num DESC";
/*      */   private static final String RA_PREDICATE = "       (select sra.structure_element_id           from structure_element sra              ,(                select structure_id, position, end_position                  from structure_element                 where structure_id = ?                   and structure_element_id in (                                                select bu.structure_element_id                                                  from finance_cube fc, budget_user bu                                                  where bu.model_id = fc.model_id                                                   and fc.vis_id = ?                                                   and bu.user_id = ?                                                )               ) X         where sra.structure_id = X.structure_id           and sra.position >= X.position and sra.position <= X.end_position       )";
/*      */   private static final String PICKER_SEARCH = "select   se.STRUCTURE_ID  ,se.STRUCTURE_ELEMENT_ID  ,se.VIS_ID  ,se.STRUCTURE_ID  ,se.STRUCTURE_ELEMENT_ID  ,se.VIS_ID  ,se.DESCRIPTION  ,se.POSITION  ,se.DEPTH  ,se.LEAF  ,se.IS_CREDIT  ,se.CAL_ELEM_TYPE from structure_element se,  (    select se1.structure_id, se1.structure_element_id    from structure_element se1, HIERARCHY hi        where se1.structure_id = hi.hierarchy_id        and hi.dimension_id = ?        and se1.vis_id = ?  )inner_struc  where se.STRUCTURE_ID = inner_struc.structure_id start with se.STRUCTURE_ELEMENT_ID = inner_struc.structure_element_id connect by prior se.PARENT_ID = se.STRUCTURE_ELEMENT_ID";
/* 4080 */   private static final String[] HAS_USER_ACCESS_TO_RESP_AREA_SQL = { "select count(*)", "from", "(", "   select se.structure_id, se.structure_element_id, se.vis_id, bu.read_only, se.position, se.end_position", "   from budget_user bu,", "        structure_element se,", "        model m,", "        structure_element target", "   where bu.user_id = ?", "     and bu.model_id = ?", "     ${readOnlyPredicate}", "     and m.model_id = bu.model_id", "     and se.structure_id = m.budget_hierarchy_id", "     and bu.structure_element_id = se.structure_element_id", "     and target.structure_id = m.budget_hierarchy_id", "     and target.structure_element_id = ?", "     and se.position <= target.position", "     and target.end_position <= se.end_position", ") " };
/*      */ 
/* 4169 */   private static final JdbcUtils.ColType[] PATH_TO_ROOT_META_DATA = { new JdbcUtils.StructureElementRefColType("StructureElement", "STRUCTURE_ID", "STRUCTURE_ELEMENT_ID", "VIS_ID"), new JdbcUtils.ColType("StructureId", "STRUCTURE_ID", 0), new JdbcUtils.ColType("StructureElementId", "STRUCTURE_ELEMENT_ID", 0), new JdbcUtils.ColType("VisId", "VIS_ID", 1), new JdbcUtils.ColType("Description", "DESCRIPTION", 1), new JdbcUtils.ColType("Position", "POSITION", 0), new JdbcUtils.ColType("Depth", "DEPTH", 0), new JdbcUtils.ColType("Leaf", "LEAF", 1), new JdbcUtils.ColType("IsCredit", "IS_CREDIT", 1) };
/*      */   private static final String INSERT_INTO_SE_QUERY_TEMP = "insert into se_query_temp( structure_id, structure_element_id ) values( ?, ? )";
/*      */   private static final String PATH_TO_ROOT_BATCH_QUERY = "select \n se.STRUCTURE_ID\n,se.STRUCTURE_ELEMENT_ID\n,se.VIS_ID\n,se.STRUCTURE_ID\n,se.STRUCTURE_ELEMENT_ID\n,se.VIS_ID\n,se.DESCRIPTION\n,se.POSITION\n,se.DEPTH\n,se.LEAF\n,se.IS_CREDIT\nfrom structure_element se \nstart with (se.structure_id,se.structure_element_id) in ( select * from se_query_temp ) \nconnect by prior se.structure_id = se.structure_id \n       and prior se.parent_id = se.structure_element_id";
/*      */   private static final String DEPTH_OF_BUDGET_HIRARCHY = "select max(depth) from structure_element where structure_id = (   select        BUDGET_HIERARCHY_ID    from model    where model_id = ?)";
/*      */   private static final String INSERT_INTO_FD_TEMP_DATA_SQL = "insert into fd_temp_data (structure_id,structure_element_id,selected) values(?,?,?)";
/*      */   private static final String QUERY_SELECTION_ROOTS_SQL = "-- Query roots SQL start of filtered onDemand load query\nwith fd_temp_data_t as -- simulate fd_temp_data table replace this with select * from fd_temp_data dude.\n(\n\tselect * from fd_temp_data\n),\nticks_and_crosses as -- Join ticks and crosses to structure_element to get structre info.\n(\n  select se.vis_id, se.description, se.structure_id, se.structure_element_id, se.position, \n         se.end_position, se.depth, se.is_credit, se.leaf, td.selected\n  from fd_temp_data_t td,\n       structure_element se\n  where td.structure_id = se.structure_id \n    and td.structure_element_id = se.structure_element_id\n),\nchild_entries as -- Determine the child set of selections\n(\n  select children.*\n  from ticks_and_crosses parents,\n       ticks_and_crosses children \n  where parents.structure_id = children.structure_id \n    and parents.position <= children.position \n    and children.position <= parents.end_position \n    and parents.structure_element_id != children.structure_element_id\n),\nroots as -- Select positive selections which are not in the child set\n(\n  select * \n  from ticks_and_crosses tac\n  where tac.selected = 'Y'\n   and ( tac.structure_id, tac.structure_element_id ) not in \n   ( select structure_id, structure_element_id \n     from child_entries )\n)\nselect * from roots ";
/* 4439 */   private static final JdbcUtils.ColType[] QUERY_SELECTION_ROOTS_META_DATA = { new JdbcUtils.StructureElementRefColType("StructureElement", "STRUCTURE_ID", "STRUCTURE_ELEMENT_ID", "VIS_ID"), new JdbcUtils.ColType("StructureId", "STRUCTURE_ID", 0), new JdbcUtils.ColType("StructureElementId", "STRUCTURE_ELEMENT_ID", 0), new JdbcUtils.ColType("VisId", "VIS_ID", 1), new JdbcUtils.ColType("Description", "DESCRIPTION", 1), new JdbcUtils.ColType("Position", "POSITION", 0), new JdbcUtils.ColType("Depth", "DEPTH", 0), new JdbcUtils.ColType("Leaf", "LEAF", 5), new JdbcUtils.ColType("IsCredit", "IS_CREDIT", 5) };
/*      */   private static final String QUERY_SELECTIONS_IMMEDIATE_CHILDREN_SQL = "with params as\n(\n  select * \n  from structure_element se\n  where se.structure_id = ? and se.structure_element_id = ? \n),\nfd_temp_data_t as -- simulate fd_temp_data table replace this with select * from fd_temp_data dude.\n(\n  select * from fd_temp_data \n),\nticks_and_crosses as -- Join ticks and crosses to structure_element to get structre info.\n(\n  select se.vis_id, se.description, se.structure_id, se.structure_element_id, \n         se.position, se.child_index,\n         se.end_position, se.depth, se.is_credit, se.leaf, td.selected\n  from fd_temp_data_t td,\n       structure_element se\n  where td.structure_id = se.structure_id \n    and td.structure_element_id = se.structure_element_id\n),\nchild_tac as -- Narrow the ticks and crosses to child selections\n(\n  select tac.* \n  from ticks_and_crosses tac,\n       params \n  where tac.position >= params.position and tac.end_position <= params.end_position\n),\ninclusions as\n(\n  select * from child_tac where child_tac.selected = 'Y'\n),\nexclusions as\n(\n  select * from child_tac where child_tac.selected != 'Y'\n),\ndirect_children as -- query the direct children of the expanding node\n( \n  select se.* \n  from structure_element se\n  where se.structure_id = ( select structure_id from params ) and\n        se.parent_id = ( select structure_element_id from params )\n),\nexcluded_children as -- query the nodes to remove based on exclusions\n(\n  select dc.* \n  from direct_children dc,\n       exclusions exc\n  where dc.position >= exc.position and dc.position <= exc.end_position\n),\nlowest_inc as -- The lowest level postive selections \n(\n  select * from \n  (\n    select tac.structure_id, tac.structure_element_id, \n           tac.vis_id, tac.position tp, tac.end_position tep, tac.selected, \n           tac.vis_id tvid, rank() over ( order by dc.depth - tac.depth ) as rk\n    from direct_children dc, \n         inclusions tac\n    where dc.structure_id = tac.structure_id \n      and tac.position >= dc.position \n      and tac.position <= dc.end_position\n  ) where rk = 1\n),\ninc_path_to_root as -- Path to root of lowest positive selections\n(\n  select distinct se.*\n  from structure_element se\n  connect by prior se.parent_id = se.structure_element_id and\n             prior se.structure_id = se.structure_id\n  start with ( se.structure_id, se.structure_element_id ) in \n  ( select structure_id, structure_element_id from lowest_inc )\n)\nselect /*+ all_rows */ * from \n(\n  select *\n  from \n  (\n    select dc.vis_id, dc.description, dc.structure_id, dc.structure_element_id, \n           dc.position, dc.end_position, dc.child_index, dc.depth, dc.is_credit, dc.leaf\n    from direct_children dc\n    minus\n    select ec.vis_id, ec.description, ec.structure_id, ec.structure_element_id, \n           ec.position, ec.end_position, ec.child_index, ec.depth, ec.is_credit, ec.leaf\n    from excluded_children ec\n  )\n  union \n  select *\n  from\n  (\n    select dc.vis_id, dc.description, dc.structure_id, dc.structure_element_id, \n           dc.position, dc.end_position, dc.child_index, dc.depth, dc.is_credit, dc.leaf\n    from inc_path_to_root iptr, direct_children dc\n    where iptr.structure_id = dc.structure_id \n      and iptr.structure_element_id = dc.structure_element_id\n  )\n) \norder by structure_id, child_index";
/* 4655 */   public static String SQL_CAL_STRUCTURE_ELEMENT = "select 0       ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ID      ,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID      ,STRUCTURE_ELEMENT.VIS_ID      ,STRUCTURE_ELEMENT.DESCRIPTION      ,STRUCTURE_ELEMENT.DEPTH      ,STRUCTURE_ELEMENT.PARENT_ID      ,STRUCTURE_ELEMENT.CAL_VIS_ID_PREFIX, STRUCTURE_ELEMENT.LEAF from STRUCTURE_ELEMENT where 1=1  and  STRUCTURE_ELEMENT_ID = ?";
/*      */   protected StructureElementEVO mDetails;
/*      */ 
/*      */   public StructureElementDAO(Connection connection)
/*      */   {
/*   67 */     super(connection);
/*      */   }
/*      */ 
/*      */   public StructureElementDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public StructureElementDAO(DataSource ds)
/*      */   {
/*   83 */     super(ds);
/*      */   }
/*      */ 
/*      */   protected StructureElementPK getPK()
/*      */   {
/*   91 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void setDetails(StructureElementEVO details)
/*      */   {
/*  100 */     this.mDetails = details.deepClone();
/*      */   }
/*      */ 
/*      */   public StructureElementPK create()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  109 */     doCreate();
/*      */ 
/*  111 */     return this.mDetails.getPK();
/*      */   }
/*      */ 
/*      */   public void load(StructureElementPK pk)
/*      */     throws ValidationException
/*      */   {
/*  121 */     doLoad(pk);
/*      */   }
/*      */ 
/*      */   public void store()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  130 */     doStore();
/*      */   }
/*      */ 
/*      */   public void remove()
/*      */   {
/*  139 */     doRemove();
/*      */   }
/*      */ 
/*      */   public StructureElementPK findByPrimaryKey(StructureElementPK pk_)
/*      */     throws ValidationException
/*      */   {
/*  148 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  149 */     if (exists(pk_))
/*      */     {
/*  151 */       if (timer != null) {
/*  152 */         timer.logDebug("findByPrimaryKey", pk_);
/*      */       }
/*  154 */       return pk_;
/*      */     }
/*      */ 
/*  157 */     throw new ValidationException(pk_ + " not found");
/*      */   }
/*      */ 
/*      */   protected boolean exists(StructureElementPK pk)
/*      */   {
/*  177 */     PreparedStatement stmt = null;
/*  178 */     ResultSet resultSet = null;
/*  179 */     boolean returnValue = false;
/*      */     try
/*      */     {
/*  183 */       stmt = getConnection().prepareStatement("select STRUCTURE_ID,STRUCTURE_ELEMENT_ID from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  185 */       int col = 1;
/*  186 */       stmt.setInt(col++, pk.getStructureId());
/*  187 */       stmt.setInt(col++, pk.getStructureElementId());
/*      */ 
/*  189 */       resultSet = stmt.executeQuery();
/*      */ 
/*  191 */       if (!resultSet.next())
/*  192 */         returnValue = false;
/*      */       else
/*  194 */         returnValue = true;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  198 */       throw handleSQLException(pk, "select STRUCTURE_ID,STRUCTURE_ELEMENT_ID from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  202 */       closeResultSet(resultSet);
/*  203 */       closeStatement(stmt);
/*  204 */       closeConnection();
/*      */     }
/*  206 */     return returnValue;
/*      */   }
/*      */ 
/*      */   private StructureElementEVO getEvoFromJdbc(ResultSet resultSet_)
/*      */     throws SQLException
/*      */   {
/*  233 */     int col = 1;
/*  234 */     StructureElementEVO evo = new StructureElementEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getTimestamp(col++));
/*      */ 
/*  253 */     return evo;
/*      */   }
/*      */ 
/*      */   private int putEvoKeysToJdbc(StructureElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  258 */     int col = startCol_;
/*  259 */     stmt_.setInt(col++, evo_.getStructureId());
/*  260 */     stmt_.setInt(col++, evo_.getStructureElementId());
/*  261 */     return col;
/*      */   }
/*      */ 
/*      */   private int putEvoDataToJdbc(StructureElementEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
/*      */   {
/*  266 */     int col = startCol_;
/*  267 */     stmt_.setString(col++, evo_.getVisId());
/*  268 */     stmt_.setString(col++, evo_.getDescription());
/*  269 */     stmt_.setInt(col++, evo_.getParentId());
/*  270 */     stmt_.setInt(col++, evo_.getChildIndex());
/*  271 */     stmt_.setInt(col++, evo_.getDepth());
/*  272 */     stmt_.setInt(col++, evo_.getPosition());
/*  273 */     stmt_.setInt(col++, evo_.getEndPosition());
/*  274 */     if (evo_.getLeaf())
/*  275 */       stmt_.setString(col++, "Y");
/*      */     else
/*  277 */       stmt_.setString(col++, " ");
/*  278 */     if (evo_.getIsCredit())
/*  279 */       stmt_.setString(col++, "Y");
/*      */     else
/*  281 */       stmt_.setString(col++, " ");
/*  282 */     if (evo_.getDisabled())
/*  283 */       stmt_.setString(col++, "Y");
/*      */     else
/*  285 */       stmt_.setString(col++, " ");
/*  286 */     if (evo_.getNotPlannable())
/*  287 */       stmt_.setString(col++, "Y");
/*      */     else
/*  289 */       stmt_.setString(col++, " ");
/*  290 */     stmt_.setInt(col++, evo_.getCalElemType());
/*  291 */     stmt_.setString(col++, evo_.getCalVisIdPrefix());
/*  292 */     stmt_.setTimestamp(col++, evo_.getActualDate());
/*  293 */     return col;
/*      */   }
/*      */ 
/*      */   protected void doLoad(StructureElementPK pk)
/*      */     throws ValidationException
/*      */   {
/*  310 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  312 */     PreparedStatement stmt = null;
/*  313 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/*  317 */       stmt = getConnection().prepareStatement("select STRUCTURE_ELEMENT.STRUCTURE_ID,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID,STRUCTURE_ELEMENT.VIS_ID,STRUCTURE_ELEMENT.DESCRIPTION,STRUCTURE_ELEMENT.PARENT_ID,STRUCTURE_ELEMENT.CHILD_INDEX,STRUCTURE_ELEMENT.DEPTH,STRUCTURE_ELEMENT.POSITION,STRUCTURE_ELEMENT.END_POSITION,STRUCTURE_ELEMENT.LEAF,STRUCTURE_ELEMENT.IS_CREDIT,STRUCTURE_ELEMENT.DISABLED,STRUCTURE_ELEMENT.NOT_PLANNABLE,STRUCTURE_ELEMENT.CAL_ELEM_TYPE,STRUCTURE_ELEMENT.CAL_VIS_ID_PREFIX,STRUCTURE_ELEMENT.ACTUAL_DATE from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  320 */       int col = 1;
/*  321 */       stmt.setInt(col++, pk.getStructureId());
/*  322 */       stmt.setInt(col++, pk.getStructureElementId());
/*      */ 
/*  324 */       resultSet = stmt.executeQuery();
/*      */ 
/*  326 */       if (!resultSet.next()) {
/*  327 */         throw new ValidationException(getEntityName() + " select of " + pk + " not found");
/*      */       }
/*      */ 
/*  330 */       this.mDetails = getEvoFromJdbc(resultSet);
/*  331 */       if (this.mDetails.isModified())
/*  332 */         this._log.info("doLoad", this.mDetails);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  336 */       throw handleSQLException(pk, "select STRUCTURE_ELEMENT.STRUCTURE_ID,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID,STRUCTURE_ELEMENT.VIS_ID,STRUCTURE_ELEMENT.DESCRIPTION,STRUCTURE_ELEMENT.PARENT_ID,STRUCTURE_ELEMENT.CHILD_INDEX,STRUCTURE_ELEMENT.DEPTH,STRUCTURE_ELEMENT.POSITION,STRUCTURE_ELEMENT.END_POSITION,STRUCTURE_ELEMENT.LEAF,STRUCTURE_ELEMENT.IS_CREDIT,STRUCTURE_ELEMENT.DISABLED,STRUCTURE_ELEMENT.NOT_PLANNABLE,STRUCTURE_ELEMENT.CAL_ELEM_TYPE,STRUCTURE_ELEMENT.CAL_VIS_ID_PREFIX,STRUCTURE_ELEMENT.ACTUAL_DATE from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  340 */       closeResultSet(resultSet);
/*  341 */       closeStatement(stmt);
/*  342 */       closeConnection();
/*      */ 
/*  344 */       if (timer != null)
/*  345 */         timer.logDebug("doLoad", pk);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doCreate()
/*      */     throws DuplicateNameValidationException, ValidationException
/*      */   {
/*  394 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  395 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  399 */       stmt = getConnection().prepareStatement("insert into STRUCTURE_ELEMENT ( STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VIS_ID,DESCRIPTION,PARENT_ID,CHILD_INDEX,DEPTH,POSITION,END_POSITION,LEAF,IS_CREDIT,DISABLED,NOT_PLANNABLE,CAL_ELEM_TYPE,CAL_VIS_ID_PREFIX,ACTUAL_DATE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */ 
/*  402 */       int col = 1;
/*  403 */       col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*  404 */       col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  407 */       int resultCount = stmt.executeUpdate();
/*  408 */       if (resultCount != 1)
/*      */       {
/*  410 */         throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*  413 */       this.mDetails.reset();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  417 */       throw handleSQLException(this.mDetails.getPK(), "insert into STRUCTURE_ELEMENT ( STRUCTURE_ID,STRUCTURE_ELEMENT_ID,VIS_ID,DESCRIPTION,PARENT_ID,CHILD_INDEX,DEPTH,POSITION,END_POSITION,LEAF,IS_CREDIT,DISABLED,NOT_PLANNABLE,CAL_ELEM_TYPE,CAL_VIS_ID_PREFIX,ACTUAL_DATE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  421 */       closeStatement(stmt);
/*  422 */       closeConnection();
/*      */ 
/*  424 */       if (timer != null)
/*  425 */         timer.logDebug("doCreate", this.mDetails.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doStore()
/*      */     throws DuplicateNameValidationException, VersionValidationException, ValidationException
/*      */   {
/*  459 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  463 */     PreparedStatement stmt = null;
/*      */ 
/*  465 */     boolean mainChanged = this.mDetails.isModified();
/*  466 */     boolean dependantChanged = false;
/*      */     try
/*      */     {
/*  469 */       if ((mainChanged) || (dependantChanged))
/*      */       {
/*  471 */         stmt = getConnection().prepareStatement("update STRUCTURE_ELEMENT set VIS_ID = ?,DESCRIPTION = ?,PARENT_ID = ?,CHILD_INDEX = ?,DEPTH = ?,POSITION = ?,END_POSITION = ?,LEAF = ?,IS_CREDIT = ?,DISABLED = ?,NOT_PLANNABLE = ?,CAL_ELEM_TYPE = ?,CAL_VIS_ID_PREFIX = ?,ACTUAL_DATE = ? where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  474 */         int col = 1;
/*  475 */         col = putEvoDataToJdbc(this.mDetails, stmt, col);
/*  476 */         col = putEvoKeysToJdbc(this.mDetails, stmt, col);
/*      */ 
/*  479 */         int resultCount = stmt.executeUpdate();
/*      */ 
/*  482 */         if (resultCount != 1) {
/*  483 */           throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
/*      */         }
/*      */ 
/*  486 */         this.mDetails.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  495 */       throw handleSQLException(getPK(), "update STRUCTURE_ELEMENT set VIS_ID = ?,DESCRIPTION = ?,PARENT_ID = ?,CHILD_INDEX = ?,DEPTH = ?,POSITION = ?,END_POSITION = ?,LEAF = ?,IS_CREDIT = ?,DISABLED = ?,NOT_PLANNABLE = ?,CAL_ELEM_TYPE = ?,CAL_VIS_ID_PREFIX = ?,ACTUAL_DATE = ? where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  499 */       closeStatement(stmt);
/*  500 */       closeConnection();
/*      */ 
/*  502 */       if ((timer != null) && (
/*  503 */         (mainChanged) || (dependantChanged)))
/*  504 */         timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doRemove()
/*      */   {
/*  523 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/*  528 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/*  533 */       stmt = getConnection().prepareStatement("delete from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ");
/*      */ 
/*  536 */       int col = 1;
/*  537 */       stmt.setInt(col++, this.mDetails.getStructureId());
/*  538 */       stmt.setInt(col++, this.mDetails.getStructureElementId());
/*      */ 
/*  540 */       int resultCount = stmt.executeUpdate();
/*      */ 
/*  542 */       if (resultCount != 1) {
/*  543 */         throw new RuntimeException(getEntityName() + " delete failed (" + getPK() + "): resultCount=" + resultCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  549 */       throw handleSQLException(getPK(), "delete from STRUCTURE_ELEMENT where    STRUCTURE_ID = ? AND STRUCTURE_ELEMENT_ID = ? ", sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  553 */       closeStatement(stmt);
/*  554 */       closeConnection();
/*      */ 
/*  556 */       if (timer != null)
/*  557 */         timer.logDebug("remove", this.mDetails.getPK());
/*      */     }
/*      */   }
/*      */ 
/*      */   public AllStructureElementsELO getAllStructureElements(int param1)
/*      */   {
/*  598 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  599 */     PreparedStatement stmt = null;
/*  600 */     ResultSet resultSet = null;
/*  601 */     AllStructureElementsELO results = new AllStructureElementsELO();
/*      */     try
/*      */     {
/*  604 */       stmt = getConnection().prepareStatement(SQL_ALL_STRUCTURE_ELEMENTS);
/*  605 */       int col = 1;
/*  606 */       stmt.setInt(col++, param1);
/*  607 */       resultSet = stmt.executeQuery();
/*  608 */       while (resultSet.next())
/*      */       {
/*  610 */         col = 2;
/*      */ 
/*  613 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  617 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/*  621 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/*  626 */         int col1 = resultSet.getInt(col++);
/*  627 */         String col2 = resultSet.getString(col++);
/*  628 */         int col3 = resultSet.getInt(col++);
/*  629 */         int col4 = resultSet.getInt(col++);
/*  630 */         int col5 = resultSet.getInt(col++);
/*  631 */         int col6 = resultSet.getInt(col++);
/*  632 */         String col7 = resultSet.getString(col++);
/*  633 */         if (resultSet.wasNull())
/*  634 */           col7 = "";
/*  635 */         String col8 = resultSet.getString(col++);
/*  636 */         if (resultSet.wasNull())
/*  637 */           col8 = "";
/*  638 */         Timestamp col9 = resultSet.getTimestamp(col++);
/*      */ 
/*  641 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"), col9);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  657 */       throw handleSQLException(SQL_ALL_STRUCTURE_ELEMENTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  661 */       closeResultSet(resultSet);
/*  662 */       closeStatement(stmt);
/*  663 */       closeConnection();
/*      */     }
/*      */ 
/*  666 */     if (timer != null) {
/*  667 */       timer.logDebug("getAllStructureElements", " StructureId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  672 */     return results;
/*      */   }
/*      */ 
/*      */   public AllLeafStructureElementsELO getAllLeafStructureElements(int param1)
/*      */   {
/*  711 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  712 */     PreparedStatement stmt = null;
/*  713 */     ResultSet resultSet = null;
/*  714 */     AllLeafStructureElementsELO results = new AllLeafStructureElementsELO();
/*      */     try
/*      */     {
/*  717 */       stmt = getConnection().prepareStatement(SQL_ALL_LEAF_STRUCTURE_ELEMENTS);
/*  718 */       int col = 1;
/*  719 */       stmt.setInt(col++, param1);
/*  720 */       resultSet = stmt.executeQuery();
/*  721 */       while (resultSet.next())
/*      */       {
/*  723 */         col = 2;
/*      */ 
/*  726 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  730 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/*  734 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/*  739 */         int col1 = resultSet.getInt(col++);
/*  740 */         String col2 = resultSet.getString(col++);
/*  741 */         int col3 = resultSet.getInt(col++);
/*  742 */         int col4 = resultSet.getInt(col++);
/*  743 */         int col5 = resultSet.getInt(col++);
/*  744 */         int col6 = resultSet.getInt(col++);
/*  745 */         String col7 = resultSet.getString(col++);
/*  746 */         if (resultSet.wasNull())
/*  747 */           col7 = "";
/*  748 */         String col8 = resultSet.getString(col++);
/*  749 */         if (resultSet.wasNull()) {
/*  750 */           col8 = "";
/*      */         }
/*      */ 
/*  753 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  768 */       throw handleSQLException(SQL_ALL_LEAF_STRUCTURE_ELEMENTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  772 */       closeResultSet(resultSet);
/*  773 */       closeStatement(stmt);
/*  774 */       closeConnection();
/*      */     }
/*      */ 
/*  777 */     if (timer != null) {
/*  778 */       timer.logDebug("getAllLeafStructureElements", " StructureId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  783 */     return results;
/*      */   }
/*      */ 
/*      */   public LeafPlannableBudgetLocationsForModelELO getLeafPlannableBudgetLocationsForModel(int param1)
/*      */   {
/*  821 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  822 */     PreparedStatement stmt = null;
/*  823 */     ResultSet resultSet = null;
/*  824 */     LeafPlannableBudgetLocationsForModelELO results = new LeafPlannableBudgetLocationsForModelELO();
/*      */     try
/*      */     {
/*  827 */       stmt = getConnection().prepareStatement(SQL_LEAF_PLANNABLE_BUDGET_LOCATIONS_FOR_MODEL);
/*  828 */       int col = 1;
/*  829 */       stmt.setInt(col++, param1);
/*  830 */       resultSet = stmt.executeQuery();
/*  831 */       while (resultSet.next())
/*      */       {
/*  833 */         col = 2;
/*      */ 
/*  836 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  840 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/*  843 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/*  846 */         String textModel = resultSet.getString(col++);
/*      */ 
/*  849 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/*  855 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/*  860 */         int col1 = resultSet.getInt(col++);
/*  861 */         String col2 = resultSet.getString(col++);
/*  862 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  865 */         results.add(erStructureElement, erModel, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  876 */       throw handleSQLException(SQL_LEAF_PLANNABLE_BUDGET_LOCATIONS_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  880 */       closeResultSet(resultSet);
/*  881 */       closeStatement(stmt);
/*  882 */       closeConnection();
/*      */     }
/*      */ 
/*  885 */     if (timer != null) {
/*  886 */       timer.logDebug("getLeafPlannableBudgetLocationsForModel", " ModelId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/*  891 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementParentsELO getStructureElementParents(int structureId, int structureElementId)
/*      */   {
/*  927 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  928 */     PreparedStatement stmt = null;
/*  929 */     ResultSet resultSet = null;
/*  930 */     StructureElementParentsELO results = new StructureElementParentsELO();
/*      */     try
/*      */     {
/*  933 */       stmt = getConnection().prepareStatement(SQL_STRUCTURE_ELEMENT_PARENTS);
/*  934 */       int col = 1;
/*  935 */       stmt.setInt(col++, structureId);
/*  936 */       stmt.setInt(col++, structureElementId);
/*  937 */       resultSet = stmt.executeQuery();
/*  938 */       while (resultSet.next())
/*      */       {
/*  940 */         col = 2;
/*      */ 
/*  943 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/*  947 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/*  951 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/*  956 */         int col1 = resultSet.getInt(col++);
/*  957 */         int col2 = resultSet.getInt(col++);
/*  958 */         int col3 = resultSet.getInt(col++);
/*      */ 
/*  961 */         results.add(erStructureElement, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  971 */       throw handleSQLException(SQL_STRUCTURE_ELEMENT_PARENTS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  975 */       closeResultSet(resultSet);
/*  976 */       closeStatement(stmt);
/*  977 */       closeConnection();
/*      */     }
/*      */ 
/*  980 */     if (timer != null) {
/*  981 */       timer.logDebug("getStructureElementParents", " StructureId=" + structureId + ",StructureElementId=" + structureElementId + " items=" + results.size());
/*      */     }
/*      */ 
/*  987 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementParentsReversedELO getStructureElementParentsReversed(int param1, int param2)
/*      */   {
/* 1023 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1024 */     PreparedStatement stmt = null;
/* 1025 */     ResultSet resultSet = null;
/* 1026 */     StructureElementParentsReversedELO results = new StructureElementParentsReversedELO();
/*      */     try
/*      */     {
/* 1029 */       stmt = getConnection().prepareStatement(SQL_STRUCTURE_ELEMENT_PARENTS_REVERSED);
/* 1030 */       int col = 1;
/* 1031 */       stmt.setInt(col++, param1);
/* 1032 */       stmt.setInt(col++, param2);
/* 1033 */       resultSet = stmt.executeQuery();
/* 1034 */       while (resultSet.next())
/*      */       {
/* 1036 */         col = 2;
/*      */ 
/* 1039 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1043 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 1047 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 1052 */         int col1 = resultSet.getInt(col++);
/* 1053 */         int col2 = resultSet.getInt(col++);
/* 1054 */         String col3 = resultSet.getString(col++);
/*      */ 
/* 1057 */         results.add(erStructureElement, col1, col2, col3);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1067 */       throw handleSQLException(SQL_STRUCTURE_ELEMENT_PARENTS_REVERSED, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1071 */       closeResultSet(resultSet);
/* 1072 */       closeStatement(stmt);
/* 1073 */       closeConnection();
/*      */     }
/*      */ 
/* 1076 */     if (timer != null) {
/* 1077 */       timer.logDebug("getStructureElementParentsReversed", " StructureId=" + param1 + ",StructureElementId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1083 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementParentsFromVisIdELO getStructureElementParentsFromVisId(String param1, int param2)
/*      */   {
/* 1124 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1125 */     PreparedStatement stmt = null;
/* 1126 */     ResultSet resultSet = null;
/* 1127 */     StructureElementParentsFromVisIdELO results = new StructureElementParentsFromVisIdELO();
/*      */     try
/*      */     {
/* 1130 */       stmt = getConnection().prepareStatement(SQL_STRUCTURE_ELEMENT_PARENTS_FROM_VIS_ID);
/* 1131 */       int col = 1;
/* 1132 */       stmt.setString(col++, param1);
/* 1133 */       stmt.setInt(col++, param2);
/* 1134 */       resultSet = stmt.executeQuery();
/* 1135 */       while (resultSet.next())
/*      */       {
/* 1137 */         col = 2;
/*      */ 
/* 1140 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1144 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 1147 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/* 1150 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/* 1153 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 1159 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(pkHierarchy, textHierarchy);
/*      */ 
/* 1164 */         int col1 = resultSet.getInt(col++);
/* 1165 */         int col2 = resultSet.getInt(col++);
/* 1166 */         String col3 = resultSet.getString(col++);
/* 1167 */         int col4 = resultSet.getInt(col++);
/*      */ 
/* 1170 */         results.add(erStructureElement, erHierarchy, col1, col2, col3, col4);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1182 */       throw handleSQLException(SQL_STRUCTURE_ELEMENT_PARENTS_FROM_VIS_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1186 */       closeResultSet(resultSet);
/* 1187 */       closeStatement(stmt);
/* 1188 */       closeConnection();
/*      */     }
/*      */ 
/* 1191 */     if (timer != null) {
/* 1192 */       timer.logDebug("getStructureElementParentsFromVisId", " VisId=" + param1 + ",StructureElementId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1198 */     return results;
/*      */   }

    /***
     * Returns the children of dimensions for a specific parent and structure.
     * Attention: returned dimensions are NOT limited by checking permission!
     * 
     * @param structureId
     * @param parentId
     * @return ImmediateChildrenELO
     */
    public ImmediateChildrenELO getImmediateChildren(int structureId, int parentId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        ImmediateChildrenELO results = new ImmediateChildrenELO();
        try {
            stmt = getConnection().prepareStatement(SQL_IMMEDIATE_CHILDREN);
            int col = 1;
            stmt.setInt(col++, structureId);
            stmt.setInt(col++, parentId);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));

                String textStructureElement = resultSet.getString(col++);

                StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);

                int col1 = resultSet.getInt(col++);
                int col2 = resultSet.getInt(col++);
                String col3 = resultSet.getString(col++);
                String col4 = resultSet.getString(col++);
                int col5 = resultSet.getInt(col++);
                int col6 = resultSet.getInt(col++);
                String col7 = resultSet.getString(col++);
                if (resultSet.wasNull())
                    col7 = "";
                String col8 = resultSet.getString(col++);
                if (resultSet.wasNull())
                    col8 = "";
                int col9 = resultSet.getInt(col++);

                results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"), col9);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_IMMEDIATE_CHILDREN, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getImmediateChildren", " StructureId=" + structureId + ",ParentId=" + parentId + " items=" + results.size());
        }

        return results;
    }
    
    /**
     * Returns the children of dimensions for a specific parent and structure (Calendar).
     * Returned dimensions are limited by the range from the budget cycle.
     */
    public ImmediateChildrenELO getImmediateCalendarChildrenLimitedByPermission(int structureId, int parentId, int budgetCycleId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        ImmediateChildrenELO results = new ImmediateChildrenELO();
        Map<String, String> calendarRange = null;
        int year = 0, month = 0, year_from = 0, month_from = 0, year_to = 0, month_to = 0;

        // get calendar range from budget cycle
        calendarRange = this.getCalendarRangeDetails(budgetCycleId);
        if (calendarRange.get("FROM_3") == null) {
            year_from = Integer.parseInt(calendarRange.get("FROM_1"));
            month_from = 1;
        } else {
            year_from = Integer.parseInt(calendarRange.get("FROM_2"));
            month_from = Integer.parseInt(calendarRange.get("FROM_1"));
        }
        if (calendarRange.get("TO_1") != null) {
            if (calendarRange.get("TO_3") == null) {
                year_to = Integer.parseInt(calendarRange.get("TO_1"));
                month_to = 12;
            } else {
                year_to = Integer.parseInt(calendarRange.get("TO_2"));
                month_to = Integer.parseInt(calendarRange.get("TO_1"));
            }
        } else {
            year_to = 2200;
            month_to = 12;
        }
        // get parent
        StructureElementELO parent = this.getStructureElement(parentId);
        try {
            year = Integer.parseInt(parent.getValueAt(0, "VisId").toString());
        } catch (NumberFormatException ignored) {
            year = 0;
        }

        String sql = "select   " + 
                     "STRUCTURE_ELEMENT.STRUCTURE_ID,   STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID,   STRUCTURE_ELEMENT.VIS_ID,   " + 
                     "STRUCTURE_ELEMENT.DESCRIPTION,   STRUCTURE_ELEMENT.POSITION,   STRUCTURE_ELEMENT.DEPTH,   STRUCTURE_ELEMENT.LEAF,   " + 
                     "STRUCTURE_ELEMENT.IS_CREDIT,   STRUCTURE_ELEMENT.CAL_ELEM_TYPE   " + "from STRUCTURE_ELEMENT   " + 
                     "where   STRUCTURE_ID = ?   and   PARENT_ID = ?   order by   POSITION";

        try {
            stmt = getConnection().prepareStatement(sql);

            int col = 1;
            stmt.setInt(col++, structureId);
            stmt.setInt(col++, parentId);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                col = 1;
                
                int childStructureId = resultSet.getInt(col++);
                int childStructureElementId = resultSet.getInt(col++);
                String visId = resultSet.getString(col++);
                
                StructureElementPK structureElementPK = new StructureElementPK(childStructureId, childStructureElementId);
                StructureElementRefImpl structureElementRef = new StructureElementRefImpl(structureElementPK, visId);

                String description = resultSet.getString(col++);
                int position = resultSet.getInt(col++);
                int depth = resultSet.getInt(col++);
                String leaf = resultSet.getString(col++);
                if (resultSet.wasNull())
                    leaf = "";
                String isCredit = resultSet.getString(col++);
                if (resultSet.wasNull())
                    isCredit = "";
                int calElemType = resultSet.getInt(col++);
                // Limit calendar dimension
                try {
                    if (year == 0) {
                        year = Integer.parseInt(visId);
                        if (year >= year_from && year <= year_to) {
                            results.add(structureElementRef, childStructureId, childStructureElementId, visId, description, position, depth, leaf.equals("Y"), isCredit.equals("Y"), calElemType);
                        }
                        year = 0;
                    } else {
                        month = Integer.parseInt(visId);
                        if ((year > year_from && year < year_to) || ((year == year_from && year != year_to && month >= month_from) || (year == year_to && year != year_from && month <= month_to)) || ((year == year_from && year == year_to) && (month >= month_from && month <= month_to))) {
                            results.add(structureElementRef, childStructureId, childStructureElementId, visId, description, position, depth, leaf.equals("Y"), isCredit.equals("Y"), calElemType);
                        }
                    }
                } catch (NumberFormatException e) {
                    results.add(structureElementRef, childStructureId, childStructureElementId, visId, description, position, depth, leaf.equals("Y"), isCredit.equals("Y"), calElemType);
                }
            }

        } catch (SQLException e) {
            throw handleSQLException(sql, e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getImmediateCalendarChildrenLimitedByPermission", " StructureId=" + structureId + ",ParentId=" + parentId + " items=" + results.size());
        }

        return results;
    }

    /**
     * Returns the children of dimensions for a specific parent and structure (Account).
     * Returned dimensions are limited by budget responsibility permission.
     */
    public ImmediateChildrenELO getImmediateAccountChildrenLimitedByPermission(int structureId, int parentId, int userId, int budgetCycleId) {
        return this.getImmediateAccountChildrenLimitedByPermission(structureId, parentId, userId, budgetCycleId, true);
    }
    
    /**
     * Returns the children of dimensions for a specific parent and structure (Account).
     * <code>permission></code> is true if dimensions have more permissions than only main group
     */
    public ImmediateChildrenELO getImmediateAccountChildrenLimitedByPermission(int structureId, int parentId, int userId, int budgetCycleId, boolean permission) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        String sql;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        ImmediateChildrenELO results = new ImmediateChildrenELO();
        
        sql =   "SELECT DISTINCT "+
                "   (  SELECT count(*) FROM budget_user bu "+
                "      WHERE (bu.structure_element_id = se.structure_element_id OR bu.structure_element_id = se.parent_id) AND bu.user_id = ? ) as flag, "+
                "      se.structure_id, se.structure_element_id, se.vis_id, se.description, se.position, se.depth, se.leaf, se.is_credit, se.cal_elem_type "+
                "FROM structure_element se "+
                "WHERE se.parent_id = ? START WITH se.structure_element_id in "+
                "( SELECT se.structure_element_id FROM structure_element se START WITH se.structure_element_id in "+
                "   (   SELECT se.structure_element_id FROM budget_user bu, structure_element se "+
                "       WHERE se.structure_element_id = bu.structure_element_id AND se.structure_id = ? AND bu.user_id = ? ) "+
                "CONNECT BY PRIOR se.structure_element_id = se.parent_id ) "+
                "CONNECT BY se.structure_element_id = PRIOR se.parent_id ORDER BY se.position ";

        try {
            stmt = getConnection().prepareStatement(sql);

            int col = 1;            
            stmt.setInt(col++, userId);
            stmt.setInt(col++, parentId);
            stmt.setInt(col++, structureId);
            stmt.setInt(col++, userId);
            
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                col = 1;
                int flag = resultSet.getInt(col++);
                
                int childStructureId = resultSet.getInt(col++);
                int childStructureElementId = resultSet.getInt(col++);
                String visId = resultSet.getString(col++);
                
                StructureElementPK structureElementPK = new StructureElementPK(childStructureId, childStructureElementId);
                StructureElementRefImpl structureElementRef = new StructureElementRefImpl(structureElementPK, visId);
                
                String description = resultSet.getString(col++);
                int position = resultSet.getInt(col++);
                int depth = resultSet.getInt(col++);
                String leaf = resultSet.getString(col++);
                if (resultSet.wasNull())
                    leaf = "";
                String isCredit = resultSet.getString(col++);
                if (resultSet.wasNull())
                    isCredit = "";
                int calElemType = resultSet.getInt(col++);
                
                boolean fullRights = leaf.equals("Y") || (!leaf.equals("Y") && flag > 0);
                results.add(structureElementRef, childStructureId, childStructureElementId, visId, description, position, depth, leaf.equals("Y"), isCredit.equals("Y"), calElemType, fullRights);
            }

        } catch (SQLException e) {
            throw handleSQLException(sql, e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getImmediateAccountChildrenLimitedByPermission", " StructureId=" + structureId + ",ParentId=" + parentId + " items=" + results.size());
        }
        
        return results;
    }

/*      */   public MassStateImmediateChildrenELO getMassStateImmediateChildren(int param1, int param2)
/*      */   {
/* 1360 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1361 */     PreparedStatement stmt = null;
/* 1362 */     ResultSet resultSet = null;
/* 1363 */     MassStateImmediateChildrenELO results = new MassStateImmediateChildrenELO();
/*      */     try
/*      */     {
/* 1366 */       stmt = getConnection().prepareStatement(SQL_MASS_STATE_IMMEDIATE_CHILDREN);
/* 1367 */       int col = 1;
/* 1368 */       stmt.setInt(col++, param1);
/* 1369 */       stmt.setInt(col++, param2);
/* 1370 */       resultSet = stmt.executeQuery();
/* 1371 */       while (resultSet.next())
/*      */       {
/* 1373 */         col = 2;
/*      */ 
/* 1376 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1380 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 1384 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 1389 */         int col1 = resultSet.getInt(col++);
/* 1390 */         int col2 = resultSet.getInt(col++);
/* 1391 */         String col3 = resultSet.getString(col++);
/* 1392 */         String col4 = resultSet.getString(col++);
/* 1393 */         int col5 = resultSet.getInt(col++);
/* 1394 */         int col6 = resultSet.getInt(col++);
/* 1395 */         String col7 = resultSet.getString(col++);
/* 1396 */         if (resultSet.wasNull())
/* 1397 */           col7 = "";
/* 1398 */         String col8 = resultSet.getString(col++);
/* 1399 */         if (resultSet.wasNull())
/* 1400 */           col8 = "";
/* 1401 */         int col9 = resultSet.getInt(col++);
/* 1402 */         String col10 = resultSet.getString(col++);
/* 1403 */         if (resultSet.wasNull())
/* 1404 */           col10 = "";
/* 1405 */         String col11 = resultSet.getString(col++);
/* 1406 */         if (resultSet.wasNull()) {
/* 1407 */           col11 = "";
/*      */         }
/*      */ 
/* 1410 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"), col9, col10.equals("Y"), col11.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1428 */       throw handleSQLException(SQL_MASS_STATE_IMMEDIATE_CHILDREN, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1432 */       closeResultSet(resultSet);
/* 1433 */       closeStatement(stmt);
/* 1434 */       closeConnection();
/*      */     }
/*      */ 
/* 1437 */     if (timer != null) {
/* 1438 */       timer.logDebug("getMassStateImmediateChildren", " StructureId=" + param1 + ",ParentId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1444 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementValuesELO getStructureElementValues(int param1, int param2)
/*      */   {
/* 1484 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1485 */     PreparedStatement stmt = null;
/* 1486 */     ResultSet resultSet = null;
/* 1487 */     StructureElementValuesELO results = new StructureElementValuesELO();
/*      */     try
/*      */     {
/* 1490 */       stmt = getConnection().prepareStatement(SQL_STRUCTURE_ELEMENT_VALUES);
/* 1491 */       int col = 1;
/* 1492 */       stmt.setInt(col++, param1);
/* 1493 */       stmt.setInt(col++, param2);
/* 1494 */       resultSet = stmt.executeQuery();
/* 1495 */       while (resultSet.next())
/*      */       {
/* 1497 */         col = 2;
/*      */ 
/* 1500 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1504 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 1508 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 1513 */         int col1 = resultSet.getInt(col++);
/* 1514 */         int col2 = resultSet.getInt(col++);
/* 1515 */         String col3 = resultSet.getString(col++);
/* 1516 */         String col4 = resultSet.getString(col++);
/* 1517 */         int col5 = resultSet.getInt(col++);
/* 1518 */         String col6 = resultSet.getString(col++);
/* 1519 */         if (resultSet.wasNull())
/* 1520 */           col6 = "";
/* 1521 */         String col7 = resultSet.getString(col++);
/* 1522 */         if (resultSet.wasNull()) {
/* 1523 */           col7 = "";
/*      */         }
/*      */ 
/* 1526 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6.equals("Y"), col7.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1540 */       throw handleSQLException(SQL_STRUCTURE_ELEMENT_VALUES, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1544 */       closeResultSet(resultSet);
/* 1545 */       closeStatement(stmt);
/* 1546 */       closeConnection();
/*      */     }
/*      */ 
/* 1549 */     if (timer != null) {
/* 1550 */       timer.logDebug("getStructureElementValues", " StructureId=" + param1 + ",StructureElementId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1556 */     return results;
/*      */   }
/*      */ 
/*      */   public CheckLeafELO getCheckLeaf(int param1)
/*      */   {
/* 1591 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1592 */     PreparedStatement stmt = null;
/* 1593 */     ResultSet resultSet = null;
/* 1594 */     CheckLeafELO results = new CheckLeafELO();
/*      */     try
/*      */     {
/* 1597 */       stmt = getConnection().prepareStatement(SQL_CHECK_LEAF);
/* 1598 */       int col = 1;
/* 1599 */       stmt.setInt(col++, param1);
/* 1600 */       resultSet = stmt.executeQuery();
/* 1601 */       while (resultSet.next())
/*      */       {
/* 1603 */         col = 2;
/*      */ 
/* 1606 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1610 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 1614 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 1619 */         String col1 = resultSet.getString(col++);
/* 1620 */         String col2 = resultSet.getString(col++);
/* 1621 */         if (resultSet.wasNull())
/* 1622 */           col2 = "";
/* 1623 */         String col3 = resultSet.getString(col++);
/* 1624 */         if (resultSet.wasNull()) {
/* 1625 */           col3 = "";
/*      */         }
/*      */ 
/* 1628 */         results.add(erStructureElement, col1, col2.equals("Y"), col3.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1638 */       throw handleSQLException(SQL_CHECK_LEAF, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1642 */       closeResultSet(resultSet);
/* 1643 */       closeStatement(stmt);
/* 1644 */       closeConnection();
/*      */     }
/*      */ 
/* 1647 */     if (timer != null) {
/* 1648 */       timer.logDebug("getCheckLeaf", " StructureElementId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1653 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementELO getStructureElement(int structureElementId)
/*      */   {
/* 1690 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1691 */     PreparedStatement stmt = null;
/* 1692 */     ResultSet resultSet = null;
/* 1693 */     StructureElementELO results = new StructureElementELO();
/*      */     try
/*      */     {
/* 1696 */       stmt = getConnection().prepareStatement(SQL_STRUCTURE_ELEMENT);
/* 1697 */       int col = 1;
/* 1698 */       stmt.setInt(col++, structureElementId);
/* 1699 */       resultSet = stmt.executeQuery();
/* 1700 */       while (resultSet.next())
/*      */       {
/* 1702 */         col = 2;
/*      */ 
/* 1705 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1709 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 1713 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 1718 */         int col1 = resultSet.getInt(col++);
/* 1719 */         int col2 = resultSet.getInt(col++);
/* 1720 */         String col3 = resultSet.getString(col++);
/* 1721 */         String col4 = resultSet.getString(col++);
/* 1722 */         int col5 = resultSet.getInt(col++);
/* 1723 */         int col6 = resultSet.getInt(col++);
                   Boolean isLeaf = (resultSet.getString(col++).equals("Y")) ? new Boolean(true) : new Boolean(false);
/*      */         
/* 1726 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, isLeaf);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1739 */       throw handleSQLException(SQL_STRUCTURE_ELEMENT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1743 */       closeResultSet(resultSet);
/* 1744 */       closeStatement(stmt);
/* 1745 */       closeConnection();
/*      */     }
/*      */ 
/* 1748 */     if (timer != null) {
/* 1749 */       timer.logDebug("getStructureElement", " StructureElementId=" + structureElementId + " items=" + results.size());
/*      */     }
/*      */ 
/* 1754 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementForIdsELO getStructureElementForIds(int param1, int param2)
/*      */   {
/* 1794 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1795 */     PreparedStatement stmt = null;
/* 1796 */     ResultSet resultSet = null;
/* 1797 */     StructureElementForIdsELO results = new StructureElementForIdsELO();
/*      */     try
/*      */     {
/* 1800 */       stmt = getConnection().prepareStatement(SQL_STRUCTURE_ELEMENT_FOR_IDS);
/* 1801 */       int col = 1;
/* 1802 */       stmt.setInt(col++, param1);
/* 1803 */       stmt.setInt(col++, param2);
/* 1804 */       resultSet = stmt.executeQuery();
/* 1805 */       while (resultSet.next())
/*      */       {
/* 1807 */         col = 2;
/*      */ 
/* 1810 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1814 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 1818 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 1823 */         int col1 = resultSet.getInt(col++);
/* 1824 */         int col2 = resultSet.getInt(col++);
/* 1825 */         String col3 = resultSet.getString(col++);
/* 1826 */         String col4 = resultSet.getString(col++);
/* 1827 */         int col5 = resultSet.getInt(col++);
/* 1828 */         int col6 = resultSet.getInt(col++);
/* 1829 */         String col7 = resultSet.getString(col++);
/* 1830 */         if (resultSet.wasNull()) {
/* 1831 */           col7 = "";
/*      */         }
/*      */ 
/* 1834 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1848 */       throw handleSQLException(SQL_STRUCTURE_ELEMENT_FOR_IDS, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1852 */       closeResultSet(resultSet);
/* 1853 */       closeStatement(stmt);
/* 1854 */       closeConnection();
/*      */     }
/*      */ 
/* 1857 */     if (timer != null) {
/* 1858 */       timer.logDebug("getStructureElementForIds", " StructureId=" + param1 + ",StructureElementId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1864 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementByVisIdELO getStructureElementByVisId(String param1, int param2)
/*      */   {
/* 1909 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1910 */     PreparedStatement stmt = null;
/* 1911 */     ResultSet resultSet = null;
/* 1912 */     StructureElementByVisIdELO results = new StructureElementByVisIdELO();
/*      */     try
/*      */     {
/* 1915 */       stmt = getConnection().prepareStatement(SQL_STRUCTURE_ELEMENT_BY_VIS_ID);
/* 1916 */       int col = 1;
/* 1917 */       stmt.setString(col++, param1);
/* 1918 */       stmt.setInt(col++, param2);
/* 1919 */       resultSet = stmt.executeQuery();
/* 1920 */       while (resultSet.next())
/*      */       {
/* 1922 */         col = 2;
/*      */ 
/* 1925 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 1929 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 1932 */         HierarchyPK pkHierarchy = new HierarchyPK(resultSet.getInt(col++));
/*      */ 
/* 1935 */         String textHierarchy = resultSet.getString(col++);
/*      */ 
/* 1938 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 1944 */         HierarchyRefImpl erHierarchy = new HierarchyRefImpl(pkHierarchy, textHierarchy);
/*      */ 
/* 1949 */         int col1 = resultSet.getInt(col++);
/* 1950 */         int col2 = resultSet.getInt(col++);
/* 1951 */         String col3 = resultSet.getString(col++);
/* 1952 */         String col4 = resultSet.getString(col++);
/* 1953 */         int col5 = resultSet.getInt(col++);
/* 1954 */         int col6 = resultSet.getInt(col++);
/* 1955 */         String col7 = resultSet.getString(col++);
/* 1956 */         if (resultSet.wasNull())
/* 1957 */           col7 = "";
/* 1958 */         int col8 = resultSet.getInt(col++);
/*      */ 
/* 1961 */         results.add(erStructureElement, erHierarchy, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1977 */       throw handleSQLException(SQL_STRUCTURE_ELEMENT_BY_VIS_ID, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1981 */       closeResultSet(resultSet);
/* 1982 */       closeStatement(stmt);
/* 1983 */       closeConnection();
/*      */     }
/*      */ 
/* 1986 */     if (timer != null) {
/* 1987 */       timer.logDebug("getStructureElementByVisId", " VisId=" + param1 + ",DimensionId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 1993 */     return results;
/*      */   }
/*      */ 
/*      */   public RespAreaImmediateChildrenELO getRespAreaImmediateChildren(int param1, int param2)
/*      */   {
/* 2040 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2041 */     PreparedStatement stmt = null;
/* 2042 */     ResultSet resultSet = null;
/* 2043 */     RespAreaImmediateChildrenELO results = new RespAreaImmediateChildrenELO();
/*      */     try
/*      */     {
/* 2046 */       stmt = getConnection().prepareStatement(SQL_RESP_AREA_IMMEDIATE_CHILDREN);
/* 2047 */       int col = 1;
/* 2048 */       stmt.setInt(col++, param1);
/* 2049 */       stmt.setInt(col++, param2);
/* 2050 */       resultSet = stmt.executeQuery();
/* 2051 */       while (resultSet.next())
/*      */       {
/* 2053 */         col = 2;
/*      */ 
/* 2056 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2060 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2063 */         ResponsibilityAreaPK pkResponsibilityArea = new ResponsibilityAreaPK(resultSet.getInt(col++));
/*      */ 
/* 2066 */         String textResponsibilityArea = "";
/*      */ 
/* 2069 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 2075 */         ResponsibilityAreaRefImpl erResponsibilityArea = new ResponsibilityAreaRefImpl(pkResponsibilityArea, textResponsibilityArea);
/*      */ 
/* 2080 */         int col1 = resultSet.getInt(col++);
/* 2081 */         int col2 = resultSet.getInt(col++);
/* 2082 */         String col3 = resultSet.getString(col++);
/* 2083 */         String col4 = resultSet.getString(col++);
/* 2084 */         int col5 = resultSet.getInt(col++);
/* 2085 */         int col6 = resultSet.getInt(col++);
/* 2086 */         String col7 = resultSet.getString(col++);
/* 2087 */         if (resultSet.wasNull())
/* 2088 */           col7 = "";
/* 2089 */         String col8 = resultSet.getString(col++);
/* 2090 */         if (resultSet.wasNull())
/* 2091 */           col8 = "";
/* 2092 */         int col9 = resultSet.getInt(col++);
/* 2093 */         int col10 = resultSet.getInt(col++);
/* 2094 */         int col11 = resultSet.getInt(col++);
/*      */ 
/* 2097 */         results.add(erStructureElement, erResponsibilityArea, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"), col9, col10, col11);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2116 */       throw handleSQLException(SQL_RESP_AREA_IMMEDIATE_CHILDREN, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2120 */       closeResultSet(resultSet);
/* 2121 */       closeStatement(stmt);
/* 2122 */       closeConnection();
/*      */     }
/*      */ 
/* 2125 */     if (timer != null) {
/* 2126 */       timer.logDebug("getRespAreaImmediateChildren", " StructureId=" + param1 + ",ParentId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2132 */     return results;
/*      */   }
/*      */ 
/*      */   public AllDisabledLeafELO getAllDisabledLeaf(int param1)
/*      */   {
/* 2166 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2167 */     PreparedStatement stmt = null;
/* 2168 */     ResultSet resultSet = null;
/* 2169 */     AllDisabledLeafELO results = new AllDisabledLeafELO();
/*      */     try
/*      */     {
/* 2172 */       stmt = getConnection().prepareStatement(SQL_ALL_DISABLED_LEAF);
/* 2173 */       int col = 1;
/* 2174 */       stmt.setInt(col++, param1);
/* 2175 */       resultSet = stmt.executeQuery();
/* 2176 */       while (resultSet.next())
/*      */       {
/* 2178 */         col = 2;
/*      */ 
/* 2181 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2185 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2189 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 2194 */         int col1 = resultSet.getInt(col++);
/* 2195 */         String col2 = resultSet.getString(col++);
/* 2196 */         String col3 = resultSet.getString(col++);
/* 2197 */         if (resultSet.wasNull()) {
/* 2198 */           col3 = "";
/*      */         }
/*      */ 
/* 2201 */         results.add(erStructureElement, col1, col2, col3.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2211 */       throw handleSQLException(SQL_ALL_DISABLED_LEAF, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2215 */       closeResultSet(resultSet);
/* 2216 */       closeStatement(stmt);
/* 2217 */       closeConnection();
/*      */     }
/*      */ 
/* 2220 */     if (timer != null) {
/* 2221 */       timer.logDebug("getAllDisabledLeaf", " StructureId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2226 */     return results;
/*      */   }
/*      */ 
/*      */   public AllNotPlannableELO getAllNotPlannable(int param1)
/*      */   {
/* 2260 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2261 */     PreparedStatement stmt = null;
/* 2262 */     ResultSet resultSet = null;
/* 2263 */     AllNotPlannableELO results = new AllNotPlannableELO();
/*      */     try
/*      */     {
/* 2266 */       stmt = getConnection().prepareStatement(SQL_ALL_NOT_PLANNABLE);
/* 2267 */       int col = 1;
/* 2268 */       stmt.setInt(col++, param1);
/* 2269 */       resultSet = stmt.executeQuery();
/* 2270 */       while (resultSet.next())
/*      */       {
/* 2272 */         col = 2;
/*      */ 
/* 2275 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2279 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2283 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 2288 */         int col1 = resultSet.getInt(col++);
/* 2289 */         String col2 = resultSet.getString(col++);
/* 2290 */         String col3 = resultSet.getString(col++);
/* 2291 */         if (resultSet.wasNull()) {
/* 2292 */           col3 = "";
/*      */         }
/*      */ 
/* 2295 */         results.add(erStructureElement, col1, col2, col3.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2305 */       throw handleSQLException(SQL_ALL_NOT_PLANNABLE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2309 */       closeResultSet(resultSet);
/* 2310 */       closeStatement(stmt);
/* 2311 */       closeConnection();
/*      */     }
/*      */ 
/* 2314 */     if (timer != null) {
/* 2315 */       timer.logDebug("getAllNotPlannable", " StructureId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2320 */     return results;
/*      */   }
/*      */ 
/*      */   public AllDisabledLeafandNotPlannableELO getAllDisabledLeafandNotPlannable(int param1)
/*      */   {
/* 2354 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2355 */     PreparedStatement stmt = null;
/* 2356 */     ResultSet resultSet = null;
/* 2357 */     AllDisabledLeafandNotPlannableELO results = new AllDisabledLeafandNotPlannableELO();
/*      */     try
/*      */     {
/* 2360 */       stmt = getConnection().prepareStatement(SQL_ALL_DISABLED_LEAFAND_NOT_PLANNABLE);
/* 2361 */       int col = 1;
/* 2362 */       stmt.setInt(col++, param1);
/* 2363 */       resultSet = stmt.executeQuery();
/* 2364 */       while (resultSet.next())
/*      */       {
/* 2366 */         col = 2;
/*      */ 
/* 2369 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2373 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2377 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 2382 */         int col1 = resultSet.getInt(col++);
/* 2383 */         String col2 = resultSet.getString(col++);
/* 2384 */         String col3 = resultSet.getString(col++);
/* 2385 */         if (resultSet.wasNull()) {
/* 2386 */           col3 = "";
/*      */         }
/*      */ 
/* 2389 */         results.add(erStructureElement, col1, col2, col3.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2399 */       throw handleSQLException(SQL_ALL_DISABLED_LEAFAND_NOT_PLANNABLE, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2403 */       closeResultSet(resultSet);
/* 2404 */       closeStatement(stmt);
/* 2405 */       closeConnection();
/*      */     }
/*      */ 
/* 2408 */     if (timer != null) {
/* 2409 */       timer.logDebug("getAllDisabledLeafandNotPlannable", " StructureId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2414 */     return results;
/*      */   }
/*      */ 
/*      */   public LeavesForParentELO getLeavesForParent(int param1, int param2, int param3, int param4, int param5)
/*      */   {
/* 2461 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2462 */     PreparedStatement stmt = null;
/* 2463 */     ResultSet resultSet = null;
/* 2464 */     LeavesForParentELO results = new LeavesForParentELO();
/*      */     try
/*      */     {
/* 2467 */       stmt = getConnection().prepareStatement(SQL_LEAVES_FOR_PARENT);
/* 2468 */       int col = 1;
/* 2469 */       stmt.setInt(col++, param1);
/* 2470 */       stmt.setInt(col++, param2);
/* 2471 */       stmt.setInt(col++, param3);
/* 2472 */       stmt.setInt(col++, param4);
/* 2473 */       stmt.setInt(col++, param5);
/* 2474 */       resultSet = stmt.executeQuery();
/* 2475 */       while (resultSet.next())
/*      */       {
/* 2477 */         col = 2;
/*      */ 
/* 2480 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2484 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2488 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 2493 */         int col1 = resultSet.getInt(col++);
/* 2494 */         int col2 = resultSet.getInt(col++);
/* 2495 */         String col3 = resultSet.getString(col++);
/* 2496 */         String col4 = resultSet.getString(col++);
/* 2497 */         int col5 = resultSet.getInt(col++);
/* 2498 */         int col6 = resultSet.getInt(col++);
/* 2499 */         String col7 = resultSet.getString(col++);
/* 2500 */         if (resultSet.wasNull())
/* 2501 */           col7 = "";
/* 2502 */         String col8 = resultSet.getString(col++);
/* 2503 */         if (resultSet.wasNull()) {
/* 2504 */           col8 = "";
/*      */         }
/*      */ 
/* 2507 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2522 */       throw handleSQLException(SQL_LEAVES_FOR_PARENT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2526 */       closeResultSet(resultSet);
/* 2527 */       closeStatement(stmt);
/* 2528 */       closeConnection();
/*      */     }
/*      */ 
/* 2531 */     if (timer != null) {
/* 2532 */       timer.logDebug("getLeavesForParent", " StructureId=" + param1 + ",StructureId=" + param2 + ",StructureElementId=" + param3 + ",StructureId=" + param4 + ",StructureElementId=" + param5 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2541 */     return results;
/*      */   }
/*      */ 
/*      */   public ChildrenForParentELO getChildrenForParent(int param1, int param2, int param3, int param4, int param5)
/*      */   {
/* 2588 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2589 */     PreparedStatement stmt = null;
/* 2590 */     ResultSet resultSet = null;
/* 2591 */     ChildrenForParentELO results = new ChildrenForParentELO();
/*      */     try
/*      */     {
/* 2594 */       stmt = getConnection().prepareStatement(SQL_CHILDREN_FOR_PARENT);
/* 2595 */       int col = 1;
/* 2596 */       stmt.setInt(col++, param1);
/* 2597 */       stmt.setInt(col++, param2);
/* 2598 */       stmt.setInt(col++, param3);
/* 2599 */       stmt.setInt(col++, param4);
/* 2600 */       stmt.setInt(col++, param5);
/* 2601 */       resultSet = stmt.executeQuery();
/* 2602 */       while (resultSet.next())
/*      */       {
/* 2604 */         col = 2;
/*      */ 
/* 2607 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2611 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2615 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 2620 */         int col1 = resultSet.getInt(col++);
/* 2621 */         int col2 = resultSet.getInt(col++);
/* 2622 */         String col3 = resultSet.getString(col++);
/* 2623 */         String col4 = resultSet.getString(col++);
/* 2624 */         int col5 = resultSet.getInt(col++);
/* 2625 */         int col6 = resultSet.getInt(col++);
/* 2626 */         String col7 = resultSet.getString(col++);
/* 2627 */         if (resultSet.wasNull())
/* 2628 */           col7 = "";
/* 2629 */         String col8 = resultSet.getString(col++);
/* 2630 */         if (resultSet.wasNull()) {
/* 2631 */           col8 = "";
/*      */         }
/*      */ 
/* 2634 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2649 */       throw handleSQLException(SQL_CHILDREN_FOR_PARENT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2653 */       closeResultSet(resultSet);
/* 2654 */       closeStatement(stmt);
/* 2655 */       closeConnection();
/*      */     }
/*      */ 
/* 2658 */     if (timer != null) {
/* 2659 */       timer.logDebug("getChildrenForParent", " StructureId=" + param1 + ",StructureId=" + param2 + ",StructureElementId=" + param3 + ",StructureId=" + param4 + ",StructureElementId=" + param5 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2668 */     return results;
/*      */   }
/*      */ 
/*      */   public ReportLeavesForParentELO getReportLeavesForParent(int param1, int param2, int param3, int param4, int param5)
/*      */   {
/* 2715 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2716 */     PreparedStatement stmt = null;
/* 2717 */     ResultSet resultSet = null;
/* 2718 */     ReportLeavesForParentELO results = new ReportLeavesForParentELO();
/*      */     try
/*      */     {
/* 2721 */       stmt = getConnection().prepareStatement(SQL_REPORT_LEAVES_FOR_PARENT);
/* 2722 */       int col = 1;
/* 2723 */       stmt.setInt(col++, param1);
/* 2724 */       stmt.setInt(col++, param2);
/* 2725 */       stmt.setInt(col++, param3);
/* 2726 */       stmt.setInt(col++, param4);
/* 2727 */       stmt.setInt(col++, param5);
/* 2728 */       resultSet = stmt.executeQuery();
/* 2729 */       while (resultSet.next())
/*      */       {
/* 2731 */         col = 2;
/*      */ 
/* 2734 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2738 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2742 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 2747 */         int col1 = resultSet.getInt(col++);
/* 2748 */         int col2 = resultSet.getInt(col++);
/* 2749 */         String col3 = resultSet.getString(col++);
/* 2750 */         String col4 = resultSet.getString(col++);
/* 2751 */         int col5 = resultSet.getInt(col++);
/* 2752 */         int col6 = resultSet.getInt(col++);
/* 2753 */         String col7 = resultSet.getString(col++);
/* 2754 */         if (resultSet.wasNull())
/* 2755 */           col7 = "";
/* 2756 */         String col8 = resultSet.getString(col++);
/* 2757 */         if (resultSet.wasNull()) {
/* 2758 */           col8 = "";
/*      */         }
/*      */ 
/* 2761 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2776 */       throw handleSQLException(SQL_REPORT_LEAVES_FOR_PARENT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2780 */       closeResultSet(resultSet);
/* 2781 */       closeStatement(stmt);
/* 2782 */       closeConnection();
/*      */     }
/*      */ 
/* 2785 */     if (timer != null) {
/* 2786 */       timer.logDebug("getReportLeavesForParent", " StructureId=" + param1 + ",StructureId=" + param2 + ",StructureElementId=" + param3 + ",StructureId=" + param4 + ",StructureElementId=" + param5 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2795 */     return results;
/*      */   }
/*      */ 
/*      */   public ReportChildrenForParentToRelativeDepthELO getReportChildrenForParentToRelativeDepth(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8)
/*      */   {
/* 2848 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2849 */     PreparedStatement stmt = null;
/* 2850 */     ResultSet resultSet = null;
/* 2851 */     ReportChildrenForParentToRelativeDepthELO results = new ReportChildrenForParentToRelativeDepthELO();
/*      */     try
/*      */     {
/* 2854 */       stmt = getConnection().prepareStatement(SQL_REPORT_CHILDREN_FOR_PARENT_TO_RELATIVE_DEPTH);
/* 2855 */       int col = 1;
/* 2856 */       stmt.setInt(col++, param1);
/* 2857 */       stmt.setInt(col++, param2);
/* 2858 */       stmt.setInt(col++, param3);
/* 2859 */       stmt.setInt(col++, param4);
/* 2860 */       stmt.setInt(col++, param5);
/* 2861 */       stmt.setInt(col++, param6);
/* 2862 */       stmt.setInt(col++, param7);
/* 2863 */       stmt.setInt(col++, param8);
/* 2864 */       resultSet = stmt.executeQuery();
/* 2865 */       while (resultSet.next())
/*      */       {
/* 2867 */         col = 2;
/*      */ 
/* 2870 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2874 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2878 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 2883 */         int col1 = resultSet.getInt(col++);
/* 2884 */         int col2 = resultSet.getInt(col++);
/* 2885 */         String col3 = resultSet.getString(col++);
/* 2886 */         String col4 = resultSet.getString(col++);
/* 2887 */         int col5 = resultSet.getInt(col++);
/* 2888 */         int col6 = resultSet.getInt(col++);
/* 2889 */         String col7 = resultSet.getString(col++);
/* 2890 */         if (resultSet.wasNull())
/* 2891 */           col7 = "";
/* 2892 */         String col8 = resultSet.getString(col++);
/* 2893 */         if (resultSet.wasNull()) {
/* 2894 */           col8 = "";
/*      */         }
/*      */ 
/* 2897 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2912 */       throw handleSQLException(SQL_REPORT_CHILDREN_FOR_PARENT_TO_RELATIVE_DEPTH, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2916 */       closeResultSet(resultSet);
/* 2917 */       closeStatement(stmt);
/* 2918 */       closeConnection();
/*      */     }
/*      */ 
/* 2921 */     if (timer != null) {
/* 2922 */       timer.logDebug("getReportChildrenForParentToRelativeDepth", " StructureId=" + param1 + ",StructureId=" + param2 + ",StructureElementId=" + param3 + ",StructureId=" + param4 + ",StructureElementId=" + param5 + ",StructureId=" + param6 + ",StructureElementId=" + param7 + ",Depth=" + param8 + " items=" + results.size());
/*      */     }
/*      */ 
/* 2934 */     return results;
/*      */   }
/*      */ 
/*      */   public BudgetHierarchyElementELO getBudgetHierarchyElement(int param1)
/*      */   {
/* 2974 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2975 */     PreparedStatement stmt = null;
/* 2976 */     ResultSet resultSet = null;
/* 2977 */     BudgetHierarchyElementELO results = new BudgetHierarchyElementELO();
/*      */     try
/*      */     {
/* 2980 */       stmt = getConnection().prepareStatement(SQL_BUDGET_HIERARCHY_ELEMENT);
/* 2981 */       int col = 1;
/* 2982 */       stmt.setInt(col++, param1);
/* 2983 */       resultSet = stmt.executeQuery();
/* 2984 */       while (resultSet.next())
/*      */       {
/* 2986 */         col = 2;
/*      */ 
/* 2989 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 2993 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 2997 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 3002 */         int col1 = resultSet.getInt(col++);
/* 3003 */         String col2 = resultSet.getString(col++);
/* 3004 */         String col3 = resultSet.getString(col++);
/* 3005 */         int col4 = resultSet.getInt(col++);
/* 3006 */         int col5 = resultSet.getInt(col++);
/* 3007 */         int col6 = resultSet.getInt(col++);
/* 3008 */         String col7 = resultSet.getString(col++);
/* 3009 */         if (resultSet.wasNull())
/* 3010 */           col7 = "";
/* 3011 */         String col8 = resultSet.getString(col++);
/* 3012 */         if (resultSet.wasNull())
/* 3013 */           col8 = "";
/* 3014 */         String col9 = resultSet.getString(col++);
/* 3015 */         if (resultSet.wasNull()) {
/* 3016 */           col9 = "";
/*      */         }
/*      */ 
/* 3019 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"), col9.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3035 */       throw handleSQLException(SQL_BUDGET_HIERARCHY_ELEMENT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3039 */       closeResultSet(resultSet);
/* 3040 */       closeStatement(stmt);
/* 3041 */       closeConnection();
/*      */     }
/*      */ 
/* 3044 */     if (timer != null) {
/* 3045 */       timer.logDebug("getBudgetHierarchyElement", " StructureId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 3050 */     return results;
/*      */   }
/*      */ 
/*      */   public BudgetLocationElementForModelELO getBudgetLocationElementForModel(int param1, int param2)
/*      */   {
/* 3096 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 3097 */     PreparedStatement stmt = null;
/* 3098 */     ResultSet resultSet = null;
/* 3099 */     BudgetLocationElementForModelELO results = new BudgetLocationElementForModelELO();
/*      */     try
/*      */     {
/* 3102 */       stmt = getConnection().prepareStatement(SQL_BUDGET_LOCATION_ELEMENT_FOR_MODEL);
/* 3103 */       int col = 1;
/* 3104 */       stmt.setInt(col++, param1);
/* 3105 */       stmt.setInt(col++, param2);
/* 3106 */       resultSet = stmt.executeQuery();
/* 3107 */       while (resultSet.next())
/*      */       {
/* 3109 */         col = 2;
/*      */ 
/* 3112 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 3116 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 3119 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
/*      */ 
/* 3122 */         String textModel = resultSet.getString(col++);
/*      */ 
/* 3125 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 3131 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
/*      */ 
/* 3136 */         int col1 = resultSet.getInt(col++);
/* 3137 */         String col2 = resultSet.getString(col++);
/* 3138 */         String col3 = resultSet.getString(col++);
/* 3139 */         int col4 = resultSet.getInt(col++);
/* 3140 */         int col5 = resultSet.getInt(col++);
/* 3141 */         int col6 = resultSet.getInt(col++);
/* 3142 */         String col7 = resultSet.getString(col++);
/* 3143 */         if (resultSet.wasNull())
/* 3144 */           col7 = "";
/* 3145 */         String col8 = resultSet.getString(col++);
/* 3146 */         if (resultSet.wasNull())
/* 3147 */           col8 = "";
/* 3148 */         String col9 = resultSet.getString(col++);
/* 3149 */         if (resultSet.wasNull()) {
/* 3150 */           col9 = "";
/*      */         }
/*      */ 
/* 3153 */         results.add(erStructureElement, erModel, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"), col9.equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3170 */       throw handleSQLException(SQL_BUDGET_LOCATION_ELEMENT_FOR_MODEL, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3174 */       closeResultSet(resultSet);
/* 3175 */       closeStatement(stmt);
/* 3176 */       closeConnection();
/*      */     }
/*      */ 
/* 3179 */     if (timer != null) {
/* 3180 */       timer.logDebug("getBudgetLocationElementForModel", " ModelId=" + param1 + ",StructureElementId=" + param2 + " items=" + results.size());
/*      */     }
/*      */ 
/* 3186 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementEVO getDetails(StructureElementPK pk, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 3204 */     return getDetails(new StructureElementCK(pk), dependants);
/*      */   }
/*      */ 
/*      */   public StructureElementEVO getDetails(StructureElementCK paramCK, String dependants)
/*      */     throws ValidationException
/*      */   {
/* 3218 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 3221 */     if (this.mDetails == null) {
/* 3222 */       doLoad(paramCK.getStructureElementPK());
/*      */     }
/* 3224 */     else if (!this.mDetails.getPK().equals(paramCK.getStructureElementPK())) {
/* 3225 */       doLoad(paramCK.getStructureElementPK());
/*      */     }
/*      */ 
/* 3228 */     StructureElementEVO details = new StructureElementEVO();
/* 3229 */     details = this.mDetails.deepClone();
/*      */ 
/* 3231 */     if (timer != null) {
/* 3232 */       timer.logDebug("getDetails", paramCK + " " + dependants);
/*      */     }
/* 3234 */     return details;
/*      */   }
/*      */ 
/*      */   public StructureElementEVO getDetails(String dependants)
/*      */     throws ValidationException
/*      */   {
/* 3240 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 3244 */     StructureElementEVO details = this.mDetails.deepClone();
/*      */ 
/* 3246 */     if (timer != null) {
/* 3247 */       timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
/*      */     }
/* 3249 */     return details;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 3254 */     return "StructureElement";
/*      */   }
/*      */ 
/*      */   public StructureElementRef getRef(StructureElementPK paramStructureElementPK)
/*      */     throws ValidationException
/*      */   {
/* 3260 */     StructureElementEVO evo = getDetails(paramStructureElementPK, "");
/* 3261 */     return evo.getEntityRef();
/*      */   }
/*      */ 
/*      */   public int getProtectionState(Set elementsToCheck)
/*      */   {
/* 3361 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 3362 */     PreparedStatement stmt = null;
/* 3363 */     ResultSet resultSet = null;
/*      */ 
/* 3365 */     int state = 0;
/*      */     try
/*      */     {
/* 3369 */       int col = 1;
/*      */ 
/* 3372 */       StringBuffer params = new StringBuffer();
/* 3373 */       Iterator iter = elementsToCheck.iterator();
/* 3374 */       boolean first = true;
/* 3375 */       while (iter.hasNext())
/*      */       {
/* 3377 */         iter.next();
/* 3378 */         if (!first)
/* 3379 */           params.append(',');
/* 3380 */         first = false;
/* 3381 */         params.append('?');
/*      */       }
/* 3383 */       String sql = MessageFormat.format("WITH \nX AS \n( \n  SELECT \n         dimension_element_id \n        ,disabled \n        ,not_plannable \n    FROM DIMENSION_ELEMENT \n   WHERE dimension_element_id IN ({0}) \n) \n,state1 AS \n( \n  SELECT  \n    CASE WHEN num <> ? THEN 1 \n    ELSE NULL \n     END VALUE \n    FROM (SELECT COUNT(*) num FROM X) \n) \n,state AS \n( \n  SELECT STATE1.VALUE FROM STATE1 \n  UNION ALL \n  SELECT \n    CASE \n    WHEN x.disabled = ''Y''      THEN 2 \n    WHEN x.not_plannable = ''Y'' THEN 3 \n    ELSE NULL \n     END \n    FROM STATE1, X \n   WHERE state1.value IS NULL \n     AND ( \n           x.disabled = ''Y'' \n           or x.not_plannable = ''Y'' \n         ) \n) \nSELECT MIN(value) FROM state \n", new Object[] { params.toString() });
/*      */ 
/* 3385 */       if (this._log.isDebugEnabled())
/* 3386 */         this._log.debug("getProtectionState", sql.toString());
/* 3387 */       stmt = getConnection().prepareStatement(sql);
/*      */ 
/* 3389 */       iter = elementsToCheck.iterator();
/* 3390 */       while (iter.hasNext())
/*      */       {
/* 3392 */         Integer id = (Integer)iter.next();
/* 3393 */         if (this._log.isDebugEnabled())
/* 3394 */           this._log.debug("Binding ", id);
/* 3395 */         stmt.setInt(col++, id.intValue());
/*      */       }
/* 3397 */       if (this._log.isDebugEnabled())
/* 3398 */         this._log.debug("Binding ", String.valueOf(elementsToCheck.size()));
/* 3399 */       stmt.setInt(col++, elementsToCheck.size());
/*      */ 
/* 3401 */       resultSet = stmt.executeQuery();
/*      */ 
/* 3403 */       if (resultSet.next())
/* 3404 */         state = resultSet.getInt(1);
/* 3405 */       if (this._log.isDebugEnabled())
/* 3406 */         this._log.debug("getProtectionState", " protection state is " + state);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3410 */       System.err.println(sqle);
/* 3411 */       sqle.printStackTrace();
/* 3412 */       throw new RuntimeException("getProtectionState", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3416 */       e.printStackTrace();
/* 3417 */       throw new RuntimeException("getProtectionState", e);
/*      */     }
/*      */     finally
/*      */     {
/* 3421 */       closeResultSet(resultSet);
/* 3422 */       closeStatement(stmt);
/* 3423 */       closeConnection();
/*      */     }
/*      */ 
/* 3426 */     if (timer != null) {
/* 3427 */       timer.logDebug("getProtectionState", "");
/*      */     }
/* 3429 */     return state;
/*      */   }
/*      */ 
/*      */   public StructureElementEVO getOnDemandStructureElement(int structureElementId)
/*      */   {
/* 3434 */     this._log.debug("StructureElementDAO.getOnDemandStructureElement");
/* 3435 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 3436 */     PreparedStatement stmt = null;
/* 3437 */     ResultSet resultSet = null;
/* 3438 */     StructureElementEVO evo = null;
/*      */ 
/* 3440 */     int state = 0;
/*      */     try
/*      */     {
/* 3444 */       int col = 1;
/*      */ 
/* 3447 */       String sql = "select STRUCTURE_ELEMENT.STRUCTURE_ID,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID,STRUCTURE_ELEMENT.VIS_ID,STRUCTURE_ELEMENT.DESCRIPTION,STRUCTURE_ELEMENT.PARENT_ID,STRUCTURE_ELEMENT.CHILD_INDEX,STRUCTURE_ELEMENT.DEPTH,STRUCTURE_ELEMENT.POSITION,STRUCTURE_ELEMENT.END_POSITION,STRUCTURE_ELEMENT.LEAF,STRUCTURE_ELEMENT.IS_CREDIT,STRUCTURE_ELEMENT.DISABLED,STRUCTURE_ELEMENT.NOT_PLANNABLE,STRUCTURE_ELEMENT.CAL_ELEM_TYPE,STRUCTURE_ELEMENT.CAL_VIS_ID_PREFIX,STRUCTURE_ELEMENT.ACTUAL_DATE FROM STRUCTURE_ELEMENT WHERE STRUCTURE_ELEMENT_ID = ?";
/* 3448 */       stmt = getConnection().prepareStatement(sql);
/*      */ 
/* 3450 */       stmt.setInt(col++, structureElementId);
/*      */ 
/* 3452 */       resultSet = stmt.executeQuery();
/*      */ 
/* 3454 */       if (resultSet.next())
/* 3455 */         evo = getEvoFromJdbc(resultSet);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3459 */       System.err.println(sqle);
/* 3460 */       sqle.printStackTrace();
/* 3461 */       throw new RuntimeException("getOnDemandStructureElement", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3465 */       e.printStackTrace();
/* 3466 */       throw new RuntimeException("getOnDemandStructureElement", e);
/*      */     }
/*      */     finally
/*      */     {
/* 3470 */       closeResultSet(resultSet);
/* 3471 */       closeStatement(stmt);
/* 3472 */       closeConnection();
/*      */     }
/*      */ 
/* 3475 */     if (timer != null) {
/* 3476 */       timer.logDebug("getProtectionState", "");
/*      */     }
/* 3478 */     return evo;
/*      */   }
/*      */ 
/*      */   public boolean isLowestRA(int modelId, int userId, int structureId, int structureElementId)
/*      */   {
/* 3502 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 3503 */     PreparedStatement stmt = null;
/* 3504 */     ResultSet resultSet = null;
/*      */ 
/* 3506 */     boolean lowest = true;
/*      */     try
/*      */     {
/* 3510 */       int col = 1;
/*      */ 
/* 3512 */       stmt = getConnection().prepareStatement("select  distinct bu.structure_element_id from  budget_user bu,  structure_element se,  (    select position, end_position from structure_element where structure_id = ? and structure_element_id = ?  )  struc where   bu.model_id = ?   and se.structure_id = ?   and se.structure_element_id = bu.structure_element_id   and se.position >= struc.position   and se.position <= struc.end_position   and se.disabled <> 'Y'   and se.not_plannable <> 'Y' ");
/* 3513 */       stmt.setInt(col++, structureId);
/* 3514 */       stmt.setInt(col++, structureElementId);
/* 3515 */       stmt.setInt(col++, modelId);
/* 3516 */       stmt.setInt(col++, structureId);
/*      */ 
/* 3518 */       resultSet = stmt.executeQuery();
/*      */ 
/* 3520 */       int count = 0;
/* 3521 */       while (resultSet.next())
/*      */       {
/* 3523 */         count++;
/* 3524 */         if (count <= 1)
/*      */           continue;
/* 3526 */         lowest = false;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3533 */       System.err.println(sqle);
/* 3534 */       sqle.printStackTrace();
/* 3535 */       throw new RuntimeException(getEntityName() + " isLowestRA", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3539 */       e.printStackTrace();
/* 3540 */       throw new RuntimeException(getEntityName() + " isLowestRA", e);
/*      */     }
/*      */     finally
/*      */     {
/* 3544 */       closeResultSet(resultSet);
/* 3545 */       closeStatement(stmt);
/* 3546 */       closeConnection();
/*      */     }
/*      */ 
/* 3549 */     if (timer != null) {
/* 3550 */       timer.logDebug("isLowestRa", "");
/*      */     }
/* 3552 */     return lowest;
/*      */   }
/*      */ 
/*      */   public boolean isNodeSubmitable(int budgetcycleId, int structureId, int structureElementId)
/*      */   {
/* 3576 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 3577 */     PreparedStatement stmt = null;
/* 3578 */     ResultSet resultSet = null;
/*      */ 
/* 3580 */     boolean submitable = true;
/*      */     try
/*      */     {
/* 3584 */       int col = 1;
/*      */ 
/* 3586 */       stmt = getConnection().prepareStatement("select se.* from structure_element se, (    select position, end_position from structure_element where structure_id = ? and structure_element_id = ? ) struc where se.structure_id = ? and se.position >= struc.position and se.position <= struc.end_position and se.disabled <> 'Y' and se.not_plannable <> 'Y' and se.structure_element_id not in (    select bs.structure_element_id from budget_state bs where bs.budget_cycle_id = ? and bs.state > 3 and bs.submitable = 'Y' )");
/* 3587 */       stmt.setInt(col++, structureId);
/* 3588 */       stmt.setInt(col++, structureElementId);
/* 3589 */       stmt.setInt(col++, structureId);
/* 3590 */       stmt.setInt(col++, budgetcycleId);
/*      */ 
/* 3592 */       resultSet = stmt.executeQuery();
/*      */ 
/* 3594 */       int count = 0;
/* 3595 */       while (resultSet.next())
/*      */       {
/* 3597 */         count++;
/* 3598 */         if (count <= 1)
/*      */           continue;
/* 3600 */         submitable = false;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3607 */       System.err.println(sqle);
/* 3608 */       sqle.printStackTrace();
/* 3609 */       throw new RuntimeException(getEntityName() + " isNodeSubmitable", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3613 */       e.printStackTrace();
/* 3614 */       throw new RuntimeException(getEntityName() + " isNodeSubmitable", e);
/*      */     }
/*      */     finally
/*      */     {
/* 3618 */       closeResultSet(resultSet);
/* 3619 */       closeStatement(stmt);
/* 3620 */       closeConnection();
/*      */     }
/*      */ 
/* 3623 */     if (timer != null) {
/* 3624 */       timer.logDebug("isNodeSubmitable", "");
/*      */     }
/* 3626 */     return submitable;
/*      */   }
/*      */ 
/*      */   public StructureElementValuesELO getStructureElementIdFromModel(int modelId)
/*      */   {
/* 3651 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 3652 */     PreparedStatement stmt = null;
/* 3653 */     ResultSet resultSet = null;
/*      */ 
/* 3655 */     StructureElementValuesELO results = new StructureElementValuesELO();
/*      */     try
/*      */     {
/* 3659 */       stmt = getConnection().prepareStatement(" SELECT STRUCTURE_ELEMENT.structure_id, STRUCTURE_ELEMENT.structure_element_id, STRUCTURE_ELEMENT.vis_id, STRUCTURE_ELEMENT.description, STRUCTURE_ELEMENT.position, STRUCTURE_ELEMENT.leaf, STRUCTURE_ELEMENT.is_credit, MODEL_DIMENSION_REL.dimension_seq_num  FROM HIERARCHY, MODEL_DIMENSION_REL, STRUCTURE_ELEMENT  WHERE   HIERARCHY.dimension_id = MODEL_DIMENSION_REL.dimension_id AND STRUCTURE_ELEMENT.structure_id = HIERARCHY.hierarchy_id AND STRUCTURE_ELEMENT.parent_id = 0 AND MODEL_DIMENSION_REL.model_id = ?  ORDER BY  MODEL_DIMENSION_REL.dimension_seq_num DESC");
/* 3660 */       stmt.setInt(1, modelId);
/*      */ 
/* 3662 */       resultSet = stmt.executeQuery();
/*      */ 
/* 3664 */       if (resultSet.next())
/*      */       {
/* 3666 */         StructureElementPK pkSElement = new StructureElementPK(resultSet.getInt("structure_id"), resultSet.getInt("structure_element_id"));
/* 3667 */         String textSElement = resultSet.getString("vis_id");
/*      */ 
/* 3669 */         StructureElementRefImpl erSElement = new StructureElementRefImpl(pkSElement, textSElement);
/*      */ 
/* 3671 */         results.add(erSElement, resultSet.getInt("structure_id"), resultSet.getInt("structure_element_id"), resultSet.getString("vis_id"), resultSet.getString("description"), resultSet.getInt("position"), resultSet.getString("leaf").equals("Y"), resultSet.getString("is_credit").equals("Y"));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3685 */       System.err.println(sqle);
/* 3686 */       sqle.printStackTrace();
/* 3687 */       throw new RuntimeException(getEntityName() + " getStructureElementIdFromModel", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3691 */       e.printStackTrace();
/* 3692 */       throw new RuntimeException(getEntityName() + " getStructureElementIdFromModel", e);
/*      */     }
/*      */     finally
/*      */     {
/* 3696 */       closeResultSet(resultSet);
/* 3697 */       closeStatement(stmt);
/* 3698 */       closeConnection();
/*      */     }
/*      */ 
/* 3701 */     if (timer != null) {
/* 3702 */       timer.logDebug("getStructureElementIdFromModel", "");
/*      */     }
/* 3704 */     return results;
/*      */   }
/*      */ 
/*      */   public AvailableStructureElementELO getStructureElementsForConstraints(boolean raDimension, boolean calDimension, String fcVisId, int userId, int structureId, List constraints)
/*      */   {
/* 3738 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 3739 */     PreparedStatement stmt = null;
/* 3740 */     ResultSet resultSet = null;
/*      */ 
/* 3742 */     AvailableStructureElementELO results = new AvailableStructureElementELO();
/*      */     try
/*      */     {
/* 3747 */       StringBuffer sql = new StringBuffer();
/* 3748 */       sql.append("select s.structure_element_id, s.vis_id, s.description, s.is_credit, s.disabled, s.not_plannable, s.leaf, s.position");
/* 3749 */       sql.append("  from structure_element s");
/* 3750 */       sql.append(" where s.structure_id = ? and ( 1 = 0 ");
/*      */ 
/* 3753 */       for (Iterator iter = constraints.iterator(); iter.hasNext(); )
/*      */       {
/* 3755 */         Constraint c = (Constraint)iter.next();
/*      */ 
/* 3758 */         if (calDimension)
/*      */         {
/* 3760 */           sql.append(c.getSqlPredicate("s.structure_element_id"));
/*      */         }
/*      */         else
/*      */         {
/* 3764 */           sql.append(c.getSqlPredicate("s.vis_id"));
/*      */         }
/*      */       }
/*      */ 
/* 3768 */       sql.append(" )");
/* 3769 */       if (raDimension)
/*      */       {
/* 3772 */         sql.append(" and s.structure_element_id in ");
/* 3773 */         sql.append("       (select sra.structure_element_id           from structure_element sra              ,(                select structure_id, position, end_position                  from structure_element                 where structure_id = ?                   and structure_element_id in (                                                select bu.structure_element_id                                                  from finance_cube fc, budget_user bu                                                  where bu.model_id = fc.model_id                                                   and fc.vis_id = ?                                                   and bu.user_id = ?                                                )               ) X         where sra.structure_id = X.structure_id           and sra.position >= X.position and sra.position <= X.end_position       )");
/*      */       }
/* 3775 */       sql.append(" order by s.position ");
/* 3776 */       if (this._log.isDebugEnabled())
/* 3777 */         this._log.debug("getStructureElementsForConstraints", sql.toString());
/* 3778 */       stmt = getConnection().prepareStatement(sql.toString());
/*      */ 
/* 3781 */       int index = 1;
/* 3782 */       stmt.setInt(index++, structureId);
/* 3783 */       if (this._log.isDebugEnabled()) {
/* 3784 */         this._log.debug("getStructureElementsForConstraints", "Bind variable " + structureId);
/*      */       }
/* 3786 */       GetCalendarBindVariablesDAO getCalendarBindVariablesDAO = new GetCalendarBindVariablesDAO();
/* 3787 */       for (Iterator iter = constraints.iterator(); iter.hasNext(); )
/*      */       {
/* 3789 */         Constraint c = (Constraint)iter.next();
/*      */ 
/* 3792 */         if (calDimension)
/*      */         {
/* 3794 */           int[] bindVariables = getCalendarBindVariablesDAO.getCalendarBindVariables(structureId, c.getBindVariables());
/* 3795 */           for (int j = 0; j < bindVariables.length; j++)
/*      */           {
/* 3797 */             stmt.setInt(index++, bindVariables[j]);
/* 3798 */             if (this._log.isDebugEnabled())
/* 3799 */               this._log.debug("getStructureElementsForConstraints", "Bind variable " + bindVariables[j]);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 3804 */           String[] bindVariables = c.getBindVariables();
/* 3805 */           for (int j = 0; j < bindVariables.length; j++)
/*      */           {
/* 3807 */             stmt.setString(index++, bindVariables[j]);
/* 3808 */             if (this._log.isDebugEnabled())
/* 3809 */               this._log.debug("getStructureElementsForConstraints", "Bind variable " + bindVariables[j]);
/*      */           }
/*      */         }
/*      */       }
/* 3813 */       if (raDimension)
/*      */       {
/* 3816 */         stmt.setInt(index++, structureId);
/* 3817 */         if (this._log.isDebugEnabled())
/* 3818 */           this._log.debug("getStructureElementsForConstraints", "Bind variable " + structureId);
/* 3819 */         stmt.setString(index++, fcVisId);
/* 3820 */         if (this._log.isDebugEnabled())
/* 3821 */           this._log.debug("getStructureElementsForConstraints", "Bind variable " + fcVisId);
/* 3822 */         stmt.setInt(index++, userId);
/* 3823 */         if (this._log.isDebugEnabled()) {
/* 3824 */           this._log.debug("getStructureElementsForConstraints", "Bind variable " + userId);
/*      */         }
/*      */       }
/* 3827 */       resultSet = stmt.executeQuery();
/*      */ 
/* 3829 */       while (resultSet.next())
/*      */       {
/* 3831 */         index = 1;
/* 3832 */         int id = resultSet.getInt(index++);
/* 3833 */         results.add(new Integer(1), new StructureElementPK(structureId, id), new Integer(id), resultSet.getString(index++), resultSet.getString(index++), resultSet.getString(index++), resultSet.getString(index++), resultSet.getString(index++), resultSet.getString(index++), Integer.valueOf(resultSet.getInt(index++)));
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3849 */       System.err.println(sqle);
/* 3850 */       sqle.printStackTrace();
/* 3851 */       throw new RuntimeException(getEntityName() + " getStructureElementsForConstraints", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3855 */       e.printStackTrace();
/* 3856 */       throw new RuntimeException(getEntityName() + " getStructureElementsForConstraints", e);
/*      */     }
/*      */     finally
/*      */     {
/* 3860 */       closeResultSet(resultSet);
/* 3861 */       closeStatement(stmt);
/* 3862 */       closeConnection();
/*      */     }
/*      */ 
/* 3865 */     if (timer != null) {
/* 3866 */       timer.logDebug("getStructureElementsForConstraints", "");
/*      */     }
/* 3868 */     return results;
/*      */   }
/*      */ 
/*      */   public AllSecurityStructureElementsELO getSecurityStructureElements(String sql, List params)
/*      */   {
/* 3921 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 3922 */     PreparedStatement stmt = null;
/* 3923 */     ResultSet resultSet = null;
/* 3924 */     AllSecurityStructureElementsELO results = new AllSecurityStructureElementsELO();
/*      */     try
/*      */     {
/* 3927 */       stmt = getConnection().prepareStatement(sql);
/* 3928 */       int col = 1;
/* 3929 */       Iterator iter = params.iterator();
/* 3930 */       while (iter.hasNext())
/*      */       {
/* 3932 */         Object o = iter.next();
/* 3933 */         if ((o instanceof Integer))
/*      */         {
/* 3935 */           Integer oInt = (Integer)o;
/* 3936 */           stmt.setInt(col++, oInt.intValue());
/*      */         }
/* 3938 */         else if ((o instanceof String))
/*      */         {
/* 3940 */           String s = (String)o;
/* 3941 */           stmt.setString(col++, s);
/*      */         }
/*      */       }
/* 3944 */       resultSet = stmt.executeQuery();
/* 3945 */       while (resultSet.next())
/*      */       {
/* 3947 */         col = 1;
/*      */ 
/* 3949 */         int structureElementId = resultSet.getInt(col++);
/*      */ 
/* 3952 */         results.add(structureElementId);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3957 */       System.err.println(sqle);
/* 3958 */       sqle.printStackTrace();
/* 3959 */       throw new RuntimeException(getEntityName() + " AllStructureElements", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3963 */       closeResultSet(resultSet);
/* 3964 */       closeStatement(stmt);
/* 3965 */       closeConnection();
/*      */     }
/*      */ 
/* 3968 */     if (timer != null) {
/* 3969 */       timer.logDebug("getSecurityStructureElements", "");
/*      */     }
/* 3971 */     return results;
/*      */   }

	public EntityList getSearchTree(int dimensionId, String visID, Integer position) {
		Timer timer = new Timer(_log);

		if (visID == null) {
			visID = "";
		}
		String[] columnNames = {"StructureElement", "StructureId", "StructureElementId", "VisId", "Description", "Position", "Depth", "Leaf", "IsCredit", "CalElemType"};

		EntityListImpl results = new EntityListImpl(columnNames, new Object[0][columnNames.length]);

		SqlBuilder sqlb = new SqlBuilder(new String[]{"with getMatches as", "(", "select  s.STRUCTURE_ID, s.STRUCTURE_ELEMENT_ID, s.POSITION, row_number() over (order by POSITION) as SEQ, s.VIS_ID", "from    STRUCTURE_ELEMENT s, HIERARCHY hi", "where   s.STRUCTURE_ID = hi.HIERARCHY_ID", "and     hi.DIMENSION_ID = <dimensionId>", "and     (${matchVisId}", "         ${matchDescr}", "        )", ")", ",getSelection as", "(",
				"select  p.STRUCTURE_ID,p.STRUCTURE_ELEMENT_ID,p.VIS_ID,p.DESCRIPTION,p.POSITION,p.DEPTH,p.LEAF,p.IS_CREDIT,p.CAL_ELEM_TYPE, SEQ", "        ,(select max(SEQ) from  getMatches) as NUM_MATCHES", "        ,nvl((select min(SEQ) from getMatches where POSITION > <position>),1) as CHOSEN_MATCH", "from    STRUCTURE_ELEMENT p", "        ,getMatches", "where  p.STRUCTURE_ID = getMatches.STRUCTURE_ID", "and    getMatches.POSITION between p.POSITION and p.END_POSITION", ")",
				"select  * from getSelection", "where  SEQ = nvl(CHOSEN_MATCH,1)", "order  by POSITION desc"});

		String searchFor = visID;
		if (searchFor.indexOf("%") < 0) {
			sqlb.substitute(new String[]{"${matchVisId}", "s.VIS_ID = <searchFor>"});
			sqlb.substitute(new String[]{"${matchDescr}", null});
		} else {
			sqlb.substitute(new String[]{"${matchVisId}", "s.VIS_ID like <searchFor> escape '_'"});
			sqlb.substitute(new String[]{"${matchDescr}", "or s.DESCRIPTION like <searchFor> escape '_'"});

			searchFor = searchFor.replace("_", "__");
		}

		SqlExecutor sqle = new SqlExecutor("getSearchTree", getDataSource(), sqlb, _log);
		sqle.addBindVariable("<dimensionId>", Integer.valueOf(dimensionId));
		sqle.addBindVariable("<searchFor>", searchFor);
		sqle.addBindVariable("<position>", Integer.valueOf(position == null ? 0 : position.intValue()));

		ResultSet resultSet = sqle.getResultSet();
		Integer chosenMatch = null;
		Integer numMatches = null;
		try {
			while (resultSet.next()) {
				List row = new ArrayList();
				row.add(new StructureElementRefImpl(new StructureElementPK(resultSet.getInt("STRUCTURE_ID"), resultSet.getInt("STRUCTURE_ELEMENT_ID")), resultSet.getString("VIS_ID")));

				row.add(Integer.valueOf(resultSet.getInt("STRUCTURE_ID")));
				row.add(Integer.valueOf(resultSet.getInt("STRUCTURE_ELEMENT_ID")));
				row.add(resultSet.getString("VIS_ID"));
				row.add(resultSet.getString("DESCRIPTION"));
				row.add(Integer.valueOf(resultSet.getInt("POSITION")));
				row.add(Integer.valueOf(resultSet.getInt("DEPTH")));
				row.add(Boolean.valueOf(resultSet.getString("LEAF").equals("Y")));
				row.add(Boolean.valueOf(resultSet.getString("IS_CREDIT").equals("Y")));
				row.add(Integer.valueOf(resultSet.getInt("CAL_ELEM_TYPE")));

				chosenMatch = Integer.valueOf(resultSet.getInt("CHOSEN_MATCH"));
				numMatches = Integer.valueOf(resultSet.getInt("NUM_MATCHES"));

				results.add(row);
			}
		} catch (SQLException e) {
			throw handleSQLException(sqlb.toString(), e);
		} finally {
			sqle.close();
		}
		if (timer != null) {
			timer.logDebug("getSearchTree", " DimensionId=" + dimensionId + " text=" + visID + " startPos=" + position + " items=" + results.getNumRows() + " chosenMatch=" + chosenMatch + " numMatches=" + numMatches);
		}

		return results;
	}
/*      */ 
/*      */   public EntityList getSearchTree(int dimensionId, String visID)
/*      */   {
/* 4001 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 4002 */     PreparedStatement stmt = null;
/* 4003 */     ResultSet resultSet = null;
/* 4004 */     ImmediateChildrenELO results = new ImmediateChildrenELO();
/*      */     try
/*      */     {
/* 4007 */       stmt = getConnection().prepareStatement("select   se.STRUCTURE_ID  ,se.STRUCTURE_ELEMENT_ID  ,se.VIS_ID  ,se.STRUCTURE_ID  ,se.STRUCTURE_ELEMENT_ID  ,se.VIS_ID  ,se.DESCRIPTION  ,se.POSITION  ,se.DEPTH  ,se.LEAF  ,se.IS_CREDIT  ,se.CAL_ELEM_TYPE from structure_element se,  (    select se1.structure_id, se1.structure_element_id    from structure_element se1, HIERARCHY hi        where se1.structure_id = hi.hierarchy_id        and hi.dimension_id = ?        and se1.vis_id = ?  )inner_struc  where se.STRUCTURE_ID = inner_struc.structure_id start with se.STRUCTURE_ELEMENT_ID = inner_struc.structure_element_id connect by prior se.PARENT_ID = se.STRUCTURE_ELEMENT_ID");
/* 4008 */       int col = 1;
/* 4009 */       stmt.setInt(col++, dimensionId);
/* 4010 */       stmt.setString(col++, visID);
/* 4011 */       resultSet = stmt.executeQuery();
/* 4012 */       while (resultSet.next())
/*      */       {
/* 4014 */         col = 1;
/*      */ 
/* 4017 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 4021 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 4025 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 4030 */         int col1 = resultSet.getInt(col++);
/* 4031 */         int col2 = resultSet.getInt(col++);
/* 4032 */         String col3 = resultSet.getString(col++);
/* 4033 */         String col4 = resultSet.getString(col++);
/* 4034 */         int col5 = resultSet.getInt(col++);
/* 4035 */         int col6 = resultSet.getInt(col++);
/* 4036 */         String col7 = resultSet.getString(col++);
/* 4037 */         if (resultSet.wasNull())
/* 4038 */           col7 = "";
/* 4039 */         String col8 = resultSet.getString(col++);
/* 4040 */         if (resultSet.wasNull())
/* 4041 */           col8 = "";
/* 4042 */         int col9 = resultSet.getInt(col++);
/*      */ 
/* 4045 */         results.add(erStructureElement, col1, col2, col3, col4, col5, col6, col7.equals("Y"), col8.equals("Y"), col9);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4061 */       throw handleSQLException("select   se.STRUCTURE_ID  ,se.STRUCTURE_ELEMENT_ID  ,se.VIS_ID  ,se.STRUCTURE_ID  ,se.STRUCTURE_ELEMENT_ID  ,se.VIS_ID  ,se.DESCRIPTION  ,se.POSITION  ,se.DEPTH  ,se.LEAF  ,se.IS_CREDIT  ,se.CAL_ELEM_TYPE from structure_element se,  (    select se1.structure_id, se1.structure_element_id    from structure_element se1, HIERARCHY hi        where se1.structure_id = hi.hierarchy_id        and hi.dimension_id = ?        and se1.vis_id = ?  )inner_struc  where se.STRUCTURE_ID = inner_struc.structure_id start with se.STRUCTURE_ELEMENT_ID = inner_struc.structure_element_id connect by prior se.PARENT_ID = se.STRUCTURE_ELEMENT_ID", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4065 */       closeResultSet(resultSet);
/* 4066 */       closeStatement(stmt);
/* 4067 */       closeConnection();
/*      */     }
/*      */ 
/* 4070 */     if (timer != null) {
/* 4071 */       timer.logDebug("getSearchTree", " DimensionId=" + dimensionId + " VisID=" + visID + " items=" + results.size());
/*      */     }
/*      */ 
/* 4077 */     return results;
/*      */   }
/*      */ 
/*      */   public boolean hasUserAccessToRespArea(int userId, int modelId, int structureElementId)
/*      */   {
/* 4112 */     return hasUserAccessToRespArea(userId, modelId, structureElementId, false);
/*      */   }
/* 4125 */   public boolean hasUserAccessToRespArea(int userId, int modelId, int structureElementId, boolean writeAccess) { Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 4126 */     PreparedStatement stmt = null;
/* 4127 */     ResultSet rs = null;
/*      */ 
/* 4130 */     SqlBuilder sqlBuilder = new SqlBuilder(HAS_USER_ACCESS_TO_RESP_AREA_SQL);
/*      */     boolean result;
/*      */     try { sqlBuilder.substitute(new String[] { "${readOnlyPredicate}", writeAccess ? "and bu.read_only = ?" : "" });
/*      */ 
/* 4135 */       stmt = getConnection().prepareStatement(sqlBuilder.toString());
/* 4136 */       int col = 1;
/* 4137 */       stmt.setInt(col++, userId);
/* 4138 */       stmt.setInt(col++, modelId);
/* 4139 */       if (writeAccess)
/* 4140 */         stmt.setString(col++, " ");
/* 4141 */       stmt.setInt(col, structureElementId);
/* 4142 */       rs = stmt.executeQuery();
/*      */       //boolean result;
/* 4143 */       if (rs.next())
/* 4144 */         result = rs.getInt(1) != 0;
/*      */       else
/* 4146 */         throw new IllegalStateException("Expected one row from count(*) in hasUserAccessToRespArea()");
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4150 */       throw handleSQLException(sqlBuilder.toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4154 */       closeResultSet(rs);
/* 4155 */       closeStatement(stmt);
/* 4156 */       closeConnection();
/*      */     }
/*      */ 
/* 4159 */     if (timer != null) {
/* 4160 */       timer.logDebug("hasUserAccessToRespArea", " UserId=" + userId + " ModelId=" + modelId + " StructureElementId=" + structureElementId);
/*      */     }
/*      */ 
/* 4166 */     return result;
/*      */   }
/*      */ 
/*      */   public EntityList queryPathToRoots(Collection<StructureElementKey> elements)
/*      */   {
/* 4211 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 4212 */     PreparedStatement stmt = null;
/* 4213 */     ResultSet rs = null;
/* 4214 */     EntityList result = null;

/*      */     try
/*      */     {
/* 4217 */       stmt = getConnection().prepareStatement("insert into se_query_temp( structure_id, structure_element_id ) values( ?, ? )");
/* 4218 */       for (StructureElementKey seKey : elements)
/*      */       {
/* 4220 */         stmt.setInt(1, seKey.getStructureId());
/* 4221 */         stmt.setInt(2, seKey.getId());
/* 4222 */         stmt.addBatch();
/*      */       }
/* 4224 */       int[] results = stmt.executeBatch();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4228 */       throw handleSQLException("insert into se_query_temp( structure_id, structure_element_id ) values( ?, ? )", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4232 */       closeResultSet(rs);
/* 4233 */       closeStatement(stmt);
/* 4234 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 4239 */       stmt = getConnection().prepareStatement("select \n se.STRUCTURE_ID\n,se.STRUCTURE_ELEMENT_ID\n,se.VIS_ID\n,se.STRUCTURE_ID\n,se.STRUCTURE_ELEMENT_ID\n,se.VIS_ID\n,se.DESCRIPTION\n,se.POSITION\n,se.DEPTH\n,se.LEAF\n,se.IS_CREDIT\nfrom structure_element se \nstart with (se.structure_id,se.structure_element_id) in ( select * from se_query_temp ) \nconnect by prior se.structure_id = se.structure_id \n       and prior se.parent_id = se.structure_element_id");
/* 4240 */       rs = stmt.executeQuery();
/* 4241 */       result = JdbcUtils.extractToEntityListImpl(PATH_TO_ROOT_META_DATA, rs);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4245 */       throw handleSQLException("select \n se.STRUCTURE_ID\n,se.STRUCTURE_ELEMENT_ID\n,se.VIS_ID\n,se.STRUCTURE_ID\n,se.STRUCTURE_ELEMENT_ID\n,se.VIS_ID\n,se.DESCRIPTION\n,se.POSITION\n,se.DEPTH\n,se.LEAF\n,se.IS_CREDIT\nfrom structure_element se \nstart with (se.structure_id,se.structure_element_id) in ( select * from se_query_temp ) \nconnect by prior se.structure_id = se.structure_id \n       and prior se.parent_id = se.structure_element_id", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4249 */       closeResultSet(rs);
/* 4250 */       closeStatement(stmt);
/* 4251 */       closeConnection();
/*      */     }
/*      */ 
/* 4254 */     if (timer != null) {
/* 4255 */       timer.logDebug("queryPathToRoots");
/*      */     }
/* 4257 */     return result;
/*      */   }
/*      */ 
/*      */   public CalendarInfoImpl getCalendarInfoForModel(int modelId)
/*      */   {
/* 4267 */     CalendarForModelELO calDetails = new HierarchyDAO().getCalendarForModel(modelId);
/*      */ 
/* 4269 */     if (calDetails.getNumRows() == 0) {
/* 4270 */       return null;
/*      */     }
/* 4272 */     int hierarchyId = ((Integer)calDetails.getValueAt(0, "HierarchyId")).intValue();
/*      */ 
/* 4274 */     return getCalendarInfo(hierarchyId);
/*      */   }
/*      */ 
/*      */   public CalendarInfoImpl getCalendarInfoForModelVisId(String modelVisId)
/*      */   {
/* 4284 */     CalendarForModelVisIdELO calDetails = new HierarchyDAO().getCalendarForModelVisId(modelVisId);
/*      */ 
/* 4286 */     if (calDetails.getNumRows() == 0) {
/* 4287 */       return null;
/*      */     }
/* 4289 */     int hierarchyId = ((Integer)calDetails.getValueAt(0, "HierarchyId")).intValue();
/*      */ 
/* 4291 */     return getCalendarInfo(hierarchyId);
/*      */   }
/*      */ 
/*      */   public CalendarInfoImpl getCalendarInfoForFinanceCube(int financeCubeId)
/*      */   {
/* 4301 */     CalendarForFinanceCubeELO calDetails = new HierarchyDAO().getCalendarForFinanceCube(financeCubeId);
/*      */ 
/* 4303 */     if (calDetails.getNumRows() == 0) {
/* 4304 */       return null;
/*      */     }
/* 4306 */     int hierarchyId = ((Integer)calDetails.getValueAt(0, "HierarchyId")).intValue();
/*      */ 
/* 4308 */     return getCalendarInfo(hierarchyId);
/*      */   }
/*      */ 
/*      */   public CalendarInfoImpl getCalendarInfo(int structureId)
/*      */   {
/* 4318 */     return CalendarInfoImpl.getCalendarInfo(Integer.valueOf(structureId), getAllStructureElements(structureId));
/*      */   }
/*      */ 
/*      */   public int getDepthOfBudgetHierarchy(int modelId)
/*      */   {
/* 4335 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 4336 */     PreparedStatement stmt = null;
/* 4337 */     ResultSet rs = null;
/* 4338 */     int result = 0;
/*      */     try
/*      */     {
/* 4341 */       stmt = getConnection().prepareStatement("select max(depth) from structure_element where structure_id = (   select        BUDGET_HIERARCHY_ID    from model    where model_id = ?)");
/* 4342 */       stmt.setInt(1, modelId);
/*      */ 
/* 4344 */       rs = stmt.executeQuery();
/*      */ 
/* 4346 */       if (rs.next())
/* 4347 */         result = rs.getInt(1);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4351 */       throw handleSQLException("select max(depth) from structure_element where structure_id = (   select        BUDGET_HIERARCHY_ID    from model    where model_id = ?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4355 */       closeResultSet(rs);
/* 4356 */       closeStatement(stmt);
/* 4357 */       closeConnection();
/*      */     }
/*      */ 
/* 4360 */     if (timer != null) {
/* 4361 */       timer.logDebug("getDepthOfBudgetHierarchy");
/*      */     }
/* 4363 */     return result;
/*      */   }
/*      */ 
///*      */   private void insertIntoFdTempData(Map<StructureElementRef, Boolean> seSelectionMap)
///*      */   {
///* 4375 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
///* 4376 */     PreparedStatement stmt = null;
///*      */     try
///*      */     {
///* 4379 */       stmt = getConnection().prepareStatement("insert into fd_temp_data (structure_id,structure_element_id,selected) values(?,?,?)");
///*      */ 
///* 4381 */       for (Map.Entry entry : seSelectionMap.entrySet())
///*      */       {
///* 4383 */         StructureElementRefImpl seRefImpl = (StructureElementRefImpl)entry.getKey();
///* 4384 */         stmt.setInt(1, seRefImpl.getStructureElementPK().getStructureId());
///* 4385 */         stmt.setInt(2, seRefImpl.getStructureElementPK().getStructureElementId());
///* 4386 */         stmt.setString(3, ((Boolean)entry.getValue()).booleanValue() ? "Y" : " ");
///*      */ 
///* 4388 */         stmt.addBatch();
///*      */       }
///* 4390 */       results = stmt.executeBatch();
///*      */     }
///*      */     catch (SQLException sqle)
///*      */     {
///*      */       int[] results;
///* 4394 */       throw handleSQLException("insert into fd_temp_data (structure_id,structure_element_id,selected) values(?,?,?)", sqle);
///*      */     }
///*      */     finally
///*      */     {
///* 4398 */       closeStatement(stmt);
///* 4399 */       closeConnection();
///*      */     }
///*      */   }

			private void insertIntoFdTempData(Map<StructureElementRef, Boolean> seSelectionMap) {	    
			    Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
			    PreparedStatement stmt = null;
			
			    try {
			       stmt = this.getConnection().prepareStatement("insert into fd_temp_data (structure_id,structure_element_id,selected) values(?,?,?)");
			       Iterator sqle = seSelectionMap.entrySet().iterator();
			
			       while(sqle.hasNext()) {
			          Entry entry = (Entry)sqle.next();
			          StructureElementRefImpl seRefImpl = (StructureElementRefImpl)entry.getKey();
			          stmt.setInt(1, seRefImpl.getStructureElementPK().getStructureId());
			          stmt.setInt(2, seRefImpl.getStructureElementPK().getStructureElementId());
			          stmt.setString(3, ((Boolean)entry.getValue()).booleanValue()?"Y":" ");
			          stmt.addBatch();
			       }
			
			       int[] sqle1 = stmt.executeBatch();
			    } catch (SQLException var10) {
			       throw this.handleSQLException("insert into fd_temp_data (structure_id,structure_element_id,selected) values(?,?,?)", var10);
			    } finally {
			       this.closeStatement(stmt);
			       this.closeConnection();
			    }
			 }
/*      */ 
/*      */   public void deleteFdTempData()
/*      */   {
/* 4455 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {
/* 4458 */       stmt = getConnection().prepareStatement("delete from fd_temp_data");
/* 4459 */       stmt.executeUpdate();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4463 */       throw handleSQLException("delete from fd_temp_data", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4467 */       closeStatement(stmt);
/* 4468 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public EntityList querySelectionRoots(Map<StructureElementRef, Boolean> seSelectionMap)
/*      */   {
/* 4480 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 4482 */     insertIntoFdTempData(seSelectionMap);
/*      */ 
/* 4484 */     PreparedStatement stmt = null;
/* 4485 */     EntityList list = null;
/* 4486 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 4489 */       stmt = getConnection().prepareStatement("-- Query roots SQL start of filtered onDemand load query\nwith fd_temp_data_t as -- simulate fd_temp_data table replace this with select * from fd_temp_data dude.\n(\n\tselect * from fd_temp_data\n),\nticks_and_crosses as -- Join ticks and crosses to structure_element to get structre info.\n(\n  select se.vis_id, se.description, se.structure_id, se.structure_element_id, se.position, \n         se.end_position, se.depth, se.is_credit, se.leaf, td.selected\n  from fd_temp_data_t td,\n       structure_element se\n  where td.structure_id = se.structure_id \n    and td.structure_element_id = se.structure_element_id\n),\nchild_entries as -- Determine the child set of selections\n(\n  select children.*\n  from ticks_and_crosses parents,\n       ticks_and_crosses children \n  where parents.structure_id = children.structure_id \n    and parents.position <= children.position \n    and children.position <= parents.end_position \n    and parents.structure_element_id != children.structure_element_id\n),\nroots as -- Select positive selections which are not in the child set\n(\n  select * \n  from ticks_and_crosses tac\n  where tac.selected = 'Y'\n   and ( tac.structure_id, tac.structure_element_id ) not in \n   ( select structure_id, structure_element_id \n     from child_entries )\n)\nselect * from roots ");
/* 4490 */       rs = stmt.executeQuery();
/* 4491 */       list = JdbcUtils.extractToEntityListImpl(QUERY_SELECTION_ROOTS_META_DATA, rs);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4495 */       throw handleSQLException("insert into fd_temp_data (structure_id,structure_element_id,selected) values(?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4499 */       closeResultSet(rs);
/* 4500 */       closeStatement(stmt);
/* 4501 */       closeConnection();
/*      */     }
/*      */ 
/* 4504 */     if (timer != null) {
/* 4505 */       timer.logDebug("querySelectionRoots");
/*      */     }
/* 4507 */     return list;
/*      */   }
/*      */ 
/*      */   public EntityList querySelectionsImmediateChildren(Map<StructureElementRef, Boolean> seSelectionMap, StructureElementPK sePK)
/*      */   {
/* 4623 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 4625 */     insertIntoFdTempData(seSelectionMap);
/*      */ 
/* 4627 */     PreparedStatement stmt = null;
/* 4628 */     EntityList list = null;
/* 4629 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 4632 */       stmt = getConnection().prepareStatement("with params as\n(\n  select * \n  from structure_element se\n  where se.structure_id = ? and se.structure_element_id = ? \n),\nfd_temp_data_t as -- simulate fd_temp_data table replace this with select * from fd_temp_data dude.\n(\n  select * from fd_temp_data \n),\nticks_and_crosses as -- Join ticks and crosses to structure_element to get structre info.\n(\n  select se.vis_id, se.description, se.structure_id, se.structure_element_id, \n         se.position, se.child_index,\n         se.end_position, se.depth, se.is_credit, se.leaf, td.selected\n  from fd_temp_data_t td,\n       structure_element se\n  where td.structure_id = se.structure_id \n    and td.structure_element_id = se.structure_element_id\n),\nchild_tac as -- Narrow the ticks and crosses to child selections\n(\n  select tac.* \n  from ticks_and_crosses tac,\n       params \n  where tac.position >= params.position and tac.end_position <= params.end_position\n),\ninclusions as\n(\n  select * from child_tac where child_tac.selected = 'Y'\n),\nexclusions as\n(\n  select * from child_tac where child_tac.selected != 'Y'\n),\ndirect_children as -- query the direct children of the expanding node\n( \n  select se.* \n  from structure_element se\n  where se.structure_id = ( select structure_id from params ) and\n        se.parent_id = ( select structure_element_id from params )\n),\nexcluded_children as -- query the nodes to remove based on exclusions\n(\n  select dc.* \n  from direct_children dc,\n       exclusions exc\n  where dc.position >= exc.position and dc.position <= exc.end_position\n),\nlowest_inc as -- The lowest level postive selections \n(\n  select * from \n  (\n    select tac.structure_id, tac.structure_element_id, \n           tac.vis_id, tac.position tp, tac.end_position tep, tac.selected, \n           tac.vis_id tvid, rank() over ( order by dc.depth - tac.depth ) as rk\n    from direct_children dc, \n         inclusions tac\n    where dc.structure_id = tac.structure_id \n      and tac.position >= dc.position \n      and tac.position <= dc.end_position\n  ) where rk = 1\n),\ninc_path_to_root as -- Path to root of lowest positive selections\n(\n  select distinct se.*\n  from structure_element se\n  connect by prior se.parent_id = se.structure_element_id and\n             prior se.structure_id = se.structure_id\n  start with ( se.structure_id, se.structure_element_id ) in \n  ( select structure_id, structure_element_id from lowest_inc )\n)\nselect /*+ all_rows */ * from \n(\n  select *\n  from \n  (\n    select dc.vis_id, dc.description, dc.structure_id, dc.structure_element_id, \n           dc.position, dc.end_position, dc.child_index, dc.depth, dc.is_credit, dc.leaf\n    from direct_children dc\n    minus\n    select ec.vis_id, ec.description, ec.structure_id, ec.structure_element_id, \n           ec.position, ec.end_position, ec.child_index, ec.depth, ec.is_credit, ec.leaf\n    from excluded_children ec\n  )\n  union \n  select *\n  from\n  (\n    select dc.vis_id, dc.description, dc.structure_id, dc.structure_element_id, \n           dc.position, dc.end_position, dc.child_index, dc.depth, dc.is_credit, dc.leaf\n    from inc_path_to_root iptr, direct_children dc\n    where iptr.structure_id = dc.structure_id \n      and iptr.structure_element_id = dc.structure_element_id\n  )\n) \norder by structure_id, child_index");
/* 4633 */       stmt.setInt(1, sePK.getStructureId());
/* 4634 */       stmt.setInt(2, sePK.getStructureElementId());
/* 4635 */       rs = stmt.executeQuery();
/* 4636 */       list = JdbcUtils.extractToEntityListImpl(QUERY_SELECTION_ROOTS_META_DATA, rs);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4640 */       throw handleSQLException("insert into fd_temp_data (structure_id,structure_element_id,selected) values(?,?,?)", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4644 */       closeResultSet(rs);
/* 4645 */       closeStatement(stmt);
/* 4646 */       closeConnection();
/*      */     }
/*      */ 
/* 4649 */     if (timer != null) {
/* 4650 */       timer.logDebug("querySelectionsImmediateChildren");
/*      */     }
/* 4652 */     return list;
/*      */   }
/*      */ 
/*      */   public StructureElementELO getCalStructureElement(int param1)
/*      */   {
/* 4687 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 4688 */     PreparedStatement stmt = null;
/* 4689 */     ResultSet resultSet = null;
/* 4690 */     StructureElementELO results = new StructureElementELO();
/*      */     try
/*      */     {
/* 4693 */       stmt = getConnection().prepareStatement(SQL_CAL_STRUCTURE_ELEMENT);
/* 4694 */       int col = 1;
/* 4695 */       stmt.setInt(col++, param1);
/* 4696 */       resultSet = stmt.executeQuery();
/* 4697 */       while (resultSet.next())
/*      */       {
/* 4699 */         col = 2;
/*      */ 
/* 4702 */         StructureElementPK pkStructureElement = new StructureElementPK(resultSet.getInt(col++), resultSet.getInt(col++));
/*      */ 
/* 4706 */         String textStructureElement = resultSet.getString(col++);
/*      */ 
/* 4710 */         StructureElementRefImpl erStructureElement = new StructureElementRefImpl(pkStructureElement, textStructureElement);
/*      */ 
/* 4715 */         int col1 = resultSet.getInt(col++);
/* 4716 */         int col2 = resultSet.getInt(col++);
/* 4717 */         String col3 = resultSet.getString(col++);
/* 4718 */         String col4 = resultSet.getString(col++);
/* 4719 */         int col5 = resultSet.getInt(col++);
/* 4720 */         int col6 = resultSet.getInt(col++);
/* 4721 */         String col7 = resultSet.getString(col++);
/*      */ 
/* 4723 */         String calVisId = col7 + col3;
/*      */         Boolean isLeaf = (resultSet.getString(col++).equals("Y")) ? new Boolean(true) : new Boolean(false);
/* 4725 */         results.add(erStructureElement, col1, col2, calVisId, col4, col5, col6, isLeaf);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4738 */       throw handleSQLException(SQL_STRUCTURE_ELEMENT, sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4742 */       closeResultSet(resultSet);
/* 4743 */       closeStatement(stmt);
/* 4744 */       closeConnection();
/*      */     }
/*      */ 
/* 4747 */     if (timer != null) {
/* 4748 */       timer.logDebug("getStructureElement", " StructureElementId=" + param1 + " items=" + results.size());
/*      */     }
/*      */ 
/* 4753 */     return results;
/*      */   }

	public List<Map<String,String>> getStructureElements( List<Integer> dimsList ) {
		String sql = "select STRUCTURE_ELEMENT_ID, VIS_ID, DESCRIPTION, CAL_VIS_ID_PREFIX, LEAF from STRUCTURE_ELEMENT where STRUCTURE_ELEMENT_ID IN ";

		String in = "(";
		boolean first = true;
		for( Integer dim : dimsList ) {
			if( !first )
				in += ",";
			first = false;
			in += dim.toString();
		}
		sql += in + ")";
		
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				Map<String,String> map = new HashMap<String, String>();
				map.put( "STRUCTURE_ELEMENT_ID", resultSet.getString(1) );
				map.put( "VIS_ID", resultSet.getString(2) );
				map.put( "DESCRIPTION", resultSet.getString(3) );
				map.put( "CAL_VIS_ID_PREFIX", resultSet.getString(4) );
				map.put( "LEAF", resultSet.getString(5) );
				resultList.add( map );
			}
		} catch (SQLException sqle)	{
			throw handleSQLException(sql, sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
		
		return resultList;
	}

	/**
	 * return id of certain year and month
	 */
	public int getCalYearMonthId(String visId, String calVisIdPrefix, int dimId)
	{
		String sql = "select s.structure_element_id from structure_element s, hierarchy h where s.vis_id = ? and s.cal_vis_id_prefix = ? and s.structure_id = h.hierarchy_id and h.dimension_id = ? ";

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		int result = 0;
		try {
			stmt = getConnection().prepareStatement(sql);
			int col = 1;
			stmt.setString(col++, visId);
			stmt.setString(col++, calVisIdPrefix);
			stmt.setInt(col++, dimId);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next())
				result = resultSet.getInt(1);
		} catch (SQLException sqle)	{
			throw handleSQLException(sql, sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}

		return result;
	}

/*      */   public void queryVisualIds(Map<String, DimensionLookup> dimLookUps)
/*      */   {
/* 4763 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 4764 */     CallableStatement stmt = null;
/* 4765 */     PreparedStatement pstat = null;
/* 4766 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 4770 */       pstat = getConnection().prepareStatement("select empty_clob() from dual");
/* 4771 */       resultSet = pstat.executeQuery();
/* 4772 */       resultSet.next();
/* 4773 */       DatumWithConnection datum = (DatumWithConnection)resultSet.getClob(1);
/* 4774 */       OracleConnection oconn = datum.getOracleConnection();
/*      */ 
/* 4776 */       StructDescriptor TYPE_hierElemVisIdRow = new StructDescriptor("TYPE_HIERELEMVISIDROW", oconn);
/* 4777 */       ArrayDescriptor TYPE_hierElemVisIdTable = new ArrayDescriptor("TYPE_HIERELEMVISIDTABLE", oconn);
/* 4778 */       StructDescriptor TYPE_hierElemLookupRow = new StructDescriptor("TYPE_HIERELEMLOOKUPROW", oconn);
/* 4779 */       ArrayDescriptor TYPE_hierElemLookupTable = new ArrayDescriptor("TYPE_HIERELEMLOOKUPTABLE", oconn);
/* 4780 */       StructDescriptor TYPE_dimLookupRow = new StructDescriptor("TYPE_DIMLOOKUPROW", oconn);
/* 4781 */       ArrayDescriptor TYPE_dimLookupTable = new ArrayDescriptor("TYPE_DIMLOOKUPTABLE", oconn);
/*      */ 
/* 4784 */       STRUCT[] dimLookupTableObjs = new STRUCT[dimLookUps.size()];
/* 4785 */       int dimNum = 0;
/* 4786 */       for (DimensionLookup dimensionLookup : dimLookUps.values())
/*      */       {
/* 4788 */         STRUCT[] hierElemLookupTableObjs = new STRUCT[dimensionLookup.getHierarchies().size()];
/* 4789 */         int hierNum = 0;
/* 4790 */         for (HierarchyLookup hierarchyLookup : dimensionLookup.getHierarchies().values())
/*      */         {
/* 4792 */           STRUCT[] hierElemVisIdTableObjs = new STRUCT[hierarchyLookup.getStructureElements().values().size()];
/* 4793 */           int seNum = 0;
/* 4794 */           for (StructureElementLookup structureElementLookup : hierarchyLookup.getStructureElements().values())
/*      */           {
/* 4796 */             Object[] hierElemVisIdRowObjs = { structureElementLookup.getStructureElementVisId() };
/* 4797 */             hierElemVisIdTableObjs[seNum] = new STRUCT(TYPE_hierElemVisIdRow, oconn, hierElemVisIdRowObjs);
/* 4798 */             seNum++;
/*      */           }
/* 4800 */           Object[] hierElemLookupRowObjs = { hierarchyLookup.getHierarchyVisId(), new ARRAY(TYPE_hierElemVisIdTable, oconn, hierElemVisIdTableObjs) };
/*      */ 
/* 4803 */           hierElemLookupTableObjs[hierNum] = new STRUCT(TYPE_hierElemLookupRow, oconn, hierElemLookupRowObjs);
/* 4804 */           hierNum++;
/*      */         }
/* 4806 */         Object[] dimLookupRowObjs = { dimensionLookup.getDimensionVisId(), new ARRAY(TYPE_hierElemLookupTable, oconn, hierElemLookupTableObjs) };
/*      */ 
/* 4809 */         dimLookupTableObjs[dimNum] = new STRUCT(TYPE_dimLookupRow, oconn, dimLookupRowObjs);
/* 4810 */         dimNum++;
/*      */       }
/* 4812 */       ARRAY dimLookupTableArray = new ARRAY(TYPE_dimLookupTable, oconn, dimLookupTableObjs);
/*      */ 
/* 4814 */       stmt = getConnection().prepareCall("begin ? := dimension_utils.lookup_hierarchy_elements(?); end;");
/*      */ 
/* 4816 */       stmt.registerOutParameter(1, -10);
/* 4817 */       stmt.setObject(2, dimLookupTableArray);
/*      */ 
/* 4819 */       stmt.execute();
/*      */ 
/* 4821 */       resultSet = (ResultSet)stmt.getObject(1);
/* 4822 */       while (resultSet.next())
/*      */       {
/* 4824 */         String dimensionVisId = resultSet.getString("dimension_vis_id");
/* 4825 */         int dimensionId = resultSet.getInt("dimension_id");
/* 4826 */         String hierarchyVisId = resultSet.getString("structure_vis_id");
/* 4827 */         int hierarchyId = resultSet.getInt("structure_id");
/* 4828 */         String structureElementVisId = resultSet.getString("structure_element_vis_id");
/* 4829 */         String structureElementDescription = resultSet.getString("structure_element_description");
/* 4830 */         int structureElementId = resultSet.getInt("structure_element_id");
/* 4831 */         boolean leaf = resultSet.getString("leaf").equalsIgnoreCase("Y");
/* 4832 */         boolean nullElement = resultSet.getString("null_element").equalsIgnoreCase("Y");
/* 4833 */         DimensionLookup dimensionLookup = (DimensionLookup)dimLookUps.get(dimensionVisId);
/* 4834 */         if (dimensionLookup != null) {
/* 4835 */           dimensionLookup.setElementInfo(dimensionId, hierarchyVisId, hierarchyId, structureElementVisId, structureElementDescription, structureElementId, leaf);
/*      */         }
/*      */ 
/* 4838 */         if (nullElement) {
/* 4839 */           dimensionLookup.setElementInfo(dimensionId, hierarchyVisId, hierarchyId, "", "", structureElementId, leaf);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4845 */       throw handleSQLException("queryVisualIds", sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 4849 */       closeResultSet(resultSet);
/* 4850 */       closeStatement(pstat);
/* 4851 */       closeStatement(stmt);
/* 4852 */       closeConnection();
/*      */     }
/*      */ 
/* 4855 */     if (timer != null)
/* 4856 */       timer.logDebug("queryVisualIds");
/*      */   }
/*      */ 
/*      */   public Map<Integer, StructureElementEVO> getStructureElements(int[] structureElementIds)
/*      */   {
/* 4866 */     this._log.debug("StructureElementDAO.getStructureElements");
/* 4867 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 4868 */     SqlExecutor executor = null;
/* 4869 */     ResultSet resultSet = null;
/* 4870 */     StructureElementEVO evo = null;
/* 4871 */     Map result = new HashMap();
/*      */ 
/* 4873 */     int state = 0;
/*      */     try
/*      */     {
/* 4877 */       int col = 1;
/*      */ 
/* 4880 */       SqlBuilder sqlBuilder = new SqlBuilder(new String[] { "select STRUCTURE_ELEMENT.STRUCTURE_ID,STRUCTURE_ELEMENT.STRUCTURE_ELEMENT_ID,STRUCTURE_ELEMENT.VIS_ID,STRUCTURE_ELEMENT.DESCRIPTION,STRUCTURE_ELEMENT.PARENT_ID,STRUCTURE_ELEMENT.CHILD_INDEX,STRUCTURE_ELEMENT.DEPTH,STRUCTURE_ELEMENT.POSITION,STRUCTURE_ELEMENT.END_POSITION,STRUCTURE_ELEMENT.LEAF,STRUCTURE_ELEMENT.IS_CREDIT,STRUCTURE_ELEMENT.DISABLED,STRUCTURE_ELEMENT.NOT_PLANNABLE,STRUCTURE_ELEMENT.CAL_ELEM_TYPE,STRUCTURE_ELEMENT.CAL_VIS_ID_PREFIX,STRUCTURE_ELEMENT.ACTUAL_DATE", " FROM STRUCTURE_ELEMENT WHERE STRUCTURE_ELEMENT_ID in ( " });
/*      */ 
/* 4882 */       for (int i = 0; i < structureElementIds.length; i++)
/*      */       {
/* 4884 */         sqlBuilder.addLine("<se" + i + ".id>");
/* 4885 */         if (i < structureElementIds.length - 1)
/* 4886 */           sqlBuilder.addLine(",");
/*      */       }
/* 4888 */       sqlBuilder.addLine(" ) order by STRUCTURE_ID desc");
/*      */ 
/* 4890 */       executor = new SqlExecutor("getStructureElements", getDataSource(), sqlBuilder, this._log);
/*      */ 
/* 4892 */       for (int i = 0; i < structureElementIds.length; i++) {
/* 4893 */         executor.addBindVariable("<se" + i + ".id>", Integer.valueOf(structureElementIds[i]));
/*      */       }
/* 4895 */       resultSet = executor.getResultSet();
/*      */ 
/* 4897 */       while (resultSet.next())
/*      */       {
/* 4899 */         evo = getEvoFromJdbc(resultSet);
/* 4900 */         result.put(Integer.valueOf(evo.getStructureElementId()), evo);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 4905 */       System.err.println(sqle);
/* 4906 */       sqle.printStackTrace();
/* 4907 */       throw new RuntimeException("getStructureElements", sqle);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4911 */       e.printStackTrace();
/* 4912 */       throw new RuntimeException("getStructureElements", e);
/*      */     }
/*      */     finally
/*      */     {
/* 4916 */       closeResultSet(resultSet);
/* 4917 */       if (executor != null) {
/* 4918 */         executor.close();
/*      */       }
/*      */     }
/* 4921 */     if (timer != null) {
/* 4922 */       timer.logDebug("getStructureElements", "");
/*      */     }
/* 4924 */     return result;
/*      */   }
/*      */ 
/*      */   private class GetCalendarBindVariablesDAO extends AbstractDAO
/*      */   {
/*      */     private GetCalendarBindVariablesDAO()
/*      */     {
/*      */     }
/*      */ 
/*      */     public int[] getCalendarBindVariables(int structureId, String[] visIds)
/*      */     {
/* 3875 */       int[] results = new int[visIds.length];
/*      */ 
/* 3878 */       PreparedStatement stmt = null;
/* 3879 */       ResultSet resultSet = null;
/*      */       try
/*      */       {
/* 3882 */         stmt = getConnection().prepareStatement("select calendar_utils.findElement( ?, ? ) from dual");
/* 3883 */         for (int i = 0; i < results.length; i++)
/*      */         {
/* 3885 */           stmt.setInt(1, structureId);
/* 3886 */           stmt.setString(2, visIds[i]);
/* 3887 */           resultSet = stmt.executeQuery();
/* 3888 */           while (resultSet.next())
/*      */           {
/* 3890 */             results[i] = resultSet.getInt(1);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 3896 */         System.err.println(sqle);
/* 3897 */         sqle.printStackTrace();
/* 3898 */         throw new RuntimeException(getEntityName() + " getCalendarBindVariables", sqle);
/*      */       }
/*      */       finally
/*      */       {
/* 3902 */         closeResultSet(resultSet);
/* 3903 */         closeStatement(stmt);
/* 3904 */         closeConnection();
/*      */       }
/*      */ 
/* 3907 */       return results;
/*      */     }
/*      */ 
/*      */     public String getEntityName()
/*      */     {
/* 3912 */       return "CalendaElement";
/*      */     }
/*      */   }

    public Map<String, String> getCalendarRangeDetails(int budgetCycleId) {
        String sql_1 = "select s1.vis_id, s2.vis_id ss2, s3.vis_id from structure_element s1 left JOIN structure_element s2 on s1.parent_id = s2.structure_element_id left JOIN structure_element s3 on s2.parent_id = s3.structure_element_id where s1.structure_element_id = (select PERIOD_ID from BUDGET_CYCLE where BUDGET_CYCLE_ID = " + budgetCycleId + ")";
        String sql_2 = "select s1.vis_id, s2.vis_id ss2, s3.vis_id from structure_element s1 left JOIN structure_element s2 on s1.parent_id = s2.structure_element_id left JOIN structure_element s3 on s2.parent_id = s3.structure_element_id where s1.structure_element_id = (select PERIOD_ID_TO from BUDGET_CYCLE where BUDGET_CYCLE_ID = " + budgetCycleId + ")";

        Map<String, String> map = new HashMap<String, String>();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement(sql_1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                map.put("FROM_1", resultSet.getString(1));
                map.put("FROM_2", resultSet.getString(2));
                map.put("FROM_3", resultSet.getString(3));
            }

            stmt = getConnection().prepareStatement(sql_2);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                map.put("TO_1", resultSet.getString(1));
                map.put("TO_2", resultSet.getString(2));
                map.put("TO_3", resultSet.getString(3));
            }
        } catch (SQLException sqle) {
            throw handleSQLException(sql_1, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        return map;
    }

}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dimension.StructureElementDAO
 * JD-Core Version:    0.6.0
 */