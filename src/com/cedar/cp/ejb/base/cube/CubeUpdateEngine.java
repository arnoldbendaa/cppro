// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ModelLockedException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeDimensionsAndHierachiesELO;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.cube.Cell;
import com.cedar.cp.ejb.base.cube.CellCalc;
import com.cedar.cp.ejb.base.cube.CellCalcLink;
import com.cedar.cp.ejb.base.cube.CellNote;
import com.cedar.cp.ejb.base.cube.CellPosting;
import com.cedar.cp.ejb.base.cube.CellPostingXMLReader;
import com.cedar.cp.ejb.base.cube.CubeUpdate;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine$1;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine$2;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine$3;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine$4;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine$5;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine$6;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine$ActivityWriter;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine$DataTypeDetails;
import com.cedar.cp.ejb.base.cube.FormNote;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesDAO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesEVO;
import com.cedar.cp.ejb.impl.model.CellCalculationDataDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Log;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.sql.DataSource;

public class CubeUpdateEngine extends AbstractDAO {

   private static final String INSERT_BUDGET_ACTIVITY_LINK_SQL = "insert into budget_activity_link \tselect distinct ?, dim{resp_area_dim_index}, ? \tfrom  {temp_table_name} tft, structure_element se    where tft.dim{resp_area_dim_index} = se.structure_element_id and\t\t  se.leaf = \'Y\' ";
   private static final String TRANSACTION_UPDATE_SQL = "begin cp_utils.setTaskId(?);cube_update.transaction_update( ?, ?, ?, ?, ? ,? );end; ";
   private static final String MERGE_CELL_CALC_LINKS_SQL = " merge into {cft_table_name} cft using \n ( \n    select {outer_dim_cols_to_select} data_type, max(short_id) short_id, max(cell_calc_id) cell_calc_id \n    from cell_calc_tran \n    group by {outer_dim_cols_to_select} data_type \n    order by {outer_dim_cols_to_select} data_type \n ) delta \n on \n ( \n   {merge_join_clause} \n   cft.data_type = delta.data_type \n ) \n when matched then \n      update set cft.short_id = delta.short_id, cft.cell_calc_id = delta.cell_calc_id \n when not matched then \n      insert ( {insert_cols_clause}       data_type,       short_id,       cell_calc_id ) \n      values ( {insert_vals_clause} delta.data_type, delta.short_id, delta.cell_calc_id ) ";
   private static String INSERT_CELL_CALC_LINKS_INTO_GLOBAL_TEMPORARY = "insert into cell_calc_tran ( {dim_cols} data_type, short_id, cell_calc_id ) values( {dim_cols_bind_vars} ?, ?, ? )";
   private static final String INSERT_CELL_NOTES_SQL = "insert into {string_table_name} ( {dim_cols} data_type, seq, created, user_id, user_name, string_value, link_id, link_type ) values( {dim_cols_bind_vars} ?,{sft_sequence}.nextval,systimestamp, ?, ?, ?, ?, ? )";
   private static String INSERT_INTO_GLOBAL_TEMPORARY_COMPANY = "insert into {temp_table_name} ( {dim_cols} data_type, number_value, string_value, company, date_value ) values( {dim_cols_bind_vars} ?, ?, ?, ?, ? )";
   private static String INSERT_INTO_GLOBAL_TEMPORARY = "insert into {temp_table_name} ( {dim_cols} data_type, number_value, string_value, date_value ) values( {dim_cols_bind_vars} ?, ?, ?, ? )";
   private static final String LOOKUP_SE_SQL = "select ids.seid, se.vis_id, se.description\nfrom \n  ( select distinct seid from \n    ( \n\t   {id_select_statement} \n    ) \n  ) ids, \n  structure_element se \nwhere ids.seid = se.structure_element_id ";
   private NumberFormat mActvityNumberFormatter;
   private NumberFormat mNumberFormatter;
   private DateFormat mTimeFormatter;
   private DateFormat mDateFormatter;
   private DateFormat mDateTimeFormatter;
   private CubeUpdateEngine$ActivityWriter mActivityWriter;
   private UserEVO mUserEVO;
   private FinanceCubeCK mFinanceCubeCK;
   private ModelEVO mModelEVO;
   private FinanceCubeEVO mFinanceCubeEVO;
   private int mUserId;
   private int mFinanceCubeId;
   private FinanceCubeDimensionsAndHierachiesELO mDimensionsAndHierachiesELO;
   private int mNumDims;
   private transient Log mLog = new Log(this.getClass());
   private Integer mTaskId;
   private UserDAO mUserDAO;
   private ModelDAO mModelDAO;
   private DataTypeDAO mDataTypeDAO;
   private FormNotesDAO mFormNotesDAO;
   private FinanceCubeDAO mFinanceCubeDAO;
   private VirementRequestDAO mVirementRequestDAO;
   private CellCalculationDataDAO mCellCalculationDataDAO;
   private int mActivityUpdateTypeId = -1;
   private boolean isGlobalModel = true;
   private int mBudgetCycleId;
   private boolean invertNumber;
   private String[] excludedDataTypes = null;


   public CubeUpdateEngine(Connection connection) {
      super(connection);
   }

   public CubeUpdateEngine(DataSource ds) {
      super(ds);
   }

   public CubeUpdateEngine() {}

   public String getEntityName() {
      return "CubeUpdateEngine";
   }

   public void updateCube(Reader xmlStream) throws Exception {
      this.updateCube(xmlStream, false);
   }

   public void updateCube(Reader xmlStream, boolean clearTempTable) throws Exception {
      long start = System.currentTimeMillis();
      CellPostingXMLReader reader = this.parseXMLDocument(xmlStream);
      CubeUpdate cubeUpdate = reader.getCubeUpdate();
      cubeUpdate.setActivityUpdateTypeId(this.getActivityUpdateTypeId());
      this.invertNumber = cubeUpdate.isInvertNumbers();
      if (cubeUpdate.getExcludedDataTypes() != null) {
          this.excludedDataTypes = cubeUpdate.getExcludedDataTypes().split(",");
      }
      this.mFinanceCubeId = cubeUpdate.getFinanceCubeId();
      this.mBudgetCycleId = cubeUpdate.getBudgetCycleId();
      this.queryDimensionAndHierarchyInfo();
      this.mUserId = cubeUpdate.getUserId();
      long end = System.currentTimeMillis();
      this.mLog.info("updateCube", "Parsed XML document " + cubeUpdate.getCellCount() + " cells in " + (end - start) + " milliseconds");
      this.updateCube(cubeUpdate, true, clearTempTable);
   }

   public void updateCube(CubeUpdate cubeUpdate, boolean createBudgetActivity) throws Exception {
      this.updateCube(cubeUpdate, createBudgetActivity, false);
   }

