package jp.alhinc.ishiguro_marina.filter;

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

import jp.alhinc.ishiguro_marina.beans.User;

@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//セッション取得
    	HttpSession session = ((HttpServletRequest)request).getSession();
    	String servletPath = ((HttpServletRequest)request).getServletPath();
    	List<String> messages = new ArrayList<String>();

    	//ログイン情報取得
    	User user = (User)session.getAttribute("loginUser");

    	if (!(servletPath.contains("/css"))){

	    	if(user == null && !servletPath.equals("/login") && !servletPath.contains("/css")) {
	    		//login情報なし＆URLがログイン以外
	    		messages.add("ログインしてください");
	    		session.setAttribute("errorMessages", messages);
	    		((HttpServletResponse)response).sendRedirect("login");

	    	}else {
	    		chain.doFilter(request, response); // サーブレットを実行
	    	}
    	}else {
    		chain.doFilter(request, response); // サーブレットを実行
    	}
	}

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void destroy() {
	}

}
