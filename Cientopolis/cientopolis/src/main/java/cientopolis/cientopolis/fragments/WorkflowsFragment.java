package cientopolis.cientopolis.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cientopolis.cientopolis.R;
import cientopolis.cientopolis.RequestController;
import cientopolis.cientopolis.adapters.WorkflowAdapter;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.interfaces.WorkflowClickListener;
import cientopolis.cientopolis.models.ProjectsModel;
import cientopolis.cientopolis.models.ResponseDTO;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.reflect.TypeToken;

/**
 * Created by nicolas.valentini on 2/7/17.
 */

public class WorkflowsFragment extends Fragment implements RequestControllerListener<List<ProjectsModel>>  {

    private View view;
    private RecyclerView recycler;
    private View downloading;
    private View error;
    private TextView textError;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button retry;
    private RequestController requestController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requestController = new RequestController(getContext(), this);
        view = inflater.inflate(R.layout.fragment_workflows_list, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        downloading = view.findViewById(R.id.cargando);
        downloading.setVisibility(View.VISIBLE);
        Animation ballAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.brand_animation);
        ballAnimation.setRepeatCount(Animation.INFINITE);
        ImageView logo = (ImageView) view.findViewById(R.id.logo);
        logo.startAnimation(ballAnimation);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        error = view.findViewById(R.id.error);
        textError = (TextView) error.findViewById(R.id.text_error);
        retry = (Button) view.findViewById(R.id.retry);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                downloading.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                recycler.setVisibility(View.GONE);
                requestController.get(new TypeToken<ResponseDTO<List<ProjectsModel>>>() {}.getType(), "projects", 6, getParams());
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestController.get(new TypeToken<ResponseDTO<List<ProjectsModel>>>() {}.getType(), "projects", 6, getParams());
                downloading.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                recycler.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
            }
        });
        if (savedInstanceState == null ) {
            // if i haven´t an instance i request for one.
            requestController.get(new TypeToken<ResponseDTO<List<ProjectsModel>>>() {}.getType(), "projects", 6, getParams());
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
    public void responseOk(Integer id, ResponseDTO<List<ProjectsModel>> response) {
        mSwipeRefreshLayout.setRefreshing(false);

        if(response.getData().size() >  0){
            downloading.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            recycler.setAdapter(new WorkflowAdapter(response.getData(), new WorkflowClickListener() {
                @Override
                public void clicked(Integer id) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_main, WorklowDetailfragment.newInstance(id));
                    ft.commit();
                }
            }));
        }
        else {
            retry.setVisibility(View.GONE);
            downloading.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            recycler.setVisibility(View.GONE);
            textError.setText(R.string.empty_workflows);
        }
    }

    @Override
    public void responseError(Integer id, ResponseDTO<List<ProjectsModel>>  response) {

    }
}
