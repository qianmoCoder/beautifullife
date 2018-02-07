# -*- coding: utf-8 -*-

import os
import sys

jarName = "ijiamiencryption.jar"
url = '"http://10.103.23.140:10055/api"'
user = '"app"'
filePath = '"%s"' % (sys.argv[1])
encfilepath = '""'
soPath = '""'
signAlias = '"etcp"'
isApi = '"1"'
planId = '"5"'
sign_command = 'java -jar %s %s %s %s %s %s %s %s %s' % (
    jarName, url, user, filePath, encfilepath, soPath, signAlias, isApi, planId)
print sign_command
os.system(sign_command)
