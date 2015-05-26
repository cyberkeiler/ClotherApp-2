package clotherapp.terramultimedia.de.clotherappii;

import android.content.Context;
import android.os.AsyncTask;

import de.ovgu.cse.se.ClotherAPI.ConfigurationContext;
import de.ovgu.cse.se.ClotherAPI.IObjectProvider;
import de.ovgu.cse.se.ClotherAPI.ObjectProviderFactory;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAddedException;
import de.ovgu.cse.se.ClotherAPI.models.User;

/**
 * Created by Toby on 26.05.15.
 */
public class AddUser extends AsyncTask<User, Void, Boolean> {

    Context context;
    IObjectProvider provider;

    @Override
    protected Boolean doInBackground(User... params) {
        provider = ObjectProviderFactory
                .getObjectProvider(ConfigurationContext.TEST);
        try {
            provider.addUser(params[0]);
        } catch (UserNotAddedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
