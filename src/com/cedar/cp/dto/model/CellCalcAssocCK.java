/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CellCalcAssocCK extends CellCalcCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CellCalcAssocPK mCellCalcAssocPK;
/*     */ 
/*     */   public CellCalcAssocCK(ModelPK paramModelPK, CellCalcPK paramCellCalcPK, CellCalcAssocPK paramCellCalcAssocPK)
/*     */   {
/*  31 */     super(paramModelPK, paramCellCalcPK);
/*     */ 
/*  35 */     this.mCellCalcAssocPK = paramCellCalcAssocPK;
/*     */   }
/*     */ 
/*     */   public CellCalcAssocPK getCellCalcAssocPK()
/*     */   {
/*  43 */     return this.mCellCalcAssocPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mCellCalcAssocPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mCellCalcAssocPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof CellCalcAssocPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof CellCalcAssocCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     CellCalcAssocCK other = (CellCalcAssocCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  78 */     eq = (eq) && (this.mCellCalcPK.equals(other.mCellCalcPK));
/*  79 */     eq = (eq) && (this.mCellCalcAssocPK.equals(other.mCellCalcAssocPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mCellCalcAssocPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("CellCalcAssocCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mCellCalcAssocPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("CellCalcAssocCK", token[(i++)]);
/* 115 */     checkExpected("ModelPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("CellCalcPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("CellCalcAssocPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new CellCalcAssocCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CellCalcPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CellCalcAssocPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.CellCalcAssocCK
 * JD-Core Version:    0.6.0
 */