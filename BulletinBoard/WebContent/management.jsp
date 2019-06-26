<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<meta http-equiv="Content-Type"content="text/html; charset=UTF-8">
	<title>ユーザー管理</title>
	<head>
		<title>ユーザー管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  		<script type="text/javascript">
			function statusChange(){
				return window.confirm('ステータスを変更してもよろしいですか？');
			}
		</script>
    </head>
	<body>
		<div class="header">
			<a href="logout">ログアウト</a>
			<a href="signup">ユーザー新規登録</a>
			<a href="./">戻る</a>
		</div>
		<h1>UserList</h1>
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

		<br />
		<table class="userList">
			<tr>
				<th>ログインID</th>
				<th>名称</th>
				<th>支店</th>
				<th>部署・役職</th>
				<th>ステータス</th>
				<th>ユーザー情報</th>
			</tr>
			<c:forEach items="${userList}" var="userList">
				<div class="id"><input type="hidden" name="id" value="${userList.id}" /></div>
				<tr>
					<td><c:out value="${userList.loginId}"/></td>
					<td><c:out value="${userList.name}"/></td>
					<td><c:out value="${userList.branchName}"/></td>
					<td><c:out value="${userList.departmentName}"/></td>
					<td>
						<c:if test="${userList.id == loginUser.id}">
							<div class="printloggedin">
								変更不可
							</div>
						</c:if>
						<c:if test="${userList.id != loginUser.id}">
							<form action="changeStatus" method="post" onSubmit="return statusChange();">
								<c:if test="${userList.isStopped == 0}">
									<input type="hidden" name="isStopped" value="1"/>
									<input type="submit" value="停止" />
								</c:if>
								<c:if test="${userList.isStopped == 1}">
									<input type="hidden" name="isStopped" value="0"/>
									<input type="submit" value="復活" />
								</c:if>
								<input type="hidden" name="id" value="${userList.id}"/>
							</form>
						</c:if>
					</td>
					<td>
						<form action="editUser">
							<input type="hidden" name="id" value="${userList.id}"/>
							<input class="button" type="submit" value="編集する"/>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table><br />
		<div class="copyright"> Copyright(c)Your Name</div>
	</body>
</html>