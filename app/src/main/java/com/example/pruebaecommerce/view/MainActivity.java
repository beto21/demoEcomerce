package com.example.pruebaecommerce.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.pruebaecommerce.R;
import com.example.pruebaecommerce.view.FragmentBusqueda;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm realm = Realm.getDefaultInstance();

        addFragment();
    }


    private void addFragment(){
        FragmentBusqueda fragmentBusqueda = FragmentBusqueda.newInstance();
        if (!fragmentBusqueda.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragmentBusqueda, FragmentBusqueda.class.getName());
            transaction.commit();
        }
    }


}