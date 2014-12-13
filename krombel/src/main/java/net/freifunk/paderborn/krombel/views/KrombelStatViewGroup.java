package net.freifunk.paderborn.krombel.views;

import android.content.*;
import android.util.*;
import android.widget.*;

import net.freifunk.paderborn.krombel.*;
import net.freifunk.paderborn.krombel.sync.api.*;

import org.androidannotations.annotations.*;

import java.text.*;

/**
 * FIXME doc
 */
@EViewGroup(R.layout.view_krombel_stat)
public class KrombelStatViewGroup extends RelativeLayout implements CustomDataView<KrombelStat> {
    private final DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
    @ViewById
    TextView textStatName, textStatValue, textStatDate;

    public KrombelStatViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(KrombelStat stat) {
        if (stat == null) {
            return;
        }
        textStatName.setText(stat.getType().stringResId);
        textStatValue.setText(stat.getCount() + "");
        textStatDate.setText(sdf.format(stat.getTimestamp()));
    }
}
