<%@ page import="com.diss.document.impl.metainfo.template.hierarchy.DocumentMetaInfoTemplateHierarchyRootLevel" %>



<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateHierarchyRootLevelInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="documentMetaInfoTemplateHierarchyRootLevel.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="128" required="" value="${documentMetaInfoTemplateHierarchyRootLevelInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateHierarchyRootLevelInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="documentMetaInfoTemplateHierarchyRootLevel.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1024" required="" value="${documentMetaInfoTemplateHierarchyRootLevelInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: documentMetaInfoTemplateHierarchyRootLevelInstance, field: 'template', 'error')} required">
	<label for="template">
		<g:message code="documentMetaInfoTemplateHierarchyRootLevel.template.label" default="Template" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="template" name="template.id" from="${com.diss.document.impl.metainfo.template.DocumentMetaInfoTemplate.list()}" optionKey="id" required="" value="${documentMetaInfoTemplateHierarchyRootLevelInstance?.template?.id}" class="many-to-one"/>

</div>

