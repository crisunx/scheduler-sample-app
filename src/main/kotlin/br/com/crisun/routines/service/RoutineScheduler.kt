package br.com.crisun.routines.service

import br.com.crisun.routines.configuration.logger
import br.com.crisun.routines.model.Command
import br.com.crisun.routines.repository.FileWriterRepository
import br.com.crisun.routines.repository.RoutineRepository
import java.time.LocalDateTime
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.support.PeriodicTrigger
import org.springframework.stereotype.Component

@Component
class RoutineScheduler(val routineRepository: RoutineRepository, val fileWriterRepository: FileWriterRepository) {
    val tasks: MutableMap<Long, ScheduledFuture<*>?> = mutableMapOf()
    val taskScheduler = ThreadPoolTaskScheduler().apply { initialize() }

    @PostConstruct
    fun start() {
        val routines = routineRepository.findAll()

        routines.forEach {
            addTask(it.id, it.interval, it.command, it.message)
        }
    }

    fun removeTask(id: Long) {
        logger().info("Removing a routine: $id")
        tasks.remove(id)?.cancel(true)
    }

    fun addTask(id: Long, period: Long, command: Command, mensagem: String) {
        logger().info("Routine $id: $command, message: $mensagem started...")

        val task = when (command) {
            Command.WRITE_TO_FILE_ONE -> Runnable {
                fileWriterRepository.writeToFileOne(mensagem)
                routineRepository.insertHistory(id, LocalDateTime.now())
            }
            Command.WRITE_TO_FILE_TWO -> Runnable {
                fileWriterRepository.writeToFileTwo(mensagem)
                routineRepository.insertHistory(id, LocalDateTime.now())
            }
        }

        tasks[id] = taskScheduler.schedule(task, PeriodicTrigger(period, TimeUnit.SECONDS))
    }
}