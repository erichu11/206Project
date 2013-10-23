package softeng206.A3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {
	
	public CustomCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}
	
	private Context context;

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View listItemView = inflater.inflate(R.layout.first_last_name, null);
		return listItemView;
	}
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView firstName = (TextView)view.findViewById(R.id.contactList_firstName);
 		TextView lastName = (TextView)view.findViewById(R.id.contactList_lastName);
 		
 		// Set the text for each textview (use the position argument to find the appropriate element in the list)
 		firstName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
 		lastName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
		
	}
}
