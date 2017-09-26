package vn.congphuong.ominext.soanvanthpt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.congphuong.ominext.soanvanthpt.R;
import vn.congphuong.ominext.soanvanthpt.data.Lesson;
import vn.congphuong.ominext.soanvanthpt.event.OnClickLessonAdapter;

/**
 * Created by Ominext on 9/22/2017.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    OnClickLessonAdapter mListener;
    ArrayList<Lesson> lessons;


    public LessonAdapter(ArrayList<Lesson> lessons, OnClickLessonAdapter mListener) {
        this.lessons = lessons;
        this.mListener = mListener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.tvSttLesson.setText(String.valueOf(position + 1));
        holder.tvNameLesson.setText(lesson.getName());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void filter(String charText,ArrayList<Lesson>  listSeach) {
        charText = charText.toLowerCase(Locale.getDefault());
        lessons.clear();
        if(charText.length()==0){
            lessons.addAll(listSeach);
        }
        else {
            for(Lesson l : listSeach){
                if(l.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    lessons.add(l);
                }
            }
        }
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_stt_lesson)
        TextView tvSttLesson;
        @BindView(R.id.tv_name_lesson)
        TextView tvNameLesson;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(lessons.get(getAdapterPosition()).getPath(),
                            lessons.get(getAdapterPosition()).getName());
                }
            });

        }
    }
}