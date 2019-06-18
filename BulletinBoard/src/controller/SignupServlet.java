package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import beans.Branch;
import beans.Department;
import beans.User;
import service.BranchService;
import service.DepartmentService;
import service.UserService;


@WebServlet(urlPatterns = { "/signup" })
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

		List<Branch> branchList = new ArrayList<Branch>();
		List<Department> departmentList = new ArrayList<Department>();

		branchList = new BranchService().select();
		departmentList = new DepartmentService().select();

		request.setAttribute("branchList", branchList);
		request.setAttribute("departmentList", departmentList);

    	//signup画面を表示
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

    	//エラーメッセージ用リスト初期化
        List<String> messages = new ArrayList<String>();
        //セッション取得
        HttpSession session = request.getSession();
    	//Beanインスタンス生成
        User user = new User();

        //入力チェック
        if (isValid(request, messages) == true) {

            //Bean格納
            user.setLoginId(request.getParameter("loginId"));
            user.setPassword(request.getParameter("password1"));
            user.setName(request.getParameter("name"));
            user.setBranchId(Integer.parseInt(request.getParameter("branchId")));
            user.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
            //DB登録
            new UserService().insert(user);
            //リダイレクト
            response.sendRedirect("./");

        } else {

        	//支店と役職・部署取得
    		List<Branch> branchList = new ArrayList<Branch>();
    		branchList = new BranchService().select();
    		List<Department> departmentList = new ArrayList<Department>();
    		departmentList = new DepartmentService().select();

            //入力内容の値の保持
            user.setLoginId(request.getParameter("loginId"));
            user.setName(request.getParameter("name"));
            if(!StringUtils.isEmpty(request.getParameter("branchId"))) {
                user.setBranchId(Integer.parseInt(request.getParameter("branchId")));
            }
            if(!StringUtils.isEmpty(request.getParameter("departmentId"))) {
                user.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
            }

        	//リクエストへ値の保持内容を格納
			request.setAttribute("branchList", branchList);
			request.setAttribute("departmentList", departmentList);
			request.setAttribute("user", user);

			//セッションへエラーメッセージ格納
            session.setAttribute("errorMessages", messages);
            //signup画面へ表示
			request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

    //入力チェック
    private boolean isValid(HttpServletRequest request, List<String> messages) {

    	String loginId = request.getParameter("loginId");
    	String name = request.getParameter("name");
    	String password1 = request.getParameter("password1");
    	String password2 = request.getParameter("password2");
    	String branchId = request.getParameter("branchId");
    	String departmentId = request.getParameter("departmentId");

    	//ログインID
    	if (StringUtils.isBlank(loginId)) {
    		messages.add("ログインIDを入力してください");
    	}else if(loginId.length() < 6 || loginId.length() > 20 || !loginId.matches("[0-9a-zA-Z]+$")){
    		messages.add("ログインIDは6文字以上20文字以下の半角英数字で入力してください");
    	}
    	//名前
    	if (StringUtils.isBlank(name)) {
    		messages.add("名前を入力してください。");
    	} else if (name.length() > 10 ) {
    		messages.add("名前は10文字以下で入力してください。");
    	}
    	//パスワード
    	if (StringUtils.isBlank(password1)) {
    		messages.add("パスワードを入力してください");
    	}else if(password1.length() < 6 || !loginId.matches("[0-9a-zA-Z]+$")){
    		messages.add("パスワードは6文字以上の記号を含む全ての半角文字で入力してください");
    	}else if (!(password1.equals(password2))){
    		messages.add("確認用のパスワードが一致していません");
    	}
    	//支店
    	if (StringUtils.isBlank(branchId)) {
    		messages.add("支店を選択してください");
    	}
    	//部署・役職
    	if (StringUtils.isBlank(departmentId)) {
    		messages.add("部署・役職を選択してください");
    	}

    	// 支店
    	String mainOffice = "1"; // 本社
    	// 部署
    	String administrator = "1"; // 総務部（本社）
    	String securityManager = "2"; // 情報セキュリティ管理部（本社）
    	String branchManager = "3";// 支店長（支店）
    	String employee = "4"; // 一般社員（本社、支店）

    	// 組み合わせ設定
    	String[] mainOfficeArray = {administrator, securityManager, employee};
    	String[] branchArray = {branchManager, employee};

    	//本社組み合わせチェック
    	if (branchId.equals(mainOffice)) {
    		if (!(Arrays.asList(mainOfficeArray).contains(departmentId))) {
        		messages.add("本社に存在しない部署です。適切な部署を選択してください。");
    		}
    	//支店組み合わせチェック
    	} else {
    		if (!(Arrays.asList(branchArray).contains(departmentId))) {
        		messages.add("支店に存在しない部署です。適切な部署を選択してください。");
    		}
    	}

    	if (messages.size() == 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
}