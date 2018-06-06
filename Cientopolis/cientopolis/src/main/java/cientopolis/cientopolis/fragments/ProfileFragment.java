package cientopolis.cientopolis.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cientopolis.cientopolis.R;
import cientopolis.cientopolis.RequestController;
import cientopolis.cientopolis.activities.StartWorkflowActivity;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.models.ProfileModel;
import cientopolis.cientopolis.models.ResponseDTO;
import cientopolis.cientopolis.models.WorkflowDetailModel;

public class ProfileFragment extends Fragment implements RequestControllerListener<ProfileModel> {

    private View view;
    private RequestController requestController;
    private Integer profileId;



    public static ProfileFragment newInstance(Integer profileId) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.profileId=profileId;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requestController = new RequestController(getContext(), this);
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (savedInstanceState == null ) {
            // if i haven´t an instance i request for one.
            Map<String, String> params = getParams();
            requestController.get(new TypeToken<ResponseDTO<WorkflowDetailModel>>() {}.getType(), "profile/"+profileId.toString(), 6, params);
        } else {
            //
        }
        return view;
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        return params;
    }

    @Override
    public void responseOk(Integer id, final ResponseDTO<ProfileModel> response) {

    }

    @Override
    public void responseError(Integer id, ResponseDTO<ProfileModel> response) {

    }
}
