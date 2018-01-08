package com.example.basty.moviles_proyect.parte_interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.basty.moviles_proyect.R;
import com.example.basty.moviles_proyect.Datos.Tarea;

import java.util.ArrayList;
import java.util.List;


//clase para fragmento controlado por el pageAdapter haciendo más sencillo agregar nuevos elementos
//se puede eliminar una tarea, rellenar listview leyendo la base de datos

public abstract class PageFragment extends Fragment {



    private TareaAdapter adapter = new TareaAdapter();
    private List<Tarea> tmpTasks = new ArrayList<>();

    //mantenemos una referencia de todas las tareas para realizar la busque
    private boolean searched = false;


    //establece la listview para el fragmento
    protected abstract void setListView(View view);


    //se establece el adapter para una especifica listview
    protected abstract void setAdapter();

  //borramos la tarea por su id
    protected void delete(int id) {
        adapter.delete(getContext(), id);
    }


    public TareaAdapter getAdapter() {
        return this.adapter;
    }


    //leemos los datos validos de la base de datos tareas activas o terminadas
    protected void fillListView(int state) {
        adapter.readAllOfState(getContext(), state);
    }



    //obtenemos el item que se selecciono y obtenemos su posicion para quitarlo del adapter
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (!getUserVisibleHint()) return false;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if (id == R.id.delete) {
            delete(getAdapter().find(info.position).getID());
            return true; //se regresa verdadero si es borrado
        }
        return super.onContextItemSelected(item);
    }



    // creacion barra de busqueda
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) //menu = menu del fragment
    {

        inflater.inflate(R.menu.menu, menu);
        saveAllCurrentTasks();
        setSearchBar(menu);

    }

    //se establece una barra de busqueda
    //se busca las tareas y se muestran en la listview
    private void setSearchBar(Menu menu) {
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Tarea> matches = new ArrayList<>();
                for (Tarea task : tmpTasks) {
                    if (task.getTitle().contains(newText)) {
                        matches.add(task);
                    }
                }
                adapter.setMatches(matches);
                return true;
            }
        });
    }


    //guardado de todas las tareas
    public void saveAllCurrentTasks() {
        tmpTasks.clear();
        tmpTasks.addAll(adapter.getAll());
    }
}
