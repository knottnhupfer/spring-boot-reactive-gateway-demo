# Simple demo to upload file

To upload this file got to project root folder and execute following command:

~~~
  curl --user user:secret -v http://localhost:8080/api/testing/README.md --upload-file README.md
~~~

For testing the gateway call:
~~~
  curl --user user:secret -v http://localhost:8080/api/test/hello
~~~
