
<%@ page import="com.diss.document.impl.metainfo.DocumentMetaInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentMetaInfo.label', default: 'DocumentMetaInfo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-documentMetaInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-documentMetaInfo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="documentMetaInfo.documentReference.label" default="Document Reference" /></th>
					
						<th><g:message code="documentMetaInfo.template.label" default="Template" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${documentMetaInfoInstanceList}" status="i" var="documentMetaInfoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${documentMetaInfoInstance.id}">${fieldValue(bean: documentMetaInfoInstance, field: "documentReference")}</g:link></td>
					
						<td>${fieldValue(bean: documentMetaInfoInstance, field: "template")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${documentMetaInfoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
