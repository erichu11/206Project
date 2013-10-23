package softeng206.A3;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class NewContact extends Activity {
	
	private Builder dialogBuilder;
	ContactDatabaseHelper helper = new ContactDatabaseHelper(NewContact.this);
	CustomCursorAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		setTitle("New Contact");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_contact, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			EditText firstName = (EditText) findViewById(R.id.new_contact_first_name);
			EditText lastName = (EditText) findViewById(R.id.new_contact_last_name);
			EditText mobilePh = (EditText) findViewById(R.id.new_contact_mobile_phone);
			EditText homePh = (EditText) findViewById(R.id.new_contact_home_phone);
			EditText workPh = (EditText) findViewById(R.id.new_contact_work_phone);
			EditText email = (EditText) findViewById(R.id.new_contact_email);
			EditText address = (EditText) findViewById(R.id.new_contact_address);
			EditText birthday = (EditText) findViewById(R.id.new_contact_birthday);
			String firstNameInput = firstName.getText().toString();
			String lastNameInput = lastName.getText().toString();
			String mobilePhInput = mobilePh.getText().toString();
			String homePhInput = homePh.getText().toString();
			String workPhInput = workPh.getText().toString();
			String emailInput = email.getText().toString();
			String addressInput = address.getText().toString();
			String birthdayInput = birthday.getText().toString();
			helper.insertData(firstNameInput,lastNameInput,mobilePhInput,homePhInput,workPhInput,emailInput,addressInput,birthdayInput);
			
			Intent intent = new Intent();
			intent.setClass(NewContact.this, MainActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_cancel:
			dialogBuilder = new AlertDialog.Builder(NewContact.this);
			dialogBuilder.setTitle("Cancel Editing?");
			dialogBuilder.setMessage("Unsaved information will be lost!");
			
			dialogBuilder.setNegativeButton("Cancel", null);
			dialogBuilder.setPositiveButton("OK", null);
			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();
			return true;
		case android.R.id.home:
			intent = new Intent();
			intent.setClass(NewContact.this, MainActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
