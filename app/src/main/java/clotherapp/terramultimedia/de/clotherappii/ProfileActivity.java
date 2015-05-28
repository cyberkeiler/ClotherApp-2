package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.ovgu.cse.se.ClotherAPI.models.Gender;


public class ProfileActivity extends Activity {
    private Activity ActivityContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ActivityContext = this;

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
        TextView txtScore = (TextView) findViewById(R.id.textscore);
        if(MainMenu.user.getCreditscore()>0) txtScore.setText(MainMenu.user.getCreditscore());

        Button btnDeleteProfile = (Button) findViewById(R.id.btnDeleteProfile);
        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Dialogabfrage: Profil löschen bestätigen
                new AlertDialog.Builder(ActivityContext)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Profil löschen?")
                        .setMessage("Bist du dir wirklich sicher das du dein Profil löschen möchtest?")
                        .setPositiveButton("Ja, sicher!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteUser(ActivityContext).execute();
                                Toast.makeText(ActivityContext, "Dein Profil wird gelöscht", Toast.LENGTH_SHORT).show();
                                //TODO: showProgress - irgendeine Warteanimation
                            }

                        })
                        .setNegativeButton("Ups, doch nicht!", null)
                        .show();
                // */

            }
        });

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
