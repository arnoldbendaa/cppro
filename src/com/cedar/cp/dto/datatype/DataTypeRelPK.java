/*     */ package com.cedar.cp.dto.datatype;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataTypeRelPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   short mDataTypeId;
/*     */   short mRefDataTypeId;
/*     */ 
/*     */   public DataTypeRelPK(short newDataTypeId, short newRefDataTypeId)
/*     */   {
/*  24 */     this.mDataTypeId = newDataTypeId;
/*  25 */     this.mRefDataTypeId = newRefDataTypeId;
/*     */   }
/*     */ 
/*     */   public short getDataTypeId()
/*     */   {
/*  34 */     return this.mDataTypeId;
/*     */   }
/*     */ 
/*     */   public short getRefDataTypeId()
/*     */   {
/*  41 */     return this.mRefDataTypeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mDataTypeId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mRefDataTypeId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     DataTypeRelPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof DataTypeRelCK)) {
/*  66 */       other = ((DataTypeRelCK)obj).getDataTypeRelPK();
/*     */     }
/*  68 */     else if ((obj instanceof DataTypeRelPK))
/*  69 */       other = (DataTypeRelPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mDataTypeId == other.mDataTypeId);
/*  76 */     eq = (eq) && (this.mRefDataTypeId == other.mRefDataTypeId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" DataTypeId=");
/*  88 */     sb.append(this.mDataTypeId);
/*  89 */     sb.append(",RefDataTypeId=");
/*  90 */     sb.append(this.mRefDataTypeId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mDataTypeId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mRefDataTypeId);
/* 104 */     return "DataTypeRelPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DataTypeRelPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("DataTypeRelPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DataTypeRelPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     short pDataTypeId = new Short(extValues[(i++)]).shortValue();
/* 121 */     short pRefDataTypeId = new Short(extValues[(i++)]).shortValue();
/* 122 */     return new DataTypeRelPK(pDataTypeId, pRefDataTypeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.datatype.DataTypeRelPK
 * JD-Core Version:    0.6.0
 */