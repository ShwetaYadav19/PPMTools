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
}
