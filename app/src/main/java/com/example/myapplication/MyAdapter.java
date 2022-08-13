package com.example.myapplication;

import static com.example.myapplication.DBmain.TABLENAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public MyAdapter(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.singledata,null);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        final Model model=modelArrayList.get(position);
        byte[]image=model.getProavatar();
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageavatar.setImageBitmap(bitmap);
        holder.txtname.setText(model.getUsername());

        holder.flowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.flowmenu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_menu:

                                Bundle bundle=new Bundle();
                                bundle.putInt("id",model.getId());
                                bundle.putByteArray("avatar",model.getProavatar());
                                bundle.putString("name",model.getUsername());
                                Intent intent=new Intent(context,ResultsActivity.class);
                                intent.putExtra("Animal", model.getUsername());
                                context.startActivity(intent);
                                break;
                            case R.id.delete_menu:
                                ///delete operation
                                DBmain dBmain=new DBmain(context);
                                sqLiteDatabase=dBmain.getReadableDatabase();
                                long recdelete=sqLiteDatabase.delete(TABLENAME,"id="+model.getId(),null);
                                if (recdelete!=-1){
                                    Toast.makeText(context, "data deleted", Toast.LENGTH_SHORT).show();
                                    //remove position after deleted
                                    modelArrayList.remove(position);
                                    //update data
                                    notifyDataSetChanged();
                                }
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                //display menu
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageavatar;
        TextView txtname;
        ImageButton flowmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageavatar=(ImageView)itemView.findViewById(R.id.viewavatar);
            txtname=(TextView)itemView.findViewById(R.id.txt_name);
            flowmenu=(ImageButton)itemView.findViewById(R.id.flowmenu);
        }
    }
}
