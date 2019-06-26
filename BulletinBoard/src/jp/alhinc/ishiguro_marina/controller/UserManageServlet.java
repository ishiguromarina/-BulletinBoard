package jp.alhinc.ishiguro_marina.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.alhinc.ishiguro_marina.beans.UserBranchDepartment;
import jp.alhinc.ishiguro_marina.service.UserService;


@WebServlet(urlPatterns = { "/userManage" })
public class UserManageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

    	//ユーザー情報
    	List<UserBranchDepartment> users = new UserService().getUsers();
    	//リクエストへ格納
    	request.setAttribute("userList", users);
		//ログインページ情報取得
        request.getRequestDispatcher("/management.jsp").forward(request, response);
    }

}