#!/usr/bin/env python

import os, sys, time
from threading import Thread
import datetime

#Fkt "2011-01-01","2011-01-02","2011-01-03","2011-01-04",

#REMEMBER, if its just one entry add a trailing comma
datelist = ("2011-03-07",)


p1 = "/cygdrive/g/misc/dev/workspace/WNMSExtract/run/wnmsxtr_1.jar"
p1 = "g:\\\\misc\\\\dev\\\\workspace\\\\WNMSExtract\\\\run\\\\wnmsxtr_1.jar"
p2 = "/home/jnramsay/wnmsextract/run/wnmsxtr_1.jar"

memrsrv = "512"
meminit = "512"
network = "TNZ"


class common(Thread):
	def __init__(self,d,p):
		self.d = d
		self.p = p
		self.g = "WNMS_All"
		self.command = "java -Xmx"+memrsrv+"m -Xms"+meminit+"m -jar "+self.p+" -x "+self.g+" -t "+self.d+" -l f -n "+network+" -a -b"
		Thread.__init__(self)

	def run(self):
			print("Processing "+self.g+" "+self.d+"\n")
			print("COMMANDLINE:: "+self.command)
			print("DATETIME   :: "+datetime.datetime.now().isoformat())
			print(self.command)
			os.system(self.command)
			#os.system("python wait.py")

			
class RncCn(common):
	def __init__(self,d,p):
		common.__init__(self,d,p)
		self.g = "WNMS_RncCn"


class INode(common):
	def __init__(self,d,p):
		common.__init__(self,d,p)
		self.g = "WNMS_INode"
		
class INodeVcc(common):
	def __init__(self,d,p):
		common.__init__(self,d,p)
		self.g = "WNMS_INodeVcc"


class NodeB(common):
	def __init__(self,d,p):
		common.__init__(self,d,p)
		self.g = "WNMS_NodeB"
		
class WiPS(common):
	def __init__(self,d,p):
		common.__init__(self,d,p)
		self.g = "WIPS_All"


class WNMS(common):
	def __init__(self,d,p):
		common.__init__(self,d,p)
		self.g = "WNMS_All"


def main(argv=None):
	print("PY DATE PROCESSOR START")
	
	osn = os.uname()[0]
	if(osn=="CYGWIN_NT-5.1"):
		proc = p1
	else:
		proc = p2
	print osn,proc
	
	for date in datelist:
		f0 = WNMS(date,proc)
		#f1 = RncCn(date,proc)
		#f2 = INode(date,proc)
		#f3 = INodeVcc(date,proc)
		#f4 = NodeB(date,proc)
		#fall = (RncCn(date,proc),INode(date,proc),INodeVcc(date,proc),NodeB(date,proc))
		
		f0.start()
		#f1.start()	
		#f2.start()	
		#f3.start()
		#f4.start()
		#for f in fall:
		#	f.start()
		
		while (f0.isAlive()):#f1.isAlive()):# or f2.isAlive() or f3.isAlive() or f4.isAlive()):
			time.sleep(5)
			
	print("PY DATE PROCESSOR COMPLETE")

if __name__ == "__main__":
		sys.exit(main())
