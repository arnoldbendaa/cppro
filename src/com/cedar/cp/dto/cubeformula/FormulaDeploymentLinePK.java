/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FormulaDeploymentLinePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mFormulaDeploymentLineId;
/*     */ 
/*     */   public FormulaDeploymentLinePK(int newFormulaDeploymentLineId)
/*     */   {
/*  23 */     this.mFormulaDeploymentLineId = newFormulaDeploymentLineId;
/*     */   }
/*     */ 
/*     */   public int getFormulaDeploymentLineId()
/*     */   {
/*  32 */     return this.mFormulaDeploymentLineId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mFormulaDeploymentLineId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     FormulaDeploymentLinePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof FormulaDeploymentLineCK)) {
/*  56 */       other = ((FormulaDeploymentLineCK)obj).getFormulaDeploymentLinePK();
/*     */     }
/*  58 */     else if ((obj instanceof FormulaDeploymentLinePK))
/*  59 */       other = (FormulaDeploymentLinePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mFormulaDeploymentLineId == other.mFormulaDeploymentLineId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" FormulaDeploymentLineId=");
/*  77 */     sb.append(this.mFormulaDeploymentLineId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mFormulaDeploymentLineId);
/*  89 */     return "FormulaDeploymentLinePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static FormulaDeploymentLinePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("FormulaDeploymentLinePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'FormulaDeploymentLinePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pFormulaDeploymentLineId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new FormulaDeploymentLinePK(pFormulaDeploymentLineId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK
 * JD-Core Version:    0.6.0
 */