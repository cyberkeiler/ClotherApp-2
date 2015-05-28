package clotherapp.terramultimedia.de.clotherappii;

import android.content.Context;
import android.os.AsyncTask;

import de.ovgu.cse.se.ClotherAPI.ConfigurationContext;
import de.ovgu.cse.se.ClotherAPI.IObjectProvider;
import de.ovgu.cse.se.ClotherAPI.ObjectProviderFactory;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAddedException;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAuthenticatedException;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotDeletedException;
import de.ovgu.cse.se.ClotherAPI.models.User;

/**
 * Created by Toby on 26.05.15.
 */
public class DeleteUser extends AsyncTask<Void, Void, Boolean> {

    Context context;


    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            MainMenu.provider.deleteUser();
        } catch (UserNotDeletedException e) {
            e.printStackTrace();
            return false;
        } catch (UserNotAuthenticatedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}