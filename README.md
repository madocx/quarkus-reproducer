# Reproducer steps

Run project in dev mode. Localstack will be initialized with dynamo table and item. Test /fruits resource to produce error.

If you switch the call method for invoke, the error no longer occurs. Switching from aws-crt http client to netty-nio also eliminates the error.
