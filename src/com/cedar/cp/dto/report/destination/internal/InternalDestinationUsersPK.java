/*     */ package com.cedar.cp.dto.report.destination.internal;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class InternalDestinationUsersPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mInternalDestinationId;
/*     */   int mUserId;
/*     */ 
/*     */   public InternalDestinationUsersPK(int newInternalDestinationId, int newUserId)
/*     */   {
/*  24 */     this.mInternalDestinationId = newInternalDestinationId;
/*  25 */     this.mUserId = newUserId;
/*     */   }
/*     */ 
/*     */   public int getInternalDestinationId()
/*     */   {
/*  34 */     return this.mInternalDestinationId;
/*     */   }
/*     */ 
/*     */   public int getUserId()
/*     */   {
/*  41 */     return this.mUserId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mInternalDestinationId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mUserId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     InternalDestinationUsersPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof InternalDestinationUsersCK)) {
/*  66 */       other = ((InternalDestinationUsersCK)obj).getInternalDestinationUsersPK();
/*     */     }
/*  68 */     else if ((obj instanceof InternalDestinationUsersPK))
/*  69 */       other = (InternalDestinationUsersPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mInternalDestinationId == other.mInternalDestinationId);
/*  76 */     eq = (eq) && (this.mUserId == other.mUserId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" InternalDestinationId=");
/*  88 */     sb.append(this.mInternalDestinationId);
/*  89 */     sb.append(",UserId=");
/*  90 */     sb.append(this.mUserId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mInternalDestinationId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mUserId);
/* 104 */     return "InternalDestinationUsersPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static InternalDestinationUsersPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("InternalDestinationUsersPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'InternalDestinationUsersPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pInternalDestinationId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pUserId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new InternalDestinationUsersPK(pInternalDestinationId, pUserId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.destination.internal.InternalDestinationUsersPK
 * JD-Core Version:    0.6.0
 */