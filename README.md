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
  "data": [
    {
      "extension": "txt",
      "count": 4,
      "lines": 21,
      "bytes": 133
    },
    {
      "extension": "Dockerfile",
      "count": 1,
      "lines": 8,
      "bytes": 171
    },
    {
      "extension": "md",
      "count": 1,
      "lines": 1,
      "bytes": 15
    },
    {
      "extension": "png",
      "count": 1,
      "lines": 0,
      "bytes": 669696
    }
  ]
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

### 3.2 Cache and Persistence
Although the application has mapped entities, there's no data being persisted in this API. The only purpose for the entities is to separate the objects that might be persisted in the future from the contract's DTOs (Data Transfer Objects). 
And for multiple requests, the application implements a cache handling using some annotations, such as @Cacheable (that I'm using in the scrap method), @EnableCaching (to the main application recognize that will be something to cache) and @CacheEvict (to clean the cache).

And, to clean the cache, I chose to parametrize the fixed time rate value in the properties application. Default value: 600000ms (10 minutes).

**IMPORTANT:**
The API will store the response in the cache so that, in subsequent requests, it doesn't take so long due to the scraping process. If, during the period that the cache is not cleared, occurr changes in the repository, the response to the request will still be the response from the last scrap made, that is, the response is the one stored in the cache. Therefore, there is a simple call to clear the cache from the following endpoint:

https://cpsoneghett-trustly-challenge.herokuapp.com/api/cache/clean

### 3.3 Error Handling
I created a custom Error Handler class to deal with some exceptions that may occur during the request.

### 3.4 Heroku

This API is deployed on Heroku and can be accessed by the following URL:

https://cpsoneghett-trustly-challenge.herokuapp.com/api/list-repository-files

### 3.5 Swagger

The Swagger page is:

https://cpsoneghett-trustly-challenge.herokuapp.com/swagger-ui.html


### 3.6 Concurrent requests
I just put a ***synchronized*** keyword in the REST controller method. I was searching for a way to use @Synchronized of Spring to deal with concurrent requests without make any transaction to wait, but this was the fastest way I found. This makes concurrent requests wait the end of the respective transaction.


