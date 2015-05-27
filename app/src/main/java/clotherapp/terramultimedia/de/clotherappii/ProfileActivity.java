package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.ovgu.cse.se.ClotherAPI.models.Gender;


public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        TextView txticon = (TextView) findViewById(R.id.txtIcon);
        txticon.setTypeface(MainMenu.fontawesome);

        if (MainMenu.user.getGender() == Gender.MALE) {
            //TODO: Setze Profil Icon bei männlichen Usern auf blau!
            //txticon.setTextColor();
        }

        TextView txtName = (TextView) findViewById(R.id.txtname);
        txtName.setText(MainMenu.user.getFirstname() + " " + MainMenu.user.getLastname());

        TextView txtMail = (TextView) findViewById(R.id.txtMail);
        txtMail.setText(MainMenu.user.getEmail());

        //TODO: Datum richtig formatieren
        TextView txtGB = (TextView) findViewById(R.id.txtGeburtstag);
        txtGB.setText(MainMenu.user.getBirthdate().toString());

        TextView txtReg = (TextView) findViewById(R.id.txtReg);
        txtReg.setText(MainMenu.user.getCreationTime().toString());

        //TODO: Fange fehler vom Creditscore ab
        //TextView txtScore = (TextView) findViewById(R.id.textscore);
        //txtScore.setText(MainMenu.user.getCreditscore());

        //TODO: Button um zurück ins Hauptmenü zu kommen
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
