package org.example.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.WorkSpaceCreationDTO;
import org.example.model.entity.WorkSpace;
import org.example.model.exceptions.WorkSpaceNotFoundException;
import org.example.service.WorkSpaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final WorkSpaceService workSpaceService;


    @GetMapping("/")
    public String admin() {
        return "admin";
    }

    @PostMapping("/add-workspace")
    public String addWorkSpace(@ModelAttribute("dto") @Validated WorkSpaceCreationDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "error";
        }
        workSpaceService.save(dto);
        return "redirect:/admin/";
    }

    @PostMapping("/remove-workspace")
    public String removeWorkSpace(@RequestParam("id") @NotNull Long id, Model model) {
        try {
            workSpaceService.removeWorkSpace(id);
        } catch (WorkSpaceNotFoundException e) {
            model.addAttribute("message", "Workspace not found");
            return "error";
        }
        return "redirect:/admin/";
    }

    @GetMapping("/all-workspaces")
    public String viewAllWorkSpaces(Model model) {
        List<WorkSpace> workSpaces = workSpaceService.findAll();
        model.addAttribute("workSpaces", workSpaces);
        return "workspaces";
    }

}
