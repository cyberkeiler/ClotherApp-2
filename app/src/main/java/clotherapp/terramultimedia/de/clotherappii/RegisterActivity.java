package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import de.ovgu.cse.se.ClotherAPI.ConfigurationContext;
import de.ovgu.cse.se.ClotherAPI.IObjectProvider;
import de.ovgu.cse.se.ClotherAPI.ObjectProviderFactory;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAddedException;
import de.ovgu.cse.se.ClotherAPI.models.Gender;
import de.ovgu.cse.se.ClotherAPI.models.User;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Setzt die Icon Schrift für das Symbol
        TextView RegisterIcon = (TextView) findViewById(R.id.textView2);
        RegisterIcon .setTypeface(MainMenu.fontawesome);

        //TODO: Erstelle Textfeldabfrage usw. plausibilitätsprüfung und erstelle angand dieser einen User

        //email

        Button register = (Button) findViewById(R.id.Registerpls);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newuser = new User();
                EditText Email = (EditText) findViewById(R.id.EmailAdress);
            String EmailString = Email.getText().toString();
            if (isEmailValid(EmailString)){
                newuser.setEmail (EmailString);
            }
            else {
                Toast.makeText(getApplicationContext(), "Ungültige Emailadresse", Toast.LENGTH_SHORT).show();
                Email.setText("");
                Email.requestFocus();
                return;
            }
            //password
            EditText password = (EditText) findViewById(R.id.password);
            EditText passwordConfirmation = (EditText) findViewById(R.id.confirmpassword);
            String passwordAsString = password.getText().toString();
            String passwordConfirmAsString = passwordConfirmation.getText().toString();
            if (!isPasswordValid(passwordAsString)&& !isPasswordIdentical(passwordAsString, passwordConfirmAsString)){
                Toast.makeText(getApplicationContext(), "Passwörter stimmen nicht überein",Toast.LENGTH_SHORT).show();
                    password.setText("");
                    passwordConfirmation.setText("");
                    password.requestFocus();
                    return;
            }
            else {
                newuser.setPassword(password.getText().toString());
            }

        //Birthday
                //TODO: Date übernehmen...
                EditText BDay = (EditText) findViewById (R.id.Birthday);
                String Birthday = BDay.getText().toString();
                Date myBDay = new Date(100,1,1);
                //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
                //myBDay.parse(Birthday);
                //df.format(myBDay);
                newuser.setBirthdate(myBDay);
        //Firstname & LastName
            EditText firstName = (EditText) findViewById(R.id.FirstName);
            EditText lastName = (EditText) findViewById(R.id.LastName);
                if (firstName.getText().toString().equals("") || lastName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Leere Namen nicht erláubt)", Toast.LENGTH_SHORT).show();
                }
                else {
                    newuser.setFirstname(firstName.getText().toString());
                    newuser.setLastname(lastName.getText().toString());
                }
            //TODO: gender auswahl
            Switch Genderswitch = new Switch(getApplicationContext());
                Genderswitch.setTextOff("MALE");

                Genderswitch.setTextOn("FEMALE");
                Genderswitch.getTextOn();
                if (Genderswitch.isChecked())
                    newuser.setGender(Gender.FEMALE);
                else
                    newuser.setGender(Gender.MALE);

                boolean res = false;
                try {
                    res = new AddUser().execute(newuser).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                if (res){
                    Toast.makeText(getApplicationContext(), "Profil erfolgreich erstellt", Toast.LENGTH_SHORT).show();
                        finish();}
                    //TODO: Bei erfolgreicher Registrierung soll der User automatische eingeloggt sein
                    //dazu: MainMenu.Resume() und MainMenu.user = getUser setzen!
                    //MainMenu.Resume();
                    // MainMenu.user = MainMenu.provider.getUser();
                else
                    Toast.makeText(getApplicationContext(), "Fehler bei Registrierung!", Toast.LENGTH_SHORT).show();


            }
        });


    }

    // Funktionen zum prüfen
    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        if( password.length() >= 4){
            return true;
        } // TODO: password needs Großbuchstaben und zahl
            /*if (password.matches("[A-Z]") && password.matches ("[0-9]")){
                return true;
            }
            else {
                Toast.makeText(getApplicationContext(), "Password benötigt alphanumerische Zeichen und mindestens 1 Großbuchstaben", Toast.LENGTH_SHORT).show();
                return false;}}*/
        else {Toast.makeText(getApplicationContext(), "Password zu kurz", Toast.LENGTH_LONG).show();
        return false;}
    }

    private boolean isPasswordIdentical(String password, String confirmpassword) {
        return password.equals(confirmpassword);


    }



    class AddUser extends AsyncTask<User, Void, Boolean>{

        @Override
        protected Boolean doInBackground(User... params) {
            IObjectProvider provider = ObjectProviderFactory.getObjectProvider(ConfigurationContext.TEST);
            try {
                provider.addUser(params[0]);
            } catch (UserNotAddedException e) {
                return false;
            }
            return true;
        }
    }
}
