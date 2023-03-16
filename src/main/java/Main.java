import dao.StudentDAO;
import model.Student;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static StudentDAO studentDAO = new StudentDAO();

    private static void mainMenu() {
        System.out.println("--------QUAN LY THONG TIN SINH VIEN--------");
        System.out.println("1. Danh sách sinh viên");
        System.out.println("2. Nhập sinh viên mới");
        System.out.println("3. Xóa sinh viên theo mã");
        System.out.println("4. Cập nhật thông tin sinh viên");
        System.out.println("5. Tìm một sinh viên theo họ tên hoặc theo mã gần đúng");
        System.out.println("6. Sắp xếp sinh viên theo Gpa tăng");
        System.out.println("7. Sinh viên nữ ở Hà Nội GPA trên 2.5");
        System.out.println("8. Sắp xếp sinh viên theo họ tên");
        System.out.println("9. Thoát");
    }

    private static void option1() {
        List<Student> studentList = studentDAO.getAll();

        String leftAlignFormat = "| %-11s | %-15s | %-9d | %9s  | %n";
        System.out.format("+-------------+-----------------+-----------+------------+%n");
        System.out.format("|Mã Sinh viên |     Họ tên      | Giới tính | Địa chỉ    |%n");
        System.out.format("+-------------+-----------------+-----------+------------+%n");
        for (int i = 0; i < studentList.size(); i++) {
            System.out.format(leftAlignFormat, studentList.get(i).getId(), studentList.get(i).getFull_name(), studentList.get(i).getGender(), studentList.get(i).getAddress());
        }
        System.out.format("+-------------+-----------------+-----------+------------+%n");
    }
    private static void option2(Scanner in){
        Student s = new Student();
        System.out.print("\tNhập mã sinh viên: ");
        s.setId(in.nextLine());
        System.out.print("\tNhập tên sinh viên: ");
        s.setFull_name(in.nextLine());
        System.out.print("\tNhập ngày sinh: ");
        s.setDateOfBirth(in.nextLine());
        System.out.print("\tNhập địa chỉ: ");
        s.setAddress(in.nextLine());
        System.out.print("\tNhập sdt: ");
        s.setPhone(in.nextLine());
        System.out.print("\tNhập email: ");
        s.setEmail(in.nextLine());
        System.out.print("\tNhập Gpa: ");
        s.setGpa(in.nextLine());
        System.out.print("\tNhập giới tính: ");
        s.setGender(in.nextInt());
        System.out.print("\tLưu thành công!!! ");
        studentDAO.insert(s);

    }
    private static void option3(Scanner in) {
        Student s = new Student();
        System.out.print("\tNhập mã sinh viên: ");
        String idd = in.nextLine();
        s.setId(idd);
        studentDAO.delete(idd);
        System.out.print("\tXoa Thanh cong ");
    }
    private static void option4(Scanner in) {
        System.out.print("\t Nhập mã sinh viên cần update: ");
        String idd = in.nextLine();
        Student tmp = studentDAO.getById(idd);
        if(tmp == null){
            System.out.println("sinh viên không tồn tại");
            return;
        }
        Student s = new Student();
        System.out.print("\tNhập tên sinh viên: ");
        s.setFull_name(in.nextLine());
        System.out.print("\tNhập ngày sinh: ");
        s.setDateOfBirth(in.nextLine());
        System.out.print("\tNhập địa chỉ: ");
        s.setAddress(in.nextLine());
        System.out.print("\tNhập sdt: ");
        s.setPhone(in.nextLine());
        System.out.print("\tNhập email: ");
        s.setEmail(in.nextLine());
        System.out.print("\tNhập Gpa: ");
        s.setGpa(in.nextLine());
        System.out.print("\tNhập giới tính: ");
        s.setGender(in.nextInt());

        studentDAO.update(s, idd);
        System.out.println("Update thành công");
    }
    private static void option5(Scanner in) {
            System.out.print("\tNhập mã sinh viên cần tìm: ");
            String idd = in.nextLine();
            Student s1 = studentDAO.getById(idd);
            System.out.println(s1);

    }
    private static void option6() {
        System.out.print("\tSắp xếp theo GPA tăng dần ");

        List<Student> studentList = studentDAO.getAll();

        Collections.sort(studentList);
        for (Student s: studentList) {
            System.out.println(s);

        }

    }
    private static void option7() {
        System.out.print("\tSinh vien nu ở HN GPA>2.5 \n");
        List<Student> studentList = studentDAO.getAll();


        studentList.stream().filter(students -> students.getGender() == 0 && students.getAddress().toLowerCase().equalsIgnoreCase("Hà Nội")
                        && Double.parseDouble(students.getGpa()) > 2.5)
                .forEach(System.out::println);

    }
    private static void option8() {
        System.out.print("\tSắp xếp theo tên (stream) \n");
        List<Student> studentList = studentDAO.getAll();

        studentList.stream().sorted((o1, o2) -> {
            return  o1.getFull_name().compareTo(o2.getFull_name());
        }).forEach(System.out::println);

    }
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int option = -1;
        do {
            mainMenu();
            System.out.print("Nhập lựa chọn: ");
            try {
                option = Integer.parseInt(in.nextLine());

            } catch (Exception e) {
                System.out.println("Nhập sai định dạng");
                continue;
            }
            if (option < 1 || option > 9) {
                System.out.println("Lựa chọn không hợp lệ!");
                continue;
            }

            switch (option) {
                case 1:
                    option1();
                    break;
                case 2:
                    option2(in);
                    break;
                case 3:
                    option3(in);
                    break;
                case 4:
                    option4(in);
                    break;
                case 5:
                    option5(in);
                    break;
                case 6:
                    option6();
                    break;
                case 7:
                    option7();
                    break;
                case 8:
                    option8();
                    break;

            }

        }
        while (option != 9);
        in.close();
    }
}
