package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
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
import de.ovgu.cse.se.ClotherAPI.models.User;


public class MainMenu extends Activity {
    public IObjectProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        provider = ObjectProviderFactory.getObjectProvider(ConfigurationContext.MOCKUP);
        User user = provider.getUser();

        TextView textWelcome = (TextView) findViewById(R.id.textWelcome);
        TextView textScore = (TextView) findViewById(R.id.textscore);

        textWelcome.setText("Hallo Noob!");//"+user.getFirstname()+"!");
        //textScore.setText(user.getCreditscore());

        Button btnStartVoteLoop = (Button) findViewById(R.id.btnStart);
        btnStartVoteLoop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, VoteLoop.class);
                startActivity(i);
            }
        });

        FloatingActionButton btnLogout = (FloatingActionButton) findViewById(R.id.pink_icon);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome.ttf" );
        //btnLogout.setTypeface(font);
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
