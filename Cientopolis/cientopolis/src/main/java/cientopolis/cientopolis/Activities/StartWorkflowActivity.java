package cientopolis.cientopolis.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.cientopolis.samplers.model.InformationStep;
import org.cientopolis.samplers.model.InsertDateStep;
import org.cientopolis.samplers.model.InsertTimeStep;
import org.cientopolis.samplers.model.SelectOneOption;
import org.cientopolis.samplers.model.SelectOneStep;
import org.cientopolis.samplers.model.Workflow;
import org.cientopolis.samplers.ui.SamplersMainActivity;
import org.cientopolis.samplers.ui.take_sample.TakeSampleActivity;

import java.util.ArrayList;

import cientopolis.cientopolis.R;

/**
 * Created by nicov on 26/10/17.
 */

public class StartWorkflowActivity extends SamplersMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, TakeSampleActivity.class);
        intent.putExtra(TakeSampleActivity.EXTRA_WORKFLOW, getWorkflow());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //aca tengo que ver porque es null cuando pongo atras en la vista de los gatitos
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        // InformationStep
        workflow.addStep(new InformationStep(1, "Informacion de prueba para ver que se muestra bien",101));

        // Insert Time
        workflow.addStep(new InsertTimeStep(101, "Seleccione la Hora de la muestra",102));

        // Insert Date
        workflow.addStep(new InsertDateStep(102, "Seleccione la Fecha de la muestra",2));

        // SelectOneStep
        ArrayList<SelectOneOption> optionsToSelect2 = new ArrayList<SelectOneOption>();
        optionsToSelect2.add(new SelectOneOption(1,"SI", 101));
        optionsToSelect2.add(new SelectOneOption(2,"NO", 102));
        workflow.addStep(new SelectOneStep(2, optionsToSelect2, "¿Quiere sacar una foto?"));
        return workflow;
    }
}
