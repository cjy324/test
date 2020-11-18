package JDBCtest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import JDBCtest.dto.Article;
import JDBCtest.dto.Board;
import JDBCtest.dto.Reply;
import JDBCtest.mysqlutil.MysqlUtil;
import JDBCtest.mysqlutil.SecSql;

public class ArticleDao {

	public ArticleDao() {

	}

	// 게시물 생성
	public int add(int boardId, String title, String body, int memberId) {

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article ").append("SET ").append("regDate = NOW(), ").append("updateDate = NOW(), ")
				.append("title = ?, ", title).append("body = ?, ", body).append("memberId = ?, ", memberId)
				.append("boardId = ?", boardId);

		return MysqlUtil.insert(sql);
	}

	// 게시물 리스팅
	public List<Article> getArticles() {

		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article");

		List<Article> articles = new ArrayList<Article>();
		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql); // sql을 입력받고 명령어 수행 후 출력되는 값을 돌려받아
																				// articleListMap에 넣어주는 코드

		for (Map<String, Object> articleMap : articleListMap) {
			Article article = new Article(articleMap);

			articles.add(article);
		}
		return articles;
	}

	// 게시물 수정
	public void modifyArticle(int inputedId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article ").append("SET updateDate = NOW(),").append("title = ?, ", title)
				.append("body = ?, ", body).append("WHERE id = ?", inputedId);

		MysqlUtil.update(sql);
	}

	// 게시물 삭제
	public void deleteArticle(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article ").append("WHERE id = ?", inputedId);

		MysqlUtil.delete(sql);
	}

	// 게시판 추가
	public int addBoard(String boardName) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO board ");
		sql.append("SET boardName = ?", boardName);

		return MysqlUtil.insert(sql);
	}

	// 게시판 가져오기
	public Board getBoard(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM board WHERE boardId = ?", inputedId);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}

		return new Board(boardMap);
	}

	// 댓글 추가
	public int addReply(int ArticleId, String replyBody, int memberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO reply ");
		sql.append("SET replyBody = ?, ", replyBody);
		sql.append("replyArticleId = ?, ", ArticleId);
		sql.append("replyWriterId = ? ", memberId);

		return MysqlUtil.insert(sql);
	}

	public List<Reply> getReplies() {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM reply");

		List<Reply> replies = new ArrayList<>();
		List<Map<String, Object>> replyMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> replyMap : replyMapList) {
			Reply reply = new Reply(replyMap);

			replies.add(reply);
		}

		return replies;
	}

	public Reply getReply(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM reply WHERE replyId = ?", inputedId);

		Map<String, Object> replyMap = MysqlUtil.selectRow(sql);

		if (replyMap.isEmpty()) {
			return null;
		}

		return new Reply(replyMap);
	}

	public void replyModify(int inputedId, String replyBody) {
		SecSql sql = new SecSql();

		sql.append("UPDATE reply ");
		sql.append("SET replyBody = ? ", replyBody);
		sql.append("WHERE replyId = ? ", inputedId);

		MysqlUtil.update(sql);

	}

	public void replyDelete(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM reply ");
		sql.append("WHERE replyId = ? ", inputedId);

		MysqlUtil.delete(sql);

	}

}
