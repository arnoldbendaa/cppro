// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;


public class CellState {

   public static final int PROTECTED = 1;
   public static final int LEAF_2DINTERSECTION = 2;
   public static final int DERIVED = 4;
   public static final int LINKED = 8;
   public static final int CALCULATOR = 16;
   public static final int NOTE = 32;
   public static final int NUMBER = 64;
   private static final int ALLOCATEABLE = 768;
   public static final int BASIC_ALLOCATEABLE = 256;
   public static final int POWER_ALLOCATEABLE = 512;
   public static final int REPEATABLE_RIGHT = 4096;
   public static final int REPEATABLE_LEFT = 8192;
   public static final int REPEATABLE_DOWN = 16384;
   public static final int REPEATABLE_UP = 32768;
   private static final int REPEATABLE = 61440;


   public static final boolean isCellProtected(int cellState) {
      return (cellState & 1) == 1;
   }

   public static final boolean isCell2DLeafIntersection(int cellState) {
      return (cellState & 2) == 2;
   }

   public static final boolean isCellAllocateable(int cellState) {
      return (cellState & 768) > 0;
   }

   public static final boolean isCellBasicAllocateable(int cellState) {
      return (cellState & 256) == 256;
   }

   public static final boolean isCellEntryRepeatable(int cellState) {
      return (cellState & '\uf000') > 0;
   }

   public static final boolean isCellRepeatableRight(int cellState) {
      return (cellState & 4096) == 4096;
   }

   public static final boolean isCellRepeatableLeft(int cellState) {
      return (cellState & 8192) == 8192;
   }

   public static final boolean isCellRepeatableDown(int cellState) {
      return (cellState & 16384) == 16384;
   }

   public static final boolean isCellRepeatableUp(int cellState) {
      return (cellState & '\u8000') == '\u8000';
   }

   public static final boolean isCellDerived(int cellState) {
      return (cellState & 4) == 4;
   }

   public static final boolean isCellLinked(int cellState) {
      return (cellState & 8) == 8;
   }

   public static final boolean isCellCalculator(int cellState) {
      return (cellState & 16) == 16;
   }

   public static final boolean isCellNote(int cellState) {
      return (cellState & 32) == 32;
   }

   public static final boolean isCellNumber(int cellState) {
      return (cellState & 64) == 64;
   }
}
