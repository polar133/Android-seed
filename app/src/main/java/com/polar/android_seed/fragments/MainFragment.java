package com.polar.android_seed.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polar.android_seed.R;
import com.polar.android_seed.bus.MyBus;
import com.polar.android_seed.models.data.entities.Usuario;
import com.squareup.otto.Subscribe;


public class MainFragment extends Fragment {

    TextView textUsuario, textNombre, textCorreo, textTelefono;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textUsuario = (TextView) view.findViewById(R.id.usuario_username);
        textNombre = (TextView) view.findViewById(R.id.usuario_nombre);
        textCorreo = (TextView) view.findViewById(R.id.usuario_correo);
        textTelefono = (TextView) view.findViewById(R.id.usuario_telefono);

    }

    @Override
    public void onResume() {
        super.onResume();
        MyBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MyBus.getInstance().unregister(this);
    }


    @Subscribe
    public void onUpdate(Usuario usuario) {
        textUsuario.setText(usuario.getUsername());
        textNombre.setText(usuario.getNombrecompleto());
        textCorreo.setText(usuario.getDireccioncorreo());
        textTelefono.setText(usuario.getTelefonocelular());
    }

}
