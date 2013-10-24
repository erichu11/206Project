package softeng206.A3;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {
	private Button add;
	private Spinner spinner;
	private ListView listView;
	private ImageView image;
	private EditText searchBar;
	private Builder dialogBuilder;
	ContactDatabaseHelper helper;
	CustomCursorAdapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		add = (Button) findViewById(R.id.action_add);
		listView = (ListView) findViewById(R.id.names);
		searchBar = (EditText) findViewById(R.id.searchBar);
		spinner = (Spinner) findViewById(R.id.action_sort_spinner);
		
//		addPhoto.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				Intent i = new Intent(
//						Intent.ACTION_PICK,
//						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//				startActivityForResult(i, RESULT_LOAD_IMAGE);
//			}
//		});
		setUpListView();
	}

	

	public void setUpListView() {
		helper = new ContactDatabaseHelper(this);
		adapter = new CustomCursorAdapter(this, helper.getAllData());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new listItemClickedListener());

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.action_add:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, NewContact.class);
			startActivity(intent);
			return true;
		case R.id.action_sort_spinner:

			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
					MainActivity.this);
			dialogBuilder.setTitle("Sort by: ");
			dialogBuilder.setSingleChoiceItems(new String[] { "First Name",
					"Last Name", "Mobile Phone", "Home Phone", "Work Phone" },
					-1, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								helper = new ContactDatabaseHelper(
										MainActivity.this);
								adapter = new CustomCursorAdapter(
										MainActivity.this, helper
												.order("firstName"));
								listView.setAdapter(adapter);
								break;
							case 1:
								helper = new ContactDatabaseHelper(
										MainActivity.this);
								adapter = new CustomCursorAdapter(
										MainActivity.this, helper
												.order("lastName"));
								listView.setAdapter(adapter);
								break;
							case 2:
								helper = new ContactDatabaseHelper(
										MainActivity.this);
								adapter = new CustomCursorAdapter(
										MainActivity.this, helper
												.order("mobilePhone"));
								listView.setAdapter(adapter);
								break;
							case 3:
								helper = new ContactDatabaseHelper(
										MainActivity.this);
								adapter = new CustomCursorAdapter(
										MainActivity.this, helper
												.order("homePhone"));
								listView.setAdapter(adapter);
								break;
							case 4:
								helper = new ContactDatabaseHelper(
										MainActivity.this);
								adapter = new CustomCursorAdapter(
										MainActivity.this, helper
												.order("workPhone"));
								listView.setAdapter(adapter);
								break;
							}
							dialog.dismiss();
						}
					});
			dialogBuilder.setNegativeButton("Cancel", null);
			dialogBuilder.setCancelable(true);
			dialogBuilder.create().show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		setTitle(R.string.title_main); // set title of action bar.
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class listItemClickedListener implements AdapterView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parentView, View childView,
				int childViewPosition, long id) {

			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SingleContact.class);
			intent.putExtra("the_key", id);
			startActivity(intent);

		}
	}
}
