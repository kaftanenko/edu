package com.diss.document.impl.metainfo.template.hierarchy



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentMetaInfoTemplateHierarchyRootLevelController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentMetaInfoTemplateHierarchyRootLevel.list(params), model:[documentMetaInfoTemplateHierarchyRootLevelInstanceCount: DocumentMetaInfoTemplateHierarchyRootLevel.count()]
    }

    def show(DocumentMetaInfoTemplateHierarchyRootLevel documentMetaInfoTemplateHierarchyRootLevelInstance) {
        respond documentMetaInfoTemplateHierarchyRootLevelInstance
    }

    def create() {
        respond new DocumentMetaInfoTemplateHierarchyRootLevel(params)
    }

    @Transactional
    def save(DocumentMetaInfoTemplateHierarchyRootLevel documentMetaInfoTemplateHierarchyRootLevelInstance) {
        if (documentMetaInfoTemplateHierarchyRootLevelInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateHierarchyRootLevelInstance.hasErrors()) {
            respond documentMetaInfoTemplateHierarchyRootLevelInstance.errors, view:'create'
            return
        }

        documentMetaInfoTemplateHierarchyRootLevelInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documentMetaInfoTemplateHierarchyRootLevel.label', default: 'DocumentMetaInfoTemplateHierarchyRootLevel'), documentMetaInfoTemplateHierarchyRootLevelInstance.id])
                redirect documentMetaInfoTemplateHierarchyRootLevelInstance
            }
            '*' { respond documentMetaInfoTemplateHierarchyRootLevelInstance, [status: CREATED] }
        }
    }

    def edit(DocumentMetaInfoTemplateHierarchyRootLevel documentMetaInfoTemplateHierarchyRootLevelInstance) {
        respond documentMetaInfoTemplateHierarchyRootLevelInstance
    }

    @Transactional
    def update(DocumentMetaInfoTemplateHierarchyRootLevel documentMetaInfoTemplateHierarchyRootLevelInstance) {
        if (documentMetaInfoTemplateHierarchyRootLevelInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateHierarchyRootLevelInstance.hasErrors()) {
            respond documentMetaInfoTemplateHierarchyRootLevelInstance.errors, view:'edit'
            return
        }

        documentMetaInfoTemplateHierarchyRootLevelInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentMetaInfoTemplateHierarchyRootLevel.label', default: 'DocumentMetaInfoTemplateHierarchyRootLevel'), documentMetaInfoTemplateHierarchyRootLevelInstance.id])
                redirect documentMetaInfoTemplateHierarchyRootLevelInstance
            }
            '*'{ respond documentMetaInfoTemplateHierarchyRootLevelInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentMetaInfoTemplateHierarchyRootLevel documentMetaInfoTemplateHierarchyRootLevelInstance) {

        if (documentMetaInfoTemplateHierarchyRootLevelInstance == null) {
            notFound()
            return
        }

        documentMetaInfoTemplateHierarchyRootLevelInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentMetaInfoTemplateHierarchyRootLevel.label', default: 'DocumentMetaInfoTemplateHierarchyRootLevel'), documentMetaInfoTemplateHierarchyRootLevelInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentMetaInfoTemplateHierarchyRootLevel.label', default: 'DocumentMetaInfoTemplateHierarchyRootLevel'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
