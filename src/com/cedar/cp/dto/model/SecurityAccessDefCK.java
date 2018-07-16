/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityAccessDefCK extends ModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected SecurityAccessDefPK mSecurityAccessDefPK;
/*     */ 
/*     */   public SecurityAccessDefCK(ModelPK paramModelPK, SecurityAccessDefPK paramSecurityAccessDefPK)
/*     */   {
/*  29 */     super(paramModelPK);
/*     */ 
/*  32 */     this.mSecurityAccessDefPK = paramSecurityAccessDefPK;
/*     */   }
/*     */ 
/*     */   public SecurityAccessDefPK getSecurityAccessDefPK()
/*     */   {
/*  40 */     return this.mSecurityAccessDefPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mSecurityAccessDefPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mSecurityAccessDefPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof SecurityAccessDefPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof SecurityAccessDefCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     SecurityAccessDefCK other = (SecurityAccessDefCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  75 */     eq = (eq) && (this.mSecurityAccessDefPK.equals(other.mSecurityAccessDefPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mSecurityAccessDefPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("SecurityAccessDefCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mSecurityAccessDefPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("SecurityAccessDefCK", token[(i++)]);
/* 111 */     checkExpected("ModelPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("SecurityAccessDefPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new SecurityAccessDefCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityAccessDefPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.SecurityAccessDefCK
 * JD-Core Version:    0.6.0
 */