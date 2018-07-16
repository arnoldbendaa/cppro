// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.definition;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.report.definition.ReportDefinition;
import com.cedar.cp.dto.report.definition.ReportDefinitionImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.impl.report.definition.ReportDefinitionEditorSessionImpl;
import java.util.ArrayList;
import java.util.List;

public class ReportDefinitionAdapter implements ReportDefinition {

   private ReportDefinitionImpl mEditorData;
   private ReportDefinitionEditorSessionImpl mEditorSessionImpl;


   public ReportDefinitionAdapter(ReportDefinitionEditorSessionImpl e, ReportDefinitionImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ReportDefinitionEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ReportDefinitionImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ReportDefinitionPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public int getReportTypeId() {
      return this.mEditorData.getReportTypeId();
   }

   public boolean isIsPublic() {
      return this.mEditorData.isIsPublic();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setReportTypeId(int p) {
      this.mEditorData.setReportTypeId(p);
   }

   public void setIsPublic(boolean p) {
      this.mEditorData.setIsPublic(p);
   }

   public List getReportParams() {
      if(this.mEditorData.getReportParams() == null) {
         EntityList eList = this.getReportTypeParams();
         int size = eList.getNumRows();
         ArrayList data = new ArrayList(size);

         for(int i = 0; i < size; ++i) {
            ArrayList row = new ArrayList(3);
            row.add(eList.getRowData(i));
            row.add("");
            int rowType = ((Integer)eList.getValueAt(i, "Control")).intValue();
            switch(rowType) {
            case 2:
               row.add(Boolean.FALSE);
               break;
            default:
               row.add("");
            }

            row.add(Boolean.FALSE);
            data.add(row);
         }

         this.mEditorData.setReportParams(data);
      }

      return this.mEditorData.getReportParams();
   }

   public EntityList getReportTypes() {
      if(this.mEditorData.getReportTypes() == null) {
         EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getAllReportTypes();
         this.mEditorData.setTypes(list);
      }

      return this.mEditorData.getReportTypes();
   }

   public String getReportTypeVisId() {
      if(this.mEditorData.getReportTypeVisId() == null || this.mEditorData.getReportTypeVisId().length() == 0) {
         EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getAllReportTypes();

         for(int i = 0; i < list.getNumRows(); ++i) {
            Integer id = (Integer)list.getValueAt(i, "ReportTypeId");
            EntityRef ref = (EntityRef)list.getValueAt(i, "ReportType");
            if(id.intValue() == this.getReportTypeId()) {
               this.mEditorData.setReportTypeVisId(ref.getNarrative());
               break;
            }
         }
      }

      return this.mEditorData.getReportTypeVisId();
   }

   public EntityList getReportTypeParams() {
      if(this.mEditorData.getReportTypeParams() == null) {
         EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getAllReportTypeParamsforType(this.mEditorData.getReportTypeId());
         this.mEditorData.setTypeParams(list);
      }

      return this.mEditorData.getReportTypeParams();
   }
}
