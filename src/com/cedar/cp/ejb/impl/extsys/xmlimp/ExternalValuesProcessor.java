// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExtSysImportRow;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalValuesProcessor extends AbstractElementProcessor {

   private String INSERT_SQL = "insert into ext_sys_import_row ( \texternal_system_id, \tcompany_vis_id, \tledger_vis_id, \tcalendar_year_vis_id, \tvis_id0, \tvis_id1, \tvis_id2, \tvis_id3, \tvis_id4, \tvis_id5, \tvis_id6, \tvis_id7, \tvis_id8, \tvis_id9, \tcurrency_vis_id, \tvalue_type_vis_id, \tvalue ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
   private int mChildIndex;
   private int mBatchCount;
   private int mBatchLimit = 5000;
   private PreparedStatement mBatchInsertStatement;
   private String mCompanyVisId;
   private String mLedgerVisId;


   public ExternalValuesProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("values")) {
         throw new SAXException("Expected element \'values\'");
      } else {
         this.mCompanyVisId = this.getAttributeStringValue(attributes, "companyVisId");
         this.mLedgerVisId = this.getAttributeStringValue(attributes, "ledgerVisId");
         this.mBatchCount = 0;
         this.mChildIndex = 0;
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mBatchCount > 0) {
         this.executeBatch();
      }

      this.closeConnection();
      this.log("Imported " + this.mChildIndex + " values rows for company: " + this.mCompanyVisId + " ledger: " + this.mLedgerVisId);
      this.mCompanyVisId = null;
      this.mLedgerVisId = null;
   }

   private void prepareBatch() throws SAXException {
      try {
         this.mBatchInsertStatement = this.getConnection().prepareStatement(this.INSERT_SQL);
      } catch (SQLException var2) {
         throw new SAXException("Failed to perpare insert ext sys dim element statement.", var2);
      }
   }

   public void addToBatch(ExtSysImportRow extSysImportRow) throws SAXException {
      try {
         if(this.mBatchInsertStatement == null) {
            this.prepareBatch();
         }

         byte e = 1;
         int var6 = e + 1;
         this.mBatchInsertStatement.setInt(e, extSysImportRow.getExternalSystemId());
         this.mBatchInsertStatement.setString(var6++, extSysImportRow.getCompanyVisId());
         this.mBatchInsertStatement.setString(var6++, extSysImportRow.getLedgerVisId());
         this.mBatchInsertStatement.setString(var6++, extSysImportRow.getCalendarYearVisId());
         String[] visIds = extSysImportRow.getVisId();

         for(int i = 0; i < 10; ++i) {
            if(visIds[i] != null) {
               this.mBatchInsertStatement.setString(var6++, visIds[i]);
            } else {
               this.mBatchInsertStatement.setNull(var6++, 12);
            }
         }

         this.mBatchInsertStatement.setString(var6++, extSysImportRow.getCurrencyVisId());
         this.mBatchInsertStatement.setString(var6++, extSysImportRow.getValueTypeVisId());
         this.mBatchInsertStatement.setLong(var6++, extSysImportRow.getValue());
         this.mBatchInsertStatement.addBatch();
         ++this.mBatchCount;
         ++this.mChildIndex;
         if(this.mBatchCount >= this.mBatchLimit) {
            this.executeBatch();
         }

      } catch (SQLException var5) {
         throw new SAXException("Failed to add to batch.", var5);
      }
   }

   private void executeBatch() throws SAXException {
      try {
         int[] e = this.mBatchInsertStatement.executeBatch();
         this.mBatchCount = 0;
         this.closeStatement(this.mBatchInsertStatement);
         this.mBatchInsertStatement = null;
      } catch (SQLException var2) {
         throw new SAXException("Failed to execute batch insert into ext sys cal element:", var2);
      }
   }

   public String getCompanyVisId() {
      return this.mCompanyVisId;
   }

   public String getLedgerVisId() {
      return this.mLedgerVisId;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }
}
