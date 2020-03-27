<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.Cliente"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="WEB-INF/tags.tld" prefix="n"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cliente</title>
<link href="css/bootstrap.min.css" rel="stylesheet">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<script src="js/controle.js"></script>

<style>
.center {
	position: absolute;
	margin-left: 40%;
	margin-top: 6%;
	width: 60%;
}
</style>

</head>

<body>

	<jsp:include page="index.jsp" />


	<div class="center">

		<!-- Form para carregar os dados no XML -->
		<form name="form" action="cliente" class="form form-horizontal"
			method="post" style="margin-left: 1%; margin-top: 1%">

			<input type="hidden" name="acao_form" value="padrao" /> <input
				type="hidden" name="id_table" />


			<div class="form-group">
				<div class="col-md-3">

					<label>ID</label>
					<n:inputIdCliente />
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-3">
					<label>Nome</label> <input type="text" class="form-control"
						name="nomeClie" value="${cliente.nome}"
						pattern="([^\s][A-z0-9À-ž\s]+{2,50})" id="nomeClie"
						placeholder="Nome Cliente" required>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-3">
					<label>Sobrenome</label> <input type="text" class="form-control"
						id="sobreClie" value="${cliente.sobrenome}" name="sobreClie"
						pattern="([^\s][A-z0-9À-ž\s]+{4,50})"
						placeholder="Sobrenome Cliente" required>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-3">
					<label>CPF</label> <input type="text" class="form-control"
						id="cpfClie" name="cpfClie" value="${cliente.CPF}"
						pattern="\d{3}\.\d{3}\.\d{3}-\d{2}"
						title="Digite o CPF no formato nnn.nnn.nnn-nn"
						placeholder="888.888.888-88" required />
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-3">
					<label>Data Nascimento</label> <input type="date"
						class="form-control" id="dataNascClie" name="dataNascClie"
						value="${cliente.dataNascimento}" placeholder="Data Nascimento"
						required>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-3">
					<label>Localidade</label> <input type="text" class="form-control"
						id="localClie" name="localClie" value="${cliente.localidade}"
						pattern="([^\s][A-z0-9À-ž\s]+)" placeholder="Localidade Cliente"
						required>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-3">
					<button type="submit" value="salvar" onclick="setAction('salvar')"
						class="btn btn-info">Salvar</button>
				</div>
			</div>

		</form>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>

</body>
</html>