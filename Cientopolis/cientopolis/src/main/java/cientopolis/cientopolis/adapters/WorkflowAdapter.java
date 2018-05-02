package cientopolis.cientopolis.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cientopolis.cientopolis.R;
import cientopolis.cientopolis.fragments.SearchWorkflowFragment;
import cientopolis.cientopolis.interfaces.WorkflowClickListener;

/**
 * Created by nicolas.valentini on 2/7/17.
 */

public class WorkflowAdapter extends RecyclerView.Adapter<WorkflowAdapter.ViewHolder> {

    private List<String> workflows;
    private WorkflowClickListener listener;

    public WorkflowAdapter(List<String> workflows, WorkflowClickListener listener) {
        this.workflows = workflows;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workflow, parent, false);
        return new WorkflowAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.workflowDescription.setText(workflows.get(position));
        holder.workflowName.setText("Número" + String.valueOf(position));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clicked(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workflows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView workflowDescription;
        TextView workflowName;
        ImageView workflowType;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            workflowDescription = (TextView) itemView.findViewById(R.id.workflow_description);
            workflowName = (TextView) itemView.findViewById(R.id.workflow_name);
            workflowType = (ImageView) itemView.findViewById(R.id.image);
            card = (CardView) itemView.findViewById(R.id.workflowCard);

        }
    }
}
