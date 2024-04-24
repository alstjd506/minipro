package kdgProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class boardDado {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	memberDao Mdao = new memberDao();
	
	
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
	//게시글 작성
	boolean getBoardInput(board board) {
		getConn();
		String sql = "insert into board(board_title, board_name, board_con, board_pw) "
				+ "values(?, ?, ?, ?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getBTitle());
			psmt.setString(2, Mdao.loginId);
			psmt.setString(3, board.getBCon());
			psmt.setString(4, board.getBPw());
			
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true;
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//게시글 수정
	boolean getBoardCorrect(board board) {
		getConn();
		String sql = "update board "
				+ " set board_con = ? "
				+ " where board_name = ? ";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getBCon());
			psmt.setString(2, Mdao.loginId);
		
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//게시글 삭제
	boolean deleteBoard(String bPw) {
		getConn();
		String sql = "delete board "
		    + " 	  where board_title = (select board_title"
		    + "								from board"
		    + "								where board_name = ? ) ";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Mdao.loginId);
			rs = psmt.executeQuery();
			if(rs.next()) {
				if (rs.getString(1).equals(bPw)) {
					return true;
			} else
				System.out.println("비밀번호가 일치하지 않습니다.");	
				return false;
		}
		System.out.println("게시글이 존재하지 않습니다.");
		
	} catch (Exception e) {
		e.printStackTrace();
	}		
		return false;
	}
	
}
