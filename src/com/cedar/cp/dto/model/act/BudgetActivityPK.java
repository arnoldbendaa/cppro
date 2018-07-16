/*     */ package com.cedar.cp.dto.model.act;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetActivityPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mBudgetActivityId;
/*     */ 
/*     */   public BudgetActivityPK(int newBudgetActivityId)
/*     */   {
/*  23 */     this.mBudgetActivityId = newBudgetActivityId;
/*     */   }
/*     */ 
/*     */   public int getBudgetActivityId()
/*     */   {
/*  32 */     return this.mBudgetActivityId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mBudgetActivityId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     BudgetActivityPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof BudgetActivityCK)) {
/*  56 */       other = ((BudgetActivityCK)obj).getBudgetActivityPK();
/*     */     }
/*  58 */     else if ((obj instanceof BudgetActivityPK))
/*  59 */       other = (BudgetActivityPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mBudgetActivityId == other.mBudgetActivityId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" BudgetActivityId=");
/*  77 */     sb.append(this.mBudgetActivityId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mBudgetActivityId);
/*  89 */     return "BudgetActivityPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetActivityPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("BudgetActivityPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetActivityPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pBudgetActivityId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new BudgetActivityPK(pBudgetActivityId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.act.BudgetActivityPK
 * JD-Core Version:    0.6.0
 */