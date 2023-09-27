CAR MANAGEMENT
==============

# Giới thiệu 

Khái niệm: Car management là phần mềm quản lý xe ô tô thông qua các thao tác cơ bản, hiện thực hoá bằng api, được thiết kế bởi Backend developer.

# Công nghệ sử dụng để viết API

- Tiêu chuẩn API: RESTful API
- Ngôn ngữ sử dụng: Java
- Framework: Spring Boot
- Cơ sở dữ liệu: MySQL
- API testing: Postman

# Đặc tả API

##  1. Khi restart server dữ liệu vẫn không bị mất
- Mô tả: Để khi restart server dữ liệu vẫn toàn vẹn, ta phải tiến hành kiểm soát thông qua Hibernate. Lúc này, ta cần cấu hình trong file application.properties, set giá trị cho spring.jpa.hibernate.ddl-auto, trong đó:
          + create: Trước tiên Hibernate sẽ drop các bảng đang có và sau đó tạo ra các bảng mới.
          + update: Mô hình đối tượng được tạo dựa trên việc ánh xạ (thông qua các annotation hoặc XML) được so sánh với dữ liệu hiện có trong schema và sau đó Hibernate sẽ cập nhật lại schema với những thông tin mới. Nó sẽ không bao giờ xóa đi các bảng hay các cột đang có ngay cả khi chúng không còn được ứng dụng của ta yêu cầu nữa.
          + create-drop: Tương tự như create nhưng sau khi tất cả các hoạt động đã được hoàn thành, Hibernate sẽ thực hiện việc drop database. Thường được sử dụng cho unit test.
          + validate: Hibernate chỉ xác thực xem các bảng và các cột có tồn tại hay không, nếu không nó sẽ ném ra một ngoại lệ (exception).
          + none: Giá trị này được dùng để tắt việc tạo DDL.
- Do đó, để thoả mãn yêu cầu dữ liệu không bị mất khi start server, ta có thể cấu hình spring.jpa.hibernate.ddl-auto với giá trị là update.

##  2. API cho phép thêm vào xe ô tô mới, tự tạo id

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

##  3. API cho phép xóa xe ô tô bằng id
- Mô tả: Sau khi người dùng gọi API kèm theo các thông tin về id xe ô tô đính kèm trên path variable, hệ thống sẽ tiến hành kiểm tra thông tin hợp lệ của xe thông qua id. Nếu không có lỗi gì xảy ra thì tiến hành xoá xe ô tô này khỏi hệ thống, nếu không sẽ trả về thông tin lỗi tương ứng. 

- Giao thức: DELETE
  
- URL: 
```sh
      http://localhost:777/api/v1/cars/delete/{id}
````
  - Result:
    
      <img src=https://res.cloudinary.com/dufk6qhfc/image/upload/v1695805739/Screenshot_2023-09-27_at_16.08.37_yscjba.png width="80%" height="80%" border="1">

##  4. API cho phép thay đổi thông tin ô tô bằng id
- Mô tả: Sau khi người dùng gọi API kèm theo các thông tin cần thay đổi về tô tô trong body data và id xe ô tô cần thay đổi trên path variable. Hệ thống sẽ tiến hành kiểm tra thông tin hợp lệ của xe thông qua id. Sau đó tiến hành kiểm tra thông tin các trường cần thay đổi và cập nhật theo thông tin người dùng đã nhập (Biển số xe không được trùng với biển số đã tồn tại trong hệ thống). Nếu không có lỗi gì xảy ra thì tiến hành xoá xe ô tô này khỏi hệ thống, nếu không sẽ trả về thông tin lỗi tương ứng. 

- Giao thức: PUT
  
- URL: 
```sh
      http://localhost:777/api/v1/cars/update/{id}
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
    
      <img src=https://res.cloudinary.com/dufk6qhfc/image/upload/v1695806092/Screenshot_2023-09-27_at_16.14.04_lsmgsn.png width="80%" height="80%" border="1">


##  5. API cho phép nhập vào tọa độ x:y, và số lượng xe cần tìm n. Tìm n xe gần tọa độ x:y nhất và trả về ra danh sách xe ô tô (id, licensePlate, distance) với thứ tự từ gần đến xa
- Mô tả: Sau khi người dùng gọi API kèm theo các thông tin về toạ độ x:y và số lượng xe cần tìm n trong body data. Hệ thống sẽ tiến hành kiểm tra và trả về danh sách ô tô theo yêu cầu theo các bước sau:
          + Lấy ra tất cả các ô tô hiện có trong hệ thống.
          + Tính khoảng cách giữa điểm nhập vào với các điểm trên hệ thống thông qua công thức Euclid.
          + Tạo mảng mới và lưu danh sách các xe ô tô kèm khoảng cách tương ứng từ ô tô đó đến điểm nhập.
          + Sắp xếp lại mảng mới này theo thứ tự khoảng cách tăng dần.
          + Xuất ra danh sách n xe theo yêu cầu của người dùng.

- Validate: Số lượng xe cần tìm không được vượt quá số lượng xe tồn tại trong hệ thống.
  
- Giao thức: GET
  
- URL: 
```sh
      http://localhost:777/api/v1/cars/get-car-by-coordinate
````
- Body data sample: 
```sh
      {
        "x": 212,
        "y": 286,
        "n": 2
      }
````
  - Result:
    
      <img src=https://res.cloudinary.com/dufk6qhfc/image/upload/v1695806299/Screenshot_2023-09-27_at_16.18.08_whhzye.png width="80%" height="80%" border="1">




