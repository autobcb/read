package web.Properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "admin")
class AdminProperties {

    lateinit final var username: String

    lateinit final var password: String

    lateinit final var androidid: String

}