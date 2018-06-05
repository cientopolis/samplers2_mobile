package cientopolis.cientopolis.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nico on 15/4/18.
 */

public class WorkflowDetailModel implements Serializable {

    private String description;
    private String name;
    private Integer workflow;
    private String createdDate;

    public WorkflowDetailModel() {
    }

    public WorkflowDetailModel(String description, String name, Integer workflow, String createdDate) {
        this.description = description;
        this.name = name;
        this.workflow = workflow;
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Integer workflow) {
        this.workflow = workflow;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
