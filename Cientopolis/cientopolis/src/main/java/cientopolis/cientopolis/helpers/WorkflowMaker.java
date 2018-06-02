package cientopolis.cientopolis.helpers;

import org.cientopolis.samplers.model.BaseStep;
import org.cientopolis.samplers.model.InformationStep;
import org.cientopolis.samplers.model.InsertDateStep;
import org.cientopolis.samplers.model.InsertTextStep;
import org.cientopolis.samplers.model.InsertTimeStep;
import org.cientopolis.samplers.model.LocationStep;
import org.cientopolis.samplers.model.MultipleSelectOption;
import org.cientopolis.samplers.model.PhotoStep;
import org.cientopolis.samplers.model.SelectOneOption;
import org.cientopolis.samplers.model.SelectOneStep;
import org.cientopolis.samplers.model.Workflow;

import java.util.ArrayList;

import cientopolis.cientopolis.models.StepModel;
import cientopolis.cientopolis.models.WorkflowModel;

public class WorkflowMaker {

    public static ArrayList<BaseStep> getSteps(WorkflowModel model) throws IllegalStateException {


       ArrayList<BaseStep> steps = new ArrayList<BaseStep>();
        BaseStep step;
        for (StepModel actualStep : model.getSteps()) {

            switch (actualStep.getStepType()) {
                case "TextStep":
                    step = new InsertTextStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getSampleText(),actualStep.getMaxLength(), InsertTextStep.InputType.TYPE_TEXT,actualStep.getOptional(),actualStep.getNextStepId());
                    break;
                case "InformationStep":
                    step = new InformationStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId() == 0 ? null : actualStep.getNextStepId() );

                    break;
                /*case "PhotoStep":
                    step = new PhotoStep(actualStep.getId(),actualStep.getInstructionsToShow(),actualStep.getImageToOverlay(),actualStep.getNextStepId());

                    break;*/
                case "LocationStep":
                    step = new LocationStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
               /*case "SelectMultipleStep":
                    step = new InsertTextStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getSampleText(),actualStep.getMaxLength(), InsertTextStep.InputType.TYPE_TEXT,actualStep.getOptional(),actualStep.getNextStepId());

                    break;*/
              /*  case "SelectOneStep":

                    step = new SelectOneStep(actualStep.getId(), WorkflowMaker.getOptionToShow(actualStep.getOptionsToShow()),actualStep.getTitle());

                    break;*/
                case "TimeStep":
                    step = new InsertTimeStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
               /* case "RouteStep": //TODO
                    step = new InsertTextStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getSampleText(),actualStep.getMaxLength(), InsertTextStep.InputType.TYPE_TEXT,actualStep.getOptional(),actualStep.getNextStepId());

                    break;
                case "SoundRecordStep": //TODO
                    step = new InsertTextStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getSampleText(),actualStep.getMaxLength(), InsertTextStep.InputType.TYPE_TEXT,actualStep.getOptional(),actualStep.getNextStepId());

                    break;*/
                case "DateStep":
                    step = new InsertDateStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
                default:
                    //TODO definir este caso

                    throw new IllegalStateException("se rompio todo");

                   // step = new InsertTimeStep(actualStep.getId(),"qwdqwdqwdqwd",actualStep.getNextStepId());

                    //break;

            }
            steps.add(step);
        }
        return steps;
    }

   /* public static ArrayList<SelectOneOption> getOptionToShow(ArrayList<OptionsToShowModel> options){
        ArrayList<SelectOneOption> optionsToShow = new ArrayList<SelectOneOption>();
        for (OptionsToShowModel option : options) {
            SelectOneOption optionToShow = new SelectOneOption(option.getId(),option.getTextToShow(),option.getNextStepId());
            optionsToShow.add(optionToShow);

        }
        return optionsToShow;
    }*/
}
