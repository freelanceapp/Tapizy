package infobite.ibt.tapizy.ui.fragment.video_fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.VideoListAdapter;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.video_list.VideoListModel;
import infobite.ibt.tapizy.utils.BaseFragment;

import static infobite.ibt.tapizy.ui.activity.user_activities.trending_module.VideoActivity.videoFragmentManager;

public class VideoGalleryFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private VideoListAdapter obj_adapter;
    private ArrayList<VideoListModel> al_video = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_video_gallery, container, false);
        mContext = getActivity();
        activity= getActivity();
        init();
        return rootView;
    }

    private void init() {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view1);
        recyclerViewLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        fn_checkpermission();
    }

    private void fn_checkpermission() {
        /*RUN TIME PERMISSIONS*/
        if ((ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
            /*ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSIONS);*/
        } else {
            Log.e("Else", "Else");
            fn_video();
        }
    }

    public void fn_video() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_id, thum;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media._ID, MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = mContext.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));
            Log.e("column_id", cursor.getString(column_id));
            Log.e("thum", cursor.getString(thum));

            VideoListModel obj_model = new VideoListModel();
            obj_model.setBoolean_selected(false);
            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor.getString(thum));
            al_video.add(obj_model);
        }
        obj_adapter = new VideoListAdapter(mContext, al_video, activity, this);
        recyclerView.setAdapter(obj_adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_video();
                    } else {
                        Toast.makeText(mContext, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select:
                int pos = Integer.parseInt(v.getTag().toString());
                String strVideoPath = al_video.get(pos).getStr_path();
                String strVideoThumb = al_video.get(pos).getStr_thumb();
                VideoTrimmerFragment videoTrimmerFragment = new VideoTrimmerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("video_path", strVideoPath);
                bundle.putString("video_thumb", strVideoThumb);
                videoTrimmerFragment.setArguments(bundle);

                videoFragmentManager
                        .beginTransaction()
                        .replace(R.id.fram_container, videoTrimmerFragment,
                                Constant.VideoTrimmerFragment).commit();
                break;
        }
    }
}
