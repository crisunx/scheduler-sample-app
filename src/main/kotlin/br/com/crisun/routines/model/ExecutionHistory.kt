package br.com.crisun.routines.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ExecutionHistory(val id: Int, @JsonFormat(pattern = "yyyy-dd-MM HH:mm:ss") val executedAt: LocalDateTime)