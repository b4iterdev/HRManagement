# Hệ thống Quản lý Nhân sự (HRManagement)

Đây là một ứng dụng desktop đơn giản được xây dựng bằng Java Swing để quản lý thông tin nhân viên trong một công ty.

## Tính năng chính

-   **Quản lý Nhân viên:** Thêm, sửa, xóa, tìm kiếm và xem danh sách nhân viên.
-   **Phân loại Nhân viên:** Hỗ trợ hai loại nhân viên là Nhân viên toàn thời gian (`FullTimeEmployee`) và Nhân viên bán thời gian (`PartTimeEmployee`).
-   **Tính lương:** Tự động tính lương dựa trên loại nhân viên.
-   **Lưu trữ dữ liệu:** Dữ liệu nhân viên được lưu trữ bền vững trong file `employees.dat` để không bị mất sau mỗi phiên làm việc.
-   **Giao diện đồ họa:** Cung cấp giao diện người dùng thân thiện được xây dựng bằng Java Swing.

## Yêu cầu hệ thống

-   **Java Development Kit (JDK)**: Phiên bản 11 trở lên.
-   **Gradle**: Dự án sử dụng Gradle để quản lý và xây dựng. Tuy nhiên, bạn không cần cài đặt Gradle riêng vì dự án đã đi kèm với Gradle Wrapper (`gradlew`).

## Hướng dẫn khởi chạy chương trình

Bạn có thể khởi chạy ứng dụng một cách dễ dàng bằng cách sử dụng Gradle Wrapper được cung cấp sẵn trong dự án.

1.  **Mở Terminal (hoặc Command Prompt/PowerShell trên Windows):**
    Di chuyển đến thư mục gốc của dự án (thư mục chứa file `gradlew`).

2.  **Thực thi lệnh `run` của Gradle:**

    *   Trên **macOS** hoặc **Linux**:
        ```bash
        ./gradlew run
        ```
        *Lưu ý: Nếu bạn gặp lỗi "Permission denied", hãy cấp quyền thực thi cho file `gradlew` bằng lệnh `chmod +x gradlew` rồi chạy lại.*

    *   Trên **Windows**:
        ```cmd
        gradlew.bat run
        ```
        *Hoặc trong PowerShell:*
        ```powershell
        .\gradlew.bat run
        ```

Gradle sẽ tự động biên dịch mã nguồn và khởi chạy ứng dụng. Cửa sổ đăng nhập của chương trình sẽ xuất hiện sau khi quá trình xây dựng hoàn tất.

## Cấu trúc dự án

Dự án được tổ chức theo mô hình 3 lớp (Model-View-Controller):

```
app/src/main/java/hrmanagement/
├── App.java                // Lớp chính để chạy ứng dụng
├── controller/
│   └── EmployeeManager.java  // Lớp xử lý logic nghiệp vụ
├── model/
│   ├── Employee.java         // Lớp cha
│   ├── FullTimeEmployee.java // Lớp con
│   ├── PartTimeEmployee.java // Lớp con
│   └── Payable.java          // Interface tính lương
└── view/
    └── gui/
        ├── EmployeeDialog.java // Dialog thêm/sửa
        ├── LoginDialog.java    // Dialog đăng nhập
        └── MainFrame.java      // Cửa sổ chính
```
