package io.agileintelligence.ppmtools.web;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.agileintelligence.ppmtools.domain.Project;
import io.agileintelligence.ppmtools.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("")
    private ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap= new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String >>(errorMap,HttpStatus.BAD_REQUEST);
        }

        projectService.saveOrUpdateproject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

}
