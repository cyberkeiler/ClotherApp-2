package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.ovgu.cse.se.ClotherAPI.models.User;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //TODO: Erstelle Textfeldabfrage usw. plausibilitätsprüfung und erstelle angand dieser einen User
        User newuser = new User();
        //email
        EditText Email = (EditText) findViewById(R.id.EmailAdress);
        String EmailString = Email.getText().toString();
        if (isEmailValid(EmailString)){
            newuser.setEmail (EmailString);
        }
        else {
            Toast.makeText(getApplicationContext(), "Ungültige Emailadresse", Toast.LENGTH_SHORT);
            Email.setText("");
            Email.requestFocus();
            return;
        }
        //password
        EditText password = (EditText) findViewById(R.id.password);
        EditText passwordConfirmation = (EditText) findViewById(R.id.confirmpassword);
        String passwordAsString = password.getText().toString();
        String passwordConfirmAsString = passwordConfirmation.getText().toString();
        if (isPasswordValid(passwordAsString) && isPasswordIdentical(passwordAsString, passwordConfirmAsString)){
            Toast.makeText(getApplicationContext(), "Passwörter stimmen nicht überein",Toast.LENGTH_SHORT);
                    password.setText("");
                    passwordConfirmation.setText("");
                    password.requestFocus();
                    return;
        }
        else {
            newuser.setPassword(password.getText().toString());
        }

        //Birthday
        //Firstname & LastName
        EditText firstName = (EditText) findViewById(R.id.FirstName);
        EditText lastName = (EditText) findViewById(R.id.LastName);
        newuser.setFirstname(firstName.getText().toString());
        newuser.setLastname(lastName.getText().toString());

        Button register = (Button) findViewById(R.id.Registerpls);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


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

    private boolean isPasswordIdentical(String password, String confirmpassword) {
        return password.equals(confirmpassword);

    }
}
