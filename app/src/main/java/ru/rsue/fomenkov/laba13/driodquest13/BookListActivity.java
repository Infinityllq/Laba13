package ru.rsue.fomenkov.laba13.driodquest13;

import androidx.fragment.app.Fragment;

public class BookListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new BookListFragment();
    }
}
