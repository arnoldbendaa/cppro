/**
 * 
 */
package com.cedar.cp.util.flatform.model;

/**
 * @author oracle
 * 
 * Describes a range of the outline
 */
public class OutlineRange {

	public OutlineRange(int start, int end) {
		super();
		this.first = start;
		this.last = end;
	}
	
	public OutlineRange(int start, int end, int currentLenght) {
		super();
		this.first = start;
		this.last = end;
		this.currentLenght = currentLenght;
	}
	
	public OutlineRange(OutlineRange outlineRange, int currentLenght) {
		super();
		this.first = outlineRange.first;
		this.last = outlineRange.last;
		this.currentLenght = currentLenght;
	}

	/**
	 * A number of the starting cell
	 */
	private int first;
	
	/**
	 * A number of the last cell
	 */
	private int last;
	
	/**
	 * Number of entities to display
	 */
	private int currentLenght=0;

	public int getFirst() {
		return first;
	}

	public void setFirst(int start) {
		this.first = start;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int end) {
		this.last = end;
	}


	public int getCurrentLenght() {
		return currentLenght;
	}

	public void setCurrentLenght(int currentLenght) {
		this.currentLenght = currentLenght;
	}
	
	@Override
	public String toString() {
		return "OutlineRange [first=" + first + ", last=" + last + " currentLength="+currentLenght+"]";
	}
}
