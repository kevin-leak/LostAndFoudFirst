package Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import Bean.Goods;

/**
 * Created by Administrator on 2017/12/25.
 */

public abstract class CommonViewHolder<T> extends RecyclerView.ViewHolder {
        public CommonViewHolder(Context context, ViewGroup root, int layoutRes) {
            super(LayoutInflater.from(context).inflate(layoutRes, root, false));
        }

        public Context getContext() {
            return this.itemView.getContext();
        }

        public abstract void bindData(Goods goods);



        public interface ViewHolderCreator<VH extends CommonViewHolder> {
            VH createByViewGroupAndType(ViewGroup var1, int var2,int var3, int var4);
        }

}
