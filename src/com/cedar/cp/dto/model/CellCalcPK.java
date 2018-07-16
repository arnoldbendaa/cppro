/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CellCalcPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCellCalcId;
/*     */ 
/*     */   public CellCalcPK(int newCellCalcId)
/*     */   {
/*  23 */     this.mCellCalcId = newCellCalcId;
/*     */   }
/*     */ 
/*     */   public int getCellCalcId()
/*     */   {
/*  32 */     return this.mCellCalcId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCellCalcId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CellCalcPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CellCalcCK)) {
/*  56 */       other = ((CellCalcCK)obj).getCellCalcPK();
/*     */     }
/*  58 */     else if ((obj instanceof CellCalcPK))
/*  59 */       other = (CellCalcPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCellCalcId == other.mCellCalcId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CellCalcId=");
/*  77 */     sb.append(this.mCellCalcId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCellCalcId);
/*  89 */     return "CellCalcPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CellCalcPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CellCalcPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CellCalcPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCellCalcId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CellCalcPK(pCellCalcId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.CellCalcPK
 * JD-Core Version:    0.6.0
 */