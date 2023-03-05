package com.venuprasath.greeting;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public record Quote(String type, Value value) { }
