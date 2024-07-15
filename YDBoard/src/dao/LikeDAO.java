package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.BoardVO;
import vo.LikeVO;

public class LikeDAO extends DAO{

	
	
	
//	public List<LikeVO> liLike(){
//		conn = getConn();
//		List<LikeVO> list = new ArrayList<>();
//		
//		String sql = "select * from LIKES ";
//		sql +=       "where list_no = 1";
//	
//		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
//			while(rs.next()) {
//				LikeVO bv = new LikeVO();
//				
//				bv.setBoardNo(rs.getInt("board_no"));
//				bv.setTitle(rs.getString("title"));
//				bv.setWriter(rs.getString("writer"));
//				bv.setMdate(rs.getString("mdate"));
//				bv.setSumLike(rs.getInt("sum_like"));
//				
//				list.add(bv);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
		
	
	
	//-------------------- 좋아요 항목 생성
	public void cLike(String id, int bN){
		conn = getConn();
		String sql = "INSERT INTO LIKES (C_USER, "
									+ " LIKE_NO, "
									+ "BOARD_NO )";
		sql +=       " VALUES (?, "
							+ " 1, "
							+ " ?)";
						
	
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setInt(2, bN);

			rs = psmt.executeQuery();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//----------------------싫어요
	
	public void cHate(String id, int bN){
		conn = getConn();
		String sql = "INSERT INTO LIKES (C_USER,"
									+ " HATE_NO, "
									+ "BOARD_NO)";
		sql +=       " VALUES (?, "
							+ " 1, "
							+ " ? )";
	
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setInt(2, bN);

			rs = psmt.executeQuery();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//------------------------- 좋아요 개수 
	public int cntLike(int bN){
		conn = getConn();
		int cntR = 0;
		String sql = "SELECT * "
				+ "FROM LIKES "
				+ "WHERE LIKE_NO = 1 "
				+ " AND BOARD_NO = ?";
	
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, bN);

			rs = psmt.executeQuery();
			while(rs.next()) {

				cntR++;
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return cntR;
	}
	
	//------------------------- 시러요 개수 
	public int cntHate(int bN){
		conn = getConn();
		int cntR = 0;
		String sql = "SELECT * "
				+ "FROM LIKES "
				+ "WHERE HATE_NO = 1 "
				+ " AND BOARD_NO = ?";
	
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, bN);

			rs = psmt.executeQuery();
			while(rs.next()) {

				cntR++;
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return cntR;
	}
	
	//------------------중복확인
	public int checkL(int bN, String user){
		conn = getConn();
		int cntR = 0;
		String sql = "SELECT * "
				+ "FROM LIKES "
				+ "WHERE BOARD_NO = ? "
				+ " AND C_USER = ?";
	
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, bN);
			psmt.setString(2, user);

			rs = psmt.executeQuery();
			while(rs.next()) {

				return cntR=1;
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return cntR;
	}
}
