// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:36
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.virement;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllRootsForModelELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.virement.VirementGroupImpl;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.model.virement.VirementLineSpreadImpl;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.TaskReport;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.virement.VirementUpdateEngine$1;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;

public class VirementUpdateEngine extends AbstractDAO {

   private static String VIREMENT_CHECK_SQL = "with virement_data_types as \n(\n  select dt.vis_id \n  from data_type dt,\n       finance_cube_data_type fcdt,\n       finance_cube fc\n  where fc.finance_cube_id = {finance_cube_id}\n    and fc.finance_cube_id = fcdt.finance_cube_id\n    and fcdt.data_type_id = dt.data_type_id \n    and dt.sub_type in (1,2)\n  union all\n  select \'  \' from dual\n)\nselect vire_resp.vis_id as cat_vis_id, \n       vire_resp.se_vis_id as cat_se_vis_id, \n       vire_resp.tran_limit as cat_tran_limit, \n       vire_resp.total_limit_in as cat_total_limit_in, \n       vire_resp.total_limit_out as cat_total_limit_out, \n       vire_acct.se_vis_id as acct_se_vis_id, \n       vire_acct.tran_limit as acct_tran_limit, \n       vire_acct.total_limit_in as acct_total_limit_in, \n       vire_acct.total_limit_out as acct_total_limit_out, \n       vire_acct.in_flag as acct_in_flag, \n       vire_acct.out_flag as acct_out_flag, \n\t\t( select count(1) from virement_account \n\t\t  where virement_category_id = vire_resp.virement_category_id \n\t\t) as num_acct_defs, \n\t\t( select sum(public_value) \n         from nft{finance_cube_id} nft \n         where dim0 = vire_resp.se_id and \n\t\t\t\t{additional_dimension_predicates} \n               dim{account_dimension_index} = ? and \n\t\t\t\tdim{calendar_dimension_index} = ? and \n               data_type in (select vis_id from virement_data_types) \n\t\t) as resp_public_value, \n\t\t( select sum(public_value) \n         from nft{finance_cube_id} nft \n         where dim0 = vire_resp.se_id and \n\t\t\t\t{additional_dimension_predicates} \n               dim{account_dimension_index} = vire_acct.se_id and \n\t\t\t\tdim{calendar_dimension_index} = ? and \n               data_type in (select vis_id from virement_data_types) \n\t\t) as acct_public_value \nfrom \n( select seq.acct_row_num, seq.vis_id as se_vis_id, va.*, \n \t\t  va.structure_element_id as se_id \n  from virement_account va, \n       ( select rownum as acct_row_num, structure_id, structure_element_id, vis_id \n         from structure_element \n         connect by structure_element_id = prior parent_id \n         start with structure_element_id = ? \n       ) seq \n  where va.structure_id = seq.structure_id and \n        va.structure_element_id = seq.structure_element_id \n) vire_acct, \n( select resp_row_num, vc.virement_category_id, vc.vis_id, vc.tran_limit, vc.total_limit_in, vc.total_limit_out, \n\t\t  seq.vis_id as se_vis_id, ve.structure_element_id as se_id \n  from virement_category vc, \n     virement_location ve, \n     ( select rownum as resp_row_num, structure_element_id, vis_id \n       from structure_element \n       connect by structure_element_id = prior parent_id \n       start with structure_element_id = ? \n     ) seq \n  where vc.model_id = ? and \n  ve.structure_element_id = seq.structure_element_id and \n        vc.virement_category_id = ve.virement_category_id \n) vire_resp \nwhere vire_resp.virement_category_id = vire_acct.virement_category_id (+) \norder by resp_row_num, acct_row_num ";
   private static final String DEPENDANTS_FOR_LOAD = "<30><31><32><33><34><35>";
   private static final String sCubeUpdateXML = "<CubeUpdate>\n<UserId>{0}</UserId>\n<FinanceCubeId>{1}</FinanceCubeId>\n<AbsoluteValues>false</AbsoluteValues>\n<UpdateType>3</UpdateType>\n<BudgetCycleId>{2}</BudgetCycleId>\n<Cells>\n{3}{4}</Cells>\n</CubeUpdate>";
   private static final String sCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n";
   private static final String sCellNoteLineXML = "<CellNote addr=\"{0}\" dataType=\"{1}\" userName=\"{2}\" linkId=\"{3,number,0}\" linkType=\"{4}\"><![CDATA[{5}]]></CellNote>";
   private NumberFormat mNumberFormat;
   private NumberFormat mOutputFormat;
   private TaskMessageLogger mMessageLogger;
   private TaskReportWriter mReportWriter;
   private AllRootsForModelELO mModelRoots;
   private CubeUpdateEngine mUpdateEngine;
   private VirementRequestImpl mVirementRequest;
   private int mUserId;
   private int mDimensionCount;
   private ModelDAO mModelDAO;
   private ModelEVO mModelEVO;
   private UserEVO mUserEVO;
   private UserDAO mUserDAO;
   private FinanceCubeDAO mFinanceCubeDAO;
   private FinanceCubeEVO mFinanceCubeEVO;
   private BudgetCycleEVO mBudgetCycleEVO;
   private DataTypeDAO mDataTypeDAO;
   private BudgetCycleDAO mBudgetCycleDAO;
   private StructureElementDAO mStructureElementDAO;
   private ExternalSystemDAO mExternalSystemDAO;
   private TaskReport mTaskReport;
   private Integer mTaskId;
   private final String mSymbol;
   static final int DOC_ATTACHMENT = 1;
   static final int BUDGET_TRANSFER = 2;


