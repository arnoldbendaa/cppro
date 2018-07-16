/*     */ package com.cedar.cp.dto.xmlreportfolder;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class XmlReportFolderCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected XmlReportFolderPK mXmlReportFolderPK;
/*     */ 
/*     */   public XmlReportFolderCK(XmlReportFolderPK paramXmlReportFolderPK)
/*     */   {
/*  26 */     this.mXmlReportFolderPK = paramXmlReportFolderPK;
/*     */   }
/*     */ 
/*     */   public XmlReportFolderPK getXmlReportFolderPK()
/*     */   {
/*  34 */     return this.mXmlReportFolderPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mXmlReportFolderPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mXmlReportFolderPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof XmlReportFolderPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof XmlReportFolderCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     XmlReportFolderCK other = (XmlReportFolderCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mXmlReportFolderPK.equals(other.mXmlReportFolderPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mXmlReportFolderPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("XmlReportFolderCK|");
/*  92 */     sb.append(this.mXmlReportFolderPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static XmlReportFolderCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("XmlReportFolderCK", token[(i++)]);
/* 101 */     checkExpected("XmlReportFolderPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new XmlReportFolderCK(XmlReportFolderPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlreportfolder.XmlReportFolderCK
 * JD-Core Version:    0.6.0
 */