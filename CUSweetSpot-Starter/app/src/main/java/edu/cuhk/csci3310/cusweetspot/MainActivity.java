package edu.cuhk.csci3310.cusweetspot;

//
// Name: Yeung Man Wai
// SID: 1155126854
//

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.io.IOException;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private SweetListAdapter mAdapter;
    static LinkedList<String> mImagePathList = new LinkedList<>();
    
    // TODO:
    // Define other attributes as needed
    static LinkedList<Sweet> sweetList = new LinkedList<>();

    //    final String mRawFilePath = "android.resource://edu.cuhk.csci3310.cusweetspot/raw/";
    final String mAppFilePath = "/data/data/edu.cuhk.csci3310.cusweetspot/";
    final String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.cusweetspot/drawable/";

    // ... Rest of MainActivity code ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // read csv file
        readSweetData();

        // Put initial data into the image list.
        if (mImagePathList.isEmpty())
            for (int i = 1; i <= 10; i++) {
                mImagePathList.addLast(mDrawableFilePath + "image" + String.format("%02d", i));
            }

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        // Currently only an image path list is passed

        // TODO:
        // Update and pass more information as needed
        mAdapter = new SweetListAdapter(this, mImagePathList, sweetList);

        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        // Give the RecyclerView a default layout manager.
        // TODO:
        // Set up Grid according to the orientation of phone
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
    }

    // TODO:
    // Add more utility methods as needed
    private void readSweetData() {
        InputStream is = getResources().openRawResource(R.raw.cu_sweeties);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Sweet sweet = new Sweet();
                sweet.setFilename(tokens[0].length() > 0 ? tokens[0] : "");
                sweet.setSweet_name(tokens[1].length() > 0 ? tokens[1] : "");
                sweet.setResto(tokens[2].length() > 0 ? tokens[2] : "");
                sweetList.add(sweet);
            }
        } catch (IOException e) {
            Log.wtf(TAG, "Error reading csv file at line " + line, e);
            e.printStackTrace();
        }
    }
}
