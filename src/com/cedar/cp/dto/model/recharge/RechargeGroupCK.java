/*     */ package com.cedar.cp.dto.model.recharge;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RechargeGroupCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected RechargeGroupPK mRechargeGroupPK;
/*     */ 
/*     */   public RechargeGroupCK(RechargeGroupPK paramRechargeGroupPK)
/*     */   {
/*  26 */     this.mRechargeGroupPK = paramRechargeGroupPK;
/*     */   }
/*     */ 
/*     */   public RechargeGroupPK getRechargeGroupPK()
/*     */   {
/*  34 */     return this.mRechargeGroupPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mRechargeGroupPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mRechargeGroupPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof RechargeGroupPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof RechargeGroupCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     RechargeGroupCK other = (RechargeGroupCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mRechargeGroupPK.equals(other.mRechargeGroupPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mRechargeGroupPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("RechargeGroupCK|");
/*  92 */     sb.append(this.mRechargeGroupPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static RechargeGroupCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("RechargeGroupCK", token[(i++)]);
/* 101 */     checkExpected("RechargeGroupPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new RechargeGroupCK(RechargeGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.recharge.RechargeGroupCK
 * JD-Core Version:    0.6.0
 */