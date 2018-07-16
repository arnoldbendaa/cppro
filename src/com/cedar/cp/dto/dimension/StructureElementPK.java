/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.api.dimension.StructureElementKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class StructureElementPK extends PrimaryKey
/*     */   implements Serializable, StructureElementKey
/*     */ {
/* 137 */   private int mHashCode = -2147483648;
/*     */   int mStructureId;
/*     */   int mStructureElementId;
/*     */ 
/*     */   public StructureElementPK(int newStructureId, int newStructureElementId)
/*     */   {
/*  26 */     this.mStructureId = newStructureId;
/*  27 */     this.mStructureElementId = newStructureElementId;
/*     */   }
/*     */ 
/*     */   public int getStructureId()
/*     */   {
/*  36 */     return this.mStructureId;
/*     */   }
/*     */ 
/*     */   public int getStructureElementId()
/*     */   {
/*  43 */     return this.mStructureElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  51 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  53 */       this.mHashCode += String.valueOf(this.mStructureId).hashCode();
/*  54 */       this.mHashCode += String.valueOf(this.mStructureElementId).hashCode();
/*     */     }
/*     */ 
/*  57 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     StructureElementPK other = null;
/*     */ 
/*  67 */     if ((obj instanceof StructureElementCK)) {
/*  68 */       other = ((StructureElementCK)obj).getStructureElementPK();
/*     */     }
/*  70 */     else if ((obj instanceof StructureElementPK))
/*  71 */       other = (StructureElementPK)obj;
/*     */     else {
/*  73 */       return false;
/*     */     }
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mStructureId == other.mStructureId);
/*  78 */     eq = (eq) && (this.mStructureElementId == other.mStructureElementId);
/*     */ 
/*  80 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  88 */     StringBuffer sb = new StringBuffer();
/*  89 */     sb.append(" StructureId=");
/*  90 */     sb.append(this.mStructureId);
/*  91 */     sb.append(",StructureElementId=");
/*  92 */     sb.append(this.mStructureElementId);
/*  93 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 101 */     StringBuffer sb = new StringBuffer();
/* 102 */     sb.append(" ");
/* 103 */     sb.append(this.mStructureId);
/* 104 */     sb.append(",");
/* 105 */     sb.append(this.mStructureElementId);
/* 106 */     return "StructureElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static StructureElementPK getKeyFromTokens(String extKey)
/*     */   {
/* 111 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 113 */     if (extValues.length != 2) {
/* 114 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 116 */     if (!extValues[0].equals("StructureElementPK")) {
/* 117 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'StructureElementPK|'");
/*     */     }
/* 119 */     extValues = extValues[1].split(",");
/*     */ 
/* 121 */     int i = 0;
/* 122 */     int pStructureId = new Integer(extValues[(i++)]).intValue();
/* 123 */     int pStructureElementId = new Integer(extValues[(i++)]).intValue();
/* 124 */     return new StructureElementPK(pStructureId, pStructureElementId);
/*     */   }
/*     */ 
/*     */   public int getId()
/*     */   {
/* 133 */     return this.mStructureElementId;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.StructureElementPK
 * JD-Core Version:    0.6.0
 */