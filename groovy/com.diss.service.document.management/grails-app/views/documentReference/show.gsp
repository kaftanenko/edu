
<%@ page import="com.diss.document.impl.DocumentReference" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentReference.label', default: 'DocumentReference')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-documentReference" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-documentReference" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list documentReference">
			
				<g:if test="${documentReferenceInstance?.documentUrl}">
				<li class="fieldcontain">
					<span id="documentUrl-label" class="property-label"><g:message code="documentReference.documentUrl.label" default="Document Url" /></span>
					
						<span class="property-value" aria-labelledby="documentUrl-label"><g:fieldValue bean="${documentReferenceInstance}" field="documentUrl"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentReferenceInstance?.template}">
				<li class="fieldcontain">
					<span id="template-label" class="property-label"><g:message code="documentReference.template.label" default="Template" /></span>
					
						<span class="property-value" aria-labelledby="template-label"><g:link controller="documentTemplate" action="show" id="${documentReferenceInstance?.template?.id}">${documentReferenceInstance?.template?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:documentReferenceInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${documentReferenceInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
