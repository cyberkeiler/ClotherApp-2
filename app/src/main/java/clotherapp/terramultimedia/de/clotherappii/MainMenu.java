package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import de.ovgu.cse.se.ClotherAPI.IObjectProvider;
import de.ovgu.cse.se.ClotherAPI.models.User;


public class MainMenu extends Activity {
    public static IObjectProvider provider;
    public static Typeface fontawesome;
    public static Typeface clotheicons;
    public static User user = null;
    public static int userscore = 0;

    private TextView textWelcome;
    private TextView textScore;

    public static final SimpleDateFormat df_Tag = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat df_TagUhrzeit = new SimpleDateFormat("dd.MM.yyyy HH:mm");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Lade Schrift für Icons!
        fontawesome = Typeface.createFromAsset( getAssets(), "fontawesome.ttf" );
        clotheicons = Typeface.createFromAsset( getAssets(), "Clothes.ttf" );

        // Begrüßungstext und Creditscore
        textWelcome = (TextView) findViewById(R.id.textWelcome);
        textScore = (TextView) findViewById(R.id.textscore);

        Button btnStartVoteLoop = (Button) findViewById(R.id.btnStart);
        btnStartVoteLoop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, VoteLoop.class);
                //Animation zum MainMenu
                overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom);
                startActivity(i);
            }
        });

        Button btnProfile = (Button) findViewById(R.id.btnprofile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        Button btnMyOutfits = (Button) findViewById(R.id.btnOutfits);
        btnMyOutfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, NewOutfitActivity.class);
                startActivity(i);
            }
        });

        TextView appIcon = (TextView) findViewById(R.id.appIcon);

        //Logout
        Button btnLogout = (Button) findViewById(R.id.btnlogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Beende Verbindung zu API
                provider.closeConnection();
                user = null;

                //Rufe onResume() auf -> springt in LoginACtivity (user = null!)
                onResume();
            }
        });

        Button hot2 = (Button) findViewById(R.id.hot2);
        Button not2 = (Button) findViewById(R.id.not2);
        hot2.setTypeface(MainMenu.fontawesome);
        not2.setTypeface(MainMenu.fontawesome);
        
        //Gestensteuerung
        //TODO: Warum klappt das denn nicht? 

        GestureDetector.OnDoubleTapListener dtListener = new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Intent i = new Intent(MainMenu.this, VoteLoop.class);
                overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Wid das Hauptmenü wieder aufgerufen wird hier der user gechecked
        if (user != null) {
            //Setze Begrüßung
            if (user.getFirstname() != null)
                textWelcome.setText("Hallo " + user.getFirstname() + "!");
           //if (user.getCreditscore() > 0)
                textScore.setText("" + user.getCreditscore());
        } else {
            //kein User -> Aufforderung zum Login
            Toast.makeText(this, "Bitte einloggen!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainMenu.this, LoginActivity.class);
            //Animation zum MainMenu
            // TODO: Check ob wirksam, ansonsten können wir diese Zeile wieder entfernen not DONE
            overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
