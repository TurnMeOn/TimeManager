from django.db import models
from django.contrib.auth.hashers import make_password, check_password

class School(models.Model):
    school_id = models.CharField(max_length=10)
    name = models.CharField(max_length=30)
    province = models.CharField(max_length=20)
    city = models.CharField(max_length=30)
    def __str__(self):
        return self.name

class User(models.Model):
    user_id = models.CharField(max_length=10)
    password = models.CharField(max_length=64)
    stu_id = models.CharField(max_length=20)  # 学号
    nickname = models.CharField(max_length=15)
    realname = models.CharField(max_length=10, null=True)
    gender = models.SmallIntegerField()  # 1是男, 2是女, 3以上是其他
    birthday = models.DateField(null=True)
    mobile = models.CharField(max_length=21)  # 手机号是21位
    email = models.EmailField(null=True)
    updatetime = models.DateField(auto_now_add=True)

    school_index = models.ForeignKey(School, on_delete=models.CASCADE)
    major = models.CharField(max_length=30)
    entry_date = models.DateField()
    def __str__(self):
        return self.realname

class Group(models.Model):
    group_id = models.CharField(max_length=10)
    name = models.CharField(max_length=50)
    introduction = models.CharField(max_length=100, default="无")
    establish_date = models.DateField(auto_now=True)
    update_date = models.DateField(auto_now_add=True)
    is_public = models.BooleanField()
    max_member = models.PositiveSmallIntegerField()
    def __str__(self):
        return self.group_name

class Group_User(models.Model):
    group_index = models.ForeignKey(Group, on_delete=models.CASCADE)
    user_index = models.ForeignKey(User, on_delete=models.CASCADE)
    privilege = models.SmallIntegerField()  # 1是创建者, 2是管理员, 3是普通成员
    visible = models.BooleanField(default=True)
    update_time = models.DateTimeField(auto_now_add=True)


class Affairs(models.Model):
    name = models.CharField(max_length=20)
    type = models.SmallIntegerField()  # 1是课程, 2是目标, 3是活动
    place = models.CharField(max_length=30, default="未填写")
    details = models.CharField(max_length=100, default="无")
    organizer_index = models.ForeignKey(User)
    def __str__(self):
        return self.name


class SingleAffairsTime(models.Model):
    ''' 单一时间 '''
    affair_index = models.ForeignKey(Affairs)
    dateTime = models.DateTimeField()


class SuccessiveAffairsTime(models.Model):
    ''' 连续时间 '''
    affair_index = models.ForeignKey(Affairs)
    time = models.TimeField()
    startData = models.DateField()
    lastData = models.DateField()
    every = models.SmallIntegerField()  # 从最低一位到第7位分别对于星期一到日


class HaveAffair(models.Model):
    user_index = models.ForeignKey(User)
    affair_index = models.ForeignKey(Affairs)


class InGroup(models.Model):
    user_index = models.ForeignKey(User)
    group_index = models.ForeignKey(Group)
    role_index = models.ForeignKey(Group_User)
