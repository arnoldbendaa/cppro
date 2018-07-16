// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalElementEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalendarYearDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalendarYearEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalCalendarYearProcessor extends AbstractElementProcessor {

   private int mChildIndex;
   private int mBatchCount;
   private int mBatchLimit = 5000;
   private PreparedStatement mBatchInsertStatement;
   private ExtSysCalendarYearEVO mExtSysCalendarYearEVO;
   private ExtSysCalendarYearDAO mExtSysCalendarYearDAO;


   public ExternalCalendarYearProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("calendarYear")) {
         throw new SAXException("Expected element \'calendarYear\'");
      } else {
         this.mExtSysCalendarYearEVO = new ExtSysCalendarYearEVO();
         this.mExtSysCalendarYearEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysCalendarYearEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysCalendarYearEVO.setYear(this.getAttributeIntValue(attributes, "year"));
         this.mExtSysCalendarYearEVO.setCalendarYearVisId(this.getAttributeStringValue(attributes, "calendarYearVisId"));

         try {
            this.getExtSysCalendarYearDAO().insert(this.mExtSysCalendarYearEVO);
         } catch (DuplicateNameValidationException var6) {
            throw new SAXException("Duplicate calendar year:" + this.mExtSysCalendarYearEVO.getCalendarYearVisId());
         } catch (ValidationException var7) {
            throw new SAXException(var7.getMessage(), var7);
         }

         this.mBatchCount = 0;
         this.mChildIndex = 0;
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysCalendarYearEVO = null;
      if(this.mBatchCount > 0) {
         this.executeBatch();
      }

      this.closeConnection();
   }

   private void prepareBatch() throws SAXException {
      try {
         this.mBatchInsertStatement = this.getConnection().prepareStatement("insert into ext_sys_cal_element( external_system_id, company_vis_id, calendar_year_vis_id, cal_element_vis_id, calendar_year_id, description, idx ) values ( ?, ?, ?, ?, ?, ?, ? )");
      } catch (SQLException var2) {
         throw new SAXException("Failed to perpare insert ext sys dim element statement.", var2);
      }
   }

   public void addToBatch(ExtSysCalElementEVO extSysCalElementEVO) throws SAXException {
      try {
         if(this.mBatchInsertStatement == null) {
            this.prepareBatch();
         }

         byte e = 1;
         int var4 = e + 1;
         this.mBatchInsertStatement.setInt(e, extSysCalElementEVO.getExternalSystemId());
         this.mBatchInsertStatement.setString(var4++, extSysCalElementEVO.getCompanyVisId());
         this.mBatchInsertStatement.setString(var4++, extSysCalElementEVO.getCalendarYearVisId());
         this.mBatchInsertStatement.setString(var4++, extSysCalElementEVO.getCalElementVisId());
         this.mBatchInsertStatement.setInt(var4++, extSysCalElementEVO.getCalendarYearId());
         this.mBatchInsertStatement.setString(var4++, extSysCalElementEVO.getDescription());
         this.mBatchInsertStatement.setInt(var4++, extSysCalElementEVO.getIdx());
         this.mBatchInsertStatement.addBatch();
         ++this.mBatchCount;
         ++this.mChildIndex;
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
         throw new SAXException("Failed to execute batch insert into ext sys cal element:", var2);
      }
   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysCalendarYearDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   private ExtSysCalendarYearDAO getExtSysCalendarYearDAO() {
      if(this.mExtSysCalendarYearDAO == null) {
         this.mExtSysCalendarYearDAO = new ExtSysCalendarYearDAO();
      }

      return this.mExtSysCalendarYearDAO;
   }

   public String getExternalCalendarYearVisId() {
      return this.mExtSysCalendarYearEVO != null?this.mExtSysCalendarYearEVO.getCalendarYearVisId():null;
   }

   public int getChildIndex() {
      return this.mChildIndex;
   }
}
