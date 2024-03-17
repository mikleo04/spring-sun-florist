# Sun Florist App - Java Spring Boot

a mini project with a flower shop application theme created using Java 17 and Spring Boot 3.2.3. The purpose of this application is to make it easier for flower shops to manage products, customers, and sales transactions.

This application was built using:
#### Spring IoC
```java
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    
}
```

#### Java Stream
```java
Long totalPrice = detailResponses.stream()
                .mapToLong(value -> (value.getQuantity() * value.getFlowerPrice()))
                .reduce(0, Long::sum);
```

```java
List<TransactionDetailResponse> detailResponses = transaction.getTransactionDetails().stream()
    .map(transactionDetail ->
        TransactionDetailResponse.builder()
            .id(transactionDetail.getId())
            .flowerPrice(transactionDetail.getFlowerPrice())
            .quantity(transactionDetail.getQuantity())
            .flowerId(transactionDetail.getFlower().getId())
            .build()
    ).toList();
```

#### Native SQL Query
```java
@Modifying
@Query(
  nativeQuery = true,
  value = """
          INSERT INTO m_customer (id, name, birth_date, address, mobile_phone_no, status)
          VALUES (:id, :name, :birthDate, :address, :mobilePhone, true)
          """
)
void create(
  @Param("id") String id,
  @Param("name") String name,
  @Param("birthDate") Date birthDate,
  @Param("address") String address,
  @Param("mobilePhone") String mobilePhone
);
```

## Feature

### Customer
```java
public class Customer {
    private String id;
    private String name;
    private String mobilePhoneNo;
    private String address;
    private Date birthDate;
    private Boolean status;
}
```

### Flower
```java
public class Flower {
    private String id;
    private String name;
    private Long price;
    private Integer stock;
    private Image image;
}
```

### Image
```java
public class Image {
    private String id;
    private String name;
    private String path;
    private Long size;
    private String contentType;
}
```

### Transaction
```java
public class Transaction {
    private String id;
    private Customer customer;
    private List<TransactionDetail> transactionDetails;
    private Date transDate;
}
```

### Transaction Detail
```java
public class TransactionDetail {
    private String id;
    private Transaction transaction;
    private Flower flower;
    private Long flowerPrice;
    private Integer quantity;
}

```

## API Documentation

### Customer
1. Create Customer 

    request :
   - Endpoint : ```/api/customers```
   - Method : POST
   - Body :
     ```json
          {
              "name": "name_d61e3d0414da",
              "mobilePhoneNo": "mobilePhoneNo_04682b2fad11",
              "address": "address_3dd223486389",
              "birthDate": "2024-03-17",
              "status": true
          }
        ```
   - Response:
      ```json
          {
              "statusCode": 201,
              "message": "Successfully save data",
              "data": {
                  "id": "79f5ce59-9874-44e3-8c3d-74c2f36c474b",
                  "name": "name_d61e3d0414da",
                  "mobilePhoneNo": "mobilePhoneNo_04682b2fad11",
                  "address": "address_3dd223486389",
                  "birthDate": "2024-03-17",
                  "status": true
              }
          }
      ```
     
2. Get Customer By Id
   
    request :
    - Endpoint : ```/api/customers/{id}```
    - Method : GET
    - Response:
       ```json
           {
               "statusCode": 200,
               "message": "Successfully fetch data",
               "data": {
                   "id": "79f5ce59-9874-44e3-8c3d-74c2f36c474b",
                   "name": "name_d61e3d0414da",
                   "mobilePhoneNo": "mobilePhoneNo_04682b2fad11",
                   "address": "address_3dd223486389",
                   "birthDate": "2024-03-17",
                   "status": true
               }
           }
       ```
      
3. Get All Customer
   
    request :
    - Endpoint : ```/api/customers```
    - Method : GET
    - Response:
       ```json
           {
               "statusCode": 200,
               "message": "Successfully fetch data",
               "data": [
                  {
                      "id": "79f5ce59-9874-44e3-8c3d-74c2f36c474b",
                      "name": "name_d61e3d0414da",
                      "mobilePhoneNo": "mobilePhoneNo_04682b2fad11",
                      "address": "address_3dd223486389",
                      "birthDate": "2024-03-17",
                      "status": true
                  },
                  {
                      "id": "79f5ce59-9874-44e3-8c3d-74c2f36c474b",
                      "name": "name_d61e3d0414da",
                      "mobilePhoneNo": "mobilePhoneNo_04682b2fad11",
                      "address": "address_3dd223486389",
                      "birthDate": "2024-03-17",
                      "status": true
                  }
               ],
               "paging": {
                      "totalPage": 1,
                      "totalElement": 2,
                      "page": 1,
                      "size": 10,
                      "hasNext": false,
                      "hasPrevious": false
               }
         }
       ```

