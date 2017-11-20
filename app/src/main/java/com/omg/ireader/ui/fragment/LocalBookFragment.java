package com.omg.ireader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.omg.ireader.R;
import com.omg.ireader.model.local.BookRepository;
import com.omg.ireader.ui.adapter.FileSystemAdapter;
import com.omg.ireader.utils.FileUtils;
import com.omg.ireader.utils.RxUtils;
import com.omg.ireader.widget.RefreshLayout;
import com.omg.ireader.widget.itemdecoration.DividerItemDecoration;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * . on 17-5-27.
 * 本地书籍
 */

public class LocalBookFragment extends BaseFileFragment {
    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    @BindView(R.id.local_book_rv_content)
    RecyclerView mRvContent;
    @Override
    protected int getContentId() {
        return R.layout.fragment_local_book;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }

    private void setUpAdapter(){
        mAdapter = new FileSystemAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //如果是已加载的文件，则点击事件无效。
                    String id = mAdapter.getItem(pos).getAbsolutePath();
                    if (BookRepository.getInstance().getCollBook(id) != null){
                        return;
                    }

                    //点击选中
                    mAdapter.setCheckedItem(pos);

                    //反馈
                    if (mListener != null){
                        mListener.onItemCheckedChange(mAdapter.getItemIsChecked(pos));
                    }
                }
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //显示数据
        Disposable disposable = FileUtils.getSDTxtFile()
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        files -> {
                            if (files.size() == 0){
                                //数据为空
                                mRlRefresh.showEmpty();
                            }
                            else {
                                mAdapter.refreshItems(files);
                                mRlRefresh.showFinish();
                                //反馈
                                if (mListener != null){
                                    mListener.onCategoryChanged();
                                }
                            }
                        }
                );
        addDisposable(disposable);
    }
}
