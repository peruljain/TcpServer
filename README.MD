# Problem Statement
Create a tcp server

# Approach
Refer Video : https://www.youtube.com/watch?v=f9gUFy-9uCM&list=PLsdq-3Z1EPT3VjDhjMb5yBsgn0wn2-fjp&index=2

1. Star listening on the port
2. Wait for client to connect
3. Read the request and send the response
   1. Invoke read system call to read request
   2. Invoke write system call to write request
   3. Close the connection
4. Do this over and over again


