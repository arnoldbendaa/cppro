/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CubeFormulaPackagePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCubeFormulaPackageId;
/*     */ 
/*     */   public CubeFormulaPackagePK(int newCubeFormulaPackageId)
/*     */   {
/*  23 */     this.mCubeFormulaPackageId = newCubeFormulaPackageId;
/*     */   }
/*     */ 
/*     */   public int getCubeFormulaPackageId()
/*     */   {
/*  32 */     return this.mCubeFormulaPackageId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCubeFormulaPackageId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CubeFormulaPackagePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CubeFormulaPackageCK)) {
/*  56 */       other = ((CubeFormulaPackageCK)obj).getCubeFormulaPackagePK();
/*     */     }
/*  58 */     else if ((obj instanceof CubeFormulaPackagePK))
/*  59 */       other = (CubeFormulaPackagePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCubeFormulaPackageId == other.mCubeFormulaPackageId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CubeFormulaPackageId=");
/*  77 */     sb.append(this.mCubeFormulaPackageId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCubeFormulaPackageId);
/*  89 */     return "CubeFormulaPackagePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CubeFormulaPackagePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CubeFormulaPackagePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CubeFormulaPackagePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCubeFormulaPackageId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CubeFormulaPackagePK(pCubeFormulaPackageId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.CubeFormulaPackagePK
 * JD-Core Version:    0.6.0
 */