<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ユーザー新規登録</title>
	</head>
	<body>
		<div class="header">
			<a href="./">ホーム</a>
			<a href="userManage">戻る</a>
			<a href="logout">ログアウト</a>
		</div>
		<h1>Signup</h1>
		<c:if test="${not empty errorMessages}">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>
		<form action="signup" method="post">
			<br />
			<label for="name">名前</label>
			<input name="name" value="${user.name}" id="name" /> <br />
			<label for="loginId">ログインID</label>
			<input name="loginId" value="${user.name}" id="loginId" /> <br />
			<label for="password1">パスワード</label>
			<input name="password1" type="password" id="password1" /> <br />
			<label for="password2">確認用パスワード</label>
			<input name="password2" type="password" id="password2" /> <br />
			<label for="branchId">支店</label>
			<select name="branchId">
				<c:forEach items="${branchList}" var="branch">
					<c:if test="${branch.id == user.branchId}">
						<option value="${branch.id}" selected><c:out value="${branch.name}"></c:out></option>
					</c:if>
					<c:if test="${branch.id != user.branchId}">
						<option value="${branch.id}" ><c:out value="${branch.name}"></c:out></option>
					</c:if>
				</c:forEach>
			</select><br /><br />

			<select name="departmentId">
				<c:forEach items="${departmentList}" var="department">
					<c:if test="${department.id == user.departmentId}">
						<option value="${department.id}" selected><c:out value="${department.name}"></c:out></option>
					</c:if>
					<c:if test="${department.id != user.departmentId}">
						<option value="${department.id}" ><c:out value="${department.name}"></c:out></option>
					</c:if>
				</c:forEach>
			</select><br /><br />

			<input type="submit" value="登録" /> <br />
		</form>
		<div class="copyright">Copyright(c)Your Name</div>
	</body>
</html>