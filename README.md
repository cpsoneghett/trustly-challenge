# Trustly Challenge Api

## 1. Description

This is an API that returns the total number of lines and the total number of bytes of all the files of a given public Github repository, grouped by file extension.

## 2. API Contract
The API contract is defined this way:

Request:
``` json
{
    "repositoryUrl" : "https://github.com/cpsoneghett/test-scraping"
}
```

Response:
``` json
{
    "repositoryName": "test-scraping",
    "owner": "cpsoneghett",
    "totalFiles": 7,
    "extensions": {
        "": [
            {
                "fileName": "Dockerfile",
                "fileSize": "1 Bytes",
                "totalLines": "0",
                "notEmptyLines": "0"
            }
        ],
        "txt": [
            {
                "fileName": "file_inside_folder",
                "fileSize": "50 Bytes",
                "totalLines": "9",
                "notEmptyLines": "7"
            },
            {
                "fileName": "test01",
                "fileSize": "27 Bytes",
                "totalLines": "3",
                "notEmptyLines": "3"
            },
            {
                "fileName": "test02",
                "fileSize": "28 Bytes",
                "totalLines": "5",
                "notEmptyLines": "3"
            },
            {
                "fileName": "test03",
                "fileSize": "28 Bytes",
                "totalLines": "4",
                "notEmptyLines": "3"
            }
        ],
        "md": [
            {
                "fileName": "README",
                "fileSize": "15 Bytes",
                "totalLines": "1",
                "notEmptyLines": "1"
            }
        ],
        "png": [
            {
                "fileName": "eu2",
                "fileSize": "669696 Bytes",
                "totalLines": "0",
                "notEmptyLines": "0"
            }
        ]
    }
}
```

## 3. Features

### 3.1 Web Scraping
I did not used any existing library or API outside the native Java resources to make the web scraping of the GitHub repository page, as the challenge requires.

It was used the code below to extract the page information.
``` java
URL url = new URL( webUrl );
InputStream is = url.openStream();
``` 
Once the page informations were extracted, it verifies each content if it is a file or a folder. For a file, it will access again the href witch references that file and extract the number of lines (if it has) and the size informations. If it is a folder, the method will recursively access the href of this folder and extract each file inside of it, even if it has another folders inside.

### 3.2 Persistence
I opted to persist the scraped data of repository to be easier to retrieve in a second call (even with second level cache).
I used MySQL database with database migration with Flyway.

### 3.3 Error Handling
I created a custom Error Handler class to deal with some exceptions that may occur during the request.

### 3.4 Heroku

This API is deployed on Heroku and can be accessed by the following URL:

https://cpsoneghett-trustly-challenge.herokuapp.com/api/list-repository-files

### 3.5 Swagger

The Swagger page is:

https://cpsoneghett-trustly-challenge.herokuapp.com/swagger-ui.html


### 3.6 Concurrent requests
I just put a ***synchronized*** keyword in the REST controller method. I was searching for a way to use @Synchronized of Spring to deal with concurrent requests without make any transaction to wait, but this was the fastest way I found. Unfortunaly, this makes concurrent requests wait the end of the respective transaction.


