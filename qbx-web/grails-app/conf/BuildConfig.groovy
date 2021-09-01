grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.work.dir = "target"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.plugin.location."qbx-core" = "../qbx-core"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    def gebVersion = "0.9.1"
    def seleniumVersion = "2.40.0"
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        runtime 'mysql:mysql-connector-java:5.1.28'
        compile "com.braintreepayments.gateway:braintree-java:2.25.1"

        test "org.gebish:geb-spock:$gebVersion"
        test "org.gebish:geb-junit4:$gebVersion"

        test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
        test("org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion")
        test("org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion")

        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"

        compile 'org.apache.commons:commons-lang3:3.2.1'
        compile "org.codehaus.groovy.modules.http-builder:http-builder:0.7"
    }

    plugins {
        runtime ":asset-pipeline:1.7.4",
                ":database-migration:1.3.8",
                ":hibernate:$grailsVersion"

        build ":tomcat:$grailsVersion"


        compile ":cache:1.1.1",
                ":mail:1.0.4",
                ":rendering:0.4.4",
                ":spring-mobile:0.5.1",
                ":spring-security-core:1.2.7.3"

        compile ":greenmail:1.3.4"
        test ":geb:$gebVersion"

        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
    }
}
