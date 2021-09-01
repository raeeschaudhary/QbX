dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
    username = "root"
    password = "root"
    properties {
        /** The maximum number of active connections that can be allocated from
         * this pool at the same time, or zero for no limit.
         * Make it '-1' to prevent performance problems with idle connections
         */
        maxActive = -1
        /**
         * The maximum number of active connections that can remain idle in the
         * pool, without extra ones being released, or zero for no limit.
         */
        maxIdle = 8
        /**
         * The minimum number of active connections that can remain idle in the
         * pool, without extra ones being created, or 0 to create none.
         */
        minIdle = 0
        /**
         * The maximum number of milliseconds that the pool will wait (when there
         * are no available connections) for a connection to be returned before
         * throwing an exception, or -1 to wait indefinitely.
         */
        maxWait = 180000

        minEvictableIdleTimeMillis = 1000 * 60 * 15
        timeBetweenEvictionRunsMillis = 1000 * 60 * 15
        numTestsPerEvictionRun = 3

        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = false

        validationQuery = "select 1"
    }
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            url = "jdbc:mysql://localhost:3306/qbx?zeroDateTimeBehavior=convertToNull"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            dialect = "org.hibernate.dialect.H2Dialect"
            driverClassName = "org.h2.Driver"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            username = "sa"
            password = ""
        }
    }
    production {
        dataSource {
            url = "jdbc:mysql://localhost:3306/qbcheck?zeroDateTimeBehavior=convertToNull"
            username = 'qbcheck'
            password = 'Qbx14Chk'
        }
    }
}
