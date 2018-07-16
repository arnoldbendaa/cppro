package com.cedar.cp.util.sparse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

@SuppressWarnings({"rawtypes", "unchecked", "hiding"})
public class LinkedListSparse2DArray<T extends Object> implements
		Sparse2DArray<T>, Serializable {
	private static final long serialVersionUID = -6801118758527340549L;

	protected EventListenerList listenerList = new EventListenerList();
	private int mModificationCount;
	private ColumnLink<T> mColumns;
	private RowLink<T> mRows;
	// Set with hidden rows
	private Map<Integer, RowLink> hiddenRows = new HashMap<Integer,RowLink>();

	public int getModificationCount() {
		return this.mModificationCount;
	}

	public boolean isEmpty() {
		return this.mRows == null;
	}

	public T get(int row, int column) {
		CellLink<T> cell = this.findCell(row, column, false);
		return cell != null ? cell.getData() : null;
	}

	public void put(int row, int column, T element) {
		CellLink<T> cell = this.findCell(row, column, true);
		cell.setData(element);
		++this.mModificationCount;
		this.fireSparse2DArrayCellUpdated(row, column);
	}

	public T remove(int row, int column) {
		CellLink<T> cell = this.findCell(row, column, false);
		if (cell != null) {
			++this.mModificationCount;
			this.removeCellLinkFromRow(cell, row);
			this.removeCellLinkFromColumn(cell, column);
		}

		return null;
	}

	public int getRowCount() {
		RowLink link;
		for (link = this.mRows; link != null && link.getRowLink() != null; link = link
				.getRowLink()) {
			;
		}

		return link != null ? 1 + link.getRow() : 0;
	}

	public int getColumnCount() {
		ColumnLink link;
		for (link = this.mColumns; link != null && link.getColumnLink() != null; link = link
				.getColumnLink()) {
			;
		}

		return link != null ? 1 + link.getColumn() : 0;
	}

	public void insertColumns(int column, int numColumns) {
		for (ColumnLink columnLink = this.mColumns; columnLink != null; columnLink = columnLink
				.getColumnLink()) {
			if (columnLink.getColumn() >= column) {
				ColumnLink.accessMethod000(columnLink, columnLink.getColumn()
						+ numColumns);

				for (CellLink cellLink = columnLink.getRowLink(); cellLink != null; cellLink = CellLink
						.accessMethod200(cellLink)) {
					CellLink.accessMethod100(cellLink, cellLink.getColumn()
							+ numColumns);
				}
			}
		}

	}

	public void removeColumns(int column, int numColumns) {
		for (ColumnLink columnLink = this.mColumns; columnLink != null; columnLink = columnLink
				.getColumnLink()) {
			CellLink cellLink;
			if (column <= columnLink.getColumn()
					&& columnLink.getColumn() < column + numColumns) {
				for (cellLink = columnLink.getRowLink(); cellLink != null; cellLink = CellLink
						.accessMethod200(cellLink)) {
					this.removeCellLinkFromColumn(cellLink,
							columnLink.getColumn());
					this.removeCellLinkFromRow(cellLink, cellLink.getRow());
				}
			} else if (columnLink.getColumn() >= column + numColumns) {
				ColumnLink.accessMethod000(columnLink, columnLink.getColumn()
						- numColumns);

				for (cellLink = columnLink.getRowLink(); cellLink != null; cellLink = CellLink
						.accessMethod200(cellLink)) {
					CellLink.accessMethod100(cellLink, cellLink.getColumn()
							- numColumns);
				}
			}
		}

	}

	public void insertRows(int row, int numRows) {
		for (RowLink rowLink = this.mRows; rowLink != null; rowLink = rowLink
				.getRowLink()) {
			if (rowLink.getRow() >= row) {
				RowLink.accessMethodSetRow(rowLink, rowLink.getRow() + numRows);

				for (CellLink cellLink = rowLink.getColumnLink(); cellLink != null; cellLink = CellLink
						.accessPropertyMColumnLink(cellLink)) {
					CellLink.accessMethodSetRow(cellLink, cellLink.getRow()
							+ numRows);
				}
			}
		}

	}

	public void removeRows(int row, int numRows) {
		for (RowLink rowLink = this.mRows; rowLink != null; rowLink = rowLink
				.getRowLink()) {
			CellLink cellLink;
			if (row <= rowLink.getRow() && rowLink.getRow() < row + numRows) {
				for (cellLink = rowLink.getColumnLink(); cellLink != null; cellLink = CellLink
						.accessPropertyMColumnLink(cellLink)) {
					this.removeCellLinkFromColumn(cellLink,
							cellLink.getColumn());
					this.removeCellLinkFromRow(cellLink, cellLink.getRow());
				}
			} else if (rowLink.getRow() >= row + numRows) {
				RowLink.accessMethodSetRow(rowLink, rowLink.getRow() - numRows);

				for (cellLink = rowLink.getColumnLink(); cellLink != null; cellLink = CellLink
						.accessPropertyMColumnLink(cellLink)) {
					CellLink.accessMethodSetRow(cellLink, cellLink.getRow()
							- numRows);
				}
			}
		}

	}

	public Iterator<CellLink<T>> iterator() {
		return new Itr();
	}

	public Iterator<CellLink<T>> rowIterator(int row) {
		return new RowItr(row);
	}

	public Iterator<CellLink<T>> columnIterator(int column) {
		return new ColumnItr(column);
	}

	public Iterator<CellLink<T>> rangeIterator(int startRow, int startColumn,
			int endRow, int endColumn) {
		return new RangeItr(startRow, startColumn, endRow, endColumn);
	}

	/**
	 * Removes CellLink from row
	 * @param cell
	 * @param row
	 */
	private void removeCellLinkFromRow(CellLink<T> cell, int row) {
		RowLink r = this.findRow(row, false);
		if (r == null) {
			throw new IllegalStateException("Unable to find expected row ");
		} else {
			if (r.getColumnLink() == cell) {
				r.setColumnLink(cell.getColumnLink());
				if (r.getColumnLink() == null) {
					this.removeRowLink(r);
				}
			} else {
				CellLink previousLink;
				for (previousLink = r.getColumnLink(); previousLink != null
						&& previousLink.getColumnLink() != cell; previousLink = previousLink
						.getColumnLink()) {
					;
				}

				if (previousLink == null) {
					throw new IllegalStateException(
							"Unable to find expected previous CellLink ");
				}

				previousLink.setColumnLink(cell.getColumnLink());
			}

		}
	}

	/**
	 * Removes CellLink from column
	 * @param cell
	 * @param column
	 */
	private void removeCellLinkFromColumn(CellLink<T> cell, int column) {
		ColumnLink c = this.findColumn(column, false);
		if (c == null) {
			throw new IllegalStateException("Unable to find expected column ");
		} else {
			if (c.getRowLink() == cell) {
				c.setRowLink(cell.getRowLink());
				if (c.getRowLink() == null) {
					this.removeColumnLink(c);
				}
			} else {
				CellLink previousLink;
				for (previousLink = c.getRowLink(); previousLink != null
						&& previousLink.getRowLink() != cell; previousLink = previousLink
						.getRowLink()) {
					;
				}

				if (previousLink == null) {
					throw new IllegalStateException(
							"Unable to find expected previous CellLink ");
				}

				previousLink.setRowLink(cell.getRowLink());
			}

		}
	}

	/**
	 * Collapses given range of rows
	 * @param firstRowIdx
	 * @param numRows
	 */
	public void collapseRows(int firstRowIdx, int numRows) {
		System.out.println("collapseRows() - " + this.toString());
		RowLink<T> parentRow = firstRowIdx>0 ? findRow(firstRowIdx-1, true) : null;
		RowLink<T> lastRow=findRow(firstRowIdx+numRows-1, true);
		
		for(int i=firstRowIdx; i<=numRows; i++){
			RowLink<T> r=findRow(i, true);
			hiddenRows.put(r.getRowOrginal(), r);
			System.out.println("row: "+r.mRow+", "+r.getColumnLink());
		}
		
		// Attach next node of last RowLink to parent of first one
		if(parentRow!=null)
			parentRow.setRowLink(lastRow.getRowLink());
		else {
			// TODO root?
		}
			
		
		for (RowLink rowLink = lastRow.getRowLink(); rowLink != null; rowLink = rowLink.getRowLink()) {
			rowLink.setRow(rowLink.getRow() - numRows);
		}
		
		System.out.println();
	}
	
	/**
	 * Expands given range of rows
	 * @param row
	 * @param numRows
	 */
	@SuppressWarnings("unused")
	public void expandRows(int firstRowIdx, int numRows){
		System.out.println("expandRows() - " + this.toString());
		RowLink<T> parentRow = firstRowIdx>0 ? findRow(firstRowIdx-1, true) : null;
		RowLink<T> nextAfterLastRow = parentRow.getRowLink(); 
		RowLink<T> firstRow=hiddenRows.get(firstRowIdx);
		
		// Attach next node of last RowLink to parent of first one
		if(parentRow!=null)
			parentRow.setRowLink(firstRow.getRowLink());
		else {
			// TODO root?
		}
		
		// Adding row to the last row
		hiddenRows.get(numRows).setRowLink(nextAfterLastRow);
		
		for(int i=firstRowIdx+1; i <= numRows; i++ ){
			RowLink<T> child=hiddenRows.get(i);
			RowLink<T> parent=hiddenRows.get(i-1);
			parent.setRowLink(child);
			hiddenRows.remove(i-1);
		}
		hiddenRows.remove(numRows);
		
		// Recalculation row number
		for (RowLink rowLink = nextAfterLastRow; rowLink != null; rowLink = rowLink.getRowLink()) {
			rowLink.setRow(rowLink.getRow() + numRows);
		}
	}
	
	public void collapseRows1(int row, int numRows) {
		System.out.println("collapseRows() - " + this.toString());
		
		for (RowLink rowLink = this.mRows; rowLink != null; rowLink = rowLink.getRowLink()) {
			CellLink cellLink;
			if (row <= rowLink.getRow() && rowLink.getRow() < row + numRows) {
				for (cellLink = rowLink.getColumnLink(); cellLink != null; cellLink = CellLink.accessPropertyMColumnLink(cellLink)) {
					this.removeCellLinkFromColumn(cellLink, cellLink.getColumn());
					this.removeCellLinkFromRow(cellLink, cellLink.getRow());
				}
			} else if (rowLink.getRow() >= row + numRows) {
				RowLink.accessMethodSetRow(rowLink, rowLink.getRow() - numRows);

				for (cellLink = rowLink.getColumnLink(); cellLink != null; cellLink = CellLink.accessPropertyMColumnLink(cellLink)) {
					CellLink.accessMethodSetRow(cellLink, cellLink.getRow() - numRows);
				}
			}
		}

	}
	
	private void removeRowLink(RowLink<T> rowLink) {
		RowLink previousLink;
		for (previousLink = this.mRows; previousLink != null
				&& previousLink.getRowLink() != rowLink; previousLink = previousLink
				.getRowLink()) {
			;
		}

		if (previousLink == null) {
			this.mRows = rowLink.getRowLink();
		} else {
			previousLink.setRowLink(rowLink.getRowLink());
		}

	}

	private void removeColumnLink(ColumnLink<T> columnLink) {
		ColumnLink previousLink;
		for (previousLink = this.mColumns; previousLink != null
				&& previousLink.getColumnLink() != columnLink; previousLink = previousLink
				.getColumnLink()) {
			;
		}

		if (previousLink == null) {
			this.mColumns = columnLink.getColumnLink();
		} else {
			previousLink.setColumnLink(columnLink.getColumnLink());
		}

	}

	protected CellLink<T> findCell(int row, int column, boolean create) {
		CellLink result = null;
		RowLink rowLink = this.findRow(row, create);
		if (rowLink != null) {
			result = RowLink.accessMethod600(rowLink, this, column, create);
		}

		return result;
	}

	private RowLink<T> findRow(int row, boolean create) {
		return this.findRow(row, create, -1);
	}

	private RowLink<T> findRow(int row, boolean create, int maxRow) {
		RowLink link = this.mRows;

		RowLink previousLink;
		for (previousLink = null; link != null; link = link.getRowLink()) {
			int testRow = link.getRow();
			if (testRow == row) {
				return link;
			}

			if (testRow > row) {
				if (testRow <= maxRow) {
					return link;
				}
				break;
			}

			previousLink = link;
		}

		return create ? this.createNewRowLink(previousLink, row) : null;
	}

	private RowLink<T> createNewRowLink(RowLink<T> after, int row) {
		RowLink newLink = new RowLink(row);
		if (after == null) {
			newLink.setRowLink(this.mRows);
			this.mRows = newLink;
		} else {
			newLink.setRowLink(after.getRowLink());
			after.setRowLink(newLink);
		}

		return newLink;
	}

	private void patchCellLinkIntoColumnLinks(CellLink<T> newLink, int row,
			int column) {
		ColumnLink columnLink = this.findColumn(column, true);
		ColumnLink.accessMethod700(columnLink, newLink, row);
	}

	private ColumnLink<T> findColumn(int column, boolean create) {
		ColumnLink link = this.mColumns;

		ColumnLink previousLink;
		for (previousLink = null; link != null; link = link.getColumnLink()) {
			int testColumn = link.getColumn();
			if (testColumn == column) {
				return link;
			}

			if (testColumn > column) {
				break;
			}

			previousLink = link;
		}

		return create ? this.createNewColumnLink(previousLink, column) : null;
	}

	private ColumnLink<T> createNewColumnLink(ColumnLink<T> after, int column) {
		ColumnLink newLink = new ColumnLink(column);
		if (after == null) {
			newLink.setColumnLink(this.mColumns);
			this.mColumns = newLink;
		} else {
			newLink.setColumnLink(after.getColumnLink());
			after.setColumnLink(newLink);
		}

		return newLink;
	}

	public void addSparse2DArrayListener(Sparse2DArrayListener l) {
		this.listenerList.add(Sparse2DArrayListener.class, l);
	}

	public void removeSparse2DArrayListener(Sparse2DArrayListener l) {
		this.listenerList.remove(Sparse2DArrayListener.class, l);
	}

	public Sparse2DArrayListener[] getSparse2DArrayListeners() {
		return (Sparse2DArrayListener[]) this.listenerList
				.getListeners(Sparse2DArrayListener.class);
	}

	public void fireSparse2DArrayDataChanged() {
		this.fireSparse2DArrayChanged(new Sparse2DArrayEvent(this));
	}

	public void fireSparse2DArrayRowsInserted(int firstRow, int lastRow) {
		this.fireSparse2DArrayChanged(new Sparse2DArrayEvent(this, firstRow,
				lastRow, -1, 1));
	}

	public void fireSparse2DArrayRowsUpdated(int firstRow, int lastRow) {
		this.fireSparse2DArrayChanged(new Sparse2DArrayEvent(this, firstRow,
				lastRow, -1, 0));
	}

	public void fireSparse2DArrayRowsDeleted(int firstRow, int lastRow) {
		this.fireSparse2DArrayChanged(new Sparse2DArrayEvent(this, firstRow,
				lastRow, -1, -1));
	}

	public void fireSparse2DArrayCellUpdated(int row, int column) {
		this.fireSparse2DArrayChanged(new Sparse2DArrayEvent(this, row, row,
				column));
	}

	public void fireSparse2DArrayChanged(Sparse2DArrayEvent e) {
		Object[] listeners = this.listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == Sparse2DArrayListener.class) {
				((Sparse2DArrayListener) listeners[i + 1])
						.sparse2DArrayChanged(e);
			}
		}

	}

	public <T extends Object & EventListener> T[] getListeners(
			Class<T> listenerType) {
		return this.listenerList.getListeners(listenerType);
	}

	// $FF: synthetic method
	static int accessMethod800(LinkedListSparse2DArray x0) {
		return x0.mModificationCount;
	}

	// $FF: synthetic method
	static RowLink accessMethod900(LinkedListSparse2DArray x0) {
		return x0.mRows;
	}

	// $FF: synthetic method
	static RowLink accessMethod1000(LinkedListSparse2DArray x0, int x1,
			boolean x2) {
		return x0.findRow(x1, x2);
	}

	// $FF: synthetic method
	static ColumnLink accessMethod1100(LinkedListSparse2DArray x0, int x1,
			boolean x2) {
		return x0.findColumn(x1, x2);
	}

	// $FF: synthetic method
	static RowLink accessMethod1200(LinkedListSparse2DArray x0, int x1,
			boolean x2, int x3) {
		return x0.findRow(x1, x2, x3);
	}

	// $FF: synthetic method
	static void accessMethod1400(LinkedListSparse2DArray x0, CellLink x1,
			int x2, int x3) {
		x0.patchCellLinkIntoColumnLinks(x1, x2, x3);
	}

	public static class CellLink<T> implements Serializable {
		private static final long serialVersionUID = -6771358610948303782L;
		private int mRow;
		private int mColumn;
		private T mData;
		private CellLink<T> mRowLink;
		private CellLink<T> mColumnLink;

		public CellLink(int row, int column) {
			this.mRow = row;
			this.mColumn = column;
		}

		public int getRow() {
			return this.mRow;
		}

		private void setRow(int row) {
			this.mRow = row;
		}

		public int getColumn() {
			return this.mColumn;
		}

		private void setColumn(int column) {
			this.mColumn = column;
		}

		public T getData() {
			return this.mData;
		}

		public void setData(T data) {
			this.mData = data;
		}

		public CellLink<T> getRowLink() {
			return this.mRowLink;
		}

		public void setRowLink(CellLink<T> rowLink) {
			this.mRowLink = rowLink;
		}

		public CellLink<T> getColumnLink() {
			return this.mColumnLink;
		}

		public void setColumnLink(CellLink<T> columnLink) {
			this.mColumnLink = columnLink;
		}

		// $FF: synthetic method
		static void accessMethod100(CellLink x0, int x1) {
			x0.setColumn(x1);
		}

		// $FF: synthetic method
		static CellLink accessMethod200(CellLink x0) {
			return x0.mRowLink;
		}

		// $FF: synthetic method
		static void accessMethodSetRow(CellLink x0, int x1) {
			x0.setRow(x1);
		}

		// $FF: synthetic method
		static CellLink accessPropertyMColumnLink(CellLink x0) {
			return x0.mColumnLink;
		}

		@Override
		public String toString() {
			return "CellLink [mRow=" + mRow + ", mColumn=" + mColumn
					+ ", mData=" + mData + "]";
		}

	}

	public static class ColumnLink<T> implements Serializable {
		private static final long serialVersionUID = 5342219688831814749L;
		private int mColumn;
		private ColumnLink<T> mColumnLink;
		private LinkedListSparse2DArray.CellLink<T> mRowLink;

		/* Support for row hiding */
		private int mColumnOriginal;
		
		public ColumnLink(int column) {
			this.mColumn = column;
			this.mColumnOriginal = column;
		}

		private void patchCellLink(LinkedListSparse2DArray.CellLink<T> newLink,
				int row) {
			LinkedListSparse2DArray.CellLink link = this.mRowLink;
			LinkedListSparse2DArray.CellLink previousLink = null;
			while (link != null) {
				int testRow = link.getRow();
				if (testRow == row)
					throw new IllegalStateException(
							"Trying to patch in cell link for row " + row
									+ " on column " + this.mColumn);
				if (testRow > row) {
					break;
				}

				previousLink = link;
				link = link.getRowLink();
			}

			newLink.setRowLink(link);
			if (previousLink == null) {
				this.mRowLink = newLink;
			} else {
				previousLink.setRowLink(newLink);
			}
		}

		/**
		 * Retrieves original column index
		 * @return
		 */
		public int getColumnOriginal(){
			return mColumnOriginal;
		}
		
		public int getColumn() {
			return this.mColumn;
		}

		private void setColumn(int column) {
			this.mColumn = column;
		}

		public ColumnLink<T> getColumnLink() {
			return this.mColumnLink;
		}

		public void setColumnLink(ColumnLink<T> columnLink) {
			this.mColumnLink = columnLink;
		}

		public LinkedListSparse2DArray.CellLink<T> getRowLink() {
			return this.mRowLink;
		}

		public void setRowLink(LinkedListSparse2DArray.CellLink<T> rowLink) {
			this.mRowLink = rowLink;
		}

		// $FF: synthetic method
		static void accessMethod000(ColumnLink x0, int x1) {
			x0.setColumn(x1);
		}

		// $FF: synthetic method
		static void accessMethod700(ColumnLink x0, CellLink x1, int x2) {
			x0.patchCellLink(x1, x2);
		}
	}

	public static class RowLink<T> implements Serializable {
		private static final long serialVersionUID = 1421629700728536342L;
		private int mRow;
		private RowLink<T> mRowLink;
		private LinkedListSparse2DArray.CellLink<T> mColumnLink;

		/* Support for row hiding */
		private int mRowOriginal;
		
		public RowLink(int row) {
			this.mRow = row;
			this.mRowOriginal = row;
		}

		private LinkedListSparse2DArray.CellLink<T> findCell(
				LinkedListSparse2DArray<T> array, int column, boolean create) {
			return findCell(array, column, create, -1);
		}

		private LinkedListSparse2DArray.CellLink<T> findCell(
				LinkedListSparse2DArray<T> array, int column, boolean create,
				int maxColumn) {
			LinkedListSparse2DArray.CellLink link = this.mColumnLink;
			LinkedListSparse2DArray.CellLink previousLink = null;
			while (link != null) {
				int testColumn = link.getColumn();
				if (testColumn == column)
					return link;
				if (testColumn > column) {
					if (testColumn > maxColumn)
						break;
					return link;
				}

				previousLink = link;
				link = link.getColumnLink();
			}
			if (create)
				return createNewCellLink(array, previousLink, this.mRow, column);
			return null;
		}

		private LinkedListSparse2DArray.CellLink<T> createNewCellLink(
				LinkedListSparse2DArray<T> array,
				LinkedListSparse2DArray.CellLink<T> after, int row, int column) {
			LinkedListSparse2DArray.CellLink newLink = new LinkedListSparse2DArray.CellLink(
					row, column);

			if (after == null) {
				newLink.setColumnLink(this.mColumnLink);
				this.mColumnLink = newLink;
			} else {
				newLink.setColumnLink(after.getColumnLink());
				after.setColumnLink(newLink);
			}

			array.patchCellLinkIntoColumnLinks(newLink, row, column);
			return newLink;
		}

		public int getRow() {
			return this.mRow;
		}
		
		/**
		 * Retrieves the original row number
		 * set up initially.
		 * @return original row number
		 */
		public int getRowOrginal() {
			return this.mRowOriginal;
		}

		private void setRow(int row) {
			this.mRow = row;
		}

		public RowLink<T> getRowLink() {
			return this.mRowLink;
		}

		public void setRowLink(RowLink<T> rowLink) {
			this.mRowLink = rowLink;
		}

		public LinkedListSparse2DArray.CellLink<T> getColumnLink() {
			return this.mColumnLink;
		}

		public void setColumnLink(LinkedListSparse2DArray.CellLink<T> columnLink) {
			this.mColumnLink = columnLink;
		}

		// $FF: synthetic method
		static void accessMethodSetRow(RowLink x0, int x1) {
			x0.setRow(x1);
		}

		// $FF: synthetic method
		static CellLink accessMethod600(RowLink x0, LinkedListSparse2DArray x1,
				int x2, boolean x3) {
			return x0.findCell(x1, x2, x3);
		}

		// $FF: synthetic method
		static CellLink accessMethod1300(RowLink x0,
				LinkedListSparse2DArray x1, int x2, boolean x3, int x4) {
			return x0.findCell(x1, x2, x3, x4);
		}

	}

	private class RangeItr implements
			Iterator<LinkedListSparse2DArray.CellLink<T>> {
		private int mExpectedModification = LinkedListSparse2DArray.this.mModificationCount;
		private LinkedListSparse2DArray.CellLink<T> mLastRetrieved = null;
		private LinkedListSparse2DArray.CellLink<T> mNextCell = null;
		private LinkedListSparse2DArray.RowLink<T> mCurrentRow = null;
		private int mEndRow;
		private int mStartColumn;
		private int mEndColumn;

		public RangeItr(int startRow, int startColumn, int endRow, int endColumn) {
			this.mEndRow = endRow;
			this.mStartColumn = startColumn;
			this.mEndColumn = endColumn;

			while ((startRow <= endRow) && (this.mNextCell == null)) {
				this.mCurrentRow = LinkedListSparse2DArray.this.findRow(
						startRow, false, this.mEndRow);
				if (this.mCurrentRow != null) {
					this.mNextCell = this.mCurrentRow.findCell(
							LinkedListSparse2DArray.this, this.mStartColumn,
							false, this.mEndColumn);
				}
				startRow++;
			}
		}

		public boolean hasNext() {
			return this.mNextCell != null;
		}

		public LinkedListSparse2DArray.CellLink<T> next() {
			this.mLastRetrieved = this.mNextCell;

			this.mNextCell = this.mLastRetrieved.getColumnLink();

			if ((this.mNextCell != null)
					&& (this.mNextCell.getColumn() > this.mEndColumn))
				this.mNextCell = null;
			if (this.mNextCell == null) {
				this.mCurrentRow = this.mCurrentRow.getRowLink();
				while ((this.mCurrentRow != null)
						&& (this.mCurrentRow.getRow() <= this.mEndRow)) {
					this.mNextCell = this.mCurrentRow.findCell(
							LinkedListSparse2DArray.this, this.mStartColumn,
							false, this.mEndColumn);

					if (this.mNextCell != null)
						break;
					this.mCurrentRow = this.mCurrentRow.getRowLink();
				}

			}

			return this.mLastRetrieved;
		}

		public void remove() {
			if (this.mLastRetrieved == null)
				throw new IllegalStateException();
			checkForComodification();

			LinkedListSparse2DArray.this.remove(this.mLastRetrieved.getRow(),
					this.mLastRetrieved.getColumn());
			this.mExpectedModification = LinkedListSparse2DArray.this.mModificationCount;
		}

		void checkForComodification() {
			if (LinkedListSparse2DArray.this.mModificationCount != this.mExpectedModification)
				throw new ConcurrentModificationException();
		}
	}

	private class ColumnItr implements
			Iterator<LinkedListSparse2DArray.CellLink<T>> {
		private int mExpectedModification = LinkedListSparse2DArray.this.mModificationCount;
		private LinkedListSparse2DArray.CellLink<T> mLastRetrieved = null;
		private LinkedListSparse2DArray.CellLink<T> mNextCell = null;

		public ColumnItr(int column) {
			LinkedListSparse2DArray.ColumnLink currentColumn = LinkedListSparse2DArray.this
					.findColumn(column, false);
			if (currentColumn != null) {
				this.mNextCell = currentColumn.getRowLink();
			}
		}

		public boolean hasNext() {
			return this.mNextCell != null;
		}

		public LinkedListSparse2DArray.CellLink<T> next() {
			checkForComodification();
			this.mLastRetrieved = this.mNextCell;
			this.mNextCell = this.mLastRetrieved.getRowLink();
			return this.mLastRetrieved;
		}

		public void remove() {
			if (this.mLastRetrieved == null)
				throw new IllegalStateException();
			checkForComodification();

			LinkedListSparse2DArray.this.remove(this.mLastRetrieved.getRow(),
					this.mLastRetrieved.getColumn());
			this.mExpectedModification = LinkedListSparse2DArray.this.mModificationCount;
		}

		void checkForComodification() {
			if (LinkedListSparse2DArray.this.mModificationCount != this.mExpectedModification)
				throw new ConcurrentModificationException();
		}
	}

	private class RowItr implements
			Iterator<LinkedListSparse2DArray.CellLink<T>> {
		private int mExpectedModification = LinkedListSparse2DArray.this.mModificationCount;
		private LinkedListSparse2DArray.CellLink<T> mLastRetrieved = null;
		private LinkedListSparse2DArray.CellLink<T> mNextCell = null;

		public RowItr(int row) {
			LinkedListSparse2DArray.RowLink currentRow = LinkedListSparse2DArray.this
					.findRow(row, false);
			if (currentRow != null) {
				this.mNextCell = currentRow.getColumnLink();
			}
		}

		public boolean hasNext() {
			return this.mNextCell != null;
		}

		public LinkedListSparse2DArray.CellLink<T> next() {
			checkForComodification();
			this.mLastRetrieved = this.mNextCell;
			this.mNextCell = this.mLastRetrieved.getColumnLink();
			return this.mLastRetrieved;
		}

		public void remove() {
			if (this.mLastRetrieved == null)
				throw new IllegalStateException();
			checkForComodification();

			LinkedListSparse2DArray.this.remove(this.mLastRetrieved.getRow(),
					this.mLastRetrieved.getColumn());
			this.mExpectedModification = LinkedListSparse2DArray.this.mModificationCount;
		}

		void checkForComodification() {
			if (LinkedListSparse2DArray.this.mModificationCount != this.mExpectedModification)
				throw new ConcurrentModificationException();
		}
	}

	private class Itr implements Iterator<LinkedListSparse2DArray.CellLink<T>> {
		private int mExpectedModification = LinkedListSparse2DArray.this.mModificationCount;
		private LinkedListSparse2DArray.RowLink<T> mCurrentRow;
		private LinkedListSparse2DArray.CellLink<T> mLastRetrieved = null;
		private LinkedListSparse2DArray.CellLink<T> mNextCell = null;

		public Itr() {
			if (LinkedListSparse2DArray.this.mRows != null) {
				this.mCurrentRow = LinkedListSparse2DArray.this.mRows;
				this.mNextCell = LinkedListSparse2DArray.this.mRows
						.getColumnLink();
			}
		}

		public boolean hasNext() {
			return this.mNextCell != null;
		}

		public LinkedListSparse2DArray.CellLink<T> next() {
			checkForComodification();
			this.mLastRetrieved = this.mNextCell;
			this.mNextCell = this.mLastRetrieved.getColumnLink();
			if (this.mNextCell == null) {
				this.mCurrentRow = this.mCurrentRow.getRowLink();
				if (this.mCurrentRow != null)
					this.mNextCell = this.mCurrentRow.getColumnLink();
			}
			return this.mLastRetrieved;
		}

		public void remove() {
			if (this.mLastRetrieved == null)
				throw new IllegalStateException();
			checkForComodification();

			LinkedListSparse2DArray.this.remove(this.mLastRetrieved.getRow(),
					this.mLastRetrieved.getColumn());
			this.mExpectedModification = LinkedListSparse2DArray.this.mModificationCount;
		}

		void checkForComodification() {
			if (LinkedListSparse2DArray.this.mModificationCount != this.mExpectedModification)
				throw new ConcurrentModificationException();
		}
	}
}
