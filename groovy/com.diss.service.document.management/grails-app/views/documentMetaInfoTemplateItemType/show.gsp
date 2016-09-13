
<%@ page import="com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplateItemType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentMetaInfoTemplateItemType.label', default: 'DocumentMetaInfoTemplateItemType')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-documentMetaInfoTemplateItemType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-documentMetaInfoTemplateItemType" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list documentMetaInfoTemplateItemType">
			
				<g:if test="${documentMetaInfoTemplateItemTypeInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="documentMetaInfoTemplateItemType.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${documentMetaInfoTemplateItemTypeInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentMetaInfoTemplateItemTypeInstance?.domainValuesAreExtendable}">
				<li class="fieldcontain">
					<span id="domainValuesAreExtendable-label" class="property-label"><g:message code="documentMetaInfoTemplateItemType.domainValuesAreExtendable.label" default="Domain Values Are Extendable" /></span>
					
						<span class="property-value" aria-labelledby="domainValuesAreExtendable-label"><g:formatBoolean boolean="${documentMetaInfoTemplateItemTypeInstance?.domainValuesAreExtendable}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentMetaInfoTemplateItemTypeInstance?.multiplicity}">
				<li class="fieldcontain">
					<span id="multiplicity-label" class="property-label"><g:message code="documentMetaInfoTemplateItemType.multiplicity.label" default="Multiplicity" /></span>
					
						<span class="property-value" aria-labelledby="multiplicity-label"><g:fieldValue bean="${documentMetaInfoTemplateItemTypeInstance}" field="multiplicity"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentMetaInfoTemplateItemTypeInstance?.sortOrder}">
				<li class="fieldcontain">
					<span id="sortOrder-label" class="property-label"><g:message code="documentMetaInfoTemplateItemType.sortOrder.label" default="Sort Order" /></span>
					
						<span class="property-value" aria-labelledby="sortOrder-label"><g:fieldValue bean="${documentMetaInfoTemplateItemTypeInstance}" field="sortOrder"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentMetaInfoTemplateItemTypeInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="documentMetaInfoTemplateItemType.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${documentMetaInfoTemplateItemTypeInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:documentMetaInfoTemplateItemTypeInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${documentMetaInfoTemplateItemTypeInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
