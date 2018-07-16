/*     */ package com.cedar.cp.dto.model.act;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetActivityLinkCK extends BudgetActivityCK
/*     */   implements Serializable
/*     */ {
/*     */   protected BudgetActivityLinkPK mBudgetActivityLinkPK;
/*     */ 
/*     */   public BudgetActivityLinkCK(ModelPK paramModelPK, BudgetActivityPK paramBudgetActivityPK, BudgetActivityLinkPK paramBudgetActivityLinkPK)
/*     */   {
/*  32 */     super(paramModelPK, paramBudgetActivityPK);
/*     */ 
/*  36 */     this.mBudgetActivityLinkPK = paramBudgetActivityLinkPK;
/*     */   }
/*     */ 
/*     */   public BudgetActivityLinkPK getBudgetActivityLinkPK()
/*     */   {
/*  44 */     return this.mBudgetActivityLinkPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mBudgetActivityLinkPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mBudgetActivityLinkPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof BudgetActivityLinkPK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof BudgetActivityLinkCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     BudgetActivityLinkCK other = (BudgetActivityLinkCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mBudgetActivityPK.equals(other.mBudgetActivityPK));
/*  80 */     eq = (eq) && (this.mBudgetActivityLinkPK.equals(other.mBudgetActivityLinkPK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mBudgetActivityLinkPK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("BudgetActivityLinkCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mBudgetActivityLinkPK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("BudgetActivityLinkCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("BudgetActivityPK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("BudgetActivityLinkPK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new BudgetActivityLinkCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetActivityPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetActivityLinkPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.act.BudgetActivityLinkCK
 * JD-Core Version:    0.6.0
 */