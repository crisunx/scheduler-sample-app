package br.com.crisun.routines.service

import br.com.crisun.routines.exception.NotFoundException
import br.com.crisun.routines.model.Routine
import br.com.crisun.routines.repository.RoutineRepository
import org.springframework.stereotype.Service

@Service
class RoutineService(val repository: RoutineRepository, val routineScheduler: RoutineScheduler) {
    fun findAll() = repository.findAll()

    fun find(id: Long) = repository.find(id) ?: throw NotFoundException("Routine not found")

    fun insert(routine: Routine) {
        val id = repository.insert(routine)
        routineScheduler.addTask(id, routine.interval, routine.command, routine.message)
    }

    fun delete(id: Long) {
        repository.delete(id)
        routineScheduler.removeTask(id)
    }
}