from django.shortcuts import render
from django.http import HttpResponse
from Resource.models import School, User, Group
# Create your views here.

def index(request):
    return HttpResponse("Here you come!")

def QueryUser(request, user_id):
    user = User.objects.get(user_id=user_id)
    school = user.school_id
    data = {
        'stu_id': user.stu_id,
        'nickname': user.nickname,
        'realname': user.realname,
        'age': ' ',
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
