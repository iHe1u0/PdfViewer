package com.imorning.pdf.bean;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.imorning.pdf.R;
import com.imorning.pdf.activity.PdfActivity;
import com.imorning.pdf.activity.SplashActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private final List<RecentList> recentLists;

    /**
     * 构造函数
     */
    public RecentAdapter(List<RecentList> recentLists) {
        this.recentLists = recentLists;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rencent_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.fileNameTextView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            RecentList recentList = recentLists.get(position);
            //   /root/storage/emulated/0/Android/data/cn.wps.moffice_eng/.Cloud/cn/21799544/f/2ef1df1a-6199-425a-86db-01b38e2120db/21天学通c++_第7版.pdf
            File file = new File(recentList.getFilePath());
            if (file.exists()) {
                Intent intent = new Intent(parent.getContext(), PdfActivity.class);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.fromFile(file));
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentList recentList = recentLists.get(position);
        holder.fileNameTextView.setText(recentList.getFileName());
        holder.lastTimeTextView.setText(String.valueOf(recentList.getLastTime()));
        holder.pageTextView.setText(String.valueOf(recentList.getPage()));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return recentLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView fileNameTextView;
        private final AppCompatTextView lastTimeTextView;
        private final AppCompatTextView pageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.recent_list_name);
            lastTimeTextView = itemView.findViewById(R.id.recent_list_last);
            pageTextView = itemView.findViewById(R.id.recent_list_page);
        }
    }
}
