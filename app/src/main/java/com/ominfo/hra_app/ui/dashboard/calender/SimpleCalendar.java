package com.ominfo.hra_app.ui.dashboard.calender;

import static com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment.calenderHolidayLeave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.util.LogUtil;

import java.util.Calendar;

public class SimpleCalendar extends LinearLayout {

    public static final String CUSTOM_GREY = "#ffffff";
    public static final String[] ENG_MONTH_NAMES = {"January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};

    public static Button selectedDayButton;
    public static Button[] days;
    public static LinearLayout weekOneLayout;
    public static LinearLayout weekTwoLayout;
    public static LinearLayout weekThreeLayout;
    public static LinearLayout weekFourLayout;
    public static LinearLayout weekFiveLayout;
    public static LinearLayout weekSixLayout;
    public static LinearLayout[] weeks;

    public static int currentDateDay, chosenDateDay, currentDateMonth,
            chosenDateMonth, currentDateYear, chosenDateYear,
            pickedDateDay, pickedDateMonth, pickedDateYear;
    public static int userMonth, userYear;
    public static DayClickListener mListener;

    public static Calendar calendar;
    public static LinearLayout.LayoutParams defaultButtonParams;
    public static LinearLayout.LayoutParams userButtonParams;

    public SimpleCalendar(Context context) {
        super(context);
        init(context);
    }

    public SimpleCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SimpleCalendar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @SuppressLint("SetTextI18n")
    public void init(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        View view = LayoutInflater.from(context).inflate(R.layout.calender_layout, this, true);
        calendar = Calendar.getInstance();

        weekOneLayout = view.findViewById(R.id.calendar_week_1);
        weekTwoLayout = view.findViewById(R.id.calendar_week_2);
        weekThreeLayout = view.findViewById(R.id.calendar_week_3);
        weekFourLayout = view.findViewById(R.id.calendar_week_4);
        weekFiveLayout = view.findViewById(R.id.calendar_week_5);
        weekSixLayout = view.findViewById(R.id.calendar_week_6);
        TextView currentDate = view.findViewById(R.id.current_date);
        TextView currentMonth = view.findViewById(R.id.current_month);
        AppCompatImageView pre = view.findViewById(R.id.prePage);
        AppCompatImageView next = view.findViewById(R.id.nextPage);

        currentDateDay = chosenDateDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (userMonth != 0 && userYear != 0) {
            currentDateMonth = chosenDateMonth = userMonth;
            currentDateYear = chosenDateYear = userYear;
        } else {
            currentDateMonth = chosenDateMonth = calendar.get(Calendar.MONTH);
            currentDateYear = chosenDateYear = calendar.get(Calendar.YEAR);
        }
        float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,6f, getResources().getDisplayMetrics());
        currentDate.setTextSize(myTextSize); currentMonth.setTextSize(myTextSize);
        currentDate.setText("" + currentDateDay);
        currentMonth.setText(ENG_MONTH_NAMES[currentDateMonth]);

        initializeDaysWeeks();
        if (userButtonParams != null) {
            defaultButtonParams = userButtonParams;
        } else {
            defaultButtonParams = getdaysLayoutParams();
        }
        addDaysinCalendar(defaultButtonParams, context, metrics);

        initCalendarWithDate(chosenDateYear, chosenDateMonth, chosenDateDay);
        pre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDateDay =01;
                        currentDateMonth = 06;
                                currentDateYear = 2022;
                LogUtil.printToastMSG(context,"yoo");
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                calendar = Calendar.getInstance();

                weekOneLayout = view.findViewById(R.id.calendar_week_1);
                weekTwoLayout = view.findViewById(R.id.calendar_week_2);
                weekThreeLayout = view.findViewById(R.id.calendar_week_3);
                weekFourLayout = view.findViewById(R.id.calendar_week_4);
                weekFiveLayout = view.findViewById(R.id.calendar_week_5);
                weekSixLayout = view.findViewById(R.id.calendar_week_6);
                TextView currentDate = view.findViewById(R.id.current_date);
                TextView currentMonth = view.findViewById(R.id.current_month);
                AppCompatImageView pre = view.findViewById(R.id.prePage);
                AppCompatImageView next = view.findViewById(R.id.nextPage);

                currentDateDay = chosenDateDay = calendar.get(Calendar.DAY_OF_MONTH);

                if (userMonth != 0 && userYear != 0) {
                    currentDateMonth = chosenDateMonth = userMonth;
                    currentDateYear = chosenDateYear = userYear;
                } else {
                    currentDateMonth = chosenDateMonth = calendar.get(Calendar.MONTH);
                    currentDateYear = chosenDateYear = calendar.get(Calendar.YEAR);
                }
                float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,6f, getResources().getDisplayMetrics());
                currentDate.setTextSize(myTextSize); currentMonth.setTextSize(myTextSize);
                currentDate.setText("" + currentDateDay);
                currentMonth.setText(ENG_MONTH_NAMES[currentDateMonth]);

