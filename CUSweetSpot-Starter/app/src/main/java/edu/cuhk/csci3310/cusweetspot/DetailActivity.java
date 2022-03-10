package edu.cuhk.csci3310.cusweetspot;

//
// Name: Yeung Man Wai
// SID: 1155126854
//

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    // TODO:
    // Define other attributes as needed
    private String sweet_name;
    private Integer rating;
    private TextView ratingView, restoView;
    private EditText editNameView;
    private SharedPreferences mPreferences;
    private String NAME_KEY, RATING_KEY;
    private final String smSharedPrefFile = "edu.cuhk.csci3310.cusweetspot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ratingView = findViewById(R.id.value_rating);
        rating = Integer.parseInt(ratingView.getText().toString());
        setSupportActionBar(toolbar);

        // TODO:
        // 1. Obtain data via Intent
        Intent intent = getIntent();
        Bundle data = intent.getExtras();

        // generate keys according to the item position
        int position = data.getInt("position");
        NAME_KEY = "name_"+ position;
        RATING_KEY = "rating_"+ position;

        // 2. Setup Views based on the data received from intent
        ImageView imageView = findViewById(R.id.image_large);
        String filename = data.getString("filename");
        Uri uri = Uri.parse(filename);
        imageView.setImageURI(uri);
        editNameView = findViewById(R.id.edit_name);
        restoView = findViewById(R.id.link_cu_map);
        restoView.setText(data.getString("resto"));

        // 3. Setup event handler for resto onclick
        restoView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent impIntent = new Intent(Intent.ACTION_VIEW);
                impIntent.setData(Uri.parse("https://www.openrice.com/en/hongkong/restaurants?what=" + restoView.getText()));
                startActivity(impIntent);
            }
        });

        // 4. Perform necessary data-persistence steps
        // retrieve saved states (if have) and set views
        mPreferences = getSharedPreferences(smSharedPrefFile, MODE_PRIVATE);
        sweet_name = mPreferences.getString(NAME_KEY, data.getString("sweet_name"));
        rating = mPreferences.getInt(RATING_KEY, 3);
        editNameView.setText(sweet_name);
        ratingView.setText(rating.toString());
    }

    // save data to sharedPreferences
    @Override
    protected void onPause() {
        super.onPause();
        sweet_name = editNameView.getText().toString();
        rating = Integer.parseInt(ratingView.getText().toString());
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(NAME_KEY, sweet_name);
        preferencesEditor.putInt(RATING_KEY, rating);
        preferencesEditor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO:
        // Perform necessary data-persistence steps
        // save data to variables so that they can be saved in onSaveInstanceState
        sweet_name = editNameView.getText().toString();
        rating = Integer.parseInt(ratingView.getText().toString());
    }

    // TODO:
    // Add more utility methods as needed
    public void minusRating(View view) {
        if(rating > 1) {
            rating -= 1;
            ratingView.setText(rating.toString());
        }
    }

    public void plusRating(View view) {
        if(rating < 5) {
            rating += 1;
            ratingView.setText(rating.toString());
        }
    }
}