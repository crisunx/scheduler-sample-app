package br.com.crisun.routines.model

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

class Routine(val id: Long, interval: Long, val command: Command, message: String, var executionHistory: List<ExecutionHistory> = listOf()) {
    val interval = interval
        @Positive get() = field
    val message = message
        @NotEmpty get() = field
}