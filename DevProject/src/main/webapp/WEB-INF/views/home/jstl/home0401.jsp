<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>7장 JSP</h3>
	<p>JSTL태그들의 example:::c:catch</p>
	
	<!--
		EL안에서 발생하는 에러정보는el안에서 처리하므로 var속성에 설정된 변수로 에러정보를 확인할 수 없습니다.
	  -->
	<c:catch var="ex">
		${member.hobbyArray[3] }
	</c:catch>
	
	<p> 
		<c:if test="${ex!=null }">
			${ex}
		</c:if>
	</p>
</body>
</html>