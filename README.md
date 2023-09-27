CAR MANAGEMENT
==============

# Giới thiệu 

Khái niệm: Car management là phần mềm quản lý xe ô tô thông qua các thao tác cơ bản hiện thực hoá bằng api được thiết kế bởi Backend developer.

# Công nghệ sử dụng để viết API

- Tiêu Chuẩn API: RESTful API
- Ngôn ngữ sử dụng: Java
- Framework: Spring Boot
- Cơ sở dữ liệu: MySQL
- API testing: Postman

# Đặc tả API

##  API cho phép thêm vào xe ô tô mới, tự tạo id

- Mô tả: Sau khi người dùng gọi API kèm theo các thông tin về xe ô tô trong body data, hệ thống sẽ tiến hành kiểm tra thông tin (Không cho phép trùng biển số với xe đã tồn tại trong hệ thống). Nếu không có lỗi gì xảy ra thì tiến hành thêm vào xe ô tô này, id tự tăng. 

- Giao thức: POST
  
- URL: 
```sh
      http://localhost:777/api/v1/cars/create
````
- Body data sample:
```sh
      {
        "licensePlate": "70K1-42244",
        "ownerName": "Bui Van Hoa",
        "ownerBirthday": "1998-03-04",
        "x": 190,
        "y": 200
      }
````
  - Result:
<img src=https://res.cloudinary.com/dufk6qhfc/image/upload/v1695804842/Screenshot_2023-09-27_at_15.52.10_ee13xy.png width="80%" height="80%" border="1">

