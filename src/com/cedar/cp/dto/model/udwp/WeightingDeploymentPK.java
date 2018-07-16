/*     */ package com.cedar.cp.dto.model.udwp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class WeightingDeploymentPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mDeploymentId;
/*     */ 
/*     */   public WeightingDeploymentPK(int newDeploymentId)
/*     */   {
/*  23 */     this.mDeploymentId = newDeploymentId;
/*     */   }
/*     */ 
/*     */   public int getDeploymentId()
/*     */   {
/*  32 */     return this.mDeploymentId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mDeploymentId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     WeightingDeploymentPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof WeightingDeploymentCK)) {
/*  56 */       other = ((WeightingDeploymentCK)obj).getWeightingDeploymentPK();
/*     */     }
/*  58 */     else if ((obj instanceof WeightingDeploymentPK))
/*  59 */       other = (WeightingDeploymentPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mDeploymentId == other.mDeploymentId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" DeploymentId=");
/*  77 */     sb.append(this.mDeploymentId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mDeploymentId);
/*  89 */     return "WeightingDeploymentPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static WeightingDeploymentPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("WeightingDeploymentPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'WeightingDeploymentPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pDeploymentId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new WeightingDeploymentPK(pDeploymentId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.udwp.WeightingDeploymentPK
 * JD-Core Version:    0.6.0
 */