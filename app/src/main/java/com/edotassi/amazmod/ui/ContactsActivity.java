package com.edotassi.amazmod.ui;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.edotassi.amazmod.R;

import org.tinylog.Logger;

import java.util.Set;

import amazmod.com.models.Contact;
import butterknife.BindView;
import butterknife.OnClick;

public class ContactsActivity extends AppCompatActivity {

    @BindView(R.id.activity_contacts_contact_list)
    ListView listView;

    private Set<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.pref_contacts);
        } catch (NullPointerException exception) {
            Logger.error("ContactsActivity onCreate exception: " + exception.getMessage());
        }
    }

    @OnClick(R.id.activity_contacts_add)
    protected void addContact() {

    }

}
