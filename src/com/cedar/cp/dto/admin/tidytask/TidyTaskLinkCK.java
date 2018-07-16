/*     */ package com.cedar.cp.dto.admin.tidytask;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TidyTaskLinkCK extends TidyTaskCK
/*     */   implements Serializable
/*     */ {
/*     */   protected TidyTaskLinkPK mTidyTaskLinkPK;
/*     */ 
/*     */   public TidyTaskLinkCK(TidyTaskPK paramTidyTaskPK, TidyTaskLinkPK paramTidyTaskLinkPK)
/*     */   {
/*  29 */     super(paramTidyTaskPK);
/*     */ 
/*  32 */     this.mTidyTaskLinkPK = paramTidyTaskLinkPK;
/*     */   }
/*     */ 
/*     */   public TidyTaskLinkPK getTidyTaskLinkPK()
/*     */   {
/*  40 */     return this.mTidyTaskLinkPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mTidyTaskLinkPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mTidyTaskLinkPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof TidyTaskLinkPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof TidyTaskLinkCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     TidyTaskLinkCK other = (TidyTaskLinkCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mTidyTaskPK.equals(other.mTidyTaskPK));
/*  75 */     eq = (eq) && (this.mTidyTaskLinkPK.equals(other.mTidyTaskLinkPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mTidyTaskLinkPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("TidyTaskLinkCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mTidyTaskLinkPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static TidyTaskCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("TidyTaskLinkCK", token[(i++)]);
/* 111 */     checkExpected("TidyTaskPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("TidyTaskLinkPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new TidyTaskLinkCK(TidyTaskPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), TidyTaskLinkPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.admin.tidytask.TidyTaskLinkCK
 * JD-Core Version:    0.6.0
 */