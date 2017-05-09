from django.shortcuts import render
from django.http import HttpResponse
from Resource.models import School, User, Group, Group_User
from django.utils import timezone
from lxml import etree
# Create your views here.

def index(request):
    return HttpResponse("Here you come!")

def QueryUser(request, user_id):
    """"""
    user = User.objects.get(user_id=user_id)
    school = user.school_id
    # birthdate = (str(user.birthday).split('-'))
    # b_y, b_m, b_d = (int(birthdate[0]), int(birthdate[1]), int(birthdate[2]))
    # now = timezone.datetime.now()
    # n_y, n_m, n_d = (now.year, now.month, now.day)
    # age = n_y - b_y
    # if n_m < b_m or (n_m == b_m and n_d < b_d):
    #     age -= 1
    data = {
        'stu_id': user.stu_id,
        'nickname': user.nickname,
        'realname': user.realname,
        'birthday': str(user.birthday),
        'gender': user.gender,
        'email': user.email,
        'mobile':user.mobile,
        'school_id': school.school_id,
        'school_name': school.school_name,
        'province': school.school_province,
        'city': school.school_city,
        'major': user.major,
        'updatetime': str(user.user_updatetime),
        'entry_date': str(user.entry_date)
    }
    return render(request, 'Resource/person-information.xml', data, content_type='application/xml')

def QueryGroup(request, group_id):
    group = Group.objects.get(group_id=group_id)
    members = Group_User.objects.filter(group_id=group_id)
    data = {
        'id': group.group_id,
        'name': group.group_name,
        'public': group.group_is_public,
        'establishdate': str(group.group_establish_date),
        'introduction': group.group_introduction,
        'max': group.group_max_member,
        'update': str(group.group_update_date),
        'members': members,
    }

    return render(request, 'Resource/group.xml', data, content_type='application/xml')

def QueryGroupList(request, user_id):
    groups = Group_User.objects.filter(user_id=user_id)
    data = {
        'belongs': user_id,
        'groups': groups,
    }
    return render(request, 'Resource/groupList.xml', data, content_type='application/xml')


def WriteUser(request,):
    xml = etree.parse(filename)
    person = xml.xpath('//*')[0]
    user_id = person.get('id')
    schoolID = person.get('schoolID')
    major = person.get('major')
    mobile = person.get('mobile')
    entry_date = person.get('entryDate')
    pass
