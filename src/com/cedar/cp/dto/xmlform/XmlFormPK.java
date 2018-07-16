/*     */ package com.cedar.cp.dto.xmlform;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class XmlFormPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mXmlFormId;
/*     */ 
/*     */   public XmlFormPK(int newXmlFormId)
/*     */   {
/*  23 */     this.mXmlFormId = newXmlFormId;
/*     */   }
/*     */ 
/*     */   public int getXmlFormId()
/*     */   {
/*  32 */     return this.mXmlFormId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mXmlFormId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     XmlFormPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof XmlFormCK)) {
/*  56 */       other = ((XmlFormCK)obj).getXmlFormPK();
/*     */     }
/*  58 */     else if ((obj instanceof XmlFormPK))
/*  59 */       other = (XmlFormPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mXmlFormId == other.mXmlFormId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" XmlFormId=");
/*  77 */     sb.append(this.mXmlFormId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mXmlFormId);
/*  89 */     return "XmlFormPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static XmlFormPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("XmlFormPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'XmlFormPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pXmlFormId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new XmlFormPK(pXmlFormId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlform.XmlFormPK
 * JD-Core Version:    0.6.0
 */