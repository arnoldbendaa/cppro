/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CubeFormulaPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCubeFormulaId;
/*     */ 
/*     */   public CubeFormulaPK(int newCubeFormulaId)
/*     */   {
/*  23 */     this.mCubeFormulaId = newCubeFormulaId;
/*     */   }
/*     */ 
/*     */   public int getCubeFormulaId()
/*     */   {
/*  32 */     return this.mCubeFormulaId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCubeFormulaId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CubeFormulaPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CubeFormulaCK)) {
/*  56 */       other = ((CubeFormulaCK)obj).getCubeFormulaPK();
/*     */     }
/*  58 */     else if ((obj instanceof CubeFormulaPK))
/*  59 */       other = (CubeFormulaPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCubeFormulaId == other.mCubeFormulaId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CubeFormulaId=");
/*  77 */     sb.append(this.mCubeFormulaId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCubeFormulaId);
/*  89 */     return "CubeFormulaPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CubeFormulaPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CubeFormulaPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CubeFormulaPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCubeFormulaId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CubeFormulaPK(pCubeFormulaId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.CubeFormulaPK
 * JD-Core Version:    0.6.0
 */