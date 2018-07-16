/*     */ package com.cedar.cp.dto.xmlreportfolder;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class XmlReportFolderPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mXmlReportFolderId;
/*     */ 
/*     */   public XmlReportFolderPK(int newXmlReportFolderId)
/*     */   {
/*  23 */     this.mXmlReportFolderId = newXmlReportFolderId;
/*     */   }
/*     */ 
/*     */   public int getXmlReportFolderId()
/*     */   {
/*  32 */     return this.mXmlReportFolderId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mXmlReportFolderId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     XmlReportFolderPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof XmlReportFolderCK)) {
/*  56 */       other = ((XmlReportFolderCK)obj).getXmlReportFolderPK();
/*     */     }
/*  58 */     else if ((obj instanceof XmlReportFolderPK))
/*  59 */       other = (XmlReportFolderPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mXmlReportFolderId == other.mXmlReportFolderId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" XmlReportFolderId=");
/*  77 */     sb.append(this.mXmlReportFolderId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mXmlReportFolderId);
/*  89 */     return "XmlReportFolderPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static XmlReportFolderPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("XmlReportFolderPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'XmlReportFolderPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pXmlReportFolderId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new XmlReportFolderPK(pXmlReportFolderId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlreportfolder.XmlReportFolderPK
 * JD-Core Version:    0.6.0
 */