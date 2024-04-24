package kdgProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bookDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	
	private void getConn() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "jsp", "jsp");
		} catch (Exception e) {
		
			e.printStackTrace();
			return;
		}
	}
	
	//도서 등록
	boolean getBookInput(book book) {
		getConn();
		String sql = "insert into books(book_no, book_name, book_author) "
				+ "values(?, ?, ?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, book.getBookNo());
			psmt.setString(2, book.getBookName());
			psmt.setString(3, book.getBookAuthor());
			
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true;
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//도서 조회
	List<book> bookList(){ 
		getConn();
		List<book> bookList = new ArrayList<book>();
		String sql = "select * from books order by book_no";
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				book book = new book();
				book.setBookNo(rs.getString("book_no"));
				book.setBookName(rs.getString("book_name"));
				book.setBookAuthor(rs.getString("book_author"));
				book.setBookCheck(rs.getString("book_check"));
				bookList.add(book);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookList;
	}
	//대여가능한 도서 조회
	List<book> nRentList(){ 
		getConn();
		List<book> rBList = new ArrayList<book>();
		String sql = "select * from books where book_check = 'O' order by book_no ";
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				book book = new book();
				book.setBookNo(rs.getString("book_no"));
				book.setBookName(rs.getString("book_name"));
				book.setBookAuthor(rs.getString("book_author"));
				book.setBookCheck(rs.getString("book_check"));
				rBList.add(book);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rBList;
	}
	
	//도서 검색
	List<book> showList(book book) {
		getConn();
		List<book> showList = new ArrayList<book>();
		if(book.equals(book.getBookNo())) {
			
			String sql = "select * from books where book_no = ?";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookNo());
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					book = new book();
					book.setBookNo(rs.getString("book_no"));
					book.setBookName(rs.getString("book_name"));
					book.setBookAuthor(rs.getString("book_author"));
					book.setBookCheck(rs.getString("book_check"));
					showList.add(book);
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}else if(book.equals(book.getBookName())) {
			String sql = "select * from books where book_name = ?";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookName());
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					book = new book();
					book.setBookNo(rs.getString("book_no"));
					book.setBookName(rs.getString("book_name"));
					book.setBookAuthor(rs.getString("book_author"));
					book.setBookCheck(rs.getString("book_check"));
					showList.add(book);
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}else {
			String sql = "select * from books where book_author = ?";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookAuthor());
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					book = new book();
					book.setBookNo(rs.getString("book_no"));
					book.setBookName(rs.getString("book_name"));
					book.setBookAuthor(rs.getString("book_author"));
					book.setBookCheck(rs.getString("book_check"));
					showList.add(book);
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		return showList;
	}
	
	//도서 삭제
	boolean deleteBook(book book) {
		getConn();
		if(book.equals(book.getBookName())) {
			String sql = "delete books ";
			sql += " 	  where book_name = ?";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookName());
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}else  {
			String sql = "delete books ";
			sql += " 	  where book_no = ?";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookNo());
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	//도서 대여
	boolean rentBook(book book) {
		getConn();
		if(book.equals(book.getBookNo())) {
			String sql = "update books ";
			sql += "	  set book_check = '대여불가' ";
			sql += "	  where book_no = ? ";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookNo());
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}else {
			String sql = "update books ";
			sql += "	  set book_check = '대여불가' ";
			sql += "	  where book_name = ? ";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookName());
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	
	//도서 반납
	boolean returnBook(book book) {
		getConn();
		if(book.equals(book.getBookName())) {
			String sql = "update books ";
			sql += "	  set book_check = 'O' ";
			sql += "	  where book_name = ? ";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookName());
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}else {
			String sql = "update books ";
			sql += "	  set book_check = 'O'";
			sql += "	  where book_No = ? ";
			
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookNo());
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return false;
	}
}
