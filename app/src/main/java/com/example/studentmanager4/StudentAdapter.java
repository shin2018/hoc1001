package com.example.studentmanager4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager4.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private List<Student> studentListFull;
    private OnStudentActionListener listener;

    public interface OnStudentActionListener {
        void onItemClick(Student student);
        void onEditClick(Student student, int position);
        void onDeleteClick(Student student, int position);
    }

    public StudentAdapter(List<Student> studentList, OnStudentActionListener listener) {
        this.studentList = studentList;
        this.studentListFull = new ArrayList<>(studentList);
        this.listener = listener;
    }

    public void updateData(List<Student> newList) {
        this.studentList = newList;
        this.studentListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        Context context = holder.itemView.getContext();
        
        holder.tvId.setText(context.getString(R.string.id_label, student.getId()));
        holder.tvName.setText(student.getName());
        holder.tvClass.setText(context.getString(R.string.class_label, student.getClassName()));
        
        if (student.getName() != null && !student.getName().isEmpty()) {
            holder.tvInitial.setText(student.getName().substring(0, 1).toUpperCase());
        } else {
            holder.tvInitial.setText("S");
        }
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(student);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(student, position);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(student, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void filter(String text) {
        studentList = new ArrayList<>();
        if (text.isEmpty()) {
            studentList.addAll(studentListFull);
        } else {
            String filterPattern = text.toLowerCase().trim();
            for (Student item : studentListFull) {
                if (item.getName().toLowerCase().contains(filterPattern) || 
                    item.getId().toLowerCase().contains(filterPattern)) {
                    studentList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvClass, tvInitial;
        ImageButton btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvInitial = itemView.findViewById(R.id.tvInitial);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
