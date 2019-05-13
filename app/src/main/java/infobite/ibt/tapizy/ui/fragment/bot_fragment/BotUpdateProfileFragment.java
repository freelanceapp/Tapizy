package infobite.ibt.tapizy.ui.fragment.bot_fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.SpinnerBotCategoryAdapter;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.SubCatList;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.bot_profile_data.BotDetailMainModal;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.BaseFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static infobite.ibt.tapizy.ui.activity.bot_activities.BotProfileActivity.fragmentUtilsBot;

public class BotUpdateProfileFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private static final int LOAD_IMAGE_GALLERY = 123;
    private static int PICK_IMAGE_CAMERA = 124;
    private static int PERMISSION_REQUEST_CODE = 456;
    private File finalFile = null;

    private String strCategory = "", strSubCategory = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.bot_fragment_profile_update, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();
        init();
        if (checkPermission()) {
            Alerts.show(mContext, "Permission granted");
        } else {
            requestPermission();
        }
    }

    private void setData() {
        ((TextView) rootView.findViewById(R.id.edtBotName)).setText(User.getBotDetail().getBotName());
        ((TextView) rootView.findViewById(R.id.txtSelectedColor)).setText(User.getBotDetail().getBotColor());
        ((EditText) rootView.findViewById(R.id.edtWeblink)).setText(User.getBotDetail().getWebisite());
        ((EditText) rootView.findViewById(R.id.edtBotDescription)).setText(User.getBotDetail().getDescription());

        Glide.with(mContext)
                .load(Constant.BOT_PROFILE_IMAGE + User.getBotDetail().getBotAvtar())
                .placeholder(R.drawable.img_chatbot)
                .into((ImageView) rootView.findViewById(R.id.imgBotProfile));
    }

    private void init() {
        rootView.findViewById(R.id.btnCreateBot).setOnClickListener(this);
        rootView.findViewById(R.id.imgBotProfile).setOnClickListener(this);

        rootView.findViewById(R.id.imgBack).setOnClickListener(this);
        rootView.findViewById(R.id.rlBlue).setOnClickListener(this);
        rootView.findViewById(R.id.rlGreen).setOnClickListener(this);
        rootView.findViewById(R.id.rlMaroon).setOnClickListener(this);
        rootView.findViewById(R.id.rlOlive).setOnClickListener(this);
        rootView.findViewById(R.id.rlPurple).setOnClickListener(this);
        rootView.findViewById(R.id.rlTeal).setOnClickListener(this);

        botCategorySpinner();
    }

    /*
     * BotDetail profile select and upload
     * */
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Alerts.show(mContext, "Permission not granted");
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void selectImage() {
        try {
            PackageManager pm = mContext.getPackageManager();
            int permission = pm.checkPermission(Manifest.permission.CAMERA, mContext.getPackageName());
            if (permission == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] choose = {"Pick From Camera", "Choose From Gallery", "Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                builder.setTitle("Select Option");
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choose[which].equals("Pick From Camera")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            activity.startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (choose[which].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            activity.startActivityForResult(i, LOAD_IMAGE_GALLERY);
                        } else if (choose[which].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(mContext, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOAD_IMAGE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Alerts.show(mContext, "Please give permission");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ((ImageView) rootView.findViewById(R.id.imgBotProfile)).setImageBitmap(photo);
                Uri tempUri = getImageUri(mContext, photo);
                finalFile = new File(getRealPathFromURI(tempUri));
                //api hit
                updateBotProfileImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == LOAD_IMAGE_GALLERY && resultCode == RESULT_OK && null != data) {
            final Uri uriImage = data.getData();
            final InputStream inputStream;
            try {
                inputStream = mContext.getContentResolver().openInputStream(uriImage);
                final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                ((ImageView) rootView.findViewById(R.id.imgBotProfile)).setImageBitmap(imageMap);
                String imagePath2 = getPath(uriImage);
                finalFile = new File(imagePath2);
                //api hit
                updateBotProfileImage();
            } catch (FileNotFoundException e) {
                Toast.makeText(mContext, "Image not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            ((ImageView) rootView.findViewById(R.id.imgBotProfile)).setImageDrawable(getResources().getDrawable(R.drawable.ic_profile_img));
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (mContext.getContentResolver() != null) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String strPath = cursor.getString(column_index);
        cursor.close();
        return strPath;
    }

    /*
     * Bot profile image update
     * */
    private void updateBotProfileImage() {
        String botId = User.getBotDetail().getId();
        if (cd.isNetworkAvailable()) {
            RequestBody id = RequestBody.create(MediaType.parse("text/plain"), botId);
            RequestBody imageBodyFile = RequestBody.create(MediaType.parse("image/*"), finalFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("u_avtar", finalFile.getName(),
                    imageBodyFile);

            RetrofitService.getBotDetail(new Dialog(mContext), retrofitApiClient.botUpdateProfileImage(id, imagePart), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    BotDetailMainModal mainModal = (BotDetailMainModal) result.body();
                    if (!mainModal.getError()) {
                        User.setBotDetail(mainModal.getBot());
                        Alerts.show(mContext, mainModal.getMessage());
                    } else {
                        Alerts.show(mContext, mainModal.getMessage());
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    /*
     * Category and sub category spinner
     * */
    private void botCategorySpinner() {
        final List<SubCatList> items = new ArrayList<>();
        for (int i = 0; i < Constant.CATEGORY_LIST.length; i++) {
            String strName = Constant.CATEGORY_LIST[i];
            SubCatList subCatList = new SubCatList(String.valueOf(i), strName);
            items.add(subCatList);
        }

        SpinnerBotCategoryAdapter botCategoryAdapter = new SpinnerBotCategoryAdapter(mContext, R.layout.spinner_category_layout, items);
        final Spinner spinnerList = rootView.findViewById(R.id.spinnerCategory);
        spinnerList.setAdapter(botCategoryAdapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    subCategoryApi(String.valueOf(position));
                    strCategory = items.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String mainCategory = User.getBotDetail().getBotType();
        for (int i = 0; i < items.size(); i++) {
            if (mainCategory.equals(items.get(i).getName())) {
                final int finalI = i;
                spinnerList.post(new Runnable() {
                    @Override
                    public void run() {
                        spinnerList.setSelection(finalI);
                    }
                });
                subCategoryApi(String.valueOf(i));
            }
        }
    }

    private void subCategoryApi(String strCategoryId) {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getResponseData(new Dialog(mContext), retrofitApiClient.subCategory(strCategoryId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("bot_sub_cat");
                            if (jsonArray.length() > 0) {
                                final List<SubCatList> items = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String strSubCat = object.getString("name");
                                    SubCatList subCatList = new SubCatList(String.valueOf(i), strSubCat);
                                    items.add(subCatList);
                                }
                                botSubCategorySpinner(items);
                            } else {
                                Alerts.show(mContext, "Sub category not found");
                            }

                        } else {
                            Alerts.show(mContext, jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void botSubCategorySpinner(final List<SubCatList> items) {
        SpinnerBotCategoryAdapter botCategoryAdapter = new SpinnerBotCategoryAdapter(mContext, R.layout.spinner_category_layout, items);
        Spinner spinnerList = rootView.findViewById(R.id.spinnerSubCategory);
        spinnerList.setAdapter(botCategoryAdapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSubCategory = items.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String subType = User.getBotDetail().getBotSubType();
        for (int i = 0; i < items.size(); i++) {
            if (subType.equals(items.get(i).getName())) {
                spinnerList.setSelection(i);
            }
        }
    }

    /******/
    /*
     * Click method
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBotProfile:
                try {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, LOAD_IMAGE_GALLERY);
                    } else {
                        selectImage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnCreateBot:
                updateBotApi();
                break;
            case R.id.rlBlue: {
                rootView.findViewById(R.id.btnCreateBot).setBackgroundColor(getResources().getColor(R.color.bot_blue));
                rootView.findViewById(R.id.viewA).setBackgroundColor(getResources().getColor(R.color.bot_blue));
                int[] a = {R.id.txtBlue, R.id.txtGreen, R.id.txtMaroon, R.id.txtOlive, R.id.txtPurple, R.id.txtTeal};
                int[] b = {R.id.rlC_Blue, R.id.rlC_Green, R.id.rlC_Maroon, R.id.rlC_Olive, R.id.rlC_Purple, R.id.rlC_Teal};
                setColorClick("Blue", a, b);
                break;
            }
            case R.id.rlGreen: {
                rootView.findViewById(R.id.btnCreateBot).setBackgroundColor(getResources().getColor(R.color.bot_green));
                rootView.findViewById(R.id.viewA).setBackgroundColor(getResources().getColor(R.color.bot_green));
                int[] a = {R.id.txtGreen, R.id.txtMaroon, R.id.txtOlive, R.id.txtPurple, R.id.txtTeal, R.id.txtBlue};
                int[] b = {R.id.rlC_Green, R.id.rlC_Maroon, R.id.rlC_Olive, R.id.rlC_Purple, R.id.rlC_Teal, R.id.rlC_Blue};
                setColorClick("Green", a, b);
                break;
            }
            case R.id.rlMaroon: {
                rootView.findViewById(R.id.btnCreateBot).setBackgroundColor(getResources().getColor(R.color.bot_maroon));
                rootView.findViewById(R.id.viewA).setBackgroundColor(getResources().getColor(R.color.bot_maroon));
                int[] a = {R.id.txtMaroon, R.id.txtOlive, R.id.txtPurple, R.id.txtTeal, R.id.txtBlue, R.id.txtGreen};
                int[] b = {R.id.rlC_Maroon, R.id.rlC_Olive, R.id.rlC_Purple, R.id.rlC_Teal, R.id.rlC_Green, R.id.rlC_Blue};
                setColorClick("Maroon", a, b);
                break;
            }
            case R.id.rlOlive: {
                rootView.findViewById(R.id.btnCreateBot).setBackgroundColor(getResources().getColor(R.color.bot_olive));
                rootView.findViewById(R.id.viewA).setBackgroundColor(getResources().getColor(R.color.bot_olive));
                int[] a = {R.id.txtOlive, R.id.txtPurple, R.id.txtTeal, R.id.txtBlue, R.id.txtGreen, R.id.txtMaroon};
                int[] b = {R.id.rlC_Olive, R.id.rlC_Purple, R.id.rlC_Teal, R.id.rlC_Green, R.id.rlC_Blue, R.id.rlC_Maroon};
                setColorClick("Olive", a, b);
                break;
            }
            case R.id.rlPurple: {
                rootView.findViewById(R.id.btnCreateBot).setBackgroundColor(getResources().getColor(R.color.bot_purple));
                rootView.findViewById(R.id.viewA).setBackgroundColor(getResources().getColor(R.color.bot_purple));
                int[] a = {R.id.txtPurple, R.id.txtTeal, R.id.txtBlue, R.id.txtGreen, R.id.txtMaroon, R.id.txtOlive};
                int[] b = {R.id.rlC_Purple, R.id.rlC_Teal, R.id.rlC_Green, R.id.rlC_Blue, R.id.rlC_Maroon, R.id.rlC_Olive};
                setColorClick("Purple", a, b);
                break;
            }
            case R.id.rlTeal: {
                rootView.findViewById(R.id.btnCreateBot).setBackgroundColor(getResources().getColor(R.color.bot_teal));
                rootView.findViewById(R.id.viewA).setBackgroundColor(getResources().getColor(R.color.bot_teal));
                int[] a = {R.id.txtTeal, R.id.txtBlue, R.id.txtGreen, R.id.txtMaroon, R.id.txtOlive, R.id.txtPurple};
                int[] b = {R.id.rlC_Teal, R.id.rlC_Green, R.id.rlC_Blue, R.id.rlC_Maroon, R.id.rlC_Olive, R.id.rlC_Purple};
                setColorClick("Teal", a, b);
                break;
            }
        }
    }

    private void setColorClick(String color, int[] a, int[] b) {
        Alerts.show(mContext, color);
        ((TextView) rootView.findViewById(R.id.txtSelectedColor)).setText(color);

        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                ((TextView) rootView.findViewById(a[i])).setTextColor(getResources().getColor(R.color.white));
            } else {
                ((TextView) rootView.findViewById(a[i])).setTextColor(getResources().getColor(R.color.gray_h));
            }
        }

        for (int i = 0; i < b.length; i++) {
            if (i == 0) {
                (rootView.findViewById(b[i])).setVisibility(View.VISIBLE);
            } else {
                (rootView.findViewById(b[i])).setVisibility(View.GONE);
            }
        }
    }

    /*
     * Create bot api
     * */
    private void updateBotApi() {
        String strId = User.getBotDetail().getId();
        String strBotName = ((EditText) rootView.findViewById(R.id.edtBotName)).getText().toString();
        String strBotColor = ((TextView) rootView.findViewById(R.id.txtSelectedColor)).getText().toString();
        String strWeblink = ((EditText) rootView.findViewById(R.id.edtWeblink)).getText().toString();
        String strDescription = ((EditText) rootView.findViewById(R.id.edtBotDescription)).getText().toString();

        if (strBotName.isEmpty()) {
            Alerts.show(mContext, "Enter name...!!!");
        } else if (strBotColor.isEmpty()) {
            Alerts.show(mContext, "Select color...!!!");
        } else if (strDescription.isEmpty()) {
            Alerts.show(mContext, "Enter description...!!!");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getBotDetail(new Dialog(mContext), retrofitApiClient.botUpdateProfile(strId, strBotName,
                        strBotColor, strCategory, strSubCategory, strWeblink, strDescription), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        BotDetailMainModal mainModal = (BotDetailMainModal) result.body();
                        if (!mainModal.getError()) {
                            User.setBotDetail(mainModal.getBot());
                            Alerts.show(mContext, mainModal.getMessage());
                            fragmentUtilsBot.replaceFragment(new BotProfileFragment(), Constant.BotProfileFragment, R.id.frameLayout);
                        } else {
                            Alerts.show(mContext, mainModal.getMessage());
                        }
                    }

                    @Override
                    public void onResponseFailed(String error) {
                        Alerts.show(mContext, error);
                    }
                });
            } else {
                cd.show(mContext);
            }
        }
    }
}
