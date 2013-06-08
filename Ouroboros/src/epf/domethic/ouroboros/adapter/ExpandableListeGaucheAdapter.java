package epf.domethic.ouroboros.adapter;

import java.util.ArrayList;

import epf.domethic.ouroboros.R;
import epf.domethic.ouroboros.activity.DetailListeActivity;
import epf.domethic.ouroboros.activity.TitreListeActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExpandableListeGaucheAdapter {
	private Context context;
	private ArrayList<TitreListeActivity> elmtList;

	public ExpandableListeGaucheAdapter(Context context, ArrayList<TitreListeActivity> elmtList) {
		this.context = context;
		this.elmtList = elmtList;
	}

	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<DetailListeActivity> productList = elmtList.get(groupPosition)
				.getProductList();
		return productList.get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {

		DetailListeActivity detailListe = (DetailListeActivity) getChild(groupPosition,
				childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.elements_section, null);
		}

		TextView sequence = (TextView) view.findViewById(R.id.sequence);
		sequence.setText(detailListe.getSequence().trim() + ") ");
		TextView childItem = (TextView) view.findViewById(R.id.childItem);
		childItem.setText(detailListe.getName().trim());

		return view;
	}

	public int getChildrenCount(int groupPosition) {

		ArrayList<DetailListeActivity> productList = elmtList.get(groupPosition)
				.getProductList();
		return productList.size();

	}


	public Object getGroup(int groupPosition) {
		return elmtList.get(groupPosition);
	}


	public int getGroupCount() {
		return elmtList.size();
	}


	public long getGroupId(int groupPosition) {
		return groupPosition;
	}


	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {

		TitreListeActivity titreListe = (TitreListeActivity) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.titre_section, null);
		}

		TextView heading = (TextView) view.findViewById(R.id.heading);
		heading.setText(titreListe.getName().trim());

		return view;
	}


	public boolean hasStableIds() {
		return true;
	}


	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}