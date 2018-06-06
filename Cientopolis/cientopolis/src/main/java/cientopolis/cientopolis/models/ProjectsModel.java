package cientopolis.cientopolis.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectsModel implements Serializable{
    private Integer id;
    private String name;
    private String owner;

    public ProjectsModel(Integer id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
