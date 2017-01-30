package etg.com.petagram;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.DatePicker;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Contact contact;
    DatePickerDialog fechaNacimiento;
    TextInputEditText tvFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contact = new Contact();
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tvFechaNacimiento = (TextInputEditText) findViewById(R.id.txtBirthdate);
        tvFechaNacimiento.setOnClickListener(this);
        fechaNacimiento = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvFechaNacimiento.setText(sdf.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        Bundle params = getIntent().getExtras();
        if (params != null) {
            if (!params.getString(getResources().getString(R.string.pProcess)).isEmpty()) {
                if (params.getString(getResources().getString(R.string.pProcess)).equalsIgnoreCase("edit")) {
                    contactMapper(params);
                    String birthDate[] = contact.getBirthDate().split("/");
                    ((TextInputEditText) findViewById(R.id.txtFullName)).setText(contact.getFullName());
                    ((TextInputEditText) findViewById(R.id.txtBirthdate)).setText(contact.getBirthDate());
                    ((TextInputEditText) findViewById(R.id.txtPhoneNumber)).setText(contact.getPhoneNumber());
                    ((TextInputEditText) findViewById(R.id.txtEmail)).setText(contact.getEmail());
                    ((TextInputEditText) findViewById(R.id.txtDescription)).setText(contact.getDescription());

                }
            }
        }
    }

    public void onClick(View v) {
        if (v == tvFechaNacimiento) {
            /*DialogFragment fragment = new DatePickerFragment();
            fragment.show(getFragmentManager(), "datePicker");*/
            if (contact != null) {
                if (contact.getBirthDate() != null) {
                    if (contact.getBirthDate().contains("/")) {
                        String fecha[] = contact.getBirthDate().split("/");
                        fechaNacimiento.updateDate(Integer.valueOf(fecha[2]), Integer.valueOf(fecha[1]), Integer.valueOf(fecha[0]));
                    }
                }
            }
            fechaNacimiento.show();
        }
    }

    public void nextActivity(View v) {
        captureInfo();
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(getResources().getString(R.string.pFullName), contact.getFullName());
        intent.putExtra(getResources().getString(R.string.pBirthDate), contact.getBirthDate());
        intent.putExtra(getResources().getString(R.string.pPhoneNumber), contact.getPhoneNumber());
        intent.putExtra(getResources().getString(R.string.pEmail), contact.getEmail());
        intent.putExtra(getResources().getString(R.string.pDescription), contact.getDescription());
        startActivity(intent);
    }

    private void captureInfo() {
        try {
            contact.setFullName(((TextInputEditText) findViewById(R.id.txtFullName)).getText().toString());
            contact.setBirthDate(((TextInputEditText) findViewById(R.id.txtBirthdate)).getText().toString());
            contact.setPhoneNumber(((TextInputEditText) findViewById(R.id.txtPhoneNumber)).getText().toString());
            contact.setEmail(((TextInputEditText) findViewById(R.id.txtEmail)).getText().toString());
            contact.setDescription(((TextInputEditText) findViewById(R.id.txtDescription)).getText().toString());
        } catch (Exception e) {
            new Exception("Ha ocurrido un error al capturar la información: " + e.getMessage());
        }
    }

    private void contactMapper(Bundle params) {
        try {
            contact.setFullName(params.getString(getResources().getString(R.string.pFullName)));
            contact.setBirthDate(params.getString(getResources().getString(R.string.pBirthDate)));
            contact.setPhoneNumber(params.getString(getResources().getString(R.string.pPhoneNumber)));
            contact.setEmail(params.getString(getResources().getString(R.string.pEmail)));
            contact.setDescription(params.getString(getResources().getString(R.string.pDescription)));
        } catch (Exception e) {
            new Exception("Ha ocurrido un error en el mapeo del contacto: " + e.getMessage());
        }
    }


}
