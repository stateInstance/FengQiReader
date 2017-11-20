package com.omg.ireader.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.omg.ireader.R;
import com.omg.ireader.RxBus;
import com.omg.ireader.event.AddBookCityEvent;
import com.omg.ireader.event.DownloadMessage;
import com.omg.ireader.model.bean.BookCityBean;
import com.omg.ireader.model.flag.BookCityType;
import com.omg.ireader.model.local.BookCityRepository;
import com.omg.ireader.presenter.BookCityPresenter;
import com.omg.ireader.presenter.contract.BookCityContract;
import com.omg.ireader.ui.activity.SearchWebActivity;
import com.omg.ireader.ui.adapter.BookCityAdapter;
import com.omg.ireader.ui.base.BaseMVPFragment;
import com.omg.ireader.ui.base.adapter.BaseListAdapter;
import com.omg.ireader.utils.ToastUtils;
import com.omg.ireader.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by yhl on 2017/10/11.
 */

public class BookCityFragment extends BaseMVPFragment<BookCityContract.Presenter> {
private static final String TAG="BookCityFragment";

    @BindView(R.id.book_city_rv_content)
    RecyclerView mCityContent;

    @Override
    protected int getContentId() {
        return R.layout.fragment_book_city;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }
    BookCityAdapter mBookCityAdapter;
    private void setUpAdapter() {
        List<BookCityBean>  bookCityOptions = BookCityRepository.getInstance().getBookCityOptions();
        if (bookCityOptions.size()<1){
            for (BookCityType type : BookCityType.values()){
                bookCityOptions.add(new BookCityBean(type.getTitle(),type.getPathUrl(),type.getIconId(),type.getPathUrl()));
            }
            BookCityRepository.getInstance().saveBookCityOptions(bookCityOptions);
        }
        mBookCityAdapter = new BookCityAdapter();
        mCityContent.setHasFixedSize(true);
        mCityContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mCityContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mCityContent.setAdapter(mBookCityAdapter);
        mBookCityAdapter.addItems(bookCityOptions);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mBookCityAdapter.setOnItemClickListener(mOnItemClickListener);
        mBookCityAdapter.setOnItemLongClickListener(mOnItemLongClickListener);

        Disposable addCityDisp = RxBus.getInstance()
                .toObservable(AddBookCityEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            mBookCityAdapter.addItem(0,event.bookCityBean);
                        }
                );
        addDisposable(addCityDisp);
    }

    BaseListAdapter.OnItemClickListener mOnItemClickListener = (view, pos) -> {
        BookCityType type=  BookCityType.values()[pos];
        Intent intent  = new Intent(getContext(),SearchWebActivity.class);;
        intent.putExtra("BookCityType",type.getPathUrl());
        startActivity(intent);
    };
    BaseListAdapter.OnItemLongClickListener mOnItemLongClickListener=new BaseListAdapter.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(View view, int pos) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                            case 0:
                                mPresenter.updateBookCityOptions(pos);
                                mBookCityAdapter.notifyItemMoved(pos,0);
                                break;
                            case 1:
                                //删除  需要对数据库排序
                                List<BookCityBean> bookCityOptions = BookCityRepository.getInstance().getBookCityOptions();
                                BookCityBean deleBookCityBean = bookCityOptions.remove(pos);
                                mPresenter.deleteBookCityOption(deleBookCityBean);
                                mBookCityAdapter.removeItem(deleBookCityBean);
                                break;
                            case 2:
                                ToastUtils.show("分享功能正在开发，敬请稍后！");
                                break;
                        }
                }
            });
            AlertDialog alertDialog = builder.create();
            builder.show();
            alertDialog.getWindow().setLayout(150, 320);
            return true;
        }
    };

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    protected BookCityContract.Presenter bindPresenter() {
        return new BookCityPresenter();
    }
}
