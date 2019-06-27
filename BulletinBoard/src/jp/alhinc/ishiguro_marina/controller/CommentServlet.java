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

import jp.alhinc.ishiguro_marina.beans.Comment;
import jp.alhinc.ishiguro_marina.beans.User;
import jp.alhinc.ishiguro_marina.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

		//トップページ情報取得
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

    	//セッション取得
        HttpSession session = request.getSession();
        //エラーメッセージ
        List<String> messages = new ArrayList<String>();

        //入力チェック
        if (isValid(request, messages) == true) {

        	//セッションからログイン情報を取得
            User user = (User) session.getAttribute("loginUser");
            //Beanのインスタンス生成
            Comment comment = new Comment();
            //Beanへリクエストとセッションから取得した内容を格納
            comment.setText(request.getParameter("text"));
            comment.setPostId(Integer.parseInt(request.getParameter("messageId")));
            comment.setUserId(user.getId());

            //登録実行
            new CommentService().register(comment);
            //画面表示
            response.sendRedirect("./");

        } else {
        	//入力チェック不可だった場合、エラーメッセージをセッションへ格納
            session.setAttribute("errorMessages", messages);
            response.sendRedirect("./");
        }
    }

    //入力チェック
    private boolean isValid(HttpServletRequest request, List<String> messages) {

    	//リクエスト内容からメッセージを取得
        String text = request.getParameter("text");

        //コメントチェック
        if (StringUtils.isBlank(text)) {
            messages.add("コメントを入力してください");
        }else if (500 < text.length()) {
            messages.add("コメントは500文字以下で入力してください");
        }

        //エラー判定
        if (messages.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}