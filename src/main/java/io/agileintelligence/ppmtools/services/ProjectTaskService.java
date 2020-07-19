package io.agileintelligence.ppmtools.services;

import java.util.List;
import io.agileintelligence.ppmtools.domain.Backlog;
import io.agileintelligence.ppmtools.domain.Project;
import io.agileintelligence.ppmtools.domain.ProjectTask;
import io.agileintelligence.ppmtools.exceptions.ProjectNotFoundException;
import io.agileintelligence.ppmtools.repository.BacklogRepository;
import io.agileintelligence.ppmtools.repository.ProjectRepository;
import io.agileintelligence.ppmtools.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        //Exceptions: Project not found
        try {
            //PTs to be added to a specific project, project != null
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            //set the bl to the pt
            projectTask.setBacklog(backlog);
            //we want our project sequence to be like this IDPRO-1 IDPRO-2  ......100 101
            Integer BacklogSequence = backlog.getPTSequence();
            // Update the BL SEQUENC
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);
            //Add sequence to ProjectTask
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority is null
            if (projectTask == null || projectTask.getPriority() == null
                    || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            //INITIAL status when status is null
            if (projectTask != null && (projectTask.getStatus() == "" || projectTask.getStatus() == null)) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception ex) {
            throw new ProjectNotFoundException("Project not Found");
        }
    }

    public List<ProjectTask> findBacklogById(String backlog_id) {
        Project project = projectRepository.findByProjectIdentifier(backlog_id);

        if (project == null) {
            throw new ProjectNotFoundException("Project with ID: " + backlog_id + " does not exist");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null){
            throw new ProjectNotFoundException("Project with ID: " + backlog_id + " does not exist");
        }
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
        }
        //make sure we are searching on the right backlog

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in project : '" + backlog_id+"'");
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deleteByProjectSequence( String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
        projectTaskRepository.delete(projectTask);
    }
}
