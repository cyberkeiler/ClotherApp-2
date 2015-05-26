package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;


public class VoteLoop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_loop);

        Button btnhot2 = (Button) findViewById(R.id.hot2);
        Button btnnot2 = (Button) findViewById(R.id.not2);
        btnhot2.setTypeface(MainMenu.fontawesome);
        btnnot2.setTypeface(MainMenu.fontawesome);

    }
}
