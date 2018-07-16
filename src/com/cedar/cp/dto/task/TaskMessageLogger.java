// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task;

import com.cedar.cp.dto.base.PrimaryKey;
import java.util.List;
import javax.naming.InitialContext;

public interface TaskMessageLogger {

   void log(String var1) throws Exception;

   void logInfo(String var1);

   void log(List var1) throws Exception;

   void sendEntityEventMessage(InitialContext var1, String var2, PrimaryKey var3, int var4);
}
