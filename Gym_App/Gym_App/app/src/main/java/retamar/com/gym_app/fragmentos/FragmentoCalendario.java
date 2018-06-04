package retamar.com.gym_app.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.EjerciciosHolder;
import retamar.com.gym_app.adaptadores.FirebaseAdapter;
import retamar.com.gym_app.utils.Ejercicios;

public class FragmentoCalendario extends Fragment {

    View v;
    Context contexto;
    CalendarListener listener;
    CalendarView calendar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contexto = context;
        listener = (CalendarListener) contexto;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragmento_calendario, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instancias();
        acciones();
    }


    private void instancias() {
        calendar = v.findViewById(R.id.calendar);
    }


    private void acciones() {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, i);
                cal.set(Calendar.MONTH, i1);
                cal.set(Calendar.DAY_OF_MONTH, i2);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String dateTime = dateFormat.format(cal.getTime());

                listener.onDateRangeSelected(dateTime);
            }
        });

    }

    public interface CalendarListener {
        void onDateRangeSelected(String date);
    }
}
