/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="nullableBoolean")
/*    */ @XmlEnum
/*    */ public enum NullableBoolean
/*    */ {
/* 35 */   TRUE("true"), 
/*    */ 
/* 37 */   FALSE("false");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 42 */   private NullableBoolean(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 46 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static NullableBoolean fromValue(String v) {
/* 50 */     for (NullableBoolean c : values()) {
/* 51 */       if (c.value.equals(v)) {
/* 52 */         return c;
/*    */       }
/*    */     }
/* 55 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.NullableBoolean
 * JD-Core Version:    0.6.0
 */