   public VirementUpdateEngine(int userId, VirementRequestImpl request) {
      this.mSymbol = "£";
      this.mVirementRequest = request;
      this.mUserId = userId;
      this.mUpdateEngine = new CubeUpdateEngine();
   }

   public VirementUpdateEngine(int userId, VirementRequestImpl request, TaskReport taskReport) {
      this(userId, request);
      this.mTaskReport = taskReport;
   }

   public VirementUpdateEngine() {
      this.mSymbol = "£";
   }

   public void performUpdate() throws ValidationException, Exception {
      this.mUpdateEngine.setFinanceCubeId(this.getFinanceCubeId());
      this.mUpdateEngine.setNumDims(this.getDimensionCount());
      this.mUpdateEngine.setTaskId(this.getTaskId());
      CalendarInfo calendarInfo = this.getCalendarInfo(this.getFinanceCubeId());
      StringBuffer notesXML = new StringBuffer();
      StringBuffer linesXML = new StringBuffer();
      this.getTaskReport().addReport();
      TaskReport tr = this.getTaskReport();
      tr.addParamSection("WHY");
      tr.addParam("Reason", this.mVirementRequest.getReason());
      tr.addParam("Reference", this.mVirementRequest.getReference());
      tr.addEndParamSection();
      tr.addParamSection("CONTEXT");
      tr.addParam("Model", this.getModelEVO().getVisId() + " - " + this.getModelEVO().getDescription());
      tr.addParam("Finance Cube", this.getFinanceCubeEVO().getVisId() + " - " + this.getFinanceCubeEVO().getDescription());
      tr.addParam("Budget Cycle", this.getBudgetCycleEVO().getVisId() + " - " + this.getBudgetCycleEVO().getDescription());
      tr.addEndParamSection();
      tr.addMatrixSection("DETAILS");
      tr.addRow();
      tr.addCellHeading("Direction", "left");
      Iterator hIter = this.queryDimensionHeaders().iterator();

      while(hIter.hasNext()) {
         EntityRef gIter = (EntityRef)hIter.next();
         tr.addCellHeading(gIter.getNarrative(), "left");
      }

      tr.addCellHeading("Notes", "left");
      tr.addCellHeading("Data Type", "left");
      tr.addCellHeading("Transfer Value", "right");
      tr.addEndRow();
      Iterator gIter1 = this.mVirementRequest.getVirementGroups().iterator();

      VirementGroupImpl completeXML;
      while(gIter1.hasNext()) {
         completeXML = (VirementGroupImpl)gIter1.next();
         linesXML.append(this.generateXMLLines(completeXML, calendarInfo));
      }

      gIter1 = this.mVirementRequest.getVirementGroups().iterator();

      while(gIter1.hasNext()) {
         completeXML = (VirementGroupImpl)gIter1.next();
         notesXML.append(this.generateXMLNotes(completeXML));
      }

      String completeXML1 = MessageFormat.format("<CubeUpdate>\n<UserId>{0}</UserId>\n<FinanceCubeId>{1}</FinanceCubeId>\n<AbsoluteValues>false</AbsoluteValues>\n<UpdateType>3</UpdateType>\n<BudgetCycleId>{2}</BudgetCycleId>\n<Cells>\n{3}{4}</Cells>\n</CubeUpdate>", new Object[]{String.valueOf(this.getUserId()), String.valueOf(this.getFinanceCubeId()), String.valueOf(this.getBudgetCycleId()), linesXML.toString(), notesXML.toString()});
      this.mUpdateEngine.setActivityUpdateTypeId(this.mVirementRequest.getRequestId());
      this.mUpdateEngine.updateCube(new StringReader(completeXML1));
      this.mUpdateEngine.setFinanceCubeHasDataFlag();
      this.getTaskReport().addEndMatrixSection();
      this.getTaskReport().addEndReport();
      this.getTaskReport().flushText();
   }