   public void updateCube(CubeUpdate cubeUpdate, boolean createBudgetActivity, boolean clearTempTable) throws Exception {
      this.mUserId = cubeUpdate.getUserId();
      long start = System.currentTimeMillis();
      this.mLog.info("updateCube", "Updating for userid:" + cubeUpdate.getUserId() + ", " + "finance cube id: " + cubeUpdate.getFinanceCubeId());
      this.checkModelLockStatus();
      boolean changedData = false;
      this.mActivityWriter = null;

      CubeUpdateEngine$ActivityWriter aw = this.getActivityWriter();
      aw.addParamSection("CONTEXT");
      aw.addParam("Model", this.getModelEVO().getVisId() + " - " + this.getModelEVO().getDescription());
      aw.addParam("FinanceCube", this.getFinanceCubeEVO().getVisId() + " - " + this.getFinanceCubeEVO().getDescription());
      aw.addParam("Absolute Values", cubeUpdate.isAbsoluteValues()?"True":"False");
      aw.addEndParamSection();
      long end;
      if(cubeUpdate.getCellCount() > 0) {
    	
    	List cells = cubeUpdate.getCells();
    	// check which sql to execute
     	 Iterator cmpyIter = cells.iterator();
     	 while(cmpyIter.hasNext()) {
              Cell i$ = (Cell)cmpyIter.next();
              if(i$ instanceof CellPosting) {
                 CellPosting cell = (CellPosting)i$;
                 if (cell.getCompany() == null) {
                 	isGlobalModel = false;
                 }
              }
     	 } 
    	  
         this.ensureWorkTablesAreEmpty();
         start = System.currentTimeMillis();
         this.insertDeltasIntoTempTable(this.mFinanceCubeId, cells);
         Map notes = this.lookupStructureElementDetails();
         end = System.currentTimeMillis();
         this.mLog.info("updateCube", "Insert into temp " + cubeUpdate.getCellCount() + " cells in " + (end - start) + " milliseconds");
         start = System.currentTimeMillis();
         boolean id = false;

         while(!id) {
            try {
               this.executeCubeUpdate(!cubeUpdate.isAbsoluteValues(), this.mUserId);
               id = true;
               end = System.currentTimeMillis();
            } catch (Exception var15) {
               var15.printStackTrace();
               throw var15;
            }
         }

         this.mLog.info("updateCube", "cube_update.transaction_update completed in " + (end - start) + " milliseconds");
         changedData = true;
         start = System.currentTimeMillis();
         if(createBudgetActivity) {
            this.writeActivityDeltaDetails(this.getFinanceCubeEVO().getModelId(), cubeUpdate.getCells().iterator(), notes, cubeUpdate.isAbsoluteValues());
            end = System.currentTimeMillis();
            this.mLog.info("updateCube", "Write budget activity cell details " + (end - start) + " milliseconds");
         }
      }

      if(cubeUpdate.getCellCalcsCount() > 0) {
         this.processCellCalcUpdates(cubeUpdate.getCellCalcs(), cubeUpdate.getCellCalcLinks());
         changedData = true;
      }

      if(cubeUpdate.getCellCalcsCount() == 0 && cubeUpdate.getCellCalcLinkCount() > 0) {
         this.processCellCalcLinkUpdates(cubeUpdate.getCellCalcLinks());
         changedData = true;
      }

      if(cubeUpdate.getCellNoteCount() > 0) {
    	  
    	  List cellNotes = cubeUpdate.getCellNotes();
    	  
     	 for(int results = 0; results < cellNotes.size(); ++results) {
              CellNote row = (CellNote)cellNotes.get(results);
              String note = row.getNote() != null && row.getNote().length() != 0?row.getNote():"";
              String[] lines = note.split("\n");
              String time = lines[lines.length - 1];
             
              int position =  time.indexOf(",cmpy=");
  			 String company = time.substring(position + 6);
  			 if (!company.equals("")) {
 	    		 row.setCompany(company);
 	    	 } else {
 	    		 row.setCompany(null);
 	    		 isGlobalModel = false;
 	    	 }
     	 }
    	  
    	  List update = this.cellNotesUpdate(cellNotes, "UPDATE");
    	  List insert = this.cellNotesUpdate(cellNotes, "INSERT");
    	  List delete = this.cellNotesUpdate(cellNotes, "DELETE");
    	  if (update.size() > 0) this.updateCellNotes(update);
    	  if (insert.size() > 0) this.insertCellNotes(insert);
    	  if (delete.size() > 0) this.deleteCellNotes(delete);
    	  changedData = true;
    	  if(createBudgetActivity) {
    		  this.writeActivityNoteDetails(this.getFinanceCubeEVO().getModelId(), this.getFinanceCubeEVO().getFinanceCubeId(), cubeUpdate.getCellNotes());
    	  }
      }

      if(cubeUpdate.getFormNotesCount() > 0) {
         Iterator var16 = cubeUpdate.getFormNotes().iterator();
         int var17 = -1;

         while(var16.hasNext()) {
            FormNote note = (FormNote)var16.next();
            FormNotesDAO formNoteDao = this.getFormNotesDOA();
            FormNotesEVO evo = new FormNotesEVO();
            evo.setFormNoteId(var17--);
            evo.setFormId(note.getFormId());
            evo.setStructureElementId(note.getStructureElementId());
            evo.setNote(note.getNote());
            evo.setAttachmentId(note.getAttachmentId());
            evo.setUpdatedByUserId(this.mUserId);
            formNoteDao.setDetails(evo);
            formNoteDao.create();
         }
      }

      if(changedData) {
         this.setFinanceCubeHasDataFlag();
         if(createBudgetActivity) {
            start = System.currentTimeMillis();
            this.insertBudgetActivity(cubeUpdate, this.getBudgetActivityDetails());
            end = System.currentTimeMillis();
            this.mLog.info("updateCube", "Insert budget activity " + (end - start) + " milliseconds");
         }
      }

      if(clearTempTable) {
         this.clearDownTFTTable();
         this.clearDownWorkTable();
         this.clearDownCubeFormulaWorkTables();
      }

   }
   
   private List<CellNote> cellNotesUpdate(List cellNotes, String action) {
	   
	   List<CellNote> cellNotesToUpdate = new ArrayList<CellNote>();
	   for(int results = 0; results < cellNotes.size(); ++results) {
           CellNote row = (CellNote)cellNotes.get(results);
           
           if (row.getAction().toString().equals(action)) {
        	   cellNotesToUpdate.add(row);
           }
       }
	   return cellNotesToUpdate;
   }

   private String queryInsertBudgetActivityLinkSQL() {
      CubeUpdateEngine$1 parser = new CubeUpdateEngine$1(this, "insert into budget_activity_link \tselect distinct ?, dim{resp_area_dim_index}, ? \tfrom  {temp_table_name} tft, structure_element se    where tft.dim{resp_area_dim_index} = se.structure_element_id and\t\t  se.leaf = \'Y\' ");
      return parser.parse();
   }

   private void insertBudgetActivity(CubeUpdate cubeUpdate, String xml) throws Exception {
      ModelDAO modelDAO = new ModelDAO();
      int budgetActivityId = modelDAO.reserveIds(1);
      String description = this.getBudgetActivityDescription(cubeUpdate.getUpdateType());
      BudgetActivityEVO baEVO = new BudgetActivityEVO(budgetActivityId, this.getModelEVO().getModelId(), this.getUserEVO().getName(), new Timestamp(System.currentTimeMillis()), cubeUpdate.getUpdateType(), description, xml, (Boolean)null, cubeUpdate.getActivityUpdateTypeId(), Collections.EMPTY_LIST);
      FinanceCubePK fcPK = new FinanceCubePK(this.mFinanceCubeId);
      FinanceCubeCK fcCK = modelDAO.getFinanceCubeCK(fcPK);
      ModelEVO modelEVO = modelDAO.getDetails(fcCK.getModelPK(), "");
      modelEVO.addBudgetActivitiesItem(baEVO);
      modelDAO.setDetails(modelEVO);
      modelDAO.store();
      PreparedStatement ps = null;

      try {
         ps = this.getConnection().prepareStatement(this.queryInsertBudgetActivityLinkSQL());
         ps.setInt(1, baEVO.getBudgetActivityId());
         if(cubeUpdate.getBudgetCycleId() == 0) {
            ps.setNull(2, 4);
         } else {
            ps.setInt(2, cubeUpdate.getBudgetCycleId());
         }

         ps.executeUpdate();
      } finally {
         this.closeStatement(ps);
         this.closeConnection();
      }

      if(3 == cubeUpdate.getUpdateType()) {
         this.getVirementRequestDAO().updateBudgetActivityId(this.getActivityUpdateTypeId(), baEVO.getBudgetActivityId());
      }

   }

