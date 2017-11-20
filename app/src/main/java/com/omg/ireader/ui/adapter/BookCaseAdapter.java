package com.omg.ireader.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.omg.ireader.R;
import com.omg.ireader.RxBus;
import com.omg.ireader.event.DeleteTaskEvent;
import com.omg.ireader.model.bean.CollBookBean;
import com.omg.ireader.model.local.BookRepository;
import com.omg.ireader.widget.bookcase.BookCaseDragGridListener;
import com.omg.ireader.widget.bookcase.BookCaseDragGridView;

import java.io.File;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/17.
 */
public class BookCaseAdapter extends BaseAdapter implements BookCaseDragGridListener {
    private List<CollBookBean> bilist;
    private static LayoutInflater inflater = null;
    private int mHidePosition = -1;
    private Context context;
    public BookCaseAdapter(Context context, List<CollBookBean> bilist) {
        this.context=context;
        this.bilist = bilist;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (bilist.size() < 10) {
            return 10;
        } else {
            return bilist.size();
        }
    }

    @Override
    public CollBookBean getItem(int position) {
        return bilist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup arg2) {
        final ViewHolder viewHolder;
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.bookcase_item, null);
            viewHolder = new ViewHolder(contentView);
            contentView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) contentView.getTag();
        }

        if (bilist.size() > position) {
            if (position == mHidePosition) {
                contentView.setVisibility(View.INVISIBLE);
            } else {
                contentView.setVisibility(View.VISIBLE);
            }
            if (BookCaseDragGridView.getShowDeleteButton()) {
                viewHolder.deleteItem_IB.setVisibility(View.VISIBLE);
            } else {
                viewHolder.deleteItem_IB.setVisibility(View.INVISIBLE);
            }
            viewHolder.name.setVisibility(View.VISIBLE);
            String fileName = bilist.get(position).getTitle();
            viewHolder.name.setText(fileName);
        } else {
            contentView.setVisibility(View.INVISIBLE);
        }
        return contentView;
    }

    static class ViewHolder {
        @BindView(R.id.ib_close)
        ImageButton deleteItem_IB;
        @BindView(R.id.tv_name)
        TextView name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Drag移动时item交换数据,并在数据库中更新交换后的位置数据
     *
     * @param oldPosition
     * @param newPosition
     */
    @Override
    public void reorderItems(int oldPosition, int newPosition) {
        List<CollBookBean> beanList = BookRepository.getInstance().getCollBooks();
        // CollBookBean temp = bilist.get(oldPosition);
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(beanList, i, i + 1);
            }
        } else if (oldPosition > newPosition) {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(beanList, i, i - 1);
            }
        }
          // bilist.set(newPosition, temp);
          BookRepository.getInstance().saveCollBooksWithAsync(beanList);
    }


    /**
     * 隐藏item
     *
     * @param hidePosition
     */
    @Override
    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
        notifyDataSetChanged();
    }

    /**
     * 删除书本
     *
     * @param deletePosition
     */
    @Override
    public void removeItem(int deletePosition) {
        List<CollBookBean> beanList = BookRepository.getInstance().getCollBooks();
        CollBookBean collBookBean = beanList.get(deletePosition);
        showDeleDialog(collBookBean,deletePosition);
        notifyDataSetChanged();
    }



    public void setCollBookBean(List<CollBookBean> CollBookBeans) {
        this.bilist = CollBookBeans;
        notifyDataSetChanged();
    }

    /**
     * Book打开后位置移动到第一位
     *
     * @param openPosition
     */
    @Override
    public void setItemToFirst(int openPosition) {
        if (openPosition != 0) {
            for (int i = openPosition; i > 0; i--) {
                Collections.swap(bilist, i, i - 1);
            }
            BookRepository.getInstance().saveCollBooksWithAsync(bilist);
        }
        notifyDataSetChanged();
    }

    @Override
    public void nitifyDataRefresh() {
        bilist.clear();
        bilist.addAll(BookRepository.getInstance().getCollBooks());
        notifyDataSetChanged();
    }
    private void showDeleDialog(CollBookBean collBook,int deletePosition) {
        if (collBook.isLocal()) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_delete, null);
            CheckBox cb = (CheckBox) view.findViewById(R.id.delete_cb_select);
            new AlertDialog.Builder(context)
                    .setTitle("删除文件")
                    .setView(view)
                    .setPositiveButton(context.getResources().getString(R.string.nb_common_sure), (dialog, which) -> {
                        boolean isChecked = cb.isChecked();
                        Log.i("showDeleDialog","isChecked---->"+isChecked);
                        if (isChecked) {
                            ProgressDialog progressDialog = new ProgressDialog(context);
                            progressDialog.setMessage("正在删除中");
                            progressDialog.show();
                            //删除
                            File file = new File(collBook.get_id());

                            if (file.exists()) file.delete();
                            BookRepository.getInstance().deleteCollBook(collBook);
                            BookRepository.getInstance().deleteBookRecord(collBook.get_id());
                            //从Adapter中删除
                            bilist.remove(deletePosition);
                            notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else {
                            BookRepository.getInstance().deleteCollBook(collBook);
                            BookRepository.getInstance().deleteBookRecord(collBook.get_id());
                            //从Adapter中删除
                            bilist.remove(deletePosition);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(context.getResources().getString(R.string.nb_common_cancel), null)
                    .show();
        } else {
            RxBus.getInstance().post(new DeleteTaskEvent(collBook));
        }
    }
}
