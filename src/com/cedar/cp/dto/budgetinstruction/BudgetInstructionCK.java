/*     */ package com.cedar.cp.dto.budgetinstruction;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetInstructionCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected BudgetInstructionPK mBudgetInstructionPK;
/*     */ 
/*     */   public BudgetInstructionCK(BudgetInstructionPK paramBudgetInstructionPK)
/*     */   {
/*  26 */     this.mBudgetInstructionPK = paramBudgetInstructionPK;
/*     */   }
/*     */ 
/*     */   public BudgetInstructionPK getBudgetInstructionPK()
/*     */   {
/*  34 */     return this.mBudgetInstructionPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mBudgetInstructionPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mBudgetInstructionPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof BudgetInstructionPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof BudgetInstructionCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     BudgetInstructionCK other = (BudgetInstructionCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mBudgetInstructionPK.equals(other.mBudgetInstructionPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mBudgetInstructionPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("BudgetInstructionCK|");
/*  92 */     sb.append(this.mBudgetInstructionPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static BudgetInstructionCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("BudgetInstructionCK", token[(i++)]);
/* 101 */     checkExpected("BudgetInstructionPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new BudgetInstructionCK(BudgetInstructionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.budgetinstruction.BudgetInstructionCK
 * JD-Core Version:    0.6.0
 */