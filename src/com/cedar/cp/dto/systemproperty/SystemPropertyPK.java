/*     */ package com.cedar.cp.dto.systemproperty;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SystemPropertyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mSystemPropertyId;
/*     */ 
/*     */   public SystemPropertyPK(int newSystemPropertyId)
/*     */   {
/*  23 */     this.mSystemPropertyId = newSystemPropertyId;
/*     */   }
/*     */ 
/*     */   public int getSystemPropertyId()
/*     */   {
/*  32 */     return this.mSystemPropertyId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mSystemPropertyId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     SystemPropertyPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof SystemPropertyCK)) {
/*  56 */       other = ((SystemPropertyCK)obj).getSystemPropertyPK();
/*     */     }
/*  58 */     else if ((obj instanceof SystemPropertyPK))
/*  59 */       other = (SystemPropertyPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mSystemPropertyId == other.mSystemPropertyId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" SystemPropertyId=");
/*  77 */     sb.append(this.mSystemPropertyId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mSystemPropertyId);
/*  89 */     return "SystemPropertyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static SystemPropertyPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("SystemPropertyPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'SystemPropertyPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pSystemPropertyId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new SystemPropertyPK(pSystemPropertyId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.systemproperty.SystemPropertyPK
 * JD-Core Version:    0.6.0
 */