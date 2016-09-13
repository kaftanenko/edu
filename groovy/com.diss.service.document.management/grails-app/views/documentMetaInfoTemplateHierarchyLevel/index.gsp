
<%@ page import="com.diss.document.impl.metainfo.template.hierarchy.DocumentMetaInfoTemplateHierarchyLevel" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentMetaInfoTemplateHierarchyLevel.label', default: 'DocumentMetaInfoTemplateHierarchyLevel')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-documentMetaInfoTemplateHierarchyLevel" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-documentMetaInfoTemplateHierarchyLevel" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="documentMetaInfoTemplateHierarchyLevel.template.label" default="Template" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${documentMetaInfoTemplateHierarchyLevelInstanceList}" status="i" var="documentMetaInfoTemplateHierarchyLevelInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${documentMetaInfoTemplateHierarchyLevelInstance.id}">${fieldValue(bean: documentMetaInfoTemplateHierarchyLevelInstance, field: "template")}</g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${documentMetaInfoTemplateHierarchyLevelInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
