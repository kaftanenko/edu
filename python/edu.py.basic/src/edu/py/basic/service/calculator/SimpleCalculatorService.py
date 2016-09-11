'''
Calcualtor service - simple implementation.

@author: java.developer
Created on 04.04.2012
'''
class SimpleCalculatorService:
    
    PI = 3.1415
    
    def add(self, a, b):
        SimpleCalculatorService.verifyArgsAreInteger(a, b)
        return a + b
    
    def subtract(self, a, b):
        SimpleCalculatorService.verifyArgsAreInteger(a, b)
        return a - b
    
    def multiply(self, a, b):
        SimpleCalculatorService.verifyArgsAreInteger(a, b)
        return a * b
    
    def divide(self, a, b):
        SimpleCalculatorService.verifyArgsAreInteger(a, b)
        return a / b
    
    @staticmethod
    def verifyArgsAreInteger(a, b):
        if not isinstance(a, int) or not isinstance(b, int):
            raise ValueError("Wrong arguments type. Only initeger arguments are supported.")
