/*    */ package com.cedar.cp.ejb.impl.model.cc.imp;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public enum CellCalcUpdateType
/*    */   implements Serializable
/*    */ {
/* 14 */   INSERT, 
/*    */ 
/* 19 */   MERGE, 
/*    */ 
/* 24 */   UPDATE, 
/*    */ 
/* 29 */   REPLACE, 
/*    */ 
/* 34 */   DELETE;
/*    */ 
/*    */   public static CellCalcUpdateType parse(String s)
/*    */   {
/* 38 */     if (s.equalsIgnoreCase("insert"))
/* 39 */       return INSERT;
/* 40 */     if (s.equalsIgnoreCase("merge"))
/* 41 */       return MERGE;
/* 42 */     if (s.equalsIgnoreCase("update"))
/* 43 */       return UPDATE;
/* 44 */     if (s.equalsIgnoreCase("replace"))
/* 45 */       return REPLACE;
/* 46 */     if (s.equalsIgnoreCase("delete"))
/* 47 */       return DELETE;
/* 48 */     throw new IllegalStateException("Unable to parse to cell calc update type:" + s);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUpdateType
 * JD-Core Version:    0.6.0
 */