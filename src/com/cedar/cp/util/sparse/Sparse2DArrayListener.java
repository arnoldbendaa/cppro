// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.sparse;

import com.cedar.cp.util.sparse.Sparse2DArrayEvent;
import java.util.EventListener;

public interface Sparse2DArrayListener extends EventListener {

   void sparse2DArrayChanged(Sparse2DArrayEvent var1);
}
