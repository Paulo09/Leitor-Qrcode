<%@ page import="cadastro.Objeto" %>



<div class="fieldcontain ${hasErrors(bean: objetoInstance, field: 'matricula', 'error')} ">
	<label for="matricula">
		<g:message code="objeto.matricula.label" default="Matricula" />
		
	</label>
	<g:textField name="matricula" value="${objetoInstance?.matricula}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: objetoInstance, field: 'nome', 'error')} ">
	<label for="nome">
		<g:message code="objeto.nome.label" default="Nome" />
		
	</label>
	<g:textField name="nome" value="${objetoInstance?.nome}"/>
</div>

