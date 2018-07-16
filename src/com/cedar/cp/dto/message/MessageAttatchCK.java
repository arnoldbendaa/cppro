/*     */ package com.cedar.cp.dto.message;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MessageAttatchCK extends MessageCK
/*     */   implements Serializable
/*     */ {
/*     */   protected MessageAttatchPK mMessageAttatchPK;
/*     */ 
/*     */   public MessageAttatchCK(MessagePK paramMessagePK, MessageAttatchPK paramMessageAttatchPK)
/*     */   {
/*  29 */     super(paramMessagePK);
/*     */ 
/*  32 */     this.mMessageAttatchPK = paramMessageAttatchPK;
/*     */   }
/*     */ 
/*     */   public MessageAttatchPK getMessageAttatchPK()
/*     */   {
/*  40 */     return this.mMessageAttatchPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mMessageAttatchPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mMessageAttatchPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof MessageAttatchPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof MessageAttatchCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     MessageAttatchCK other = (MessageAttatchCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mMessagePK.equals(other.mMessagePK));
/*  75 */     eq = (eq) && (this.mMessageAttatchPK.equals(other.mMessageAttatchPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mMessageAttatchPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("MessageAttatchCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mMessageAttatchPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static MessageCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("MessageAttatchCK", token[(i++)]);
/* 111 */     checkExpected("MessagePK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("MessageAttatchPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new MessageAttatchCK(MessagePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MessageAttatchPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.message.MessageAttatchCK
 * JD-Core Version:    0.6.0
 */