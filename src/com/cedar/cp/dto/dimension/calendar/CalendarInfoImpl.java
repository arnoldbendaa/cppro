// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.StructureElementNode;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.dimension.StructureElementCK;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarElementNodeImpl;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CalendarInfoImpl implements CalendarInfo, Serializable {

   private int mStructureId;
   private CalendarElementNode mRoot;
   private Map<Integer, CalendarElementNodeImpl> mNodeMap;


   public CalendarInfoImpl(int structureId, CalendarElementNode root, Map<Integer, CalendarElementNodeImpl> nodeMap) {
      this.mStructureId = structureId;
      this.mRoot = root;
      this.mNodeMap = nodeMap;
   }

   public int getCalendarId() {
      return this.mStructureId;
   }

   public CalendarElementNode getRoot() {
      return this.mRoot;
   }

   public CalendarElementNode getElementOffsetByYearAndPeriod(int seid, int yearOffset, int offset) {
      CalendarElementNodeImpl elem = (CalendarElementNodeImpl)this.mNodeMap.get(Integer.valueOf(seid));
      if(elem == null) {
         return null;
      } else {
         CalendarElementNodeImpl result = elem;
         if(yearOffset != 0) {
            result = (CalendarElementNodeImpl)this.getElementOffsetByYear(seid, yearOffset);
         }

         if(result == null) {
            return null;
         } else {
            if(offset != 0) {
               result = (CalendarElementNodeImpl)this.getElementByOffset(result.getStructureElementId(), offset);
            }

            return result;
         }
      }
   }

   public CalendarElementNode getElementByOffset(int seid, int offset) {
      CalendarElementNodeImpl elem = (CalendarElementNodeImpl)this.mNodeMap.get(Integer.valueOf(seid));
      if(elem == null) {
         return null;
      } else if(offset == 0) {
         return elem;
      } else {
         List siblingsInThisCalendar = ((CalendarElementNodeImpl)this.mRoot).getChildNodesOfType(elem.getElemType(), new ArrayList());
         int currentIndex = siblingsInThisCalendar.indexOf(elem);
         int newIndex = currentIndex + offset;
         return newIndex >= 0 && newIndex < siblingsInThisCalendar.size()?(CalendarElementNode)siblingsInThisCalendar.get(newIndex):null;
      }
   }

   public CalendarElementNode getElementOffsetByYear(int seid, int yearOffset) {
      CalendarElementNodeImpl elem = (CalendarElementNodeImpl)this.mNodeMap.get(Integer.valueOf(seid));
      if(elem == null) {
         return null;
      } else if(yearOffset == 0) {
         return elem;
      } else {
         CalendarElementNodeImpl currentYear = (CalendarElementNodeImpl)elem.getYearNode();
         if(currentYear == null) {
            return null;
         } else {
            List years = ((CalendarElementNodeImpl)currentYear.getParent()).getChildren();
            int targetYearIndex = years.indexOf(currentYear) + yearOffset;
            if(targetYearIndex >= 0 && targetYearIndex < years.size()) {
               CalendarElementNodeImpl targetYear = (CalendarElementNodeImpl)years.get(targetYearIndex);
               List siblingsInThisYear = currentYear.getChildNodesOfType(elem.getElemType(), new ArrayList());
               int currentIndexInYear = siblingsInThisYear.indexOf(elem);
               List siblingsInTargetYear = targetYear.getChildNodesOfType(elem.getElemType(), new ArrayList());
               return currentIndexInYear >= 0 && currentIndexInYear < siblingsInTargetYear.size()?(CalendarElementNode)siblingsInTargetYear.get(currentIndexInYear):null;
            } else {
               return null;
            }
         }
      }
   }

   public List<CalendarElementNode> getYtdElements(int seid) {
      CalendarElementNodeImpl elem = (CalendarElementNodeImpl)this.mNodeMap.get(Integer.valueOf(seid));
      if(elem == null) {
         return null;
      } else {
         CalendarElementNodeImpl year = (CalendarElementNodeImpl)elem.getYearNode();
         if(year == null) {
            return null;
         } else {
            ArrayList siblingsInThisYear = new ArrayList();
            if(elem.getElemType() != 3 && elem.getElemType() != 6 && elem.getElemType() != 8 && elem.getElemType() != 9 && elem.getElemType() != 7) {
               year.getChildNodesOfType(elem.getElemType(), siblingsInThisYear);
            } else {
               year.getChildNodesOfType(6, siblingsInThisYear);
               year.getChildNodesOfType(3, siblingsInThisYear);
               year.getChildNodesOfType(8, siblingsInThisYear);
               year.getChildNodesOfType(9, siblingsInThisYear);
               year.getChildNodesOfType(7, siblingsInThisYear);
            }

            int currentIndexInYear = siblingsInThisYear.indexOf(elem);
            ArrayList result = new ArrayList();
            result.addAll(siblingsInThisYear.subList(0, currentIndexInYear + 1));
            return result;
         }
      }
   }

   public CalendarElementNode findRelativeElement(CalendarElementNode contextNode, String pathToRoot) {
      if(pathToRoot == null) {
         return null;
      } else {
         if(pathToRoot.indexOf(63) > -1) {
            CalendarElementNode[] contextPath = contextNode.getPath();
            if(pathToRoot.startsWith("/")) {
               pathToRoot = pathToRoot.substring(1);
            }

            String[] parts = pathToRoot.split("/");

            for(int path = 0; path < parts.length; ++path) {
               if("?".equals(parts[path]) && path < contextPath.length) {
                  parts[path] = contextPath[path + 1].getVisId();
               }
            }

            StringBuilder var10 = new StringBuilder();
            String[] arr$ = parts;
            int len$ = parts.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               String part = arr$[i$];
               if(part != null && part.length() > 0) {
                  var10.append("/");
                  var10.append(part);
               }
            }

            pathToRoot = var10.toString();
         }

         return this.findElement(pathToRoot);
      }
   }

   public CalendarElementNode findElement(String pathToRoot) {
      StringTokenizer st = new StringTokenizer(pathToRoot, "/", false);
      ArrayList tokens = new ArrayList();

      while(st.hasMoreTokens()) {
         tokens.add(st.nextToken());
      }

      if(tokens.isEmpty()) {
         return null;
      } else {
         ArrayList searchNodes = new ArrayList();
         searchNodes.add(this.mRoot);
         return this.findElement(tokens, 0, searchNodes);
      }
   }

   private CalendarElementNode findElement(List<String> tokens, int tokenIndex, List<CalendarElementNode> nodes) {
      if(tokenIndex >= tokens.size()) {
         return !nodes.isEmpty()?(CalendarElementNode)nodes.get(0):null;
      } else {
         String token = (String)tokens.get(tokenIndex);
         Iterator i$ = nodes.iterator();

         CalendarElementNode found;
         do {
            if(!i$.hasNext()) {
               return null;
            }

            CalendarElementNode node = (CalendarElementNode)i$.next();
            List matches = ((CalendarElementNodeImpl)node).findChildElementsWithVisId(token, new ArrayList());
            found = this.findElement(tokens, tokenIndex + 1, matches);
         } while(found == null);

         return found;
      }
   }

   public CalendarElementNode getById(int structureElementId) {
      return (CalendarElementNode)this.mNodeMap.get(Integer.valueOf(structureElementId));
   }

   public CalendarElementNode getById(Object id) {
      if(id instanceof StructureElementNode) {
         id = Integer.valueOf(((StructureElementNode)id).getStructureElementId());
      }

      if(id instanceof StructureElementRef) {
         id = ((StructureElementRef)id).getPrimaryKey();
      }

      if(id instanceof StructureElementCK) {
         id = ((StructureElementCK)id).getPK();
      }

      if(id instanceof StructureElementPK) {
         return this.getById(((StructureElementPK)id).getStructureElementId());
      } else if(id instanceof Integer) {
         return this.getById(((Integer)id).intValue());
      } else {
         throw new IllegalArgumentException("Unexpected calendar element key:" + id);
      }
   }

   public static CalendarInfoImpl getCalendarInfo(Object key, EntityList elems) {
      CalendarElementNodeImpl root = null;
      ArrayList nodes = new ArrayList();
      HashMap nodeMap = new HashMap();

      for(int i$ = 0; i$ < elems.getNumRows(); ++i$) {
         StructureElementRef node = (StructureElementRef)elems.getValueAt(i$, "StructureElement");
         int parent = ((StructureElementPK)node.getPrimaryKey()).getStructureId();
         int structureElementId = ((StructureElementPK)node.getPrimaryKey()).getStructureElementId();
         int parentId = ((Integer)elems.getValueAt(i$, "ParentId")).intValue();
         String description = (String)elems.getValueAt(i$, "Description");
         int depth = ((Integer)elems.getValueAt(i$, "Depth")).intValue();
         int position = ((Integer)elems.getValueAt(i$, "Position")).intValue();
         int endPosition = ((Integer)elems.getValueAt(i$, "EndPosition")).intValue();
         int calElemType = ((Integer)elems.getValueAt(i$, "CalElemType")).intValue();
         Timestamp actualDate = (Timestamp)elems.getValueAt(i$, "ActualDate");
         CalendarElementNodeImpl node1 = new CalendarElementNodeImpl(parentId, parent, structureElementId, node.getNarrative(), description, position, endPosition, depth, calElemType, actualDate, (List)null);
         nodes.add(node1);
         nodeMap.put(Integer.valueOf(structureElementId), node1);
      }

      Iterator var17 = nodes.iterator();

      while(var17.hasNext()) {
         CalendarElementNodeImpl var18 = (CalendarElementNodeImpl)var17.next();
         if(var18.getParentId() != 0) {
            CalendarElementNodeImpl var19 = (CalendarElementNodeImpl)nodeMap.get(Integer.valueOf(var18.getParentId()));
            if(var19 == null) {
               throw new IllegalStateException("Failed to locate calendar element node id : " + var18.getParentId());
            }

            if(var19.getChildren() == null) {
               var19.setChildren(new ArrayList());
            }

            var19.getChildren().add(var18);
            var18.setParent(var19);
         } else {
            root = var18;
         }
      }

      return new CalendarInfoImpl(((Integer)key).intValue(), root, nodeMap);
   }
}
