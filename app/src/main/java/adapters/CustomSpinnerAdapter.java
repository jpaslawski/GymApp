package adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private int hidingItemIndex;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, String[] objects, int hidingItemIndex) {
        super(context, textViewResourceId, objects);
        this.hidingItemIndex = hidingItemIndex;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v;
        if (position == hidingItemIndex) {
            TextView item = new TextView(getContext());
            item.setVisibility(View.GONE);
            v = item;
        } else {
            v = super.getDropDownView(position, null, parent);
        }
        return v;
    }
}
