package vo;

public class tableVo {
	int totRevenue;
	String storeName;
	String tableRows;
	
	public int getTotRevenue() {
		return totRevenue;
	}
	public void setTotRevenue(int totRevenue) {
		this.totRevenue = totRevenue;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getTableRows() {
		return tableRows;
	}
	public void setTableRows(String tableRows) {
		this.tableRows = tableRows;
	}
	
	@Override
	public String toString() {
		return totRevenue+","+storeName+","+tableRows;
	}
}
