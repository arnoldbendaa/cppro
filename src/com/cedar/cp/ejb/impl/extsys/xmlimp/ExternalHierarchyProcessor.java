// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElementEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierarchyDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierarchyEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalHierarchyProcessor extends AbstractElementProcessor {

   private int mChildIndex;
   private int mInsertCount;
   private int mBatchCount;
   private int mBatchLimit = 5000;
   private PreparedStatement mBatchInsertStatement;
   private ExtSysHierarchyEVO mExtSysHierarchyEVO;
   private ExtSysHierarchyDAO mExtSysHierarchyDAO;


   public ExternalHierarchyProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("hierarchy")) {
         throw new SAXException("Expected element \'hierarchy\'");
      } else {
         this.mChildIndex = 0;
         this.mInsertCount = 0;
         this.mBatchCount = 0;
         this.mExtSysHierarchyEVO = new ExtSysHierarchyEVO();
         this.mExtSysHierarchyEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysHierarchyEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysHierarchyEVO.setLedgerVisId(this.getExternalSystemDefLoader().getExternalLedgerVisId());
         this.mExtSysHierarchyEVO.setDimensionVisId(this.getExternalSystemDefLoader().getExternalDimensionVisId());
         this.mExtSysHierarchyEVO.setHierarchyVisId(this.getAttributeStringValue(attributes, "hierarchyVisId"));
         this.mExtSysHierarchyEVO.setDescription(this.getAttributeStringValue(attributes, "description"));

         try {
            this.getExtSysHierarchyDAO().insert(this.mExtSysHierarchyEVO);
         } catch (DuplicateNameValidationException var6) {
            throw new SAXException("Duplicate hierarchy element:" + this.mExtSysHierarchyEVO.getHierarchyVisId());
         } catch (ValidationException var7) {
            throw new SAXException(var7.getMessage(), var7);
         }

         this.log("Importing hierarchy : " + this.mExtSysHierarchyEVO.getHierarchyVisId() + " - " + this.mExtSysHierarchyEVO.getDescription());
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mBatchCount > 0) {
         this.executeBatch();
      }

      this.closeConnection();
      this.log("Imported " + this.mInsertCount + " hierarchy elements into " + this.mExtSysHierarchyEVO.getHierarchyVisId());
      this.mExtSysHierarchyEVO = null;
      this.mInsertCount = 0;
      this.mBatchCount = 0;
      this.mChildIndex = 0;
   }

   private void prepareBatch() throws SAXException {
      try {
         this.mBatchInsertStatement = this.getConnection().prepareStatement("insert into ext_sys_hier_element( external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id, hierarchy_vis_id, hier_element_vis_id, description, parent_vis_id, idx, credit_debit ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  )");
      } catch (SQLException var2) {
         throw new SAXException("Failed to perpare insert ext sys dim element statement.", var2);
      }
   }

   public void addToBatch(ExtSysHierElementEVO extSysHierElementEVO) throws SAXException {
      try {
         if(this.mBatchInsertStatement == null) {
            this.prepareBatch();
         }

         byte e = 1;
         int var4 = e + 1;
         this.mBatchInsertStatement.setInt(e, extSysHierElementEVO.getExternalSystemId());
         this.mBatchInsertStatement.setString(var4++, extSysHierElementEVO.getCompanyVisId());
         this.mBatchInsertStatement.setString(var4++, extSysHierElementEVO.getLedgerVisId());
         this.mBatchInsertStatement.setString(var4++, extSysHierElementEVO.getDimensionVisId());
         this.mBatchInsertStatement.setString(var4++, extSysHierElementEVO.getHierarchyVisId());
         this.mBatchInsertStatement.setString(var4++, extSysHierElementEVO.getHierElementVisId());
         this.mBatchInsertStatement.setString(var4++, extSysHierElementEVO.getDescription());
         this.mBatchInsertStatement.setString(var4++, extSysHierElementEVO.getParentVisId());
         this.mBatchInsertStatement.setInt(var4++, extSysHierElementEVO.getIdx());
         this.mBatchInsertStatement.setInt(var4++, extSysHierElementEVO.getCreditDebit());
         this.mBatchInsertStatement.addBatch();
         ++this.mBatchCount;
         ++this.mChildIndex;
         ++this.mInsertCount;
         this.registerKey(new Object[]{Integer.valueOf(extSysHierElementEVO.getExternalSystemId()), extSysHierElementEVO.getCompanyVisId(), extSysHierElementEVO.getLedgerVisId(), extSysHierElementEVO.getDimensionVisId(), extSysHierElementEVO.getHierarchyVisId(), extSysHierElementEVO.getHierElementVisId()});
         if(this.mBatchCount >= this.mBatchLimit) {
            this.executeBatch();
         }

      } catch (SQLException var3) {
         throw new SAXException("Failed to add to batch.", var3);
      }
   }

   private void executeBatch() throws SAXException {
      Object status = null;

      try {
         int[] var7 = this.mBatchInsertStatement.executeBatch();
         this.mBatchCount = 0;
         this.closeStatement(this.mBatchInsertStatement);
         this.mBatchInsertStatement = null;
         this.clearKeys();
      } catch (BatchUpdateException var5) {
         int[] updateCounts = var5.getUpdateCounts();

         for(int i = 0; i < updateCounts.length; ++i) {
            if(updateCounts[i] <= 0 && updateCounts[i] != -2) {
               throw new IllegalStateException("Failed to insert hierarchy element feed : " + this.getKey(i));
            }
         }
      } catch (SQLException var6) {
         throw new SAXException("Failed to execute batch insert into ext hier element:", var6);
      }

   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysHierarchyDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   private ExtSysHierarchyDAO getExtSysHierarchyDAO() {
      if(this.mExtSysHierarchyDAO == null) {
         this.mExtSysHierarchyDAO = new ExtSysHierarchyDAO();
      }

      return this.mExtSysHierarchyDAO;
   }

   public String getExternalHierarchyVisId() {
      return this.mExtSysHierarchyEVO != null?this.mExtSysHierarchyEVO.getHierarchyVisId():null;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }

   public void incInsertCount() {
      ++this.mInsertCount;
   }
}
