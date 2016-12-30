# lambda-reload-bug
Reproduces a bug with JVM / spring boot when using lambda expression

To reproduce a bug run grails app with ```./grailsw run-app```

Should output something like:
```#
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGSEGV (0xb) at pc=0x00007f497380b27b, pid=710, tid=0x00007f493d14b700
#
# JRE version: Java(TM) SE Runtime Environment (8.0_111-b14) (build 1.8.0_111-b14)
# Java VM: Java HotSpot(TM) 64-Bit Server VM (25.111-b14 mixed mode linux-amd64 compressed oops)
# Problematic frame:
# V  [libjvm.so+0x37127b]  GraphBuilder::try_inline_full(ciMethod*, bool, Bytecodes::Code, Instruction*)+0x2fb
#
# Failed to write core dump. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
#
# An error report file with more information is saved as:
# /home/markus/git/lambda-reload-bug/hs_err_pid710.log
#
# Compiler replay data is saved as:
# /home/markus/git/lambda-reload-bug/replay_pid710.log
#
# If you would like to submit a bug report, please visit:
#   http://bugreport.java.com/bugreport/crash.jsp
#
```

Tested with
```
~/git/lambda-reload-bug$ uname -a
Linux markus-Precision-M4800 4.4.0-57-generic #78-Ubuntu SMP Fri Dec 9 23:50:32 UTC 2016 x86_64 x86_64 x86_64 GNU/Linux

~/git/lambda-reload-bug$ java -version
java version "1.8.0_111"
Java(TM) SE Runtime Environment (build 1.8.0_111-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.111-b14, mixed mode)
```
