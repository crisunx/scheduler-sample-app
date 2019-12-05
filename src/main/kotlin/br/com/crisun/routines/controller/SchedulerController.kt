package br.com.crisun.routines.controller

import br.com.crisun.routines.model.Routine
import br.com.crisun.routines.service.RoutineService
import javax.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/scheduler")
class SchedulerController(val service: RoutineService) {
    @GetMapping("/routines")
    fun routines() = service.findAll()

    @GetMapping("/routine/{id}")
    fun routine(@PathVariable(value = "id", required = true) id: Long) = service.find(id)

    @PostMapping("/routines")
    fun routines(@Valid @RequestBody routine: Routine) {
        service.insert(routine)
    }

    @DeleteMapping("/routine/{id}")
    fun delete(@PathVariable(value = "id", required = true) id: Long) {
        service.delete(id)
    }
}
