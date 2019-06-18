package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

@WebFilter(urlPatterns = {"/editUser", "/userManage", "/signup"})
public class AuthorityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//セッション取得
    	HttpSession session = ((HttpServletRequest)request).getSession();
    	List<String> messages = new ArrayList<String>();

    	//ログイン情報取得
    	User user = (User)session.getAttribute("loginUser");

    	if(user == null) {
    		//login情報なし
    		messages.add("ログインしてください");
    		session.setAttribute("errorMessages", messages);
    		((HttpServletResponse)response).sendRedirect("login");
    		return;
    	}

    	if(user.getBranchId() == 1 && user.getDepartmentId() == 1) {
    		// 権限ありの場合、サーブレットを実行
    		chain.doFilter(request, response);
    	}else {
    		// 権限なしの場合、ホーム画面へ遷移
    		messages.add("権限がありません");
    		session.setAttribute("errorMessages", messages);
    		((HttpServletResponse)response).sendRedirect("./");
    	}
	}

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void destroy() {
	}

}
