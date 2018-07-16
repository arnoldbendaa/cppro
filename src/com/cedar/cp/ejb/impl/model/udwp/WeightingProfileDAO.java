// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.udwp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.udwp.AllWeightingProfilesELO;
import com.cedar.cp.dto.model.udwp.ProfileImpl;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileLineCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileLinePK;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JdbcUtils;
import com.cedar.cp.util.common.JdbcUtils.ColType;
import com.cedar.cp.util.common.JdbcUtils.RefColType;

public class WeightingProfileDAO extends AbstractDAO {

   Log _log = new Log(this.getClass());
   private static final String SQL_SELECT_COLUMNS = "select WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_PROFILE.MODEL_ID,WEIGHTING_PROFILE.VIS_ID,WEIGHTING_PROFILE.DESCRIPTION,WEIGHTING_PROFILE.START_LEVEL,WEIGHTING_PROFILE.LEAF_LEVEL,WEIGHTING_PROFILE.PROFILE_TYPE,WEIGHTING_PROFILE.DYNAMIC_OFFSET,WEIGHTING_PROFILE.DYNAMIC_DATA_TYPE_ID,WEIGHTING_PROFILE.DYNAMIC_ES_IF_WFBTOZ,WEIGHTING_PROFILE.VERSION_NUM,WEIGHTING_PROFILE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE.UPDATED_TIME,WEIGHTING_PROFILE.CREATED_TIME";
   protected static final String SQL_LOAD = " from WEIGHTING_PROFILE where    PROFILE_ID = ? ";
   protected static final String SQL_CREATE = "insert into WEIGHTING_PROFILE ( PROFILE_ID,MODEL_ID,VIS_ID,DESCRIPTION,START_LEVEL,LEAF_LEVEL,PROFILE_TYPE,DYNAMIC_OFFSET,DYNAMIC_DATA_TYPE_ID,DYNAMIC_ES_IF_WFBTOZ,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String SQL_DUPLICATE_VALUE_CHECK_IDENTIFIER = "select count(*) from WEIGHTING_PROFILE where    MODEL_ID = ? AND VIS_ID = ? and not(    PROFILE_ID = ? )";
   protected static final String SQL_STORE = "update WEIGHTING_PROFILE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,START_LEVEL = ?,LEAF_LEVEL = ?,PROFILE_TYPE = ?,DYNAMIC_OFFSET = ?,DYNAMIC_DATA_TYPE_ID = ?,DYNAMIC_ES_IF_WFBTOZ = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PROFILE_ID = ? AND VERSION_NUM = ?";
   protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from WEIGHTING_PROFILE where PROFILE_ID = ?";
   protected static String SQL_ALL_WEIGHTING_PROFILES = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,WEIGHTING_PROFILE.PROFILE_ID      ,WEIGHTING_PROFILE.VIS_ID      ,WEIGHTING_PROFILE.DESCRIPTION      ,WEIGHTING_PROFILE.PROFILE_TYPE      ,WEIGHTING_PROFILE.START_LEVEL      ,WEIGHTING_PROFILE.LEAF_LEVEL      ,MODEL.VIS_ID from WEIGHTING_PROFILE    ,MODEL where 1=1   and WEIGHTING_PROFILE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID";
   protected static String SQL_ALL_WEIGHTING_PROFILES_FOR_USER = "select 0       ,MODEL.MODEL_ID      ,MODEL.VIS_ID      ,WEIGHTING_PROFILE.PROFILE_ID      ,WEIGHTING_PROFILE.VIS_ID      ,WEIGHTING_PROFILE.DESCRIPTION      ,WEIGHTING_PROFILE.PROFILE_TYPE      ,WEIGHTING_PROFILE.START_LEVEL      ,WEIGHTING_PROFILE.LEAF_LEVEL      ,MODEL.VIS_ID from WEIGHTING_PROFILE    ,MODEL where model.model_id in (select distinct model_id from budget_user where user_id = ?)   and WEIGHTING_PROFILE.MODEL_ID = MODEL.MODEL_ID  order by MODEL.VIS_ID";
   protected static final String SQL_DELETE_BATCH = "delete from WEIGHTING_PROFILE where    PROFILE_ID = ? ";
   private static String[][] SQL_DELETE_CHILDREN = new String[][]{{"WEIGHTING_PROFILE_LINE", "delete from WEIGHTING_PROFILE_LINE where     WEIGHTING_PROFILE_LINE.PROFILE_ID = ? "}, {"WEIGHTING_DEPLOYMENT", "delete from WEIGHTING_DEPLOYMENT where     WEIGHTING_DEPLOYMENT.PROFILE_ID = ? "}};
   private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[][]{{"WEIGHTING_DEPLOYMENT_LINE", "delete from WEIGHTING_DEPLOYMENT_LINE WeightingDeploymentLine where exists (select * from WEIGHTING_DEPLOYMENT_LINE,WEIGHTING_DEPLOYMENT,WEIGHTING_PROFILE where     WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID = WEIGHTING_DEPLOYMENT.DEPLOYMENT_ID and WEIGHTING_DEPLOYMENT.PROFILE_ID = WEIGHTING_PROFILE.PROFILE_ID and WeightingDeploymentLine.DEPLOYMENT_ID = WEIGHTING_DEPLOYMENT_LINE.DEPLOYMENT_ID "}};
   private static String SQL_DELETE_DEPENDANT_CRITERIA = "and WEIGHTING_PROFILE.PROFILE_ID = ?)";
   public static final String SQL_BULK_GET_ALL = " from WEIGHTING_PROFILE where 1=1 and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_PROFILE.PROFILE_ID";
   protected static final String SQL_GET_ALL = " from WEIGHTING_PROFILE where    MODEL_ID = ? ";
   private static final ColType[] QUERY_PROFILES_COL_INFO = new ColType[]{new WeightingProfileRefColType("WeightingProfile", "profile_id", "vis_id"), new ColType("Description", "description", 1), new ColType("Global", "global_dep", 1), new ColType("ProfileType", "profile_type", 0), new ColType("StartLevel", "start_level", 0), new ColType("LeafLevel", "leaf_level", 0), new ColType("DynamicOffset", "dynamic_offset", 0), new ColType("DynamicDataType", "dynamic_data_type", 1)};
   private static final String QUERY_PROFILE_TEMPLATE_SQL = "with params as \n(\n  select ? as model_id, \n         ? as target_start_level, \n         ? as target_leaf_level, \n         ? as acct_se_id, \n         ? as bus_se_id, \n         ? as data_type_vis_id \n  from dual \n),\ntact_se as\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select acct_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, -1 as depth from dual\n),\ntbus_se as\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select bus_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, -1 as depth from dual\n),\ndt as \n(\n  select ldt.data_type_id from data_type ldt \n  where ldt.vis_id = (select data_type_vis_id from params)\n  union all\n  select -1 as data_type_id from dual\n),\nline_det as\n(\n  select wd.profile_id,\n         wd.deployment_id,\n         act_se.position as act_se_position,\n         act_se.end_position as act_se_end_position,\n         act_se.depth as act_se_depth,\n         wdl.account_selection_flag as act_sel_flag,\n         bus_se.position as bus_se_position,\n         bus_se.end_position as bus_se_end_position,\n         bus_se.depth as bus_se_depth,\n         wdl.business_selection_flag as bus_sel_flag,\n         wdl.data_type_id,\n         wd.any_data_type\n  from weighting_profile wp,\n      weighting_deployment wd,\n      weighting_deployment_line wdl,\n      structure_element act_se,\n      structure_element bus_se\n  where wp.model_id = (select model_id from params) and \n      wp.start_level <= (select target_start_level from params) and \n      wp.leaf_level = (select target_leaf_level from params) and\n      wd.profile_id = wp.profile_id and\n      act_se.structure_id (+) = wdl.account_structure_id and\n      act_se.structure_element_id (+) = wdl.account_structure_element_id and\n      bus_se.structure_id (+) = wdl.business_structure_id and\n      bus_se.structure_element_id (+) = wdl.business_structure_element_id and\n      wdl.deployment_id = wd.deployment_id\n)\nselect wp.profile_id,\n       wp.vis_id,\n       wp.description,\n       wd.global_dep,\n       wp.profile_type,\n       wp.start_level,\n       wp.leaf_level,\n       wp.dynamic_offset,\n       decode( wp.dynamic_data_type_id, null, null, \n              (select dt.vis_id \n               from data_type dt \n               where dt.data_type_id = wp.dynamic_data_type_id) ) as dynamic_data_type,\n       max(wd.weighting) as weighting\nfrom weighting_profile wp,\n(\n  select wd.*,\n         case when wd.any_account = \'Y\' and\n                   wd.any_business = \'Y\' and\n                   wd.any_data_type = \'Y\'\n         then \'Y\'\n         else \'N\'\n        end as global_dep\n  from weighting_deployment wd\n) wd,\ntact_se,\ntbus_se,\ndt,\n(   \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.act_se_depth ) as rk\n    from line_det ld,\n         tact_se se\n    where se.position >= ld.act_se_position and \n          se.position <= ld.act_se_end_position \n  ) order by rk desc\n) line_det1,\n(   \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.bus_se_depth ) as rk\n    from line_det ld,\n         tbus_se se\n    where se.position >= ld.bus_se_position and \n          se.position <= ld.bus_se_end_position \n  ) order by rk desc\n) line_det2,\n( select * from line_det ) line_det3\nwhere\n  wp.model_id = (select model_id from params) and\n  wd.profile_id = wp.profile_id and\n  wp.profile_type in (0,1) and\n  wp.start_level <= (select target_start_level from params) and \n  wp.leaf_level = (select target_leaf_level from params) and\n  wd.deployment_id = line_det1.deployment_id (+) and\n  wd.deployment_id = line_det2.deployment_id (+) and\n  wd.deployment_id = line_det3.deployment_id (+) and\n  ( wd.any_account = \'Y\' or line_det1.act_sel_flag = \'Y\' ) and\n  ( wd.any_business = \'Y\' or line_det2.bus_sel_flag = \'Y\' ) and\n  ( wd.any_data_type = \'Y\' or ( dt.data_type_id = line_det3.data_type_id ) )\ngroup by wp.profile_id, wp.vis_id, wp.description, wd.global_dep, wp.profile_type,\n        wp.start_level, wp.leaf_level, wp.dynamic_offset, wp.dynamic_data_type_id\norder by weighting desc, wp.vis_id asc";
   private static final String QUERY_VIREMENT_PROFILES_SQL = "with params as\n(\n  select ? as model_id, \n         ? as bus_s_id, \n         ? as bus_se_id, \n         ? as acct_s_id, \n         ? as acct_se_id, \n         ? as cal_s_id, \n         ? as cal_se_id \n  from dual \n),\nthe_trg_cal_elem as -- The target calendar element\n(\n  select se.cal_elem_type,\n         se.position,\n         se.end_position\n  from structure_element se\n  where se.structure_id = (select cal_s_id from params)\n    and se.structure_element_id = (select cal_se_id from params)\n),\na_trg_cal_leaf_elem as -- Workout the target leaf elem type i.e. Month Weeks Days etc.\n(\n  select se.cal_elem_type\n  from structure_element se\n  where se.structure_id = (select cal_s_id from params)\n    and se.position > (select position from the_trg_cal_elem)\n    and se.end_position <= (select end_position from the_trg_cal_elem)\n    and se.cal_elem_type not in (6,7) -- filter out opening and adjustment types\n    and se.leaf = \'Y\'\n    and rownum = 1 -- Only need the first row\n  order by position\n),\ntact_se as -- Query the target account structure element details\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select acct_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, \n         -1 as depth \n  from dual\n),\ntbus_se as -- Query the target business structure element details\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select bus_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, \n         -1 as depth \n  from dual\n),\nline_det as -- Query required details from weighting deployment lines\n( \n  select wd.profile_id,\n         wd.deployment_id,\n         act_se.position as act_se_position,\n         act_se.end_position as act_se_end_position,\n         act_se.depth as act_se_depth,\n         wdl.account_selection_flag as act_sel_flag,\n         bus_se.position as bus_se_position,\n         bus_se.end_position as bus_se_end_position,\n         bus_se.depth as bus_se_depth,\n         wdl.business_selection_flag as bus_sel_flag,\n         wdl.data_type_id,\n         wd.any_data_type\n  from weighting_profile wp,\n      weighting_deployment wd,\n      weighting_deployment_line wdl,\n      structure_element act_se,\n      structure_element bus_se,\n      params p\n  where wp.model_id = p.model_id and\n      wp.start_level <= (select cal_elem_type from the_trg_cal_elem) and\n      wp.leaf_level = (select cal_elem_type from a_trg_cal_leaf_elem) and\n      wd.profile_id = wp.profile_id and\n      act_se.structure_id (+) = wdl.account_structure_id and\n      act_se.structure_element_id (+) = wdl.account_structure_element_id and\n      bus_se.structure_id (+) = wdl.business_structure_id and\n      bus_se.structure_element_id (+) = wdl.business_structure_element_id and\n      wdl.deployment_id = wd.deployment_id      \n)\nselect wp.profile_id,\n       wp.vis_id,\n       wp.description,\n       wd.global_dep,\n       wp.profile_type,\n       wp.start_level,\n       wp.leaf_level,\n       wp.dynamic_offset,\n       decode( wp.dynamic_data_type_id, null, null,\n              (select dt.vis_id\n               from data_type dt\n               where dt.data_type_id = wp.dynamic_data_type_id) ) as dynamic_data_type,\n       max(wd.weighting) as weighting\nfrom weighting_profile wp,\n(\n  select wd.*,\n         case when wd.any_account = \'Y\' and\n                   wd.any_business = \'Y\' and\n                   wd.any_data_type = \'Y\'\n         then \'Y\'\n         else \'N\'\n        end as global_dep\n  from weighting_deployment wd\n) wd,\n( -- Select the deployment line which is closet to the target acct structure element\n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.act_se_depth ) as rk\n    from line_det ld,\n         tact_se se\n    where se.position >= ld.act_se_position and \n          se.position <= ld.act_se_end_position \n  ) order by rk desc\n) line_det1,\n( -- Select the deployment line which is closet to the target bus. structure element  \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.bus_se_depth ) as rk\n    from line_det ld,\n         tbus_se se\n    where se.position >= ld.bus_se_position and \n          se.position <= ld.bus_se_end_position \n  ) order by rk desc\n) line_det2,\n( select * from line_det ) line_det3\nwhere\n  wp.model_id = (select model_id from params) and\n  wd.profile_id = wp.profile_id and\n  wp.start_level <= (select cal_elem_type from the_trg_cal_elem) and\n  wd.deployment_id = line_det1.deployment_id (+) and\n  wd.deployment_id = line_det2.deployment_id (+) and\n  wd.deployment_id = line_det3.deployment_id (+) and\n  ( wd.any_account = \'Y\' or line_det1.act_sel_flag = \'Y\' ) and\n  ( wd.any_business = \'Y\' or line_det2.bus_sel_flag = \'Y\' ) and\n  ( wd.any_data_type = \'Y\' or\n    ( ? in\n        (select -1 from dual\n         union all\n         select nvl(data_type_id,-1) from line_det ld  where ld.deployment_id = wd.deployment_id )\n         and\n      ? in\n         (select -1 from dual\n          union all\n          select nvl(data_type_id,-1) from line_det ld where ld.deployment_id = wd.deployment_id )\n         ) )\ngroup by wp.profile_id, wp.vis_id, wp.description, wd.global_dep, wp.profile_type,\n        wp.start_level, wp.leaf_level, wp.dynamic_offset, wp.dynamic_data_type_id\norder by weighting desc, wp.vis_id asc";
   private static final String GET_WEIGHTING_PROFILE_SQL = "select weighting from weighting_profile_line where profile_id = ? order by line_idx";
   private static final String GET_WEIGHTING_PROFILE_DETAIL_SQL = "select wpl.weighting, wp.vis_id, wp.profile_type, wp.start_level, wp.leaf_level,        wp.dynamic_offset, wp.dynamic_data_type_id from weighting_profile wp,     weighting_profile_line wpl where wp.profile_id = ? and       wp.profile_id = wpl.profile_id (+)order by line_idx";
   private static final String GET_PROFILE_FOR_CALENDAR_PERIOD = "with params as \n( \n  select ? as target_element_id, \n         profile_id, \n         start_level, \n         leaf_level, \n         vis_id \n  from weighting_profile wp \n  where wp.profile_id = ? \n) \n,target_elem as -- query the target element \n( \n  select se.* \n  from structure_element se \n  where se.structure_element_id = ( select target_element_id from params )\n)\n,profile_root as -- determine the root se of the profile\n(\n  select * from \n  (\n    select se.*\n    from structure_element se\n    start with se.structure_element_id = \n      ( select structure_element_id from target_elem )\n    connect by se.structure_element_id = prior se.parent_id \n  )\n  where cal_elem_type = ( select start_level from params ) \n    and parent_id != 0\n)\n,all_se as -- query se for the whole including open and adjustment elements\n(\n    select rank() over( partition by structure_id order by position asc) idx,\n            se.*\n    from structure_element se\n    where se.structure_id = ( select structure_id from profile_root ) and\n          se.position > ( select position from profile_root  ) and\n          se.position <= ( select end_position from profile_root ) and\n          leaf = \'Y\' \n    order by se.position\n)\n,open_se as -- filter out the opening element if present\n(\n  select ase.*, -1 as pro_index \n  from all_se ase \n  where ase.cal_elem_type = 6\n)\n,normal_leaves as -- filter out the normal leaf elements\n(\n  select ase.*, rownum as pro_index \n  from all_se ase \n  where ase.cal_elem_type not in ( 6, 7 )\n)\n,adj_se as -- filter out the adjustment element if present \n(\n  select ase.*, -1 as pro_index \n  from all_se ase \n  where ase.cal_elem_type = 7\n)\n,cal_se as -- produce a view will pro_index set-up to join to profile lines\n(\n  select * from open_se \n  union all \n  select * from normal_leaves \n  union all \n  select * from adj_se\n) \n,wpl as -- query the weighting profile lines\n( \n  select wpl.line_idx+1 as idx, wpl.weighting, wp.vis_id\n  from   weighting_profile wp,\n         weighting_profile_line wpl\n  where wp.profile_id = (select profile_id from params) and\n        wp.profile_id = wpl.profile_id\n  order by line_idx\n) \n,lines as -- join the profile to the lines and limit to leaves under target cell\n(\n  select se.position, wp.vis_id, wp.profile_type, nvl(wpl.weighting,0) weighting\n  from cal_se se,\n       wpl wpl,\n       weighting_profile wp\n  where se.pro_index = wpl.idx (+) and\n        se.structure_id = ( select structure_id from target_elem ) and\n        se.position > ( select position from target_elem ) and\n        se.position <= ( select end_position from target_elem ) and\n        wp.profile_id = ( select profile_id from params )\n)      \nselect vis_id, profile_type, weighting from lines order by position";
   private static final String QUERY_DYNAMIC_WEIGHTING_FACTORS_TEMPLATE = "with params as\n(\n  select {busParams} \n         ? as acct_se, \n         ? as src_cal_se, \n         ? as trg_cal_se, \n         ? as data_type, \n\t\t  ( select hierarchy_id from hierarchy h where h.dimension_id = ? ) as cal_sid \n  from dual \n) \n,positions as \n( \n   select src_elem.position src_position, \n          src_elem.end_position src_end_position, \n          trg_elem.position trg_position, \n          trg_elem.end_position trg_end_position \n   from structure_element src_elem, \n        structure_element trg_elem \n   where src_elem.structure_id = ( select cal_sid from params ) \n\t  and src_elem.structure_element_id = ( select src_cal_se from params ) \n     and trg_elem.structure_id = ( select cal_sid from params ) \n     and trg_elem.structure_element_id = ( select trg_cal_se from params ) \n) \n,cal_src_leaves as \n( \n  select se.* \n  from structure_element se \n  where se.structure_id = ( select cal_sid from params ) \n   and se.position > (select src_position from positions) \n   and se.end_position <= (select src_end_position from positions) \n   and se.leaf = \'Y\' \n  order by position \n) \n,cal_trg_leaves as \n( \n  select se.* \n  from structure_element se \n  where se.structure_id = ( select cal_sid from params ) \n   and se.position > (select trg_position from positions) \n   and se.end_position <= (select trg_end_position from positions) \n   and se.leaf = \'Y\' \n  order by position \n) \n,src_elems as \n(\n  select {busColSel} \n         acct_se as dim{accountIdx},\n         structure_element_id as dim{calendarIdx}, \n         cal_elem_type,\n         data_type\n  from params,\n       cal_src_leaves \n)\n,trg_elems as\n(\n  select {busColSel} \n         acct_se as dim{accountIdx},\n         structure_element_id as dim{calendarIdx}, \n         cal_elem_type,\n         data_type\n  from params,\n       cal_trg_leaves \n)\nselect {dimColumns} data_type, \n       se.cal_elem_type, nft.public_value/100 as public_value, \'src\' as src_trg \nfrom src_elems se \nleft join nft{financeCubeId} nft \nusing ( {dimColumns} data_type ) \nunion all\nselect {seDimColumns} se.data_type, \n       se.cal_elem_type, null as public_value, \'trg\' as src_trg \nfrom trg_elems se";
   private static final ColType[] QUERY_SES_CAL_ELEM_TYPE_COL_INFO = new ColType[]{new ColType("structure_id", 0), new ColType("structure_element_id", 0), new ColType("vis_id", 1), new ColType("description", 1), new ColType("position", 0), new ColType("depth", 0), new ColType("cal_elem_type", 0)};
   private static final String QUERY_SES_BY_CAL_ELEM_TYPE = "with params as \n( \n  select ? structure_id, \n         ? as structure_element_id \n  from dual \n), \ntrg_cal_elem as \n(\n  select se.* \n  from structure_element se \n  where se.structure_id = (select structure_id from params) \n    and se.structure_element_id = (select structure_element_id from params) \n) \nselect * \nfrom structure_element se, \n     params p \nwhere se.structure_id = p.structure_id \n  and se.cal_elem_type = (select cal_elem_type from trg_cal_elem) \norder by se.position ";
   private static String QUERY_CAL_LEAF_LEVEL_FOR_ELEMENT = "with params as \n(\n  select ? as structure_id, \n         ? as structure_element_id \n  from dual \n),\ntrg_cal_elem as \n( \n  select se.* \n  from structure_element se \n  where se.structure_id = (select structure_id from params) \n    and se.structure_element_id = (select structure_element_id from params) \n) \nselect distinct se.cal_elem_type \nfrom structure_element se, \n     params p \nwhere se.structure_id = p.structure_id \n  and se.position > (select position from trg_cal_elem) \n  and se.position <= (select end_position from trg_cal_elem) \n  and se.leaf = \'Y\' \n  and se.cal_elem_type not in (6,7)";
   protected WeightingProfileLineDAO mWeightingProfileLineDAO;
   protected WeightingDeploymentDAO mWeightingDeploymentDAO;
   protected WeightingProfileEVO mDetails;


