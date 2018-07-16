/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FormulaDeploymentDtPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mFormulaDeploymentDtId;
/*     */ 
/*     */   public FormulaDeploymentDtPK(int newFormulaDeploymentDtId)
/*     */   {
/*  23 */     this.mFormulaDeploymentDtId = newFormulaDeploymentDtId;
/*     */   }
/*     */ 
/*     */   public int getFormulaDeploymentDtId()
/*     */   {
/*  32 */     return this.mFormulaDeploymentDtId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mFormulaDeploymentDtId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     FormulaDeploymentDtPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof FormulaDeploymentDtCK)) {
/*  56 */       other = ((FormulaDeploymentDtCK)obj).getFormulaDeploymentDtPK();
/*     */     }
/*  58 */     else if ((obj instanceof FormulaDeploymentDtPK))
/*  59 */       other = (FormulaDeploymentDtPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mFormulaDeploymentDtId == other.mFormulaDeploymentDtId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" FormulaDeploymentDtId=");
/*  77 */     sb.append(this.mFormulaDeploymentDtId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mFormulaDeploymentDtId);
/*  89 */     return "FormulaDeploymentDtPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static FormulaDeploymentDtPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("FormulaDeploymentDtPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'FormulaDeploymentDtPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pFormulaDeploymentDtId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new FormulaDeploymentDtPK(pFormulaDeploymentDtId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.FormulaDeploymentDtPK
 * JD-Core Version:    0.6.0
 */