package com.study.financial.exception

class RegisterException(override val message: String = "User already exists") : RuntimeException()
