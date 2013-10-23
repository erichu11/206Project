package softeng206.A3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDatabaseHelper extends SQLiteOpenHelper{
	
	public static final int DATABAE_VERSION = 1;
	public static final String DATABASE_NAME = "SE206Lab3Database.db";
	public static final String TABLE_CONTACTS = "CarsTable";
	
	private static final String CONTACT_ID = "_id";
	private static final String CONTACT_FIRSTNAME = "firstName";
	private static final String CONTACT_LASTNAME = "lastName";
	private static final String CONTACT_MOBILEPHONE = "mobilePhone";
	private static final String CONTACT_HOMEPHONE = "homePhone";
	private static final String CONTACT_WORKPHONE = "workPhone";
	private static final String CONTACT_EMAIL = "email";
	private static final String CONTACT_ADDRESS = "address";
	private static final String CONTACT_BIRTHDAY = "birthday";
	//private static final String CONTACT_PHOTO = "photo";
	
	private static final String CREATE_CAR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + " (" + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ CONTACT_FIRSTNAME + " varchar(50),"
			+ CONTACT_LASTNAME + " varchar(50),"
			+ CONTACT_MOBILEPHONE + " varchar(50),"
			+ CONTACT_HOMEPHONE + " varchar(50),"
			+ CONTACT_WORKPHONE + " varchar(50),"
			+ CONTACT_EMAIL + " varchar(50),"
			+ CONTACT_ADDRESS + " varchar(50),"
			+ CONTACT_BIRTHDAY + " varchar(50));";
	
	private static final String SQL_DELETE_CONTACT_TABLE = "DROP TABLE IF EXISTS" + TABLE_CONTACTS;
	
	public ContactDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABAE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CAR_TABLE);
		
		final String FIRST_ENTRY = "INSERT INTO " + TABLE_CONTACTS + " (firstName, lastName, mobilePhone, homePhone, workPhone, email, address, birthday) VALUES('Eric', 'Hu', '021596511', null, null, null,null,null)";
		db.execSQL(FIRST_ENTRY);
		final String SECOND_ENTRY = "INSERT INTO " + TABLE_CONTACTS + " (firstName, lastName, mobilePhone, homePhone, workPhone, email, address, birthday) VALUES('Gary', 'Lu', '021596522', null, null, null,null,null)";
		db.execSQL(SECOND_ENTRY);
		final String THIRD_ENTRY = "INSERT INTO " + TABLE_CONTACTS + " (firstName, lastName, mobilePhone, homePhone, workPhone, email, address, birthday) VALUES('Luman', 'wang', '021596533', null, null, null,null,null)";
		db.execSQL(THIRD_ENTRY);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_CONTACT_TABLE);
		onCreate(db);
	}
	
	public Cursor getAllData() {
		String buildSQL = "SELECT * FROM " + this.TABLE_CONTACTS;
		return this.getReadableDatabase().rawQuery(buildSQL, null);
	}
	
	public void insertData(String firstName, String lastName, String mobilePhone, String homePhone, String workPhone, String email, String address, String birthday ) {
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(ContactDatabaseHelper.CONTACT_FIRSTNAME, firstName);
		contentValues.put(ContactDatabaseHelper.CONTACT_LASTNAME, lastName);
		contentValues.put(ContactDatabaseHelper.CONTACT_MOBILEPHONE, mobilePhone);
		contentValues.put(ContactDatabaseHelper.CONTACT_HOMEPHONE, homePhone);
		contentValues.put(ContactDatabaseHelper.CONTACT_WORKPHONE, workPhone);
		contentValues.put(ContactDatabaseHelper.CONTACT_EMAIL, email);
		contentValues.put(ContactDatabaseHelper.CONTACT_ADDRESS, address);
		contentValues.put(ContactDatabaseHelper.CONTACT_BIRTHDAY, birthday);
		
		this.getWritableDatabase().insert(ContactDatabaseHelper.TABLE_CONTACTS, null, contentValues);
	}
	public Cursor getSelectedData(String end){
		String buildSQL = "SELECT * FROM " + this.TABLE_CONTACTS + "WHERE_id = " + end;
		return this.getReadableDatabase().rawQuery(buildSQL, null);
	}
	public void delete(long id){
		SQLiteDatabase database= this.getWritableDatabase();
		database.execSQL("DELETE FROM " + this.TABLE_CONTACTS + "WHERE_id = " + String.valueOf(id));
	}
	
	

}
