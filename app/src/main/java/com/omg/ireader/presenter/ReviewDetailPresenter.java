package com.omg.ireader.presenter;

import com.omg.ireader.model.bean.CommentBean;
import com.omg.ireader.model.bean.ReviewDetailBean;
import com.omg.ireader.model.remote.RemoteRepository;
import com.omg.ireader.presenter.contract.ReviewDetailContract;
import com.omg.ireader.ui.base.RxPresenter;
import com.omg.ireader.utils.LogUtils;
import com.omg.ireader.utils.RxUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * . on 17-4-30.
 */

public class ReviewDetailPresenter extends RxPresenter<ReviewDetailContract.View>
        implements ReviewDetailContract.Presenter {

    @Override
    public void refreshReviewDetail(String detailId, int start, int limit) {
        Single<ReviewDetailBean> detailSingle = RemoteRepository
                .getInstance().getReviewDetail(detailId);

        Single<List<CommentBean>> bestCommentsSingle = RemoteRepository
                .getInstance().getBestComments(detailId);

        Single<List<CommentBean>> commentsSingle = RemoteRepository
                .getInstance().getDetailBookComments(detailId, start, limit);

        Disposable detailDispo = RxUtils.toCommentDetail(detailSingle, bestCommentsSingle, commentsSingle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (bean) -> {
                            mView.finishRefresh(bean.getDetail(),
                                    bean.getBestComments(), bean.getComments());
                            mView.complete();
                        },
                        (e) -> {
                            mView.showError();
                            LogUtils.e(e);
                        }
                );
        addDisposable(detailDispo);
    }

    @Override
    public void loadComment(String detailId, int start, int limit) {
        Disposable loadDispo = RemoteRepository.getInstance()
                .getDetailBookComments(detailId, start, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (bean) -> {
                            mView.finishLoad(bean);
                        },
                        (e) -> {
                            mView.showLoadError();
                            LogUtils.e(e);
                        }
                );
        addDisposable(loadDispo);
    }
}
