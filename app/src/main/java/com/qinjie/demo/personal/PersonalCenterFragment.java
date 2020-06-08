package com.qinjie.demo.personal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.dysy.carttest.R;
import com.qinjie.demo.login.LoginMainActivity;
import com.MyApplication;
import com.qinjie.demo.pojo.user.UserOrdinary;
import com.qinjie.demo.pojo.user.UserQQ;
import com.qinjie.demo.utils.HttpClient1;
import com.qinjie.demo.utils.PersistenceToken;
import com.qinjie.demo.utils.ThreadPoolExecutorService;

import java.util.HashMap;


public class PersonalCenterFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public PersonalCenterFragment(){

    }
    private RecyclerView personal_center_recyclerView;
    /**
     * 退出登陆按钮
     */
    private Button personal_center_button_logged_out;
    private SwipeRefreshLayout personal_center_swipeRefreshLayout;
    private PersonalCenterAdapter personalCenterAdapter;
    final HashMap<String, String> mParams = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_personal_center, container,false);
        personal_center_recyclerView = view.findViewById(R.id.personal_center_recyclerView);
        personal_center_button_logged_out = view.findViewById(R.id.personal_center_button_logged_out);

        //退出登陆
        personal_center_button_logged_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersistenceToken.removeToken(getContext());
                //调用后台接口退出登陆
                ThreadPoolExecutorService.add(new Runnable() {
                    @Override
                    public void run() {
                        HttpClient1.doGet(getString(R.string.server_path) + getString(R.string.interface_user_logout), (((MyApplication) getActivity().getApplication()).getToken()));
                    }
                });

                getActivity().finish();
            }
        });

        setUserInfo();



        personal_center_swipeRefreshLayout = view.findViewById(R.id.personal_center_swipeRefreshLayout);
        //设置上拉刷新
        personal_center_swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        personal_center_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        personal_center_swipeRefreshLayout.
                        personal_center_swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "上拉刷新完成", Toast.LENGTH_SHORT).show();

//                        getFragmentManager().getFragments()
                    }
                }, 2000);
            }
        });
        return view;
    }

    /**
     * 保存修改用户信息的弹出页面内容
     */
    private View  pop_up_to_modify;
    @SuppressLint("HandlerLeak")
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: //获取用户信息成功
                    //把信息放到ui上
                    Bundle bundle = msg.getData();
                    mParams.put("nickname", bundle.get("nickname") + "");
                    mParams.put("gender", bundle.get("gender") + "");
                    mParams.put("profile", bundle.get("profile") + "");

                    //修改信息时弹出框的布局
                    LayoutInflater inflater = getLayoutInflater();
                    pop_up_to_modify  = inflater.inflate(R.layout.layout_edit_person_info,
                            null);
                    Spinner layout_edit_person_info_gender = pop_up_to_modify.findViewById(R.id.layout_edit_person_info_gender);
                    //头像
                    ImageView layout_edit_person_info_profile = pop_up_to_modify.findViewById(R.id.layout_edit_person_info_profile);
                    //头像的标签
                    layout_edit_person_info_profile.setTag(bundle.get("profile") + "");
                    EditText layout_edit_person_info_nickname = pop_up_to_modify.findViewById(R.id.layout_edit_person_info_nickname);
                    //头像加载现在的头像
                    Glide.with(getContext()).load(bundle.get("profile") + "").into(layout_edit_person_info_profile);

                    //性别
                    String[] data ={"男", "女"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,data);
                    layout_edit_person_info_gender.setAdapter(adapter);
                    if("男".equals(bundle.get("gender")+"")){
                        layout_edit_person_info_gender.setSelection(0);
                    }else{
                        layout_edit_person_info_gender.setSelection(1);
                    }
                    //昵称
                    layout_edit_person_info_nickname.setText(bundle.get("nickname") + "");
                    //设置选择头像时的弹出
                    layout_edit_person_info_profile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i,9090);
                        }
                    });



                    personalCenterAdapter = new PersonalCenterAdapter(getContext(), 8, mParams,pop_up_to_modify,myHandler,((MyApplication) getActivity().getApplication()).getToken());
                    personal_center_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    personal_center_recyclerView.setAdapter(personalCenterAdapter);
                    break;
                case 1: //获取用户信息失败
                    Toast.makeText(getContext(), "获取用户信息失败,请重试登陆",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginMainActivity.class);
                    startActivity(intent);
//                    getActivity().finish();
                    break;
                case 2: //修改个人信息
                    Bundle bundle3 = msg.getData();
                    Toast.makeText(getContext(), bundle3.get("toast")+"",Toast.LENGTH_SHORT).show();

                    setUserInfo();
                    break;
                case 3: //上传图片成功
                    Bundle bundle4 = msg.getData();
                    //设置新的头像到弹出框（修改框）
                    String profile = (String) bundle4.get("profile");
                    ImageView profileImage = pop_up_to_modify.findViewById(R.id.layout_edit_person_info_profile);
                    profileImage.setTag(profile);
                    Glide.with(getContext()).load(profile).into(profileImage);
                    break;
                default:
                    Toast.makeText(getContext(), "DEFAULT",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == 9090) {
            if (data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
// 获取选择照片的数据视图
                if(selectedImage!=null){
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
// 从数据视图中获取已选择图片的路径
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    HttpClient1.upLoadImg(getString(R.string.server_path)+getString(R.string.interface_files_upload),picturePath, myHandler, "");
                }
            }
        }
    }


    void setUserInfo(){
        //用户信息设置
        //调用接口得到用户信息
        ThreadPoolExecutorService.add(new Runnable() {
            @Override
            public void run() {
                String res = HttpClient1.doGet(getString(R.string.server_path) + getString(R.string.interface_user_info), ((MyApplication)getActivity().getApplication()).getToken());
                JSONObject jsonObject2 = JSONObject.parseObject(res, JSONObject.class);
                Message message = new Message();
                Bundle bundle = new Bundle();
                if(jsonObject2.get("code").equals(200)){
                    message.what = 0;
                    if(JSONObject.parseObject(""+JSONObject.parseObject("" + jsonObject2.get("datas"))).get("userType").equals(1)){
                        //普通用户
                        UserOrdinary userOrdinary = JSONObject.parseObject(""+JSONObject.parseObject("" + jsonObject2.get("datas")).get("userInfo"), UserOrdinary.class);
                        //获取用户信息成功
                        bundle.putString("nickname", userOrdinary.getmNickname());
                        bundle.putString("email", userOrdinary.getmEmail());
                        bundle.putString("gender", userOrdinary.getmGender());
                        bundle.putString("profile", userOrdinary.getmProfile());
                    }else{
                        //qq用户
                        UserQQ userQQ = JSONObject.parseObject(""+JSONObject.parseObject("" + jsonObject2.get("datas")).get("userInfo"), UserQQ.class);
                        //获取用户信息成功
                        bundle.putString("nickname", userQQ.getmNickname());
//                                bundle.putString("email", userQQ.getmEmail());
                        bundle.putString("gender", userQQ.getmGender());
                        bundle.putString("profile", userQQ.getmProfile());
                    }

                }else{
                    //失败
                    message.what = 1;
                }
                message.setData(bundle);
                myHandler.sendMessage(message);
            }
        });
    }
}
