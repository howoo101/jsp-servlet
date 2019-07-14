package vo;

import java.util.Calendar;

public class revenueVo {
	int revenueId;
	int menuCode;
	Calendar rDate;
	
	public int getRevenueId() {
		return revenueId;
	}
	public void setRevenueId(int revenueId) {
		this.revenueId = revenueId;
	}
	
	public int getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(int menuCode) {
		this.menuCode = menuCode;
	}
	
	public Calendar getrDate() {
		return rDate;
	}
	public void setrDate(Calendar rDate) {
		this.rDate = rDate;
	}
	
	
}
