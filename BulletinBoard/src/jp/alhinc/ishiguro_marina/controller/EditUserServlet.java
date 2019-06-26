package jp.alhinc.ishiguro_marina.controller;

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

import jp.alhinc.ishiguro_marina.beans.Branch;
import jp.alhinc.ishiguro_marina.beans.Department;
import jp.alhinc.ishiguro_marina.beans.User;
import jp.alhinc.ishiguro_marina.service.BranchService;
import jp.alhinc.ishiguro_marina.service.DepartmentService;
import jp.alhinc.ishiguro_marina.service.UserService;

@WebServlet(urlPatterns = { "/editUser" })
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1l;

	@Override
	protected void doGet (HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		List<Branch> branchList = new ArrayList<Branch>();
		List<Department> departmentList = new ArrayList<Department>();

		//支店・役職テーブルから取得
		branchList = new BranchService().select();
		departmentList = new DepartmentService().select();

		try {
			//編集ユーザー情報取得
			User editUser = new UserService().getUser(Integer.parseInt(request.getParameter("id")));

			//パラメータチェック
			if (editUser == null) {
				messages.add("入力されたパラメータが不正です。");
			}

			//エラー判定
			if (messages.size() != 0) {
				//エラーの場合、エラーメッセージを格納しユーザー管理画面へ遷移
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("userManage");
			} else {
				//正常の場合、ユーザー・支店・部署の情報を格納し編集画面へ遷移
				request.setAttribute("editUser", editUser);
				request.setAttribute("branchList", branchList);
				request.setAttribute("departmentList", departmentList);
				request.getRequestDispatcher("edituser.jsp").forward(request, response);
			}

		} catch (NumberFormatException e) {
			messages.add("入力されたパラメータが不正です。");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("userManage");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		List<Branch> branchList = new ArrayList<Branch>();
		List<Department> departmentList = new ArrayList<Department>();

		//支店・役職テーブルから取得
		branchList = new BranchService().select();
		departmentList = new DepartmentService().select();
		//Beanへ格納
		User editUser = getEditUser(request);
		//リクエストへ格納
		request.setAttribute("branchList", branchList);
		request.setAttribute("departmentList", departmentList);
		request.setAttribute("editUser", editUser);

		if (isValid(request, messages)) {
			//入力チェック正常の場合

			//UserServiceのインスタンス生成
			UserService userService = new UserService();

            //重複チェック
            User duplicateUser = userService.getUser(editUser.getId());
            if(duplicateUser != null && duplicateUser.getId() != editUser.getId()) {

            	//重複している場合、エラーメッセージを格納して編集画面へ遷移
            	messages.add("ログインIDが重複しています");
    			session.setAttribute("errorMessages", messages);
    			request.getRequestDispatcher("edituser.jsp").forward(request, response);
            }else {

				//更新実行
	            userService.update(editUser);
				//ログインユーザ＝編集ユーザだった場合、編集後の情報で上書き
				User loginUser = (User)session.getAttribute("loginUser");
				if(loginUser.getId() == editUser.getId()) {
					session.setAttribute("loginUser", editUser);
				}
				response.sendRedirect("userManage");
            }

		} else {
			//入力チェックエラーの場合
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("edituser.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request)
            throws IOException, ServletException {

		//リクエストから取得
		User editUser = new User();
		int  id = Integer.parseInt(request.getParameter("id"));
		String loginId = request.getParameter("loginId");
		String name = (request.getParameter("name"));
		int branchId = 0;
		int divisionId = 0;
		String password = (request.getParameter("password1"));
		if (!(StringUtils.isBlank(request.getParameter("branchId")))) {
			branchId = Integer.parseInt(request.getParameter("branchId"));
		}
		if (!(StringUtils.isBlank(request.getParameter("departmentId")))) {
			divisionId = Integer.parseInt(request.getParameter("departmentId"));
		}

		//Beanへ格納
		editUser.setId(id);
		editUser.setLoginId(loginId);
		editUser.setName(name);
		editUser.setBranchId(branchId);
		editUser.setDepartmentId(divisionId);
		editUser.setPassword(password);

		return editUser;
	}

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
    	}else if(!loginId.matches("[0-9a-zA-Z]{6,20}")){
    		messages.add("ログインIDは6文字以上20文字以下の半角英数字で入力してください");
    	}
    	//名前
    	if (StringUtils.isBlank(name)) {
    		messages.add("名前を入力してください。");
    	} else if (name.length() > 10 ) {
    		messages.add("名前は10文字以下で入力してください。");
    	}
    	//パスワード
    	if(!StringUtils.isBlank(password1) || !StringUtils.isBlank(password2)) {
	    	if(!password1.matches("[ -~]{6,20}") || !password2.matches("[ -~]{6,20}")){
	    		messages.add("パスワードは6文字以上の記号を含む全ての半角文字で入力してください");
	    	}
	    	if (!(password1.equals(password2))){
	    		messages.add("確認用のパスワードが一致していません");
	    	}
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