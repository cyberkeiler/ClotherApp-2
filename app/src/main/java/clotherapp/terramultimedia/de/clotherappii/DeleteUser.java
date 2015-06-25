package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAuthenticatedException;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotDeletedException;

/**
 * Created by Toby on 26.05.15.
 */
public class DeleteUser extends AsyncTask<Void, Void, Boolean> {
    Activity activity;
    Context context;

    DeleteUser(Activity activity_){
        activity = activity_;
        context = activity_;
    }


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

    @Override
    protected void onPostExecute(final Boolean success) {

        if (success) {
            Toast.makeText(activity, "Profil wurde gelöscht!", Toast.LENGTH_SHORT).show();
            MainMenu.user = null;
            activity.finish();
        } else {
            Toast.makeText(activity, "Dein Profil konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
        }
    }
}