package cientopolis.cientopolis.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nico on 15/4/18.
 */

public class StepModel implements Serializable {

    private ArrayList<OptionsToShowModel> optionsToShow;

    private Integer id;
    private String sampleText;
    private StepType stepType;
    private String inputType;
    private String title;
    private String textToShow;
    private Integer nextStepId;
    private Integer maxLength;
    private Boolean optional;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(Integer nextStepId) {
        this.nextStepId = nextStepId;
    }

    public String getSampleText() {
        return sampleText;
    }

    public void setSampleText(String sampleText) {
        this.sampleText = sampleText;
    }

    public ArrayList<OptionsToShowModel> getOptionsToShow() {
        return optionsToShow;
    }

    public void setOptionsToShow(ArrayList<OptionsToShowModel> optionsToShow) {
        this.optionsToShow = optionsToShow;
    }

    public StepType getStepType() {
        return stepType;
    }

    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }


    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextToShow() {
        return textToShow;
    }

    public void setTextToShow(String textToShow) {
        this.textToShow = textToShow;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
}
