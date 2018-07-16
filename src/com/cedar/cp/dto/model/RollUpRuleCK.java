/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RollUpRuleCK extends FinanceCubeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected RollUpRulePK mRollUpRulePK;
/*     */ 
/*     */   public RollUpRuleCK(ModelPK paramModelPK, FinanceCubePK paramFinanceCubePK, RollUpRulePK paramRollUpRulePK)
/*     */   {
/*  31 */     super(paramModelPK, paramFinanceCubePK);
/*     */ 
/*  35 */     this.mRollUpRulePK = paramRollUpRulePK;
/*     */   }
/*     */ 
/*     */   public RollUpRulePK getRollUpRulePK()
/*     */   {
/*  43 */     return this.mRollUpRulePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mRollUpRulePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mRollUpRulePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof RollUpRulePK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof RollUpRuleCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     RollUpRuleCK other = (RollUpRuleCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  78 */     eq = (eq) && (this.mFinanceCubePK.equals(other.mFinanceCubePK));
/*  79 */     eq = (eq) && (this.mRollUpRulePK.equals(other.mRollUpRulePK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mRollUpRulePK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("RollUpRuleCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mRollUpRulePK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("RollUpRuleCK", token[(i++)]);
/* 115 */     checkExpected("ModelPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("FinanceCubePK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("RollUpRulePK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new RollUpRuleCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), RollUpRulePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.RollUpRuleCK
 * JD-Core Version:    0.6.0
 */