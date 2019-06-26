package jp.alhinc.ishiguro_marina.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.alhinc.ishiguro_marina.service.CommentService;


@WebServlet(urlPatterns = {"/commentDelete"})
public class CommentDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//コメントIDを取得
		int commentId = Integer.parseInt(request.getParameter("id"));

		//削除実行
		new CommentService().delete(commentId);
		response.sendRedirect("./");

	}
}
