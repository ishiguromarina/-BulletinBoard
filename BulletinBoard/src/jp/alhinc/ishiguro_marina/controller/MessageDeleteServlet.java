package jp.alhinc.ishiguro_marina.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.alhinc.ishiguro_marina.service.MessageService;


@WebServlet(urlPatterns = {"/messageDelete"})
public class MessageDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//投稿IDを取得
		int messageId = Integer.parseInt(request.getParameter("id"));

		//投稿削除実行
		new MessageService().delete(messageId);
		response.sendRedirect("./");

	}
}
