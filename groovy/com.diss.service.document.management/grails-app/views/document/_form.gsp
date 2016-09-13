<%@ page import="com.diss.document.impl.Document" %>



<div class="fieldcontain ${hasErrors(bean: documentInstance, field: 'content', 'error')} required">
	<label for="content">
		<g:message code="document.content.label" default="Content" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="content" name="content" />

</div>

<div class="fieldcontain ${hasErrors(bean: documentInstance, field: 'createDate', 'error')} required">
	<label for="createDate">
		<g:message code="document.createDate.label" default="Create Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="createDate" precision="day"  value="${documentInstance?.createDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: documentInstance, field: 'lastModifiedDate', 'error')} required">
	<label for="lastModifiedDate">
		<g:message code="document.lastModifiedDate.label" default="Last Modified Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="lastModifiedDate" precision="day"  value="${documentInstance?.lastModifiedDate}"  />

</div>

