/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FormulaDeploymentEntryPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mFormulaDeploymentEntryId;
/*     */ 
/*     */   public FormulaDeploymentEntryPK(int newFormulaDeploymentEntryId)
/*     */   {
/*  23 */     this.mFormulaDeploymentEntryId = newFormulaDeploymentEntryId;
/*     */   }
/*     */ 
/*     */   public int getFormulaDeploymentEntryId()
/*     */   {
/*  32 */     return this.mFormulaDeploymentEntryId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mFormulaDeploymentEntryId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     FormulaDeploymentEntryPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof FormulaDeploymentEntryCK)) {
/*  56 */       other = ((FormulaDeploymentEntryCK)obj).getFormulaDeploymentEntryPK();
/*     */     }
/*  58 */     else if ((obj instanceof FormulaDeploymentEntryPK))
/*  59 */       other = (FormulaDeploymentEntryPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mFormulaDeploymentEntryId == other.mFormulaDeploymentEntryId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" FormulaDeploymentEntryId=");
/*  77 */     sb.append(this.mFormulaDeploymentEntryId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mFormulaDeploymentEntryId);
/*  89 */     return "FormulaDeploymentEntryPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static FormulaDeploymentEntryPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("FormulaDeploymentEntryPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'FormulaDeploymentEntryPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pFormulaDeploymentEntryId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new FormulaDeploymentEntryPK(pFormulaDeploymentEntryId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryPK
 * JD-Core Version:    0.6.0
 */