// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimElementEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimensionEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalDimensionProcessor extends AbstractElementProcessor {

   private int mInsertCount;
   private int mBatchCount;
   private int mBatchLimit = 5000;
   private PreparedStatement mBatchInsertStatement;
   private ExtSysDimensionEVO mExtSysDimensionEVO;
   private ExtSysDimensionDAO mExtSysDimensionDAO;


   public ExternalDimensionProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("dimension")) {
         throw new SAXException("Expected element \'valueType\'");
      } else {
         this.mExtSysDimensionEVO = new ExtSysDimensionEVO();
         this.mExtSysDimensionEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysDimensionEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysDimensionEVO.setLedgerVisId(this.getExternalSystemDefLoader().getExternalLedgerVisId());
         this.mExtSysDimensionEVO.setDimensionVisId(this.getAttributeStringValue(attributes, "dimensionVisId"));
         this.mExtSysDimensionEVO.setDescription(this.getAttributeStringValue(attributes, "description"));
         this.mExtSysDimensionEVO.setDimensionType(this.decodeDimensionType(this.getAttributeStringValue(attributes, "dimensionType")));
         this.mExtSysDimensionEVO.setImportColumnIndex(this.getAttributeIntValue(attributes, "importColumnIndex"));
         this.log("Importing dimension " + this.mExtSysDimensionEVO.getDimensionVisId() + " - " + this.mExtSysDimensionEVO.getDescription());

         try {
            this.getExtSysDimensionDAO().insert(this.mExtSysDimensionEVO);
         } catch (DuplicateNameValidationException var6) {
            throw new SAXException("Duplicate dimension:" + this.mExtSysDimensionEVO.getDimensionVisId());
         } catch (ValidationException var7) {
            throw new SAXException(var7.getMessage(), var7);
         }

         this.mBatchCount = 0;
         this.mInsertCount = 0;
      }
   }

   private int decodeDimensionType(String value) throws SAXException {
      if(value.equalsIgnoreCase("business")) {
         return 2;
      } else if(value.equalsIgnoreCase("account")) {
         return 1;
      } else {
         throw new SAXException("Invalid dimension type specified must be one of: \'business\', \'account\'");
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mBatchCount > 0) {
         this.executeBatch();
      }

      this.closeConnection();
      this.log("Imported " + this.mInsertCount + " dimension elements into " + this.mExtSysDimensionEVO.getDimensionVisId());
      this.mBatchCount = 0;
      this.mInsertCount = 0;
      this.mExtSysDimensionEVO = null;
   }

   private void prepareBatch() throws SAXException {
      try {
         this.mBatchInsertStatement = this.getConnection().prepareStatement("insert into ext_sys_dim_element( external_system_id, company_vis_id, ledger_vis_id, dimension_vis_id, dim_element_vis_id, description, credit_debit, disabled, not_plannable ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?  )");
      } catch (SQLException var2) {
         throw new SAXException("Failed to perpare insert ext sys dim element statement.", var2);
      }
   }

   public void addToBatch(ExtSysDimElementEVO extSysDimElementEVO) throws SAXException {
      try {
         if(this.mBatchInsertStatement == null) {
            this.prepareBatch();
         }

         byte e = 1;
         int var4 = e + 1;
         this.mBatchInsertStatement.setInt(e, extSysDimElementEVO.getExternalSystemId());
         this.mBatchInsertStatement.setString(var4++, extSysDimElementEVO.getCompanyVisId());
         this.mBatchInsertStatement.setString(var4++, extSysDimElementEVO.getLedgerVisId());
         this.mBatchInsertStatement.setString(var4++, extSysDimElementEVO.getDimensionVisId());
         this.mBatchInsertStatement.setString(var4++, extSysDimElementEVO.getDimElementVisId());
         this.mBatchInsertStatement.setString(var4++, extSysDimElementEVO.getDescription());
         this.mBatchInsertStatement.setInt(var4++, extSysDimElementEVO.getCreditDebit());
         this.mBatchInsertStatement.setString(var4++, extSysDimElementEVO.getDisabled()?"Y":"N");
         this.mBatchInsertStatement.setString(var4++, extSysDimElementEVO.getNotPlannable()?"Y":"N");
         this.mBatchInsertStatement.addBatch();
         ++this.mBatchCount;
         ++this.mInsertCount;
         if(this.mBatchCount >= this.mBatchLimit) {
            this.executeBatch();
         }

      } catch (SQLException var3) {
         throw new SAXException("Failed to add to batch.", var3);
      }
   }

   private void executeBatch() throws SAXException {
      try {
         int[] e = this.mBatchInsertStatement.executeBatch();
         this.mBatchCount = 0;
         this.closeStatement(this.mBatchInsertStatement);
         this.mBatchInsertStatement = null;
      } catch (SQLException var2) {
         throw new SAXException("Failed to execute batch insert into ext dim dim element:", var2);
      }
   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysDimensionDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   private ExtSysDimensionDAO getExtSysDimensionDAO() {
      if(this.mExtSysDimensionDAO == null) {
         this.mExtSysDimensionDAO = new ExtSysDimensionDAO();
      }

      return this.mExtSysDimensionDAO;
   }

   public String getExternalDimensionVisId() {
      return this.mExtSysDimensionEVO != null?this.mExtSysDimensionEVO.getDimensionVisId():null;
   }
}
