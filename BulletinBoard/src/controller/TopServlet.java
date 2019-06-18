package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserComment;
import beans.UserMessage;
import service.CommentService;
import service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

    	//リクエスト情報取得
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	String category = request.getParameter("category");

        //メッセージ取得
        List<UserMessage> messages = new MessageService().getMessage(startDate, endDate, category);
        List<UserComment> comments = new CommentService().getUserComments();

        //セッションへ表示フラグとメッセージを格納
        request.setAttribute("messages", messages);
        request.setAttribute("comments", comments);

    	//URL取得
        request.getRequestDispatcher("/top.jsp").forward(request, response);

    }
}