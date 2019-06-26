<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>掲示板</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript">
			function deleteMessage(){
				return window.confirm('削除してもよろしいですか？');
			}
		</script>
	</head>

    <body>
		<a href="message">新規投稿</a>
		<c:if test="${loginUser.branchId ==1 && loginUser.departmentId ==1}">
			<a href="userManage">ユーザー管理</a>
		</c:if>
		<a href="logout">ログアウト</a>

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

		<div class="profile">
			<h2><c:out value="${loginUser.name}" /></h2>
		</div>

		<div class="search">
			<form action="index.jsp"><br />
				<label for="startDate">開始日時</label>
				<input name="startDate" type="date" value="${startDate}" id="startDate"/> ~
				<label for="endDate">終了日時</label>
				<input name="endDate" type="date" value="${endDate}" id="endDate"/> <br />
				<label for="category">カテゴリー</label>
				<input name="category" value="${category}" id="category"/>
				<input type="submit" value="検索" /><br /><br />
			</form>
		</div><br /><br />

		<c:forEach items="${messages}" var="message">
			<div class="message">
				投稿者：<c:out value="${message.userName}" /><br>
				件名：<c:out value="${message.title}" /><br>
				カテゴリー：<c:out value="${message.category}" /><br>
				本文：
				<c:forEach var="str" items="${fn:split(message.text, '
')}">
					<c:out value="${str}" /><br>
				</c:forEach>
                投稿日：<fmt:formatDate value="${message.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /><br>
				<form action="messageDelete" method="post" onSubmit="return deleteMessage();">
					<input type="hidden" name="id" value="${message.id}"/>
					<input type="submit" value="投稿を削除"/>
				</form>
			</div>

			<c:forEach items="${comments}" var="comment">
				<c:if test="${message.id == comment.messageId}">
					<div class="comment">
						投稿者：<c:out value="${comment.userName}" /><br>
						本文：
						<c:forEach var="str" items="${fn:split(comment.text, '
')}">
						<c:out value="${str}" /><br>
						</c:forEach>
						投稿日：<fmt:formatDate value="${comment.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /><br>
						<form action="commentDelete" method="post" onSubmit="return deleteMessage();">
							<input type="hidden" name="id" value="${comment.id}"/>
							<input type="submit" value="コメントを削除"/>
						</form>
					</div>
				</c:if>
  			</c:forEach>

			<div class="commentForm">
				<form action="comment" method="post">
					<textarea name="text" cols="70" rows="3" class="tweet-box"></textarea>
					<input name="messageId" type="hidden" value="${message.id}"/>
					<input type="submit" value="コメントする"><br /><br />
				</form>
			</div>
		</c:forEach>
	</body>
</html>