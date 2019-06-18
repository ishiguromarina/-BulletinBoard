<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type"content="text/html; charset=UTF-8">
<title>ユーザー編集</title>
    <head>
        <title>ユーザー編集　${loginUser.loginId}の設定</title>
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    </head>
	<body>
		<div class="title">
			<span class="pagetitle">ユーザー編集</span>
		</div>

		<div class="header">
			<div class="link">
				<a href="./">ホーム</a>
				<a href="userManage">戻る</a>
				<a href="logout">ログアウト</a>
			</div>
		</div>
		<br><br>

		<div class="pageview">
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

			<div class="main-contents">
				<form action="editUser" method="post">
					<input name="id" value="${editUser.id}" id="id" type="hidden" />

					<label for="loginId">ログインID（半角英数字6文字以上20文字以下）</label>
					<input name="loginId" value="${editUser.loginId}" /><br /><br />

					<label for="name">名前（10文字以下）</label>
					<input type="text" name="name" value="${editUser.name}" id="name" /><br /><br />

					<label for="password1">パスワード（記号を含む全ての半角文字6文字以上20文字以下）</label>
					<input name="password1"  type="password" id="password" /><br /><br />

					<label for="password2">パスワード（確認用のためもう一度入力してください）</label>
					<input name="password2"  type="password" id="password" /><br /><br />

					<c:if test="${editUser.id != loginUser.id}">
						<label for="branchId">支店</label>
						<select name="branchId">
							<option value="" disabled selected>選択してください</option>
							<c:forEach items="${branchList}" var="branch">
								<c:if test="${branch.id ==  editUser.branchId}">
									<option value="${branch.id}" selected><c:out value="${branch.name}"></c:out></option>
								</c:if>
								<c:if test="${branch.id !=  editUser.branchId}">
									<option value="${branch.id}"><c:out value="${branch.name}"></c:out></option>
								</c:if>
							</c:forEach>
						</select><br /><br />

						<label for="departmentId">部署・役職</label>
						<select name="departmentId">
							<option value="" disabled selected>選択してください</option>
							<c:forEach items="${departmentList}" var="department">
								<c:if test="${department.id ==  editUser.departmentId}">
									<option value="${department.id}" selected><c:out value="${department.name}"></c:out></option>
								</c:if>
								<c:if test="${department.id !=  editUser.departmentId}">
									<option value="${department.id}"><c:out value="${department.name}"></c:out></option>
								</c:if>
							</c:forEach>
						</select><br /><br />
					</c:if>

					<input class="button" type="submit" value="登録" /> <br /><br />
				</form>
			<div class="copyright"> Copyright(c)Your Name</div>
		</div>

		</div>
	</body>
</html>