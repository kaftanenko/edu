
<%@ page import="com.diss.document.impl.metainfo.DocumentMetaInfoItemValue" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentMetaInfoItemValue.label', default: 'DocumentMetaInfoItemValue')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-documentMetaInfoItemValue" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-documentMetaInfoItemValue" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="itemValue" title="${message(code: 'documentMetaInfoItemValue.itemValue.label', default: 'Item Value')}" />
					
						<th><g:message code="documentMetaInfoItemValue.templateItemType.label" default="Template Item Type" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${documentMetaInfoItemValueInstanceList}" status="i" var="documentMetaInfoItemValueInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${documentMetaInfoItemValueInstance.id}">${fieldValue(bean: documentMetaInfoItemValueInstance, field: "itemValue")}</g:link></td>
					
						<td>${fieldValue(bean: documentMetaInfoItemValueInstance, field: "templateItemType")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${documentMetaInfoItemValueInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