4. Update Customer

    request :
    - Endpoint : ```/api/customers/{id}```
    - Method : PUT
    - Body :
      ```json
           {
               "name": "name_d61e3d0414da",
               "mobilePhoneNo": "mobilePhoneNo_04682b2fad11",
               "address": "address_3dd223486389",
               "birthDate": "2024-03-17",
               "status": true
           }
         ```
    - Response:
       ```json
           {
               "statusCode": 200,
               "message": "Successfully update data",
               "data": {
                   "id": "79f5ce59-9874-44e3-8c3d-74c2f36c474b",
                   "name": "name_d61e3d0414da",
                   "mobilePhoneNo": "mobilePhoneNo_04682b2fad11",
                   "address": "address_3dd223486389",
                   "birthDate": "2024-03-17",
                   "status": true
               }
           }
       ```
      
5. Delete Customer
   
    request :
    - Endpoint : ```/api/customers/{id}```
    - Method : DELETE
    - Response:
       ```json
           {
               "statusCode": 200,
               "message": "Successfully delete data",
               "data": null
           }
       ```

### Flower
1. Create Flower

   request :
    - Endpoint : ```/api/flowers```
    - Method : POST
    - Header :
        - Content-type: multipart/form-data
      
    - Form Data
        ```
        flower : {
                 "name": "bunga mawar putih",
                 "price": 150000,
                 "stock": 20
                }
        image  : image
      ```
    - Response:
       ```json
        {
          "statusCode": 201,
          "message": "Successfully save data",
          "data": {
              "id": "a0f6885d-a686-4bb2-af25-9c69f16abbd6",
              "name": "bunga mawar putih",
              "price": 150000,
              "stock": 20,
              "image": {
                 "name": "1710638222841_mawar_putih.jpg",
                 "url": "/api/flowers/images/4cdf83e7-2a08-4379-84ff-adb0a7f40249"
              }
           }
        }
       ```
2. Get Flower By Id

   request :
    - Endpoint : ```/api/flowers/{id}```
    - Method : GET
    - Response:
       ```json
           {
          "statusCode": 200,
          "message": "Successfully fetch data",
          "data": {
              "id": "a0f6885d-a686-4bb2-af25-9c69f16abbd6",
              "name": "bunga mawar putih",
              "price": 150000,
              "stock": 20,
              "image": {
                 "name": "1710638222841_mawar_putih.jpg",
                 "url": "/api/flowers/images/4cdf83e7-2a08-4379-84ff-adb0a7f40249"
              }
           }
        }
       ```

3. Get All Flowers

   request :
    - Endpoint : ```/api/flowers```
    - Method : GET
    - Response:
       ```json
           {
               "statusCode": 200,
               "message": "Successfully fetch data",
               "data": [
                  {
                    "id": "a0f6885d-a686-4bb2-af25-9c69f16abbd6",
                    "name": "bunga mawar putih",
                    "price": 150000,
                    "stock": 20,
                    "image": {
                        "name": "1710638222841_mawar_putih.jpg",
                        "url": "/api/flowers/images/4cdf83e7-2a08-4379-84ff-adb0a7f40249"
                      }
                  },
                  {
                    "id": "a0f6885d-a686-4bb2-af25-9c69f16abbd6",
                    "name": "bunga mawar putih",
                    "price": 150000,
                    "stock": 20,
                    "image": {
                        "name": "1710638222841_mawar_putih.jpg",
                        "url": "/api/flowers/images/4cdf83e7-2a08-4379-84ff-adb0a7f40249"
                      }
                  }
               ],
               "paging": {
                      "totalPage": 1,
                      "totalElement": 2,
                      "page": 1,
                      "size": 10,
                      "hasNext": false,
                      "hasPrevious": false
               }
         }
       ```

4. Update Flower

   request :
    - Endpoint : ```/api/flowers/{id}```
    - Method : PUT
   - Header :
      - Content-type: multipart/form-data

   - Form Data
       ```
       flower : {
                "name": "bunga mawar putih",
                "price": 150000,
                "stock": 20
               }
       image  : image
     ```
   - Response:
      ```json
       {
         "statusCode": 200,
         "message": "Successfully update data",
         "data": {
             "id": "a0f6885d-a686-4bb2-af25-9c69f16abbd6",
             "name": "bunga mawar putih",
             "price": 150000,
             "stock": 20,
             "image": {
                "name": "1710638222841_mawar_putih.jpg",
                "url": "/api/flowers/images/4cdf83e7-2a08-4379-84ff-adb0a7f40249"
             }
          }
       }
      ```

