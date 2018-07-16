/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysPropertyCK extends ExternalSystemCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysPropertyPK mExtSysPropertyPK;
/*     */ 
/*     */   public ExtSysPropertyCK(ExternalSystemPK paramExternalSystemPK, ExtSysPropertyPK paramExtSysPropertyPK)
/*     */   {
/*  29 */     super(paramExternalSystemPK);
/*     */ 
/*  32 */     this.mExtSysPropertyPK = paramExtSysPropertyPK;
/*     */   }
/*     */ 
/*     */   public ExtSysPropertyPK getExtSysPropertyPK()
/*     */   {
/*  40 */     return this.mExtSysPropertyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mExtSysPropertyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mExtSysPropertyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof ExtSysPropertyPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof ExtSysPropertyCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     ExtSysPropertyCK other = (ExtSysPropertyCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  75 */     eq = (eq) && (this.mExtSysPropertyPK.equals(other.mExtSysPropertyPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mExtSysPropertyPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("ExtSysPropertyCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mExtSysPropertyPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("ExtSysPropertyCK", token[(i++)]);
/* 111 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("ExtSysPropertyPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new ExtSysPropertyCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysPropertyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysPropertyCK
 * JD-Core Version:    0.6.0
 */