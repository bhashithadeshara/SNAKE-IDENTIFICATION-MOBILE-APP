package com.example.myapplication;

import static com.example.myapplication.DBmain.TABLENAME;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;

public class ResultsActivity extends AppCompatActivity {

    TextView txtSnakeName,txtDescription2,txtAddToCollection;
    ImageView imgCollection,goToCollection,goToHome,backToResultActivity,imgSnake;
    Drawable drawable;
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    int id=0;
    String snakeName;
    TextView txtResults,txtVenomisity;
    ImageView resultImage;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        txtResults = findViewById(R.id.txtResults);
        resultImage = findViewById(R.id.resultImage);
        imgCollection= findViewById(R.id.imgCollection);
        goToHome = findViewById(R.id.gotoHome);
        backToResultActivity = findViewById(R.id.backImage);
        txtAddToCollection = findViewById(R.id.txtAddToCollection);
        txtDescription2 = findViewById(R.id.txtDescription2);


        setUpToolbar();

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent intent = new Intent(ResultsActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_collection:
                        Intent intent1 = new Intent(ResultsActivity.this, CollectionActivity.class);

                        startActivity(intent1);
                        break;
                    case R.id.nav_safety:
                        Intent intent2 = new Intent(ResultsActivity.this, SafetyActivity.class);

                        startActivity(intent2);
                        break;

                }
                return false;
            }
        });

        String snakeName = getIntent().getStringExtra("Animal");
        txtResults.setText(snakeName);

        if(snakeName.equals("Python")){
            resultImage.setImageResource(R.drawable.python);
            imgSnake = findViewById(R.id.resultImage);
        }
        else if(snakeName.equals("Indian cobra")){
            resultImage.setImageResource(R.drawable.cobra);
            imgSnake = findViewById(R.id.resultImage);
        }

        else if(snakeName.equals("Green vine snake")){
            resultImage.setImageResource(R.drawable.green_vine);
            imgSnake = findViewById(R.id.resultImage);
        }
        else if(snakeName.equals("Buff-striped keelback")){

            resultImage.setImageResource(R.drawable.buff_stripped);
            imgSnake = findViewById(R.id.resultImage);
        }

        else if(snakeName.equals("Common krait")){
            resultImage.setImageResource(R.drawable.coomon_krait);
            imgSnake = findViewById(R.id.resultImage);
        }

        else if(snakeName.equals("Russells viper")){
            resultImage.setImageResource(R.drawable.russells_viper);
            imgSnake = findViewById(R.id.resultImage);
        }



        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(getApplicationContext());
        dataBaseAccess.open();
        String snakeDescription = dataBaseAccess.getDescription(snakeName);


        txtDescription2.setText(snakeDescription);


        dataBaseAccess.close();


        imgCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();


            }
        });
    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.first_color));
        actionBarDrawerToggle.syncState();


    }

    private void insertData() {

        String name = txtResults.getText().toString();
        DBmain dBmain = new DBmain(this);
        Boolean checkName  = dBmain.checkSnake(name);
        if (checkName == false) {

            ContentValues cv=new ContentValues();
            cv.put("avatar",ImageViewToByte(imgSnake));
            cv.put("name",name);
           // DBmain dBmain = new DBmain(this);
            sqLiteDatabase=dBmain.getWritableDatabase();
            Long recinsert=sqLiteDatabase.insert(TABLENAME,null,cv);
            if (recinsert!=null){
                Toast.makeText(ResultsActivity.this, "inserted successfully", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ResultsActivity.this, CollectionActivity.class);

                startActivity(intent2);

            }


        }
        else{
            AlertDialog dlg = new AlertDialog.Builder(ResultsActivity.this)
                    .setTitle("Error !")
                    .setMessage(name+" is already added to the collection")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dlg.show();
        }

    }
    private byte[] ImageViewToByte(ImageView avatar) {
        Bitmap bitmap=((BitmapDrawable)avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[]bytes=stream.toByteArray();
        return bytes;
    }
}