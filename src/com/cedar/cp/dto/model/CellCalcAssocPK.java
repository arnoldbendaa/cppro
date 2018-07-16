/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CellCalcAssocPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCellCalcAssocId;
/*     */ 
/*     */   public CellCalcAssocPK(int newCellCalcAssocId)
/*     */   {
/*  23 */     this.mCellCalcAssocId = newCellCalcAssocId;
/*     */   }
/*     */ 
/*     */   public int getCellCalcAssocId()
/*     */   {
/*  32 */     return this.mCellCalcAssocId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCellCalcAssocId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CellCalcAssocPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CellCalcAssocCK)) {
/*  56 */       other = ((CellCalcAssocCK)obj).getCellCalcAssocPK();
/*     */     }
/*  58 */     else if ((obj instanceof CellCalcAssocPK))
/*  59 */       other = (CellCalcAssocPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCellCalcAssocId == other.mCellCalcAssocId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CellCalcAssocId=");
/*  77 */     sb.append(this.mCellCalcAssocId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCellCalcAssocId);
/*  89 */     return "CellCalcAssocPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CellCalcAssocPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CellCalcAssocPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CellCalcAssocPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCellCalcAssocId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CellCalcAssocPK(pCellCalcAssocId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.CellCalcAssocPK
 * JD-Core Version:    0.6.0
 */