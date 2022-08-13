package com.example.myapplication;

import static com.example.myapplication.DBmain.TABLENAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class SnakeDetailsActivity extends AppCompatActivity {

    TextView txtSnakeName,txtDescription,txtAddToCollection;
    ImageView imageView,goToCollection,goToHome,backToResultActivity,avatar;
    Drawable drawable;
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    int id=0;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_details);

        imageView = findViewById(R.id.imgDescription);
        goToCollection= findViewById(R.id.gotoCollection);
        goToHome = findViewById(R.id.gotoHome);
        backToResultActivity = findViewById(R.id.backImage);
        txtAddToCollection = findViewById(R.id.txtAddToCollection);

        name = getIntent().getStringExtra("Snake");

        goToCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CollectionActivity.class);
                startActivity(intent);
            }
        });

        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

        backToResultActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ResultsActivity.class);
                startActivity(intent);
            }
        });

        if(name.equals("Cobra")){
            drawable = getResources().getDrawable(R.drawable.cobra);
            imageView.setImageDrawable(drawable);
            avatar = findViewById(R.id.imgDescription);
        }
        else if(name.equals("python")){
            drawable = getResources().getDrawable(R.drawable.python);
            imageView.setImageDrawable(drawable);
            avatar = findViewById(R.id.imgDescription);
        }

        txtSnakeName = findViewById(R.id.txtSnakeName);
        txtDescription = findViewById(R.id.txtDescription);


        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(getApplicationContext());
        dataBaseAccess.open();
        String snakeDescription = dataBaseAccess.getDescription(name);


        txtSnakeName.setText(name);
        txtDescription.setText(snakeDescription);

        dataBaseAccess.close();

        goToCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CollectionActivity.class);
                startActivity(intent);
            }
        });
        txtAddToCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();


            }
        });

    }

    private void insertData() {

        ContentValues cv=new ContentValues();
        cv.put("avatar",ImageViewToByte(avatar));
        cv.put("name",name);
        DBmain dBmain = new DBmain(this);
        sqLiteDatabase=dBmain.getWritableDatabase();
        Long recinsert=sqLiteDatabase.insert(TABLENAME,null,cv);
        if (recinsert!=null){
            Toast.makeText(SnakeDetailsActivity.this, "inserted successfully", Toast.LENGTH_SHORT).show();
            //clear when click on submit button

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