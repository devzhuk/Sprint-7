package ru.sber.security.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.security.dto.Student
import ru.sber.security.service.AddressBookService

@Controller
@RequestMapping("/app")
class MvcController @Autowired constructor(private val addressBookService: AddressBookService) {

    @GetMapping("/add")
    fun addForm(@ModelAttribute student: Student, model: Model): String {
        model.addAttribute("entity", student)
        return "add"
    }

    @PostMapping("/add")
    fun add(@ModelAttribute student: Student): String {
        addressBookService.add(student)
        return "redirect:/app/list"
    }

    @GetMapping("/list")
    fun list(@RequestParam(required = false) query: String?, model: Model): String {
        model.addAttribute("entityList", addressBookService.list(query))
        return "list"
    }

    @GetMapping("/{entityId}/view")
    fun view(@PathVariable entityId: Long, model: Model): String {
        model.addAttribute("entity", addressBookService.view(entityId))
        return "view"
    }

    @GetMapping("/{entityId}/edit")
    fun editForm(@PathVariable entityId: Long, model: Model): String {
        model.addAttribute("entity", addressBookService.view(entityId))
        return "edit"
    }

    @PostMapping("/{entityId}/edit")
    fun edit(@PathVariable entityId: Long, @ModelAttribute student: Student): String {
        addressBookService.edit(entityId, student)
        return "redirect:/app/list"
    }

    @GetMapping("/{entityId}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun delete(@PathVariable entityId: Long): String {
        addressBookService.delete(entityId)
        return "redirect:/app/list"
    }
}
