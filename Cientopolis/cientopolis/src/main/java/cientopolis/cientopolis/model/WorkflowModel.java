package cientopolis.cientopolis.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nico on 15/4/18.
 */

public class WorkflowModel implements Serializable {

    private ArrayList<StepModel> steps;
    private String name;
    private Integer project;

    public WorkflowModel() {
    }

    public WorkflowModel(String name, Integer project, ArrayList<StepModel> steps) {
        this.name = name;
        this.project = project;
        this.steps = steps;
    }

    public ArrayList<StepModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepModel> steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }
}
