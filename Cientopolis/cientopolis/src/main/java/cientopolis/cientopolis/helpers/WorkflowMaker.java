package cientopolis.cientopolis.helpers;

import org.cientopolis.samplers.framework.base.BaseStep;
import org.cientopolis.samplers.framework.information.InformationStep;
import org.cientopolis.samplers.framework.insertDate.InsertDateStep;
import org.cientopolis.samplers.framework.insertText.InsertTextStep;
import org.cientopolis.samplers.framework.insertTime.InsertTimeStep;
import org.cientopolis.samplers.framework.location.LocationStep;
import org.cientopolis.samplers.framework.multipleSelect.MultipleSelectOption;
import org.cientopolis.samplers.framework.multipleSelect.MultipleSelectStep;
import org.cientopolis.samplers.framework.photo.PhotoStep;
import org.cientopolis.samplers.framework.route.RouteStep;
import org.cientopolis.samplers.framework.selectOne.SelectOneOption;
import org.cientopolis.samplers.framework.selectOne.SelectOneStep;
import org.cientopolis.samplers.framework.soundRecord.SoundRecordStep;


import java.util.ArrayList;

import cientopolis.cientopolis.models.OptionsToShowModel;
import cientopolis.cientopolis.models.StepModel;
import cientopolis.cientopolis.models.WorkflowModel;

public class WorkflowMaker {

    public static ArrayList<BaseStep> getSteps(WorkflowModel model) throws IllegalStateException {
       ArrayList<BaseStep> steps = new ArrayList<BaseStep>();
        BaseStep step = null;
        for (StepModel actualStep : model.getSteps()) {

            switch (actualStep.getStepType()) {
                case TextStep:
                    step = new InsertTextStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getSampleText(),actualStep.getMaxLength(), InsertTextStep.InputType.TYPE_TEXT,actualStep.getOptional(),actualStep.getNextStepId());
                    break;
                case InformationStep:
                    step = new InformationStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
                case PhotoStep:
                    step = new PhotoStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
                case LocationStep:
                    step = new LocationStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
                case SelectMultipleStep:
                    step = new MultipleSelectStep(actualStep.getId(), getMultipleOptionsToShow(actualStep.getOptionsToShow()), actualStep.getTitle(), actualStep.getNextStepId());
                    break;
              case SelectOneStep:
                    step = new SelectOneStep(actualStep.getId(), getOptionToShow(actualStep.getOptionsToShow()), actualStep.getTitle());
                    break;
                case TimeStep:
                    step = new InsertTimeStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
                case RouteStep: //TODO
                    step = new RouteStep(actualStep.getId(), actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
                case SoundRecordStep: //TODO
                    step = new SoundRecordStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;
                case DateStep:
                    step = new InsertDateStep(actualStep.getId(),actualStep.getTextToShow(),actualStep.getNextStepId());

                    break;

            }
            steps.add(step);
        }
        return steps;
    }

    public static ArrayList<SelectOneOption> getOptionToShow(ArrayList<OptionsToShowModel> options){
        ArrayList<SelectOneOption> optionsToShow = new ArrayList<>();
        for (OptionsToShowModel option : options) {
            SelectOneOption optionToShow = new SelectOneOption(option.getId(),option.getTextToShow(),option.getNextStepId());
            optionsToShow.add(optionToShow);
        }
        return optionsToShow;
    }

    public static ArrayList<MultipleSelectOption> getMultipleOptionsToShow(ArrayList<OptionsToShowModel> options){
        ArrayList<MultipleSelectOption> optionsToShow = new ArrayList<>();
        for (OptionsToShowModel option : options) {
            MultipleSelectOption optionToShow = new MultipleSelectOption(option.getId(),option.getTextToShow());
            optionsToShow.add(optionToShow);
        }
        return optionsToShow;
    }
}
