/*      */ package com.cedar.cp.ejb.impl.dataentry;
/*      */ 
/*      */ import com.cedar.cp.api.base.EntityList;
/*      */ import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
/*      */ import com.cedar.cp.api.dataEntry.CellNoteRow;
/*      */ import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.FinanceCubeRef;
/*      */ import com.cedar.cp.dto.base.EntityListImpl;
/*      */ import com.cedar.cp.dto.dataEntry.AvailableStructureElementELO;
/*      */ import com.cedar.cp.dto.dataEntry.CellAuditDetailELO;
/*      */ import com.cedar.cp.dto.dataEntry.CellAuditDetailHeaderELO;
/*      */ import com.cedar.cp.dto.dataEntry.CellAuditELO;
/*      */ import com.cedar.cp.dto.dataEntry.CellAuditHeaderELO;
/*      */ import com.cedar.cp.dto.dataEntry.CellNoteRowImpl;
/*      */ import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
/*      */ import com.cedar.cp.dto.dimension.StructureElementPK;
/*      */ import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.base.cube.CellNote;
/*      */ import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */ import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
/*      */ import com.cedar.cp.util.DefaultValueMapping;
/*      */ import com.cedar.cp.util.Log;
/*      */ import com.cedar.cp.util.SqlBuilder;
/*      */ import com.cedar.cp.util.Timer;
/*      */ import com.cedar.cp.util.ValueMapping;
/*      */ import com.cedar.cp.util.xmlform.Column;
/*      */ import com.cedar.cp.util.xmlform.FormConfig;
/*      */ import com.cedar.cp.util.xmlform.inputs.DefaultFormDataInputModel;
/*      */ import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softproideas.common.enums.LookupParameterStatus;
import com.softproideas.common.models.DictionaryPropertiesDTO;

/*      */ import java.io.BufferedReader;
import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
import java.util.StringTokenizer;

