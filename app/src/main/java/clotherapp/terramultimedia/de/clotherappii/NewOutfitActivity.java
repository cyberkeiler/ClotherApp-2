package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outfit);

        occ_list = (ListView) findViewById(R.id.OccListView);

        try {
            new GetOccasionsTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
                Toast.makeText(getParent(), "Konnte keine Bilder abrufen", Toast.LENGTH_SHORT);
            }
        }

        @Override
        protected void onCancelled() {
            //mPicturesTask = null;
            //showProgress(false);
        }
    }
}
