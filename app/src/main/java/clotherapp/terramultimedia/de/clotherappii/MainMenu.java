package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import de.ovgu.cse.se.ClotherAPI.ConfigurationContext;
import de.ovgu.cse.se.ClotherAPI.IObjectProvider;
import de.ovgu.cse.se.ClotherAPI.ObjectProviderFactory;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAddedException;
import de.ovgu.cse.se.ClotherAPI.models.Gender;
import de.ovgu.cse.se.ClotherAPI.models.User;


public class MainMenu extends Activity {
    public static IObjectProvider provider;
    public static Typeface fontawesome;
    public static User user;

    //In dieser Funktion sollen alle Sachen, die beim Ersten aufrufen der App ausgeführt werden sollen eingefügt werden
    public static void InitialSetup() {
        //Stelle Provider zu Verfügung
        MainMenu.provider = ObjectProviderFactory.getObjectProvider(ConfigurationContext.MOCKUP);
        CreateTestData();

        //Font Awesomeness kenn nicht in einer static Methode geladen werden.
    }

    private static void CreateTestData() {
        try {
            User newuser = new User();
            newuser.setEmail("wolfi@joop.com");
            newuser.setPassword("heidi");

            newuser.setFirstname("Wolfgang");
            newuser.setLastname("Joop");
            // TODO: Geburtstag hinzufügen: 18. November 1944
            //newuser.setBirthdate();
            newuser.setGender(Gender.MALE);


            MainMenu.provider.addUser(newuser);
        } catch (UserNotAddedException e) {
            //User konnte nicht erstellt werden
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Lade Schrift für Icons!
        fontawesome = Typeface.createFromAsset( getAssets(), "fontawesome.ttf" );

        TextView textWelcome = (TextView) findViewById(R.id.textWelcome);
        TextView textScore = (TextView) findViewById(R.id.textscore);
        if (user != null) {
            textWelcome.setText("Hallo " + user.getEmail() + "!");
            //textScore.setText(user.getCreditscore());
        } else
            textWelcome.setText("Kein User!");


        Button btnStartVoteLoop = (Button) findViewById(R.id.btnStart);
        btnStartVoteLoop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, VoteLoop.class);
                startActivity(i);
            }
        });

        FloatingActionButton btnNewOutfit = (FloatingActionButton) findViewById(R.id.btnNewOutfit);
        btnNewOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, NewOutfit.class);
                startActivity(i);
            }
        });

        TextView appIcon = (TextView) findViewById(R.id.appIcon);

        //FloatingActionButton btnLogout = (FloatingActionButton) findViewById(R.id.pink_icon);

        appIcon.setTypeface(fontawesome);

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
