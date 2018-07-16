/*     */ package com.cedar.cp.dto.model.cc.imp.dyn;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ImportGridCellCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ImportGridCellPK mImportGridCellPK;
/*     */ 
/*     */   public ImportGridCellCK(ImportGridCellPK paramImportGridCellPK)
/*     */   {
/*  26 */     this.mImportGridCellPK = paramImportGridCellPK;
/*     */   }
/*     */ 
/*     */   public ImportGridCellPK getImportGridCellPK()
/*     */   {
/*  34 */     return this.mImportGridCellPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mImportGridCellPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mImportGridCellPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ImportGridCellPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ImportGridCellCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ImportGridCellCK other = (ImportGridCellCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mImportGridCellPK.equals(other.mImportGridCellPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mImportGridCellPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ImportGridCellCK|");
/*  92 */     sb.append(this.mImportGridCellPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ImportGridCellCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ImportGridCellCK", token[(i++)]);
/* 101 */     checkExpected("ImportGridCellPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ImportGridCellCK(ImportGridCellPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellCK
 * JD-Core Version:    0.6.0
 */