package JDBCtest.session;

public class Session {

	public int loginedMemberId;
	public int selectedBoardId;
	public int mangerMemberId;

	public boolean loginStatus() {
		// TODO Auto-generated method stub
		return loginedMemberId != 0;
	}
	public boolean managerLoginStatus() {
		return mangerMemberId != 0;
	}


}
