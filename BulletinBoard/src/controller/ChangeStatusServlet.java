package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

@WebServlet(urlPatterns = {"/changeStatus"})
public class ChangeStatusServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//リクエストから取得
		int userId = Integer.parseInt(request.getParameter("id"));
		int isStopped = Integer.parseInt(request.getParameter("isStopped"));
		//停止・復活実行
		new UserService().updateStatus(userId, isStopped);

		response.sendRedirect("userManage");
	}
}