5. Delete Flower

   request :
    - Endpoint : ```/api/flowers/{id}```
    - Method : DELETE
    - Response:
       ```json
           {
               "statusCode": 200,
               "message": "Successfully delete data",
               "data": null
           }
       ```

### Image
1. Download or Get Image

   request :
   - Endpoint : ```/api/flowers/images/{id}```
   - Method : GET
   - Header :
      - "content-disposition"
   - Response: ```Response Image```


### Transaction
1. Create Transaction

   request :
   - Endpoint : ```/api/transactions```
   - Method : POST
   - Body :
     ```json
            {
               "customerId": "434a7af5-c2c3-4bce-9835-5de520e1cb16",
               "transactionDetails": [
                   {
                     "flowerId": "416ded42-adce-4ef3-bcc5-a06984f5d36f",
                     "quantity": 1
                   },
                   {
                     "flowerId": "416ded42-adce-4ef3-bcc5-a06984f5d36f",
                     "quantity": 2
                   }
               ]
            }
        ```
   - Response:
      ```json
          {
             "statusCode": 201,
             "message": "Successfully save data",
             "data": {
                 "id": "e6e064ac-21d3-493a-9a7e-d6baa47058c4",
                 "customerId": "434a7af5-c2c3-4bce-9835-5de520e1cb16",
                 "transDate": "2024-03-17 14:25:44",
                 "totalPrice": 600000,
                 "detailTransaction": [
                      {
                         "id": "fbc528a3-cd21-4116-9b05-e3dd530df9e1",
                         "flowerId": "416ded42-adce-4ef3-bcc5-a06984f5d36f",
                         "flowerPrice": 200000,
                         "quantity": 1
                      },
                      {
                         "id": "fbc528a3-cd21-4116-9b05-e3dd530df9e1",
                         "flowerId": "416ded42-adce-4ef3-bcc5-a06984f5d36f",
                         "flowerPrice": 200000,
                         "quantity": 2
                      }
                  ]
              }
          }
      ```

2. Get Transaction By ID

   request :
   - Endpoint : ```/api/transactions/{id}```
   - Method : GET
   - Response:
      ```json
          {
             "statusCode": 200,
             "message": "Successfully fetch data",
             "data": {
                 "id": "e6e064ac-21d3-493a-9a7e-d6baa47058c4",
                 "customerId": "434a7af5-c2c3-4bce-9835-5de520e1cb16",
                 "transDate": "2024-03-17 14:25:44",
                 "totalPrice": 600000,
                 "detailTransaction": [
                      {
                         "id": "fbc528a3-cd21-4116-9b05-e3dd530df9e1",
                         "flowerId": "416ded42-adce-4ef3-bcc5-a06984f5d36f",
                         "flowerPrice": 200000,
                         "quantity": 1
                      },
                      {
                         "id": "fbc528a3-cd21-4116-9b05-e3dd530df9e1",
                         "flowerId": "416ded42-adce-4ef3-bcc5-a06984f5d36f",
                         "flowerPrice": 200000,
                         "quantity": 2
                      }
                  ]
              }
          }
      ```
     
3. Get All Transactions

   request :
   - Endpoint : ```/api/transactions```
   - Method : GET
   - Response:
      ```json
            {
               "statusCode": 200,
               "message": "Successfully fetch data",
               "data": [
                    {
                       "id": "fb4db356-216d-400e-9dd5-02185d1488ee",
                       "customerId": "434a7af5-c2c3-4bce-9835-5de520e1cb16",
                       "transDate": "2024-03-13 08:12:28",
                       "totalPrice": 800000,
                       "detailTransaction": [
                               {
                                   "id": "c505771d-cdfd-46f5-a695-20d50ba22a84",
                                   "flowerId": "416ded42-adce-4ef3-bcc5-a06984f5d36f",
                                   "flowerPrice": 200000,
                                   "quantity": 4
                               }
                       ]
                    },
                    {
                       "id": "7d58ebbf-decf-4e55-99fe-421b7e252b51",
                       "customerId": "434a7af5-c2c3-4bce-9835-5de520e1cb16",
                       "transDate": "2024-03-17 07:52:38",
                       "totalPrice": 800000,
                       "detailTransaction": [
                               {
                                   "id": "f9a13395-d8a9-4a73-b167-598c8fa4c6e3",
                                   "flowerId": "416ded42-adce-4ef3-bcc5-a06984f5d36f",
                                   "flowerPrice": 200000,
                                   "quantity": 4
                               }
                       ]
                    }
               ],
               "paging": {
                       "totalPage": 1,
                       "totalElement": 2,
                       "page": 1,
                       "size": 10,
                       "hasNext": false,
                       "hasPrevious": false
               }
            }
      ```