/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysPropertyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mExternalSystemId;
/*     */   String mPropertyName;
/*     */ 
/*     */   public ExtSysPropertyPK(int newExternalSystemId, String newPropertyName)
/*     */   {
/*  24 */     this.mExternalSystemId = newExternalSystemId;
/*  25 */     this.mPropertyName = newPropertyName;
/*     */   }
/*     */ 
/*     */   public int getExternalSystemId()
/*     */   {
/*  34 */     return this.mExternalSystemId;
/*     */   }
/*     */ 
/*     */   public String getPropertyName()
/*     */   {
/*  41 */     return this.mPropertyName;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mExternalSystemId).hashCode();
/*  52 */       this.mHashCode += this.mPropertyName.hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ExtSysPropertyPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ExtSysPropertyCK)) {
/*  66 */       other = ((ExtSysPropertyCK)obj).getExtSysPropertyPK();
/*     */     }
/*  68 */     else if ((obj instanceof ExtSysPropertyPK))
/*  69 */       other = (ExtSysPropertyPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mExternalSystemId == other.mExternalSystemId);
/*  76 */     eq = (eq) && (this.mPropertyName.equals(other.mPropertyName));
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ExternalSystemId=");
/*  88 */     sb.append(this.mExternalSystemId);
/*  89 */     sb.append(",PropertyName=");
/*  90 */     sb.append(this.mPropertyName);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mExternalSystemId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mPropertyName);
/* 104 */     return "ExtSysPropertyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExtSysPropertyPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ExtSysPropertyPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExtSysPropertyPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pExternalSystemId = new Integer(extValues[(i++)]).intValue();
/* 121 */     String pPropertyName = new String(extValues[(i++)]);
/* 122 */     return new ExtSysPropertyPK(pExternalSystemId, pPropertyName);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysPropertyPK
 * JD-Core Version:    0.6.0
 */