   private String getPostingValue(long value) {
      double realValue = (double)value / 10000.0D;
      return this.getOutputNumberFormatter().format(realValue);
   }

   private String generateXMLLines(VirementGroupImpl group, CalendarInfo calInfo) {
      StringBuffer sb = new StringBuffer();
      boolean firstRow = true;

      for(Iterator lIter = group.getRows().iterator(); lIter.hasNext(); firstRow = false) {
         VirementLineImpl line = (VirementLineImpl)lIter.next();
         if(line.isSummaryLine()) {
            line.spreadValueViaRatios();
            Iterator tr = line.getSpreadProfile().iterator();

            while(tr.hasNext()) {
               VirementLineSpreadImpl aIter = (VirementLineSpreadImpl)tr.next();
               if(aIter.getTransferValue() != 0.0D) {
                  String pIter = line.isTo()?this.getPostingValue(aIter.getTransferValueAsLong()):this.getPostingValue(-aIter.getTransferValueAsLong());
                  sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n", new Object[]{aIter.getAddressAsCSVString(line), line.getDataTypeRef(), pIter}));
               }
            }
         } else if(line.getTransferValueAsLong() != 0L) {
            String tr1 = line.isTo()?this.getPostingValue(line.getTransferValueAsLong()):this.getPostingValue(-line.getTransferValueAsLong());
            sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n", new Object[]{line.getAddressAsCSVString(), line.getDataTypeRef(), tr1}));
         }

         TaskReport tr2 = this.getTaskReport();
         tr2.addRow();
         tr2.addCellText(line.isFrom()?"From":"To");
         Iterator aIter1 = line.getAddress().iterator();

         while(aIter1.hasNext()) {
            StructureElementRef pIter1 = (StructureElementRef)aIter1.next();
            if(aIter1.hasNext()) {
               tr2.addCellText(pIter1.getNarrative());
            } else {
               tr2.addCellText(calInfo.getById(((StructureElementPK)pIter1.getPrimaryKey()).getStructureElementId()).getFullPathVisId());
            }
         }

         if(firstRow) {
            tr2.addCellText(group.getNotes(), this.queryReportLinesForGroup(group));
         }

         tr2.addCellText(line.getDataTypeRef().getNarrative());
         tr2.addCellNumber(this.getNumberFormatter().format(line.getTransferValue()));
         tr2.addEndRow();
         if(line.isSummaryLine()) {
            line.spreadValueViaRatios();
            Iterator pIter2 = line.getSpreadProfile().iterator();

            while(pIter2.hasNext()) {
               VirementLineSpreadImpl spread = (VirementLineSpreadImpl)pIter2.next();
               tr2.addMultiPart(3, spread.getStructureElementRef().getNarrative());
               tr2.addCellNumber(this.getNumberFormatter().format(spread.getTransferValue()));
               tr2.addRow();
               tr2.addCellText("");
               aIter1 = line.getAddress().iterator();

               while(aIter1.hasNext()) {
                  StructureElementRef se = (StructureElementRef)aIter1.next();
                  if(aIter1.hasNext()) {
                     tr2.addCellText("");
                  } else {
                     tr2.addCellText(spread.getStructureElementRef().getNarrative());
                  }
               }

               tr2.addCellText(line.getDataTypeRef().getNarrative());
               tr2.addCellNumber(this.getNumberFormatter().format(spread.getTransferValue()));
               tr2.addEndRow();
            }
         }
      }

      return sb.toString();
   }

