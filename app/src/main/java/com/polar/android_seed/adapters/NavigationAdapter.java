package com.polar.android_seed.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.polar.android_seed.R;
import com.polar.android_seed.drawer.DrawerItem;

import java.util.List;


public class NavigationAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public NavigationAdapter(Context context, int layoutResID, List<DrawerItem> listItems) {
        super(context, layoutResID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_texto);
            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        DrawerItem dItem = this.drawerItemList.get(position);
        drawerHolder.ItemName.setText(dItem.getTitulo());

        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
    }

}
