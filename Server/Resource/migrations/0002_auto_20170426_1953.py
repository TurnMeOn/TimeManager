# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2017-04-26 11:53
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Resource', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='group',
            name='group_id',
            field=models.CharField(max_length=10, primary_key=True, serialize=False),
        ),
    ]