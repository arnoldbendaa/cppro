/*     */ package com.cedar.cp.dto.message;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MessageAttatchPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   long mMessageId;
/*     */   long mMessageAttatchId;
/*     */ 
/*     */   public MessageAttatchPK(long newMessageId, long newMessageAttatchId)
/*     */   {
/*  24 */     this.mMessageId = newMessageId;
/*  25 */     this.mMessageAttatchId = newMessageAttatchId;
/*     */   }
/*     */ 
/*     */   public long getMessageId()
/*     */   {
/*  34 */     return this.mMessageId;
/*     */   }
/*     */ 
/*     */   public long getMessageAttatchId()
/*     */   {
/*  41 */     return this.mMessageAttatchId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mMessageId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mMessageAttatchId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     MessageAttatchPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof MessageAttatchCK)) {
/*  66 */       other = ((MessageAttatchCK)obj).getMessageAttatchPK();
/*     */     }
/*  68 */     else if ((obj instanceof MessageAttatchPK))
/*  69 */       other = (MessageAttatchPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mMessageId == other.mMessageId);
/*  76 */     eq = (eq) && (this.mMessageAttatchId == other.mMessageAttatchId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" MessageId=");
/*  88 */     sb.append(this.mMessageId);
/*  89 */     sb.append(",MessageAttatchId=");
/*  90 */     sb.append(this.mMessageAttatchId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mMessageId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mMessageAttatchId);
/* 104 */     return "MessageAttatchPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MessageAttatchPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("MessageAttatchPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MessageAttatchPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     long pMessageId = new Long(extValues[(i++)]).longValue();
/* 121 */     long pMessageAttatchId = new Long(extValues[(i++)]).longValue();
/* 122 */     return new MessageAttatchPK(pMessageId, pMessageAttatchId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.message.MessageAttatchPK
 * JD-Core Version:    0.6.0
 */