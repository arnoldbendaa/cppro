/**
 * 
 */
package com.cedar.cp.util.flatform.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author oracle
 * 
 * Single level
 */
public class OutlineLevel {

	private List<OutlineHead> heads;

	public List<OutlineHead> getHeads() {
		if(heads==null)
			heads=new ArrayList<OutlineHead>();
		return heads;
	}
	
	/**
	 * Returns number of heads from this OutlineLevel
	 * @return
	 */
	public int size() {
		return getHeads().size();
	}

	/**
	 * Adds a head to this OutlineLevel
	 * @param head
	 * @return
	 */
	public boolean addHead(OutlineHead head) {
		boolean result=getHeads().add(head);
		Collections.sort(getHeads());
		return result;
	}
	
	/**
	 * Removes a head from this OutlineLevel
	 * @param head
	 * @return
	 */
	public boolean removeHead(OutlineHead head){
		return getHeads().remove(head);
	}

	@Override
	public String toString() {
		return "OutlineLevel [heads=" + heads + "]";
	}
	
}
