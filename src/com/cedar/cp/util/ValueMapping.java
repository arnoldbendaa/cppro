// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.util.List;

public interface ValueMapping {

   Object getValue(Object var1);

   Object getLiteral(Object var1);

   List getLiterals();

   List getValues();
}
