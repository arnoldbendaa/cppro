/*     */ package com.cedar.cp.dto.message;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MessageUserPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   long mMessageId;
/*     */   long mMessageUserId;
/*     */ 
/*     */   public MessageUserPK(long newMessageId, long newMessageUserId)
/*     */   {
/*  24 */     this.mMessageId = newMessageId;
/*  25 */     this.mMessageUserId = newMessageUserId;
/*     */   }
/*     */ 
/*     */   public long getMessageId()
/*     */   {
/*  34 */     return this.mMessageId;
/*     */   }
/*     */ 
/*     */   public long getMessageUserId()
/*     */   {
/*  41 */     return this.mMessageUserId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mMessageId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mMessageUserId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     MessageUserPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof MessageUserCK)) {
/*  66 */       other = ((MessageUserCK)obj).getMessageUserPK();
/*     */     }
/*  68 */     else if ((obj instanceof MessageUserPK))
/*  69 */       other = (MessageUserPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mMessageId == other.mMessageId);
/*  76 */     eq = (eq) && (this.mMessageUserId == other.mMessageUserId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" MessageId=");
/*  88 */     sb.append(this.mMessageId);
/*  89 */     sb.append(",MessageUserId=");
/*  90 */     sb.append(this.mMessageUserId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mMessageId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mMessageUserId);
/* 104 */     return "MessageUserPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MessageUserPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("MessageUserPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MessageUserPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     long pMessageId = new Long(extValues[(i++)]).longValue();
/* 121 */     long pMessageUserId = new Long(extValues[(i++)]).longValue();
/* 122 */     return new MessageUserPK(pMessageId, pMessageUserId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.message.MessageUserPK
 * JD-Core Version:    0.6.0
 */