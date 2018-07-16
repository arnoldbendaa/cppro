// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.StructureElementNode;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import java.io.Serializable;

public class StructureElementNodeImpl implements StructureElementNode, Cloneable, Serializable {

   private StructureElementRefImpl mStructureElementRef;
   private int mStructureId;
   private int mStructureElementId;
   private String mVisId;
   private String mDescription;
   private int mPosition;
   private int mDepth;
   private boolean mLeaf;
   private boolean mIsCredit;
   private int mCalendarType;
   private transient CPConnection mConnection;


   public StructureElementRef getStructureElementRef() {
      return this.mStructureElementRef;
   }

   public void setStructureElementRef(StructureElementRefImpl structureElementRef) {
      this.mStructureElementRef = structureElementRef;
   }

   public StructureElementNodeImpl(CPConnection connection, EntityList pEntityList) {
      this.mConnection = connection;
      this.mStructureElementRef = (StructureElementRefImpl)pEntityList.getValueAt(0, "StructureElement");
      this.mDescription = (String)pEntityList.getValueAt(0, "Description");
      this.mIsCredit = ((Boolean)pEntityList.getValueAt(0, "IsCredit")).booleanValue();
      this.mLeaf = ((Boolean)pEntityList.getValueAt(0, "Leaf")).booleanValue();
      this.mPosition = ((Integer)pEntityList.getValueAt(0, "Position")).intValue();
      this.mDepth = ((Integer)pEntityList.getValueAt(0, "Depth")).intValue();
      this.mStructureElementId = ((Integer)pEntityList.getValueAt(0, "StructureElementId")).intValue();
      this.mStructureId = ((Integer)pEntityList.getValueAt(0, "StructureId")).intValue();
      if(EntityListImpl.containsColumn(pEntityList, "CalElemType")) {
         this.mCalendarType = ((Integer)pEntityList.getValueAt(0, "CalElemType")).intValue();
      }

      this.mVisId = (String)pEntityList.getValueAt(0, "VisId");
   }

   public StructureElementNodeImpl(CPConnection connection, EntityList pEntityList, String source) {
      this.mConnection = connection;
      this.mStructureElementRef = (StructureElementRefImpl)pEntityList.getValueAt(0, "StructureElementRef");
      this.mDescription = (String)pEntityList.getValueAt(0, "StructureElementDescription");
      this.mLeaf = ((Boolean)pEntityList.getValueAt(0, "Leaf")).booleanValue();
      this.mStructureElementId = ((Integer)pEntityList.getValueAt(0, "StructureElementId")).intValue();
      this.mStructureId = ((Integer)pEntityList.getValueAt(0, "StructureId")).intValue();
      if(EntityListImpl.containsColumn(pEntityList, "CalElemType")) {
         this.mCalendarType = ((Integer)pEntityList.getValueAt(0, "CalElemType")).intValue();
      }

      this.mVisId = (String)pEntityList.getValueAt(0, "StructureElementVisId");
   }

   protected Object clone() throws CloneNotSupportedException {
      StructureElementNodeImpl node = (StructureElementNodeImpl)super.clone();
      node.mStructureElementRef = this.mStructureElementRef;
      node.mStructureId = this.mStructureId;
      node.mStructureElementId = this.mStructureElementId;
      node.mVisId = this.mVisId;
      node.mDescription = this.mDescription;
      node.mPosition = this.mPosition;
      node.mDepth = this.mDepth;
      node.mLeaf = this.mLeaf;
      node.mIsCredit = this.mIsCredit;
      node.mConnection = this.mConnection;
      return node;
   }

   public StructureElementNodeImpl() {}

   public StructureElementNodeImpl(int structureId, int structureElementId) {
      this.mStructureId = structureId;
      this.mStructureElementId = structureElementId;
   }

   public Object getPrimaryKey() {
      return this.mStructureElementRef.getPrimaryKey();
   }

   public String getDescription() {
      return this.mDescription == null?"":this.mDescription;
   }

   public void setDescription(String pDescription) {
      this.mDescription = pDescription;
   }

   public boolean isIsCredit() {
      return this.mIsCredit;
   }

   public void setIsCredit(boolean pIsCredit) {
      this.mIsCredit = pIsCredit;
   }

   public boolean isLeaf() {
      return this.mLeaf;
   }

   public void setLeaf(boolean pLeaf) {
      this.mLeaf = pLeaf;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public int getPosition() {
      return this.mPosition;
   }

   public void setPosition(int pPosition) {
      this.mPosition = pPosition;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public void setStructureElementId(int pStructureElementId) {
      this.mStructureElementId = pStructureElementId;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public void setStructureId(int pStructureId) {
      this.mStructureId = pStructureId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String pVisId) {
      this.mVisId = pVisId;
   }

   public int getCalendarType() {
      return this.mCalendarType;
   }

   public void setCalendarType(int calendarType) {
      this.mCalendarType = calendarType;
   }

   public String toString() {
      return this.mVisId + " - " + this.getDescription();
   }

   public int getId() {
      return this.getStructureElementId();
   }

   public CPConnection getConnection() {
      return this.mConnection;
   }
}
