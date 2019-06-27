package jp.alhinc.ishiguro_marina.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import jp.alhinc.ishiguro_marina.beans.Message;
import jp.alhinc.ishiguro_marina.beans.User;
import jp.alhinc.ishiguro_marina.service.MessageService;

@WebServlet(urlPatterns = { "/message" })
public class MessageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

		//ログインページ情報取得
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

    	//セッション取得
        HttpSession session = request.getSession();
        //エラーメッセージ
        List<String> errorMessages = new ArrayList<String>();
    	//セッションからログイン情報を取得
        User user = (User) session.getAttribute("loginUser");

        //Beanへリクエストとセッションから取得した内容を格納
        Message message = new Message();
        message.setText(request.getParameter("text"));
        message.setTitle(request.getParameter("title"));
        message.setCategory(request.getParameter("category"));
        message.setUserId(user.getId());

        //入力チェック
        if (isValid(message, errorMessages)) {

            //登録実行
            new MessageService().register(message);
            //画面表示
            response.sendRedirect("./");

        } else {
        	//入力チェック不可だった場合、エラーメッセージをセッションへ格納
            session.setAttribute("errorMessages", errorMessages);
            request.setAttribute("message", message);
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }

    //入力チェック
    private boolean isValid(Message messages, List<String> errorMessages) {

    	//リクエスト内容からメッセージを取得
        String text = messages.getText();
        String title = messages.getTitle();
        String category = messages.getCategory();

        if (StringUtils.isBlank(text)) {
            errorMessages.add("本文を入力してください");
        }else if (1000 < text.length()) {
            errorMessages.add("本文は1000文字以下で入力してください");
        }
        if (StringUtils.isBlank(title)) {
            errorMessages.add("件名を入力してください");
        }else if (30 < title.length()) {
            errorMessages.add("件名は30文字以下で入力してください");
        }
        if (StringUtils.isBlank(category)) {
            errorMessages.add("カテゴリーを入力してください");
        }else if(10 < category.length()) {
            errorMessages.add("カテゴリーは10文字以下で入力してください");
        }
        //エラー判定
        if (errorMessages.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}