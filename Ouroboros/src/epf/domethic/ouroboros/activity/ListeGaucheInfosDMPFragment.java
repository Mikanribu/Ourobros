package epf.domethic.ouroboros.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.actionbarsherlock.app.SherlockFragment;

import epf.domethic.ouroboros.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
 
public class ListeGaucheInfosDMPFragment extends SherlockFragment {
 
	private ListView listViewInfos;
 
    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
		View view = inflater.inflate(R.layout.fragment_liste_infos_dmp,container, false);
 
        //R�cup�ration de la listview cr��e dans le fichier main.xml
		listViewInfos = (ListView)view.findViewById(R.id.liste_infos_dmp);
 
        //Cr�ation de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        //On d�clare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
 
        //Cr�ation d'une HashMap pour ins�rer les informations du premier item de notre listView
        map = new HashMap<String, String>();
        //on ins�re un �l�ment titre que l'on r�cup�rera dans le textView titre cr�� dans le fichier item_list_infos_dmp.xml
        map.put("titre", "Donn�es Patient");
        //on ins�re la r�f�rence � l'image (converti en String car normalement c'est un int que l'on r�cup�rera dans l'imageView cr�� dans le fichier affichageitem.xml
        map.put("img", String.valueOf(R.drawable.logo_donnees_patient));
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);
 
        //On refait la manip plusieurs fois avec des donn�es diff�rentes pour former les items de notre ListView
 
        map = new HashMap<String, String>();
        map.put("titre", "Nouvelle hospitalisation");
        map.put("img", String.valueOf(R.drawable.logo_new_hospi));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "Fin d'hospitalisation");
        map.put("img", String.valueOf(R.drawable.logo_fin_hospi));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "Impression Dossier");
        map.put("img", String.valueOf(R.drawable.logo_impression_dossier));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", "Nouveau Document");
        map.put("img", String.valueOf(R.drawable.logo_new_doc));
        listItem.add(map);
 
        //Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (getActivity().getBaseContext(), listItem, R.layout.menu_infos_dmp_elements,
               new String[] {"img", "titre", "description"}, new int[] {R.id.ivImage, R.id.tvTitre});
 
        //On attribue � notre listView l'adapter que l'on vient de cr�er
        listViewInfos.setAdapter(mSchedule);
 
        //Enfin on met un �couteur d'�v�nement sur notre listView
        listViewInfos.setOnItemClickListener(new OnItemClickListener() {
			@Override
        	@SuppressWarnings("unchecked")
         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				//on r�cup�re la HashMap contenant les infos de notre item (titre, description, img)
        		HashMap<String, String> map = (HashMap<String, String>) listViewInfos.getItemAtPosition(position);
        		
        	}
         });
 
        return view;
    }
}
