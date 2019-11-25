#!/usr/bin/env bash
protoc -I=./ --java_out=./ ./*.proto