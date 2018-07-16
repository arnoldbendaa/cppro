// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.DefaultQElementPickerModel;
import com.cedar.awt.DefaultQTableElementPickerModel$1;
import com.cedar.awt.QElementPickerModel;
import com.cedar.awt.QTableElementPickerModel;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DefaultQTableElementPickerModel implements QTableElementPickerModel {

   private QElementPickerModel mElementPickerModel = this.createDefaultTreeModel();
   private TableModel mUserTableModel = this.createDefaultUserTableModel();
   private TableModel mModelTableModel = this.createDefaultModelTableModel();
   private PropertyChangeSupport mChangeSupport = new PropertyChangeSupport(this);
   public static final String DIMENSION_LIST = "Dimension_List";
   public static final String MODEL_LIST = "Model_List";
   public static final String USER_LIST = "User_List";
   public static final String RA_LIST = "RA_List";


   public TableModel getModelTableModel() {
      return this.mModelTableModel;
   }

   public TableModel getUserTableModel() {
      return this.mUserTableModel;
   }

   public QElementPickerModel getResponsibilityAreaModel() {
      return this.mElementPickerModel;
   }

   public QElementPickerModel getResponsibilityAreaModel(DimensionRef dimRef) {
      return null;
   }

   public List getDimensionList() {
      ArrayList l = new ArrayList();
      return l;
   }

   public void setSelectedUsers(List userList) {}

   public List getSelectedUsers() {
      return null;
   }

   public void setSelectedModels(List modelsList) {}

   public List getSelectedModels() {
      return null;
   }

   public void setSelectedElements(List userList) {}

   public List getSelectedElements() {
      return null;
   }

   public DimensionRef getSelectedDimesionRef() {
      return null;
   }

   public void setDimensionModels() {}

   public ModelRef getSelectedModelRef() {
      return null;
   }

   public void setSelectedModelRef(ModelRef modelRef) {}

   public void getResponsibilityData() {}

   public void setSelectedDimensionRef(DimensionRef dimRef) {
      DefaultQTableElementPickerModel$1 t = new DefaultQTableElementPickerModel$1(this);
      t.start();
   }

   private TableModel createDefaultUserTableModel() {
      Object[] modelTable = new Object[]{"Model"};
      return new DefaultTableModel(modelTable, 2);
   }

   private TableModel createDefaultModelTableModel() {
      Object[] userTable = new Object[]{"User"};
      return new DefaultTableModel(userTable, 2);
   }

   private QElementPickerModel createDefaultTreeModel() {
      return new DefaultQElementPickerModel();
   }

   public void addPropertyChangeListener(PropertyChangeListener pcl) {
      this.mChangeSupport.addPropertyChangeListener(pcl);
   }

   public void removePropertyChangeListner(PropertyChangeListener pcl) {
      this.mChangeSupport.removePropertyChangeListener(pcl);
   }

   // $FF: synthetic method
   static TableModel accessMethod002(DefaultQTableElementPickerModel x0, TableModel x1) {
      return x0.mModelTableModel = x1;
   }

   // $FF: synthetic method
   static PropertyChangeSupport accessMethod100(DefaultQTableElementPickerModel x0) {
      return x0.mChangeSupport;
   }
}
