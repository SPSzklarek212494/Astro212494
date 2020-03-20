package com.example.astro212494;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Zegar extends Fragment{

    private MyActivityListener listener;
    TextView zegar;
    Handler handler = new Handler();
    private Button button_obl;
    private String godzina, minuta, sekunda;
    private EditText dl_geog, szer_geog;
    final String wzorzec = "-?\\d+(\\.\\d+)?";

    public Zegar() {
    }


    void toastowanie(String message)
    {
        Context context = getActivity().getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);

        toast.show();
    }

    public void aktualny_czas() {
        new Thread(new Runnable() {

            public void run() {
                while(true){
                    try {
                        handler.post(new Runnable() {
                           public void run() {

                               Calendar kalendarz = Calendar.getInstance();

                               if(kalendarz.get(Calendar.HOUR_OF_DAY)<10)
                               { godzina = "0"+ kalendarz.get(Calendar.HOUR_OF_DAY); }
                               else{ godzina = Integer.toString(kalendarz.get(Calendar.HOUR_OF_DAY)); }

                               if(kalendarz.get(Calendar.MINUTE)<10)
                               { minuta = "0"+ kalendarz.get(Calendar.MINUTE); }
                               else{ minuta = Integer.toString(kalendarz.get(Calendar.MINUTE)); }

                               if(kalendarz.get(Calendar.SECOND)<10)
                               { sekunda = "0"+ kalendarz.get(Calendar.SECOND); }
                               else{ sekunda = Integer.toString(kalendarz.get(Calendar.SECOND)); }

                               zegar.setText(godzina+":"+minuta+":"+sekunda);
                           }
                        });
                        Thread.sleep(500);
                    }
                    catch (Exception e) {
                        zegar.setText(e.getMessage());
                    }
                }
            }
        }).start();
    }


    // interfejs, który implementuje aktywność
    public interface MyActivityListener {
        public void sendMessage(String msg);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity)context;
        listener = (MyActivityListener) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_zegar, container, false);


        zegar = view.findViewById(R.id.zegar);
        aktualny_czas();

        dl_geog = view.findViewById(R.id.dl_geog);
        szer_geog = view.findViewById(R.id.szer_geog);



        button_obl = view.findViewById(R.id.button_obl);
        button_obl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                if (     dl_geog.getText().length() == 0
                        || szer_geog.getText().length() == 0
                        || String.valueOf(dl_geog).matches(wzorzec)
                        || String.valueOf(szer_geog).matches(wzorzec)
                        || Double.parseDouble(String.valueOf(dl_geog.getText())) > 90.0
                        || Double.parseDouble(String.valueOf(dl_geog.getText())) < -90.0
                        || Double.parseDouble(String.valueOf(szer_geog.getText())) > 90.0
                        || Double.parseDouble(String.valueOf(szer_geog.getText())) < -90.0
                          )
                {
                    toastowanie("BLAD WPROWADZANIA DANYCH -> SPROBUJ JESZCZE RAZ!!!!!!!!!!!");
                    dl_geog.setText("");
                    szer_geog.setText("");
                }
                else{
                    listener.sendMessage(dl_geog.getText().toString() + "_" + szer_geog.getText().toString());
                }



            }
        });

        return view;
    }
}
