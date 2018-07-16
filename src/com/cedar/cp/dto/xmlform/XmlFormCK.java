/*     */ package com.cedar.cp.dto.xmlform;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class XmlFormCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected XmlFormPK mXmlFormPK;
/*     */ 
/*     */   public XmlFormCK(XmlFormPK paramXmlFormPK)
/*     */   {
/*  26 */     this.mXmlFormPK = paramXmlFormPK;
/*     */   }
/*     */ 
/*     */   public XmlFormPK getXmlFormPK()
/*     */   {
/*  34 */     return this.mXmlFormPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mXmlFormPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mXmlFormPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof XmlFormPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof XmlFormCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     XmlFormCK other = (XmlFormCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mXmlFormPK.equals(other.mXmlFormPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mXmlFormPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("XmlFormCK|");
/*  92 */     sb.append(this.mXmlFormPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static XmlFormCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("XmlFormCK", token[(i++)]);
/* 101 */     checkExpected("XmlFormPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new XmlFormCK(XmlFormPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlform.XmlFormCK
 * JD-Core Version:    0.6.0
 */