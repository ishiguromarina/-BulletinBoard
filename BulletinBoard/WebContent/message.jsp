<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>新規投稿</title>
	</head>
	<body>
    	<div class="header">
			<a href="logout">ログアウト</a>
			<a href="./">戻る</a>
		</div>
		<h1>NewPost</h1>

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

		<div class="messageForm">
			<form action="message" method="post">
				件名<input type="text" name="title" value="${message.title}" /><br />
				カテゴリー<input type="text" name="category" value="${message.category}" /><br />
				本文<textarea name="text" cols="100" rows="5" class="tweet-box"><c:out value="${message.text}"></c:out></textarea><br />
				<input type="submit" value="投稿">
			</form>
		</div>
    </body>
</html>