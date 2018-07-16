/*     */ package com.cedar.cp.dto.report.destination.external;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExternalDestinationUsersCK extends ExternalDestinationCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExternalDestinationUsersPK mExternalDestinationUsersPK;
/*     */ 
/*     */   public ExternalDestinationUsersCK(ExternalDestinationPK paramExternalDestinationPK, ExternalDestinationUsersPK paramExternalDestinationUsersPK)
/*     */   {
/*  29 */     super(paramExternalDestinationPK);
/*     */ 
/*  32 */     this.mExternalDestinationUsersPK = paramExternalDestinationUsersPK;
/*     */   }
/*     */ 
/*     */   public ExternalDestinationUsersPK getExternalDestinationUsersPK()
/*     */   {
/*  40 */     return this.mExternalDestinationUsersPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mExternalDestinationUsersPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mExternalDestinationUsersPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof ExternalDestinationUsersPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof ExternalDestinationUsersCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     ExternalDestinationUsersCK other = (ExternalDestinationUsersCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mExternalDestinationPK.equals(other.mExternalDestinationPK));
/*  75 */     eq = (eq) && (this.mExternalDestinationUsersPK.equals(other.mExternalDestinationUsersPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mExternalDestinationUsersPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("ExternalDestinationUsersCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mExternalDestinationUsersPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalDestinationCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("ExternalDestinationUsersCK", token[(i++)]);
/* 111 */     checkExpected("ExternalDestinationPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("ExternalDestinationUsersPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new ExternalDestinationUsersCK(ExternalDestinationPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExternalDestinationUsersPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersCK
 * JD-Core Version:    0.6.0
 */