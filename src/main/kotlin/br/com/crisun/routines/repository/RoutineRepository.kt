package br.com.crisun.routines.repository

import br.com.crisun.routines.model.Command
import br.com.crisun.routines.model.ExecutionHistory
import br.com.crisun.routines.model.Routine
import java.sql.Statement
import java.time.LocalDateTime
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class RoutineRepository(var jdbcTemplate: JdbcTemplate) {
    private val SELECT = "SELECT * FROM routine WHERE id = ?"
    private val DELETE = "DELETE FROM routine WHERE id = ?"
    private val INSERT = "INSERT INTO routine (interval, command, message) VALUES (?, ?, ?)"
    private val SELECT_ALL = "SELECT * FROM routine"
    private val INSERT_HISTORY = "INSERT INTO execution_history (routine_id, executed) VALUES (?, ?)"
    private val SELECT_EXECUTION_HISTORY = "SELECT * FROM execution_history WHERE routine_id = ?"

    private val mapper: RowMapper<Routine> = RowMapper { rs, _ ->
        Routine(
            rs.getLong("id"),
            rs.getLong("interval"),
            Command.valueOf(rs.getString("command")),
            rs.getString("message")
        )
    }

    private val historyMapper: RowMapper<ExecutionHistory> = RowMapper { rs, _ ->
        ExecutionHistory(
            rs.getInt("id"),
            rs.getTimestamp("executed").toLocalDateTime()
        )
    }

    fun find(id: Long): Routine? {
        return jdbcTemplate.query(SELECT, mapper, id).getOrNull(0)?.apply {
            executionHistory = jdbcTemplate.query(SELECT_EXECUTION_HISTORY, historyMapper, id)
        }
    }

    fun findAll(): List<Routine> {
        return jdbcTemplate.query(SELECT_ALL, mapper)
    }

    fun delete(id: Long) {
        jdbcTemplate.update(DELETE, id)
    }

    fun insert(routine: Routine): Long {
        val holder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { conn ->
                conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS).apply {
                    setLong(1, routine.interval)
                    setString(2, routine.command.name)
                    setString(3, routine.message)
                }
            },
            holder
        )

        return (holder.keys!!.values.first() as Number).toLong()
    }

    fun insertHistory(id: Long, executed: LocalDateTime) {
        jdbcTemplate.update(INSERT_HISTORY, id, executed)
    }
}