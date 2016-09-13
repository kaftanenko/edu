
<%@ page import="com.diss.document.impl.metainfo.DocumentMetaInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentMetaInfo.label', default: 'DocumentMetaInfo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-documentMetaInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-documentMetaInfo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list documentMetaInfo">
			
				<g:if test="${documentMetaInfoInstance?.documentReference}">
				<li class="fieldcontain">
					<span id="documentReference-label" class="property-label"><g:message code="documentMetaInfo.documentReference.label" default="Document Reference" /></span>
					
						<span class="property-value" aria-labelledby="documentReference-label"><g:link controller="documentReference" action="show" id="${documentMetaInfoInstance?.documentReference?.id}">${documentMetaInfoInstance?.documentReference?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${documentMetaInfoInstance?.template}">
				<li class="fieldcontain">
					<span id="template-label" class="property-label"><g:message code="documentMetaInfo.template.label" default="Template" /></span>
					
						<span class="property-value" aria-labelledby="template-label"><g:link controller="documentMetaInfoTemplate" action="show" id="${documentMetaInfoInstance?.template?.id}">${documentMetaInfoInstance?.template?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:documentMetaInfoInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${documentMetaInfoInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
