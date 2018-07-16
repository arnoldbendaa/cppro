/**
 * 
 */
package com.cedar.cp.util.flatform.model;

import javax.swing.event.ChangeEvent;

/**
 * @author  Jacek Kurasiewicz
 * @email   jk@softpro.pl
 * @company Softpro.pl Sp. z o.o.
 *
 */
public class OutlineLevelModelChangeEvent extends ChangeEvent {
	private static final long serialVersionUID = -7111704957108219831L;
	public enum ChangeType {ADDED, REMOVED};
	
	private ChangeType changeType;
	private OutlineHead head;
	
	public OutlineLevelModelChangeEvent(OutlineHead head, ChangeType changeType) {
		super(head);
		this.changeType=changeType;
		this.head=head;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public OutlineHead getHead() {
		return head;
	}
}
