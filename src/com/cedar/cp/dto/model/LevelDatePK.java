/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class LevelDatePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mBudgetCycleId;
/*     */   int mDepth;
/*     */ 
/*     */   public LevelDatePK(int newBudgetCycleId, int newDepth)
/*     */   {
/*  24 */     this.mBudgetCycleId = newBudgetCycleId;
/*  25 */     this.mDepth = newDepth;
/*     */   }
/*     */ 
/*     */   public int getBudgetCycleId()
/*     */   {
/*  34 */     return this.mBudgetCycleId;
/*     */   }
/*     */ 
/*     */   public int getDepth()
/*     */   {
/*  41 */     return this.mDepth;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mBudgetCycleId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mDepth).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     LevelDatePK other = null;
/*     */ 
/*  65 */     if ((obj instanceof LevelDateCK)) {
/*  66 */       other = ((LevelDateCK)obj).getLevelDatePK();
/*     */     }
/*  68 */     else if ((obj instanceof LevelDatePK))
/*  69 */       other = (LevelDatePK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mBudgetCycleId == other.mBudgetCycleId);
/*  76 */     eq = (eq) && (this.mDepth == other.mDepth);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" BudgetCycleId=");
/*  88 */     sb.append(this.mBudgetCycleId);
/*  89 */     sb.append(",Depth=");
/*  90 */     sb.append(this.mDepth);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mBudgetCycleId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mDepth);
/* 104 */     return "LevelDatePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static LevelDatePK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("LevelDatePK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'LevelDatePK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pBudgetCycleId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pDepth = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new LevelDatePK(pBudgetCycleId, pDepth);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.LevelDatePK
 * JD-Core Version:    0.6.0
 */