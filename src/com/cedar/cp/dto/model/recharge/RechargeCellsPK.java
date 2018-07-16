/*     */ package com.cedar.cp.dto.model.recharge;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RechargeCellsPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mRechargeCellId;
/*     */   int mRechargeId;
/*     */ 
/*     */   public RechargeCellsPK(int newRechargeCellId, int newRechargeId)
/*     */   {
/*  24 */     this.mRechargeCellId = newRechargeCellId;
/*  25 */     this.mRechargeId = newRechargeId;
/*     */   }
/*     */ 
/*     */   public int getRechargeCellId()
/*     */   {
/*  34 */     return this.mRechargeCellId;
/*     */   }
/*     */ 
/*     */   public int getRechargeId()
/*     */   {
/*  41 */     return this.mRechargeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mRechargeCellId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mRechargeId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     RechargeCellsPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof RechargeCellsCK)) {
/*  66 */       other = ((RechargeCellsCK)obj).getRechargeCellsPK();
/*     */     }
/*  68 */     else if ((obj instanceof RechargeCellsPK))
/*  69 */       other = (RechargeCellsPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mRechargeCellId == other.mRechargeCellId);
/*  76 */     eq = (eq) && (this.mRechargeId == other.mRechargeId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" RechargeCellId=");
/*  88 */     sb.append(this.mRechargeCellId);
/*  89 */     sb.append(",RechargeId=");
/*  90 */     sb.append(this.mRechargeId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mRechargeCellId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mRechargeId);
/* 104 */     return "RechargeCellsPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static RechargeCellsPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("RechargeCellsPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'RechargeCellsPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pRechargeCellId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pRechargeId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new RechargeCellsPK(pRechargeCellId, pRechargeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.recharge.RechargeCellsPK
 * JD-Core Version:    0.6.0
 */