package com.polar.android_seed.helpers;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.polar.android_seed.R;
import com.polar.android_seed.adapters.NavigationAdapter;
import com.polar.android_seed.drawer.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerSetup {

    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    List<DrawerItem> dataList;
    String titulo;
    Context context;
    NavigationAdapter adapter;


    public NavigationDrawerSetup(DrawerLayout mDrawerLayout, ListView mDrawerList, Context context, String titulo) {
        this.mDrawerLayout = mDrawerLayout;
        this.mDrawerList = mDrawerList;
        this.context = context;
        this.titulo = titulo;
    }

    public void configureDrawer() {
        dataList = IniciarDrawer();
        adapter = new NavigationAdapter(context, R.layout.navegador_item, dataList);
        mDrawerList.setAdapter(adapter);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abrirActivity(i);
            }
        });

    }

    private void abrirActivity(int posicion) {
        switch (posicion) {
            case 0:
                mDrawerLayout.closeDrawer(mDrawerList);
                mDrawerList.setItemChecked(-1, true);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(context.getResources().getString(R.string.app_name));
                builder.setMessage(context.getResources().getString(R.string.opciones_cerrar_sesion));

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogoutHelper.getInstance().CerrarSesion();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
    }

    private List<DrawerItem> IniciarDrawer() {
        List<DrawerItem> dataList = new ArrayList<>();
        String[] titulos = context.getResources().getStringArray(R.array.nav_opciones);

        for (String titulo : titulos) {
            dataList.add(new DrawerItem(titulo));
        }

        return dataList;
    }

}
