package cientopolis.cientopolis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;

import org.cientopolis.samplers.framework.Workflow;
import org.cientopolis.samplers.framework.base.BaseStep;
import org.cientopolis.samplers.ui.SamplersMainActivity;
import org.cientopolis.samplers.ui.TakeSampleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cientopolis.cientopolis.R;
import cientopolis.cientopolis.RequestController;
import cientopolis.cientopolis.helpers.WorkflowMaker;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.models.ResponseDTO;
import cientopolis.cientopolis.models.WorkflowModel;

/**
 * Created by nicov on 26/10/17.
 */

public class StartWorkflowActivity extends SamplersMainActivity implements RequestControllerListener<WorkflowModel>   {

    private RequestController requestController;
    private WorkflowModel model;
    private Integer workflowId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra("workflowId")) {
            workflowId = (Integer) getIntent().getSerializableExtra("workflowId");
        }
        requestController = new RequestController(this.getApplicationContext(), this);
        requestController.get(new TypeToken<ResponseDTO<WorkflowModel>>() {}.getType(), "workflow/"+workflowId.toString(), 6, getParams());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected Workflow getWorkflow() {

        Workflow workflow = new Workflow();
        ArrayList<BaseStep> steps = WorkflowMaker.getSteps(model);
        for (BaseStep step: steps) {
            workflow.addStep(step);
        }


        return workflow;
    }

    @Override
    protected Integer getMainHelpResourceId() {
        return null;
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        return params;
    }

    @Override
    public void responseOk(Integer id, ResponseDTO<WorkflowModel> response) {

        try {
            model = response.getData();

            Intent intent = new Intent(this, TakeSampleActivity.class);
            intent.putExtra(TakeSampleActivity.EXTRA_WORKFLOW, getWorkflow());
            startActivity(intent);

        } catch(IllegalStateException e) {

            this.responseError(0, null);
        }


    }

    @Override
    public void responseError(Integer id, ResponseDTO<WorkflowModel> response) {

    }
}
