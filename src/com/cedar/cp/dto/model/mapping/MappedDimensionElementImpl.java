// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.model.mapping.MappedDimensionElement;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class MappedDimensionElementImpl implements MappedDimensionElement, Serializable, Cloneable, Comparable {

   private Object mKey;
   private int mMappingType;
   private String mVisId1;
   private String mVisId2;
   private String mVisId3;


   public MappedDimensionElementImpl(Object key, int mappingType, String visId1, String visId2, String visId3) {
      this.mKey = key;
      this.mMappingType = mappingType;
      this.mVisId1 = visId1;
      this.mVisId2 = visId2;
      this.mVisId3 = visId3;
   }

   public Object getKey() {
      return this.mKey;
   }

   public MappingKey getFinanceDimensionElementKey() {
      return new MappingKeyImpl(this.getVisId1(), this.getVisId2(), this.getVisId3());
   }

   public int getMappingType() {
      return this.mMappingType;
   }

   public void setMappingType(int mappingType) {
      this.mMappingType = mappingType;
   }

   public String getVisId1() {
      return this.mVisId1;
   }

   public void setVisId1(String visId1) {
      this.mVisId1 = visId1;
   }

   public String getVisId2() {
      return this.mVisId2;
   }

   public void setVisId2(String visId2) {
      this.mVisId2 = visId2;
   }

   public String getVisId3() {
      return this.mVisId3;
   }

   public void setVisId3(String visId3) {
      this.mVisId3 = visId3;
   }

   public Object clone() throws CloneNotSupportedException {
      MappedDimensionElementImpl copy = (MappedDimensionElementImpl)super.clone();
      copy.mKey = this.mKey;
      copy.mMappingType = this.mMappingType;
      copy.mVisId1 = this.mVisId1;
      copy.mVisId2 = this.mVisId2;
      copy.mVisId3 = this.mVisId3;
      return copy;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof MappedDimensionElement?((MappedDimensionElement)obj).getKey().equals(this.getKey()):super.equals(obj));
   }

   public int hashCode() {
      return this.mKey.hashCode();
   }

   public String toString() {
      return this.mVisId1;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<mappedDimensionElement ");
      XmlUtils.outputAttribute(out, "mappingType", Integer.valueOf(this.mMappingType));
      XmlUtils.outputAttribute(out, "visId1", this.mVisId1);
      XmlUtils.outputAttribute(out, "visId2", this.mVisId2);
      XmlUtils.outputAttribute(out, "visId3", this.mVisId3);
      out.write(" />\n");
   }

   public int compareTo(Object o) {
      if(o instanceof MappedDimensionElementImpl) {
         MappedDimensionElementImpl other = (MappedDimensionElementImpl)o;
         if(this.mKey != null && other.mKey != null && this.mKey.equals(other.mKey)) {
            return 0;
         }

         int diff;
         switch(this.mMappingType) {
         case 0:
            return this.mVisId1.compareTo(other.mVisId1);
         case 1:
            diff = this.mVisId1.compareTo(other.mVisId1);
            return diff != 0?diff:(this.mVisId3 != null && other.mVisId3 != null?this.mVisId3.compareTo(other.mVisId3):diff);
         case 2:
            diff = this.mVisId1.compareTo(this.mVisId2);
            return diff != 0?diff:this.mVisId2.compareTo(other.mVisId2);
         case 3:
            return this.mVisId1.compareTo(other.mVisId1);
         }
      }

      return -1;
   }
}
