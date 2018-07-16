// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.SqlUtils;
import com.cedar.cp.util.xmlform.InputColumnKey;
import com.cedar.cp.util.xmlform.InputColumnValue;
import com.cedar.cp.util.xmlform.Subset;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class LookupInput extends DefaultMutableTreeNode implements XMLWritable {

   private String mId;
   private String mLookupName;
   private String mKey;
   private String mValue;
   private String mPartitionColumn;
   private String mScenario;
   private String mDate;
   private String mEndDate;
   private List<Subset> mSubsets = new ArrayList();


   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
      this.setUserObject("LookupInput id=" + this.mId);
   }

   public void addColumnKey(InputColumnKey key) {
      this.mLookupName = key.getValue();
   }

   public void addColumnValue(InputColumnValue value) {
      if(this.mKey == null) {
         this.mKey = value.getName();
      } else {
         this.mValue = value.getName();
      }

   }

   public String getLookupName() {
      return this.mLookupName;
   }

   public String getLookupTableName() {
      return "UD_" + SqlUtils.generateTableName(this.mLookupName, 27);
   }

   public void setLookupName(String lookupName) {
      this.mLookupName = lookupName;
   }

   public String getKey() {
      return this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public String getScenario() {
      return this.mScenario;
   }

   public void setScenario(String scenario) {
      if(scenario.length() == 0) {
         this.mScenario = null;
      } else {
         this.mScenario = scenario;
      }

   }

   public String getDate() {
      return this.mDate;
   }

   public void setDate(String date) {
      if(date.length() == 0) {
         this.mDate = null;
      } else {
         this.mDate = date;
      }

   }

   public String getEndDate() {
      return this.mEndDate;
   }

   public void setEndDate(String date) {
      if(date.length() == 0) {
         this.mEndDate = null;
      } else {
         this.mEndDate = date;
      }

   }

   public void addSubset(Subset subset) {
      this.mSubsets.add(subset);
      this.add(subset);
   }

   public void removeSubset(Subset subset) {
      this.mSubsets.remove(subset);
      this.remove(subset);
   }

   public List<Subset> getSubsets() {
      return Collections.unmodifiableList(this.mSubsets);
   }

   public String getPartitionColumn() {
      return this.mPartitionColumn;
   }

   public void setPartitionColumn(String partitionColumn) {
      this.mPartitionColumn = partitionColumn;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<lookupInput ");
      out.write(" id=\"" + this.mId + "\"");
      out.write(" lookupName=\"" + this.mLookupName + "\"");
      out.write(" key=\"" + this.mKey + "\"");
      out.write(" value=\"" + this.mValue + "\" ");
      if(this.mScenario != null) {
         out.write(" scenario=\"" + this.mScenario + "\"");
      }

      if(this.mDate != null) {
         out.write(" date=\"" + this.mDate + "\"");
      }

      if(this.mEndDate != null) {
         out.write(" endDate=\"" + this.mEndDate + "\"");
      }

      if(this.mPartitionColumn != null) {
         out.write(" partitionColumn=\"" + this.mPartitionColumn + "\"");
      }

      if(this.mSubsets.isEmpty()) {
         out.write("/>");
      } else {
         out.write(">");
         Iterator i$ = this.mSubsets.iterator();

         while(i$.hasNext()) {
            Subset s = (Subset)i$.next();
            s.writeXml(out);
         }

         out.write("</lookupInput>");
      }

   }
}
