/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RollUpRulePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mRollUpRuleId;
/*     */ 
/*     */   public RollUpRulePK(int newRollUpRuleId)
/*     */   {
/*  23 */     this.mRollUpRuleId = newRollUpRuleId;
/*     */   }
/*     */ 
/*     */   public int getRollUpRuleId()
/*     */   {
/*  32 */     return this.mRollUpRuleId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mRollUpRuleId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     RollUpRulePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof RollUpRuleCK)) {
/*  56 */       other = ((RollUpRuleCK)obj).getRollUpRulePK();
/*     */     }
/*  58 */     else if ((obj instanceof RollUpRulePK))
/*  59 */       other = (RollUpRulePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mRollUpRuleId == other.mRollUpRuleId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" RollUpRuleId=");
/*  77 */     sb.append(this.mRollUpRuleId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mRollUpRuleId);
/*  89 */     return "RollUpRulePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static RollUpRulePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("RollUpRulePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'RollUpRulePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pRollUpRuleId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new RollUpRulePK(pRollUpRuleId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.RollUpRulePK
 * JD-Core Version:    0.6.0
 */