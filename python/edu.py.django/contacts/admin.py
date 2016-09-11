from django.contrib import admin
from contacts.models import *

class PhonNumberInline(admin.TabularInline):
    model = PhoneNumber
    extra = 0

class EmailAddressInline(admin.TabularInline):
    model = EmailAddress
    extra = 0

class PostalAddressInline(admin.StackedInline):
    model = PostalAddress
    extra = 0

class PersonAdminOptions(admin.ModelAdmin):
    list_filter = ["firstName", "lastName"]
    search_fields = ["lastName"]
    inlines = [PhonNumberInline, EmailAddressInline, PostalAddressInline]

admin.site.register(Person, PersonAdminOptions)
#admin.site.register(PhoneNumber)
#admin.site.register(EmailAddress)
#admin.site.register(PostalAddress)

