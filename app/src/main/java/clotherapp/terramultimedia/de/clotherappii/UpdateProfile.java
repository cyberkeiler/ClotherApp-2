package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import clotherapp.terramultimedia.de.clotherappii.util.SystemUiHider;
import de.ovgu.cse.se.ClotherAPI.ConfigurationContext;
import de.ovgu.cse.se.ClotherAPI.IObjectProvider;
import de.ovgu.cse.se.ClotherAPI.ObjectProviderFactory;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAuthenticatedException;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotUpdatedException;
import de.ovgu.cse.se.ClotherAPI.models.User;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class UpdateProfile extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_profile);

    //Setzt die Icon Schrift für das Symbol
    TextView RegisterIcon = (TextView) findViewById(R.id.textView2);
    RegisterIcon .setTypeface(MainMenu.fontawesome);

    //TODO: Erstelle Textfeldabfrage usw. plausibilitätsprüfung und erstelle angand dieser einen User

    //email

    Button updateProfile = (Button) findViewById(R.id.profileUpdate);

    updateProfile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User OurUser = new User();
            EditText Email = (EditText) findViewById(R.id.EmailAdress);
            String EmailString = Email.getText().toString();
            if (isEmailValid(EmailString)) {
                OurUser.setEmail(EmailString);
            } else {
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
            if (isPasswordValid(passwordAsString) && isPasswordIdentical(passwordAsString, passwordConfirmAsString)) {
                Toast.makeText(getApplicationContext(), "Passwörter stimmen nicht überein", Toast.LENGTH_SHORT);
                password.setText("");
                passwordConfirmation.setText("");
                password.requestFocus();
                return;
            } else {
                OurUser.setPassword(password.getText().toString());
            }

            //Birthday
            //Firstname & LastName
            EditText firstName = (EditText) findViewById(R.id.FirstName);
            EditText lastName = (EditText) findViewById(R.id.LastName);
            OurUser.setFirstname(firstName.getText().toString());
            OurUser.setLastname(lastName.getText().toString());

            //Test ob funktioniert hat mit Ausgabe an User
            Boolean result = false;
            try {
                result = new doUpdateProfile().execute(OurUser).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (result){
                Toast.makeText(getApplicationContext(), "Profil geändert!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Profiländerung FAILED", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    });


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


    private class doUpdateProfile extends AsyncTask<User,Void,Boolean> {

        @Override
        protected Boolean doInBackground(User... params) {
            IObjectProvider provider = ObjectProviderFactory.getObjectProvider(ConfigurationContext.TEST);
            try{
                provider.getUser();
                provider.updateUser();
            }
            catch (UserNotUpdatedException e){
                return false;
            } catch (UserNotAuthenticatedException e) {
                e.printStackTrace();
            }

            return true;
        }
    }
}


