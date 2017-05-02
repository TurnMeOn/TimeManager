from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^query/user/(?P<user_id>.+)/', views.QueryUser, name='query_user'),
    url(r'^query/group/(?P<group_id>.+)/', views.QueryGroup, name='query_group'),
    url(r'^query/grouplist/(?P<user_id>.+)/', views.QueryGroupList, name='query_grouplist'),
]
