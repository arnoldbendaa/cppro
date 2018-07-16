/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class StructureElementCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected StructureElementPK mStructureElementPK;
/*     */ 
/*     */   public StructureElementCK(StructureElementPK paramStructureElementPK)
/*     */   {
/*  26 */     this.mStructureElementPK = paramStructureElementPK;
/*     */   }
/*     */ 
/*     */   public StructureElementPK getStructureElementPK()
/*     */   {
/*  34 */     return this.mStructureElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mStructureElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mStructureElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof StructureElementPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof StructureElementCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     StructureElementCK other = (StructureElementCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mStructureElementPK.equals(other.mStructureElementPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mStructureElementPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("StructureElementCK|");
/*  92 */     sb.append(this.mStructureElementPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static StructureElementCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("StructureElementCK", token[(i++)]);
/* 101 */     checkExpected("StructureElementPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new StructureElementCK(StructureElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.StructureElementCK
 * JD-Core Version:    0.6.0
 */