   private int queryReportLinesForGroup(VirementGroupImpl group) {
      int lineCount = 0;
      List rows = group.getRows();
      Iterator gIter = rows.iterator();

      while(gIter.hasNext()) {
         ++lineCount;
         VirementLineImpl line = (VirementLineImpl)gIter.next();
         if(line.isSummaryLine()) {
            for(Iterator sIter = line.getSpreadProfile().iterator(); sIter.hasNext(); ++lineCount) {
               sIter.next();
            }
         }
      }

      return lineCount;
   }

   private String generateXMLNotes(VirementGroupImpl group) throws ValidationException {
      StringBuffer sb = new StringBuffer();
      Iterator lIter = group.getRows().iterator();

      while(lIter.hasNext()) {
         VirementLineImpl line = (VirementLineImpl)lIter.next();
         if(line.getTransferValueAsLong() != 0L) {
            String spreadIter = "Budget Transfer\n" + group.getNotes();
            sb.append(MessageFormat.format("<CellNote addr=\"{0}\" dataType=\"{1}\" userName=\"{2}\" linkId=\"{3,number,0}\" linkType=\"{4}\"><![CDATA[{5}]]></CellNote>", new Object[]{line.getAddressAsCSVString(), line.getDataTypeRef().getNarrative(), this.getUserEVO().getName(), Integer.valueOf(this.mVirementRequest.getRequestId()), Integer.valueOf(2), spreadIter}));
            if(line.isSummaryLine()) {
               Iterator spreadIter1 = line.getSpreadProfile().iterator();

               while(spreadIter1.hasNext()) {
                  VirementLineSpreadImpl lineSpread = (VirementLineSpreadImpl)spreadIter1.next();
                  if(line.getTransferValue() != 0.0D) {
                     String note = "Budget Transfer\n" + group.getNotes();
                     sb.append(MessageFormat.format("<CellNote addr=\"{0}\" dataType=\"{1}\" userName=\"{2}\" linkId=\"{3,number,0}\" linkType=\"{4}\"><![CDATA[{5}]]></CellNote>", new Object[]{lineSpread.getAddressAsCSVString(line), line.getDataTypeRef().getNarrative(), this.getUserEVO().getName(), Integer.valueOf(this.mVirementRequest.getRequestId()), Integer.valueOf(2), note}));
                  }
               }
            }
         }
      }

      return sb.toString();
   }

   private int getNumberOfDimensions(int financeCubeId) {
      ModelDAO modelDAO = new ModelDAO();
      FinanceCubeCK fcCK = modelDAO.getFinanceCubeCK(new FinanceCubePK(financeCubeId));
      ModelDimensionsELO modelDimensions = modelDAO.getModelDimensions(fcCK.getModelPK().getModelId());
      return modelDimensions.getNumRows();
   }

   public String getEntityName() {
      return "VirementUpdateEngine";
   }

   private int getUserId() {
      return this.mUserId;
   }

   private int getFinanceCubeId() {
      return this.mVirementRequest.getFinanceCubeId();
   }

   private int getModelId() {
      return ((ModelPK)this.mVirementRequest.getModelRef().getPrimaryKey()).getModelId();
   }

