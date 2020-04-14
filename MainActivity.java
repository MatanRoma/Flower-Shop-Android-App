package com.example.flowershop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String deliveryType;
    LinearLayout radioBtnDynamicLayout;
    RadioGroup[] radioGroup;
    double totalPrice = 0;
    String year, month, day, hour, minute;

    private boolean confirmed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** <-------- Makes the keyboard stay closed when opening the app --------> */
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /** <-------- Makes the background stay still when the keyboard opens --------> */
        getWindow().setBackgroundDrawableResource(R.drawable.background);

        /** <-------- Variables --------> */
        final ScrollView scrollView = findViewById(R.id.main_scroll_view);
        final RelativeLayout radioBtnLayout = findViewById(R.id.radio_btn_layout);
        final RelativeLayout checkBoxLayout = findViewById(R.id.checkBox_Layout);
        final RelativeLayout spinnerLayout = findViewById(R.id.spinner_layout);
        final RelativeLayout paymentLayout = findViewById(R.id.payment_layout);
        final RelativeLayout goodbyeLayout = findViewById(R.id.goodbye_layout);


        final Spinner spinner = (Spinner) findViewById(R.id.spinner_1);
        final String[] delivery = {getResources().getString(R.string.delivery_options), getResources().getString(R.string.pick_up_from_store), getResources().getString(R.string.standard_delivery), getResources().getString(R.string.accurate_delivery)};
        final ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(MainActivity.this, R.layout.spinner_text, delivery);

        final TextView welcome = findViewById(R.id.tv_welcome);

        final EditText radioBtn_et = findViewById(R.id.radio_btn_et);
        final Button radioBtn_btn = findViewById(R.id.radio_btn_done);
        final ImageView radio_arrow_1 = findViewById(R.id.radio_arrow_1);
        final ImageView radio_arrow_2 = findViewById(R.id.radio_arrow_2);

        final CheckBox cb_chocolate = findViewById(R.id.checkBox_chocolate);
        final CheckBox cb_bear = findViewById(R.id.checkBox_bear);
        final CheckBox cb_wine = findViewById(R.id.checkBox_wine);

        final float density = getResources().getDisplayMetrics().density;

        final EditText address_edit_txt= findViewById(R.id.address_edit_txt);

        final Button date_btn = findViewById(R.id.delivery_date_btn);
        final TextView date_txt = findViewById(R.id.delivery_date_tv);
        final ImageView date_ic = findViewById(R.id.delivery_date_ic);

        final Button time_btn = findViewById(R.id.delivery_time_btn);
        final TextView time_txt = findViewById(R.id.delivery_time_tv);
        final ImageView time_ic = findViewById(R.id.delivery_time_ic);

        final TextView payment_tv = findViewById(R.id.payment_message_tv);

        final Button again_btn = findViewById(R.id.goodbye_btn);
        final Button confirm_btn = findViewById(R.id.approve_btn);
        /** <-------- Variables --------> */


        /** <-------- Spinner --------> */
        langAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(langAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deliveryType = parent.getItemAtPosition(position).toString();

                if (deliveryType.equals(getResources().getString(R.string.delivery_options))) {
                    address_edit_txt.setVisibility(View.GONE);
                    date_btn.setVisibility(View.GONE);
                    date_txt.setVisibility(View.GONE);
                    date_ic.setVisibility(View.GONE);
                    time_btn.setVisibility(View.GONE);
                    time_txt.setVisibility(View.GONE);
                    time_ic.setVisibility(View.GONE);
                    paymentLayout.setVisibility(View.GONE);

                    confirm_btn.setEnabled(false);
                } else if (deliveryType.equals(getResources().getString(R.string.pick_up_from_store))) {
                    address_edit_txt.setVisibility(View.GONE);
                    date_btn.setVisibility(View.VISIBLE);
                    time_btn.setVisibility(View.GONE);
                    time_txt.setVisibility(View.GONE);
                    time_ic.setVisibility(View.GONE);
                    paymentLayout.setVisibility(View.VISIBLE);

                    date_btn.setText(R.string.pickup_date_btn);
                    payment_tv.setText(R.string.payment_message_pickup_tv);

                    confirm_btn.setEnabled(true);
                } else if (deliveryType.equals(getResources().getString(R.string.standard_delivery))) {
                    address_edit_txt.setVisibility(View.VISIBLE);
                    date_btn.setVisibility(View.VISIBLE);
                    time_btn.setVisibility(View.GONE);
                    time_txt.setVisibility(View.GONE);
                    time_ic.setVisibility(View.GONE);
                    paymentLayout.setVisibility(View.VISIBLE);

                    date_btn.setText(R.string.delivery_date_btn);
                    payment_tv.setText(R.string.payment_message_delivery_tv);

                    confirm_btn.setEnabled(true);
                } else if (deliveryType.equals(getResources().getString(R.string.accurate_delivery))) {
                    address_edit_txt.setVisibility(View.VISIBLE);
                    date_btn.setVisibility(View.VISIBLE);
                    time_btn.setVisibility(View.VISIBLE);
                    if (hour != null || minute != null) {
                        time_txt.setVisibility(View.VISIBLE);
                        time_ic.setVisibility(View.VISIBLE);
                    }
                    paymentLayout.setVisibility(View.VISIBLE);

                    date_btn.setText(R.string.delivery_date_btn);
                    payment_tv.setText(R.string.payment_message_delivery_tv);

                    confirm_btn.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /** <-------- Spinner --------> */


        /** <-------- Dynamic Radio Button --------> */
        radioBtn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBtnDynamicLayout = findViewById(R.id.radio_btn_dynamic_layout);

                radioBtnDynamicLayout.removeAllViews();

                if (radioBtn_et.getText().toString().matches("^[1-9][0-9]*$")) {
                    checkBoxLayout.setVisibility(View.VISIBLE);
                    spinnerLayout.setVisibility(View.VISIBLE);
                    confirm_btn.setVisibility(View.VISIBLE);

                    int numOfBouquets = Integer.parseInt(radioBtn_et.getText().toString());
                    radioGroup = new RadioGroup[numOfBouquets];

                    for (int i = 0; i < numOfBouquets; i++) {
                        HorizontalScrollView hz = new HorizontalScrollView(MainActivity.this);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMarginStart((int) (density * 10));
                        layoutParams.setMarginEnd((int) (density * 10));

                        RadioGroup.LayoutParams radioGrpParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        radioGrpParams.setMargins((int) (density * 10), (int) (density * 10), (int) (density * 10), (int) (density * 10));

                        hz.setLayoutParams(layoutParams);


                        radioGroup[i] = new RadioGroup(MainActivity.this);

                        RadioButton gaya = new RadioButton(MainActivity.this);
                        RadioButton anat = new RadioButton(MainActivity.this);
                        RadioButton hila = new RadioButton(MainActivity.this);
                        RadioButton sivan = new RadioButton(MainActivity.this);
                        RadioButton tamar = new RadioButton(MainActivity.this);

                        TextView radioTV = new TextView(MainActivity.this);
                        radioTV.setText(String.format("%s %d:", getResources().getString(R.string.bouquet_number), (i + 1)));
                        radioTV.setTextSize(density * 6);
                        radioTV.setGravity(Gravity.CENTER);
                        radioTV.setTextColor(getResources().getColor(R.color.colorBlack));
                        radioTV.setAllCaps(false);
                        radioTV.setPadding((int) (density * 5), (int) (density * 5), (int) (density * 5), (int) (density * 5));
                        radioTV.setBackgroundColor(getResources().getColor(R.color.colorTransparent));

                        gaya.setId(R.id.gaya_btn_id);
                        anat.setId(R.id.anat_btn_id);
                        hila.setId(R.id.hila_btn_id);
                        sivan.setId(R.id.sivan_btn_id);
                        tamar.setId(R.id.tamar_btn_id);

                        gaya.setBackgroundResource(R.drawable.radio_btn_selector);
                        anat.setBackgroundResource(R.drawable.radio_btn_selector);
                        hila.setBackgroundResource(R.drawable.radio_btn_selector);
                        sivan.setBackgroundResource(R.drawable.radio_btn_selector);
                        tamar.setBackgroundResource(R.drawable.radio_btn_selector);

                        gaya.setButtonDrawable(R.drawable.bouquet_gaya);
                        anat.setButtonDrawable(R.drawable.bouquet_anat);
                        hila.setButtonDrawable(R.drawable.bouquet_hila);
                        sivan.setButtonDrawable(R.drawable.bouquet_sivan);
                        tamar.setButtonDrawable(R.drawable.bouquet_tamar);

                        gaya.setText(R.string.gaya_btn_txt);
                        anat.setText(R.string.anat_btn_txt);
                        hila.setText(R.string.hila_btn_txt);
                        sivan.setText(R.string.sivan_btn_txt);
                        tamar.setText(R.string.tamar_btn_txt);

                        gaya.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        anat.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        hila.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        sivan.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tamar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        gaya.setLayoutParams(radioGrpParams);
                        anat.setLayoutParams(radioGrpParams);
                        hila.setLayoutParams(radioGrpParams);
                        sivan.setLayoutParams(radioGrpParams);
                        tamar.setLayoutParams(radioGrpParams);

                        gaya.setPadding((int) (density * 5), (int) (density * 5), (int) (density * 5), (int) (density * 5));
                        anat.setPadding((int) (density * 5), (int) (density * 5), (int) (density * 5), (int) (density * 5));
                        hila.setPadding((int) (density * 5), (int) (density * 5), (int) (density * 5), (int) (density * 5));
                        sivan.setPadding((int) (density * 5), (int) (density * 5), (int) (density * 5), (int) (density * 5));
                        tamar.setPadding((int) (density * 5), (int) (density * 5), (int) (density * 5), (int) (density * 5));
                        radioGroup[i].addView(gaya);
                        radioGroup[i].addView(anat);
                        radioGroup[i].addView(hila);
                        radioGroup[i].addView(sivan);
                        radioGroup[i].addView(tamar);

                        gaya.setChecked(true);

                        radioGroup[i].setOrientation(LinearLayout.HORIZONTAL);

                        hz.addView(radioGroup[i]);

                        radioBtnDynamicLayout.addView(radioTV);
                        radioBtnDynamicLayout.addView(hz);

                        /** <-------- Animation for Scrolling down after pressing "Continue" button --------> */
                        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                        alphaAnimation.setDuration(500);
                        alphaAnimation.setRepeatCount(7);
                        alphaAnimation.setRepeatMode(Animation.REVERSE);
                        radio_arrow_1.startAnimation(alphaAnimation);
                        radio_arrow_2.startAnimation(alphaAnimation);
                        /** <-------- Animation for Scrolling down after pressing "Continue" button --------> */
                    }
                } else {
                    checkBoxLayout.setVisibility(View.GONE);
                    spinnerLayout.setVisibility(View.GONE);
                    address_edit_txt.setVisibility(View.GONE);
                    date_btn.setVisibility(View.GONE);
                    date_txt.setVisibility(View.GONE);
                    date_ic.setVisibility(View.GONE);
                    time_btn.setVisibility(View.GONE);
                    time_txt.setVisibility(View.GONE);
                    time_ic.setVisibility(View.GONE);
                    paymentLayout.setVisibility(View.GONE);
                    confirm_btn.setVisibility(View.GONE);

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.radio_btn_edit_txt_error), Toast.LENGTH_SHORT).show();
                }
                /** <-------- Close the keyboard when pressing the  button --------> */
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                } catch (Exception e) {}
                /** <-------- Close the keyboard when pressing the  button --------> */
            }
        });
        /** <-------- Dynamic Radio Button --------> */


        /** <-------- Date Picker Dialog --------> */
        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.date_dialog, null);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();

                builder.setView(view);

                TextView delivery_date_tv = (TextView) view.findViewById(R.id.date_dlg_tv);

                if (deliveryType.equals(getResources().getString(R.string.pick_up_from_store))) {
                    delivery_date_tv.setText(R.string.select_pickup_day);
                } else if (deliveryType.equals(getResources().getString(R.string.standard_delivery))) {
                    delivery_date_tv.setText(R.string.select_delivery_day);
                } else if (deliveryType.equals(getResources().getString(R.string.accurate_delivery))) {
                    delivery_date_tv.setText(R.string.select_delivery_day);
                }

                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_dlg_picker);
                datePicker.setMinDate(System.currentTimeMillis() - 1000);

                builder.setPositiveButton(getResources().getString(R.string.dlg_btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        year = "" + datePicker.getYear();
                        month = "" + (datePicker.getMonth() + 1);
                        day = "" + datePicker.getDayOfMonth();

                        date_txt.setText(day + "/" + month + "/" + year);
                        date_txt.setVisibility(View.VISIBLE);
                        date_ic.setVisibility(View.VISIBLE);
                        //Toast.makeText(MainActivity.this, day + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.dlg_btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                final AlertDialog alertDialog = builder.create();
                Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(R.color.colorAccent);

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize((5 * density));

                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize((5 * density));
                    }
                });

                alertDialog.show();
            }
        });
        /** <-------- Date Picker Dialog --------> */


        /** <-------- Time Picker Dialog --------> */
        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.time_dialog, null);

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();

                builder.setView(view);

                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_dlg_picker);
                timePicker.setIs24HourView(true);

                builder.setPositiveButton(getResources().getString(R.string.dlg_btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /** <-------- Fills the zeros in the hour's and minute's strings --------> */
                        if (timePicker.getCurrentHour() < 10) {
                            hour = "0" + timePicker.getCurrentHour();
                        } else {
                            hour = timePicker.getCurrentHour().toString();
                        }

                        if (timePicker.getCurrentMinute() < 10) {
                            minute = "0" + timePicker.getCurrentMinute();
                        } else {
                            minute = timePicker.getCurrentMinute().toString();
                        }
                        /** <-------- Fills the zeros in the hour's and minute's strings --------> */

                        time_txt.setText(hour + ":" + minute);
                        time_txt.setVisibility(View.VISIBLE);
                        time_ic.setVisibility(View.VISIBLE);
                        //Toast.makeText(MainActivity.this, hour + ":" + minute, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.dlg_btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                final AlertDialog alertDialog = builder.create();
                Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(R.color.colorAccent);

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize((5 * density));

                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize((5 * density));
                    }
                });

                alertDialog.show();
            }
        });
        /** <-------- Time Picker Dialog --------> */


        /** <-------- Confirmation Dialog --------> */
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** <-------- Checks if the user had insert an address, date or time --------> */
                if (address_edit_txt.getText().toString().trim().length() == 0 && spinner.getSelectedItemPosition() != 1) {
                    scrollView.scrollTo(0, (int) (spinnerLayout.getY()));
                    Toast.makeText(MainActivity.this, R.string.address_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (day == null || month == null || year == null) {
                    scrollView.scrollTo(0, (int) (spinnerLayout.getY()));
                    Toast.makeText(MainActivity.this, R.string.date_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((hour == null || minute == null) && spinner.getSelectedItemPosition() == 3) {
                    scrollView.scrollTo(0, (int) (spinnerLayout.getY()));
                    Toast.makeText(MainActivity.this, R.string.time_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                /** <-------- Checks if the user had insert an address, date or time --------> */

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null);

                /*LayoutInflater inflater = MainActivity.this.getLayoutInflater();*/

                builder.setView(view);

                /*LinearLayout timeLayout = (LinearLayout) findViewById(R.id.dlg_time_layout);
                LinearLayout addressLayout = (LinearLayout) findViewById(R.id.dlg_address_layout);*/

                TextView dlg_tv_time = (TextView) view.findViewById(R.id.dlg_tv_time);
                TextView dlg_tv_address = (TextView) view.findViewById(R.id.dlg_tv_address);

                totalPrice = 0;
                for (int i = 0; i < radioGroup.length; i++) {
                    if (R.id.gaya_btn_id == radioGroup[i].getCheckedRadioButtonId()) {
                        totalPrice += 149.90;
                    } else if (R.id.anat_btn_id == radioGroup[i].getCheckedRadioButtonId()) {
                        totalPrice += 139.90;
                    } else if (R.id.hila_btn_id == radioGroup[i].getCheckedRadioButtonId()) {
                        totalPrice += 129.90;
                    } else if (R.id.sivan_btn_id == radioGroup[i].getCheckedRadioButtonId()) {
                        totalPrice += 119.90;
                    } else if (R.id.tamar_btn_id == radioGroup[i].getCheckedRadioButtonId()) {
                        totalPrice += 109.90;
                    }
                }

                if (cb_chocolate.isChecked())
                    totalPrice += (19.90 * radioGroup.length);
                if (cb_bear.isChecked())
                    totalPrice += (29.90 * radioGroup.length);
                if (cb_wine.isChecked())
                    totalPrice += (99.90 * radioGroup.length);


                if (spinner.getSelectedItemPosition() == 2) {
                    totalPrice += 14.90;

                    dlg_tv_address.setText(address_edit_txt.getText().toString());
                }
                if (spinner.getSelectedItemPosition() == 3) {
                    totalPrice += 24.90;

                    dlg_tv_time.setText(hour + ":" + minute);

                    dlg_tv_address.setText(address_edit_txt.getText().toString());
                }


                TextView dlg_tv_deliveryType = (TextView) view.findViewById(R.id.dlg_tv_delivery_type);
                TextView dlg_tv_date = (TextView) view.findViewById(R.id.dlg_tv_date);
                TextView dlg_tv_price = (TextView) view.findViewById(R.id.dlg_tv_price);
                TextView dlg_tv_bouquets = (TextView) view.findViewById(R.id.dlg_bouquets_sum);

                dlg_tv_deliveryType.setText(delivery[spinner.getSelectedItemPosition()]);
                dlg_tv_date.setText(day + "/"+ month +"/" + year);
                dlg_tv_price.setText(String.format("₪%.2f", totalPrice));
                dlg_tv_bouquets.setText("" + radioGroup.length);

                builder.setPositiveButton(getResources().getString(R.string.dlg_btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmed = true;

                        radioBtnLayout.setVisibility(View.GONE);
                        checkBoxLayout.setVisibility(View.GONE);
                        spinnerLayout.setVisibility(View.GONE);
                        address_edit_txt.setVisibility(View.GONE);
                        date_btn.setVisibility(View.GONE);
                        date_txt.setVisibility(View.GONE);
                        date_ic.setVisibility(View.GONE);
                        time_btn.setVisibility(View.GONE);
                        time_txt.setVisibility(View.GONE);
                        time_ic.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        confirm_btn.setVisibility(View.GONE);

                        goodbyeLayout.setVisibility(View.VISIBLE);
                        welcome.setText(R.string.goodbye);

                        Toast.makeText(MainActivity.this, getResources().getString(R.string.confirmed_toast), Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.dlg_btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmed = false;
                        dialog.cancel();
                    }
                });

                final AlertDialog alertDialog = builder.create();
                Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(R.color.colorAccent);

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize((5 * density));

                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize((5 * density));
                    }
                });

                alertDialog.show();
            }
        });
        /** <-------- Confirmation Dialog --------> */


        /** <-------- Restarting the Application --------> */
        again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcome.setText(R.string.welcome);
                radioBtnLayout.setVisibility(View.VISIBLE);

                radioBtnDynamicLayout.removeAllViews();
                radioBtn_et.setText("");
                cb_chocolate.setChecked(false);
                cb_bear.setChecked(false);
                cb_wine.setChecked(false);
                spinner.setSelection(0);
                address_edit_txt.setText("");
                date_txt.setText("");
                time_txt.setText("");
                year = month = day = hour = minute = null;

                goodbyeLayout.setVisibility(View.GONE);
            }
        });
        /** <-------- Restarting the Application --------> */
    }
}
