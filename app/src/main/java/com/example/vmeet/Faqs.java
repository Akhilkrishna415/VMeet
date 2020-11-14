package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class manages FAQ Activity
 */
public class Faqs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        ListView resultslistview = (ListView) findViewById(R.id.results_listview);
        HashMap<String, String> nameaddresses = new HashMap<>();
        nameaddresses.put("Q: How do I book a room ?", "Ans: Click on floating button icon in the home page or go to the navigation bar and click on book a room button ");
        nameaddresses.put("Q: Did you forgot a password ?", "Ans: Click on the reset password link and you can get reset link for the email you provided ");
        nameaddresses.put("Q: Having a trouble for sign in ?", "Ans: Contact Vmeet support team at vmeetsupportteam@gmail.com or contact +1 5147939988 ");
        nameaddresses.put("Q: How do I update my profile details ?","Ans: Go to settings and click on profile settings in profile settings page click on edit profile button ");
        nameaddresses.put("Q: How do I cancel my existence bookings?","Ans: Click on my bookings button in the navigation bar and click on cancel ");
        List<HashMap<String,String>> listitems=new ArrayList<>();
        SimpleAdapter adapter=new SimpleAdapter(this,listitems,R.layout.list_item,
                new String[]{"First line","Second line"},
                new int[]{R.id.text1,R.id.text2});
        Iterator it=nameaddresses.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String,String> resultsmap = new HashMap<>();
            Map.Entry pair =(Map.Entry)it.next();
            resultsmap.put("First line",pair.getKey().toString());
            resultsmap.put("Second line",pair.getValue().toString());
            listitems.add(resultsmap);
        }
        resultslistview.setAdapter(adapter);
        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("FAQ's");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go to settings page
                Intent intent = new Intent(this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}