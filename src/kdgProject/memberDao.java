package kdgProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class memberDao {

	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	book book = new book();
	bookDao dao = new bookDao();
	static String loginId = "";
	
	
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
	
	//로그인
	  boolean login(String memId, String memPw) {
	    getConn();
		String sql = "select mem_id FROM member WHERE mem_pw = ? ";
    
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, memPw); 
			rs = psmt.executeQuery(); 
			if (rs.next()) {
				if (rs.getString(1).equals(memId)) {
					loginId = rs.getString(1);
					return true;
				} else
					System.out.println("비밀번호가 일치하지 않습니다.");	
					return false;
			}
			System.out.println("아이디가 존재하지 않습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;// 데이터 베이스 오류를 의미  
	}
	
	List<member> memList(){ 
		getConn();
		List<member> list = new ArrayList<member>();
		String sql = "select * "
				   + "from member order by mem_no";
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				member mem = new member();
				mem.setMemNo(rs.getInt("mem_no"));
				mem.setMemId(rs.getString("mem_id"));
				mem.setMemPw(rs.getString("mem_pw"));
				mem.setMemName(rs.getString("mem_name"));
				mem.setBdate(rs.getString("birdate"));
				mem.setPhone(rs.getString("phone"));
				mem.setMemBook(rs.getString("book_name"));
				list.add(mem);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//회원 등록
	boolean getInput(member mem) {
		getConn();
		String sql = "insert into member(mem_no, mem_id, mem_pw ,mem_name, birdate, phone) "
				+ "values(emp_seq.NEXTVAL, ?, ?, ?, ?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			
			if(mem.equals(mem.getMemId())) {
				System.out.println("중복된 아이디입니다.");
			}else {
				psmt.setString(1, mem.getMemId());				
			}
			
			psmt.setString(2, mem.getMemPw());
			psmt.setString(3, mem.getMemName());
			psmt.setString(4, mem.getBdate());
			psmt.setString(5, mem.getPhone());
			
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true;
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//회원 탈퇴
	boolean deleteMember(member mem) {
		getConn();
		
		if(mem.equals(mem.getMemName())) {
			String sql = "delete member ";
			sql += " 	  where mem_name = ?";
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, mem.getMemName());
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}else if (mem.equals(mem.getMemNo())){
			String sql = "delete member ";
			sql += " 	  where mem_no = ?";
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, mem.getMemNo());
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}else {
			String sql = "delete member ";
			sql += " 	  where mem_id = ?";
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, mem.getMemId());
				
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
	boolean updateBook(book book) {
		getConn();
//		if(book.equals(book.getBookName())) {
			String sql = "update member ";
			sql += "	  set book_name = ? )";
			sql += " 	  where mem_id = ? ";
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, book.getBookName());
				psmt.setString(2, loginId);
				
				int r = psmt.executeUpdate();
				if(r > 0) {
					return true;
				} 
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}
		
//		}else {
//			String sql = "update member "
//				+ "	  set book_name = (select book_name from books where book_no = ? ) "
//				+ " 	  where mem_id = ? ";
//			try {
//				psmt = conn.prepareStatement(sql);
//				psmt.setString(1, book.getBookNo());
//				psmt.setString(2, loginId);
//				
//				int r = psmt.executeUpdate();
//				if(r > 0) {
//					return true;
//				} 
//				
//			} catch (SQLException e) {
//				
//				e.printStackTrace();
//				
//			}
//		}
		return false;
	}

	//도서 반납
	boolean returnBook(member mem) {
		getConn();
		String sql = "update member ";
		sql += "	  set book_name = '대여 도서가 없습니다' ";
		sql += "      where mem_id = ? ";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, loginId);
			
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true;
			} 
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	
		return false;
	}
	
	//본인이 대여중인 도서 조회
	List<member> membookList(){
		getConn();
		List<member> membookList = new ArrayList<member>();
		String sql = "select book_name from member where mem_id = ? ";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, loginId);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				member mem = new member();
				mem.setMemBook(rs.getString("book_name"));
				membookList.add(mem);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			

		}
		return membookList;
	
	}
	
}
