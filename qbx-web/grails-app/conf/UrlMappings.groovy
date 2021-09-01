class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'info', action: 'home')
        "/report/$token" controller: 'testResult', action: 'showReport'
        "/reportPdf/$token" controller: 'testResult', action: 'renderReportPdf'
        "500"(view: '/error')
        "404"(view: '/errors/404')
    }
}
