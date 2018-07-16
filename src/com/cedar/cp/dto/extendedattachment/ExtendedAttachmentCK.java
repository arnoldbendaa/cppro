/*     */ package com.cedar.cp.dto.extendedattachment;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtendedAttachmentCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtendedAttachmentPK mExtendedAttachmentPK;
/*     */ 
/*     */   public ExtendedAttachmentCK(ExtendedAttachmentPK paramExtendedAttachmentPK)
/*     */   {
/*  26 */     this.mExtendedAttachmentPK = paramExtendedAttachmentPK;
/*     */   }
/*     */ 
/*     */   public ExtendedAttachmentPK getExtendedAttachmentPK()
/*     */   {
/*  34 */     return this.mExtendedAttachmentPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mExtendedAttachmentPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mExtendedAttachmentPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ExtendedAttachmentPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ExtendedAttachmentCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ExtendedAttachmentCK other = (ExtendedAttachmentCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mExtendedAttachmentPK.equals(other.mExtendedAttachmentPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mExtendedAttachmentPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ExtendedAttachmentCK|");
/*  92 */     sb.append(this.mExtendedAttachmentPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExtendedAttachmentCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ExtendedAttachmentCK", token[(i++)]);
/* 101 */     checkExpected("ExtendedAttachmentPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ExtendedAttachmentCK(ExtendedAttachmentPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extendedattachment.ExtendedAttachmentCK
 * JD-Core Version:    0.6.0
 */