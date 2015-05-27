package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
            txticon.setTextColor(getResources().getColor(R.color.blue_semi_transparent));
        }

        TextView txtName = (TextView) findViewById(R.id.txtname);
        txtName.setText(MainMenu.user.getFirstname() + " " + MainMenu.user.getLastname());

        TextView txtMail = (TextView) findViewById(R.id.txtMail);
        txtMail.setText(MainMenu.user.getEmail());

        //TODO: Datum richtig formatieren
        TextView txtGB = (TextView) findViewById(R.id.txtGeburtstag);
        txtGB.setText(MainMenu.user.getBirthdate().toString());
        //hoffentlich funktioniert es so...
        String Bdaystring = (String) txtGB.getText();
        SimpleDateFormat Bday = new SimpleDateFormat("dd-mm-yyyy", Locale.GERMANY);
        Date Birthday = new Date(0,1,1);
        try {
             Birthday = Bday.parse(Bdaystring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MainMenu.user.setBirthdate(Birthday);


        TextView txtReg = (TextView) findViewById(R.id.txtReg);
        txtReg.setText(MainMenu.user.getCreationTime().toString());

        //TODO: Fange fehler vom Creditscore ab
        TextView txtScore = (TextView) findViewById(R.id.textscore);
        if (MainMenu.user.getCreditscore() >= 0){
            txtScore.setText(MainMenu.user.getCreditscore());
        }
        else {
            Toast.makeText(this, "Fatal Error", Toast.LENGTH_SHORT);
        }


        //TODO: Button um zurück ins Hauptmenü zu kommen

        Button backToMainMenu = (Button) findViewById(R.id.backtoMain);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // back to main menu?
            }
        });
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
