<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>掲示板</title>

	    <a href="message">新規投稿</a>
	    <c:if test="${loginUser.branchId ==1 && loginUser.departmentId ==1}">
	        <a href="userManage">ユーザー管理</a>
	        <a href="signup">登録する</a>
	    </c:if>
	    <a href="logout">ログアウト</a>
    </head>
    <body>

        <c:if test="${ not empty errorMessages }">
           <div class="errorMessages">
               <ul>
                   <c:forEach items="${errorMessages}" var="message">
                       <li><c:out value="${message}" />
                   </c:forEach>
               </ul>
           </div>
           <c:remove var="errorMessages" scope="session"/>
        </c:if>


	    <div class="profile">
	        <h2><c:out value="${loginUser.name}" /></h2>
		</div>

	    <form action="index.jsp"><br />
               <label for="startDate">開始日時</label>
               <input name="startDate" type="date" id="startDate"/> ~
               <label for="endDate">終了日時</label>
               <input name="endDate" type="date" id="endDate"/> <br />
               <label for="category">カテゴリー</label>
               <input name="category" id="category"/>
               <input type="submit" value="検索" /> <br />
           </form>

	    <c:forEach items="${messages}" var="message">
	            <div class="message">
	            	--投稿--------------------------------------<br>
	                ＊投稿者＊<c:out value="${message.userName}" /><br>
	                ＊カテゴリー＊<c:out value="${message.category}" /><br>
	                ＊本文＊<c:out value="${message.text}" /><br>
	                ＊投稿美＊<fmt:formatDate value="${message.createdDate}" pattern="yyyy/MM/dd HH:mm" /><br>
	                -----------------------------------------------
	            </div>

	           	<c:forEach items="${comments}" var="comment">
	           	<c:if test="${message.id == comment.messageId}">
		            <div class="comment">
		            	--コメント---------------<br>
		                投稿者：<c:out value="${comment.userName}" /><br>
		                本文：<c:out value="${comment.text}" /><br>
		                投稿日：<fmt:formatDate value="${message.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /><br>
		        	    -----------------------------
		            </div>
	            </c:if>
	   			</c:forEach>

	            <div class="comment">
					<form action="comment" method="post">
			            <textarea name="text" cols="50" rows="2" class="tweet-box"></textarea>
			            <input name="messageId" type="hidden" value="${message.id}"/> <br />
			            <input type="submit" value="コメントする">
			        </form>
	            </div>

	    </c:forEach>
    </body>
</html>