package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.recalculate.AllRecalculateBatchTasksELO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskCK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskDAO;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEVO;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskFormDAO;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskLocal;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskLocalHome;

import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RecalculateBatchTaskAccessor implements Serializable {

	private RecalculateBatchTaskLocalHome mLocalHome;
	private Hashtable mLocals = new Hashtable();
	private transient InitialContext mInitialContext;
	public static final String GET_IMPORT_TASKS_EVENTS = "<0>";
	public static final String GET_ALL_DEPENDANTS = "<0>";
	RecalculateBatchTaskEEJB recalculateBatchTaskEjb = new RecalculateBatchTaskEEJB();
	public RecalculateBatchTaskAccessor(InitialContext ctx) {
		this.mInitialContext = ctx;
	}

	private RecalculateBatchTaskLocalHome getLocalHome() {
		try {
			if (this.mLocalHome != null) {
				return this.mLocalHome;
			} else {
				this.mLocalHome = (RecalculateBatchTaskLocalHome) this.mInitialContext.lookup("java:comp/env/ejb/RecalculateBatchTaskLocalHome");
				return this.mLocalHome;
			}
		} catch (NamingException var2) {
			throw new RuntimeException("error looking up RecalculateBatchTaskLocalHome", var2);
		}
	}

	private RecalculateBatchTaskLocal getLocal(RecalculateBatchTaskPK pk) throws Exception {
		RecalculateBatchTaskLocal local = (RecalculateBatchTaskLocal) this.mLocals.get(pk);
		if (local == null) {
			local = this.getLocalHome().findByPrimaryKey(pk);
			//this.mLocals.put(pk, local);
		}

		return local;
	}

	public RecalculateBatchTaskEVO create(RecalculateBatchTaskEVO evo) throws Exception {
		RecalculateBatchTaskLocal local = this.getLocalHome().create(evo);
		RecalculateBatchTaskEVO newevo = local.getDetails("<UseLoadedEVOs>");
		RecalculateBatchTaskPK pk = newevo.getPK();
		this.mLocals.put(pk, local);
		return newevo;
	}

	public void remove(RecalculateBatchTaskPK pk) throws Exception {
		this.getLocal(pk).remove();
	}

	public RecalculateBatchTaskEVO getDetails(Object paramKey, String dependants) throws Exception {
		Object key = paramKey;
		if (paramKey instanceof EntityRef) {
			key = ((EntityRef) paramKey).getPrimaryKey();
		}

		if (key instanceof RecalculateBatchTaskCK) {
			RecalculateBatchTaskPK pk = ((RecalculateBatchTaskCK) key).getRecalculateBatchTaskPK();
			return this.getLocal(pk).getDetails((RecalculateBatchTaskCK) key, dependants);
		} else {
//			return key instanceof RecalculateBatchTaskPK ? this.getLocal((RecalculateBatchTaskPK) key).getDetails(dependants) : null;
			RecalculateBatchTaskPK pk = (RecalculateBatchTaskPK)key;
			RecalculateBatchTaskCK ck = new RecalculateBatchTaskCK(pk);
			if(key instanceof RecalculateBatchTaskPK)
				return recalculateBatchTaskEjb.getDetails(ck,dependants);
			else 
				return null;
		}
	}

	public CompositeKey getCKForDependantPK(PrimaryKey key) {
		return null;
	}

	public void setDetails(RecalculateBatchTaskEVO evo) throws Exception {
		RecalculateBatchTaskPK pk = evo.getPK();
		this.getLocal(pk).setDetails(evo);
	}

	public RecalculateBatchTaskEVO setAndGetDetails(RecalculateBatchTaskEVO evo, String dependants) throws Exception {
		return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
	}

	public AllRecalculateBatchTasksELO getAllRecalculateBatchTasks() {
		RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
		return dao.getAllRecalculateBatchTasks();
	}

	public AllRecalculateBatchTasksELO getRecalculateBatchTask(int id) {
		RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
		return dao.getRecalculateBatchTask(id);
	}
}
