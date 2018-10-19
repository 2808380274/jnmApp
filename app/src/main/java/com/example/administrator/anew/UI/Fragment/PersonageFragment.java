package com.example.administrator.anew.UI.Fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.anew.R;
import com.example.administrator.anew.UI.Activity.LoginActivity;
import com.example.administrator.anew.UI.wight.CircleImageView;
import com.example.administrator.anew.UI.wight.FontTextView;
import com.example.administrator.anew.Utils.CircleImageUtils;
import com.example.administrator.anew.Utils.FileStorage;
import com.example.administrator.anew.Utils.PermissionsActivity;
import com.example.administrator.anew.Utils.PermissionsChecker;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonageFragment extends Fragment implements View.OnClickListener {

    private CircleImageView user_photo;
    private static final int CODE_GALLERY_REQUEST = 0xa0;//相册选取
    private static final int CODE_CAMERA_REQUEST = 0xa1; //拍照
    private static final int CODE_RESULT_REQUEST = 0xa2; //剪裁图片
    private static final int REQUEST_PERMISSION = 0xa5;  //权限请求
    private static final int REQUEST_CODE = 0x11;        //返回数据

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private Uri imageUri;//原图保存地址
    private boolean isClickCamera;
    private String imagePath;
    private FontTextView tv_Login;
    private PopupWindow popupWindow;
    TextView tv_camera;
    TextView photo_album;
    TextView tv_exit;
    View rootview;
    private FontTextView tv_nickName;
    private FontTextView tv_Cycle;
    public PersonageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personage, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
         initView();

    }

    //初始化
    private void initView() {
        user_photo = (CircleImageView) getActivity().findViewById(R.id.user_photo_into);
        tv_Login=getActivity().findViewById(R.id.tv_mine_login);
        tv_nickName=getActivity().findViewById(R.id.tv_mine_nickName);
        tv_Cycle=getActivity().findViewById(R.id.tv_mine_friendCycle);

        user_photo.setOnClickListener(this);
        tv_Login.setOnClickListener(this);
        mPermissionsChecker = new PermissionsChecker(getActivity());
        Bitmap bitmap = CircleImageUtils.getCircleImageFromShare(getActivity());
        if (bitmap != null) {
            setImageToHeadView(bitmap);
        }

    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_photo_into:
                //chooseDialog();
                showPopupWindow();
                break;
            case R.id.tv_mine_login:
             startActivityForResult(new Intent(getActivity(), LoginActivity.class),REQUEST_CODE);
                break;
            //相册
            case R.id.pop_camera:
                popupWindow.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                        startPermissionsActivity();
                    } else {
                        selectFromAlbum();
                    }
                } else {
                    selectFromAlbum();
                }
                isClickCamera = false;
                break;
            case R.id.pop_album:
                popupWindow.dismiss();
                //检查权限(6.0以上做权限判断)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                        startPermissionsActivity();
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
                isClickCamera = true;
                break;
            case R.id.pop_exit:
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        user_photo.setImageBitmap(bitmap);
        //使用SharedPreferences保存
        CircleImageUtils.putCircleImageToShare(getActivity(), user_photo);
    }

    /**
     * 打开系统相机
     */
    private void openCamera() {
        File file = new FileStorage().createIconFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(getActivity(), "game", file);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, CODE_CAMERA_REQUEST);
    }

    /**
     * 从相册选择
     */
    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CODE_GALLERY_REQUEST);
    }

    /**
     * 裁剪
     */
    private void cropPhoto() {
        File file = new FileStorage().createCropFile();
        Uri outputUri = Uri.fromFile(file);//缩略图保存地址
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(imageUri, "image/*");
//        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    //返回数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_GALLERY_REQUEST: // 相册
                if (Build.VERSION.SDK_INT >= 19) {
                    handleImageOnKitKat(data);
                } else {
                    handleImageBeforeKitKat(data);
                }
                break;
            case CODE_CAMERA_REQUEST:  //拍照
                if (hasSdcard()) {
                    if (resultCode == RESULT_OK) {
                        cropPhoto();
                    }
                } else {
                    Toast.makeText(getActivity(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = null;
                try {
                    if (isClickCamera) {

                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                    } else {
                        bitmap = BitmapFactory.decodeFile(imagePath);
                    }
                    setImageToHeadView(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_PERMISSION://权限请求
                if (resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
//                    finish();
                } else {
                    if (isClickCamera) {
                        openCamera();
                    } else {
                        selectFromAlbum();
                    }
                }
                break;
            case REQUEST_CODE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (data != null) {
                       String name=data.getStringExtra("name");
                    String text=data.getStringExtra("text");
                    tv_nickName.setText(name);
                    tv_Cycle.setText(text);
                }

        }

    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        imageUri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), imageUri)) {
            //如果是document类型的uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(imageUri);
            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(imageUri, null);
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = imageUri.getPath();
        }

        cropPhoto();
    }

    private void handleImageBeforeKitKat(Intent intent) {
        imageUri = intent.getData();
        imagePath = getImagePath(imageUri, null);
        cropPhoto();
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(getActivity(), REQUEST_PERMISSION,
                PERMISSIONS);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
     //显示PopupWindow   这里可以封装，进行优化，先记下来,等待更新
    public void showPopupWindow() {
        if (popupWindow == null) {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_popupwindow, null);
            popupWindow = new PopupWindow(contentView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setAnimationStyle(R.style.contextpopAnim);

            //产生背景变暗效果
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 0.4f;
            getActivity().getWindow().setAttributes(lp);

            tv_camera = contentView.findViewById(R.id.pop_camera);
            photo_album = contentView.findViewById(R.id.pop_album);
            tv_exit = contentView.findViewById(R.id.pop_exit);
            tv_camera.setOnClickListener(this);
            tv_exit.setOnClickListener(this);
            photo_album.setOnClickListener(this);
            rootview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_personage, null);

            //设置popupWindow消失时的监听
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                //在dismiss中恢复透明度
                public void onDismiss() {
                    WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                    lp.alpha = 1f;
                    getActivity().getWindow().setAttributes(lp);
                }
            });
            popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        } else {
            //如果popupWindow正在显示，接下来隐藏
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                //产生背景变暗效果
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.4f;
                getActivity().getWindow().setAttributes(lp);
                popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
            }
        }
    }
}
