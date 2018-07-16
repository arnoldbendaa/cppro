/**
 * 
 */
package com.cedar.cp.util.flatform.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

import com.cedar.cp.util.flatform.model.OutlineHead.OutlineState;
import com.cedar.cp.util.flatform.model.OutlineLevelModelChangeEvent.ChangeType;

/**
 * 
 * @author  Jacek Kurasiewicz
 * @email   jk@softpro.pl
 * @company Softpro.pl Sp. z o.o.
 *
 * Container for OutlineLevel objects
 */
public class OutlineLevelModel {

	/**
	 * Represents outline columns where every index in the array
	 * represents specified level.
	 */
	private List<OutlineLevel> outlineLevels=new ArrayList<OutlineLevel>();
	
	/** UUDI to head mapping **/
	private Map<String, OutlineHead> uuidHeadMapping=new HashMap<String, OutlineHead>();
	
	/** UUID to level mapping **/
	private Map<String, Integer> uuidLevelMapping=new HashMap<String, Integer>();
	
	/** Event listeners **/
	private EventListenerList mListenerList = new EventListenerList();
	
	/**
	 * Default constructor
	 */
	public OutlineLevelModel() {
		super();
	}

	/**
	 * Rebuilds this model
	 * 1. Recalculates getRange().getCurrentLenght()
	 * 2. Sorts child collection
	 */
	public void rebuild(){
		// Rebuild OutlineRange.currentLength
		List<OutlineHead> firstLevel=getFirstLevel();
		if(firstLevel!=null){
			for(OutlineHead head : getFirstLevel()){
				calculateCurrentRange(head, 0);
			}
		}
		// Sort children of heads moved to calculateCurrentRange()
		// Collections.sort(head.getChildren());
	}
	
	/**
	 * Recursively calculates efective number of entities
	 * Also sorts children collections
	 */
	private int calculateCurrentRange(OutlineHead head, int cut){
		// Sort children - placed here for performance reason
		Collections.sort(head.getChildren());
		
		// Process the calculation
		if(head.getOutlineState() == OutlineState.COLLAPSED){
			cut=cut+head.getOffset();
			//System.out.println("head: "+head.toString());
			return cut;
		} else if(!head.getChildren().isEmpty()){
			for(OutlineHead child : head.getChildren()){
				cut=calculateCurrentRange(child, cut);
				//System.out.println("child: "+child.toString());
			}
			int length=head.getOffset()-cut;
			head.getRange().setCurrentLenght(length);
			return cut;
		} else {
			head.getRange().setCurrentLenght(head.getOffset());
			return cut;
		}
	}
	
	/**
	 * Adds head to the specified parent
	 * @param head - the head to be added
	 * @param parent - the parent of the head, can be null if has no parent
	 * @return
	 */
	public boolean addHead(OutlineHead head, OutlineHead parent){
		// Generate UUID
		String uuid=UUID.randomUUID().toString();
		int outlineLevel=parent == null ? 0 : parent.getOulineLevel()+1;
		
		// Update head
		head.setCtx(this);
		head.setUUID(uuid);
		if(parent!=null){
			parent.addChild(head);
			if(head.getRange().getLast() > parent.getRange().getLast())
				head.getRange().setLast(parent.getRange().getLast());
		}
		
		// Register listeners dedicated for OutlineHead Status ChangeEvent
		head.addChangeListeners(
				mListenerList.getListeners(OutlineHeadStatusChangeListener.class));
		
		// Add all required mappings
		uuidHeadMapping.put(uuid, head);
		uuidLevelMapping.put(uuid, outlineLevel);
		
		// Make sure level is initialized
		this.initLevel(outlineLevel);
		
		// Add the head to its level
		outlineLevels.get(outlineLevel).addHead(head);
		
		rebuild();
		this.fireOutlineLevelModelStateChanged(head, ChangeType.ADDED);
		return true;
	}
	
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created using the <code>event</code> parameter.
     * 
     * @param event  the <code>ItemEvent</code> object
     * @see EventListenerList
     */
	protected void fireOutlineLevelModelStateChanged(OutlineHead head, ChangeType changeType) {
		OutlineLevelModelStatusChangeListener[] listeners =
				mListenerList.getListeners(OutlineLevelModelStatusChangeListener.class);
		
		ChangeEvent e=null;
		for(OutlineLevelModelStatusChangeListener listener : listeners){
			// Lazy initialization of event
			if(e==null)
				e=new OutlineLevelModelChangeEvent(head, changeType);
			listener.stateChanged(e);
		}
	}
	
