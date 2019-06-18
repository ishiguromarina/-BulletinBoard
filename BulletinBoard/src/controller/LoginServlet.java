package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import service.LoginService;


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

    		//リクエスト変数格納
            String loginId = request.getParameter("loginId");
            String password = request.getParameter("password");

            //ログイン情報取得
            LoginService loginService = new LoginService();
            User user = loginService.login(loginId, password);

            //セッション取得
            HttpSession session = request.getSession();

            //ログイン情報取得判定
            if (user == null) {
            	//取得できなかった場合
                List<String> messages = new ArrayList<String>();
                messages.add("ログインに失敗しました");
                session.setAttribute("errorMessages", messages);
                response.sendRedirect("login");

            }else if(user.getIsStopped() != 0){
            	//停止復活フラグが"0"(稼働)以外の場合
                List<String> messages = new ArrayList<String>();
                messages.add("アカウントは停止されています");
                session.setAttribute("errorMessages", messages);
                response.sendRedirect("login");

            }else {
            	//停止復活フラグが"0"(稼働)の場合
                session.setAttribute("loginUser", user);
                response.sendRedirect("./");

            }
        }
}