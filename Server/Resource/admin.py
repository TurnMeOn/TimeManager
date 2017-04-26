from django.contrib import admin
from .models import School, User, Group, Group_User

# Register your models here.
admin.site.register((School, User, Group, Group_User))