                initializeDaysWeeks();
                if (userButtonParams != null) {
                    defaultButtonParams = userButtonParams;
                } else {
                    defaultButtonParams = getdaysLayoutParams();
                }
                addDaysinCalendar(defaultButtonParams, context, metrics);

                initCalendarWithDate(chosenDateYear, chosenDateMonth, chosenDateDay);
            }
        });
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDateDay = 01;
                        currentDateMonth =04;
                                currentDateYear =2022;
                LogUtil.printToastMSG(context,"yoo uu");
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                calendar = Calendar.getInstance();

                weekOneLayout = view.findViewById(R.id.calendar_week_1);
                weekTwoLayout = view.findViewById(R.id.calendar_week_2);
                weekThreeLayout = view.findViewById(R.id.calendar_week_3);
                weekFourLayout = view.findViewById(R.id.calendar_week_4);
                weekFiveLayout = view.findViewById(R.id.calendar_week_5);
                weekSixLayout = view.findViewById(R.id.calendar_week_6);
                TextView currentDate = view.findViewById(R.id.current_date);
                TextView currentMonth = view.findViewById(R.id.current_month);
                AppCompatImageView pre = view.findViewById(R.id.prePage);
                AppCompatImageView next = view.findViewById(R.id.nextPage);

                currentDateDay = chosenDateDay = calendar.get(Calendar.DAY_OF_MONTH);

                if (userMonth != 0 && userYear != 0) {
                    currentDateMonth = chosenDateMonth = userMonth;
                    currentDateYear = chosenDateYear = userYear;
                } else {
                    currentDateMonth = chosenDateMonth = calendar.get(Calendar.MONTH);
                    currentDateYear = chosenDateYear = calendar.get(Calendar.YEAR);
                }
                float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,6f, getResources().getDisplayMetrics());
                currentDate.setTextSize(myTextSize); currentMonth.setTextSize(myTextSize);
                currentDate.setText("" + currentDateDay);
                currentMonth.setText(ENG_MONTH_NAMES[currentDateMonth]);

                initializeDaysWeeks();
                if (userButtonParams != null) {
                    defaultButtonParams = userButtonParams;
                } else {
                    defaultButtonParams = getdaysLayoutParams();
                }
                addDaysinCalendar(defaultButtonParams, context, metrics);

                initCalendarWithDate(chosenDateYear, chosenDateMonth, chosenDateDay);
            }
        });


    }

    public static void initializeDaysWeeks() {
        weeks = new LinearLayout[6];
        days = new Button[6 * 7];
        weeks[0] = weekOneLayout;
        weeks[1] = weekTwoLayout;
        weeks[2] = weekThreeLayout;
        weeks[3] = weekFourLayout;
        weeks[4] = weekFiveLayout;
        weeks[5] = weekSixLayout;
    }

    private void setHolidays(int dayNumber,int i){
        for(int k=0;k<calenderHolidayLeave.size();k++) {
            String[] str=calenderHolidayLeave.get(k).getDate().split("-");
            int strVal = Integer.parseInt(str[2]);
            if (dayNumber == strVal) {
                days[i].setBackgroundColor(getResources().getColor(R.color.deep_yellow));
                days[i].setTextColor(Color.WHITE);
            }
        }
    }
    private void initCalendarWithDate(int year, int month, int day) {
        if (calendar == null)
            calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        int daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        chosenDateYear = year;
        chosenDateMonth = month;
        chosenDateDay = day;

        calendar.set(year, month, 1);
        int firstDayOfCurrentMonth = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        int dayNumber = 1;
        int daysLeftInFirstWeek;
        int indexOfDayAfterLastDayOfMonth;

        if (firstDayOfCurrentMonth != 1) {
            daysLeftInFirstWeek = firstDayOfCurrentMonth;
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth;
            for (int i = firstDayOfCurrentMonth; i < firstDayOfCurrentMonth + daysInCurrentMonth; ++i) {
                float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,4f, getResources().getDisplayMetrics());
                days[i].setTextSize(myTextSize);
                if (currentDateMonth == chosenDateMonth
                        && currentDateYear == chosenDateYear
                        && dayNumber == currentDateDay) {
                    days[i].setBackgroundColor(getResources().getColor(R.color.pink));
                    days[i].setTextColor(Color.WHITE);
                } else {
                    days[i].setTextColor(Color.BLACK);
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }

                int[] dateArr = new int[3];
                dateArr[0] = dayNumber;
                dateArr[1] = chosenDateMonth;
                dateArr[2] = chosenDateYear;
                days[i].setTag(dateArr);
                days[i].setText(String.valueOf(dayNumber));
                days[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //onDayClick(v);
                    }
                });
                ++dayNumber;
            }
        } else {
            daysLeftInFirstWeek = 8;
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth;
            for (int i = 8; i < 8 + daysInCurrentMonth; ++i) {
                float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,4f, getResources().getDisplayMetrics());
                days[i].setTextSize(myTextSize);

                if (currentDateMonth == chosenDateMonth
                        && currentDateYear == chosenDateYear
                        && dayNumber == currentDateDay) {
                    days[i].setBackgroundColor(getResources().getColor(R.color.pink));
                    days[i].setTextColor(Color.WHITE);
                } else {
                    days[i].setTextColor(Color.BLACK);
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }

                int[] dateArr = new int[3];
                dateArr[0] = dayNumber;
                dateArr[1] = chosenDateMonth;
                dateArr[2] = chosenDateYear;
                days[i].setTag(dateArr);
                days[i].setText(String.valueOf(dayNumber));

                //String[] test = days[i].getText().toString().split("-");

                setHolidays(dayNumber,i);
                days[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //onDayClick(v);
                    }
                });
                ++dayNumber;
            }
        }

        if (month > 0)
            calendar.set(year, month - 1, 1);
        else
            calendar.set(year - 1, 11, 1);
        int daysInPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = daysLeftInFirstWeek - 1; i >= 0; --i) {
            int[] dateArr = new int[3];
            float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,4f, getResources().getDisplayMetrics());
            days[i].setTextSize(myTextSize);
            if (chosenDateMonth > 0) {
                if (currentDateMonth != chosenDateMonth - 1
                        || currentDateYear != chosenDateYear
                        || daysInPreviousMonth != currentDateDay) {
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }

                dateArr[0] = daysInPreviousMonth;
                dateArr[1] = chosenDateMonth - 1;
                dateArr[2] = chosenDateYear;
            } else {
                if (currentDateMonth != 11
                        || currentDateYear != chosenDateYear - 1
                        || daysInPreviousMonth != currentDateDay) {
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }

                dateArr[0] = daysInPreviousMonth;
                dateArr[1] = 11;
                dateArr[2] = chosenDateYear - 1;
            }

            days[i].setTag(dateArr);
            days[i].setText(String.valueOf(daysInPreviousMonth--));
            days[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onDayClick(v);
                }
            });
        }

        int nextMonthDaysCounter = 1;
        for (int i = indexOfDayAfterLastDayOfMonth; i < days.length; ++i) {
            int[] dateArr = new int[3];
            float myTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,4f, getResources().getDisplayMetrics());
            days[i].setTextSize(myTextSize);
            if (chosenDateMonth < 11) {
                if (currentDateMonth == chosenDateMonth + 1
                        && currentDateYear == chosenDateYear
                        && nextMonthDaysCounter == currentDateDay) {
                    days[i].setBackgroundColor(getResources().getColor(R.color.pink));
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }

                dateArr[0] = nextMonthDaysCounter;
                dateArr[1] = chosenDateMonth + 1;
                dateArr[2] = chosenDateYear;
            } else {
                if (currentDateMonth == 0
                        && currentDateYear == chosenDateYear + 1
                        && nextMonthDaysCounter == currentDateDay) {
                    days[i].setBackgroundColor(getResources().getColor(R.color.pink));
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT);
                }

                dateArr[0] = nextMonthDaysCounter;
                dateArr[1] = 0;
                dateArr[2] = chosenDateYear + 1;
            }

            days[i].setTag(dateArr);
            days[i].setTextColor(Color.parseColor(CUSTOM_GREY));
            days[i].setText(String.valueOf(nextMonthDaysCounter++));
            days[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onDayClick(v);
                }
            });
        }

        calendar.set(chosenDateYear, chosenDateMonth, chosenDateDay);
    }

    public void onDayClick(View view) {
        mListener.onDayClick(view);

        if (selectedDayButton != null) {
            if (chosenDateYear == currentDateYear
                    && chosenDateMonth == currentDateMonth
                    && pickedDateDay == currentDateDay) {
                selectedDayButton.setBackgroundColor(getResources().getColor(R.color.pink));
                selectedDayButton.setTextColor(Color.WHITE);
            } else {
                selectedDayButton.setBackgroundColor(Color.TRANSPARENT);
                if (selectedDayButton.getCurrentTextColor() != Color.RED) {
                    selectedDayButton.setTextColor(getResources()
                            .getColor(R.color.color_blue_10));
                }
            }
        }

        selectedDayButton = (Button) view;
        if (selectedDayButton.getTag() != null) {
            int[] dateArray = (int[]) selectedDayButton.getTag();
            pickedDateDay = dateArray[0];
            pickedDateMonth = dateArray[1];
            pickedDateYear = dateArray[2];
        }

        if (pickedDateYear == currentDateYear
                && pickedDateMonth == currentDateMonth
                && pickedDateDay == currentDateDay) {
            selectedDayButton.setBackgroundColor(getResources().getColor(R.color.pink));
            selectedDayButton.setTextColor(Color.WHITE);
        } else {
            selectedDayButton.setBackgroundColor(getResources().getColor(R.color.light_grey_30));
            if (selectedDayButton.getCurrentTextColor() != Color.RED) {
                selectedDayButton.setTextColor(Color.WHITE);
            }
        }
    }

    public static void addDaysinCalendar(LayoutParams buttonParams, Context context,
                                   DisplayMetrics metrics) {
        int engDaysArrayCounter = 0;

        for (int weekNumber = 0; weekNumber < 6; ++weekNumber) {
            for (int dayInWeek = 0; dayInWeek < 7; ++dayInWeek) {
                final Button day = new Button(context);
                day.setTextColor(Color.parseColor(CUSTOM_GREY));
                day.setBackgroundColor(Color.WHITE);
                day.setLayoutParams(buttonParams);
                day.setTextSize((int) metrics.density * 8);
                day.setSingleLine();

                days[engDaysArrayCounter] = day;
                weeks[weekNumber].addView(day);

                ++engDaysArrayCounter;
            }
        }
    }

    private LayoutParams getdaysLayoutParams() {
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;
        return buttonParams;
    }

    public void setUserDaysLayoutParams(LinearLayout.LayoutParams userButtonParams) {
        this.userButtonParams = userButtonParams;
    }

    public void setUserCurrentMonthYear(int userMonth, int userYear) {
        this.userMonth = userMonth;
        this.userYear = userYear;
    }

    public void setDayBackground(Drawable userDrawable) {
        Drawable userDrawable1 = userDrawable;
    }

    public interface DayClickListener {
        void onDayClick(View view);
    }

    public void setCallBack(DayClickListener mListener) {
        this.mListener = mListener;
    }

}