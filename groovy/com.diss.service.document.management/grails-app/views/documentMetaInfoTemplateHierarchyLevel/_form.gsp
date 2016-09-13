<%@ page import="com.diss.document.impl.metainfo.template.hierarchy.DocumentMetaInfoTemplateHierarchyLevel" %>



<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateHierarchyLevelInstance, field: 'template', 'error')} required">
	<label for="template">
		<g:message code="documentMetaInfoTemplateHierarchyLevel.template.label" default="Template" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="template" name="template.id" from="${com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplate.list()}" optionKey="id" required="" value="${documentMetaInfoTemplateHierarchyLevelInstance?.template?.id}" class="many-to-one"/>

</div>

