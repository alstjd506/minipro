package kdgProject;

public class member {

	private String memId;
	private String memPw;
	private String memName;
	private String bdate;
	private String phone;
	private String memBook;
	private int memNo;
	
	book book = new book();
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPw() {
		return memPw;
	}
	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMemBook() {
		return memBook;
	}
	public void setMemBook(String memBook) {
		this.memBook = memBook;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	
	@Override
	public String toString() {
		return String.format("%-8d %-10s %-10s %-15s %15s %20s", memNo,memId, memName, bdate , phone, memBook);
	}
	
	public String showRent() {
		return String.format("%20s", memBook);
	}
}
