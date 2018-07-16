// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.destination.internal;

import com.cedar.cp.api.base.EntityList;
import java.util.List;

public interface InternalDestination {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   int getMessageType();

   List getUserList();

   EntityList getAvailableUsers();
}
