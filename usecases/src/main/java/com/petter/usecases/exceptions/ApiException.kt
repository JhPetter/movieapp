package com.petter.usecases.exceptions

import java.io.IOException

class ApiException : IOException("This is an generic error, please try later")

class NetworkException : IOException("To use this app you need an internet connection")