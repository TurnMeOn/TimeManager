from django.db import models
from django.contrib.auth.hashers import make_password, check_password

# Create your models here.

class School(models.Model):
    school_id = models.CharField(max_length=10, primary_key=True)
    school_name = models.CharField(max_length=30)
    school_province = models.CharField(max_length=20)
    school_city = models.CharField(max_length=30)
    def __str__(self):
        return self.school_name

class User(models.Model):
    user_id = models.CharField(max_length=20, primary_key=True)
    password = models.CharField(max_length=16)
    stu_id = models.CharField(max_length=50)
    nickname = models.CharField(max_length=15)
    realname = models.CharField(max_length=10)
    gender = models.CharField(max_length=2,
                            choices=(
                                ('男', '男'),
                                ('女', '女')
                                )
                            )
    birthday = models.DateField()
    mobile = models.IntegerField()
    email = models.EmailField()
    user_updatetime = models.DateField(auto_now_add=True)

    school_id = models.ForeignKey(School, on_delete=models.CASCADE)
    major = models.CharField(max_length=30)
    def __str__(self):
        return self.realname

class Group(models.Model):
    group_id = models.CharField(max_length=10, primary_key=True)
    group_name = models.CharField(max_length=50)
    group_introduction = models.CharField(max_length=100, default="无")
    group_establish_date = models.DateField(auto_now=True)
    group_update_date = models.DateField(auto_now_add=True)
    group_is_public = models.BooleanField()
    group_max_member = models.PositiveSmallIntegerField()
    def __str__(self):
        return self.group_name

class Group_User(models.Model):
    group_id = models.ForeignKey(Group, on_delete=models.CASCADE)
    user_id = models.ForeignKey(User, on_delete=models.CASCADE)
    privilege = models.CharField(max_length=20, choices=(
                                ('owner', 'owner'),
                                ('master', 'master'),
                                ('member', 'member'),
                                ))
    visible = models.BooleanField(default=True)
    update_time = models.DateTimeField(auto_now_add=True)
    # def __str__(self):
    #     return str(self.group_id.group_name)
