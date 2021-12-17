/*
package com.ominfo.crm_solution.ui.lr_number.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkAPIServices;
import com.ominfo.crm_solution.network.NetworkURLs;
import com.ominfo.crm_solution.network.RetroNetworkModule;
import com.ominfo.crm_solution.ui.lr_number.model.CheckLrRequest;
import com.ominfo.crm_solution.ui.lr_number.model.CheckLrResponse;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsLrImage;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AddLrAdapter extends RecyclerView.Adapter<AddLrAdapter.ViewHolder> {
    ListItemSelectListener listItemSelectListener;
    ListItemSelectCamera listItemSelectCamera;
    ListItemSelectRemove listItemSelectRemove;
    ListItemSelectRemoveImage listItemSelectRemoveImage;
    private List<VehicleDetailsLrImage> mListData;
    private Context mContext;
    private String mDate;
    boolean editStatus;
    private String mUserKey = "";
    private AppDatabase mDb;
    public AddLrAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public AddLrAdapter(Context context,boolean editStatus, List<VehicleDetailsLrImage> listData, ListItemSelectListener itemClickListener
            , ListItemSelectCamera listItemSelectCamera,ListItemSelectRemove listItemSelectRemove
    ,ListItemSelectRemoveImage listItemSelectRemoveImage) {
        this.mListData = listData;
        this.mContext = context;
        this.editStatus = editStatus;
        this.listItemSelectListener = itemClickListener;
        this.listItemSelectCamera = listItemSelectCamera;
        this.listItemSelectRemove = listItemSelectRemove;
        this.listItemSelectRemoveImage = listItemSelectRemoveImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_add_lr
                , parent, false);
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        //get dataset data
        LoginResultTable loginResultTable = mDb.getDbDAO().getLoginData();
        if (loginResultTable != null) {
            mUserKey = loginResultTable.getUserKey();
        }
        return new ViewHolder(listItem);
    }

    public void updateList(List<VehicleDetailsLrImage> list){
        mListData = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
         return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mListData != null) {
            holder.setIsRecyclable(false);
            holder.imgCamera.setImageDrawable(null);
            holder.etNoOfLr.setEnabled(false);
            //holder.imgCamera.setClickable(false);
            //holder.imgCamera.setEnabled(false);
            holder.imgRemoveImage.setVisibility(View.INVISIBLE);
            holder.etNoOfLr.setText(mListData.get(position).getLr());
            try{
                if(mListData.get(position).getImageUri()!=null) {
                    if(!mListData.get(position).getImageUri().equals("")) {
                    holder.imgCamera.setImageDrawable(null);
                    //holder.imgCamera.setImageURI(Uri.parse(mListData.get(position).getImageUri()));
                    AppUtils.loadImage(mContext, Uri.parse(mListData.get(position).getImageUri()), holder.imgCamera,holder.mProgressBar);
                    //holder.imgRemoveImage.setVisibility(View.VISIBLE);
                }}
                */