   public void validateVirement() throws ValidationException, SQLException, EJBException {
      ModelPK modelPK = (ModelPK)this.mVirementRequest.getModelRef().getPrimaryKey();
      ModelEVO modelEVO = this.getModelDAO().getDetails(modelPK, "");
      if(!modelEVO.getVirementEntryEnabled()) {
         throw new ValidationException("Budget transfer entry is disabled for model " + modelEVO.getVisId());
      } else {
         int groupIndex = 0;

         for(Iterator gIter = this.mVirementRequest.getVirementGroups().iterator(); gIter.hasNext(); ++groupIndex) {
            VirementGroupImpl group = (VirementGroupImpl)gIter.next();
            int lineIndex = 0;

            for(Iterator lIter = group.getRows().iterator(); lIter.hasNext(); ++lineIndex) {
               VirementLineImpl line = (VirementLineImpl)lIter.next();
               List address = line.getAddress();
               StructureElementRefImpl raRef = (StructureElementRefImpl)address.get(0);
               StructureElementRefImpl acctRef = (StructureElementRefImpl)address.get(address.size() - 2);
               this.checkAddressIsLeaf(address);
               if(!this.checkCOA(modelPK.getModelId(), this.getFinanceCubeId(), line)) {
                  String msg = "Group {0} line {1} cost centre:{2} account:{3} violates chart of account validation.";
                  int addressSize = line.getAddress().size();
                  throw new ValidationException(MessageFormat.format(msg, new Object[]{Integer.valueOf(groupIndex + 1), Integer.valueOf(lineIndex + 1), ((StructureElementRef)line.getAddress().get(0)).getNarrative(), ((StructureElementRef)line.getAddress().get(addressSize - 2)).getNarrative()}));
               }

               this.checkVirement(modelPK.getModelId(), this.getFinanceCubeId(), raRef, acctRef, line.isTo(), Math.abs(line.getTransferValueAsLong()));
            }
         }

      }
   }

   private boolean checkCOA(int modelId, int financeCubeId, VirementLineImpl line) throws ValidationException, SQLException {
      return this.getExternalSystemDAO().checkCOA(modelId, financeCubeId, line.getAddressForCOATest(), this.getUserId());
   }

   private void checkAddressIsLeaf(List address) throws ValidationException {
      for(int i = 0; i < address.size() - 1; ++i) {
         StructureElementRef seRef = (StructureElementRef)address.get(i);
         StructureElementPK sePK = (StructureElementPK)seRef.getPrimaryKey();
         StructureElementEVO seEVO = this.getStructureElementEVO(sePK);
         if(!seEVO.getLeaf()) {
            throw new ValidationException("Budget transfers are not allowed at a summary level");
         }

         if(seEVO.getNotPlannable()) {
            throw new ValidationException("Element " + seEVO.getVisId() + " - " + seEVO.getDescription() + " is defined as not plannable.");
         }

         if(seEVO.getDisabled()) {
            throw new ValidationException("Element " + seEVO.getVisId() + " - " + seEVO.getDescription() + " is defined as disabled.");
         }
      }

   }

   private String generateVirementCheckSQL(int financeCubeId) {
      VirementUpdateEngine$1 tp = new VirementUpdateEngine$1(this, VIREMENT_CHECK_SQL, financeCubeId);
      return tp.parse();
   }

