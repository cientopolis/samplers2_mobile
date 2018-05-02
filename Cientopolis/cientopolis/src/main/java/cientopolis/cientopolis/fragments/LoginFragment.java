package cientopolis.cientopolis.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cientopolis.cientopolis.R;
import cientopolis.cientopolis.adapters.WorkflowAdapter;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.model.ResponseDTO;

/**
 * Created by nicov on 11/9/17.
 */

public class LoginFragment extends Fragment implements RequestControllerListener<String> {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        if (savedInstanceState == null ) {
            // if i haven´t an instance i request for one.
            responseOk(1,null);
        } else {
            //
        }
        return view;
    }


    @Override
    public void responseOk(Integer id, ResponseDTO<String> response) {

    }

    @Override
    public void responseError(Integer id, ResponseDTO<String> response) {

    }
}

