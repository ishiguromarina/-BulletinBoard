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

import jp.alhinc.ishiguro_marina.beans.User;
import jp.alhinc.ishiguro_marina.service.LoginService;


@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

		//ログインページ情報取得
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

    		//リクエスト内容取得
            String loginId = request.getParameter("loginId");
            String password = request.getParameter("password");

            //値の保持のためリクエストへ格納
            request.setAttribute("loginId", loginId);
            request.setAttribute("password", password);

            //エラーメッセージ格納用のリスト作成
            List<String> messages = new ArrayList<String>();

            //ログイン情報取得
            LoginService loginService = new LoginService();
            User user = loginService.login(loginId, password);

            //セッション取得
            HttpSession session = request.getSession();

            if(isValid(request, messages)) {
            	//入力チェック正常の場合

	            //ログイン情報取得判定
	            if (user == null) {
	            	//ログイン情報が取得できなかった場合
	                messages.add("ログインに失敗しました");
	                session.setAttribute("errorMessages", messages);
	                request.getRequestDispatcher("/login.jsp").forward(request, response);

	            }else if(user.getIsStopped() != 0){
	            	//停止復活フラグが"1"(停止)の場合
	                messages.add("アカウントは停止されています");
	                session.setAttribute("errorMessages", messages);
	                request.getRequestDispatcher("/login.jsp").forward(request, response);

	            }else {
	            	//停止復活フラグが"0"(稼働)の場合
	                session.setAttribute("loginUser", user);
	                response.sendRedirect("./");
	            }
            }else {
            	//入力チェックエラーの場合
                session.setAttribute("errorMessages", messages);
                request.setAttribute("loginId", loginId);
                request.setAttribute("password", password);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
    }

    private boolean isValid(HttpServletRequest request, List<String> messages) {

       	String loginId = request.getParameter("loginId");
    	String password = request.getParameter("password");

    	//ログインID
    	if (StringUtils.isBlank(loginId)) {
    		messages.add("ログインIDを入力してください");
    	}else if(!loginId.matches("[0-9a-zA-Z]{6,20}")){
    		messages.add("ログインIDは6文字以上20文字以下の半角英数字で入力してください");
    	}
    	//パスワード
    	if (StringUtils.isBlank(password)) {
    		messages.add("パスワードを入力してください");
    	}else if(!password.matches("[ -~]{6,20}")){
    		messages.add("パスワードは6文字以上20文字以下の記号を含む全ての半角文字で入力してください");
    	}

    	if(messages.size() == 0) {
    		return true;
    	}else {
    		return false;
    	}
    }
}