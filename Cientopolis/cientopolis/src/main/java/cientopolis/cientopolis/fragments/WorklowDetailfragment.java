package cientopolis.cientopolis.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cientopolis.cientopolis.activities.StartWorkflowActivity;
import cientopolis.cientopolis.R;
import cientopolis.cientopolis.RequestController;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.models.ResponseDTO;
import cientopolis.cientopolis.models.WorkflowDetailModel;

/**
 * Created by nicov on 11/9/17.
 */

public class WorklowDetailfragment extends Fragment implements RequestControllerListener<WorkflowDetailModel> {

    private View view;
    private Button joinButton;
    private TextView description ;
    private TextView name ;
    private TextView startDate ;
    private RequestController requestController;
    private Integer projectId;




    public static WorklowDetailfragment newInstance(Integer projectId) {
        WorklowDetailfragment fragment = new WorklowDetailfragment();
        fragment.projectId=projectId;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requestController = new RequestController(getContext(), this);
        view = inflater.inflate(R.layout.fragment_workflow_detail, container, false);
        joinButton = (Button) view.findViewById(R.id.join_project);
        description = (TextView) view.findViewById(R.id.description);
        name = (TextView) view.findViewById(R.id.name);
        startDate = (TextView) view.findViewById(R.id.start_date);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartWorkflowActivity.class);
                startActivity(intent);
            }
        });

        Map<String, String> params = getParams();
        requestController.get(new TypeToken<ResponseDTO<WorkflowDetailModel>>() {}.getType(), "project/"+projectId.toString(), 6, params);
        return view;
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        return params;
    }

    @Override
    public void responseOk(Integer id, final ResponseDTO<WorkflowDetailModel> response) {
        description.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        startDate.setVisibility(View.VISIBLE);
        joinButton.setVisibility(View.VISIBLE);
        description.setText(response.getData().getDescription());
        name.setText(response.getData().getName());
        startDate.setText(response.getData().getCreatedDate());
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartWorkflowActivity.class);
                //agrega id del workflow para buscarlo en StartWorkflowActivity
                intent.putExtra("workflowId", response.getData().getWorkflow());
                startActivity(intent);
            }
        });
    }

    @Override
    public void responseError(Integer id, ResponseDTO<WorkflowDetailModel> response) {
        Toast errorToast = Toast.makeText(getContext().getApplicationContext(), "Ocurrio un error inesperado, por favor intente otra vez.", Toast.LENGTH_SHORT);
        errorToast.show();
    }
}


