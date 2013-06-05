package com.softamo.socialsharetest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends Activity {

    Button mTwitterButton;
    Button mFacebookButton;
    Button mGooglePlusButton;
    String message = "#socialpricapp Mi precio es de 100000 € ¿Cual es tu precio? http://socialpriceapp.com";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mTwitterButton = (Button) findViewById(R.id.twitterButton);
        mTwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(SN_TWITTER,message);
            }
        });
        mFacebookButton = (Button) findViewById(R.id.facebookButton);
        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(SN_FACEBOOK,message);
            }
        });
        mGooglePlusButton = (Button) findViewById(R.id.googlePlusButton);
        mGooglePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(SN_GOOGLE_PLUS,message);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private final static String SN_TWITTER = "twitter";
    private final static String SN_FACEBOOK = "facebook";
    private final static String SN_GOOGLE_PLUS = "googlePlus";

    private void shareText(String socialNetworkName, String message) {

        Intent shareIntent = null;
        if(socialNetworkName!=null && socialNetworkName.equals(SN_TWITTER)) {
            final String[] socialNetworkApps = {
                    // package // name - nb installs (thousands)
                    "com.twitter.android", // official - 10 000
                    "com.twidroid", // twidroid - 5 000
                    "com.handmark.tweetcaster", // Tweecaster - 5 000
                    "com.thedeck.android" }; // TweetDeck - 5 000 };
            shareIntent = findIntentForSocialNetwork(socialNetworkApps);

        } else if(socialNetworkName!=null && socialNetworkName.equals(SN_GOOGLE_PLUS)) {
            final String[] socialNetworkApps = {
                    "com.google.android.apps.plus"
            };
            shareIntent = findIntentForSocialNetwork(socialNetworkApps);

        } else if(socialNetworkName!=null && socialNetworkName.equals(SN_FACEBOOK)) {
            final String[] socialNetworkApps = {
                    "com.facebook.katana"
            };
            shareIntent = findIntentForSocialNetwork(socialNetworkApps);

        }

        if(shareIntent==null) {
            shareIntent = new Intent();
        }
        shareIntent.setAction(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, message).setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.action_share)));
    }


    private Intent findIntentForSocialNetwork(String[] socialNetworkApps) {

        Intent intent = new Intent();
        intent.setType("text/plain");
        final PackageManager packageManager = getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        for (int i = 0; i < socialNetworkApps.length; i++) {
            for (ResolveInfo resolveInfo : list) {
                String p = resolveInfo.activityInfo.packageName;
                if (p != null && p.startsWith(socialNetworkApps[i])) {
                    intent.setPackage(p);
                    return intent;
                }
            }
        }
        return null;
    }
}
