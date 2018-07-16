/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="hierarchyEventType")
/*    */ @XmlEnum
/*    */ public enum HierarchyEventType
/*    */ {
/* 35 */   HIERARCHY_ELEMENT("hierarchy-element"), 
/*    */ 
/* 37 */   HIERARCHY_ELEMENT_FEED("hierarchy-element-feed");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 42 */   private HierarchyEventType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 46 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static HierarchyEventType fromValue(String v) {
/* 50 */     for (HierarchyEventType c : values()) {
/* 51 */       if (c.value.equals(v)) {
/* 52 */         return c;
/*    */       }
/*    */     }
/* 55 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.HierarchyEventType
 * JD-Core Version:    0.6.0
 */