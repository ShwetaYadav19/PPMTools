package io.agileintelligence.ppmtools.services;

import io.agileintelligence.ppmtools.domain.Project;
import io.agileintelligence.ppmtools.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateproject(Project project){

        //Logic
        return projectRepository.save(project);
    }

}
