#!/usr/bin/env python

import os, sys, time
from threading import Thread
import datetime

#Fkt "2011-01-01","2011-01-02","2011-01-03","2011-01-04",


p1 = "/cygdrive/g/misc/dev/workspace/WNMSExtract/run/wnmsxtr_1.jar"
p1 = "g:\\\\misc\\\\dev\\\\workspace\\\\WNMSExtract\\\\run\\\\wnmsxtr_1.jar"
p2 = "/home/jnramsay/wnmsextract/run/wnmsxtr_1.jar"

memrsrv = "512"
meminit = "512"
network = "TNZ"


class common(Thread):
	def __init__(self,d,g,p):
		self.d = d
		self.p = p
		self.g = g
		self.command = "java -Xmx"+memrsrv+"m -Xms"+meminit+"m -jar "+self.p+" -x "+self.g+" -t "+self.d+" -l f -n "+network+" -a -b"
		Thread.__init__(self)

	def run(self):
			print("Processing "+self.g+" "+self.d+"\n")
			print("COMMANDLINE:: "+self.command)
			print("DATETIME   :: "+datetime.datetime.now().isoformat())
			os.system(self.command)
			#os.system("python wait.py")



cmdsetlist=(
	('2011-03-25','WNMS_INode'),
	('2011-03-25','WNMS_INodeVcc'),
	('2011-03-25','WNMS_RncCn'),
	('2011-03-26','WNMS_INode'),
	('2011-03-26','WNMS_INodeVcc'),
	('2011-03-26','WNMS_RncCn')
	)

def main(argv=None):
	print("PY DATE PROCESSOR START")
	
	osn = os.uname()[0]
	if(osn=="CYGWIN_NT-5.1"):
		proc = p1
	else:
		proc = p2
	print osn,proc
	
	for cmdset in cmdsetlist:
		f = common(cmdset[0],cmdset[1],proc)
		f.start()

		
		while (f.isAlive()):
			time.sleep(5)
			
	print("PY DATE PROCESSOR COMPLETE")

if __name__ == "__main__":
		sys.exit(main())
