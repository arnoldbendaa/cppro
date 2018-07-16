// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.sqlmon;

import java.io.Serializable;

public interface SqlDetails extends Serializable {

   String getDetails();

   String getSql();
}
