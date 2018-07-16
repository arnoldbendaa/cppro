/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcDeploymentLineCK extends CcDeploymentCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CcDeploymentLinePK mCcDeploymentLinePK;
/*     */ 
/*     */   public CcDeploymentLineCK(ModelPK paramModelPK, CcDeploymentPK paramCcDeploymentPK, CcDeploymentLinePK paramCcDeploymentLinePK)
/*     */   {
/*  32 */     super(paramModelPK, paramCcDeploymentPK);
/*     */ 
/*  36 */     this.mCcDeploymentLinePK = paramCcDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public CcDeploymentLinePK getCcDeploymentLinePK()
/*     */   {
/*  44 */     return this.mCcDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mCcDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mCcDeploymentLinePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof CcDeploymentLinePK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof CcDeploymentLineCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     CcDeploymentLineCK other = (CcDeploymentLineCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mCcDeploymentPK.equals(other.mCcDeploymentPK));
/*  80 */     eq = (eq) && (this.mCcDeploymentLinePK.equals(other.mCcDeploymentLinePK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mCcDeploymentLinePK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("CcDeploymentLineCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mCcDeploymentLinePK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("CcDeploymentLineCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("CcDeploymentPK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("CcDeploymentLinePK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new CcDeploymentLineCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcDeploymentPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcDeploymentLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcDeploymentLineCK
 * JD-Core Version:    0.6.0
 */