package epf.domethic.ouroboros.data;

import epf.domethic.ouroboros.outils.PersColumns;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Classe permettant la cr�ation de la Base de Donn�es Patient
public class PersDBOpenHelper extends SQLiteOpenHelper {
	
	/* ----------	D�claration des variables	----------*/

	private final static String TAG = PersDBOpenHelper.class.getSimpleName(); 
	
	private static final String DATABASE_NAME = "patients.db";   //Nom de la Base de Donn�es
	
	private static final int DATABASE_VERSION = 1;				 //version de la base de donn�es
	
	public static final String TABLE_PATIENT = "Patient";  		 //Nom de la table patient
	public static final String TABLE_USER = "User";  		 	 //Nom de la table des utilisateurs
	
	
	/* ----------	D�claration des focntions	----------*/
    
    //String de cr�ation de la BDD avec le nom de la table et des colonnes
	private static final String DATABASE_CREATE_PATIENT = 
			"CREATE TABLE " + TABLE_PATIENT + "( " + PersColumns._ID + " integer primary key autoincrement," +
					PersColumns.KEY_NOM + " text not null," + PersColumns.KEY_PRENOM + " text not null," + PersColumns.KEY_SEXE + " text not null," +
					PersColumns.KEY_DATE_NAISSANCE + " text not null," + PersColumns.KEY_LIEU_NAISSANCE + " text not null," +
					PersColumns.KEY_ADRESSE + " text not null,"+ PersColumns.KEY_VILLE + " text not null," + PersColumns.KEY_CODE_POSTAL + " text not null," +
					PersColumns.KEY_PAYS + " text not null," + PersColumns.KEY_NATIONALITE + " text not null," + PersColumns.KEY_TELEPHONE + " text not null," +
					PersColumns.KEY_NUMSS + " text not null," + PersColumns.KEY_MEDECIN_TRAITANT + " text not null,"+ PersColumns.KEY_HOSPITALISE + " text not null)";
	
	private static final String DATABASE_CREATE_USER = 
			"CREATE TABLE " + TABLE_USER + "( " + PersColumns._ID + " integer primary key autoincrement," +
					PersColumns.KEY_PSEUDO + " text not null," + PersColumns.KEY_MDP + " text not null," +
					PersColumns.KEY_NOM + " text not null," + PersColumns.KEY_PRENOM + " text not null," + PersColumns.KEY_MAIL + " text not null," +
					PersColumns.KEY_TELEPHONE + " text not null," + PersColumns.KEY_SERVICE + " text not null," + PersColumns.KEY_FONCTION +" text not null)";

	//Constructeur de la clase
	public PersDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Cr�ation de la base : [version " + db.getVersion() + "]");
		db.execSQL(DATABASE_CREATE_PATIENT);		//Cr�ation de la Base de Donn�es
		db.execSQL(DATABASE_CREATE_USER);			//Cr�ation de la Base de Donn�es
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Message lors de la mise � jour de la Base de Donn�es
		Log.w(TAG, "Mise � jour de la base [" + DATABASE_NAME + "] : [" + oldVersion + " --> " + newVersion + "]");

	}

}
