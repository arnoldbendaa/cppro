/*     */ package com.cedar.cp.dto.model.budgetlimit;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetLimitPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mBudgetLimitId;
/*     */ 
/*     */   public BudgetLimitPK(int newBudgetLimitId)
/*     */   {
/*  23 */     this.mBudgetLimitId = newBudgetLimitId;
/*     */   }
/*     */ 
/*     */   public int getBudgetLimitId()
/*     */   {
/*  32 */     return this.mBudgetLimitId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mBudgetLimitId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     BudgetLimitPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof BudgetLimitCK)) {
/*  56 */       other = ((BudgetLimitCK)obj).getBudgetLimitPK();
/*     */     }
/*  58 */     else if ((obj instanceof BudgetLimitPK))
/*  59 */       other = (BudgetLimitPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mBudgetLimitId == other.mBudgetLimitId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" BudgetLimitId=");
/*  77 */     sb.append(this.mBudgetLimitId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mBudgetLimitId);
/*  89 */     return "BudgetLimitPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetLimitPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("BudgetLimitPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetLimitPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pBudgetLimitId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new BudgetLimitPK(pBudgetLimitId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK
 * JD-Core Version:    0.6.0
 */