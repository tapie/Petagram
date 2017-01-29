package etg.com.petagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        contactMapper();
        ((TextView) findViewById(R.id.tvFullName)).setText(contact.getFullName());
        ((TextView) findViewById(R.id.tvBirthDate)).setText(contact.getBirthDate());
        ((TextView) findViewById(R.id.tvPhoneNumber)).setText(contact.getPhoneNumber());
        ((TextView) findViewById(R.id.tvEmail)).setText(contact.getEmail());
        ((TextView) findViewById(R.id.tvDescription)).setText(contact.getDescription());
    }

    private void contactMapper() {
        try {
            Bundle params = getIntent().getExtras();
            contact.setFullName(params.getString(getResources().getString(R.string.pFullName)));
            contact.setBirthDate(params.getString(getResources().getString(R.string.pBirthDate)));
            contact.setPhoneNumber(params.getString(getResources().getString(R.string.pPhoneNumber)));
            contact.setEmail(params.getString(getResources().getString(R.string.pEmail)));
            contact.setDescription(params.getString(getResources().getString(R.string.pDescription)));
        } catch (Exception e) {
            new Exception("Ha ocurrido un error en el mapeo del contacto: " + e.getMessage());
        }
    }

    public void editInfo(View v) {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.putExtra(getResources().getString(R.string.pFullName), contact.getFullName());
        intent.putExtra(getResources().getString(R.string.pBirthDate), contact.getBirthDate());
        intent.putExtra(getResources().getString(R.string.pPhoneNumber), contact.getPhoneNumber());
        intent.putExtra(getResources().getString(R.string.pEmail), contact.getEmail());
        intent.putExtra(getResources().getString(R.string.pDescription), contact.getDescription());
        intent.putExtra(getResources().getString(R.string.pProcess), "edit");
        startActivity(intent);
    }
}
