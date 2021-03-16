package study.kstock.stockapi.config

import org.apache.catalina.connector.Connector
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class TomcatConfig {

    @Bean
    fun tomcatEmbeddedServletContainerFactory():  TomcatServletWebServerFactory? {
        return object : TomcatServletWebServerFactory() {
            override fun customizeConnector(connector: Connector) {
                super.customizeConnector(connector)
                connector.parseBodyMethods = "POST,PUT,DELETE"
            }
        }
    }
}