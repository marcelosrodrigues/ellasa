<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3>Parab�ns ${franquia.nome}! Voc� � o mais novo membro da fam�lia Ella S/A!</h3>

<div class="well bs-component col-lg-8">
	<fieldset>
		<div class="form-group">
				<div class="col-sm-8">
					Obrigado por sua participa��o e Seja Bem-Vindo!
				</div>
		</div>
		<div class="form-group">
				<div class="col-sm-10">
					Seu c�digo de fraqueado � ${franquia.codigo} e a sua senha � ${franquia.cleanPassword}.</p>
				</div>
		</div>		


	
	</fieldset>
</div>