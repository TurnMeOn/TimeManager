import os

os.chdir('app/src/main/java/wesayallright/timemanager/db/dbHelper/')

files = os.walk(os.getcwd())
for root, dirs, file in files:
    f = open(file, 'r')