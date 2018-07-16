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

public class ResultSetMultiMapInputModel extends ResultSetInputModel {

   private Map<String, Map<String, Object>> mMapping;


   public ResultSetMultiMapInputModel(ResultSet resultSet) {
      super(resultSet);
      this.buildMaps();
   }

   private void buildMaps() {
      this.mMapping = new HashMap();
      HashMap currentMap = null;
      String currentPartitonKey = null;

      for(int i = 0; i < this.getRowCount(); ++i) {
         String partitionKey = this.getValueAt(i, 0).toString();
         if(!partitionKey.equals(currentPartitonKey)) {
            currentMap = new HashMap();
            this.mMapping.put(partitionKey, currentMap);
            currentPartitonKey = partitionKey;
         }

         currentMap.put(this.getValueAt(i, 1).toString(), this.getValueAt(i, 2));
      }

   }

   public ResultSetMultiMapInputModel(String[] columnNames, Class[] columnClasses, List<Object[]> resultSet) {
      super(columnNames, columnClasses, resultSet);
      this.buildMaps();
   }

   public Map getMapping(String partitionKey) {
      return this.mMapping.keySet().contains(partitionKey)?Collections.unmodifiableMap((Map)this.mMapping.get(partitionKey)):null;
   }
}
