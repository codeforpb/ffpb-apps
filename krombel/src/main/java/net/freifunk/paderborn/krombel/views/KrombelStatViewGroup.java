package net.freifunk.paderborn.krombel.views;

import android.content.*;
import android.util.*;
import android.widget.*;

import net.freifunk.paderborn.krombel.R;
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
            bindNull();
        } else {
            bindNonNull(stat);
        }


    }

    private void bindNull() {
        textStatName.setText("");
        textStatValue.setText("");
        textStatDate.setText("");
    }

    private void bindNonNull(KrombelStat stat) {
        textStatName.setText(stat.getType().stringResId);
        textStatValue.setText(stat.getCount() + "");
        textStatDate.setText(sdf.format(stat.getTimestamp()));
        if (stat.getType() == KrombelStatType.CURRENT_CLIENTS
                || stat.getType() == KrombelStatType.CURRENT_NODES) {
            textStatDate.setVisibility(INVISIBLE);
        }
    }
}
