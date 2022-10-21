<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글삭제</title>
</head>
<body>

<form method=post action="boarddelete.do">
<input type="hidden" name="no" value="${param.no}"/>
<input type="hidden" name="page" value="${param.page}"/>
<table border=1 width=400 align=center>
	<caption>글삭제</caption>
	<tr><th>비밀번호</th>
		<td><input type=password name="passwd" required="required"></td>
	</tr>
	<tr><td colspan=2 align=center>
			<input type=submit value="글삭제">
			<input type=reset value="취소">
		</td>
	</tr>
</table>
</form>

</body>
</html>