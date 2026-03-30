package com.example.studentmanager4;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager4.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment implements StudentAdapter.OnStudentActionListener {

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList = new ArrayList<>();
    private FloatingActionButton fabAdd;
    private EditText etSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAdd = view.findViewById(R.id.fabAdd);
        etSearch = view.findViewById(R.id.etSearch);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadSampleData();

        studentAdapter = new StudentAdapter(studentList, this);
        recyclerView.setAdapter(studentAdapter);

        fabAdd.setOnClickListener(v -> showAddEditDialog(null, -1));

        // Thiết lập tính năng tìm kiếm
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                studentAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void showAddEditDialog(Student student, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit_student, null);
        builder.setView(dialogView);

        EditText etId = dialogView.findViewById(R.id.etStudentId);
        EditText etName = dialogView.findViewById(R.id.etStudentName);
        EditText etClass = dialogView.findViewById(R.id.etStudentClass);
        EditText etPhone = dialogView.findViewById(R.id.etStudentPhone);

        boolean isEdit = (student != null);
        if (isEdit) {
            builder.setTitle("Edit Student");
            etId.setText(student.getId());
            etId.setEnabled(false);
            etName.setText(student.getName());
            etClass.setText(student.getClassName());
            etPhone.setText(student.getPhone());
        } else {
            builder.setTitle("Add New Student");
        }

        builder.setPositiveButton(isEdit ? "Update" : "Add", (dialog, which) -> {
            String id = etId.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String className = etClass.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(getContext(), "Please enter required info", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEdit) {
                student.setName(name);
                student.setClassName(className);
                student.setPhone(phone);
            } else {
                studentList.add(new Student(id, name, className, phone));
            }
            // Cập nhật lại dữ liệu cho Adapter sau khi thêm/sửa
            studentAdapter.updateData(new ArrayList<>(studentList));
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public void onItemClick(Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Student Details");
        builder.setMessage("ID: " + student.getId() + "\n" +
                "Name: " + student.getName() + "\n" +
                "Class: " + student.getClassName() + "\n" +
                "Phone: " + student.getPhone());
        builder.setPositiveButton("Close", null);
        builder.show();
    }

    @Override
    public void onEditClick(Student student, int position) {
        showAddEditDialog(student, position);
    }

    @Override
    public void onDeleteClick(Student student, int position) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete " + student.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    studentList.remove(student); // Xóa theo đối tượng để tránh sai lệch index khi đang filter
                    studentAdapter.updateData(new ArrayList<>(studentList));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void loadSampleData() {
        if (studentList.isEmpty()) {
            studentList.add(new Student("SV001", "Nguyen Van A", "CNTT K43", "0123456789"));
            studentList.add(new Student("SV002", "Tran Thi B", "QTKD K42", "0987654321"));
            studentList.add(new Student("SV003", "Le Van C", "DTVT K44", "0912345678"));
        }
    }
}
