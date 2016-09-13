package com.diss.document.impl.metainfo.template.hierarchy



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentMetaInfoTemplateHierarchyLevelController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentMetaInfoTemplateHierarchyLevel.list(params), model:[documentMetaInfoTemplateHierarchyLevelInstanceCount: DocumentMetaInfoTemplateHierarchyLevel.count()]
    }

    def show(DocumentMetaInfoTemplateHierarchyLevel documentMetaInfoTemplateHierarchyLevelInstance) {
        respond documentMetaInfoTemplateHierarchyLevelInstance
    }

    def create() {
        respond new DocumentMetaInfoTemplateHierarchyLevel(params)
    }

    @Transactional
    def save(DocumentMetaInfoTemplateHierarchyLevel documentMetaInfoTemplateHierarchyLevelInstance) {
        if (documentMetaInfoTemplateHierarchyLevelInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateHierarchyLevelInstance.hasErrors()) {
            respond documentMetaInfoTemplateHierarchyLevelInstance.errors, view:'create'
            return
        }

        documentMetaInfoTemplateHierarchyLevelInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documentMetaInfoTemplateHierarchyLevel.label', default: 'DocumentMetaInfoTemplateHierarchyLevel'), documentMetaInfoTemplateHierarchyLevelInstance.id])
                redirect documentMetaInfoTemplateHierarchyLevelInstance
            }
            '*' { respond documentMetaInfoTemplateHierarchyLevelInstance, [status: CREATED] }
        }
    }

    def edit(DocumentMetaInfoTemplateHierarchyLevel documentMetaInfoTemplateHierarchyLevelInstance) {
        respond documentMetaInfoTemplateHierarchyLevelInstance
    }

    @Transactional
    def update(DocumentMetaInfoTemplateHierarchyLevel documentMetaInfoTemplateHierarchyLevelInstance) {
        if (documentMetaInfoTemplateHierarchyLevelInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoTemplateHierarchyLevelInstance.hasErrors()) {
            respond documentMetaInfoTemplateHierarchyLevelInstance.errors, view:'edit'
            return
        }

        documentMetaInfoTemplateHierarchyLevelInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentMetaInfoTemplateHierarchyLevel.label', default: 'DocumentMetaInfoTemplateHierarchyLevel'), documentMetaInfoTemplateHierarchyLevelInstance.id])
                redirect documentMetaInfoTemplateHierarchyLevelInstance
            }
            '*'{ respond documentMetaInfoTemplateHierarchyLevelInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentMetaInfoTemplateHierarchyLevel documentMetaInfoTemplateHierarchyLevelInstance) {

        if (documentMetaInfoTemplateHierarchyLevelInstance == null) {
            notFound()
            return
        }

        documentMetaInfoTemplateHierarchyLevelInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentMetaInfoTemplateHierarchyLevel.label', default: 'DocumentMetaInfoTemplateHierarchyLevel'), documentMetaInfoTemplateHierarchyLevelInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentMetaInfoTemplateHierarchyLevel.label', default: 'DocumentMetaInfoTemplateHierarchyLevel'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
