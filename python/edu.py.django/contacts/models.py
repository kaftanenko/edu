from django.db import models

# Create your models here.
class Person(models.Model):
    firstName = models.CharField(max_length=63)
    lastName = models.CharField(max_length=63)
    birthDate = models.DateTimeField('birth_date2')
    
    def __unicode__(self):
        return "[firstName = " + self.firstName + ", lastName = " + self.lastName + "]"
    
class Contact(models.Model):
    pass
    
class PhoneNumber(Contact):
    person = models.ForeignKey(Person)
    countryCode = models.IntegerField()
    internalPhoneNumber = models.IntegerField()
    
    def __unicode__(self):
        return "[countryCode = " + str(self.countryCode) + ", internalPhoneNumber = " + str(self.internalPhoneNumber) + "]"
    
class EmailAddress(Contact):
    person = models.ForeignKey(Person)
    emailAddress = models.CharField(max_length=255)
    
    def __unicode__(self):
        return "[emailAddress = " + self.emailAddress + "]"
    
class PostalAddress(Contact):
    person = models.ForeignKey(Person)
    street = models.CharField(max_length=127)
    street2 = models.CharField(max_length=127)
    city = models.CharField(max_length=63)
    zip = models.CharField(max_length=10)
    state = models.CharField(max_length=31)
    country = models.CharField(max_length=31)

    def __unicode__(self):
        return "[city = " + self.city + "]"
    