/*if(mListData.get(position).getImage()!=null) {
                if(!mListData.get(position).getImage().equals("")) {
                    holder.imgCamera.setImageDrawable(null);
                    //holder.imgRemoveImage.setImageBitmap(getBitmapFromURL(mListData.get(position).getImage());
                    AppUtils.loadImageURL(mContext, mListData.get(position).getImage(), holder.imgCamera,holder.mProgressBar);
                    holder.imgRemoveImage.setVisibility(View.VISIBLE);
                }}*//*

             else if(mListData.get(position).getImagePath()!=null) {
                 if(!mListData.get(position).getImagePath().equals("")) {
                     holder.imgCamera.setImageDrawable(null);
                     //holder.imgCamera.setImageURI(Uri.fromFile(new File(mListData.get(position).getImagePath())));
                     AppUtils.loadImageURL(mContext, mListData.get(position).getImagePath(), holder.imgCamera, holder.mProgressBar);
                     //holder.imgRemoveImage.setVisibility(View.VISIBLE);
                 }
               }
                else {
                    holder.imgCamera.setImageDrawable(null);
                    holder.imgCamera.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_camera_with_card));
                    holder.imgRemoveImage.setVisibility(View.INVISIBLE);
                }
            }catch (Exception e){}
                if(!editStatus) {
                    holder.etNoOfLr.setEnabled(true);
                    holder.imgCamera.setClickable(true);
                    holder.imgCamera.setEnabled(true);
                    holder.imgRemove.setVisibility(View.INVISIBLE);
                    holder.imgRemoveImage.setVisibility(View.INVISIBLE);
                    if(mListData.size()>1){
                        holder.imgRemove.setVisibility(View.VISIBLE);
                        holder.imgRemoveImage.setVisibility(View.VISIBLE);
                        holder.etNoOfLr.setEnabled(true);
                        holder.imgCamera.setClickable(true);
                        holder.imgCamera.setEnabled(true);
                    }
                }
            if(position==mListData.size()-1){
                if(!editStatus) {
                    holder.etNoOfLr.setEnabled(true);
                    holder.imgCamera.setClickable(true);
                    holder.imgCamera.setEnabled(true);
                    holder.imgAdd.setVisibility(View.VISIBLE);
                    holder.imgRemoveImage.setVisibility(View.VISIBLE);
                    if(mListData.size()>1){
                        holder.imgRemove.setVisibility(View.VISIBLE);
                        holder.imgRemoveImage.setVisibility(View.VISIBLE);
                        holder.etNoOfLr.setEnabled(true);
                        holder.imgCamera.setClickable(true);
                        holder.imgCamera.setEnabled(true);
                    }
                }
                holder.imgAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.etNoOfLr.setEnabled(true);
                        holder.imgCamera.setClickable(true);
                        holder.imgCamera.setEnabled(true);
                        mListData.add(new VehicleDetailsLrImage("","", null,null));
                        notifyDataSetChanged();
                        listItemSelectListener.onItemClick(mListData,position);
                        //LogUtil.printToastMSG(mContext,"size - "+mListData.size());
                        if(position==mListData.size()-1){
                            if(!editStatus) {
                                holder.imgAdd.setVisibility(View.VISIBLE);
                                if(mListData.size()>1){
                                    holder.imgRemoveImage.setVisibility(View.INVISIBLE);
                                    holder.imgRemove.setVisibility(View.VISIBLE);
                                    holder.etNoOfLr.setEnabled(false);
                                    //holder.imgCamera.setClickable(false);
                                    //holder.imgCamera.setEnabled(false);
                                }
                            }
                        }
                        else {
                            if(!editStatus) {
                                holder.imgAdd.setVisibility(View.GONE);
                                holder.imgRemove.setVisibility(View.VISIBLE);
                                holder.imgRemoveImage.setVisibility(View.VISIBLE);
                                holder.etNoOfLr.setEnabled(true);
                                holder.imgCamera.setClickable(true);
                                holder.imgCamera.setEnabled(true);
                            }
                        }
                    }
                });
            }
            else {
                if(!editStatus) {
                    holder.imgAdd.setVisibility(View.GONE);
                }
            }
             }
        else
            {
                holder.setIsRecyclable(true);
            }

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListData.remove(position);
                notifyDataSetChanged();
                //notifyItemRemoved(position);
                listItemSelectRemove.onItemClick(mListData,position);
            }
        });

        holder.imgRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.imgCamera.setImageDrawable(null);
                holder.imgCamera.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_camera_with_card));
                mListData.get(position).setImagePath(null);
                mListData.get(position).setImage("");
                mListData.get(position).setImageUri(null);
                notifyDataSetChanged();
                //notifyItemRemoved(position);
                listItemSelectRemoveImage.onItemClick(mListData,position);
            }
        });
        holder.etNoOfLr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!TextUtils.isEmpty(holder.etNoOfLr.getEditableText().toString().trim())) {
                        checkLrNo(holder.etNoOfLr.getEditableText().toString().trim(), holder.etNoOfLr);
                    }else {
                        holder.etNoOfLr.setError("Please Enter Lr");
                    }
                }
            }
        });
        holder.etNoOfLr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(!TextUtils.isEmpty(holder.etNoOfLr.getEditableText().toString().trim())) {
                        checkLrNo(holder.etNoOfLr.getEditableText().toString().trim(), holder.etNoOfLr);
                    }else {
                        holder.etNoOfLr.setError("Please Enter Lr");
                    }
                }
                return false;
            }
        });
         holder.imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                try {
                    bitmap = AppUtils.getBitmapFromView(holder.imgCamera);
                }catch (Exception e){
                    LogUtil.printToastMSG(mContext,"Fail to load Image");
                }
                mListData.set(position,new VehicleDetailsLrImage(holder.etNoOfLr.getEditableText().toString().trim(),mListData.get(position).getImage(), mListData.get(position).getImageUri(), mListData.get(position).getImagePath()));
                listItemSelectCamera.onItemClick(mListData,position,bitmap,editStatus);

            }
        });

        holder.etNoOfLr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is empty
                //if(s.length()>0) {
                    mListData.get(position).setLr(s.toString());//position,new VehicleDetailsLrImage(,mListData.get(position).getImage(),mListData.get(position).getImageUri()));mContext.notifyDataSetChanged();
                //}
            }
        });

    }

    private void checkLrNo(String lrNo,TextInputEditText editText){
        NetworkAPIServices mNetworkAPIServices = RetroNetworkModule.getInstance().getAPI();
        CheckLrRequest checkLrRequest = new CheckLrRequest();
        checkLrRequest.setUserkey(mUserKey);
        checkLrRequest.setLrNo(lrNo);
        Gson gson = new Gson();
        String bodyInStringFormat = gson.toJson(checkLrRequest);
        Call<CheckLrResponse> call = mNetworkAPIServices.checkLrNo(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL,
                DynamicAPIPath.POST_CHECK_NO),bodyInStringFormat);

        call.enqueue(new Callback<CheckLrResponse>() {
            @Override
            public void onResponse(@NonNull Call<CheckLrResponse> call, @NonNull retrofit2.Response<CheckLrResponse> response) {
                try {
                    //dismissLoader();
                    if (response.body() != null) {
                        CheckLrResponse userInfo = response.body();
                        if (userInfo.getStatus().equals("1")) {
                          //LogUtil.printToastMSG(mContext,userInfo.getMessage());
                        } else {
                            editText.setError(userInfo.getMessage());
                            //LogUtil.printToastMSG(mContext,userInfo.getMessage());
                        }
                    }
                } catch (Exception e) {
                    editText.setError("LR no already " + "exists");
                    //LogUtil.printToastMSG(mContext,"LR no already " + "exists");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckLrResponse> call, @NonNull Throwable t) {
                //dismissLoader();
               */
