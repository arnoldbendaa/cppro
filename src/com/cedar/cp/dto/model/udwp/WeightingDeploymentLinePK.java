/*     */ package com.cedar.cp.dto.model.udwp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class WeightingDeploymentLinePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mDeploymentId;
/*     */   int mLineIdx;
/*     */ 
/*     */   public WeightingDeploymentLinePK(int newDeploymentId, int newLineIdx)
/*     */   {
/*  24 */     this.mDeploymentId = newDeploymentId;
/*  25 */     this.mLineIdx = newLineIdx;
/*     */   }
/*     */ 
/*     */   public int getDeploymentId()
/*     */   {
/*  34 */     return this.mDeploymentId;
/*     */   }
/*     */ 
/*     */   public int getLineIdx()
/*     */   {
/*  41 */     return this.mLineIdx;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mDeploymentId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mLineIdx).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     WeightingDeploymentLinePK other = null;
/*     */ 
/*  65 */     if ((obj instanceof WeightingDeploymentLineCK)) {
/*  66 */       other = ((WeightingDeploymentLineCK)obj).getWeightingDeploymentLinePK();
/*     */     }
/*  68 */     else if ((obj instanceof WeightingDeploymentLinePK))
/*  69 */       other = (WeightingDeploymentLinePK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mDeploymentId == other.mDeploymentId);
/*  76 */     eq = (eq) && (this.mLineIdx == other.mLineIdx);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" DeploymentId=");
/*  88 */     sb.append(this.mDeploymentId);
/*  89 */     sb.append(",LineIdx=");
/*  90 */     sb.append(this.mLineIdx);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mDeploymentId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mLineIdx);
/* 104 */     return "WeightingDeploymentLinePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static WeightingDeploymentLinePK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("WeightingDeploymentLinePK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'WeightingDeploymentLinePK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pDeploymentId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pLineIdx = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new WeightingDeploymentLinePK(pDeploymentId, pLineIdx);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.udwp.WeightingDeploymentLinePK
 * JD-Core Version:    0.6.0
 */