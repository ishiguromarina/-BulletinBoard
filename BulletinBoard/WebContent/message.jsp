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

		<div class="form-area">
	        <form action="message" method="post">
	            件名<input type="text" name="title" value="${message.title}" id="title" /><br />
	            カテゴリー<input type="text" name="category" value="${message.category}" id="category" /><br />
	            本文<textarea name="text" value="${message.text}" cols="100" rows="5" class="tweet-box"></textarea><br />
	            <input type="submit" value="投稿">
	        </form>
		</div>
    </body>
</html>