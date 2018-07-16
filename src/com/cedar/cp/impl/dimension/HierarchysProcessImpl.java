// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyEditorSession;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.HierarchysProcess;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.dimension.calendar.CalendarEditorSession;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionServer;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.dimension.HierarchyEditorSessionImpl;
import com.cedar.cp.impl.dimension.calendar.CalendarEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.OnDemandFilterMutableTreeNode;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class HierarchysProcessImpl extends BusinessProcessImpl implements HierarchysProcess {

	private Log mLog = new Log(this.getClass());

	public HierarchysProcessImpl(CPConnection connection) {
		super(connection);
	}

	public void deleteObject(Object primaryKey) throws ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		HierarchyEditorSessionServer es = new HierarchyEditorSessionServer(this.getConnection());

		try {
			es.delete(primaryKey);
		} catch (ValidationException var5) {
			throw var5;
		} catch (CPException var6) {
			throw new RuntimeException("can\'t delete " + primaryKey, var6);
		}

		if (timer != null) {
			timer.logDebug("deleteObject", primaryKey);
		}

	}

	public HierarchyEditorSession getHierarchyEditorSession(Object key) throws ValidationException {
		HierarchyEditorSessionImpl sess = new HierarchyEditorSessionImpl(this, key);
		this.mActiveSessions.add(sess);
		return sess;
	}

	public EntityList getAllHierarchys() {
		try {
			return this.getConnection().getListHelper().getAllHierarchys();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get AllHierarchys", var2);
		}
	}

	public EntityList getHierarchiesForLoggedUser() {
		try {
			return this.getConnection().getListHelper().getHierarchiesForLoggedUser();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get HierarchiesForLoggedUser", var2);
		}
	}

	public EntityList getSelectedHierarchys() {
		try {
			return this.getConnection().getListHelper().getSelectedHierarchys();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can\'t get SelectedHierarchys", e);
		}
	}

	public EntityList getImportableHierarchies(int param1) {
		try {
			return this.getConnection().getListHelper().getImportableHierarchies(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get ImportableHierarchies", var3);
		}
	}

	public EntityList getHierarcyDetailsFromDimId(int param1) {
		try {
			return this.getConnection().getListHelper().getHierarcyDetailsFromDimId(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get HierarcyDetailsFromDimId", var3);
		}
	}

	public EntityList getHierarcyDetailsIncRootNodeFromDimId(int param1) {
		try {
			return this.getConnection().getListHelper().getHierarcyDetailsIncRootNodeFromDimId(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get HierarcyDetailsIncRootNodeFromDimId", var3);
		}
	}

	public EntityList getCalendarForModel(int param1) {
		try {
			return this.getConnection().getListHelper().getCalendarForModel(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get CalendarForModel", var3);
		}
	}

	public EntityList getCalendarForModelVisId(String param1) {
		try {
			return this.getConnection().getListHelper().getCalendarForModelVisId(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get CalendarForModelVisId", var3);
		}
	}

	public EntityList getCalendarForFinanceCube(int param1) {
		try {
			return this.getConnection().getListHelper().getCalendarForFinanceCube(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get CalendarForFinanceCube", var3);
		}
	}

	public String getProcessName() {
		String ret = "Processing Hierarchy";
		return ret;
	}

	protected int getProcessID() {
		return 41;
	}

	public TreeModel getRuntimeStructure(HierarchyRef hierarchy) throws ValidationException {
		EntityList rootElem = this.getConnection().getListHelper().getImmediateChildren(((HierarchyRefImpl) hierarchy).getHierarchyPK().getHierarchyId(), 0);
		if (rootElem.getNumRows() < 0) {
			throw new ValidationException("Runtime structure not found for hierarchy" + hierarchy);
		} else {
			return new DefaultTreeModel(new OnDemandMutableTreeNode(new StructureElementNodeImpl(this.getConnection(), rootElem.getRowData(0)), "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
		}
	}

	public EntityList queryPathToRoots(List<StructureElementKey> elements) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		HierarchyEditorSessionServer es = new HierarchyEditorSessionServer(this.getConnection());
		EntityList result = null;

		try {
			result = es.queryPathToRoots(elements);
		} catch (CPException var6) {
			throw new RuntimeException("Failed to queryPathToRoots", var6);
		}

		if (timer != null) {
			timer.logDebug("queryPathToRootd");
		}

		return result;
	}

	public CalendarEditorSession getCalendarEditorSession(Object key) {
		CalendarEditorSessionImpl sess = new CalendarEditorSessionImpl(this, key);
		this.mActiveSessions.add(sess);
		return sess;
	}

	public void deleteCalendarObject(Object primaryKey) throws ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		CalendarEditorSessionServer ss = new CalendarEditorSessionServer(this.getConnection());

		try {
			ss.delete(primaryKey);
		} catch (ValidationException var5) {
			throw var5;
		} catch (CPException var6) {
			throw new RuntimeException("can\'t delete " + primaryKey, var6);
		}

		if (timer != null) {
			timer.logDebug("deleteCalendarObject", primaryKey);
		}

	}

	public CalendarInfo getCalendarInfoForModel(Object key) {
		EntityList calDetails = this.getCalendarForModel(this.coerceModelId(key));
		if (calDetails.getNumRows() == 0) {
			return null;
		} else {
			int hierarchyId = ((Integer) calDetails.getValueAt(0, "HierarchyId")).intValue();
			return this.getCalendarInfo(Integer.valueOf(hierarchyId));
		}
	}

	public CalendarInfo getCalendarInfoForFinanceCube(Object key) {
		EntityList calDetails = this.getCalendarForFinanceCube(this.coerceHierarchyId(key));
		if (calDetails.getNumRows() == 0) {
			return null;
		} else {
			int hierarchyId = ((Integer) calDetails.getValueAt(0, "HierarchyId")).intValue();
			return this.getCalendarInfo(Integer.valueOf(hierarchyId));
		}
	}

	public CalendarInfo getCalendarInfo(Object key) {
		int hierarchyId = this.coerceHierarchyId(key);
		EntityList elems = this.getConnection().getListHelper().getAllStructureElements(hierarchyId);
		return CalendarInfoImpl.getCalendarInfo(Integer.valueOf(hierarchyId), elems);
	}

	public Map<DimensionRef, TreeModel> getTreeModels(ModelRef modelRef) {
		HashMap models = new HashMap();
		int modelId = ((ModelRefImpl) modelRef).getModelPK().getModelId();
		boolean currentDimensionId = true;
		EntityList roots = this.getConnection().getListHelper().getAllRootsForModel(modelId);

		for (int i = 0; i < roots.getNumRows(); ++i) {
			DimensionRef dimensionRef = (DimensionRef) roots.getValueAt(i, "Dimension");
			DefaultTreeModel model = (DefaultTreeModel) models.get(dimensionRef);
			DefaultMutableTreeNode root = null;
			if (model == null) {
				root = new DefaultMutableTreeNode(dimensionRef);
				model = new DefaultTreeModel(root);
				models.put(dimensionRef, model);
			} else {
				root = (DefaultMutableTreeNode) model.getRoot();
			}

			StructureElementNodeImpl se = new StructureElementNodeImpl(this.getConnection(), roots.getRowData(i));
			new OnDemandMutableTreeNode(se, "com.cedar.cp.impl.dimension.StructureElementProxyNode");
			root.add(new OnDemandMutableTreeNode(se, "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
		}

		return models;
	}

	public Map<DimensionRef, TreeModel> getFilteredTreeModels(Map<DimensionRef, Map<StructureElementRef, Boolean>> filters) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		HierarchyEditorSessionServer ss = new HierarchyEditorSessionServer(this.getConnection());
		HashMap models = new HashMap();

		try {
			Map e = ss.getFilteredTreeRoots(filters);
			Iterator i$ = e.entrySet().iterator();

			while (i$.hasNext()) {
				Entry entry = (Entry) i$.next();
				DimensionRef dimensionRef = (DimensionRef) entry.getKey();
				EntityList rootsList = (EntityList) entry.getValue();
				DefaultMutableTreeNode root = new DefaultMutableTreeNode(dimensionRef);

				for (int i = 0; i < rootsList.getNumRows(); ++i) {
					StructureElementNodeImpl se = new StructureElementNodeImpl(this.getConnection(), rootsList.getRowData(i));
					OnDemandFilterMutableTreeNode rootElem = new OnDemandFilterMutableTreeNode(se, "com.cedar.cp.impl.dimension.StructureElementFilterProxyNode", filters.get(dimensionRef));
					root.add(rootElem);
				}

				models.put(dimensionRef, new DefaultTreeModel(root));
			}
		} catch (CPException var14) {
			throw new RuntimeException("can\'t query getFilteredTreeModels", var14);
		}

		if (timer != null) {
			timer.logDebug("getFilteredTreeModels");
		}

		return models;
	}

	public EntityList getImmediateChildrenWithFilter(StructureElementRef seRef, Map<StructureElementRef, Boolean> filters) {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		HierarchyEditorSessionServer ss = new HierarchyEditorSessionServer(this.getConnection());
		EntityList result = null;

		try {
			result = ss.getImmediateChildrenWithFilter(seRef, filters);
		} catch (CPException var7) {
			throw new RuntimeException("can\'t query getFilteredTreeModels", var7);
		}

		if (timer != null) {
			timer.logDebug("getFilteredTreeModels");
		}

		return result;
	}

	public List<TreeNode> searchTree(Object dimensionKey, List<String> visIds, TreeModel tm) {
		ArrayList result = new ArrayList();
		int dimensionId = this.coerceDimensionId(dimensionKey);
		Iterator i$ = visIds.iterator();

		while (i$.hasNext()) {
			String visId = (String) i$.next();
			EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(dimensionId, visId);
			result.addAll(SwingUtils.locateNodesInTree(tm, elements));
		}

		return result;
	}

	public List<TreeNode> queryTreeNodes(Object dimensionKey, List<Object> keys, TreeModel tm) {
		ArrayList result = new ArrayList();
		this.coerceDimensionId(dimensionKey);
		Iterator i$ = keys.iterator();

		while (i$.hasNext()) {
			Object seKey = i$.next();
			StructureElementKey sek = (StructureElementKey) seKey;
			ArrayList seKeys = new ArrayList();
			seKeys.add(sek);
			EntityList pathToRootElements = this.queryPathToRoots(seKeys);
			result.addAll(SwingUtils.locateNodesInTree(tm, pathToRootElements));
		}

		return result;
	}

	private int coerceHierarchyId(Object key) {
		if (key instanceof HierarchyRef) {
			key = ((HierarchyRef) key).getPrimaryKey();
		}

		if (key instanceof HierarchyCK) {
			key = ((HierarchyCK) key).getHierarchyPK();
		}

		if (key instanceof HierarchyPK) {
			key = Integer.valueOf(((HierarchyPK) key).getHierarchyId());
		}

		if (key instanceof Integer) {
			return ((Integer) key).intValue();
		} else {
			throw new IllegalArgumentException("Unexpected hierarchy key type:" + key);
		}
	}

	private int coerceDimensionId(Object key) {
		if (key instanceof DimensionRef) {
			key = ((DimensionRef) key).getPrimaryKey();
		}

		if (key instanceof DimensionPK) {
			key = Integer.valueOf(((DimensionPK) key).getDimensionId());
		}

		if (key instanceof Number) {
			return ((Number) key).intValue();
		} else {
			throw new IllegalArgumentException("Unexpected dimension key type:" + key);
		}
	}

	private int coerceModelId(Object key) {
		if (key instanceof ModelRef) {
			key = ((ModelRef) key).getPrimaryKey();
		}

		if (key instanceof FinanceCubeRef) {
			key = ((FinanceCubeRef) key).getPrimaryKey();
		}

		if (key instanceof FinanceCubeCK) {
			key = ((FinanceCubeCK) key).getModelPK();
		}

		if (key instanceof ModelPK) {
			key = Integer.valueOf(((ModelPK) key).getModelId());
		}

		if (key instanceof Integer) {
			return ((Integer) key).intValue();
		} else {
			throw new IllegalStateException("Unexpected model id key class:" + key);
		}
	}
}
