import socket

# ports = [22, 80, 443]
host = "scanme.nmap.org"

def scanPort(host, port):
    
    banner = None
    
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.settimeout(1.0)
    
        print(f"Trying port {port}")
    
    
        if sock.connect_ex((host, port)) != 0:
            return
    
        print(f"Port {port} is open")
        
        try:
            banner = sock.recv(1024)
        except:
            pass

        if not banner:
            print("No banner received")
        
            #incase it's an HTTP port
            try:
                sock.sendall(b"GET / HTTP/1.1\r\nHost: scanme.nmap.org\r\n\r\n")
                response = sock.recv(1024)
                print(response.decode(errors="ignore"))
            except:
                pass
        
        else:
            print(f"Banner: {banner.decode(errors='ignore')}")
        
    
# for port in ports:
    # scanPort(host, port)

# for port in range(20,1025):
    # scanPort(host, port)

for port in range(20,200):
    scanPort(host, port)