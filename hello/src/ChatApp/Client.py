import socket
import threading
import _thread

serverHost = "127.0.0.1"
serverPort = 12345
stopEvent = threading.Event()

#
## sends a user message to the server
def sendToServer(sock):

    while not stopEvent.is_set():
        
        message = input("Enter 1 to exit\nOtherwise, enter the message to send to the server: ").strip()
    
        if (message == "1"):
            stopEvent.set()
            break

        sock.send(message.encode("utf-8"))

#
## listens for messages from the server
def listenToServer(sock):

    sock.settimeout(1)

    while not stopEvent.is_set():
        
        try:
            data = sock.recv(1024).decode("utf-8")
            
            if not data:
                print("Recieve message thread: Connection has been closed by the server")
                break
            
            print(f"Server message: {data}")
        except socket.timeout:
            continue

    # print("The socket has been closed from outside this thread")


def main():

    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientSocket.connect((serverHost, serverPort))
    print(f"Connected to {serverHost}:{serverPort}")

    try:
        thread = threading.Thread(target=listenToServer, args=(clientSocket,))
        thread.start()

        sendToServer(clientSocket)

    except KeyboardInterrupt:
        stopEvent.set()
    finally:
        # we don't want to close the socket if the listening thread is still listening
        thread.join()
        clientSocket.close()

if __name__ == "__main__":
    main()