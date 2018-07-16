/**
 * 
 */
package com.cedar.cp.util.flatform.model;

import javax.swing.event.ChangeListener;


/**
 * @author  Jacek Kurasiewicz
 * @email   jk@softpro.pl
 * @company Softpro.pl Sp. z o.o.
 *
 * OutlineHeadStatusChangeListener is used to notify listeners that a OutlineStatus
 * has changed.
 */
public abstract class OutlineHeadStatusChangeListener implements ChangeListener {

	private Object[] param;

	public OutlineHeadStatusChangeListener() {
		super();
	}
	
	public OutlineHeadStatusChangeListener(Object... param) {
		super();
		this.param = param;
	}

	public Object getParam(int idx) {
		return param[idx];
	}
}
