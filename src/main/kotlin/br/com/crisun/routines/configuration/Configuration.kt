package br.com.crisun.routines.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "routines", ignoreUnknownFields = false)
class Configuration {
    lateinit var path: String
    lateinit var fileOne: String
    lateinit var fileTwo: String
}