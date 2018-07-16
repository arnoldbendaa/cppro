/*     */ package com.cedar.cp.dto.xmlform;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class XmlFormUserLinkPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mXmlFormId;
/*     */   int mUserId;
/*     */ 
/*     */   public XmlFormUserLinkPK(int newXmlFormId, int newUserId)
/*     */   {
/*  24 */     this.mXmlFormId = newXmlFormId;
/*  25 */     this.mUserId = newUserId;
/*     */   }
/*     */ 
/*     */   public int getXmlFormId()
/*     */   {
/*  34 */     return this.mXmlFormId;
/*     */   }
/*     */ 
/*     */   public int getUserId()
/*     */   {
/*  41 */     return this.mUserId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mXmlFormId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mUserId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     XmlFormUserLinkPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof XmlFormUserLinkCK)) {
/*  66 */       other = ((XmlFormUserLinkCK)obj).getXmlFormUserLinkPK();
/*     */     }
/*  68 */     else if ((obj instanceof XmlFormUserLinkPK))
/*  69 */       other = (XmlFormUserLinkPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mXmlFormId == other.mXmlFormId);
/*  76 */     eq = (eq) && (this.mUserId == other.mUserId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" XmlFormId=");
/*  88 */     sb.append(this.mXmlFormId);
/*  89 */     sb.append(",UserId=");
/*  90 */     sb.append(this.mUserId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mXmlFormId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mUserId);
/* 104 */     return "XmlFormUserLinkPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static XmlFormUserLinkPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("XmlFormUserLinkPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'XmlFormUserLinkPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pXmlFormId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pUserId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new XmlFormUserLinkPK(pXmlFormId, pUserId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlform.XmlFormUserLinkPK
 * JD-Core Version:    0.6.0
 */