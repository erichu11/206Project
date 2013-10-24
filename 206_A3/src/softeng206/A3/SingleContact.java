package softeng206.A3;

import java.sql.Blob;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleContact extends Activity {
	ContactDatabaseHelper helper = new ContactDatabaseHelper(this);
	long id;
	Contact contact;
	byte[] bytes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_contact);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent newActivity = getIntent();
		id = newActivity.getLongExtra("the_key", -1);

		Cursor cur = helper.getSelectedData(String.valueOf(id));
		if (cur.moveToFirst()) {
			String dafirst = cur.getString(cur.getColumnIndex(cur
					.getColumnName(1)));
			String dalast = cur.getString(cur.getColumnIndex(cur
					.getColumnName(2)));
			String damobile = cur.getString(cur.getColumnIndex(cur
					.getColumnName(3)));
			String dahome = cur.getString(cur.getColumnIndex(cur
					.getColumnName(4)));
			String dawork = cur.getString(cur.getColumnIndex(cur
					.getColumnName(5)));
			String daemail = cur.getString(cur.getColumnIndex(cur
					.getColumnName(6)));
			String daaddress = cur.getString(cur.getColumnIndex(cur
					.getColumnName(7)));
			String dabirthday = cur.getString(cur.getColumnIndex(cur
					.getColumnName(8)));
			bytes = cur.getBlob(cur.getColumnIndex(cur.getColumnName(9)));

			contact = new Contact(dafirst, dalast, damobile, dahome, dawork,
					daemail, daaddress, dabirthday, bytes);
		}

		TextView firstName = (TextView) findViewById(R.id.single_contact_first_name);
		TextView lastName = (TextView) findViewById(R.id.single_contact_last_name);
		TextView mobilePh = (TextView) findViewById(R.id.single_contact_mobilePh);
		TextView homePh = (TextView) findViewById(R.id.single_contact_homePh);
		TextView workPh = (TextView) findViewById(R.id.single_contact_workPh);
		TextView email = (TextView) findViewById(R.id.single_contact_email);
		TextView address = (TextView) findViewById(R.id.single_contact_address);
		TextView birthday = (TextView) findViewById(R.id.single_contact_birthday);
		ImageView image = (ImageView) findViewById(R.id.contact_photo);

		firstName.setText(contact.getFirstName());
		lastName.setText(contact.getLastName());
		mobilePh.setText(contact.getMobilePh());
		homePh.setText(contact.getHomePh());
		workPh.setText(contact.getWorkPh());
		email.setText(contact.getEmailAddress());
		address.setText(contact.getHomeAddress());
		birthday.setText(contact.getBirthday());
		if (contact.getPhoto() != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(contact.getPhoto(), 0,
					contact.getPhoto().length);
			image.setImageBitmap(bmp);
		}
	}

	public void delete() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				SingleContact.this);
		dialogBuilder.setTitle("Delete this contact?");
		dialogBuilder.setMessage("This cannot be undone!");

		dialogBuilder.setNegativeButton("Cancel", null);
		dialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						helper.delete(id);
						CustomCursorAdapter adapter = new CustomCursorAdapter(
								SingleContact.this, helper.getAllData());
						Intent intent = new Intent();
						intent.setClass(SingleContact.this, MainActivity.class);
						startActivity(intent);
						finish();

					}
				});
		dialogBuilder.setCancelable(true);
		dialogBuilder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		setTitle(contact.getName());
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_contact, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			Intent intent = new Intent();
			intent.setClass(SingleContact.this, Edit.class);
			intent.putExtra("the_key", id);
			startActivity(intent);
			return true;
		case R.id.action_delete:
			delete();
			return true;
		case android.R.id.home:
			intent = new Intent();
			intent.setClass(SingleContact.this, MainActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
