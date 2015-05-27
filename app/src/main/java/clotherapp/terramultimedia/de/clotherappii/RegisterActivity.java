package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import de.ovgu.cse.se.ClotherAPI.models.User;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //TODO: Erstelle Textfeldabfrage usw. plausibilitätsprüfung und erstelle angand dieser einen User
        User newuser;

        //TODO: AUSKOMMENTIEREN. Netzwerkanbindung habe ich für dich Vorbereitet:
        /*

        boolean res = false;
        try {
            res = new AddUser().execute(newuser).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (res)
            Toast.makeText(this, "Profil erfolgreich erstellt", Toast.LENGTH_SHORT).show();
            //TODO: Bei erfolgreicher Registrierung soll der User automatische eingeloggt sein
            //dazu: MainMenu.Resume() und MainMenu.user = getUser setzen!
        else
            Toast.makeText(this, "Fehler bei Registrierung!", Toast.LENGTH_SHORT).show();

         */

    }

    // Funktionen zum prüfen
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isPasswordIdentical(String password) {
        //TODO: Vergleiche Passwortwiederholung identisch
        return false;
    }
}
