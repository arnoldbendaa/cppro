/*     */ package com.cedar.cp.dto.datatype;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataTypePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   short mDataTypeId;
/*     */ 
/*     */   public DataTypePK(short newDataTypeId)
/*     */   {
/*  23 */     this.mDataTypeId = newDataTypeId;
/*     */   }
/*     */ 
/*     */   public short getDataTypeId()
/*     */   {
/*  32 */     return this.mDataTypeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mDataTypeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     DataTypePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof DataTypeCK)) {
/*  56 */       other = ((DataTypeCK)obj).getDataTypePK();
/*     */     }
/*  58 */     else if ((obj instanceof DataTypePK))
/*  59 */       other = (DataTypePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mDataTypeId == other.mDataTypeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" DataTypeId=");
/*  77 */     sb.append(this.mDataTypeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mDataTypeId);
/*  89 */     return "DataTypePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DataTypePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("DataTypePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DataTypePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     short pDataTypeId = new Short(extValues[(i++)]).shortValue();
/* 106 */     return new DataTypePK(pDataTypeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.datatype.DataTypePK
 * JD-Core Version:    0.6.0
 */