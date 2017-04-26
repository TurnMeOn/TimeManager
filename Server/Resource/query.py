from django.db import models
from django.shortcuts import render
from Resource.models import School, User, Group



def QueryUser(request, user_id):
    user = User.objects.get(user_id=user_id)
    school = School.objects.get(school_id=user.school_id)
    data = {
        'stu_id': user.stu_id,
        'nickname': user.nickname,
        'realname': user.realname,
        'gender': user.gender,
        'school_id': school.school_id,
        'school_name': school.school_name,
        'major': user.major,
    }
    # return render(request, 'Resource/person_information.xml', data)
    return HttpResponse
