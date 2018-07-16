/*     */ package com.cedar.cp.dto.message;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MessageUserCK extends MessageCK
/*     */   implements Serializable
/*     */ {
/*     */   protected MessageUserPK mMessageUserPK;
/*     */ 
/*     */   public MessageUserCK(MessagePK paramMessagePK, MessageUserPK paramMessageUserPK)
/*     */   {
/*  29 */     super(paramMessagePK);
/*     */ 
/*  32 */     this.mMessageUserPK = paramMessageUserPK;
/*     */   }
/*     */ 
/*     */   public MessageUserPK getMessageUserPK()
/*     */   {
/*  40 */     return this.mMessageUserPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mMessageUserPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mMessageUserPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof MessageUserPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof MessageUserCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     MessageUserCK other = (MessageUserCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mMessagePK.equals(other.mMessagePK));
/*  75 */     eq = (eq) && (this.mMessageUserPK.equals(other.mMessageUserPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mMessageUserPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("MessageUserCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mMessageUserPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static MessageCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("MessageUserCK", token[(i++)]);
/* 111 */     checkExpected("MessagePK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("MessageUserPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new MessageUserCK(MessagePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MessageUserPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.message.MessageUserCK
 * JD-Core Version:    0.6.0
 */