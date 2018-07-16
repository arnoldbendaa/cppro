// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import java.net.URL;
import javax.swing.table.TableModel;

public interface ResourceStorage {

   int storeResource(String var1, byte[] var2);

   URL getResourceURL(int var1);

   TableModel getResources();
}
