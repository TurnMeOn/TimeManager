import os
import re
import time


def main(modelsTemplateFile, modelsDir, migrationsDir):
    convert2JaveType = {
        'CharField': 'String',
        'IntegerField': 'Integer',
        'BooleanField': 'Boolean',
        'SmallIntegerField': 'Short',
        'DateField': 'Date',
        'TimeField': 'Date',
        'DateTimeField': 'Date',
        'ForeignKey': 'Object',
        'EmailField': 'String'
    }
    convert2SQLType = {
        'CharField': 'vchar',
        'IntegerField': 'integer',
        'BooleanField': 'bool',
        'SmallIntegerField': 'smallint',
        'DateField': 'date',
        'TimeField': 'time',
        'DateTimeField': 'datetime',
        'ForeignKey': 'integer',
        'EmailField': 'vchar'
    }
    models = open(modelsTemplateFile, 'r')
    modelTemplate = open('modelTemplate.txt', 'r')
    migrationTemplate = open('migrationTemplate.txt', 'r')
    line = models.readline()

    while line != '':
        sp = line.split(' ')
        if sp[0] == 'class':
            tableName = sp[1].split('(')[0]
            comment = "MyModel 自动生成 at: " + time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
            content_model = ''
            content_migration = ''
            declaredPrimerKey = False

            modelFile = open(modelsDir + tableName + '.java', 'w')
            migrationFile = open(migrationsDir +  tableName + '_migration.java', 'w')

            line = models.readline()
            pattern = re.compile('\s+(\S+)\s+=\s+models\.(\w+)\((.+)\)')

            while line != '' and line[0] == ' ':
                if len(line) >= 4 and(line[4:6] == 'def' or line[5] == ' '):
                    line = models.readline()
                    continue

                m = pattern.match(line)
                if m is not None:
                    fieldName = m.group(1)
                    fieldType = m.group(2)
                    fieldPropertys = m.group(3).split(',')
                    # model
                    content_model += '    public '
                    if fieldType == 'ForeignKey':
                        content_model += fieldPropertys[0] + ' '
                    else:
                        content_model += convert2JaveType.get(fieldType) + ' '
                    content_model += fieldName + ';\n'
                    # migration
                    declaredNullable = False
                    if fieldType == 'ForeignKey':
                        content_migration += fieldName.lower() + '_id ' + convert2SQLType.get(fieldType)
                    else:
                        content_migration += fieldName.lower() + ' ' + convert2SQLType.get(fieldType)
                    isForeignKey = False
                    for p in fieldPropertys:
                        if not isForeignKey and fieldType == 'ForeignKey':
                            isForeignKey = True
                            continue
                        p = p.split("=")
                        p[0].lower()
                        p[1].lower()
                        if p[0] == 'max_length':
                            content_migration += "(" + p[1] + ')'
                        elif p[0] == 'primer_key' and p[1] != 'false':
                            declaredPrimerKey = True
                            content_migration += ' primer key'
                        elif p[0] == 'null':
                            declaredNullable = True
                            if p[1] == 'true':
                                content_migration += ' NULL'
                            else:
                                content_migration += ' NOT NULL'

                    if not declaredNullable:
                        content_migration += ' NOT NULL'
                    if fieldType == 'ForeignKey':
                        content_migration += " REFERENCE " + fieldPropertys[0]
                        isForeignKey = False
                    content_migration += ' ,'

                line = models.readline()
            if not declaredPrimerKey:
                content_migration += 'id INTEGER NOT NULL PRIMER KEY,'
                content_model += '\n public Integer id;\n'
            content_migration = content_migration[:len(content_migration) - 1]
            content_migration = " CREATE TABLE IF NOT EXISTS " + tableName + " (" +content_migration + ")"

            modelTemplate.seek(0, 0)
            template = modelTemplate.read()
            template = re.sub('{{comment}}', comment, template)
            template = re.sub('{{tableName}}', tableName, template)
            template = re.sub('{{content}}', content_model, template)
            modelFile.write(template)
            print("成功生成" + tableName + '.java')
            modelFile.close()

            migrationTemplate.seek(0, 0)
            template = migrationTemplate.read()
            template = re.sub('{{comment}}', comment, template)
            template = re.sub('{{tableName}}', tableName, template)
            template = re.sub('{{content}}', content_migration, template)
            migrationFile.write(template)
            print("成功生成" + tableName + '_migration.java')
            migrationFile.close()


        else:
            line = models.readline()

    models.close()
    migrationTemplate.close()
    modelTemplate.close()


if __name__ == '__main__':
    main("../Server/Resource/models.py",
         "../Client/app/src/main/java/wesayallright/timemanager/db/Models/",
         "../Client/app/src/main/java/wesayallright/timemanager/db/dbHelper/")