/* errorMessage(getString(R.string.ERR_SERVER_ERROR), new ErrorCallbacks() {
                    @Override
                    public void onOkClick() {
                        //DO something
                    }
                });*//*

                //Util.showToastMessage(LoginActivity.this, getString(R.string.msg_try_again_later));
            }
        });
    }

      public static Bitmap getBitmapFromURL(String src) {
    try {
        Log.e("src", src);
        URL url = new URL(src);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        Log.e("Bitmap", "returned");
        return myBitmap;
    } catch (IOException e) {
        e.printStackTrace();
        Log.e("Exception", e.getMessage());
        return null;
    }
}

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgAdd,imgCamera,imgRemove,imgRemoveImage ;
        TextInputEditText etNoOfLr;
        AppCompatTextView tvDb;
        TextInputLayout inputetNoOfLr;
        ProgressBar mProgressBar;

        ViewHolder(View itemView) {
            super(itemView);
            imgAdd = itemView.findViewById(R.id.imgAdd);
            imgRemoveImage = itemView.findViewById(R.id.imgRemoveImage);
            imgRemove = itemView.findViewById(R.id.imgRemove);
            imgCamera = itemView.findViewById(R.id.imgCamera);
            etNoOfLr = itemView.findViewById(R.id.etNoOfLr);
            //inputetNoOfLr = itemView.findViewById(R.id.inputetNoOfLr);
            tvDb = itemView.findViewById(R.id.tvDb);
            mProgressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(List<VehicleDetailsLrImage> mData,int position);
    }

    public interface ListItemSelectCamera {
        void onItemClick(List<VehicleDetailsLrImage> mData,int position,Bitmap bitmap,boolean status);
    }

    public interface ListItemSelectRemove {
        void onItemClick(List<VehicleDetailsLrImage> mData,int position);
    }

    public interface ListItemSelectRemoveImage {
        void onItemClick(List<VehicleDetailsLrImage> mData,int position);
    }
}
*/
