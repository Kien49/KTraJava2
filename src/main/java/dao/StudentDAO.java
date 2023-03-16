package dao;

import connection.MyConnection;
import model.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> getAll() {
        List<Student> studentList = new ArrayList<>();
        try {
            Connection conn = MyConnection.getConnection();
            String sql = "SELECT * FROM students";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getString("id"));
                s.setFull_name(rs.getString("full_name"));
                s.setGender(rs.getInt("gender"));
                s.setDateOfBirth(rs.getString("date_of_birth"));
                s.setAddress(rs.getString("address"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setGpa(rs.getString("gpa"));
                studentList.add(s);

            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public void insert(Student s) {
        try {
            Connection conn = MyConnection.getConnection();
            String sql = String.format("insert into `students` (`id`,`full_name`,`gender`,`date_of_birth`,`address`, `phone`, `email`, `gpa`) VALUES ('%s','%s','%d','%s', '%s','%s','%s','%s') ",
                    s.getId(), s.getFull_name(), s.getGender(), s.getDateOfBirth(), s.getAddress(), s.getPhone(), s.getEmail(), s.getGpa()
            );
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Student getById(String id) {
        final String sql = "SELECT * FROM `students` WHERE  `id` = '" + id + "'";
        Student s = null;

        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                s = new Student();
                s.setId(rs.getString("id"));
                s.setFull_name(rs.getString("full_name"));
                s.setGender(rs.getInt("gender"));
                s.setDateOfBirth(rs.getString("date_of_birth"));
                s.setAddress(rs.getString("address"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setGpa(rs.getString("gpa"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void delete(String id) {
        Student student = getById(id);
        if (student == null) {
            throw new RuntimeException("Sinh viên không tồn tại!");
        }

        final String sql = "DELETE FROM `students` WHERE  `id` = '" + id + "'";
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Xoá thất bại");
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Student s, String id) {

        Student tmp = getById(id);
        if (tmp == null) {
            System.out.println("Cập nhật thất bại do không có id = " + id);
            return;
        }
        try {
            // Buoc 1
            Connection conn = MyConnection.getConnection();
            // Buoc 2
            String sql = String.format("UPDATE `students` SET `full_name`='%s', `gender`='%d', `date_of_birth`='%s', `address`='%s', `phone` = '%s',`email` = '%s', `gpa` = '%s'  WHERE `id` = '" + id + "'",
                    s.getFull_name(), s.getGender(), s.getDateOfBirth(), s.getAddress(), s.getPhone(), s.getEmail(), s.getGpa()
            );

            System.out.println(sql);

            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Cập nhật thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student getByName(String name){
        try {
            Student s = null;

            Connection conn = MyConnection.getConnection();
            String sql = "Select *from `students` where `full_name` like '%" + name +"%'";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                s = new Student();
                s.setId(rs.getString("id"));
                s.setFull_name(rs.getString("full_name"));
                s.setGender(rs.getInt("gender"));
                s.setDateOfBirth(rs.getString("date_of_birth"));
                s.setAddress(rs.getString("address"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setGpa(rs.getString("gpa"));
            }
            rs.close();
            stmt.close();
            conn.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
