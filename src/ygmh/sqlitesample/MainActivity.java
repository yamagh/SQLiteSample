package ygmh.sqlitesample;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private int cnt = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("## onCreateDone");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClickButton1(View v) {
		System.out.println("## onClick");

		MySQLiteOpenHelper msoh = new MySQLiteOpenHelper(this);  // thisで ActivityContext を入れる。ApplicationContextは今回は使わずやってみる。
		SQLiteDatabase sqldb = msoh.getWritableDatabase();
		
		ContentValues val = new ContentValues();
		val.put("data", "data" + Integer.toString(cnt++));
		sqldb.insert("mytable", null, val);
		
		Cursor cur = sqldb.query("mytable", new String[] {"_id", "data"}, null, null, null, null, "_id ASC");
		cur.moveToFirst();
		int _idIndex = cur.getColumnIndex("_id");
		int dataIndex = cur.getColumnIndex("data");
//		System.out.println( "_id: "  +Integer.toString(_idIndex));
//		System.out.println( "data: " + Integer.toString(dataIndex));

		while( cur.isLast() != true ){
			System.out.println(cur.getString(_idIndex) + ", " + cur.getString(dataIndex));
			cur.moveToNext();
		}
		
		// 画面アイテムの書き換え練習
//		EditText et = (EditText)this.findViewById(R.id.editText1);
//		et.setText(Integer.toString(cnt++));
//		Button btn = (Button)v;
//		btn.setText(Integer.toString(cnt++));
//		btn.setText(R.string.btn1_clicked + ": " + cnt++);

		// メッセージ表示の練習
//		AlertDialog.Builder adiagBld = new AlertDialog.Builder(this);
//		adiagBld.setTitle("testTitle");
//		adiagBld.setMessage("SampleMessage");
//		adiagBld.setPositiveButton("OK",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				}
//			);
//		adiagBld.setNegativeButton("Cancel",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				}
//			);
//		adiagBld.setCancelable(true);
//		AlertDialog adiag = adiagBld.create();
//		adiag.show();
	}
	
	private static class MySQLiteOpenHelper extends SQLiteOpenHelper {
		
		static final String DB = "sqlite_sample.db";
		static final int DB_VERSION = 1;
		static final String CREATE_TABLE = "create table mytable ( _id integer primary key autoincrement, data integer not null );";
		static final String DROP_TABLE = "drop table mytable;";
		
	    public MySQLiteOpenHelper(Context c) {
	        super(c, DB, null, DB_VERSION);
	    }
	    
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(CREATE_TABLE);
	    }
	    
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        db.execSQL(DROP_TABLE);
	        onCreate(db);
	    }
		
	}

}
