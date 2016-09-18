<%@ page import="com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplate" %>



<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="documentMetaInfoTemplate.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="128" required="" value="${documentMetaInfoTemplateInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="documentMetaInfoTemplate.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1024" required="" value="${documentMetaInfoTemplateInstance?.description}"/>

</div>

