package io.agileintelligence.ppmtools.services;

import io.agileintelligence.ppmtools.domain.Project;
import io.agileintelligence.ppmtools.exceptions.CustomResponseEntityExceptionHandler;
import io.agileintelligence.ppmtools.exceptions.ProjectIdException;
import io.agileintelligence.ppmtools.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CustomResponseEntityExceptionHandler customResponseEntityExceptionHandler;

    public Project saveOrUpdateproject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null ){
            throw new ProjectIdException("Project ID " + projectId + " does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
       return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Cannot delete Project with ID " + projectId + ". This project does not exist");
        }
        projectRepository.delete(project);
    }
    public Project updateProject(String projectId, Project project){
        Project existingProject = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(existingProject == null){
            throw new ProjectIdException("Project with ID " + projectId + " does not exist");
        }
        existingProject.setDescription(project.getDescription());
        existingProject.setProjectName(project.getProjectName());
        return projectRepository.save(existingProject);
    }
}
