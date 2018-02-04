package com.chandilsachin.notely.util.lifecycle.arch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chandilsachin.notely.R;

import io.reactivex.disposables.CompositeDisposable;

public abstract class LifeCycleFragment extends Fragment {

    public CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutId(), container, false);
        compositeDisposable = new CompositeDisposable();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view, savedInstanceState);
        initLoadViews();
        setUpEvents();
    }

    /**
     * Returns layout id for fragment
     *
     * @return
     */
    public abstract int getLayoutId();

    public abstract void init(View v, @Nullable Bundle savedInstanceState);

    public abstract void initLoadViews();

    public void setUpEvents() {
    }

    public void setUpToolbar(Toolbar toolbar, int stringRes) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.setTitle(stringRes);
    }

    public void setUpToolbar(Toolbar toolbar) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.setTitle(R.string.app_name);
    }

    public void setUpToolbar(Toolbar toolbar, String title) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.setTitle(title);
    }

    public void setDisplayHomeAsUpEnabled(boolean value) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(value);
    }

    public ActionBar getSupportActionBar(){
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) fm.popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
