<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script>
	function rand(len) {
		return Math.floor(Math.random() * 10);

	}

	function setRand() {
		document.getElementById('rand').value = rand(50);
	}

	function desabilitar() {
		document.getElementById('rand').readOnly = true;
	}
</script>
</head>

<body>
	<input type="text" id="rand">
	<button type="submit" value="salvar" onclick="desabilitar()">Salvar</button>

</body>

</html>