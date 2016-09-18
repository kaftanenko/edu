
<%@ page import="com.diss.document.impl.DocumentReference" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentReference.label', default: 'DocumentReference')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-documentReference" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-documentReference" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="documentUrl" title="${message(code: 'documentReference.documentUrl.label', default: 'Document Url')}" />
					
						<th><g:message code="documentReference.template.label" default="Template" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${documentReferenceInstanceList}" status="i" var="documentReferenceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${documentReferenceInstance.id}">${fieldValue(bean: documentReferenceInstance, field: "documentUrl")}</g:link></td>
					
						<td>${fieldValue(bean: documentReferenceInstance, field: "template")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${documentReferenceInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
