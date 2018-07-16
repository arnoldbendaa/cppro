// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.DataTypeColumnValue;
import com.cedar.cp.util.xmlform.FixedColumnValue;
import com.cedar.cp.util.xmlform.StructureColumnValue;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class FinanceCubeInput extends DefaultMutableTreeNode implements XMLWritable {

   public static final int FINANCE_DATA_SCALE_FACTOR = 10000;
   public static final String VARIABLES_KEY = "cedar.financeCubeInput.key";
   private String mId;
   private List mColumnValues = new ArrayList();
   private String mCubeVisId;
   private int mCubeId;
   private int mModelId;


   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
      this.setUserObject("FinanceCubeInput id=" + this.mId);
   }

   public String getCubeVisId() {
      return this.mCubeVisId;
   }

   public void setCubeVisId(String id) {
      this.mCubeVisId = id;
   }

   public void setCubeId(int id) {
      this.mCubeId = id;
   }

   public int getCubeId() {
      return this.mCubeId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void addFixedColumnValue(FixedColumnValue value) {
      this.mColumnValues.add(0, value);
      this.insert(value, 0);
   }

   public void addStructureColumnValue(StructureColumnValue value) {
      this.mColumnValues.add(value);
      this.add(value);
   }

   public void addDataTypeColumnValue(DataTypeColumnValue value) {
      this.mColumnValues.add(value);
      this.add(value);
   }

   public List getColumnValues() {
      return Collections.unmodifiableList(this.mColumnValues);
   }

   public List getAxisDimensionIndexes() {
      ArrayList results = new ArrayList();
      Iterator iter = this.mColumnValues.iterator();

      while(iter.hasNext()) {
         Object o = iter.next();
         if(o instanceof StructureColumnValue) {
            StructureColumnValue column = (StructureColumnValue)o;
            results.add(new Integer(column.getDim()));
         }
      }

      return results;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<financeCubeInput ");
      out.write(" id=\"" + this.mId + "\"");
      out.write(" cubeId=\"" + this.mCubeId + "\"");
      out.write(" cubeVisId=\"" + XmlUtils.escapeStringForXML(this.mCubeVisId) + "\">");
      Iterator iter = this.mColumnValues.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</financeCubeInput>");
   }
}
