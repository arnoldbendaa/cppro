// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.sparse;

import com.cedar.cp.util.sparse.Sparse2DArrayListener;
import java.util.Iterator;

public interface Sparse2DArray<T extends Object> {

   boolean isEmpty();

   T get(int var1, int var2);

   void put(int var1, int var2, T var3);

   T remove(int var1, int var2);

   int getRowCount();

   int getColumnCount();

   Iterator iterator();

   Iterator rowIterator(int var1);

   Iterator columnIterator(int var1);

   Iterator rangeIterator(int var1, int var2, int var3, int var4);

   void addSparse2DArrayListener(Sparse2DArrayListener var1);

   void removeSparse2DArrayListener(Sparse2DArrayListener var1);
}
