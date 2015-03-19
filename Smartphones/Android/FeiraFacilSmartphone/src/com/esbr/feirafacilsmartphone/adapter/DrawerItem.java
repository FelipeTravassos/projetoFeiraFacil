package com.esbr.feirafacilsmartphone.adapter;

public class DrawerItem {
	private String title;
    private int icon;
    private boolean isHead;

    public DrawerItem() {
    }

    public DrawerItem(
        final String title, final int icon, final boolean isHead) {
        this.title = title;
        this.icon = icon;
        this.isHead = isHead;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean getIsHead() {
        return this.isHead;
    }

    public void setIsHead(boolean head) {
        this.isHead = head;
    }
}
