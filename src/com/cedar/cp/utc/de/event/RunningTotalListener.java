// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.de.event;

import com.cedar.cp.utc.de.event.RunningTotalEvent;
import java.util.EventListener;

public interface RunningTotalListener extends EventListener {

   void runningTotalChanged(RunningTotalEvent var1);
}
