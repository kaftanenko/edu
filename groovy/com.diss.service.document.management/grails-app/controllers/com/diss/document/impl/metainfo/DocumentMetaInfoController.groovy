package com.diss.document.impl.metainfo



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentMetaInfoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentMetaInfo.list(params), model:[documentMetaInfoInstanceCount: DocumentMetaInfo.count()]
    }

    def show(DocumentMetaInfo documentMetaInfoInstance) {
        respond documentMetaInfoInstance
    }

    def create() {
        respond new DocumentMetaInfo(params)
    }

    @Transactional
    def save(DocumentMetaInfo documentMetaInfoInstance) {
        if (documentMetaInfoInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoInstance.hasErrors()) {
            respond documentMetaInfoInstance.errors, view:'create'
            return
        }

        documentMetaInfoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documentMetaInfo.label', default: 'DocumentMetaInfo'), documentMetaInfoInstance.id])
                redirect documentMetaInfoInstance
            }
            '*' { respond documentMetaInfoInstance, [status: CREATED] }
        }
    }

    def edit(DocumentMetaInfo documentMetaInfoInstance) {
        respond documentMetaInfoInstance
    }

    @Transactional
    def update(DocumentMetaInfo documentMetaInfoInstance) {
        if (documentMetaInfoInstance == null) {
            notFound()
            return
        }

        if (documentMetaInfoInstance.hasErrors()) {
            respond documentMetaInfoInstance.errors, view:'edit'
            return
        }

        documentMetaInfoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentMetaInfo.label', default: 'DocumentMetaInfo'), documentMetaInfoInstance.id])
                redirect documentMetaInfoInstance
            }
            '*'{ respond documentMetaInfoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentMetaInfo documentMetaInfoInstance) {

        if (documentMetaInfoInstance == null) {
            notFound()
            return
        }

        documentMetaInfoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentMetaInfo.label', default: 'DocumentMetaInfo'), documentMetaInfoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentMetaInfo.label', default: 'DocumentMetaInfo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
