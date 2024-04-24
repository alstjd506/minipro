package kdgProject;

public class book {
	private String bookNo;
	private String bookName;
	private String bookAuthor;
	private String bookCheck;
	
	
	public String getBookCheck() {
		return bookCheck;
	}
	public void setBookCheck(String bookCheck) {
		this.bookCheck = bookCheck;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
	public String showBook() {
		return String.format("%-8s %-10s %-15s", bookNo, bookName, bookAuthor);
	}
	@Override
	public String toString() {
		return String.format("%s\t\t %s\t\t %s\t\t %s", bookNo, bookName, bookAuthor, bookCheck);
	}
	
	
}
