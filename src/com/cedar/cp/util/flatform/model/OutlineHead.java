/**
 * 
 */
package com.cedar.cp.util.flatform.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * 
 * @author  Jacek Kurasiewicz
 * @email   jk@softpro.pl
 * @company Softpro.pl Sp. z o.o.
 *
 * Describes the head of the outline. It will be rendered as a square
 * with +/-.
 */
public class OutlineHead implements Comparable<OutlineHead>{
	public enum OutlineState {
		EXPANDED, COLLAPSED
	};

	private String UUID;
	
	/**
	 * The range of the outline
	 */
	private OutlineRange range;

	/**
	 * The state of the outline
	 */
	private OutlineState outlineState;

	/**
	 * The parent of the outline
	 */
	private OutlineHead parent;

	/**
	 * The children of the outline. There are first level children only. To get
	 * information about next levels one need to access children of the children.
	 */
	private List<OutlineHead> children=new ArrayList<OutlineHead>();
	
	/**
	 * The outline level model context
	 */
	private OutlineLevelModel ctx;
	
	/** Event listeners **/
	private EventListenerList mListenerList = new EventListenerList();
	
	
	/*------------ Constructors ------------*/
	
	/**
	 * Default constructor
	 * @param range
	 * @param outlineState
	 */
	public OutlineHead(OutlineRange range, OutlineState outlineState) {
		super();
		this.range = range;
		this.outlineState = outlineState;
	}
	
	/**
	 * Default constructor
	 * @param rangeX
	 * @param rangeY
	 * @param outlineState
	 */
	public OutlineHead(int rangeX, int rangeY, OutlineState outlineState){
		this(new OutlineRange(rangeX, rangeY), outlineState);
	}

	
	/*------------ Model manipulation methods ------------*/
	
	/**
	 * Adds a child to parent and parent to child.
	 * @param child
	 * @return
	 */
	public boolean addChild(OutlineHead child) {
		child.setParent(this);
		boolean result=children.add(child);
		return result;
	}

	/**
	 * Removes references between parent and child.
	 * @param child
	 * @return
	 */
	public boolean removeChild(OutlineHead child) {
		child.setParent(null);
		return children.remove(child);
	}

	/**
	 * Outline level
	 * @return outline level
	 */
	public Integer getOulineLevel(){
		return ctx.getHeadOutlineLevelByUUID(UUID);
	}
	
	/**
	 * Switches outline state
	 * @return new outline state value COLLAPSED or EXPANDED
	 */
	public OutlineState switchOutlineState(){
		if(this.outlineState==OutlineState.COLLAPSED)
			this.outlineState=OutlineState.EXPANDED;
		else
			this.outlineState=OutlineState.COLLAPSED;
		
		this.fireOutlineHeadStateChanged();
		return this.outlineState;
	}
	
	/*------------ Listeners support --------------*/
	
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created using the <code>event</code> parameter.
     * 
     * @param event  the <code>ItemEvent</code> object
     * @see EventListenerList
     */
	protected void fireOutlineHeadStateChanged() {
		ctx.rebuild();
		
		OutlineHeadStatusChangeListener[] listeners=mListenerList.getListeners(OutlineHeadStatusChangeListener.class);
		
		ChangeEvent e=null;
		for(OutlineHeadStatusChangeListener listener : listeners){
			// Lazy initialization of event
			if(e==null)
				e=new ChangeEvent(this);
			listener.stateChanged(e);
		}
	}
	
    /**
     * Adds a <code>OutlineHeadStatusChangeListener</code> to the button.
     * @param listener the listener to be added
     */
    public void addChangeListener(OutlineHeadStatusChangeListener listener) {
        mListenerList.add(OutlineHeadStatusChangeListener.class, listener);
    }
    
    /**
     * Adds a <code>OutlineHeadStatusChangeListener</code> to the button.
     * @param listeners the listener to be added
     */
    public void addChangeListeners(OutlineHeadStatusChangeListener[] listeners) {
		for(OutlineHeadStatusChangeListener listener : listeners){
			mListenerList.add(OutlineHeadStatusChangeListener.class, listener);
		}
    }
	
	/*------------ Getters and Setters ------------*/

	public OutlineRange getRange() {
		return range;
	}

	protected void setRange(OutlineRange range) {
		this.range = range;
	}

	public OutlineState getOutlineState() {
		return outlineState;
	}

	public void setOutlineState(OutlineState outlineState) {
		OutlineState previous=this.outlineState;
		this.outlineState = outlineState;
		if(previous!=outlineState){
			fireOutlineHeadStateChanged();
		}
	}

	public OutlineHead getParent() {
		return parent;
	}

	protected void setParent(OutlineHead parent) {
		this.parent = parent;
	}

	/**
	 * Retrieves sorted collection of children
	 * @return
	 */
	public List<OutlineHead> getChildren() {
		return children;
	}

	protected void setChildren(List<OutlineHead> children) {
		this.children = children;
	}

	public String getUUID() {
		return UUID;
	}

	protected void setUUID(String uUID) {
		UUID = uUID;
	}

	@Override
	public int compareTo(OutlineHead o) {
		return new Integer(this.range.getFirst()).compareTo(new Integer(o.getRange().getFirst())) ;
	}

	protected void setCtx(OutlineLevelModel ctx) {
		this.ctx = ctx;
	}

	@Override
	public String toString() {
		return "OutlineHead [range=" + range + ", outlineState=" + outlineState
				//+ ", UUID=" + UUID 
				+ "]";
	}
	
	/**
	 * Calculates number of entities occupied by this head
	 * @return number of entities occupied by this head
	 */
	public int getOffset(){
		return this.getRange().getLast()-this.getRange().getFirst()+1;
	}

}
