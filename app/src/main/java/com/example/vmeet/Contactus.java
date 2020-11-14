package com.example.vmeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * This class manages how to contact vmeet team via mail or call
 */
public class Contactus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Contact us");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This class manages to call the dedicated customer care number
     */
    public void call(View view) {
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setData(Uri.parse("tel:5135699966"));
        startActivity(call);
    }

    /**
     * This class manages to send mail to dedicated Vmeet team gmail Id
     */
    public void Mail(View view) {
        Intent Mail = new Intent(Intent.ACTION_SEND);
        String[] recipients = {"mailto@gmail.com"};
        Mail.putExtra(Intent.EXTRA_EMAIL, recipients);
        Mail.putExtra(Intent.EXTRA_SUBJECT, "Subject text here...");
        Mail.putExtra(Intent.EXTRA_TEXT, "Body of the content here...");
        Mail.putExtra(Intent.EXTRA_CC, "vmeetadmin@gmail.com");
        Mail.setType("text/html");
        Mail.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(Mail, "Send mail"));
    }
}