// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.LookupInput;
import com.cedar.cp.util.xmlform.RowInput;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.cedar.cp.util.xmlform.swing.FinanceFormTableModel;
import com.cedar.cp.util.xmlform.swing.FormTableModel;
import com.cedar.cp.util.xmlform.swing.FormTreeModel;
import com.cedar.cp.util.xmlform.swing.FormulaParseException;
import com.cedar.cp.util.xmlform.swing.NonTableFormTableModel;

public class FormModel {

   private FormConfig mConfig;
   private FormTableModel mTableModel;
   private FormTreeModel mTreeModel;
   private transient Log mLog;


   public FormModel(FormConfig config) throws FormulaParseException {
      this(config, false);
   }

   public FormModel(FormConfig config, boolean ignoreParseErrors) throws FormulaParseException {
      this.mLog = new Log(this.getClass());
      this.mConfig = config;
      switch(config.getType()) {
      case 1:
         this.mTableModel = new NonTableFormTableModel();
         break;
      case 2:
         this.mTableModel = new NonTableFormTableModel();
         break;
      case 3:
         this.mTableModel = new FinanceFormTableModel();
      }

      try {
         this.mTableModel.rebuildFromConfig(config);
      } catch (FormulaParseException var4) {
         if(!ignoreParseErrors) {
            throw var4;
         }

         this.mLog.debug("Detected Formula Parse Error " + var4);
      }

      this.mTreeModel = new FormTreeModel(config);
      this.mTreeModel.setFormModel(this);
   }

   public FormDataInputModel getNonProtectedValues() {
      return this.mTableModel.getNonProtectedValues();
   }

   public void inputChangedEvent(RowInput input) {
      this.mLog.debug("Something has changed in the input area of the config");
   }

   public void inputChangedEvent(FinanceCubeInput input) {
      this.mLog.debug("Something has changed in the input area of the config");
   }

   public void inputChangedEvent(LookupInput input) {
      this.mLog.debug("Something has changed in the input area of the config");
   }

   public FormConfig getFormConfig() {
      return this.mConfig;
   }

   public FormTableModel getFormTableModel() {
      return this.mTableModel;
   }

   public FormTreeModel getFormTreeModel() {
      return this.mTreeModel;
   }

   public void setFormTreeModelChanged() {}
}
