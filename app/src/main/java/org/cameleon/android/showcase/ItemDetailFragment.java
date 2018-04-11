package org.cameleon.android.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import org.cameleon.android.showcase.dummy.ComponentContent;

import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item";

    /**
     * The dummy content this fragment is presenting.
     */
    private ComponentContent.ComponentItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (ComponentContent.ComponentItem) getArguments().getSerializable(ARG_ITEM);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(activity.getString(mItem.idItemText));
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        switch (mItem.idDetail) {
            case R.layout.item_detail_menu: {
                inflater.inflate(R.menu.menu_main, menu);
                break;
            }
            default:
                super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(mItem.idDetail, container, false);

        Activity activity = getActivity();
        if (activity != null) {
            switch (mItem.idDetail) {
                case R.layout.item_detail_text: {
                    initAutoCompleteTextView(rootView, activity);
                    initMultiAutoCompleteTextView(rootView, activity);
                    break;
                }
                default:
            }
        }

        return rootView;
    }

    private void initMultiAutoCompleteTextView(View rootView, Activity activity) {
        MultiAutoCompleteTextView multiAutoCompleteTextView = (MultiAutoCompleteTextView) rootView.findViewById(R.id.multiAutoCompleteTextView);
        if (multiAutoCompleteTextView != null) {
            int layoutItemId = android.R.layout.simple_dropdown_item_1line;
            List<String> list = Arrays.asList(getResources().getStringArray(R.array.word_list));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, layoutItemId, list);
            multiAutoCompleteTextView.setAdapter(adapter);
        }
    }

    private void initAutoCompleteTextView(View rootView, Activity activity) {
        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        if (autocompleteView != null) {
            int layoutItemId = android.R.layout.simple_dropdown_item_1line;
            List<String> dogList = Arrays.asList(getResources().getStringArray(R.array.word_list));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, layoutItemId, dogList);
            autocompleteView.setAdapter(adapter);
        }
    }
}
