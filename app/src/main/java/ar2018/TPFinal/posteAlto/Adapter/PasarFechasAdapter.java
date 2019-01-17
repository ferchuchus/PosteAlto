package ar2018.TPFinal.posteAlto.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ar2018.TPFinal.posteAlto.R;

public class PasarFechasAdapter extends PagerAdapter {
    private Context mContext;
    private List<Fragment> mFechas;

    public PasarFechasAdapter(Context context, List<Fragment> fechas) {
        super();
        mContext = context;
        mFechas = fechas;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        //mFechas.get(position);
        LayoutInflater inflater;
        inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_fecha, collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mFechas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}





