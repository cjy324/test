package JDBCtest.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import JDBCtest.dto.Article;
import JDBCtest.dto.Board;
import JDBCtest.dto.Recommand;
import JDBCtest.dto.Reply;
import JDBCtest.dto.View;
import JDBCtest.mysqlutil.MysqlUtil;
import JDBCtest.mysqlutil.SecSql;

public class ArticleDao {

	public ArticleDao() {

	}

	public int boardAdd(String name, String code) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO board");
		sql.append("SET name = ?,", name);
		sql.append("code = ?", code);

		return MysqlUtil.insert(sql);
	}

	public Board getBoardById(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM board");
		sql.append("WHERE id = ?", inputedId);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}

		return new Board(boardMap);
	}

	public int add(int boardId, String title, String body, int memberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("body = ?,", body);
		sql.append("boardId = ?,", boardId);
		sql.append("memberId = ?", memberId);

		return MysqlUtil.insert(sql);
	}

	public List<Article> getBoardArticlesForPrint(int boardId) {
		SecSql sql = new SecSql();

		sql.append("SELECT article.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");
		sql.append("WHERE article.boardId = ?", boardId);

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articlesMap : articlesMapList) {
			Article article = new Article(articlesMap);
			
			articles.add(article);
			
		}
	//	Collections.reverse(articles);
		return articles;
	}

	public Article getArticleById(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("SELECT article.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");
		sql.append("WHERE article.id = ?", inputedId);

		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

		if (articleMap.isEmpty()) {
			return null;
		}

		return new Article(articleMap);
	}

	public void articleModify(int id, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("body = ?", body);
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);

	}

	public void articleDelete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);

	}

	public int replyAdd(int articleId, String replyBody, int replyMemberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO reply");
		sql.append("SET regDate = NOW(),");
		sql.append("replyBody = ?,", replyBody);
		sql.append("replyArticleId = ?,", articleId);
		sql.append("replyMemberId = ?", replyMemberId);

		return MysqlUtil.insert(sql);
	}

	public Reply getReply(int inputedId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM reply");
		sql.append("WHERE id = ?", inputedId);

		Map<String, Object> replyMap = MysqlUtil.selectRow(sql);

		if (replyMap.isEmpty()) {
			return null;
		}

		return new Reply(replyMap);
	}

	public void replyModify(int id, String replyBody) {
		SecSql sql = new SecSql();

		sql.append("UPDATE reply");
		sql.append("SET");
		sql.append("replyBody = ?", replyBody);
		sql.append("WHERE id = ?", id);

		MysqlUtil.update(sql);

	}

	public void replyDelete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM reply");
		sql.append("WHERE id = ?", id);

		MysqlUtil.delete(sql);
	}

	public List<Reply> getRepliesForPrint(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT reply.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM reply");
		sql.append("INNER JOIN member");
		sql.append("ON reply.replyMemberId = member.id");
		sql.append("WHERE reply.replyArticleId = ?", articleId);

		List<Reply> replise = new ArrayList<>();
		List<Map<String, Object>> repliseMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> repliseMap : repliseMapList) {
			Reply reply = new Reply(repliseMap);

			replise.add(reply);
		}

		return replise;
	}

	public int recommandAdd(int articleId, int recommandMemberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO recommand");
		sql.append("SET regDate = NOW(),");
		sql.append("recommandArticleId = ?,", articleId);
		sql.append("recommandMemberId = ?", recommandMemberId);

		return MysqlUtil.insert(sql);
	}

	public Recommand getRecommand(int articleId, int recommandMemberId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM recommand");
		sql.append("WHERE recommandArticleId = ?", articleId);
		sql.append("AND");
		sql.append("recommandMemberId = ?", recommandMemberId);

		Map<String, Object> recomandMap = MysqlUtil.selectRow(sql);

		if (recomandMap.isEmpty()) {
			return null;
		}

		return new Recommand(recomandMap);
	}

	public void recomandDelete(int articleId, int recommandMemberId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM recommand");
		sql.append("WHERE recommandArticleId = ?", articleId);
		sql.append("AND");
		sql.append("recommandMemberId = ?", recommandMemberId);

		MysqlUtil.delete(sql);
		
	}

	public List<Recommand> getRecommands(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM recommand");
		sql.append("WHERE recommandArticleId = ?", articleId);

		List<Recommand> recommands = new ArrayList<>();
		List<Map<String, Object>> recommandsMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> recommandsMap : recommandsMapList) {
			Recommand recommand = new Recommand(recommandsMap);

			recommands.add(recommand);
		}

		return recommands;
	}

	public void addView(int articleId) {
		
		SecSql sql = new SecSql();

		sql.append("INSERT INTO view");
		sql.append("SET");
		sql.append("viewArticleId = ?", articleId);

		MysqlUtil.insert(sql);
		
	}

	public List<View> getViews(int articleId) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM view");
		sql.append("WHERE viewArticleId = ?", articleId);

		List<View> views = new ArrayList<>();
		List<Map<String, Object>> viewsMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> viewsMap : viewsMapList) {
			View view = new View(viewsMap);

			views.add(view);
		}

		return views;
	}

	public List<Board> getBoards() {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM board");

		List<Board> boards = new ArrayList<>();
		List<Map<String, Object>> boardsMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> boardsMap : boardsMapList) {
			Board board = new Board(boardsMap);

			boards.add(board);
	}

		return boards;

}

	public Board getBoardByCode(String code) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM board");
		sql.append("WHERE code = ?", code);

		Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

		if (boardMap.isEmpty()) {
			return null;
		}

		return new Board(boardMap);
	}

	public List<Article> articles() {
		SecSql sql = new SecSql();

		sql.append("SELECT article.*, ");
		sql.append("member.name AS extra_memberName");
		sql.append("FROM article");
		sql.append("INNER JOIN member");
		sql.append("ON article.memberId = member.id");

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articlesMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articlesMap : articlesMapList) {
			Article article = new Article(articlesMap);

			articles.add(article);
		}

		return articles;
	}
}
