package dao;

import java.sql.SQLException;

public class CustomerDAO extends DAO{

	public void cCtm(String id, String pwd) {
		conn = getConn();
		String sql = "INSERT INTO customer (ctm_id, ctm_pwd) ";
		sql +=       "VALUES ('"+id+"', '"+pwd+"')";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("중복된 아이디 입니다.");
			return;
			//e.printStackTrace();
		}
		System.out.println("회원가입이 완료되었습니다.");
	}
}
