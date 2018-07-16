// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.AllDimensionElementsELO;
import com.cedar.cp.dto.dimension.AllDimensionElementsForDimensionELO;
import com.cedar.cp.dto.dimension.AllDimensionElementsForModelELO;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.dimension.AllFeedsForDimensionElementELO;
import com.cedar.cp.dto.dimension.AllHierarchysELO;
import com.cedar.cp.dto.dimension.AllSecurityRangesELO;
import com.cedar.cp.dto.dimension.AllSecurityRangesForModelELO;
import com.cedar.cp.dto.dimension.AugHierarachyElementELO;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.CalendarForFinanceCubeELO;
import com.cedar.cp.dto.dimension.CalendarForModelELO;
import com.cedar.cp.dto.dimension.CalendarForModelVisIdELO;
import com.cedar.cp.dto.dimension.DimensionCK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarachyElementELO;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
import com.cedar.cp.dto.dimension.HierarcyDetailsIncRootNodeFromDimIdELO;
import com.cedar.cp.dto.dimension.ImportableDimensionsELO;
import com.cedar.cp.dto.dimension.ImportableHierarchiesELO;
import com.cedar.cp.dto.dimension.SelectedHierarchysELO;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.dimension.AugHierarchyElementDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionLocal;
import com.cedar.cp.ejb.impl.dimension.DimensionLocalHome;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAO;
import com.cedar.cp.ejb.impl.dimension.SecurityRangeDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DimensionAccessor implements Serializable {

   private DimensionLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_ELEMENTS = "<0>";
   public static final String GET_CALENDAR_SPEC = "<1>";
   public static final String GET_CALENDAR_YEAR_SPECS = "<2>";
   public static final String GET_HIERARCHIES = "<3>";
   public static final String GET_HIERARCHY_ELEMENTS = "<4>";
   public static final String GET_FEEDER_ELEMENTS = "<5>";
   public static final String GET_AUG_HIERARCHY_ELEMENTS = "<6>";
   public static final String GET_SECURITY_RANGES = "<7>";
   public static final String GET_RANGE_ROWS = "<8>";
   public static final String GET_ALL_DEPENDANTS = "<0><1><2><3><4><5><6><7><8>";
   static DimensionEEJB dimensionEEJB = new DimensionEEJB();


   public DimensionAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private DimensionLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (DimensionLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/DimensionLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up DimensionLocalHome", var2);
      }
   }

   private DimensionEEJB getLocal(DimensionPK pk) throws Exception {
//      DimensionLocal local = (DimensionLocal)this.mLocals.get(pk);
//      if(local == null) {
//         local = this.getLocalHome().findByPrimaryKey(pk);
//         this.mLocals.put(pk, local);
//      }
//
//      return local;
	   return dimensionEEJB;
   }

   public DimensionEVO create(DimensionEVO evo) throws Exception {
//      DimensionLocal local = this.getLocalHome().create(evo);
//      DimensionEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      DimensionPK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
//      return newevo;
	    dimensionEEJB.ejbCreate(evo);
	    DimensionEVO newevo = dimensionEEJB.getDetails("<UseLoadedEVOs>");
	    DimensionPK pk = newevo.getPK();
		this.mLocals.put(pk, dimensionEEJB);
		return newevo;
   }

   public void remove(DimensionPK pk) throws Exception {
//      this.getLocal(pk).remove();
      dimensionEEJB.ejbRemove();
   }

   public DimensionEVO getDetails(Object paramKey, String dependants) throws Exception {
	   DimensionEEJB dimensionEEJBLocal = new DimensionEEJB();
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof HierarchyPK) {
         key = (HierarchyCK)this.getCKForDependantPK((PrimaryKey)key);
      }

      if(key instanceof DimensionCK) {
         DimensionPK pk = ((DimensionCK)key).getDimensionPK();
         return dimensionEEJB.getDetails((DimensionCK)key, dependants);
      } else {
//         return key instanceof DimensionPK?this.getLocal((DimensionPK)key).getDetails(dependants):null;
    	  DimensionPK pk = (DimensionPK)key;
    	  DimensionCK ck = new DimensionCK(pk);
    	  if(key instanceof DimensionPK)
    		  return dimensionEEJB.getDetails(ck,dependants);
    	  else
    		  return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      if(key instanceof HierarchyPK) {
         HierarchyDAO dao = new HierarchyDAO();
         return (CompositeKey)dao.getRef((HierarchyPK)key).getPrimaryKey();
      } else {
         return null;
      }
   }

   public void setDetails(DimensionEVO evo) throws Exception {
      DimensionPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public DimensionEVO setAndGetDetails(DimensionEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public DimensionPK generateKeys(DimensionPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public Collection findAll() throws Exception {
      DimensionDAO dao = new DimensionDAO();
      return dao.findAll();
   }

   public AllDimensionsELO getAllDimensions() {
      DimensionDAO dao = new DimensionDAO();
      return dao.getAllDimensions();
   }

   public AvailableDimensionsELO getAvailableDimensions() {
      DimensionDAO dao = new DimensionDAO();
      return dao.getAvailableDimensions();
   }

   public ImportableDimensionsELO getImportableDimensions() {
      DimensionDAO dao = new DimensionDAO();
      return dao.getImportableDimensions();
   }

   public AllDimensionsForModelELO getAllDimensionsForModel(int param1) {
      DimensionDAO dao = new DimensionDAO();
      return dao.getAllDimensionsForModel(param1);
   }

   public AllDimensionElementsELO getAllDimensionElements() {
      DimensionElementDAO dao = new DimensionElementDAO();
      return dao.getAllDimensionElements();
   }

   public AllDimensionElementsForDimensionELO getAllDimensionElementsForDimension(int param1) {
      DimensionElementDAO dao = new DimensionElementDAO();
      return dao.getAllDimensionElementsForDimension(param1);
   }
   
   public AllDimensionElementsForModelELO getAllDimensionElementsForModels(List<Integer> modelIds) {
       DimensionElementDAO dao = new DimensionElementDAO();
       return dao.getAllDimensionElementsForModels(modelIds);
    }

   public AllHierarchysELO getAllHierarchys() {
      HierarchyDAO dao = new HierarchyDAO();
      return dao.getAllHierarchys();
   }
   
	public SelectedHierarchysELO getSelectedHierarchys() {
		HierarchyDAO dao = new HierarchyDAO();
		return dao.getSelectedHierarchys();
	}

   public ImportableHierarchiesELO getImportableHierarchies(int param1) {
      HierarchyDAO dao = new HierarchyDAO();
      return dao.getImportableHierarchies(param1);
   }

   public HierarcyDetailsFromDimIdELO getHierarcyDetailsFromDimId(int param1) {
      HierarchyDAO dao = new HierarchyDAO();
      return dao.getHierarcyDetailsFromDimId(param1);
   }

   public HierarcyDetailsIncRootNodeFromDimIdELO getHierarcyDetailsIncRootNodeFromDimId(int param1) {
      HierarchyDAO dao = new HierarchyDAO();
      return dao.getHierarcyDetailsIncRootNodeFromDimId(param1);
   }

   public CalendarForModelELO getCalendarForModel(int param1) {
      HierarchyDAO dao = new HierarchyDAO();
      return dao.getCalendarForModel(param1);
   }

   public CalendarForModelVisIdELO getCalendarForModelVisId(String param1) {
      HierarchyDAO dao = new HierarchyDAO();
      return dao.getCalendarForModelVisId(param1);
   }

   public CalendarForFinanceCubeELO getCalendarForFinanceCube(int param1) {
      HierarchyDAO dao = new HierarchyDAO();
      return dao.getCalendarForFinanceCube(param1);
   }

   public HierarachyElementELO getHierarachyElement(int param1) {
      HierarchyElementDAO dao = new HierarchyElementDAO();
      return dao.getHierarachyElement(param1);
   }

   public AllFeedsForDimensionElementELO getAllFeedsForDimensionElement(int param1) {
      HierarchyElementFeedDAO dao = new HierarchyElementFeedDAO();
      return dao.getAllFeedsForDimensionElement(param1);
   }

   public AugHierarachyElementELO getAugHierarachyElement(int param1) {
      AugHierarchyElementDAO dao = new AugHierarchyElementDAO();
      return dao.getAugHierarachyElement(param1);
   }

   public AllSecurityRangesELO getAllSecurityRanges() {
      SecurityRangeDAO dao = new SecurityRangeDAO();
      return dao.getAllSecurityRanges();
   }

   public AllSecurityRangesForModelELO getAllSecurityRangesForModel(int param1) {
      SecurityRangeDAO dao = new SecurityRangeDAO();
      return dao.getAllSecurityRangesForModel(param1);
   }

   public HashMap getDimRefreshMap(int[] dimIds) {
      DimensionElementDAO dao = new DimensionElementDAO();
      return dao.getDimRefreshMap(dimIds);
   }

   public HashMap getAllDimensionElementCounts() {
      DimensionElementDAO dao = new DimensionElementDAO();
      return dao.getAllDimensionElementCounts();
   }

   public ModelRefImpl queryOwningModel(DimensionPK dimPK) throws Exception {
      return (new DimensionDAO()).queryModelOwner(dimPK);
   }

   public void flush(DimensionPK pk) throws Exception {
//      this.getLocal(pk).flush();
	   dimensionEEJB.flush();
   }

	public AllDimensionsELO getDimensionsForLoggedUser(int userId) {
		DimensionDAO dao = new DimensionDAO();
	    return dao.getDimensionsForLoggedUser(userId);
	}
}
