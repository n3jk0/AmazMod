package com.edotassi.amazmod.ui.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.edotassi.amazmod.R;

import amazmod.com.models.Contact;
import butterknife.BindView;

public class AddContactDialog extends DialogFragment {
    @BindView(R.id.contact_name_id)
    TextView contactNameTextView;

    @BindView(R.id.contact_number_id)
    TextView contactNumberTextView;

    private Contact savedContact;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_add_contact, null));
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        builder.setPositiveButton("Save", (dialog, which) -> {
            savedContact = new Contact();
            savedContact.setName((String) contactNameTextView.getText());
            // todo: validation
            savedContact.setPhoneNumber((String) contactNumberTextView.getText());
            dialog.dismiss();
        });


        return builder.create();
    }

    public Contact getSavedContact() {
        return savedContact;
    }
}
