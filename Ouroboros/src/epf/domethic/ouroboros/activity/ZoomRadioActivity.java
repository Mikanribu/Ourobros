package epf.domethic.ouroboros.activity;



import com.actionbarsherlock.app.SherlockActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import epf.domethic.ouroboros.R;

public class ZoomRadioActivity extends SherlockActivity {

	private WebView webView;
	private String imageUrl ="http://m.goirand.free.fr/evaluation_anatomie/cliche_thorax.jpg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom_radio);
		webView = (WebView) findViewById(R.id.wvDoc);

		// On cherche l'image via l'URL
		try {

			webView.loadUrl(imageUrl);
<<<<<<< HEAD
		 WebSettings webSettings = webView.getSettings();
			webSettings.setBuiltInZoomControls(true);
			//webSettings.setDisplayZoomControls(false);
			webSettings.setUseWideViewPort(true);
	//		webSettings.setDefaultZoom(ZoomDensity.FAR);
			webSettings.setLoadWithOverviewMode(true);

			 
=======
			WebSettings webSettings = webView.getSettings();
			webSettings.setBuiltInZoomControls(true);
			webSettings.setUseWideViewPort(true);
			webSettings.setLoadWithOverviewMode(true);

>>>>>>> 57aff0944042f61ae2cf94874c728d7f8f755d69
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
