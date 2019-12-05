package br.com.crisun.routines.controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchedulerControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `Insert a routine`() {
        mockMvc.perform(
            post("/api/v1/scheduler/routines")
                .contentType(MediaType.APPLICATION_JSON).content("{\"interval\":1,\"command\":\"WRITE_TO_FILE_ONE\",\"message\":\"message 3\"}")
        ).andDo(print()).andExpect(status().is2xxSuccessful)
    }

    @Test
    fun `Delete a routine`() {
        mockMvc.perform(
            delete("/api/v1/scheduler/routine/1")
        ).andDo(print()).andExpect(status().is2xxSuccessful)
    }

    @Test
    fun `Query all routines`() {
        mockMvc.perform(get("/api/v1/scheduler/routines")).andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.[0].id").value(1))
            .andExpect(jsonPath("$.[1].id").value(2))
    }

    @Test
    fun `Query a specific routine`() {
        mockMvc.perform(get("/api/v1/scheduler/routine/3")).andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.executionHistory[0].id").value(3))
    }

    @Test
    fun `Try to remove a routine that does not exist`() {
        mockMvc.perform(
            delete("/api/v1/scheduler/routine/100")
        ).andDo(print()).andExpect(status().is2xxSuccessful)
    }

    @Test
    fun `Query a routine that does not exist`() {
        mockMvc.perform(get("/api/v1/scheduler/routine/100")).andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(content().string("Routine not found"))
    }

    @Test
    fun `Try to insert a routine without a message`() {
        mockMvc.perform(
            post("/api/v1/scheduler/routines")
                .contentType(MediaType.APPLICATION_JSON).content("{\"interval\":1,\"command\":\"WRITE_TO_FILE_ONE\",\"message\":\"\"}")
        ).andDo(print()).andExpect(status().is4xxClientError)
    }

    @Test
    fun `Try to insert a routine with a invalid command`() {
        mockMvc.perform(
            post("/api/v1/scheduler/routines")
                .contentType(MediaType.APPLICATION_JSON).content("{\"interval\":1,\"command\":\"WRITE_TO_FILE_THREE\",\"message\":\"message 3\"}")
        ).andDo(print()).andExpect(status().is4xxClientError)
    }
}