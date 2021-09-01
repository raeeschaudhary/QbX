import se.qbtech.qbx.domain.model.authentication.Person

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
        all: '*/*',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        form: 'application/x-www-form-urlencoded',
        html: ['text/html', 'application/xhtml+xml'],
        js: 'text/javascript',
        json: ['application/json', 'text/json'],
        multipartForm: 'multipart/form-data',
        rss: 'application/rss+xml',
        text: 'text/plain',
        xml: ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = ''

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

def basePath = System.properties['base.dir']
def configPath = basePath + "/web-app"

environments {
    def portForFunctionalTests = 1515
    development {
        grails.logging.jul.usebridge = true

        grails {
            mail {
                host = "smtp.mandrillapp.com"
                port = 587
                username = "daniel.zakrisson@qbtech.com"
                password = "3O5UlviHRNeLso5mkNUaKg"
            }
        }
    }

    production {
        grails.logging.jul.usebridge = false
        basePath = System.properties['catalina.base'] + "/webapps/ROOT"
        configPath = basePath
        greenmail.disabled = true
        grails {
            mail {
                host = "smtp.mandrillapp.com"
                port = 587
                username = "daniel.zakrisson@qbtech.com"
                password = "3O5UlviHRNeLso5mkNUaKg"
            }
        }
    }

    test {
        grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
        grails.serverURL = "http://localhost:$portForFunctionalTests/${appName}"
    }
}

grails.config.locations = [
        "file:${configPath}/WEB-INF/conf/qbx-config.groovy"
]

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    appenders {
        rollingFile name: 'file', maxFileSize: 5120000, file: "${basePath}/logs/qbcheck.log", maxBackupIndex: 10, layout: pattern(conversionPattern: '%d [%t] %-5p (%c) - %m%n'), encoding: 'UTF-8'
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p (%c) - %m%n'), encoding: 'UTF-8'
        'null' name: "stacktrace"
        environments {
            production {
                rollingFile name: 'file', maxFileSize: 5120000, file: "${basePath}/logs/qbcheck.log", maxBackupIndex: 10, layout: pattern(conversionPattern: '%d [%t] %-5p (%c) - %m%n'), encoding: 'UTF-8'
                console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p (%c) - %m%n'), encoding: 'UTF-8'
                'null' name: "stacktrace"
            }
        }
    }

    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    root {
        info 'stdout', 'file'
        additivity = true
    }
}

grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
grails.plugin.databasemigration.changelogLocation = '../qbx-core/grails-app/migrations'

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'se.qbtech.qbx.domain.model.authentication.Person'
grails.plugins.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'se.qbtech.qbx.domain.model.authentication.PersonRole'
grails.plugins.springsecurity.authority.className = 'se.qbtech.qbx.domain.model.authentication.Role'
grails.plugins.springsecurity.requestMap.className = 'se.qbtech.qbx.domain.model.authentication.Requestmap'
grails.plugins.springsecurity.securityConfigType = 'Requestmap'
grails.plugins.springsecurity.password.algorithm = 'bcrypt'
grails.plugins.springsecurity.password.encodeHashAsBase64 = true
grails.plugins.springsecurity.password.bcrypt.logrounds = 12
grails.plugins.springsecurity.useSecurityEventListener = true

grails.plugins.springsecurity.onInteractiveAuthenticationSuccessEvent = { e, appCtx ->
    Person.withTransaction {
        def user = Person.get(appCtx.springSecurityService.currentUser.id)
        if (user) {
            user.lastLoginDate = new Date()
            user.save()
        }
    }
}

qbx.publicAccess.enabled = false
qbx.qbTest.costInUsd = 29
qbx.qbTest.pathToSaveTestJSON = '/data/qbcheck/files/JSON/'
qbx.qbTest.pg.server.url = 'http://pg.qbcheck.com/'

braintree.environment = com.braintreegateway.Environment.SANDBOX
braintree.merchantId = "9zbjmbms3cg8829g"
braintree.publicKey = "gy2sz29zpb5tnsf6"
braintree.privateKey = "27ddd9b500c9fc3b9b7e1ba71bfb30cf"
braintree.encryptionKey = "MIIBCgKCAQEAuJNzClkFUqqVH+ZjeOrOQ3/dHenWXVfHEMFxFCohYMuN7PS3zknFbXVjy9VTSqepPV7l+BrRLmBL6hoOTFRCqOk73lrYTxipIpNiw+Wlf0RFYbreLaCTVlPpCMqcVAaEw9PH1mKw4I6QdxRq8SwlKBFjD0aD2wV4i+vb52TE04U1FuLXUJJKM2JiEkgzQRPkmMDvbFtWfLV8J4P+rDndVkrjsRdxbnSKxX47UiGpt3p5WYSPsuj0PY+z3nHSTdK6KnjzjW5ZC96BWO+2uoz91XrZIlUgJKfMZcWAxJfqbr0B+CBiGo9E/4ccDuHmM9WLv1knowqcXsNFZfFlpy3OFQIDAQAB"

qbx.videoInstrationsLink.child = '//player.vimeo.com/video/91159988'
qbx.videoInstrationsLink.adult = '//player.vimeo.com/video/91159985'
qbx.fps.threshold = 20
qbx.reviewEmail = "info@qbtech.com"