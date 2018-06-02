package cientopolis.cientopolis.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nico on 15/4/18.
 */

public class StepModel implements Serializable {

    private ArrayList<String> optionsToShow;

    private int id;
    private String sampleText;
    private String stepType;
    private String sampleTest;
    private String inputType;
    private String instructToShow;
    private String imageToOverlay;
    private String title;
    private String textToShow;
    private int nextStepId;

    private Integer maxLength;

    private Boolean optional;

    public StepModel(ArrayList<String> optionsToShow, int id, String sampleText, String stepType, String sampleTest, String inputType, String instructToShow, String imageToOverlay, String title, String textToShow, int nextStepId, Integer maxLength, Boolean optional) {
        this.optionsToShow = optionsToShow;
        this.id = id;
        this.sampleText = sampleText;
        this.stepType = stepType;
        this.sampleTest = sampleTest;
        this.inputType = inputType;
        this.instructToShow = instructToShow;
        this.imageToOverlay = imageToOverlay;
        this.title = title;
        this.textToShow = textToShow;
        this.nextStepId = nextStepId;
        this.maxLength = maxLength;
        this.optional = optional;
    }

    public StepModel(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSampleText() {
        return sampleText;
    }

    public void setSampleText(String sampleText) {
        this.sampleText = sampleText;
    }

    public int getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(int nextStepId) {
        this.nextStepId = nextStepId;
    }

    public ArrayList<String> getOptionsToShow() {
        return optionsToShow;
    }

    public void setOptionsToShow(ArrayList<String> optionsToShow) {
        this.optionsToShow = optionsToShow;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public String getSampleTest() {
        return sampleTest;
    }

    public void setSampleTest(String sampleTest) {
        this.sampleTest = sampleTest;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInstructToShow() {
        return instructToShow;
    }

    public void setInstructToShow(String instructToShow) {
        this.instructToShow = instructToShow;
    }

    public String getImageToOverlay() {
        return imageToOverlay;
    }

    public void setImageToOverlay(String imageToOverlay) {
        this.imageToOverlay = imageToOverlay;
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
