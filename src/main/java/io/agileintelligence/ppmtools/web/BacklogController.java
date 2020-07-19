package io.agileintelligence.ppmtools.web;

import javax.validation.Valid;
import io.agileintelligence.ppmtools.domain.ProjectTask;
import io.agileintelligence.ppmtools.services.MapValidationErrorService;
import io.agileintelligence.ppmtools.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result, @PathVariable String backlog_id) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }
}
