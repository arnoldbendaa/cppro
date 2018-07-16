// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.QElementPickerModel;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.table.TableModel;

public interface QTableElementPickerModel {

   TableModel getModelTableModel();

   TableModel getUserTableModel();

   QElementPickerModel getResponsibilityAreaModel(DimensionRef var1);

   QElementPickerModel getResponsibilityAreaModel();

   List getDimensionList();

   void setSelectedUsers(List var1);

   List getSelectedUsers();

   void setSelectedModels(List var1);

   List getSelectedModels();

   void setSelectedElements(List var1);

   List getSelectedElements();

   DimensionRef getSelectedDimesionRef();

   void setSelectedDimensionRef(DimensionRef var1);

   ModelRef getSelectedModelRef();

   void setSelectedModelRef(ModelRef var1);

   void setDimensionModels();

   void getResponsibilityData();

   void addPropertyChangeListener(PropertyChangeListener var1);

   void removePropertyChangeListner(PropertyChangeListener var1);
}
