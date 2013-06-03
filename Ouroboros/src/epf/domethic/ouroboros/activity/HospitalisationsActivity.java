package epf.domethic.ouroboros.activity;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import epf.domethic.ouroboros.R;
import epf.domethic.ouroboros.activity.ListerPatientsFragment.OnPatientSelectedListener;
import epf.domethic.ouroboros.model.Patient;
import epf.domethic.ouroboros.widget.AnimationLayout;

public class HospitalisationsActivity extends FragmentActivity implements
		AnimationLayout.Listener, OnPatientSelectedListener {
	/** Called when the activity is first created. */

	private int position;
	
	
	public final static String TAG = "Demo";

	protected LinearLayout mList;
	protected AnimationLayout mLayout;
	private TextView tvRecherche;
	private TextView tvArchives;
	ListerPatientsFragment fragment_liste = new ListerPatientsFragment();
	AfficherPatientFragment fragment_detail = new AfficherPatientFragment();
	RecherchePatientFragment fragment_recherche = new RecherchePatientFragment(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospitalisations);


		ActionBar actionBar = getActionBar();
		
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.barre_haut);
		actionBar.setBackgroundDrawable (myDrawable);
		
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true); // The icone_launcher will not go
												// back automatically to home (API min 14)

		//Pas d'affichage du nom de l'application dans la barre d'action
		actionBar.setDisplayShowTitleEnabled(false);
		
		mList = (LinearLayout) findViewById(R.id.animation_layout_sidebar);
		mLayout = (AnimationLayout) findViewById(R.id.animation_layout);
		mLayout.setListener(this);
		
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		
		fragmentTransaction.add(R.id.tiers, fragment_liste);
		fragmentTransaction.add(R.id.deuxTiers, fragment_detail);
		fragmentTransaction.commit();
			
	    tvRecherche = (TextView) findViewById(R.id.tvRecherche);	
	    tvRecherche.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	
	    	FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
	    	fragmentTransaction.detach(fragment_liste);
	    	fragmentTransaction.detach(fragment_detail);
	    	fragmentTransaction.add(R.id.tiers, fragment_recherche);
	    	fragmentTransaction.commit();
	        		
	    	}
	    });
	    
	    tvArchives = (TextView) findViewById(R.id.tvArchives);
		
	    tvArchives.setOnClickListener(new View.OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		
	    	}
	    });
	}


	@Override
	public void onPatientSelected(int position) {
		this.position = position;
		Patient patient = Patient.ALL.get(position);
		fragment_detail.afficherPatient(patient);

	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the options menu from XML
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.accueil, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(
				R.id.action_recherche).getActionView();
		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false); // Do not iconify the widget;
													// expand it by default

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if(menuItem.getItemId()== android.R.id.home){
			mLayout.toggleSidebar();
		}
		return (super.onOptionsItemSelected(menuItem));
	}

	public void onClickContentButton(View v) {
		mLayout.toggleSidebar();
	}

	@Override
	public void onBackPressed() {
		if (mLayout.isOpening()) {
			mLayout.closeSidebar();
		} else {
			finish();
		}
	}

	/* Callback of AnimationLayout.Listener to monitor status of Sidebar */
	@Override
	public void onSidebarOpened() {
		Log.d(TAG, "opened");
	}

	/* Callback of AnimationLayout.Listener to monitor status of Sidebar */
	@Override
	public void onSidebarClosed() {
		Log.d(TAG, "closed");
	}

	/* Callback of AnimationLayout.Listener to monitor status of Sidebar */
	@Override
	public boolean onContentTouchedWhenOpening() {
		// the content area is touched when sidebar opening, close sidebar
		Log.d(TAG, "going to close sidebar");
		mLayout.closeSidebar();
		return true;
	}
}
