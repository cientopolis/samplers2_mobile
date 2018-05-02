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
import java.util.List;

import cientopolis.cientopolis.R;
import cientopolis.cientopolis.adapters.WorkflowAdapter;
import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.interfaces.WorkflowClickListener;
import cientopolis.cientopolis.model.ResponseDTO;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by nicolas.valentini on 2/7/17.
 */

public class WorkflowsFragment extends Fragment implements RequestControllerListener<String> {

    private View view;
    private RecyclerView recycler;
    private View downloading;
    private View error;
    private TextView textError;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button retry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                //requestController.get(new TypeToken<ResponseDTO<List<Project>>>() {}.getType(), "/projects/myprojects", 6, params);
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //requestController.get(new TypeToken<ResponseDTO<List<Project>>>() {}.getType(), "/projects/myprojects", 6, params);
                downloading.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                recycler.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
            }
        });
        if (savedInstanceState == null ) {
            // if i haven´t an instance i request for one.
            responseOk();
        } else {
            //
        }
        return view;
    }

    public void responseOk(){
        mSwipeRefreshLayout.setRefreshing(false);
        List<String> cientifics = new ArrayList<String>();
        cientifics.add("Nicolas Valentini");
        cientifics.add("Alex Rojas");
        cientifics.add("Diego Torres");
        cientifics.add("Juan Perez");
        cientifics.add("Jose Gomez");
        cientifics.add("Jose Gomez");
        cientifics.add("Jose Gomez");
        cientifics.add("Jose Gomez");
        cientifics.add("Jose Gomez");
        cientifics.add("Jose Gomez");
        if(cientifics.size()>0){
            downloading.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            recycler.setAdapter(new WorkflowAdapter(cientifics, new WorkflowClickListener() {
                @Override
                public void clicked(Integer id) {
                    Fragment fragment = new WorklowDetailfragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_main, fragment);
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
    public void responseOk(Integer id, ResponseDTO<String> response) {

    }

    @Override
    public void responseError(Integer id, ResponseDTO<String> response) {

    }
}
