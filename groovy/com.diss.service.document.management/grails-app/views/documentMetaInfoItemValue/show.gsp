
<%@ page import="com.diss.document.impl.metainfo.DocumentMetaInfoItemValue" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentMetaInfoItemValue.label', default: 'DocumentMetaInfoItemValue')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-documentMetaInfoItemValue" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-documentMetaInfoItemValue" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list documentMetaInfoItemValue">
			
				<g:if test="${documentMetaInfoItemValueInstance?.itemValue}">
				<li class="fieldcontain">
					<span id="itemValue-label" class="property-label"><g:message code="documentMetaInfoItemValue.itemValue.label" default="Item Value" /></span>
					
						<span class="property-value" aria-labelledby="itemValue-label"><g:fieldValue bean="${documentMetaInfoItemValueInstance}" field="itemValue"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentMetaInfoItemValueInstance?.templateItemType}">
				<li class="fieldcontain">
					<span id="templateItemType-label" class="property-label"><g:message code="documentMetaInfoItemValue.templateItemType.label" default="Template Item Type" /></span>
					
						<span class="property-value" aria-labelledby="templateItemType-label"><g:link controller="documentMetaInfoTemplateItemType" action="show" id="${documentMetaInfoItemValueInstance?.templateItemType?.id}">${documentMetaInfoItemValueInstance?.templateItemType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:documentMetaInfoItemValueInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${documentMetaInfoItemValueInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
