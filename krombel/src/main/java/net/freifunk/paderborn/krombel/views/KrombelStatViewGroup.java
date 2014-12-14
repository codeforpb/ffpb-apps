package net.freifunk.paderborn.krombel.views;

import android.content.*;
import android.content.res.*;
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
    @ViewById
    RelativeLayout content;
    private int mType = 0;

    public KrombelStatViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.KrombelStatViewGroup,
                0, 0);

        try {
            mType = a.getInt(R.styleable.KrombelStatViewGroup_type, 0);
        } finally {
            a.recycle();
        }
    }

    @AfterViews
    void changeBackground() {
        if (mType == 0) {
            content.setBackgroundColor(getResources().getColor(R.color.backgroundClients));
        } else {
            content.setBackgroundColor(getResources().getColor(R.color.backgroundNodes));
        }
    }

    public void bind(KrombelStat stat) {
        if (stat == null) {
            textStatName.setText("");
            textStatValue.setText("");
            textStatDate.setText("");
        } else {
            textStatName.setText(stat.getType().stringResId);
            textStatValue.setText(stat.getCount() + "");
            textStatDate.setText(sdf.format(stat.getTimestamp()));
        }
    }
}
