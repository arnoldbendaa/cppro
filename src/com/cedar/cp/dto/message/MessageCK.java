/*     */ package com.cedar.cp.dto.message;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MessageCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected MessagePK mMessagePK;
/*     */ 
/*     */   public MessageCK(MessagePK paramMessagePK)
/*     */   {
/*  26 */     this.mMessagePK = paramMessagePK;
/*     */   }
/*     */ 
/*     */   public MessagePK getMessagePK()
/*     */   {
/*  34 */     return this.mMessagePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mMessagePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mMessagePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof MessagePK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof MessageCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     MessageCK other = (MessageCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mMessagePK.equals(other.mMessagePK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mMessagePK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("MessageCK|");
/*  92 */     sb.append(this.mMessagePK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static MessageCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("MessageCK", token[(i++)]);
/* 101 */     checkExpected("MessagePK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new MessageCK(MessagePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.message.MessageCK
 * JD-Core Version:    0.6.0
 */