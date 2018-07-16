// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.extsys.ExtSysHierElemFeedEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElementDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElementEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalHierarchyProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalHierarchyElementProcessor extends AbstractElementProcessor {

   private int mChildIndex;
   private int mBatchCount;
   private int mBatchLimit = 5000;
   private PreparedStatement mBatchInsertStatement;
   private ExtSysHierElementEVO mExtSysHierElementEVO;
   private ExtSysHierElementDAO mExtSysHierElementDAO;


   public ExternalHierarchyElementProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("hierarchyElement")) {
         throw new SAXException("Expected element \'hierarchyElement\'");
      } else {
         this.mChildIndex = 0;
         this.mExtSysHierElementEVO = new ExtSysHierElementEVO();
         this.mExtSysHierElementEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysHierElementEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysHierElementEVO.setLedgerVisId(this.getExternalSystemDefLoader().getExternalLedgerVisId());
         this.mExtSysHierElementEVO.setDimensionVisId(this.getExternalSystemDefLoader().getExternalDimensionVisId());
         this.mExtSysHierElementEVO.setHierarchyVisId(this.getExternalSystemDefLoader().getExternalHierarchyVisId());
         this.mExtSysHierElementEVO.setHierElementVisId(this.getAttributeStringValue(attributes, "hierarchyElementVisId"));
         this.mExtSysHierElementEVO.setDescription(this.getAttributeStringValue(attributes, "description"));
         this.mExtSysHierElementEVO.setParentVisId(this.getParentVisId());
         ExternalHierarchyProcessor processor = (ExternalHierarchyProcessor)this.getExternalSystemDefLoader().findElementProcessor(ExternalHierarchyProcessor.class);
         this.mExtSysHierElementEVO.setIdx(processor.getChildIndex());
         processor.addToBatch(this.mExtSysHierElementEVO);
      }
   }

   private String getParentVisId() {
      ExternalHierarchyElementProcessor processor = (ExternalHierarchyElementProcessor)this.getExternalSystemDefLoader().findElementProcessor(ExternalHierarchyElementProcessor.class);
      return processor != null?processor.getExternalHierarchyElementVisId():null;
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysHierElementEVO = null;
      if(this.mBatchCount > 0) {
         this.executeBatch();
      }

      this.closeConnection();
   }

   private void prepareBatch() throws SAXException {
      try {
         this.mBatchInsertStatement = this.getConnection().prepareStatement("insert into ext_sys_hier_elem_feed( external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id, hierarchy_vis_id, hier_element_vis_id, dim_element_vis_id, idx ) values ( ?, ?, ?, ?, ?, ?, ?, ?  )");
      } catch (SQLException var2) {
         throw new SAXException("Failed to perpare insert ext sys hier elem feed statement.", var2);
      }
   }

   public void addToBatch(ExtSysHierElemFeedEVO extSysHierElemFeedEVO) throws SAXException {
      try {
         if(this.mBatchInsertStatement == null) {
            this.prepareBatch();
         }

         byte e = 1;
         int var5 = e + 1;
         this.mBatchInsertStatement.setInt(e, extSysHierElemFeedEVO.getExternalSystemId());
         this.mBatchInsertStatement.setString(var5++, extSysHierElemFeedEVO.getCompanyVisId());
         this.mBatchInsertStatement.setString(var5++, extSysHierElemFeedEVO.getLedgerVisId());
         this.mBatchInsertStatement.setString(var5++, extSysHierElemFeedEVO.getDimensionVisId());
         this.mBatchInsertStatement.setString(var5++, extSysHierElemFeedEVO.getHierarchyVisId());
         this.mBatchInsertStatement.setString(var5++, extSysHierElemFeedEVO.getHierElementVisId());
         this.mBatchInsertStatement.setString(var5++, extSysHierElemFeedEVO.getDimElementVisId());
         this.mBatchInsertStatement.setInt(var5++, extSysHierElemFeedEVO.getIdx());
         this.mBatchInsertStatement.addBatch();
         ++this.mBatchCount;
         ++this.mChildIndex;
         ExternalHierarchyProcessor hierProcessor = (ExternalHierarchyProcessor)this.getExternalSystemDefLoader().findElementProcessor(ExternalHierarchyProcessor.class);
         hierProcessor.incInsertCount();
         this.registerKey(new Object[]{Integer.valueOf(extSysHierElemFeedEVO.getExternalSystemId()), extSysHierElemFeedEVO.getCompanyVisId(), extSysHierElemFeedEVO.getLedgerVisId(), extSysHierElemFeedEVO.getDimensionVisId(), extSysHierElemFeedEVO.getHierarchyVisId(), extSysHierElemFeedEVO.getHierElementVisId(), extSysHierElemFeedEVO.getDimElementVisId(), Integer.valueOf(extSysHierElemFeedEVO.getIdx())});
         if(this.mBatchCount >= this.mBatchLimit) {
            this.executeBatch();
         }

      } catch (SQLException var4) {
         throw new SAXException("Failed to add to batch.", var4);
      }
   }

   private void executeBatch() throws SAXException {
      try {
         int[] e = this.mBatchInsertStatement.executeBatch();
         this.mBatchCount = 0;
         this.closeStatement(this.mBatchInsertStatement);
         this.mBatchInsertStatement = null;
         this.clearKeys();
      } catch (BatchUpdateException var4) {
         int[] updateCounts = var4.getUpdateCounts();

         for(int i = 0; i < updateCounts.length; ++i) {
            if(updateCounts[i] <= 0 && updateCounts[i] != -2) {
               throw new IllegalStateException("Failed to insert hierarchy element feed : " + this.getKey(i));
            }
         }
      } catch (SQLException var5) {
         throw new SAXException("Failed to execute batch insert into ext hier element:", var5);
      }

   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExySysHierElementDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   public String getExternalHierarchyElementVisId() {
      return this.mExtSysHierElementEVO != null?this.mExtSysHierElementEVO.getHierElementVisId():null;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }

   private ExtSysHierElementDAO getExySysHierElementDAO() {
      if(this.mExtSysHierElementDAO == null) {
         this.mExtSysHierElementDAO = new ExtSysHierElementDAO();
      }

      return this.mExtSysHierElementDAO;
   }
}
