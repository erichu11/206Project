package softeng206.A3;

import java.io.ByteArrayOutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Edit extends Activity {
	private Builder dialogBuilder;
	long id;
	private Contact contact;
	private ContactDatabaseHelper helper = new ContactDatabaseHelper(this);
	private EditText firstName;
	private EditText lastName;
	private EditText mobilePh;
	private EditText homePh;
	private EditText workPh;
	private EditText email;
	private EditText address;
	private EditText birthday;
	private byte[] bytes;
	private static int SELECT_PICTURE = 1;
	private String selectedImagePath;
	private ImageView imageView;
	private Bitmap bmp;
	private boolean photoChanged;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent newActivity = getIntent();
		id = newActivity.getLongExtra("the_key", -1);
		Cursor cur = helper.getSelectedData(String.valueOf(id)); //get the cursor of clicked contact by pass id

		imageView = (ImageView) findViewById(R.id.edit_contact_photo);
		imageView.setImageResource(R.drawable.default_photo);
		Button editPhoto = (Button) findViewById(R.id.edit_photo_button);
		editPhoto.setOnClickListener(new View.OnClickListener() { //if edit photo button clicked, pop out a window and let user choose a photo from gallery.
					public void onClick(View arg0) { 

						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(
								Intent.createChooser(intent, "Select Picture"),
								SELECT_PICTURE);

					}
				});
		if (cur.moveToFirst()) { // get parameters from clicked contact
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

			String dadob = cur.getString(cur.getColumnIndex(cur
					.getColumnName(8)));
			bytes = cur.getBlob(cur.getColumnIndex(cur.getColumnName(9)));

			contact = new Contact(dafirst, dalast, damobile, dahome, dawork,
					daemail, daaddress, dadob, bytes);
		}
		// link the layouts.
		firstName = (EditText) findViewById(R.id.edit_contact_first_name);
		lastName = (EditText) findViewById(R.id.edit_contact_last_name);
		mobilePh = (EditText) findViewById(R.id.edit_contact_mobilePh);
		homePh = (EditText) findViewById(R.id.edit_contact_homePh);
		workPh = (EditText) findViewById(R.id.edit_contact_workPh);
		email = (EditText) findViewById(R.id.edit_contact_email);
		address = (EditText) findViewById(R.id.edit_contact_address);
		birthday = (EditText) findViewById(R.id.edit_contact_birthday);
		//set the information from contact into the layout.
		firstName.setText(contact.getFirstName());
		lastName.setText(contact.getLastName());
		mobilePh.setText(contact.getMobilePh());
		homePh.setText(contact.getHomePh());
		workPh.setText(contact.getWorkPh());
		email.setText(contact.getEmailAddress());
		address.setText(contact.getHomeAddress());
		birthday.setText(contact.getBirthday());
		if (contact.getPhoto() != null) {	//if the byte[] is not null, make it into a bitmap and set it as the contact photo
			Bitmap bmp = BitmapFactory.decodeByteArray(contact.getPhoto(), 0,
					contact.getPhoto().length);
			imageView.setImageBitmap(bmp);
		}

	}
/*
 * find the image path of the selected image
 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				photoChanged=true;
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				imageView.setImageURI(selectedImageUri);
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		setTitle("Edit");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
/*
 * dertermine the actions when buttons in menu bar are clicked.
 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			String firstNameInput = firstName.getText().toString();
			String lastNameInput = lastName.getText().toString();
			String mobilePhInput = mobilePh.getText().toString();
			String homePhInput = homePh.getText().toString();
			String workPhInput = workPh.getText().toString();
			String emailInput = email.getText().toString();
			String addressInput = address.getText().toString();
			String birthdayInput = birthday.getText().toString();
			
			if(photoChanged){
			bmp = BitmapFactory.decodeFile(selectedImagePath);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			bytes = stream.toByteArray();
			}
			helper.updateRow(id, firstNameInput, lastNameInput, mobilePhInput,
					homePhInput, workPhInput, emailInput, addressInput,
					birthdayInput, bytes);

			Intent intent = new Intent();
			intent.setClass(Edit.this, MainActivity.class);
			startActivity(intent);
			finish();
			return true;
		case R.id.action_cancel:
			dialogBuilder = new AlertDialog.Builder(Edit.this);
			dialogBuilder.setTitle("Cancel Editing?");
			dialogBuilder.setMessage("Unsaved information will be lost!");

			dialogBuilder.setNegativeButton("Cancel", null);
			dialogBuilder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							Intent intent = new Intent();
							intent.setClass(Edit.this, MainActivity.class);
							startActivity(intent);

						}
					});
			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();
			return true;
		case android.R.id.home:
			intent = new Intent();
			intent.setClass(Edit.this, MainActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}