   public void checkVirement(int modelId, int financeCubeId, StructureElementRefImpl raRef, StructureElementRefImpl acctRef, boolean to, long value) throws SQLException, ValidationException {
      if(this.countVirementCategories(modelId) != 0) {
         value = Math.abs(value);
         PreparedStatement ps = null;
         ResultSet rs = null;

         try {
            String sql = this.generateVirementCheckSQL(financeCubeId);
            ps = this.getConnection().prepareStatement(sql);
            int paramNumber = 1;

            int msg;
            for(msg = 1; msg < this.getDimensionCount() - 2; ++msg) {
               ps.setInt(paramNumber++, this.getBusinessDimensionRootElementId(modelId, msg));
            }

            ps.setInt(paramNumber++, this.getAccountDimensionRootElementId(modelId));
            ps.setInt(paramNumber++, this.getCalendarDimensionRootElementId(modelId));

            for(msg = 1; msg < this.getDimensionCount() - 2; ++msg) {
               ps.setInt(paramNumber++, this.getBusinessDimensionRootElementId(modelId, msg));
            }

            ps.setInt(paramNumber++, this.getCalendarDimensionRootElementId(modelId));
            ps.setInt(paramNumber++, acctRef.getStructureElementPK().getStructureElementId());
            ps.setInt(paramNumber++, raRef.getStructureElementPK().getStructureElementId());
            ps.setInt(paramNumber, modelId);
            rs = ps.executeQuery();
            if(!rs.next()) {
               String var40 = "No budget transfer categories defined for {0} {1}";
               throw new ValidationException(MessageFormat.format(var40, new Object[]{raRef.getNarrative(), acctRef.getNarrative()}));
            }

            boolean var41 = false;
            boolean accountAccessChecked = false;

            do {
               String catVisId = rs.getString("cat_vis_id");
               String catSeVisId = rs.getString("cat_se_vis_id");
               long catTranLimit = rs.getLong("cat_tran_limit");
               long catTotalLimitIn = rs.getLong("cat_total_limit_in");
               long catTotalLimitOut = rs.getLong("cat_total_limit_out");
               int numAcctDefs = rs.getInt("num_acct_defs");
               String acctSeVisId = rs.getString("acct_se_vis_id");
               long acctTranLimit = rs.getLong("acct_tran_limit");
               long acctTotalLimitIn = rs.getLong("acct_total_limit_in");
               long acctTotalLimitOut = rs.getLong("acct_total_limit_out");
               String acctInFlag = rs.getString("acct_in_flag");
               String acctOutFlag = rs.getString("acct_out_flag");
               long respPublicValue = rs.getLong("resp_public_value");
               long acctPublicValue = rs.getLong("acct_public_value");
               String msg1;
               if(acctSeVisId != null) {
                  accountAccessChecked = true;
                  if(!var41) {
                     if(!var41) {
                        if(to && !acctInFlag.equalsIgnoreCase("Y") || !to && !acctOutFlag.equalsIgnoreCase("Y")) {
                           msg1 = "Budget transfers {0} {1} {2} are not permitted. (Cat: {3}, Resp: {4}, Acct: {5})";
                           throw new ValidationException(MessageFormat.format(msg1, new Object[]{to?"into":"out of", raRef.getNarrative(), acctRef.getNarrative(), catVisId, catSeVisId, acctSeVisId}));
                        }

                        var41 = true;
                     }

                     if(acctTranLimit < value) {
                        msg1 = "The value of the transfer into {0} {1} is greater than the maximum permitted of £{2}. (Cat: {3}, Resp: {4}, Acct: {5})";
                        throw new ValidationException(MessageFormat.format(msg1, new Object[]{raRef.getNarrative(), acctRef.getNarrative(), Long.valueOf(acctTranLimit), catVisId, catSeVisId, acctSeVisId}));
                     }

                     if(to && acctPublicValue + value > acctTotalLimitIn) {
                        msg1 = "The aggregate value of the transfers into {0} {1} is greater than the maximum permitted of £{2}. (Cat: {3}, Resp: {4}, Acct: {5})";
                        throw new ValidationException(MessageFormat.format(msg1, new Object[]{raRef.getNarrative(), acctRef.getNarrative(), Long.valueOf(acctTotalLimitIn), catVisId, catSeVisId, acctSeVisId}));
                     }

                     if(!to && acctPublicValue - value < -acctTotalLimitOut) {
                        msg1 = "The aggregate value of the transfers out of {0} {1} is greater than the maximum permitted of £{2}. (Cat: {3}, Resp: {4}, Acct: {5})";
                        throw new ValidationException(MessageFormat.format(msg1, new Object[]{raRef.getNarrative(), acctRef.getNarrative(), Long.valueOf(acctTotalLimitOut), catVisId, catSeVisId, acctSeVisId}));
                     }
                  }
               } else if(numAcctDefs != 0 && !accountAccessChecked) {
                  msg1 = "Account access denied on {0} {1}. (Cat: {2})";
                  throw new ValidationException(MessageFormat.format(msg1, new Object[]{raRef.getNarrative(), acctRef.getNarrative(), catVisId}));
               }

               if(catTranLimit < value) {
                  msg1 = "The value of the transfer out of {0} {1} is greater than the maximum permitted of £{2}. (Cat: {3}, Resp: {4})";
                  throw new ValidationException(MessageFormat.format(msg1, new Object[]{raRef.getNarrative(), acctRef.getNarrative(), Long.valueOf(catTranLimit), catVisId, catSeVisId}));
               }

               if(to && respPublicValue + value > catTotalLimitIn) {
                  msg1 = "The aggregate value of the transfers into {0} {1} is greater than the maximum permitted of £{2}. (Cat: {3}, Resp: {4})";
                  throw new ValidationException(MessageFormat.format(msg1, new Object[]{raRef.getNarrative(), acctRef.getNarrative(), Long.valueOf(catTotalLimitIn), catVisId, catSeVisId}));
               }

               if(!to && respPublicValue - value < -catTotalLimitOut) {
                  msg1 = "The aggregate value of the transfers out of {0} {1} is greater than the maximum permitted of £{2}. (Cat: {3}, Resp: {4})";
                  throw new ValidationException(MessageFormat.format(msg1, new Object[]{raRef.getNarrative(), acctRef.getNarrative(), Long.valueOf(catTotalLimitOut), catVisId, catSeVisId}));
               }
            } while(rs.next());
         } finally {
            this.closeResultSet(rs);
            this.closeStatement(ps);
            this.closeConnection();
         }

      }
   }

