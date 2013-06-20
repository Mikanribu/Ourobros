package epf.domethic.ouroboros.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import epf.domethic.ouroboros.R;
import epf.domethic.ouroboros.dao.PatientDAO;

import epf.domethic.ouroboros.adapter.PatientCursorAdapter;
import epf.domethic.ouroboros.model.Patient;
import epf.domethic.ouroboros.model.Patient.Sexe;
import epf.domethic.ouroboros.outils.ParserJSON;
import epf.domethic.ouroboros.outils.PatientColumns;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListerPatientsFragment extends SherlockListFragment {

	public interface OnPatientSelectedListener {
		public void onPatientSelected(int position, Patient patient);
	}

	//url contenant le fichier json des patients de l'application
	static String url = "http://raw.github.com/Mikanribu/Ouroboros/master/json_patients";

	private OnPatientSelectedListener listener;

	private ListView patientListView;

	private PatientDAO dao;
	private Patient patient; //Cr�ation d'un patient de la classe Patient


	private final static String TAG = ListerPatientsFragment.class.getSimpleName();
	private PatientCursorAdapter adapter;  //Cr�ation du Cursor pour les patients via la Base de Donn�es

    JSONArray patients = null;	//Objet JSON r�cup�r� de la classe ParserJSON
    List<Patient> patientList = new ArrayList<Patient>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		//R�cup�ration de la listview cr��e dans le fichier main.xml
		patientListView = getListView();
		
		//patientListView = (ListView) view.findViewById(R.id.liste_patients);
		//patientListView = getListView();
		
		// patientListView.setSelector(R.drawable.list_selector_holo_light);
		//patientListView.setFastScrollEnabled(true);
		//patientListView.setFastScrollAlwaysVisible(true);
		//patientListView.setDividerHeight(0);
		
		patientList = (ArrayList<Patient>) Patient.ALL;
		//PatientCursorAdapter patientAdapter = new PatientCursorAdapter(getSherlockActivity(), patientList);
		patientListView.setAdapter(adapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.dao = new PatientDAO(getActivity());
	
		//On v�rifie si la base de donn�es est vide ou non
		if(dao.dbIsEmpty() == true) {
			RecuperationJSON(); //Dans le cas o� elle est vide on r�cup�re les donn�es du fichier JSON
		}
		//List<Patient> patient = dao.getPatients(); 
		//adapter = new PatientAdapter(getActivity(),patient);
		Cursor cursor = dao.getPatientsCursor(); //Cr�ation du Cursor qui va nous permettre de se d�placer dans la BDD
	
		adapter = new PatientCursorAdapter(getActivity(), dao.getPatientsCursor(), true); //D�finition de l'adapter 

		setListAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				
		View view = inflater.inflate(R.layout.fragment_patients_list, container, false);
		return view;
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
		// adapter.notifyDataSetChanged();
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
			// listener.onPatientSelected(position);
			Cursor cursor = (Cursor) getListAdapter().getItem(position);
			patient = dao.getPatient(cursor);
			listener.onPatientSelected(position, patient);
		}
	}

	public Patient getPatientSelected() {
		return patient;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		adapter.notifyDataSetChanged();
	}
	
	//Fonction qui permet de r�cup�rer l'objet JSON via l'url
	//Qui le parse et ajoute les informations dans la BDD
	public void RecuperationJSON() {
		//Permet d'acc�der � internet sans erreurs "Network access" d� � l'acc�s � internet dans le thread principal

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
                int id = c.getInt(PatientColumns._ID);
                String nom = c.getString(PatientColumns.KEY_NOM);
                String prenom = c.getString(PatientColumns.KEY_PRENOM);
                Sexe sexe = Sexe.valueOf(c.getString(PatientColumns.KEY_SEXE));
                Date dateNaissance = null;
                //R�cup�re la date de naissance du format Date
				try {
					dateNaissance = Utils.parserDate(c
							.getString(PatientColumns.KEY_DATE_NAISSANCE));
				} catch (ParseException e) {
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
                //Cr�ation d'un patient avec les donn�es 
                Patient p = new Patient (id, nom, prenom, sexe, dateNaissance, lieuNaissance,
                					adresse, ville, codePostal, pays, nationalite, telephone, numSS,
                					medecinTraitant, hospitalise);
                
                PatientDAO dao = new PatientDAO(this.getActivity());
                dao.ajouterPatient(p); //Ajoute un patient dans la BDD
                dao.close();
               // patientList.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
}
