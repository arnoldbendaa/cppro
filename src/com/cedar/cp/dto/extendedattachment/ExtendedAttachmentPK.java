/*     */ package com.cedar.cp.dto.extendedattachment;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtendedAttachmentPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mExtendedAttachmentId;
/*     */ 
/*     */   public ExtendedAttachmentPK(int newExtendedAttachmentId)
/*     */   {
/*  23 */     this.mExtendedAttachmentId = newExtendedAttachmentId;
/*     */   }
/*     */ 
/*     */   public int getExtendedAttachmentId()
/*     */   {
/*  32 */     return this.mExtendedAttachmentId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mExtendedAttachmentId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ExtendedAttachmentPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ExtendedAttachmentCK)) {
/*  56 */       other = ((ExtendedAttachmentCK)obj).getExtendedAttachmentPK();
/*     */     }
/*  58 */     else if ((obj instanceof ExtendedAttachmentPK))
/*  59 */       other = (ExtendedAttachmentPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mExtendedAttachmentId == other.mExtendedAttachmentId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ExtendedAttachmentId=");
/*  77 */     sb.append(this.mExtendedAttachmentId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mExtendedAttachmentId);
/*  89 */     return "ExtendedAttachmentPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExtendedAttachmentPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ExtendedAttachmentPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExtendedAttachmentPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pExtendedAttachmentId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ExtendedAttachmentPK(pExtendedAttachmentId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK
 * JD-Core Version:    0.6.0
 */