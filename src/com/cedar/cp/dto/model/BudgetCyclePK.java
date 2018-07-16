/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetCyclePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mBudgetCycleId;
/*     */ 
 /*     */   public BudgetCyclePK(int newBudgetCycleId)
/*     */   {
/*  23 */     this.mBudgetCycleId = newBudgetCycleId;
/*     */   }
/*     */ 
/*     */   public int getBudgetCycleId()
/*     */   {
/*  32 */     return this.mBudgetCycleId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mBudgetCycleId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     BudgetCyclePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof BudgetCycleCK)) {
/*  56 */       other = ((BudgetCycleCK)obj).getBudgetCyclePK();
/*     */     }
/*  58 */     else if ((obj instanceof BudgetCyclePK))
/*  59 */       other = (BudgetCyclePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mBudgetCycleId == other.mBudgetCycleId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" BudgetCycleId=");
/*  77 */     sb.append(this.mBudgetCycleId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mBudgetCycleId);
/*  89 */     return "BudgetCyclePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetCyclePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("BudgetCyclePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetCyclePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pBudgetCycleId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new BudgetCyclePK(pBudgetCycleId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.BudgetCyclePK
 * JD-Core Version:    0.6.0
 */