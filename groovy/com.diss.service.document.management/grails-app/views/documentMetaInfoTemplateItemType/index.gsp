
<%@ page import="com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplateItemType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentMetaInfoTemplateItemType.label', default: 'DocumentMetaInfoTemplateItemType')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-documentMetaInfoTemplateItemType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-documentMetaInfoTemplateItemType" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'documentMetaInfoTemplateItemType.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="domainValuesAreExtendable" title="${message(code: 'documentMetaInfoTemplateItemType.domainValuesAreExtendable.label', default: 'Domain Values Are Extendable')}" />
					
						<g:sortableColumn property="multiplicity" title="${message(code: 'documentMetaInfoTemplateItemType.multiplicity.label', default: 'Multiplicity')}" />
					
						<g:sortableColumn property="sortOrder" title="${message(code: 'documentMetaInfoTemplateItemType.sortOrder.label', default: 'Sort Order')}" />
					
						<g:sortableColumn property="type" title="${message(code: 'documentMetaInfoTemplateItemType.type.label', default: 'Type')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${documentMetaInfoTemplateItemTypeInstanceList}" status="i" var="documentMetaInfoTemplateItemTypeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${documentMetaInfoTemplateItemTypeInstance.id}">${fieldValue(bean: documentMetaInfoTemplateItemTypeInstance, field: "name")}</g:link></td>
					
						<td><g:formatBoolean boolean="${documentMetaInfoTemplateItemTypeInstance.domainValuesAreExtendable}" /></td>
					
						<td>${fieldValue(bean: documentMetaInfoTemplateItemTypeInstance, field: "multiplicity")}</td>
					
						<td>${fieldValue(bean: documentMetaInfoTemplateItemTypeInstance, field: "sortOrder")}</td>
					
						<td>${fieldValue(bean: documentMetaInfoTemplateItemTypeInstance, field: "type")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${documentMetaInfoTemplateItemTypeInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
