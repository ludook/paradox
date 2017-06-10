#!/usr/bin/env bash
socat pty,link=/tmp/virtualcom0,raw  tcp:10.0.0.142:3333
