package vo;

public class LikeVO {
	int boardNo;
	int like;
	int hate;
	String cUser;
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getHate() {
		return hate;
	}
	public void setHate(int hate) {
		this.hate = hate;
	}
	public String getcUser() {
		return cUser;
	}
	public void setcUser(String cUser) {
		this.cUser = cUser;
	}
}
