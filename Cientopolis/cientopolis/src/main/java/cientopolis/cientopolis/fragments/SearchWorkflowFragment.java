package cientopolis.cientopolis.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import cientopolis.cientopolis.R;

/**
 * Created by nicolas.valentini on 2/7/17.
 */

public class SearchWorkflowFragment extends Fragment {
    View view;
    Spinner spinnerProject;
    FloatingActionButton searchButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workflow_search, container, false);
        spinnerProject = (Spinner) view.findViewById(R.id.project_type);
        ArrayAdapter<CharSequence> adapterProject = ArrayAdapter.createFromResource(getContext(), R.array.project_types, android.R.layout.simple_spinner_item);
        adapterProject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProject.setAdapter(adapterProject);
        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchButton = (FloatingActionButton) view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }

}
