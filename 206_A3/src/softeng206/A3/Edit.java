package softeng206.A3;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class Edit extends Activity {
		private Builder dialogBuilder;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_edit);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			setTitle("Eric Hu");
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.edit, menu);
			return true;
		}
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.action_save:
				Intent intent = new Intent();
				intent.setClass(Edit.this, MainActivity.class);
				startActivity(intent);
				return true;
			case R.id.action_cancel:
				dialogBuilder = new AlertDialog.Builder(Edit.this);
				dialogBuilder.setTitle("Cancel Editing?");
				dialogBuilder.setMessage("Unsaved information will be lost!");
				
				dialogBuilder.setNegativeButton("Cancel", null);
				dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent = new Intent();
						intent.setClass(Edit.this, MainActivity.class);
						startActivity(intent);
						
					}
				} );
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