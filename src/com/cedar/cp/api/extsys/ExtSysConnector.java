// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extsys;

import java.util.Properties;
import javax.swing.table.TableModel;

public interface ExtSysConnector {

   void startup(Properties var1) throws Exception;

   TableModel queryDrillback(String var1, String var2, String[] var3, String[] var4, String[] var5, Integer var6, String[] var7, Integer var8, String var9, String var10, String var11, StringBuilder var12, Properties var13) throws Exception;

   void closedown() throws Exception;
}