   public int countVirementCategories(int modelId) throws SQLException {
      PreparedStatement ps = null;
      ResultSet rs = null;

      int var4;
      try {
         ps = this.getConnection().prepareStatement("select count(1) from virement_category where model_id = ?");
         ps.setInt(1, modelId);
         rs = ps.executeQuery();
         if(!rs.next()) {
            byte var8 = 0;
            return var8;
         }

         var4 = rs.getInt(1);
      } finally {
         this.closeResultSet(rs);
         this.closeStatement(ps);
         this.closeConnection();
      }

      return var4;
   }

   public void markRequestAsProcessed() throws Exception {
      VirementRequestCK key = (VirementRequestCK)this.mVirementRequest.getPrimaryKey();
      ModelDAO dao = this.getModelDAO();
      ModelEVO evo = dao.getDetails((ModelCK)key, "<30><31><32><33><34><35>");
      VirementRequestEVO virementEVO = evo.getVirementRequestsItem(key.getVirementRequestPK());
      virementEVO.setRequestStatus(3);
      dao.setDetails(evo);
      dao.store();
   }

   private int getAccountDimensionRootElementId(int modelId) {
      return this.getBusinessDimensionRootElementId(modelId, this.getDimensionCount() - 2);
   }

   private int getCalendarDimensionRootElementId(int modelId) {
      return this.getBusinessDimensionRootElementId(modelId, this.getDimensionCount() - 1);
   }

   private int getBusinessDimensionRootElementId(int modelId, int dimensionIndex) {
      if(this.mModelRoots == null) {
         this.mModelRoots = this.getModelDAO().getAllRootsForModel(modelId);
      }

      for(int row = 0; row < this.mModelRoots.size(); ++row) {
         if(((Integer)this.mModelRoots.getValueAt(row, "DimensionSeqNum")).intValue() == dimensionIndex) {
            return ((Integer)this.mModelRoots.getValueAt(row, "StructureElementId")).intValue();
         }
      }

      throw new IllegalStateException("Unable to locate dimension root for dimension index:" + dimensionIndex);
   }

   private int getDimensionCount(int financeCubeId) {
      if(this.mDimensionCount == 0) {
         this.mDimensionCount = this.getNumberOfDimensions(financeCubeId);
      }

      return this.mDimensionCount;
   }

