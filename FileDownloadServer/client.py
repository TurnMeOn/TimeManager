import socket
import os
import struct
import traceback

FILENAME_LENGTH = struct.calcsize(b'128s')
FILE_INFO_LENGTH = struct.calcsize(b'128sq32s') # 文件名, 文件大小, MD5


if __name__ == '__main__':
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        server.connect(('alphamj.cn', 9999))
        print('connected to server')
        cmdPack = struct.pack(b'128s', 'gp_G1003.xml'.encode())
        server.send(cmdPack)
        recv = server.recv(FILE_INFO_LENGTH)
        fname, fsize, md5 = struct.unpack(b'128sq32s', recv)
        fname = fname.decode()
        fize = int(fsize)
        if fsize == 0:
            print('Server returns error, No such a file %s ' % fname)
        else :
            recvSize = 0
            f = open(fname.split("xml")[0]+"xml", "wb")
            while (recvSize < fsize):
                if (fsize - recvSize > 1024):
                    fileData = server.recv(1024)
                    recvSize += 1024
                else :
                    fileData = server.recv(fsize - recvSize)
                    recvSize = fsize
                f.write(fileData)
            f.close()
            print('File receive success')
    except Exception:
        print('Oops, something goes wrong.')
        traceback.print_exc()
    cmdPack = struct.pack(b'128s', 'exit'.encode())
    server.send(cmdPack)