/*      */ import oracle.jdbc.OracleConnection;
/*      */ import oracle.sql.ARRAY;
/*      */ import oracle.sql.ArrayDescriptor;
/*      */ import oracle.sql.DatumWithConnection;
/*      */ import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
/*      */ 
/*      */ public class DataEntryDAO extends AbstractDAO
/*      */ {
/*   77 */   private Log mLog = new Log(getClass());
/*      */ 
/*   79 */   protected static String SQL_AVAILABLE_ELEMENTS = "select s.depth, s.structure_element_id, s.vis_id, s.description, s.is_credit, s.disabled, s.leaf, s.position  from structure_element s, hierarchy h  where h.dimension_id = ? and h.vis_id = ?   and s.structure_id = h.hierarchy_id order by position asc";
/*      */ 
/*   87 */   protected static String SQL_AVAILABLE_ELEMENTS_ROOTED_AT = "select s.depth, s.structure_element_id, s.vis_id, s.description, s.is_credit, s.disabled, s.leaf, s.position  from structure_element s, hierarchy h       ,(        select structure_id, position, end_position          from structure_element          where structure_element_id = ?       ) X where h.dimension_id = ? and h.vis_id = ?   and s.structure_id = h.hierarchy_id   and s.structure_id = X.structure_id   and s.position >= X.position and s.position <= X.end_position order by position asc";
/*      */ 
/*  102 */   protected static String SQL_GET_DESCENDENT_LEAF_ELEMENTS = "select  distinct s.structure_element_id, s.position   from structure_element s      ,(        select structure_id, position, end_position          from structure_element          where structure_element_id = ?       ) X where s.leaf = 'Y' and s.disabled <> 'Y'   and s.structure_id = X.structure_id   and s.position >= X.position and s.position <= X.end_position order by position asc";
/*      */ 
/*  116 */   protected static String SQL_AVAILABLE_ELEMENTS_2 = "select s.depth, s.structure_element_id, s.vis_id, s.description, s.is_credit, s.disabled, s.not_plannable, s.leaf, s.position  from structure_element s  where s.structure_id = ? and parent_id = 0";
/*      */ 
/*  122 */   protected static String SQL_AVAILABLE_ELEMENTS_ROOTED_AT_2 = "select s.depth, s.structure_element_id, s.vis_id, s.description, s.is_credit, s.disabled, s.not_plannable, s.leaf, s.position  from structure_element s  where s.structure_id = ? and structure_element_id = ?";
/*      */ 
/*  128 */   protected static String SQL_ACCOUNT_LEAVES = "select 0,s.dimension_element_id, s.vis_id, s.description, decode(s.credit_debit,1,'Y','N'), s.disabled, 'Y'  from dimension_element s, model_dimension_rel m, finance_cube fc where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 1   and s.dimension_id = m.dimension_id order by s.vis_id";
/*      */ 
/*  137 */   protected static String SQL_DIMENSION_LEAVES = "select s.dimension_element_id, s.vis_id, s.description  from dimension_element s where s.dimension_id = ? order by s.vis_id asc";
/*      */ 
/*  143 */   protected static String SQL_CALENDAR_DIMENSION_LEAVES = "select s.dimension_element_id, calendar_utils.queryPathToRoot(s.dimension_element_id) vis_id  from dimension_element s where s.dimension_id = ? order by vis_id asc";
/*      */ 
/*  149 */   protected static String SQL_OTHER_BUSINESS_DIMENSIONS = "select d.dimension_id, d.vis_id, d.description  from model_dimension_rel m, finance_cube fc, dimension d where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and d.dimension_id = m.dimension_id   and m.dimension_type = 2   and m.dimension_seq_num > 0 order by m.dimension_seq_num";
/*      */ 
/*  159 */   protected static String SQL_ALL_DIMENSIONS = "select d.dimension_id, d.vis_id, d.description  from model_dimension_rel m, finance_cube fc, dimension d where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and d.dimension_id = m.dimension_id order by m.dimension_seq_num";
/*      */ 
/*  167 */   protected static String SQL_BUDGET_LOCATION_ROOT = "select s.structure_id, s.structure_element_id, s.description  from structure_element s, model m, finance_cube fc where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and s.structure_id = m.budget_hierarchy_id   and s.parent_id = 0";
/*      */ 
/*  175 */   protected static String SQL_PERIOD_ROOT = "select s.structure_id, s.structure_element_id, s.description  from structure_element s, model_dimension_rel m, finance_cube fc, hierarchy h where fc.finance_cube_id = ?   and m.model_id = fc.model_id   and m.dimension_type = 3   and h.dimension_id = m.dimension_id   and s.structure_id = h.hierarchy_id   and s.parent_id = 0";
/*      */ 
/*  185 */   protected static String SQL_GET_STRING_TABLE_NAME = "select f.string_data_table   from finance_cube f  where f.finance_cube_id = ?";
/*      */ 
/*  191 */   protected static String SQL_GET_CELL_NOTE = "select s.CREATED,s.USER_NAME,s.STRING_VALUE,s.LINK_ID, s.LINK_TYPE,\n       case when s.CREATED > nvl(u.LAST_VIEWED, to_date(''2000-01-01'', ''YYYY-MM-DD''))\n            then 0 else 1 end\n  from {0} s, {1} u\n where {2} and s.DATA_TYPE = ?\n   and {3}\n   and s.DATA_TYPE = u.DATA_TYPE (+)\n   and ? = u.USER_ID (+)\n order by s.created";

			 protected static String SQL_GET_CELL_NOTE_COMPANY = "select s.CREATED,s.USER_NAME,s.STRING_VALUE,s.LINK_ID, s.LINK_TYPE,\n       case when s.CREATED > nvl(u.LAST_VIEWED, to_date(''2000-01-01'', ''YYYY-MM-DD''))\n            then 0 else 1 end\n  from {0} s, {1} u\n where {2} and s.DATA_TYPE = ?\n   and {3}\n   and s.DATA_TYPE = u.DATA_TYPE (+)\n and ? = u.USER_ID (+)\n and s.COMPANY = ? order by s.created";
/*      */ 
/*  202 */   protected static String SQL_UPDATE_LAST_VIEWED = "merge into {0} u using\n   (select {1},DATA_TYPE,? USER_ID,CREATED\n    from (select {2},s.DATA_TYPE,max(s.CREATED) CREATED\n          from {3} s\n          where {4} and s.DATA_TYPE=?\n          group by {1},s.DATA_TYPE\n         )\n   ) s\non (\n    {5} and s.DATA_TYPE=u.DATA_TYPE and s.USER_ID=u.USER_ID\n   )\nwhen matched then\n     update set u.LAST_VIEWED=s.CREATED\nwhen not matched then\n     insert ({1},DATA_TYPE,USER_ID,LAST_VIEWED)\n     values ({2},s.DATA_TYPE,s.USER_ID,s.CREATED)";
/*      */ 
/*  220 */   protected static String SQL_XML_FORM_FROM_FORM_ID = "select x.vis_id, x.definition   from xml_form x  where x.xml_form_id = ?";
/*      */ 
/*  226 */   protected static String SQL_XML_FORM_READONLY_SECURITY_FROM_FORM_ID = "select x.security_access   from xml_form x  where x.xml_form_id = ?";
/*      */ 
/*  232 */   protected static String SQL_XML_FORM_ID_FROM_CELL_CALC_ID = "select        c.xmlform_id   from cell_calc c  where c.cell_calc_id = ? ";
/*      */ 
/*  238 */   protected static String SQL_CELL_CALCULATION_DATA = "select row_id, col_id, numeric_value, string_value   from CELL_CALCULATION_DATA  where finance_cube_id = ?    and cell_calc_short_id = ?    and row_id >= 0  order by row_id, col_id";
/*      */ 
/*  247 */   protected static String SQL_CELL_CALCULATION_SUMMARY_DATA = "select col_id, numeric_value   from CELL_CALCULATION_DATA  where finance_cube_id = ?    and cell_calc_short_id = ?    and row_id = -1 ";
/*      */ 
/*  255 */   protected static String SQL_INSERT_IMPORT_DATA = "insert into imp_exp_row ( batch_id, row_no, dim0, dim1, dim2,       dim3, dim4, dim5, dim6, dim7, dim8, dim9, data_type, number_value, string_value, date_value ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
/*      */ 
/*  260 */   protected static String SQL_FINANCE_CUBE_ID_FROM_VISID = "select fc.finance_cube_id   from FINANCE_CUBE fc, MODEL m where fc.model_id = m.model_id and m.vis_id = ? and fc.vis_id = ?";
/*      */ 
/*  265 */   protected static String SQL_FINANCE_CUBE_ID_FROM_VISID_WITHOUT_MODEL = "select fc.finance_cube_id   from FINANCE_CUBE fc where fc.vis_id = ?";
/*      */ 
/*  270 */   protected static String SQL_HIERARCHY_MAX_DEPTH = "select max(depth) from structure_element where structure_id = ?";
/*      */ 
/*      */   public DataEntryDAO()
/*      */   {
/*      */   }
/*      */ 
/*      */   public DataEntryDAO(Connection connection)
/*      */   {
/*   74 */     super(connection);
/*      */   }
/*      */ 
/*      */   public void insertImportData(int batchId, List data)
/*      */   {
/*  275 */     if (data.isEmpty()) {
/*  276 */       return;
/*      */     }
/*  278 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  279 */     PreparedStatement stmt = null;
/*      */     try
/*      */     {					
/*  283 */       stmt = getConnection().prepareStatement(SQL_INSERT_IMPORT_DATA);
/*      */ 
/*  285 */       Iterator iter = data.iterator();
/*  286 */       int row_num = 1;
/*  287 */       while (iter.hasNext())
/*      */       {
/*  289 */         Object[] row = (Object[])(Object[])iter.next();
/*  290 */         int index = 1;
/*  291 */         stmt.setInt(index++, batchId);
/*  292 */         stmt.setInt(index++, row_num++);
/*      */ 
/*  295 */         for (int i = 0; i < row.length - 2; i++)
/*      */         {
/*  297 */           stmt.setObject(index++, row[i]);
/*      */         }
/*  299 */         for (int i = row.length - 2; i < 10; i++)
/*  300 */           stmt.setInt(index++, -1);
/*  301 */         stmt.setString(index++, row[(row.length - 2)].toString());
/*  302 */         Object[] value = (Object[])(Object[])row[(row.length - 1)];
/*  303 */         stmt.setObject(index++, value[0]);
/*  304 */         stmt.setObject(index++, value[1]);
/*  305 */         stmt.setObject(index++, value[2]);
/*      */ 
/*  307 */         stmt.addBatch();
/*      */       }
/*      */ 
/*  310 */       stmt.executeBatch();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  314 */       System.err.println(sqle);
/*  315 */       sqle.printStackTrace();
/*  316 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insertImportData").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  320 */       closeStatement(stmt);
/*  321 */       closeConnection();
/*      */     }
/*      */ 
/*  324 */     if (timer != null)
/*  325 */       timer.logDebug("insertImportData", new StringBuilder().append(" items=").append(data.size()).toString());
/*      */   }
/*      */ 
/*      */   public List[] getDataExtract(int financeCubeId, List businessDims, List accountIds, List periodIds, List dataTypes, boolean fullIntersection, boolean getCount, Integer rowLimit)
/*      */   {
/*  331 */     List seIdList = new ArrayList();
/*  332 */     for (int i = 0; i < businessDims.size(); i++)
/*      */     {
/*  334 */       seIdList.addAll((List)businessDims.get(i));
/*  335 */       if (((List)businessDims.get(i)).size() != 1)
/*  336 */         throw new IllegalArgumentException(new StringBuilder().append("unexpected number of seIds in busDim ").append(i).toString());
/*      */     }
/*  338 */     seIdList.addAll(accountIds);
/*  339 */     if (accountIds.size() != 1) {
/*  340 */       throw new IllegalArgumentException("unexpected number of seIds in accountIds ");
/*      */     }
/*  342 */     seIdList.addAll(periodIds);
/*  343 */     if (periodIds.size() != 1) {
/*  344 */       throw new IllegalArgumentException("unexpected number of seIds in periodIds ");
/*      */     }
/*  346 */     if (dataTypes.size() != 1) {
/*  347 */       throw new IllegalArgumentException("unexpected number of data types");
/*      */     }
/*      */ 
/*  350 */     int nDims = seIdList.size();
/*      */ 
/*  353 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "with params as", "(", "select  <financeCubeId> FINANCE_CUBE_ID", "        ,<dataTypeId> DATA_TYPE_ID", "from    dual", ")", ",getRules as", "(", "select  DIMENSION_SEQ_NUM", "        ,nvl(ROLL_UP,decode(SUB_TYPE,0,'Y',' ')) ROLL_UP", "        ,DATA_TYPE_ID", "from    params", "join    FINANCE_CUBE_DATA_TYPE using (FINANCE_CUBE_ID, DATA_TYPE_ID)", "join    DATA_TYPE using (DATA_TYPE_ID)", "join    FINANCE_CUBE using (FINANCE_CUBE_ID)", "join    MODEL_DIMENSION_REL using (MODEL_ID)", "left", "join    ROLL_UP_RULE using (FINANCE_CUBE_ID, DIMENSION_ID, DATA_TYPE_ID)", ")", "select   case when SUB_TYPE = 4 then", "            case MEASURE_CLASS", "            when 0 then 'STRING_VALUE'", "            when 1 then 'NUMBER_VALUE / '||power(10,MEASURE_SCALE)", "            when 2 then 'to_char(DATE_VALUE,''HH24:MI:SS'')'", "            when 3 then 'to_char(DATE_VALUE,''DD/MM/YYYY'')'", "            when 4 then 'to_char(DATE_VALUE,''DD/MM/YYYY HH24:MI:SS'')'", "            when 5 then 'STRING_VALUE'", "            end", "         else 'NUMBER_VALUE / 10000'", "         end as FT_VALUE_CLAUSE", "        ,case when SUB_TYPE = 4 then", "            case MEASURE_CLASS", "            when 0 then 'max'", "            when 1 then 'sum'", "            when 2 then 'max'", "            when 3 then 'max'", "            when 4 then 'max'", "            when 5 then 'max'", "            end", "         else 'sum'", "         end as FT_GROUP_CLAUSE", "        ${selectRollUpRules}", "from    params", "join    DATA_TYPE using (DATA_TYPE_ID)" });
/*      */ 
/*  400 */     sqlb.substituteRepeatingLines("${selectRollUpRules}", nDims, ",", new String[] { ",(select ROLL_UP from getRules", "  where DIMENSION_SEQ_NUM = ${index}) as ROLL_UP_${index}" });
/*      */ 
/*  405 */     SqlExecutor sqle = new SqlExecutor("getDataExtract(dtypes)", getDataSource(), sqlb, this._log);
/*  406 */     sqle.setLogSql(false);
/*  407 */     sqle.addBindVariable("<financeCubeId>", Integer.valueOf(financeCubeId));
/*  408 */     sqle.addBindVariable("<dataTypeId>", Short.valueOf(((DataTypePK)((EntityRef)dataTypes.get(0)).getPrimaryKey()).getDataTypeId()));
/*  409 */     EntityList dataTypeCols = sqle.getEntityList();
/*      */ 
/*  412 */     StructureElementRef calRef = (StructureElementRef)seIdList.get(nDims - 1);
/*  413 */     StructureElementPK calPk = (StructureElementPK)calRef.getPrimaryKey();
/*      */ 
/*  415 */     sqlb = new SqlBuilder(new String[] { "select  calendar_utils.queryPathToRoot(STRUCTURE_ELEMENT_ID) as VIS_ID", "from    (", "        select  STRUCTURE_ID, POSITION, END_POSITION", "        from    STRUCTURE_ELEMENT", "        where   STRUCTURE_ID = <calStructureId>", "        and     STRUCTURE_ELEMENT_ID = <calElementId>", "        ) p", "join    STRUCTURE_ELEMENT se", "        on (se.STRUCTURE_ID = p.STRUCTURE_ID and", "            se.POSITION between p.POSITION and p.END_POSITION", "            ${calLeafClause})", "order   by se.POSITION" });
/*      */ 
/*  431 */     String calRollUp = (String)dataTypeCols.getValueAt(0, new StringBuilder().append("ROLL_UP_").append(String.valueOf(nDims - 1)).toString());
/*  432 */     if ((calRollUp == null) || (calRollUp.equals(" ")))
/*      */     {
/*  434 */       sqlb.substitute(new String[] { "${calLeafClause}", null });
/*      */     }
/*  436 */     else sqlb.substitute(new String[] { "${calLeafClause}", "and LEAF = 'Y'" });
/*      */ 
/*  439 */     sqle = new SqlExecutor("getDataExtract(cal)", getDataSource(), sqlb, this._log);
/*  440 */     sqle.setLogSql(false);
/*  441 */     sqle.addBindVariable("<calStructureId>", Integer.valueOf(calPk.getStructureId()));
/*  442 */     sqle.addBindVariable("<calElementId>", Integer.valueOf(calPk.getStructureElementId()));
/*  443 */     List calVisIdList = new ArrayList();
/*  444 */     ResultSet rs = sqle.getResultSet();
/*      */     try
/*      */     {
/*  447 */       while (rs.next())
/*  448 */         calVisIdList.add(rs.getString("VIS_ID"));
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  452 */       throw new RuntimeException(e);
/*      */     }
/*  454 */     sqle.close();
/*      */ 
/*  459 */     sqlb = new SqlBuilder(new String[] { "with" });
/*      */ 
/*  463 */     sqlb.addRepeatingLines(nDims, ",", new String[] { "${separator}getDim${index} as", "(", "select  DIM${index}, VIS${index}, ROWNUM as POS${index}, LEAF${index}", "from    (", "        select  STRUCTURE_ELEMENT_ID as DIM${index},VIS_ID as VIS${index} ,se.LEAF as LEAF${index}", "        from    (", "                select  STRUCTURE_ID, POSITION, END_POSITION", "                from    STRUCTURE_ELEMENT", "                where   STRUCTURE_ID = <sid.${index}>", "                and     STRUCTURE_ELEMENT_ID = <seid.${index}>", "                ) p", "        join    STRUCTURE_ELEMENT se", "                on (se.STRUCTURE_ID = p.STRUCTURE_ID and", "                    se.POSITION between p.POSITION and p.END_POSITION", "                    ${leafClause.${index}})", "        order by se.POSITION", "        )", ")" });
/*      */ 
/*  484 */     if ((getCount) && (fullIntersection))
/*      */     {
/*  486 */       sqlb.addLines(new String[] { "-- get row and column counts", "select" });
/*      */ 
/*  490 */       sqlb.addRepeatingLines(nDims - 1, "* ", new String[] { "        ${separator}(select count(*) from getDim${index})" });
/*      */ 
/*  493 */       sqlb.addLines(new String[] { "        as NUM_ROWS", "        ,(select count(*) from getDim${calDimIndex})", "        as NUM_COLS", "from    dual" });
/*      */     }
/*      */     else
/*      */     {
/*  502 */       if (fullIntersection)
/*      */       {
/*  504 */         sqlb.addLines(new String[] { "-- cartesian product of ${getDims}", ",getFull as", "(", "select  ${seIds},${visIds}", "        ,<dataType> as DATA_TYPE", "        ,${positions},${leafs}", "from    ${getDims}", ")", ",getJoin as", "(", "select  ${visIds},DATA_TYPE", "        ${getValues}", "        ,${positions},${leafs}", "from    getFull", "${fullJoinClause}", "join    DFT${financeCubeId} using (${seIds},DATA_TYPE)", ")" });
/*      */       }
/*      */       else
/*      */       {
/*  526 */         sqlb.addLines(new String[] { ",getJoin as", "(", "select  ${visIds},DATA_TYPE", "        ${getValues}", "        ,${positions},${leafs}", "from    DFT${financeCubeId}", "${joinDims}", "where   DATA_TYPE = <dataType>", ")" });
/*      */ 
/*  537 */         sqlb.substituteRepeatingLines("${joinDims}", nDims, ",", new String[] { "join    getDim${index} using (DIM${index})" });
/*      */       }
/*      */ 
/*  542 */       if (getCount)
/*      */       {
/*  544 */         sqlb.addLines(new String[] { "-- get counts", ",getFinal as", "(", "select  ${visIds},DATA_TYPE", "        ${groupValues}", "from    getJoin", "group   by ${groupBy},DATA_TYPE", ")", "select /*+ NO_QUERY_TRANSFORMATION */ count(*) as NUM_ROWS from getFinal" });
/*      */       }
/*      */       else
/*      */       {
/*  558 */         sqlb.addLines(new String[] { "select  ${hint}${visIds},DATA_TYPE", "        ${groupValues}", "from    getJoin", "group   by ${groupBy},DATA_TYPE", "order   by", "        ${sortDimCols}" });
/*      */ 
/*  566 */         sqlb.substitute(new String[] { "${hint}", fullIntersection ? null : "/*+ NO_QUERY_TRANSFORMATION */ " });
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  571 */     sqlb.substituteRepeatingLines("${getRequiredElements}", nDims, ",", new String[] { "-- required child elements for dimension ${index}", "${separator}getDim${index} as", "(", "select  DIM${index}, VIS${index}, ROWNUM as POS${index}, LEAF${index}", "from    (", "        select  STRUCTURE_ELEMENT_ID as DIM${index},VIS_ID as VIS${index}", "                ,LEAF as LEAF${index}", "        from    (", "                select  STRUCTURE_ID, POSITION, END_POSITION", "                from    STRUCTURE_ELEMENT", "                where   STRUCTURE_ID = <sid.${index}>", "                and     STRUCTURE_ELEMENT_ID = <seid.${index}>", "                ) p", "        join    STRUCTURE_ELEMENT se", "                on (se.STRUCTURE_ID = p.STRUCTURE_ID and", "                    se.POSITION between p.POSITION and p.END_POSITION", "                    ${leafClause.${index}})", "        order   by se.POSITION", "        )", ")" });
/*      */ 
/*  595 */     for (int i = 0; i < nDims; i++)
/*      */     {
/*  598 */       String rollUp = (String)dataTypeCols.getValueAt(0, new StringBuilder().append("ROLL_UP_").append(i).toString());
/*  599 */       if ((rollUp == null) || (rollUp.equals(" ")))
/*      */       {
/*  601 */         sqlb.substitute(new String[] { new StringBuilder().append("${leafClause.").append(i).append("}").toString(), null });
/*      */       }
/*  603 */       else sqlb.substitute(new String[] { new StringBuilder().append("${leafClause.").append(i).append("}").toString(), "and LEAF = 'Y'" });
/*      */     }
/*      */ 
/*  606 */     if (getCount)
/*      */     {
/*  608 */       sqlb.substitute(new String[] { "${getValues}", null, "${groupValues}", null });
/*      */     }
/*      */     else
/*      */     {
/*  615 */       sqlb.substituteRepeatingLines("${getValues}", calVisIdList.size(), null, new String[] { ",case when POS${calDimIndex} = ${num} then ${valueClause} end as VAL${num}" });
/*      */ 
/*  618 */       sqlb.substituteRepeatingLines("${groupValues}", calVisIdList.size(), null, new String[] { ",${valueGroupClause}(VAL${num}) as VAL${num}" });
/*      */     }
/*      */ 
/*  623 */     sqlb.substitute(new String[] { "${financeCubeId}", String.valueOf(financeCubeId), "${calDimIndex}", String.valueOf(nDims - 1), "${valueClause}", (String)dataTypeCols.getValueAt(0, "FT_VALUE_CLAUSE"), "${valueGroupClause}", (String)dataTypeCols.getValueAt(0, "FT_GROUP_CLAUSE"), "${seIds}", SqlBuilder.repeatString("${separator}DIM${index}", ",", nDims), "${visIds}", SqlBuilder.repeatString("${separator}VIS${index}", ",", nDims - 1), "${positions}", SqlBuilder.repeatString("${separator}POS${index}", ",", nDims), "${leafs}", SqlBuilder.repeatString("${separator}LEAF${index}", ",", nDims), "${getDims}", SqlBuilder.repeatString("${separator}getDim${index}", ",", nDims), "${leadDimColNums}", SqlBuilder.repeatString("${separator}${num}", ",", nDims - 1), "${fullJoinClause}", fullIntersection ? "left" : "", "${fullIntersect}", fullIntersection ? "'Y'" : "' '", "${groupBy}", SqlBuilder.repeatString("${separator}VIS${index},POS${index},LEAF${index}", ",", nDims - 1) });
/*      */ 
/*  640 */     sqlb.substituteRepeatingLines("${sortDimCols}", nDims - 1, ",", new String[] { "${separator}decode(LEAF${index},'Y','1'||VIS${index},'0'||to_char(10000000+POS${index}))" });
/*      */ 
/*  644 */     sqle = new SqlExecutor("getDataExtract(main)", getDataSource(), sqlb, this._log);
/*  645 */     sqle.setFetchSize(Integer.valueOf(1024));
/*  646 */     sqle.setMaxRows(rowLimit);
/*      */ 
/*  648 */     sqle.addBindVariable("<dataType>", ((EntityRef)dataTypes.get(0)).getNarrative());
/*  649 */     sqle.addBindVariable("<dataTypeId>", Short.valueOf(((DataTypePK)((EntityRef)dataTypes.get(0)).getPrimaryKey()).getDataTypeId()));
/*  650 */     for (int i = 0; i < seIdList.size(); i++)
/*      */     {
/*  652 */       StructureElementRef seRef = (StructureElementRef)seIdList.get(i);
/*  653 */       StructureElementPK pk = (StructureElementPK)seRef.getPrimaryKey();
/*  654 */       sqle.addBindVariable(new StringBuilder().append("<sid.").append(i).append(">").toString(), Integer.valueOf(pk.getStructureId()));
/*  655 */       sqle.addBindVariable(new StringBuilder().append("<seid.").append(i).append(">").toString(), Integer.valueOf(pk.getStructureElementId()));
/*  656 */       if (i == seIdList.size() - 1) {
/*  657 */         sqle.addBindVariable("<calStructureId>", Integer.valueOf(pk.getStructureId()));
/*      */       }
/*      */     }
/*  660 */     return new List[] { calVisIdList, sqle.getList() };
/*      */   }
/*      */ 
/*      */   public List getFinanceCubeDataSlice(int financeCubeId, String aggregationRule, int[] rowHeadings, int[] columnHeadings, int[] filterHeadings, List rows, List columns, List filters)
/*      */   {
/*  667 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  668 */     PreparedStatement stmt = null;
/*  669 */     ResultSet resultSet = null;
/*  670 */     List results = new ArrayList();
/*      */     try
/*      */     {
/*  674 */       int structureCount = rowHeadings.length + columnHeadings.length + filterHeadings.length;
/*  675 */       int dtIndex = structureCount - 1;
/*      */ 
/*  678 */       StringBuffer groupBy = new StringBuffer(" \n group by 1");
/*  679 */       StringBuffer sql = new StringBuffer();
/*  680 */       sql.append("select 1");
/*  681 */       for (int i = 0; i < rowHeadings.length; i++)
/*      */       {
/*  683 */         int dim = rowHeadings[i];
/*  684 */         if (dim == dtIndex)
/*      */         {
/*  686 */           sql.append(",data_type");
/*  687 */           groupBy.append(",data_type");
/*      */         }
/*      */         else
/*      */         {
/*  691 */           sql.append(new StringBuilder().append(",dim").append(dim).toString());
/*  692 */           groupBy.append(new StringBuilder().append(",dim").append(dim).toString());
/*      */         }
/*      */       }
/*  695 */       for (int i = 0; i < columnHeadings.length; i++)
/*      */       {
/*  697 */         int dim = columnHeadings[i];
/*  698 */         if (dim == dtIndex)
/*      */         {
/*  700 */           sql.append(",data_type");
/*  701 */           groupBy.append(",data_type");
/*      */         }
/*      */         else
/*      */         {
/*  705 */           sql.append(new StringBuilder().append(",dim").append(dim).toString());
/*  706 */           groupBy.append(new StringBuilder().append(",dim").append(dim).toString());
/*      */         }
/*      */       }
/*  709 */       sql.append(",");
/*  710 */       sql.append(aggregationRule);
/*  711 */       sql.append("(public_value/");
/*  712 */       sql.append(10000);
/*  713 */       sql.append(")\n  from nft");
/*  714 */       sql.append(financeCubeId);
/*  715 */       sql.append("\n where public_value <> 0");
/*      */ 
/*  717 */       for (int i = 0; i < rowHeadings.length; i++)
/*      */       {
/*  719 */         int dim = rowHeadings[i];
/*  720 */         Set vars = (Set)rows.get(i);
/*      */ 
/*  723 */         sql.append("\n    and (");
/*  724 */         int blockSize = 500;
/*  725 */         int numBlocks = 1 + vars.size() / blockSize;
/*  726 */         for (int block = 0; block < numBlocks; block++)
/*      */         {
/*  729 */           int count = vars.size() - block * blockSize;
/*  730 */           if (count > blockSize)
/*  731 */             count = blockSize;
/*  732 */           if (count <= 0)
/*      */             continue;
/*  734 */           if (block == 0)
/*  735 */             sql.append("\n         (");
/*      */           else
/*  737 */             sql.append("\n         or (");
/*  738 */           if (dim == dtIndex)
/*  739 */             sql.append("\n   data_type in ( ''");
/*      */           else
/*  741 */             sql.append(new StringBuilder().append("\n   dim").append(dim).append(" in (-1").toString());
/*  742 */           for (int j = 0; j < count; j++)
/*  743 */             sql.append(",?");
/*  744 */           sql.append("))");
/*      */         }
/*      */ 
/*  747 */         sql.append(")");
/*      */       }
/*      */ 
/*  750 */       for (int i = 0; i < columnHeadings.length; i++)
/*      */       {
/*  752 */         int dim = columnHeadings[i];
/*  753 */         Set vars = (Set)columns.get(i);
/*      */ 
/*  756 */         sql.append("\n    and (");
/*  757 */         int blockSize = 500;
/*  758 */         int numBlocks = 1 + vars.size() / blockSize;
/*  759 */         for (int block = 0; block < numBlocks; block++)
/*      */         {
/*  762 */           int count = vars.size() - block * blockSize;
/*  763 */           if (count > blockSize)
/*  764 */             count = blockSize;
/*  765 */           if (count <= 0)
/*      */             continue;
/*  767 */           if (block == 0)
/*  768 */             sql.append("\n         (");
/*      */           else
/*  770 */             sql.append("\n         or (");
/*  771 */           if (dim == dtIndex)
/*  772 */             sql.append("\n   data_type in ( ''");
/*      */           else
/*  774 */             sql.append(new StringBuilder().append("\n   dim").append(dim).append(" in (-1").toString());
/*  775 */           for (int j = 0; j < count; j++)
/*  776 */             sql.append(",?");
/*  777 */           sql.append("))");
/*      */         }
/*      */ 
/*  780 */         sql.append(")");
/*      */       }
/*      */ 
/*  783 */       for (int i = 0; i < filterHeadings.length; i++)
/*      */       {
/*  785 */         int dim = filterHeadings[i];
/*  786 */         Set vars = (Set)filters.get(i);
/*      */ 
/*  789 */         sql.append("\n    and (");
/*  790 */         int blockSize = 500;
/*  791 */         int numBlocks = 1 + vars.size() / blockSize;
/*  792 */         for (int block = 0; block < numBlocks; block++)
/*      */         {
/*  795 */           int count = vars.size() - block * blockSize;
/*  796 */           if (count > blockSize)
/*  797 */             count = blockSize;
/*  798 */           if (count <= 0)
/*      */             continue;
/*  800 */           if (block == 0)
/*  801 */             sql.append("\n         (");
/*      */           else
/*  803 */             sql.append("\n         or (");
/*  804 */           if (dim == dtIndex)
/*  805 */             sql.append("\n   data_type in ( ''");
/*      */           else
/*  807 */             sql.append(new StringBuilder().append("\n   dim").append(dim).append(" in (-1").toString());
/*  808 */           for (int j = 0; j < count; j++)
/*  809 */             sql.append(",?");
/*  810 */           sql.append("))");
/*      */         }
/*      */ 
/*  813 */         sql.append(")");
/*      */       }
/*      */ 
/*  817 */       sql.append(groupBy);
/*  818 */       stmt = getConnection().prepareStatement(sql.toString());
/*  819 */       if (this.mLog.isDebugEnabled()) {
/*  820 */         this.mLog.debug("getFinanceCubeDataSlice", new StringBuilder().append("SQL is :\n").append(sql).toString());
/*      */       }
/*      */ 
/*  823 */       int index = 1;
/*  824 */       for (int i = 0; i < rowHeadings.length; i++)
/*      */       {
/*  826 */         Set vars = (Set)rows.get(i);
/*  827 */         Iterator iter = vars.iterator();
/*  828 */         while (iter.hasNext())
/*      */         {
/*  830 */           Object var = iter.next();
/*  831 */           if (this.mLog.isDebugEnabled())
/*  832 */             this.mLog.debug("getFinanceCubeDataSlice", new StringBuilder().append("Bind var is ").append(var).toString());
/*  833 */           if ((var instanceof Integer))
/*  834 */             stmt.setInt(index++, ((Integer)var).intValue());
/*      */           else
/*  836 */             stmt.setString(index++, var.toString());
/*      */         }
/*      */       }
/*  839 */       for (int i = 0; i < columnHeadings.length; i++)
/*      */       {
/*  841 */         Set vars = (Set)columns.get(i);
/*  842 */         Iterator iter = vars.iterator();
/*  843 */         while (iter.hasNext())
/*      */         {
/*  845 */           Object var = iter.next();
/*  846 */           if (this.mLog.isDebugEnabled())
/*  847 */             this.mLog.debug("getFinanceCubeDataSlice", new StringBuilder().append("Bind var is ").append(var).toString());
/*  848 */           if ((var instanceof Integer))
/*  849 */             stmt.setInt(index++, ((Integer)var).intValue());
/*      */           else
/*  851 */             stmt.setString(index++, var.toString());
/*      */         }
/*      */       }
/*  854 */       for (int i = 0; i < filters.size(); i++)
/*      */       {
/*  856 */         Set vars = (Set)filters.get(i);
/*  857 */         Iterator iter = vars.iterator();
/*  858 */         while (iter.hasNext())
/*      */         {
/*  860 */           Object var = iter.next();
/*  861 */           if (this.mLog.isDebugEnabled())
/*  862 */             this.mLog.debug("getFinanceCubeDataSlice", new StringBuilder().append("Bind var is ").append(var).toString());
/*  863 */           if ((var instanceof Integer))
/*  864 */             stmt.setInt(index++, ((Integer)var).intValue());
/*      */           else {
/*  866 */             stmt.setString(index++, var.toString());
/*      */           }
/*      */         }
/*      */       }
/*  870 */       stmt.setFetchSize(1024);
/*  871 */       resultSet = stmt.executeQuery();
/*  872 */       int colCount = rowHeadings.length + columnHeadings.length + 1;
/*  873 */       while (resultSet.next())
/*      */       {
/*  875 */         Object[] row = new Object[colCount];
/*  876 */         resultSet.getInt(1);
/*  877 */         for (int i = 0; i < colCount; i++)
/*      */         {
/*  879 */           row[i] = resultSet.getObject(i + 2);
/*      */         }
/*      */ 
/*  882 */         results.add(row);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  888 */       System.err.println(sqle);
/*  889 */       sqle.printStackTrace();
/*  890 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getFinanceCubeDataSlice").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  894 */       closeResultSet(resultSet);
/*  895 */       closeStatement(stmt);
/*  896 */       closeConnection();
/*      */     }
/*      */ 
/*  899 */     if (timer != null) {
/*  900 */       timer.logDebug("getFinanceCubeDataSlice", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*  902 */     return results;
/*      */   }
/*      */ 
/*      */   public int getFinanceCubeIdFromVisId(String modelVisId, String fcVisId)
/*      */   {
/*  908 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  909 */     PreparedStatement stmt = null;
/*  910 */     ResultSet resultSet = null;
/*  911 */     int results = 0;
/*      */     try
/*      */     {
/*  915 */       if ((modelVisId == null) || (modelVisId.trim().length() == 0))
/*      */       {
/*  917 */         stmt = getConnection().prepareStatement(SQL_FINANCE_CUBE_ID_FROM_VISID_WITHOUT_MODEL);
/*  918 */         int col1 = 1;
/*  919 */         stmt.setString(col1++, fcVisId);
/*  920 */         resultSet = stmt.executeQuery();
/*      */       }
/*      */       else
/*      */       {
/*  924 */         stmt = getConnection().prepareStatement(SQL_FINANCE_CUBE_ID_FROM_VISID);
/*  925 */         int col1 = 1;
/*  926 */         stmt.setString(col1++, modelVisId);
/*  927 */         stmt.setString(col1++, fcVisId);
/*  928 */         resultSet = stmt.executeQuery();
/*      */       }
/*  930 */       if (resultSet.next())
/*      */       {
/*  932 */         results = resultSet.getInt(1);
/*      */       }
/*  934 */       if (resultSet.next())
/*  935 */         throw new IllegalStateException(new StringBuilder().append("Non-unique finace cube with vis_id ").append(fcVisId).toString());
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  939 */       System.err.println(sqle);
/*  940 */       sqle.printStackTrace();
/*  941 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getFinanceCubeIdFromVisId").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  945 */       closeResultSet(resultSet);
/*  946 */       closeStatement(stmt);
/*  947 */       closeConnection();
/*      */     }
/*      */ 
/*  950 */     if (timer != null) {
/*  951 */       timer.logDebug("getFinanceCubeIdFromVisId", new StringBuilder().append(" cubeId=").append(results).toString());
/*      */     }
/*  953 */     return results;
/*      */   }
/*      */ 
/*      */   public List getDescendentLeafElements(int rootedAtId)
/*      */   {
/*  964 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*  965 */     PreparedStatement stmt = null;
/*  966 */     ResultSet resultSet = null;
/*  967 */     List results = new ArrayList();
/*      */     try
/*      */     {
/*  970 */       stmt = getConnection().prepareStatement(SQL_GET_DESCENDENT_LEAF_ELEMENTS);
/*  971 */       int col1 = 1;
/*  972 */       stmt.setInt(col1++, rootedAtId);
/*  973 */       resultSet = stmt.executeQuery();
/*  974 */       while (resultSet.next())
/*      */       {
/*  976 */         int index = 1;
/*      */ 
/*  978 */         int id = resultSet.getInt(index++);
/*  979 */         results.add(new Integer(id));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  984 */       System.err.println(sqle);
/*  985 */       sqle.printStackTrace();
/*  986 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" AvailableStructureElements").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/*  990 */       closeResultSet(resultSet);
/*  991 */       closeStatement(stmt);
/*  992 */       closeConnection();
/*      */     }
/*      */ 
/*  995 */     if (timer != null) {
/*  996 */       timer.logDebug("getAvailableStructureElement", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/*  998 */     return results;
/*      */   }
/*      */ 
/*      */   public AvailableStructureElementELO getStructureElements(int rootedAtId, int dimId, String visId)
/*      */   {
/* 1011 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1012 */     PreparedStatement stmt = null;
/* 1013 */     ResultSet resultSet = null;
/* 1014 */     AvailableStructureElementELO results = new AvailableStructureElementELO();
/*      */     try
/*      */     {
/* 1017 */       if (rootedAtId > 0)
/* 1018 */         stmt = getConnection().prepareStatement(SQL_AVAILABLE_ELEMENTS_ROOTED_AT);
/*      */       else
/* 1020 */         stmt = getConnection().prepareStatement(SQL_AVAILABLE_ELEMENTS);
/* 1021 */       int col1 = 1;
/* 1022 */       if (rootedAtId > 0)
/* 1023 */         stmt.setInt(col1++, rootedAtId);
/* 1024 */       stmt.setInt(col1++, dimId);
/* 1025 */       stmt.setString(col1++, visId);
/* 1026 */       resultSet = stmt.executeQuery();
/* 1027 */       while (resultSet.next())
/*      */       {
/* 1029 */         int index = 1;
/*      */ 
/* 1031 */         int level = resultSet.getInt(index++);
/* 1032 */         int id = resultSet.getInt(index++);
/* 1033 */         String vis = resultSet.getString(index++);
/* 1034 */         String label = resultSet.getString(index++);
/* 1035 */         String isCredit = resultSet.getString(index++);
/* 1036 */         String disabled = resultSet.getString(index++);
/* 1037 */         String notPlannable = resultSet.getString(index++);
/* 1038 */         String leaf = resultSet.getString(index++);
/* 1039 */         Integer position = Integer.valueOf(resultSet.getInt(index++));
/*      */ 
/* 1041 */         results.add(new Integer(level), null, new Integer(id), vis, label, isCredit, disabled, notPlannable, leaf, position);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1057 */       System.err.println(sqle);
/* 1058 */       sqle.printStackTrace();
/* 1059 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" AvailableStructureElements").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1063 */       closeResultSet(resultSet);
/* 1064 */       closeStatement(stmt);
/* 1065 */       closeConnection();
/*      */     }
/*      */ 
/* 1068 */     if (timer != null) {
/* 1069 */       timer.logDebug("getAvailableStructureElement", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/* 1071 */     return results;
/*      */   }
/*      */ 
/*      */   public AvailableStructureElementELO getRootedStructureElement(int rootedAtId, int dimId, int hierId)
/*      */   {
/* 1084 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1085 */     PreparedStatement stmt = null;
/* 1086 */     ResultSet resultSet = null;
/* 1087 */     AvailableStructureElementELO results = new AvailableStructureElementELO();
/*      */     try
/*      */     {
/* 1090 */       if (rootedAtId > 0)
/* 1091 */         stmt = getConnection().prepareStatement(SQL_AVAILABLE_ELEMENTS_ROOTED_AT_2);
/*      */       else
/* 1093 */         stmt = getConnection().prepareStatement(SQL_AVAILABLE_ELEMENTS_2);
/* 1094 */       int col1 = 1;
/* 1095 */       stmt.setInt(col1++, hierId);
/* 1096 */       if (rootedAtId > 0)
/* 1097 */         stmt.setInt(col1++, rootedAtId);
/* 1098 */       resultSet = stmt.executeQuery();
/* 1099 */       while (resultSet.next())
/*      */       {
/* 1101 */         int index = 1;
/*      */ 
/* 1103 */         int level = resultSet.getInt(index++);
/* 1104 */         int id = resultSet.getInt(index++);
/* 1105 */         StructureElementPK primaryKey = new StructureElementPK(hierId, id);
/* 1106 */         String vis = resultSet.getString(index++);
/* 1107 */         String label = resultSet.getString(index++);
/* 1108 */         String isCredit = resultSet.getString(index++);
/* 1109 */         String disabled = resultSet.getString(index++);
/* 1110 */         String notPlannable = resultSet.getString(index++);
/* 1111 */         String leaf = resultSet.getString(index++);
/* 1112 */         Integer position = Integer.valueOf(resultSet.getInt(index++));
/*      */ 
/* 1114 */         results.add(new Integer(level), primaryKey, new Integer(id), vis, label, isCredit, disabled, notPlannable, leaf, position);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1130 */       System.err.println(sqle);
/* 1131 */       sqle.printStackTrace();
/* 1132 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" AvailableStructureElements").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1136 */       closeResultSet(resultSet);
/* 1137 */       closeStatement(stmt);
/* 1138 */       closeConnection();
/*      */     }
/*      */ 
/* 1141 */     if (timer != null) {
/* 1142 */       timer.logDebug("getAvailableStructureElement", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/* 1144 */     return results;
/*      */   }
/*      */ 
/*      */   public Map getFinanceCubeDataForExtract(int fcId, int[] structureElementIds, String dataType)
/*      */   {
/* 1160 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1161 */     PreparedStatement stmt = null;
/* 1162 */     ResultSet resultSet = null;
/* 1163 */     Map results = new HashMap();
/*      */     try
/*      */     {
/* 1167 */       int nDims = structureElementIds.length - 1;
/*      */ 
/* 1169 */       StringBuffer sql = new StringBuffer();
/* 1170 */       sql.append(new StringBuilder().append("with params as (\n  select vis_id, structure_id, structure_element_id, position, end_position\n  from structure_element se1 \n  where se1.structure_element_id = ?\n)\n select se.structure_element_id,public_value/10000\n from nft").append(fcId).append(" nft\n").append("    ,structure_element se\n").append("    ,params \n").append(" where \n").append("      se.structure_id = params.structure_id\n").append("  and se.leaf = 'Y' \n").append("  and se.position >= params.position \n").append("  and se.position <= params.end_position\n").append("  and nft.dim").append(nDims).append(" = se.structure_element_id\n").append("  and nft.data_type = ?").toString());
/*      */ 
/* 1186 */       for (int i = 0; i < nDims; i++)
/*      */       {
/* 1188 */         sql.append(new StringBuilder().append("\n  and nft.dim").append(i).append(" = ?").toString());
/*      */       }
/* 1190 */       this.mLog.debug(sql.toString());
/* 1191 */       stmt = getConnection().prepareStatement(sql.toString());
/*      */ 
/* 1193 */       int col1 = 1;
/* 1194 */       stmt.setInt(col1++, structureElementIds[nDims]);
/* 1195 */       stmt.setString(col1++, dataType);
/* 1196 */       for (int i = 0; i < nDims; i++)
/*      */       {
/* 1198 */         stmt.setInt(col1++, structureElementIds[i]);
/*      */       }
/* 1200 */       resultSet = stmt.executeQuery();
/* 1201 */       while (resultSet.next())
/*      */       {
/* 1203 */         int index = 1;
/*      */ 
/* 1205 */         int structureElementId = resultSet.getInt(index++);
/* 1206 */         Object value = resultSet.getObject(index++);
/*      */ 
/* 1208 */         results.put(new Integer(structureElementId), value);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1213 */       System.err.println(sqle);
/* 1214 */       sqle.printStackTrace();
/* 1215 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetLocationRoot").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1219 */       closeResultSet(resultSet);
/* 1220 */       closeStatement(stmt);
/* 1221 */       closeConnection();
/*      */     }
/*      */ 
/* 1224 */     if (timer != null) {
/* 1225 */       timer.logDebug("getBudgetLocationRoot", new StringBuilder().append(" result=").append(results).toString());
/*      */     }
/* 1227 */     return results;
/*      */   }
/*      */ 
/*      */   public Object getSingleCellValue(int fcId, int[] structureElementIds, String dataType)
/*      */   {
/* 1242 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1243 */     PreparedStatement stmt = null;
/* 1244 */     ResultSet resultSet = null;
/* 1245 */     Object result = null;
/*      */     try
/*      */     {
/* 1249 */       int nDims = structureElementIds.length;
/*      */ 
/* 1251 */       StringBuffer sql = new StringBuffer();
/* 1252 */       sql.append(new StringBuilder().append("select public_value/10000\n from nft").append(fcId).append(" \n").append(" where data_type = ?\n").toString());
/*      */ 
/* 1255 */       for (int i = 0; i < nDims; i++)
/*      */       {
/* 1257 */         sql.append(new StringBuilder().append("\n  and dim").append(i).append(" = ?").toString());
/*      */       }
/* 1259 */       this.mLog.debug(sql.toString());
/* 1260 */       stmt = getConnection().prepareStatement(sql.toString());
/*      */ 
/* 1262 */       int col1 = 1;
/* 1263 */       stmt.setString(col1++, dataType);
/* 1264 */       for (int i = 0; i < nDims; i++)
/*      */       {
/* 1266 */         stmt.setInt(col1++, structureElementIds[i]);
/*      */       }
/* 1268 */       resultSet = stmt.executeQuery();
/* 1269 */       if (resultSet.next())
/*      */       {
/* 1271 */         int index = 1;
/* 1272 */         result = resultSet.getObject(index++);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1277 */       System.err.println(sqle);
/* 1278 */       sqle.printStackTrace();
/* 1279 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetLocationRoot").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1283 */       closeResultSet(resultSet);
/* 1284 */       closeStatement(stmt);
/* 1285 */       closeConnection();
/*      */     }
/*      */ 
/* 1288 */     if (timer != null) {
/* 1289 */       timer.logDebug("getBudgetLocationRoot", new StringBuilder().append(" result=").append(result).toString());
/*      */     }
/* 1291 */     return result;
/*      */   }
/*      */ 
/*      */   public BigDecimal getFinanceCubeCell(String modelVisId, String financeCubeVisId, boolean ytd, String[] structureVisIds, String[] elementVisIds, String dataType)
/*      */   {
/* 1309 */     int fcId = getFinanceCubeIdFromVisId(modelVisId, financeCubeVisId);
/* 1310 */     if (fcId == 0)
/* 1311 */       throw new IllegalArgumentException("Invalid finance cube vis id");
/* 1312 */     return getFinanceCubeCell(modelVisId, fcId, ytd, structureVisIds, elementVisIds, dataType);
/*      */   }
/*      */ 
/*      */   public BigDecimal getFinanceCubeCell(String modelVisId, int fcId, boolean ytd, String[] structureVisIds, String[] elementVisIds, String dataType)
/*      */   {
/* 1330 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1332 */     int nDims = structureVisIds.length;
/* 1333 */     int calDimIndex = nDims - 1;
/*      */ 
/* 1335 */     PreparedStatement stmt = null;
/* 1336 */     ResultSet resultSet = null;
/* 1337 */     BigDecimal results = null;
/*      */     try
/*      */     {
/* 1342 */       List<String[]> hostVariables = new ArrayList<String[]>();
/*      */ 
/* 1344 */       SqlBuilder sqls = new SqlBuilder(new String[] { "with getModelDims as", "(", "    select  DIMENSION_ID, DIMENSION_SEQ_NUM", "    from    MODEL", "    join    MODEL_DIMENSION_REL using (MODEL_ID)", "    where   VIS_ID = ?", ")", "select  sum(PUBLIC_VALUE) / ${scaleFactor}", "from    NFT${financeCubeId}", "where   DATA_TYPE = ?" });
/*      */ 
/* 1357 */       hostVariables.add(new String[] { "modelVisId", modelVisId });
/* 1358 */       hostVariables.add(new String[] { "dataType", dataType });
/*      */ 
/* 1360 */       sqls.addRepeatingLines(nDims - 1, ",", new String[] { "and     DIM${index} =", "            (", "            select  STRUCTURE_ELEMENT_ID", "            from    getModelDims gmd", "            join    HIERARCHY h", "                    on (DIMENSION_SEQ_NUM = ${index} and", "                        h.DIMENSION_ID = gmd.DIMENSION_ID)", "            join    STRUCTURE_ELEMENT se on (STRUCTURE_ID = HIERARCHY_ID)", "            where   h.VIS_ID = ?", "            and     se.VIS_ID = ?", "            )" });
/*      */ 
/* 1374 */       for (int i = 0; i < nDims - 1; i++)
/*      */       {
/* 1376 */         hostVariables.add(new String[] { new StringBuilder().append("hierVisId[").append(i).append("]").toString(), structureVisIds[i] });
/* 1377 */         hostVariables.add(new String[] { new StringBuilder().append("elemVisId[").append(i).append("]").toString(), elementVisIds[i] });
/*      */       }
/*      */ 
/* 1380 */       sqls.addLines(new String[] { "and     DIM${calDimIndex} in", "            (", "                with getCalElem as", "                (", "                select  se.STRUCTURE_ELEMENT_ID", "                        ,se.POSITION", "                        ,se.STRUCTURE_ID", "                        ,se.VIS_ID", "                        ,case when 'Y' = ? then", "                            (", "                            select  POSITION", "                            from    STRUCTURE_ELEMENT ye", "                            where   ye.STRUCTURE_ID = se.STRUCTURE_ID", "                            and     ye.CAL_ELEM_TYPE = 0", "                            and     se.POSITION between ye.POSITION", "                                                    and ye.END_POSITION", "                            )", "                         else null", "                         end as YEAR_POSITION", "                from    getModelDims gmd", "                join    HIERARCHY h", "                        on (DIMENSION_SEQ_NUM = ${calDimIndex} and", "                            h.DIMENSION_ID = gmd.DIMENSION_ID)", "                join    STRUCTURE_ELEMENT se on (STRUCTURE_ID = HIERARCHY_ID)", "                where   h.VIS_ID = ?", "                and     se.STRUCTURE_ELEMENT_ID = calendar_utils.findElement(HIERARCHY_ID,?)", "                )", "                ,getCalYtd as", "                (", "                select  se.STRUCTURE_ELEMENT_ID, se.VIS_ID", "                from    getCalElem", "                        ,STRUCTURE_ELEMENT se", "                where   se.STRUCTURE_ID = getCalElem.STRUCTURE_ID", "                and     se.POSITION between getCalElem.YEAR_POSITION", "                                        and (getCalElem.POSITION - 1)", "                and     se.LEAF = 'Y'", "                )", "            select  STRUCTURE_ELEMENT_ID from getCalYtd where 'Y' = ?", "            union all", "            select  STRUCTURE_ELEMENT_ID from getCalElem", "            )" });
/*      */ 
/* 1424 */       hostVariables.add(new String[] { "ytd", ytd ? "Y" : "N" });
/* 1425 */       hostVariables.add(new String[] { new StringBuilder().append("hierVisId[").append(calDimIndex).append("]").toString(), structureVisIds[calDimIndex] });
/* 1426 */       hostVariables.add(new String[] { new StringBuilder().append("elemVisId]").append(calDimIndex).append("]").toString(), elementVisIds[calDimIndex] });
/* 1427 */       hostVariables.add(new String[] { "ytd", ytd ? "Y" : "N" });
/*      */ 
/* 1429 */       sqls.substitute(new String[] { "${financeCubeId}", String.valueOf(fcId), "${calDimIndex}", String.valueOf(calDimIndex), "${scaleFactor}", String.valueOf(10000) });
/*      */ 
/* 1435 */       this._log.debug("getFinanceCubeCell", sqls.toString());
/* 1436 */       stmt = getConnection().prepareStatement(sqls.toString());
/*      */ 
/* 1438 */       int col = 1;
/* 1439 */       for (String[] hvPair : hostVariables)
/*      */       {
/* 1441 */         this._log.debug("getFinanceCubeCell", new StringBuilder().append(hvPair[0]).append("=").append(hvPair[1]).toString());
/* 1442 */         stmt.setString(col++, hvPair[1]);
/*      */       }
/* 1444 */       resultSet = stmt.executeQuery();
/* 1445 */       if (resultSet.next())
/* 1446 */         results = resultSet.getBigDecimal(1);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1450 */       sqle.printStackTrace();
/* 1451 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getFinanceCubeCell").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1455 */       closeResultSet(resultSet);
/* 1456 */       closeStatement(stmt);
/* 1457 */       closeConnection();
/*      */     }
/*      */ 
/* 1460 */     if (timer != null) {
/* 1461 */       timer.logDebug("getFinanceCubeCell", new StringBuilder().append(" result=").append(results).toString());
/*      */     }
/* 1463 */     return results;
/*      */   }
/*      */ 
/*      */   public BigDecimal getFinanceCubeCell(String modelVisId, int fcId, boolean ytd, int[] structureIds, String[] elementVisIds, String dataType)
/*      */   {
/* 1481 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/*      */ 
/* 1483 */     int nDims = structureIds.length;
/* 1484 */     int calDimIndex = nDims - 1;
/*      */ 
/* 1486 */     PreparedStatement stmt = null;
/* 1487 */     ResultSet resultSet = null;
/* 1488 */     BigDecimal results = null;
/*      */     try
/*      */     {
/* 1493 */       List<String[]> hostVariables = new ArrayList<String[]>();
/*      */ 
/* 1495 */       SqlBuilder sqls = new SqlBuilder(new String[] { "select  sum(PUBLIC_VALUE) / ${scaleFactor}", "from    NFT${financeCubeId}", "where   DATA_TYPE = ?" });
/*      */ 
/* 1501 */       hostVariables.add(new String[] { "dataType", dataType });
/*      */ 
/* 1503 */       sqls.addRepeatingLines(nDims - 1, ",", new String[] { "and     DIM${index} =", "            (", "            select  STRUCTURE_ELEMENT_ID", "            from    structure_element se", "            where   se.structure_id = ?", "            and     se.vis_id = ?", "            )" });
/*      */ 
/* 1513 */       for (int i = 0; i < nDims - 1; i++)
/*      */       {
/* 1515 */         hostVariables.add(new String[] { new StringBuilder().append("hierId[").append(i).append("]").toString(), String.valueOf(structureIds[i]) });
/* 1516 */         hostVariables.add(new String[] { new StringBuilder().append("elemVisId[").append(i).append("]").toString(), elementVisIds[i] });
/*      */       }
/*      */ 
/* 1519 */       sqls.addLines(new String[] { "and     DIM${calDimIndex} in", "            (", "                with getCalElem as", "                (", "                select  se.STRUCTURE_ELEMENT_ID", "                        ,se.POSITION", "                        ,se.STRUCTURE_ID", "                        ,se.VIS_ID", "                        ,case when 'Y' = ? then", "                            (", "                            select  POSITION", "                            from    STRUCTURE_ELEMENT ye", "                            where   ye.STRUCTURE_ID = se.STRUCTURE_ID", "                            and     ye.CAL_ELEM_TYPE = 0", "                            and     se.POSITION between ye.POSITION", "                                                    and ye.END_POSITION", "                            )", "                         else null", "                         end as YEAR_POSITION", "                from    structure_element se", "                where   se.STRUCTURE_ID = ?", "                and     se.STRUCTURE_ELEMENT_ID = calendar_utils.findElement(se.STRUCTURE_ID,?)", "                )", "                ,getCalYtd as", "                (", "                select  se.STRUCTURE_ELEMENT_ID, se.VIS_ID", "                from    getCalElem", "                        ,STRUCTURE_ELEMENT se", "                where   se.STRUCTURE_ID = getCalElem.STRUCTURE_ID", "                and     se.POSITION between getCalElem.YEAR_POSITION", "                                        and (getCalElem.POSITION - 1)", "                and     se.LEAF = 'Y'", "                )", "            select  STRUCTURE_ELEMENT_ID from getCalYtd where 'Y' = ?", "            union all", "            select  STRUCTURE_ELEMENT_ID from getCalElem", "            )" });
/*      */ 
/* 1559 */       hostVariables.add(new String[] { "ytd", ytd ? "Y" : "N" });
/* 1560 */       hostVariables.add(new String[] { new StringBuilder().append("hierId[").append(calDimIndex).append("]").toString(), String.valueOf(structureIds[calDimIndex]) });
/* 1561 */       hostVariables.add(new String[] { new StringBuilder().append("elemVisId]").append(calDimIndex).append("]").toString(), elementVisIds[calDimIndex] });
/* 1562 */       hostVariables.add(new String[] { "ytd", ytd ? "Y" : "N" });
/*      */ 
/* 1564 */       sqls.substitute(new String[] { "${financeCubeId}", String.valueOf(fcId), "${calDimIndex}", String.valueOf(calDimIndex), "${scaleFactor}", String.valueOf(10000) });
/*      */ 
/* 1570 */       this._log.debug("getFinanceCubeCell", sqls.toString());
/* 1571 */       stmt = getConnection().prepareStatement(sqls.toString());
/*      */ 
/* 1573 */       int col = 1;
/* 1574 */       for (String[] hvPair : hostVariables)
/*      */       {
/* 1576 */         this._log.debug("getFinanceCubeCell", new StringBuilder().append(hvPair[0]).append("=").append(hvPair[1]).toString());
/* 1577 */         stmt.setString(col++, hvPair[1]);
/*      */       }
/* 1579 */       resultSet = stmt.executeQuery();
/* 1580 */       if (resultSet.next())
/* 1581 */         results = resultSet.getBigDecimal(1);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1585 */       sqle.printStackTrace();
/* 1586 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getFinanceCubeCell").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1590 */       closeResultSet(resultSet);
/* 1591 */       closeStatement(stmt);
/* 1592 */       closeConnection();
/*      */     }
/*      */ 
/* 1595 */     if (timer != null) {
/* 1596 */       timer.logDebug("getFinanceCubeCell", new StringBuilder().append(" result=").append(results).toString());
/*      */     }
/* 1598 */     return results;
/*      */   }
/*      */ 
/*      */   public Object getCellValue(int fcId, int[] structureElementIds, String dataType)
/*      */   {
/* 1603 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 1604 */     PreparedStatement stmt = null;
/* 1605 */     Object result = null;
/* 1606 */     ResultSet resultSet = null;
/* 1607 */     List results = null;
/*      */     try
/*      */     {
/* 1612 */       int nDims = structureElementIds.length;
/*      */ 
/* 1614 */       StringBuffer sql = new StringBuffer();
/* 1615 */       sql.append("select number_value / power(10, (select nvl(measure_scale,4) from data_type where vis_id = ?)), ");
/* 1616 */       sql.append("string_value, date_value");
/* 1617 */       sql.append(new StringBuilder().append("\n from DFT").append(fcId).toString());
/* 1618 */       sql.append("\n where data_type = ? ");
/* 1619 */       for (int i = 0; i < structureElementIds.length; i++)
/*      */       {
/* 1621 */         sql.append(new StringBuilder().append("\n and dim").append(i).append(" = ? ").toString());
/*      */       }
/* 1623 */       stmt = getConnection().prepareStatement(sql.toString());
/*      */ 
/* 1626 */       int index = 1;
/* 1627 */       stmt.setString(index++, dataType);
/* 1628 */       stmt.setString(index++, dataType);
/* 1629 */       for (int i = 0; i < nDims; i++)
/*      */       {
/* 1631 */         stmt.setInt(index++, structureElementIds[i]);
/*      */       }
/* 1633 */       resultSet = stmt.executeQuery();
/* 1634 */       if (resultSet.next())
/*      */       {
/* 1636 */         results = new ArrayList();
/* 1637 */         result = resultSet.getBigDecimal(1);
/* 1638 */         if (result == null)
/* 1639 */           result = resultSet.getString(1);
/* 1640 */         if (result == null)
/* 1641 */           result = resultSet.getTime(1);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1646 */       System.err.println(sqle);
/* 1647 */       sqle.printStackTrace();
/* 1648 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCellValue").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 1652 */       closeResultSet(resultSet);
/* 1653 */       closeStatement(stmt);
/* 1654 */       closeConnection();
/*      */     }
/*      */ 
/* 1657 */     if (timer != null) {
/* 1658 */       timer.logDebug("getCellValue", new StringBuilder().append(" result=").append(results).toString());
/*      */     }
/* 1660 */     return result;
/*      */   }
/*      */ 	

    /**
     * Method return the value of cells, based on received keys.
     * 
     * @param financeCubeId
     * @param numDims
     * @param structureVisIds
     * @param cellKeys
     * @return
     */
    public EntityList getCellValues(int financeCubeId, int numDims, String[] structureVisIds, List<String[]> cellKeys) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Timer timer = new Timer(this._log);
        EntityListImpl returnList = null;
        
        String[] displayName = getDisplayNameFromFinanceCubeID(financeCubeId);
        if(displayName[1] != null) {
            for(String[] cellKey : cellKeys) {
                cellKey[1] = cellKey[1].equals(displayName[1]) ? displayName[0] : cellKey[1];
            }
        }
        
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement pstat = getConnection().prepareStatement("select empty_clob() from dual");
            resultSet = pstat.executeQuery();
            resultSet.next();
            DatumWithConnection datum = (DatumWithConnection) resultSet.getClob(1);
            OracleConnection oconn = datum.getOracleConnection();

            StructDescriptor hierVisIdRowDesc = new StructDescriptor("TYPE_HIERVISIDROW", oconn);
            ArrayDescriptor hierVisIdArrayDesc = new ArrayDescriptor("TYPE_HIERVISIDTABLE", oconn);
            StructDescriptor cellkeyRowDesc = new StructDescriptor("TYPE_CELLLOOKUPVISIDROW", oconn);
            ArrayDescriptor cellKeyArrayDesc = new ArrayDescriptor("TYPE_CELLLOOKUPVISIDTABLE", oconn);

            closeResultSet(resultSet);
            closeStatement(pstat);

            StringBuilder traceHierVisIds = new StringBuilder();
            STRUCT[] hierVisIdRowArray = new STRUCT[structureVisIds.length];
            for (int i = 0; i < structureVisIds.length; i++) {
                Object[] rowObj = { String.valueOf(i), structureVisIds[i] };
                hierVisIdRowArray[i] = new STRUCT(hierVisIdRowDesc, oconn, rowObj);
                traceHierVisIds.append('[');
                traceHierVisIds.append(String.valueOf(i));
                traceHierVisIds.append('-');
                traceHierVisIds.append(structureVisIds[i]);
                traceHierVisIds.append(']');
            }
            ARRAY hierVisIdTable = new ARRAY(hierVisIdArrayDesc, oconn, hierVisIdRowArray);
//            this.mLog.info("getCellValues", new StringBuilder().append("hierVisIds=").append(traceHierVisIds.toString()).toString());

            STRUCT[] structArray = new STRUCT[cellKeys.size()];
            for (int i = 0; i < cellKeys.size(); i++) {
                Object[] rowObj = new Object[13];
                rowObj[0] = String.valueOf(i);
                Object[] cellKey = (Object[]) cellKeys.get(i);
                for (int j = 0; j < cellKey.length - 1; j++)
                    rowObj[(j + 1)] = cellKey[j];
                rowObj[12] = cellKey[(cellKey.length - 1)];
                structArray[i] = new STRUCT(cellkeyRowDesc, oconn, rowObj);
            }
            ARRAY cellLookupVisIdTable = new ARRAY(cellKeyArrayDesc, oconn, structArray);

            stmt = getConnection().prepareCall("begin cube_utils.getCellValues(?,?,?,?,?); end;");
            stmt.setInt(1, financeCubeId);
            stmt.setInt(2, numDims);
            stmt.setObject(3, hierVisIdTable);
            stmt.setObject(4, cellLookupVisIdTable);
            stmt.registerOutParameter(5, -10);
            stmt.execute();

            resultSet = (ResultSet) stmt.getObject(5);
            resultSet.setFetchSize(200);

            returnList = new EntityListImpl(new String[] { "P_INDEX", "VAL" }, new Object[0][2]);
            Integer prevPindex = Integer.valueOf(-1);
            List cellNoteRows = null;
            while (resultSet.next()) {
                Integer pIndex = Integer.valueOf(resultSet.getInt("P_INDEX"));
                String val = resultSet.getString("VAL");
                Integer valType = Integer.valueOf(resultSet.getInt("VAL_TYPE"));

                if (pIndex != prevPindex) {
                    if (cellNoteRows != null) {
                        List l = new ArrayList();
                        l.add(prevPindex);
                        l.add(cellNoteRows);
                        returnList.add(l);
                    }

                    if (valType.intValue() == -1)
                        cellNoteRows = new ArrayList();
                    else {
                        cellNoteRows = null;
                    }
                    prevPindex = pIndex;
                }

                if (valType.intValue() == -1) {
                    CellNoteRow cnr = new CellNoteRowImpl(resultSet.getString("NOTE_USER"), resultSet.getTimestamp("NOTE_CREATED"), resultSet.getString("NOTE_TEXT"), Integer.valueOf(resultSet.getInt("NOTE_ATTACHMENT_ID")));

                    cellNoteRows.add(cnr);
                } else {
                    Object retVal = null;
                    switch (valType.intValue()) {
                        case 0:
                            retVal = val;
                            break;
                        case 1:
                            retVal = val == null ? null : new BigDecimal(val.replace(",", "."));
                            break;
                        case 5:
                            retVal = val;
                            break;
                        case 3:
                            try {
                                retVal = new Date(dateFormat.parse(val).getTime());
                            } catch (ParseException e) {
                            }
                        case 2:
                            try {
                                retVal = new Time(timeFormat.parse(val).getTime());
                            } catch (ParseException e) {
                            }
                        case 4:
                            try {
                                retVal = new Timestamp(dateTimeFormat.parse(val).getTime());
                            } catch (ParseException e) {
                            }
                    }
                    List l = new ArrayList();
                    l.add(pIndex);
                    l.add(retVal);
                    returnList.add(l);
                }
            }
            if (cellNoteRows != null) {
                List l = new ArrayList();
                l.add(prevPindex);
                l.add(cellNoteRows);
                returnList.add(l);
            }

        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCellValues").toString(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        try {
            for (int i = 0; i < cellKeys.size(); i++) {
                Object val = null;
                for (int j = 0; j < returnList.getNumRows(); j++) {
                    if (((Integer) returnList.getValueAt(j, "P_INDEX")).intValue() != i)
                        continue;
                    val = returnList.getValueAt(j, "VAL");
                    break;
                }

                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(i));
                sb.append(",");
                for (int j = 0; j < ((String[]) cellKeys.get(i)).length; j++) {
                    sb.append(((String[]) cellKeys.get(i))[j]);
                    sb.append(",");
                }
//                if (val == null) {
//                    this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=no value").toString());
//                } else if ((val instanceof List))
//                    this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=").append(((List) val).size()).append(" notes").toString());
//                else {
//                    this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=").append(val.toString()).toString());
//                }
            }
//            timer.logInfo("getCellValues", new StringBuilder().append("required=").append(cellKeys.size()).append(returnList != null ? new StringBuilder().append(" found=").append(returnList.getNumRows()).toString() : " no rows found").toString());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return returnList;
    }
    
    private String[] getDisplayNameFromCompany(String cmpy) {
       String sql = "SELECT se.vis_id, se.display_name "+
                    "FROM STRUCTURE_ELEMENT se "+
                    "JOIN HIERARCHY h "+
                    "ON (se.STRUCTURE_ID=h.HIERARCHY_ID) "+
                    "JOIN MODEL_DIMENSION_REL dmr "+
                    "ON (h.DIMENSION_ID = dmr.DIMENSION_ID) "+
                    "JOIN MODEL m "+
                    "ON (dmr.MODEL_ID = m.MODEL_ID and DIMENSION_TYPE=2) "+
                    "WHERE m.VIS_ID like '" + cmpy + "/1%' and se.PARENT_ID = 0 ";
        String[] result = new String[2];
        ResultSet rs = null;
        String visId = null;
        try {
            PreparedStatement pstat = getConnection().prepareStatement(sql);
            rs = pstat.executeQuery();
            if(rs.next()) {
                result[0] = rs.getString(1); //VIS ID
                result[1] = rs.getString(2); // DISPLAY NAME
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }    
            } catch (SQLException ex) {
                
            }
        }
        return result;
    }
    
    public String[] getDisplayNameFromFinanceCubeID(Integer id) {
        String sql = "SELECT se.vis_id, se.display_name "+
                    "FROM STRUCTURE_ELEMENT se "+
                    "JOIN HIERARCHY h "+
                    "ON (se.STRUCTURE_ID=h.HIERARCHY_ID) "+
                    "JOIN MODEL_DIMENSION_REL dmr "+
                    "ON (h.DIMENSION_ID = dmr.DIMENSION_ID) "+
                    "JOIN MODEL m "+
                    "ON (dmr.MODEL_ID = m.MODEL_ID and DIMENSION_TYPE=2) "+
                    "JOIN FINANCE_CUBE fc "+
                    "ON(m.MODEL_ID = fc.MODEL_ID) "+
                    "WHERE fc.FINANCE_CUBE_ID = " + id + " and se.PARENT_ID = 0 ";
        String[] result = new String[2];
        ResultSet rs = null;
        String visId = null;
        try {
            PreparedStatement pstat = getConnection().prepareStatement(sql);
            rs = pstat.executeQuery();
            if(rs.next()) {
                result[0] = rs.getString(1); //VIS ID
                result[1] = rs.getString(2); // DISPLAY NAME
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }    
            } catch (SQLException ex) {
                
            }
        }
        return result;
    }

    public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys, String company) {       
        // long startTime = System.nanoTime();
        // System.out.println("== Ilosc komorek: " + cellKeys.size());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Timer timer = new Timer(this._log);
        EntityListImpl returnList = null;
        
        // CHANGE DISPLAY NAMES ON VIS_ID
        String[] displayName = getDisplayNameFromFinanceCubeID(fcId);
        if(displayName[1] != null) {
            for(String[] cellKey : cellKeys) {
                cellKey[1] = cellKey[1].equals(displayName[1]) ? displayName[0] : cellKey[1];
            }
        }

        CallableStatement stmt = null;
        ResultSet resultSet = null;
        try {
//            // Get your database connection (session)
//            Connection conn = getConnection();
//            // Start Oracle Debugger
//            startOracleDebugger(conn);
            
            PreparedStatement pstat = getConnection().prepareStatement("select empty_clob() from dual");
            resultSet = pstat.executeQuery();
            resultSet.next();
            DatumWithConnection datum = (DatumWithConnection) resultSet.getClob(1);
            OracleConnection oconn = datum.getOracleConnection();
        	
//        	Connection oconn = this.mDataSource.getConnection();

            StructDescriptor hierVisIdRowDesc = new StructDescriptor("TYPE_HIERVISIDROW", oconn);
            ArrayDescriptor hierVisIdArrayDesc = new ArrayDescriptor("TYPE_HIERVISIDTABLE", oconn);
            StructDescriptor cellkeyRowDesc = new StructDescriptor("TYPE_CELLLOOKUPVISIDROW", oconn);
            ArrayDescriptor cellKeyArrayDesc = new ArrayDescriptor("TYPE_CELLLOOKUPVISIDTABLE", oconn);

//            closeResultSet(resultSet);
//            closeStatement(pstat);

            StringBuilder traceHierVisIds = new StringBuilder();
            STRUCT[] hierVisIdRowArray = new STRUCT[structureVisIds.length];
            for (int i = 0; i < structureVisIds.length; i++) {
                Object[] rowObj = { String.valueOf(i), structureVisIds[i] };
                hierVisIdRowArray[i] = new STRUCT(hierVisIdRowDesc, oconn, rowObj);
                traceHierVisIds.append('[');
                traceHierVisIds.append(String.valueOf(i));
                traceHierVisIds.append('-');
                traceHierVisIds.append(structureVisIds[i]);
                traceHierVisIds.append(']');
            }
            ARRAY hierVisIdTable = new ARRAY(hierVisIdArrayDesc, oconn, hierVisIdRowArray);
//            this.mLog.info("getCellValues", new StringBuilder().append("hierVisIds=").append(traceHierVisIds.toString()).toString());

            STRUCT[] structArray = new STRUCT[cellKeys.size()];
            for (int i = 0; i < cellKeys.size(); i++) {
                Object[] rowObj = new Object[13];
                rowObj[0] = String.valueOf(i);
                Object[] cellKey = (Object[]) cellKeys.get(i);
                for (int j = 0; j < cellKey.length - 1; j++)
                    rowObj[(j + 1)] = cellKey[j];
                rowObj[12] = cellKey[(cellKey.length - 1)];
                structArray[i] = new STRUCT(cellkeyRowDesc, oconn, rowObj);
            }
            ARRAY cellLookupVisIdTable = new ARRAY(cellKeyArrayDesc, oconn, structArray);

            // long startTime2 = System.nanoTime();
            stmt = getConnection().prepareCall("begin cube_utils.getCellValues(?,?,?,?,?,?); end;");
            stmt.setInt(1, fcId);
            stmt.setInt(2, numDims);
            stmt.setObject(3, hierVisIdTable);
            stmt.setObject(4, cellLookupVisIdTable);
            stmt.setObject(5, company);
            stmt.registerOutParameter(6, -10);
            stmt.execute();
            
//            // Stop Oracle Debugger
//            stopOracleDebugger(conn);
    
            // long endTime2 = System.nanoTime();
            // long duration2 = endTime2 - startTime2;
            // double seconds2 = (double)duration2 / 1000000000.0;
            // System.out.println("== Zapytanie wykonano w " + seconds2 + " sekund");

            resultSet = (ResultSet) stmt.getObject(6);
            resultSet.setFetchSize(200);

            returnList = new EntityListImpl(new String[] { "P_INDEX", "VAL" }, new Object[0][2]);
            Integer prevPindex = Integer.valueOf(-1);
            List cellNoteRows = null;
            while (resultSet.next()) {
                Integer pIndex = Integer.valueOf(resultSet.getInt("P_INDEX"));
                String val = resultSet.getString("VAL");
                Integer valType = Integer.valueOf(resultSet.getInt("VAL_TYPE"));

                if (pIndex != prevPindex) {
                    if (cellNoteRows != null) {
                        List l = new ArrayList();
                        l.add(prevPindex);
                        l.add(cellNoteRows);
                        returnList.add(l);
                    }

                    if (valType.intValue() == -1)
                        cellNoteRows = new ArrayList();
                    else {
                        cellNoteRows = null;
                    }
                    prevPindex = pIndex;
                }

                if (valType.intValue() == -1) {
                    CellNoteRow cnr = new CellNoteRowImpl(resultSet.getString("NOTE_USER"), resultSet.getTimestamp("NOTE_CREATED"), resultSet.getString("NOTE_TEXT"), Integer.valueOf(resultSet.getInt("NOTE_ATTACHMENT_ID")));

                    cellNoteRows.add(cnr);
                } else {
                    Object retVal = null;
                    switch (valType.intValue()) {
                        case 0:
                            retVal = val;
                            break;
                        case 1:
                            retVal = val == null ? null : new BigDecimal(val.replace(",", "."));
                            break;
                        case 5:
                            retVal = val;
                            break;
                        case 3:
                            try {
                                retVal = new Date(dateFormat.parse(val).getTime());
                            } catch (ParseException e) {
                            }
                        case 2:
                            try {
                                retVal = new Time(timeFormat.parse(val).getTime());
                            } catch (ParseException e) {
                            }
                        case 4:
                            try {
                                retVal = new Timestamp(dateTimeFormat.parse(val).getTime());
                            } catch (ParseException e) {
                            }
                    }
                    List l = new ArrayList();
                    l.add(pIndex);
                    l.add(retVal);
                    returnList.add(l);
                }
            }
            if (cellNoteRows != null) {
                List l = new ArrayList();
                l.add(prevPindex);
                l.add(cellNoteRows);
                returnList.add(l);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCellValues").toString(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        try {
            for (int i = 0; i < cellKeys.size(); i++) {
                Object val = null;
                for (int j = 0; j < returnList.getNumRows(); j++) {
                    if (((Integer) returnList.getValueAt(j, "P_INDEX")).intValue() != i)
                        continue;
                    val = returnList.getValueAt(j, "VAL");
                    break;
                }

                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(i));
                sb.append(",");
                for (int j = 0; j < ((String[]) cellKeys.get(i)).length; j++) {
                    sb.append(((String[]) cellKeys.get(i))[j]);
                    sb.append(",");
                }
//                if (val == null) {
//                    this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=no value").toString());
//                } else if ((val instanceof List))
//                    this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=").append(((List) val).size()).append(" notes").toString());
//                else {
//                    this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=").append(val.toString()).toString());
//                }
            }

//            timer.logInfo("getCellValues", new StringBuilder().append("required=").append(cellKeys.size()).append(returnList != null ? new StringBuilder().append(" found=").append(returnList.getNumRows()).toString() : " no rows found").toString());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        // long endTime = System.nanoTime();
        // long duration = endTime - startTime;
        // double seconds = (double)duration / 1000000000.0;
        // System.out.println("== Cala metode wykonano w " + seconds + " sekund");
        return returnList;
    }


/*      */   public EntityList getCellValues(int fcId, int numDims, int[] hierIds, List<String[]> cellKeys)
/*      */   {
/* 1939 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 1940 */     SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
/* 1941 */     SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*      */ 
/* 1943 */     Timer timer = new Timer(this._log);
/* 1944 */     EntityListImpl returnList = null;
/*      */ 
/* 1946 */     CallableStatement stmt = null;
/* 1947 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1951 */       PreparedStatement pstat = getConnection().prepareStatement("select empty_clob() from dual");
/* 1952 */       resultSet = pstat.executeQuery();
/* 1953 */       resultSet.next();
/* 1954 */       DatumWithConnection datum = (DatumWithConnection)resultSet.getClob(1);
/* 1955 */       OracleConnection oconn = datum.getOracleConnection();
/*      */ 
/* 1958 */       StructDescriptor cellkeyRowDesc = new StructDescriptor("TYPE_CELLLOOKUPSEIDROW", oconn);
/* 1959 */       ArrayDescriptor cellKeyArrayDesc = new ArrayDescriptor("TYPE_CELLLOOKUPSEIDTABLE", oconn);
/*      */ 
/* 1961 */       closeResultSet(resultSet);
/* 1962 */       closeStatement(pstat);
/*      */ 
/* 1965 */       StringBuilder hierIdString = new StringBuilder();
/* 1966 */       for (int i = 0; i < hierIds.length; i++)
/*      */       {
/* 1968 */         if (i > 0)
/* 1969 */           hierIdString.append(",");
/* 1970 */         hierIdString.append(String.valueOf(hierIds[i]));
/*      */       }
/*      */ 
/* 1974 */       STRUCT[] structArray = new STRUCT[cellKeys.size()];
/* 1975 */       for (int i = 0; i < cellKeys.size(); i++)
/*      */       {
/* 1977 */         Object[] rowObj = new Object[13];
/* 1978 */         rowObj[0] = new Integer(i);
/* 1979 */         Object[] cellKey = (Object[])cellKeys.get(i);
/* 1980 */         for (int j = 0; j < cellKey.length - 1; j++)
/* 1981 */           rowObj[(j + 1)] = cellKey[j];
/* 1982 */         rowObj[12] = cellKey[(cellKey.length - 1)];
/* 1983 */         structArray[i] = new STRUCT(cellkeyRowDesc, oconn, rowObj);
/*      */       }
/* 1985 */       ARRAY cellLookupSeIdTable = new ARRAY(cellKeyArrayDesc, oconn, structArray);
/*      */ 
/* 1988 */       stmt = getConnection().prepareCall("begin cube_utils.getCellValues(?,?,?,?,?); end;");
/* 1989 */       stmt.setInt(1, fcId);
/* 1990 */       stmt.setInt(2, numDims);
/* 1991 */       stmt.setString(3, hierIdString.toString());
/* 1992 */       stmt.setObject(4, cellLookupSeIdTable);
/* 1993 */       stmt.registerOutParameter(5, -10);
/* 1994 */       stmt.execute();
/*      */ 
/* 1996 */       resultSet = (ResultSet)stmt.getObject(5);
/* 1997 */       resultSet.setFetchSize(200);
/*      */ 
/* 2000 */       returnList = new EntityListImpl(new String[] { "P_INDEX", "VAL" }, new Object[0][2]);
/* 2001 */       Integer prevPindex = Integer.valueOf(-1);
/* 2002 */       List cellNoteRows = null;
/* 2003 */       while (resultSet.next())
/*      */       {
/* 2005 */         Integer pIndex = Integer.valueOf(resultSet.getInt("P_INDEX"));
/* 2006 */         String val = resultSet.getString("VAL");
/* 2007 */         Integer valType = Integer.valueOf(resultSet.getInt("VAL_TYPE"));
/*      */ 
/* 2010 */         if (pIndex != prevPindex)
/*      */         {
/* 2012 */           if (cellNoteRows != null)
/*      */           {
/* 2014 */             List l = new ArrayList();
/* 2015 */             l.add(prevPindex);
/* 2016 */             l.add(cellNoteRows);
/* 2017 */             returnList.add(l);
/*      */           }
/*      */ 
/* 2020 */           if (valType.intValue() == -1)
/* 2021 */             cellNoteRows = new ArrayList();
/*      */           else {
/* 2023 */             cellNoteRows = null;
/*      */           }
/* 2025 */           prevPindex = pIndex;
/*      */         }
/*      */ 
/* 2028 */         if (valType.intValue() == -1)
/*      */         {
/* 2030 */           CellNoteRow cnr = new CellNoteRowImpl(resultSet.getString("NOTE_USER"), resultSet.getTimestamp("NOTE_CREATED"), resultSet.getString("NOTE_TEXT"), Integer.valueOf(resultSet.getInt("NOTE_ATTACHMENT_ID")));
/*      */ 
/* 2036 */           cellNoteRows.add(cnr);
/*      */         }
/*      */         else
/*      */         {
/* 2040 */           Object retVal = null;
/* 2041 */           switch (valType.intValue())
/*      */           {
/*      */           case 0:
/* 2044 */             retVal = val;
/* 2045 */             break;
/*      */           case 1:
/* 2047 */             retVal = new BigDecimal(val);
/* 2048 */             break;
/*      */           case 5:
/* 2050 */             retVal = val;
/* 2051 */             break;
/*      */           case 3:
/*      */             try
/*      */             {
/* 2055 */               retVal = new Date(dateFormat.parse(val).getTime());
/*      */             }
/*      */             catch (ParseException e)
/*      */             {
/*      */             }
/*      */           case 2:
/*      */             try {
/* 2062 */               retVal = new Time(timeFormat.parse(val).getTime());
/*      */             }
/*      */             catch (ParseException e) {
/*      */             }
/*      */           case 4:
/*      */             try {
/* 2068 */               retVal = new Timestamp(dateTimeFormat.parse(val).getTime());
/*      */             }
/*      */             catch (ParseException e) {
/*      */             }
/*      */           }
/* 2073 */           List l = new ArrayList();
/* 2074 */           l.add(pIndex);
/* 2075 */           l.add(retVal);
/* 2076 */           returnList.add(l);
/*      */         }
/*      */       }
/* 2079 */       if (cellNoteRows != null)
/*      */       {
/* 2081 */         List l = new ArrayList();
/* 2082 */         l.add(prevPindex);
/* 2083 */         l.add(cellNoteRows);
/* 2084 */         returnList.add(l);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2089 */       System.err.println(sqle);
/* 2090 */       sqle.printStackTrace();
/* 2091 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCellValues").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2095 */       closeResultSet(resultSet);
/* 2096 */       closeStatement(stmt);
/* 2097 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2103 */       for (int i = 0; i < cellKeys.size(); i++)
/*      */       {
/* 2105 */         Object val = null;
/* 2106 */         for (int j = 0; j < returnList.getNumRows(); j++)
/*      */         {
/* 2108 */           if (((Integer)returnList.getValueAt(j, "P_INDEX")).intValue() != i)
/*      */             continue;
/* 2110 */           val = returnList.getValueAt(j, "VAL");
/* 2111 */           break;
/*      */         }
/*      */ 
/* 2114 */         StringBuilder sb = new StringBuilder();
/* 2115 */         sb.append(String.valueOf(i));
/* 2116 */         sb.append(",");
/* 2117 */         for (int j = 0; j < ((String[])cellKeys.get(i)).length; j++)
/*      */         {
/* 2119 */           sb.append(((String[])cellKeys.get(i))[j]);
/* 2120 */           sb.append(",");
/*      */         }
///* 2122 */         if (val == null)
///*      */         {
///* 2124 */           this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=no value").toString());
///*      */         }
///* 2128 */         else if ((val instanceof List))
///* 2129 */           this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=").append(((List)val).size()).append(" notes").toString());
///*      */         else {
///* 2131 */           this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=").append(val.toString()).toString());
///*      */         }
/*      */       }
/*      */ 
///* 2135 */       if (timer != null)
///* 2136 */         timer.logDebug("getCellValues", new StringBuilder().append("required=").append(cellKeys.size()).append(returnList != null ? new StringBuilder().append(" found=").append(returnList.getNumRows()).toString() : " no rows found").toString());
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/* 2140 */       t.printStackTrace();
/*      */     }
/*      */ 
/* 2143 */     return returnList;
/*      */   }

			 public EntityList getCellValues(int fcId, int numDims, int[] hierIds, List<String[]> cellKeys, String company)
/*      */   {
/* 1939 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 1940 */     SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
/* 1941 */     SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*      */ 
/* 1943 */     Timer timer = new Timer(this._log);
/* 1944 */     EntityListImpl returnList = null;
/*      */ 
/* 1946 */     CallableStatement stmt = null;
/* 1947 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 1951 */       PreparedStatement pstat = getConnection().prepareStatement("select empty_clob() from dual");
/* 1952 */       resultSet = pstat.executeQuery();
/* 1953 */       resultSet.next();
/* 1954 */       DatumWithConnection datum = (DatumWithConnection)resultSet.getClob(1);
/* 1955 */       OracleConnection oconn = datum.getOracleConnection();
/*      */ 
/* 1958 */       StructDescriptor cellkeyRowDesc = new StructDescriptor("TYPE_CELLLOOKUPSEIDROW", oconn);
/* 1959 */       ArrayDescriptor cellKeyArrayDesc = new ArrayDescriptor("TYPE_CELLLOOKUPSEIDTABLE", oconn);
/*      */ 
/* 1961 */       closeResultSet(resultSet);
/* 1962 */       closeStatement(pstat);
/*      */ 
/* 1965 */       StringBuilder hierIdString = new StringBuilder();
/* 1966 */       for (int i = 0; i < hierIds.length; i++)
/*      */       {
/* 1968 */         if (i > 0)
/* 1969 */           hierIdString.append(",");
/* 1970 */         hierIdString.append(String.valueOf(hierIds[i]));
/*      */       }
/*      */ 
/* 1974 */       STRUCT[] structArray = new STRUCT[cellKeys.size()];
/* 1975 */       for (int i = 0; i < cellKeys.size(); i++)
/*      */       {
/* 1977 */         Object[] rowObj = new Object[13];
/* 1978 */         rowObj[0] = new Integer(i);
/* 1979 */         Object[] cellKey = (Object[])cellKeys.get(i);
/* 1980 */         for (int j = 0; j < cellKey.length - 1; j++)
/* 1981 */           rowObj[(j + 1)] = cellKey[j];
/* 1982 */         rowObj[12] = cellKey[(cellKey.length - 1)];
/* 1983 */         structArray[i] = new STRUCT(cellkeyRowDesc, oconn, rowObj);
/*      */       }
/* 1985 */       ARRAY cellLookupSeIdTable = new ARRAY(cellKeyArrayDesc, oconn, structArray);
/*      */ 
/* 1988 */       stmt = getConnection().prepareCall("begin cube_utils.getCellValues(?,?,?,?,?,?); end;");
/* 1989 */       stmt.setInt(1, fcId);
/* 1990 */       stmt.setInt(2, numDims);
/* 1991 */       stmt.setString(3, hierIdString.toString());
/* 1992 */       stmt.setObject(4, cellLookupSeIdTable);
				 stmt.setObject(5, company);
/* 1993 */       stmt.registerOutParameter(6, -10);
/* 1994 */       stmt.execute();
/*      */ 
/* 1996 */       resultSet = (ResultSet)stmt.getObject(5);
/* 1997 */       resultSet.setFetchSize(200);
/*      */ 
/* 2000 */       returnList = new EntityListImpl(new String[] { "P_INDEX", "VAL" }, new Object[0][2]);
/* 2001 */       Integer prevPindex = Integer.valueOf(-1);
/* 2002 */       List cellNoteRows = null;
/* 2003 */       while (resultSet.next())
/*      */       {
/* 2005 */         Integer pIndex = Integer.valueOf(resultSet.getInt("P_INDEX"));
/* 2006 */         String val = resultSet.getString("VAL");
/* 2007 */         Integer valType = Integer.valueOf(resultSet.getInt("VAL_TYPE"));
/*      */ 
/* 2010 */         if (pIndex != prevPindex)
/*      */         {
/* 2012 */           if (cellNoteRows != null)
/*      */           {
/* 2014 */             List l = new ArrayList();
/* 2015 */             l.add(prevPindex);
/* 2016 */             l.add(cellNoteRows);
/* 2017 */             returnList.add(l);
/*      */           }
/*      */ 
/* 2020 */           if (valType.intValue() == -1)
/* 2021 */             cellNoteRows = new ArrayList();
/*      */           else {
/* 2023 */             cellNoteRows = null;
/*      */           }
/* 2025 */           prevPindex = pIndex;
/*      */         }
/*      */ 
/* 2028 */         if (valType.intValue() == -1)
/*      */         {
/* 2030 */           CellNoteRow cnr = new CellNoteRowImpl(resultSet.getString("NOTE_USER"), resultSet.getTimestamp("NOTE_CREATED"), resultSet.getString("NOTE_TEXT"), Integer.valueOf(resultSet.getInt("NOTE_ATTACHMENT_ID")));
/*      */ 
/* 2036 */           cellNoteRows.add(cnr);
/*      */         }
/*      */         else
/*      */         {
/* 2040 */           Object retVal = null;
/* 2041 */           switch (valType.intValue())
/*      */           {
/*      */           case 0:
/* 2044 */             retVal = val;
/* 2045 */             break;
/*      */           case 1:
/* 2047 */             retVal = new BigDecimal(val);
/* 2048 */             break;
/*      */           case 5:
/* 2050 */             retVal = val;
/* 2051 */             break;
/*      */           case 3:
/*      */             try
/*      */             {
/* 2055 */               retVal = new Date(dateFormat.parse(val).getTime());
/*      */             }
/*      */             catch (ParseException e)
/*      */             {
/*      */             }
/*      */           case 2:
/*      */             try {
/* 2062 */               retVal = new Time(timeFormat.parse(val).getTime());
/*      */             }
/*      */             catch (ParseException e) {
/*      */             }
/*      */           case 4:
/*      */             try {
/* 2068 */               retVal = new Timestamp(dateTimeFormat.parse(val).getTime());
/*      */             }
/*      */             catch (ParseException e) {
/*      */             }
/*      */           }
/* 2073 */           List l = new ArrayList();
/* 2074 */           l.add(pIndex);
/* 2075 */           l.add(retVal);
/* 2076 */           returnList.add(l);
/*      */         }
/*      */       }
/* 2079 */       if (cellNoteRows != null)
/*      */       {
/* 2081 */         List l = new ArrayList();
/* 2082 */         l.add(prevPindex);
/* 2083 */         l.add(cellNoteRows);
/* 2084 */         returnList.add(l);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2089 */       System.err.println(sqle);
/* 2090 */       sqle.printStackTrace();
/* 2091 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCellValues").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2095 */       closeResultSet(resultSet);
/* 2096 */       closeStatement(stmt);
/* 2097 */       closeConnection();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2103 */       for (int i = 0; i < cellKeys.size(); i++)
/*      */       {
/* 2105 */         Object val = null;
/* 2106 */         for (int j = 0; j < returnList.getNumRows(); j++)
/*      */         {
/* 2108 */           if (((Integer)returnList.getValueAt(j, "P_INDEX")).intValue() != i)
/*      */             continue;
/* 2110 */           val = returnList.getValueAt(j, "VAL");
/* 2111 */           break;
/*      */         }
/*      */ 
/* 2114 */         StringBuilder sb = new StringBuilder();
/* 2115 */         sb.append(String.valueOf(i));
/* 2116 */         sb.append(",");
/* 2117 */         for (int j = 0; j < ((String[])cellKeys.get(i)).length; j++)
/*      */         {
/* 2119 */           sb.append(((String[])cellKeys.get(i))[j]);
/* 2120 */           sb.append(",");
/*      */         }
///* 2122 */         if (val == null)
///*      */         {
///* 2124 */           this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=no value").toString());
///*      */         }
///* 2128 */         else if ((val instanceof List))
///* 2129 */           this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=").append(((List)val).size()).append(" notes").toString());
///*      */         else {
///* 2131 */           this.mLog.debug("getCellValues", new StringBuilder().append("[").append(sb.toString()).append("]=").append(val.toString()).toString());
///*      */         }
/*      */       }
/*      */ 
///* 2135 */       if (timer != null)
///* 2136 */         timer.logDebug("getCellValues", new StringBuilder().append("required=").append(cellKeys.size()).append(returnList != null ? new StringBuilder().append(" found=").append(returnList.getNumRows()).toString() : " no rows found").toString());
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/* 2140 */       t.printStackTrace();
/*      */     }
/*      */ 
/* 2143 */     return returnList;
/*      */   }

	public EntityList getOACellValues(int company, List<String[]> cellKeys) {
//long startTime = System.nanoTime();
//System.out.println("== Ilosc komorek: " + cellKeys.size());
		Timer timer = new Timer(this._log);
		EntityListImpl returnList = null;
//		TreeMap treeMap = new TreeMap();
		
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		try {
			PreparedStatement pstat = getConnection().prepareStatement("select empty_clob() from dual");
			resultSet = pstat.executeQuery();
			resultSet.next();
			DatumWithConnection datum = (DatumWithConnection) resultSet.getClob(1);
			OracleConnection oconn = datum.getOracleConnection();

			StructDescriptor cellkeyRowDesc = new StructDescriptor("TYPE_OACELLLOOKUPVISIDROW", oconn);
			ArrayDescriptor cellKeyArrayDesc = new ArrayDescriptor("TYPE_OACELLLOOKUPVISIDTABLE", oconn);
			
			closeResultSet(resultSet);
			closeStatement(pstat);
			
			// CHANGE DISPLAY NAMES ON VIS_ID
	        String[] displayName = getDisplayNameFromCompany(String.valueOf(company));
	        if(displayName[1] != null) {
	            for(String[] cellKey : cellKeys) {
	                cellKey[1] = cellKey[1].equals(displayName[1]) ? displayName[0] : cellKey[1];
	            }
	        }		
			
			STRUCT[] structArray = new STRUCT[cellKeys.size()];
			for (int i = 0; i < cellKeys.size(); i++) {
				Object[] rowObj = new Object[7];
				rowObj[0] = String.valueOf(i);
				Object[] cellKey = (Object[]) cellKeys.get(i);
				rowObj[1] = cellKey[0]; // column to get
				rowObj[2] = cellKey[1]; // dim0 - costcentre
				rowObj[3] = cellKey[2]; // dim1 - expensecode
				rowObj[4] = String.valueOf(cellKey[3]); // dim2 - year
				rowObj[5] = String.valueOf(cellKey[4]); // dim2 - period
				rowObj[6] = cellKey[5]; // dim3 - project
				structArray[i] = new STRUCT(cellkeyRowDesc, oconn, rowObj);
//				treeMap.put(cellKey[1].toString()+cellKey[2].toString()+cellKey[3].toString()+cellKey[4].toString(), rowObj[0]+"/"+cellKey[0].toString());
			}
			ARRAY cellLookupVisIdTable = new ARRAY(cellKeyArrayDesc, oconn, structArray);
			
//long startTime2 = System.nanoTime();
			stmt = getConnection().prepareCall("begin cube_utils.getOACellValues(?,?,?); end;");
			stmt.setInt(1, company);
			stmt.setObject(2, cellLookupVisIdTable);
			stmt.registerOutParameter(3, -10);
			stmt.execute();
//long endTime2 = System.nanoTime();
//long duration2 = endTime2 - startTime2;
//double seconds2 = (double)duration2 / 1000000000.0;
//System.out.println("== Zapytanie wykonano w " + seconds2 + " sekund");
			
			resultSet = (ResultSet) stmt.getObject(3);
			resultSet.setFetchSize(200);
			
			returnList = new EntityListImpl(new String[] { "P_INDEX", "VAL" }, new Object[0][2]);

			while (resultSet.next()) {
				// For procedure with Select UNION
				Integer pIndex = Integer.valueOf(resultSet.getInt("P_INDEX"));
				BigDecimal val = resultSet.getBigDecimal("VAL");
				List cellRow = new ArrayList();
				cellRow.add(pIndex);
				cellRow.add(val);
				returnList.add(cellRow);
			}
		} catch (SQLException sqle) {
			System.err.println(sqle);
			sqle.printStackTrace();
			throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getOACellValues").toString(), sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		} 
//long endTime = System.nanoTime();
//long duration = endTime - startTime;
//double seconds = (double)duration / 1000000000.0;
//System.out.println("== Cala metode wykonano w " + seconds + " sekund");
		return returnList;
	}

    public EntityList getCurrencyCellValues(List<String[]> cellKeys) {
        Timer timer = new Timer(this._log);
        EntityListImpl returnList = null;
        
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement pstat = getConnection().prepareStatement("select empty_clob() from dual");
            resultSet = pstat.executeQuery();
            resultSet.next();
            DatumWithConnection datum = (DatumWithConnection) resultSet.getClob(1);
            OracleConnection oconn = datum.getOracleConnection();

            StructDescriptor cellKeyRowDesc = new StructDescriptor("TYPE_CURRCELLLOOKUPVISIDROW", oconn);
            ArrayDescriptor cellKeyArrayDesc = new ArrayDescriptor("TYPE_CURRCELLLOOKUPVISIDTABLE", oconn);
            
            closeResultSet(resultSet);
            closeStatement(pstat);
            
            STRUCT[] structArray = new STRUCT[cellKeys.size()];
            for (int i = 0; i < cellKeys.size(); i++) {
                Object[] rowObj = new Object[5];
                rowObj[0] = String.valueOf(i);
                Object[] cellKey = (Object[]) cellKeys.get(i);
                rowObj[1] = cellKey[0]; // curr
                rowObj[2] = String.valueOf(cellKey[1]); // year
                rowObj[3] = String.valueOf(cellKey[2]); // period
                rowObj[4] = String.valueOf(cellKey[3]); // cmpy
                structArray[i] = new STRUCT(cellKeyRowDesc, oconn, rowObj);
            }
            ARRAY cellLookupVisIdTable = new ARRAY(cellKeyArrayDesc, oconn, structArray);
            
            stmt = getConnection().prepareCall("begin cube_utils.getCurrencyCellValues(?,?); end;");
            stmt.setObject(1, cellLookupVisIdTable);
            stmt.registerOutParameter(2, -10);
            stmt.execute();
           
            resultSet = (ResultSet) stmt.getObject(2);
            resultSet.setFetchSize(200);
            
            returnList = new EntityListImpl(new String[] { "P_INDEX", "FIELD_VALUE" }, new Object[0][2]);

            while (resultSet.next()) {
                // For procedure with Select UNION
                Integer pIndex = Integer.valueOf(resultSet.getInt("P_INDEX"));
                String val = resultSet.getString("FIELD_VALUE");
                List cellRow = new ArrayList();
                cellRow.add(pIndex);
                cellRow.add(val);
                returnList.add(cellRow);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCurrencyCellValues").toString(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        return returnList;
    }
    
    
    public EntityList getParameterCellValues(List<String[]> cellKeys) {
        Timer timer = new Timer(this._log);
        EntityListImpl returnList = null;
        
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement pstat = getConnection().prepareStatement("select empty_clob() from dual");
            resultSet = pstat.executeQuery();
            resultSet.next();
            DatumWithConnection datum = (DatumWithConnection) resultSet.getClob(1);
            OracleConnection oconn = datum.getOracleConnection();

            StructDescriptor cellKeyRowDesc = new StructDescriptor("TYPE_PARACELLLOOKUPVISIDROW", oconn);
            ArrayDescriptor cellKeyArrayDesc = new ArrayDescriptor("TYPE_PARACELLLOOKUPVISIDTABLE", oconn);
            
            closeResultSet(resultSet);
            closeStatement(pstat);
            
            Map<String, String[]> companyDisplayName = new HashMap<String, String[]>();
            
            STRUCT[] structArray = new STRUCT[cellKeys.size()];
            for (int i = 0; i < cellKeys.size(); i++) {
                Object[] rowObj = new Object[4];
                rowObj[0] = String.valueOf(i);
                Object[] cellKey = (Object[]) cellKeys.get(i);
                if (!companyDisplayName.containsKey(String.valueOf(cellKey[0]))){
                    String[] displayName = getDisplayNameFromCompany(String.valueOf(cellKey[0]));
                    companyDisplayName.put(String.valueOf(cellKey[0]), displayName);
                }
                String[] displayName = companyDisplayName.get(String.valueOf(cellKey[0]));
                if (displayName[1] != null) {
                    rowObj[2] = cellKey[1].equals(displayName[1]) ? displayName[0] : cellKey[1];
                }
                else{
                    rowObj[2] = String.valueOf(cellKey[1]); // costc
                }
                rowObj[1] = String.valueOf(cellKey[0]); // cpmy
                rowObj[3] = String.valueOf(cellKey[2]); // field
                structArray[i] = new STRUCT(cellKeyRowDesc, oconn, rowObj);
            }
            ARRAY cellLookupVisIdTable = new ARRAY(cellKeyArrayDesc, oconn, structArray);
            stmt = getConnection().prepareCall("begin cube_utils.getParaCellValues(?,?); end;");
            stmt.setObject(1, cellLookupVisIdTable);
            stmt.registerOutParameter(2, -10);
            stmt.execute();
            
            resultSet = (ResultSet) stmt.getObject(2);
            resultSet.setFetchSize(200);
            
            returnList = new EntityListImpl(new String[] { "P_INDEX", "FIELD_VALUE" }, new Object[0][2]);
            
            String status;
            String val;

            while (resultSet.next()) {
                Integer pIndex = Integer.valueOf(resultSet.getInt("P_INDEX"));
                status = resultSet.getString("STATUS");
                val = resultSet.getString("VALUE");
                if (status.equals("Y")) {
                    if (val.equals(Integer.toString(LookupParameterStatus.DELETED.getValue()))) {
                        val = "DELETED";
                    } else if (val.equals(Integer.toString(LookupParameterStatus.SUSPENDED.getValue()))) {
                        val = "SUSPENDED";
                    } else {
                        val = "ACTIVE";
                    }
                } else if(status.equals("C")) //currency
                {
                   DictionaryPropertiesDTO  properties = fromJSON(val);
                   val = properties.getCurrency(); 
                }
                
                List cellRow = new ArrayList();
                cellRow.add(pIndex);
                cellRow.add(val);
                returnList.add(cellRow);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getParaCellValues").toString(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        return returnList;
    }
    
    public EntityList getAuctionCellValues(List<String[]> cellKeys) {
        Timer timer = new Timer(this._log);
        EntityListImpl returnList = null;
        
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement pstat = getConnection().prepareStatement("select empty_clob() from dual");
            resultSet = pstat.executeQuery();
            resultSet.next();
            DatumWithConnection datum = (DatumWithConnection) resultSet.getClob(1);
            OracleConnection oconn = datum.getOracleConnection();

            StructDescriptor cellKeyRowDesc = new StructDescriptor("TYPE_AUCTCELLLOOKUPVISIDROW", oconn);
            ArrayDescriptor cellKeyArrayDesc = new ArrayDescriptor("TYPE_AUCTCELLLOOKUPVISIDTABLE", oconn);
            
            closeResultSet(resultSet);
            closeStatement(pstat);
            
            STRUCT[] structArray = new STRUCT[cellKeys.size()];
            for (int i = 0; i < cellKeys.size(); i++) {
                Object[] rowObj = new Object[4];
                rowObj[0] = String.valueOf(i);
                Object[] cellKey = (Object[]) cellKeys.get(i);
                rowObj[1] = String.valueOf(cellKey[0]); // table
                rowObj[2] = String.valueOf(cellKey[1]); // key
                rowObj[3] = String.valueOf(cellKey[2]); // column
                structArray[i] = new STRUCT(cellKeyRowDesc, oconn, rowObj);
            }
            ARRAY cellLookupVisIdTable = new ARRAY(cellKeyArrayDesc, oconn, structArray);
            stmt = getConnection().prepareCall("begin cube_utils.getAuctionCellValues(?,?); end;");
            stmt.setObject(1, cellLookupVisIdTable);
            stmt.registerOutParameter(2, -10);
            stmt.execute();
            
            resultSet = (ResultSet) stmt.getObject(2);
            resultSet.setFetchSize(200);
            
            returnList = new EntityListImpl(new String[] { "P_INDEX", "FIELD_VALUE" }, new Object[0][2]);

            while (resultSet.next()) {
                Integer pIndex = Integer.valueOf(resultSet.getInt("P_INDEX"));
                String val = resultSet.getString("FIELD_VALUE");
                List cellRow = new ArrayList();
                cellRow.add(pIndex);
                cellRow.add(val);
                returnList.add(cellRow);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
            sqle.printStackTrace();
            throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getAuctionCellValues").toString(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        return returnList;
    }
 

/*      */   public StructureElementRef getBudgetLocationRoot(int financeCubeId)
/*      */   {
/* 2148 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2149 */     PreparedStatement stmt = null;
/* 2150 */     ResultSet resultSet = null;
/* 2151 */     StructureElementRef results = null;
/*      */     try
/*      */     {
/* 2154 */       stmt = getConnection().prepareStatement(SQL_BUDGET_LOCATION_ROOT);
/* 2155 */       int col1 = 1;
/* 2156 */       stmt.setInt(col1++, financeCubeId);
/* 2157 */       resultSet = stmt.executeQuery();
/* 2158 */       if (resultSet.next())
/*      */       {
/* 2160 */         int index = 1;
/*      */ 
/* 2162 */         int structureId = resultSet.getInt(index++);
/* 2163 */         int structureElementId = resultSet.getInt(index++);
/* 2164 */         String label = resultSet.getString(index++);
/*      */ 
/* 2166 */         StructureElementPK key = new StructureElementPK(structureId, structureElementId);
/* 2167 */         results = new StructureElementRefImpl(key, label);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2172 */       System.err.println(sqle);
/* 2173 */       sqle.printStackTrace();
/* 2174 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getBudgetLocationRoot").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2178 */       closeResultSet(resultSet);
/* 2179 */       closeStatement(stmt);
/* 2180 */       closeConnection();
/*      */     }
/*      */ 
/* 2183 */     if (timer != null) {
/* 2184 */       timer.logDebug("getBudgetLocationRoot", new StringBuilder().append(" result=").append(results).toString());
/*      */     }
/* 2186 */     return results;
/*      */   }
/*      */ 
/*      */   public StructureElementRef getPeriodRoot(int financeCubeId)
/*      */   {
/* 2191 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2192 */     PreparedStatement stmt = null;
/* 2193 */     ResultSet resultSet = null;
/* 2194 */     StructureElementRef results = null;
/*      */     try
/*      */     {
/* 2197 */       stmt = getConnection().prepareStatement(SQL_PERIOD_ROOT);
/* 2198 */       int col1 = 1;
/* 2199 */       stmt.setInt(col1++, financeCubeId);
/* 2200 */       resultSet = stmt.executeQuery();
/* 2201 */       if (resultSet.next())
/*      */       {
/* 2203 */         int index = 1;
/*      */ 
/* 2205 */         int structureId = resultSet.getInt(index++);
/* 2206 */         int structureElementId = resultSet.getInt(index++);
/* 2207 */         String label = resultSet.getString(index++);
/*      */ 
/* 2209 */         StructureElementPK key = new StructureElementPK(structureId, structureElementId);
/* 2210 */         results = new StructureElementRefImpl(key, label);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2215 */       System.err.println(sqle);
/* 2216 */       sqle.printStackTrace();
/* 2217 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getPeriodRoot").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2221 */       closeResultSet(resultSet);
/* 2222 */       closeStatement(stmt);
/* 2223 */       closeConnection();
/*      */     }
/*      */ 
/* 2226 */     if (timer != null) {
/* 2227 */       timer.logDebug("getPeriodRoot", new StringBuilder().append(" result=").append(results).toString());
/*      */     }
/* 2229 */     return results;
/*      */   }
/*      */ 
/*      */   public AvailableStructureElementELO getAccountLeaves(int financeCubeId)
/*      */   {
/* 2234 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2235 */     PreparedStatement stmt = null;
/* 2236 */     ResultSet resultSet = null;
/* 2237 */     AvailableStructureElementELO results = new AvailableStructureElementELO();
/*      */     try
/*      */     {
/* 2240 */       stmt = getConnection().prepareStatement(SQL_ACCOUNT_LEAVES);
/* 2241 */       int col1 = 1;
/* 2242 */       stmt.setInt(col1++, financeCubeId);
/* 2243 */       resultSet = stmt.executeQuery();
/* 2244 */       while (resultSet.next())
/*      */       {
/* 2246 */         int index = 1;
/*      */ 
/* 2248 */         int level = resultSet.getInt(index++);
/* 2249 */         int id = resultSet.getInt(index++);
/* 2250 */         String vis = resultSet.getString(index++);
/* 2251 */         String label = resultSet.getString(index++);
/* 2252 */         String isCredit = resultSet.getString(index++);
/* 2253 */         String disabled = resultSet.getString(index++);
/* 2254 */         String notPlannable = resultSet.getString(index++);
/* 2255 */         String leaf = resultSet.getString(index++);
/* 2256 */         Integer position = Integer.valueOf(resultSet.getInt(index++));
/*      */ 
/* 2258 */         results.add(new Integer(level), null, new Integer(id), vis, label, isCredit, disabled, notPlannable, leaf, position);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2274 */       System.err.println(sqle);
/* 2275 */       sqle.printStackTrace();
/* 2276 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" AvailableAccountLeaves").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2280 */       closeResultSet(resultSet);
/* 2281 */       closeStatement(stmt);
/* 2282 */       closeConnection();
/*      */     }
/*      */ 
/* 2285 */     if (timer != null) {
/* 2286 */       timer.logDebug("getAvailableAccountLeaves", new StringBuilder().append(" items=").append(results.size()).toString());
/*      */     }
/* 2288 */     return results;
/*      */   }
/*      */ 
/*      */   public ValueMapping getOtherBusinessLeaves(int financeCubeId)
/*      */   {
/* 2293 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2294 */     PreparedStatement stmt = null;
/* 2295 */     ResultSet resultSet = null;
/* 2296 */     ValueMapping results = null;
/*      */     try
/*      */     {
/* 2299 */       List literals = new ArrayList();
/* 2300 */       List values = new ArrayList();
/* 2301 */       stmt = getConnection().prepareStatement(SQL_OTHER_BUSINESS_DIMENSIONS);
/* 2302 */       int col1 = 1;
/* 2303 */       stmt.setInt(col1++, financeCubeId);
/* 2304 */       resultSet = stmt.executeQuery();
/* 2305 */       while (resultSet.next())
/*      */       {
/* 2307 */         int index = 1;
/* 2308 */         int id = resultSet.getInt(index++);
/* 2309 */         String vis = resultSet.getString(index++);
/* 2310 */         String descr = resultSet.getString(index++);
/*      */ 
/* 2313 */         literals.add(new StringBuilder().append(vis).append(" - ").append(descr).toString());
/* 2314 */         values.add(getDimensionLeaves(id, true));
/*      */       }
/*      */ 
/* 2317 */       ValueMapping mapping = new DefaultValueMapping((String[])(String[])literals.toArray(new String[0]), values.toArray());
/*      */ 
/* 2319 */       results = mapping;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2323 */       System.err.println(sqle);
/* 2324 */       sqle.printStackTrace();
/* 2325 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getOtherBusinessLeaves").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2329 */       closeResultSet(resultSet);
/* 2330 */       closeStatement(stmt);
/* 2331 */       closeConnection();
/*      */     }
/*      */ 
/* 2334 */     if (timer != null) {
/* 2335 */       timer.logDebug("getOtherBusinessLeaves", new StringBuilder().append(" items=").append(results.getLiterals().size()).toString());
/*      */     }
/* 2337 */     return results;
/*      */   }
/*      */ 
/*      */   public List<Map<String, Integer>[]> getAllElems(int financeCubeId, List<String> hierarchyNames)
/*      */   {
/* 2413 */     List dimMapping = new ArrayList();
/*      */ 
/* 2415 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "with getStructures as", "-- dimensions/structures used by the cube model", "(", "select  DIMENSION_SEQ_NUM, DIMENSION_ID, HIERARCHY_ID as STRUCTURE_ID, DIMENSION_TYPE", "from    FINANCE_CUBE", "join    MODEL_DIMENSION_REL using (MODEL_ID)", "join    HIERARCHY h using (DIMENSION_ID)", "where   FINANCE_CUBE_ID = <financeCubeId>", "and     (DIMENSION_TYPE = 3", "or      (DIMENSION_TYPE <> 3", "         ${matchStructureNames}", "        ))", ")", ",getElems as", "(", "-- non-leaf structure elements", "select  DIMENSION_SEQ_NUM, STRUCTURE_ELEMENT_ID,LEAF,VIS_ID", "from    getStructures", "join    STRUCTURE_ELEMENT using (STRUCTURE_ID)", "where   DIMENSION_TYPE <> 3 and LEAF <> 'Y'", "union all", "-- leaf dimension elements", "select  DIMENSION_SEQ_NUM, DIMENSION_ELEMENT_ID, 'Y', VIS_ID", "from    (select distinct DIMENSION_SEQ_NUM, DIMENSION_ID, DIMENSION_TYPE from getStructures)", "join    DIMENSION_ELEMENT using (DIMENSION_ID)", "where   DIMENSION_TYPE <> 3", "union all", "-- calendar structure, with path to root instead of vis_id", "select  DIMENSION_SEQ_NUM, STRUCTURE_ELEMENT_ID, LEAF", "        ,calendar_utils.queryPathToRoot(STRUCTURE_ELEMENT_ID) as VIS_ID", "from    getStructures", "join    STRUCTURE_ELEMENT using (STRUCTURE_ID)", "where   DIMENSION_TYPE = 3", ")", "select  DIMENSION_SEQ_NUM, STRUCTURE_ELEMENT_ID ELEM_ID, VIS_ID, LEAF", "from    getElems", "order   by DIMENSION_SEQ_NUM" });
/*      */ 
/* 2454 */     if ((hierarchyNames != null) && (hierarchyNames.size() > 0))
/*      */     {
/* 2456 */       sqlb.substitute(new String[] { "${matchStructureNames}", "and     h.VIS_ID in (${matchStructureName})" });
/*      */ 
/* 2459 */       sqlb.substitute(new String[] { "${matchStructureName}", SqlBuilder.repeatString("${separator}<structureName.${index}>", ",", hierarchyNames.size()) });
/*      */     }
/*      */ 
/* 2462 */     SqlExecutor sqle = new SqlExecutor("getAllElems", getDataSource(), sqlb, this._log);
/* 2463 */     sqle.addBindVariable("<financeCubeId>", Integer.valueOf(financeCubeId));
/* 2464 */     if (hierarchyNames != null)
/*      */     {
/* 2466 */       for (int i = 0; i < hierarchyNames.size(); i++) {
/* 2467 */         sqle.addBindVariable(new StringBuilder().append("<structureName.").append(i).append(">").toString(), (String)hierarchyNames.get(i));
/*      */       }
/*      */     }
/* 2470 */     ResultSet rs = sqle.getResultSet();
/*      */ 
/* 2472 */     int prevDimensionSeqNum = -1;
/* 2473 */     Map allElems = null;
/* 2474 */     Map leafElems = null;
/*      */     try
/*      */     {
/* 2477 */       while (rs.next())
/*      */       {
/* 2479 */         int dimensionSeqNum = rs.getInt("DIMENSION_SEQ_NUM");
/* 2480 */         int elemId = rs.getInt("ELEM_ID");
/* 2481 */         String visId = rs.getString("VIS_ID");
/* 2482 */         boolean isLeaf = rs.getString("LEAF").equals("Y");
/* 2483 */         if (dimensionSeqNum != prevDimensionSeqNum)
/*      */         {
/* 2485 */           prevDimensionSeqNum = dimensionSeqNum;
/* 2486 */           allElems = new HashMap();
/* 2487 */           leafElems = new HashMap();
/* 2488 */           dimMapping.add(new Map[] { allElems, leafElems });
/*      */         }
/* 2490 */         allElems.put(visId, Integer.valueOf(elemId));
/* 2491 */         if (isLeaf)
/* 2492 */           leafElems.put(visId, Integer.valueOf(elemId));
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2497 */       throw new RuntimeException(e);
/*      */     }
/*      */ 
/* 2500 */     sqle.close();
/*      */ 
/* 2502 */     return dimMapping;
/*      */   }
/*      */ 
/*      */   public ValueMapping getDimensionLeaves(int dimensionId, boolean includeDescr)
/*      */   {
/* 2507 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2508 */     PreparedStatement stmt = null;
/* 2509 */     ResultSet resultSet = null;
/* 2510 */     ValueMapping results = null;
/*      */     try
/*      */     {
/* 2513 */       List literals = new ArrayList();
/* 2514 */       List values = new ArrayList();
/*      */ 
/* 2516 */       stmt = getConnection().prepareStatement(SQL_DIMENSION_LEAVES);
/* 2517 */       int col1 = 1;
/* 2518 */       stmt.setInt(col1++, dimensionId);
/* 2519 */       resultSet = stmt.executeQuery();
/* 2520 */       while (resultSet.next())
/*      */       {
/* 2522 */         int index = 1;
/*      */ 
/* 2524 */         int id = resultSet.getInt(index++);
/* 2525 */         String vis = resultSet.getString(index++);
/* 2526 */         String label = resultSet.getString(index++);
/*      */ 
/* 2528 */         if (includeDescr)
/* 2529 */           literals.add(new StringBuilder().append(vis).append(" - ").append(label).toString());
/*      */         else {
/* 2531 */           literals.add(vis);
/*      */         }
/* 2533 */         values.add(new Integer(id));
/*      */       }
/*      */ 
/* 2536 */       ValueMapping mapping = new DefaultValueMapping((String[])(String[])literals.toArray(new String[0]), values.toArray());
/*      */ 
/* 2538 */       results = mapping;
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2542 */       System.err.println(sqle);
/* 2543 */       sqle.printStackTrace();
/* 2544 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" AvailableAccountLeaves").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2548 */       closeResultSet(resultSet);
/* 2549 */       closeStatement(stmt);
/* 2550 */       closeConnection();
/*      */     }
/*      */ 
/* 2553 */     if (timer != null) {
/* 2554 */       timer.logDebug("getAvailableAccountLeaves", new StringBuilder().append(" items=").append(results.getLiterals().size()).toString());
/*      */     }
/* 2556 */     return results;
/*      */   }
/*      */ 
/*      */   public ValueMapping getCalendarDimensionLeaves(int dimensionId)
/*      */   {
/* 2561 */     Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
/* 2562 */     PreparedStatement stmt = null;
/* 2563 */     ResultSet resultSet = null;
/* 2564 */     ValueMapping results = null;
/*      */     try
/*      */     {
/* 2567 */       List literals = new ArrayList();
/* 2568 */       List values = new ArrayList();
/*      */ 
/* 2570 */       stmt = getConnection().prepareStatement(SQL_CALENDAR_DIMENSION_LEAVES);
/* 2571 */       int col1 = 1;
/* 2572 */       stmt.setInt(col1++, dimensionId);
/* 2573 */       resultSet = stmt.executeQuery();
/* 2574 */       while (resultSet.next())
/*      */       {
/* 2576 */         int index = 1;
/*      */ 
/* 2578 */         int id = resultSet.getInt(index++);
/* 2579 */         String vis = resultSet.getString(index++);
/*      */ 
/* 2581 */         literals.add(vis);
/* 2582 */         values.add(new Integer(id));
/*      */       }
/*      */ 
/* 2585 */       results = new DefaultValueMapping((String[])(String[])literals.toArray(new String[0]), values.toArray());
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2589 */       System.err.println(sqle);
/* 2590 */       sqle.printStackTrace();
/* 2591 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" AvailableAccountLeaves").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2595 */       closeResultSet(resultSet);
/* 2596 */       closeStatement(stmt);
/* 2597 */       closeConnection();
/*      */     }
/*      */ 
/* 2600 */     if (timer != null) {
/* 2601 */       timer.logDebug("getAvailableAccountLeaves", new StringBuilder().append(" items=").append(results.getLiterals().size()).toString());
/*      */     }
/* 2603 */     return results;
/*      */   }
/*      */ 
/*      */   public EntityList getCellNote(int financeCubeId, String cellPk, int userId)
/*      */   {
/* 2608 */     String[] columnNames = { "CREATED", "USER_NAME", "STRING_VALUE", "VIEWED", "LINK_ID", "LINK_TYPE" };
/* 2609 */     EntityListImpl noteList = new EntityListImpl(columnNames, new Object[0][columnNames.length]);
/*      */ 
/* 2611 */     String sftTableName = new StringBuilder().append("SFT").append(financeCubeId).toString();
/* 2612 */     String uftTableName = new StringBuilder().append("UFT").append(financeCubeId).toString();
/*      */ 
/* 2614 */     boolean updateLastViewed = false;
/*      */ 
/* 2617 */     String[] addrPart = cellPk.split(",");
/* 2618 */     int numDims = addrPart.length - 1;
/*      */ 
/* 2620 */     this.mLog.debug("getCellNote", cellPk);
/*      */ 
/* 2622 */     PreparedStatement stmt = null;
/* 2623 */     ResultSet resultSet = null;
/* 2624 */     String sql = null;
/*      */     try
/*      */     {
/* 2628 */       sql = MessageFormat.format(SQL_GET_CELL_NOTE, new Object[] { sftTableName, uftTableName, getDimMatchSql(numDims), getDimTableMatchSql(numDims, "s.", "u.", true) });
/*      */ 
/* 2637 */       this.mLog.debug("getCellNote", sql);
/*      */ 
/* 2639 */       stmt = getConnection().prepareStatement(sql);
/*      */ 
/* 2642 */       int col = 1;
/* 2643 */       for (int i = 0; i < addrPart.length; i++)
/*      */       {
/* 2645 */         stmt.setString(col++, addrPart[i]);
/*      */       }
/* 2647 */       stmt.setInt(col++, userId);
/* 2648 */       resultSet = stmt.executeQuery();
/*      */ 
/* 2651 */       while (resultSet.next())
/*      */       {
/* 2653 */         col = 1;
/* 2654 */         List l = new ArrayList();
/* 2655 */         Timestamp created = resultSet.getTimestamp(col++);
/* 2656 */         String userName = resultSet.getString(col++);
/* 2657 */         String stringValue = resultSet.getString(col++);
/* 2658 */         int linkId = resultSet.getInt(col++);
/* 2659 */         int linkType = resultSet.getInt(col++);
/* 2660 */         int viewedByUser = resultSet.getInt(col++);
/* 2661 */         l.add(created);
/* 2662 */         l.add(userName);
/* 2663 */         l.add(stringValue);
/* 2664 */         l.add(new Boolean(viewedByUser == 1));
/* 2665 */         l.add(Integer.valueOf(linkId));
/* 2666 */         l.add(Integer.valueOf(linkType));
/* 2667 */         noteList.add(l);
/*      */ 
/* 2669 */         if (viewedByUser == 0)
/* 2670 */           updateLastViewed = true;
/*      */       }
/* 2672 */       closeResultSet(resultSet);
/* 2673 */       resultSet = null;
/* 2674 */       closeStatement(stmt);
/* 2675 */       stmt = null;
/*      */ 
/* 2677 */       if (updateLastViewed)
/*      */       {
/* 2679 */         sql = MessageFormat.format(SQL_UPDATE_LAST_VIEWED, new Object[] { uftTableName, getDimListSql(numDims, null), getDimListSql(numDims, "s."), sftTableName, getDimMatchSql(numDims), getDimTableMatchSql(numDims, "s.", "u.", false) });
/*      */ 
/* 2690 */         this.mLog.debug("getCellNote", sql);
/*      */ 
/* 2692 */         stmt = getConnection().prepareStatement(sql);
/*      */ 
/* 2695 */         col = 1;
/* 2696 */         this.mLog.debug(new StringBuilder().append("bindVar ").append(col).append("=").append(userId).toString());
/* 2697 */         stmt.setInt(col++, userId);
/* 2698 */         for (int i = 0; i < addrPart.length; i++)
/*      */         {
/* 2700 */           this.mLog.debug(new StringBuilder().append("bindVar ").append(col).append("=").append(addrPart[i]).toString());
/* 2701 */           stmt.setString(col++, addrPart[i]);
/*      */         }
/*      */ 
/* 2704 */         stmt.execute();
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2709 */       this.mLog.debug("getCellNote", sql);
/* 2710 */       System.err.println(sqle);
/* 2711 */       sqle.printStackTrace();
/* 2712 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" CellNote").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2716 */       closeResultSet(resultSet);
/* 2717 */       closeStatement(stmt);
/* 2718 */       closeConnection();
/*      */     }
/*      */ 
/* 2721 */     return noteList;
/*      */   }

	public EntityList getAllNotes(int financeCubeId, int userId)
	{
		String SQL_GET_ALL_NOTES_COMPANY = "select " +
					"(select se.vis_id from structure_element se where se.structure_element_id = s.dim0) dim0, "+
					"(select se.vis_id from structure_element se where se.structure_element_id = s.dim1) dim1, "+
					"(select CONCAT(se.cal_vis_id_prefix,se.vis_id) dim3 from structure_element se where se.structure_element_id = s.dim2) dim2, "+
					"s.DATA_TYPE,s.STRING_VALUE, s.COMPANY from {0} s ORDER BY s.CREATED ASC";
		String[] columnNamesCompany = { "DIM0", "DIM1", "DIM2", "DATA_TYPE", "STRING_VALUE", "COMPANY" };
		
		
		String SQL_GET_ALL_NOTES = "select " +
				"(select se.vis_id from structure_element se where se.structure_element_id = s.dim0) dim0, "+
				"(select se.vis_id from structure_element se where se.structure_element_id = s.dim1) dim1, "+
				"(select CONCAT(se.cal_vis_id_prefix,se.vis_id) dim3 from structure_element se where se.structure_element_id = s.dim2) dim2, "+
				"s.DATA_TYPE,s.STRING_VALUE from {0} s ORDER BY s.CREATED ASC";
		String[] columnNames = { "DIM0", "DIM1", "DIM2", "DATA_TYPE", "STRING_VALUE" };
		
		
		EntityListImpl noteList;
		String sftTableName = new StringBuilder().append("SFT").append(financeCubeId).toString();

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String sql = null;
		try
		{
			noteList = new EntityListImpl(columnNamesCompany, new Object[0][columnNamesCompany.length]);
			sql = MessageFormat.format(SQL_GET_ALL_NOTES_COMPANY, new Object[] { sftTableName } );

			stmt = getConnection().prepareStatement(sql);

			resultSet = stmt.executeQuery();
			
			int col = 1;
			String dim0, dim1, dim2;
			while (resultSet.next())
			{
				col = 1;
				List l = new ArrayList();
				dim0= resultSet.getString(col++);
				dim1= resultSet.getString(col++);
				dim2= resultSet.getString(col++);
				String dataType = resultSet.getString(col++);
				String noteString = resultSet.getString(col++);
				String company = resultSet.getString(col++);
				
				l.add(dim0);
				l.add(dim1);
				l.add(dim2);
				l.add(dataType);
				l.add(noteString);
				l.add(company);
				noteList.add(l);
			}
			closeResultSet(resultSet);
			resultSet = null;
			closeStatement(stmt);
			stmt = null;
		} catch (SQLException sqle)	{
			
			
			noteList = new EntityListImpl(columnNames, new Object[0][columnNames.length]);
			sql = MessageFormat.format(SQL_GET_ALL_NOTES, new Object[] { sftTableName } );

			try {
				stmt = getConnection().prepareStatement(sql);
			
				resultSet = stmt.executeQuery();
				
				int col = 1;
				String dim0, dim1, dim2;
				while (resultSet.next())
				{
					col = 1;
					List l = new ArrayList();
					dim0= resultSet.getString(col++);
					dim1= resultSet.getString(col++);
					dim2= resultSet.getString(col++);
					String dataType = resultSet.getString(col++);
					String noteString = resultSet.getString(col++);
					
					l.add(dim0);
					l.add(dim1);
					l.add(dim2);
					l.add(dataType);
					l.add(noteString);
					noteList.add(l);
				}
				closeResultSet(resultSet);
				resultSet = null;
				closeStatement(stmt);
				stmt = null;
			} catch (SQLException e) {
				this.mLog.debug("getAllNotes", sql);
				System.err.println(e);
				e.printStackTrace();
				throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getAllNotes").toString(), e);
			}
	
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
		return noteList;
	}
	
	public EntityList getLastNotes(int financeCubeId) {
		String SQL_GET_LAST_NOTES_COMPANY = "" +
				"select \n" + 
				"	(select se.vis_id from structure_element se where se.structure_element_id = s.dim0) dim0, \n" + 
				"	(select se.vis_id from structure_element se where se.structure_element_id = s.dim1) dim1, \n" + 
				"	(select CONCAT(se.cal_vis_id_prefix,se.vis_id) dim3 from structure_element se where se.structure_element_id = s.dim2) dim2,\n" + 
					"s.DATA_TYPE, s.STRING_VALUE, s.USER_NAME, s.LINK_ID, s.LINK_TYPE, s.CREATED, s.dim0 dim0_Id, s.dim1 dim1_Id, s.dim2 dim2_Id, s.COMPANY \n" + 
				"from {0} s\n" + 
				"INNER JOIN\n" + 
				"    (SELECT DIM0,DIM1,DIM2,DATA_TYPE,COMPANY, MAX(SEQ) AS MaxSEQ\n" + 
				"    FROM {0} \n" + 
				"    GROUP BY DIM0,DIM1,DIM2,DATA_TYPE,COMPANY) grouped \n" + 
				"ON s.DIM0 = grouped.DIM0 \n" + 
				"AND s.DIM1 = grouped.DIM1 \n" + 
				"AND s.DIM2 = grouped.DIM2 \n" + 
				"AND s.COMPANY = grouped.COMPANY \n" + 
				"AND s.DATA_TYPE = grouped.DATA_TYPE \n" + 
				"AND s.SEQ = grouped.MaxSEQ";
		String[] columnNamesCompany = { "DIM0", "DIM1", "DIM2", "DATA_TYPE", "STRING_VALUE", "USER_NAME", "LINK_ID", "LINK_TYPE", "NOTE_PK", "CREATED", "COMPANY" };
		
		
		String SQL_GET_LAST_NOTES = "" +
				"select \n" + 
				"	(select se.vis_id from structure_element se where se.structure_element_id = s.dim0) dim0, \n" + 
				"	(select se.vis_id from structure_element se where se.structure_element_id = s.dim1) dim1, \n" + 
				"	(select CONCAT(se.cal_vis_id_prefix,se.vis_id) dim3 from structure_element se where se.structure_element_id = s.dim2) dim2,\n" + 
					"s.DATA_TYPE,s.STRING_VALUE, s.USER_NAME, s.LINK_ID, s.LINK_TYPE, s.CREATED, s.dim0 dim0_Id, s.dim1 dim1_Id, s.dim2 dim2_Id \n" + 
				"from {0} s\n" + 
				"INNER JOIN\n" + 
				"    (SELECT DIM0,DIM1,DIM2,DATA_TYPE, MAX(SEQ) AS MaxSEQ\n" + 
				"    FROM {0} \n" + 
				"    GROUP BY DIM0,DIM1,DIM2,DATA_TYPE) grouped \n" + 
				"ON s.DIM0 = grouped.DIM0 \n" + 
				"AND s.DIM1 = grouped.DIM1 \n" + 
				"AND s.DIM2 = grouped.DIM2 \n" + 
				"AND s.DATA_TYPE = grouped.DATA_TYPE \n" + 
				"AND s.SEQ = grouped.MaxSEQ";
		String[] columnNames = { "DIM0", "DIM1", "DIM2", "DATA_TYPE", "STRING_VALUE", "USER_NAME", "LINK_ID", "LINK_TYPE", "NOTE_PK", "CREATED" };
		
		
		EntityListImpl noteList;
		String sftTableName = new StringBuilder().append("SFT").append(financeCubeId).toString();

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String sql = null;
		try
		{
			noteList = new EntityListImpl(columnNamesCompany, new Object[0][columnNamesCompany.length]);
			sql = MessageFormat.format(SQL_GET_LAST_NOTES_COMPANY, new Object[] { sftTableName } );

			stmt = getConnection().prepareStatement(sql);

			resultSet = stmt.executeQuery();
			
			int col = 1;
			String dim0, dim1, dim2, notePk;
			int dim0Id, dim1Id, dim2Id;
			while (resultSet.next())
			{
				col = 1;
				List l = new ArrayList();
				dim0 = resultSet.getString(col++);
				dim1 = resultSet.getString(col++);
				dim2 = resultSet.getString(col++);
				String dataType = resultSet.getString(col++);
				String noteString = resultSet.getString(col++);
				String username = resultSet.getString(col++);
				int linkId = resultSet.getInt(col++);
				int linkType = resultSet.getInt(col++);
				Timestamp created = resultSet.getTimestamp(col++);
				dim0Id = resultSet.getInt(col++);
				dim1Id = resultSet.getInt(col++);
				dim2Id = resultSet.getInt(col++);
				String company = resultSet.getString(col++);
				
				
				l.add(dim0);
				l.add(dim1);
				l.add(dim2);
				l.add(dataType);
				l.add(noteString);
				l.add(username);
				l.add(linkId);
				l.add(linkType);
				l.add(dim0Id + "," + dim1Id + "," + dim2Id + "," + dataType);
                l.add(created);
				l.add(company);
				noteList.add(l);
			}
			closeResultSet(resultSet);
			resultSet = null;
			closeStatement(stmt);
			stmt = null;
		} catch (SQLException sqle)	{
			// it is not a global mapped model
			
			noteList = new EntityListImpl(columnNames, new Object[0][columnNames.length]);
			sql = MessageFormat.format(SQL_GET_LAST_NOTES, new Object[] { sftTableName } );

			try {
				stmt = getConnection().prepareStatement(sql);
			
				resultSet = stmt.executeQuery();
				
				int col = 1;
				String dim0, dim1, dim2, notePk;
				int dim0Id, dim1Id, dim2Id;
				while (resultSet.next())
				{
					col = 1;
					List l = new ArrayList();
					dim0= resultSet.getString(col++);
					dim1= resultSet.getString(col++);
					dim2= resultSet.getString(col++);
					String dataType = resultSet.getString(col++);
					String noteString = resultSet.getString(col++);
	                String username = resultSet.getString(col++);
	                int linkId = resultSet.getInt(col++);
	                int linkType = resultSet.getInt(col++);
	                Timestamp created = resultSet.getTimestamp(col++);
					dim0Id = resultSet.getInt(col++);
					dim1Id = resultSet.getInt(col++);
					dim2Id = resultSet.getInt(col++);
					
					l.add(dim0);
					l.add(dim1);
					l.add(dim2);
					l.add(dataType);
					l.add(noteString);
		            l.add(username);
		            l.add(linkId);
		            l.add(linkType);
					l.add(dim0Id + "," + dim1Id + "," + dim2Id + "," + dataType);
					l.add(created);
					noteList.add(l);
				}
				closeResultSet(resultSet);
				resultSet = null;
				closeStatement(stmt);
				stmt = null;
			} catch (SQLException e) {
				this.mLog.debug("getAllNotes", sql);
				//System.err.println(e);
				//e.printStackTrace();
				//throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getAllNotes").toString(), e);
			}
	
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
		return noteList;
	}
	
	
	public EntityList getCellNote(int financeCubeId, String notePk, int userId, String company)
	/*      */   {
		/* 2608 */     String[] columnNames = { "CREATED", "USER_NAME", "STRING_VALUE", "VIEWED", "LINK_ID", "LINK_TYPE" };
		/* 2609 */     EntityListImpl noteList = new EntityListImpl(columnNames, new Object[0][columnNames.length]);
		/*      */ 
		/* 2611 */     String sftTableName = new StringBuilder().append("SFT").append(financeCubeId).toString();
		/* 2612 */     String uftTableName = new StringBuilder().append("UFT").append(financeCubeId).toString();
		/*      */ 
		/* 2614 */     boolean updateLastViewed = false;
		/*      */ 
		/* 2617 */     String[] addrPart = notePk.split(",");
		/* 2618 */     int numDims = addrPart.length - 1;
		/*      */ 
		/* 2620 */     this.mLog.debug("getCellNote", notePk);
		/*      */ 
		/* 2622 */     PreparedStatement stmt = null;
		/* 2623 */     ResultSet resultSet = null;
		/* 2624 */     String sql = null;
		/*      */     try
		/*      */     {
		/* 2628 */       sql = MessageFormat.format(SQL_GET_CELL_NOTE_COMPANY, new Object[] { sftTableName, uftTableName, getDimMatchSql(numDims), getDimTableMatchSql(numDims, "s.", "u.", true) });
		/*      */ 
		/* 2637 */       this.mLog.debug("getCellNote", sql);
		/*      */ 
		/* 2639 */       stmt = getConnection().prepareStatement(sql);
		/*      */ 
		/* 2642 */       int col = 1;
		/* 2643 */       for (int i = 0; i < addrPart.length; i++)
		/*      */       {
		/* 2645 */         stmt.setString(col++, addrPart[i]);
		/*      */       }
		/* 2647 */       stmt.setInt(col++, userId);
						 stmt.setString(col++, company);
		/* 2648 */       resultSet = stmt.executeQuery();
		/*      */ 
		/* 2651 */       while (resultSet.next())
		/*      */       {
		/* 2653 */         col = 1;
		/* 2654 */         List l = new ArrayList();
		/* 2655 */         Timestamp created = resultSet.getTimestamp(col++);
		/* 2656 */         String userName = resultSet.getString(col++);
		/* 2657 */         String stringValue = resultSet.getString(col++);
		/* 2658 */         int linkId = resultSet.getInt(col++);
		/* 2659 */         int linkType = resultSet.getInt(col++);
		/* 2660 */         int viewedByUser = resultSet.getInt(col++);
		/* 2661 */         l.add(created);
		/* 2662 */         l.add(userName);
		/* 2663 */         l.add(stringValue);
		/* 2664 */         l.add(new Boolean(viewedByUser == 1));
		/* 2665 */         l.add(Integer.valueOf(linkId));
		/* 2666 */         l.add(Integer.valueOf(linkType));
		/* 2667 */         noteList.add(l);
		/*      */ 
		/* 2669 */         if (viewedByUser == 0)
		/* 2670 */           updateLastViewed = true;
		/*      */       }
		/* 2672 */       closeResultSet(resultSet);
		/* 2673 */       resultSet = null;
		/* 2674 */       closeStatement(stmt);
		/* 2675 */       stmt = null;
		/*      */ 
		/* 2677 */       if (updateLastViewed)
		/*      */       {
		/* 2679 */         sql = MessageFormat.format(SQL_UPDATE_LAST_VIEWED, new Object[] { uftTableName, getDimListSql(numDims, null), getDimListSql(numDims, "s."), sftTableName, getDimMatchSql(numDims), getDimTableMatchSql(numDims, "s.", "u.", false) });
		/*      */ 
		/* 2690 */         this.mLog.debug("getCellNote", sql);
		/*      */ 
		/* 2692 */         stmt = getConnection().prepareStatement(sql);
		/*      */ 
		/* 2695 */         col = 1;
		/* 2696 */         this.mLog.debug(new StringBuilder().append("bindVar ").append(col).append("=").append(userId).toString());
		/* 2697 */         stmt.setInt(col++, userId);
		/* 2698 */         for (int i = 0; i < addrPart.length; i++)
		/*      */         {
		/* 2700 */           this.mLog.debug(new StringBuilder().append("bindVar ").append(col).append("=").append(addrPart[i]).toString());
		/* 2701 */           stmt.setString(col++, addrPart[i]);
		/*      */         }
		/*      */ 
		/* 2704 */         stmt.execute();
		/*      */       }
		/*      */     }
		/*      */     catch (SQLException sqle)
		/*      */     {
		/* 2709 */       this.mLog.debug("getCellNote", sql);
		/* 2710 */       System.err.println(sqle);
		/* 2711 */       sqle.printStackTrace();
		/* 2712 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" CellNote").toString(), sqle);
		/*      */     }
		/*      */     finally
		/*      */     {
		/* 2716 */       closeResultSet(resultSet);
		/* 2717 */       closeStatement(stmt);
		/* 2718 */       closeConnection();
		/*      */     }
		/*      */ 
		/* 2721 */     return noteList;
	/*      */   }


/*      */ 
/*      */   public EntityList getCellAudit(int financeCubeId, String cellPk, int userId)
/*      */   {
/* 2726 */     Timer t = new Timer(this._log);
/* 2727 */     CellAuditELO audit = new CellAuditELO();
/*      */ 
/* 2730 */     String[] addrPart = cellPk.split(",");
/* 2731 */     int numDims = addrPart.length - 1;
/* 2732 */     int calStructureId = 0;
/* 2733 */     CellAuditHeaderELO header = new CellAuditHeaderELO();
/*      */ 
/* 2735 */     this.mLog.debug("getCellAudit", cellPk);
/*      */ 
/* 2737 */     PreparedStatement stmt = null;
/* 2738 */     ResultSet resultSet = null;
/*      */ 
/* 2741 */     String sql = getAuditHeader(cellPk);
/* 2742 */     //System.out.println(sql);
/*      */     try
/*      */     {
/* 2746 */       this.mLog.debug("getCellAudit", sql);
/*      */ 
/* 2748 */       stmt = getConnection().prepareStatement(sql);
/* 2749 */       int col = 1;
/* 2750 */       stmt.setInt(col++, financeCubeId);
/* 2751 */       for (int i = 0; i < numDims; i++)
/*      */       {
/* 2753 */         stmt.setInt(col++, Integer.parseInt(addrPart[i]));
/*      */       }
/* 2755 */       resultSet = stmt.executeQuery();
/*      */ 
/* 2758 */       int headerStructureId = 0;
/* 2759 */       int headerStructureElementId = 0;
/*      */ 
/* 2763 */       List checkList = new ArrayList();
/* 2764 */       while (resultSet.next())
/*      */       {
/* 2766 */         col = 1;
/* 2767 */         headerStructureId = resultSet.getInt(col++);
/* 2768 */         calStructureId = headerStructureId;
/* 2769 */         headerStructureElementId = resultSet.getInt(col++);
/* 2770 */         String headerVisId = resultSet.getString(col++);
/* 2771 */         String headerDescription = resultSet.getString(col++);
/* 2772 */         String dimDescription = resultSet.getString(col);
/*      */ 
/* 2775 */         if (checkList.contains(Integer.valueOf(headerStructureElementId)))
/*      */           continue;
/* 2777 */         header.add(headerVisId, headerDescription, dimDescription);
/* 2778 */         checkList.add(Integer.valueOf(headerStructureElementId));
/*      */       }
/*      */ 
/* 2782 */       stmt = getConnection().prepareStatement("select vis_id, description from data_type where vis_id = ? ");
/* 2783 */       stmt.setString(1, addrPart[(addrPart.length - 1)]);
/* 2784 */       resultSet = stmt.executeQuery();
/* 2785 */       while (resultSet.next())
/*      */       {
/* 2787 */         header.add(resultSet.getString(1), resultSet.getString(2), "Data Type");
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 2792 */       this.mLog.debug("getCellAudit", sql);
/* 2793 */       System.err.println(sqle);
/* 2794 */       sqle.printStackTrace();
/* 2795 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" CellAudit").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 2799 */       closeResultSet(resultSet);
/* 2800 */       closeStatement(stmt);
/* 2801 */       closeConnection();
/* 2802 */       t.logDebug("getCellAudit", "");
/*      */     }
/*      */ 
/* 2806 */     SqlBuilder sqlb = new SqlBuilder(new String[] { "with getCal as", "(", "select  STRUCTURE_ID,POSITION,END_POSITION", "from    STRUCTURE_ELEMENT", "where   STRUCTURE_ID = <calSid>", "and     STRUCTURE_ELEMENT_ID = <seId.${calDimIndex}>", ")", ",getHistory_init as", "(", "select  DIM${calDimIndex}", "        ,to_char(a.CREATED_TIME, 'DD/MON/YYYY')  as D", "        ,to_char(a.CREATED_TIME, 'HH24:MI')      as T", "        ,nvl(u.NAME, 'System' )                  as U", "        ,nvl(u.FULL_NAME, 'No user Record' )     as UN", "        -- audit number:", "        ,case when SUB_TYPE <> 4", "              then a.NUMBER_VALUE / 10000", "              when MEASURE_CLASS = 1", "                  then a.NUMBER_VALUE / power(10, MEASURE_SCALE)", "         end as AFT_N", "        -- current number:", "        ,case when SUB_TYPE <> 4", "              then d.NUMBER_VALUE / 10000", "              when MEASURE_CLASS = 1", "                  then d.NUMBER_VALUE / power(10, MEASURE_SCALE)", "         end as DFT_N", "        -- audit string:", "        ,case when MEASURE_CLASS = 0", "              then substr(a.STRING_VALUE,1,MEASURE_LENGTH)", "              when MEASURE_CLASS = 2", "                  then to_char(a.DATE_VALUE,'HH24:MI:SS')", "              when MEASURE_CLASS = 3", "                  then to_char(a.DATE_VALUE,'DD/MON/YYYY')", "              when MEASURE_CLASS = 4", "                  then to_char(a.DATE_VALUE,'DD/MON/YYYY HH24:MI:SS')", "              when MEASURE_CLASS = 5", "                  then decode(a.STRING_VALUE,'Y','true','false')", "         end as AFT_S", "        -- current string:", "        ,case when MEASURE_CLASS = 0", "              then substr(d.STRING_VALUE,1,MEASURE_LENGTH)", "              when MEASURE_CLASS = 2", "                  then to_char(d.DATE_VALUE,'HH24:MI:SS')", "              when MEASURE_CLASS = 3", "                  then to_char(d.DATE_VALUE,'DD/MON/YYYY')", "              when MEASURE_CLASS = 4", "                  then to_char(d.DATE_VALUE,'DD/MON/YYYY HH24:MI:SS')", "              when MEASURE_CLASS = 5", "                  then decode(d.STRING_VALUE,'Y','true','false')", "         end as DFT_S", "        ,a.CREATED_TIME", "from    DFT${financeCubeId} d", "join    AFT${financeCubeId} a using (${dimKeys}, DATA_TYPE)", "join    DATA_TYPE on (VIS_ID = DATA_TYPE)", "left", "join    USR u using (USER_ID)", "where", "${matchLeadDims}", "and     DATA_TYPE = <dataType>", ")", ",getHistory as", "(", "select  STRUCTURE_ELEMENT_ID, VIS_ID, DESCRIPTION,se.POSITION", "        ,D, T, U, UN", "        ,AFT_N, DFT_N, AFT_S, DFT_S", "        ,CREATED_TIME", "        -- preceding changes total:", "        ,sum(AFT_N) over (partition by STRUCTURE_ELEMENT_ID", "                          order by CREATED_TIME desc", "                          range unbounded preceding) as TOTAL_CHANGE", "from    getCal", "join    STRUCTURE_ELEMENT se", "        on  (", "                se.STRUCTURE_ID = getCal.STRUCTURE_ID", "            and se.POSITION between getCal.POSITION and getCal.END_POSITION", "            )", "join    getHistory_init on (DIM${calDimIndex} = se.STRUCTURE_ELEMENT_ID)", "order   by CREATED_TIME", ")", "select  VIS_ID, DESCRIPTION", "        ,cursor", "        (", "        select  D, T, U, UN", "                ,DFT_N - TOTAL_CHANGE as ORIG_NUM", "                ,AFT_N                as CHANGE_NUM", "                ,DFT_N - lag(TOTAL_CHANGE,1,0)", "                         over (partition by getPeriods.STRUCTURE_ELEMENT_ID", "                                   order by CREATED_TIME desc)", "                       as CURRENT_NUM", "                ,lead(AFT_S,1,null)", "                 over (partition by getPeriods.STRUCTURE_ELEMENT_ID", "                           order by CREATED_TIME desc)", "                       as ORIG_STR", "                ,AFT_S as CURRENT_S", "        from    getHistory", "        where   getHistory.STRUCTURE_ELEMENT_ID", "                = getPeriods.STRUCTURE_ELEMENT_ID", "        order   by CREATED_TIME desc", "        ) as AUDIT_ROWS", "from    (", "        select  distinct STRUCTURE_ELEMENT_ID, VIS_ID, DESCRIPTION, POSITION", "        from    getHistory", "        ) getPeriods", "order   by POSITION" });
/*      */ 
/* 2913 */     sqlb.substituteRepeatingLines("${matchLeadDims}", numDims - 1, "and", new String[] { "${separator}     DIM${index} = <seId.${index}>" });
/*      */ 
/* 2916 */     sqlb.substitute(new String[] { "${calDimIndex}", String.valueOf(numDims - 1) });
/* 2917 */     sqlb.substitute(new String[] { "${financeCubeId}", String.valueOf(financeCubeId) });
/* 2918 */     sqlb.substitute(new String[] { "${dimKeys}", SqlBuilder.repeatString("${separator}DIM${index}", ",", numDims) });
/*      */ 
/* 2920 */     SqlExecutor sqle = new SqlExecutor("getAuditDetail", getDataSource(), sqlb, this._log);
/* 2921 */     sqle.addBindVariable("<calSid>", Integer.valueOf(calStructureId));
/* 2922 */     for (int i = 0; i < numDims; i++)
/* 2923 */       sqle.addBindVariable(new StringBuilder().append("<seId.").append(i).append(">").toString(), Integer.valueOf(Integer.parseInt(addrPart[i])));
/* 2924 */     sqle.addBindVariable("<dataType>", addrPart[(addrPart.length - 1)]);
/*      */     try
/*      */     {
/* 2928 */       resultSet = sqle.getResultSet();
/*      */ 
/* 2933 */       ResultSet rs2 = null;
/*      */ 
/* 2945 */       CellAuditDetailHeaderELO detailHeader = new CellAuditDetailHeaderELO();
/* 2946 */       while (resultSet.next())
/*      */       {
/* 2948 */         int col = 1;
/* 2949 */         boolean processed = false;
/* 2950 */         String detailVidId = resultSet.getString(col++);
/* 2951 */         String detailDescription = resultSet.getString(col++);
/* 2952 */         rs2 = (ResultSet)resultSet.getObject(col);
/* 2953 */         CellAuditDetailELO detail = new CellAuditDetailELO();
/* 2954 */         while (rs2.next())
/*      */         {
/* 2956 */           int col2 = 1;
/* 2957 */           String auditDate = rs2.getString(col2++);
/* 2958 */           String auditTime = rs2.getString(col2++);
/* 2959 */           String auditUserId = rs2.getString(col2++);
/* 2960 */           String auditUserDescription = rs2.getString(col2++);
/* 2961 */           BigDecimal auditOriginalNumber = rs2.getBigDecimal(col2++);
/* 2962 */           BigDecimal auditChangeNumber = rs2.getBigDecimal(col2++);
/* 2963 */           BigDecimal auditCurrentNumber = rs2.getBigDecimal(col2++);
/* 2964 */           String auditOriginalString = rs2.getString(col2++);
/* 2965 */           String auditCurrentString = rs2.getString(col2);
/*      */ 
/* 2967 */           processed = true;
/* 2968 */           if (auditChangeNumber != null) {
/* 2969 */             detail.add(auditDate, auditTime, auditUserId, auditUserDescription, auditOriginalNumber, auditChangeNumber, auditCurrentNumber);
/*      */           }
/*      */           else {
/* 2972 */             detail.add(auditDate, auditTime, auditUserId, auditUserDescription, auditOriginalString, null, auditCurrentString);
/*      */           }
/*      */         }
/*      */ 
/* 2976 */         if (processed) {
/* 2977 */           detailHeader.add(detailVidId, detailDescription, detail);
/*      */         }
/*      */       }
/* 2980 */       audit.add(header, detailHeader);
/* 2981 */       sqle.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2985 */       e.printStackTrace();
/* 2986 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" CellAudit").toString(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 2990 */       sqle.close();
/* 2991 */       t.logDebug("getCellAudit", "");
/*      */     }
/*      */ 
/* 2994 */     return audit;
/*      */   }
/*      */ 
/*      */   public String getAuditHeader(String cellPk)
/*      */   {
/* 2999 */     String[] addrPart = cellPk.split(",");
/* 3000 */     int numDims = addrPart.length - 1;
/*      */ 
/* 3002 */     StringBuffer sql = new StringBuffer();
/* 3003 */     sql.append("select se.structure_id,se.structure_element_id, se.vis_id, se.description as se_description, d.description from model m, finance_cube fc, model_dimension_rel mdr, dimension d, hierarchy h, structure_element se where fc.finance_cube_id = ? and fc.model_id = m.model_id and m.model_id = mdr.model_id and mdr.dimension_id = d.dimension_id and d.dimension_id = h.dimension_id and h.hierarchy_id = se.structure_id and se.structure_element_id in (");
/* 3004 */     for (int i = 0; i < numDims; i++)
/*      */     {
/* 3006 */       if (i != 0)
/* 3007 */         sql.append(",");
/* 3008 */       sql.append(" ? ");
/*      */     }
/* 3010 */     sql.append(") order by mdr.dimension_seq_num");
/*      */ 
/* 3012 */     return sql.toString();
/*      */   }
/*      */ 
/*      */   private String getDimMatchSql(int numDims)
/*      */   {
/* 3017 */     StringBuffer sql = new StringBuffer();
/* 3018 */     for (int i = 0; i < numDims; i++)
/*      */     {
/* 3020 */       if (i > 0)
/* 3021 */         sql.append(" and");
/* 3022 */       sql.append(new StringBuilder().append(" s.DIM").append(i).append(" = ?").toString());
/*      */     }
/* 3024 */     return sql.toString();
/*      */   }
/*      */ 
/*      */   private String getDimTableMatchSql(int numDims, String cid1, String cid2, boolean outerJoin)
/*      */   {
/* 3029 */     StringBuffer sql = new StringBuffer();
/* 3030 */     for (int i = 0; i < numDims; i++)
/*      */     {
/* 3032 */       if (i > 0)
/* 3033 */         sql.append(" and");
/* 3034 */       sql.append(new StringBuilder().append(" ").append(cid1).append("DIM").append(i).append(" = ").append(cid2).append("DIM").append(i).append(outerJoin ? " (+)" : "").toString());
/*      */     }
/* 3036 */     return sql.toString();
/*      */   }
/*      */ 
/*      */   private String getDimListSql(int numDims, String cid)
/*      */   {
/* 3041 */     StringBuffer sql = new StringBuffer();
/* 3042 */     for (int i = 0; i < numDims; i++)
/*      */     {
/* 3044 */       if (i > 0)
/* 3045 */         sql.append(",");
/* 3046 */       sql.append(new StringBuilder().append(cid == null ? "" : cid).append("DIM").append(i).toString());
/*      */     }
/* 3048 */     return sql.toString();
/*      */   }
/*      */ 
/*      */   public int getDefinitionIdFromCellCalcId(int cellCalcId)
/*      */   {
/* 3053 */     PreparedStatement stmt = null;
/* 3054 */     ResultSet resultSet = null;
/* 3055 */     int definitionId = -1;
/*      */     try
/*      */     {
/* 3060 */       stmt = getConnection().prepareStatement(SQL_XML_FORM_ID_FROM_CELL_CALC_ID);
/* 3061 */       int col1 = 1;
/* 3062 */       stmt.setInt(col1++, cellCalcId);
/* 3063 */       resultSet = stmt.executeQuery();
/* 3064 */       if (resultSet.next())
/*      */       {
/* 3066 */         definitionId = resultSet.getInt(1);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3071 */       System.err.println(sqle);
/* 3072 */       sqle.printStackTrace();
/* 3073 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" XMLFormDefinition").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3077 */       closeResultSet(resultSet);
/* 3078 */       closeStatement(stmt);
/* 3079 */       closeConnection();
/*      */     }
/*      */ 
/* 3082 */     return definitionId;
/*      */   }
/*      */ 
/*      */   public int getMaxDepthForHierarchy(int hierarchyId)
/*      */   {
/* 3087 */     PreparedStatement stmt = null;
/* 3088 */     ResultSet resultSet = null;
/* 3089 */     int maxDepth = 0;
/*      */     try
/*      */     {
/* 3092 */       stmt = getConnection().prepareStatement(SQL_HIERARCHY_MAX_DEPTH);
/* 3093 */       int index = 1;
/* 3094 */       stmt.setInt(index++, hierarchyId);
/* 3095 */       resultSet = stmt.executeQuery();
/* 3096 */       if (resultSet.next())
/*      */       {
/* 3098 */         maxDepth = 1 + resultSet.getInt(1);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3103 */       System.err.println(sqle);
/* 3104 */       sqle.printStackTrace();
/* 3105 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getMaxDepthForHierarchy").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3109 */       closeResultSet(resultSet);
/* 3110 */       closeStatement(stmt);
/* 3111 */       closeConnection();
/*      */     }
/*      */ 
/* 3114 */     return maxDepth;
/*      */   }
/*      */ 
/*      */   public String[] getXMLFormConfig(int formId)
/*      */   {
/* 3120 */     PreparedStatement stmt = null;
/* 3121 */     ResultSet resultSet = null;
/* 3122 */     String definition = null;
/*      */     try
/*      */     {
			    stmt = this.getConnection().prepareStatement(SQL_XML_FORM_FROM_FORM_ID);
			    byte sqle = 1;
			    byte var10001 = sqle;
			    int sqle1 = sqle + 1;
			    stmt.setInt(var10001, formId);
			    resultSet = stmt.executeQuery();
			    if(resultSet.next()) {
			       String visId = resultSet.getString(1);
			       Clob clob = resultSet.getClob(2);
			       BufferedReader br = null;
			
			       try {
			          br = new BufferedReader(clob.getCharacterStream());
			          char[] e = new char[(int)clob.length()];
			          br.read(e);
			          definition = new String(e);
			          br.close();
			       } catch (Exception var15) {
			          var15.printStackTrace();
			       }
			
			       String[] e1 = new String[]{visId, definition};
			       return e1;
			    }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3152 */       System.err.println(sqle);
/* 3153 */       sqle.printStackTrace();
/* 3154 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" XMLFormDefinition").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3158 */       closeResultSet(resultSet);
/* 3159 */       closeStatement(stmt);
/* 3160 */       closeConnection();
/*      */     }
/* 3162 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean getXMLFormReadOnlySecurityFlag(int formId)
/*      */   {
/* 3167 */     PreparedStatement stmt = null;
/* 3168 */     ResultSet resultSet = null;
/* 3169 */     boolean securityAccess = false;
/*      */     try
/*      */     {
/* 3173 */       stmt = getConnection().prepareStatement(SQL_XML_FORM_READONLY_SECURITY_FROM_FORM_ID);
/* 3174 */       int col1 = 1;
/* 3175 */       stmt.setInt(col1++, formId);
/* 3176 */       resultSet = stmt.executeQuery();
/* 3177 */       if (resultSet.next())
/*      */       {
/* 3179 */         String val = resultSet.getString(1);
/* 3180 */         if ((val != null) && (val.equals("Y")))
/* 3181 */           securityAccess = true;
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3186 */       System.err.println(sqle);
/* 3187 */       sqle.printStackTrace();
/* 3188 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getXMLFormReadOnlySecurityFlag").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3192 */       closeResultSet(resultSet);
/* 3193 */       closeStatement(stmt);
/* 3194 */       closeConnection();
/*      */     }
/*      */ 
/* 3197 */     return securityAccess;
/*      */   }
/*      */ 
/*      */   public FormDataInputModel getCalculationPreviousData(FormConfig formConfig, int financeCubeId, int cellCalcShortId)
/*      */   {
/* 3203 */     PreparedStatement stmt = null;
/* 3204 */     ResultSet resultSet = null;
/* 3205 */     DefaultFormDataInputModel result = null;
/*      */     try
/*      */     {
/* 3208 */       result = new DefaultFormDataInputModel(formConfig);
/*      */ 
/* 3210 */       List rowData = new ArrayList();
/* 3211 */       int lastRow = -1;
/* 3212 */       Map rowMapping = null;
/*      */ 
/* 3214 */       stmt = getConnection().prepareStatement(SQL_CELL_CALCULATION_DATA);
/* 3215 */       int col1 = 1;
/* 3216 */       stmt.setInt(col1++, financeCubeId);
/* 3217 */       stmt.setInt(col1++, cellCalcShortId);
/* 3218 */       resultSet = stmt.executeQuery();
/* 3219 */       while (resultSet.next())
/*      */       {
/* 3221 */         int index = 1;
/* 3222 */         int row = resultSet.getInt(index++);
/* 3223 */         String colId = resultSet.getString(index++);
/* 3224 */         Object numericValue = resultSet.getObject(index++);
/* 3225 */         String stringValue = resultSet.getString(index++);
/*      */ 
/* 3228 */         if (row != lastRow)
/*      */         {
/* 3230 */           rowMapping = new HashMap();
/* 3231 */           rowData.add(rowMapping);
/* 3232 */           lastRow = row;
/*      */         }
/*      */ 
/* 3235 */         Column c = result.getColumnMapping(colId);
/* 3236 */         if (c != null)
/*      */         {
/* 3238 */           if (c.getType().equals("string"))
/* 3239 */             rowMapping.put(colId, stringValue);
/*      */           else {
/* 3241 */             rowMapping.put(colId, numericValue);
/*      */           }
/*      */         }
/*      */       }
/* 3245 */       result.setData(rowData);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3249 */       System.err.println(sqle);
/* 3250 */       sqle.printStackTrace();
/* 3251 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" XMLFormDefinition").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3255 */       closeResultSet(resultSet);
/* 3256 */       closeStatement(stmt);
/* 3257 */       closeConnection();
/*      */     }
/*      */ 
/* 3260 */     return result;
/*      */   }
/*      */ 
/*      */   public List<Integer> getCellCalcShortIdsForRA(int financeCubeId, int cellCalcDeploymentId, int budgetLocation)
/*      */   {
/* 3265 */     List result = new ArrayList();
/* 3266 */     PreparedStatement stmt = null;
/* 3267 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 3270 */       StringBuilder sql = new StringBuilder("select distinct short_id from CFT");
/* 3271 */       sql.append(financeCubeId);
/* 3272 */       sql.append(" where cell_calc_id = ? and dim0 = ?");
/* 3273 */       stmt = getConnection().prepareStatement(sql.toString());
/* 3274 */       stmt.setInt(1, cellCalcDeploymentId);
/* 3275 */       stmt.setInt(2, budgetLocation);
/* 3276 */       resultSet = stmt.executeQuery();
/* 3277 */       while (resultSet.next())
/*      */       {
/* 3279 */         int shortId = resultSet.getInt(1);
/* 3280 */         result.add(Integer.valueOf(shortId));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3285 */       System.err.println(sqle);
/* 3286 */       sqle.printStackTrace();
/* 3287 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCellCalcShortIdsForRA").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3291 */       closeResultSet(resultSet);
/* 3292 */       closeStatement(stmt);
/* 3293 */       closeConnection();
/*      */     }
/*      */ 
/* 3296 */     return result;
/*      */   }
/*      */ 
/*      */   public List<Integer> getCellCalcShortIdsForRA(int financeCubeId, int cellCalcDeploymentId, int budgetLocation, List<Integer> calIds)
/*      */   {
/* 3301 */     int calDim = getNumberofDims(financeCubeId) - 1;
/*      */ 
/* 3303 */     List result = new ArrayList();
/* 3304 */     PreparedStatement stmt = null;
/* 3305 */     ResultSet resultSet = null;
/*      */     try
/*      */     {
/* 3308 */       StringBuilder sql = new StringBuilder("select distinct short_id from CFT");
/* 3309 */       sql.append(financeCubeId);
/* 3310 */       sql.append(" where cell_calc_id = ? and dim0 = ?");
/* 3311 */       sql.append(" and dim").append(calDim).append(" in (");
/* 3312 */       for (int i = 0; i < calIds.size(); i++)
/*      */       {
/* 3314 */         if (i > 0)
/* 3315 */           sql.append(",");
/* 3316 */         sql.append("?");
/*      */       }
/* 3318 */       sql.append(")");
/* 3319 */       stmt = getConnection().prepareStatement(sql.toString());
/* 3320 */       int index = 1;
/* 3321 */       stmt.setInt(index++, cellCalcDeploymentId);
/* 3322 */       stmt.setInt(index++, budgetLocation);
/* 3323 */       for (Iterator i$ = calIds.iterator(); i$.hasNext(); ) { int calStrucId = ((Integer)i$.next()).intValue();
/* 3324 */         stmt.setInt(index++, calStrucId); }
/* 3325 */       resultSet = stmt.executeQuery();
/* 3326 */       while (resultSet.next())
/*      */       {
/* 3328 */         int shortId = resultSet.getInt(1);
/* 3329 */         result.add(Integer.valueOf(shortId));
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3334 */       System.err.println(sqle);
/* 3335 */       sqle.printStackTrace();
/* 3336 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCellCalcShortIdsForRA").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3340 */       closeResultSet(resultSet);
/* 3341 */       closeStatement(stmt);
/* 3342 */       closeConnection();
/*      */     }
/*      */ 
/* 3345 */     return result;
/*      */   }
/*      */ 
/*      */   private int getNumberofDims(int financeCubeId)
/*      */   {
/* 3350 */     PreparedStatement stmt = null;
/* 3351 */     ResultSet resultSet = null;
/* 3352 */     String sql = "select count(*) from model_dimension_rel mdr\n  where mdr.model_id = (select model_id from finance_cube where finance_cube_id = ?)";
/*      */ 
/* 3355 */     int result = 3;
/*      */     try
/*      */     {
/* 3358 */       stmt = getConnection().prepareStatement(sql);
/* 3359 */       stmt.setInt(1, financeCubeId);
/* 3360 */       resultSet = stmt.executeQuery();
/*      */ 
/* 3362 */       resultSet.next();
/* 3363 */       result = resultSet.getInt(1);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3367 */       sqle.printStackTrace();
/* 3368 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getNumberofDims").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3372 */       closeResultSet(resultSet);
/* 3373 */       closeStatement(stmt);
/* 3374 */       closeConnection();
/*      */     }
/*      */ 
/* 3377 */     return result;
/*      */   }
/*      */ 
/*      */   public List<Object> getCellCalculationContext(int financeCubeId, int cellCalcShortId)
/*      */   {
/* 3382 */     List result = new ArrayList();
/* 3383 */     PreparedStatement stmt = null;
/* 3384 */     ResultSet resultSet = null;
/*      */ 
/* 3386 */     int noOfDims = getNumberofDims(financeCubeId);
/*      */     try
/*      */     {
/* 3389 */       StringBuilder sql = new StringBuilder("select distinct ");
/* 3390 */       for (int i = 0; i < noOfDims - 1; i++)
/*      */       {
/* 3392 */         if (i > 0)
/* 3393 */           sql.append(',');
/* 3394 */         sql.append("c.dim").append(i);
/* 3395 */         sql.append(", s").append(i).append(".vis_id, s").append(i).append(".description");
/*      */       }
/* 3397 */       int lastId = noOfDims - 1;
/* 3398 */       sql.append(", c.dim").append(lastId);
/* 3399 */       sql.append(", s").append(lastId).append(".cal_vis_id_prefix||s").append(lastId).append(".vis_id as s").append(lastId).append("vis_id, s").append(lastId).append(".description");
/* 3400 */       sql.append(" from cft").append(financeCubeId).append(" c");
/* 3401 */       for (int i = 0; i < noOfDims; i++)
/*      */       {
/* 3403 */         sql.append(", structure_element s").append(i);
/*      */       }
/* 3405 */       sql.append(" where c.short_id = ?");
/* 3406 */       for (int i = 0; i < noOfDims; i++)
/*      */       {
/* 3408 */         sql.append(" and c.dim").append(i).append(" = s").append(i).append(".structure_element_id");
/*      */       }
/* 3410 */       stmt = getConnection().prepareStatement(sql.toString());
/* 3411 */       stmt.setInt(1, cellCalcShortId);
/* 3412 */       resultSet = stmt.executeQuery();
/* 3413 */       if (resultSet.next())
/*      */       {
/* 3415 */         int index = 1;
/* 3416 */         for (int i = 0; i < noOfDims; i++)
/*      */         {
/* 3418 */           result.add(Integer.valueOf(resultSet.getInt(index++)));
/* 3419 */           result.add(resultSet.getString(index++));
/* 3420 */           result.add(resultSet.getString(index++));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3426 */       System.err.println(sqle);
/* 3427 */       sqle.printStackTrace();
/* 3428 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" getCellCalculationContext").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3432 */       closeResultSet(resultSet);
/* 3433 */       closeStatement(stmt);
/* 3434 */       closeConnection();
/*      */     }
/*      */ 
/* 3437 */     return result;
/*      */   }
/*      */ 
/*      */   public Map getCalculationSummaryData(FormConfig formConfig, int financeCubeId, int cellCalcShortId)
/*      */   {
/* 3443 */     PreparedStatement stmt = null;
/* 3444 */     ResultSet resultSet = null;
/* 3445 */     Map result = null;
/*      */     try
/*      */     {
/* 3448 */       result = new HashMap();
/*      */ 
/* 3450 */       stmt = getConnection().prepareStatement(SQL_CELL_CALCULATION_SUMMARY_DATA);
/* 3451 */       int col1 = 1;
/* 3452 */       stmt.setInt(col1++, financeCubeId);
/* 3453 */       stmt.setInt(col1++, cellCalcShortId);
/* 3454 */       resultSet = stmt.executeQuery();
/* 3455 */       while (resultSet.next())
/*      */       {
/* 3457 */         int index = 1;
/* 3458 */         String colId = resultSet.getString(index++);
/* 3459 */         Object numericValue = resultSet.getObject(index++);
/*      */ 
/* 3462 */         result.put(colId, numericValue);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 3467 */       System.err.println(sqle);
/* 3468 */       sqle.printStackTrace();
/* 3469 */       throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" XMLFormDefinition").toString(), sqle);
/*      */     }
/*      */     finally
/*      */     {
/* 3473 */       closeResultSet(resultSet);
/* 3474 */       closeStatement(stmt);
/* 3475 */       closeConnection();
/*      */     }
/*      */ 
/* 3478 */     return result;
/*      */   }
/*      */ 
/*      */   public String validateMassUpdate(int financeCubeId, int srcCalElem, int targetCalElem)
/*      */   {
/* 3483 */     Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
/*      */ 
/* 3485 */     CallableStatement stmt = null;
/* 3486 */     String result = "";
/*      */     try
/*      */     {
/* 3489 */       stmt = getConnection().prepareCall("begin mass_update.validateCalendarMapping(?,?,?,?); end;");
/*      */ 
/* 3491 */       stmt.setInt(1, financeCubeId);
/* 3492 */       stmt.setInt(2, srcCalElem);
/* 3493 */       stmt.setInt(3, targetCalElem);
/* 3494 */       stmt.registerOutParameter(4, 12);
/*      */ 
/* 3496 */       stmt.execute();
/*      */ 
/* 3498 */       result = stmt.getString(4);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 3502 */       e.printStackTrace();
/* 3503 */       throw new RuntimeException(e);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3507 */       e.printStackTrace();
/* 3508 */       throw new RuntimeException(e);
/*      */     }
/*      */     finally
/*      */     {
/* 3512 */       if (timer != null)
/*      */       {
/* 3514 */         timer.logDebug("validateCalendarMapping");
/*      */       }
/* 3516 */       closeStatement(stmt);
/* 3517 */       closeConnection();
/*      */     }
/*      */ 
/* 3520 */     return result;
/*      */   }
/*      */ 
/*      */   public String getEntityName()
/*      */   {
/* 3525 */     return "DataEntry";
/*      */   }

			/**
			 * For DataEditor
			 * @param financeCubeId
			 * @param srcCalElem
			 * @param targetCalElem
			 * @return
			 */
			public List<Object[]> getDataEditorData(List<Integer> fcIds, Object[] costCenters, Object[] expenseCodes, List<String> dataTypes, int fromYear, int fromPeriod, int toYear, int toPeriod) {
				Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
				
				ArrayList<Object[]> allResults = new ArrayList<Object[]>();
				
				// Object[] to one String
				String allExpenseCodes = "", allCostCenters = "";
				for (Object expense : expenseCodes) {
				    allExpenseCodes += "'" + expense + "', ";
				}
				allExpenseCodes = allExpenseCodes.substring(0, allExpenseCodes.length() - 2);
				
                for (Object cc : costCenters) {
                    allCostCenters += "'" + cc + "', ";
                }
                allCostCenters = allCostCenters.substring(0, allCostCenters.length() - 2);
                
				// for each dataType doing sql :(
				if (dataTypes.size() > 0) {
					for (String dataType : dataTypes) {
						
						String calendarCondition = "";
						if (toYear - fromYear > 0) {
							// 5/2010 - 10/2013
							// where (year = 2010 and period >= 5) or (year > 2010 and year < 2013) or (year = 2013 and period <= 10)
							calendarCondition = " WHERE (YEAR = " + fromYear + " AND PERIOD >= " + fromPeriod + ") or (YEAR > " + fromYear + " AND YEAR < " + toYear + ") or (YEAR = " + toYear + " AND PERIOD <= " + toPeriod + ")";
						} else {
							// 1/2014 - 2/2014
							// where (year = 2014 and period >= 1) or (year = 2014 and period <= 2)
							calendarCondition = " WHERE (YEAR = " + fromYear + " AND PERIOD >= " + fromPeriod + " AND PERIOD <= " + toPeriod + ")";
						}
						String costCenterConditon = !costCenters[0].equals("All") ? "          de0.VIS_ID IN (" + allCostCenters + ")\n" : "";
						String expenseCodeConditon = !expenseCodes[0].equals("All") ? "          de1.VIS_ID IN (" + allExpenseCodes + ")\n" : "";
						String dimensionCondition = "";
						if (costCenterConditon.length() > 0 && expenseCodeConditon.length() > 0) {
							dimensionCondition = "WHERE\n" + costCenterConditon + "and" + expenseCodeConditon;
						} else if (costCenterConditon.length() > 0) {
							dimensionCondition = "WHERE\n" + costCenterConditon;
						} else if (expenseCodeConditon.length() > 0) {
							dimensionCondition = "WHERE\n" + expenseCodeConditon;
						}
						
						String valueGetter = "";
						int divisor = 1;
						boolean isDataTypeNumeric = false;
						DataTypeDAO dao = new DataTypeDAO();
						for (Object[] currentDataType : dao.getAllDataTypes().getDataAsArray()) {
							if (((DataTypeRefImpl) currentDataType[0]).getNarrative().equals(dataType)) {
								if (((DataTypeRefImpl) currentDataType[0]).getMeasureClass() == 1 && ((DataTypeRefImpl) currentDataType[0]).getSubType() == 4) {
									isDataTypeNumeric = true;
									// Measure Numeric
									Integer scale = ((DataTypeRefImpl) currentDataType[0]).getMeasureScale();
									if (scale != null) {
										divisor = 10^scale;
									} 							
									valueGetter = "  ,sum(decode(DATA_TYPE,'" + dataType + "',NUMBER_VALUE / " + divisor + ",null)) DATA_TYPE  \n";
								} else if (((DataTypeRefImpl) currentDataType[0]).getMeasureClass() == 0 && ((DataTypeRefImpl) currentDataType[0]).getSubType() == 4) {
									isDataTypeNumeric = false;
									valueGetter = "  ,max(decode(DATA_TYPE,'" + dataType + "',substr(STRING_VALUE,1," + ((DataTypeRefImpl) currentDataType[0]).getMeasureLength() + "),null)) DATA_TYPE  \n";
								} else {
									// Financial Value
									valueGetter = "  ,sum(decode(DATA_TYPE,'" + dataType + "',NUMBER_VALUE / 10000,null)) DATA_TYPE  \n";
								}
							}	
						}
						String forEachFinanceCube = forEachIn(fcIds);
						
						StringBuilder subSelect = new StringBuilder();
						for (Integer financeCubeId : fcIds) {
                            subSelect.append(
                                "      select " + financeCubeId + " as FINANCE_CUBE_ID, DIM0,DIM1,DIM2, DATA_TYPE\n" + 
                                "      ,to_number(to_char(NUMBER_VALUE / DIVISOR,'999999999999999999')) NUMBER_VALUE\n" + 
                                "      , null STRING_VALUE, null DATE_VALUE\n" + 
                                "      ,YEAR,PERIOD\n" + 
                                "      from    DFT" + financeCubeId + "\n" + 
                                "      join    getPeriods using (DIM2)\n" + calendarCondition +
                                "      \n" + 
                                "      union   all\n" + 
                                "      \n" + 
                                "      select  distinct " + financeCubeId + " as FINANCE_CUBE_ID, DIM0,DIM1,DIM2, DATA_TYPE\n" + 
                                "      ,0 NUMBER_VALUE, null STRING_VALUE, null DATE_VALUE\n" + 
                                "      ,YEAR,PERIOD\n" + 
                                "      from    SFT" + financeCubeId + "\n" + 
                                "      join    getPeriods using (DIM2)\n" + calendarCondition);
                            subSelect.append(" union all ");
                        }
						subSelect.delete(subSelect.length() - 11, subSelect.length());
						
						String sql = "with getPeriods as\n" + 
								"(\n" + 
								"  select  YEAR, PERIOD, DIM2, nvl(DIVISOR,1) DIVISOR\n" + 
								"  from\n" + 
								"  /* get calendar year and period names */\n" + 
								"  (\n" + 
								"    select  VIS_ID as CAL_PERIOD\n" + 
								"    ,STRUCTURE_ELEMENT_ID as DIM2\n" + 
								"    from    STRUCTURE_ELEMENT se\n" + 
								"  ) p\n" + 
								"  left\n" + 
								"  join\n" + 
								"  /* get mapped periods (might not be mapped) */\n" + 
								"  /* works out divisor in case CP is 1 to many against FMS*/\n" + 
								"  (\n" + 
								"    select  YEAR,PERIOD,DIMENSION_ELEMENT_ID as DIM2, DIVISOR\n" + 
								"    from    (\n" + 
								"        select YEAR,PERIOD, DIMENSION_ELEMENT_ID\n" + 
								"        from    MAPPED_FINANCE_CUBE\n" + 
								"        join    MAPPED_CALENDAR_YEAR\n" + 
								"        using (MAPPED_MODEL_ID)\n" + 
								"        join    MAPPED_CALENDAR_ELEMENT\n" + 
								"        using (MAPPED_CALENDAR_YEAR_ID)\n" + 
								"        where   FINANCE_CUBE_ID IN (" + forEachFinanceCube + ")\n" + 
								"        and     PERIOD not in (0,999)\n" + 
								"    )\n" + 
								"    join    (\n" + 
								"        select DIMENSION_ELEMENT_ID, count(*) as DIVISOR\n" + 
								"        from    (\n" + 
								"            select YEAR,PERIOD, DIMENSION_ELEMENT_ID\n" + 
								"            from    MAPPED_FINANCE_CUBE\n" + 
								"            join    MAPPED_CALENDAR_YEAR\n" + 
								"            using (MAPPED_MODEL_ID)\n" + 
								"            join    MAPPED_CALENDAR_ELEMENT\n" + 
								"            using (MAPPED_CALENDAR_YEAR_ID)\n" + 
								"            where   FINANCE_CUBE_ID IN (" + forEachFinanceCube + ")\n" + 
								"            and     PERIOD not in (0,999)\n" +
								"        )\n" + 
								"        group by DIMENSION_ELEMENT_ID\n" + 
								"    )\n" + 
								"    using (DIMENSION_ELEMENT_ID)\n" + 
								"  ) c using (DIM2)\n" + 
								"  order by YEAR,PERIOD, CAL_PERIOD\n" + 
								")\n" + 
								"\n" + 
								",getValueRows as\n" + 
								"/* now get the values */\n" + 
								"(\n" + 
								"  select FINANCE_CUBE_ID, de0.VIS_ID as COST_CENTRE\n" + 
								"  ,de1.VIS_ID as EXPENSE_CODE\n" + 
								"  ,YEAR, PERIOD  \n" + 
								valueGetter + 
								"  ,DIM0,DIM1,DIM2\n" + 
								"  from    (\n" + 
								    subSelect +
								"  )\n" + 
								"  join    DIMENSION_ELEMENT de0 on (de0.DIMENSION_ELEMENT_ID = DIM0)\n" + 
								"  join    DIMENSION_ELEMENT de1 on (de1.DIMENSION_ELEMENT_ID = DIM1)\n" + dimensionCondition + 
								"  group   by  FINANCE_CUBE_ID, de0.VIS_ID,de1.VIS_ID,YEAR,PERIOD,DIM0,DIM1,DIM2\n" + 
								")\n" + 
								"select FINANCE_CUBE_ID, fc.vis_id, fc.model_id, COST_CENTRE,DIM0, EXPENSE_CODE, DIM1\n" + 
								",YEAR, PERIOD\n" + 
								",DATA_TYPE\n" + 
								",DIM2\n" + 
								"from getValueRows\n" + 
								"natural join finance_cube fc\n" + 
								"order by fc.vis_id, COST_CENTRE, EXPENSE_CODE";
						CallableStatement stmt = null;
						ArrayList<Object[]> result = new ArrayList<Object[]>();
						try {
							stmt = getConnection().prepareCall(sql);
							ResultSet resultSet = stmt.executeQuery();
							while (resultSet.next()) {
								int index = 1;
								int financeCubeId = resultSet.getInt(index++);
								String financeCubeVisId = resultSet.getString(index++);
								int modelId = resultSet.getInt(index++);
								String costCentre = resultSet.getString(index++);
								int dim0 = resultSet.getInt(index++);
								String expenseCode = resultSet.getString(index++);
								int dim1 = resultSet.getInt(index++);
								int year = resultSet.getInt(index++);
								int period = resultSet.getInt(index++);
								String value = "";
								if (!isDataTypeNumeric) {
									value = resultSet.getString(index++);
								} else {
									value = String.valueOf(resultSet.getInt(index++));
								}
								int dim2 = resultSet.getInt(index++);
								Object[] row = new Object[10];
								int i = 0;
								
								ModelPK modelPK = new ModelPK(modelId);
								FinanceCubePK fcPk= new FinanceCubePK(financeCubeId);
								FinanceCubeCK fcCK = new FinanceCubeCK(modelPK, fcPk);
								FinanceCubeRefImpl fcRef = new FinanceCubeRefImpl(fcCK, financeCubeVisId);
								
								row[i++] = fcRef;
								row[i++] = costCentre;
								row[i++] = dim0;
								row[i++] = expenseCode;
								row[i++] = dim1;
								row[i++] = year;
								row[i++] = period;
								row[i++] = dataType;
								row[i++] = value;
								row[i++] = dim2;
								
								result.add(row);
				
							}
						} catch (SQLException e) {
							throw new RuntimeException(e);
						} catch (Exception e) {
							throw new RuntimeException(e);
						} finally {
							if (timer != null) {
								timer.logDebug("getDataEditorData");
							}
							closeStatement(stmt);
							closeConnection();
						}
						allResults.addAll(result);
					}
				}
				return allResults;
			}
			
	private String forEachIn(List<Integer> list) {
	    StringBuilder result = new StringBuilder();
	    for (Integer integer: list) {
            result.append(integer);
            result.append(", ");
        }
	    result.delete(result.length() - 2, result.length());
	    return result.toString();
	}

	public void insertCellNote(CellNote cellNote, int financeCubeId, int userId, int budgetCycleId) throws ValidationException {
		PreparedStatement stmt = null;

		try {			
			String sql;
			if (cellNote.getCompany() == null) {
				sql = "insert into sft" + financeCubeId + " ( dim0, dim1, dim2, data_type, seq, created, user_id, user_name, string_value, link_id, link_type, budget_cycle_id, data_entry_profile_id) values( ?, ?, ?, ?, SFTSEQ" + financeCubeId + ".nextval,to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF'), ?, ?, ?, ?, ?, ?, ?)";
			} else {
				sql = "insert into sft" + financeCubeId + " ( dim0, dim1, dim2, data_type, seq, created, user_id, user_name, string_value, link_id, link_type, budget_cycle_id, data_entry_profile_id, company) values( ?, ?, ?, ?, SFTSEQ" + financeCubeId + ".nextval,to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF'), ?, ?, ?, ?, ?, ?, ?, ?)";
			}
			this.mLog.debug("insertCellNotes", sql);
			stmt = this.getConnection().prepareStatement(sql);

			StringTokenizer st = new StringTokenizer(cellNote.getAddr(), ",");
			int[] address = new int[3];

			int index;
			for (index = 0; index < 3; ++index) {
				address[index] = Integer.parseInt(st.nextToken());
			}

			index = 1;
			for (int i = 0; i < 3; ++i) {
				stmt.setInt(index++, address[i]);
			}

			String note = cellNote.getNote() != null && cellNote.getNote().length() != 0 ? cellNote.getNote() : "";
			stmt.setString(index++, cellNote.getDataType());
			stmt.setString(index++, cellNote.getTimestamp());
			stmt.setInt(index++, userId);
			stmt.setString(index++, cellNote.getUserName());
			stmt.setString(index++, note);
			stmt.setInt(index++, cellNote.getLinkId());
			stmt.setInt(index++, cellNote.getLinkType());
			stmt.setInt(index++, budgetCycleId);
			stmt.setInt(index++, cellNote.getDataEntryProfileId());
			if (cellNote.getCompany() != null) {
				stmt.setString(index, cellNote.getCompany());
			}
			this.mLog.debug("insertCellNotes", cellNote.getAddr() + "," + cellNote.getDataType() + "," + userId + "," + cellNote.getUserName());
			int result = stmt.executeUpdate();
			
			if (result != 1 && result != -2) {
				throw new IllegalStateException("Failed to insert cell note.");
			}
		} catch (SQLException e) {
			throw handleSQLException("Failed to delete cell note.", e);
		} finally {
			this.closeStatement(stmt);
			this.closeConnection();
		}
	}

	public void updateCellNote(CellNote cellNote, int financeCubeId) throws ValidationException {
		PreparedStatement stmt = null;

		try {
			String sql = "update sft" + financeCubeId + " set string_value = ?, link_id = ?, link_type = ? where created = to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF')";
			this.mLog.debug("updateCellNotes", sql);
			stmt = this.getConnection().prepareStatement(sql);

			int index = 1;
			String note = cellNote.getNote() != null && cellNote.getNote().length() != 0 ? cellNote.getNote() : "";

			stmt.setString(index++, note);
			stmt.setInt(index++, cellNote.getLinkId());
			stmt.setInt(index++, cellNote.getLinkType());
			stmt.setString(index++, cellNote.getTimestamp());
			this.mLog.debug("updateCellNotes", cellNote.getAddr() + "," + cellNote.getDataType() + "," + cellNote.getUserName());

			int result = stmt.executeUpdate();
			if (result != 1 && result != -2) {
				throw new IllegalStateException("Failed to update cell note.");
			}

		} catch (SQLException e) {
			throw handleSQLException("Error in SQL to update the note", e);
		} finally {
			this.closeStatement(stmt);
			this.closeConnection();
		}
	}

	public void deleteCellNote(CellNote cellNote, int financeCubeId) throws ValidationException {
		PreparedStatement stmt = null;

		try {
			String sql = "delete from sft" + financeCubeId + " where created = to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF')";
			this.mLog.debug("deleteCellNotes", sql);

			stmt = this.getConnection().prepareStatement(sql);
			String note = cellNote.getNote() != null && cellNote.getNote().length() != 0 ? cellNote.getNote() : "";
			stmt.setString(1, cellNote.getTimestamp());
			this.mLog.debug("deleteCellNotes", cellNote.getAddr() + "," + cellNote.getDataType() + "," + cellNote.getUserName());

			int result = stmt.executeUpdate();
			if (result != 1 && result != -2) {
				throw new IllegalStateException("Failed execute query to delete cell note.");
			}
		} catch (SQLException e) {
			throw handleSQLException("Failed to delete cell note.", e);
		} finally {
			this.closeStatement(stmt);
			this.closeConnection();
		}
	}
	
	 private static DictionaryPropertiesDTO fromJSON(String properties) {
	        ObjectMapper mapper = new ObjectMapper();
	        if(properties == null || properties.equals("")) {
	            properties = "{\"currency\":\"none\",\"precision\":4}";
	        }
	        
	        DictionaryPropertiesDTO p = new DictionaryPropertiesDTO();
	        try {
	            // read from string, convert it to DictionaryPropertiesDTO class
	           p = mapper.readValue(properties, DictionaryPropertiesDTO.class);
	            
	        } catch (JsonGenerationException e) {
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return p;
	    }
}

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.dataentry.DataEntryDAO
 * JD-Core Version:    0.6.0
 */