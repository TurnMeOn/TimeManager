# -*- coding: utf-8 -*-
# Generated by Django 1.11.1 on 2017-05-10 12:05
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Affairs',
            fields=[
                ('id', models.CharField(max_length=10, primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=20)),
                ('type', models.SmallIntegerField()),
                ('place', models.CharField(default='未填写', max_length=30)),
                ('details', models.CharField(default='无', max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='Group',
            fields=[
                ('group_id', models.CharField(max_length=10, primary_key=True, serialize=False)),
                ('group_name', models.CharField(max_length=50)),
                ('group_introduction', models.CharField(default='无', max_length=100)),
                ('group_establish_date', models.DateField(auto_now=True)),
                ('group_update_date', models.DateField(auto_now_add=True)),
                ('group_is_public', models.BooleanField()),
                ('group_max_member', models.PositiveSmallIntegerField()),
            ],
        ),
        migrations.CreateModel(
            name='Group_User',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('privilege', models.CharField(choices=[('owner', 'owner'), ('master', 'master'), ('member', 'member')], max_length=20)),
                ('visible', models.BooleanField(default=True)),
                ('update_time', models.DateTimeField(auto_now_add=True)),
                ('group_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Resource.Group')),
            ],
        ),
        migrations.CreateModel(
            name='ScheTime',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('place', models.CharField(max_length=20)),
                ('day', models.CharField(max_length=10)),
                ('start_date', models.DateField()),
                ('end_date', models.DateField()),
            ],
        ),
        migrations.CreateModel(
            name='School',
            fields=[
                ('school_id', models.CharField(max_length=10, primary_key=True, serialize=False)),
                ('school_name', models.CharField(max_length=30)),
                ('school_province', models.CharField(max_length=20)),
                ('school_city', models.CharField(max_length=30)),
            ],
        ),
        migrations.CreateModel(
            name='SingleAffairsTime',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('dateTime', models.DateTimeField()),
                ('affair_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Resource.Affairs')),
            ],
        ),
        migrations.CreateModel(
            name='SuccessiveAffairsTime',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('time', models.TimeField()),
                ('startData', models.DateField()),
                ('lastData', models.DateField()),
                ('every', models.SmallIntegerField()),
                ('affair_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Resource.Affairs')),
            ],
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('user_id', models.CharField(max_length=20, primary_key=True, serialize=False)),
                ('password', models.CharField(max_length=16)),
                ('stu_id', models.CharField(max_length=50)),
                ('nickname', models.CharField(max_length=15)),
                ('realname', models.CharField(max_length=10)),
                ('gender', models.CharField(choices=[('男', '男'), ('女', '女')], max_length=2)),
                ('birthday', models.DateField()),
                ('mobile', models.IntegerField()),
                ('email', models.EmailField(max_length=254)),
                ('user_updatetime', models.DateField(auto_now_add=True)),
                ('major', models.CharField(max_length=30)),
                ('entry_date', models.DateField()),
                ('school_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Resource.School')),
            ],
        ),
        migrations.AddField(
            model_name='group_user',
            name='user_id',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Resource.User'),
        ),
        migrations.AddField(
            model_name='affairs',
            name='organizer',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='Resource.User'),
        ),
    ]
