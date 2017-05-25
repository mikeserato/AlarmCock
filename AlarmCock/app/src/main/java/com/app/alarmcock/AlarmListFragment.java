package com.app.alarmcock;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


public class AlarmListFragment extends Fragment {
    private int[] mImageResIds;
    private String[] mNames;
    private String[] mLabels;
    private OnAlarmSelected mListener;

    public static AlarmListFragment newInstance() {
        return new AlarmListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnAlarmSelected) {
            mListener = (OnAlarmSelected) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnAlarmSelected.");
        }

        try{
            SQLiteOpenHelper alarmDatabaseHelper = new AlarmDatabaseHelper(context);
            SQLiteDatabase db = alarmDatabaseHelper.getReadableDatabase();

            long numRows = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM ALARM", null);
            int rowLength = (int) numRows;
            mNames = new String[rowLength];
            mLabels = new String[rowLength];

            Cursor cursor = db.query("ALARM", new String[]{"TIME"}, null, null, null, null, null);

            int i = 0;

            if(cursor.moveToFirst()){
                do{
                    String timeText = cursor.getString(0);
                    mNames[i] = timeText;
                    mLabels[i] = "Alarm " + (i+1);
                    i++;
                }while(cursor.moveToNext());
            }

            cursor.close();
            db.close();
        }catch(SQLiteException e){}

        // Get alarm names
        final Resources resources = context.getResources();

        // Get alarm images.
        final TypedArray typedArray = resources.obtainTypedArray(R.array.images);
        final int imageCount = mNames.length;
        mImageResIds = new int[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageResIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        final Activity activity = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
        recyclerView.setAdapter(new AlarmAdapter(activity));
        return view;
    }

    class AlarmAdapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater mLayoutInflater;

        public AlarmAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new ViewHolder(mLayoutInflater
                    .inflate(R.layout.recycler_item_alarm, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final int imageResId = mImageResIds[position];
            final String name = mNames[position];
            final String label = mLabels[position];
            viewHolder.setData(imageResId, name, label);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onAlarmSelected(imageResId, name);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mNames.length;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Views
        private ImageView mImageView;
        private TextView mNameTextView;
        private Switch mSwitch;

        private ViewHolder(View itemView) {
            super(itemView);

            // Get references to image and name.
            mImageView = (ImageView) itemView.findViewById(R.id.alarm_image);
            mNameTextView = (TextView) itemView.findViewById(R.id.name);
            mSwitch = (Switch) itemView.findViewById(R.id.switch1);

        }

        private void setData(int imageResId, String name, String label) {
            mImageView.setImageResource(imageResId);
            mNameTextView.setText(label);
            mSwitch.setText(name);
        }
    }

    public interface OnAlarmSelected {
        void onAlarmSelected(int imageResId, String name);
    }
}