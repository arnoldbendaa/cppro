/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="dimensionEventType")
/*    */ @XmlEnum
/*    */ public enum DimensionEventType
/*    */ {
/* 36 */   DIMENSION_ELEMENT("dimension-element"), 
/*    */ 
/* 38 */   HIERARCHY("hierarchy"), 
/*    */ 
/* 40 */   YEAR("year");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 45 */   private DimensionEventType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static DimensionEventType fromValue(String v) {
/* 53 */     for (DimensionEventType c : values()) {
/* 54 */       if (c.value.equals(v)) {
/* 55 */         return c;
/*    */       }
/*    */     }
/* 58 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.DimensionEventType
 * JD-Core Version:    0.6.0
 */