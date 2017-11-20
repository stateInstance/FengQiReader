package com.omg.ireader.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;

import com.omg.ireader.R;
import com.omg.ireader.RxBus;
import com.omg.ireader.event.DeleteTaskEvent;
import com.omg.ireader.model.bean.CollBookBean;
import com.omg.ireader.model.local.BookRepository;
import com.omg.ireader.ui.activity.MoreSettingActivity;
import com.omg.ireader.ui.activity.NativeFileSystemActivity;
import com.omg.ireader.ui.activity.ReadActivity;
import com.omg.ireader.ui.activity.SearchWebActivity;
import com.omg.ireader.ui.adapter.BookCaseAdapter;
import com.omg.ireader.ui.base.BaseFragment;
import com.omg.ireader.utils.ToastUtils;
import com.omg.ireader.widget.bookcase.BookCaseDragGridView;
import com.omg.ireader.widget.view.menu.AllAngleExpandableButton;
import com.omg.ireader.widget.view.menu.ButtonData;
import com.omg.ireader.widget.view.menu.ButtonEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yhl on 2017/10/10.
 */

public class BookCaseFragment extends BaseFragment {


    @BindView(R.id.bookcase_grid_content)
    BookCaseDragGridView mContainerView;
    @BindView(R.id.bookcase_btn)
    FloatingActionButton mFloatBtn;
    @BindView(R.id.button_expandable)
    AllAngleExpandableButton mAllAngleExpandableButton;

    @Override
    protected int getContentId() {
        return R.layout.fragment_bookcase;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }


    private BookCaseAdapter adapter;
    private List<CollBookBean> bookLists;

    private void setUpAdapter() {
        bookLists = BookRepository.getInstance().getCollBooks();
        adapter = new BookCaseAdapter(getContext(), bookLists);
        mContainerView.setAdapter(adapter);
        mContainerView.setOnItemClickListener(mOnItemClickListener);
        mFloatBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), NativeFileSystemActivity.class);
            getContext().startActivity(intent);
        });
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.book_case_plus, R.drawable.book_case_search, R.drawable.book_case_copy, R.drawable.book_case_settings};
        int[] color = {R.color.light_blue, R.color.red, R.color.green, R.color.yellow};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(getActivity(), drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(getActivity(), drawable[i], 0);
            }
            buttonData.setBackgroundColorId(getActivity(), color[i]);
            buttonDatas.add(buttonData);
        }
        mAllAngleExpandableButton.setButtonDatas(buttonDatas);
        mAllAngleExpandableButton.setButtonEventListener(mButtonEventListener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        adapter.nitifyDataRefresh();
        super.onResume();
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            //如果是本地文件，首先判断这个文件是否存在
            CollBookBean collBook = adapter.getItem(position);
            if (collBook.isLocal()) {
                //id表示本地文件的路径
                String path = collBook.get_id();
                File file = new File(path);
                //判断这个本地文件是否存在
                if (file.exists()) {
                    ReadActivity.startActivity(getContext(),
                            adapter.getItem(position), true);
                    adapter.setItemToFirst(position);

                } else {
                    //提示(从目录中移除这个文件)
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.nb_common_tip))
                            .setMessage("文件不存在,是否删除")
                            .setPositiveButton(getResources().getString(R.string.nb_common_sure),
                                    (dialog, which) -> deleteBook(collBook, position))
                            .setNegativeButton(getResources().getString(R.string.nb_common_cancel), null)
                            .show();
                }
            } else {
                ReadActivity.startActivity(getContext(),
                        adapter.getItem(position), true);
            }
        }
    };

    /**
     * 默认删除本地文件
     *
     * @param collBook
     */
    private void deleteBook(CollBookBean collBook, int position) {
        if (collBook.isLocal()) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_delete, null);
            CheckBox cb = (CheckBox) view.findViewById(R.id.delete_cb_select);
            new AlertDialog.Builder(getContext())
                    .setTitle("删除文件")
                    .setView(view)
                    .setPositiveButton(getResources().getString(R.string.nb_common_sure), (dialog, which) -> {
                        boolean isSelected = cb.isSelected();
                        if (isSelected) {
                            ProgressDialog progressDialog = new ProgressDialog(getContext());
                            progressDialog.setMessage("正在删除中");
                            progressDialog.show();
                            //删除
                            File file = new File(collBook.get_id());
                            if (file.exists()) file.delete();
                            BookRepository.getInstance().deleteCollBook(collBook);
                            BookRepository.getInstance().deleteBookRecord(collBook.get_id());
                            //从Adapter中删除
                            adapter.removeItem(position);
                            progressDialog.dismiss();
                        } else {
                            BookRepository.getInstance().deleteCollBook(collBook);
                            BookRepository.getInstance().deleteBookRecord(collBook.get_id());
                            //从Adapter中删除
                            adapter.removeItem(position);
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.nb_common_cancel), null)
                    .show();
        } else {
            RxBus.getInstance().post(new DeleteTaskEvent(collBook));
        }
    }

    @Override
    public void onStop() {
        BookCaseDragGridView.setIsShowDeleteButton(false);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        BookCaseDragGridView.setIsShowDeleteButton(false);
        super.onDestroy();
    }

    public boolean backPress() {
        boolean isShow = BookCaseDragGridView.getShowDeleteButton();
        if (isShow && adapter != null)
            BookCaseDragGridView.setIsShowDeleteButton(false);
        adapter.notifyDataSetChanged();
        return isShow;
    }

    ButtonEventListener mButtonEventListener = new ButtonEventListener() {
        @Override
        public void onButtonClicked(int index) {
                switch (index){
                    case 1:
                        Intent searchWebIntent = new Intent(getContext(), SearchWebActivity.class);
                        getContext().startActivity(searchWebIntent);

                        break;
                    case 2:
                        Intent intent = new Intent(getContext(), NativeFileSystemActivity.class);
                        getContext().startActivity(intent);
                        break;
                    case 3:
                        Intent moreSettingIntent = new Intent(getContext(), MoreSettingActivity.class);
                        getContext().startActivity(moreSettingIntent);
                        break;
                }
        }

        @Override
        public void onExpand() {

        }

        @Override
        public void onCollapse() {

        }
    };

}
