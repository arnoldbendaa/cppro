/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcDeploymentEntryCK extends CcDeploymentLineCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CcDeploymentEntryPK mCcDeploymentEntryPK;
/*     */ 
/*     */   public CcDeploymentEntryCK(ModelPK paramModelPK, CcDeploymentPK paramCcDeploymentPK, CcDeploymentLinePK paramCcDeploymentLinePK, CcDeploymentEntryPK paramCcDeploymentEntryPK)
/*     */   {
/*  34 */     super(paramModelPK, paramCcDeploymentPK, paramCcDeploymentLinePK);
/*     */ 
/*  39 */     this.mCcDeploymentEntryPK = paramCcDeploymentEntryPK;
/*     */   }
/*     */ 
/*     */   public CcDeploymentEntryPK getCcDeploymentEntryPK()
/*     */   {
/*  47 */     return this.mCcDeploymentEntryPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  55 */     return this.mCcDeploymentEntryPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  63 */     return this.mCcDeploymentEntryPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  72 */     if ((obj instanceof CcDeploymentEntryPK)) {
/*  73 */       return obj.equals(this);
/*     */     }
/*  75 */     if (!(obj instanceof CcDeploymentEntryCK)) {
/*  76 */       return false;
/*     */     }
/*  78 */     CcDeploymentEntryCK other = (CcDeploymentEntryCK)obj;
/*  79 */     boolean eq = true;
/*     */ 
/*  81 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  82 */     eq = (eq) && (this.mCcDeploymentPK.equals(other.mCcDeploymentPK));
/*  83 */     eq = (eq) && (this.mCcDeploymentLinePK.equals(other.mCcDeploymentLinePK));
/*  84 */     eq = (eq) && (this.mCcDeploymentEntryPK.equals(other.mCcDeploymentEntryPK));
/*     */ 
/*  86 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  94 */     StringBuffer sb = new StringBuffer();
/*  95 */     sb.append(super.toString());
/*  96 */     sb.append("[");
/*  97 */     sb.append(this.mCcDeploymentEntryPK);
/*  98 */     sb.append("]");
/*  99 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 107 */     StringBuffer sb = new StringBuffer();
/* 108 */     sb.append("CcDeploymentEntryCK|");
/* 109 */     sb.append(super.getPK().toTokens());
/* 110 */     sb.append('|');
/* 111 */     sb.append(this.mCcDeploymentEntryPK.toTokens());
/* 112 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 117 */     String[] token = extKey.split("[|]");
/* 118 */     int i = 0;
/* 119 */     checkExpected("CcDeploymentEntryCK", token[(i++)]);
/* 120 */     checkExpected("ModelPK", token[(i++)]);
/* 121 */     i++;
/* 122 */     checkExpected("CcDeploymentPK", token[(i++)]);
/* 123 */     i++;
/* 124 */     checkExpected("CcDeploymentLinePK", token[(i++)]);
/* 125 */     i++;
/* 126 */     checkExpected("CcDeploymentEntryPK", token[(i++)]);
/* 127 */     i = 1;
/* 128 */     return new CcDeploymentEntryCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcDeploymentPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcDeploymentLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcDeploymentEntryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 138 */     if (!expected.equals(found))
/* 139 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcDeploymentEntryCK
 * JD-Core Version:    0.6.0
 */