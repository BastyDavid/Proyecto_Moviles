package com.example.basty.moviles_proyect.parte_interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.basty.moviles_proyect.R;


//fragment para la lista de tareas que seran finalisadas puede mostrar y borrar las tareas terminadas
public class TareasFinalizadasFragment extends PageFragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tareas_terminadas, container, false);
        setListView(view);
        setAdapter();
        registerForContextMenu(listView);
        final int finished = 1;
        fillListView(finished);
        setHasOptionsMenu(true);
        return view;
    }

    //establece el listview
    @Override
    protected void setListView(View view) {
        listView = (ListView) view.findViewById(R.id.finishedTasksList);
    }

    //establece el adapatador del listview
    @Override
    protected void setAdapter() {
        listView.setAdapter(getAdapter());
    }

}
