/*     */ package com.cedar.cp.dto.formnotes;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FormNotesCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected FormNotesPK mFormNotesPK;
/*     */ 
/*     */   public FormNotesCK(FormNotesPK paramFormNotesPK)
/*     */   {
/*  26 */     this.mFormNotesPK = paramFormNotesPK;
/*     */   }
/*     */ 
/*     */   public FormNotesPK getFormNotesPK()
/*     */   {
/*  34 */     return this.mFormNotesPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mFormNotesPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mFormNotesPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof FormNotesPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof FormNotesCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     FormNotesCK other = (FormNotesCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mFormNotesPK.equals(other.mFormNotesPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mFormNotesPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("FormNotesCK|");
/*  92 */     sb.append(this.mFormNotesPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static FormNotesCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("FormNotesCK", token[(i++)]);
/* 101 */     checkExpected("FormNotesPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new FormNotesCK(FormNotesPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.formnotes.FormNotesCK
 * JD-Core Version:    0.6.0
 */