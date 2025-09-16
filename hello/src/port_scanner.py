"""
This module is designed to scan and see which ports are open on a specified host
"""

import socket

# ports = [22, 80, 443]
HOST = "scanme.nmap.org"

def scan_port(host, port):
    """
    This method scans the port number specified by port on the host specified by host
    """

    banner = None

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        sock.settimeout(1.0)

        print(f"Trying port {port}")

        if sock.connect_ex((host, port)) != 0:
            return

        print(f"Port {port} is open")

        try:
            banner = sock.recv(1024)
        except BlockingIOError:
            print("Blocking IO Error")
        except TimeoutError:
            print("Timeout Error")

        if not banner:
            print("No banner received")

            #incase it's an HTTP port
            try:
                sock.sendall(b"GET / HTTP/1.1\r\nHost: scanme.nmap.org\r\n\r\n")
                response = sock.recv(1024)
                print(response.decode(errors="ignore"))
            except BlockingIOError:
                print("Blocking IO Error")
            except TimeoutError:
                print("Timeout Error")

        else:
            print(f"Banner: {banner.decode(errors='ignore')}")


def main():
    """
    Main - cycles through ports in specified range
    """
    # for port in ports:
    # scanPort(host, port)

    # for port in range(20,1025):
    # scanPort(host, port)

    for port in range(20,200):
        scan_port(HOST, port)

if __name__ == "__main__":
    main()
