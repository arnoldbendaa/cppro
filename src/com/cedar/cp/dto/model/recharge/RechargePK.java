/*     */ package com.cedar.cp.dto.model.recharge;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RechargePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mRechargeId;
/*     */ 
/*     */   public RechargePK(int newRechargeId)
/*     */   {
/*  23 */     this.mRechargeId = newRechargeId;
/*     */   }
/*     */ 
/*     */   public int getRechargeId()
/*     */   {
/*  32 */     return this.mRechargeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mRechargeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     RechargePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof RechargeCK)) {
/*  56 */       other = ((RechargeCK)obj).getRechargePK();
/*     */     }
/*  58 */     else if ((obj instanceof RechargePK))
/*  59 */       other = (RechargePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mRechargeId == other.mRechargeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" RechargeId=");
/*  77 */     sb.append(this.mRechargeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mRechargeId);
/*  89 */     return "RechargePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static RechargePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("RechargePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'RechargePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pRechargeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new RechargePK(pRechargeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.recharge.RechargePK
 * JD-Core Version:    0.6.0
 */