package wesayallright.timemanager.surface.scheduleFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wesayallright.timemanager.R;

public class MyAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;
    private int itemid;

    public MyAdapter(Context pContext, List<String> pList,int itemid) {
        this.mContext = pContext;
        this.mList = pList;
        this.itemid =itemid;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(R.layout.item_custom, null);
        if (convertView != null) {
            TextView _TextView1 = (TextView) convertView.findViewById(R.id.textView1);
            _TextView1.setText(mList.get(position));
            if(position==itemid)
                _TextView1.setTextColor(0xffff0000);
        }
        return convertView;
    }
}
