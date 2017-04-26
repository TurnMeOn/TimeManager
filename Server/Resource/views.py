from django.shortcuts import render
from django.http import HttpResponse
from Resource.models import School, User, Group
from django.utils import timezone
# Create your views here.

def index(request):
    return HttpResponse("Here you come!")

def QueryUser(request, user_id):
    user = User.objects.get(user_id=user_id)
    school = user.school_id
    birthdate = (str(user.birthday).split('-'))
    b_y, b_m, b_d = (int(birthdate[0]), int(birthdate[1]), int(birthdate[2]))
    now = timezone.datetime.now()
    n_y, n_m, n_d = (now.year, now.month, now.day)
    age = n_y - b_y
    if n_m < b_m or (n_m == b_m and n_d < b_d):
        age -= 1
    data = {
        'stu_id': user.stu_id,
        'nickname': user.nickname,
        'realname': user.realname,
        'age': age,
        'gender': user.gender,
        'email': user.email,
        'mobile':user.mobile,
        'school_id': school.school_id,
        'school_name': school.school_name,
        'province': school.school_province,
        'city': school.school_city,
        'major': user.major,
    }
    # return render(request, 'Resource/person_information.xml', data)
    # return HttpResponse(data['city'])
    return render(request, 'Resource/person-information.xml', data)
