function validar() {
	var nomProd = form.nomProd.value;
	var descProd = form.descProd.value;
	var qntProd = form.qntProd.value;
	var obsProd = form.obsProd.value;

	if (nomProd == "") {
		alert('Preencha o campo Nome Produto');
		form.nomProd.focus();
		return false;
	}

	if (descProd == "") {
		alert('Preencha o campo Descrição');
		form.descProd.focus();
		return false;
	}

	if (qntProd == "") {
		alert('Preencha o campo Quantidade');
		form.qntProd.focus();
		return false;
	}

	if (obsProd == "") {
		alert('Preencha o campo Observação');
		form.obsProd.focus();
		return false;
	}
}

function setDisabled() {
	document.getElementById("idProd").hidden = false;
}

function setAction(action) {
	document.forms[0].acao_form.value = action;
}

function setId(id) {
	document.forms[1].id_table.value = id;
}

function setAction_form2(action) {
	document.forms[1].acao_form.value = action;

}

function setEditar_form2(action) {
	document.forms[1].acao_form.value = action;
}

function confirmarDelete() {
	var result = confirm("Deseja confirmar?");
	if (result == true) {
		return true;
	} else {
		return false;
	}
}