   private String getBudgetActivityDescription(int type) {
      switch(type) {
      case 0:
         return "Manual data entry";
      case 1:
         return "Mass Update";
      case 2:
         return "Allocation";
      case 3:
         return "Budget Transfer";
      case 4:
         return "Cell Calculation Rebuild";
      case 5:
      default:
         throw new IllegalStateException("Unexpected update type:" + type);
      case 6:
         return "Excel Import";
      }
   }

   private CellPostingXMLReader parseXMLDocument(Reader xmlStream) throws Exception {
      CellPostingXMLReader reader = new CellPostingXMLReader();
      reader.init();
      reader.parseConfigFile(xmlStream);
      return reader;
   }

   private void checkGlobalTemporaryTableEmpty() throws SQLException {
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         ps = this.getConnection().prepareStatement("select count(*) from " + this.getXactTableName());
         rs = ps.executeQuery();
         if(!rs.next()) {
            throw new IllegalStateException("Failed to retrieve query");
         }

         int rows = rs.getInt(1);
         if(rows != 0) {
            throw new IllegalStateException("Temporary table contains:" + rows + " rows!");
         }
      } finally {
         this.closeResultSet(rs);
         this.closeStatement(ps);
         this.closeConnection();
      }

   }

   private void insertDeltasIntoTempTable(int financeCubeId, List cells) throws Exception {
      PreparedStatement stmt = null;
      int numDimensions = this.getNumDims();
      Map dataTypeMap = this.loadDataTypes(financeCubeId);
      HashSet measurePostings = new HashSet(512);

      try {
    	  this.getConnection().setAutoCommit(false);
         String insSQL = this.queryInsertTempSQL(isGlobalModel);
         stmt = this.getConnection().prepareStatement(insSQL);
         Iterator deltaIter = cells.iterator();

         CellPosting cellPosting;
         while(deltaIter.hasNext()) {
            Cell i$ = (Cell)deltaIter.next();
            if(i$ instanceof CellPosting) {
               cellPosting = (CellPosting)i$;
               if(cellPosting.isNumericPosting()) {
                  this.addNumericPostingToBatch(stmt, cellPosting, numDimensions, dataTypeMap);
               } else if(!measurePostings.add(cellPosting)) {
                  measurePostings.remove(cellPosting);
                  measurePostings.add(cellPosting);
               }
            }
         }

         Iterator i$1 = measurePostings.iterator();

         while(i$1.hasNext()) {
            cellPosting = (CellPosting)i$1.next();
            this.addNonNumericPostingToBatch(stmt, cellPosting, numDimensions);
         }

         stmt.executeBatch();
         Statement st = getConnection().createStatement();
    	 ResultSet r = st.executeQuery("select * from TX1_4");
             while(r.next()){
            	 System.out.println(r.getString(1));
             }

      } finally {
//         this.closeStatement(stmt);
//         this.closeConnection();
      }
   }

   private void addNumericPostingToBatch(PreparedStatement stmt, CellPosting cellPosting, int numDimensions, Map<String, CubeUpdateEngine$DataTypeDetails> dataTypeMap) throws Exception {
      StringTokenizer st = new StringTokenizer(cellPosting.getAddr(), ",");
      int[] address = new int[numDimensions];

      for(int i = 0; i < numDimensions; ++i) {
         address[i] = Integer.parseInt(st.nextToken());
      }

      String dataType = cellPosting.getDataType();
      int index = 1;

      for(int dataTypeDetail = 0; dataTypeDetail < numDimensions; ++dataTypeDetail) {
         stmt.setInt(index++, address[dataTypeDetail]);
      }

      stmt.setString(index++, dataType);
      if(cellPosting.getDelta() != null) {
         if(cellPosting.getDelta().trim().length() == 0) {
            stmt.setLong(index++, 0L);
         } else {
            double var15 = this.getNumberFormatter().parse(cellPosting.getDelta()).doubleValue() * 10000.0D;
            long numberValue = Math.round(var15);
            if(numberValue > 999999999999999999L) {
               throw new IllegalStateException("Max value exceeded:" + var15 / 10000.0D);
            }
            if (invertNumber) {
                boolean excludes = false;
                // Excluded data types
                if ((excludedDataTypes != null) && (excludedDataTypes.length > 0)) {
                    for (int i = 0; i < excludedDataTypes.length; i++) {
                        if (excludedDataTypes[i].toLowerCase().equals(dataType.toLowerCase())) {
                            excludes = true;
                            break;
                        }
                    }
                }
                if (!excludes) {
                    numberValue *= -1;
                }
            }
            stmt.setLong(index++, numberValue);
         }
      } else {
         if(cellPosting.getNumberValue() == null) {
            throw new IllegalStateException("Expected a numeric cell posting");
         }

         if(cellPosting.getNumberValue().trim().length() == 0) {
            stmt.setLong(index++, 0L);
         } else {
            CubeUpdateEngine$DataTypeDetails var14 = (CubeUpdateEngine$DataTypeDetails)dataTypeMap.get(dataType);
            if(var14 == null) {
               throw new IllegalStateException("Unable to locate data type:" + dataType);
            }

            int decp = var14.getMeasureScale();
            double var16 = this.getNumberFormatter().parse(cellPosting.getNumberValue()).doubleValue();
            if(decp > 0) {
               var16 *= Math.pow(10.0D, (double)decp);
            }

            stmt.setLong(index++, Math.round(var16));
         }
      }

      stmt.setNull(index++, 12);
      String cmpy = cellPosting.getCompany();
      if (cmpy != null) {
     	 stmt.setInt(index++, Integer.parseInt(cmpy));
      }
      stmt.setNull(index, 91);
      this.mLog.debug("addNumericPostingToBatch", "Address:" + cellPosting.getAddr() + " DataType:" + cellPosting.getDataType() + " Value:" + cellPosting.getValue());
      stmt.addBatch();
   }

   private void addNonNumericPostingToBatch(PreparedStatement stmt, CellPosting cellPosting, int numDimensions) throws Exception {
      StringTokenizer st = new StringTokenizer(cellPosting.getAddr(), ",");
      int[] address = new int[numDimensions];

      for(int dataType = 0; dataType < numDimensions; ++dataType) {
         address[dataType] = Integer.parseInt(st.nextToken());
      }

      String var9 = cellPosting.getDataType();
      int index = 1;

      for(int i = 0; i < numDimensions; ++i) {
         stmt.setInt(index++, address[i]);
      }

      stmt.setString(index++, var9);
      if(cellPosting.getDelta() == null && cellPosting.getNumberValue() == null) {
         stmt.setNull(index++, 2);
         if(cellPosting.getStringValue() != null) {
            stmt.setString(index++, cellPosting.getStringValue());
         } else if(cellPosting.getBooleanValue() != null) {
            stmt.setString(index++, Boolean.parseBoolean(cellPosting.getBooleanValue())?"Y":" ");
         } else {
            stmt.setNull(index++, 12);
         }
         String cmpy = cellPosting.getCompany();
         if (cmpy != null) {
        	 stmt.setString(index++, cmpy);
         }
         
         if(cellPosting.getTimeValue() != null) {
            stmt.setTimestamp(index, new Timestamp(this.getTimeFormatter().parse(cellPosting.getTimeValue()).getTime()));
         } else if(cellPosting.getDateValue() != null) {
            stmt.setDate(index, new Date(this.getDateFormmater().parse(cellPosting.getDateValue()).getTime()));
         } else if(cellPosting.getDateTimeValue() != null) {
            stmt.setTimestamp(index, new Timestamp(this.getDateTimeFormatter().parse(cellPosting.getDateTimeValue()).getTime()));
         } else {
            stmt.setNull(index, 91);
         }

         stmt.addBatch();
         this.mLog.debug("addNonNumericPostingToBatch", "Address:" + cellPosting.getAddr() + " DataType:" + cellPosting.getDataType() + " Value:" + cellPosting.getValue());
      } else {
         throw new IllegalStateException("Expected a non numeric cell posting");
      }
   }

   private void writeActivityDeltaDetails(int modelId, Iterator deltaIter, Map<Integer, String[]> seCellMap, boolean absolute) throws Exception {
      int numDimensions = this.getNumDims();
      CubeUpdateEngine$ActivityWriter aw = this.getActivityWriter();
      aw.addMatrixSection("Cell updates");
      ModelDimensionsELO mdELO = this.getModelDAO().getModelDimensions(modelId);
      aw.addRow();

      for(int cell = 0; cell < numDimensions; ++cell) {
         aw.addCellHeading(((DimensionRef)mdELO.getValueAt(cell, "Dimension")).getNarrative());
      }

      aw.addCellHeading("Data Type");
      if(absolute) {
         aw.addCellHeading("Replacement Value");
      } else {
         aw.addCellHeading("Posted Value");
      }

      aw.addEndRow();

      for(; deltaIter.hasNext(); aw.addEndRow()) {
         CellPosting var13 = (CellPosting)deltaIter.next();
         StringTokenizer st = new StringTokenizer(var13.getAddr(), ",");
         int[] address = new int[numDimensions];

         int i;
         for(i = 0; i < numDimensions; ++i) {
            address[i] = Integer.parseInt(st.nextToken());
         }

         aw.addRow();

         for(i = 0; i < numDimensions; ++i) {
            String[] seDetails = (String[])((String[])seCellMap.get(new Integer(address[i])));
            if(seDetails != null) {
               aw.addCellText(seDetails[0] + " - " + seDetails[1]);
            } else {
               aw.addCellText("");
               this.mLog.debug("Failed to lookup cell details dim[" + i + "] address [" + address[i] + "]");
            }
         }

         aw.addCellText(var13.getDataType());
         if(var13.getDelta() != null) {
            aw.addCellNumber(var13.getDelta());
         } else if(var13.getNumberValue() != null) {
            aw.addCellNumber(var13.getNumberValue());
         } else if(var13.getStringValue() != null) {
            aw.addCellText(var13.getStringValue());
         } else if(var13.getTimeValue() != null) {
            aw.addCellText(var13.getTimeValue());
         } else if(var13.getDateValue() != null) {
            aw.addCellText(var13.getDateTimeValue());
         } else if(var13.getDateTimeValue() != null) {
            aw.addCellText(var13.getDateTimeValue());
         } else if(var13.getBooleanValue() != null) {
            aw.addCellText(var13.getBooleanValue());
         }
      }

      aw.addEndMatrixSection();
   }

   private void writeActivityNoteDetails(int modelId, int financeCubeId, Collection<CellNote> notes) throws Exception {
      int numDimensions = this.getNumDims();
      Map seCellMap = this.lookupStructureElementDetails(financeCubeId, notes);
      CubeUpdateEngine$ActivityWriter aw = this.getActivityWriter();
      aw.addMatrixSection("Cell Notes");
      ModelDimensionsELO mdELO = this.getModelDAO().getModelDimensions(modelId);
      aw.addRow();

      for(int i$ = 0; i$ < numDimensions; ++i$) {
         aw.addCellHeading(((DimensionRef)mdELO.getValueAt(i$, "Dimension")).getNarrative());
      }

      aw.addCellHeading("Data Type");
      aw.addCellHeading("Note");
      aw.addEndRow();
      Iterator var14 = notes.iterator();

      while(var14.hasNext()) {
         CellNote cell = (CellNote)var14.next();
         StringTokenizer st = new StringTokenizer(cell.getAddr(), ",");
         int[] address = new int[numDimensions];

         int i;
         for(i = 0; i < numDimensions; ++i) {
            address[i] = Integer.parseInt(st.nextToken());
         }

         aw.addRow();

         for(i = 0; i < numDimensions; ++i) {
            String[] seDetails = (String[])((String[])seCellMap.get(new Integer(address[i])));
            if(seDetails != null) {
               aw.addCellText(seDetails[0] + " - " + seDetails[1]);
            } else {
               aw.addCellText("");
               this.mLog.debug("Failed to lookup cell details dim[" + i + "] address [" + address[i] + "]");
            }
         }

         aw.addCellText(cell.getDataType());
         aw.addCellText(cell.getNote());
         aw.addEndRow();
      }

      aw.addEndMatrixSection();
   }

   private void ensureWorkTablesAreEmpty() throws SQLException {
      CallableStatement cs = null;

      try {
         cs = this.getConnection().prepareCall("begin cube_update.failIfWorkTablesNotEmpty(?,?);end;");
         cs.setString(1, this.getXactTableName());
         cs.setString(2, this.getWorkTableName());
         cs.executeUpdate();
      } finally {
         this.closeStatement(cs);
         this.closeConnection();
      }

   }

   public void executeCubeUpdate(boolean postingDeltas, int auditUserId) throws SQLException {
      CallableStatement cs = null;

      try {
    	 if (isGlobalModel) {
    		 cs = this.getConnection().prepareCall("begin cp_utils.setTaskId(?);cube_update.transaction_updateGlobal( ?, ?, ?, ?, ? ,? );end; ");
    	 } else {
         	cs = this.getConnection().prepareCall("begin cp_utils.setTaskId(?);cube_update.transaction_update( ?, ?, ?, ?, ? ,? );end; ");
    	 }
         byte col = 1;
         int var9;
         if(this.getTaskId() == null) {
            var9 = col + 1;
            cs.setNull(col, 4);
         } else {
            var9 = col + 1;
            cs.setInt(col, this.getTaskId().intValue());
         }

         cs.setInt(var9++, this.getFinanceCubeId());
         cs.setString(var9++, this.getXactTableName());
         cs.setString(var9++, this.getWorkTableName());
         cs.setString(var9++, postingDeltas?"N":"Y");
         cs.setString(var9++, String.valueOf(auditUserId));
         cs.setString(var9++, "N");
         cs.executeUpdate();
      } finally {
         this.closeStatement(cs);
         this.closeConnection();
      }

   }

   private void processCellCalcUpdates(List cellCalcs, List cellCalcLinks) throws Exception {
      CellCalculationDataDAO ccdDAO = this.getCellCalculationDataDAO();
      HashMap cellCalcMap = new HashMap();
      int numNewCCDKeys = 0;
      Iterator cIter = cellCalcs.iterator();

      while(cIter.hasNext()) {
         CellCalc newCCDKey = (CellCalc)cIter.next();
         cellCalcMap.put(new Integer(newCCDKey.getShortId()), newCCDKey);
         if(newCCDKey.isNewKey()) {
            ++numNewCCDKeys;
         }
      }

      int var11 = -1;
      if(numNewCCDKeys > 0) {
         var11 = ccdDAO.assignKeys(numNewCCDKeys);
      }

      ccdDAO.persistCellCalculationDataRows(this.mFinanceCubeId, cellCalcs, var11);
      Iterator clIter = cellCalcLinks.iterator();

      while(clIter.hasNext()) {
         CellCalcLink cellCalcLink = (CellCalcLink)clIter.next();
         CellCalc cellCalc = (CellCalc)cellCalcMap.get(new Integer(cellCalcLink.getShortId()));
         if(cellCalc == null) {
            throw new IllegalStateException("No CellCalc found for CellCalcLink with shortId:" + cellCalcLink.getShortId());
         }

         cellCalcLink.setShortId(cellCalc.getShortId());
      }

      this.insertCCsIntoGlobalTemporary(cellCalcLinks);
      this.executeCftUpsert();
   }

   private void processCellCalcLinkUpdates(List cellCalcLinks) throws Exception {
      this.insertCCsIntoGlobalTemporary(cellCalcLinks);
      this.executeCftUpsert();
   }

   private String queryMergeCellCalcLinkSQL() {
      CubeUpdateEngine$2 tp = new CubeUpdateEngine$2(this, " merge into {cft_table_name} cft using \n ( \n    select {outer_dim_cols_to_select} data_type, max(short_id) short_id, max(cell_calc_id) cell_calc_id \n    from cell_calc_tran \n    group by {outer_dim_cols_to_select} data_type \n    order by {outer_dim_cols_to_select} data_type \n ) delta \n on \n ( \n   {merge_join_clause} \n   cft.data_type = delta.data_type \n ) \n when matched then \n      update set cft.short_id = delta.short_id, cft.cell_calc_id = delta.cell_calc_id \n when not matched then \n      insert ( {insert_cols_clause}       data_type,       short_id,       cell_calc_id ) \n      values ( {insert_vals_clause} delta.data_type, delta.short_id, delta.cell_calc_id ) ");
      return tp.parse();
   }

   public int executeCftUpsert() throws Exception {
      PreparedStatement stmt = null;

      int var3;
      try {
         String mergeSQL = this.queryMergeCellCalcLinkSQL();
         stmt = this.getConnection().prepareStatement(mergeSQL);
         var3 = stmt.executeUpdate();
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }

      return var3;
   }

   private String getInsertCellCalcLinkIntoGlobalTemporarySQL() {
      CubeUpdateEngine$3 parser = new CubeUpdateEngine$3(this, INSERT_CELL_CALC_LINKS_INTO_GLOBAL_TEMPORARY);
      return parser.parse();
   }

   private void insertCCsIntoGlobalTemporary(List cellCalcLinks) throws SQLException {
      PreparedStatement stmt = null;
      int numDimensions = this.getNumDims();

      try {
         String insSQL = this.getInsertCellCalcLinkIntoGlobalTemporarySQL();
         stmt = this.getConnection().prepareStatement(insSQL);
         Iterator iter = cellCalcLinks.iterator();

         while(iter.hasNext()) {
            CellCalcLink clink = (CellCalcLink)iter.next();
            StringTokenizer st = new StringTokenizer(clink.getAddr(), ",");
            int[] address = new int[numDimensions];

            for(int dataType = 0; dataType < numDimensions; ++dataType) {
               address[dataType] = Integer.parseInt(st.nextToken());
            }

            String var15 = clink.getDataType();
            int index = 1;

            for(int i = 0; i < numDimensions; ++i) {
               stmt.setInt(index++, address[i]);
            }

            stmt.setString(index++, var15);
            stmt.setInt(index++, clink.getShortId());
            stmt.setInt(index, clink.getCellCalcId());
            stmt.addBatch();
         }

         stmt.executeBatch();
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }
   }

   private String queryInsertCellNotesSQL(boolean isCompany) {
      CubeUpdateEngine$4 parser;
      if (isCompany){
    	  parser = new CubeUpdateEngine$4(this, "insert into {string_table_name} ( {dim_cols} data_type, seq, created, user_id, user_name, string_value, link_id, link_type, budget_cycle_id, data_entry_profile_id, company) values( {dim_cols_bind_vars} ?,{sft_sequence}.nextval,to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF'), ?, ?, ?, ?, ?, ?, ?, ?)");
      } else {
    	  parser = new CubeUpdateEngine$4(this, "insert into {string_table_name} ( {dim_cols} data_type, seq, created, user_id, user_name, string_value, link_id, link_type, budget_cycle_id, data_entry_profile_id) values( {dim_cols_bind_vars} ?,{sft_sequence}.nextval,to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF'), ?, ?, ?, ?, ?, ?, ?)");
      }
	  return parser.parse();
   }

   private void insertCellNotes(List cellNotes) throws Exception {
      PreparedStatement stmt = null;
      int numDimensions = this.getNumDims();

      try {
    	 	 
         String sql = this.queryInsertCellNotesSQL(isGlobalModel);
         this.mLog.debug("insertCellNotes", sql);
         stmt = this.getConnection().prepareStatement(sql);

         for(int results = 0; results < cellNotes.size(); ++results) {
            CellNote row = (CellNote)cellNotes.get(results);
            StringTokenizer st = new StringTokenizer(row.getAddr(), ",");
            int[] address = new int[numDimensions];

            int index;
            for(index = 0; index < numDimensions; ++index) {
               address[index] = Integer.parseInt(st.nextToken());
            }

            index = 1;

            for(int i = 0; i < numDimensions; ++i) {
               stmt.setInt(index++, address[i]);
            }
            
            String note = row.getNote() != null && row.getNote().length() != 0?row.getNote():"";
            String[] lines = note.split("\n");
            String time = lines[lines.length - 1];
            String newNote = "";
            for (int i = 0; i < lines.length - 1; i++) {
            	newNote += lines[i] + "\n";
            }
            newNote = newNote.substring(0, newNote.length()-1);
            int position =  time.indexOf(",cmpy=");
			String company = time.substring(position + 6);
			time = time.substring(0, position);
            
            stmt.setString(index++, row.getDataType());
            stmt.setString(index++, time);
            stmt.setInt(index++, this.mUserId);
            stmt.setString(index++, row.getUserName());
            stmt.setString(index++, newNote);
            stmt.setInt(index++, row.getLinkId());
            stmt.setInt(index++, row.getLinkType());
            stmt.setInt(index++, this.mBudgetCycleId);
            stmt.setInt(index++, row.getDataEntryProfileId());            
            if (row.getCompany() != null) {
            	stmt.setString(index, company);
            }
            stmt.addBatch();
            this.mLog.debug("insertCellNotes", row.getAddr() + "," + row.getDataType() + "," + this.mUserId + "," + row.getUserName());
            //cellNotesToInsert.put(row.getPrimaryKey(), row);
         }

         int[] var15 = stmt.executeBatch();

         for(int var16 = 0; var16 < var15.length; ++var16) {
            if(var15[var16] != 1 && var15[var16] != -2) {
               throw new IllegalStateException("Failed to insert cell note[" + var15[var16] + "]");
            }
         }

      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }
   }
   
   private String queryUpdateCellNotesSQL() {
      CubeUpdateEngine$4 parser = new CubeUpdateEngine$4(this, "update {string_table_name} set string_value = ?, link_id = ?, link_type = ? where created = to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF')");
      return parser.parse();
   }
  
   
   private void updateCellNotes(List cellNotes) throws Exception {
      PreparedStatement stmt = null;

      try {
         String sql = this.queryUpdateCellNotesSQL();
         this.mLog.debug("updateCellNotes", sql);
         stmt = this.getConnection().prepareStatement(sql);

         for(int results = 0; results < cellNotes.size(); ++results) {
            CellNote row = (CellNote)cellNotes.get(results);

            int index = 1;
            // get date from last line and remove it from note
            String note = row.getNote() != null && row.getNote().length() != 0?row.getNote():"";
            String[] lines = note.split("\n");
            String time = lines[lines.length - 1];
            String newNote = "";
            for (int i = 0; i < lines.length - 1; i++) {
            	newNote += lines[i] + "\n";
            }
            newNote = newNote.substring(0, newNote.length()-1);
            int linkId = row.getLinkId();
            int position =  time.indexOf(",cmpy=");
			time = time.substring(0, position);

            stmt.setString(index++, newNote);
            stmt.setInt(index++, linkId);
            stmt.setInt(index++, row.getLinkType());
            stmt.setString(index++, time);
            stmt.addBatch();
            this.mLog.debug("updateCellNotes", row.getAddr() + "," + row.getDataType() + "," + this.mUserId + "," + row.getUserName());
         }

         int[] var15 = stmt.executeBatch();

         for(int var16 = 0; var16 < var15.length; ++var16) {
            if(var15[var16] != 1 && var15[var16] != -2) {
               throw new IllegalStateException("Failed to update cell note[" + var15[var16] + "]");
            }
         }

      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }
   }
   
   private String queryDeleteAttSQL() {
	      CubeUpdateEngine$4 parser = new CubeUpdateEngine$4(this, "delete from extended_attachment where extended_attachment_id = (select link_id from {string_table_name} where created = to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF'))");
      return parser.parse();
   }
   
   private String queryDeleteCellNotesSQL() {
      CubeUpdateEngine$4 parser = new CubeUpdateEngine$4(this, "delete from {string_table_name} where created = to_timestamp(? ,'YYYY-MM-DD HH24:MI:SS.FF')");
      return parser.parse();
   }

   private void deleteCellNotes(List cellNotes) throws Exception {
      PreparedStatement stmt = null;
      PreparedStatement stmt2 = null;

      try {
         String sql = this.queryDeleteCellNotesSQL();
         String sqlAtt = this.queryDeleteAttSQL();
         this.mLog.debug("deleteCellNotes", sql);
         stmt = this.getConnection().prepareStatement(sql);
         stmt2 = this.getConnection().prepareStatement(sqlAtt);

         for(int results = 0; results < cellNotes.size(); ++results) {
            CellNote row = (CellNote)cellNotes.get(results);

            // get date from last line
            String note = row.getNote() != null && row.getNote().length() != 0?row.getNote():"";
            String[] lines = note.split("\n");
            String time = lines[lines.length - 1];
            int position =  time.indexOf(",cmpy=");
			time = time.substring(0, position);
           
            stmt2.setString(1, time);
            
            stmt.setString(1, time);
            stmt.addBatch();
            stmt2.addBatch();
            this.mLog.debug("deleteCellNotes", row.getAddr() + "," + row.getDataType() + "," + this.mUserId + "," + row.getUserName());
         }
         
         int[] res = stmt2.executeBatch();
         int[] var15 = stmt.executeBatch();         
         
         for(int var16 = 0; var16 < var15.length; ++var16) {
            if(var15[var16] != 1 && var15[var16] != -2) {
               throw new IllegalStateException("Failed to delete cell note[" + var15[var16] + "]");
            }
         }
         for(int i = 0; i < res.length; ++i) {
             if(res[i] != 1 && res[i] != -2) {
                throw new IllegalStateException("Failed to delete old attachment [" + res[i] + "]");
             }
         }

      } finally {
    	 this.closeStatement(stmt2);
         this.closeStatement(stmt);
         this.closeConnection();
      }
   }

   private int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   private String queryFinanceCubeStringTableName(int financeCubeId) {
      return "sft" + financeCubeId;
   }

   private String queryFinanceCubeCellCalcTableName(int financeCubeId) {
      return "cft" + financeCubeId;
   }

   private String getOuterDimColsToSelect(String preFix) {
      StringBuffer sb = new StringBuffer();
      int numDims = this.getNumDims();

      for(int i = 0; i < numDims; ++i) {
         if(preFix != null) {
            sb.append(preFix);
            sb.append('.');
         }

         sb.append("dim");
         sb.append(Integer.toString(i));
         sb.append(", ");
      }

      return sb.toString();
   }

   public String getXactTableName() {
      return "TX1_" + this.getFinanceCubeId();
   }

   private String getWorkTableName() {
      return "TX2_" + this.getFinanceCubeId();
   }

   private String getSftSequence() {
      return "SFTSEQ" + this.getFinanceCubeId();
   }

   private String getFormulaDepWorkTableName() {
      return "FOR" + this.getNumDims() + "DEP";
   }

   private String getFormulaXTranWorkTableName() {
      return "FOR" + this.getNumDims() + "XTRAN";
   }

   private String getMergeJoinClause(String ft, String delta) {
      StringBuffer sb = new StringBuffer();
      int numDims = this.getNumDims();

      for(int i = 0; i < numDims; ++i) {
         sb.append(ft);
         sb.append(".dim");
         sb.append(Integer.toString(i));
         sb.append(" = ");
         sb.append(delta);
         sb.append(".dim");
         sb.append(Integer.toString(i));
         sb.append(" ");
         sb.append(" and ");
         sb.append("\n");
      }

      return sb.toString();
   }

   private String getInsertColsClause() {
      return this.getOuterDimColsToSelect((String)null);
   }

   private String getInsertValsClause() {
      StringBuffer sb = new StringBuffer();
      int numDims = this.getNumDims();

      for(int i = 0; i < numDims; ++i) {
         sb.append("delta.dim");
         sb.append(Integer.toString(i));
         sb.append(", ");
      }

      return sb.toString();
   }

   private String queryInsertTempSQL(boolean isCompany) {
	   CubeUpdateEngine$5 parser;
	   if (isCompany) {
		  parser = new CubeUpdateEngine$5(this, INSERT_INTO_GLOBAL_TEMPORARY_COMPANY);
	   } else {
		  parser = new CubeUpdateEngine$5(this, INSERT_INTO_GLOBAL_TEMPORARY);
	   }
	   return parser.parse();
   }

   private String getDimCols() {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.getNumDims(); ++i) {
         sb.append("dim");
         sb.append(Integer.toString(i));
         sb.append(", ");
      }

      return sb.toString();
   }

   private String getDimColsBindVars() {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.getNumDims(); ++i) {
         sb.append("?, ");
      }

      return sb.toString();
   }

   private String querySELookupSQL() {
      CubeUpdateEngine$6 parser = new CubeUpdateEngine$6(this, "select ids.seid, se.vis_id, se.description\nfrom \n  ( select distinct seid from \n    ( \n\t   {id_select_statement} \n    ) \n  ) ids, \n  structure_element se \nwhere ids.seid = se.structure_element_id ");
      return parser.parse();
   }

   public Map lookupStructureElementDetails(int financeCubeId, Collection<CellNote> cellNotes) throws SQLException, Exception {
      this.clearDownTFTTable();
      this.insertCellAddressesIntoTempTable(cellNotes);
      return this.lookupStructureElementDetails();
   }

   private void insertCellAddressesIntoTempTable(Collection<CellNote> cells) throws Exception {
      PreparedStatement stmt = null;
      int numDimensions = this.getNumDims();

      try {
         String insSQL = this.queryInsertTempSQL(isGlobalModel);
         stmt = this.getConnection().prepareStatement(insSQL);
         Iterator deltaIter = cells.iterator();

         while(deltaIter.hasNext()) {
            Cell cell = (Cell)deltaIter.next();
            StringTokenizer st = new StringTokenizer(cell.getAddr(), ",");
            int[] address = new int[numDimensions];

            for(int dataType = 0; dataType < numDimensions; ++dataType) {
               address[dataType] = Integer.parseInt(st.nextToken());
            }

            String var16 = cell.getDataType();
            if(cell instanceof CellNote) {
               CellNote cellNote = (CellNote)cell;
               int index = 1;

               for(int i = 0; i < numDimensions; ++i) {
                  stmt.setInt(index++, address[i]);
               }

               stmt.setString(index++, var16);
               stmt.setNull(index++, 4);
               stmt.setNull(index++, 12);
               if (isGlobalModel) {
            	   stmt.setString(index++, cellNote.getCompany());
               }
               stmt.setNull(index++, 91);
               stmt.addBatch();
            }
         }

         stmt.executeBatch();
      } finally {
         this.closeStatement(stmt);
         this.closeConnection();
      }
   }

   public Map<Integer, String[]> lookupStructureElementDetails() throws SQLException {
      PreparedStatement ps = null;
      ResultSet rs = null;
      HashMap nodeMap = new HashMap();

      try {
         String lookupSQL = this.querySELookupSQL();
         ps = this.getConnection().prepareStatement(lookupSQL);
         rs = ps.executeQuery();

         while(rs.next()) {
            byte col = 1;
            Integer seId = new Integer(rs.getInt(col++));
            String visId = rs.getString(col++);
            String description = rs.getString(col);
            nodeMap.put(seId, new String[] { visId, description });
         }

         return nodeMap;
      } finally {
//         this.closeResultSet(rs);
//         this.closeStatement(ps);
//         this.closeConnection();
      }
   }

   public void setNumDims(int numDims) {
      this.mNumDims = numDims;
   }

   public int getNumDims() {
      return this.mNumDims;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   private int getRespAreaDimensionIndex() {
      if(this.mDimensionsAndHierachiesELO == null) {
         this.queryDimensionAndHierarchyInfo();
      }

      for(int i = 0; i < this.mDimensionsAndHierachiesELO.size(); ++i) {
         if(this.mDimensionsAndHierachiesELO.getValueAt(i, "col2").equals("Y")) {
            return i;
         }
      }

      throw new IllegalStateException("Unable to locate primary business dimension");
   }

   private void queryDimensionAndHierarchyInfo() {
      FinanceCubeDAO fCubeDAO = this.getFinanceCubeDAO();
      this.mDimensionsAndHierachiesELO = fCubeDAO.getFinanceCubeDimensionsAndHierachies(this.mFinanceCubeId);
      this.mNumDims = this.mDimensionsAndHierachiesELO.getNumRows();
   }

   private void checkModelLockStatus() throws ModelLockedException, ValidationException {
      FinanceCubePK fcPK = new FinanceCubePK(this.mFinanceCubeId);
      ModelDAO modelDAO = this.getModelDAO();
      FinanceCubeCK fcCK = modelDAO.getFinanceCubeCK(fcPK);
      ModelEVO modelEVO = modelDAO.getDetails(fcCK.getModelPK(), "");
      if(modelEVO.getLocked()) {
         throw new ModelLockedException(new ModelRefImpl(fcCK.getModelPK(), modelEVO.getVisId()), "Updates not allowed when model is locked for maintenance");
      }
   }

   public void setFinanceCubeHasDataFlag() throws ValidationException {
      FinanceCubePK fcPK = new FinanceCubePK(this.mFinanceCubeId);
      ModelDAO modelDAO = this.getModelDAO();
      FinanceCubeCK fcCK = modelDAO.getFinanceCubeCK(fcPK);
      ModelEVO modelEVO = modelDAO.getDetails(fcCK.getModelPK(), "<0>");
      FinanceCubeEVO fcEVO = modelEVO.getFinanceCubesItem(fcPK);
      if(!fcEVO.getHasData()) {
         fcEVO.setHasData(true);

         try {
            modelDAO.setDetails(modelEVO);
            modelDAO.store();
         } catch (Exception var7) {
            throw new CPException(var7.getMessage(), var7);
         }
      }

   }

   public void clearDownTFTTable() throws Exception {
      PreparedStatement ps = null;

      try {
         ps = this.getConnection().prepareStatement("delete from " + this.getXactTableName());
         ps.executeUpdate();
      } finally {
         this.closeStatement(ps);
         this.closeConnection();
      }

   }

   public void clearDownWorkTable() throws Exception {
      PreparedStatement ps = null;

      try {
         ps = this.getConnection().prepareStatement("delete from " + this.getWorkTableName());
         ps.executeUpdate();
      } finally {
         this.closeStatement(ps);
         this.closeConnection();
      }

   }

   public void clearDownCubeFormulaWorkTables() throws Exception {
      PreparedStatement ps = null;

      try {
         ps = this.getConnection().prepareStatement("delete from " + this.getFormulaDepWorkTableName());
         ps.executeUpdate();
         ps = this.getConnection().prepareStatement("delete from " + this.getFormulaXTranWorkTableName());
         ps.executeUpdate();
      } finally {
         this.closeStatement(ps);
         this.closeConnection();
      }

   }

   public Map<String, CubeUpdateEngine$DataTypeDetails> loadDataTypes(int financeCubeId) {
      HashMap dataTypeMap = new HashMap();
      AllDataTypeForFinanceCubeELO dataTypes = this.getDataTypeDAO().getAllDataTypeForFinanceCube(financeCubeId);

      for(int i = 0; i < dataTypes.getNumRows(); ++i) {
         DataTypeRef dtRef = (DataTypeRef)dataTypes.getValueAt(i, "DataType");
         int subType = ((Integer)dataTypes.getValueAt(i, "SubType")).intValue();
         Integer measureClass = (Integer)dataTypes.getValueAt(i, "MeasureClass");
         Integer measureScale = (Integer)dataTypes.getValueAt(i, "MeasureScale");
         Integer measureLength = (Integer)dataTypes.getValueAt(i, "MeasureLength");
         dataTypeMap.put(dtRef.getNarrative(), new CubeUpdateEngine$DataTypeDetails(dtRef, subType, measureClass != null?measureClass.intValue():-1, measureScale != null?measureScale.intValue():-1, measureLength != null?measureLength.intValue():-1));
      }

      return dataTypeMap;
   }

   public String getBudgetActivityDetails() {
      this.getActivityWriter().addEndReport();
      return this.getActivityWriter().getXMLTxt();
   }

   private CubeUpdateEngine$ActivityWriter getActivityWriter() {
      if(this.mActivityWriter == null) {
         this.mActivityWriter = new CubeUpdateEngine$ActivityWriter(this);
         this.mActivityWriter.addReport();
      }

      return this.mActivityWriter;
   }

   private ModelEVO getModelEVO() throws ValidationException {
      if(this.mModelEVO == null) {
         this.mModelEVO = this.getModelDAO().getDetails(this.getFinanceCubeCK().getModelPK(), "");
      }

      return this.mModelEVO;
   }

   private ModelDAO getModelDAO() {
      if(this.mModelDAO == null) {
         this.mModelDAO = new ModelDAO();
      }

      return this.mModelDAO;
   }

   private FinanceCubeCK getFinanceCubeCK() {
      if(this.mFinanceCubeCK == null) {
         this.mFinanceCubeCK = this.getModelDAO().getFinanceCubeCK(new FinanceCubePK(this.mFinanceCubeId));
      }

      return this.mFinanceCubeCK;
   }

   private UserEVO getUserEVO() throws ValidationException {
      if(this.mUserEVO == null) {
         this.mUserEVO = this.getUserDAO().getDetails(new UserPK(this.mUserId), "");
      }

      return this.mUserEVO;
   }

   private UserDAO getUserDAO() {
      if(this.mUserDAO == null) {
         this.mUserDAO = new UserDAO();
      }

      return this.mUserDAO;
   }

   private VirementRequestDAO getVirementRequestDAO() {
      if(this.mVirementRequestDAO == null) {
         this.mVirementRequestDAO = new VirementRequestDAO();
      }

      return this.mVirementRequestDAO;
   }

   FinanceCubeEVO getFinanceCubeEVO() throws ValidationException {
      if(this.mFinanceCubeEVO == null) {
         this.mFinanceCubeEVO = this.getFinanceCubeDAO().getDetails(this.getFinanceCubeCK(), "");
      }

      return this.mFinanceCubeEVO;
   }

   private FinanceCubeDAO getFinanceCubeDAO() {
      if(this.mFinanceCubeDAO == null) {
         this.mFinanceCubeDAO = new FinanceCubeDAO();
      }

      return this.mFinanceCubeDAO;
   }

   private NumberFormat getActivityNumberFormatter() {
      if(this.mActvityNumberFormatter == null) {
         this.mActvityNumberFormatter = new DecimalFormat();
         this.mActvityNumberFormatter.setMaximumFractionDigits(2);
         this.mActvityNumberFormatter.setMinimumFractionDigits(2);
      }

      return this.mActvityNumberFormatter;
   }

   private NumberFormat getNumberFormatter() {
      if(this.mNumberFormatter == null) {
         this.mNumberFormatter = new DecimalFormat();
      }

      return this.mNumberFormatter;
   }

   private DateFormat getTimeFormatter() {
      if(this.mTimeFormatter == null) {
         this.mTimeFormatter = new SimpleDateFormat("HH:mm:ss");
      }

      return this.mTimeFormatter;
   }

   private DateFormat getDateFormmater() {
      if(this.mDateFormatter == null) {
         this.mDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
      }

      return this.mDateFormatter;
   }

   private DateFormat getDateTimeFormatter() {
      if(this.mDateTimeFormatter == null) {
         this.mDateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      }

      return this.mDateTimeFormatter;
   }

   public void setTaskId(Integer taskId) {
      this.mTaskId = taskId;
   }

   public Integer getTaskId() {
      return this.mTaskId;
   }

   private int queryDecimalPlaces(String numericValue) {
      String value = numericValue.trim();
      int pointIndex = value.indexOf(46);
      return pointIndex != -1?value.length() - (pointIndex + 1):0;
   }

   private DataTypeDAO getDataTypeDAO() {
      if(this.mDataTypeDAO == null) {
         this.mDataTypeDAO = new DataTypeDAO();
      }

      return this.mDataTypeDAO;
   }

   private FormNotesDAO getFormNotesDOA() {
      if(this.mFormNotesDAO == null) {
         this.mFormNotesDAO = new FormNotesDAO();
      }

      return this.mFormNotesDAO;
   }

   private CellCalculationDataDAO getCellCalculationDataDAO() {
      if(this.mCellCalculationDataDAO == null) {
         this.mCellCalculationDataDAO = new CellCalculationDataDAO();
      }

      return this.mCellCalculationDataDAO;
   }

   public int getActivityUpdateTypeId() {
      return this.mActivityUpdateTypeId;
   }

   public void setActivityUpdateTypeId(int activityUpdateTypeId) {
      this.mActivityUpdateTypeId = activityUpdateTypeId;
   }

   public void setCellCalculationDataDAO(CellCalculationDataDAO cellCalculationDataDAO) {
      this.mCellCalculationDataDAO = cellCalculationDataDAO;
   }

   public void setUserDAO(UserDAO userDAO) {
      this.mUserDAO = userDAO;
   }

   public void setModelDAO(ModelDAO modelDAO) {
      this.mModelDAO = modelDAO;
   }

   public void setDataTypeDAO(DataTypeDAO dataTypeDAO) {
      this.mDataTypeDAO = dataTypeDAO;
   }

   public void setFormNotesDAO(FormNotesDAO formNotesDAO) {
      this.mFormNotesDAO = formNotesDAO;
   }

   public void setFinanceCubeDAO(FinanceCubeDAO financeCubeDAO) {
      this.mFinanceCubeDAO = financeCubeDAO;
   }

   public void setVirementRequestDAO(VirementRequestDAO virementRequestDAO) {
      this.mVirementRequestDAO = virementRequestDAO;
   }

   // $FF: synthetic method
   static int accessMethod000(CubeUpdateEngine x0) {
      return x0.getRespAreaDimensionIndex();
   }

   // $FF: synthetic method
   static int accessMethod100(CubeUpdateEngine x0) {
      return x0.mFinanceCubeId;
   }

   // $FF: synthetic method
   static String accessMethod200(CubeUpdateEngine x0, int x1) {
      return x0.queryFinanceCubeCellCalcTableName(x1);
   }

   // $FF: synthetic method
   static String accessMethod300(CubeUpdateEngine x0, String x1) {
      return x0.getOuterDimColsToSelect(x1);
   }

   // $FF: synthetic method
   static String accessMethod400(CubeUpdateEngine x0, String x1, String x2) {
      return x0.getMergeJoinClause(x1, x2);
   }

   // $FF: synthetic method
   static String accessMethod500(CubeUpdateEngine x0) {
      return x0.getInsertColsClause();
   }

   // $FF: synthetic method
   static String accessMethod600(CubeUpdateEngine x0) {
      return x0.getInsertValsClause();
   }

   // $FF: synthetic method
   static String accessMethod700(CubeUpdateEngine x0) {
      return x0.getDimCols();
   }

   // $FF: synthetic method
   static String accessMethod800(CubeUpdateEngine x0) {
      return x0.getDimColsBindVars();
   }

   // $FF: synthetic method
   static int accessMethod900(CubeUpdateEngine x0) {
      return x0.getFinanceCubeId();
   }

   // $FF: synthetic method
   static String accessMethod1000(CubeUpdateEngine x0, int x1) {
      return x0.queryFinanceCubeStringTableName(x1);
   }

   // $FF: synthetic method
   static String accessMethod1100(CubeUpdateEngine x0) {
      return x0.getSftSequence();
   }

}
