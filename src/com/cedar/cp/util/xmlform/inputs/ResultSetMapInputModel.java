// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.xmlform.inputs.ResultSetInputModel;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetMapInputModel extends ResultSetInputModel {

   private Map mMapping = new HashMap();


   public ResultSetMapInputModel(ResultSet resultSet) {
      super(resultSet);

      for(int i = 0; i < this.getRowCount(); ++i) {
         this.mMapping.put(this.getValueAt(i, 0).toString(), this.getValueAt(i, 1));
      }

   }

   public ResultSetMapInputModel(String[] columnNames, Class[] columnClasses, List<Object[]> resultSet) {
      super(columnNames, columnClasses, resultSet);

      for(int i = 0; i < this.getRowCount(); ++i) {
         this.mMapping.put(this.getValueAt(i, 0).toString(), this.getValueAt(i, 1));
      }

   }

   public Map getMapping() {
      return Collections.unmodifiableMap(this.mMapping);
   }

   public int getSheetProtectionLevel() {
      return 0;
   }
}
