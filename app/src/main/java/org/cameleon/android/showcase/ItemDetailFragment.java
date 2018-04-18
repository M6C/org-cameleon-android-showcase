package org.cameleon.android.showcase;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.cameleon.android.showcase.dummy.ComponentContent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(mItem.idDetail, container, false);

        Activity activity = getActivity();
        if (activity != null) {
            switch (mItem.idDetail) {
                case R.layout.item_detail_text: {
                    initAutoCompleteTextView(rootView, activity);
                    initMultiAutoCompleteTextView(rootView, activity);
                    initDatePickerTextView(rootView, activity);
                    initTimePickerTextView(rootView, activity);
                    break;
                }
                case R.layout.item_detail_widget: {
                    initWebView(rootView, activity);
                    break;
                }
                case R.layout.item_detail_botton_sheet: {
                    initBottmSheet(rootView, activity);
                    break;
                }
                default:
            }
        }

        return rootView;
    }

    private void initWebView(View rootView, Activity activity) {
//        WebView webView = rootView.findViewById(R.id.webView);
//        webView.loadUrl("https://www.google.com/maps");
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

    private void initDatePickerTextView(View rootView, final Activity activity) {
        final EditText date = (EditText)rootView.findViewById(R.id.editText9);
        final DateFormat sdf = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM);
        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                date.setText(sdf.format(cal.getTime()));
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(activity, listener, year, month, day).show();
            }
        });
    }

    private void initTimePickerTextView(View rootView, final Activity activity) {
        final EditText time = (EditText)rootView.findViewById(R.id.editText8);
        final DateFormat sdf = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
        final Calendar cal = Calendar.getInstance();
        final int hour = cal.get(Calendar.HOUR);
        final int minute = cal.get(Calendar.MINUTE);
        final TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                cal.set(Calendar.HOUR, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                time.setText(sdf.format(cal.getTime()));
            }
        };
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(activity, listener, hour, minute, true);
                dialog.show();
            }
        });
    }

    private void initBottmSheet(View rootView, final Activity activity) {
        View bottomSheet = rootView.findViewById(R.id.bottom_sheet);
        if (bottomSheet != null) {
            final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(300);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheet.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                            //set a new Timer
                            Timer timer = new Timer();
                            TimerTask timerTask = new TimerTask() {
                                public void run() {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do stuff to update UI here!
                                            Toast.makeText(activity, "Its been 5 seconds", Toast.LENGTH_SHORT).show();
                                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        }
                                    });
                                }
                            };
                            timer.schedule(timerTask, 5000);


//                        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
//
//                        //Schedule a task to run every 5 seconds (or however long you want)
//                        scheduleTaskExecutor.schedule(new Runnable() {
//                            @Override
//                            public void run() {
//                                activity.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // Do stuff to update UI here!
//                                        Toast.makeText(activity, "Its been 5 seconds", Toast.LENGTH_SHORT).show();
//                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                                    }
//                                });
//
//                            }
//                        }, 5, TimeUnit.SECONDS); // or .MINUTES, .HOURS etc.
                        } else {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }
                    return false;
                }
            });
            bottomSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                        //set a new Timer
                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            public void run() {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do stuff to update UI here!
                                        Toast.makeText(activity, "Its been 5 seconds", Toast.LENGTH_SHORT).show();
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                    }
                                });
                            }
                        };
                        timer.schedule(timerTask, 5000);





//                        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
//
//                        //Schedule a task to run every 5 seconds (or however long you want)
//                        scheduleTaskExecutor.schedule(new Runnable() {
//                            @Override
//                            public void run() {
//                                activity.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // Do stuff to update UI here!
//                                        Toast.makeText(activity, "Its been 5 seconds", Toast.LENGTH_SHORT).show();
//                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                                    }
//                                });
//
//                            }
//                        }, 5, TimeUnit.SECONDS); // or .MINUTES, .HOURS etc.
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
            });
        }
    }
}
