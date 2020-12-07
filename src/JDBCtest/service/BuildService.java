package JDBCtest.service;

import java.io.File;
import java.util.List;

import JDBCtest.container.Container;
import JDBCtest.dto.Article;
import JDBCtest.dto.Board;
import JDBCtest.util.Util;

public class BuildService {

	ArticleSerivice articleSerivice;
	MemberService memberService;

	public BuildService() {
		articleSerivice = Container.articleSerivice;
		memberService = Container.memberService;
	}

	public void buildSite() {
		// site라는 폴더가 없을 경우 폴더 생성
		File site = new File("site");
		File articleFolder = new File("site/article");

		if (site.exists() == false) {
			site.mkdir();
		}
		if (articleFolder.exists() == false) {
			articleFolder.mkdir();
		}

		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		// 각 게시판 별 게시물리스트 페이지 생성
		List<Board> boards = articleSerivice.getBoards();

		for (Board board : boards) {
			String fileName = board.code + "-list-1.html";

			String html = "";
			int boardId = Container.session.selectedBoardId;

			String template = Util.getFileContents("site_template/article/list.html");
			
			List<Article> articles = articleSerivice.getBoardArticlesForPrint(boardId);

			for (Article article : articles) {
				html += "<tr>";
				html += "<td>" + article.id + "</td>";
				html += "<td>" + article.regDate + "</td>";
				html += "<td><a href=\"" + article.id + ".html\">" + article.title + "</a></td>";
				html += "</tr>";
			}

			html = template.replace("${TR}", html);
			
			html = head + html + foot;

			Util.writeFileContents("site/article/" + fileName, html);
		}

		// 게시물 별 파일 생성
		List<Article> articles = articleSerivice.getArticlesForPrint();

		for (Article article : articles) {
			Board board = articleSerivice.getBoardById(article.boardId);

	//		String fileName = "article_" + article.id + ".html";
			String html = "<meta charset=\"UTF-8\">";
			html += "<div>번호 : " + article.id + "</div>";
			html += "<div>날짜 : " + article.regDate + "</div>";
			html += "<div>작성자 : " + article.extra_memberName + "</div>";
			html += "<div>제목 : " + article.title + "</div>";
			html += "<div>내용 : " + article.body + "</div>";
			html += "<div>게시판 : " + board.name + " 게시판" + "</div>";

			if (article.id > 1) {
				html += "<div><a href=\"article_" + (article.id - 1) + ".html\">이전글</a></div>";
			}
			if (article.id != (articles.size())) {
				html += "<div><a href=\"article_" + (article.id + 1) + ".html\">다음글</a></div>";
			}

			html = head + html + foot;

			Util.writeFileContents("site/article/" + article.id + ".html", html);
		}
	}

}