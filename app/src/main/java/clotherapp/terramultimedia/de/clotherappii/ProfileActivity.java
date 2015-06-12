package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
            //Setze Profil Icon bei männlichen Usern auf blau!
            txticon.setTextColor(getResources().getColor(R.color.blue_semi_transparent));
        }

        TextView txtName = (TextView) findViewById(R.id.txtname);
        txtName.setText(MainMenu.user.getFirstname() + " " + MainMenu.user.getLastname());

        TextView txtMail = (TextView) findViewById(R.id.txtMail);
        txtMail.setText(MainMenu.user.getEmail());

        //TODO: Datum richtig formatieren might be done
        TextView txtGB = (TextView) findViewById(R.id.txtGeburtstag);
        txtGB.setText(MainMenu.df_Tag.format(MainMenu.user.getBirthdate()));

        TextView txtReg = (TextView) findViewById(R.id.txtReg);
        txtReg.setText(MainMenu.df_TagUhrzeit.format(MainMenu.user.getCreationTime()));

        TextView txtScore = (TextView) findViewById(R.id.textscore);
        txtScore.setText("" + MainMenu.user.getCreditscore());

        //TODO: Profil editieren - right now create new user ....unschön!
        ((TextView) findViewById(R.id.bbtn_ChangeData_Icon)).setTypeface(MainMenu.fontawesome);
        LinearLayout btnChangeProfile = (LinearLayout) findViewById(R.id.bbtn_ChangeData);
        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UpdateProfile.class));
            }
        });

        //Macht Mülleimericon
        ((TextView) findViewById(R.id.bbtn_Delete_Icon)).setTypeface(MainMenu.fontawesome);

        LinearLayout btnDeleteProfile = (LinearLayout) findViewById(R.id.bbtn_Delete);
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

        // Button um zurück ins Hauptmenü zu kommen

        ((TextView) findViewById(R.id.bbtn_BackMenu_Icon)).setTypeface(MainMenu.fontawesome);
        LinearLayout backToMain = (LinearLayout) findViewById(R.id.bbtn_BackMenu);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // schauen ob man damit zurück zum main menu kommt
            }
        });
    }
}