   private int getDimensionCount() {
      if(this.mDimensionCount == 0) {
         this.mDimensionCount = this.getNumberOfDimensions(this.getFinanceCubeId());
      }

      return this.mDimensionCount;
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

   private ModelDAO getModelDAO() {
      if(this.mModelDAO == null) {
         this.mModelDAO = new ModelDAO();
      }

      return this.mModelDAO;
   }

   private ModelEVO getModelEVO() throws ValidationException {
      if(this.mModelEVO == null) {
         this.mModelEVO = this.getModelDAO().getDetails(new ModelPK(this.getModelId()), "");
      }

      return this.mModelEVO;
   }

   private FinanceCubeEVO getFinanceCubeEVO() throws ValidationException {
      if(this.mFinanceCubeEVO == null) {
         this.mFinanceCubeEVO = this.getFinanceCubeDAO().getDetails(new FinanceCubeCK(new ModelPK(this.getModelId()), new FinanceCubePK(this.getFinanceCubeId())), "");
      }

      return this.mFinanceCubeEVO;
   }

   private FinanceCubeDAO getFinanceCubeDAO() {
      if(this.mFinanceCubeDAO == null) {
         this.mFinanceCubeDAO = new FinanceCubeDAO();
      }

      return this.mFinanceCubeDAO;
   }

   private StructureElementEVO getStructureElementEVO(StructureElementPK sePK) throws ValidationException {
      return this.getStructurElementDAO().getDetails(sePK, "");
   }

   private DataTypeDAO getDataTypeDAO() {
      if(this.mDataTypeDAO == null) {
         this.mDataTypeDAO = new DataTypeDAO();
      }

      return this.mDataTypeDAO;
   }

   private BudgetCycleDAO getBudgetCycleDAO() {
      if(this.mBudgetCycleDAO == null) {
         this.mBudgetCycleDAO = new BudgetCycleDAO();
      }

      return this.mBudgetCycleDAO;
   }

   private BudgetCycleEVO getBudgetCycleEVO() throws ValidationException {
      if(this.mBudgetCycleEVO == null) {
         this.mBudgetCycleEVO = this.getBudgetCycleDAO().getDetails(new BudgetCycleCK(new ModelPK(this.getModelId()), new BudgetCyclePK(this.getBudgetCycleId())), "");
      }

      return this.mBudgetCycleEVO;
   }

   private StructureElementDAO getStructurElementDAO() {
      if(this.mStructureElementDAO == null) {
         this.mStructureElementDAO = new StructureElementDAO();
      }

      return this.mStructureElementDAO;
   }

   private ExternalSystemDAO getExternalSystemDAO() {
      if(this.mExternalSystemDAO == null) {
         this.mExternalSystemDAO = new ExternalSystemDAO();
      }

      return this.mExternalSystemDAO;
   }

   private CalendarInfo getCalendarInfo(int financeCubeId) {
      return this.getStructurElementDAO().getCalendarInfoForFinanceCube(financeCubeId);
   }

   public TaskMessageLogger getMessageLogger() {
      return this.mMessageLogger;
   }

   public void setMessageLogger(TaskMessageLogger messageLogger) {
      this.mMessageLogger = messageLogger;
   }

   public TaskReportWriter getReportWriter() {
      return this.mReportWriter;
   }

   public void setReportWriter(TaskReportWriter reportWriter) {
      this.mReportWriter = reportWriter;
   }

   private int getBudgetCycleId() {
      return this.mVirementRequest.getBudgetCycleId();
   }

   private List queryDimensionHeaders() throws Exception {
      AllDimensionsForModelELO dims = (new DimensionDAO()).getAllDimensionsForModel(this.getModelId());
      ArrayList dimHeaders = new ArrayList();

      for(int i = 0; i < dims.getNumRows(); ++i) {
         dimHeaders.add(dims.getValueAt(i, "Dimension"));
      }

      return dimHeaders;
   }

   private TaskReport getTaskReport() {
      return this.mTaskReport;
   }

   private NumberFormat getNumberFormatter() {
      if(this.mNumberFormat == null) {
         this.mNumberFormat = NumberFormat.getInstance();
         this.mNumberFormat.setMinimumFractionDigits(2);
         this.mNumberFormat.setMaximumFractionDigits(2);
      }

      return this.mNumberFormat;
   }

   private NumberFormat getOutputNumberFormatter() {
      if(this.mOutputFormat == null) {
         this.mOutputFormat = new DecimalFormat();
         this.mOutputFormat.setMaximumFractionDigits(4);
      }

      return this.mOutputFormat;
   }

   public void setTaskId(Integer taskId) {
      this.mTaskId = taskId;
   }

   public Integer getTaskId() {
      return this.mTaskId;
   }

   // $FF: synthetic method
   static int accessMethod000(VirementUpdateEngine x0, int x1) {
      return x0.getDimensionCount(x1);
   }

}
