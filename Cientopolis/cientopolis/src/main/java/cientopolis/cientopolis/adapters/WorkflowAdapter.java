package cientopolis.cientopolis.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cientopolis.cientopolis.R;
import cientopolis.cientopolis.interfaces.WorkflowClickListener;
import cientopolis.cientopolis.models.ProjectsModel;

/**
 * Created by nicolas.valentini on 2/7/17.
 */

public class WorkflowAdapter extends RecyclerView.Adapter<WorkflowAdapter.ViewHolder> {

    private List<ProjectsModel>  workflow;
    private WorkflowClickListener listener;

    public WorkflowAdapter(List<ProjectsModel> workflows, WorkflowClickListener listener) {
        this.workflow = workflows;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workflow, parent, false);
        return new WorkflowAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.workflowDescription.setText(workflow.get(position).getName());
        holder.workflowName.setText(workflow.get(position).getName());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clicked(workflow.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return workflow.size();
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
