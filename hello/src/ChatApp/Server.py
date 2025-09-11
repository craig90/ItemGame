import socket
import threading
import time
import struct

clients = []
lock = threading.Lock()
stopEvent = threading.Event()

#
## Set up the server
def initServer():
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    host = '0.0.0.0'  # Or '0.0.0.0' to listen on all available interfaces
    port = 12345      # higher than 1024
    serverSocket.bind((host, port))

    # number of new connections that can be stored/queued in backlog - not really important for a program of this size
    serverSocket.listen(5)

    print (f"Server listening on {host}:{port}")

    return serverSocket

#
## Chat with the client
def talkToClient(connection, address):
    
    try:
        
        connection.settimeout(1)
        
        while not stopEvent.is_set():
            
            try:
                data = connection.recv(1024)
                if not data:
                    break
                print(F"Received from {address}: {data.decode()}")
                connection.sendall(b"Server recieved your message!")
            except socket.timeout:
                continue
        
        print("Server connection closed from outside the thread")
            
    except Exception as e:
        print(f"Error with client {address}: {e}")
    finally:

        ## remove connection from the list.... remember it's a shared resource
        lock.acquire()
        clients.remove(connection)
        lock.release()
        
        connection.close()
        print(f"Connection with {address} closed")



def broadcastMessage():

    while True:

        # make sure we are the only one accessing the client list
        lock.acquire()

        numClients = len(clients)
        message = f"There are currently {numClients} clients connected to the server"
        encodedMessage = message.encode('utf-8')

        # print(f"Number of clients {numClients}"
        # packedNum = struct.pack('!i', numClients)

        # message = encodedMessagePartOne + packedNum + encodedMessagePartTwo

        for connection in clients:
            connection.sendall(encodedMessage)
        
        print(f"There are currently {len(clients)} clients connected to the server")
        
        lock.release()

        time.sleep(10)



## start here


serverSocket = initServer()

broadcastThread = threading.Thread(target=broadcastMessage, args=(clients))
broadcastThread.daemon = True
broadcastThread.start()

try:
    while True:
        connection, address = serverSocket.accept()
        print(f"Accepted connection from: {address}")
    
        ## add the new client to the list... however throw a mutex around it since it will shared by different threads
        lock.acquire()
        clients.append(connection)
        lock.release()

        thread = threading.Thread(target=talkToClient, args=(connection,address))
        thread.start()
        
except KeyboardInterrupt:
    print("\nThe server is shutting down")
    stopEvent.set()
finally:
    serverSocket.close()


