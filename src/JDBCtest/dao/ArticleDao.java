package JDBCtest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import JDBCtest.dto.Article;
import JDBCtest.dto.Board;
import JDBCtest.dto.Member;
import JDBCtest.mysqlutil.MysqlUtil;
import JDBCtest.mysqlutil.SecSql;

public class ArticleDao {

	String driver;
	Connection conn;
	String url;
	String userName;
	String userPw;
	String sql;

	public ArticleDao() {

		driver = "com.mysql.cj.jdbc.Driver";
		url = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
		userName = "sbsst";
		userPw = "sbs123414";

	}

	// 게시물 생성
	public int add(int boardId, String title, String body, int memberId) {
		int id = 0;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				conn = DriverManager.getConnection(url, userName, userPw);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sql = "INSERT INTO article ";
			sql += "SET ";
			sql += "regDate = NOW(), ";
			sql += "updateDate = NOW(), ";
			sql += "title = ?, ";
			sql += "body = ?, ";
			sql += "memberId = ?,";
			sql += "boardId = ?";

			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, title);
				pstmt.setString(2, body);
				pstmt.setInt(3, memberId);
				pstmt.setInt(4, boardId);

				pstmt.executeUpdate();

				ResultSet addedArticleId = pstmt.getGeneratedKeys();
				addedArticleId.next();
				id = addedArticleId.getInt(1);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return id;
	}

	// 게시물 리스팅
	public List<Article> getArticles() {
//		List<Article> articles = new ArrayList<>();

		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "textBoard");
		MysqlUtil.setDevMode(true);

		List<Map<String, Object>> articleListMap = new ArrayList<Map<String, Object>>();

		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article");
		


		Map<String, Object> articleMap = MysqlUtil.selectRow(sql.append("SELECT * FROM article"));
		System.out.println(articleMap);
		
		articleListMap.add(articleMap);
		System.out.println(articleListMap);

//		articleMap.put("id", );

		MysqlUtil.closeConnection();

		return null;
	}

	// 게시물 수정
	public void modifyArticle(int inputedId, String title, String body) {

		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "textBoard");

		MysqlUtil.setDevMode(true);

		new SecSql().append("UPDATE article SET updateDate = NOW(),").append("title = ?", title)
				.append("body = ?", body).append("WHERE id = ?", inputedId);

		MysqlUtil.closeConnection();

	}

	// 게시물 삭제
	public void deleteArticle(int inputedId) {
		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				conn = DriverManager.getConnection(url, userName, userPw);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sql = "DELETE FROM article ";
			sql += "WHERE id = ? ";

			PreparedStatement pstmt;

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, inputedId);

				pstmt.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			try {
				if (conn != null) {
					conn.close();

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public int boardAdd(String boardName) {
		int boardId = 0;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				conn = DriverManager.getConnection(url, userName, userPw);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sql = "INSERT INTO board ";
			sql += "SET ";
			sql += "boardName = ?";

			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, boardName);

				pstmt.executeUpdate();

				ResultSet addedBoardId = pstmt.getGeneratedKeys();
				addedBoardId.next();
				boardId = addedBoardId.getInt(1);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return boardId;
	}

	public Board getBoard(int inputedId) {
		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				conn = DriverManager.getConnection(url, userName, userPw);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sql = "SELECT * FROM board ";

			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {

					if (rs.getInt("boardId") == inputedId) {
						int boardId = rs.getInt("boardId");
						String boardName = rs.getString("boardName");

						Board board = new Board(boardId, boardName);
						return board;
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			try {
				if (conn != null) {
					conn.close();

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

}
