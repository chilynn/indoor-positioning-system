package com.neu.wifilocalization.mycenter.activity;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.activity.BaseActivity;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.application.Jumper;
import com.neu.wifilocalization.login.activity.LoginActivity;
import com.neu.wifilocalization.model.ServerResponse;
import com.neu.wifilocalization.model.User;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.BitmapUtil;
import com.neu.wifilocalization.utils.CommonUtils;

public class MyCenterActivity extends BaseActivity {

    @ViewInject(R.id.nickname_layout)
    private RelativeLayout nicknameLayout;
    @ViewInject(R.id.nickname_text)
    private TextView nicknameText;
    @ViewInject(R.id.signature_text)
    private TextView signatureText;
    @ViewInject(R.id.logout_layout)
    private RelativeLayout logoutLayout;
    @ViewInject(R.id.switch_avatar_layout)
    private RelativeLayout avatarLayout;
    @ViewInject(R.id.avatar_image)
    private ImageView avatarImage;

    /* 头像名称 */
    private String fileName = "";
    private String path = "";
    private boolean isCrop = false;
    private boolean isChoosing = false;
    private boolean isTakePhoto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.my_center_index);
        ViewUtils.inject(this);
        initHeader();
        hideFooterBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        updataUser();
    }

    public void initHeader() {
        this.titleText.setText("个人中心");
    }

    public void updataUser() {
        if (!isChoosing) {
            view.showBusy();
            if (isCrop) {
                final String filePath = CommonUtils.formatImageFilePath(fileName);
                controller.updateUser(false, false, loginedUser, filePath, new Callback<ServerResponse>() {
                    @Override
                    public void execute(ServerResponse serverResponse) {
                        File tempFile = new File(filePath);
                        if (tempFile.exists()) {
                            tempFile.delete();
                        }

                        isCrop = false;
                        view.showMessage(serverResponse.getMsg());
                        getUserInfo();
                        if (serverResponse.getState().equals("1")) {
                        }
                    }
                });
            } else {
                System.out.println("获取用户信息");
                getUserInfo();
            }
        }
    }

    public void getUserInfo() {
        controller.getUserInfo(false, false, loginedUser, "", loginedUser.getUsername(), new Callback<User>() {
            @Override
            public void execute(User user) {
                view.hideBusy();
                if (user.getNickname() != null) {
                    nicknameText.setText(user.getNickname());
                }
                if (user.getSignature() != null) {
                    signatureText.setText(user.getSignature());
                }
                if (user.getAvatar() != null) {
                    bitmapUtils.display(avatarImage, Const.BASE_IMAGE + "/" + user.getAvatar());
                    loginedUser.setAvatar(user.getAvatar());
                }
                try {
                    User queryUser = db.findFirst(Selector.from(User.class).where("username", "=",
                            loginedUser.getUsername()));
                    queryUser.setAvatar(user.getAvatar());
                    db.update(queryUser, "avatar");
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.nickname_layout)
    public void editNickname(View v) {
        controller.getHuizModel().put("editInfoType", "1");
        controller.getHuizModel().put("editContent", loginedUser.getNickname());
        Jumper.jump(MyCenterActivity.this, EditInfoActivity.class);
        AnimationUtils.rightToLeft(MyCenterActivity.this);
    }

    @OnClick(R.id.signature_layout)
    public void editSignature(View v) {
        controller.getHuizModel().put("editInfoType", "2");
        controller.getHuizModel().put("editContent", loginedUser.getSignature());
        Jumper.jump(MyCenterActivity.this, EditInfoActivity.class);
        AnimationUtils.rightToLeft(MyCenterActivity.this);
    }

    @OnClick(R.id.switch_avatar_layout)
    public void switchAvatar(View v) {
        showAvatarDialog();
    }

    @OnClick(R.id.logout_layout)
    public void logout(View v) {
        try {
            User lastLoginUser = db.findFirst(Selector.from(User.class).where("isLastLogin", "=", true));
            if (lastLoginUser != null) {
                lastLoginUser.setAutoLogin(false);
                db.update(lastLoginUser, null);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        App.getInstance().loginedUser = null;
        Jumper.jump(MyCenterActivity.this, LoginActivity.class);
        AnimationUtils.rightToLeft(MyCenterActivity.this);
        finish();
    }

    /**
     * 显示选择对话框
     */
    private void showAvatarDialog() {
        new AlertDialog.Builder(MyCenterActivity.this).setTitle("设置头像")
                .setItems(new String[] { "选择本地图片", "拍照" }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                        case 0: // 相册
                            Intent intentFromGallery = new Intent();
                            intentFromGallery.setType("image/*"); // 设置文件类型
                            intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intentFromGallery, Const.IMAGE_REQUEST_CODE);
                            break;
                        case 1: // 照相
                            fileName = CommonUtils.getCurrentTime() + ".jpg";
                            Intent intentFromCapture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            intentFromCapture.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName)));
                            startActivityForResult(intentFromCapture, Const.CAMERA_REQUEST_CODE);
                            break;
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
            case Const.IMAGE_REQUEST_CODE: // 相册
                isChoosing = true;
                Uri uri = data.getData();
                if (uri != null) {
                    Cursor cursor = getContentResolver().query(uri, new String[] { MediaStore.Images.Media.DATA },
                            null, null, null);
                    cursor.moveToFirst();
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String[] fragments = path.split("/");
                    fileName = fragments[fragments.length - 1];
                    cursor.close();
                }
                startPhotoCrop(data.getData());
                break;
            case Const.CAMERA_REQUEST_CODE: // 照相
                isTakePhoto = true;
                isChoosing = true;
                path = Environment.getExternalStorageDirectory() + "/" + fileName;
                File tempFile = new File(path);
                startPhotoCrop(Uri.fromFile(tempFile));
                break;
            case Const.RESULT_REQUEST_CODE:
                saveBitmap2file(data);
                break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     * 
     * @param uri
     */
    public void startPhotoCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Const.RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     * 
     * @param picdata
     */
    private void saveBitmap2file(Intent data) {
        isCrop = true;
        isChoosing = false;
        Bundle extras = data.getExtras();
        Bitmap photo = extras.getParcelable("data");
        BitmapUtil.saveBitmap2file(photo, fileName);
        File tempFile = new File(path);
        if (tempFile.exists() && isTakePhoto) {
            tempFile.delete();
            isTakePhoto = false;
        }
    }

}