   public WeightingProfileDAO(Connection connection) {
      super(connection);
   }

   public WeightingProfileDAO() {}

   public WeightingProfileDAO(DataSource ds) {
      super(ds);
   }

   protected WeightingProfilePK getPK() {
      return this.mDetails.getPK();
   }

   public void setDetails(WeightingProfileEVO details) {
      this.mDetails = details.deepClone();
   }

/*      */   private WeightingProfileEVO getEvoFromJdbc(ResultSet resultSet_)
   /*      */     throws SQLException
   /*      */   {
   /*  118 */     int col = 1;
   /*  119 */     WeightingProfileEVO evo = new WeightingProfileEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), getWrappedIntegerFromJdbc(resultSet_, col++), getWrappedBooleanFromJdbc(resultSet_, col++), resultSet_.getInt(col++), null, null);
   /*      */ 
   /*  135 */     evo.setUpdatedByUserId(resultSet_.getInt(col++));
   /*  136 */     evo.setUpdatedTime(resultSet_.getTimestamp(col++));
   /*  137 */     evo.setCreatedTime(resultSet_.getTimestamp(col++));
   /*  138 */     return evo;
   /*      */   }

   private int putEvoKeysToJdbc(WeightingProfileEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
      int col = startCol_ + 1;
      stmt_.setInt(startCol_, evo_.getProfileId());
      return col;
   }

   private int putEvoDataToJdbc(WeightingProfileEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
      int col = startCol_ + 1;
      stmt_.setInt(startCol_, evo_.getModelId());
      stmt_.setString(col++, evo_.getVisId());
      stmt_.setString(col++, evo_.getDescription());
      stmt_.setInt(col++, evo_.getStartLevel());
      stmt_.setInt(col++, evo_.getLeafLevel());
      stmt_.setInt(col++, evo_.getProfileType());
      stmt_.setInt(col++, evo_.getDynamicOffset());
      this.setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDynamicDataTypeId());
      this.setWrappedPrimitiveToJdbc(stmt_, col++, evo_.getDynamicEsIfWfbtoz());
      stmt_.setInt(col++, evo_.getVersionNum());
      stmt_.setInt(col++, evo_.getUpdatedByUserId());
      stmt_.setTimestamp(col++, evo_.getUpdatedTime());
      stmt_.setTimestamp(col++, evo_.getCreatedTime());
      return col;
   }

   protected void doLoad(WeightingProfilePK pk) throws ValidationException {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      try {
         stmt = this.getConnection().prepareStatement("select WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_PROFILE.MODEL_ID,WEIGHTING_PROFILE.VIS_ID,WEIGHTING_PROFILE.DESCRIPTION,WEIGHTING_PROFILE.START_LEVEL,WEIGHTING_PROFILE.LEAF_LEVEL,WEIGHTING_PROFILE.PROFILE_TYPE,WEIGHTING_PROFILE.DYNAMIC_OFFSET,WEIGHTING_PROFILE.DYNAMIC_DATA_TYPE_ID,WEIGHTING_PROFILE.DYNAMIC_ES_IF_WFBTOZ,WEIGHTING_PROFILE.VERSION_NUM,WEIGHTING_PROFILE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE.UPDATED_TIME,WEIGHTING_PROFILE.CREATED_TIME from WEIGHTING_PROFILE where    PROFILE_ID = ? ");
         byte sqle = 1;
         byte var10001 = sqle;
         int sqle1 = sqle + 1;
         stmt.setInt(var10001, pk.getProfileId());
         resultSet = stmt.executeQuery();
         if(!resultSet.next()) {
            throw new ValidationException(this.getEntityName() + " select of " + pk + " not found");
         }

         this.mDetails = this.getEvoFromJdbc(resultSet);
         if(this.mDetails.isModified()) {
            this._log.info("doLoad", this.mDetails);
         }
      } catch (SQLException var9) {
         throw this.handleSQLException(pk, "select WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_PROFILE.MODEL_ID,WEIGHTING_PROFILE.VIS_ID,WEIGHTING_PROFILE.DESCRIPTION,WEIGHTING_PROFILE.START_LEVEL,WEIGHTING_PROFILE.LEAF_LEVEL,WEIGHTING_PROFILE.PROFILE_TYPE,WEIGHTING_PROFILE.DYNAMIC_OFFSET,WEIGHTING_PROFILE.DYNAMIC_DATA_TYPE_ID,WEIGHTING_PROFILE.DYNAMIC_ES_IF_WFBTOZ,WEIGHTING_PROFILE.VERSION_NUM,WEIGHTING_PROFILE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE.UPDATED_TIME,WEIGHTING_PROFILE.CREATED_TIME from WEIGHTING_PROFILE where    PROFILE_ID = ? ", var9);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("doLoad", pk);
         }

      }

   }

   protected void doCreate() throws DuplicateNameValidationException, ValidationException {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      this.mDetails.postCreateInit();
      PreparedStatement stmt = null;

      try {
         this.duplicateValueCheckIdentifier();
         this.mDetails.setCreatedTime(new Timestamp((new Date()).getTime()));
         this.mDetails.setUpdatedTime(new Timestamp((new Date()).getTime()));
         stmt = this.getConnection().prepareStatement("insert into WEIGHTING_PROFILE ( PROFILE_ID,MODEL_ID,VIS_ID,DESCRIPTION,START_LEVEL,LEAF_LEVEL,PROFILE_TYPE,DYNAMIC_OFFSET,DYNAMIC_DATA_TYPE_ID,DYNAMIC_ES_IF_WFBTOZ,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
         byte e = 1;
         int e1 = this.putEvoKeysToJdbc(this.mDetails, stmt, e);
         this.putEvoDataToJdbc(this.mDetails, stmt, e1);
         int resultCount = stmt.executeUpdate();
         if(resultCount != 1) {
            throw new RuntimeException(this.getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
         }

         this.mDetails.reset();
      } catch (SQLException var10) {
         throw this.handleSQLException(this.mDetails.getPK(), "insert into WEIGHTING_PROFILE ( PROFILE_ID,MODEL_ID,VIS_ID,DESCRIPTION,START_LEVEL,LEAF_LEVEL,PROFILE_TYPE,DYNAMIC_OFFSET,DYNAMIC_DATA_TYPE_ID,DYNAMIC_ES_IF_WFBTOZ,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)", var10);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("doCreate", this.mDetails.toString());
         }

      }

      try {
         this.getWeightingProfileLineDAO().update(this.mDetails.getWeightingLinesMap());
         this.getWeightingDeploymentDAO().update(this.mDetails.getWeightingDeploymentsMap());
      } catch (Exception var9) {
         throw new RuntimeException("unexpected exception", var9);
      }
   }

   protected void duplicateValueCheckIdentifier() throws DuplicateNameValidationException {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      try {
         stmt = this.getConnection().prepareStatement("select count(*) from WEIGHTING_PROFILE where    MODEL_ID = ? AND VIS_ID = ? and not(    PROFILE_ID = ? )");
         byte sqle = 1;
         int var12 = sqle + 1;
         stmt.setInt(sqle, this.mDetails.getModelId());
         stmt.setString(var12++, this.mDetails.getVisId());
         this.putEvoKeysToJdbc(this.mDetails, stmt, var12);
         resultSet = stmt.executeQuery();
         if(!resultSet.next()) {
            throw new RuntimeException(this.getEntityName() + " select of " + this.getPK() + " not found");
         }

         sqle = 1;
         var12 = sqle + 1;
         int count = resultSet.getInt(sqle);
         if(count > 0) {
            throw new DuplicateNameValidationException(this.getEntityName() + " " + this.getPK() + " Identifier");
         }
      } catch (SQLException var9) {
         throw this.handleSQLException(this.getPK(), "select count(*) from WEIGHTING_PROFILE where    MODEL_ID = ? AND VIS_ID = ? and not(    PROFILE_ID = ? )", var9);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("duplicateValueCheckIdentifier", "");
         }

      }

   }

   protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      boolean mainChanged = this.mDetails.isModified();
      boolean dependantChanged = false;

      try {
         if(mainChanged) {
            this.duplicateValueCheckIdentifier();
         }

         if(this.getWeightingProfileLineDAO().update(this.mDetails.getWeightingLinesMap())) {
            dependantChanged = true;
         }

         if(this.getWeightingDeploymentDAO().update(this.mDetails.getWeightingDeploymentsMap())) {
            dependantChanged = true;
         }

         if(mainChanged || dependantChanged) {
            this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
            this.mDetails.setUpdatedTime(new Timestamp((new Date()).getTime()));
            stmt = this.getConnection().prepareStatement("update WEIGHTING_PROFILE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,START_LEVEL = ?,LEAF_LEVEL = ?,PROFILE_TYPE = ?,DYNAMIC_OFFSET = ?,DYNAMIC_DATA_TYPE_ID = ?,DYNAMIC_ES_IF_WFBTOZ = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PROFILE_ID = ? AND VERSION_NUM = ?");
            byte sqle = 1;
            int var12 = this.putEvoDataToJdbc(this.mDetails, stmt, sqle);
            var12 = this.putEvoKeysToJdbc(this.mDetails, stmt, var12);
            stmt.setInt(var12++, this.mDetails.getVersionNum() - 1);
            int resultCount = stmt.executeUpdate();
            if(resultCount == 0) {
               this.checkVersionNum();
            }

            if(resultCount != 1) {
               throw new RuntimeException(this.getEntityName() + " update failed (" + this.getPK() + "): resultCount=" + resultCount);
            }

            this.mDetails.reset();
         }
      } catch (SQLException var10) {
         throw this.handleSQLException(this.getPK(), "update WEIGHTING_PROFILE set MODEL_ID = ?,VIS_ID = ?,DESCRIPTION = ?,START_LEVEL = ?,LEAF_LEVEL = ?,PROFILE_TYPE = ?,DYNAMIC_OFFSET = ?,DYNAMIC_DATA_TYPE_ID = ?,DYNAMIC_ES_IF_WFBTOZ = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    PROFILE_ID = ? AND VERSION_NUM = ?", var10);
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null && (mainChanged || dependantChanged)) {
            timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
         }

      }

   }

   private void checkVersionNum() throws VersionValidationException {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      try {
         stmt = this.getConnection().prepareStatement("select VERSION_NUM from WEIGHTING_PROFILE where PROFILE_ID = ?");
         byte sqle = 1;
         byte var10001 = sqle;
         int sqle1 = sqle + 1;
         stmt.setInt(var10001, this.mDetails.getProfileId());
         resultSet = stmt.executeQuery();
         if(!resultSet.next()) {
            throw new RuntimeException(this.getEntityName() + " checkVersionNum: select of " + this.getPK() + " not found");
         }

         sqle = 1;
         var10001 = sqle;
         sqle1 = sqle + 1;
         int dbVersionNumber = resultSet.getInt(var10001);
         if(this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
            throw new VersionValidationException(this.getEntityName() + " " + this.getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
         }
      } catch (SQLException var9) {
         throw this.handleSQLException(this.getPK(), "select VERSION_NUM from WEIGHTING_PROFILE where PROFILE_ID = ?", var9);
      } finally {
         this.closeStatement(stmt);
         this.closeResultSet(resultSet);
         if(timer != null) {
            timer.logDebug("checkVersionNum", this.mDetails.getPK());
         }

      }

   }

   /*      */   public AllWeightingProfilesELO getAllWeightingProfiles()
   /*      */   {
   /*  552 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
   /*  553 */     PreparedStatement stmt = null;
   /*  554 */     ResultSet resultSet = null;
   /*  555 */     AllWeightingProfilesELO results = new AllWeightingProfilesELO();
   /*      */     try
   /*      */     {
   /*  558 */       stmt = getConnection().prepareStatement(SQL_ALL_WEIGHTING_PROFILES);
   /*  559 */       int col = 1;
   /*  560 */       resultSet = stmt.executeQuery();
   /*  561 */       while (resultSet.next())
   /*      */       {
   /*  563 */         col = 2;
   /*      */ 
   /*  566 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
   /*      */ 
   /*  569 */         String textModel = resultSet.getString(col++);
   /*      */ 
   /*  572 */         WeightingProfilePK pkWeightingProfile = new WeightingProfilePK(resultSet.getInt(col++));
   /*      */ 
   /*  575 */         String textWeightingProfile = resultSet.getString(col++);
   /*      */ 
   /*  580 */         WeightingProfileCK ckWeightingProfile = new WeightingProfileCK(pkModel, pkWeightingProfile);
   /*      */ 
   /*  586 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
   /*      */ 
   /*  592 */         WeightingProfileRefImpl erWeightingProfile = new WeightingProfileRefImpl(ckWeightingProfile, textWeightingProfile);
   /*      */ 
   /*  597 */         String col1 = resultSet.getString(col++);
   /*  598 */         int col2 = resultSet.getInt(col++);
   /*  599 */         int col3 = resultSet.getInt(col++);
   /*  600 */         int col4 = resultSet.getInt(col++);
   /*  601 */         String col5 = resultSet.getString(col++);
   /*      */ 
   /*  604 */         results.add(erWeightingProfile, erModel, col1, col2, col3, col4, col5);
   /*      */       }
   /*      */ 
   /*      */     }
   /*      */     catch (SQLException sqle)
   /*      */     {
   /*  617 */       throw handleSQLException(SQL_ALL_WEIGHTING_PROFILES, sqle);
   /*      */     }
   /*      */     finally
   /*      */     {
   /*  621 */       closeResultSet(resultSet);
   /*  622 */       closeStatement(stmt);
   /*  623 */       closeConnection();
   /*      */     }
   /*      */ 
   /*  626 */     if (timer != null) {
   /*  627 */       timer.logDebug("getAllWeightingProfiles", " items=" + results.size());
   /*      */     }
   /*      */ 
   /*  631 */     return results;
   /*      */   }

   public boolean update(Map items) throws DuplicateNameValidationException, VersionValidationException, ValidationException {
      if(items == null) {
         return false;
      } else {
         Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
         PreparedStatement deleteStmt = null;
         boolean somethingChanged = false;

         try {
            Iterator sqle = (new ArrayList(items.values())).iterator();

            while(sqle.hasNext()) {
               this.mDetails = (WeightingProfileEVO)sqle.next();
               if(this.mDetails.deletePending()) {
                  somethingChanged = true;
                  this.deleteDependants(this.mDetails.getPK());
               }
            }

            Iterator iter2 = (new ArrayList(items.values())).iterator();

            while(iter2.hasNext()) {
               this.mDetails = (WeightingProfileEVO)iter2.next();
               if(this.mDetails.deletePending()) {
                  somethingChanged = true;
                  if(deleteStmt == null) {
                     deleteStmt = this.getConnection().prepareStatement("delete from WEIGHTING_PROFILE where    PROFILE_ID = ? ");
                  }

                  byte iter1 = 1;
                  byte var10001 = iter1;
                  int iter11 = iter1 + 1;
                  deleteStmt.setInt(var10001, this.mDetails.getProfileId());
                  if(this._log.isDebugEnabled()) {
                     this._log.debug("update", "WeightingProfile deleting ProfileId=" + this.mDetails.getProfileId());
                  }

                  deleteStmt.addBatch();
                  items.remove(this.mDetails.getPK());
               }
            }

            if(deleteStmt != null) {
               Timer iter13 = this._log.isDebugEnabled()?new Timer(this._log):null;
               deleteStmt.executeBatch();
               if(iter13 != null) {
                  iter13.logDebug("update", "delete batch");
               }
            }

            Iterator iter12 = items.values().iterator();

            while(iter12.hasNext()) {
               this.mDetails = (WeightingProfileEVO)iter12.next();
               if(this.mDetails.insertPending()) {
                  somethingChanged = true;
                  this.doCreate();
               } else if(this.mDetails.isModified()) {
                  somethingChanged = true;
                  this.doStore();
               } else if(!this.mDetails.deletePending()) {
                  if(this.getWeightingProfileLineDAO().update(this.mDetails.getWeightingLinesMap())) {
                     somethingChanged = true;
                  }

                  if(this.getWeightingDeploymentDAO().update(this.mDetails.getWeightingDeploymentsMap())) {
                     somethingChanged = true;
                  }
               }
            }

            boolean var8 = somethingChanged;
            return var8;
         } catch (SQLException var12) {
            throw this.handleSQLException("delete from WEIGHTING_PROFILE where    PROFILE_ID = ? ", var12);
         } finally {
            if(deleteStmt != null) {
               this.closeStatement(deleteStmt);
               this.closeConnection();
            }

            this.mDetails = null;
            if(somethingChanged && timer != null) {
               timer.logDebug("update", "collection");
            }

         }
      }
   }

   private void deleteDependants(WeightingProfilePK pk) {
      Set emptyStrings = Collections.emptySet();
      this.deleteDependants(pk, emptyStrings);
   }

   private void deleteDependants(WeightingProfilePK pk, Set<String> exclusionTables) {
      int i;
      Timer timer;
      PreparedStatement stmt;
      int var23;
      int resultCount;
      String s;
      byte sqle;
      for(i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; --i) {
         if(!exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0])) {
            timer = this._log.isDebugEnabled()?new Timer(this._log):null;
            stmt = null;
            resultCount = 0;
            s = null;

            try {
               s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
               if(this._log.isDebugEnabled()) {
                  this._log.debug("deleteDependants", s);
               }

               stmt = this.getConnection().prepareStatement(s);
               sqle = 1;
               var23 = sqle + 1;
               stmt.setInt(sqle, pk.getProfileId());
               resultCount = stmt.executeUpdate();
            } catch (SQLException var20) {
               throw this.handleSQLException(pk, s, var20);
            } finally {
               this.closeStatement(stmt);
               this.closeConnection();
               if(timer != null) {
                  timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
               }

            }
         }
      }

      for(i = SQL_DELETE_CHILDREN.length - 1; i > -1; --i) {
         if(!exclusionTables.contains(SQL_DELETE_CHILDREN[i][0])) {
            timer = this._log.isDebugEnabled()?new Timer(this._log):null;
            stmt = null;
            resultCount = 0;
            s = null;

            try {
               s = SQL_DELETE_CHILDREN[i][1];
               if(this._log.isDebugEnabled()) {
                  this._log.debug("deleteDependants", s);
               }

               stmt = this.getConnection().prepareStatement(s);
               sqle = 1;
               var23 = sqle + 1;
               stmt.setInt(sqle, pk.getProfileId());
               resultCount = stmt.executeUpdate();
            } catch (SQLException var19) {
               throw this.handleSQLException(pk, s, var19);
            } finally {
               this.closeStatement(stmt);
               this.closeConnection();
               if(timer != null) {
                  timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
               }

            }
         }
      }

   }

   public void bulkGetAll(ModelPK entityPK, ModelEVO owningEVO, String dependants) {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;
      int itemCount = 0;
      ArrayList theseItems = new ArrayList();
      owningEVO.setUserDefinedWeightingProfiles(theseItems);
      owningEVO.setUserDefinedWeightingProfilesAllItemsLoaded(true);

      try {
         stmt = this.getConnection().prepareStatement("select WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_PROFILE.MODEL_ID,WEIGHTING_PROFILE.VIS_ID,WEIGHTING_PROFILE.DESCRIPTION,WEIGHTING_PROFILE.START_LEVEL,WEIGHTING_PROFILE.LEAF_LEVEL,WEIGHTING_PROFILE.PROFILE_TYPE,WEIGHTING_PROFILE.DYNAMIC_OFFSET,WEIGHTING_PROFILE.DYNAMIC_DATA_TYPE_ID,WEIGHTING_PROFILE.DYNAMIC_ES_IF_WFBTOZ,WEIGHTING_PROFILE.VERSION_NUM,WEIGHTING_PROFILE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE.UPDATED_TIME,WEIGHTING_PROFILE.CREATED_TIME from WEIGHTING_PROFILE where 1=1 and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_PROFILE.PROFILE_ID");
         byte sqle = 1;
         int var15 = sqle + 1;
         stmt.setInt(sqle, entityPK.getModelId());
         resultSet = stmt.executeQuery();

         while(resultSet.next()) {
            ++itemCount;
            this.mDetails = this.getEvoFromJdbc(resultSet);
            theseItems.add(this.mDetails);
         }

         if(timer != null) {
            timer.logDebug("bulkGetAll", "items=" + itemCount);
         }

         if(itemCount > 0 && dependants.indexOf("<38>") > -1) {
            this.getWeightingProfileLineDAO().bulkGetAll(entityPK, theseItems, dependants);
         }

         if(itemCount > 0 && dependants.indexOf("<39>") > -1) {
            this.getWeightingDeploymentDAO().bulkGetAll(entityPK, theseItems, dependants);
         }
      } catch (SQLException var13) {
         throw this.handleSQLException("select WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_PROFILE.MODEL_ID,WEIGHTING_PROFILE.VIS_ID,WEIGHTING_PROFILE.DESCRIPTION,WEIGHTING_PROFILE.START_LEVEL,WEIGHTING_PROFILE.LEAF_LEVEL,WEIGHTING_PROFILE.PROFILE_TYPE,WEIGHTING_PROFILE.DYNAMIC_OFFSET,WEIGHTING_PROFILE.DYNAMIC_DATA_TYPE_ID,WEIGHTING_PROFILE.DYNAMIC_ES_IF_WFBTOZ,WEIGHTING_PROFILE.VERSION_NUM,WEIGHTING_PROFILE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE.UPDATED_TIME,WEIGHTING_PROFILE.CREATED_TIME from WEIGHTING_PROFILE where 1=1 and WEIGHTING_PROFILE.MODEL_ID = ? order by  WEIGHTING_PROFILE.PROFILE_ID", var13);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         this.mDetails = null;
      }

   }

   public Collection getAll(int selectModelId, String dependants, Collection currentList) {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;
      ArrayList items = new ArrayList();

      try {
         stmt = this.getConnection().prepareStatement("select WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_PROFILE.MODEL_ID,WEIGHTING_PROFILE.VIS_ID,WEIGHTING_PROFILE.DESCRIPTION,WEIGHTING_PROFILE.START_LEVEL,WEIGHTING_PROFILE.LEAF_LEVEL,WEIGHTING_PROFILE.PROFILE_TYPE,WEIGHTING_PROFILE.DYNAMIC_OFFSET,WEIGHTING_PROFILE.DYNAMIC_DATA_TYPE_ID,WEIGHTING_PROFILE.DYNAMIC_ES_IF_WFBTOZ,WEIGHTING_PROFILE.VERSION_NUM,WEIGHTING_PROFILE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE.UPDATED_TIME,WEIGHTING_PROFILE.CREATED_TIME from WEIGHTING_PROFILE where    MODEL_ID = ? ");
         byte sqle = 1;
         byte var10001 = sqle;
         int sqle1 = sqle + 1;
         stmt.setInt(var10001, selectModelId);
         resultSet = stmt.executeQuery();

         while(resultSet.next()) {
            this.mDetails = this.getEvoFromJdbc(resultSet);
            this.getDependants(this.mDetails, dependants);
            items.add(this.mDetails);
         }

         if(currentList != null) {
            ListIterator iter = items.listIterator();
            WeightingProfileEVO currentEVO = null;
            WeightingProfileEVO newEVO = null;

            Iterator iter2;
            while(iter.hasNext()) {
               newEVO = (WeightingProfileEVO)iter.next();
               iter2 = currentList.iterator();

               while(iter2.hasNext()) {
                  currentEVO = (WeightingProfileEVO)iter2.next();
                  if(currentEVO.getPK().equals(newEVO.getPK())) {
                     iter.set(currentEVO);
                     break;
                  }
               }
            }

            iter2 = currentList.iterator();

            while(iter2.hasNext()) {
               currentEVO = (WeightingProfileEVO)iter2.next();
               if(currentEVO.insertPending()) {
                  items.add(currentEVO);
               }
            }
         }

         this.mDetails = null;
      } catch (SQLException var16) {
         throw this.handleSQLException("select WEIGHTING_PROFILE.PROFILE_ID,WEIGHTING_PROFILE.MODEL_ID,WEIGHTING_PROFILE.VIS_ID,WEIGHTING_PROFILE.DESCRIPTION,WEIGHTING_PROFILE.START_LEVEL,WEIGHTING_PROFILE.LEAF_LEVEL,WEIGHTING_PROFILE.PROFILE_TYPE,WEIGHTING_PROFILE.DYNAMIC_OFFSET,WEIGHTING_PROFILE.DYNAMIC_DATA_TYPE_ID,WEIGHTING_PROFILE.DYNAMIC_ES_IF_WFBTOZ,WEIGHTING_PROFILE.VERSION_NUM,WEIGHTING_PROFILE.UPDATED_BY_USER_ID,WEIGHTING_PROFILE.UPDATED_TIME,WEIGHTING_PROFILE.CREATED_TIME from WEIGHTING_PROFILE where    MODEL_ID = ? ", var16);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("getAll", " ModelId=" + selectModelId + " items=" + items.size());
         }

      }

      return items;
   }

   public WeightingProfileEVO getDetails(ModelCK paramCK, String dependants) throws ValidationException {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      if(this.mDetails == null) {
         this.doLoad(((WeightingProfileCK)paramCK).getWeightingProfilePK());
      } else if(!this.mDetails.getPK().equals(((WeightingProfileCK)paramCK).getWeightingProfilePK())) {
         this.doLoad(((WeightingProfileCK)paramCK).getWeightingProfilePK());
      }

      if(dependants.indexOf("<38>") > -1 && !this.mDetails.isWeightingLinesAllItemsLoaded()) {
         this.mDetails.setWeightingLines(this.getWeightingProfileLineDAO().getAll(this.mDetails.getProfileId(), dependants, this.mDetails.getWeightingLines()));
         this.mDetails.setWeightingLinesAllItemsLoaded(true);
      }

      if(dependants.indexOf("<39>") > -1 && !this.mDetails.isWeightingDeploymentsAllItemsLoaded()) {
         this.mDetails.setWeightingDeployments(this.getWeightingDeploymentDAO().getAll(this.mDetails.getProfileId(), dependants, this.mDetails.getWeightingDeployments()));
         this.mDetails.setWeightingDeploymentsAllItemsLoaded(true);
      }

      if(paramCK instanceof WeightingProfileLineCK) {
         if(this.mDetails.getWeightingLines() == null) {
            this.mDetails.loadWeightingLinesItem(this.getWeightingProfileLineDAO().getDetails(paramCK, dependants));
         } else {
            WeightingProfileLinePK details = ((WeightingProfileLineCK)paramCK).getWeightingProfileLinePK();
            WeightingProfileLineEVO evo = this.mDetails.getWeightingLinesItem(details);
            if(evo == null) {
               this.mDetails.loadWeightingLinesItem(this.getWeightingProfileLineDAO().getDetails(paramCK, dependants));
            }
         }
      } else if(paramCK instanceof WeightingDeploymentCK) {
         if(this.mDetails.getWeightingDeployments() == null) {
            this.mDetails.loadWeightingDeploymentsItem(this.getWeightingDeploymentDAO().getDetails(paramCK, dependants));
         } else {
            WeightingDeploymentPK details1 = ((WeightingDeploymentCK)paramCK).getWeightingDeploymentPK();
            WeightingDeploymentEVO evo1 = this.mDetails.getWeightingDeploymentsItem(details1);
            if(evo1 == null) {
               this.mDetails.loadWeightingDeploymentsItem(this.getWeightingDeploymentDAO().getDetails(paramCK, dependants));
            } else {
               this.getWeightingDeploymentDAO().getDetails(paramCK, evo1, dependants);
            }
         }
      }

      new WeightingProfileEVO();
      WeightingProfileEVO details2 = this.mDetails.deepClone();
      if(timer != null) {
         timer.logDebug("getDetails", paramCK + " " + dependants);
      }

      return details2;
   }

   public WeightingProfileEVO getDetails(ModelCK paramCK, WeightingProfileEVO paramEVO, String dependants) throws ValidationException {
      WeightingProfileEVO savedEVO = this.mDetails;
      this.mDetails = paramEVO;
      WeightingProfileEVO newEVO = this.getDetails(paramCK, dependants);
      this.mDetails = savedEVO;
      return newEVO;
   }

   public WeightingProfileEVO getDetails(String dependants) throws ValidationException {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      this.getDependants(this.mDetails, dependants);
      WeightingProfileEVO details = this.mDetails.deepClone();
      if(timer != null) {
         timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
      }

      return details;
   }

   protected WeightingProfileLineDAO getWeightingProfileLineDAO() {
      if(this.mWeightingProfileLineDAO == null) {
         if(this.mDataSource != null) {
            this.mWeightingProfileLineDAO = new WeightingProfileLineDAO(this.mDataSource);
         } else {
            this.mWeightingProfileLineDAO = new WeightingProfileLineDAO(this.getConnection());
         }
      }

      return this.mWeightingProfileLineDAO;
   }

   protected WeightingDeploymentDAO getWeightingDeploymentDAO() {
      if(this.mWeightingDeploymentDAO == null) {
         if(this.mDataSource != null) {
            this.mWeightingDeploymentDAO = new WeightingDeploymentDAO(this.mDataSource);
         } else {
            this.mWeightingDeploymentDAO = new WeightingDeploymentDAO(this.getConnection());
         }
      }

      return this.mWeightingDeploymentDAO;
   }

   public String getEntityName() {
      return "WeightingProfile";
   }

   /*      */   public WeightingProfileRefImpl getRef(WeightingProfilePK paramWeightingProfilePK)
   /*      */   {
   /* 1237 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
   /* 1238 */     PreparedStatement stmt = null;
   /* 1239 */     ResultSet resultSet = null;
   /*      */     try
   /*      */     {
   /* 1242 */       stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID,WEIGHTING_PROFILE.VIS_ID from WEIGHTING_PROFILE,MODEL where 1=1 and WEIGHTING_PROFILE.PROFILE_ID = ? and WEIGHTING_PROFILE.MODEL_ID = MODEL.MODEL_ID");
   /* 1243 */       int col = 1;
   /* 1244 */       stmt.setInt(col++, paramWeightingProfilePK.getProfileId());
   /*      */ 
   /* 1246 */       resultSet = stmt.executeQuery();
   /*      */ 
   /* 1248 */       if (!resultSet.next()) {
   /* 1249 */         throw new RuntimeException(getEntityName() + " getRef " + paramWeightingProfilePK + " not found");
   /*      */       }
   /* 1251 */       col = 2;
   /* 1252 */       ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));
   /*      */ 
   /* 1256 */       String textWeightingProfile = resultSet.getString(col++);
   /* 1257 */       WeightingProfileCK ckWeightingProfile = new WeightingProfileCK(newModelPK, paramWeightingProfilePK);
   /*      */ 
   /* 1262 */       WeightingProfileRefImpl localWeightingProfileRefImpl = new WeightingProfileRefImpl(ckWeightingProfile, textWeightingProfile);
   /*      */       return localWeightingProfileRefImpl;
   /*      */     }
   /*      */     catch (SQLException sqle)
   /*      */     {
   /* 1267 */       throw handleSQLException(paramWeightingProfilePK, "select 0,MODEL.MODEL_ID,WEIGHTING_PROFILE.VIS_ID from WEIGHTING_PROFILE,MODEL where 1=1 and WEIGHTING_PROFILE.PROFILE_ID = ? and WEIGHTING_PROFILE.MODEL_ID = MODEL.MODEL_ID", sqle);
   /*      */     }
   /*      */     finally
   /*      */     {
   /* 1271 */       closeResultSet(resultSet);
   /* 1272 */       closeStatement(stmt);
   /* 1273 */       closeConnection();
   /*      */ 
   /* 1275 */       if (timer != null)
   /* 1276 */         timer.logDebug("getRef", paramWeightingProfilePK); 
   /* 1276 */     }
   /*      */   }

   public void getDependants(Collection c, String dependants) {
      if(c != null) {
         Iterator iter = c.iterator();

         while(iter.hasNext()) {
            WeightingProfileEVO evo = (WeightingProfileEVO)iter.next();
            this.getDependants(evo, dependants);
         }

      }
   }

   public void getDependants(WeightingProfileEVO evo, String dependants) {
      if(!evo.insertPending()) {
         if(evo.getProfileId() >= 1) {
            if(dependants.indexOf("<38>") > -1 && !evo.isWeightingLinesAllItemsLoaded()) {
               evo.setWeightingLines(this.getWeightingProfileLineDAO().getAll(evo.getProfileId(), dependants, evo.getWeightingLines()));
               evo.setWeightingLinesAllItemsLoaded(true);
            }

            if(dependants.indexOf("<39>") > -1 || dependants.indexOf("<40>") > -1) {
               if(!evo.isWeightingDeploymentsAllItemsLoaded()) {
                  evo.setWeightingDeployments(this.getWeightingDeploymentDAO().getAll(evo.getProfileId(), dependants, evo.getWeightingDeployments()));
                  evo.setWeightingDeploymentsAllItemsLoaded(true);
               }

               this.getWeightingDeploymentDAO().getDependants(evo.getWeightingDeployments(), dependants);
            }

         }
      }
   }

   public EntityList queryProfiles(int modelId, int fromLevel, int toLevel, int acctStructureElementId, int busStructureElementId, String dataTypeId) {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      EntityListImpl var11;
      try {
         stmt = this.getConnection().prepareStatement("with params as \n(\n  select ? as model_id, \n         ? as target_start_level, \n         ? as target_leaf_level, \n         ? as acct_se_id, \n         ? as bus_se_id, \n         ? as data_type_vis_id \n  from dual \n),\ntact_se as\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select acct_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, -1 as depth from dual\n),\ntbus_se as\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select bus_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, -1 as depth from dual\n),\ndt as \n(\n  select ldt.data_type_id from data_type ldt \n  where ldt.vis_id = (select data_type_vis_id from params)\n  union all\n  select -1 as data_type_id from dual\n),\nline_det as\n(\n  select wd.profile_id,\n         wd.deployment_id,\n         act_se.position as act_se_position,\n         act_se.end_position as act_se_end_position,\n         act_se.depth as act_se_depth,\n         wdl.account_selection_flag as act_sel_flag,\n         bus_se.position as bus_se_position,\n         bus_se.end_position as bus_se_end_position,\n         bus_se.depth as bus_se_depth,\n         wdl.business_selection_flag as bus_sel_flag,\n         wdl.data_type_id,\n         wd.any_data_type\n  from weighting_profile wp,\n      weighting_deployment wd,\n      weighting_deployment_line wdl,\n      structure_element act_se,\n      structure_element bus_se\n  where wp.model_id = (select model_id from params) and \n      wp.start_level <= (select target_start_level from params) and \n      wp.leaf_level = (select target_leaf_level from params) and\n      wd.profile_id = wp.profile_id and\n      act_se.structure_id (+) = wdl.account_structure_id and\n      act_se.structure_element_id (+) = wdl.account_structure_element_id and\n      bus_se.structure_id (+) = wdl.business_structure_id and\n      bus_se.structure_element_id (+) = wdl.business_structure_element_id and\n      wdl.deployment_id = wd.deployment_id\n)\nselect wp.profile_id,\n       wp.vis_id,\n       wp.description,\n       wd.global_dep,\n       wp.profile_type,\n       wp.start_level,\n       wp.leaf_level,\n       wp.dynamic_offset,\n       decode( wp.dynamic_data_type_id, null, null, \n              (select dt.vis_id \n               from data_type dt \n               where dt.data_type_id = wp.dynamic_data_type_id) ) as dynamic_data_type,\n       max(wd.weighting) as weighting\nfrom weighting_profile wp,\n(\n  select wd.*,\n         case when wd.any_account = \'Y\' and\n                   wd.any_business = \'Y\' and\n                   wd.any_data_type = \'Y\'\n         then \'Y\'\n         else \'N\'\n        end as global_dep\n  from weighting_deployment wd\n) wd,\ntact_se,\ntbus_se,\ndt,\n(   \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.act_se_depth ) as rk\n    from line_det ld,\n         tact_se se\n    where se.position >= ld.act_se_position and \n          se.position <= ld.act_se_end_position \n  ) order by rk desc\n) line_det1,\n(   \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.bus_se_depth ) as rk\n    from line_det ld,\n         tbus_se se\n    where se.position >= ld.bus_se_position and \n          se.position <= ld.bus_se_end_position \n  ) order by rk desc\n) line_det2,\n( select * from line_det ) line_det3\nwhere\n  wp.model_id = (select model_id from params) and\n  wd.profile_id = wp.profile_id and\n  wp.profile_type in (0,1) and\n  wp.start_level <= (select target_start_level from params) and \n  wp.leaf_level = (select target_leaf_level from params) and\n  wd.deployment_id = line_det1.deployment_id (+) and\n  wd.deployment_id = line_det2.deployment_id (+) and\n  wd.deployment_id = line_det3.deployment_id (+) and\n  ( wd.any_account = \'Y\' or line_det1.act_sel_flag = \'Y\' ) and\n  ( wd.any_business = \'Y\' or line_det2.bus_sel_flag = \'Y\' ) and\n  ( wd.any_data_type = \'Y\' or ( dt.data_type_id = line_det3.data_type_id ) )\ngroup by wp.profile_id, wp.vis_id, wp.description, wd.global_dep, wp.profile_type,\n        wp.start_level, wp.leaf_level, wp.dynamic_offset, wp.dynamic_data_type_id\norder by weighting desc, wp.vis_id asc");
         byte sqle = 1;
         int var18 = sqle + 1;
         stmt.setInt(sqle, modelId);
         stmt.setInt(var18++, fromLevel);
         stmt.setInt(var18++, toLevel);
         stmt.setInt(var18++, acctStructureElementId);
         stmt.setInt(var18++, busStructureElementId);
         stmt.setString(var18++, dataTypeId);
         resultSet = stmt.executeQuery();
         var11 = JdbcUtils.extractToEntityListImpl(QUERY_PROFILES_COL_INFO, resultSet);
      } catch (SQLException var15) {
         throw this.handleSQLException("with params as \n(\n  select ? as model_id, \n         ? as target_start_level, \n         ? as target_leaf_level, \n         ? as acct_se_id, \n         ? as bus_se_id, \n         ? as data_type_vis_id \n  from dual \n),\ntact_se as\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select acct_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, -1 as depth from dual\n),\ntbus_se as\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select bus_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, -1 as depth from dual\n),\ndt as \n(\n  select ldt.data_type_id from data_type ldt \n  where ldt.vis_id = (select data_type_vis_id from params)\n  union all\n  select -1 as data_type_id from dual\n),\nline_det as\n(\n  select wd.profile_id,\n         wd.deployment_id,\n         act_se.position as act_se_position,\n         act_se.end_position as act_se_end_position,\n         act_se.depth as act_se_depth,\n         wdl.account_selection_flag as act_sel_flag,\n         bus_se.position as bus_se_position,\n         bus_se.end_position as bus_se_end_position,\n         bus_se.depth as bus_se_depth,\n         wdl.business_selection_flag as bus_sel_flag,\n         wdl.data_type_id,\n         wd.any_data_type\n  from weighting_profile wp,\n      weighting_deployment wd,\n      weighting_deployment_line wdl,\n      structure_element act_se,\n      structure_element bus_se\n  where wp.model_id = (select model_id from params) and \n      wp.start_level <= (select target_start_level from params) and \n      wp.leaf_level = (select target_leaf_level from params) and\n      wd.profile_id = wp.profile_id and\n      act_se.structure_id (+) = wdl.account_structure_id and\n      act_se.structure_element_id (+) = wdl.account_structure_element_id and\n      bus_se.structure_id (+) = wdl.business_structure_id and\n      bus_se.structure_element_id (+) = wdl.business_structure_element_id and\n      wdl.deployment_id = wd.deployment_id\n)\nselect wp.profile_id,\n       wp.vis_id,\n       wp.description,\n       wd.global_dep,\n       wp.profile_type,\n       wp.start_level,\n       wp.leaf_level,\n       wp.dynamic_offset,\n       decode( wp.dynamic_data_type_id, null, null, \n              (select dt.vis_id \n               from data_type dt \n               where dt.data_type_id = wp.dynamic_data_type_id) ) as dynamic_data_type,\n       max(wd.weighting) as weighting\nfrom weighting_profile wp,\n(\n  select wd.*,\n         case when wd.any_account = \'Y\' and\n                   wd.any_business = \'Y\' and\n                   wd.any_data_type = \'Y\'\n         then \'Y\'\n         else \'N\'\n        end as global_dep\n  from weighting_deployment wd\n) wd,\ntact_se,\ntbus_se,\ndt,\n(   \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.act_se_depth ) as rk\n    from line_det ld,\n         tact_se se\n    where se.position >= ld.act_se_position and \n          se.position <= ld.act_se_end_position \n  ) order by rk desc\n) line_det1,\n(   \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.bus_se_depth ) as rk\n    from line_det ld,\n         tbus_se se\n    where se.position >= ld.bus_se_position and \n          se.position <= ld.bus_se_end_position \n  ) order by rk desc\n) line_det2,\n( select * from line_det ) line_det3\nwhere\n  wp.model_id = (select model_id from params) and\n  wd.profile_id = wp.profile_id and\n  wp.profile_type in (0,1) and\n  wp.start_level <= (select target_start_level from params) and \n  wp.leaf_level = (select target_leaf_level from params) and\n  wd.deployment_id = line_det1.deployment_id (+) and\n  wd.deployment_id = line_det2.deployment_id (+) and\n  wd.deployment_id = line_det3.deployment_id (+) and\n  ( wd.any_account = \'Y\' or line_det1.act_sel_flag = \'Y\' ) and\n  ( wd.any_business = \'Y\' or line_det2.bus_sel_flag = \'Y\' ) and\n  ( wd.any_data_type = \'Y\' or ( dt.data_type_id = line_det3.data_type_id ) )\ngroup by wp.profile_id, wp.vis_id, wp.description, wd.global_dep, wp.profile_type,\n        wp.start_level, wp.leaf_level, wp.dynamic_offset, wp.dynamic_data_type_id\norder by weighting desc, wp.vis_id asc", var15);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("queryProfiles");
         }

      }

      return var11;
   }

   public EntityList queryProfiles(int modelId, int dataTypeId, int acctStructureId, int acctStructureElementId, int busStructureId, int busStructureElementId, int calStructureId, int calStructureElementId) {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      EntityListImpl var13;
      try {
         stmt = this.getConnection().prepareStatement("with params as\n(\n  select ? as model_id, \n         ? as bus_s_id, \n         ? as bus_se_id, \n         ? as acct_s_id, \n         ? as acct_se_id, \n         ? as cal_s_id, \n         ? as cal_se_id \n  from dual \n),\nthe_trg_cal_elem as -- The target calendar element\n(\n  select se.cal_elem_type,\n         se.position,\n         se.end_position\n  from structure_element se\n  where se.structure_id = (select cal_s_id from params)\n    and se.structure_element_id = (select cal_se_id from params)\n),\na_trg_cal_leaf_elem as -- Workout the target leaf elem type i.e. Month Weeks Days etc.\n(\n  select se.cal_elem_type\n  from structure_element se\n  where se.structure_id = (select cal_s_id from params)\n    and se.position > (select position from the_trg_cal_elem)\n    and se.end_position <= (select end_position from the_trg_cal_elem)\n    and se.cal_elem_type not in (6,7) -- filter out opening and adjustment types\n    and se.leaf = \'Y\'\n    and rownum = 1 -- Only need the first row\n  order by position\n),\ntact_se as -- Query the target account structure element details\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select acct_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, \n         -1 as depth \n  from dual\n),\ntbus_se as -- Query the target business structure element details\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select bus_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, \n         -1 as depth \n  from dual\n),\nline_det as -- Query required details from weighting deployment lines\n( \n  select wd.profile_id,\n         wd.deployment_id,\n         act_se.position as act_se_position,\n         act_se.end_position as act_se_end_position,\n         act_se.depth as act_se_depth,\n         wdl.account_selection_flag as act_sel_flag,\n         bus_se.position as bus_se_position,\n         bus_se.end_position as bus_se_end_position,\n         bus_se.depth as bus_se_depth,\n         wdl.business_selection_flag as bus_sel_flag,\n         wdl.data_type_id,\n         wd.any_data_type\n  from weighting_profile wp,\n      weighting_deployment wd,\n      weighting_deployment_line wdl,\n      structure_element act_se,\n      structure_element bus_se,\n      params p\n  where wp.model_id = p.model_id and\n      wp.start_level <= (select cal_elem_type from the_trg_cal_elem) and\n      wp.leaf_level = (select cal_elem_type from a_trg_cal_leaf_elem) and\n      wd.profile_id = wp.profile_id and\n      act_se.structure_id (+) = wdl.account_structure_id and\n      act_se.structure_element_id (+) = wdl.account_structure_element_id and\n      bus_se.structure_id (+) = wdl.business_structure_id and\n      bus_se.structure_element_id (+) = wdl.business_structure_element_id and\n      wdl.deployment_id = wd.deployment_id      \n)\nselect wp.profile_id,\n       wp.vis_id,\n       wp.description,\n       wd.global_dep,\n       wp.profile_type,\n       wp.start_level,\n       wp.leaf_level,\n       wp.dynamic_offset,\n       decode( wp.dynamic_data_type_id, null, null,\n              (select dt.vis_id\n               from data_type dt\n               where dt.data_type_id = wp.dynamic_data_type_id) ) as dynamic_data_type,\n       max(wd.weighting) as weighting\nfrom weighting_profile wp,\n(\n  select wd.*,\n         case when wd.any_account = \'Y\' and\n                   wd.any_business = \'Y\' and\n                   wd.any_data_type = \'Y\'\n         then \'Y\'\n         else \'N\'\n        end as global_dep\n  from weighting_deployment wd\n) wd,\n( -- Select the deployment line which is closet to the target acct structure element\n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.act_se_depth ) as rk\n    from line_det ld,\n         tact_se se\n    where se.position >= ld.act_se_position and \n          se.position <= ld.act_se_end_position \n  ) order by rk desc\n) line_det1,\n( -- Select the deployment line which is closet to the target bus. structure element  \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.bus_se_depth ) as rk\n    from line_det ld,\n         tbus_se se\n    where se.position >= ld.bus_se_position and \n          se.position <= ld.bus_se_end_position \n  ) order by rk desc\n) line_det2,\n( select * from line_det ) line_det3\nwhere\n  wp.model_id = (select model_id from params) and\n  wd.profile_id = wp.profile_id and\n  wp.start_level <= (select cal_elem_type from the_trg_cal_elem) and\n  wd.deployment_id = line_det1.deployment_id (+) and\n  wd.deployment_id = line_det2.deployment_id (+) and\n  wd.deployment_id = line_det3.deployment_id (+) and\n  ( wd.any_account = \'Y\' or line_det1.act_sel_flag = \'Y\' ) and\n  ( wd.any_business = \'Y\' or line_det2.bus_sel_flag = \'Y\' ) and\n  ( wd.any_data_type = \'Y\' or\n    ( ? in\n        (select -1 from dual\n         union all\n         select nvl(data_type_id,-1) from line_det ld  where ld.deployment_id = wd.deployment_id )\n         and\n      ? in\n         (select -1 from dual\n          union all\n          select nvl(data_type_id,-1) from line_det ld where ld.deployment_id = wd.deployment_id )\n         ) )\ngroup by wp.profile_id, wp.vis_id, wp.description, wd.global_dep, wp.profile_type,\n        wp.start_level, wp.leaf_level, wp.dynamic_offset, wp.dynamic_data_type_id\norder by weighting desc, wp.vis_id asc");
         byte sqle = 1;
         int var20 = sqle + 1;
         stmt.setInt(sqle, modelId);
         stmt.setInt(var20++, busStructureId);
         stmt.setInt(var20++, busStructureElementId);
         stmt.setInt(var20++, acctStructureId);
         stmt.setInt(var20++, acctStructureElementId);
         stmt.setInt(var20++, calStructureId);
         stmt.setInt(var20++, calStructureElementId);
         stmt.setInt(var20++, dataTypeId);
         stmt.setInt(var20++, dataTypeId);
         resultSet = stmt.executeQuery();
         var13 = JdbcUtils.extractToEntityListImpl(QUERY_PROFILES_COL_INFO, resultSet);
      } catch (SQLException var17) {
         throw this.handleSQLException("with params as\n(\n  select ? as model_id, \n         ? as bus_s_id, \n         ? as bus_se_id, \n         ? as acct_s_id, \n         ? as acct_se_id, \n         ? as cal_s_id, \n         ? as cal_se_id \n  from dual \n),\nthe_trg_cal_elem as -- The target calendar element\n(\n  select se.cal_elem_type,\n         se.position,\n         se.end_position\n  from structure_element se\n  where se.structure_id = (select cal_s_id from params)\n    and se.structure_element_id = (select cal_se_id from params)\n),\na_trg_cal_leaf_elem as -- Workout the target leaf elem type i.e. Month Weeks Days etc.\n(\n  select se.cal_elem_type\n  from structure_element se\n  where se.structure_id = (select cal_s_id from params)\n    and se.position > (select position from the_trg_cal_elem)\n    and se.end_position <= (select end_position from the_trg_cal_elem)\n    and se.cal_elem_type not in (6,7) -- filter out opening and adjustment types\n    and se.leaf = \'Y\'\n    and rownum = 1 -- Only need the first row\n  order by position\n),\ntact_se as -- Query the target account structure element details\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select acct_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, \n         -1 as depth \n  from dual\n),\ntbus_se as -- Query the target business structure element details\n(\n  select lse.structure_id, lse.structure_element_id, lse.position, lse.depth\n  from structure_element lse\n  where lse.structure_element_id = (select bus_se_id from params)\n  union all \n  select -1 as structure_id, -1 as structure_element_id, -1 as position, \n         -1 as depth \n  from dual\n),\nline_det as -- Query required details from weighting deployment lines\n( \n  select wd.profile_id,\n         wd.deployment_id,\n         act_se.position as act_se_position,\n         act_se.end_position as act_se_end_position,\n         act_se.depth as act_se_depth,\n         wdl.account_selection_flag as act_sel_flag,\n         bus_se.position as bus_se_position,\n         bus_se.end_position as bus_se_end_position,\n         bus_se.depth as bus_se_depth,\n         wdl.business_selection_flag as bus_sel_flag,\n         wdl.data_type_id,\n         wd.any_data_type\n  from weighting_profile wp,\n      weighting_deployment wd,\n      weighting_deployment_line wdl,\n      structure_element act_se,\n      structure_element bus_se,\n      params p\n  where wp.model_id = p.model_id and\n      wp.start_level <= (select cal_elem_type from the_trg_cal_elem) and\n      wp.leaf_level = (select cal_elem_type from a_trg_cal_leaf_elem) and\n      wd.profile_id = wp.profile_id and\n      act_se.structure_id (+) = wdl.account_structure_id and\n      act_se.structure_element_id (+) = wdl.account_structure_element_id and\n      bus_se.structure_id (+) = wdl.business_structure_id and\n      bus_se.structure_element_id (+) = wdl.business_structure_element_id and\n      wdl.deployment_id = wd.deployment_id      \n)\nselect wp.profile_id,\n       wp.vis_id,\n       wp.description,\n       wd.global_dep,\n       wp.profile_type,\n       wp.start_level,\n       wp.leaf_level,\n       wp.dynamic_offset,\n       decode( wp.dynamic_data_type_id, null, null,\n              (select dt.vis_id\n               from data_type dt\n               where dt.data_type_id = wp.dynamic_data_type_id) ) as dynamic_data_type,\n       max(wd.weighting) as weighting\nfrom weighting_profile wp,\n(\n  select wd.*,\n         case when wd.any_account = \'Y\' and\n                   wd.any_business = \'Y\' and\n                   wd.any_data_type = \'Y\'\n         then \'Y\'\n         else \'N\'\n        end as global_dep\n  from weighting_deployment wd\n) wd,\n( -- Select the deployment line which is closet to the target acct structure element\n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.act_se_depth ) as rk\n    from line_det ld,\n         tact_se se\n    where se.position >= ld.act_se_position and \n          se.position <= ld.act_se_end_position \n  ) order by rk desc\n) line_det1,\n( -- Select the deployment line which is closet to the target bus. structure element  \n  select * \n  from \n  (\n    select ld.*, rank() over( order by se.depth - ld.bus_se_depth ) as rk\n    from line_det ld,\n         tbus_se se\n    where se.position >= ld.bus_se_position and \n          se.position <= ld.bus_se_end_position \n  ) order by rk desc\n) line_det2,\n( select * from line_det ) line_det3\nwhere\n  wp.model_id = (select model_id from params) and\n  wd.profile_id = wp.profile_id and\n  wp.start_level <= (select cal_elem_type from the_trg_cal_elem) and\n  wd.deployment_id = line_det1.deployment_id (+) and\n  wd.deployment_id = line_det2.deployment_id (+) and\n  wd.deployment_id = line_det3.deployment_id (+) and\n  ( wd.any_account = \'Y\' or line_det1.act_sel_flag = \'Y\' ) and\n  ( wd.any_business = \'Y\' or line_det2.bus_sel_flag = \'Y\' ) and\n  ( wd.any_data_type = \'Y\' or\n    ( ? in\n        (select -1 from dual\n         union all\n         select nvl(data_type_id,-1) from line_det ld  where ld.deployment_id = wd.deployment_id )\n         and\n      ? in\n         (select -1 from dual\n          union all\n          select nvl(data_type_id,-1) from line_det ld where ld.deployment_id = wd.deployment_id )\n         ) )\ngroup by wp.profile_id, wp.vis_id, wp.description, wd.global_dep, wp.profile_type,\n        wp.start_level, wp.leaf_level, wp.dynamic_offset, wp.dynamic_data_type_id\norder by weighting desc, wp.vis_id asc", var17);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("queryProfiles(budget transfers version)");
         }

      }

      return var13;
   }

   public int[] getWeightingProfile(int profileId) {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      int[] var8;
      try {
         stmt = this.getConnection().prepareStatement("select weighting from weighting_profile_line where profile_id = ? order by line_idx");
         stmt.setInt(1, profileId);
         resultSet = stmt.executeQuery();
         int[] sqle = new int[512];

         int row;
         for(row = 0; resultSet.next(); ++row) {
            sqle[row] = resultSet.getInt(1);
         }

         if(row == 0) {
            Object var14 = null;
            return (int[])var14;
         }

         int[] result = new int[row];
         System.arraycopy(sqle, 0, result, 0, row);
         var8 = result;
      } catch (SQLException var12) {
         throw this.handleSQLException("select weighting from weighting_profile_line where profile_id = ? order by line_idx", var12);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("getWeightingProfile");
         }

      }

      return var8;
   }

   public ProfileImpl getWeightingProfileDetail(int profileId) {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      Object result;
      try {
         stmt = this.getConnection().prepareStatement("select wpl.weighting, wp.vis_id, wp.profile_type, wp.start_level, wp.leaf_level,        wp.dynamic_offset, wp.dynamic_data_type_id from weighting_profile wp,     weighting_profile_line wpl where wp.profile_id = ? and       wp.profile_id = wpl.profile_id (+)order by line_idx");
         stmt.setInt(1, profileId);
         resultSet = stmt.executeQuery();
         int[] sqle = new int[512];
         int row = 0;
         String visId = null;

         int type;
         for(type = -1; resultSet.next(); ++row) {
            sqle[row] = resultSet.getInt(1);
            if(row == 0) {
               visId = resultSet.getString(2);
               type = resultSet.getInt("profile_type");
            }
         }

         if(row != 0) {
            int[] var16 = new int[row];
            System.arraycopy(sqle, 0, var16, 0, row);
            ProfileImpl var10 = new ProfileImpl(new WeightingProfileRefImpl(new WeightingProfilePK(profileId), visId), type, var16);
            return var10;
         }

         result = null;
      } catch (SQLException var14) {
         throw this.handleSQLException("select wpl.weighting, wp.vis_id, wp.profile_type, wp.start_level, wp.leaf_level,        wp.dynamic_offset, wp.dynamic_data_type_id from weighting_profile wp,     weighting_profile_line wpl where wp.profile_id = ? and       wp.profile_id = wpl.profile_id (+)order by line_idx", var14);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("getStaticWeightingProfileDetail");
         }

      }

      return (ProfileImpl)result;
   }

   public ProfileImpl getWeightingProfileDetail(int profileId, int calendar_structure_element) {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      Object result;
      try {
         stmt = this.getConnection().prepareStatement("with params as \n( \n  select ? as target_element_id, \n         profile_id, \n         start_level, \n         leaf_level, \n         vis_id \n  from weighting_profile wp \n  where wp.profile_id = ? \n) \n,target_elem as -- query the target element \n( \n  select se.* \n  from structure_element se \n  where se.structure_element_id = ( select target_element_id from params )\n)\n,profile_root as -- determine the root se of the profile\n(\n  select * from \n  (\n    select se.*\n    from structure_element se\n    start with se.structure_element_id = \n      ( select structure_element_id from target_elem )\n    connect by se.structure_element_id = prior se.parent_id \n  )\n  where cal_elem_type = ( select start_level from params ) \n    and parent_id != 0\n)\n,all_se as -- query se for the whole including open and adjustment elements\n(\n    select rank() over( partition by structure_id order by position asc) idx,\n            se.*\n    from structure_element se\n    where se.structure_id = ( select structure_id from profile_root ) and\n          se.position > ( select position from profile_root  ) and\n          se.position <= ( select end_position from profile_root ) and\n          leaf = \'Y\' \n    order by se.position\n)\n,open_se as -- filter out the opening element if present\n(\n  select ase.*, -1 as pro_index \n  from all_se ase \n  where ase.cal_elem_type = 6\n)\n,normal_leaves as -- filter out the normal leaf elements\n(\n  select ase.*, rownum as pro_index \n  from all_se ase \n  where ase.cal_elem_type not in ( 6, 7 )\n)\n,adj_se as -- filter out the adjustment element if present \n(\n  select ase.*, -1 as pro_index \n  from all_se ase \n  where ase.cal_elem_type = 7\n)\n,cal_se as -- produce a view will pro_index set-up to join to profile lines\n(\n  select * from open_se \n  union all \n  select * from normal_leaves \n  union all \n  select * from adj_se\n) \n,wpl as -- query the weighting profile lines\n( \n  select wpl.line_idx+1 as idx, wpl.weighting, wp.vis_id\n  from   weighting_profile wp,\n         weighting_profile_line wpl\n  where wp.profile_id = (select profile_id from params) and\n        wp.profile_id = wpl.profile_id\n  order by line_idx\n) \n,lines as -- join the profile to the lines and limit to leaves under target cell\n(\n  select se.position, wp.vis_id, wp.profile_type, nvl(wpl.weighting,0) weighting\n  from cal_se se,\n       wpl wpl,\n       weighting_profile wp\n  where se.pro_index = wpl.idx (+) and\n        se.structure_id = ( select structure_id from target_elem ) and\n        se.position > ( select position from target_elem ) and\n        se.position <= ( select end_position from target_elem ) and\n        wp.profile_id = ( select profile_id from params )\n)      \nselect vis_id, profile_type, weighting from lines order by position");
         stmt.setInt(1, calendar_structure_element);
         stmt.setInt(2, profileId);
         resultSet = stmt.executeQuery();
         int[] sqle = new int[512];
         int row = 0;
         String visId = null;

         int type;
         for(type = -1; resultSet.next(); ++row) {
            sqle[row] = resultSet.getInt("weighting");
            if(row == 0) {
               visId = resultSet.getString("vis_id");
               type = resultSet.getInt("profile_type");
            }
         }

         if(row != 0) {
            int[] var17 = new int[row];
            System.arraycopy(sqle, 0, var17, 0, row);
            ProfileImpl var11 = new ProfileImpl(new WeightingProfileRefImpl(new WeightingProfilePK(profileId), visId), type, var17);
            return var11;
         }

         result = null;
      } catch (SQLException var15) {
         throw this.handleSQLException("with params as \n( \n  select ? as target_element_id, \n         profile_id, \n         start_level, \n         leaf_level, \n         vis_id \n  from weighting_profile wp \n  where wp.profile_id = ? \n) \n,target_elem as -- query the target element \n( \n  select se.* \n  from structure_element se \n  where se.structure_element_id = ( select target_element_id from params )\n)\n,profile_root as -- determine the root se of the profile\n(\n  select * from \n  (\n    select se.*\n    from structure_element se\n    start with se.structure_element_id = \n      ( select structure_element_id from target_elem )\n    connect by se.structure_element_id = prior se.parent_id \n  )\n  where cal_elem_type = ( select start_level from params ) \n    and parent_id != 0\n)\n,all_se as -- query se for the whole including open and adjustment elements\n(\n    select rank() over( partition by structure_id order by position asc) idx,\n            se.*\n    from structure_element se\n    where se.structure_id = ( select structure_id from profile_root ) and\n          se.position > ( select position from profile_root  ) and\n          se.position <= ( select end_position from profile_root ) and\n          leaf = \'Y\' \n    order by se.position\n)\n,open_se as -- filter out the opening element if present\n(\n  select ase.*, -1 as pro_index \n  from all_se ase \n  where ase.cal_elem_type = 6\n)\n,normal_leaves as -- filter out the normal leaf elements\n(\n  select ase.*, rownum as pro_index \n  from all_se ase \n  where ase.cal_elem_type not in ( 6, 7 )\n)\n,adj_se as -- filter out the adjustment element if present \n(\n  select ase.*, -1 as pro_index \n  from all_se ase \n  where ase.cal_elem_type = 7\n)\n,cal_se as -- produce a view will pro_index set-up to join to profile lines\n(\n  select * from open_se \n  union all \n  select * from normal_leaves \n  union all \n  select * from adj_se\n) \n,wpl as -- query the weighting profile lines\n( \n  select wpl.line_idx+1 as idx, wpl.weighting, wp.vis_id\n  from   weighting_profile wp,\n         weighting_profile_line wpl\n  where wp.profile_id = (select profile_id from params) and\n        wp.profile_id = wpl.profile_id\n  order by line_idx\n) \n,lines as -- join the profile to the lines and limit to leaves under target cell\n(\n  select se.position, wp.vis_id, wp.profile_type, nvl(wpl.weighting,0) weighting\n  from cal_se se,\n       wpl wpl,\n       weighting_profile wp\n  where se.pro_index = wpl.idx (+) and\n        se.structure_id = ( select structure_id from target_elem ) and\n        se.position > ( select position from target_elem ) and\n        se.position <= ( select end_position from target_elem ) and\n        wp.profile_id = ( select profile_id from params )\n)      \nselect vis_id, profile_type, weighting from lines order by position", var15);
      } finally {
         this.closeResultSet(resultSet);
         this.closeStatement(stmt);
         this.closeConnection();
         if(timer != null) {
            timer.logDebug("getStaticWeightingProfileDetail for cal element");
         }

      }

      return (ProfileImpl)result;
   }

   public EntityList queryDynamicWeightingFactors(int financeCubeId, int[] structureElementIds, int targetElementId, String dataType, int targetFromLevel, int targetToLevel, int calDimId) {
      WeightingProfileDAO$1 parser = new WeightingProfileDAO$1(this, "{", "}", "with params as\n(\n  select {busParams} \n         ? as acct_se, \n         ? as src_cal_se, \n         ? as trg_cal_se, \n         ? as data_type, \n\t\t  ( select hierarchy_id from hierarchy h where h.dimension_id = ? ) as cal_sid \n  from dual \n) \n,positions as \n( \n   select src_elem.position src_position, \n          src_elem.end_position src_end_position, \n          trg_elem.position trg_position, \n          trg_elem.end_position trg_end_position \n   from structure_element src_elem, \n        structure_element trg_elem \n   where src_elem.structure_id = ( select cal_sid from params ) \n\t  and src_elem.structure_element_id = ( select src_cal_se from params ) \n     and trg_elem.structure_id = ( select cal_sid from params ) \n     and trg_elem.structure_element_id = ( select trg_cal_se from params ) \n) \n,cal_src_leaves as \n( \n  select se.* \n  from structure_element se \n  where se.structure_id = ( select cal_sid from params ) \n   and se.position > (select src_position from positions) \n   and se.end_position <= (select src_end_position from positions) \n   and se.leaf = \'Y\' \n  order by position \n) \n,cal_trg_leaves as \n( \n  select se.* \n  from structure_element se \n  where se.structure_id = ( select cal_sid from params ) \n   and se.position > (select trg_position from positions) \n   and se.end_position <= (select trg_end_position from positions) \n   and se.leaf = \'Y\' \n  order by position \n) \n,src_elems as \n(\n  select {busColSel} \n         acct_se as dim{accountIdx},\n         structure_element_id as dim{calendarIdx}, \n         cal_elem_type,\n         data_type\n  from params,\n       cal_src_leaves \n)\n,trg_elems as\n(\n  select {busColSel} \n         acct_se as dim{accountIdx},\n         structure_element_id as dim{calendarIdx}, \n         cal_elem_type,\n         data_type\n  from params,\n       cal_trg_leaves \n)\nselect {dimColumns} data_type, \n       se.cal_elem_type, nft.public_value/100 as public_value, \'src\' as src_trg \nfrom src_elems se \nleft join nft{financeCubeId} nft \nusing ( {dimColumns} data_type ) \nunion all\nselect {seDimColumns} se.data_type, \n       se.cal_elem_type, null as public_value, \'trg\' as src_trg \nfrom trg_elems se", structureElementIds, financeCubeId);
      String sql = parser.parse();
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         ps = this.getConnection().prepareStatement(sql);
         int sqle = 1;
         int[] dynamicProfileQueyColInfo = structureElementIds;
         int weightingInfo = structureElementIds.length;

         for(int i$ = 0; i$ < weightingInfo; ++i$) {
            int seId = dynamicProfileQueyColInfo[i$];
            ps.setInt(sqle++, seId);
         }

         ps.setInt(sqle++, targetElementId);
         ps.setString(sqle++, dataType);
         ps.setInt(sqle, calDimId);
         rs = ps.executeQuery();
         ColType[] var22 = new ColType[structureElementIds.length + 4];

         for(sqle = 0; sqle < structureElementIds.length; ++sqle) {
            var22[sqle] = new ColType("dim" + sqle, "dim" + sqle, 0);
         }

         var22[sqle++] = new ColType("data_type", "data_type", 1);
         var22[sqle++] = new ColType("cal_elem_type", "cal_elem_type", 0);
         var22[sqle++] = new ColType("public_value", "public_value", 0);
         var22[sqle] = new ColType("src_trg", "src_trg", 1);
         EntityListImpl var23 = JdbcUtils.extractToEntityListImpl(var22, rs);
         EntityList var24 = this.adjustDynamicWeightingFactorsForCalendarLevel(var23, targetFromLevel, targetToLevel);
         return var24;
      } catch (SQLException var20) {
         throw this.handleSQLException(sql, var20);
      } finally {
         this.closeResultSet(rs);
         this.closeStatement(ps);
         this.closeConnection();
      }
   }

   public ProfileImpl getProfileDetail(int financeCubeId, int profileId, int[] address, String dataType) throws ValidationException {
      FinanceCubeCK fcCK = (new ModelDAO()).getFinanceCubeCK(new FinanceCubePK(financeCubeId));
      WeightingProfileEVO wpEVO = this.getDetails(new WeightingProfileCK(fcCK.getModelPK(), new WeightingProfilePK(profileId)), "");
      if(wpEVO == null) {
         throw new ValidationException("Unable to locate weighting profile for id:" + profileId);
      } else {
         int calStructureId = address[address.length - 2];
         int calStructureElementId = address[address.length - 1];
         if(wpEVO.getProfileType() != 0 && wpEVO.getProfileType() != 2 && wpEVO.getProfileType() != 3) {
            if(wpEVO.getProfileType() != 1) {
               throw new ValidationException("Unexpeted profile type from context query:" + wpEVO.getProfileType());
            } else {
               int dynamicOffset = wpEVO.getDynamicOffset();
               String dynamicDataType = null;
               if(wpEVO.getDynamicDataTypeId() != null) {
                  DataTypeEVO wpRef = (new DataTypeDAO()).getDetails(new DataTypePK(wpEVO.getDynamicDataTypeId().shortValue()), "");
                  dynamicDataType = wpRef.getVisId();
               }

               if(dynamicDataType == null) {
                  dynamicDataType = dataType;
               }

               WeightingProfileRefImpl var23 = new WeightingProfileRefImpl(new WeightingProfilePK(profileId), wpEVO.getVisId());
               EntityList siblings = this.querySiblingsInStructure(calStructureId, calStructureElementId);
               int index = -1;

               int srcCalElementId;
               for(srcCalElementId = 0; srcCalElementId < siblings.getNumRows() && index == -1; ++srcCalElementId) {
                  if(((Integer)siblings.getValueAt(srcCalElementId, "structure_element_id")).intValue() == calStructureElementId) {
                     index = srcCalElementId;
                  }
               }

               if(index == -1) {
                  throw new IllegalStateException("Unable to locate own row in querySiblingsInStructure");
               } else if(index + dynamicOffset >= 0 && index + dynamicOffset < siblings.getNumRows()) {
                  srcCalElementId = ((Integer)siblings.getValueAt(index + dynamicOffset, "structure_element_id")).intValue();
                  int[] structureElementIds = new int[address.length / 2];

                  int targetLeafLevel;
                  for(targetLeafLevel = 0; targetLeafLevel < address.length; targetLeafLevel += 2) {
                     structureElementIds[targetLeafLevel / 2] = address[targetLeafLevel + 1];
                  }

                  structureElementIds[structureElementIds.length - 1] = srcCalElementId;
                  targetLeafLevel = this.queryCalElementLeafLevel(calStructureId, calStructureElementId);
                  if(targetLeafLevel == -1) {
                     throw new IllegalStateException("Unable to determine leaf cal elem type for:" + calStructureElementId);
                  } else {
                     HierarchyPK hPK = new HierarchyPK(calStructureId);
                     int calDimId = (new DimensionDAO()).getHierarchyCK(hPK).getDimensionPK().getDimensionId();
                     EntityList weightingFactors = this.queryDynamicWeightingFactors(financeCubeId, structureElementIds, calStructureElementId, dynamicDataType, ((Integer)siblings.getValueAt(index, "cal_elem_type")).intValue(), targetLeafLevel, calDimId);
                     int[] weightings = new int[weightingFactors.getNumRows()];

                     for(int row = 0; row < weightingFactors.getNumRows(); ++row) {
                        Integer w = (Integer)weightingFactors.getValueAt(row, "public_value");
                        weightings[row] = w != null?w.intValue():0;
                     }

                     return new ProfileImpl(var23, 1, weightings);
                  }
               } else {
                  return new ProfileImpl(var23, wpEVO.getProfileType(), (int[])null);
               }
            }
         } else {
            return this.getWeightingProfileDetail(profileId, calStructureElementId);
         }
      }
   }

   public EntityList querySiblingsInStructure(int structureId, int structureElementId) {
      PreparedStatement ps = null;
      ResultSet rs = null;

      EntityListImpl var6;
      try {
         ps = this.getConnection().prepareStatement("with params as \n( \n  select ? structure_id, \n         ? as structure_element_id \n  from dual \n), \ntrg_cal_elem as \n(\n  select se.* \n  from structure_element se \n  where se.structure_id = (select structure_id from params) \n    and se.structure_element_id = (select structure_element_id from params) \n) \nselect * \nfrom structure_element se, \n     params p \nwhere se.structure_id = p.structure_id \n  and se.cal_elem_type = (select cal_elem_type from trg_cal_elem) \norder by se.position ");
         byte e = 1;
         byte var10001 = e;
         int e1 = e + 1;
         ps.setInt(var10001, structureId);
         ps.setInt(e1, structureElementId);
         rs = ps.executeQuery();
         var6 = JdbcUtils.extractToEntityListImpl(QUERY_SES_CAL_ELEM_TYPE_COL_INFO, rs);
      } catch (SQLException var10) {
         throw this.handleSQLException("querySiblingsInStructure", var10);
      } finally {
         this.closeResultSet(rs);
         this.closeStatement(ps);
         this.closeConnection();
      }

      return var6;
   }

   public int queryCalElementLeafLevel(int structureId, int structureElementId) {
      PreparedStatement ps = null;
      ResultSet rs = null;

      int var6;
      try {
         ps = this.getConnection().prepareStatement(QUERY_CAL_LEAF_LEVEL_FOR_ELEMENT);
         byte e = 1;
         byte var10001 = e;
         int e1 = e + 1;
         ps.setInt(var10001, structureId);
         ps.setInt(e1, structureElementId);
         rs = ps.executeQuery();
         var6 = rs.next()?rs.getInt(1):-1;
      } catch (SQLException var10) {
         throw this.handleSQLException("queryCalElementLeafLevel", var10);
      } finally {
         this.closeResultSet(rs);
         this.closeStatement(ps);
         this.closeConnection();
      }

      return var6;
   }

   private int queryFactorLevel(EntityListImpl factors) {
      for(int row = 0; row < factors.getNumRows(); ++row) {
         int calElemType = ((Integer)factors.getValueAt(row, "cal_elem_type")).intValue();
         if(calElemType != 6 && calElemType != 7) {
            return calElemType;
         }
      }

      return -1;
   }

   private EntityList adjustDynamicWeightingFactorsForCalendarLevel(EntityListImpl wf, int fromLevel, int toLevel) {
      int factorLevel = this.queryFactorLevel(wf);
      if(factorLevel == -1) {
         return null;
      } else {
         new ArrayList();
         int targetRowsStartIndex = this.queryIndexOfTargetRows(wf);
         if(targetRowsStartIndex == -1) {
            throw new IllegalStateException("Failed to locate target row index in weighting factor result set.");
         } else {
            int i;
            int targetRowIndex;
            int srcCalElemType;
            if(factorLevel > toLevel) {
               i = targetRowsStartIndex;
               targetRowIndex = 0;

               while(targetRowIndex < targetRowsStartIndex && i < wf.getNumRows()) {
                  srcCalElemType = this.summariseWeightingsForRow(wf, targetRowIndex, i, targetRowsStartIndex, fromLevel, toLevel);
                  targetRowIndex += Math.abs(srcCalElemType);
                  if(srcCalElemType >= 0) {
                     ++i;
                  }
               }
            } else if(factorLevel < toLevel) {
               i = 0;
               targetRowIndex = targetRowsStartIndex;

               while(targetRowIndex < wf.getNumRows() && i < targetRowsStartIndex) {
                  srcCalElemType = this.allocateWeightingForRow(wf, i, targetRowIndex);
                  targetRowIndex += Math.abs(srcCalElemType);
                  if(srcCalElemType >= 0) {
                     ++i;
                  }
               }
            } else {
               i = 0;
               targetRowIndex = targetRowsStartIndex;

               while(i < targetRowsStartIndex && targetRowIndex < wf.getNumRows()) {
                  srcCalElemType = ((Integer)wf.getValueAt(i, "cal_elem_type")).intValue();
                  int targetCalElemType = ((Integer)wf.getValueAt(targetRowIndex, "cal_elem_type")).intValue();
                  if(srcCalElemType == targetCalElemType) {
                     wf.setValueAt(targetRowIndex, "public_value", wf.getValueAt(i, "public_value"));
                     ++i;
                     ++targetRowIndex;
                  } else if(srcCalElemType != 6 && srcCalElemType != 7) {
                     if(targetCalElemType != 6 && targetCalElemType != 7) {
                        throw new IllegalStateException("Unexpected calendar element type combination for matching level:" + srcCalElemType + " and " + targetCalElemType);
                     }

                     ++targetRowIndex;
                  } else {
                     ++i;
                  }
               }
            }

            for(i = 0; i < targetRowsStartIndex; ++i) {
               wf.remove(0);
            }

            return wf;
         }
      }
   }

   private int allocateWeightingForRow(EntityListImpl wf, int srcRowStartIndex, int targetRowStartIndex) {
      int srcCalElemType = ((Integer)wf.getValueAt(srcRowStartIndex, "cal_elem_type")).intValue();
      int targetCalElemType = ((Integer)wf.getValueAt(targetRowStartIndex, "cal_elem_type")).intValue();
      int trgRowCount = this.queryRowCount(targetCalElemType, srcCalElemType);
      if(trgRowCount < 0) {
         return trgRowCount;
      } else {
         Integer srcValue = (Integer)wf.getValueAt(srcRowStartIndex, "public_value");
         double toAllocate = srcValue != null?(double)srcValue.intValue():0.0D;
         double sumOfWeightins = (double)trgRowCount;
         if(toAllocate != 0.0D) {
            for(int trgRowIndex = targetRowStartIndex; trgRowIndex < targetRowStartIndex + trgRowCount && trgRowIndex < wf.getNumRows(); ++trgRowIndex) {
               double alloc = (double)Math.round(toAllocate * (1.0D / sumOfWeightins) * 100.0D) / 100.0D;
               wf.setValueAt(trgRowIndex, "public_value", Integer.valueOf(Double.valueOf(alloc).intValue()));
               toAllocate -= alloc;
               --sumOfWeightins;
            }
         }

         return trgRowCount;
      }
   }

   private int summariseWeightingsForRow(EntityListImpl wf, int srcRowStartIndex, int targetRowStartIndex, int targetRowsStartIndex, int fromLevel, int toLevel) {
      int srcCalElemType = ((Integer)wf.getValueAt(srcRowStartIndex, "cal_elem_type")).intValue();
      int targetCalElemType = ((Integer)wf.getValueAt(targetRowStartIndex, "cal_elem_type")).intValue();
      int srcRowCount = this.queryRowCount(srcCalElemType, targetCalElemType);
      if(srcRowCount < 0) {
         return srcRowCount;
      } else {
         int weighting = 0;

         for(int srcRowIndex = srcRowStartIndex; srcRowIndex < srcRowStartIndex + srcRowCount && srcRowIndex < targetRowsStartIndex; ++srcRowIndex) {
            Integer publicValue = (Integer)wf.getValueAt(srcRowIndex, "public_value");
            if(publicValue != null) {
               weighting += publicValue.intValue();
            }
         }

         wf.setValueAt(targetRowStartIndex, "public_value", Integer.valueOf(weighting));
         return srcRowCount;
      }
   }

   private int queryRowCount(int srcCalElemType, int targetCalElemType) {
      switch(targetCalElemType) {
      case 0:
         switch(srcCalElemType) {
         case 0:
            return 1;
         case 1:
            return 2;
         case 2:
            return 4;
         case 3:
            return 12;
         case 4:
            return 53;
         case 5:
            return 365;
         case 6:
            return -1;
         case 7:
            return -1;
         default:
            throw new IllegalStateException("Unexpected src/target calendar element type combo:" + srcCalElemType + ", " + targetCalElemType);
         }
      case 1:
         switch(srcCalElemType) {
         case 0:
            throw new IllegalStateException("Invalid summary combination year to year");
         case 1:
            return 1;
         case 2:
            return 2;
         case 3:
            return 6;
         case 4:
            return 27;
         case 5:
            return 183;
         case 6:
            return -1;
         case 7:
            return -1;
         default:
            throw new IllegalStateException("Unexpected src/target calendar element type combo:" + srcCalElemType + ", " + targetCalElemType);
         }
      case 2:
         switch(srcCalElemType) {
         case 0:
            throw new IllegalStateException("Invalid summary combination year to quarter");
         case 1:
            throw new IllegalStateException("Invalid summary combination half year to quarter");
         case 2:
            return 1;
         case 3:
            return 3;
         case 4:
            return 14;
         case 5:
            return 92;
         case 6:
            return -1;
         case 7:
            return -1;
         default:
            throw new IllegalStateException("Unexpected src/target calendar element type combo:" + srcCalElemType + ", " + targetCalElemType);
         }
      case 3:
         switch(srcCalElemType) {
         case 0:
            throw new IllegalStateException("Invalid summary combination year to month");
         case 1:
            throw new IllegalStateException("Invalid summary combination half year to month");
         case 2:
            throw new IllegalStateException("Invalid summary combination quarter to month");
         case 3:
            return 1;
         case 4:
         default:
            throw new IllegalStateException("Unexpected src/target calendar element type combo:" + srcCalElemType + ", " + targetCalElemType);
         case 5:
            return 31;
         case 6:
            return -1;
         case 7:
            return -1;
         }
      case 4:
         switch(srcCalElemType) {
         case 0:
            throw new IllegalStateException("Invalid summary combination year to month");
         case 1:
            throw new IllegalStateException("Invalid summary combination half year to month");
         case 2:
            throw new IllegalStateException("Invalid summary combination quarter to month");
         case 3:
            return 1;
         case 4:
         default:
            throw new IllegalStateException("Unexpected src/target calendar element type combo:" + srcCalElemType + ", " + targetCalElemType);
         case 5:
            return 31;
         case 6:
            return -1;
         case 7:
            return -1;
         }
      case 5:
         switch(srcCalElemType) {
         case 0:
            throw new IllegalStateException("Invalid summary combination year to month");
         case 1:
            throw new IllegalStateException("Invalid summary combination half year to month");
         case 2:
            throw new IllegalStateException("Invalid summary combination quarter to month");
         case 3:
            throw new IllegalStateException("Invalid summary combination quarter to month");
         case 4:
         default:
            throw new IllegalStateException("Unexpected src/target calendar element type combo:" + srcCalElemType + ", " + targetCalElemType);
         case 5:
            return 1;
         case 6:
            return -1;
         case 7:
            return -1;
         }
      case 6:
         return srcCalElemType == 6?1:0;
      case 7:
         return srcCalElemType == 7?1:0;
      default:
         throw new IllegalStateException("Unexpected src/target calendar element type combo:" + srcCalElemType + ", " + targetCalElemType);
      }
   }

   private int queryIndexOfTargetRows(EntityList wf) {
      for(int i = 0; i < wf.getNumRows(); ++i) {
         String srcTrg = (String)wf.getValueAt(i, "src_trg");
         if(srcTrg.equalsIgnoreCase("trg")) {
            return i;
         }
      }

      return -1;
   }
   
   public static class WeightingProfileRefColType extends RefColType {

	   public WeightingProfileRefColType(String name, String colName, String visIdColName) {
	      super(name, colName, visIdColName);
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      int profileId = rs.getInt(this.mColName);
	      String visId = rs.getString(this.mVisIdColName);
	      return new WeightingProfileRefImpl(new WeightingProfilePK(profileId), visId);
	   }
	}

	public AllWeightingProfilesELO getAllWeightingProfilesForLoggedUser(int userId) {
		Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
   /*  553 */     PreparedStatement stmt = null;
   /*  554 */     ResultSet resultSet = null;
   /*  555 */     AllWeightingProfilesELO results = new AllWeightingProfilesELO();
   /*      */     try
   /*      */     {
   /*  558 */       stmt = getConnection().prepareStatement(SQL_ALL_WEIGHTING_PROFILES_FOR_USER);
   /*  559 */       int col = 1;
   					stmt.setInt(1, userId);
   /*  560 */       resultSet = stmt.executeQuery();
   /*  561 */       while (resultSet.next())
   /*      */       {
   /*  563 */         col = 2;
   /*      */ 
   /*  566 */         ModelPK pkModel = new ModelPK(resultSet.getInt(col++));
   /*      */ 
   /*  569 */         String textModel = resultSet.getString(col++);
   /*      */ 
   /*  572 */         WeightingProfilePK pkWeightingProfile = new WeightingProfilePK(resultSet.getInt(col++));
   /*      */ 
   /*  575 */         String textWeightingProfile = resultSet.getString(col++);
   /*      */ 
   /*  580 */         WeightingProfileCK ckWeightingProfile = new WeightingProfileCK(pkModel, pkWeightingProfile);
   /*      */ 
   /*  586 */         ModelRefImpl erModel = new ModelRefImpl(pkModel, textModel);
   /*      */ 
   /*  592 */         WeightingProfileRefImpl erWeightingProfile = new WeightingProfileRefImpl(ckWeightingProfile, textWeightingProfile);
   /*      */ 
   /*  597 */         String col1 = resultSet.getString(col++);
   /*  598 */         int col2 = resultSet.getInt(col++);
   /*  599 */         int col3 = resultSet.getInt(col++);
   /*  600 */         int col4 = resultSet.getInt(col++);
   /*  601 */         String col5 = resultSet.getString(col++);
   /*      */ 
   /*  604 */         results.add(erWeightingProfile, erModel, col1, col2, col3, col4, col5);
   /*      */       }
   /*      */ 
   /*      */     }
   /*      */     catch (SQLException sqle)
   /*      */     {
   /*  617 */       throw handleSQLException(SQL_ALL_WEIGHTING_PROFILES_FOR_USER, sqle);
   /*      */     }
   /*      */     finally
   /*      */     {
   /*  621 */       closeResultSet(resultSet);
   /*  622 */       closeStatement(stmt);
   /*  623 */       closeConnection();
   /*      */     }
   /*      */ 
   /*  626 */     if (timer != null) {
   /*  627 */       timer.logDebug("getAllWeightingProfiles", " items=" + results.size());
   /*      */     }
   /*      */ 
   /*  631 */     return results;
	}

}
