// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.datatype.DataTypeRef;
import java.io.Serializable;
import java.util.List;

public interface VirementGroup extends Serializable {

   String getKeyAsText();

   Object getKey();

   String getNotes();

   long getRemainder(DataTypeRef var1);

   List getRows();
}
