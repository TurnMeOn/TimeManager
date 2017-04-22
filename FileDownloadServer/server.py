import hashlib
import socket
import struct
import threading
import os



def sendFile(filename, sock):
    # filename = filename.decode()
    try:
        filename = filename.split('xml')[0]+'xml'
        path = "test/" + filename
        # path = path.split(".xml")[0] + ".xml"
        print("start sending progress %s.." % (path))
        # print("path=%s" % path)

        f = open(path, 'rb')
        fsize = os.path.getsize(path)
        md5 = hashlib.md5()
        md5.update(f.read())
        f.seek(0)

        header = struct.pack(b'128sIq32s3s', filename.encode(), len(filename),fsize, md5.hexdigest().encode(), 'EOS'.encode())
        print("Find %s %d %s." % (filename, fsize, md5.hexdigest()))
        sock.send(header)
        sendSize = 0
        lastPrint = 0
        print('fname size is ==================%d' % len(filename))
        print('File size is ===================%d' % fsize)
        print("Sending file %s" % (filename), end='')
        while (sendSize < fsize):
            if (fsize - sendSize >= 1024):
                # 如果剩余文件大小大于1024字节
                fileData = f.read(1024)
                sendSize += 1024
            else :
                # 文件剩余部分不足1024字节, 全部读入
                fileData = f.read()
                sendSize = fsize
            sock.send(fileData)
            if ((sendSize - lastPrint) > (fsize / 10)):
                print("%.2f%%" % (lastPrint / fsize * 100))
                lastPrint = sendSize
        print("")
        print("Finish.")
    except:
        pass


def transportation(userSock, userAddress):
    global FILENAME_LENGTH
    # cmdLength = int(userSock.recv(64).decode())
    # 先发送64个字节表示接下来的文件名有多长
    command = userSock.recv(struct.calcsize(b'128s'))
    command, = struct.unpack(b'128s', command) # 解码
    command = command.decode()

    while True:
        print("%s: start send %s" % (userAddress, command))
        sendFile(command, userSock)
        # cmdLength = int(userSock.recv(64).decode())
        command = userSock.recv(struct.calcsize(b'128s'))
        command, = struct.unpack(b'128s', command)  # 解码
        command = command.decode()
        if (command[:4] == 'exit'):
            break
    print("disconnect to %s" % (str(userAddress)))
    userSock.close()



if __name__ == '__main__':
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, -1)
    sock.bind(('127.0.0.1', 9999))
    sock.listen(10)
    print("Start sever, waiting for connect...")
    while True:
        userBundle = sock.accept()
        print("Connected to %s." % (str(userBundle[1])))
        startMission = threading.Thread(target=transportation, args=(userBundle))
        print("Start progress.")
        startMission.start()


