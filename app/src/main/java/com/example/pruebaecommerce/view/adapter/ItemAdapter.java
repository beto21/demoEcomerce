package com.example.pruebaecommerce.view.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebaecommerce.R;
import com.example.pruebaecommerce.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items;

    private int TYPE_ITEM = 1;

    private int TYPE_EMPTY = 0;


    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ITEM){
            view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent, false);
            return new ItemHolder(view);
        }
        else {
            view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent,false);
            return new EmptyHolder(view);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder){
            ((ItemHolder)holder).bind(items.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.isEmpty()){
            return TYPE_EMPTY;
        }
        else {
            return TYPE_ITEM;
        }
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void clear() {
        if (!items.isEmpty()){
            items.clear();
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.titulo)
        TextView title;
        @BindView(R.id.precio)
        TextView precio;
        @BindView(R.id.image)
        ImageView imageView;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bind(Item item){
            title.setText(item.getTitle());
            precio.setText(String.format(item.getPrice().toString(), Locale.forLanguageTag("es-MX")));
            Picasso.get().load(item.getImage()).into(imageView);
        }
    }

    static class EmptyHolder extends RecyclerView.ViewHolder{

        public EmptyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
