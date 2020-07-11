package io.agileintelligence.ppmtools.web;

import io.agileintelligence.ppmtools.domain.Project;
import io.agileintelligence.ppmtools.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    private ResponseEntity<Project> createNewProject(@RequestBody Project project){
        projectService.saveOrUpdateproject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

}
