package se.qbtech.qbx.domain.model.authentication

import org.springframework.dao.DataIntegrityViolationException

class PersonController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [personList: Person.list(params), personTotal: Person.count()]
    }

    def create() {
        [person: new Person(params)]
    }

    def save() {
        def person = new Person(params)
        if (!person.save(flush: true)) {
            render(view: "create", model: [person: person])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), person.id])
        redirect(action: "show", id: person.id)
    }

    def show(Long id) {
        def person = Person.get(id)
        if (!person) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), id])
            redirect(action: "list")
            return
        }

        [person: person]
    }

    def edit(Long id) {
        def person = Person.get(id)
        if (!person) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), id])
            redirect(action: "list")
            return
        }

        [person: person]
    }

    def update(Long id, Long version) {
        def person = Person.get(id)
        if (!person) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (person.version > version) {
                person.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'person.label', default: 'Person')] as Object[],
                          "Another user has updated this Person while you were editing")
                render(view: "edit", model: [person: person])
                return
            }
        }

        person.properties = params

        if (!person.save(flush: true)) {
            render(view: "edit", model: [person: person])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), person.id])
        redirect(action: "show", id: person.id)
    }

    def delete(Long id) {
        def person = Person.get(id)
        if (!person) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), id])
            redirect(action: "list")
            return
        }

        try {
            person.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'person.label', default: 'Person'), id])
            redirect(action: "show", id: id)
        }
    }
}
