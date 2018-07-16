/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcMappingEntryCK extends CcMappingLineCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CcMappingEntryPK mCcMappingEntryPK;
/*     */ 
/*     */   public CcMappingEntryCK(ModelPK paramModelPK, CcDeploymentPK paramCcDeploymentPK, CcDeploymentLinePK paramCcDeploymentLinePK, CcMappingLinePK paramCcMappingLinePK, CcMappingEntryPK paramCcMappingEntryPK)
/*     */   {
/*  36 */     super(paramModelPK, paramCcDeploymentPK, paramCcDeploymentLinePK, paramCcMappingLinePK);
/*     */ 
/*  42 */     this.mCcMappingEntryPK = paramCcMappingEntryPK;
/*     */   }
/*     */ 
/*     */   public CcMappingEntryPK getCcMappingEntryPK()
/*     */   {
/*  50 */     return this.mCcMappingEntryPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  58 */     return this.mCcMappingEntryPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  66 */     return this.mCcMappingEntryPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  75 */     if ((obj instanceof CcMappingEntryPK)) {
/*  76 */       return obj.equals(this);
/*     */     }
/*  78 */     if (!(obj instanceof CcMappingEntryCK)) {
/*  79 */       return false;
/*     */     }
/*  81 */     CcMappingEntryCK other = (CcMappingEntryCK)obj;
/*  82 */     boolean eq = true;
/*     */ 
/*  84 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  85 */     eq = (eq) && (this.mCcDeploymentPK.equals(other.mCcDeploymentPK));
/*  86 */     eq = (eq) && (this.mCcDeploymentLinePK.equals(other.mCcDeploymentLinePK));
/*  87 */     eq = (eq) && (this.mCcMappingLinePK.equals(other.mCcMappingLinePK));
/*  88 */     eq = (eq) && (this.mCcMappingEntryPK.equals(other.mCcMappingEntryPK));
/*     */ 
/*  90 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append(super.toString());
/* 100 */     sb.append("[");
/* 101 */     sb.append(this.mCcMappingEntryPK);
/* 102 */     sb.append("]");
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 111 */     StringBuffer sb = new StringBuffer();
/* 112 */     sb.append("CcMappingEntryCK|");
/* 113 */     sb.append(super.getPK().toTokens());
/* 114 */     sb.append('|');
/* 115 */     sb.append(this.mCcMappingEntryPK.toTokens());
/* 116 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 121 */     String[] token = extKey.split("[|]");
/* 122 */     int i = 0;
/* 123 */     checkExpected("CcMappingEntryCK", token[(i++)]);
/* 124 */     checkExpected("ModelPK", token[(i++)]);
/* 125 */     i++;
/* 126 */     checkExpected("CcDeploymentPK", token[(i++)]);
/* 127 */     i++;
/* 128 */     checkExpected("CcDeploymentLinePK", token[(i++)]);
/* 129 */     i++;
/* 130 */     checkExpected("CcMappingLinePK", token[(i++)]);
/* 131 */     i++;
/* 132 */     checkExpected("CcMappingEntryPK", token[(i++)]);
/* 133 */     i = 1;
/* 134 */     return new CcMappingEntryCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcDeploymentPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcDeploymentLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcMappingLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CcMappingEntryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 145 */     if (!expected.equals(found))
/* 146 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcMappingEntryCK
 * JD-Core Version:    0.6.0
 */