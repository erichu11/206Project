package softeng206.A3;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class SingleContact extends Activity {
	
	public void delete(){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SingleContact.this);
		dialogBuilder.setTitle("Delete this contact?");
		dialogBuilder.setMessage("This cannot be undone!");
		
		dialogBuilder.setNegativeButton("Cancel", null);
		dialogBuilder.setPositiveButton("OK", null);
		dialogBuilder.setCancelable(true);
		dialogBuilder.create().show();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_contact);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		setTitle("Eric Hu");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_contact, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			Intent intent = new Intent();
			intent.setClass(SingleContact.this, Edit.class);
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
