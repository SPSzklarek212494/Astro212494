package com.example.astro212494;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

public class MainActivity extends AppCompatActivity implements Zegar.MyActivityListener {

    private ViewPager strona;
    private PagerAdapter stronaAdapter;

    private TextView sun_czas_wsch;
    private TextView sun_azymut_wsch;

    private TextView sun_czas_zach;
    private TextView sun_azymut_zach;

    private TextView sun_swit;
    private TextView sun_zmierzch;


    private TextView moon_wsch;
    private TextView moon_zach;

    private TextView moon_now;
    private TextView moon_pelnia;

    private TextView moon_faza;
    private TextView moon_dzien;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> lista_podstron = new ArrayList<>();
        lista_podstron.add(new Slonce());
        lista_podstron.add(new Zegar());
        lista_podstron.add(new Ksiezyc());

        strona = findViewById(R.id.MainVievPager);
        stronaAdapter = new Adapter_zmiany_stron(getSupportFragmentManager(), lista_podstron);
        strona.setAdapter(stronaAdapter);
        strona.setCurrentItem(1);


    }

    String przetwarzanie_liczby(Integer liczba)
    {
        if(liczba<10){
            return "0"+Integer.toString(liczba);
        }
        else{
            return Integer.toString(liczba);
        }
    }

    @Override
    public void sendMessage(String msg) {
        String dl_geog = msg.split("_")[0];
        String szer_geog = msg.split("_")[1];

        AstroDateTime czas = new AstroDateTime(
                Integer.parseInt(new SimpleDateFormat("yyyy", Locale.ENGLISH).format(System.currentTimeMillis())),
                Integer.parseInt(new SimpleDateFormat("MM", Locale.ENGLISH).format(System.currentTimeMillis())),
                Integer.parseInt(new SimpleDateFormat("dd", Locale.ENGLISH).format(System.currentTimeMillis())),
                Integer.parseInt(new SimpleDateFormat("hh", Locale.GERMANY).format(System.currentTimeMillis())),
                Integer.parseInt(new SimpleDateFormat("mm", Locale.GERMANY).format(System.currentTimeMillis())),
                Integer.parseInt(new SimpleDateFormat("ss", Locale.GERMANY).format(System.currentTimeMillis())),
                1,
                true
        );
        AstroCalculator.Location lokacja = new AstroCalculator.Location(Double.valueOf(szer_geog), Double.valueOf(dl_geog));
        AstroCalculator kalkulator = new AstroCalculator(czas,lokacja);
        String lancuch;

        sun_czas_wsch = findViewById(R.id.sun_czas_wsch);
        lancuch = przetwarzanie_liczby(kalkulator.getSunInfo().getSunrise().getHour()-1)
                +":"+przetwarzanie_liczby(kalkulator.getSunInfo().getSunrise().getMinute())
                +":"+przetwarzanie_liczby(kalkulator.getSunInfo().getSunrise().getSecond());
        sun_czas_wsch.setText(lancuch);

        sun_azymut_wsch = findViewById(R.id.sun_azymut_wsch);
        sun_azymut_wsch.setText( Double.toString(kalkulator.getSunInfo().getAzimuthRise()) );

        sun_czas_zach = findViewById(R.id.sun_czas_zach);
        lancuch = przetwarzanie_liczby(kalkulator.getSunInfo().getSunset().getHour()-1)
                +":"+przetwarzanie_liczby(kalkulator.getSunInfo().getSunset().getMinute())
                +":"+przetwarzanie_liczby(kalkulator.getSunInfo().getSunset().getSecond());
        sun_czas_zach.setText(lancuch);

        sun_azymut_zach = findViewById(R.id.sun_azymut_zach);
        sun_azymut_zach.setText( Double.toString(kalkulator.getSunInfo().getAzimuthSet()) );

        sun_swit = findViewById(R.id.sun_swit);
        lancuch = przetwarzanie_liczby(kalkulator.getSunInfo().getTwilightMorning().getHour()-1)
                +":"+przetwarzanie_liczby(kalkulator.getSunInfo().getTwilightMorning().getMinute())
                +":"+przetwarzanie_liczby(kalkulator.getSunInfo().getTwilightMorning().getSecond());
        sun_swit.setText(lancuch);

        sun_zmierzch = findViewById(R.id.sun_zmierzch);
        lancuch = przetwarzanie_liczby(kalkulator.getSunInfo().getTwilightEvening().getHour()-1)
                +":"+przetwarzanie_liczby(kalkulator.getSunInfo().getTwilightEvening().getMinute())
                +":"+przetwarzanie_liczby(kalkulator.getSunInfo().getTwilightEvening().getSecond());
        sun_zmierzch.setText(lancuch);




        moon_zach = findViewById(R.id.moon_zach);
        lancuch = przetwarzanie_liczby(kalkulator.getMoonInfo().getMoonrise().getHour()-1)
                +":"+przetwarzanie_liczby(kalkulator.getMoonInfo().getMoonrise().getMinute())
                +":"+przetwarzanie_liczby(kalkulator.getMoonInfo().getMoonrise().getSecond());
        moon_zach.setText(lancuch);

        moon_wsch = findViewById(R.id.moon_wsch);
        lancuch = przetwarzanie_liczby(kalkulator.getMoonInfo().getMoonset().getHour()-1)
                +":"+przetwarzanie_liczby(kalkulator.getMoonInfo().getMoonset().getMinute())
                +":"+przetwarzanie_liczby(kalkulator.getMoonInfo().getMoonset().getSecond());
        moon_wsch.setText(lancuch);

        moon_now = findViewById(R.id.moon_now);
        lancuch = przetwarzanie_liczby(kalkulator.getMoonInfo().getNextNewMoon().getDay())
                +"-"+przetwarzanie_liczby(kalkulator.getMoonInfo().getNextNewMoon().getMonth())
                +"-"+przetwarzanie_liczby(kalkulator.getMoonInfo().getNextNewMoon().getYear());
        moon_now.setText(lancuch);

        moon_pelnia = findViewById(R.id.moon_pelnia);
        lancuch = przetwarzanie_liczby(kalkulator.getMoonInfo().getNextFullMoon().getDay())
                +"-"+przetwarzanie_liczby(kalkulator.getMoonInfo().getNextFullMoon().getMonth())
                +"-"+przetwarzanie_liczby(kalkulator.getMoonInfo().getNextFullMoon().getYear());
        moon_pelnia.setText(lancuch);

        moon_faza = findViewById(R.id.moon_faza);

        moon_faza.setText(Double.toString(kalkulator.getMoonInfo().getIllumination()*100.0));

        moon_dzien = findViewById(R.id.moon_dzien);
        moon_dzien.setText(Integer.toString((int)kalkulator.getMoonInfo().getAge()));




    }
}

//--------------------------------------------------------------------------------------------------

class Adapter_zmiany_stron extends FragmentStatePagerAdapter {

    private List<Fragment> lista_podstron;

    public Adapter_zmiany_stron(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.lista_podstron = fragmentList;
    }

    @Override
    public Fragment getItem(int pozycja)
    {
        return lista_podstron.get(pozycja);
    }

    @Override
    public int getCount()
    {
        return lista_podstron.size();
    }
}