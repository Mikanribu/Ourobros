package epf.domethic.ouroboros.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockListFragment;

import epf.domethic.ouroboros.dao.PatientDAO;
//import epf.domethic.ouroboros.adapter.PatientAdapter;
import epf.domethic.ouroboros.adapter.PatientCursorAdapter;
import epf.domethic.ouroboros.model.Patient;
import epf.domethic.ouroboros.comparator.NameComparator;
import epf.domethic.ouroboros.model.Patient.Sexe;
import epf.domethic.ouroboros.outils.ParserJSON;
import epf.domethic.ouroboros.outils.PatientColumns;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class ListerPatientsFragment extends SherlockListFragment {

	public interface OnPatientSelectedListener {
		public void onPatientSelected(int position, Patient patient);
	}
	
	static String url = "http://raw.github.com/Mikanribu/Ouroboros/master/json_patients";

	private OnPatientSelectedListener listener;
	//private ListerPatientsFragment liste;
	private PatientDAO dao;
	private Patient patient;

	private final static String TAG = ListerPatientsFragment.class.getSimpleName();
	private PatientCursorAdapter adapter;

    JSONArray patients = null;
    List<Patient> patientList = new ArrayList<Patient>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.dao = new PatientDAO(getActivity());
	
		if(dao.dbIsEmpty() == true) {
			Log.v("TAG","DANS LE IF!!!!!");
			RecuperationJSON();
		}
		//List<Patient> patient = dao.getPatients(); 
		//adapter = new PatientAdapter(getActivity(),patient);
		Cursor cursor = dao.getPatientsCursor();
	
		adapter = new PatientCursorAdapter(getActivity(), dao.getPatientsCursor(), true);
		setListAdapter(adapter);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (OnPatientSelectedListener) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}

	public void update() {
		//adapter.notifyDataSetChanged();
		Cursor cursor = dao.getPatientsCursor();
		adapter.changeCursor(cursor);
	}
	
	public void update(String like) {
		Cursor cursor = dao.getPatientsCursor(like);
		adapter.changeCursor(cursor);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (listener != null) {
			//listener.onPatientSelected(position);
			Cursor cursor = (Cursor)getListAdapter().getItem(position);
			patient = dao.getPatient(cursor);
			listener.onPatientSelected(position, patient);
		}
	}
	
	public Patient getPatientSelected(){
		return patient;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		adapter.notifyDataSetChanged();
	}
	
	public void RecuperationJSON() {
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		// Creation d'une instance ParserJSON
        ParserJSON jParser = new ParserJSON();    
        //On r�cup�re JSON string � partir de l'URL
        JSONObject json = jParser.getJSONFromUrl(url);        
        
        try {
            patients = json.getJSONArray("patients");
             
            // Boucle sur tous les patients du fichier JSON
            for(int i = 0; i < patients.length(); i++){
                JSONObject c = patients.getJSONObject(i);
                 
                // On r�cup�re toutes les donn�es qu'on stocke dans une variable
                String nom = c.getString(PatientColumns.KEY_NOM);
                String prenom = c.getString(PatientColumns.KEY_PRENOM);
                Sexe sexe = Sexe.valueOf(c.getString(PatientColumns.KEY_SEXE));
                Date dateNaissance = null;
				try {
					dateNaissance = Utils.parserDate(c.getString(PatientColumns.KEY_DATE_NAISSANCE));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                String lieuNaissance = c.getString(PatientColumns.KEY_LIEU_NAISSANCE);
                String adresse = c.getString(PatientColumns.KEY_ADRESSE);
                String ville = c.getString(PatientColumns.KEY_VILLE);
                String codePostal = c.getString(PatientColumns.KEY_CODE_POSTAL);
                String pays = c.getString(PatientColumns.KEY_PAYS);
                String nationalite = c.getString(PatientColumns.KEY_NATIONALITE);
                String telephone = c.getString(PatientColumns.KEY_TELEPHONE);
                String numSS = c.getString(PatientColumns.KEY_NUMSS);
                String medecinTraitant = c.getString(PatientColumns.KEY_MEDECIN_TRAITANT);
                boolean hospitalise = c.getInt(PatientColumns.KEY_HOSPITALISE) ==1;
                 
                Patient p = new Patient (nom, prenom, sexe, dateNaissance, lieuNaissance,
                					adresse, ville, codePostal, pays, nationalite, telephone, numSS,
                					medecinTraitant, hospitalise);
                
                PatientDAO dao = new PatientDAO(this.getActivity());
                dao.ajouterPatient(p);
                dao.close();
                patientList.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Tri des noms des patients par ordre alphab�tique
       // Collections.sort(patientList, new NameComparator());
	}
}
