
<%@ page import="com.diss.document.impl.Document" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'document.label', default: 'Document')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-document" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-document" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list document">
			
				<g:if test="${documentInstance?.content}">
				<li class="fieldcontain">
					<span id="content-label" class="property-label"><g:message code="document.content.label" default="Content" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentInstance?.createDate}">
				<li class="fieldcontain">
					<span id="createDate-label" class="property-label"><g:message code="document.createDate.label" default="Create Date" /></span>
					
						<span class="property-value" aria-labelledby="createDate-label"><g:formatDate date="${documentInstance?.createDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentInstance?.lastModifiedDate}">
				<li class="fieldcontain">
					<span id="lastModifiedDate-label" class="property-label"><g:message code="document.lastModifiedDate.label" default="Last Modified Date" /></span>
					
						<span class="property-value" aria-labelledby="lastModifiedDate-label"><g:formatDate date="${documentInstance?.lastModifiedDate}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:documentInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${documentInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
