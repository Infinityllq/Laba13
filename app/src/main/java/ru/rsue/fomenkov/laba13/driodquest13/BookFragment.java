package ru.rsue.fomenkov.laba13.driodquest13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

public class BookFragment extends Fragment {
    public EditText mTitleField;
    public static Book mBook;
    public static Button mDateButton;
    public CheckBox mReadedCheckBox;


    private static final String ARG_BOOK_ID = "book_id";
    private static final String DIALOG_DATE="DialogDate";
    private static final int REQUEST_DATE=0;

    private CompoundButton.OnCheckedChangeListener newOnCheckedChangelListener;
    private FragmentManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID bookId = (UUID) getArguments().getSerializable(ARG_BOOK_ID);
        mBook = BookLab.get(getActivity()).getBook(bookId);
    }
    @Override
    public void onPause(){
        super.onPause();
        BookLab.get(getActivity()).updateBook(mBook);
    }
    public static BookFragment newInstance(UUID bookId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_ID, bookId);
        BookFragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book, container,
                false);
        mTitleField = (EditText) v.findViewById(R.id.book_title);
        mTitleField.setText(mBook.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBook.setTitle(s.toString());
            }


            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        mDateButton = (Button)v.findViewById(R.id.book_date);
        updateDate();
        mDateButton.setText(mBook.getDate().toString());
       mDateButton.setOnClickListener(new View.OnClickListener(){

           @Override
                   public void onClick(View v){
               FragmentManager manager=getFragmentManager();
               DatePickerFragment dialog = DatePickerFragment.newInstance((mBook.getDate()));
               dialog.setTargetFragment(BookFragment.this, REQUEST_DATE);
               dialog.show(manager, DIALOG_DATE);
            }
        });

        mReadedCheckBox = (CheckBox)v.findViewById(R.id.book_readed);
        mReadedCheckBox.setChecked(mBook.isReaded());
        mReadedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBook.setReaded(isChecked);
            }
        });
        return v;
    }
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode== REQUEST_DATE){
            Date date=(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBook.setDate(date);
            updateDate();
        }
    }

    private static void updateDate() {
        mDateButton.setText(mBook.getDate().toString());
    }
}