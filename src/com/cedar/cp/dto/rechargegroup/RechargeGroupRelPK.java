/*     */ package com.cedar.cp.dto.rechargegroup;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RechargeGroupRelPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mRechargeGroupRelId;
/*     */   int mRechargeGroupId;
/*     */ 
/*     */   public RechargeGroupRelPK(int newRechargeGroupRelId, int newRechargeGroupId)
/*     */   {
/*  24 */     this.mRechargeGroupRelId = newRechargeGroupRelId;
/*  25 */     this.mRechargeGroupId = newRechargeGroupId;
/*     */   }
/*     */ 
/*     */   public int getRechargeGroupRelId()
/*     */   {
/*  34 */     return this.mRechargeGroupRelId;
/*     */   }
/*     */ 
/*     */   public int getRechargeGroupId()
/*     */   {
/*  41 */     return this.mRechargeGroupId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mRechargeGroupRelId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mRechargeGroupId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     RechargeGroupRelPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof RechargeGroupRelCK)) {
/*  66 */       other = ((RechargeGroupRelCK)obj).getRechargeGroupRelPK();
/*     */     }
/*  68 */     else if ((obj instanceof RechargeGroupRelPK))
/*  69 */       other = (RechargeGroupRelPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mRechargeGroupRelId == other.mRechargeGroupRelId);
/*  76 */     eq = (eq) && (this.mRechargeGroupId == other.mRechargeGroupId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" RechargeGroupRelId=");
/*  88 */     sb.append(this.mRechargeGroupRelId);
/*  89 */     sb.append(",RechargeGroupId=");
/*  90 */     sb.append(this.mRechargeGroupId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mRechargeGroupRelId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mRechargeGroupId);
/* 104 */     return "RechargeGroupRelPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static RechargeGroupRelPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("RechargeGroupRelPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'RechargeGroupRelPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pRechargeGroupRelId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pRechargeGroupId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new RechargeGroupRelPK(pRechargeGroupRelId, pRechargeGroupId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.rechargegroup.RechargeGroupRelPK
 * JD-Core Version:    0.6.0
 */