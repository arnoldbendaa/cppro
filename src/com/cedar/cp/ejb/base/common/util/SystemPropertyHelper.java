// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.common.util;

import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import java.sql.Connection;

public class SystemPropertyHelper {

   public static boolean queryBooleanSystemProperty(Connection con, String varName, boolean defaultValue) {
      String value = queryStringSystemProperty(con, varName, defaultValue?Boolean.TRUE.toString():Boolean.FALSE.toString());
      return Boolean.valueOf(value).booleanValue();
   }

   public static int queryIntegerSystemProperty(Connection con, String varName, int defaultValue) {
      SystemPropertyDAO sysDAO = con != null?new SystemPropertyDAO(con):new SystemPropertyDAO();
      SystemPropertyELO elo = sysDAO.getSystemProperty(varName);
      String batchSize = null;
      int result;
      if(elo != null && elo.size() > 0 && (batchSize = (String)elo.getValueAt(0, "Value")) != null) {
         try {
            result = Integer.valueOf(batchSize).intValue();
         } catch (NumberFormatException var8) {
            throw new IllegalStateException("Integer System Property:" + varName + " malformed");
         }
      } else {
         result = defaultValue;
      }

      return result;
   }

   public static String queryStringSystemProperty(Connection con, String varName, String defaultValue) {
      SystemPropertyDAO sysDAO = con != null?new SystemPropertyDAO(con):new SystemPropertyDAO();
      SystemPropertyELO elo = sysDAO.getSystemProperty(varName);
      String value = defaultValue;
      if(elo != null && elo.size() > 0) {
         value = (String)elo.getValueAt(0, "Value");
      }

      return value;
   }
}
