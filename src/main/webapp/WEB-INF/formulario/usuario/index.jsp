<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<div class="col-lg-12">


    <c:if test="${not empty sucesso}">
        <div class="alert alert-dismissable alert-success">
            <button type="button" class="close" data-dismiss="alert">�</button>
            <strong>${sucesso}</strong>
        </div>
    </c:if>

    <form action="<c:url value='/usuario/pesquisar.do'/>" role="form">
        <div class="row">
            <div class="col-sm-4">
                <div class="form-group">
                    <input type="text" name="object.nomeCompleto" id="object.nomeCompleto" placeholder="<fmt:message key='usuario.nome' bundle='${labels}'  />" class="form-control" />
                </div>
            </div>
            <div class="col-sm-4">
                <div class="form-group">
                    <input type="text" name="object.email" id="object.email" placeholder="<fmt:message key='usuario.email' bundle='${labels}'  />" class="form-control" />
                </div>
            </div>
            <div class="col-sm-4">
                <button type="submit" value="pesquisar" class="btn btn-info"><fmt:message key="button.pesquisar" bundle="${labels}" /></button>
            </div>
        </div>
    </form>

    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th style="width:5%">#</th>
            <th style="width:25%"><fmt:message key="usuario.nome" bundle="${labels}"/></th>
            <th><fmt:message key="usuario.email" bundle="${labels}"/></th>
            <th><fmt:message key="usuario.celular" bundle="${labels}"/></th>
            <th><fmt:message key="usuario.residencial" bundle="${labels}"/></th>
            <th><fmt:message key="usuario.bloqueado" bundle="${labels}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultlist.list}" var="object">
            <tr>
                <td><a href='<c:url value="/usuario/abrir.do?id=${object.id}" />'>${object.id}</a></td>
                <td>${object.nomeCompleto}</td>
                <td>${object.email}</td>
                <td>(${object.celular.ddd}) ${object.celular.numero}</td>
                <td>(${object.residencial.ddd}) ${object.residencial.numero}</td>
                <td><c:choose>
                     <c:when test="${object.bloqueado}">
                         Bloqueado
                     </c:when>
                     <c:otherwise>
                        Ativo
                     </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="row">
        <div class="col-sm-6">
            <div class="dataTables_info" id="dataTables-example_info" role="alert" aria-live="polite" aria-relevant="all">P�gina ${resultlist.page + 1} de ${resultlist.pageCount}</div>
        </div>
        <c:if test="${resultilist.pageCount gt 1}">
            <div class="col-sm-6" style="top: -30px;text-align:right;">
                <div class="dataTables_paginate paging_simple_numbers">
                    <ul class="pagination">
                        <li class="paginate_button previous <c:if test='${not resultlist.previous}'>disabled</c:if>" aria-controls="dataTables-example" tabindex="0"
                            id="dataTables-example_previous">
                            <c:if test='${resultlist.previous}'>
                                <a href="<c:url value='/usuario/pesquisar.do'/>?object.nome=${param['object.email']}&page=${resultlist.page - 1}">&lt;&lt;</a>
                            </c:if>
                        </li>

                        <c:forEach begin="1" end="${resultlist.pageCount}" var="index">
                            <li class="paginate_button" tabindex="${index - 1}">
                                <a href="<c:url value='/loja/pesquisar.do'/>?object.nome=${param['object.nome']}&page=${index -1}">${index}</a>
                            </li>
                        </c:forEach>

                        <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0"
                            id="dataTables-example_next <c:if test='${not resultlist.next}'>disabled</c:if>">
                            <c:if test='${not resultlist.next}'>
                                <a href="<c:url value='/usuario/pesquisar.do'/>?object.email=${param['object.email']}&page=${resultlist.page + 1}">&gt;&gt;</a>
                            </c:if>
                        </li>
                    </ul>
                </div>
            </div>
        </c:if>
    </div>
    <div class="row" style="margin-top: 10px;">
        <div class="col-sm-6">
            <a href="<c:url value='/usuario/novo.do' />" class="btn btn-info">Adicionar</a>
        </div>
    </div>


</div>