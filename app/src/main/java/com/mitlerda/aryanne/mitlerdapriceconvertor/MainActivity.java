package com.mitlerda.aryanne.mitlerdapriceconvertor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mitlerda.aryanne.mitlerdapriceconvertor.data.CouronneType;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.LireType;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.MarkType;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.Monnaie;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.MonnaieType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner fromSpinner;
    Spinner fromSubSpinner;
    Spinner toSpinner;
    Spinner toSubSpinner;
    EditText fromText;
    TextView toText;
    MonnaieType from;
    MonnaieType to;
    Monnaie subFrom;
    Monnaie subTo;
    private ArrayAdapter<String> couronneArrayAdapter;
    private ArrayAdapter<String> lireArrayAdapter;
    private ArrayAdapter<String> markArrayAdapter;

    public ArrayAdapter<String> getCouronneArrayAdapter() {
        return couronneArrayAdapter;
    }

    public ArrayAdapter<String> getLireArrayAdapter() {
        return lireArrayAdapter;
    }

    public ArrayAdapter<String> getMarkArrayAdapter() {
        return markArrayAdapter;
    }

    public MonnaieType getFrom() {
        return from;
    }

    public void setFrom(MonnaieType from) {
        this.from = from;
    }

    public MonnaieType getTo() {
        return to;
    }

    public void setTo(MonnaieType to) {
        this.to = to;
    }

    public Monnaie getSubFrom() {
        return subFrom;
    }

    public void setSubFrom(Monnaie subFrom) {
        this.subFrom = subFrom;
    }

    public Monnaie getSubTo() {
        return subTo;
    }

    public void setSubTo(Monnaie subTo) {
        this.subTo = subTo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromSpinner = (Spinner) findViewById(R.id.from_spinner);
        fromSubSpinner = (Spinner) findViewById(R.id.from_sub_spinner);
        toSpinner = (Spinner) findViewById(R.id.to_spinner);
        toSubSpinner = (Spinner) findViewById(R.id.to_sub_spinner);
        fromText = (EditText) findViewById(R.id.from_value);
        toText = (TextView) findViewById(R.id.to_value);

        fromText.setText("0");

        List<String> monnaieStrs = new ArrayList<>();
        List<String> couronneStrs = new ArrayList<>();
        List<String> lireStrs = new ArrayList<>();
        List<String> markStrs = new ArrayList<>();
        for (MonnaieType monnaieType : MonnaieType.values()) {
            monnaieStrs.add(monnaieType.name());
        }
        for (CouronneType couronneType : CouronneType.values()) {
            couronneStrs.add(couronneType.getNom());
        }
        for (LireType lireType : LireType.values()) {
            lireStrs.add(lireType.getNom());
        }
        for (MarkType markType : MarkType.values()) {
            markStrs.add(markType.getNom());
        }

        ArrayAdapter<String> monaieArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, monnaieStrs);
        couronneArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, couronneStrs);
        lireArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lireStrs);
        markArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, markStrs);


        fromSpinner.setAdapter(monaieArrayAdapter);
        toSpinner.setAdapter(monaieArrayAdapter);

        fromSpinner.setOnItemSelectedListener(new SpinerListener(this, fromSubSpinner, Entry.From));
        toSpinner.setOnItemSelectedListener(new SpinerListener(this, toSubSpinner, Entry.To));

        fromSubSpinner.setOnItemSelectedListener(new SubSpinnerListener(Entry.From));
        toSubSpinner.setOnItemSelectedListener(new SubSpinnerListener(Entry.To));

        fromText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                calcul();
                return false;
            }
        });
    }

    private void calcul() {
        if (subTo != null && subFrom != null) {
            toText.setText(String.valueOf(subTo.fromCouronne(subFrom.toCouronne(Float.parseFloat(fromText.getText().toString())))));
        }
    }

    private enum Entry {
        From, To;
    }

    private class SpinerListener implements AdapterView.OnItemSelectedListener {
        MainActivity mainActivity;
        Spinner subSpinner;
        Entry entry;

        public SpinerListener(MainActivity mainActivity, Spinner subSpinner, Entry entry) {
            this.mainActivity = mainActivity;
            this.subSpinner = subSpinner;
            this.entry = entry;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            ArrayAdapter toUse = null;

            MonnaieType monnaieType = MonnaieType.values()[i];
            Monnaie subMonnaie = null;
            switch (monnaieType) {
                case Couronne:
                    toUse = mainActivity.getCouronneArrayAdapter();
                    subMonnaie = CouronneType.Couronnes;
                    break;
                case Lire:
                    toUse = mainActivity.getLireArrayAdapter();
                    subMonnaie = LireType.LireMonavidienne;
                    break;
                case Mark:
                    toUse = mainActivity.getMarkArrayAdapter();
                    subMonnaie = MarkType.MarkPrartien;
                    break;
            }
            switch (entry) {
                case From:
                    setFrom(monnaieType);
                    setSubFrom(subMonnaie);
                    break;
                case To:
                    setTo(monnaieType);
                    setSubTo(subMonnaie);
            }
            subSpinner.setAdapter(toUse);
            subSpinner.invalidate();
            calcul();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            return;
        }
    }

    private class SubSpinnerListener implements AdapterView.OnItemSelectedListener {

        Entry entry;

        public SubSpinnerListener(Entry entry) {
            this.entry = entry;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Monnaie subFrom = null;
            switch (entry == Entry.From ? getFrom() : getTo()) {
                case Couronne:
                    subFrom = CouronneType.values()[i];
                    break;
                case Lire:
                    subFrom = LireType.values()[i];
                    break;
                case Mark:
                    subFrom = MarkType.values()[i];
                    break;
            }
            switch (entry) {
                case From:
                    setSubFrom(subFrom);
                    break;
                case To:
                    setSubTo(subFrom);
                    break;
            }
            calcul();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            return;
        }
    }
}