    /**
     * Adds a <code>OutlineLevelModelStatusChangeListener</code> to the button.
     * @param listener the listener to be added
     */
    public void addOutlineLevelModelStatusChangeListener(OutlineLevelModelStatusChangeListener listener) {
        mListenerList.add(OutlineLevelModelStatusChangeListener.class, listener);
    }
	
    /**
     * Adds a <code>OutlineHeadStatusChangeListener</code> to the OutlineHead.
     * @param l the listener to be added
     */
    public void addOutlineHeadStatusChangeListener(OutlineHeadStatusChangeListener l) {
        mListenerList.add(OutlineHeadStatusChangeListener.class, l);
    }
    
	/**
	 * Remove head from its place
	 * @param head
	 * @return
	 */
	public boolean removeHead(OutlineHead head){
		OutlineHead parent=head.getParent();
		int headLevel=head.getOulineLevel();
		
		// This modifies outlineLevel
		moveHeadsUp(head.getChildren());
		
		// This modifies uuidLevelMapping and uuidDescendantsMapping
		for(Iterator<OutlineHead> it = head.getChildren().iterator(); it.hasNext();){
			OutlineHead child=it.next();
			if(parent!=null){
				parent.addChild(child);
				uuidLevelMapping.put(child.getUUID(), parent.getOulineLevel()+1);
			} else {
				child.setParent(null);
				uuidLevelMapping.put(child.getUUID(), 0);
			}
			moveMappingHeadsUp(child.getChildren());
			it.remove();
		}
		
		outlineLevels.get(headLevel).removeHead(head);
		uuidHeadMapping.remove(head.getUUID());
		uuidLevelMapping.remove(head.getUUID());
		
		rebuild();
		this.fireOutlineLevelModelStateChanged(head, ChangeType.REMOVED);
		return true;
	}

	/**
	 * Recursively iterates over children of a head shifts its level
	 * one step up.
	 * @param heads collections of children
	 */
	private void moveMappingHeadsUp(List<OutlineHead> heads) {
		
		for (Iterator<OutlineHead> it = heads.iterator(); it.hasNext();) {
			OutlineHead child = it.next();
			uuidLevelMapping.put(child.getUUID(), child.getParent().getOulineLevel() + 1);
			moveMappingHeadsUp(child.getChildren());
		}
	}
	
	/**
	 * Retrieves sorted first level of outline
	 * @return first level heads
	 */
	public List<OutlineHead> getFirstLevel(){
		if(outlineLevels.size()==0)
			return null;
		List<OutlineHead> heads=outlineLevels.get(0).getHeads();
		return heads;
	}
	
	/**
	 * Shifts subtrees of given tree one level up
	 * @param heads
	 * @return true if success
	 */
	private boolean moveHeadsUp(List<OutlineHead> heads){
		for(OutlineHead head : heads){
			int level=head.getOulineLevel();
			outlineLevels.get(level).removeHead(head);
			initLevel(head.getOulineLevel()-1);
			outlineLevels.get(level-1).addHead(head);
			
			// Recursively continue work
			moveHeadsUp(head.getChildren());
			
			// Clean OutlineLevel if necessary
			OutlineLevel ol=outlineLevels.get(level);
			if(ol.getHeads().isEmpty()){
				outlineLevels.remove(ol);
			}
		}
		return true;
	}
	
	/**
	 * Retrieves head by its UUID
	 * @param UUID
	 * @return head or null if not found
	 */
	protected OutlineHead getHeadByUUID(String UUID){
		if(uuidHeadMapping.containsKey(UUID))
			return uuidHeadMapping.get(UUID);
		return null;
	}
	
	/**
	 * Retrieves level of head by its UUID
	 * @param UUID
	 * @return level of head or null if not found
	 */
	protected Integer getHeadOutlineLevelByUUID(String UUID){
		if(uuidLevelMapping.containsKey(UUID))
			return uuidLevelMapping.get(UUID);
		return null;
	}
	
	/**
	 * Initializes specified level if does not exist
	 * @param outlineLevel - the level
	 */
	protected void initLevel(int outlineLevel) {
		int size=outlineLevels.size();
		
		for(int i=size-1; i < outlineLevel; i++){
			outlineLevels.add(new OutlineLevel());
		}
	}

	/**
	 * Collection of outline levels
	 * @return collection of outline levels
	 */
	public List<OutlineLevel> getOutlineLevels() {
		return outlineLevels;
	}

	/**
	 * Retrieves sorted outline heads from given level.
	 * @param index - 0 based index of the level
	 * @return collection of head objects if level exists otherwise null
	 */
	public List<OutlineHead> getHeadsFromOutlineLevel(int index) {
		if(outlineLevels.size() <= index)
			return null;
		List<OutlineHead> heads=outlineLevels.get(index).getHeads();
		return heads;
	}
	
}