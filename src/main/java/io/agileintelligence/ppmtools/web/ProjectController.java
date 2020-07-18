package io.agileintelligence.ppmtools.web;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.agileintelligence.ppmtools.domain.Project;
import io.agileintelligence.ppmtools.services.MapValidationErrorService;
import io.agileintelligence.ppmtools.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    private ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

        if (errorMap!=null){
            return errorMap;
        }
        projectService.saveOrUpdateproject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }


    @GetMapping("/{projectId}")
    private ResponseEntity<?> getProjectById(@PathVariable String projectId){
        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(project,HttpStatus.OK);
    }

    @GetMapping("/all")
    private Iterable<Project> getAllProjects(){
        Iterable<Project> projects = projectService.findAllProjects();
        return projects;
    }

    @DeleteMapping("/{projectId}")
    private ResponseEntity<?> deleteProject(@PathVariable String projectId){
       projectService.deleteProjectByIdentifier(projectId);
       return new ResponseEntity<String>("Project with ID " + projectId + " was deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/{projectId}")
    private ResponseEntity<?> updateProject(@PathVariable String projectId, @RequestBody Project project){
        projectService.updateProject(projectId, project);
        return new ResponseEntity<String>("Project with ID " + projectId + " was updated successfully", HttpStatus.OK);
    }

}
