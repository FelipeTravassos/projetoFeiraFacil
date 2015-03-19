package com.esbr.feirafacilsmartphone.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.esbr.feirafacilsmartphone.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrawerAdapter extends BaseAdapter{

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
	private ViewHolder holder;
	private Context context;
	private List<DrawerItem> itemDrawer;
	private SortedSet<Integer> separatorsSet = new TreeSet<Integer>();
	private HashSet<Integer> checkedItems;

	public DrawerAdapter(final Context context) {
		this.context = context;
		itemDrawer = new ArrayList<DrawerItem>();
		this.checkedItems = new HashSet<Integer>();
	}

	public void adicionarCategoria(final String title, final int icon) {
		itemDrawer.add(new DrawerItem(title, icon, "categoria"));
	}

	public final void adicionarMenu(final String title) {
		itemDrawer.add(new DrawerItem(title, 0, "menu"));
		separatorsSet.add(itemDrawer.size() - 1);
	}
	
	public final void adicionarInformacaoUsuario(final String title, final int icon) {
		itemDrawer.add(new DrawerItem(title, icon, "informacao_usuario"));
	}

	@Override
	public final int getItemViewType(final int position) {
		return separatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public final boolean isEnabled(final int position) {
		int type = getItemViewType(position);
		return type != TYPE_SEPARATOR;
	}

	public final void setChecked(final int pos, final boolean checked) {
		if (checked) {
			this.checkedItems.add(Integer.valueOf(pos));
		} else {
			this.checkedItems.remove(Integer.valueOf(pos));
		}

		this.notifyDataSetChanged();
	}

	public final void resetarCheck() {
		this.checkedItems.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public final int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	@Override
	public final int getCount() {
		return itemDrawer.size();
	}

	@Override
	public final Object getItem(final int position) {
		return itemDrawer.get(position);
	}

	@Override
	public final long getItemId(final int position) {
		return position;
	}

	
	@Override
	public final View getView(final int position, final View convertView, final ViewGroup parent) {
		holder = null;
		View view = convertView;
		DrawerItem item = itemDrawer.get(position);

		if (view == null) {

			int layout = 0;
			
			if (item.getTipo().equals("informacao_usuario")) {
				
				layout = R.layout.informacao_usuario;
				
			} else if (item.getTipo().equals("menu")) {
				
				layout = R.layout.header;	
				
			} else if (item.getTipo().equals("categoria")) {
				
				layout = R.layout.drawer_list_item;
				
			}

			view = LayoutInflater.from(context).inflate(layout, null);

			TextView title = (TextView) view.findViewById(R.id.title);
			ImageView icon = (ImageView) view.findViewById(R.id.icon);
			View viewNavigation = view.findViewById(R.id.viewNavigation);
			
			LinearLayout linearColor = (LinearLayout) view.findViewById(R.id.ns_menu_row);
			view.setTag(new ViewHolder(title, icon, linearColor, viewNavigation));

		}

		if (holder == null && view != null) {
			Object tag = view.getTag();
			if (tag instanceof ViewHolder) {
				holder = (ViewHolder) tag;
			}
		}
		if (item != null && holder != null) {
			if (holder.getTitle() != null) {
				holder.getTitle().setText(item.getTitle());
			}
			if (holder.getIcon() != null) {
				if (item.getIcon() != 0) {
					holder.getIcon().setVisibility(View.VISIBLE);
					holder.getIcon().setImageResource(item.getIcon());
				} else {
					holder.getIcon().setVisibility(View.GONE);
				}
			}
		}

		if (item != null && !item.getTipo().equals("menu")) {
			if (checkedItems.contains(Integer.valueOf(position))) {
				holder.getViewNavigation().setVisibility(View.VISIBLE);
			} else {
				holder.getViewNavigation().setVisibility(View.GONE);
			}
		}

		return view;
	}
}
