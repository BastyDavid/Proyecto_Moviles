package com.example.basty.moviles_proyect.parte_interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;



  class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabTitles = new ArrayList<>();


    //agregamos el fragment a la pager list
    void addFragments(Fragment fragment, String titles) {
        this.fragments.add(fragment); //fragmento agregar
        this.tabTitles.add(titles); //titulo del fragment
    }


     ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //obtenemos el fragment con posicion
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

      //obtenemos la cantidad de fragment de pagina
    @Override
    public int getCount() {
        return fragments.size();
    }

      //titulo de page
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}