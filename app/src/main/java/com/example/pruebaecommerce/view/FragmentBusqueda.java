package com.example.pruebaecommerce.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pruebaecommerce.R;
import com.example.pruebaecommerce.data.DemoData;
import com.example.pruebaecommerce.data.retrofit.ClientInteractor;
import com.example.pruebaecommerce.data.retrofit.ClienteIteractorFactory;
import com.example.pruebaecommerce.model.Item;
import com.example.pruebaecommerce.view.adapter.ItemAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class FragmentBusqueda extends Fragment {


    private Context context;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.edBuscar)
    EditText edBuscar;
    @BindView(R.id.loader)
    ProgressBar progressBar;
    private ItemAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private DemoData clientInteractor;
    private LinearLayoutManager layoutManager;


    public FragmentBusqueda() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    public static FragmentBusqueda newInstance() {
        FragmentBusqueda fragment = new FragmentBusqueda();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientInteractor = ClientInteractor.getData();
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view =  inflater.inflate(R.layout.fragment_busqueda, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.hasFixedSize();
        buscar();
        return view;
    }


    @OnClick(R.id.imageButton)
     void buscar(){
        getCompositeDisposable().add(clientInteractor.buscarArticulo(edBuscar.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                })
                .doFinally(() -> progressBar.setVisibility(View.GONE))
                .subscribe(articulo -> {
                    if (recyclerView.getVisibility() == View.GONE) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    getArticulos(articulo.getItems());
                }, throwable -> {

                }));
    }

    private CompositeDisposable getCompositeDisposable(){
        if (compositeDisposable == null){
            compositeDisposable =  new CompositeDisposable();
        }
        return compositeDisposable;
    }

    public void getArticulos(List<Item> items){
        if (adapter == null){
            adapter = new ItemAdapter(items);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else {
            adapter.clear();
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }
}