// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.RTree;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class CellFormatEntry {

   private FormatProperty mFormatProperty;
   private RTree.Rect mRect;
   private long mVersion;


   public CellFormatEntry(FormatProperty formatProperty, RTree.Rect rect, long version) {
      this.mFormatProperty = formatProperty;
      this.mRect = rect;
      this.mVersion = version;
   }

   public FormatProperty getFormatProperty() {
      return this.mFormatProperty;
   }

   public void setFormatProperty(FormatProperty formatProperty) {
      this.mFormatProperty = formatProperty;
   }

   public RTree.Rect getRect() {
      return this.mRect;
   }

   public void setRect(RTree.Rect rect) {
      this.mRect = rect;
   }

   public long getVersion() {
      return this.mVersion;
   }

   public void setVersion(long version) {
      this.mVersion = version;
   }

   public static CellFormatEntry selectMostSignificantFormat(Collection<CellFormatEntry> formats) {
      long cellCount = 0L;
      CellFormatEntry result = null;
      Iterator i$ = formats.iterator();

      while(i$.hasNext()) {
         CellFormatEntry entry = (CellFormatEntry)i$.next();
         long currentCellCount = entry.getRect().area();
         Iterator i$1 = formats.iterator();

         while(true) {
            if(i$1.hasNext()) {
               CellFormatEntry checkEntry = (CellFormatEntry)i$1.next();
               if(checkEntry == entry || checkEntry.getVersion() <= entry.getVersion() || !checkEntry.getRect().intersects(entry.getRect())) {
                  continue;
               }

               currentCellCount -= RTree.Rect.intersection(checkEntry.getRect(), entry.getRect(), new RTree.Rect()).area();
               if(currentCellCount >= 0L) {
                  continue;
               }

               currentCellCount = 0L;
            }

            if(currentCellCount > cellCount) {
               result = entry;
               cellCount = currentCellCount;
            }
            break;
         }
      }

      return result;
   }

   public static void pruneRedundantFormats(Collection<CellFormatEntry> formats) {
      ArrayList delList = new ArrayList();
      Iterator i$ = formats.iterator();

      CellFormatEntry entry;
      while(i$.hasNext()) {
         entry = (CellFormatEntry)i$.next();
         long cellCount = entry.getRect().area();
         Iterator i$1 = formats.iterator();

         while(i$1.hasNext()) {
            CellFormatEntry refEntry = (CellFormatEntry)i$1.next();
            if(refEntry != entry && refEntry.getVersion() > entry.getVersion() && refEntry.getRect().intersects(entry.getRect())) {
               cellCount -= RTree.Rect.intersection(refEntry.getRect(), entry.getRect(), new RTree.Rect()).area();
            }
         }

         if(cellCount <= 0L) {
            delList.add(entry);
         }
      }

      i$ = delList.iterator();

      while(i$.hasNext()) {
         entry = (CellFormatEntry)i$.next();
         formats.remove(entry);
      }

   }

   public static boolean hasMultipleFormats(Map<String, Collection<CellFormatEntry>> activeFormats, String name) {
      if(activeFormats == null) {
         return false;
      } else {
         Collection entries = (Collection)activeFormats.get(name);
         if(entries != null) {
            FormatProperty property = null;
            Iterator i$ = entries.iterator();

            while(i$.hasNext()) {
               CellFormatEntry cfe = (CellFormatEntry)i$.next();
               if(property == null) {
                  property = cfe.getFormatProperty();
               } else if(GeneralUtils.isDifferent(property, cfe.getFormatProperty())) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
