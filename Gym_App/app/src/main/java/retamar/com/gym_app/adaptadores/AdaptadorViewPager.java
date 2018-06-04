package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import retamar.com.gym_app.R;
import retamar.com.gym_app.fragmentos.FragmentoCalendario;
import retamar.com.gym_app.fragmentos.FragmentoEjercicios;
import retamar.com.gym_app.fragmentos.FragmentoPerfil;

public class AdaptadorViewPager extends FragmentPagerAdapter {

    private final static int NUM_VIEWS = 3;

     private Context contexto;

    public AdaptadorViewPager(FragmentManager fm, Context contexto) {
        super(fm);
        this.contexto = contexto;
    }

    // Determina el fragmento para cada pestaña pulsada
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  new FragmentoEjercicios();
            case 1:
                return  new FragmentoCalendario();
            case 2:
                return  new FragmentoPerfil();

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_VIEWS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       switch (position) {
            case 0:
                return contexto.getString(R.string.fragmento_ejercicios);
            case 1:
                return contexto.getString(R.string.fragmento_actividad);
            case 2:
                return contexto.getString(R.string.fragmento_perfil);

                default:
                    return null;
        }
    }
}
