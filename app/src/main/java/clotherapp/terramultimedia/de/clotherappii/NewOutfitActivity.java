package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.ovgu.cse.se.ClotherAPI.exceptions.OccasionNotFoundException;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAuthenticatedException;
import de.ovgu.cse.se.ClotherAPI.models.Occasion;


public class NewOutfitActivity extends Activity {
    private String[] TestItems;
    //private Boolean mPicturesTask = null;
    private ListView occ_list;
    private LinearLayout[] lay_steps;
    private int lay_step_act = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outfit);

        lay_steps = new LinearLayout[2];
        lay_steps[0] = (LinearLayout) findViewById(R.id.layoutTakePhoto);
        lay_steps[1] = (LinearLayout) findViewById(R.id.layoutOccasion);

        SetStep(0);

        Button btnNext = (Button) findViewById(R.id.btnContinue);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextStep();
                ;
            }
        });

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrevStep();;
            }
        });

        occ_list = (ListView) findViewById(R.id.OccListView);

        //TODO: Dieser GetOccaisonTask verhindert das Bedienen der App - Nicht richtig Asynchron implementiert- FIXEN!!
        try {
            new GetOccasionsTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //Funktionen zur Stepnavigation
    private void SetStep(int step_){

        if(step_ > lay_steps.length) return;
        if(step_ < 0) return;

        lay_step_act = step_;
        for (int i=0; i < lay_steps.length; i++){
            if(i == lay_step_act)
                lay_steps[i].setVisibility(LinearLayout.VISIBLE);
            else
                lay_steps[i].setVisibility(LinearLayout.GONE);
        }
    }

    private void NextStep(){
        SetStep(++lay_step_act);
    }

    private void PrevStep(){
        SetStep(--lay_step_act);
    }

    private void populateListView(List<Occasion> liste) {
        List<String> temp = new ArrayList<String>();

        for (Iterator<Occasion> i = liste.iterator(); i.hasNext(); ) {
            Occasion o = i.next();
            temp.add(o.getName());
        }

        String[] TestItems = new String[temp.size()];
        temp.toArray(TestItems);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.da_item, TestItems);

        ListView occ_list = (ListView) findViewById(R.id.OccListView);
        occ_list.setAdapter(adapter);
    }

    public class GetOccasionsTask extends AsyncTask<Void, Void, Boolean> {
        public List<Occasion> occasionList;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                occasionList = MainMenu.provider.getOccasions();
            } catch (OccasionNotFoundException e) {
                e.printStackTrace();
            } catch (UserNotAuthenticatedException e) {
                e.printStackTrace();
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            //mPicturesTask = null;
            //showProgress(false);

            if (success) {
                populateListView(occasionList);
            } else {
                Toast.makeText(getParent(), "Konnte keine Bilder abrufen", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            //mPicturesTask = null;
            //showProgress(false);
        }
    }
}
