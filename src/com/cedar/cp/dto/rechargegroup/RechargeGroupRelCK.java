/*     */ package com.cedar.cp.dto.rechargegroup;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeGroupCK;
/*     */ import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RechargeGroupRelCK extends RechargeGroupCK
/*     */   implements Serializable
/*     */ {
/*     */   protected RechargeGroupRelPK mRechargeGroupRelPK;
/*     */ 
/*     */   public RechargeGroupRelCK(RechargeGroupPK paramRechargeGroupPK, RechargeGroupRelPK paramRechargeGroupRelPK)
/*     */   {
/*  30 */     super(paramRechargeGroupPK);
/*     */ 
/*  33 */     this.mRechargeGroupRelPK = paramRechargeGroupRelPK;
/*     */   }
/*     */ 
/*     */   public RechargeGroupRelPK getRechargeGroupRelPK()
/*     */   {
/*  41 */     return this.mRechargeGroupRelPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  49 */     return this.mRechargeGroupRelPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  57 */     return this.mRechargeGroupRelPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  66 */     if ((obj instanceof RechargeGroupRelPK)) {
/*  67 */       return obj.equals(this);
/*     */     }
/*  69 */     if (!(obj instanceof RechargeGroupRelCK)) {
/*  70 */       return false;
/*     */     }
/*  72 */     RechargeGroupRelCK other = (RechargeGroupRelCK)obj;
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mRechargeGroupPK.equals(other.mRechargeGroupPK));
/*  76 */     eq = (eq) && (this.mRechargeGroupRelPK.equals(other.mRechargeGroupRelPK));
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(super.toString());
/*  88 */     sb.append("[");
/*  89 */     sb.append(this.mRechargeGroupRelPK);
/*  90 */     sb.append("]");
/*  91 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append("RechargeGroupRelCK|");
/* 101 */     sb.append(super.getPK().toTokens());
/* 102 */     sb.append('|');
/* 103 */     sb.append(this.mRechargeGroupRelPK.toTokens());
/* 104 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static RechargeGroupCK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] token = extKey.split("[|]");
/* 110 */     int i = 0;
/* 111 */     checkExpected("RechargeGroupRelCK", token[(i++)]);
/* 112 */     checkExpected("RechargeGroupPK", token[(i++)]);
/* 113 */     i++;
/* 114 */     checkExpected("RechargeGroupRelPK", token[(i++)]);
/* 115 */     i = 1;
/* 116 */     return new RechargeGroupRelCK(RechargeGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), RechargeGroupRelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 124 */     if (!expected.equals(found))
/* 125 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.rechargegroup.RechargeGroupRelCK
 * JD-Core Version:    0.6.0
 */