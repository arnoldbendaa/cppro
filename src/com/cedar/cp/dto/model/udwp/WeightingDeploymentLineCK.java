/*     */ package com.cedar.cp.dto.model.udwp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class WeightingDeploymentLineCK extends WeightingDeploymentCK
/*     */   implements Serializable
/*     */ {
/*     */   protected WeightingDeploymentLinePK mWeightingDeploymentLinePK;
/*     */ 
/*     */   public WeightingDeploymentLineCK(ModelPK paramModelPK, WeightingProfilePK paramWeightingProfilePK, WeightingDeploymentPK paramWeightingDeploymentPK, WeightingDeploymentLinePK paramWeightingDeploymentLinePK)
/*     */   {
/*  34 */     super(paramModelPK, paramWeightingProfilePK, paramWeightingDeploymentPK);
/*     */ 
/*  39 */     this.mWeightingDeploymentLinePK = paramWeightingDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public WeightingDeploymentLinePK getWeightingDeploymentLinePK()
/*     */   {
/*  47 */     return this.mWeightingDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  55 */     return this.mWeightingDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  63 */     return this.mWeightingDeploymentLinePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  72 */     if ((obj instanceof WeightingDeploymentLinePK)) {
/*  73 */       return obj.equals(this);
/*     */     }
/*  75 */     if (!(obj instanceof WeightingDeploymentLineCK)) {
/*  76 */       return false;
/*     */     }
/*  78 */     WeightingDeploymentLineCK other = (WeightingDeploymentLineCK)obj;
/*  79 */     boolean eq = true;
/*     */ 
/*  81 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  82 */     eq = (eq) && (this.mWeightingProfilePK.equals(other.mWeightingProfilePK));
/*  83 */     eq = (eq) && (this.mWeightingDeploymentPK.equals(other.mWeightingDeploymentPK));
/*  84 */     eq = (eq) && (this.mWeightingDeploymentLinePK.equals(other.mWeightingDeploymentLinePK));
/*     */ 
/*  86 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  94 */     StringBuffer sb = new StringBuffer();
/*  95 */     sb.append(super.toString());
/*  96 */     sb.append("[");
/*  97 */     sb.append(this.mWeightingDeploymentLinePK);
/*  98 */     sb.append("]");
/*  99 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 107 */     StringBuffer sb = new StringBuffer();
/* 108 */     sb.append("WeightingDeploymentLineCK|");
/* 109 */     sb.append(super.getPK().toTokens());
/* 110 */     sb.append('|');
/* 111 */     sb.append(this.mWeightingDeploymentLinePK.toTokens());
/* 112 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 117 */     String[] token = extKey.split("[|]");
/* 118 */     int i = 0;
/* 119 */     checkExpected("WeightingDeploymentLineCK", token[(i++)]);
/* 120 */     checkExpected("ModelPK", token[(i++)]);
/* 121 */     i++;
/* 122 */     checkExpected("WeightingProfilePK", token[(i++)]);
/* 123 */     i++;
/* 124 */     checkExpected("WeightingDeploymentPK", token[(i++)]);
/* 125 */     i++;
/* 126 */     checkExpected("WeightingDeploymentLinePK", token[(i++)]);
/* 127 */     i = 1;
/* 128 */     return new WeightingDeploymentLineCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), WeightingProfilePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), WeightingDeploymentPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), WeightingDeploymentLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 138 */     if (!expected.equals(found))
/* 139 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.udwp.WeightingDeploymentLineCK
 * JD-Core Version:    0.6.0
 */