
<%@ page import="com.diss.document.impl.metainfo.template.hierarchy.DocumentMetaInfoTemplateHierarchyRootLevel" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentMetaInfoTemplateHierarchyRootLevel.label', default: 'DocumentMetaInfoTemplateHierarchyRootLevel')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-documentMetaInfoTemplateHierarchyRootLevel" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-documentMetaInfoTemplateHierarchyRootLevel" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list documentMetaInfoTemplateHierarchyRootLevel">
			
				<g:if test="${documentMetaInfoTemplateHierarchyRootLevelInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="documentMetaInfoTemplateHierarchyRootLevel.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${documentMetaInfoTemplateHierarchyRootLevelInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentMetaInfoTemplateHierarchyRootLevelInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="documentMetaInfoTemplateHierarchyRootLevel.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${documentMetaInfoTemplateHierarchyRootLevelInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentMetaInfoTemplateHierarchyRootLevelInstance?.template}">
				<li class="fieldcontain">
					<span id="template-label" class="property-label"><g:message code="documentMetaInfoTemplateHierarchyRootLevel.template.label" default="Template" /></span>
					
						<span class="property-value" aria-labelledby="template-label"><g:link controller="documentMetaInfoTemplate" action="show" id="${documentMetaInfoTemplateHierarchyRootLevelInstance?.template?.id}">${documentMetaInfoTemplateHierarchyRootLevelInstance?.template?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:documentMetaInfoTemplateHierarchyRootLevelInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${documentMetaInfoTemplateHierarchyRootLevelInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
