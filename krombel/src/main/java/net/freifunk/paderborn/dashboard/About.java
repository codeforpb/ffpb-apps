package net.freifunk.paderborn.dashboard;

import android.support.v7.app.*;
import android.text.method.*;
import android.widget.*;

import org.androidannotations.annotations.*;

@EActivity(R.layout.about)
public class About extends ActionBarActivity {
    @ViewById(R.id.textGithub)
    @FromHtml(R.string.githubLink)
    TextView textGithub;

    @ViewById(R.id.textFfpb)
    @FromHtml(R.string.ffpbUrl)
    TextView textFfpb;

    @ViewById(R.id.textAboutText)
    @FromHtml(R.string.aboutText)
    TextView textAboutText;

    @AfterViews
    void linkify() {
        MovementMethod movementMethod = LinkMovementMethod.getInstance();
        textAboutText.setMovementMethod(movementMethod);
        textGithub.setMovementMethod(movementMethod);
        textFfpb.setMovementMethod(movementMethod);
    }
}
