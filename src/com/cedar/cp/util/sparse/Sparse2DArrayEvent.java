// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.sparse;

import com.cedar.cp.util.sparse.Sparse2DArray;
import java.util.EventObject;

public class Sparse2DArrayEvent extends EventObject {

   public static final int INSERT = 1;
   public static final int UPDATE = 0;
   public static final int DELETE = -1;
   public static final int ALL_COLUMNS = -1;
   protected int type;
   protected int firstRow;
   protected int lastRow;
   protected int column;


   public Sparse2DArrayEvent(Sparse2DArray source) {
      this(source, 0, Integer.MAX_VALUE, -1, 0);
   }

   public Sparse2DArrayEvent(Sparse2DArray source, int row) {
      this(source, row, row, -1, 0);
   }

   public Sparse2DArrayEvent(Sparse2DArray source, int firstRow, int lastRow) {
      this(source, firstRow, lastRow, -1, 0);
   }

   public Sparse2DArrayEvent(Sparse2DArray source, int firstRow, int lastRow, int column) {
      this(source, firstRow, lastRow, column, 0);
   }

   public Sparse2DArrayEvent(Sparse2DArray source, int firstRow, int lastRow, int column, int type) {
      super(source);
      this.firstRow = firstRow;
      this.lastRow = lastRow;
      this.column = column;
      this.type = type;
   }

   public int getFirstRow() {
      return this.firstRow;
   }

   public int getLastRow() {
      return this.lastRow;
   }

   public int getColumn() {
      return this.column;
   }

   public int getType() {
      return this.type;
   }
}
