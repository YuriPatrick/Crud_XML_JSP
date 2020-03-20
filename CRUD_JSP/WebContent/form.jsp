<%@page import="controller.Operacoes"%>
<%@page import="javax.xml.parsers.ParserConfigurationException"%>
<%@page import="model.Produto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="WEB-INF/tags.tld" prefix="m" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Formulario_XML</title>

<link href="css/bootstrap.min.css" rel="stylesheet">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<script src="js/controle.js"></script>

</head>

<body>

	<!-- Form para carregar os dados no XML -->

	<form onSubmit="return validar();" name="form" action="ProdutoServlet"
		class="form form-horizontal" method="post"
		style="margin-left: 1%; margin-top: 1%">

		<input type="hidden" name="acao_form" value="padrao"/> 
		<input type="hidden"name="id_table" />

		<div class="form-group">
			<div class="col-md-3">

				<label>ID</label> 
				<m:inputId />
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-3">
				<label>Nome</label> <input type="text" class="form-control"
					name="nomProd" value="${produto.nome}" pattern="[A-Za-z]{4,30}"
					id="nomProd" placeholder="Nome Produto" required>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-3">
				<label>Descrição</label> <input type="text" class="form-control"
					id="descProd" value="${produto.descricao}" name="descProd"
					pattern="([^\s][A-z0-9À-ž\s]+{4,50})"
					placeholder="Descrição Produto">
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-3">
				<label>Quantidade</label> <input type="number" class="form-control"
					id="qntProd" name="qntProd" value="${produto.qnt}" min="1"
					placeholder="Quantidade Produto">
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-3">
				<label>Observação</label> <input type="text" class="form-control"
					id="obsProd" name="obsProd" value="${produto.obs}"
					pattern="([^\s][A-z0-9À-ž\s]+)" placeholder="Observação Produto">
			</div>
		</div>

		<button type="submit" value="salvar" onclick="setAction('salvar')"
			class="btn btn-info">Salvar</button>

	</form>

	<!-- Form para listagem e edição dos dados no XML -->

	<form onSubmit="return desabilitar();" name="form_table"
		action="ProdutoServlet" class="form form-horizontal" method="post">

		<%
			List<Produto> listaProdutos = (List<Produto>) request.getAttribute("listaproduto");
			request.setAttribute("produto", listaProdutos);
		%>
		<br>

		<div class="w-100 p-3" style="background-color: #eee;">
			<table id="table" class="table table-dark">
				<thead>
					<tr>
						<th scope="col">ID Produto</th>
						<th scope="col">Nome Produto</th>
						<th scope="col">Descrição Produto</th>
						<th scope="col">Quantidade Produto</th>
						<th scope="col">Observação Produto</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="produto" items="${produto}">
						<tr id="idtbl">
							<td>${produto.id}</td>
							<td>${produto.nome}</td>
							<td>${produto.descricao}</td>
							<td>${produto.qnt}</td>
							<td>${produto.obs}</td>

							<td><button type="submit" value="editar" id="editar"
									onclick="setEditar_form2('editar'); setId('${produto.id}')"
									class="btn btn-info">Editar</button></td>

							<td><button type="submit" value="deletar"
									onclick="setAction_form2('deletar'); setId('${produto.id}'); return confirmarDelete();"
									class="btn btn-info">Deletar</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<input type="hidden" name="acao_form" /> <input type="hidden"
			name="id_table" />
	</form>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>

</body>
</html>