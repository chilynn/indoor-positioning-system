package com.neu.wifilocalization.activity;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.huiztech.common.http.HuizBaseJsonResponseHandler.Callback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.neu.tools.photoselector.activity.ViewPhotoActivity;
import com.neu.tools.photoselector.adapter.PhotoGridAdapter;
import com.neu.tools.photoselector.utils.BitmapStore;
import com.neu.tools.photoselector.utils.FileUtils;
import com.neu.tools.photoselector.view.PhotoSelectorPopWindow;
import com.neu.wifilocalization.R;
import com.neu.wifilocalization.application.App;
import com.neu.wifilocalization.application.Const;
import com.neu.wifilocalization.model.Category;
import com.neu.wifilocalization.model.IdServerResponse;
import com.neu.wifilocalization.model.Node;
import com.neu.wifilocalization.model.Position;
import com.neu.wifilocalization.model.ServerResponse;
import com.neu.wifilocalization.utils.AnimationUtils;
import com.neu.wifilocalization.utils.CommonUtils;

public class EditNodeActivity extends BaseActivity {

    private int MaxSelect = 9;
    private PhotoGridAdapter adapter;
    private PhotoSelectorPopWindow photoSelectorPopWindow;
    private String path = "";
    private ArrayList<String> uploadImages = new ArrayList<String>();

    @ViewInject(R.id.node_name_edit)
    private EditText nameEdit;
    @ViewInject(R.id.node_description_edit)
    private EditText descriptionEdit;
    @ViewInject(R.id.image_selector_grid_view)
    private GridView imageSelectorGridView;
    @ViewInject(R.id.node_category_text)
    private TextView categoryText;

    private int positionId;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.node_edit);
        ViewUtils.inject(this);
        initHeader();
        hideFooterBar();
        initPosition();
        initBodyView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategory();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
    }

    public void initHeader() {
        this.titleText.setText("编辑节点");
        this.functionButton.setImageResource(R.drawable.icon_confirm_white);
    }

    public void initBodyView() {
        imageSelectorGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new PhotoGridAdapter(this, MaxSelect);
        adapter.update();
        imageSelectorGridView.setAdapter(adapter);
        imageSelectorGridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == BitmapStore.bmp.size()) {
                    photoSelectorPopWindow = new PhotoSelectorPopWindow(EditNodeActivity.this, imageSelectorGridView,
                            new doCamera(), MaxSelect);
                } else {
                    Intent intent = new Intent(EditNodeActivity.this, ViewPhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
    }

    public void initPosition() {
        positionId = (Integer) controller.getHuizModel().get("positionId");
        System.out.println("编辑位置的ID" + positionId);
    }

    public void getCategory() {
        if (controller.getHuizModel().get("category") != null) {
            category = (Category) controller.getHuizModel().get("category");
            categoryText.setText(category.getName());
        }
    }

    public void getImagePath() {
        for (int i = 0; i < BitmapStore.drr.size(); i++) {
            String Str = BitmapStore.drr.get(i).substring(BitmapStore.drr.get(i).lastIndexOf("/") + 1,
                    BitmapStore.drr.get(i).lastIndexOf("."));
            uploadImages.add(FileUtils.SDPATH + Str + ".jpg");
        }
        for (int i = 0; i < uploadImages.size(); i++) {
            System.out.println("高清的压缩图片地址：" + uploadImages.get(i));
        }
    }

    public class doCamera implements OnClickListener {
        @Override
        public void onClick(View v) {
            photo();
            photoSelectorPopWindow.dismiss();
        }
    }

    public void photo() {
        String fileName = CommonUtils.getCurrentTime() + ".jpg";
        Intent openCameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName)));
        openCameraIntent.putExtra("return-data", true);
        path = Environment.getExternalStorageDirectory() + "/" + fileName;
        System.out.println("文件路径" + path);
        startActivityForResult(openCameraIntent, Const.CAMERA_REQUEST_CODE);
    }

    @OnClick(R.id.node_category_layout)
    public void selectCategory(View v) {
        model.put("nearCategory", null);
        Intent intent = new Intent();
        intent.setClass(EditNodeActivity.this, CategoryActivity.class);
        startActivity(intent);
        AnimationUtils.rightToLeft(this);
    }

    @OnClick(R.id.activity_base_function_button)
    public void uploadNode(View v) {
        getImagePath();
        if (isFilled()) {
            String name = nameEdit.getText().toString();
            String description = descriptionEdit.getText().toString();
            Node node = new Node();
            node.setName(name);
            node.setDescription(description);
            Position position = new Position();
            position.setId(positionId);
            boolean hideLoading = true;
            if (uploadImages.size() >= 1) {
                hideLoading = false;
            }
            controller.uploadNode(true, hideLoading, node, position, category.getId() + "",
                    new Callback<IdServerResponse>() {
                        @Override
                        public void execute(IdServerResponse uploadServerResponse) {
                            view.showMessage(uploadServerResponse.getMsg());
                            System.out.println("返回的id：" + uploadServerResponse.getId());
                            uploadImages(uploadServerResponse.getId() + "");
                        }
                    });
        }
    }

    public void uploadImages(String id) {
        if (uploadImages.size() >= 1) {
            controller.uploadImages(false, true, id, uploadImages, new Callback<ServerResponse>() {
                @Override
                public void execute(ServerResponse serverResponse) {
                    if (serverResponse.getState().equals("0")) {

                    } else {
                        BitmapStore.clearAll();
                        FileUtils.deleteDir();
                        finish();
                    }
                }
            });
        }
    }

    public boolean isFilled() {
        String hint = "";
        boolean pass = false;
        while (!pass) {
            if (!nameEdit.getText().toString().equals("")) {
                pass = true;
            } else {
                hint = "请输入名称";
                pass = false;
                break;
            }
            if (category!=null) {
                pass = true;
            } else {
                hint = "请选择类别";
                pass = false;
                break;
            }
            if (!descriptionEdit.getText().toString().equals("")) {
                pass = true;
            } else {
                hint = "请输入简介";
                pass = false;
                break;
            }
        }
        if (!pass) {
            view.showMessage(hint);
        } else {
        }
        return pass;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case Const.CAMERA_REQUEST_CODE:
            if (BitmapStore.drr.size() < MaxSelect) {
                if (!App.getInstance().fileUrl.equals("")) {
                    BitmapStore.drr.add(App.getInstance().fileUrl);
                    App.getInstance().fileUrl = "";
                }
            }
            break;
        }
    }

}
