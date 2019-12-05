package br.com.crisun.routines.repository

import br.com.crisun.routines.configuration.Configuration
import java.io.File
import org.springframework.stereotype.Repository

@Repository
class FileWriterRepository(configuration: Configuration) {
    private final val fileOne: File
    private final val fileTwo: File

    init {
        File(configuration.path).mkdirs()
        fileOne = File("${configuration.path}/${configuration.fileOne}")
        fileTwo = File("${configuration.path}/${configuration.fileTwo}")
    }

    fun writeToFileOne(message: String) {
        fileOne.appendText("$message\n")
    }

    fun writeToFileTwo(message: String) {
        fileTwo.appendText("$message\n")
    }
}