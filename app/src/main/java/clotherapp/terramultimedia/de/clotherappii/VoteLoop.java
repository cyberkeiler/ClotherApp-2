package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import de.ovgu.cse.se.ClotherAPI.exceptions.PictureNotFoundException;
import de.ovgu.cse.se.ClotherAPI.exceptions.UserNotAuthenticatedException;
import de.ovgu.cse.se.ClotherAPI.exceptions.VoteNotAddedException;
import de.ovgu.cse.se.ClotherAPI.models.Picture;
import de.ovgu.cse.se.ClotherAPI.models.Vote;


public class VoteLoop extends Activity {
    public GetPicturesTask mPictureTask;
    public List<Picture> pictureList;
    private Picture picture;
    private int num_picture;
    private int picture_offset;

    private ProgressBar progressBar;

    private ImageView imageview;
    private TextView creator_name;
    private TextView creatoricon;
    private TextView occasion;
    //private TextView tags;
    private TextView uploaded;

    TextView ResponseIcon;

    // private int votebonus = 0;

    static final int VOTE_HOT = 4;
    static final int VOTE_NOT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_loop);

        //Vote Buttons
        Button btnhot2 = (Button) findViewById(R.id.btnhot2);
        Button btnnot2 = (Button) findViewById(R.id.btnnot2);
        btnhot2.setTypeface(MainMenu.fontawesome);
        btnnot2.setTypeface(MainMenu.fontawesome);

        ResponseIcon = (TextView) findViewById(R.id.ResponseTxt);
        ResponseIcon.setTypeface(MainMenu.fontawesome);


        btnhot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeWithAnim(true);
            }
        });

        btnnot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeWithAnim(false);

            }
        });




        //Bilddetails
        imageview = (ImageView) findViewById(R.id.imageView2);
        creatoricon = (TextView) findViewById(R.id.txtPictureUsericon);
        creator_name = (TextView) findViewById(R.id.txtPictureUsername);
        occasion = (TextView) findViewById(R.id.txtPictureOccasion);
        //tags = (TextView) findViewById(R.id.txtPictureTags);
        uploaded = (TextView) findViewById(R.id.txtPictureDate);

        //Icon setzen
        creatoricon.setTypeface(MainMenu.fontawesome);

        //ProgressBar
        progressBar = (ProgressBar) findViewById(R.id.PictureprogressBar);



        //Lade die ersten 20 Pictures in die API
        picture_offset = 0;
        showProgress(true);
        mPictureTask = new GetPicturesTask(picture_offset);
        mPictureTask.execute();


        this.imageview.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Toast.makeText(VoteLoop.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                LikeWithAnim(true);

            }

            public void onSwipeLeft() {
                Toast.makeText(VoteLoop.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                LikeWithAnim(false);

            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO: Update User, dass der neue Punktestand auch auf dem Server ist
    }

    private void LikeWithAnim(boolean like){
        //TODO: Prüfe ob eigenes Bild oder ob ich dieses Bild bereits bewertet habe

        Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(2000);

        if(like) {
            sendVote(5);
            ResponseIcon.setText(R.string.icon_heart);
            ResponseIcon.setTextColor(getResources().getColor(R.color.pink));
        }
        else {
            sendVote(0);
            ResponseIcon.setText(R.string.icon_not);
            ResponseIcon.setTextColor(getResources().getColor(R.color.color_black));
        }

        ResponseIcon.setVisibility(View.VISIBLE);
        ResponseIcon.startAnimation(out);

        //Blendet den Response Icon nach Ende Animation aus
        final Handler AftAnimHandler = new Handler();
        AftAnimHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ResponseIcon.setVisibility(View.INVISIBLE);
            }
        }, 1950);

        //Blende Bild aus
        Animation out2 = new AlphaAnimation(1.0f, 0.0f);
        out2.setDuration(1000);
        imageview.startAnimation(out2);

        Handler AftAnim2Handler = new Handler();
        AftAnim2Handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Blende nacg 0.5s ausblenden das neue Bild ein!
                Animation in = new AlphaAnimation(0.0f, 1.0f);
                in.setDuration(1000);

                showNextPicture();
                imageview.startAnimation(in);
            }
        }, 980);

    }

    private void sendVote(int rating) {
        //votebonus ++;
        //TODO: VoteButtons gesperrt solange PictureList geladen wird
        //TODO: ADDVote - Testen!
        VoteTask mVoteTask = new VoteTask(picture, rating);
        mVoteTask.execute();

        MainMenu.user.setCreditscore(MainMenu.user.getCreditscore() + 1);
    }

    public void showProgress(boolean on){
        //Zeigt Progressbar ja oder nein!
        if(on){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showNextPicture(){
        num_picture ++;
        if(num_picture<pictureList.size()){
            picture = pictureList.get(num_picture);
        }
        else{
            //Nächsten 20 Bilder müssen geladen werden.
            picture_offset = picture_offset + 20;
            showProgress(true);
            mPictureTask = new GetPicturesTask(picture_offset);
            mPictureTask.execute();
        }

        showPicture();
    }
    private void showPicture(){


        if (picture == null){
            Toast.makeText(this, "Picture is null", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Unterscheidung ob am gleichen Tag "vor xx min/ xx h" oder Datum
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy\n HH:mm");

        //Name des Creators
        if(picture.getCreator() != null) {
            creator_name.setText(picture.getCreator().getFirstname());
        }
        else {
            creator_name.setText("Nulluser");
        }

        //Occasion
        if(picture.getOccasion() != null){
            occasion.setText(picture.getOccasion().getName());
        }
        else {
            occasion.setText("[-keine Occasion-]");
        }

        //TODO: Tags in String einfügen mit Hastag davor
        //Tags
        /*if(picture.getTags() != null){

        }*/

        //Erstellungsdatum
        if(picture.getCreationTime() != null) {


            uploaded.setText(df.format(picture.getCreationTime()));
        }

        if(picture.getImage()!= null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture.getImage(), 0, picture.getImage().length);
            if (bitmap != null) {


                imageview.setImageBitmap(bitmap);
            }
        }

    }

    public class VoteTask extends AsyncTask<Void, Void, Boolean>{
        private Vote vote;
        VoteTask(Picture picture, int rating){
            vote = new Vote();
            vote.setCreator(MainMenu.user);
            vote.setPicture(picture);
            vote.setRating(rating);


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                MainMenu.provider.addVote(vote);
            } catch (VoteNotAddedException e) {
                e.printStackTrace();
                return false;
            } catch (UserNotAuthenticatedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }


    public class GetPicturesTask extends AsyncTask<Void, Void, Boolean> {

        private int offset = 0;

        GetPicturesTask(int offset) {
            this.offset = offset;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                pictureList = MainMenu.provider.getPictures(offset);
            } catch (PictureNotFoundException e) {
                e.printStackTrace();
            } catch (UserNotAuthenticatedException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            mPictureTask = null;
            showProgress(false);

            if (success) {
                num_picture = 0;
                picture = pictureList.get(num_picture);
                showPicture();
            } else {
                Toast.makeText(getParent(), "Laden der Bilder fehlgeschlagen", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mPictureTask = null;
            showProgress(false);
        }
    }
}
