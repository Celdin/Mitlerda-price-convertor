package com.mitlerda.aryanne.mitlerdapriceconvertor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mitlerda.aryanne.mitlerdapriceconvertor.data.CouronneDivision;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.CouronneType;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.Division;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.LireDivision;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.LireType;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.MarkDivision;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.MarkType;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.Monnaie;
import com.mitlerda.aryanne.mitlerdapriceconvertor.data.MonnaieType;
import com.mitlerda.aryanne.mitlerdapriceconvertor.view.FromEditor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner fromSpinner;
    Spinner fromSubSpinner;
    Spinner toSpinner;
    Spinner toSubSpinner;
    FromEditor fromEditor;
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
        fromEditor = (FromEditor) findViewById(R.id.from_value);
        toText = (TextView) findViewById(R.id.to_value);

        final ImageButton swapButton = (ImageButton) findViewById(R.id.swap_button);

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
                R.layout.spinner_item, monnaieStrs);
        couronneArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.sub_spinner_item, couronneStrs);
        lireArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.sub_spinner_item, lireStrs);
        markArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.sub_spinner_item, markStrs);


        fromSpinner.setAdapter(monaieArrayAdapter);
        toSpinner.setAdapter(monaieArrayAdapter);

        fromSpinner.setOnItemSelectedListener(new SpinerListener(this, fromSubSpinner, Entry.From));
        toSpinner.setOnItemSelectedListener(new SpinerListener(this, toSubSpinner, Entry.To));

        fromSubSpinner.setOnItemSelectedListener(new SubSpinnerListener(Entry.From));
        toSubSpinner.setOnItemSelectedListener(new SubSpinnerListener(Entry.To));

        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tmpPos = toSpinner.getSelectedItemPosition();
                int tmpSubFromPos = fromSubSpinner.getSelectedItemPosition();
                int tmpSubToPos = toSubSpinner.getSelectedItemPosition();
                toSpinner.setSelection(fromSpinner.getSelectedItemPosition());
                fromSpinner.setSelection(tmpPos);
                toSpinner.getOnItemSelectedListener().onItemSelected(null, null, toSpinner.getSelectedItemPosition(), -1);
                fromSpinner.getOnItemSelectedListener().onItemSelected(null, null, fromSpinner.getSelectedItemPosition(), -1);
                fromSubSpinner.setSelection(tmpSubToPos);
                toSubSpinner.setSelection(tmpSubFromPos);
            }
        });
    }

    private void calcul() {
        if (subTo != null && subFrom != null) {
            BigDecimal value = subTo.fromCouronne(subFrom.toCouronne(fromEditor.getvalue()));
            value.setScale(5, BigDecimal.ROUND_HALF_UP);
            String result = "";
            switch (to){
                case Couronne:
                    for(CouronneDivision division : CouronneDivision.values()){
                        Result result1 = new Result(value, result, division, division.name()).invoke();
                        value = result1.getValue();
                        result = result1.getResult();
                    }
                    break;
                case Lire:
                    for(LireDivision division : LireDivision.values()){
                        Result result1 = new Result(value, result, division, division.name()).invoke();
                        value = result1.getValue();
                        result = result1.getResult();
                    }
                    break;
                case Mark:
                    for(MarkDivision division : MarkDivision.values()){
                        Result result1 = new Result(value, result, division, division.name()).invoke();
                        value = result1.getValue();
                        result = result1.getResult();
                    }
                    break;
            }
            toText.setText(result);
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
            if(!toUse.equals(subSpinner.getAdapter())) {
                switch (entry) {
                    case From:
                        setFrom(monnaieType);
                        setSubFrom(subMonnaie);
                        fromEditor.updateField(monnaieType.getBaseDivision(), new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                                calcul();
                                return false;
                            }
                        });
                        break;
                    case To:
                        setTo(monnaieType);
                        setSubTo(subMonnaie);
                }
                subSpinner.setAdapter(toUse);
                subSpinner.invalidate();
                calcul();
            }
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

    private class Result {
        private BigDecimal value;
        private String result;
        private Division division;
        private String name;

        public Result(BigDecimal value, String result, Division division, String name) {
            this.value = value;
            this.result = result;
            this.division = division;
            this.name = name;
        }

        public BigDecimal getValue() {
            return value;
        }

        public String getResult() {
            return result;
        }

        public Result invoke() {
            value = division.fromReference(value);
            final int entier = value.intValue();
            if(entier >0){
                result += entier + " " + name + (entier>1?"s ":" ");
                value = value.subtract(BigDecimal.valueOf(entier));
            }
            value = division.toReference(value);
            return this;
        }
    }
}
