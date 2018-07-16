/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="hierarchyActionType")
/*    */ @XmlEnum
/*    */ public enum HierarchyActionType
/*    */ {
/* 37 */   INSERT("insert"), 
/*    */ 
/* 39 */   AMEND("amend"), 
/*    */ 
/* 41 */   MOVE("move"), 
/*    */ 
/* 43 */   DELETE("delete");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 48 */   private HierarchyActionType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 52 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static HierarchyActionType fromValue(String v) {
/* 56 */     for (HierarchyActionType c : values()) {
/* 57 */       if (c.value.equals(v)) {
/* 58 */         return c;
/*    */       }
/*    */     }
/* 61 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.HierarchyActionType
 * JD-Core Version:    0.6.0
 */