/*     */ package com.cedar.cp.dto.xmlreport;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class XmlReportCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected XmlReportPK mXmlReportPK;
/*     */ 
/*     */   public XmlReportCK(XmlReportPK paramXmlReportPK)
/*     */   {
/*  26 */     this.mXmlReportPK = paramXmlReportPK;
/*     */   }
/*     */ 
/*     */   public XmlReportPK getXmlReportPK()
/*     */   {
/*  34 */     return this.mXmlReportPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mXmlReportPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mXmlReportPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof XmlReportPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof XmlReportCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     XmlReportCK other = (XmlReportCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mXmlReportPK.equals(other.mXmlReportPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mXmlReportPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("XmlReportCK|");
/*  92 */     sb.append(this.mXmlReportPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static XmlReportCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("XmlReportCK", token[(i++)]);
/* 101 */     checkExpected("XmlReportPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new XmlReportCK(XmlReportPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlreport.XmlReportCK
 * JD-Core Version:    0.6.0
 */