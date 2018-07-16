/*     */ package com.cedar.cp.dto.xmlreport;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class XmlReportPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mXmlReportId;
/*     */ 
/*     */   public XmlReportPK(int newXmlReportId)
/*     */   {
/*  23 */     this.mXmlReportId = newXmlReportId;
/*     */   }
/*     */ 
/*     */   public int getXmlReportId()
/*     */   {
/*  32 */     return this.mXmlReportId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mXmlReportId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     XmlReportPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof XmlReportCK)) {
/*  56 */       other = ((XmlReportCK)obj).getXmlReportPK();
/*     */     }
/*  58 */     else if ((obj instanceof XmlReportPK))
/*  59 */       other = (XmlReportPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mXmlReportId == other.mXmlReportId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" XmlReportId=");
/*  77 */     sb.append(this.mXmlReportId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mXmlReportId);
/*  89 */     return "XmlReportPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static XmlReportPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("XmlReportPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'XmlReportPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pXmlReportId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new XmlReportPK(pXmlReportId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlreport.XmlReportPK
 * JD-Core Version:    0.6.0
 */