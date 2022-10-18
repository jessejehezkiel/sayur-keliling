package com.example.sayurkelilingrevisi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sayurkelilingrevisi.Model.Chat;
import com.example.sayurkelilingrevisi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PesanAdapter extends RecyclerView.Adapter<PesanAdapter.ViewHolder> {

    private Context context;
    private List<Chat> mChat;
    private String imageURL;


    FirebaseUser firebaseUser;


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;



    public PesanAdapter(Context context, List<Chat> mChat, String imageURL) {
        this.context = context;
        this.mChat = mChat;
        this.imageURL = imageURL;
    }


    @NonNull
    @Override
    public PesanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_kanan,
                    parent,
                    false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_kiri,
                    parent,
                    false);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesanAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.show_pesan.setText(chat.getPesan());

        if (imageURL.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(imageURL).into(holder.profile_image);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_pesan;
        public ImageView profile_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_pesan = itemView.findViewById(R.id.show_pesan);
            profile_image = itemView.findViewById(R.id.profile_image);

        }
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        assert fuser != null;
        if (mChat.get(position).getPengirim().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
}