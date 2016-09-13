<%@ page import="com.diss.document.impl.DocumentTemplate" %>



<div class="fieldcontain ${hasErrors(bean: documentTemplateInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="documentTemplate.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="128" required="" value="${documentTemplateInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentTemplateInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="documentTemplate.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1024" required="" value="${documentTemplateInstance?.description}"/>

</div>

