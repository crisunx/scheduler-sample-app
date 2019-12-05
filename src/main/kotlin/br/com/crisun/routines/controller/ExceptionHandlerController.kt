package br.com.crisun.routines.controller

import br.com.crisun.routines.configuration.logger
import br.com.crisun.routines.exception.NotFoundException
import java.sql.SQLException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerController {
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [DataAccessException::class, SQLException::class])
    fun dataBaseError(e: Exception) {
        logger().error("Data Base error: ", e)
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = [NotFoundException::class])
    fun notFoundError(e: Exception): String {
        logger().info(e.message)
        return "${e.message}"
    }
}