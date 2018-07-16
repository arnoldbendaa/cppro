/*     */ package com.cedar.cp.dto.xmlform;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class XmlFormUserLinkCK extends XmlFormCK
/*     */   implements Serializable
/*     */ {
/*     */   protected XmlFormUserLinkPK mXmlFormUserLinkPK;
/*     */ 
/*     */   public XmlFormUserLinkCK(XmlFormPK paramXmlFormPK, XmlFormUserLinkPK paramXmlFormUserLinkPK)
/*     */   {
/*  29 */     super(paramXmlFormPK);
/*     */ 
/*  32 */     this.mXmlFormUserLinkPK = paramXmlFormUserLinkPK;
/*     */   }
/*     */ 
/*     */   public XmlFormUserLinkPK getXmlFormUserLinkPK()
/*     */   {
/*  40 */     return this.mXmlFormUserLinkPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mXmlFormUserLinkPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mXmlFormUserLinkPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof XmlFormUserLinkPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof XmlFormUserLinkCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     XmlFormUserLinkCK other = (XmlFormUserLinkCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mXmlFormPK.equals(other.mXmlFormPK));
/*  75 */     eq = (eq) && (this.mXmlFormUserLinkPK.equals(other.mXmlFormUserLinkPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mXmlFormUserLinkPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("XmlFormUserLinkCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mXmlFormUserLinkPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static XmlFormCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("XmlFormUserLinkCK", token[(i++)]);
/* 111 */     checkExpected("XmlFormPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("XmlFormUserLinkPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new XmlFormUserLinkCK(XmlFormPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), XmlFormUserLinkPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlform.XmlFormUserLinkCK
 * JD-Core Version:    0.6.0
 */