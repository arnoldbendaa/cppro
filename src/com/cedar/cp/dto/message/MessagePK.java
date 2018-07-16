/*     */ package com.cedar.cp.dto.message;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MessagePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   long mMessageId;
/*     */ 
/*     */   public MessagePK(long newMessageId)
/*     */   {
/*  23 */     this.mMessageId = newMessageId;
/*     */   }
/*     */ 
/*     */   public long getMessageId()
/*     */   {
/*  32 */     return this.mMessageId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMessageId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     MessagePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof MessageCK)) {
/*  56 */       other = ((MessageCK)obj).getMessagePK();
/*     */     }
/*  58 */     else if ((obj instanceof MessagePK))
/*  59 */       other = (MessagePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMessageId == other.mMessageId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MessageId=");
/*  77 */     sb.append(this.mMessageId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMessageId);
/*  89 */     return "MessagePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MessagePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("MessagePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MessagePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     long pMessageId = new Long(extValues[(i++)]).longValue();
/* 106 */     return new MessagePK(pMessageId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.message.MessagePK
 * JD-Core Version:    0.6.0
 */