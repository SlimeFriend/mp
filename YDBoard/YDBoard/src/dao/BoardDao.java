package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.BoardVO;


public class BoardDao extends DAO{
	
	// ----------------- 아이디 체크 ----------------
//	public int loginExists(String id) {
//		conn = getConn();
//		String sql = "select count(1) from CUSTOMER ";
//		sql +=       " where CTM_ID = ?";
//		
//			try {
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, id);
//			rs = psmt.executeQuery();
//			if(rs.next()) {
//				return 1;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
	
	// -------------------- 비번체크 ----------------
//	public boolean pwdCorrect(String pwd, String id) {
//		conn = getConn();
//		String sql = "select * from customer ";
//		sql +=       " where ctm_id = '"+id+"'";
//		
//		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
//			if(rs.next()) {
//				if(rs.getString(1) == pwd) {
//					return true;
//				}
//				return false;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//		
//	}
	
	
	//------------------------싹다체크---------------------

	public int accCorrect(String id, String pwd) {
		conn = getConn();
		String sql = "select * from customer ";
		sql +=       " where ctm_id = '"+id+"' AND ctm_pwd = '"+pwd+"'";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				return 1;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
	
	//------------------- 게임게시판 리스트
	
	public List<BoardVO> viewGList() {
		conn = getConn();
		List<BoardVO> list = new ArrayList<>();
		
		String sql = "select * from board_List ";
		sql +=       "where list_no = 1 "
					+ "ORDER BY board_no desc ";
	
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				BoardVO bv = new BoardVO();
				
				bv.setBoardNo(rs.getInt("board_no"));
				bv.setTitle(rs.getString("title"));
				bv.setWriter(rs.getString("writer"));
				bv.setMdate(rs.getString("mdate"));
				bv.setSumLike(rs.getInt("sum_like"));
				
				list.add(bv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//------------------- 뮤직게시판 리스트
	
		public List<BoardVO> viewMList() {
			conn = getConn();
			List<BoardVO> list = new ArrayList<>();
			
			String sql = "select * from board_List ";
			sql +=       "where list_no = 2"
					+ "ORDER BY board_no desc ";
		
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					BoardVO bv = new BoardVO();
					
					bv.setBoardNo(rs.getInt("board_no"));
					bv.setTitle(rs.getString("title"));
					bv.setWriter(rs.getString("writer"));
					bv.setMdate(rs.getString("mdate"));
					bv.setSumLike(rs.getInt("sum_like"));
					
					list.add(bv);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
	//----------------------------- 뷰티 게시판리스트
		public List<BoardVO> viewBList() {
			conn = getConn();
			List<BoardVO> list = new ArrayList<>();
			
			String sql = "select * from board_List ";
			sql +=       "where list_no = 3"
					+ "ORDER BY board_no desc ";
		
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					BoardVO bv = new BoardVO();
					
					bv.setBoardNo(rs.getInt("board_no"));
					bv.setTitle(rs.getString("title"));
					bv.setWriter(rs.getString("writer"));
					bv.setMdate(rs.getString("mdate"));
					bv.setSumLike(rs.getInt("sum_like"));
					
					list.add(bv);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
	//----------------------게시판 세부 보기
		
//		public List<BoardVO> viewBoard(int bN){
//			conn = getConn();
//			List<BoardVO> list = new ArrayList<>();
//			String sql = "select * from board_List ";
//			sql +=       "where board_no = ?";
//		
//			try {
//				psmt = conn.prepareStatement(sql);
//				psmt.setInt(1, bN);
//				rs = psmt.executeQuery();
//					BoardVO bv = new BoardVO();
//					
//					bv.setBoardNo(rs.getInt("board_no"));
//					bv.setTitle(rs.getString("title"));
//					bv.setWriter(rs.getString("writer"));
//					bv.setMdate(rs.getString("mdate"));
//					bv.setSumLike(rs.getInt("sum_like"));
//					bv.setbContent(rs.getString("b_content"));
//					bv.setbPassword(rs.getString("b_password"));
//					
//					
//				
//				}
//			catch (SQLException e) {
//				e.printStackTrace();
//			}
//			return list;
//			
//		}
		
	//-----------------------  게시판 세부보기(vo로만)
		public BoardVO viewBoard(int bN){
			conn = getConn();
			BoardVO bv = new BoardVO();
			String sql = "select * from board_List ";
			sql +=       "where board_no = ?";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, bN);
				rs = psmt.executeQuery();
				while(rs.next()) {

					bv.setBoardNo(rs.getInt("board_no"));
					bv.setTitle(rs.getString("title"));
					bv.setWriter(rs.getString("writer"));
					bv.setMdate(rs.getString("mdate"));
					bv.setSumLike(rs.getInt("sum_like"));
					bv.setbContent(rs.getString("b_content"));
					bv.setbPassword(rs.getString("b_password"));
					
				}
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return bv;
			
		}
		
	//-------------------게시글 생성 비회원
		public void cBoard(String bN, String bC, String bP){
			conn = getConn();
			String sql = "INSERT INTO BOARD_LIST (BOARD_NO, "
												+	"TITLE, "
												+"WRITER, "
												+ "SUM_LIKE, "
												+ "LIST_NO, "
												+ "B_CONTENT, "
												+ "B_PASSWORD)";
			sql +=       "VALUES (jsp.game_board_no.NEXTVAL, "
								+ "?, "
								+ "'유저', "
								+ "0, "
								+ "1, "
								+ "?, "
								+ "?)";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, bN);
				psmt.setString(2, bC);
				psmt.setString(3, bP);
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//--------------------------음악 게시글 생성 비회원
		
		public void cmBoard(String bN, String bC, String bP){
			conn = getConn();
			String sql = "INSERT INTO BOARD_LIST (BOARD_NO, "
												+	"TITLE, "
												+"WRITER, "
												+ "SUM_LIKE, "
												+ "LIST_NO, "
												+ "B_CONTENT, "
												+ "B_PASSWORD)";
			sql +=       "VALUES (jsp.music_board_no.NEXTVAL, "
								+ "?, "
								+ "'유저', "
								+ "0, "
								+ "2, "
								+ "?, "
								+ "?)";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, bN);
				psmt.setString(2, bC);
				psmt.setString(3, bP);
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		// -------------------------- 뷰티 게시글 비회원
		
		public void cbBoard(String bN, String bC, String bP){
			conn = getConn();
			String sql = "INSERT INTO BOARD_LIST (BOARD_NO, "
												+	"TITLE, "
												+"WRITER, "
												+ "SUM_LIKE, "
												+ "LIST_NO, "
												+ "B_CONTENT, "
												+ "B_PASSWORD)";
			sql +=       "VALUES (jsp.beauty_board_no.NEXTVAL, "
								+ "?, "
								+ "'유저', "
								+ "0, "
								+ "3, "
								+ "?, "
								+ "?)";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, bN);
				psmt.setString(2, bC);
				psmt.setString(3, bP);
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		// ------------------------- 게시글 생성 회원
		public void ccBoard(String bN, String cid,String bC){
			conn = getConn();
			String sql = "INSERT INTO BOARD_LIST (BOARD_NO, "
												+	"TITLE, "
												+"WRITER, "
												+ "SUM_LIKE, "
												+ "LIST_NO, "
												+ "B_CONTENT) ";
			sql +=       "VALUES (jsp.game_board_no.NEXTVAL, "
								+ "?, "
								+ "?, "
								+ "0, "
								+ "1, "
								+ "? ) ";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, bN);
				psmt.setString(2, cid);
				psmt.setString(3, bC);
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//------------------------게시글 음악 회원 
		public void ccmBoard(String bN, String cid,String bC){
			conn = getConn();
			String sql = "INSERT INTO BOARD_LIST (BOARD_NO, "
												+	"TITLE, "
												+"WRITER, "
												+ "SUM_LIKE, "
												+ "LIST_NO, "
												+ "B_CONTENT) ";
			sql +=       "VALUES (jsp.music_board_no.NEXTVAL, "
								+ "?, "
								+ "?, "
								+ "0, "
								+ "2, "
								+ "? ) ";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, bN);
				psmt.setString(2, cid);
				psmt.setString(3, bC);
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//------------------------------------ 게시글 뷰티
		public void ccbBoard(String bN, String cid,String bC){
			conn = getConn();
			String sql = "INSERT INTO BOARD_LIST (BOARD_NO, "
												+	"TITLE, "
												+"WRITER, "
												+ "SUM_LIKE, "
												+ "LIST_NO, "
												+ "B_CONTENT) ";
			sql +=       "VALUES (jsp.beauty_board_no.NEXTVAL, "
								+ "?, "
								+ "?, "
								+ "0, "
								+ "3, "
								+ "? ) ";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, bN);
				psmt.setString(2, cid);
				psmt.setString(3, bC);
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		
		
	//-----------------게시글 수정 회원 비회원 통합
		public void chBoard(String bN, String bC, int bNO){
			conn = getConn();
			String sql = "UPDATE BOARD_LIST SET TITLE = ?, "
											+ "B_CONTENT = ?";
			sql +=       "WHERE BOARD_NO = ?";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, bN);
				psmt.setString(2, bC);
				psmt.setInt(3, bNO);
				
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	//----------------------- 게시글 삭제(비회원)
		public void dBoard(int bN){
			conn = getConn();
			String sql = "DELETE FROM BOARD_LIST ";
			sql +=       "WHERE BOARD_NO = ?";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, bN);
				
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//----------------------- 게시글 삭제(회원)
		public void cdBoard(String bN, String bC, int bNO){
			conn = getConn();
			String sql = "DELETE FROM BOARD_LIST ";
			sql +=       "WHERE BOARD_NO = ?";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, bN);
				psmt.setString(2, bC);
				
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//---------------------- 게시글 추천수 

		public void likeBoard(int suml, int bN){
			conn = getConn();
			String sql = "UPDATE BOARD_LIST SET SUM_LIKE = ? ";
			sql +=       " WHERE BOARD_NO = ?";
		
			try {
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, suml);
				psmt.setInt(2, bN);
				
				rs = psmt.executeQuery();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
}
		
