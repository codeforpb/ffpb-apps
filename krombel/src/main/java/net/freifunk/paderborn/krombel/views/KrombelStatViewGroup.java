package net.freifunk.paderborn.krombel.views;

import android.widget.*;

import net.freifunk.paderborn.krombel.*;
import net.freifunk.paderborn.krombel.sync.api.*;

import org.androidannotations.annotations.*;

import java.text.*;

/**
 * FIXME doc
 */
@EViewGroup(R.layout.view_krombel_stat)
public class KrombelStatViewGroup implements CustomDataView<KrombelStat>{
    private final DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
    @ViewById
    TextView textStatName, textStatValue, textStatDate;

    public void bind(KrombelStat stat){
        textStatName.setText(stat.getType().stringResId);
        textStatValue.setText(stat.getCount() + "");
        textStatDate.setText(sdf.format(stat.getTimestamp()));
    }
}
