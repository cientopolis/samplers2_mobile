package cientopolis.cientopolis.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cientopolis.cientopolis.Activities.StartWorkflowActivity;
import cientopolis.cientopolis.R;
import cientopolis.cientopolis.RequestController;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.model.ResponseDTO;

/**
 * Created by nicov on 11/9/17.
 */

public class WorklowDetailfragment extends Fragment implements RequestControllerListener<String> {

    private View view;
    private Button joinButton;
    private RequestController requestController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workflow_detail, container, false);
        joinButton = (Button) view.findViewById(R.id.join_project);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartWorkflowActivity.class);
                startActivity(intent);
            }
        });
        if (savedInstanceState == null ) {
            // if i haven´t an instance i request for one.
            Map<String, String> params = getParams();
            //requestController.get(new TypeToken<ResponseDTO<List<String>>>() {}.getType(), "/games/mygames", 6, params);
            responseOk(1,null);
        } else {
            //
        }
        return view;
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("social_id", "1");
        params.put("token", "1");
        return params;
    }

    @Override
    public void responseOk(Integer id, ResponseDTO<String> response) {

    }

    @Override
    public void responseError(Integer id, ResponseDTO<String> response) {

    }
}


