package clotherapp.terramultimedia.de.clotherappii;

import android.content.Context;
import android.os.AsyncTask;

import de.ovgu.cse.se.ClotherAPI.ConfigurationContext;
import de.ovgu.cse.se.ClotherAPI.IObjectProvider;
import de.ovgu.cse.se.ClotherAPI.ObjectProviderFactory;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserdataNotCorrectException;
import de.ovgu.cse.se.ClotherAPI.models.User;

/**
 * Created by Toby on 26.05.15.
 */
public class TryLogin extends AsyncTask<User, Void, Boolean> {
    Context context;
    IObjectProvider provider;

    @Override
    protected Boolean doInBackground(User... params) {
        provider = ObjectProviderFactory
                .getObjectProvider(ConfigurationContext.TEST);

        try {
            provider.authenticate(params[0].getEmail(), params[0].getPassword());
        } catch (UserdataNotCorrectException e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }
}

