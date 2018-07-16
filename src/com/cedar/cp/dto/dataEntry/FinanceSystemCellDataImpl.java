package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinanceSystemCellDataImpl implements FinanceSystemCellData, Serializable {
	private String mDimSelectionSummary;
	private String mOtherSelectionSummary;
	private BigDecimal mTotal = null;
	private String mTotalName = null;
	private String mValidationMessage = null;
	private String mFmsTransferUrl = null;
	private List<List<Object>> mRows = new ArrayList();
	private List<Integer[]> mColumnGroupRanges = new ArrayList();
	private List<String> mColumnGroupNames = new ArrayList();
	private List<String> mColumnNames = new ArrayList();
	private List<Integer> mColumnTypes = new ArrayList();
	private List<Integer> mColumnMaxScales = new ArrayList();
	private Map<Integer, String> mColumnGroupSources = new HashMap();
	private List<Object> mCurrentRow = null;
	private List<Object> mCurrentColumnGroupRow = null;
	private List<List<Object>> mCurrentColumnGroupRows = null;

	public void setDimSelectionSummary(String selectionSummary) {
		mDimSelectionSummary = selectionSummary;
	}

	public void setOtherSelectionSummary(String selectionSummary) {
		mOtherSelectionSummary = selectionSummary;
	}

	public void addToTotal(BigDecimal amount) {
		if (amount == null)
			return;
		if (mTotal == null)
			mTotal = new BigDecimal(0);
		mTotal = mTotal.add(amount);
	}

	public BigDecimal getTotal() {
		return mTotal;
	}

	public void setTotalName(String name) {
		mTotalName = name;
	}

	public String getTotalName() {
		return mTotalName;
	}

	public void setValidationMessage(String m) {
		mValidationMessage = m;
	}

	public String getValidationMessage() {
		return mValidationMessage;
	}

	public String getDimSelectionSummary() {
		return mDimSelectionSummary;
	}

	public String getOtherSelectionSummary() {
		return mOtherSelectionSummary;
	}

	public int getRowCount() {
		return mRows.size();
	}

	public int getColumnCount() {
		return mColumnNames.size();
	}

	public int getColumnGroupCount() {
		return mColumnGroupNames.size();
	}

	public void addRow(List<Object> row) {
		mRows.add(row);
	}

	public void addColumnNameAndType(String name, int type) {
		mColumnNames.add(name);
		mColumnTypes.add(Integer.valueOf(type));
		mColumnMaxScales.add(Integer.valueOf(0));
	}

	public void addColumnGroupName(String name) {
		mColumnGroupNames.add(name);
	}

	public void addColumnGroupSource(String name, int col) {
		mColumnGroupSources.put(Integer.valueOf(col), name);
	}

	public void addColumnGroupRange(Integer start, Integer end) {
		mColumnGroupRanges.add(new Integer[] { start, end });
	}

	public Object getValueAt(int row, int col) {
		try {
			return ((List) mRows.get(row)).get(col);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public String getColumnName(int col) {
		return (String) mColumnNames.get(col);
	}

	public Integer getColumnType(int col) {
		return (Integer) mColumnTypes.get(col);
	}

	public int getColumnMaxScale(int col) {
		return ((Integer) mColumnMaxScales.get(col)).intValue();
	}

	public void setColumnMaxScale(int col, int scale) {
		mColumnMaxScales.set(col, Integer.valueOf(scale));
	}

	public Integer getColumnGroupSize(String name) {
		for (int i = 0; i < mColumnGroupNames.size(); i++) {
			if (getColumnGroupName(i).equals(name)) {
				Integer[] range = getColumnGroupRange(i);
				return Integer.valueOf(1 + (range[1].intValue() - range[0].intValue()));
			}
		}
		throw new IllegalStateException("group name not found: " + name);
	}

	public Integer[] getColumnGroupRange(int group) {
		return (Integer[]) mColumnGroupRanges.get(group);
	}

	public String getColumnGroupName(int group) {
		return (String) mColumnGroupNames.get(group);
	}

	public void dropUnusedColumnGroup(String groupName) {
		int groupIndex = -1;
		for (int i = 0; i < getColumnGroupCount(); i++) {
			if (groupName.equals(getColumnGroupName(i))) {
				groupIndex = i;
				break;
			}
		}
		if (groupIndex == -1)
			throw new IllegalStateException("unknown group name " + groupName);
		Integer[] groupRange = getColumnGroupRange(groupIndex);
		int startCol = groupRange[0].intValue();
		int endCol = groupRange[1].intValue();
		mColumnGroupNames.remove(groupIndex);
		mColumnGroupRanges.remove(groupIndex);

		for (int i = endCol; i > startCol - 1; i--) {
			mColumnNames.remove(i);
			mColumnTypes.remove(i);
			mColumnMaxScales.remove(i);
		}

		for (int i = 0; i < getColumnGroupCount(); i++) {
			Integer[] otherGroupRange = getColumnGroupRange(i);
			int otherStartCol = otherGroupRange[0].intValue();
			int otherEndCol = otherGroupRange[1].intValue();
			if (otherStartCol > startCol) {
				otherStartCol -= 1 + (endCol - startCol);
				otherEndCol -= 1 + (endCol - startCol);
				((Integer[]) mColumnGroupRanges.get(i))[0] = Integer.valueOf(otherStartCol);
				((Integer[]) mColumnGroupRanges.get(i))[1] = Integer.valueOf(otherEndCol);
			}
		}
	}

	public void newRow() {
		mCurrentRow = new ArrayList();
	}

	public void storeRow() {
		if (mCurrentRow != null)
			mRows.add(mCurrentRow);
		mCurrentRow = null;
	}

	public void addColumnValue(Object o) {
		mCurrentRow.add(o);
	}

	public int getCurrentRowColumnCount() {
		return mCurrentRow.size();
	}

	public void newColumnGroupRows() {
		mCurrentColumnGroupRows = new ArrayList();
	}

	public void newColumnGroupRow() {
		mCurrentColumnGroupRow = new ArrayList();
	}

	public void storeColumnGroupRow() {
		if (mCurrentColumnGroupRow != null)
			mCurrentColumnGroupRows.add(mCurrentColumnGroupRow);
		mCurrentColumnGroupRow = null;
	}

	public void storeColumnGroupRows() {
		if (mCurrentColumnGroupRows != null)
			mCurrentRow.add(mCurrentColumnGroupRows);
		mCurrentColumnGroupRows = null;
	}

	public void addColumnGroupValue(Object o) {
		mCurrentColumnGroupRow.add(o);
	}

	public void straightenOutResults() {
		if (getColumnGroupCount() == 0) {
			return;
		}
		List currentRows = mRows;

		mRows = new ArrayList();

		for (int mainRow = 0; mainRow < currentRows.size(); mainRow++) {
			List rowdata = (List) currentRows.get(mainRow);
			int extraRowCount = 0;

			newRow();
			for (int mainCol = 0; mainCol < rowdata.size(); mainCol++) {
				int actualColIndex = mainCol;
				String colGroupName = (String) mColumnGroupSources.get(Integer.valueOf(mainCol));

				if (colGroupName == null) {
					addColumnValue(rowdata.get(mainCol));
				} else if (mColumnGroupNames.contains(colGroupName)) {
					List groupRows = (List) rowdata.get(mainCol);
					if (groupRows.size() == 0) {
						for (int extraCol = 0; extraCol < getColumnGroupSize(colGroupName).intValue(); extraCol++)
							addColumnValue(null);
					} else {
						List groupRow = (List) groupRows.get(0);

						for (int extraCol = 0; extraCol < groupRow.size(); extraCol++) {
							Object embeddedColumnValue = groupRow.get(extraCol);
							addColumnValue(embeddedColumnValue);
							if ((embeddedColumnValue != null) && ((embeddedColumnValue instanceof BigDecimal))) {
								BigDecimal bd = (BigDecimal) embeddedColumnValue;
								if (bd.scale() > getColumnMaxScale(actualColIndex))
									setColumnMaxScale(actualColIndex, bd.scale());
							}
							actualColIndex++;
						}
						if (groupRows.size() > extraRowCount)
							extraRowCount = groupRows.size();
					}
				}
			}
			storeRow();

			for (int extraRow = 1; extraRow < extraRowCount; extraRow++) {
				newRow();

				for (int mainCol = 0; mainCol < rowdata.size(); mainCol++) {
					int actualColIndex = mainCol;

					String colGroupName = (String) mColumnGroupSources.get(Integer.valueOf(mainCol));
					if (colGroupName == null) {
						addColumnValue(null);
					} else if (mColumnGroupNames.contains(colGroupName)) {
						List groupRows = (List) rowdata.get(mainCol);
						if (extraRow > groupRows.size()) {
							for (int extraCol = 0; extraCol < getColumnGroupSize(colGroupName).intValue(); extraCol++)
								addColumnValue(null);
						} else {
							List groupRow = (List) groupRows.get(extraRow);

							for (int extraCol = 0; extraCol < groupRow.size(); extraCol++) {
								Object embeddedColumnValue = groupRow.get(extraCol);
								addColumnValue(embeddedColumnValue);
								if ((embeddedColumnValue != null) && ((embeddedColumnValue instanceof BigDecimal))) {
									BigDecimal bd = (BigDecimal) embeddedColumnValue;
									if (bd.scale() > getColumnMaxScale(actualColIndex))
										setColumnMaxScale(actualColIndex, bd.scale());
								}
								actualColIndex++;
							}
						}
					}
				}

				storeRow();
			}
		}
	}

	public void setFmsTransferUrl(String s) {
		mFmsTransferUrl = s;
	}

	public String getFmsTransferUrl() {
		return mFmsTransferUrl;
	}

	
	@Override
	public List<String> getColumnNames() {
		if (mColumnNames == null) {
			return new ArrayList<String>();
		} else {
			return mColumnNames;
		}
	}

	@Override
	public List<List<Object>> getRows() {
		return mRows;
	}
}