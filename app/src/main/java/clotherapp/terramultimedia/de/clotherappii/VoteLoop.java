package clotherapp.terramultimedia.de.clotherappii;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
    private TextView creatorname;
    private TextView creatoricon;
    private TextView occasion;
    private TextView tags;
    private TextView uploaded;

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

        btnhot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteAndNext();
            }
        });

        btnnot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteAndNext();
            }
        });

        //Bilddetails
        imageview = (ImageView) findViewById(R.id.imageView2);
        creatoricon = (TextView) findViewById(R.id.txtPictureUsericon);
        creatorname = (TextView) findViewById(R.id.txtPictureUsername);
        occasion = (TextView) findViewById(R.id.txtPictureOccasion);
        tags = (TextView) findViewById(R.id.txtPictureTags);
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

    }

    private void VoteAndNext() {
        //TODO: VoteButtons gesperrt solange PictureList geladen wird
        //TODO: ADDVote
        //TODO: Prüfe ob eigenes Bild oder ob ich dieses Bild bereits bewertet habe
        //TODO: Füge Credits für Vote hinzu
        /*User myUser = MainMenu.user; //je vote 1 punkt ?
        myUser.setCreditscore(myUser.getCreditscore() + 1);
        */

        showNextPicture();
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
        if(picture.getCreator() != null)
            creatorname.setText(picture.getCreator().getFirstname());
        else
            creatorname.setText("Nulluser");

        //Occasion
        if(picture.getOccasion() != null)
            occasion.setText(picture.getOccasion().getName());
        else
            occasion.setText("[-keine Occasion-]");

        //TODO: Tags in String einfügen mit Hastag davor
        //Tags
        if(picture.getTags() != null){

        }

        //Erstellungsdatum
        if(picture.getCreationTime() != null)
            uploaded.setText(df.format(picture.getCreationTime()));

        if(picture.getImage()!= null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture.getImage(), 0, picture.getImage().length);
            if (bitmap != null)
                imageview.setImageBitmap(bitmap);
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
