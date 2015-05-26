package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.ovgu.cse.se.ClotherAPI.exceptions.OccasionNotAddedException;
import de.ovgu.cse.se.ClotherAPI.exceptions.OccasionNotFoundException;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAuthenticatedException;
import de.ovgu.cse.se.ClotherAPI.models.Occasion;


public class NewOutfit extends Activity {
    private String[] TestItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_outfit);

        Occasion occas = new Occasion();
        occas.setName("Test");

        try {
            MainMenu.provider.addOccasion(occas);
        } catch (OccasionNotAddedException e) {
            e.printStackTrace();
        } catch (UserNotAuthenticatedException e) {
            e.printStackTrace();
        }


        ListView occ_list = (ListView) findViewById(R.id.OccListView);

        try {
            List<Occasion> all_occ = MainMenu.provider.getOccasions();

            List<String> temp = new ArrayList<String>();

            for (Iterator<Occasion> i = all_occ.iterator(); i.hasNext(); ) {
                Occasion o = i.next();
                temp.add(o.getName());
            }

            TestItems = new String[temp.size()];
            temp.toArray(TestItems);

        } catch (OccasionNotFoundException e) {
            e.printStackTrace();
            TestItems = new String[]{"Occasion nicht gefunden!"};
        } catch (UserNotAuthenticatedException e) {
            e.printStackTrace();
            TestItems = new String[]{"Du hast leider nicht genug Rechte!"};
        }
        populateListView();
    }

    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.da_item, TestItems);

        ListView occ_list = (ListView) findViewById(R.id.OccListView);
        occ_list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_outfit, menu);
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
}
