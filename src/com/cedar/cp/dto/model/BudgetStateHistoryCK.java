/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetStateHistoryCK extends BudgetStateCK
/*     */   implements Serializable
/*     */ {
/*     */   protected BudgetStateHistoryPK mBudgetStateHistoryPK;
/*     */ 
/*     */   public BudgetStateHistoryCK(ModelPK paramModelPK, BudgetCyclePK paramBudgetCyclePK, BudgetStatePK paramBudgetStatePK, BudgetStateHistoryPK paramBudgetStateHistoryPK)
/*     */   {
/*  33 */     super(paramModelPK, paramBudgetCyclePK, paramBudgetStatePK);
/*     */ 
/*  38 */     this.mBudgetStateHistoryPK = paramBudgetStateHistoryPK;
/*     */   }
/*     */ 
/*     */   public BudgetStateHistoryPK getBudgetStateHistoryPK()
/*     */   {
/*  46 */     return this.mBudgetStateHistoryPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  54 */     return this.mBudgetStateHistoryPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  62 */     return this.mBudgetStateHistoryPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  71 */     if ((obj instanceof BudgetStateHistoryPK)) {
/*  72 */       return obj.equals(this);
/*     */     }
/*  74 */     if (!(obj instanceof BudgetStateHistoryCK)) {
/*  75 */       return false;
/*     */     }
/*  77 */     BudgetStateHistoryCK other = (BudgetStateHistoryCK)obj;
/*  78 */     boolean eq = true;
/*     */ 
/*  80 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  81 */     eq = (eq) && (this.mBudgetCyclePK.equals(other.mBudgetCyclePK));
/*  82 */     eq = (eq) && (this.mBudgetStatePK.equals(other.mBudgetStatePK));
/*  83 */     eq = (eq) && (this.mBudgetStateHistoryPK.equals(other.mBudgetStateHistoryPK));
/*     */ 
/*  85 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  93 */     StringBuffer sb = new StringBuffer();
/*  94 */     sb.append(super.toString());
/*  95 */     sb.append("[");
/*  96 */     sb.append(this.mBudgetStateHistoryPK);
/*  97 */     sb.append("]");
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 106 */     StringBuffer sb = new StringBuffer();
/* 107 */     sb.append("BudgetStateHistoryCK|");
/* 108 */     sb.append(super.getPK().toTokens());
/* 109 */     sb.append('|');
/* 110 */     sb.append(this.mBudgetStateHistoryPK.toTokens());
/* 111 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 116 */     String[] token = extKey.split("[|]");
/* 117 */     int i = 0;
/* 118 */     checkExpected("BudgetStateHistoryCK", token[(i++)]);
/* 119 */     checkExpected("ModelPK", token[(i++)]);
/* 120 */     i++;
/* 121 */     checkExpected("BudgetCyclePK", token[(i++)]);
/* 122 */     i++;
/* 123 */     checkExpected("BudgetStatePK", token[(i++)]);
/* 124 */     i++;
/* 125 */     checkExpected("BudgetStateHistoryPK", token[(i++)]);
/* 126 */     i = 1;
/* 127 */     return new BudgetStateHistoryCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetCyclePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetStatePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetStateHistoryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 137 */     if (!expected.equals(found))
/* 138 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.BudgetStateHistoryCK
 * JD-Core Version:    0.6.0
 */